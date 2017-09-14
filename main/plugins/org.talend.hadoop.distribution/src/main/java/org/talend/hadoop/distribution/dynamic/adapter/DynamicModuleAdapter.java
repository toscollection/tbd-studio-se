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
package org.talend.hadoop.distribution.dynamic.adapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IProgressMonitor;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.hadoop.distribution.HadoopDistributionPlugin;
import org.talend.hadoop.distribution.dynamic.DynamicConfiguration;
import org.talend.hadoop.distribution.dynamic.bean.ModuleBean;
import org.talend.hadoop.distribution.dynamic.bean.TemplateBean;
import org.talend.hadoop.distribution.dynamic.resolver.IDependencyResolver;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;
import org.talend.maven.aether.DependencyNode;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class DynamicModuleAdapter extends AbstractDynamicAdapter {

    public static final String TAG_NAME = "libraryNeeded"; //$NON-NLS-1$

    public static final String ATTR_CONTEXT = "context"; //$NON-NLS-1$

    public static final String ATTR_ID = "id"; //$NON-NLS-1$

    public static final String ATTR_NAME = "name"; //$NON-NLS-1$

    public static final String ATTR_MVN_URI = "mvn_uri"; //$NON-NLS-1$

    public static final String ATTR_URI_PATH = "uripath"; //$NON-NLS-1$

    public static final String ATTR_BUNDLE_ID = "bundleID"; //$NON-NLS-1$

    public static final String ATTR_REQUIRED = "required"; //$NON-NLS-1$

    public static final String ATTR_MESSAGE = "message"; //$NON-NLS-1$

    public static final String ATTR_LANGUAGE = "language"; //$NON-NLS-1$

    private ModuleBean moduleBean;

    private IDependencyResolver dependencyResolver;

    private List<String> runtimeIds;

    private Map<String, List<String>> registedModulesMap;

    private Map<String, ModuleNeeded> existingModuleMap;

    public DynamicModuleAdapter(TemplateBean templateBean, DynamicConfiguration configuration, ModuleBean bean,
            IDependencyResolver resolver, Map<String, List<String>> registedModulesMap) {
        super(templateBean, configuration);
        this.moduleBean = bean;
        this.dependencyResolver = resolver;
        this.runtimeIds = new ArrayList<>();
        this.registedModulesMap = registedModulesMap;
        this.existingModuleMap = HadoopDistributionPlugin.getInstance().getExistingModuleMap();
    }

    public List<IDynamicConfiguration> adapt(IProgressMonitor monitor) throws Exception {
        resolve();
        
        TemplateBean templateBean = getTemplateBean();
        DynamicConfiguration configuration = getConfiguration();
        String distribution = configuration.getDistribution();
        String hadoopVersion = configuration.getVersion();

        List<IDynamicConfiguration> librariesNeeded = new ArrayList<>();

        String type = moduleBean.getType();
        if (ModuleBean.TYPE_BASE.equalsIgnoreCase(type)) {
            String groupId = moduleBean.getGroupId();
            String artifactId = moduleBean.getArtifactId();
            String scope = moduleBean.getScope();
            String classifier = moduleBean.getClassifier();
            String moduleVersion = moduleBean.getVersion();

            String registedModuleKey = getRegistedModulesKey(groupId, artifactId, moduleVersion, classifier);
            List<String> registedRuntimeIds = registedModulesMap.get(registedModuleKey);
            if (registedRuntimeIds != null) {
                runtimeIds.addAll(registedRuntimeIds);
            } else {
                DependencyNode dependencyNode = null;
                if (StringUtils.isEmpty(moduleVersion)) {
                    dependencyNode = dependencyResolver.collectDependencies(groupId, artifactId, scope, classifier, monitor);
                } else {
                    dependencyNode = dependencyResolver.collectDependencies(groupId, artifactId, moduleVersion, scope, classifier,
                            monitor);
                }
                librariesNeeded = createLibrariesNeeded(dependencyNode, distribution, hadoopVersion, moduleBean, runtimeIds,
                        templateBean);
            }
        } else if (ModuleBean.TYPE_REFERENCE.equalsIgnoreCase(type)) {
            String jarName = moduleBean.getJarName();
            ModuleNeeded moduleNeeded = existingModuleMap.get(jarName);
            if (moduleNeeded == null) {
                throw new UnsupportedOperationException("can't find existing library: " + jarName);
            }
            runtimeIds.add(moduleNeeded.getId());
        } else if (ModuleBean.TYPE_STANDARD.equalsIgnoreCase(type)) {
            String id = moduleBean.getId();
            String jarName = moduleBean.getJarName();
            String runtimeId = null;
            ModuleNeeded moduleNeeded = existingModuleMap.get(jarName);
            if (moduleNeeded != null) {
                runtimeId = moduleNeeded.getId();
            } else {
                runtimeId = DynamicDistributionUtils.getPluginKey(distribution, hadoopVersion, id);
                if (!registedModulesMap.containsKey(runtimeId)) {
                    IDynamicConfiguration libraryNeeded = createLibraryNeeded(moduleBean);
                    libraryNeeded.setAttribute(ATTR_ID, runtimeId);
                    librariesNeeded.add(libraryNeeded);
                    List<String> registedRuntimeIds = new ArrayList<>();
                    registedRuntimeIds.add(runtimeId);
                    registedModulesMap.put(runtimeId, registedRuntimeIds);
                }
            }

            runtimeIds.add(runtimeId);
        }

        return librariesNeeded;
    }

    private List<IDynamicConfiguration> createLibrariesNeeded(DependencyNode node, String distribution, String version,
            ModuleBean bean, List<String> runtimeIds, TemplateBean templateBean) {
        List<IDynamicConfiguration> librariesNeeded = new ArrayList<>();
        if (node == null) {
            return librariesNeeded;
        }

        String registedModulesKey = getRegistedModulesKey(node);
        List<String> registedRuntimeIds = registedModulesMap.get(registedModulesKey);
        if (registedRuntimeIds != null) {
            runtimeIds.addAll(registedRuntimeIds);
        } else {
            registedRuntimeIds = new ArrayList<>();
            registedModulesMap.put(registedModulesKey, registedRuntimeIds);

            String runtimeId = null;
            String jarName = node.getJarName();
            ModuleNeeded moduleNeeded = null;
            if (reuseExistingJars()) {
                moduleNeeded = existingModuleMap.get(jarName);
            }
            if (moduleNeeded != null) {
                runtimeId = moduleNeeded.getId();
            } else {
                runtimeId = DynamicDistributionUtils.getPluginKey(distribution, version, node.getJarName());
                IDynamicConfiguration libraryNeeded = createLibraryNeeded(node, templateBean);
                libraryNeeded.setAttribute(ATTR_CONTEXT, bean.getContext());
                libraryNeeded.setAttribute(ATTR_ID, runtimeId);
                librariesNeeded.add(libraryNeeded);
            }

            registedRuntimeIds.add(runtimeId);

            List<DependencyNode> dependencies = node.getDependencies();
            if (dependencies != null) {
                for (DependencyNode dependency : dependencies) {
                    List<IDynamicConfiguration> childLibrariesNeeded = createLibrariesNeeded(dependency, distribution, version,
                            bean, registedRuntimeIds, templateBean);
                    librariesNeeded.addAll(childLibrariesNeeded);
                }
            }

            runtimeIds.addAll(registedRuntimeIds);
        }

        return librariesNeeded;
    }

    private IDynamicConfiguration createLibraryNeeded(DependencyNode node, TemplateBean templateBean) {
        IDynamicConfiguration libraryNeeded = null;

        libraryNeeded = DynamicFactory.getInstance().createDynamicConfiguration();
        libraryNeeded.setConfigurationName(TAG_NAME);

        String jarname = node.getJarName();
        String repositoryUri = null;
        String addRepositoryUri = templateBean.getAddRepositoryInMvnUri();
        if (Boolean.valueOf(addRepositoryUri)) {
            repositoryUri = templateBean.getRepository();
        }
        String mvnUri = DynamicDistributionUtils.getMvnUrl(node, repositoryUri);

        libraryNeeded.setAttribute(ATTR_NAME, jarname);
        libraryNeeded.setAttribute(ATTR_MVN_URI, mvnUri);

        return libraryNeeded;
    }

    private IDynamicConfiguration createLibraryNeeded(ModuleBean bean) {
        IDynamicConfiguration libraryNeeded = null;

        libraryNeeded = DynamicFactory.getInstance().createDynamicConfiguration();
        libraryNeeded.setConfigurationName(TAG_NAME);

        if (bean != null) {
            String bundleID = bean.getBundleID();
            String context = bean.getContext();
            String id = bean.getId();
            String jarName = bean.getJarName();
            String language = bean.getLanguage();
            String message = bean.getMessage();
            String mvnUri = bean.getMvnUri();
            String required = bean.getRequired();
            String uriPath = bean.getUriPath();

            libraryNeeded.setAttribute(ATTR_BUNDLE_ID, bundleID);
            libraryNeeded.setAttribute(ATTR_CONTEXT, context);
            libraryNeeded.setAttribute(ATTR_ID, id);
            libraryNeeded.setAttribute(ATTR_NAME, jarName);
            libraryNeeded.setAttribute(ATTR_LANGUAGE, language);
            libraryNeeded.setAttribute(ATTR_MESSAGE, message);
            libraryNeeded.setAttribute(ATTR_MVN_URI, mvnUri);
            libraryNeeded.setAttribute(ATTR_REQUIRED, required);
            libraryNeeded.setAttribute(ATTR_URI_PATH, uriPath);
        }

        return libraryNeeded;
    }

    @Override
    protected void resolve() throws Exception {
        if (isResolved()) {
            return;
        }

        TemplateBean templateBean = getTemplateBean();
        String artifactId = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getArtifactId());
        String bundleID = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getBundleID());
        String context = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getContext());
        String classifier = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getClassifier());
        String groupId = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getGroupId());
        String language = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getLanguage());
        String id = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getId());
        String jarName = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getJarName());
        String message = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getMessage());
        String mvnUri = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getMvnUri());
        String required = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getRequired());
        String scope = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getScope());
        String type = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getType());
        String uriPath = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getUriPath());
        String version = (String) DynamicDistributionUtils.calculate(templateBean, moduleBean.getVersion());

        moduleBean.setArtifactId(artifactId);
        moduleBean.setBundleID(bundleID);
        moduleBean.setContext(context);
        moduleBean.setClassifier(classifier);
        moduleBean.setGroupId(groupId);
        moduleBean.setLanguage(language);
        moduleBean.setId(id);
        moduleBean.setJarName(jarName);
        moduleBean.setMessage(message);
        moduleBean.setMvnUri(mvnUri);
        moduleBean.setScope(scope);
        moduleBean.setRequired(required);
        moduleBean.setType(type);
        moduleBean.setUriPath(uriPath);
        moduleBean.setVersion(version);

        setResolved(true);
    }

    private String getRegistedModulesKey(DependencyNode node) {
        return getRegistedModulesKey(node.getGroupId(), node.getArtifactId(), node.getVersion(), node.getClassifier());
    }

    private String getRegistedModulesKey(String groupId, String artifactId, String version, String classifier) {
        return groupId + "/" + artifactId + "/" + version + "/" + classifier;
    }

    public List<String> getRuntimeIds() {
        return this.runtimeIds;
    }

    public void setRuntimeIds(List<String> runtimeIds) {
        this.runtimeIds = runtimeIds;
    }

    /**
     * Seems can't use existing jars, for example: <br/>
     * If we want to reuse jackson-jaxrs-1.8.8.jar, the existing mvn uri for it is:<br/>
     * mvn_uri="mvn:org.talend.libraries/jackson-jaxrs-1.8.8/6.0.0", <br/>
     * The following dependency will be added into pom.xml:<br/>
     * groupId: org.talend.libraries<br/>
     * artifactId: jackson-jaxrs<br/>
     * version: 1.8.8<br/>
     * <br>
     * While jackson-jaxrs-1.8.8.jar may be also used by other artifact. so the official dependency of
     * jackson-jaxrs-1.8.8.jar will be also added into pom.xml:<br>
     * groupId: org.codehaus.jackson<br/>
     * artifactId: jackson-jaxrs<br/>
     * version: 1.8.8<br/>
     * <br>
     * If in this case, studio can't get jar of org.codehaus.jackson/jackson-jaxrs<br/>
     * <br/>
     * So we need to forbid to reuse existing jars, Unless we disable offline option in studio maven configuration.
     */
    private boolean reuseExistingJars() {
        return false;
    }
}
