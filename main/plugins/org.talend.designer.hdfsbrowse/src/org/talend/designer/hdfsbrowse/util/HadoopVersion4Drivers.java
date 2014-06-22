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
package org.talend.designer.hdfsbrowse.util;

import java.util.ArrayList;
import java.util.List;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopVersion4Drivers {

    private String versionDisplayName;

    private String versionValue;

    private List<EHadoopDistributions> distributions = new ArrayList<EHadoopDistributions>();

    private List<String> drivers = new ArrayList<String>();

    HadoopVersion4Drivers(EHadoopDistributions distribution, String driver) {
        this(new EHadoopDistributions[] { distribution }, null, null, new String[] { driver });
    }

    HadoopVersion4Drivers(EHadoopDistributions distribution, String[] drivers) {
        this(new EHadoopDistributions[] { distribution }, null, null, drivers);
    }

    HadoopVersion4Drivers(EHadoopDistributions distribution, String versionDisplayName, String versionValue) {
        this(new EHadoopDistributions[] { distribution }, versionDisplayName, versionValue, new String[0]);
    }

    HadoopVersion4Drivers(EHadoopDistributions distribution, String versionDisplayName, String versionValue, String driver) {
        this(new EHadoopDistributions[] { distribution }, versionDisplayName, versionValue, new String[] { driver });
    }

    HadoopVersion4Drivers(EHadoopDistributions distribution, String versionDisplayName, String versionValue, String[] drivers) {
        this(new EHadoopDistributions[] { distribution }, versionDisplayName, versionValue, drivers);
    }

    HadoopVersion4Drivers(EHadoopDistributions[] distributions, String versionDisplayName, String versionValue, String driver) {
        this(distributions, versionDisplayName, versionValue, new String[] { driver });
    }

    HadoopVersion4Drivers(EHadoopDistributions[] hdDistributions, String versionDisplayName, String versionValue, String[] drivers) {
        if (hdDistributions != null) {
            for (EHadoopDistributions dis : hdDistributions) {
                if (dis != null && !distributions.contains(dis)) {
                    distributions.add(dis);
                }
            }
        }
        if (drivers != null) {
            for (String d : drivers) {
                if (d != null && !this.drivers.contains(d)) {
                    this.drivers.add(d);
                }

            }
        }
        this.versionDisplayName = versionDisplayName;
        this.versionValue = versionValue;
    }

    public String getVersionDisplayName() {
        return this.versionDisplayName;
    }

    public String getVersionValue() {
        return this.versionValue;
    }

    public List<EHadoopDistributions> getDistributions() {
        return this.distributions;
    }

    public List<String> getDrivers() {
        return this.drivers;
    }

}
