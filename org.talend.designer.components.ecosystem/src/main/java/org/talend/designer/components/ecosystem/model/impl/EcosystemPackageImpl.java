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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemFactory;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Language;
import org.talend.designer.components.ecosystem.model.Revision;

/**
 * <!-- begin-user-doc --> An implementation of the model <b>Package</b>. <!-- end-user-doc -->
 * @generated
 */
public class EcosystemPackageImpl extends EPackageImpl implements EcosystemPackage {

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass componentExtensionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EClass revisionEClass = null;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private EEnum languageEEnum = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package package URI value.
     * <p>
     * Note: the correct way to create the package is via the static factory method {@link #init init()}, which also
     * performs initialization of the package, or returns the registered package, if one already exists. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private EcosystemPackageImpl() {
        super(eNS_URI, EcosystemFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this
     * model, and for any others upon which it depends.  Simple
     * dependencies are satisfied by calling this method on all
     * dependent packages before doing anything else.  This method drives
     * initialization for interdependent packages directly, in parallel
     * with this package, itself.
     * <p>Of this package and its interdependencies, all packages which
     * have not yet been registered by their URI values are first created
     * and registered.  The packages are then initialized in two steps:
     * meta-model objects for all of the packages are created before any
     * are initialized, since one package's meta-model objects may refer to
     * those of another.
     * <p>Invocation of this method will not affect any packages that have
     * already been initialized.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static EcosystemPackage init() {
        if (isInited) return (EcosystemPackage)EPackage.Registry.INSTANCE.getEPackage(EcosystemPackage.eNS_URI);

        // Obtain or create and register package
        EcosystemPackageImpl theEcosystemPackage = (EcosystemPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(eNS_URI) instanceof EcosystemPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(eNS_URI) : new EcosystemPackageImpl());

        isInited = true;

        // Create package meta-data objects
        theEcosystemPackage.createPackageContents();

        // Initialize created meta-data
        theEcosystemPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theEcosystemPackage.freeze();

        return theEcosystemPackage;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getComponentExtension() {
        return componentExtensionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComponentExtension_Name() {
        return (EAttribute)componentExtensionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComponentExtension_Description() {
        return (EAttribute)componentExtensionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComponentExtension_Language() {
        return (EAttribute)componentExtensionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getComponentExtension_Revisions() {
        return (EReference)componentExtensionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getComponentExtension_InstalledRevision() {
        return (EReference)componentExtensionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getComponentExtension_LatestRevision() {
        return (EReference)componentExtensionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComponentExtension_InstalledLocation() {
        return (EAttribute)componentExtensionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getComponentExtension_Author() {
        return (EAttribute)componentExtensionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EClass getRevision() {
        return revisionEClass;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRevision_Id() {
        return (EAttribute)revisionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRevision_Name() {
        return (EAttribute)revisionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRevision_Description() {
        return (EAttribute)revisionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRevision_Url() {
        return (EAttribute)revisionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRevision_Date() {
        return (EAttribute)revisionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EReference getRevision_Extension() {
        return (EReference)revisionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getRevision_FileName() {
        return (EAttribute)revisionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EEnum getLanguage() {
        return languageEEnum;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EcosystemFactory getEcosystemFactory() {
        return (EcosystemFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        componentExtensionEClass = createEClass(COMPONENT_EXTENSION);
        createEAttribute(componentExtensionEClass, COMPONENT_EXTENSION__NAME);
        createEAttribute(componentExtensionEClass, COMPONENT_EXTENSION__DESCRIPTION);
        createEAttribute(componentExtensionEClass, COMPONENT_EXTENSION__LANGUAGE);
        createEReference(componentExtensionEClass, COMPONENT_EXTENSION__REVISIONS);
        createEReference(componentExtensionEClass, COMPONENT_EXTENSION__INSTALLED_REVISION);
        createEReference(componentExtensionEClass, COMPONENT_EXTENSION__LATEST_REVISION);
        createEAttribute(componentExtensionEClass, COMPONENT_EXTENSION__INSTALLED_LOCATION);
        createEAttribute(componentExtensionEClass, COMPONENT_EXTENSION__AUTHOR);

        revisionEClass = createEClass(REVISION);
        createEAttribute(revisionEClass, REVISION__ID);
        createEAttribute(revisionEClass, REVISION__NAME);
        createEAttribute(revisionEClass, REVISION__DESCRIPTION);
        createEAttribute(revisionEClass, REVISION__URL);
        createEAttribute(revisionEClass, REVISION__DATE);
        createEReference(revisionEClass, REVISION__EXTENSION);
        createEAttribute(revisionEClass, REVISION__FILE_NAME);

        // Create enums
        languageEEnum = createEEnum(LANGUAGE);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes

        // Initialize classes and features; add operations and parameters
        initEClass(componentExtensionEClass, ComponentExtension.class, "ComponentExtension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getComponentExtension_Name(), ecorePackage.getEString(), "name", null, 1, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getComponentExtension_Description(), ecorePackage.getEString(), "description", null, 1, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getComponentExtension_Language(), this.getLanguage(), "language", "", 1, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$ //$NON-NLS-2$
        initEReference(getComponentExtension_Revisions(), this.getRevision(), this.getRevision_Extension(), "revisions", null, 1, -1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getComponentExtension_InstalledRevision(), this.getRevision(), null, "installedRevision", null, 0, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getComponentExtension_LatestRevision(), this.getRevision(), null, "latestRevision", null, 0, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getComponentExtension_InstalledLocation(), ecorePackage.getEString(), "installedLocation", null, 0, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getComponentExtension_Author(), ecorePackage.getEString(), "author", null, 0, 1, ComponentExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        initEClass(revisionEClass, Revision.class, "Revision", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS); //$NON-NLS-1$
        initEAttribute(getRevision_Id(), ecorePackage.getEInt(), "id", null, 1, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getRevision_Name(), ecorePackage.getEString(), "name", null, 1, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getRevision_Description(), ecorePackage.getEString(), "description", null, 1, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getRevision_Url(), ecorePackage.getEString(), "url", null, 1, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getRevision_Date(), ecorePackage.getEDate(), "date", null, 1, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEReference(getRevision_Extension(), this.getComponentExtension(), this.getComponentExtension_Revisions(), "extension", null, 1, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$
        initEAttribute(getRevision_FileName(), ecorePackage.getEString(), "fileName", null, 0, 1, Revision.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED); //$NON-NLS-1$

        // Initialize enums and add enum literals
        initEEnum(languageEEnum, Language.class, "Language"); //$NON-NLS-1$
        addEEnumLiteral(languageEEnum, Language.PERL);
        addEEnumLiteral(languageEEnum, Language.JAVA);

        // Create resource
        createResource(eNS_URI);
    }

} // EcosystemPackageImpl
