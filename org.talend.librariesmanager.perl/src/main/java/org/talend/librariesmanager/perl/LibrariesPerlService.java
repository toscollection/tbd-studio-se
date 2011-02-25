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
package org.talend.librariesmanager.perl;

import org.eclipse.jface.action.Action;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.routines.IRoutinesProvider;
import org.talend.core.service.ILibrariesPerlService;
import org.talend.librariesmanager.perl.model.service.PerlLibrariesService;
import org.talend.librariesmanager.perl.model.service.TosPerlRoutinesProvider;
import org.talend.librariesmanager.perl.ui.actions.InstallPerlModulesAction;

/**
 * DOC guanglong.du class global comment. Detailled comment
 */
public class LibrariesPerlService implements ILibrariesPerlService {

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.ILibrariesPerlService#getInstallPerlModulesAction()
     */

    public Action getInstallPerlModulesAction() {
        return new InstallPerlModulesAction();
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.ILibrariesPerlService#getTosPerlRoutinesProvider()
     */

    public IRoutinesProvider getTosPerlRoutinesProvider() {
        return new TosPerlRoutinesProvider();
    }

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.ILibrariesPerlService#getPerlLibrariesService()
     */

    public ILibrariesService getPerlLibrariesService() {
        return new PerlLibrariesService();
    }

}
