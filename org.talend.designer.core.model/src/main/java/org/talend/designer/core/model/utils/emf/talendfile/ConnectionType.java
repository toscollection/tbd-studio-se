/**
 * <copyright>
 * </copyright>
 *
 * $Id: ConnectionType.java 7174 2007-11-23 09:13:18Z ggu $
 */
package org.talend.designer.core.model.utils.emf.talendfile;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Connection Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getElementParameter <em>Element Parameter</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectorName <em>Connector Name</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getLabel <em>Label</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectionStyle <em>Connection Style</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMergeOrder <em>Merge Order</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMetaname <em>Metaname</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelX <em>Offset Label X</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelY <em>Offset Label Y</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOutputId <em>Output Id</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getSourceName <em>Source Name</em>}</li>
 *   <li>{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getTargetName <em>Target Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType()
 * @model extendedMetaData="name='Connection_._type' kind='elementOnly'"
 * @generated
 */
public interface ConnectionType extends EObject {
    /**
     * Returns the value of the '<em><b>Element Parameter</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Element Parameter</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Element Parameter</em>' containment reference list.
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_ElementParameter()
     * @model type="org.talend.designer.core.model.utils.emf.talendfile.ElementParameterType" containment="true"
     *        extendedMetaData="kind='element' name='ElementParameter' namespace='##targetNamespace'"
     * @generated
     */
    EList getElementParameter();

    /**
     * Returns the value of the '<em><b>Connector Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connector Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connector Name</em>' attribute.
     * @see #setConnectorName(String)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_ConnectorName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='connectorName' namespace='##targetNamespace'"
     * @generated
     */
    String getConnectorName();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectorName <em>Connector Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connector Name</em>' attribute.
     * @see #getConnectorName()
     * @generated
     */
    void setConnectorName(String value);

    /**
     * Returns the value of the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Label</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Label</em>' attribute.
     * @see #setLabel(String)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_Label()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='label' namespace='##targetNamespace'"
     * @generated
     */
    String getLabel();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getLabel <em>Label</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Label</em>' attribute.
     * @see #getLabel()
     * @generated
     */
    void setLabel(String value);

    /**
     * Returns the value of the '<em><b>Connection Style</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connection Style</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connection Style</em>' attribute.
     * @see #isSetConnectionStyle()
     * @see #unsetConnectionStyle()
     * @see #setConnectionStyle(int)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_ConnectionStyle()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='lineStyle' namespace='##targetNamespace'"
     * @generated
     */
    int getConnectionStyle();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectionStyle <em>Connection Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Connection Style</em>' attribute.
     * @see #isSetConnectionStyle()
     * @see #unsetConnectionStyle()
     * @see #getConnectionStyle()
     * @generated
     */
    void setConnectionStyle(int value);

    /**
     * Unsets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectionStyle <em>Connection Style</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetConnectionStyle()
     * @see #getConnectionStyle()
     * @see #setConnectionStyle(int)
     * @generated
     */
    void unsetConnectionStyle();

    /**
     * Returns whether the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getConnectionStyle <em>Connection Style</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Connection Style</em>' attribute is set.
     * @see #unsetConnectionStyle()
     * @see #getConnectionStyle()
     * @see #setConnectionStyle(int)
     * @generated
     */
    boolean isSetConnectionStyle();

    /**
     * Returns the value of the '<em><b>Merge Order</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Merge Order</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Merge Order</em>' attribute.
     * @see #isSetMergeOrder()
     * @see #unsetMergeOrder()
     * @see #setMergeOrder(int)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_MergeOrder()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='mergeOrder' namespace='##targetNamespace'"
     * @generated
     */
    int getMergeOrder();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMergeOrder <em>Merge Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Merge Order</em>' attribute.
     * @see #isSetMergeOrder()
     * @see #unsetMergeOrder()
     * @see #getMergeOrder()
     * @generated
     */
    void setMergeOrder(int value);

    /**
     * Unsets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMergeOrder <em>Merge Order</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetMergeOrder()
     * @see #getMergeOrder()
     * @see #setMergeOrder(int)
     * @generated
     */
    void unsetMergeOrder();

    /**
     * Returns whether the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMergeOrder <em>Merge Order</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Merge Order</em>' attribute is set.
     * @see #unsetMergeOrder()
     * @see #getMergeOrder()
     * @see #setMergeOrder(int)
     * @generated
     */
    boolean isSetMergeOrder();

    /**
     * Returns the value of the '<em><b>Metaname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Metaname</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Metaname</em>' attribute.
     * @see #setMetaname(String)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_Metaname()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='metaname' namespace='##targetNamespace'"
     * @generated
     */
    String getMetaname();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getMetaname <em>Metaname</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Metaname</em>' attribute.
     * @see #getMetaname()
     * @generated
     */
    void setMetaname(String value);

    /**
     * Returns the value of the '<em><b>Offset Label X</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Offset Label X</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Offset Label X</em>' attribute.
     * @see #isSetOffsetLabelX()
     * @see #unsetOffsetLabelX()
     * @see #setOffsetLabelX(int)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_OffsetLabelX()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='offsetLabelX' namespace='##targetNamespace'"
     * @generated
     */
    int getOffsetLabelX();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelX <em>Offset Label X</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Offset Label X</em>' attribute.
     * @see #isSetOffsetLabelX()
     * @see #unsetOffsetLabelX()
     * @see #getOffsetLabelX()
     * @generated
     */
    void setOffsetLabelX(int value);

    /**
     * Unsets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelX <em>Offset Label X</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOffsetLabelX()
     * @see #getOffsetLabelX()
     * @see #setOffsetLabelX(int)
     * @generated
     */
    void unsetOffsetLabelX();

    /**
     * Returns whether the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelX <em>Offset Label X</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Offset Label X</em>' attribute is set.
     * @see #unsetOffsetLabelX()
     * @see #getOffsetLabelX()
     * @see #setOffsetLabelX(int)
     * @generated
     */
    boolean isSetOffsetLabelX();

    /**
     * Returns the value of the '<em><b>Offset Label Y</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Offset Label Y</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Offset Label Y</em>' attribute.
     * @see #isSetOffsetLabelY()
     * @see #unsetOffsetLabelY()
     * @see #setOffsetLabelY(int)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_OffsetLabelY()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='offsetLabelY' namespace='##targetNamespace'"
     * @generated
     */
    int getOffsetLabelY();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelY <em>Offset Label Y</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Offset Label Y</em>' attribute.
     * @see #isSetOffsetLabelY()
     * @see #unsetOffsetLabelY()
     * @see #getOffsetLabelY()
     * @generated
     */
    void setOffsetLabelY(int value);

    /**
     * Unsets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelY <em>Offset Label Y</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOffsetLabelY()
     * @see #getOffsetLabelY()
     * @see #setOffsetLabelY(int)
     * @generated
     */
    void unsetOffsetLabelY();

    /**
     * Returns whether the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOffsetLabelY <em>Offset Label Y</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Offset Label Y</em>' attribute is set.
     * @see #unsetOffsetLabelY()
     * @see #getOffsetLabelY()
     * @see #setOffsetLabelY(int)
     * @generated
     */
    boolean isSetOffsetLabelY();

    /**
     * Returns the value of the '<em><b>Output Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Output Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Output Id</em>' attribute.
     * @see #isSetOutputId()
     * @see #unsetOutputId()
     * @see #setOutputId(int)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_OutputId()
     * @model unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Int"
     *        extendedMetaData="kind='attribute' name='outputId' namespace='##targetNamespace'"
     * @generated
     */
    int getOutputId();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOutputId <em>Output Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Output Id</em>' attribute.
     * @see #isSetOutputId()
     * @see #unsetOutputId()
     * @see #getOutputId()
     * @generated
     */
    void setOutputId(int value);

    /**
     * Unsets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOutputId <em>Output Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #isSetOutputId()
     * @see #getOutputId()
     * @see #setOutputId(int)
     * @generated
     */
    void unsetOutputId();

    /**
     * Returns whether the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getOutputId <em>Output Id</em>}' attribute is set.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return whether the value of the '<em>Output Id</em>' attribute is set.
     * @see #unsetOutputId()
     * @see #getOutputId()
     * @see #setOutputId(int)
     * @generated
     */
    boolean isSetOutputId();

    /**
     * Returns the value of the '<em><b>Source Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source Name</em>' attribute.
     * @see #setSourceName(String)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_SourceName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='source' namespace='##targetNamespace'"
     * @generated
     */
    String getSourceName();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getSourceName <em>Source Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source Name</em>' attribute.
     * @see #getSourceName()
     * @generated
     */
    void setSourceName(String value);

    /**
     * Returns the value of the '<em><b>Target Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Target Name</em>' attribute.
     * @see #setTargetName(String)
     * @see org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage#getConnectionType_TargetName()
     * @model dataType="org.eclipse.emf.ecore.xml.type.String"
     *        extendedMetaData="kind='attribute' name='target' namespace='##targetNamespace'"
     * @generated
     */
    String getTargetName();

    /**
     * Sets the value of the '{@link org.talend.designer.core.model.utils.emf.talendfile.ConnectionType#getTargetName <em>Target Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target Name</em>' attribute.
     * @see #getTargetName()
     * @generated
     */
    void setTargetName(String value);

} // ConnectionType