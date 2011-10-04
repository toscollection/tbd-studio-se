package org.talend.configurator.utils;

import static org.junit.Assert.*;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.Test;

public class SimpleConfiguratorUtilsTest {

	@Test
	public void testParseVersionFromLocation() throws URISyntaxException {
		String version = SimpleConfiguratorUtils.parseVersionFromLocation(new URI("file://foo/bar/org.talend_V1.0_RC1.jar"));
		assertEquals("V1.0_RC1", version);
		//check folder without last /
		version = SimpleConfiguratorUtils.parseVersionFromLocation(new URI("file://foo/bar/org.talend_V1.2.0_NB5"));
		assertEquals("V1.2.0_NB5", version);
		//check folder with last /
		version = SimpleConfiguratorUtils.parseVersionFromLocation(new URI("file://foo/bar/org.talend_V1.2.8_NB5/"));
		assertEquals("V1.2.8_NB5", version);
	}

}
