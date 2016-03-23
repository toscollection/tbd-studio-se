// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.service;

import java.util.ArrayList;
import java.util.List;

import org.talend.core.hadoop.IHadoopDistributionService;
import org.talend.core.runtime.hd.IHDistribution;
import org.talend.core.runtime.hd.IHDistributionVersion;
import org.talend.hadoop.distribution.constants.apache.IApacheDistribution;
import org.talend.hadoop.distribution.constants.emr.IAmazonEMRDistribution;
import org.talend.hadoop.distribution.helper.DistributionHelper;
import org.talend.hadoop.distribution.helper.DistributionsManager;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;
import org.talend.hadoop.distribution.model.DistributionBean;

/**
 * created by cmeng on Jan 15, 2016 Detailled comment
 *
 */
public class HadoopDistributionService implements IHadoopDistributionService {

    public IHDistribution[] getDistributions(String service) {
        if (service != null) {
            DistributionsManager helper = new DistributionsManager(service);
            return helper.getDistributions();
        }
        return new IHDistribution[0];
    }

    @Override
    public IHDistribution[] getOozieDistributions() {
        DistributionBean[] hadoopDistributions = HadoopDistributionsHelper.getHadoopDistributions();
        List<IHDistribution> oozieDistributions = new ArrayList<IHDistribution>();
        for (DistributionBean d : hadoopDistributions) {
            if (IApacheDistribution.DISTRIBUTION_NAME.equals(d.name) || IAmazonEMRDistribution.DISTRIBUTION_NAME.equals(d.name)) {
                continue;
            }
            oozieDistributions.add(d);
        }
        return oozieDistributions.toArray(new IHDistribution[0]);
    }

    @Override
    public boolean doSupportService(IHDistributionVersion distributionVersion, String service) {
        return DistributionHelper.doSupportService(distributionVersion, service);
    }

    public IHDistribution getHadoopDistribution(String name, boolean byDisplay) {
        return HadoopDistributionsHelper.getHadoopDistribution(name, byDisplay);
    }

    public IHDistributionVersion getHadoopDistributionVersion(String version, boolean byDisplay) {
        return HadoopDistributionsHelper.getDistributionVersion(version, byDisplay);
    }

}
