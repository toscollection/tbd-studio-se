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
package org.talend.repository.hbaseprovider.util;

import org.apache.commons.lang.StringUtils;
import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.core.database.conn.ConnParameterKeys;
import org.talend.core.database.hbase.conn.version.EHBaseDistributions;
import org.talend.core.model.metadata.IMetadataConnection;

/**
 * created by ycbai on 2013-3-26 Detailled comment
 * 
 */
public class HBaseClassLoaderFactory {

    public static ClassLoader getClassLoader(IMetadataConnection metadataConnection) {
        ClassLoader loader = null;
        if (metadataConnection != null) {
            String distribution = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_DISTRIBUTION);
            if (EHBaseDistributions.CUSTOM.getName().equals(distribution)) {
                return getCustomClassLoader(metadataConnection);
            }
            String version = (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HBASE_VERSION);
            if (StringUtils.isNotEmpty(distribution) && StringUtils.isNotEmpty(version)) {
                String index = "HBASE" + ":" + distribution + ":" + version; //$NON-NLS-1$  //$NON-NLS-2$  //$NON-NLS-3$
                loader = ClassLoaderFactory.getClassLoader(index);
            }
        }
        if (loader == null) {
            loader = HBaseClassLoaderFactory.class.getClassLoader();
        }

        return loader;
    }

    public static ClassLoader getCustomClassLoader(IMetadataConnection metadataConnection) {
        String hcId = metadataConnection.getId();
        String index = "HadoopCustomVersion:HBase:" + hcId; //$NON-NLS-1$ 
        return ClassLoaderFactory.getCustomClassLoader(index,
                (String) metadataConnection.getParameter(ConnParameterKeys.CONN_PARA_KEY_HADOOP_CUSTOM_JARS));
    }
}
