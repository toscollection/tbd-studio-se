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
package org.talend.hadoop.distribution.hdinsight320.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.hdinsight320.HDInsight32Distribution;
import org.talend.hadoop.distribution.test.config.AbstractTest4DefaultConfiguration;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HDInsight32DefaultConfigurationTest extends AbstractTest4DefaultConfiguration {

    @Override
    protected Class<? extends HadoopComponent> getHadoopComponentClass() {
        return HDInsight32Distribution.class;
    }

    @Before
    public void preTest() {
        HadoopComponent hadoopComponent = getHadoopComponent();
        assertNotNull("Can't load hadoop distribution", hadoopComponent);
    }

    // @Test
    public void testBasic() {
        //
    }

    @Test
    public void testHive_NotSupport() {
        HadoopComponent hadoopComponent = getHadoopComponent();
        String defaultValue = hadoopComponent.getDefaultConfig(hadoopComponent.getDistribution(), EHadoopCategory.HIVE.getName());
        assertTrue("Should not support for Hive", defaultValue == null);
    }

    @Test
    public void testHBase_NotSupport() {
        HadoopComponent hadoopComponent = getHadoopComponent();
        String defaultValue = hadoopComponent
                .getDefaultConfig(hadoopComponent.getDistribution(), EHadoopCategory.HBASE.getName());
        assertTrue("Should not support for HBase", defaultValue == null);

    }
}
