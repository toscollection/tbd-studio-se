// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.utils;

import org.apache.commons.lang.StringUtils;
import org.talend.core.CorePlugin;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess2;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.designer.hdfsbrowse.manager.HadoopClassLoaderFactory;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class OozieClassLoaderFactory {

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = null;

        boolean isFromRepository = false;
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter oozieConnIdParameter = process.getElementParameter(EOozieParameterName.REPOSITORY_CONNECTION_ID
                .getName());
        if (oozieConnIdParameter != null) {
            String oozieConnId = (String) oozieConnIdParameter.getValue();
            if (StringUtils.isNotEmpty(oozieConnId)) {
                isFromRepository = true;
            }
        }

        if (isFromRepository) {// get parameters from repository.

        } else { // get parameters from preference.
            String distributionValue = getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION);
            String versionValue = getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION);
            classLoader = HadoopClassLoaderFactory.getClassLoader(distributionValue, versionValue);
        }

        if (classLoader == null) {
            classLoader = OozieClassLoaderFactory.class.getClassLoader();
        }

        return classLoader;
    }

    private static String getParamValueFromPreference(String prefKey) {
        String versionValue = CorePlugin.getDefault().getPreferenceStore().getString(prefKey);
        return versionValue;
    }

}
