// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend �?www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.repository.repositoryitem.toolBarAction;

import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.IWorkbenchWindowPulldownDelegate2;
import org.talend.designer.business.diagram.custom.actions.CreateDiagramAction;
import org.talend.designer.core.ui.action.CreateProcess;
import org.talend.repository.ui.actions.metadata.CreateConnectionAction;
import org.talend.repository.ui.actions.metadata.CreateFileDelimitedAction;
import org.talend.repository.ui.actions.metadata.CreateFileLdifAction;
import org.talend.repository.ui.actions.metadata.CreateFilePositionalAction;
import org.talend.repository.ui.actions.metadata.CreateFileRegexpAction;
import org.talend.repository.ui.actions.metadata.CreateFileXmlAction;
import org.talend.repository.ui.actions.metadata.CreateGenericSchemaAction;
import org.talend.repository.ui.actions.metadata.CreateLDAPSchemaAction;

/**
 * DOC qwei class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ææäº, 29 ä¹æ 2006) nrousseau $
 * 
 */
public abstract class AbstractCreatToolbarAction implements IWorkbenchWindowPulldownDelegate2 {

    /**
     * The menu created by this action
     */
    private Menu fMenu;

    protected boolean fRecreateMenu = false;

    /**
     * The action used to render this delegate.
     */
    private IAction fAction;

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowPulldownDelegate2#getMenu(org.eclipse.swt.widgets.Menu)
     */
    public Menu getMenu(Menu parent) {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#dispose()
     */
    public void dispose() {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchWindowActionDelegate#init(org.eclipse.ui.IWorkbenchWindow)
     */
    public void init(IWorkbenchWindow window) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    public void run(IAction action) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IActionDelegate#selectionChanged(org.eclipse.jface.action.IAction,
     * org.eclipse.jface.viewers.ISelection)
     */
    public void selectionChanged(IAction action, ISelection selection) {

    }

    protected void fillMenu(Menu menu) {
        addToMenu(menu, new CreateProcess(true), -1);
        addSeparator(menu);
        addToMenu(menu, new CreateDiagramAction(true), -1);
        addSeparator(menu);
        addToMenu(menu, new CreateConnectionAction(true), -1);
        addToMenu(menu, new CreateFileDelimitedAction(true), -1);
        addToMenu(menu, new CreateFilePositionalAction(true), -1);
        addToMenu(menu, new CreateFileRegexpAction(true), -1);
        addToMenu(menu, new CreateFileXmlAction(true), -1);
        addToMenu(menu, new CreateFileLdifAction(true), -1);
        addToMenu(menu, new CreateLDAPSchemaAction(true), -1);
        addToMenu(menu, new CreateGenericSchemaAction(true), -1);
    }

    /**
     * Adds a separator to the given menu
     * 
     * @param menu
     */
    protected void addSeparator(Menu menu) {
        new MenuItem(menu, SWT.SEPARATOR);
    }

    protected void addToMenu(Menu menu, IAction action, int accelerator) {
        StringBuffer label = new StringBuffer();
        if (accelerator >= 0 && accelerator < 10) {
            // add the numerical accelerator
            label.append('&');
            label.append(accelerator);
            label.append(' ');
        }
        label.append(action.getText());
        action.setText(label.toString());
        ActionContributionItem item = new ActionContributionItem(action);
        item.fill(menu, -1);
    }

    public Menu getMenu(Control parent) {
        setMenu(new Menu(parent));
        fillMenu(fMenu);
        initMenu();
        return fMenu;
    }

    private void setMenu(Menu menu) {
        if (fMenu != null) {
            fMenu.dispose();
        }
        fMenu = menu;
    }

    /**
     * Creates the menu for the action
     */
    private void initMenu() {
        // Add listener to re-populate the menu each time
        // it is shown because of dynamic history list
        fMenu.addMenuListener(new MenuAdapter() {

            public void menuShown(MenuEvent e) {
                if (fRecreateMenu) {
                    Menu m = (Menu) e.widget;
                    MenuItem[] items = m.getItems();
                    for (int i = 0; i < items.length; i++) {
                        items[i].dispose();
                    }
                    fillMenu(m);
                    fRecreateMenu = false;
                }
            }
        });
    }

}
