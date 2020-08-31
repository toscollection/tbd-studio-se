package org.talend.hadoop.distribution.test.condition.common;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.talend.hadoop.distribution.ESparkVersion;
import org.talend.hadoop.distribution.condition.common.SparkBatchLocalCondition;


public class SparkBatchLocalConditionTest {
    
    @Test
    public void testGetConditionString() {
        
        String expected  = "(#LINK@NODE.SPARK_CONFIGURATION.SPARK_LOCAL_MODE=='true') AND (#LINK@NODE.SPARK_CONFIGURATION.SPARK_VERSION=='SPARK_3_0_0')";
        SparkBatchLocalCondition condition = new SparkBatchLocalCondition(ESparkVersion.SPARK_3_0);
        assertEquals(expected, condition.getConditionString());
    }
}
