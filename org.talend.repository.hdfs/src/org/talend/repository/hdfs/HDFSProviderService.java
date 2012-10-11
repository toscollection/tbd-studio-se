// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.talend.core.ui.IHDFSProviderService;
import org.talend.repository.hdfs.ui.HDFSWizard;
import org.talend.repository.model.RepositoryNode;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class HDFSProviderService implements IHDFSProviderService {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.IProviderService#newWizard(org.eclipse.ui.IWorkbench, boolean,
     * org.talend.repository.model.RepositoryNode, java.lang.String[])
     */
    @Override
    public IWizard newWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        if (node == null) {
            return null;
        }
        if (workbench == null) {
            workbench = PlatformUI.getWorkbench();
        }
        return new HDFSWizard(workbench, creation, node, existingNames);
    }

}
