/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie;

import org.talend.repository.model.hadoopcluster.HadoopSubConnection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.repository.model.oozie.OozieConnection#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.oozie.OozieConnection#getOozieEndPoind <em>Oozie End Poind</em>}</li>
 *   <li>{@link org.talend.repository.model.oozie.OozieConnection#getOozieVersion <em>Oozie Version</em>}</li>
 *   <li>{@link org.talend.repository.model.oozie.OozieConnection#isEnableKerberos <em>Enable Kerberos</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.repository.model.oozie.OoziePackage#getOozieConnection()
 * @model
 * @generated
 */
public interface OozieConnection extends HadoopSubConnection {
    /**
     * Returns the value of the '<em><b>User Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>User Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>User Name</em>' attribute.
     * @see #setUserName(String)
     * @see org.talend.repository.model.oozie.OoziePackage#getOozieConnection_UserName()
     * @model
     * @generated
     */
    String getUserName();

    /**
     * Sets the value of the '{@link org.talend.repository.model.oozie.OozieConnection#getUserName <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>User Name</em>' attribute.
     * @see #getUserName()
     * @generated
     */
    void setUserName(String value);

    /**
     * Returns the value of the '<em><b>Oozie End Poind</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Oozie End Poind</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Oozie End Poind</em>' attribute.
     * @see #setOozieEndPoind(String)
     * @see org.talend.repository.model.oozie.OoziePackage#getOozieConnection_OozieEndPoind()
     * @model
     * @generated
     */
    String getOozieEndPoind();

    /**
     * Sets the value of the '{@link org.talend.repository.model.oozie.OozieConnection#getOozieEndPoind <em>Oozie End Poind</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Oozie End Poind</em>' attribute.
     * @see #getOozieEndPoind()
     * @generated
     */
    void setOozieEndPoind(String value);

    /**
     * Returns the value of the '<em><b>Oozie Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Oozie Version</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Oozie Version</em>' attribute.
     * @see #setOozieVersion(String)
     * @see org.talend.repository.model.oozie.OoziePackage#getOozieConnection_OozieVersion()
     * @model
     * @generated
     */
    String getOozieVersion();

    /**
     * Sets the value of the '{@link org.talend.repository.model.oozie.OozieConnection#getOozieVersion <em>Oozie Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Oozie Version</em>' attribute.
     * @see #getOozieVersion()
     * @generated
     */
    void setOozieVersion(String value);

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
     * @see org.talend.repository.model.oozie.OoziePackage#getOozieConnection_EnableKerberos()
     * @model
     * @generated
     */
    boolean isEnableKerberos();

    /**
     * Sets the value of the '{@link org.talend.repository.model.oozie.OozieConnection#isEnableKerberos <em>Enable Kerberos</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Enable Kerberos</em>' attribute.
     * @see #isEnableKerberos()
     * @generated
     */
    void setEnableKerberos(boolean value);

} // OozieConnection
