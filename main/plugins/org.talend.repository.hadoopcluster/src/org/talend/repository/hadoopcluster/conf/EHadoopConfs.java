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
package org.talend.repository.hadoopcluster.conf;

/**
 * created by ycbai on 2015年6月1日 Detailled comment
 *
 */
public enum EHadoopConfs {

    HDFS("HDFS", new String[] { "core-site.xml", "hdfs-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    MAP_REDUCE("MapReduce", new String[] { "core-site.xml", "mapred-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    YARN("Yarn", new String[] { "core-site.xml", "yarn-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    HCATALOG("HCatalog", new String[] { "webhcat-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ 

    HIVE("Hive", new String[] { "mapred-site.xml", "hive-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    HBASE("HBase", new String[] { "hdfs-site.xml", "hbase-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

    OOZIE("Oozie", new String[] { "oozie-site.xml" }), //$NON-NLS-1$ //$NON-NLS-2$ 

    ;

    private String name;

    private String[] confFiles;

    EHadoopConfs(String name, String[] confFiles) {
        this.name = name;
        this.confFiles = confFiles;
    }

    public String getName() {
        return this.name;
    }

    public String[] getConfFiles() {
        return this.confFiles;
    }

    public static EHadoopConfs getConfByName(String name) {
        EHadoopConfs[] confs = values();
        for (EHadoopConfs conf : confs) {
            if (conf.getName().equals(name)) {
                return conf;
            }
        }
        return null;
    }

}
