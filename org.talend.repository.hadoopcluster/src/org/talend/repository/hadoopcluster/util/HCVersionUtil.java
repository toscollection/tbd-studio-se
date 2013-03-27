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
package org.talend.repository.hadoopcluster.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.eclipse.emf.common.util.EMap;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * created by ycbai on 2013-3-13 Detailled comment
 * 
 */
public class HCVersionUtil {

    public final static String JAR_SEPARATOR = ";"; //$NON-NLS-1$

    public final static String COMP_JAR_PARAM = "JAR_NAME"; //$NON-NLS-1$

    public static Map<String, Set<String>> getCustomVersionMap(HadoopClusterConnection connection) {
        Map<String, Set<String>> map = new HashMap<String, Set<String>>();
        if (connection == null) {
            return map;
        }

        EMap<String, String> parameters = connection.getParameters();
        if (parameters.size() == 0) {
            return map;
        }

        ECustomVersionGroup[] values = ECustomVersionGroup.values();
        for (ECustomVersionGroup group : values) {
            String groupName = group.getName();
            String jarString = parameters.get(groupName);
            if (jarString != null) {
                Set<String> jarSet = new HashSet<String>();
                String[] jarArray = jarString.split(JAR_SEPARATOR);
                for (String jar : jarArray) {
                    jarSet.add(jar);
                }
                map.put(groupName, jarSet);
            }
        }

        return map;
    }

    public static void injectCustomVersionMap(HadoopClusterConnection connection, Map<String, Set<String>> map) {
        if (connection == null || map == null) {
            return;
        }

        EMap<String, String> parameters = connection.getParameters();
        Iterator<Entry<String, Set<String>>> iter = map.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, Set<String>> entry = iter.next();
            String groupName = entry.getKey();
            Set<String> jars = entry.getValue();
            if (jars != null && jars.size() > 0) {
                StringBuffer jarBuffer = new StringBuffer();
                for (String jar : jars) {
                    jarBuffer.append(jar).append(JAR_SEPARATOR);
                }
                if (jarBuffer.length() > 0) {
                    jarBuffer.deleteCharAt(jarBuffer.length() - 1);
                    parameters.put(groupName, jarBuffer.toString());
                }
            }
        }
    }

    public static List<Map<String, Object>> getCompCustomJarParamFromRep(HadoopClusterConnection connection,
            ECustomVersionGroup versionGroup) {
        List<Map<String, Object>> compCustomJars = new ArrayList<Map<String, Object>>();
        if (connection == null || versionGroup == null) {
            return compCustomJars;
        }
        Map<String, Set<String>> customVersionMap = getCustomVersionMap(connection);
        Set<String> jars = customVersionMap.get(versionGroup.getName());
        if (jars != null) {
            for (String jar : jars) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(COMP_JAR_PARAM, jar);
                compCustomJars.add(map);
            }
        }

        return compCustomJars;
    }

    public static Map<String, Set<String>> getRepCustomJarParamFromComp(List<Map<String, Object>> compCustomJars,
            ECustomVersionGroup versionGroup) {
        Map<String, Set<String>> customVersionMap = new HashMap<String, Set<String>>();
        if (compCustomJars == null) {
            return customVersionMap;
        }
        Set<String> repCustomJars = new HashSet<String>();
        for (Map<String, Object> map : compCustomJars) {
            Object obj = map.get(COMP_JAR_PARAM);
            if (obj != null && obj instanceof String) {
                repCustomJars.add((String) obj);
            }
        }
        customVersionMap.put(versionGroup.getName(), repCustomJars);

        return customVersionMap;
    }

}
