// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.configurator.ambari;

import java.util.HashMap;
import java.util.Map;

import org.apache.ambari.api.model.ApiService;
import org.apache.ambari.api.model.ApiServiceList;
import org.apache.ambari.api.v1.ConfigsResource;
import org.apache.ambari.api.v1.ServicesResource;
import org.talend.repository.hadoopcluster.configurator.HadoopCluster;
import org.talend.repository.hadoopcluster.configurator.HadoopClusterService;
import org.talend.repository.hadoopcluster.configurator.HadoopHostedService;

/**
 * created by bchen on Jun 3, 2015 Detailled comment
 *
 */
public class HadoopAmbariCluster implements HadoopCluster {

    ServicesResource services;

    ConfigsResource configs;

    /**
     * DOC bchen HadoopAmbariCluster constructor comment.
     */
    public HadoopAmbariCluster(ServicesResource services, ConfigsResource configs) {
        this.services = services;
        this.configs = configs;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.hadoopcluster.configurator.HadoopCluster#getHostedServices()
     */
    @Override
    public Map<HadoopHostedService, HadoopClusterService> getHostedServices() {
        ApiServiceList sers = services.readServices();
        Map<HadoopHostedService, HadoopClusterService> servicesMapping = new HashMap<HadoopHostedService, HadoopClusterService>();
        for (ApiService service : sers.getServices()) {
            String serviceName = service.getInfo().getServiceName();
            if (HadoopHostedService.isSupport(serviceName)) {
                servicesMapping.put(HadoopHostedService.fromString(serviceName), new HadoopAmbariClusterService(serviceName,
                        configs));
            }
        }
        return servicesMapping;
    }

}
