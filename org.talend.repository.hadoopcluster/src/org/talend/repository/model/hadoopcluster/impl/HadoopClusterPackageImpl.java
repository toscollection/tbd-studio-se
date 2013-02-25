/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster.impl;

import java.util.Map;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

import org.talend.core.model.metadata.builder.connection.ConnectionPackage;

import org.talend.core.model.properties.PropertiesPackage;

import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;
import org.talend.repository.model.hadoopcluster.HadoopSubConnection;
import org.talend.repository.model.hadoopcluster.HadoopSubConnectionItem;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class HadoopClusterPackageImpl extends EPackageImpl implements HadoopClusterPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hadoopClusterConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hadoopClusterConnectionItemEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hadoopAdditionalPropertiesEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hadoopSubConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass hadoopSubConnectionItemEClass = null;

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
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private HadoopClusterPackageImpl() {
        super(eNS_URI, HadoopClusterFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link HadoopClusterPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static HadoopClusterPackage init() {
        if (isInited) return (HadoopClusterPackage)EPackage.Registry.INSTANCE.getEPackage(HadoopClusterPackage.eNS_URI);

        // Obtain or create and register package
        HadoopClusterPackageImpl theHadoopClusterPackage = (HadoopClusterPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof HadoopClusterPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new HadoopClusterPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        PropertiesPackage.eINSTANCE.eClass();
        XMLTypePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        theHadoopClusterPackage.createPackageContents();

        // Initialize created meta-data
        theHadoopClusterPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        theHadoopClusterPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(HadoopClusterPackage.eNS_URI, theHadoopClusterPackage);
        return theHadoopClusterPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHadoopClusterConnection() {
        return hadoopClusterConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_Distribution() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_DfVersion() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_UseCustomVersion() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_Server() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_NameNodeURI() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_JobTrackerURI() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_EnableKerberos() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_Principal() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_UserName() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_Group() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(9);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopClusterConnection_ConnectionList() {
        return (EAttribute)hadoopClusterConnectionEClass.getEStructuralFeatures().get(10);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getHadoopClusterConnection_Parameters() {
        return (EReference)hadoopClusterConnectionEClass.getEStructuralFeatures().get(11);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHadoopClusterConnectionItem() {
        return hadoopClusterConnectionItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHadoopAdditionalProperties() {
        return hadoopAdditionalPropertiesEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopAdditionalProperties_Key() {
        return (EAttribute)hadoopAdditionalPropertiesEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopAdditionalProperties_Value() {
        return (EAttribute)hadoopAdditionalPropertiesEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHadoopSubConnection() {
        return hadoopSubConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getHadoopSubConnection_RelativeHadoopClusterId() {
        return (EAttribute)hadoopSubConnectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getHadoopSubConnectionItem() {
        return hadoopSubConnectionItemEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public HadoopClusterFactory getHadoopClusterFactory() {
        return (HadoopClusterFactory)getEFactoryInstance();
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
        hadoopClusterConnectionEClass = createEClass(HADOOP_CLUSTER_CONNECTION);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__DISTRIBUTION);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__DF_VERSION);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__USE_CUSTOM_VERSION);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__SERVER);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__NAME_NODE_URI);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__JOB_TRACKER_URI);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__ENABLE_KERBEROS);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__PRINCIPAL);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__USER_NAME);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__GROUP);
        createEAttribute(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__CONNECTION_LIST);
        createEReference(hadoopClusterConnectionEClass, HADOOP_CLUSTER_CONNECTION__PARAMETERS);

        hadoopClusterConnectionItemEClass = createEClass(HADOOP_CLUSTER_CONNECTION_ITEM);

        hadoopAdditionalPropertiesEClass = createEClass(HADOOP_ADDITIONAL_PROPERTIES);
        createEAttribute(hadoopAdditionalPropertiesEClass, HADOOP_ADDITIONAL_PROPERTIES__KEY);
        createEAttribute(hadoopAdditionalPropertiesEClass, HADOOP_ADDITIONAL_PROPERTIES__VALUE);

        hadoopSubConnectionEClass = createEClass(HADOOP_SUB_CONNECTION);
        createEAttribute(hadoopSubConnectionEClass, HADOOP_SUB_CONNECTION__RELATIVE_HADOOP_CLUSTER_ID);

        hadoopSubConnectionItemEClass = createEClass(HADOOP_SUB_CONNECTION_ITEM);
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
        ConnectionPackage theConnectionPackage = (ConnectionPackage)EPackage.Registry.INSTANCE.getEPackage(ConnectionPackage.eNS_URI);
        XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
        PropertiesPackage thePropertiesPackage = (PropertiesPackage)EPackage.Registry.INSTANCE.getEPackage(PropertiesPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        hadoopClusterConnectionEClass.getESuperTypes().add(theConnectionPackage.getConnection());
        hadoopClusterConnectionItemEClass.getESuperTypes().add(thePropertiesPackage.getConnectionItem());
        hadoopSubConnectionEClass.getESuperTypes().add(theConnectionPackage.getConnection());
        hadoopSubConnectionItemEClass.getESuperTypes().add(thePropertiesPackage.getConnectionItem());

        // Initialize classes and features; add operations and parameters
        initEClass(hadoopClusterConnectionEClass, HadoopClusterConnection.class, "HadoopClusterConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getHadoopClusterConnection_Distribution(), theXMLTypePackage.getString(), "distribution", null, 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_DfVersion(), theXMLTypePackage.getString(), "dfVersion", null, 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_UseCustomVersion(), theXMLTypePackage.getBoolean(), "useCustomVersion", null, 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_Server(), theXMLTypePackage.getString(), "server", "localhost", 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_NameNodeURI(), theXMLTypePackage.getString(), "nameNodeURI", "hdfs://localhost:8020", 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_JobTrackerURI(), theXMLTypePackage.getString(), "jobTrackerURI", "localhost:50300", 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_EnableKerberos(), theXMLTypePackage.getBoolean(), "enableKerberos", null, 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_Principal(), theXMLTypePackage.getString(), "principal", "nn/_HOST@EXAMPLE.COM", 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_UserName(), theXMLTypePackage.getString(), "userName", "anonymous", 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_Group(), theXMLTypePackage.getString(), "group", null, 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopClusterConnection_ConnectionList(), ecorePackage.getEString(), "connectionList", null, 0, -1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getHadoopClusterConnection_Parameters(), this.getHadoopAdditionalProperties(), null, "parameters", null, 0, 1, HadoopClusterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hadoopClusterConnectionItemEClass, HadoopClusterConnectionItem.class, "HadoopClusterConnectionItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(hadoopAdditionalPropertiesEClass, Map.Entry.class, "HadoopAdditionalProperties", !IS_ABSTRACT, !IS_INTERFACE, !IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getHadoopAdditionalProperties_Key(), theXMLTypePackage.getString(), "key", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getHadoopAdditionalProperties_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, Map.Entry.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hadoopSubConnectionEClass, HadoopSubConnection.class, "HadoopSubConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getHadoopSubConnection_RelativeHadoopClusterId(), ecorePackage.getEString(), "relativeHadoopClusterId", null, 1, 1, HadoopSubConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(hadoopSubConnectionItemEClass, HadoopSubConnectionItem.class, "HadoopSubConnectionItem", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        // Create resource
        createResource(eNS_URI);

        // Create annotations
        // MapEntry
        createMapEntryAnnotations();
    }

    /**
     * Initializes the annotations for <b>MapEntry</b>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected void createMapEntryAnnotations() {
        String source = "MapEntry";		
        addAnnotation
          (hadoopAdditionalPropertiesEClass, 
           source, 
           new String[] {
           });
    }

} //HadoopClusterPackageImpl
