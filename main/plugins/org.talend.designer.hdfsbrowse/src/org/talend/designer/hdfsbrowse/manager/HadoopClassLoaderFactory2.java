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
package org.talend.designer.hdfsbrowse.manager;

import java.net.MalformedURLException;
import java.util.Set;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.classloader.ClassLoaderFactory;
import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.hadoop.EHadoopCategory;
import org.talend.core.hadoop.EHadoopConfigurationJars;
import org.talend.designer.hdfsbrowse.constants.IHadoopArgs;

/**
 * 
 * created by ycbai on Aug 11, 2014 Detailled comment
 * 
 * <p>
 * TODO: Need to improve it and replace {@link HadoopClassLoaderFactory} with it after.
 * </p>
 *
 */
public class HadoopClassLoaderFactory2 {

    public static ClassLoader getHDFSClassLoader(String distribution, String version, boolean useKrb) {
        return getClassLoader(EHadoopCategory.HDFS, distribution, version, useKrb);
    }

    public static ClassLoader getMRClassLoader(String distribution, String version, boolean useKrb) {
        return getClassLoader(EHadoopCategory.MAP_REDUCE, distribution, version, useKrb);
    }

    public static ClassLoader getHiveEmbeddedClassLoader(String distribution, String version, boolean useKrb) {
        return getClassLoader(EHadoopCategory.HIVE, distribution, version, useKrb, IHadoopArgs.HIVE_ARG_EMBEDDED);
    }

    public static ClassLoader getHiveStandaloneClassLoader(String distribution, String version, boolean useKrb) {
        return getClassLoader(EHadoopCategory.HIVE, distribution, version, useKrb, IHadoopArgs.HIVE_ARG_STANDALONE);
    }

    public static ClassLoader getHadoopCustomClassLoader(String uid, Object customJars) {
        return HadoopClassLoaderFactory2.builder().withTypePrefix(EHadoopCategory.CUSTOM.getName()).withUid(uid)
                .build(customJars, true);
    }

    /**
     * DOC ycbai Comment method "builder".
     * 
     * <p>
     * Build the classloader with some options.
     * <p>
     * 
     * @return
     */
    public static HadoopClassLoaderFactory2.Builder builder() {
        return new HadoopClassLoaderFactory2.Builder();
    }

    /**
     * 
     * created by ycbai on Aug 11, 2014 Detailled comment
     *
     */
    public static class Builder {

        private static final String INDEX_SEP = ":"; //$NON-NLS-1$

        private StringBuffer indexBuffer = new StringBuffer();

        public Builder withTypePrefix(String typePrefix) {
            withArg(typePrefix);
            return this;
        }

        public Builder withDistribution(String distribution) {
            withArg(distribution);
            return this;
        }

        public Builder withVersion(String version) {
            withArg(version);
            return this;
        }

        public Builder withUid(String uid) {
            withArg(uid);
            return this;
        }

        public Builder withArg(String arg) {
            indexBuffer.append(INDEX_SEP).append(arg);
            return this;
        }

        public ClassLoader build() {
            return build(null, true);
        }

        public ClassLoader build(boolean showDownloadIfNotExist) {
            return build(null, showDownloadIfNotExist);
        }

        public ClassLoader build(Object extraArg, boolean showDownloadIfNotExist) {
            if (indexBuffer.length() > 0) { // Remove the first colon.
                indexBuffer.deleteCharAt(0);
            }
            return getClassLoader(indexBuffer.toString(), extraArg, showDownloadIfNotExist);
        }
    }

    public static ClassLoader getClassLoader(EHadoopCategory category, String distribution, String version, boolean useKrb,
            String... extraArgs) {
        Builder builder = HadoopClassLoaderFactory2.builder().withTypePrefix(category.getName()).withDistribution(distribution)
                .withVersion(version);
        if (extraArgs != null && extraArgs.length > 0) {
            for (String arg : extraArgs) {
                builder.withArg(arg);
            }
        }
        ClassLoader classLoader = builder.build();
        if (classLoader instanceof DynamicClassLoader && useKrb) {
            classLoader = createSecurityLoader(category, (DynamicClassLoader) classLoader);
        }

        return classLoader;
    }

    private static ClassLoader getClassLoader(String index, Object extraJars, boolean showDownloadIfNotExist) {
        ClassLoader loader = null;
        if (index.startsWith(EHadoopCategory.CUSTOM.getName())) {
            loader = getCustomClassLoader(index, extraJars, showDownloadIfNotExist);
        } else {
            loader = ClassLoaderFactory.getClassLoader(index, showDownloadIfNotExist);
        }
        if (loader == null) {
            loader = HadoopClassLoaderFactory2.class.getClassLoader();
        }

        return loader;
    }

    @SuppressWarnings("unchecked")
    private static ClassLoader getCustomClassLoader(String index, Object customJars, boolean showDownloadIfNotExist) {
        if (customJars instanceof Set) {
            return ClassLoaderFactory.getCustomClassLoader(index, (Set<String>) customJars);
        }

        return ClassLoaderFactory.getCustomClassLoader(index, String.valueOf(customJars));
    }

    private static DynamicClassLoader createSecurityLoader(EHadoopCategory category, DynamicClassLoader loader) {
        String[] securityJars;
        switch (category) {
        case HDFS:
            securityJars = EHadoopConfigurationJars.HDFS.getEnableSecurityJars();
            break;
        case MAP_REDUCE:
            securityJars = EHadoopConfigurationJars.MAP_REDUCE.getEnableSecurityJars();
            break;
        case HCATALOG:
            securityJars = EHadoopConfigurationJars.HCATALOG.getEnableSecurityJars();
            break;
        case HIVE:
            securityJars = EHadoopConfigurationJars.HIVE.getEnableSecurityJars();
            break;

        default:
            securityJars = new String[0];
            break;
        }

        DynamicClassLoader securityClassLoader = null;
        try {
            securityClassLoader = DynamicClassLoader.createNewOneBaseLoader(loader, securityJars, null);
        } catch (MalformedURLException e) {
            ExceptionHandler.process(e);
        }

        return securityClassLoader;
    }

}
