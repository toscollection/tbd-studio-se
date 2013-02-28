/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isLookup <em>Lookup</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinModel <em>Join Model</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinOptimization <em>Join Optimization</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getCustomPartitioner <em>Custom Partitioner</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getIncreaseParallelism <em>Increase Parallelism</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable()
 * @model
 * @generated
 */
public interface InputTable extends AbstractInOutTable {
    /**
     * Returns the value of the '<em><b>Lookup</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lookup</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lookup</em>' attribute.
     * @see #setLookup(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_Lookup()
     * @model
     * @generated
     */
    boolean isLookup();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isLookup <em>Lookup</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lookup</em>' attribute.
     * @see #isLookup()
     * @generated
     */
    void setLookup(boolean value);

    /**
     * Returns the value of the '<em><b>Join Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Join Model</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Join Model</em>' attribute.
     * @see #setJoinModel(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_JoinModel()
     * @model
     * @generated
     */
    String getJoinModel();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinModel <em>Join Model</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Join Model</em>' attribute.
     * @see #getJoinModel()
     * @generated
     */
    void setJoinModel(String value);

    /**
     * Returns the value of the '<em><b>Join Optimization</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Join Optimization</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Join Optimization</em>' attribute.
     * @see #setJoinOptimization(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_JoinOptimization()
     * @model
     * @generated
     */
    String getJoinOptimization();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinOptimization <em>Join Optimization</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Join Optimization</em>' attribute.
     * @see #getJoinOptimization()
     * @generated
     */
    void setJoinOptimization(String value);

    /**
     * Returns the value of the '<em><b>Custom Partitioner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Custom Partitioner</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Custom Partitioner</em>' attribute.
     * @see #setCustomPartitioner(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_CustomPartitioner()
     * @model
     * @generated
     */
    String getCustomPartitioner();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getCustomPartitioner <em>Custom Partitioner</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Custom Partitioner</em>' attribute.
     * @see #getCustomPartitioner()
     * @generated
     */
    void setCustomPartitioner(String value);

    /**
     * Returns the value of the '<em><b>Increase Parallelism</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Increase Parallelism</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Increase Parallelism</em>' attribute.
     * @see #setIncreaseParallelism(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_IncreaseParallelism()
     * @model
     * @generated
     */
    String getIncreaseParallelism();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getIncreaseParallelism <em>Increase Parallelism</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Increase Parallelism</em>' attribute.
     * @see #getIncreaseParallelism()
     * @generated
     */
    void setIncreaseParallelism(String value);

} // InputTable
