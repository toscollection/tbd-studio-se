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
package org.talend.designer.hdfsbrowse.hadoop.service.check;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.classloader.DynamicClassLoader;
import org.talend.core.utils.ReflectionUtils;
import org.talend.designer.hdfsbrowse.exceptions.HadoopServerException;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterUtil;

/**
 * created by ycbai on Aug 14, 2014 Detailled comment
 *
 */
public abstract class AbstractCheckedServiceProvider implements ICheckedServiceProvider {

    @Override
    public boolean checkService(final HadoopServiceProperties serviceProperties, final int timeout) throws HadoopServerException {
        boolean checkedOK = true;

        ClassLoader classLoader = getClassLoader(serviceProperties);
        try {
            Thread.currentThread().setContextClassLoader(classLoader);
            ICheckedWorkUnit workUnit = new CheckedWorkUnit() {

                @Override
                protected Object run(ClassLoader cl) throws Exception {
                    return check(serviceProperties, cl);
                }
            };
            workUnit.setTimeout(timeout);
            workUnit.setClassLoader(classLoader);
            workUnit.execute();
        } catch (Exception e) {
            checkedOK = false;
            throw new HadoopServerException(e);
        }

        return checkedOK;
    }

    protected abstract Object check(final HadoopServiceProperties serviceProperties, final ClassLoader classLoader)
            throws Exception;

    protected abstract ClassLoader getClassLoader(HadoopServiceProperties serviceProperties);

    protected void setHadoopProperties(Object conf, HadoopServiceProperties serviceProperties) throws Exception {
        List<Map<String, Object>> hadoopProperties = serviceProperties.getHadoopProperties();
        if (hadoopProperties != null) {
            for (Map<String, Object> propMap : hadoopProperties) {
                String key = String.valueOf(propMap.get("PROPERTY")); //$NON-NLS-1$
                String value = String.valueOf(propMap.get("VALUE")); //$NON-NLS-1$
                ReflectionUtils.invokeMethod(conf, "set", new Object[] { key, value }); //$NON-NLS-1$
            }
        }
    }

    protected ClassLoader addCustomConfsJarIfNeeded(ClassLoader baseLoader, HadoopServiceProperties serviceProperties) {
        ClassLoader classLoader = baseLoader;
        if (serviceProperties.isUseCustomConfs()) {
            String clusterId = serviceProperties.getRelativeHadoopClusterId();
            if (baseLoader instanceof DynamicClassLoader && clusterId != null) {
                String customConfsJarName = HadoopParameterUtil.getConfsJarDefaultName(clusterId);
                try {
                    classLoader = DynamicClassLoader.createNewOneBaseLoader((DynamicClassLoader) baseLoader,
                            new String[] { customConfsJarName }, null);
                } catch (MalformedURLException e) {
                    ExceptionHandler.process(e);
                }
            }
        }
        return classLoader;
    }

}
