package org.talend.configurator.utils;

import static org.junit.Assert.assertEquals;

import java.net.URISyntaxException;

import junit.framework.Assert;

import org.junit.Test;

public class SimpleConfiguratorUtilsTest {

    @Test
    public void testParseVersionFromLocation() throws URISyntaxException {
        String version = SimpleConfiguratorUtils.extractVersionFromLocation("/foo/bar/org.talend_V1.0_RC1.jar");
        assertEquals("V1.0_RC1", version);
        // check folder without last /
        version = SimpleConfiguratorUtils.extractVersionFromLocation("org.talend_V1.2.0_NB5");
        assertEquals("V1.2.0_NB5", version);
        // check folder with last /
        version = SimpleConfiguratorUtils.extractVersionFromLocation("/foo/bar/org.talend_V1.2.8_NB5/");
        assertEquals("V1.2.8_NB5", version);
    }

    @Test
    public void testSortPluginNameVersion() {
        String lastestVersion = SimpleConfiguratorUtils.getLatestPluginVersion(new String[] { "aaaa.bbbb_1.0.0.xx_xxx",
                "aaaa.bbbb_1.0.100.xx_xxx", "aaaa.bbbb_4.2.100.xx_xxx", "aaaa.bbbb_2.3.100.xx_xxx" });
        Assert.assertEquals(lastestVersion, "4.2.100.xx_xxx");
    }

    @Test
    public void testSortPluginNameVersion_Patch() {
        String lastestVersion = SimpleConfiguratorUtils.getLatestPluginVersion(new String[] { "aaaa.bbbb_5.0.0.NB_r69398",
                "aaaa.bbbb_5.0.0.NB_r69398_patched", "aaaa.bbbb_4.1.0.NB_r69398", "aaaa.bbbb_2.3.100.xx_xxx" });
        Assert.assertEquals(lastestVersion, "5.0.0.NB_r69398_patched");
    }
}
