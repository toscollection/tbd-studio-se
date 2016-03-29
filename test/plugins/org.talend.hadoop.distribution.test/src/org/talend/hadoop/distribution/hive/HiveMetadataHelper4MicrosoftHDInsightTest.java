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
package org.talend.hadoop.distribution.hive;

import org.junit.Test;
import org.talend.core.model.metadata.connection.hive.HiveModeInfo;
import org.talend.hadoop.distribution.constants.hdinsight.IMicrosoftHDInsightDistribution;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HiveMetadataHelper4MicrosoftHDInsightTest extends AbstractTest4HiveMetadataHelper {

    @Override
    protected String getDistribution() {
        return IMicrosoftHDInsightDistribution.DISTRIBUTION_NAME;
    }

    @Override
    protected String getDistributionDisplay() {
        return IMicrosoftHDInsightDistribution.DISTRIBUTION_DISPLAY_NAME;
    }

    @Test
    public void testHiveMode_HDInsight31() {
        String[] arr = new String[] { "Shoudn't support" };
        doTestGetHiveModesDisplay(IMicrosoftHDInsightDistribution.VERSION_31, arr);
    }

    @Test
    public void testHiveServer_HDInsight31() {
        String[] arr = new String[] { "Shoudn't support" };
        doTestGetHiveServersDisplay(IMicrosoftHDInsightDistribution.VERSION_31, HiveModeInfo.EMBEDDED.getName(), arr);
        doTestGetHiveServersDisplay(IMicrosoftHDInsightDistribution.VERSION_31, HiveModeInfo.STANDALONE.getName(), arr);
    }

    @Test
    public void testHiveMode_HDInsight32() {
        String[] arr = new String[] { "Shoudn't support" };
        doTestGetHiveModesDisplay(IMicrosoftHDInsightDistribution.VERSION_32, arr);
    }

    @Test
    public void testHiveServer_HDInsight32() {
        String[] arr = new String[] { "Shoudn't support" };
        doTestGetHiveServersDisplay(IMicrosoftHDInsightDistribution.VERSION_32, HiveModeInfo.EMBEDDED.getName(), arr);
        doTestGetHiveServersDisplay(IMicrosoftHDInsightDistribution.VERSION_32, HiveModeInfo.STANDALONE.getName(), arr);
    }
}
