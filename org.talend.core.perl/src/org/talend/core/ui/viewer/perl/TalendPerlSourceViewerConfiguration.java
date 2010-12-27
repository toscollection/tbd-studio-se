// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.ui.viewer.perl;

import org.eclipse.jface.internal.text.html.HTMLTextPresenter;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.DefaultAutoIndentStrategy;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IAutoIndentStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Shell;
import org.epic.perleditor.PerlEditorPlugin;
import org.epic.perleditor.editors.PartitionTypes;
import org.epic.perleditor.editors.PerlPresentationReconciler;
import org.epic.perleditor.editors.perl.PerlAutoIndentStrategy;
import org.epic.perleditor.editors.perl.PerlDoubleClickSelector;
import org.epic.perleditor.editors.util.PreferenceUtil;
import org.epic.perleditor.preferences.PreferenceConstants;

/**
 * @author luelljoc
 * 
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and
 * Comments
 */
public class TalendPerlSourceViewerConfiguration extends SourceViewerConfiguration {

    private final IPreferenceStore prefs;

    private final TalendPerlSourceViewer viewer;

    public TalendPerlSourceViewerConfiguration(IPreferenceStore store, TalendPerlSourceViewer viewer) {
        assert store != null;

        prefs = store;
        this.viewer = viewer;
    }

    public IAnnotationHover getAnnotationHover(ISourceViewer sourceViewer) {
        return new TalendPerlAnnotationHover(viewer);
    }

    public IAutoIndentStrategy getAutoIndentStrategy(ISourceViewer sourceViewer, String contentType) {
        return (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType) ? new PerlAutoIndentStrategy()
                : new DefaultAutoIndentStrategy());
    }

    public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
        return PartitionTypes.getTypes();
    }

    public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {
        ContentAssistant assistant = new ContentAssistant();

        // Enable content assist for all content types
        String[] contentTypes = this.getConfiguredContentTypes(sourceViewer);
        for (int i = 0; i < contentTypes.length; i++) {
            assistant.setContentAssistProcessor(new TalendPerlCompletionProcessor(), contentTypes[i]);
        }

        assistant.enableAutoActivation(true);
        assistant.enableAutoInsert(true);
        assistant.setAutoActivationDelay(500);
        assistant.setProposalPopupOrientation(ContentAssistant.PROPOSAL_OVERLAY);
        assistant.setContextInformationPopupOrientation(ContentAssistant.CONTEXT_INFO_ABOVE);
        assistant.setContextInformationPopupBackground(PerlEditorPlugin.getDefault().getColor(new RGB(0, 0, 0)));
        assistant.setProposalSelectorBackground(PerlEditorPlugin.getDefault().getColor(new RGB(255, 255, 255)));
        assistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));

        return assistant;
    }

    public String[] getDefaultPrefixes(ISourceViewer sourceViewer, String contentType) {
        return new String[] { "#", "" }; //$NON-NLS-1$ //$NON-NLS-2$

    }

    public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
        return new PerlDoubleClickSelector();
    }

    public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
        return new String[] { PreferenceUtil.getTab(0), "\t" }; //$NON-NLS-1$
    }

    public int getTabWidth(ISourceViewer sourceViewer) {
        return prefs.getInt(PreferenceConstants.EDITOR_TAB_WIDTH);
    }

    public ITextHover getTextHover(ISourceViewer sourceViewer, String contentType) {
        return new TalendPerlTextHover(viewer);
    }

    public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
        return new PerlPresentationReconciler(prefs);
    }

    public IReconciler getReconciler(ISourceViewer sourceViewer) {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.text.source.SourceViewerConfiguration#getInformationControlCreator(org.eclipse.jface.text.source.ISourceViewer)
     */
    public IInformationControlCreator getInformationControlCreator(ISourceViewer sourceViewer) {
        return new IInformationControlCreator() {

            public IInformationControl createInformationControl(Shell parent) {
                return new DefaultInformationControl(parent, SWT.NONE, new HTMLTextPresenter(true));
            }
        };
    }
}
