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
package org.talend.designer.core.perl.ui.editor;

import java.util.List;

import org.eclipse.core.resources.IncrementalProjectBuilder;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.FindReplaceDocumentAdapter;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Region;
import org.eclipse.ui.progress.WorkbenchJob;
import org.epic.perleditor.editors.PerlEditor;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.Information;
import org.talend.core.model.properties.InformationLevel;
import org.talend.core.model.properties.Property;
import org.talend.designer.codegen.ITalendSynchronizer;
import org.talend.designer.core.ISyntaxCheckableEditor;
import org.talend.designer.core.ui.AbstractMultiPageTalendEditor;
import org.talend.designer.core.ui.views.problems.Problems;

/**
 * Perl editor with read only content. <br/>
 * 
 * $Id: TalendPerlEditor.java 52559 2010-12-13 04:14:06Z nrousseau $
 * 
 */
public class TalendPerlEditor extends PerlEditor implements ISyntaxCheckableEditor {

    private boolean disposed = false;

    private String currentSelection;

    private IProcess2 process;

    /**
     * Constructs a new TalendPerlEditor.
     */
    public TalendPerlEditor(IProcess2 process) {
        super();
        this.process = process;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.editors.text.TextEditor#isSaveAsAllowed()
     */
    @Override
    public boolean isSaveAsAllowed() {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.texteditor.AbstractTextEditor#isEditable()
     */
    @Override
    public boolean isEditable() {
        return false;
    }

    private static boolean codeSynchronized;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.core.ui.editor.ICheckable#checkCode()
     */
    public void validateSyntax() {
        revalidateSyntax();
        Job refreshJob = new WorkbenchJob("") {//$NON-NLS-1$

            /*
             * (non-Javadoc)
             * 
             * @see org.eclipse.ui.progress.UIJob#runInUIThread(org.eclipse.core.runtime.IProgressMonitor)
             */
            @Override
            public IStatus runInUIThread(IProgressMonitor monitor) {

                placeCursorToSelection();

                Property property = process.getProperty();
                ITalendSynchronizer synchronizer = CorePlugin.getDefault().getCodeGeneratorService().createRoutineSynchronizer();

                try {

                    if (LanguageManager.getCurrentLanguage() == ECodeLanguage.PERL) {
                        try {
                            CorePlugin.getDefault().getRunProcessService().getProject(ECodeLanguage.PERL).getProject().build(
                                    IncrementalProjectBuilder.AUTO_BUILD, null);

                        } catch (CoreException e) {
                            ExceptionHandler.process(e);
                        }
                    }

                    List<Information> informations = Problems.addRoutineFile(synchronizer.getFile(property.getItem()), property,
                            true);

                    // save error status
                    property.getInformations().clear();
                    // add only the errors in the property, not the warnings
                    for (Information info : informations) {
                        if (info.getLevel().equals(InformationLevel.ERROR_LITERAL)) {
                            property.getInformations().add(info);
                        }
                    }
                    Problems.computePropertyMaxInformationLevel(property);

                } catch (SystemException e) {
                    ExceptionHandler.process(e);
                }

                Problems.refreshRepositoryView();
                Problems.refreshProblemTreeView();

                codeSynchronized = false;
                return Status.OK_STATUS;
            }
        };
        refreshJob.setSystem(true);

        // need some time to wait for PerlSyntaxValidationThread, as revalidateSyntax will activate it
        refreshJob.schedule(300);
    }

    /*
     * Return if this perl editor had been disposed.
     * 
     * @see org.talend.designer.core.ISyntaxCheckableEditor#isDisposed()
     */
    public boolean isDisposed() {
        return this.disposed;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.epic.perleditor.editors.PerlEditor#dispose()
     */
    @Override
    public void dispose() {
        this.disposed = true;
        super.dispose();
    }

    private void placeCursorToSelection() {
        String mainPart = "[" + currentSelection + " main ] start"; //$NON-NLS-1$ //$NON-NLS-2$
        String assignmentPart = "$current_component=\"" + currentSelection + "\";"; //$NON-NLS-1$ //$NON-NLS-2$
        IDocument doc = getDocumentProvider().getDocument(getEditorInput());
        FindReplaceDocumentAdapter frda = new FindReplaceDocumentAdapter(doc);
        try {
            Region region = (Region) frda.find(0, mainPart, true, false, false, false);
            if (region != null) {
                Region region2 = (Region) frda.find(region.getOffset(), assignmentPart, true, false, false, false);
                if (region2 != null) {
                    selectAndReveal(region2.getOffset(), assignmentPart.length());
                } else {
                    selectAndReveal(region.getOffset(), mainPart.length());
                }
            } else {
                selectAndReveal(0, 0);
            }
        } catch (BadLocationException e) {
            selectAndReveal(0, 0);
        }
    }

    /**
     * DOC nrousseau Comment method "placeCursorTo".
     * 
     * @param string
     */
    public void placeCursorTo(String currentSelection) {
        this.currentSelection = currentSelection;
        placeCursorToSelection();
    }

    /*
     * (non-Javadoc)
     * 
     * @seeorg.talend.designer.core.ui.editor.ITalendCodeEditor#removeEditorPart(org.talend.designer.core.ui.
     * AbstractMultiPageTalendEditor)
     */
    public void removeEditorPart(AbstractMultiPageTalendEditor abstractMultiPageTalendEditor) {

    }
}
