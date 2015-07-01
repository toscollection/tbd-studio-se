// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.creator;

import java.util.Map;

import org.eclipse.core.runtime.CoreException;
import org.talend.core.hadoop.conf.EHadoopConfs;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.repository.hadoopcluster.creator.AbstractHadoopSubConnectionCreator;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;
import org.talend.repository.model.hdfs.HDFSConnection;
import org.talend.repository.model.hdfs.HDFSFactory;

/**
 * created by ycbai on 2015年6月29日 Detailled comment
 *
 */
public class HDFSConnectionCreator extends AbstractHadoopSubConnectionCreator {

    @Override
    public ConnectionItem create(String relativeHadoopClusterId, Map<String, Map<String, String>> initParams)
            throws CoreException {
        HDFSConnection connection = HDFSFactory.eINSTANCE.createHDFSConnection();
        Property connectionProperty = PropertiesFactory.eINSTANCE.createProperty();
        setPropertyParameters(relativeHadoopClusterId, connectionProperty);

        HadoopSubConnectionItem connectionItem = HDFSFactory.eINSTANCE.createHDFSConnectionItem();
        connectionItem.setProperty(connectionProperty);
        connectionItem.setConnection(connection);

        appendToHadoopCluster(relativeHadoopClusterId, connectionItem);

        return connectionItem;
    }

    @Override
    public String getTypeName() {
        return EHadoopConfs.HDFS.getName();
    }

}
