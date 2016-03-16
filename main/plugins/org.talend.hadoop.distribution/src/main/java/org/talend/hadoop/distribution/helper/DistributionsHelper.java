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
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.talend.commons.exception.CommonExceptionHandler;
import org.talend.hadoop.distribution.ComponentType;
import org.talend.hadoop.distribution.DistributionFactory;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.constants.Constant;
import org.talend.hadoop.distribution.model.DistributionBean;
import org.talend.hadoop.distribution.model.DistributionVersion;

/**
 * DOC ggu class global comment. Detailled comment
 */
public final class DistributionsHelper {

    private final String serviceName;

    private final ComponentType componentType;

    /**
     * service can't be null. if the service is HadoopComponent directly, the componentType will be null.
     */
    public DistributionsHelper(String serviceName, ComponentType componentType) {
        super();
        this.serviceName = serviceName;
        this.componentType = componentType;
    }

    public DistributionsHelper(String serviceName) {
        this(serviceName, null);
    }

    public DistributionsHelper(ComponentType componentType) {
        this(componentType.getService(), componentType);
    }

    private String getServiceName() {
        return serviceName;
    }

    private ComponentType getComponentType() {
        if (componentType == null) {
            ComponentType componentTypeByService = null;
            for (ComponentType t : ComponentType.values()) {
                if (t.getService().equals(getServiceName())) {
                    componentTypeByService = t;
                    break;
                }
            }
            return componentTypeByService;
        }
        return componentType;
    }

    public DistributionBean[] getDistributions() {
        if (getServiceName() == null) {
            return new DistributionBean[0];
        }
        final ComponentType type = getComponentType();

        // We retrieve all the implementations of the HadoopComponent service.
        BundleContext bc = FrameworkUtil.getBundle(DistributionFactory.class).getBundleContext();
        Collection<ServiceReference<? extends HadoopComponent>> distributions = new LinkedList<>();
        try {
            Class<? extends HadoopComponent> clazz = (Class<? extends HadoopComponent>) Class.forName(getServiceName());
            distributions.addAll(bc.getServiceReferences(clazz, null));
        } catch (InvalidSyntaxException | ClassNotFoundException e) {
            CommonExceptionHandler.process(e);
            return new DistributionBean[0];
        }
        Map<String, DistributionBean> disctributionsMap = new HashMap<String, DistributionBean>();

        for (ServiceReference<? extends HadoopComponent> sr : distributions) {
            HadoopComponent hc = bc.getService(sr);
            final String distribution = hc.getDistribution();
            final String distributionName = hc.getDistributionName();

            DistributionBean disctributionBean = disctributionsMap.get(distribution);
            if (disctributionBean == null) {
                disctributionBean = new DistributionBean(type, distribution, distributionName);
                disctributionsMap.put(distribution, disctributionBean);
            } else {// check the name and displayName
                if (!distribution.equals(disctributionBean.name) || !distributionName.equals(disctributionBean.displayName)) {
                    CommonExceptionHandler.warn(" There are different distribution name for " + disctributionBean);
                    continue;
                }
            }

            final String version = hc.getVersion();
            if (version != null) {
                DistributionVersion versionBean = new DistributionVersion(hc, disctributionBean, version, hc.getVersionName(type));
                versionBean.addModuleGroups(hc.getModuleGroups(type));
                // special condition for current version
                versionBean.displayCondition = hc.getDisplayCondition(type);
                disctributionBean.addVersion(versionBean);

                // FIXME, the default version is last one via service order?
                disctributionBean.setDefaultVersion(versionBean);

            }
            // add all version conditions ?
            disctributionBean.addCondition(hc.getDisplayCondition(type));
        }

        List<DistributionBean> distributionsList = new ArrayList<DistributionBean>(disctributionsMap.values());
        Collections.sort(distributionsList, new Comparator<DistributionBean>() {

            @Override
            public int compare(DistributionBean b1, DistributionBean b2) {
                if (Constant.DISTRIBUTION_CUSTOM.equals(b1.name)) { //$NON-NLS-1$
                    return 1;
                }
                if (Constant.DISTRIBUTION_CUSTOM.equals(b2.name)) { //$NON-NLS-1$
                    return -1;
                }
                return b1.name.compareTo(b2.name);
            }
        });
        return distributionsList.toArray(new DistributionBean[0]);
    }

}
