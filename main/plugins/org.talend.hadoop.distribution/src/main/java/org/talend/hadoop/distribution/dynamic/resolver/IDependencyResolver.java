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
package org.talend.hadoop.distribution.dynamic.resolver;

import java.util.List;

import org.talend.designer.maven.aether.DependencyNode;
import org.talend.designer.maven.aether.IDynamicMonitor;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public interface IDependencyResolver {

    public DependencyNode collectDependencies(String groupId, String artifactId, String version, String scope, String classifier,
            IDynamicMonitor monitor) throws Exception;

    public DependencyNode collectDependencies(String groupId, String artifactId, String scope, String classifier,
            IDynamicMonitor monitor) throws Exception;

    public String getDependencyVersionByHadoopVersion(String groupId, String artifactId, IDynamicMonitor monitor)
            throws Exception;

    public List<String> listHadoopVersions(String baseVersion, String topVersion, IDynamicMonitor monitor) throws Exception;

}
