/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcorePackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;

import org.talend.repository.model.oozie.OozieConnection;
import org.talend.repository.model.oozie.OozieConnectionItem;
import org.talend.repository.model.oozie.OozieFactory;
import org.talend.repository.model.oozie.OoziePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class OoziePackageImpl extends EPackageImpl implements OoziePackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass oozieConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass oozieConnectionItemEClass = null;

    /**
     * Creates an instance of the model <b>Package</b>, registered with
     * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
     * package URI value.
     * <p>Note: the correct way to create the package is via the static
     * factory method {@link #init init()}, which also performs
     * initialization of the package, or returns the registered package,
     * if one already exists.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.eclipse.emf.ecore.EPackage.Registry
     * @see org.talend.repository.model.oozie.OoziePackage#eNS_URI
     * @see #init()
     * @generated
     */
    private OoziePackageImpl() {
        super(eNS_URI, OozieFactory.eINSTANCE);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private static boolean isInited = false;

    /**
     * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
     * 
     * <p>This method is used to initialize {@link OoziePackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static OoziePackage init() {
        if (isInited) return (OoziePackage)EPackage.Registry.INSTANCE.getEPackage(OoziePackage.eNS_URI);

        // Obtain or create and register package
        OoziePackageImpl theOoziePackage = (OoziePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OoziePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OoziePackageImpl());

        isInited = true;

        // Initialize simple dependencies
        HadoopClusterPackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theOoziePackage.createPackageContents();

        // Initialize created meta-data
        theOoziePackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theOoziePackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(OoziePackage.eNS_URI, theOoziePackage);
        return theOoziePackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOozieConnection() {
        return oozieConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOozieConnection_UserName() {
        return (EAttribute)oozieConnectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOozieConnection_OozieEndPoind() {
        return (EAttribute)oozieConnectionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOozieConnection_OozieVersion() {
        return (EAttribute)oozieConnectionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOozieConnection_EnableKerberos() {
        return (EAttribute)oozieConnectionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOozieConnectionItem() {
        return oozieConnectionItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OozieFactory getOozieFactory() {
        return (OozieFactory)getEFactoryInstance();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isCreated = false;

    /**
     * Creates the meta-model objects for the package.  This method is
     * guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void createPackageContents() {
        if (isCreated) return;
        isCreated = true;

        // Create classes and their features
        oozieConnectionEClass = createEClass(OOZIE_CONNECTION);
        createEAttribute(oozieConnectionEClass, OOZIE_CONNECTION__USER_NAME);
        createEAttribute(oozieConnectionEClass, OOZIE_CONNECTION__OOZIE_END_POIND);
        createEAttribute(oozieConnectionEClass, OOZIE_CONNECTION__OOZIE_VERSION);
        createEAttribute(oozieConnectionEClass, OOZIE_CONNECTION__ENABLE_KERBEROS);

        oozieConnectionItemEClass = createEClass(OOZIE_CONNECTION_ITEM);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private boolean isInitialized = false;

    /**
     * Complete the initialization of the package and its meta-model.  This
     * method is guarded to have no affect on any invocation but its first.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public void initializePackageContents() {
        if (isInitialized) return;
        isInitialized = true;

        // Initialize package
        setName(eNAME);
        setNsPrefix(eNS_PREFIX);
        setNsURI(eNS_URI);

        // Obtain other dependent packages
        HadoopClusterPackage theHadoopClusterPackage = (HadoopClusterPackage)EPackage.Registry.INSTANCE.getEPackage(HadoopClusterPackage.eNS_URI);
        EcorePackage theEcorePackage = (EcorePackage)EPackage.Registry.INSTANCE.getEPackage(EcorePackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        oozieConnectionEClass.getESuperTypes().add(theHadoopClusterPackage.getHadoopSubConnection());
        oozieConnectionItemEClass.getESuperTypes().add(theHadoopClusterPackage.getHadoopSubConnectionItem());

        // Initialize classes and features; add operations and parameters
        initEClass(oozieConnectionEClass, OozieConnection.class, "OozieConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOozieConnection_UserName(), theEcorePackage.getEString(), "userName", null, 0, 1, OozieConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOozieConnection_OozieEndPoind(), theEcorePackage.getEString(), "oozieEndPoind", null, 0, 1, OozieConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOozieConnection_OozieVersion(), theEcorePackage.getEString(), "oozieVersion", null, 0, 1, OozieConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOozieConnection_EnableKerberos(), ecorePackage.getEBoolean(), "enableKerberos", null, 0, 1, OozieConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(oozieConnectionItemEClass, OozieConnectionItem.class, "OozieConnectionItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);
    }

} //OoziePackageImpl
