/**
 * <copyright> </copyright>
 * 
 * $Id: ConnectionTypeImpl.java 5672 2007-09-18 10:45:52Z ftang $
 */
package org.talend.designer.core.model.utils.emf.talendfile.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.talend.designer.core.model.AbstractConnection;
import org.talend.designer.core.model.utils.emf.talendfile.ConnectionType;
import org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType;
import org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Connection Type</b></em>'.
 * 
 * @extend AbstractConnection <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getElementParameter <em>
 * Element Parameter</em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getConnectorName <em>Connector
 * Name</em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getLabel <em>Label</em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getConnectionStyle <em>
 * Connection Style</em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getMergeOrder <em>Merge Order
 * </em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getMetaname <em>Metaname</em>}
 * </li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getOffsetLabelX <em>Offset
 * Label X</em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getOffsetLabelY <em>Offset
 * Label Y</em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getOutputId <em>Output Id
 * </em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getSourceName <em>Source Name
 * </em>}</li>
 * <li>{@link org.talend.designer.core.model.utils.emf.talendfile.impl.ConnectionTypeImpl#getTargetName <em>Target Name
 * </em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ConnectionTypeImpl extends AbstractConnection implements ConnectionType {

    /**
     * The cached value of the '{@link #getElementParameter() <em>Element Parameter</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getElementParameter()
     * @generated
     * @ordered
     */
    protected EList elementParameter;

    /**
     * The default value of the '{@link #getConnectorName() <em>Connector Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getConnectorName()
     * @generated
     * @ordered
     */
    protected static final String CONNECTOR_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getConnectorName() <em>Connector Name</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getConnectorName()
     * @generated
     * @ordered
     */
    protected String connectorName = CONNECTOR_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getLabel() <em>Label</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected static final String LABEL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getLabel() <em>Label</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getLabel()
     * @generated
     * @ordered
     */
    protected String label = LABEL_EDEFAULT;

    /**
     * The default value of the '{@link #getConnectionStyle() <em>Connection Style</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getConnectionStyle()
     * @generated
     * @ordered
     */
    protected static final int CONNECTION_STYLE_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getConnectionStyle() <em>Connection Style</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * 
     * @see #getConnectionStyle()
     * @generated
     * @ordered
     */
    protected int connectionStyle = CONNECTION_STYLE_EDEFAULT;

    /**
     * This is true if the Connection Style attribute has been set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean connectionStyleESet;

    /**
     * The default value of the '{@link #getMergeOrder() <em>Merge Order</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getMergeOrder()
     * @generated
     * @ordered
     */
    protected static final int MERGE_ORDER_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getMergeOrder() <em>Merge Order</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getMergeOrder()
     * @generated
     * @ordered
     */
    protected int mergeOrder = MERGE_ORDER_EDEFAULT;

    /**
     * This is true if the Merge Order attribute has been set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean mergeOrderESet;

    /**
     * The default value of the '{@link #getMetaname() <em>Metaname</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getMetaname()
     * @generated
     * @ordered
     */
    protected static final String METANAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getMetaname() <em>Metaname</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getMetaname()
     * @generated
     * @ordered
     */
    protected String metaname = METANAME_EDEFAULT;

    /**
     * The default value of the '{@link #getOffsetLabelX() <em>Offset Label X</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getOffsetLabelX()
     * @generated
     * @ordered
     */
    protected static final int OFFSET_LABEL_X_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOffsetLabelX() <em>Offset Label X</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getOffsetLabelX()
     * @generated
     * @ordered
     */
    protected int offsetLabelX = OFFSET_LABEL_X_EDEFAULT;

    /**
     * This is true if the Offset Label X attribute has been set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean offsetLabelXESet;

    /**
     * The default value of the '{@link #getOffsetLabelY() <em>Offset Label Y</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getOffsetLabelY()
     * @generated
     * @ordered
     */
    protected static final int OFFSET_LABEL_Y_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOffsetLabelY() <em>Offset Label Y</em>}' attribute. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * 
     * @see #getOffsetLabelY()
     * @generated
     * @ordered
     */
    protected int offsetLabelY = OFFSET_LABEL_Y_EDEFAULT;

    /**
     * This is true if the Offset Label Y attribute has been set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean offsetLabelYESet;

    /**
     * The default value of the '{@link #getOutputId() <em>Output Id</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getOutputId()
     * @generated
     * @ordered
     */
    protected static final int OUTPUT_ID_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getOutputId() <em>Output Id</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getOutputId()
     * @generated
     * @ordered
     */
    protected int outputId = OUTPUT_ID_EDEFAULT;

    /**
     * This is true if the Output Id attribute has been set. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     * @ordered
     */
    protected boolean outputIdESet;

    /**
     * The default value of the '{@link #getSourceName() <em>Source Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getSourceName()
     * @generated
     * @ordered
     */
    protected static final String SOURCE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSourceName() <em>Source Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getSourceName()
     * @generated
     * @ordered
     */
    protected String sourceName = SOURCE_NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getTargetName() <em>Target Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getTargetName()
     * @generated
     * @ordered
     */
    protected static final String TARGET_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getTargetName() <em>Target Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getTargetName()
     * @generated
     * @ordered
     */
    protected String targetName = TARGET_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected ConnectionTypeImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected EClass eStaticClass() {
        return TalendFilePackage.Literals.CONNECTION_TYPE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public EList getElementParameter() {
        if (elementParameter == null) {
            elementParameter = new EObjectContainmentEList(ElementParameterType.class, this,
                    TalendFilePackage.CONNECTION_TYPE__ELEMENT_PARAMETER);
        }
        return elementParameter;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getConnectorName() {
        return connectorName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setConnectorName(String newConnectorName) {
        String oldConnectorName = connectorName;
        connectorName = newConnectorName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__CONNECTOR_NAME,
                    oldConnectorName, connectorName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getLabel() {
        return label;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setLabel(String newLabel) {
        String oldLabel = label;
        label = newLabel;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__LABEL, oldLabel, label));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getConnectionStyle() {
        return connectionStyle;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setConnectionStyle(int newConnectionStyle) {
        int oldConnectionStyle = connectionStyle;
        connectionStyle = newConnectionStyle;
        boolean oldConnectionStyleESet = connectionStyleESet;
        connectionStyleESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__CONNECTION_STYLE,
                    oldConnectionStyle, connectionStyle, !oldConnectionStyleESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetConnectionStyle() {
        int oldConnectionStyle = connectionStyle;
        boolean oldConnectionStyleESet = connectionStyleESet;
        connectionStyle = CONNECTION_STYLE_EDEFAULT;
        connectionStyleESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, TalendFilePackage.CONNECTION_TYPE__CONNECTION_STYLE,
                    oldConnectionStyle, CONNECTION_STYLE_EDEFAULT, oldConnectionStyleESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetConnectionStyle() {
        return connectionStyleESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getMergeOrder() {
        return mergeOrder;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setMergeOrder(int newMergeOrder) {
        int oldMergeOrder = mergeOrder;
        mergeOrder = newMergeOrder;
        boolean oldMergeOrderESet = mergeOrderESet;
        mergeOrderESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__MERGE_ORDER, oldMergeOrder,
                    mergeOrder, !oldMergeOrderESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetMergeOrder() {
        int oldMergeOrder = mergeOrder;
        boolean oldMergeOrderESet = mergeOrderESet;
        mergeOrder = MERGE_ORDER_EDEFAULT;
        mergeOrderESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, TalendFilePackage.CONNECTION_TYPE__MERGE_ORDER,
                    oldMergeOrder, MERGE_ORDER_EDEFAULT, oldMergeOrderESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetMergeOrder() {
        return mergeOrderESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getMetaname() {
        return metaname;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setMetaname(String newMetaname) {
        String oldMetaname = metaname;
        metaname = newMetaname;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__METANAME, oldMetaname,
                    metaname));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getOffsetLabelX() {
        return offsetLabelX;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setOffsetLabelX(int newOffsetLabelX) {
        int oldOffsetLabelX = offsetLabelX;
        offsetLabelX = newOffsetLabelX;
        boolean oldOffsetLabelXESet = offsetLabelXESet;
        offsetLabelXESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_X,
                    oldOffsetLabelX, offsetLabelX, !oldOffsetLabelXESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetOffsetLabelX() {
        int oldOffsetLabelX = offsetLabelX;
        boolean oldOffsetLabelXESet = offsetLabelXESet;
        offsetLabelX = OFFSET_LABEL_X_EDEFAULT;
        offsetLabelXESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_X,
                    oldOffsetLabelX, OFFSET_LABEL_X_EDEFAULT, oldOffsetLabelXESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetOffsetLabelX() {
        return offsetLabelXESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getOffsetLabelY() {
        return offsetLabelY;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setOffsetLabelY(int newOffsetLabelY) {
        int oldOffsetLabelY = offsetLabelY;
        offsetLabelY = newOffsetLabelY;
        boolean oldOffsetLabelYESet = offsetLabelYESet;
        offsetLabelYESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_Y,
                    oldOffsetLabelY, offsetLabelY, !oldOffsetLabelYESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetOffsetLabelY() {
        int oldOffsetLabelY = offsetLabelY;
        boolean oldOffsetLabelYESet = offsetLabelYESet;
        offsetLabelY = OFFSET_LABEL_Y_EDEFAULT;
        offsetLabelYESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_Y,
                    oldOffsetLabelY, OFFSET_LABEL_Y_EDEFAULT, oldOffsetLabelYESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetOffsetLabelY() {
        return offsetLabelYESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getOutputId() {
        return outputId;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setOutputId(int newOutputId) {
        int oldOutputId = outputId;
        outputId = newOutputId;
        boolean oldOutputIdESet = outputIdESet;
        outputIdESet = true;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__OUTPUT_ID, oldOutputId,
                    outputId, !oldOutputIdESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void unsetOutputId() {
        int oldOutputId = outputId;
        boolean oldOutputIdESet = outputIdESet;
        outputId = OUTPUT_ID_EDEFAULT;
        outputIdESet = false;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.UNSET, TalendFilePackage.CONNECTION_TYPE__OUTPUT_ID, oldOutputId,
                    OUTPUT_ID_EDEFAULT, oldOutputIdESet));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean isSetOutputId() {
        return outputIdESet;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getSourceName() {
        return sourceName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setSourceName(String newSourceName) {
        String oldSourceName = sourceName;
        sourceName = newSourceName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__SOURCE_NAME, oldSourceName,
                    sourceName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setTargetName(String newTargetName) {
        String oldTargetName = targetName;
        targetName = newTargetName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, TalendFilePackage.CONNECTION_TYPE__TARGET_NAME, oldTargetName,
                    targetName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case TalendFilePackage.CONNECTION_TYPE__ELEMENT_PARAMETER:
            return ((InternalEList) getElementParameter()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case TalendFilePackage.CONNECTION_TYPE__ELEMENT_PARAMETER:
            return getElementParameter();
        case TalendFilePackage.CONNECTION_TYPE__CONNECTOR_NAME:
            return getConnectorName();
        case TalendFilePackage.CONNECTION_TYPE__LABEL:
            return getLabel();
        case TalendFilePackage.CONNECTION_TYPE__CONNECTION_STYLE:
            return new Integer(getConnectionStyle());
        case TalendFilePackage.CONNECTION_TYPE__MERGE_ORDER:
            return new Integer(getMergeOrder());
        case TalendFilePackage.CONNECTION_TYPE__METANAME:
            return getMetaname();
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_X:
            return new Integer(getOffsetLabelX());
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_Y:
            return new Integer(getOffsetLabelY());
        case TalendFilePackage.CONNECTION_TYPE__OUTPUT_ID:
            return new Integer(getOutputId());
        case TalendFilePackage.CONNECTION_TYPE__SOURCE_NAME:
            return getSourceName();
        case TalendFilePackage.CONNECTION_TYPE__TARGET_NAME:
            return getTargetName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case TalendFilePackage.CONNECTION_TYPE__ELEMENT_PARAMETER:
            getElementParameter().clear();
            getElementParameter().addAll((Collection) newValue);
            return;
        case TalendFilePackage.CONNECTION_TYPE__CONNECTOR_NAME:
            setConnectorName((String) newValue);
            return;
        case TalendFilePackage.CONNECTION_TYPE__LABEL:
            setLabel((String) newValue);
            return;
        case TalendFilePackage.CONNECTION_TYPE__CONNECTION_STYLE:
            setConnectionStyle(((Integer) newValue).intValue());
            return;
        case TalendFilePackage.CONNECTION_TYPE__MERGE_ORDER:
            setMergeOrder(((Integer) newValue).intValue());
            return;
        case TalendFilePackage.CONNECTION_TYPE__METANAME:
            setMetaname((String) newValue);
            return;
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_X:
            setOffsetLabelX(((Integer) newValue).intValue());
            return;
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_Y:
            setOffsetLabelY(((Integer) newValue).intValue());
            return;
        case TalendFilePackage.CONNECTION_TYPE__OUTPUT_ID:
            setOutputId(((Integer) newValue).intValue());
            return;
        case TalendFilePackage.CONNECTION_TYPE__SOURCE_NAME:
            setSourceName((String) newValue);
            return;
        case TalendFilePackage.CONNECTION_TYPE__TARGET_NAME:
            setTargetName((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void eUnset(int featureID) {
        switch (featureID) {
        case TalendFilePackage.CONNECTION_TYPE__ELEMENT_PARAMETER:
            getElementParameter().clear();
            return;
        case TalendFilePackage.CONNECTION_TYPE__CONNECTOR_NAME:
            setConnectorName(CONNECTOR_NAME_EDEFAULT);
            return;
        case TalendFilePackage.CONNECTION_TYPE__LABEL:
            setLabel(LABEL_EDEFAULT);
            return;
        case TalendFilePackage.CONNECTION_TYPE__CONNECTION_STYLE:
            unsetConnectionStyle();
            return;
        case TalendFilePackage.CONNECTION_TYPE__MERGE_ORDER:
            unsetMergeOrder();
            return;
        case TalendFilePackage.CONNECTION_TYPE__METANAME:
            setMetaname(METANAME_EDEFAULT);
            return;
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_X:
            unsetOffsetLabelX();
            return;
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_Y:
            unsetOffsetLabelY();
            return;
        case TalendFilePackage.CONNECTION_TYPE__OUTPUT_ID:
            unsetOutputId();
            return;
        case TalendFilePackage.CONNECTION_TYPE__SOURCE_NAME:
            setSourceName(SOURCE_NAME_EDEFAULT);
            return;
        case TalendFilePackage.CONNECTION_TYPE__TARGET_NAME:
            setTargetName(TARGET_NAME_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case TalendFilePackage.CONNECTION_TYPE__ELEMENT_PARAMETER:
            return elementParameter != null && !elementParameter.isEmpty();
        case TalendFilePackage.CONNECTION_TYPE__CONNECTOR_NAME:
            return CONNECTOR_NAME_EDEFAULT == null ? connectorName != null : !CONNECTOR_NAME_EDEFAULT.equals(connectorName);
        case TalendFilePackage.CONNECTION_TYPE__LABEL:
            return LABEL_EDEFAULT == null ? label != null : !LABEL_EDEFAULT.equals(label);
        case TalendFilePackage.CONNECTION_TYPE__CONNECTION_STYLE:
            return isSetConnectionStyle();
        case TalendFilePackage.CONNECTION_TYPE__MERGE_ORDER:
            return isSetMergeOrder();
        case TalendFilePackage.CONNECTION_TYPE__METANAME:
            return METANAME_EDEFAULT == null ? metaname != null : !METANAME_EDEFAULT.equals(metaname);
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_X:
            return isSetOffsetLabelX();
        case TalendFilePackage.CONNECTION_TYPE__OFFSET_LABEL_Y:
            return isSetOffsetLabelY();
        case TalendFilePackage.CONNECTION_TYPE__OUTPUT_ID:
            return isSetOutputId();
        case TalendFilePackage.CONNECTION_TYPE__SOURCE_NAME:
            return SOURCE_NAME_EDEFAULT == null ? sourceName != null : !SOURCE_NAME_EDEFAULT.equals(sourceName);
        case TalendFilePackage.CONNECTION_TYPE__TARGET_NAME:
            return TARGET_NAME_EDEFAULT == null ? targetName != null : !TARGET_NAME_EDEFAULT.equals(targetName);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (connectorName: ");
        result.append(connectorName);
        result.append(", label: ");
        result.append(label);
        result.append(", connectionStyle: ");
        if (connectionStyleESet)
            result.append(connectionStyle);
        else
            result.append("<unset>");
        result.append(", mergeOrder: ");
        if (mergeOrderESet)
            result.append(mergeOrder);
        else
            result.append("<unset>");
        result.append(", metaname: ");
        result.append(metaname);
        result.append(", offsetLabelX: ");
        if (offsetLabelXESet)
            result.append(offsetLabelX);
        else
            result.append("<unset>");
        result.append(", offsetLabelY: ");
        if (offsetLabelYESet)
            result.append(offsetLabelY);
        else
            result.append("<unset>");
        result.append(", outputId: ");
        if (outputIdESet)
            result.append(outputId);
        else
            result.append("<unset>");
        result.append(", sourceName: ");
        result.append(sourceName);
        result.append(", targetName: ");
        result.append(targetName);
        result.append(')');
        return result.toString();
    }

} // ConnectionTypeImpl
