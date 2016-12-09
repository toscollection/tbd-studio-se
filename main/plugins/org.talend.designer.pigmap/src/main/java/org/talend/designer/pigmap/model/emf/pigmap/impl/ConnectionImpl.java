/**
 * <copyright> </copyright>
 * 
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.NodeType;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Connection</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl#getSource <em>Source</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConnectionImpl extends EObjectImpl implements Connection {

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSource() <em>Source</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getSource()
     * @generated
     * @ordered
     */
    protected AbstractNode source;

    /**
     * The cached value of the '{@link #getTarget() <em>Target</em>}' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getTarget()
     * @generated
     * @ordered
     */
    protected AbstractNode target;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ConnectionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return PigmapPackage.Literals.CONNECTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated NOT
     */
    public String getName() {
        if (eContainer() != null && eContainer() instanceof PigMapData) {
            PigMapData mapData = (PigMapData) eContainer();
            String nodeName = "";
            if (mapData.eContainer() instanceof NodeType) {
                NodeType nodeType = (NodeType) mapData.eContainer();
                for (Object objectParam : nodeType.getElementParameter()) {
                    if (objectParam instanceof ElementParameterType) {
                        ElementParameterType elemParam = (ElementParameterType) objectParam;
                        if (elemParam.getValue() != null) {
                            if ("UNIQUE_NAME".equals(elemParam.getName()) || elemParam.getName() == null) {
                                // note: after convert to the jobscript, the element parameter name for UNIQUE_NAME is
                                // lost.
                                // unique name is then the first parameter usually, and with name = null.
                                // similar code done in CreateXtextProcessService.checkElementParameterType
                                nodeName = elemParam.getValue() + "_";
                                break;
                            }
                        }
                    }
                }
            }
            return nodeName + "Connection_" + mapData.getConnections().indexOf(this);
        }
        return null;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AbstractNode getSource() {
        return source;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setSource(AbstractNode newSource) {
        AbstractNode oldSource = source;
        source = newSource;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.CONNECTION__SOURCE, oldSource, source));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public AbstractNode getTarget() {
        return target;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setTarget(AbstractNode newTarget) {
        AbstractNode oldTarget = target;
        target = newTarget;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, PigmapPackage.CONNECTION__TARGET, oldTarget, target));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case PigmapPackage.CONNECTION__NAME:
                return getName();
            case PigmapPackage.CONNECTION__SOURCE:
                return getSource();
            case PigmapPackage.CONNECTION__TARGET:
                return getTarget();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case PigmapPackage.CONNECTION__SOURCE:
                setSource((AbstractNode)newValue);
                return;
            case PigmapPackage.CONNECTION__TARGET:
                setTarget((AbstractNode)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case PigmapPackage.CONNECTION__SOURCE:
                setSource((AbstractNode)null);
                return;
            case PigmapPackage.CONNECTION__TARGET:
                setTarget((AbstractNode)null);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case PigmapPackage.CONNECTION__NAME:
                return NAME_EDEFAULT == null ? getName() != null : !NAME_EDEFAULT.equals(getName());
            case PigmapPackage.CONNECTION__SOURCE:
                return source != null;
            case PigmapPackage.CONNECTION__TARGET:
                return target != null;
        }
        return super.eIsSet(featureID);
    }

} // ConnectionImpl
