// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the  agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//   
// ============================================================================
package com.talend.perl.editorwrapper.views;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorActionBarContributor;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IKeyBindingService;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchWindow;

/**
 * DOC ccarbone  class global comment. Detailled comment
 * <br/>
 *
 * $Id$
 *
 */
public class PerlEditorSite implements IEditorSite, IViewSite {

	private IViewSite viewSite;

	public PerlEditorSite(IViewSite viewSite) {
		this.viewSite = viewSite;
	}

	public IEditorActionBarContributor getActionBarContributor() {
		return null;
	}

	public IActionBars getActionBars() {
		return viewSite.getActionBars();
	}

	public void registerContextMenu(MenuManager menuManager,
			ISelectionProvider selectionProvider, boolean includeEditorInput) {
	}

	public void registerContextMenu(String menuId, MenuManager menuManager,
			ISelectionProvider selectionProvider, boolean includeEditorInput) {
	}

	public String getId() {
		return viewSite.getId();
	}

	public IKeyBindingService getKeyBindingService() {
		return viewSite.getKeyBindingService();
	}

	public IWorkbenchPart getPart() {
		return viewSite.getPart();
	}

	public String getPluginId() {
		return viewSite.getPluginId();
	}

	public String getRegisteredName() {
		return viewSite.getPluginId();
	}

	public void registerContextMenu(MenuManager menuManager,
			ISelectionProvider selectionProvider) {
		viewSite.registerContextMenu(menuManager, selectionProvider);
	}

	public void registerContextMenu(String menuId, MenuManager menuManager,
			ISelectionProvider selectionProvider) {
		viewSite.registerContextMenu(menuId, menuManager, selectionProvider);
	}

	public IWorkbenchPage getPage() {
		return viewSite.getPage();
	}

	public ISelectionProvider getSelectionProvider() {
		return viewSite.getSelectionProvider();
	}

	public Shell getShell() {
		return viewSite.getShell();
	}

	public IWorkbenchWindow getWorkbenchWindow() {
		return viewSite.getWorkbenchWindow();
	}

	public void setSelectionProvider(ISelectionProvider provider) {
		viewSite.setSelectionProvider(provider);
	}

	public Object getAdapter(Class adapter) {
		return viewSite.getAdapter(adapter);
	}

	public Object getService(Class api) {
		return viewSite.getService(api);
	}

	public boolean hasService(Class api) {
		return viewSite.hasService(api);
	}

	public String getSecondaryId() {
		return viewSite.getSecondaryId();
	}

}
