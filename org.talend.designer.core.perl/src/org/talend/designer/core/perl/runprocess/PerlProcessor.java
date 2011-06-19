// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.core.perl.runprocess;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Level;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.IBreakpointManager;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.core.ILaunchConfigurationType;
import org.eclipse.debug.core.ILaunchConfigurationWorkingCopy;
import org.eclipse.debug.core.ILaunchManager;
import org.eclipse.debug.core.model.IBreakpoint;
import org.eclipse.jface.preference.IPreferenceStore;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.PluginChecker;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.general.Project;
import org.talend.core.model.process.IContext;
import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.properties.Property;
import org.talend.core.model.utils.PerlResourcesHelper;
import org.talend.core.prefs.CorePreferenceInitializer;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.codegen.ICodeGenerator;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.core.ISyntaxCheckableEditor;
import org.talend.designer.core.perl.DesignerPerlPlugin;
import org.talend.designer.core.perl.i18n.Messages;
import org.talend.designer.core.runprocess.Processor;
import org.talend.designer.runprocess.ItemCacheManager;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.designer.runprocess.ProcessorUtilities;

/**
 * DOC chuger class global comment. Detailled comment <br/>
 * 
 * $Id: PerlProcessor.java 52559 2010-12-13 04:14:06Z nrousseau $
 * 
 */
public class PerlProcessor extends Processor {

    public static final String PROCESSOR_TYPE = "perlProcessor"; //$NON-NLS-1$

    /** Tells if filename is based on id or label of the process. */
    private boolean filenameFromLabel;

    private Property property;

    /**
     * Constructs a new PerlProcessor.
     * 
     * @param process Process to be turned in PERL code.
     * @param filenameFromLabel Tells if filename is based on id or label of the process.
     */
    public PerlProcessor(IProcess process, Property property, boolean filenameFromLabel) {
        super(process);
        this.property = property;
        this.process = process;
        this.filenameFromLabel = filenameFromLabel;
    }

    public void initPaths(IContext context) throws ProcessorException {
        if (context.equals(this.context)) {
            return;
        }
        try {
            project = PerlUtils.getProject();
        } catch (CoreException e1) {
            throw new ProcessorException(Messages.getString("PerlProcessor.notFoundedPerlProject")); //$NON-NLS-1$
        }
        initCodePath(context);
        this.context = context;
    }

    private void initCodePath(IContext context) throws ProcessorException {

        String projectName = PerlResourcesHelper.getRootProjectName(property);
        codePath = new Path(PerlResourcesHelper.getJobFileName(projectName, filenameFromLabel ? escapeFilename(process.getName())
                : process.getId(), process.getVersion())); //$NON-NLS-1$

        contextPath = new Path(PerlResourcesHelper.getContextFileName(projectName, filenameFromLabel ? process.getName()
                : process.getId(), process.getVersion(), context.getName())); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public void initPath() throws ProcessorException {
        initCodePath(context);
    }

    public void generateCode(boolean statistics, boolean trace, boolean perlProperties, boolean exportAsOSGI) throws ProcessorException {
        // Should never be called.
        generateCode(statistics, trace, perlProperties);
    }
    public void generateCode(boolean statistics, boolean trace, boolean perlProperties) throws ProcessorException {
        super.generateCode(statistics, trace, perlProperties);
        try {
            ICodeGenerator codeGen;
            ICodeGeneratorService service = DesignerPerlPlugin.getDefault().getCodeGeneratorService();
            if (perlProperties) {
                String perlInterpreter = getInterpreter();
                String perlLib = getLibraryPath();
                String currentPerlProject = PerlResourcesHelper.getRootProjectName(property);
                String codeLocation = getCodeLocation();

                codeGen = service.createCodeGenerator(process, statistics, trace, perlInterpreter, perlLib, codeLocation,
                        currentPerlProject);

            } else {
                codeGen = service.createCodeGenerator(process, statistics, trace);
            }
            // set the selected context. if don't find, will keep default
            if (!process.getContextManager().getDefaultContext().getName().equals(context.getName())) {
                boolean found = false;
                for (IContext c : process.getContextManager().getListContext()) {
                    if (c.getName().equals(context.getName())) {
                        found = true;
                    }
                }
                if (found) {
                    codeGen.setContextName(context.getName());
                }
            }

            String processCode = ""; //$NON-NLS-1$
            try {
                processCode = codeGen.generateProcessCode();
                if (PluginChecker.isSnippetsPluginLoaded()) {
                    processCode = replaceSnippet(processCode);
                }
            } catch (SystemException e) {
                throw new ProcessorException(Messages.getString("Processor.generationFailed"), e); //$NON-NLS-1$
            }

            // Generating files
            IFile codeFile = project.getFile(codePath);
            InputStream codeStream = new ByteArrayInputStream(processCode.getBytes());
            if (!codeFile.exists()) {
                // see bug 0003592, detele file with different case in windows
                deleteFileIfExisted(codeFile);

                codeFile.create(codeStream, true, null);
            } else {
                codeFile.setContents(codeStream, true, false, null);
            }

            // Set Breakpoints in generated code file
            List<INode> breakpointNodes = CorePlugin.getContext().getBreakpointNodes(process);
            if (!breakpointNodes.isEmpty()) {
                String[] nodeNames = new String[breakpointNodes.size()];
                int pos = 0;
                String nodeName;
                for (INode node : breakpointNodes) {
                    nodeName = node.getUniqueName();
                    if (node.getComponent().getMultipleComponentManagers().size() > 0) {
                        nodeName += "_" + node.getComponent().getMultipleComponentManagers().get(0).getInput().getName(); //$NON-NLS-1$
                    }
                    nodeNames[pos++] = "[" + nodeName + " main ] start"; //$NON-NLS-1$ //$NON-NLS-2$
                }
                int[] lineNumbers = getLineNumbers(codeFile, nodeNames);
                setBreakpoints(codeFile, lineNumbers);
            }

            // updateContextCode(codeGen);
            syntaxCheck();

            service.createPerlRoutineSynchronizer().syncAllRoutines();
            if (checkableEditor != null) {
                // removed by bug 5033
                // checkableEditor.validateSyntax();
            }
        } catch (CoreException e1) {
            if (e1.getStatus() != null && e1.getStatus().getException() != null) {
                ExceptionHandler.process(e1.getStatus().getException());
            }
            throw new ProcessorException(Messages.getString("Processor.tempFailed"), e1); //$NON-NLS-1$
        } catch (SystemException e) {
            throw new ProcessorException(Messages.getString("Processor.tempFailed"), e); //$NON-NLS-1$
        }
    }

    private void syntaxCheck() {
        if (checkableEditor != null) {
            checkableEditor.validateSyntax();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getCodeContext()
     */
    public String getCodeContext() {
        return getCodeProject().getLocation().append(getContextPath()).removeLastSegments(1).toOSString();
    }

    private static String escapeFilename(final String filename) {
        return filename != null ? filename.replace(" ", "") : ""; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }

    /**
     * Getter for codePath.
     * 
     * @return the codePath
     */
    public IPath getCodePath() {
        return this.codePath;
    }

    /**
     * Getter for contextPath.
     * 
     * @return the contextPath
     */
    public IPath getContextPath() {
        return this.contextPath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getCodeProject()
     */
    public IProject getCodeProject() {
        return this.project;
    }

    /**
     * Find line numbers of the beginning of the code of process nodes.
     * 
     * @param file Code file where we are searching node's code.
     * @param nodes List of nodes searched.
     * @return Line numbers where code of nodes appears.
     * @throws CoreException Search failed.
     */
    private static int[] getLineNumbers(IFile file, String[] nodes) throws CoreException {
        List<Integer> lineNumbers = new ArrayList<Integer>();

        // List of code's lines searched in the file
        List<String> searchedLines = new ArrayList<String>();
        for (String node : nodes) {
            // searchedLines.add("[" + node + " main ] start");
            searchedLines.add(node);
        }

        LineNumberReader lineReader = new LineNumberReader(new InputStreamReader(file.getContents()));
        try {
            String line = lineReader.readLine();
            while (!searchedLines.isEmpty() && line != null) {
                boolean nodeFound = false;
                for (Iterator<String> i = searchedLines.iterator(); !nodeFound && i.hasNext();) {
                    String nodeMain = i.next();
                    if (line.indexOf(nodeMain) != -1) {
                        nodeFound = true;
                        i.remove();

                        // Search the first valid code line
                        boolean lineCodeFound = false;
                        line = lineReader.readLine();
                        while (line != null && !lineCodeFound) {
                            if (isCodeLine(line)) {
                                lineCodeFound = true;
                                lineNumbers.add(new Integer(lineReader.getLineNumber()));
                            }
                            line = lineReader.readLine();
                        }
                    }
                }
                line = lineReader.readLine();
            }
        } catch (IOException ioe) {
            IStatus status = new Status(IStatus.ERROR, "", IStatus.OK, "Source code read failure.", ioe); //$NON-NLS-1$ //$NON-NLS-2$
            throw new CoreException(status);
        }

        int[] res = new int[lineNumbers.size()];
        int pos = 0;
        for (Integer i : lineNumbers) {
            res[pos++] = i.intValue();
        }
        return res;
    }

    /**
     * Return line number where stands specific node in code generated.
     * 
     * @param nodeName
     */
    public int getLineNumber(String nodeName) {
        IFile codeFile = project.getFile(codePath);
        int[] lineNumbers = new int[] { 0 };
        try {
            lineNumbers = PerlProcessor.getLineNumbers(codeFile, new String[] { nodeName });
        } catch (CoreException e) {
            // e.printStackTrace();
            ExceptionHandler.process(e);
        }
        if (lineNumbers.length > 0) {
            return lineNumbers[0];
        } else {
            return 0;
        }
    }

    /**
     * Tells if a line is a line of perl code, not an empty or comment line.
     * 
     * @param line The tested line of code.
     * @return true if the line is a line of code.
     */
    private static boolean isCodeLine(String line) {
        String trimed = line.trim();
        return trimed.length() > 0 && trimed.charAt(0) != '#';
    }

    /**
     * Set perl breakpoints on a perl file.
     * 
     * @param codeFile Perl file in wich breakpoints are added.
     * @param lineNumbers Line numbers in the source file where breakpoints are installed.
     * @throws CoreException Breakpoint addition failed.
     */
    private static void setBreakpoints(IFile codeFile, int[] lineNumbers) throws CoreException {
        final String perlBrekPointMarker = "org.epic.debug.perlLineBreakpointMarker"; //$NON-NLS-1$
        codeFile.deleteMarkers(perlBrekPointMarker, true, IResource.DEPTH_ZERO);

        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IConfigurationElement[] configElems = registry.getConfigurationElementsFor("org.eclipse.debug.core.breakpoints"); //$NON-NLS-1$
        IConfigurationElement perlBreakConfigElem = null;
        for (IConfigurationElement elem : configElems) {
            if (elem.getAttribute("id").equals("perlLineBreakpoint")) { //$NON-NLS-1$ //$NON-NLS-2$
                perlBreakConfigElem = elem;
            }
        }
        if (perlBreakConfigElem == null) {
            IStatus status = new Status(IStatus.ERROR, DesignerPerlPlugin.PLUGIN_ID, IStatus.OK,
                    "Breakpoint implementation not found.", null); //$NON-NLS-1$
            throw new CoreException(status);
        }

        IBreakpointManager breakpointManager = DebugPlugin.getDefault().getBreakpointManager();
        for (int line : lineNumbers) {
            IMarker breakMarker = codeFile.createMarker(perlBrekPointMarker);
            breakMarker.setAttribute(IBreakpoint.ID, "perlBreak" + line); //$NON-NLS-1$
            breakMarker.setAttribute(IMarker.LINE_NUMBER, new Integer(line));
            breakMarker.setAttribute(IMarker.CHAR_START, new Integer(-1));
            breakMarker.setAttribute(IMarker.CHAR_END, new Integer(-1));
            breakMarker.setAttribute("PerlDebug_INVALID_POS", Boolean.FALSE); //$NON-NLS-1$
            breakMarker.setAttribute(IBreakpoint.PERSISTED, Boolean.TRUE);
            breakMarker.setAttribute(IBreakpoint.ENABLED, Boolean.TRUE);
            breakMarker.setAttribute(IBreakpoint.REGISTERED, Boolean.TRUE);

            IBreakpoint breakpoint = (IBreakpoint) perlBreakConfigElem.createExecutableExtension("class"); //$NON-NLS-1$
            breakpoint.setMarker(breakMarker);
            breakpointManager.addBreakpoint(breakpoint);
        }
    }

    /*
     * Get the interpreter for Perl language.
     * 
     * @see org.talend.designer.runprocess.IProcessor#getInterpreter()
     */
    public String getInterpreter() throws ProcessorException {
        // if the interpreter has been set to a specific one (not standard),
        // then this value won't be null
        String interpreter = super.getInterpreter();
        if (interpreter != null) {
            return interpreter;
        }
        return getDefaultInterpreter();

    }

    public static String getDefaultInterpreter() throws ProcessorException {
        String interpreter;
        IPreferenceStore prefStore = CorePlugin.getDefault().getPreferenceStore();
        interpreter = prefStore.getString(ITalendCorePrefConstants.PERL_INTERPRETER);
        if (interpreter == null || interpreter.length() == 0) {
            throw new ProcessorException(Messages.getString("Processor.configurePerl")); //$NON-NLS-1$
        }
        return interpreter;

    }

    public String getLibraryPath() throws ProcessorException {
        // if the library path has been set to a specific one (not standard),
        // then this value won't be null
        String libraryPath = super.getLibraryPath();
        if (libraryPath != null) {
            return libraryPath;
        }
        try {
            libraryPath = PerlUtils.getPerlModulePath().toOSString();
        } catch (CoreException e) {
            throw new ProcessorException(Messages.getString("Processor.perlModuleNotFound")); //$NON-NLS-1$
        }
        return libraryPath;
    }

    public String getCodeLocation() throws ProcessorException {
        // if the routine path has been set to a specific one (not standard),
        // then this value won't be null
        String codeLocation = super.getCodeLocation();
        if (codeLocation != null) {
            return codeLocation;
        }
        return this.getCodeProject().getLocation().toOSString();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getProcessorType()
     */
    public String getProcessorType() {
        return ECodeLanguage.PERL.getName();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#setProcessorStates(java.lang .String)
     */
    public void setProcessorStates(int states) {
        // do nothings for perl right now.

    }

    private ISyntaxCheckableEditor checkableEditor;

    public void setSyntaxCheckableEditor(ISyntaxCheckableEditor editor) {
        this.checkableEditor = editor;
    }

    /**
     * 
     * DOC wzhang Comment method "getPerlPathCommand". see bug 6972.
     * 
     * @return
     * @throws IOException
     */
    private static String getPerlPathCommand() throws IOException {
        Process p = null;
        Properties envVars = new Properties();
        Runtime r = Runtime.getRuntime();
        String os = System.getProperty("os.name").toLowerCase(); //$NON-NLS-1$
        if ((os.indexOf("windows 95") > -1) || (os.indexOf("windows 98") > -1)) { //$NON-NLS-1$
            p = r.exec("command.com /c path"); //$NON-NLS-1$
        } else if ((os.indexOf("nt") > -1) || (os.indexOf("windows") > -1)) { //$NON-NLS-1$ //$NON-NLS-2$ 
            p = r.exec("cmd.exe /c path"); //$NON-NLS-1$
        } else {
            p = r.exec("env"); //$NON-NLS-1$
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        String line = null;
        while ((line = br.readLine()) != null) {
            int idx = line.indexOf('=');
            String key = line.substring(0, idx);
            String value = line.substring(idx + 1);
            envVars.setProperty(key.toUpperCase(), value);
        }

        String perlPath = Platform.getInstallLocation().getURL().getFile().substring(1)
                + CorePreferenceInitializer.PERL_EMBEDDED_INTERPRETER_DIRECTORY;
        File perlDir = new File(perlPath);
        if (!perlDir.exists()) {
            // perlPath = CorePreferenceInitializer.PERL_WIN32_INTERPRETER_PATH;
            IPreferenceStore store = CorePlugin.getDefault().getPreferenceStore();
            perlPath = store.getString(ITalendCorePrefConstants.PERL_INTERPRETER);
        }
        perlPath = new File(perlPath).getParentFile().getAbsolutePath();

        String oldPath = envVars.getProperty("PATH"); //$NON-NLS-1$
        if (!oldPath.contains(perlPath)) {
            oldPath = perlPath + ";" + oldPath; //$NON-NLS-1$
        }
        return oldPath;
    }

    public static int exec(StringBuffer out, StringBuffer err, IPath absCodePath, String contextName, Level level,
            String perlInterpreterLibOption, String perlModuleDirectoryOption, int statOption, int traceOption,
            String... codeOptions) throws ProcessorException {

        // String[] cmd = Processor.getCommandLine(absCodePath, contextName,
        // perlInterpreterLibOption,
        // perlModuleDirectoryOption, statOption, traceOption, codeOptions);

        String[] cmd = getCommandLineByCondition(getDefaultInterpreter(), absCodePath, perlInterpreterLibOption,
                perlModuleDirectoryOption, true);
        cmd = addCommmandLineAttch(true, cmd, contextName, statOption, traceOption, codeOptions);
        Processor.logCommandLine(cmd, level);
        try {
            int status = -1;

            Process process = Runtime.getRuntime().exec(cmd);

            Processor.createProdConsThread(process.getErrorStream(), true, 1024, out, err).start();

            Processor.createProdConsThread(process.getInputStream(), false, 1024, out, err).start();

            status = process.waitFor();

            return status;
        } catch (IOException ioe) {
            throw new ProcessorException(Messages.getString("Processor.execFailed"), ioe); //$NON-NLS-1$
        } catch (InterruptedException ie) {
            throw new ProcessorException(Messages.getString("Processor.execFailed"), ie); //$NON-NLS-1$
        }
    }

    /**
     * get executable commandline by context conditon.
     * 
     * @param absCodePath
     * @param perlInterpreterLibOption
     * @param perlModuleDirectoryOption
     * @return
     * @throws ProcessorException
     */
    private static String[] getCommandLineByCondition(String perlInterpreter, IPath absCodePath, String perlInterpreterLibOption,
            String perlModuleDirectoryOption, boolean isExe) throws ProcessorException {
        assert (absCodePath != null);
        String[] cmd = new String[] {};
        // see bug 7622.
        if (isExe) {
            // see bug 6972
            String os = Platform.getOS();
            if (os.equals(Platform.OS_WIN32)) {
                cmd = (String[]) ArrayUtils.add(cmd, "cmd.exe"); //$NON-NLS-1$
                cmd = (String[]) ArrayUtils.add(cmd, "/c"); //$NON-NLS-1$
                cmd = (String[]) ArrayUtils.add(cmd, "set"); //$NON-NLS-1$
                try {
                    cmd = (String[]) ArrayUtils.add(cmd, "path=" + getPerlPathCommand()); //$NON-NLS-1$
                } catch (IOException e) {
                    throw new ProcessorException(e);
                }
                cmd = (String[]) ArrayUtils.add(cmd, "&"); //$NON-NLS-1$
            }
        }
        cmd = (String[]) ArrayUtils.add(cmd, perlInterpreter);

        if (perlInterpreterLibOption != null && perlInterpreterLibOption.length() > 0) {
            cmd = (String[]) ArrayUtils.add(cmd, perlInterpreterLibOption);
        }
        if (perlModuleDirectoryOption != null && perlModuleDirectoryOption.length() > 0) {
            cmd = (String[]) ArrayUtils.add(cmd, perlModuleDirectoryOption);
        }
        if (absCodePath != null) {
            cmd = (String[]) ArrayUtils.add(cmd, absCodePath.toOSString());
        }
        return cmd;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#getTypeName()
     */
    public String getTypeName() {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#saveLaunchConfiguration()
     */
    public Object saveLaunchConfiguration() throws CoreException {
        ILaunchConfiguration config = null;
        ILaunchManager launchManager = DebugPlugin.getDefault().getLaunchManager();
        String projectName = this.getCodeProject().getName();
        ILaunchConfigurationType type = launchManager.getLaunchConfigurationType(PerlUtils.PERL_LAUNCHCONFIGURATION);
        if (type != null) {
            ILaunchConfigurationWorkingCopy wc = type.newInstance(null,
                    launchManager.generateUniqueLaunchConfigurationNameFrom(this.getCodePath().lastSegment()));
            wc.setAttribute(PerlUtils.ATTR_STARTUP_FILE, this.getCodePath().toOSString());
            wc.setAttribute(PerlUtils.ATTR_PROJECT_NAME, projectName);
            wc.setAttribute(PerlUtils.ATTR_WORKING_DIRECTORY, (String) null);
            wc.setAttribute(PerlUtils.ATTR_PROGRAM_PARAMETERS, CTX_ARG + context.getName());

            config = wc.doSave();
        }
        return config;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#saveLaunchConfigurationWithParam ()
     */
    public Object saveLaunchConfigurationWithParam(String parameterStr) throws CoreException {
        return saveLaunchConfiguration();
    }

    public String[] getCommandLine() throws ProcessorException {
        String interpreter = getInterpreter();
        String perlInterpreterLibOption = null;
        String perlModuleDirectoryOption = null;
        String perlLib = getLibraryPath();
        perlInterpreterLibOption = perlLib != null && perlLib.length() > 0 ? "-I" + perlLib : ""; //$NON-NLS-1$ //$NON-NLS-2$

        IPath absCodePath = Path.fromOSString(getCodeLocation()).append(this.getCodePath());
        String[] cmd = getCommandLineByCondition(interpreter, absCodePath, perlInterpreterLibOption, perlModuleDirectoryOption,
                false);
        // achen modify to fix 0001268
        if (!ProcessorUtilities.isExportConfig()) {
            return cmd;
        } else {
            List<String> list = new ArrayList<String>();
            if (Platform.OS_LINUX.equals(getTargetPlatform())) {
                list.add("cd `dirname $0`\n"); //$NON-NLS-1$
            } else {
                list.add("cd %~dp0\r\n"); //$NON-NLS-1$
            }
            list.addAll(Arrays.asList(cmd));
            return list.toArray(new String[0]);
        }
        // end

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.Processor#generateContextCode(boolean, boolean, boolean)
     */
    @Override
    public void generateContextCode() throws ProcessorException {
        RepositoryContext repositoryContext = (RepositoryContext) CorePlugin.getContext().getProperty(
                Context.REPOSITORY_CONTEXT_KEY);
        Project repositoryProject = repositoryContext.getProject();

        ICodeGenerator codeGen;
        ICodeGeneratorService service = DesignerPerlPlugin.getDefault().getCodeGeneratorService();
        String perlInterpreter = getInterpreter();
        String perlLib = getLibraryPath();
        String currentPerlProject = repositoryProject.getTechnicalLabel();
        String codeLocation = getCodeLocation();

        codeGen = service.createCodeGenerator(process, false, false, perlInterpreter, perlLib, codeLocation, currentPerlProject);

        updateContextCode(codeGen);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.runprocess.IProcessor#computeLibrariesPath(Set<String>)
     */
    public void computeLibrariesPath(Set<String> jobModuleList) {
    }

    public Property getProperty() {
        if (property == null) {
            property = ItemCacheManager.getProcessItem(process.getId(), process.getVersion()).getProperty();
        }
        return property;
    }

}
