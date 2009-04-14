package org.epic.perleditor.editors.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.ILog;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.epic.core.Constants;
import org.epic.core.util.PerlExecutor;
import org.epic.perleditor.PerlEditorPlugin;

/**
 * Abstract base class for PerlValidator.
 * 
 * This class contains those parts of PerlValidator implementation which are independent of the Eclipse runtime
 * environment to aid testing.
 * 
 * @author jploski
 */
abstract class PerlValidatorBase {

    private static final boolean DEBUG = false;

    private static int maxErrorsShown = 500;

    private static final int BUF_SIZE = 1024;

    private final ILog log;

    private final PerlValidatorErrors errors;

    private final PerlExecutor executor;

    protected PerlValidatorBase(ILog log, PerlExecutor executor) {
        this.log = log;
        this.executor = executor;

        errors = new PerlValidatorErrors();
    }

    /**
     * Validates the provided source code, creates markers on the given IResource.
     */
    public synchronized Set validate(IResource resource, String sourceCode) throws CoreException {
        String perlOutput = runPerl(resource, sourceCode);
        Set uniSet = new HashSet();
        if (DEBUG)
            printPerlOutput(perlOutput);

        // TODO check if perlOutput is empty (indicates error)

        // Mark problem markers as unused
        //
        // TODO: Scheduling all markers for removal at this point is actually
        // wrong because some markers might have been added by validation of
        // another resource (trigger). They should only disappear if validation
        // of all triggers no longer signals a problem. However, for the time
        // being we can live with disappearing markers - they typically do not
        // vanish completely because a related marker is still left in
        // the trigger as a reminder (at least this is the case for the most
        // common 'Can't locate .. in @INC' error message, which always leaves
        // "Compilation failed in require" in the trigger).
        //
        clearAllUsedMarkers(resource);

        List lines = makeLinesList(perlOutput);
        boolean continued = false;

        // Markers have to be added in reverse order
        // Otherwise lower line number will appear at the end of the list
        for (int i = lines.size() - 1; i >= 0; i--) {
            String line = (String) lines.get(i);

            // Is this a continuation of the line i-1?
            if (line.startsWith(" ")) {//$NON-NLS-1$
                continued = true;
                continue;
            } else {
                if (continued)
                    line += lines.get(i + 1);
                continued = false;
            }

            ParsedErrorLine pline = new ParsedErrorLine(line, log);
            IResource errorResource = getErrorResource(pline, resource);
            Integer lineNr = new Integer(pline.getLineNumber());
            resource.getProject();
            if (lineNr.intValue() > 0) {

                String uniName = setErrorMark(resource.getLocation().toString(), lineNr.intValue());
                if (!("".equals(uniName)) && uniName != null) {//$NON-NLS-1$
                    uniSet.add(uniName);
                }

            }
            if (shouldIgnore(pline, errorResource))
                continue;

            PerlValidatorErrors.ErrorMessage errorMsg = errors.getErrorMessage(pline.getMessage());

            Map attributes = new HashMap(11);

            attributes.put(IMarker.SEVERITY, errorMsg.getSeverity());
            attributes.put(Constants.MARKER_ATTR_PERL_ERROR_EXPLANATION, errorMsg.getExplanation());

            if (!pline.isLocalError() && errorResource == resource) {
                // last resort: we have a non-local error, but the resource
                // referred to in the error message could not be found
                attributes.put(IMarker.MESSAGE, pline.getMessage() + " in " + pline.getPath() + " line " + lineNr);

            } else {
                attributes.put(IMarker.MESSAGE, pline.getMessage());
                attributes.put(IMarker.LINE_NUMBER, lineNr);

                if (shouldUnderlineError(errorResource, pline.getLineNumber())) {
                    String errorSourceCode;

                    try {
                        if (errorResource == resource)
                            errorSourceCode = sourceCode;
                        else
                            errorSourceCode = readSourceFile(errorResource);

                        underlineError(errorResource, errorSourceCode, pline.getLineNumber(), attributes);
                    } catch (IOException e) {
                        Status status = new Status(Status.ERROR, PerlEditorPlugin.getPluginId(), IStatus.OK,
                                "Could not read source file of resource: " + errorResource.getLocation()
                                        + ". Error markers will " + "be incorrect for this resource.", e);
                        // trouble reading the other file's source code
                        throw new CoreException(status);
                    }
                }
            }

            addMarker(errorResource, attributes);
        }

        removeUnusedMarkers(resource);
        return uniSet;
    }

    protected abstract void addMarker(IResource resource, Map attributes);

    protected abstract void clearAllUsedMarkers(IResource resource);

    protected IResource getErrorResource(ParsedErrorLine line, IResource resource) {
        return line.isLocalError() ? resource : null;
    }

    protected List getPerlArgs() {
        List args = new ArrayList();
        args.add("-c");
        return args;
    }

    protected abstract boolean isProblemMarkerPresent(ParsedErrorLine line, IResource resource);

    /**
     * Reads contents of the given text file.
     * 
     * @param path file that should be read
     * @return text contents
     */
    protected String readSourceFile(String path) throws IOException {
        BufferedReader in = null;

        try {
            StringWriter sourceCode = new StringWriter();

            char[] buf = new char[BUF_SIZE];
            in = new BufferedReader(new FileReader(path));

            int read = 0;
            while ((read = in.read(buf)) > 0) {
                sourceCode.write(buf, 0, read);
            }
            return sourceCode.toString();
        } finally {
            if (in != null)
                try {
                    in.close();
                } catch (IOException e) {
                }
        }
    }

    /**
     * Reads contents of the given text file.
     * 
     * @param resource file that should be read
     * @return text contents
     */
    protected String readSourceFile(IResource resource) throws IOException {
        return readSourceFile(resource.getLocation().makeAbsolute().toString());
    }

    protected abstract void removeUnusedMarkers(IResource resource);

    protected abstract boolean shouldUnderlineError(IResource resource, int lineNr);

    /**
     * Splits up the given text content into a list of up to maxErrorsShown lines. If there are more lines in content,
     * remaining lines are ignored.
     * 
     * @return a list of Strings, one per line (without line terminators)
     */
    private static List makeLinesList(String perlOutput) {
        List lines = new ArrayList();
        StringTokenizer st = new StringTokenizer(perlOutput, "\r\n");
        int lineCount = 0;

        while (st.hasMoreTokens() && lineCount < maxErrorsShown) {
            lines.add(st.nextToken());
            lineCount++;
        }
        return lines;
    }

    private void printPerlOutput(String perlOutput) {
        if (perlOutput.indexOf("syntax OK") == -1) {
            System.out.println("-----------------------------------------");
            System.out.println("           OUTPUT");
            System.out.println("-----------------------------------------");
            System.out.println(perlOutput);
            System.out.println("-----------------------------------------");
        }
    }

    /**
     * @return true if the error message contained in the line should be ignored, false otherwise
     */
    private boolean shouldIgnore(ParsedErrorLine line, IResource resource) {
        // No line number?
        if (line.getLineNumber() < 0)
            return true;

        // Marker already present?
        if (isProblemMarkerPresent(line, resource)) {
            return true;
        }

        // Useless error message, with better ones around in the same output?
        if (line.getMessage().indexOf("BEGIN failed--compilation aborted") == 0)
            return true;
        else
            return false;
    }

    /**
     * Executes the Perl interpreter to validate the given script.
     * 
     * @param resource source file
     * @param sourceCode text of the source file
     * @return stderr output of the Perl interpreter
     */
    private String runPerl(IResource resource, String sourceCode) throws CoreException {
        return executor.execute(resource, getPerlArgs(), sourceCode).stderr;
    }

    private void underlineError(IResource resource, String sourceCode, int lineNo, Map attributes) {
        // Get start and end offset
        int lineOffset = 0;
        try {
            Document document = new Document(sourceCode);
            lineOffset = document.getLineOffset(lineNo - 1);
        } catch (BadLocationException e) {
            // this one should never occur
            log.log(new Status(Status.ERROR, PerlEditorPlugin.getPluginId(), IStatus.OK,
                    "Unexpected exception in PerlValidator.underlineError: " + resource.getFullPath() + ", lineNo: " + lineNo
                            + "; report it as bug in plug-in " + PerlEditorPlugin.getPluginId(), e));
        }

        int endOfLine = sourceCode.indexOf("\n", lineOffset);
        String markerLine;

        if (endOfLine != -1) {
            markerLine = sourceCode.substring(lineOffset, endOfLine);
        } else {
            markerLine = sourceCode.substring(lineOffset);
        }

        char[] bytes = markerLine.toCharArray();

        int start = 0;
        while (start < bytes.length) {
            if (bytes[start] != '\t' && bytes[start] != ' ') {
                break;
            }
            start++;
        }

        start += lineOffset;

        int end = start + markerLine.trim().length();

        attributes.put(IMarker.CHAR_START, new Integer(start));
        attributes.put(IMarker.CHAR_END, new Integer(end));
    }

    public String setErrorMark(String path, int lineNum) {
        String uniName = null;

        String[][] result = matchString(path, lineNum);

        int first = 0;
        int second = 0;
        if (result != null) {
            if (result[0][0] != null) {
                first = Integer.parseInt(result[0][0]);
            }
            if (result[1][0] != null) {
                second = Integer.parseInt(result[1][0]);

            }
        }

        if (lineNum > first && lineNum < second && result != null) {
            int index1 = result[1][1].indexOf("[");//$NON-NLS-1$
            int index2 = result[1][1].indexOf("]");//$NON-NLS-1$

            int inde1 = result[0][1].indexOf("[");//$NON-NLS-1$
            int inde2 = result[0][1].indexOf("]");//$NON-NLS-1$

            String uniNameFir = null;
            String uniNameSec = null;
            if (index1 > 0 && index2 > index1) {
                String nodeAllName = result[1][1].substring(index1, index2);
                int index3 = nodeAllName.indexOf(" ");//$NON-NLS-1$
                if (index3 > 0) {
                    uniNameSec = nodeAllName.substring(1, index3);
                }

            }

            if (inde1 > 0 && inde2 > inde1) {
                String nodeAllName = result[0][1].substring(index1, index2);
                int index3 = nodeAllName.indexOf(" ");//$NON-NLS-1$
                if (index3 > 0) {
                    uniNameFir = nodeAllName.substring(1, index3);
                }

            }

            if (uniNameFir.equals(uniNameSec)) {
                uniName = uniNameFir;
            }

        }
        return uniName;
    }

    public String[][] matchString(String path, int lineNum) {
        String[][] result = new String[2][2];
        Pattern patternStart = Pattern.compile("\\[\\s*(\\w)+_\\d+\\s*\\w+\\s*\\]\\s*start");//$NON-NLS-1$
        Pattern patternStop = Pattern.compile("\\[\\s*(\\w)+_\\d+\\s*\\w+\\s*\\]\\s*stop");//$NON-NLS-1$
        File file = new File(path);
        FileReader fread = null;
        try {
            fread = new FileReader(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(fread);
        int point = 0;
        String str = null;
        String strtmp = null;
        int tmp = 0;
        int min = lineNum;
        try {
            while ((str = br.readLine()) != null) {
                point++;
                strtmp = str.trim();
                if (point < lineNum) {
                    Matcher matchStart = patternStart.matcher(strtmp);
                    if (matchStart.find()) {
                        tmp = lineNum - point;
                        if (tmp < min) {
                            min = tmp;
                            result[0][0] = String.valueOf(point);
                            result[0][1] = str;
                        }
                    }

                } else if (point > lineNum) {
                    Matcher matchStop = patternStop.matcher(strtmp);
                    if (matchStop.find()) {
                        result[1][0] = String.valueOf(point);
                        result[1][1] = str;
                        break;
                    }
                } else {
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
                fread.close();
                file = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    protected static class ParsedErrorLine {

        private static final Pattern errorLineNoPattern = Pattern.compile("^(.*) at (\\S+) line (\\d+)[\\.,]");//$NON-NLS-1$

        private static final Pattern cgiCarpPattern = Pattern.compile("^\\[.*?\\] \\S+: (.*)");//$NON-NLS-1$

        private final ILog log;

        private final String line;

        private final String msg;

        private final String path;

        private final int lineNo;

        public ParsedErrorLine(String line, ILog log) {
            this.line = line;
            this.log = log;

            Matcher m = errorLineNoPattern.matcher(line);
            if (m.find()) {
                msg = normalizeMsg(m.group(1));
                path = m.group(2);
                lineNo = parseInt(m.group(3));
            } else {
                msg = normalizeMsg(line);
                path = "-";
                lineNo = -1;
            }
        }

        public int getLineNumber() {
            return lineNo;
        }

        public String getMessage() {
            return msg;
        }

        public String getPath() {
            return path;
        }

        public boolean isLocalError() {
            return "-".equals(path);
        }

        public String toString() {
            return msg + ", " + path + ":" + lineNo;
        }

        private int parseInt(String str) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                // this one should never occur
                log.log(new Status(Status.ERROR, PerlEditorPlugin.getPluginId(), IStatus.OK,
                        "Could not parse line number contained in Perl " + "error message {" + line + "}; report it as a bug "
                                + "in plug-in " + PerlEditorPlugin.getPluginId(), e));
                return -1;
            }
        }

        private String normalizeMsg(String msg) {
            return stripCGICarpOutput(msg);
        }

        /**
         * @return msg with CGI::Carp's timestamp stripped from the beginning (if it was present)
         */
        private String stripCGICarpOutput(String msg) {
            if (msg.startsWith("[")) {
                Matcher m = cgiCarpPattern.matcher(msg);
                if (m.find())
                    return m.group(1);
            }
            return msg;
        }
    }
}
