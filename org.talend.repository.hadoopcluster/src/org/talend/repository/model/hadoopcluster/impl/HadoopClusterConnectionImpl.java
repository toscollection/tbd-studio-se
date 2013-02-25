/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster.impl;

import java.util.Collection;
import java.util.Map;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EDataTypeUniqueEList;

import org.talend.core.model.metadata.builder.connection.impl.ConnectionImpl;

import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getDfVersion <em>Df Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isUseCustomVersion <em>Use Custom Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getServer <em>Server</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getNameNodeURI <em>Name Node URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getJobTrackerURI <em>Job Tracker URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#isEnableKerberos <em>Enable Kerberos</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getPrincipal <em>Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getGroup <em>Group</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getConnectionList <em>Connection List</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.impl.HadoopClusterConnectionImpl#getParameters <em>Parameters</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class HadoopClusterConnectionImpl extends ConnectionImpl implements HadoopClusterConnection {
    /**
     * The default value of the '{@link #getDistribution() <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistribution()
     * @generated
     * @ordered
     */
    protected static final String DISTRIBUTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDistribution() <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDistribution()
     * @generated
     * @ordered
     */
    protected String distribution = DISTRIBUTION_EDEFAULT;

    /**
     * The default value of the '{@link #getDfVersion() <em>Df Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDfVersion()
     * @generated
     * @ordered
     */
    protected static final String DF_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDfVersion() <em>Df Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDfVersion()
     * @generated
     * @ordered
     */
    protected String dfVersion = DF_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #isUseCustomVersion() <em>Use Custom Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseCustomVersion()
     * @generated
     * @ordered
     */
    protected static final boolean USE_CUSTOM_VERSION_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isUseCustomVersion() <em>Use Custom Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isUseCustomVersion()
     * @generated
     * @ordered
     */
    protected boolean useCustomVersion = USE_CUSTOM_VERSION_EDEFAULT;

    /**
     * The default value of the '{@link #getServer() <em>Server</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServer()
     * @generated
     * @ordered
     */
    protected static final String SERVER_EDEFAULT = "localhost";

    /**
     * The cached value of the '{@link #getServer() <em>Server</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getServer()
     * @generated
     * @ordered
     */
    protected String server = SERVER_EDEFAULT;

    /**
     * The default value of the '{@link #getNameNodeURI() <em>Name Node URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNameNodeURI()
     * @generated
     * @ordered
     */
    protected static final String NAME_NODE_URI_EDEFAULT = "hdfs://localhost:8020";

    /**
     * The cached value of the '{@link #getNameNodeURI() <em>Name Node URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getNameNodeURI()
     * @generated
     * @ordered
     */
    protected String nameNodeURI = NAME_NODE_URI_EDEFAULT;

    /**
     * The default value of the '{@link #getJobTrackerURI() <em>Job Tracker URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobTrackerURI()
     * @generated
     * @ordered
     */
    protected static final String JOB_TRACKER_URI_EDEFAULT = "localhost:50300";

    /**
     * The cached value of the '{@link #getJobTrackerURI() <em>Job Tracker URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getJobTrackerURI()
     * @generated
     * @ordered
     */
    protected String jobTrackerURI = JOB_TRACKER_URI_EDEFAULT;

    /**
     * The default value of the '{@link #isEnableKerberos() <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableKerberos()
     * @generated
     * @ordered
     */
    protected static final boolean ENABLE_KERBEROS_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isEnableKerberos() <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isEnableKerberos()
     * @generated
     * @ordered
     */
    protected boolean enableKerberos = ENABLE_KERBEROS_EDEFAULT;

    /**
     * The default value of the '{@link #getPrincipal() <em>Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrincipal()
     * @generated
     * @ordered
     */
    protected static final String PRINCIPAL_EDEFAULT = "nn/_HOST@EXAMPLE.COM";

    /**
     * The cached value of the '{@link #getPrincipal() <em>Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getPrincipal()
     * @generated
     * @ordered
     */
    protected String principal = PRINCIPAL_EDEFAULT;

    /**
     * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUserName()
     * @generated
     * @ordered
     */
    protected static final String USER_NAME_EDEFAULT = "anonymous";

    /**
     * The cached value of the '{@link #getUserName() <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUserName()
     * @generated
     * @ordered
     */
    protected String userName = USER_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getGroup() <em>Group</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected static final String GROUP_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getGroup() <em>Group</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getGroup()
     * @generated
     * @ordered
     */
    protected String group = GROUP_EDEFAULT;

    /**
     * The cached value of the '{@link #getConnectionList() <em>Connection List</em>}' attribute list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getConnectionList()
     * @generated
     * @ordered
     */
    protected EList<String> connectionList;

    /**
     * The cached value of the '{@link #getParameters() <em>Parameters</em>}' map.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getParameters()
     * @generated
     * @ordered
     */
    protected Map.Entry<String, String> parameters;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected HadoopClusterConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDistribution() {
        return distribution;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDistribution(String newDistribution) {
        String oldDistribution = distribution;
        distribution = newDistribution;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION, oldDistribution, distribution));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getDfVersion() {
        return dfVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setDfVersion(String newDfVersion) {
        String oldDfVersion = dfVersion;
        dfVersion = newDfVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION, oldDfVersion, dfVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isUseCustomVersion() {
        return useCustomVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUseCustomVersion(boolean newUseCustomVersion) {
        boolean oldUseCustomVersion = useCustomVersion;
        useCustomVersion = newUseCustomVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION, oldUseCustomVersion, useCustomVersion));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getServer() {
        return server;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setServer(String newServer) {
        String oldServer = server;
        server = newServer;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER, oldServer, server));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getNameNodeURI() {
        return nameNodeURI;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setNameNodeURI(String newNameNodeURI) {
        String oldNameNodeURI = nameNodeURI;
        nameNodeURI = newNameNodeURI;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI, oldNameNodeURI, nameNodeURI));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getJobTrackerURI() {
        return jobTrackerURI;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setJobTrackerURI(String newJobTrackerURI) {
        String oldJobTrackerURI = jobTrackerURI;
        jobTrackerURI = newJobTrackerURI;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI, oldJobTrackerURI, jobTrackerURI));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isEnableKerberos() {
        return enableKerberos;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setEnableKerberos(boolean newEnableKerberos) {
        boolean oldEnableKerberos = enableKerberos;
        enableKerberos = newEnableKerberos;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS, oldEnableKerberos, enableKerberos));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getPrincipal() {
        return principal;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPrincipal(String newPrincipal) {
        String oldPrincipal = principal;
        principal = newPrincipal;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL, oldPrincipal, principal));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setUserName(String newUserName) {
        String oldUserName = userName;
        userName = newUserName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME, oldUserName, userName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getGroup() {
        return group;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setGroup(String newGroup) {
        String oldGroup = group;
        group = newGroup;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP, oldGroup, group));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList<String> getConnectionList() {
        if (connectionList == null) {
            connectionList = new EDataTypeUniqueEList<String>(String.class, this, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST);
        }
        return connectionList;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Map.Entry<String, String> getParameters() {
        return parameters;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NotificationChain basicSetParameters(Map.Entry<String, String> newParameters, NotificationChain msgs) {
        Map.Entry<String, String> oldParameters = parameters;
        parameters = newParameters;
        if (eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS, oldParameters, newParameters);
            if (msgs == null) msgs = notification; else msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setParameters(Map.Entry<String, String> newParameters) {
        if (newParameters != parameters) {
            NotificationChain msgs = null;
            if (parameters != null)
                msgs = ((InternalEObject)parameters).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS, null, msgs);
            if (newParameters != null)
                msgs = ((InternalEObject)newParameters).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS, null, msgs);
            msgs = basicSetParameters(newParameters, msgs);
            if (msgs != null) msgs.dispatch();
        }
        else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS, newParameters, newParameters));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                return basicSetParameters(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                return getDistribution();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                return getDfVersion();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                return isUseCustomVersion();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                return getServer();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                return getNameNodeURI();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                return getJobTrackerURI();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                return isEnableKerberos();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                return getPrincipal();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                return getUserName();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                return getGroup();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                return getConnectionList();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                return getParameters();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                setDistribution((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                setDfVersion((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                setUseCustomVersion((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                setServer((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                setNameNodeURI((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                setJobTrackerURI((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos((Boolean)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                setPrincipal((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                setUserName((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                setGroup((String)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                getConnectionList().clear();
                getConnectionList().addAll((Collection<? extends String>)newValue);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                setParameters((Map.Entry<String, String>)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                setDistribution(DISTRIBUTION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                setDfVersion(DF_VERSION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                setUseCustomVersion(USE_CUSTOM_VERSION_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                setServer(SERVER_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                setNameNodeURI(NAME_NODE_URI_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                setJobTrackerURI(JOB_TRACKER_URI_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos(ENABLE_KERBEROS_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                setPrincipal(PRINCIPAL_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                setUserName(USER_NAME_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                setGroup(GROUP_EDEFAULT);
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                getConnectionList().clear();
                return;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                setParameters((Map.Entry<String, String>)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DISTRIBUTION:
                return DISTRIBUTION_EDEFAULT == null ? distribution != null : !DISTRIBUTION_EDEFAULT.equals(distribution);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__DF_VERSION:
                return DF_VERSION_EDEFAULT == null ? dfVersion != null : !DF_VERSION_EDEFAULT.equals(dfVersion);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION:
                return useCustomVersion != USE_CUSTOM_VERSION_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__SERVER:
                return SERVER_EDEFAULT == null ? server != null : !SERVER_EDEFAULT.equals(server);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI:
                return NAME_NODE_URI_EDEFAULT == null ? nameNodeURI != null : !NAME_NODE_URI_EDEFAULT.equals(nameNodeURI);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI:
                return JOB_TRACKER_URI_EDEFAULT == null ? jobTrackerURI != null : !JOB_TRACKER_URI_EDEFAULT.equals(jobTrackerURI);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS:
                return enableKerberos != ENABLE_KERBEROS_EDEFAULT;
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PRINCIPAL:
                return PRINCIPAL_EDEFAULT == null ? principal != null : !PRINCIPAL_EDEFAULT.equals(principal);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__USER_NAME:
                return USER_NAME_EDEFAULT == null ? userName != null : !USER_NAME_EDEFAULT.equals(userName);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__GROUP:
                return GROUP_EDEFAULT == null ? group != null : !GROUP_EDEFAULT.equals(group);
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST:
                return connectionList != null && !connectionList.isEmpty();
            case HadoopClusterPackage.HADOOP_CLUSTER_CONNECTION__PARAMETERS:
                return parameters != null;
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (distribution: ");
        result.append(distribution);
        result.append(", dfVersion: ");
        result.append(dfVersion);
        result.append(", useCustomVersion: ");
        result.append(useCustomVersion);
        result.append(", server: ");
        result.append(server);
        result.append(", nameNodeURI: ");
        result.append(nameNodeURI);
        result.append(", jobTrackerURI: ");
        result.append(jobTrackerURI);
        result.append(", enableKerberos: ");
        result.append(enableKerberos);
        result.append(", principal: ");
        result.append(principal);
        result.append(", userName: ");
        result.append(userName);
        result.append(", group: ");
        result.append(group);
        result.append(", connectionList: ");
        result.append(connectionList);
        result.append(')');
        return result.toString();
    }

} //HadoopClusterConnectionImpl
