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
package org.talend.hadoop.distribution.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.model.DistributionBean;

/**
 * DOC ggu class global comment. Detailled comment
 */
public class HadoopDistributionsHelper {

    /**
     * for Hadoop Distributions.
     * 
     * @link HadoopComponent
     */
    private static final DistributionsHelper HELPER = new DistributionsHelper(HadoopComponent.class.getName());

    public static DistributionBean[] getHadoopDistributions() {
        return HELPER.getDistributions();
    }

    public static String[] getHadoopDistributionsDisplay(boolean withCustom) {
        List<String> distributionsDisplay = new ArrayList<String>();
        for (DistributionBean bean : getHadoopDistributions()) {
            if (!withCustom && bean.useCustom()) {
                continue;
            }
            distributionsDisplay.add(bean.displayName);
        }
        return distributionsDisplay.toArray(new String[0]);
    }

    public static DistributionBean getHadoopDistributionByDisplayName(String displayName) {
        DistributionBean distribution = null;
        for (DistributionBean bean : getHadoopDistributions()) {
            if (bean.displayName.equals(displayName)) {
                distribution = bean;
                break;
            }
        }
        return distribution;
    }

    public static DistributionBean getHadoopDistribution(String name) {
        DistributionBean distribution = null;
        for (DistributionBean bean : getHadoopDistributions()) {
            if (bean.name.equals(name)) {
                distribution = bean;
                break;
            }
        }
        return distribution;
    }

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

        BundleContext bc = FrameworkUtil.getBundle(DistributionFactory.class).getBundleContext();
        Collection<ServiceReference<HadoopComponent>> distributions = Collections.EMPTY_LIST;
        try {
            distributions = bc.getServiceReferences(HadoopComponent.class, null);
        } catch (InvalidSyntaxException e) {
            CommonExceptionHandler.process(e);
        }

        for (ServiceReference<HadoopComponent> sr : distributions) {
            HadoopComponent np = bc.getService(sr);
            String thatDistribution = np.getDistribution();
            if (EHadoopDistributions.CUSTOM.getName().equals(thatDistribution) && thatDistribution.equals(pDistribution)) {
                return np;
            }
            if (thatDistribution != null && thatDistribution.equals(pDistribution)) {
                String thatVersion = np.getVersion();
                if (thatVersion != null && thatVersion.equals(pVersion)) {
                    return np;
                }
            }
        }
        throw new Exception("The distribution " + pDistribution + " with the version " + pVersion + " doesn't exist."); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
