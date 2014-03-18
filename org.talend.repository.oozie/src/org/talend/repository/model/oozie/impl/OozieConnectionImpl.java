/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionImpl;

import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.model.oozie.OoziePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.repository.model.oozie.impl.OozieConnectionImpl#getUserName <em>User Name</em>}</li>
 *   <li>{@link org.talend.repository.model.oozie.impl.OozieConnectionImpl#getOozieEndPoind <em>Oozie End Poind</em>}</li>
 *   <li>{@link org.talend.repository.model.oozie.impl.OozieConnectionImpl#getOozieVersion <em>Oozie Version</em>}</li>
 *   <li>{@link org.talend.repository.model.oozie.impl.OozieConnectionImpl#isEnableKerberos <em>Enable Kerberos</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OozieConnectionImpl extends HadoopSubConnectionImpl implements OozieConnection {
    /**
     * The default value of the '{@link #getUserName() <em>User Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getUserName()
     * @generated
     * @ordered
     */
    protected static final String USER_NAME_EDEFAULT = null;

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
     * The default value of the '{@link #getOozieEndPoind() <em>Oozie End Poind</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOozieEndPoind()
     * @generated
     * @ordered
     */
    protected static final String OOZIE_END_POIND_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOozieEndPoind() <em>Oozie End Poind</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOozieEndPoind()
     * @generated
     * @ordered
     */
    protected String oozieEndPoind = OOZIE_END_POIND_EDEFAULT;

    /**
     * The default value of the '{@link #getOozieVersion() <em>Oozie Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOozieVersion()
     * @generated
     * @ordered
     */
    protected static final String OOZIE_VERSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getOozieVersion() <em>Oozie Version</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getOozieVersion()
     * @generated
     * @ordered
     */
    protected String oozieVersion = OOZIE_VERSION_EDEFAULT;

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
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OozieConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OoziePackage.Literals.OOZIE_CONNECTION;
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
            eNotify(new ENotificationImpl(this, Notification.SET, OoziePackage.OOZIE_CONNECTION__USER_NAME, oldUserName, userName));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOozieEndPoind() {
        return oozieEndPoind;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOozieEndPoind(String newOozieEndPoind) {
        String oldOozieEndPoind = oozieEndPoind;
        oozieEndPoind = newOozieEndPoind;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OoziePackage.OOZIE_CONNECTION__OOZIE_END_POIND, oldOozieEndPoind, oozieEndPoind));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getOozieVersion() {
        return oozieVersion;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setOozieVersion(String newOozieVersion) {
        String oldOozieVersion = oozieVersion;
        oozieVersion = newOozieVersion;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, OoziePackage.OOZIE_CONNECTION__OOZIE_VERSION, oldOozieVersion, oozieVersion));
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
            eNotify(new ENotificationImpl(this, Notification.SET, OoziePackage.OOZIE_CONNECTION__ENABLE_KERBEROS, oldEnableKerberos, enableKerberos));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case OoziePackage.OOZIE_CONNECTION__USER_NAME:
                return getUserName();
            case OoziePackage.OOZIE_CONNECTION__OOZIE_END_POIND:
                return getOozieEndPoind();
            case OoziePackage.OOZIE_CONNECTION__OOZIE_VERSION:
                return getOozieVersion();
            case OoziePackage.OOZIE_CONNECTION__ENABLE_KERBEROS:
                return isEnableKerberos();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case OoziePackage.OOZIE_CONNECTION__USER_NAME:
                setUserName((String)newValue);
                return;
            case OoziePackage.OOZIE_CONNECTION__OOZIE_END_POIND:
                setOozieEndPoind((String)newValue);
                return;
            case OoziePackage.OOZIE_CONNECTION__OOZIE_VERSION:
                setOozieVersion((String)newValue);
                return;
            case OoziePackage.OOZIE_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos((Boolean)newValue);
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
            case OoziePackage.OOZIE_CONNECTION__USER_NAME:
                setUserName(USER_NAME_EDEFAULT);
                return;
            case OoziePackage.OOZIE_CONNECTION__OOZIE_END_POIND:
                setOozieEndPoind(OOZIE_END_POIND_EDEFAULT);
                return;
            case OoziePackage.OOZIE_CONNECTION__OOZIE_VERSION:
                setOozieVersion(OOZIE_VERSION_EDEFAULT);
                return;
            case OoziePackage.OOZIE_CONNECTION__ENABLE_KERBEROS:
                setEnableKerberos(ENABLE_KERBEROS_EDEFAULT);
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
            case OoziePackage.OOZIE_CONNECTION__USER_NAME:
                return USER_NAME_EDEFAULT == null ? userName != null : !USER_NAME_EDEFAULT.equals(userName);
            case OoziePackage.OOZIE_CONNECTION__OOZIE_END_POIND:
                return OOZIE_END_POIND_EDEFAULT == null ? oozieEndPoind != null : !OOZIE_END_POIND_EDEFAULT.equals(oozieEndPoind);
            case OoziePackage.OOZIE_CONNECTION__OOZIE_VERSION:
                return OOZIE_VERSION_EDEFAULT == null ? oozieVersion != null : !OOZIE_VERSION_EDEFAULT.equals(oozieVersion);
            case OoziePackage.OOZIE_CONNECTION__ENABLE_KERBEROS:
                return enableKerberos != ENABLE_KERBEROS_EDEFAULT;
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
        result.append(" (userName: ");
        result.append(userName);
        result.append(", oozieEndPoind: ");
        result.append(oozieEndPoind);
        result.append(", oozieVersion: ");
        result.append(oozieVersion);
        result.append(", enableKerberos: ");
        result.append(enableKerberos);
        result.append(')');
        return result.toString();
    }

} //OozieConnectionImpl
