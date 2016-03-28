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
package org.talend.hadoop.distribution.constants.emr;

import org.talend.core.runtime.hd.IHDConstants;

/**
 * DOC ggu class global comment. Detailled comment
 */
@SuppressWarnings("nls")
public interface IAmazonEMRDistribution {

    static final String DISTRIBUTION_NAME = IHDConstants.DISTRIBUTION_AMAZON_EMR;

    static final String DISTRIBUTION_DISPLAY_NAME = "Amazon EMR";

    /**
     * Version strings if need.
     */
    static final String VERSION_400 = "EMR_4_0_0";

    static final String VERSION_400_DISPLAY = "EMR 4.0.0 (Apache 2.6.0)";

    static final String VERSION_400_PIG_DISPLAY = "EMR 4.0.0 (Pig 0.14.0)";

    static final String VERSION_400_HIVE_DISPLAY = "EMR 4.0.0 (Hive 1.0.0)";

    static final String VERSION_APACHE_240 = "APACHE_2_4_0_EMR";

    static final String VERSION_APACHE_240_DISPLAY = "Apache 2.4.0";

    static final String VERSION_APACHE_240_PIG_DISPLAY = "Apache 2.4.0 (Pig 0.12.0)";

    static final String VERSION_APACHE_240_HBASE_DISPLAY = "Apache 2.4.0 (HBase 0.94.18)";

    static final String VERSION_APACHE_240_HIVE_DISPLAY = "Apache 2.4.0 (Hive 0.11.0)";

    static final String VERSION_APACHE_240_HIVE_0131 = "APACHE_2_4_0_EMR_0_13_1";

    static final String VERSION_APACHE_240_HIVE_0131_DISPLAY = "Apache 2.4.0 (Hive 0.13.1)";

    static final String VERSION_APACHE_103 = "APACHE_1_0_3_EMR";

    static final String VERSION_APACHE_103_DISPLAY = "Apache 1.0.3";

    static final String VERSION_APACHE_103_PIG_DISPLAY = "Apache 1.0.3 (Pig 0.9.2)";

    static final String VERSION_APACHE_103_HBASE_DISPLAY = "Apache 1.0.3 (HBase 0.92.0)";

    static final String VERSION_APACHE_103_HIVE_DISPLAY = "Apache 1.0.3 (Hive 0.8.1)";
}
