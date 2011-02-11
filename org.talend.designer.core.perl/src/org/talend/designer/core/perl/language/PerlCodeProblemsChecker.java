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
package org.talend.designer.core.perl.language;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.language.CodeProblemsChecker;
import org.talend.core.model.process.Problem;
import org.talend.core.model.process.Problem.ProblemStatus;
import org.talend.designer.codegen.IAloneProcessNodeConfigurer;
import org.talend.designer.core.perl.i18n.Messages;
import org.talend.designer.core.perl.runprocess.PerlProcessor;
import org.talend.designer.runprocess.ProcessorException;

/**
 * Check syntax of Perl expressions.
 * 
 * A temporary file is created and loaded with expression, then the file is checked by the perl.exe command.
 * 
 * Returned errors by perl.exe are filtered and returned.
 * 
 * $Id: PerlCodeProblemsChecker.java 52559 2010-12-13 04:14:06Z nrousseau $
 * 
 */
public class PerlCodeProblemsChecker extends CodeProblemsChecker {

    private static File tempFile;

    public static final String STRING1_EXPRESSION = "-e"; //$NON-NLS-1$

    public static final String STRING1_PREFIX = " at "; //$NON-NLS-1$

    public static final String STRING1_SUFFIX = " line "; //$NON-NLS-1$

    public static final String STRING1_SUFFIX_PREFIX = STRING1_PREFIX + STRING1_SUFFIX;

    public static final String STRING2_SUFFIX = " had compilation errors"; //$NON-NLS-1$

    public static final String STRING2_REPLACED = "=> expression had compilation errors"; //$NON-NLS-1$

    public static final int PERL_STATUS_OK = 0;

    public static final int PERL_STATUS_ERROR_EXPRESSION = 2;

    public static final int PERL_STATUS_ERROR_SCALAR_FOUND = 255;

    public static final int PERL_STATUS_ERROR_FILE = 9;

    // private PerlValidatorErrors errors;

    /**
     * DOC amaumont PerlExpressionSyntaxChecker constructor comment.
     */
    public PerlCodeProblemsChecker() {
        super();
    }

    /**
     * DOC amaumont Comment method "getTempFile".
     */
    private File getTempFile() {
        if (tempFile == null || !tempFile.exists()) {
            try {
                tempFile = File.createTempFile("perlSyntaxChecker", ".tmp"); //$NON-NLS-1$ //$NON-NLS-2$
                tempFile.deleteOnExit();
                return tempFile;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return tempFile;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.language.perl.CodeSyntaxChecker#checkSyntax(java.lang.String)
     */
    public List<Problem> checkProblemsForExpression(String expression) {

        if (expression == null) {
            return null;
        }

        StringBuffer out = new StringBuffer();
        StringBuffer err = new StringBuffer();

        int status = -1;
        // // if expression contains a backslash, we must parse a File because parsing expression is not reliable
        // if (false && expression.indexOf('\\') == -1) {
        // TimeMeasure.start("expression");
        // status = callPerlCommandWithExpression(expression, out, err);
        // TimeMeasure.end("expression");
        // } else {
        // TimeMeasure.start("file");
        status = callPerlCommandWithFile(expression, out, err);
        // TimeMeasure.end("file");
        // }

        String stdErr = null;

        if (status == PERL_STATUS_OK) {

        } else if (status == PERL_STATUS_ERROR_EXPRESSION || status == PERL_STATUS_ERROR_SCALAR_FOUND
                || status == PERL_STATUS_ERROR_FILE) {
            stdErr = err.toString();
            if (status == PERL_STATUS_ERROR_EXPRESSION) {
                stdErr = replaceStdErrStringForExpression(stdErr);

            } else if (status == PERL_STATUS_ERROR_FILE || status == PERL_STATUS_ERROR_SCALAR_FOUND) {
                stdErr = replaceStdErrStringForFile(stdErr);
            }

        } else {
            stdErr = Messages.getString("PerlCodeProblemsChecker.unknowPerlStatus") + err.toString(); //$NON-NLS-1$
        }

        if (stdErr != null) {
            ArrayList<Problem> problems = new ArrayList<Problem>();
            problems.add(new Problem(null, stdErr, ProblemStatus.ERROR));
            return problems;
        }

        // System.out.println("\nout= " + out.toString());
        // System.out.println("err= " + err.toString());

        return null;
    }

    /**
     * DOC amaumont Comment method "replaceStdErrStringForFile".
     * 
     * @param stdErr
     * @return
     */
    private String replaceStdErrStringForFile(String stdErr) {
        String absolutePath = tempFile.getAbsolutePath();
        stdErr = StringUtils.replace(stdErr, STRING1_PREFIX + absolutePath + STRING1_SUFFIX, STRING1_SUFFIX_PREFIX);
        stdErr = StringUtils.replace(stdErr, absolutePath + STRING2_SUFFIX, STRING2_REPLACED);
        return stdErr;
    }

    /**
     * DOC amaumont Comment method "replaceStdErrStringForExpression".
     * 
     * @param stdErr
     * @return
     */
    private String replaceStdErrStringForExpression(String stdErr) {
        stdErr = StringUtils.replace(stdErr, STRING1_PREFIX + STRING1_EXPRESSION + STRING1_SUFFIX, STRING1_SUFFIX_PREFIX);
        stdErr = StringUtils.replace(stdErr, STRING1_EXPRESSION + STRING2_SUFFIX, STRING2_REPLACED);
        return stdErr;
    }

    /**
     * DOC amaumont Comment method "callPerlCommandWithExpression".
     * 
     * @param err
     * @param out
     * @param expression
     * @return status
     */
    private int callPerlCommandWithExpression(String expression, StringBuffer out, StringBuffer err) {
        int status = -1;

        // System.out.println("expression='"+ expression + "'");
        String expressionEscaped = StringUtils.replace(expression, "\"", "\\\""); //$NON-NLS-1$ //$NON-NLS-2$
        for (int i = expression.length(); i > 0; i--) {
            String chara = expression.substring(i - 1, i);
            if (!chara.equals("\\")) { //$NON-NLS-1$
                if (i % 2 == 0 && i < expression.length()) {
                    expressionEscaped = expressionEscaped + "\\"; //$NON-NLS-1$
                    break;
                }
                break;
            }
        }

        String expressionEscapedQuoted = "\"" + expressionEscaped + "\""; //$NON-NLS-1$ //$NON-NLS-2$
        // System.out.println(expressionEscapedQuoted);
        try {
            status = PerlProcessor.exec(out, err, null, null, Level.TRACE, "-ce", expressionEscapedQuoted, -1, -1, //$NON-NLS-1$ //$NON-NLS-2$
                    new String[0]);
        } catch (ProcessorException e) {
            ExceptionHandler.process(e);
        }

        return status;
    }

    /**
     * DOC amaumont Comment method "callPerlCommandWithExpression".
     * 
     * @param err
     * @param out
     * @param expression
     * @return status
     */
    private int callPerlCommandWithFile(String expression, StringBuffer out, StringBuffer err) {
        int status = -1;

        File file = getTempFile();

        if (file.canWrite()) {
            try {
                FileWriter fileWriter = new FileWriter(file);
                fileWriter.write(expression);
                fileWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            throw new RuntimeException("File is not writable"); //$NON-NLS-1$
        }

        // System.out.println(expression);
        try {
            status = PerlProcessor.exec(out, err, null, null, Level.TRACE, "-c", file.getAbsolutePath(), -1, -1, //$NON-NLS-1$ //$NON-NLS-2$
                    new String[0]);
        } catch (ProcessorException e) {
            ExceptionHandler.process(e);
        }

        return status;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.language.ICodeProblemsChecker#checkProblems()
     */
    public List<Problem> checkProblems(IAloneProcessNodeConfigurer nodeConfigurer) {
        throw new UnsupportedOperationException("use checkProblemsForExpression()"); //$NON-NLS-1$
    }

}
