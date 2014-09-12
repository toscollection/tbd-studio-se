/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.common.util.EList;

import org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Pig Map Data</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getInputTables <em>Input Tables</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getOutputTables <em>Output Tables</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getVarTables <em>Var Tables</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getConnections <em>Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getPigMapData()
 * @model
 * @generated
 */
public interface PigMapData extends AbstractExternalData {
    /**
     * Returns the value of the '<em><b>Input Tables</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.InputTable}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Input Tables</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Input Tables</em>' containment reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getPigMapData_InputTables()
     * @model containment="true"
     * @generated
     */
    EList<InputTable> getInputTables();

    /**
     * Returns the value of the '<em><b>Output Tables</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Output Tables</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Output Tables</em>' containment reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getPigMapData_OutputTables()
     * @model containment="true"
     * @generated
     */
    EList<OutputTable> getOutputTables();

    /**
     * Returns the value of the '<em><b>Var Tables</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.VarTable}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Var Tables</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Var Tables</em>' containment reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getPigMapData_VarTables()
     * @model containment="true"
     * @generated
     */
    EList<VarTable> getVarTables();

    /**
     * Returns the value of the '<em><b>Connections</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.IConnection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Connections</em>' containment reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Connections</em>' containment reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getPigMapData_Connections()
     * @model containment="true"
     * @generated
     */
    EList<IConnection> getConnections();

} // PigMapData
