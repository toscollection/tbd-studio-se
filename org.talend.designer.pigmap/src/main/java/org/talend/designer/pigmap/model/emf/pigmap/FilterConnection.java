/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Filter Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getSource <em>Source</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getTarget <em>Target</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getFilterConnection()
 * @model
 * @generated
 */
public interface FilterConnection extends IConnection {
    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getFilterConnection_Name()
     * @model transient="true" changeable="false" volatile="true"
     * @generated
     */
    String getName();

    /**
     * Returns the value of the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Source</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Source</em>' reference.
     * @see #setSource(AbstractNode)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getFilterConnection_Source()
     * @model
     * @generated
     */
    AbstractNode getSource();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getSource <em>Source</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Source</em>' reference.
     * @see #getSource()
     * @generated
     */
    void setSource(AbstractNode value);

    /**
     * Returns the value of the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Target</em>' reference isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Target</em>' reference.
     * @see #setTarget(AbstractInOutTable)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getFilterConnection_Target()
     * @model
     * @generated
     */
    AbstractInOutTable getTarget();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getTarget <em>Target</em>}' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Target</em>' reference.
     * @see #getTarget()
     * @generated
     */
    void setTarget(AbstractInOutTable value);

} // FilterConnection
