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
package org.talend.designer.codegen.javamodule.ui.actions;

import org.eclipse.jface.action.Action;
import org.talend.commons.ui.image.EImage;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.designer.codegen.javamodule.i18n.Messages;
import org.talend.designer.codegen.javamodule.ui.views.ModulesView;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: CheckModulesAction.java 1754 2007-02-01 09:46:26Z plegall $
 * 
 */
public class CheckModulesAction extends Action {

    private ModulesView view;

    public CheckModulesAction(ModulesView view) {
        super();
        setText(Messages.getString("CheckModulesAction.Refresh")); //$NON-NLS-1$
        setToolTipText(Messages.getString("CheckModulesAction.Refresh")); //$NON-NLS-1$
        setImageDescriptor(ImageProvider.getImageDesc(EImage.REFRESH_ICON));
        this.view = view;
        this.setActionDefinitionId("refreshModules"); //$NON-NLS-1$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    public void run() {
        view.refresh();
    }

}
