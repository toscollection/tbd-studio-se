/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.common.util.EList;

import org.talend.designer.gefabstractmap.model.abstractmap.MapperTableEntity;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getExpression <em>Expression</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getType <em>Type</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isKey <em>Key</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getPattern <em>Pattern</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isNullable <em>Nullable</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getOutgoingConnections <em>Outgoing Connections</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getIncomingConnections <em>Incoming Connections</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getFilterOutGoingConnections <em>Filter Out Going Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode()
 * @model abstract="true"
 * @generated
 */
public interface AbstractNode extends MapperTableEntity {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Expression</em>' attribute.
     * @see #setExpression(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_Expression()
     * @model
     * @generated
     */
    String getExpression();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getExpression <em>Expression</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression</em>' attribute.
     * @see #getExpression()
     * @generated
     */
    void setExpression(String value);

    /**
     * Returns the value of the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Type</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Type</em>' attribute.
     * @see #setType(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_Type()
     * @model
     * @generated
     */
    String getType();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getType <em>Type</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Type</em>' attribute.
     * @see #getType()
     * @generated
     */
    void setType(String value);

    /**
     * Returns the value of the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Key</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Key</em>' attribute.
     * @see #setKey(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_Key()
     * @model
     * @generated
     */
    boolean isKey();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isKey <em>Key</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Key</em>' attribute.
     * @see #isKey()
     * @generated
     */
    void setKey(boolean value);

    /**
     * Returns the value of the '<em><b>Pattern</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Pattern</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Pattern</em>' attribute.
     * @see #setPattern(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_Pattern()
     * @model
     * @generated
     */
    String getPattern();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getPattern <em>Pattern</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Pattern</em>' attribute.
     * @see #getPattern()
     * @generated
     */
    void setPattern(String value);

    /**
     * Returns the value of the '<em><b>Nullable</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Nullable</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Nullable</em>' attribute.
     * @see #setNullable(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_Nullable()
     * @model
     * @generated
     */
    boolean isNullable();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isNullable <em>Nullable</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Nullable</em>' attribute.
     * @see #isNullable()
     * @generated
     */
    void setNullable(boolean value);

    /**
     * Returns the value of the '<em><b>Outgoing Connections</b></em>' reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.Connection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Outgoing Connections</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Outgoing Connections</em>' reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_OutgoingConnections()
     * @model
     * @generated
     */
    EList<Connection> getOutgoingConnections();

    /**
     * Returns the value of the '<em><b>Incoming Connections</b></em>' reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.Connection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Incoming Connections</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Incoming Connections</em>' reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_IncomingConnections()
     * @model
     * @generated
     */
    EList<Connection> getIncomingConnections();

    /**
     * Returns the value of the '<em><b>Filter Out Going Connections</b></em>' reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filter Out Going Connections</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Filter Out Going Connections</em>' reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractNode_FilterOutGoingConnections()
     * @model
     * @generated
     */
    EList<FilterConnection> getFilterOutGoingConnections();

} // AbstractNode
