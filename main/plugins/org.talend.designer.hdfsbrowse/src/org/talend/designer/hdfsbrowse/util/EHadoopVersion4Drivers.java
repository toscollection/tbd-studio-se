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
package org.talend.designer.hdfsbrowse.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public enum EHadoopVersion4Drivers {

    HDP_1_0(new HadoopVersion4Drivers(EHadoopDistributions.HORTONWORKS, "Hortonworks Data Platform V1.0.0", "HDP_1_0",
            new String[] { "hadoop-core-1.0.0.jar", "commons-logging-1.1.1.jar", "commons-configuration-1.6.jar",
                    "commons-lang-2.4.jar", "hadoop-conf-kerberos.jar", "jetty-util-6.1.26.jar", "jersey-core-1.8.jar" })),

    HDP_1_2(new HadoopVersion4Drivers(EHadoopDistributions.HORTONWORKS, "Hortonworks Data Platform V1.2.0(Bimota)", "HDP_1_2",
            new String[] { "hadoop-core-1.1.2.21.jar", "commons-logging-1.1.1.jar", "commons-configuration-1.6.jar",
                    "commons-lang-2.4.jar", "hadoop-conf-kerberos.jar", "jetty-util-6.1.26.jar", "jersey-core-1.8.jar" })),

    APACHE_1_0_0(new HadoopVersion4Drivers(EHadoopDistributions.APACHE, "Apache 1.0.0", "APACHE_1_0_0", new String[] {
            "hadoop-core-1.0.0.jar", "commons-logging-1.1.1.jar", "commons-configuration-1.6.jar", "commons-lang-2.4.jar" })),

    APACHE_0_20_204(new HadoopVersion4Drivers(EHadoopDistributions.APACHE, "Apache 0.20.204", "APACHE_0_20_204", new String[] {
            "hadoop-core-0.20.204.0.jar", "commons-logging-1.1.1.jar", "commons-configuration-1.6.jar", "commons-lang-2.4.jar" })),

    APACHE_0_20_2(new HadoopVersion4Drivers(EHadoopDistributions.APACHE, "Apache 0.20.2", "APACHE_0_20_2", new String[] {
            "hadoop-0.20.2-core.jar", "commons-logging-1.0.4.jar" })),

    CLOUDERA_0_20_CDH3U1(new HadoopVersion4Drivers(EHadoopDistributions.CLOUDERA, "Cloudera 0.20 CDH3U1", "Cloudera_0.20_CDH3U1",
            new String[] { "hadoop-0.20.2-cdh3u1-core.jar", "commons-logging-1.0.4.jar" })),

    CLOUDERA_CDH4(new HadoopVersion4Drivers(EHadoopDistributions.CLOUDERA, "Cloudera CDH4", "Cloudera_CDH4", new String[] {
            "hadoop-hdfs-2.0.0-cdh4.0.1.jar", "hadoop-common-2.0.0-cdh4.0.1.jar", "hadoop-auth-2.0.0-cdh4.0.1.jar",
            "commons-configuration-1.6.jar", "commons-lang-2.5.jar", "commons-logging-1.1.1.jar", "guava-11.0.2.jar",
            "log4j-1.2.15.jar", "protobuf-java-2.4.0a.jar", "slf4j-api-1.6.1.jar", "slf4j-log4j12-1.6.1.jar" })),

    MAPR(new HadoopVersion4Drivers(EHadoopDistributions.MAPR, "MapR 1.2.0", "MapR", new String[] { "hadoop-0.20.2-dev-core.jar",
            "commons-logging-1.0.4.jar", "maprfs-0.1.jar", "zookeeper-3.3.2.jar" }));

    private HadoopVersion4Drivers version4Drivers;

    EHadoopVersion4Drivers(HadoopVersion4Drivers version4Drivers) {
        this.version4Drivers = version4Drivers;
    }

    public String getVersionDisplay() {
        return this.version4Drivers.getVersionDisplayName();
    }

    public String getVersionValue() {
        return this.version4Drivers.getVersionValue();
    }

    public List<String> getDrivers() {
        return this.version4Drivers.getDrivers();
    }

    public String getDriverStrings() {
        StringBuffer buffer = new StringBuffer();
        List<String> drivers = getDrivers();
        for (String driver : drivers) {
            buffer.append(driver).append(";"); //$NON-NLS-1$
        }
        if (buffer.length() > 0) {
            buffer.deleteCharAt(buffer.length() - 1);
        }
        return buffer.toString();
    }

    public List<EHadoopDistributions> getSupportDistributions() {
        return this.version4Drivers.getDistributions();
    }

    public boolean supportDistribution(EHadoopDistributions distribution) {
        if (distribution != null) {
            return getSupportDistributions().contains(distribution);
        }
        return false;
    }

    public boolean supportDistribution(String distribution) {
        if (distribution != null) {
            for (EHadoopDistributions type : getSupportDistributions()) {
                if (type.getName().equalsIgnoreCase(distribution) || type.getDisplayName().equalsIgnoreCase(distribution)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean support(String distribution, String version) {
        if (distribution == null || version == null) {
            return false;
        }
        for (EHadoopVersion4Drivers v4d : EHadoopVersion4Drivers.values()) {
            if (v4d.supportDistribution(distribution)) {
                if (version.equalsIgnoreCase(v4d.getVersionValue())) {
                    return true;
                }
            }
        }
        return false;
    }

    public static EHadoopVersion4Drivers indexOfByVersionDisplay(String displayName) {
        return indexOf(displayName, true);
    }

    public static EHadoopVersion4Drivers indexOfByVersion(String value) {
        return indexOf(value, false);
    }

    private static EHadoopVersion4Drivers indexOf(String name, boolean display) {
        if (name != null) {
            for (EHadoopVersion4Drivers version : EHadoopVersion4Drivers.values()) {
                if (display) {
                    if (name.equalsIgnoreCase(version.getVersionDisplay())) {
                        return version;
                    }
                } else {
                    if (name.equalsIgnoreCase(version.getVersionValue())) {
                        return version;
                    }
                }
            }
        }
        return null;
    }

    public static List<EHadoopVersion4Drivers> indexOfByDistribution(String distribution) {
        List<EHadoopVersion4Drivers> v4dList = new ArrayList<EHadoopVersion4Drivers>();
        if (distribution != null) {
            for (EHadoopVersion4Drivers v4d : EHadoopVersion4Drivers.values()) {
                if (v4d.supportDistribution(distribution)) {
                    v4dList.add(v4d);
                }
            }
        }
        return v4dList;
    }

    public static EHadoopVersion4Drivers getVersion4DriversByDriverStrs(String driverStrs) {
        if (StringUtils.isEmpty(driverStrs)) {
            return null;
        }
        for (EHadoopVersion4Drivers version4Drivers : EHadoopVersion4Drivers.values()) {
            if (driverStrs.equalsIgnoreCase(version4Drivers.getDriverStrings())) {
                return version4Drivers;
            }
        }

        return null;
    }

    public static String getVersionByDriverStrs(String driverStrs) {
        return getVersionByDriverStrs(driverStrs, false);
    }

    public static String getVersionDisplayByDriverStrs(String driverStrs) {
        return getVersionByDriverStrs(driverStrs, true);
    }

    private static String getVersionByDriverStrs(String driverStrs, boolean display) {
        EHadoopVersion4Drivers version4Drivers = getVersion4DriversByDriverStrs(driverStrs);
        if (version4Drivers == null) {
            return null;
        }
        if (display) {
            return version4Drivers.getVersionDisplay();
        }
        return version4Drivers.getVersionValue();
    }
}
