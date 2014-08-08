/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster;

import java.util.Map;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.common.util.EMap;
import org.talend.core.model.metadata.builder.connection.Connection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDistribution <em>Distribution</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDfVersion <em>Df Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomVersion <em>Use Custom Version</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseYarn <em>Use Yarn</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getServer <em>Server</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getNameNodeURI <em>Name Node URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobTrackerURI <em>Job Tracker URI</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableKerberos <em>Enable Kerberos</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getPrincipal <em>Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getGroup <em>Group</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getAuthMode <em>Auth Mode</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getConnectionList <em>Connection List</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getParameters <em>Parameters</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseKeytab <em>Use Keytab</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytabPrincipal <em>Keytab Principal</em>}</li>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytab <em>Keytab</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection()
 * @model
 * @generated
 */
public interface HadoopClusterConnection extends Connection {
    /**
     * Returns the value of the '<em><b>Distribution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Distribution</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Distribution</em>' attribute.
     * @see #setDistribution(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Distribution()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getDistribution();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDistribution <em>Distribution</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Distribution</em>' attribute.
     * @see #getDistribution()
     * @generated
     */
    void setDistribution(String value);

    /**
     * Returns the value of the '<em><b>Df Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Df Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Df Version</em>' attribute.
     * @see #setDfVersion(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_DfVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getDfVersion();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getDfVersion <em>Df Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Df Version</em>' attribute.
     * @see #getDfVersion()
     * @generated
     */
    void setDfVersion(String value);

    /**
     * Returns the value of the '<em><b>Use Custom Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Custom Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Custom Version</em>' attribute.
     * @see #setUseCustomVersion(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseCustomVersion()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseCustomVersion();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseCustomVersion <em>Use Custom Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Custom Version</em>' attribute.
     * @see #isUseCustomVersion()
     * @generated
     */
    void setUseCustomVersion(boolean value);

    /**
     * Returns the value of the '<em><b>Use Yarn</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Yarn</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Yarn</em>' attribute.
     * @see #setUseYarn(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseYarn()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseYarn();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseYarn <em>Use Yarn</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Yarn</em>' attribute.
     * @see #isUseYarn()
     * @generated
     */
    void setUseYarn(boolean value);

    /**
     * Returns the value of the '<em><b>Server</b></em>' attribute.
     * The default value is <code>"localhost"</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Server</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Server</em>' attribute.
     * @see #setServer(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Server()
     * @model default="localhost" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getServer();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getServer <em>Server</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Server</em>' attribute.
     * @see #getServer()
     * @generated
     */
    void setServer(String value);

    /**
     * Returns the value of the '<em><b>Name Node URI</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name Node URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name Node URI</em>' attribute.
     * @see #setNameNodeURI(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_NameNodeURI()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getNameNodeURI();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getNameNodeURI <em>Name Node URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name Node URI</em>' attribute.
     * @see #getNameNodeURI()
     * @generated
     */
    void setNameNodeURI(String value);

    /**
     * Returns the value of the '<em><b>Job Tracker URI</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Job Tracker URI</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Job Tracker URI</em>' attribute.
     * @see #setJobTrackerURI(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_JobTrackerURI()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getJobTrackerURI();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getJobTrackerURI <em>Job Tracker URI</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Job Tracker URI</em>' attribute.
     * @see #getJobTrackerURI()
     * @generated
     */
    void setJobTrackerURI(String value);

    /**
     * Returns the value of the '<em><b>Enable Kerberos</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Enable Kerberos</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Enable Kerberos</em>' attribute.
     * @see #setEnableKerberos(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_EnableKerberos()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isEnableKerberos();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isEnableKerberos <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Kerberos</em>' attribute.
     * @see #isEnableKerberos()
     * @generated
     */
    void setEnableKerberos(boolean value);

    /**
     * Returns the value of the '<em><b>Principal</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Principal</em>' attribute.
     * @see #setPrincipal(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Principal()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getPrincipal <em>Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Principal</em>' attribute.
     * @see #getPrincipal()
     * @generated
     */
    void setPrincipal(String value);

    /**
     * Returns the value of the '<em><b>User Name</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>User Name</em>' attribute.
     * @see #setUserName(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UserName()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getUserName();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getUserName <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>User Name</em>' attribute.
     * @see #getUserName()
     * @generated
     */
    void setUserName(String value);

    /**
     * Returns the value of the '<em><b>Group</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Group</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Group</em>' attribute.
     * @see #setGroup(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Group()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getGroup();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getGroup <em>Group</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Group</em>' attribute.
     * @see #getGroup()
     * @generated
     */
    void setGroup(String value);

    /**
     * Returns the value of the '<em><b>Auth Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Auth Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Auth Mode</em>' attribute.
     * @see #setAuthMode(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_AuthMode()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getAuthMode();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getAuthMode <em>Auth Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Auth Mode</em>' attribute.
     * @see #getAuthMode()
     * @generated
     */
    void setAuthMode(String value);

    /**
     * Returns the value of the '<em><b>Connection List</b></em>' attribute list.
     * The list contents are of type {@link java.lang.String}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection List</em>' attribute list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection List</em>' attribute list.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_ConnectionList()
     * @model
     * @generated
     */
    EList<String> getConnectionList();

    /**
     * Returns the value of the '<em><b>Parameters</b></em>' map.
     * The key is of type {@link java.lang.String},
     * and the value is of type {@link java.lang.String},
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Parameters</em>' map isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Parameters</em>' map.
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Parameters()
     * @model mapType="org.talend.repository.model.hadoopcluster.HadoopAdditionalProperties<org.eclipse.emf.ecore.xml.type.String, org.eclipse.emf.ecore.xml.type.String>"
     * @generated
     */
    EMap<String, String> getParameters();

    /**
     * Returns the value of the '<em><b>Use Keytab</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Use Keytab</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Use Keytab</em>' attribute.
     * @see #setUseKeytab(boolean)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_UseKeytab()
     * @model dataType="org.eclipse.emf.ecore.xml.type.Boolean"
     * @generated
     */
    boolean isUseKeytab();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#isUseKeytab <em>Use Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Use Keytab</em>' attribute.
     * @see #isUseKeytab()
     * @generated
     */
    void setUseKeytab(boolean value);

    /**
     * Returns the value of the '<em><b>Keytab Principal</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Keytab Principal</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Keytab Principal</em>' attribute.
     * @see #setKeytabPrincipal(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_KeytabPrincipal()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getKeytabPrincipal();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytabPrincipal <em>Keytab Principal</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Keytab Principal</em>' attribute.
     * @see #getKeytabPrincipal()
     * @generated
     */
    void setKeytabPrincipal(String value);

    /**
     * Returns the value of the '<em><b>Keytab</b></em>' attribute.
     * The default value is <code>""</code>.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Keytab</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Keytab</em>' attribute.
     * @see #setKeytab(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopClusterConnection_Keytab()
     * @model default="" dataType="org.eclipse.emf.ecore.xml.type.String"
     * @generated
     */
    String getKeytab();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopClusterConnection#getKeytab <em>Keytab</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Keytab</em>' attribute.
     * @see #getKeytab()
     * @generated
     */
    void setKeytab(String value);

} // HadoopClusterConnection
