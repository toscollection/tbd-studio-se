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

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.hadoop.IHadoopDistributionService;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.SparkComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;


/**
 * created by cmeng on Jan 15, 2016
 * Detailled comment
 *
 */
public class HadoopDistributionService implements IHadoopDistributionService {

    /* (non-Javadoc)
     * @see org.talend.core.hadoop.IHadoopDistributionService#isSupportSpark(org.talend.core.hadoop.version.EHadoopVersion4Drivers)
     */
    @Override
    public boolean isSupportSpark(EHadoopVersion4Drivers version) {
        if (version == null) {
            return false;
        }

        try {
            HadoopComponent hc = DistributionFactory.buildDistribution(version.getDistribution().getName(),
                    version.getVersionValue());
            if (hc instanceof SparkComponent) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return false;
    }

    /* (non-Javadoc)
     * @see org.talend.core.hadoop.IHadoopDistributionService#isSupportSparkStreaming(org.talend.core.hadoop.version.EHadoopVersion4Drivers)
     */
    @Override
    public boolean isSupportSparkStreaming(EHadoopVersion4Drivers version) {
        if (version == null) {
            return false;
        }

        try {
            HadoopComponent hc = DistributionFactory.buildDistribution(version.getDistribution().getName(),
                    version.getVersionValue());
            if (hc instanceof SparkStreamingComponent) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return false;
    }

}
