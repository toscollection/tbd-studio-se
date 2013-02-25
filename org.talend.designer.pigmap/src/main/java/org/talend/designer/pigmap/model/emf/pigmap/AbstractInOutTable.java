/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.common.util.EList;

import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Abstract In Out Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getNodes <em>Nodes</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getExpressionFilter <em>Expression Filter</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateExpressionFilter <em>Activate Expression Filter</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateCondensedTool <em>Activate Condensed Tool</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isMinimized <em>Minimized</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getFilterIncomingConnections <em>Filter Incoming Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable()
 * @model abstract="true"
 * @generated
 */
public interface AbstractInOutTable extends MapperTable {
    /**
     * Returns the value of the '<em><b>Nodes</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.TableNode}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Nodes</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Nodes</em>' containment reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_Nodes()
     * @model containment="true"
     * @generated
     */
    EList<TableNode> getNodes();

    /**
     * Returns the value of the '<em><b>Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Expression Filter</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Expression Filter</em>' attribute.
     * @see #setExpressionFilter(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_ExpressionFilter()
     * @model
     * @generated
     */
    String getExpressionFilter();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getExpressionFilter <em>Expression Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Expression Filter</em>' attribute.
     * @see #getExpressionFilter()
     * @generated
     */
    void setExpressionFilter(String value);

    /**
     * Returns the value of the '<em><b>Activate Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activate Expression Filter</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activate Expression Filter</em>' attribute.
     * @see #setActivateExpressionFilter(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_ActivateExpressionFilter()
     * @model
     * @generated
     */
    boolean isActivateExpressionFilter();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateExpressionFilter <em>Activate Expression Filter</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activate Expression Filter</em>' attribute.
     * @see #isActivateExpressionFilter()
     * @generated
     */
    void setActivateExpressionFilter(boolean value);

    /**
     * Returns the value of the '<em><b>Activate Condensed Tool</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Activate Condensed Tool</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Activate Condensed Tool</em>' attribute.
     * @see #setActivateCondensedTool(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_ActivateCondensedTool()
     * @model
     * @generated
     */
    boolean isActivateCondensedTool();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateCondensedTool <em>Activate Condensed Tool</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Activate Condensed Tool</em>' attribute.
     * @see #isActivateCondensedTool()
     * @generated
     */
    void setActivateCondensedTool(boolean value);

    /**
     * Returns the value of the '<em><b>Minimized</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Minimized</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Minimized</em>' attribute.
     * @see #setMinimized(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_Minimized()
     * @model
     * @generated
     */
    boolean isMinimized();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isMinimized <em>Minimized</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Minimized</em>' attribute.
     * @see #isMinimized()
     * @generated
     */
    void setMinimized(boolean value);

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
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_Name()
     * @model
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Filter Incoming Connections</b></em>' reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Filter Incoming Connections</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Filter Incoming Connections</em>' reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getAbstractInOutTable_FilterIncomingConnections()
     * @model
     * @generated
     */
    EList<FilterConnection> getFilterIncomingConnections();

} // AbstractInOutTable
