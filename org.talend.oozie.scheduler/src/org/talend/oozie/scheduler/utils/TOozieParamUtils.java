// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
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

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.hadoop.IHadoopClusterService;
import org.talend.core.hadoop.IOozieService;
import org.talend.core.model.metadata.builder.connection.Connection;
import org.talend.core.model.process.IElementParameter;
import org.talend.core.model.process.IProcess2;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.designer.core.model.components.EOozieParameterName;
import org.talend.oozie.scheduler.views.OozieJobTrackerListener;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * DOC plv class global comment. Detailed comment
 */
public class TOozieParamUtils {

    /**
     * DOC plv Comment method "getRepositoryOozieParam". Get oozie param from repository
     * 
     * @return OozieParam
     */
    public static Map<String, String> getRepositoryOozieParam() {
        IProcess2 process = OozieJobTrackerListener.getProcess();
        IElementParameter elementParameter = process.getElementParameter(EOozieParameterName.REPOSITORY_CONNECTION_ID.getName());
        String id = (String) elementParameter.getValue();
        Connection repositoryConnection = getOozieConnectionById(id);
        if (repositoryConnection != null && GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IOozieService service = (IOozieService) GlobalServiceRegister.getDefault().getService(IOozieService.class);
            return service.getOozieParamFromConnection(repositoryConnection);
        } else {
            return new HashMap<String, String>();
        }
    }

    public static Map<String, String> getRepositoryOozieParam(String id) {
        Connection repositoryConnection = getOozieConnectionById(id);
        if (repositoryConnection != null && GlobalServiceRegister.getDefault().isServiceRegistered(IHadoopClusterService.class)) {
            IOozieService service = (IOozieService) GlobalServiceRegister.getDefault().getService(IOozieService.class);
            return service.getOozieParamFromConnection(repositoryConnection);
        } else {
            return new HashMap<String, String>();
        }
    }

    public static Connection getOozieConnectionById(String id) {
        Connection repositoryConnection = null;
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        Item item = null;
        try {
            IRepositoryViewObject repobj = factory.getLastVersion(id);
            if (repobj != null) {
                Property property = repobj.getProperty();
                if (property != null) {
                    item = property.getItem();
                }
            }
        } catch (PersistenceException e) {
            ExceptionHandler.process(e);
        }
        if (item != null) {
            if (item instanceof ConnectionItem) {
                repositoryConnection = ((ConnectionItem) item).getConnection();
            }
        }
        return repositoryConnection;
    }

    public static boolean isFromRepository() {
        boolean isFromRepository = false;
        if (!GlobalServiceRegister.getDefault().isServiceRegistered(IOozieService.class)) {
            return isFromRepository;
        }
        IProcess2 process = OozieJobTrackerListener.getProcess();
        if (process != null) {
            IElementParameter oozieConnIdParameter = process.getElementParameter(EOozieParameterName.REPOSITORY_CONNECTION_ID
                    .getName());
            if (oozieConnIdParameter != null) {
                String oozieConnId = (String) oozieConnIdParameter.getValue();
                if (StringUtils.isNotEmpty(oozieConnId)) {
                    isFromRepository = true;
                }
            }
        }
        return isFromRepository;
    }

    public static String getParamValue(String prefKey) {
        String value = "";
        if (isFromRepository()) {
            value = getParamValueFromRepository(prefKey);
        } else {
            value = getParamValueFromPreference(prefKey);
        }
        return value != null ? value : "";
    }

    public static String getParamValueFromPreference(String prefKey) {
        String versionValue = CorePlugin.getDefault().getPreferenceStore().getString(prefKey);
        return versionValue;
    }

    public static String getParamValueFromRepository(String prefKey) {
        String versionValue = TOozieParamUtils.getRepositoryOozieParam().get(prefKey);
        return versionValue;
    }

    public static String getParamValueFromRepositoryById(String prefKey, String id) {
        String versionValue = TOozieParamUtils.getRepositoryOozieParam(id).get(prefKey);
        return versionValue;
    }

    public static String getNameNode() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT);
    }

    public static String getJobTracker() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT);
    }

    public static String getOozieEndPoint() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT);
    }

    public static String getUserNameForHadoop() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME);
    }

    public static String getHadoopDistribution() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION);
    }

    public static String getHadoopVersion() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION);
    }

    public static String getHadoopCustomJars() {
        return getParamValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS);
    }

}
