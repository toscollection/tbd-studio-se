/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage
 * @generated
 */
public interface PigmapFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    PigmapFactory eINSTANCE = org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Pig Map Data</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Pig Map Data</em>'.
     * @generated
     */
    PigMapData createPigMapData();

    /**
     * Returns a new object of class '<em>Input Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Input Table</em>'.
     * @generated
     */
    InputTable createInputTable();

    /**
     * Returns a new object of class '<em>Output Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Output Table</em>'.
     * @generated
     */
    OutputTable createOutputTable();

    /**
     * Returns a new object of class '<em>Var Table</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Var Table</em>'.
     * @generated
     */
    VarTable createVarTable();

    /**
     * Returns a new object of class '<em>Table Node</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Table Node</em>'.
     * @generated
     */
    TableNode createTableNode();

    /**
     * Returns a new object of class '<em>Var Node</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Var Node</em>'.
     * @generated
     */
    VarNode createVarNode();

    /**
     * Returns a new object of class '<em>Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection</em>'.
     * @generated
     */
    Connection createConnection();

    /**
     * Returns a new object of class '<em>Lookup Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Lookup Connection</em>'.
     * @generated
     */
    LookupConnection createLookupConnection();

    /**
     * Returns a new object of class '<em>Filter Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Filter Connection</em>'.
     * @generated
     */
    FilterConnection createFilterConnection();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    PigmapPackage getPigmapPackage();

} //PigmapFactory
