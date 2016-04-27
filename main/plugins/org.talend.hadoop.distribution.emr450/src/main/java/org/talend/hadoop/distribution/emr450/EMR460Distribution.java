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

package org.talend.hadoop.distribution.emr450;

import java.util.HashMap;
import java.util.Map;

import org.talend.hadoop.distribution.ComponentType;

// This class extends {@see EMR450Distribution} as it shares the same behaviour and dependencies.
public class EMR460Distribution extends EMR450Distribution {

    public static final String VERSION_46 = "EMR_4_6_0"; //$NON-NLS-1$

    public static final String VERSION_DISPLAY_46 = "EMR 4.6.0 (Apache 2.7.2)"; //$NON-NLS-1$

    public static final String PIG_EMR460_DISPLAY = "EMR 4.6.0 (Pig 0.14.0)";//$NON-NLS-1$

    public static final String SQOOP_EMR460_DISPLAY = "EMR 4.6.0 (Sqoop 1.4.6)"; //$NON-NLS-1$

    public static final String HIVE_EMR460_DISPLAY = "EMR 4.6.0 (Hive 1.0.0)"; //$NON-NLS-1$

    private static Map<ComponentType, String> customVersionDisplayNames = new HashMap<>();

    static {
        customVersionDisplayNames.put(ComponentType.PIG, PIG_EMR460_DISPLAY);
        customVersionDisplayNames.put(ComponentType.HIVE, HIVE_EMR460_DISPLAY);
        customVersionDisplayNames.put(ComponentType.SQOOP, SQOOP_EMR460_DISPLAY);
    }

    @Override
    public String getVersion() {
        return VERSION_46;
    }

    @Override
    public String getVersionName(ComponentType componentType) {
        String customVersionName = customVersionDisplayNames.get(componentType);
        return customVersionName != null ? customVersionName : VERSION_DISPLAY_46;
    }
}
