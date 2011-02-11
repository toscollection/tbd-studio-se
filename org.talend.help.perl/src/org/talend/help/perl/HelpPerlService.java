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
package org.talend.help.perl;

import org.talend.core.service.IHelpPerlService;

/**
 * DOC guanglong.du class global comment. Detailled comment
 */
public class HelpPerlService implements IHelpPerlService {

    /*
     * (non-Jsdoc)
     * 
     * @see org.talend.core.service.IHelpPerlService#helpPress()
     */
    public void helpPress() {
        // TODO Auto-generated method stub
        OpenPerlHelpAction perlHelpAction = new OpenPerlHelpAction();
        perlHelpAction.run();
    }

}
