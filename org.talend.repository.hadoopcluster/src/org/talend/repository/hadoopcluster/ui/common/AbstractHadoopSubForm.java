// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.ui.common;

import org.eclipse.swt.widgets.Composite;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * created by ycbai on 2013-4-2 Detailled comment
 * 
 */
public abstract class AbstractHadoopSubForm<T extends HadoopSubConnection> extends AbstractHadoopForm<T> {

    protected HadoopClusterConnection clusterConnection;

    protected String distribution;

    protected String version;

    protected boolean enableKerberos;

    protected boolean enableGroup;

    /**
     * DOC ycbai AbstractHadoopSubForm constructor comment.
     * 
     * @param parent
     * @param style
     */
    protected AbstractHadoopSubForm(Composite parent, int style, ConnectionItem connectionItem) {
        super(parent, style);
        init(connectionItem);
    }

    protected AbstractHadoopSubForm(Composite parent, int style, String[] existingNames, ConnectionItem connectionItem) {
        super(parent, style, existingNames);
        init(connectionItem);
    }

    private void init(ConnectionItem item) {
        setConnectionItem(item);
        clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
        distribution = clusterConnection.getDistribution();
        version = clusterConnection.getDfVersion();
        enableKerberos = clusterConnection.isEnableKerberos();
        enableGroup = HCVersionUtil.isSupportGroup(EHadoopVersion4Drivers.indexOfByVersion(version));
        setupForm();
    }

}
