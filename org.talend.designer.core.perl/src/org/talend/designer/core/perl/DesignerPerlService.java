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
package org.talend.designer.core.perl;

import org.apache.log4j.Level;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditor;
import org.epic.perleditor.PerlEditorPlugin;
import org.epic.perleditor.editors.PerlEditor;
import org.epic.perleditor.editors.util.PerlValidator;
import org.talend.core.language.ICodeProblemsChecker;
import org.talend.core.model.process.IProcess;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.Property;
import org.talend.core.service.IDesignerPerlService;
import org.talend.designer.core.perl.language.PerlCodeProblemsChecker;
import org.talend.designer.core.perl.runprocess.PerlProcessor;
import org.talend.designer.core.perl.runprocess.PerlUtils;
import org.talend.designer.core.perl.ui.editor.StandAloneTalendPerlEditor;
import org.talend.designer.core.perl.ui.editor.TalendPerlEditor;
import org.talend.designer.runprocess.IProcessor;
import org.talend.designer.runprocess.ProcessorException;

/**
 * DOC guanglong.du class global comment. Detailled comment
 */
public class DesignerPerlService implements IDesignerPerlService {

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IPerlService#getStandAloneTalendPerlEditorID()
     */
    public String getStandAloneTalendPerlEditorID() {
        // TODO Auto-generated method stub
        return StandAloneTalendPerlEditor.ID;
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IPerlService#createNewPerlEditor(org.talend.core.model.process.IProcess2)
     */
    public AbstractDecoratedTextEditor createNewPerlEditor(IProcess2 process) {
        // TODO Auto-generated method stub
        return new TalendPerlEditor(process);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IPerlService#getViewer()
     */
    public ISourceViewer getViewer(IEditorPart editor) {
        // TODO Auto-generated method stub
        return ((PerlEditor) editor).getViewer();

    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IPerlService#perlValidator(org.eclipse.core.resources.IFile, java.lang.String)
     */
    public void perlValidator(IFile file, String sourceCode) throws CoreException {
        // TODO Auto-generated method stub
        PerlValidator.instance().validate(file, sourceCode);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IPerlService#setSyntaxValidationPreference(boolean)
     */
    public void setSyntaxValidationPreference(boolean isvailda) {
        // TODO Auto-generated method stub
        PerlEditorPlugin.getDefault().setSyntaxValidationPreference(true);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IPerlService#placeCursorTo(org.eclipse.ui.texteditor.AbstractDecoratedTextEditor)
     */
    public void placeCursorTo(AbstractDecoratedTextEditor codeEditor, String nodeName) {
        // TODO Auto-generated method stub
        ((TalendPerlEditor) codeEditor).placeCursorTo(nodeName);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IDesignerPerlService#createPerlProcessor(org.talend.core.model.process.IProcess,
     * org.talend.core.model.properties.Property, boolean)
     */
    public IProcessor createPerlProcessor(IProcess process, Property property, boolean filenameFromLabel) {
        // TODO Auto-generated method stub
        return new PerlProcessor(process, property, filenameFromLabel);
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IDesignerPerlService#createPerlCodeProblemsChecker()
     */
    public ICodeProblemsChecker createPerlCodeProblemsChecker() {
        // TODO Auto-generated method stub
        return new PerlCodeProblemsChecker();
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IDesignerPerlService#getProject()
     */
    public IProject getProject() throws CoreException {
        // TODO Auto-generated method stub
        return PerlUtils.getProject();
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IDesignerPerlService#perlExec(java.lang.StringBuffer, java.lang.StringBuffer,
     * org.eclipse.core.runtime.IPath, java.lang.String, org.apache.log4j.Level, java.lang.String, java.lang.String,
     * int, int, java.lang.String[])
     */
    public int perlExec(StringBuffer out, StringBuffer err, IPath absCodePath, String contextName, Level level,
            String perlInterpreterLibOption, String perlModuleDirectoryOption, int statOption, int traceOption,
            String... codeOptions) throws ProcessorException {
        // TODO Auto-generated method stub
        return PerlProcessor.exec(out, err, absCodePath, contextName, level, perlInterpreterLibOption, perlModuleDirectoryOption,
                statOption, traceOption, codeOptions);
    }

}
