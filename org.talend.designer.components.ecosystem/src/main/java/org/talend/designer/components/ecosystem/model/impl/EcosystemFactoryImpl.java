/**
 * // ============================================================================ // // Copyright (C) 2006-2010 Talend
 * Inc. - www.talend.com // // This source code is available under agreement available at //
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt // // You should have received a
 * copy of the agreement // along with this program; if not, write to Talend SA // 9 rue Pages 92150 Suresnes, France // //
 * ============================================================================
 * 
 * $Id$
 */
package org.talend.designer.components.ecosystem.model.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.talend.designer.components.ecosystem.model.*;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Factory</b>. <!-- end-user-doc -->
 * @generated
 */
public class EcosystemFactoryImpl extends EFactoryImpl implements EcosystemFactory {

    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public static EcosystemFactory init() {
        try {
            EcosystemFactory theEcosystemFactory = (EcosystemFactory)EPackage.Registry.INSTANCE.getEFactory("model");  //$NON-NLS-1$
            if (theEcosystemFactory != null) {
                return theEcosystemFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new EcosystemFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EcosystemFactoryImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public EObject create(EClass eClass) {
        switch (eClass.getClassifierID()) {
            case EcosystemPackage.COMPONENT_EXTENSION: return createComponentExtension();
            case EcosystemPackage.REVISION: return createRevision();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case EcosystemPackage.LANGUAGE:
                return createLanguageFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case EcosystemPackage.LANGUAGE:
                return convertLanguageToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier"); //$NON-NLS-1$ //$NON-NLS-2$
        }
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public ComponentExtension createComponentExtension() {
        ComponentExtensionImpl componentExtension = new ComponentExtensionImpl();
        return componentExtension;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Revision createRevision() {
        RevisionImpl revision = new RevisionImpl();
        return revision;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Language createLanguageFromString(EDataType eDataType, String initialValue) {
        Language result = Language.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        return result;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String convertLanguageToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EcosystemPackage getEcosystemPackage() {
        return (EcosystemPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static EcosystemPackage getPackage() {
        return EcosystemPackage.eINSTANCE;
    }

} // EcosystemFactoryImpl
