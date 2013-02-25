/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;

import org.eclipse.emf.ecore.util.EObjectResolvingEList;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Input Table</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#isLookup <em>Lookup</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#getMatchingMode <em>Matching Mode</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#getLookupMode <em>Lookup Mode</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#isInnerJoin <em>Inner Join</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl#isPersistent <em>Persistent</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class InputTableImpl extends AbstractInOutTableImpl implements InputTable {
    /**
     * The default value of the '{@link #isLookup() <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLookup()
     * @generated
     * @ordered
     */
    protected static final boolean LOOKUP_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isLookup() <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isLookup()
     * @generated
     * @ordered
     */
    protected boolean lookup = LOOKUP_EDEFAULT;

    /**
     * The default value of the '{@link #getMatchingMode() <em>Matching Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMatchingMode()
     * @generated
     * @ordered
     */
    protected static final String MATCHING_MODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMatchingMode() <em>Matching Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getMatchingMode()
     * @generated
     * @ordered
     */
    protected String matchingMode = MATCHING_MODE_EDEFAULT;

    /**
     * The default value of the '{@link #getLookupMode() <em>Lookup Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLookupMode()
     * @generated
     * @ordered
     */
    protected static final String LOOKUP_MODE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLookupMode() <em>Lookup Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLookupMode()
     * @generated
     * @ordered
     */
    protected String lookupMode = LOOKUP_MODE_EDEFAULT;

    /**
     * The default value of the '{@link #isInnerJoin() <em>Inner Join</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isInnerJoin()
     * @generated
     * @ordered
     */
    protected static final boolean INNER_JOIN_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isInnerJoin() <em>Inner Join</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isInnerJoin()
     * @generated
     * @ordered
     */
    protected boolean innerJoin = INNER_JOIN_EDEFAULT;

    /**
     * The default value of the '{@link #isPersistent() <em>Persistent</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPersistent()
     * @generated
     * @ordered
     */
    protected static final boolean PERSISTENT_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isPersistent() <em>Persistent</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isPersistent()
     * @generated
     * @ordered
     */
    protected boolean persistent = PERSISTENT_EDEFAULT;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected InputTableImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.INPUT_TABLE;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isLookup() {
        return lookup;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLookup(boolean newLookup) {
        boolean oldLookup = lookup;
        lookup = newLookup;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__LOOKUP, oldLookup, lookup));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getMatchingMode() {
        return matchingMode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setMatchingMode(String newMatchingMode) {
        String oldMatchingMode = matchingMode;
        matchingMode = newMatchingMode;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__MATCHING_MODE, oldMatchingMode, matchingMode));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String getLookupMode() {
        return lookupMode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setLookupMode(String newLookupMode) {
        String oldLookupMode = lookupMode;
        lookupMode = newLookupMode;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__LOOKUP_MODE, oldLookupMode, lookupMode));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isInnerJoin() {
        return innerJoin;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setInnerJoin(boolean newInnerJoin) {
        boolean oldInnerJoin = innerJoin;
        innerJoin = newInnerJoin;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__INNER_JOIN, oldInnerJoin, innerJoin));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public boolean isPersistent() {
        return persistent;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void setPersistent(boolean newPersistent) {
        boolean oldPersistent = persistent;
        persistent = newPersistent;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.INPUT_TABLE__PERSISTENT, oldPersistent, persistent));
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                return isLookup();
            case PigmapPackage.INPUT_TABLE__MATCHING_MODE:
                return getMatchingMode();
            case PigmapPackage.INPUT_TABLE__LOOKUP_MODE:
                return getLookupMode();
            case PigmapPackage.INPUT_TABLE__INNER_JOIN:
                return isInnerJoin();
            case PigmapPackage.INPUT_TABLE__PERSISTENT:
                return isPersistent();
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
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                setLookup((Boolean)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__MATCHING_MODE:
                setMatchingMode((String)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__LOOKUP_MODE:
                setLookupMode((String)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__INNER_JOIN:
                setInnerJoin((Boolean)newValue);
                return;
            case PigmapPackage.INPUT_TABLE__PERSISTENT:
                setPersistent((Boolean)newValue);
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
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                setLookup(LOOKUP_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__MATCHING_MODE:
                setMatchingMode(MATCHING_MODE_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__LOOKUP_MODE:
                setLookupMode(LOOKUP_MODE_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__INNER_JOIN:
                setInnerJoin(INNER_JOIN_EDEFAULT);
                return;
            case PigmapPackage.INPUT_TABLE__PERSISTENT:
                setPersistent(PERSISTENT_EDEFAULT);
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
            case PigmapPackage.INPUT_TABLE__LOOKUP:
                return lookup != LOOKUP_EDEFAULT;
            case PigmapPackage.INPUT_TABLE__MATCHING_MODE:
                return MATCHING_MODE_EDEFAULT == null ? matchingMode != null : !MATCHING_MODE_EDEFAULT.equals(matchingMode);
            case PigmapPackage.INPUT_TABLE__LOOKUP_MODE:
                return LOOKUP_MODE_EDEFAULT == null ? lookupMode != null : !LOOKUP_MODE_EDEFAULT.equals(lookupMode);
            case PigmapPackage.INPUT_TABLE__INNER_JOIN:
                return innerJoin != INNER_JOIN_EDEFAULT;
            case PigmapPackage.INPUT_TABLE__PERSISTENT:
                return persistent != PERSISTENT_EDEFAULT;
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
        result.append(" (lookup: ");
        result.append(lookup);
        result.append(", matchingMode: ");
        result.append(matchingMode);
        result.append(", lookupMode: ");
        result.append(lookupMode);
        result.append(", innerJoin: ");
        result.append(innerJoin);
        result.append(", persistent: ");
        result.append(persistent);
        result.append(')');
        return result.toString();
    }

} //InputTableImpl
