// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package com.talend.perl.editorwrapper.views;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IMemento;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPartSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;
import org.epic.perleditor.editors.PerlEditor;

/**
 * DOC ccarbone class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class PerlEditorViewWrapper extends PerlEditor implements IViewPart {

    private IViewSite viewSite;

    private IEditorSite editorSite;

    /**
     * Creates a new view.
     */
    public PerlEditorViewWrapper() {
        super();
    }

    /*
     * (non-Javadoc) Method declared on IViewPart.
     */
    public IViewSite getViewSite() {
        return viewSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite)
     */
    public void init(IViewSite site) throws PartInitException {
        viewSite = site;
        editorSite = new PerlEditorSite(site);
        setSite(editorSite);

        init(editorSite, new FileEditorInput(getSamplePerlFile()));
    }

    private IFile getSamplePerlFile() {
        IWorkspace workspace = ResourcesPlugin.getWorkspace();
        IFile perlFile = workspace.getRoot().getProject("TestPerl").getFile("test.pl"); //$NON-NLS-1$ //$NON-NLS-2$
        return perlFile;
    }

    @Override
    public IWorkbenchPartSite getSite() {
        // TODO Auto-generated method stub
        return viewSite;
    }

    @Override
    public IEditorSite getEditorSite() {
        // TODO Auto-generated method stub
        return editorSite;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IViewPart#init(org.eclipse.ui.IViewSite, org.eclipse.ui.IMemento)
     */
    public void init(IViewSite site, IMemento memento) throws PartInitException {
        /*
         * Initializes this view with the given view site. A memento is passed to the view which contains a snapshot of
         * the views state from a previous session. Where possible, the view should try to recreate that state within
         * the part controls. <p> This implementation will ignore the memento and initialize the view in a fresh state.
         * Subclasses may override the implementation to perform any state restoration as needed.
         */
        init(site);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IViewPart#saveState(org.eclipse.ui.IMemento)
     */
    public void saveState(IMemento memento) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setPartName(java.lang.String)
     */
    protected void setPartName(String partName) {
        super.setPartName(partName);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setContentDescription(java.lang.String)
     */
    protected void setContentDescription(String description) {
        super.setContentDescription(description);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.IExecutableExtension#setInitializationData(org.eclipse.core.runtime.IConfigurationElement,
     * java.lang.String, java.lang.Object)
     */
    public void setInitializationData(IConfigurationElement cfig, String propertyName, Object data) {
        super.setInitializationData(cfig, propertyName, data);

    }

    @Override
    protected void createActions() {
        // TODO Auto-generated method stub
        super.createActions();
    }

}
