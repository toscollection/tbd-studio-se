/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.talend.repository.model.oozie.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OozieFactoryImpl extends EFactoryImpl implements OozieFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static OozieFactory init() {
        try {
            OozieFactory theOozieFactory = (OozieFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.talend.org/oozie"); 
            if (theOozieFactory != null) {
                return theOozieFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new OozieFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OozieFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case OoziePackage.OOZIE_CONNECTION: return createOozieConnection();
            case OoziePackage.OOZIE_CONNECTION_ITEM: return createOozieConnectionItem();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OozieConnection createOozieConnection() {
        OozieConnectionImpl oozieConnection = new OozieConnectionImpl();
        return oozieConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OozieConnectionItem createOozieConnectionItem() {
        OozieConnectionItemImpl oozieConnectionItem = new OozieConnectionItemImpl();
        return oozieConnectionItem;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OoziePackage getOoziePackage() {
        return (OoziePackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static OoziePackage getPackage() {
        return OoziePackage.eINSTANCE;
    }

} //OozieFactoryImpl
