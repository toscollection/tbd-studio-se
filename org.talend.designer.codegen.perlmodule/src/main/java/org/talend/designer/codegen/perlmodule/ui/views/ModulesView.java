// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
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
package org.talend.designer.codegen.javamodule.ui.views;

import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.commands.ActionHandler;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.talend.commons.exception.RuntimeExceptionHandler;
import org.talend.designer.codegen.javamodule.ui.actions.CheckModulesAction;

/**
 * DOC nrousseau class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ModulesView extends ViewPart {

    private CheckModulesAction checkAction;

    private Composite modulesViewComposite;

    /**
     * Ask the modules view composite to refresh its content.
     * 
     * yzhang Comment method "refresh".
     */
    public void refresh() {
        if (this.modulesViewComposite instanceof IModulesViewComposite) {
            IModulesViewComposite javaModulesViewComposite = (IModulesViewComposite) this.modulesViewComposite;
            javaModulesViewComposite.refresh();
        } else {
            RuntimeExceptionHandler.process(new RuntimeException(
                    "Compoiste in moudles view should be an instance of IModulesViewComposite"));
        }

    }

    /*
     * Creat the content of this view.
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#createPartControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createPartControl(Composite parent) {
        // If the content of this view need to display libries of Java, a new composite for it should be developed. And
        // replace the JavaModulesViewComposite here.

        modulesViewComposite = new JavaModulesViewComposite(parent);
        makeActions();
        contributeToActionBars();
    }

    /*
     * Set focus on the composite within this view.
     * 
     * @see org.eclipse.ui.part.WorkbenchPart#setFocus()
     */
    @Override
    public void setFocus() {
        if (this.modulesViewComposite == null) {
            return;
        }
        this.modulesViewComposite.setFocus();
    }

    private void makeActions() {
        checkAction = new CheckModulesAction(this);

        IHandlerService handlerService = (IHandlerService) getSite().getService(IHandlerService.class);

        IHandler handler1 = new ActionHandler(checkAction);
        handlerService.activateHandler(checkAction.getActionDefinitionId(), handler1);
    }

    private void contributeToActionBars() {
        IActionBars bars = getViewSite().getActionBars();
        fillLocalToolBar(bars.getToolBarManager());
    }

    private void fillLocalToolBar(IToolBarManager manager) {
        manager.add(checkAction);
    }

}
