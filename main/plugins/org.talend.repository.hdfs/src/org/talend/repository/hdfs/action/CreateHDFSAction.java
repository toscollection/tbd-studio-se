// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.action;

import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.action.common.CreateHadoopNodeAction;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hdfs.node.model.HDFSRepositoryNodeType;
import org.talend.repository.hdfs.ui.HDFSWizard;
import org.talend.repository.hdfs.util.EHDFSImage;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hdfs.HDFSConnectionItem;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class CreateHDFSAction extends CreateHadoopNodeAction {

    @Override
    protected ERepositoryObjectType getNodeType() {
        return HDFSRepositoryNodeType.HDFS;
    }

    @Override
    protected IImage getNodeImage() {
        return EHDFSImage.HDFS_RESOURCE_ICON;
    }

    @Override
    protected IWizard getWizard(IWorkbench workbench, boolean isCreate, RepositoryNode node, String[] existingNames) {
        return new HDFSWizard(workbench, isCreate, node, existingNames);
    }

    @Override
    public Class getClassForDoubleClick() {
        return HDFSConnectionItem.class;
    }

    @Override
    protected boolean hideAction(RepositoryNode node) {
        HadoopClusterConnectionItem hcConnectionItem = HCRepositoryUtil.getHCConnectionItemFromRepositoryNode(node);
        if (hcConnectionItem != null) {
            HadoopClusterConnection hcConnection = (HadoopClusterConnection) hcConnectionItem.getConnection();
            EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(hcConnection.getDistribution(), false);
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(hcConnection.getDfVersion());
            if (distribution == EHadoopDistributions.MICROSOFT_HD_INSIGHT) {
                return true;
            }
        }

        return false;
    }

}
