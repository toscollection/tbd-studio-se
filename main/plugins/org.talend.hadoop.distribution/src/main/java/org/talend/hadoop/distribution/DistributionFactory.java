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

package org.talend.hadoop.distribution;

import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.helper.HadoopDistributionsHelper;

/**
 * This is a factory class that provides a way to create a {@link HadoopComponent} using the underlying GUI classes
 *
 * {@link EHadoopVersion4Drivers} and {@link EHadoopDistributions}
 *
 */
public class DistributionFactory {

    /**
     *
     * Builds a {@link HadoopComponent} distribution.
     *
     * @param pDistribution The name of the distribution
     * @param pVersion The name of the version
     * @return an implementation of {@link HadoopComponent}.
     * @throws Exception
     */
    public static HadoopComponent buildDistribution(String pDistribution, String pVersion) throws Exception {
        return HadoopDistributionsHelper.buildDistribution(pDistribution, pVersion);
    }

    /**
     * Execute a methode for a given distribution and version. This function must return a booelan
     *
     * @param methodName the name of the method
     * @param distribution the name of the distribution
     * @param version the name of the version
     * 
     * @return
     * @throws Exception
     */
    public static boolean executeBooleanMethod(String methodName, String distribution, String version) throws Exception {
        HadoopComponent distrib = DistributionFactory.buildDistribution(distribution, version);
        try {
            java.lang.reflect.Method m = distrib.getClass().getMethod(methodName, new Class<?>[0]);
            return (Boolean) m.invoke(distrib, new Object[0]);
        } catch (NoSuchMethodException e) {
            throw new Exception("The distribution " + distribution + " with the version " + version //$NON-NLS-1$ //$NON-NLS-2$
                    + " is not supported in this component. Please check your configuration."); //$NON-NLS-1$
        }
    }

}
