// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2011 Talend Ð www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.configurator.config;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import org.junit.Before;
import org.junit.Test;
import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.talend.configurator.Activator;
import org.talend.configurator.utils.EquinoxUtils;
import org.talend.configurator.utils.URIUtil;
import org.talend.configurator.utils.Utils;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * DOC sgandon class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 55206 2011-02-15 17:32:14Z mhirt $
 * 
 */
public class TalendConfiguratorImplTest {

    URI installLocationURI = EquinoxUtils.getInstallLocationURI(Activator.getDefault().getContext());

    File licenceFile = new File(URIUtil.toFile(installLocationURI), Utils.decode("6C6963656E7365"));//$NON-NLS-1$

    Bundle bundle = FrameworkUtil.getBundle(getClass());

    File datelicFile = bundle.getBundleContext().getDataFile(TalendConfiguratorImpl.LAST_LICENCE_DATE_FILE_NAME);

    @Before
    public void preTest() {
        if (licenceFile.exists()) {
            assertTrue(licenceFile.delete());
        }// else licenceFile does not exit so no need to remove

        if (datelicFile.exists()) {
            assertTrue(datelicFile.delete());
        }
    }

    /**
     * Test method for {@link org.talend.configurator.config.TalendConfiguratorImpl#hasLicenceFileChanged()} .
     * 
     * @throws IOException
     */
    @Test
    public void testHasLicenceFileChanged_NoLicence() throws IOException {
        TalendConfiguratorImpl talendConfiguratorImpl = new TalendConfiguratorImpl(bundle.getBundleContext(), bundle);
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
        assertTrue(datelicFile.exists());
    }

    /**
     * Test method for {@link org.talend.configurator.config.TalendConfiguratorImpl#hasLicenceFileChanged()} .
     * 
     * @throws IOException
     */
    @Test
    public void testHasLicenceFileChanged_NewLicence() throws IOException {
        licenceFile.createNewFile();
        TalendConfiguratorImpl talendConfiguratorImpl = new TalendConfiguratorImpl(bundle.getBundleContext(), bundle);
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
        assertTrue(datelicFile.exists());
    }

    /**
     * Test method for {@link org.talend.configurator.config.TalendConfiguratorImpl#hasLicenceFileChanged()} .
     * 
     * @throws IOException
     */
    @Test
    public void testHasLicenceFileChanged_LicenceRemoved() throws IOException {
        licenceFile.createNewFile();
        TalendConfiguratorImpl talendConfiguratorImpl = new TalendConfiguratorImpl(bundle.getBundleContext(), bundle);
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
        assertTrue(datelicFile.exists());
        // delete the licence
        assertTrue(licenceFile.delete());
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
    }

    /**
     * Test method for {@link org.talend.configurator.config.TalendConfiguratorImpl#hasLicenceFileChanged()} .
     * 
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void testHasLicenceFileChanged_NewerLicence() throws IOException, InterruptedException {
        licenceFile.createNewFile();
        TalendConfiguratorImpl talendConfiguratorImpl = new TalendConfiguratorImpl(bundle.getBundleContext(), bundle);
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
        assertTrue(datelicFile.exists());
        // wait for one second to be sure date of modification has changed caus
        // can be too quick.
        synchronized (talendConfiguratorImpl) {
            talendConfiguratorImpl.wait(1000);
        }
        // delete and recreate licence file to make it newer
        assertTrue(licenceFile.delete());
        licenceFile.createNewFile();
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
    }

    /**
     * Test method for {@link org.talend.configurator.config.TalendConfiguratorImpl#hasLicenceFileChanged()} .
     * 
     * @throws IOException
     */
    @Test
    public void testHasLicenceFileChanged_LicenceHasNotChanged() throws IOException {
        licenceFile.createNewFile();
        TalendConfiguratorImpl talendConfiguratorImpl = new TalendConfiguratorImpl(bundle.getBundleContext(), bundle);
        assertTrue(talendConfiguratorImpl.hasLicenceFileChanged());
        assertTrue(datelicFile.exists());
        assertFalse(talendConfiguratorImpl.hasLicenceFileChanged());
    }

}
