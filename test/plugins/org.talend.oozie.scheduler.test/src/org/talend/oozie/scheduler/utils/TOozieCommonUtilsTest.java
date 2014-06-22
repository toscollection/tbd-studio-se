// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.utils;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.oozie.scheduler.constants.OozieJobProcessStatus;
import org.talend.oozie.scheduler.constants.TOozieCommonConstants;
import org.talend.oozie.scheduler.constants.TOozieTimeUnit;

import com.hortonworks.etl.talend.JobContext.Timeunit;
import com.hortonworks.etl.talend.JobSubmission;

/**
 * Created by Marvin Wang on May 22, 2012 for JUnit test.
 */
public class TOozieCommonUtilsTest {

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void testIsWindowsOS() {
        String osName = System.getProperty("os.name");
        if (osName.trim().startsWith(TOozieCommonConstants.OS_WINDOWS_PREFIX))
            Assert.assertTrue("The current OS is windows, however, the method returns false!", TOozieCommonUtils.isWindowsOS());
        else
            Assert.assertFalse("The current OS is not windows, however, the method return true!", TOozieCommonUtils.isWindowsOS());
    }

    @Test
    public void testConvertToOozieJobProcessStatus() {
        OozieJobProcessStatus prepStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.PREP);
        OozieJobProcessStatus runningStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.RUNNING);
        OozieJobProcessStatus succeededStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.SUCCEEDED);
        OozieJobProcessStatus killedStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.KILLED);
        OozieJobProcessStatus failedStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.FAILED);
        OozieJobProcessStatus suspendedStatus = TOozieCommonUtils.convertToOozieJobProcessStatus(JobSubmission.Status.SUSPENDED);

        Assert.assertTrue("\"JobSubmission.Status.PREP\" should be converted to \"OozieJobProcessStatus.PREP\"!",
                OozieJobProcessStatus.PREP == prepStatus);
        Assert.assertTrue("\"JobSubmission.Status.RUNNING\" should be converted to \"OozieJobProcessStatus.RUNNING\"!",
                OozieJobProcessStatus.RUNNING == runningStatus);
        Assert.assertTrue("\"JobSubmission.Status.SUCCEEDED\" should be converted to \"OozieJobProcessStatus.SUCCEEDED\"!",
                OozieJobProcessStatus.SUCCEEDED == succeededStatus);
        Assert.assertTrue("\"JobSubmission.Status.KILLED\" should be converted to \"OozieJobProcessStatus.KILLED\"!",
                OozieJobProcessStatus.KILLED == killedStatus);
        Assert.assertTrue("\"JobSubmission.Status.FAILED\" should be converted to \"OozieJobProcessStatus.FAILED\"!",
                OozieJobProcessStatus.FAILED == failedStatus);
        Assert.assertTrue("\"JobSubmission.Status.SUSPENDED\" should be converted to \"OozieJobProcessStatus.SUSPENDED\"!",
                OozieJobProcessStatus.SUSPENDED == suspendedStatus);
    }

    @Test
    public void testGetTimeUnitItems() {
        String[] timeUnitNames = TOozieCommonUtils.getTimeUnitItems();
        String[] expectedTimeUnitNames = new String[] { TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_MINUTE,
                TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_HOUR, TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_DAY,
                TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_WEEK, TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_MONTH,
                TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_DAY,
                TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_END_OF_MONTH,
                TOozieCommonConstants.OOZIE_TIMEUNIT_DISPLAY_NAME_NONE };
        Assert.assertNotNull("The items of time unit for display should not be null!", timeUnitNames);

        Assert.assertArrayEquals("The expected names of time unit is different from the actual names!", expectedTimeUnitNames,
                timeUnitNames);
    }

    @Test
    public void testLookupTimeUnit() {
        List<Timeunit> expecteds = new ArrayList<Timeunit>();
        expecteds.add(Timeunit.MINUTE);
        expecteds.add(Timeunit.HOUR);
        expecteds.add(Timeunit.DAY);
        expecteds.add(Timeunit.WEEK);
        expecteds.add(Timeunit.MONTH);
        expecteds.add(Timeunit.END_OF_DAY);
        expecteds.add(Timeunit.END_OF_MONTH);
        expecteds.add(Timeunit.NONE);
        int lookupIndex = TOozieTimeUnit.values().length + 1;
        int expectedsSize = expecteds.size();
        for (int i = -1; i < lookupIndex; i++) {
            Timeunit acturalTU = TOozieCommonUtils.lookupTimeUnit(i);
            if (i < expectedsSize - 1 && i >= 0)
                Assert.assertEquals("The expected TimeUnit is \' " + expecteds.get(i) + "\', but the actual is \' " + acturalTU
                        + "\'!", expecteds.get(i), acturalTU);
            else
                Assert.assertEquals("The expected TimeUnit is \' " + Timeunit.NONE + "\', but the actual is \' " + acturalTU
                        + "\'!", Timeunit.NONE, acturalTU);

        }
    }
}
