/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Input Table</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isLookup <em>Lookup</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getMatchingMode <em>Matching Mode</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getLookupMode <em>Lookup Mode</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isInnerJoin <em>Inner Join</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isPersistent <em>Persistent</em>}</li>
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
     * Returns the value of the '<em><b>Matching Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Matching Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Matching Mode</em>' attribute.
     * @see #setMatchingMode(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_MatchingMode()
     * @model
     * @generated
     */
    String getMatchingMode();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getMatchingMode <em>Matching Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Matching Mode</em>' attribute.
     * @see #getMatchingMode()
     * @generated
     */
    void setMatchingMode(String value);

    /**
     * Returns the value of the '<em><b>Lookup Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lookup Mode</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lookup Mode</em>' attribute.
     * @see #setLookupMode(String)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_LookupMode()
     * @model
     * @generated
     */
    String getLookupMode();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getLookupMode <em>Lookup Mode</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Lookup Mode</em>' attribute.
     * @see #getLookupMode()
     * @generated
     */
    void setLookupMode(String value);

    /**
     * Returns the value of the '<em><b>Inner Join</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Inner Join</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Inner Join</em>' attribute.
     * @see #setInnerJoin(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_InnerJoin()
     * @model
     * @generated
     */
    boolean isInnerJoin();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isInnerJoin <em>Inner Join</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Inner Join</em>' attribute.
     * @see #isInnerJoin()
     * @generated
     */
    void setInnerJoin(boolean value);

    /**
     * Returns the value of the '<em><b>Persistent</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Persistent</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Persistent</em>' attribute.
     * @see #setPersistent(boolean)
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getInputTable_Persistent()
     * @model
     * @generated
     */
    boolean isPersistent();

    /**
     * Sets the value of the '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isPersistent <em>Persistent</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Persistent</em>' attribute.
     * @see #isPersistent()
     * @generated
     */
    void setPersistent(boolean value);

} // InputTable
