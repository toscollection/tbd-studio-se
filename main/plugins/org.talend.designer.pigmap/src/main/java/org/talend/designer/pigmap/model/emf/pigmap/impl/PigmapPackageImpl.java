/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

import org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage;

import org.talend.designer.gefabstractmap.model.abstractmap.AbstractmapPackage;

import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.Connection;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.IConnection;
import org.talend.designer.pigmap.model.emf.pigmap.INodeConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.LookupConnection;
import org.talend.designer.pigmap.model.emf.pigmap.NodeType;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarNode;
import org.talend.designer.pigmap.model.emf.pigmap.VarTable;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PigmapPackageImpl extends EPackageImpl implements PigmapPackage {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass pigMapDataEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass abstractInOutTableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass inputTableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass outputTableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass varTableEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass abstractNodeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass tableNodeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass varNodeEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass iConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass iNodeConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass connectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass lookupConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EClass filterConnectionEClass = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    private EEnum nodeTypeEEnum = null;

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
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#eNS_URI
     * @see #init()
     * @generated
     */
    private PigmapPackageImpl() {
        super(eNS_URI, PigmapFactory.eINSTANCE);
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
     * <p>This method is used to initialize {@link PigmapPackage#eINSTANCE} when that field is accessed.
     * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #eNS_URI
     * @see #createPackageContents()
     * @see #initializePackageContents()
     * @generated
     */
    public static PigmapPackage init() {
        if (isInited) return (PigmapPackage)EPackage.Registry.INSTANCE.getEPackage(PigmapPackage.eNS_URI);

        // Obtain or create and register package
        PigmapPackageImpl thePigmapPackage = (PigmapPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof PigmapPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new PigmapPackageImpl());

        isInited = true;

        // Initialize simple dependencies
        AbstractmapPackage.eINSTANCE.eClass();
        TalendFilePackage.eINSTANCE.eClass();

        // Create package meta-data objects
        thePigmapPackage.createPackageContents();

        // Initialize created meta-data
        thePigmapPackage.initializePackageContents();

        // Mark meta-data to indicate it can't be changed
        thePigmapPackage.freeze();

  
        // Update the registry and return the package
        EPackage.Registry.INSTANCE.put(PigmapPackage.eNS_URI, thePigmapPackage);
        return thePigmapPackage;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getPigMapData() {
        return pigMapDataEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPigMapData_InputTables() {
        return (EReference)pigMapDataEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPigMapData_OutputTables() {
        return (EReference)pigMapDataEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPigMapData_VarTables() {
        return (EReference)pigMapDataEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getPigMapData_Connections() {
        return (EReference)pigMapDataEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAbstractInOutTable() {
        return abstractInOutTableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractInOutTable_Nodes() {
        return (EReference)abstractInOutTableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractInOutTable_ExpressionFilter() {
        return (EAttribute)abstractInOutTableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractInOutTable_ActivateExpressionFilter() {
        return (EAttribute)abstractInOutTableEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractInOutTable_ActivateCondensedTool() {
        return (EAttribute)abstractInOutTableEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractInOutTable_Minimized() {
        return (EAttribute)abstractInOutTableEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractInOutTable_Name() {
        return (EAttribute)abstractInOutTableEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractInOutTable_FilterIncomingConnections() {
        return (EReference)abstractInOutTableEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getInputTable() {
        return inputTableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getInputTable_Lookup() {
        return (EAttribute)inputTableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getInputTable_JoinModel() {
        return (EAttribute)inputTableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getInputTable_JoinOptimization() {
        return (EAttribute)inputTableEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getInputTable_CustomPartitioner() {
        return (EAttribute)inputTableEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getInputTable_IncreaseParallelism() {
        return (EAttribute)inputTableEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getOutputTable() {
        return outputTableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOutputTable_Reject() {
        return (EAttribute)outputTableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOutputTable_RejectInnerJoin() {
        return (EAttribute)outputTableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOutputTable_ErrorReject() {
        return (EAttribute)outputTableEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOutputTable_AllInOne() {
        return (EAttribute)outputTableEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getOutputTable_EnableEmptyElement() {
        return (EAttribute)outputTableEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getVarTable() {
        return varTableEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getVarTable_Name() {
        return (EAttribute)varTableEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getVarTable_Minimized() {
        return (EAttribute)varTableEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getVarTable_Nodes() {
        return (EReference)varTableEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getAbstractNode() {
        return abstractNodeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractNode_Name() {
        return (EAttribute)abstractNodeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractNode_Expression() {
        return (EAttribute)abstractNodeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractNode_Type() {
        return (EAttribute)abstractNodeEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractNode_Key() {
        return (EAttribute)abstractNodeEClass.getEStructuralFeatures().get(3);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractNode_Pattern() {
        return (EAttribute)abstractNodeEClass.getEStructuralFeatures().get(4);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getAbstractNode_Nullable() {
        return (EAttribute)abstractNodeEClass.getEStructuralFeatures().get(5);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractNode_OutgoingConnections() {
        return (EReference)abstractNodeEClass.getEStructuralFeatures().get(6);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractNode_IncomingConnections() {
        return (EReference)abstractNodeEClass.getEStructuralFeatures().get(7);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getAbstractNode_FilterOutGoingConnections() {
        return (EReference)abstractNodeEClass.getEStructuralFeatures().get(8);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getTableNode() {
        return tableNodeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTableNode_LookupOutgoingConnections() {
        return (EReference)tableNodeEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getTableNode_LookupIncomingConnections() {
        return (EReference)tableNodeEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getVarNode() {
        return varNodeEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getIConnection() {
        return iConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getINodeConnection() {
        return iNodeConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getINodeConnection_Name() {
        return (EAttribute)iNodeConnectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getINodeConnection_Source() {
        return (EReference)iNodeConnectionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getINodeConnection_Target() {
        return (EReference)iNodeConnectionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getConnection() {
        return connectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getLookupConnection() {
        return lookupConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EClass getFilterConnection() {
        return filterConnectionEClass;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EAttribute getFilterConnection_Name() {
        return (EAttribute)filterConnectionEClass.getEStructuralFeatures().get(0);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getFilterConnection_Source() {
        return (EReference)filterConnectionEClass.getEStructuralFeatures().get(1);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EReference getFilterConnection_Target() {
        return (EReference)filterConnectionEClass.getEStructuralFeatures().get(2);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EEnum getNodeType() {
        return nodeTypeEEnum;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PigmapFactory getPigmapFactory() {
        return (PigmapFactory)getEFactoryInstance();
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
        pigMapDataEClass = createEClass(PIG_MAP_DATA);
        createEReference(pigMapDataEClass, PIG_MAP_DATA__INPUT_TABLES);
        createEReference(pigMapDataEClass, PIG_MAP_DATA__OUTPUT_TABLES);
        createEReference(pigMapDataEClass, PIG_MAP_DATA__VAR_TABLES);
        createEReference(pigMapDataEClass, PIG_MAP_DATA__CONNECTIONS);

        abstractInOutTableEClass = createEClass(ABSTRACT_IN_OUT_TABLE);
        createEReference(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__NODES);
        createEAttribute(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER);
        createEAttribute(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER);
        createEAttribute(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL);
        createEAttribute(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__MINIMIZED);
        createEAttribute(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__NAME);
        createEReference(abstractInOutTableEClass, ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS);

        inputTableEClass = createEClass(INPUT_TABLE);
        createEAttribute(inputTableEClass, INPUT_TABLE__LOOKUP);
        createEAttribute(inputTableEClass, INPUT_TABLE__JOIN_MODEL);
        createEAttribute(inputTableEClass, INPUT_TABLE__JOIN_OPTIMIZATION);
        createEAttribute(inputTableEClass, INPUT_TABLE__CUSTOM_PARTITIONER);
        createEAttribute(inputTableEClass, INPUT_TABLE__INCREASE_PARALLELISM);

        outputTableEClass = createEClass(OUTPUT_TABLE);
        createEAttribute(outputTableEClass, OUTPUT_TABLE__REJECT);
        createEAttribute(outputTableEClass, OUTPUT_TABLE__REJECT_INNER_JOIN);
        createEAttribute(outputTableEClass, OUTPUT_TABLE__ERROR_REJECT);
        createEAttribute(outputTableEClass, OUTPUT_TABLE__ALL_IN_ONE);
        createEAttribute(outputTableEClass, OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT);

        varTableEClass = createEClass(VAR_TABLE);
        createEAttribute(varTableEClass, VAR_TABLE__NAME);
        createEAttribute(varTableEClass, VAR_TABLE__MINIMIZED);
        createEReference(varTableEClass, VAR_TABLE__NODES);

        abstractNodeEClass = createEClass(ABSTRACT_NODE);
        createEAttribute(abstractNodeEClass, ABSTRACT_NODE__NAME);
        createEAttribute(abstractNodeEClass, ABSTRACT_NODE__EXPRESSION);
        createEAttribute(abstractNodeEClass, ABSTRACT_NODE__TYPE);
        createEAttribute(abstractNodeEClass, ABSTRACT_NODE__KEY);
        createEAttribute(abstractNodeEClass, ABSTRACT_NODE__PATTERN);
        createEAttribute(abstractNodeEClass, ABSTRACT_NODE__NULLABLE);
        createEReference(abstractNodeEClass, ABSTRACT_NODE__OUTGOING_CONNECTIONS);
        createEReference(abstractNodeEClass, ABSTRACT_NODE__INCOMING_CONNECTIONS);
        createEReference(abstractNodeEClass, ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS);

        tableNodeEClass = createEClass(TABLE_NODE);
        createEReference(tableNodeEClass, TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS);
        createEReference(tableNodeEClass, TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS);

        varNodeEClass = createEClass(VAR_NODE);

        iConnectionEClass = createEClass(ICONNECTION);

        iNodeConnectionEClass = createEClass(INODE_CONNECTION);
        createEAttribute(iNodeConnectionEClass, INODE_CONNECTION__NAME);
        createEReference(iNodeConnectionEClass, INODE_CONNECTION__SOURCE);
        createEReference(iNodeConnectionEClass, INODE_CONNECTION__TARGET);

        connectionEClass = createEClass(CONNECTION);

        lookupConnectionEClass = createEClass(LOOKUP_CONNECTION);

        filterConnectionEClass = createEClass(FILTER_CONNECTION);
        createEAttribute(filterConnectionEClass, FILTER_CONNECTION__NAME);
        createEReference(filterConnectionEClass, FILTER_CONNECTION__SOURCE);
        createEReference(filterConnectionEClass, FILTER_CONNECTION__TARGET);

        // Create enums
        nodeTypeEEnum = createEEnum(NODE_TYPE);
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
        TalendFilePackage theTalendFilePackage = (TalendFilePackage)EPackage.Registry.INSTANCE.getEPackage(TalendFilePackage.eNS_URI);
        AbstractmapPackage theAbstractmapPackage = (AbstractmapPackage)EPackage.Registry.INSTANCE.getEPackage(AbstractmapPackage.eNS_URI);

        // Create type parameters

        // Set bounds for type parameters

        // Add supertypes to classes
        pigMapDataEClass.getESuperTypes().add(theTalendFilePackage.getAbstractExternalData());
        abstractInOutTableEClass.getESuperTypes().add(theAbstractmapPackage.getMapperTable());
        inputTableEClass.getESuperTypes().add(this.getAbstractInOutTable());
        outputTableEClass.getESuperTypes().add(this.getAbstractInOutTable());
        varTableEClass.getESuperTypes().add(theAbstractmapPackage.getMapperTable());
        abstractNodeEClass.getESuperTypes().add(theAbstractmapPackage.getMapperTableEntity());
        tableNodeEClass.getESuperTypes().add(this.getAbstractNode());
        varNodeEClass.getESuperTypes().add(this.getAbstractNode());
        iNodeConnectionEClass.getESuperTypes().add(this.getIConnection());
        connectionEClass.getESuperTypes().add(this.getINodeConnection());
        lookupConnectionEClass.getESuperTypes().add(this.getINodeConnection());
        filterConnectionEClass.getESuperTypes().add(this.getIConnection());

        // Initialize classes and features; add operations and parameters
        initEClass(pigMapDataEClass, PigMapData.class, "PigMapData", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getPigMapData_InputTables(), this.getInputTable(), null, "inputTables", null, 0, -1, PigMapData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPigMapData_OutputTables(), this.getOutputTable(), null, "outputTables", null, 0, -1, PigMapData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPigMapData_VarTables(), this.getVarTable(), null, "varTables", null, 0, -1, PigMapData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getPigMapData_Connections(), this.getIConnection(), null, "connections", null, 0, -1, PigMapData.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(abstractInOutTableEClass, AbstractInOutTable.class, "AbstractInOutTable", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getAbstractInOutTable_Nodes(), this.getTableNode(), null, "nodes", null, 0, -1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractInOutTable_ExpressionFilter(), ecorePackage.getEString(), "expressionFilter", null, 0, 1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractInOutTable_ActivateExpressionFilter(), ecorePackage.getEBoolean(), "activateExpressionFilter", null, 0, 1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractInOutTable_ActivateCondensedTool(), ecorePackage.getEBoolean(), "activateCondensedTool", null, 0, 1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractInOutTable_Minimized(), ecorePackage.getEBoolean(), "minimized", null, 0, 1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractInOutTable_Name(), ecorePackage.getEString(), "name", null, 0, 1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAbstractInOutTable_FilterIncomingConnections(), this.getFilterConnection(), null, "filterIncomingConnections", null, 0, -1, AbstractInOutTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(inputTableEClass, InputTable.class, "InputTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getInputTable_Lookup(), ecorePackage.getEBoolean(), "lookup", null, 0, 1, InputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getInputTable_JoinModel(), ecorePackage.getEString(), "joinModel", null, 0, 1, InputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getInputTable_JoinOptimization(), ecorePackage.getEString(), "joinOptimization", null, 0, 1, InputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getInputTable_CustomPartitioner(), ecorePackage.getEString(), "customPartitioner", null, 0, 1, InputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getInputTable_IncreaseParallelism(), ecorePackage.getEString(), "increaseParallelism", null, 0, 1, InputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(outputTableEClass, OutputTable.class, "OutputTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getOutputTable_Reject(), ecorePackage.getEBoolean(), "reject", null, 0, 1, OutputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOutputTable_RejectInnerJoin(), ecorePackage.getEBoolean(), "rejectInnerJoin", null, 0, 1, OutputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOutputTable_ErrorReject(), ecorePackage.getEBoolean(), "errorReject", null, 0, 1, OutputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOutputTable_AllInOne(), ecorePackage.getEBoolean(), "allInOne", null, 0, 1, OutputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getOutputTable_EnableEmptyElement(), ecorePackage.getEBoolean(), "enableEmptyElement", "true", 0, 1, OutputTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(varTableEClass, VarTable.class, "VarTable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getVarTable_Name(), ecorePackage.getEString(), "name", null, 0, 1, VarTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getVarTable_Minimized(), ecorePackage.getEBoolean(), "minimized", null, 0, 1, VarTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getVarTable_Nodes(), this.getVarNode(), null, "nodes", null, 0, -1, VarTable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(abstractNodeEClass, AbstractNode.class, "AbstractNode", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getAbstractNode_Name(), ecorePackage.getEString(), "name", null, 0, 1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractNode_Expression(), ecorePackage.getEString(), "expression", null, 0, 1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractNode_Type(), ecorePackage.getEString(), "type", null, 0, 1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractNode_Key(), ecorePackage.getEBoolean(), "key", null, 0, 1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractNode_Pattern(), ecorePackage.getEString(), "pattern", null, 0, 1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEAttribute(getAbstractNode_Nullable(), ecorePackage.getEBoolean(), "nullable", null, 0, 1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAbstractNode_OutgoingConnections(), this.getConnection(), null, "outgoingConnections", null, 0, -1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAbstractNode_IncomingConnections(), this.getConnection(), null, "incomingConnections", null, 0, -1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getAbstractNode_FilterOutGoingConnections(), this.getFilterConnection(), null, "filterOutGoingConnections", null, 0, -1, AbstractNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(tableNodeEClass, TableNode.class, "TableNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEReference(getTableNode_LookupOutgoingConnections(), this.getLookupConnection(), null, "lookupOutgoingConnections", null, 0, -1, TableNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getTableNode_LookupIncomingConnections(), this.getLookupConnection(), null, "lookupIncomingConnections", null, 0, -1, TableNode.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(varNodeEClass, VarNode.class, "VarNode", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(iConnectionEClass, IConnection.class, "IConnection", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(iNodeConnectionEClass, INodeConnection.class, "INodeConnection", IS_ABSTRACT, IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getINodeConnection_Name(), ecorePackage.getEString(), "name", null, 0, 1, INodeConnection.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getINodeConnection_Source(), this.getAbstractNode(), null, "source", null, 0, 1, INodeConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getINodeConnection_Target(), this.getAbstractNode(), null, "target", null, 0, 1, INodeConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        initEClass(connectionEClass, Connection.class, "Connection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(lookupConnectionEClass, LookupConnection.class, "LookupConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

        initEClass(filterConnectionEClass, FilterConnection.class, "FilterConnection", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
        initEAttribute(getFilterConnection_Name(), ecorePackage.getEString(), "name", null, 0, 1, FilterConnection.class, IS_TRANSIENT, IS_VOLATILE, !IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFilterConnection_Source(), this.getAbstractNode(), null, "source", null, 0, 1, FilterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
        initEReference(getFilterConnection_Target(), this.getAbstractInOutTable(), null, "target", null, 0, 1, FilterConnection.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

        // Initialize enums and add enum literals
        initEEnum(nodeTypeEEnum, NodeType.class, "NodeType");
        addEEnumLiteral(nodeTypeEEnum, NodeType.ELEMENT);
        addEEnumLiteral(nodeTypeEEnum, NodeType.ATTRIBUT);
        addEEnumLiteral(nodeTypeEEnum, NodeType.NAME_SPACE);

        // Create resource
        createResource(eNS_URI);
    }

} //PigmapPackageImpl
