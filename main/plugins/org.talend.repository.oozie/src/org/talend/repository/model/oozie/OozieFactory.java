/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see org.talend.repository.model.oozie.OoziePackage
 * @generated
 */
public interface OozieFactory extends EFactory {
    /**
     * The singleton instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    OozieFactory eINSTANCE = org.talend.repository.model.oozie.impl.OozieFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Connection</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection</em>'.
     * @generated
     */
    OozieConnection createOozieConnection();

    /**
     * Returns a new object of class '<em>Connection Item</em>'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return a new object of class '<em>Connection Item</em>'.
     * @generated
     */
    OozieConnectionItem createOozieConnectionItem();

    /**
     * Returns the package supported by this factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the package supported by this factory.
     * @generated
     */
    OoziePackage getOoziePackage();

} //OozieFactory
