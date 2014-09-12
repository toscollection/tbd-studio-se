/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.impl;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

import org.talend.designer.pigmap.model.emf.pigmap.*;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class PigmapFactoryImpl extends EFactoryImpl implements PigmapFactory {
    /**
     * Creates the default factory implementation.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static PigmapFactory init() {
        try {
            PigmapFactory thePigmapFactory = (PigmapFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.talend.org/pigmap"); 
            if (thePigmapFactory != null) {
                return thePigmapFactory;
            }
        }
        catch (Exception exception) {
            EcorePlugin.INSTANCE.log(exception);
        }
        return new PigmapFactoryImpl();
    }

    /**
     * Creates an instance of the factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PigmapFactoryImpl() {
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
            case PigmapPackage.PIG_MAP_DATA: return createPigMapData();
            case PigmapPackage.INPUT_TABLE: return createInputTable();
            case PigmapPackage.OUTPUT_TABLE: return createOutputTable();
            case PigmapPackage.VAR_TABLE: return createVarTable();
            case PigmapPackage.TABLE_NODE: return createTableNode();
            case PigmapPackage.VAR_NODE: return createVarNode();
            case PigmapPackage.CONNECTION: return createConnection();
            case PigmapPackage.LOOKUP_CONNECTION: return createLookupConnection();
            case PigmapPackage.FILTER_CONNECTION: return createFilterConnection();
            default:
                throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object createFromString(EDataType eDataType, String initialValue) {
        switch (eDataType.getClassifierID()) {
            case PigmapPackage.NODE_TYPE:
                return createNodeTypeFromString(eDataType, initialValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String convertToString(EDataType eDataType, Object instanceValue) {
        switch (eDataType.getClassifierID()) {
            case PigmapPackage.NODE_TYPE:
                return convertNodeTypeToString(eDataType, instanceValue);
            default:
                throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
        }
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PigMapData createPigMapData() {
        PigMapDataImpl pigMapData = new PigMapDataImpl();
        return pigMapData;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public InputTable createInputTable() {
        InputTableImpl inputTable = new InputTableImpl();
        return inputTable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OutputTable createOutputTable() {
        OutputTableImpl outputTable = new OutputTableImpl();
        return outputTable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public VarTable createVarTable() {
        VarTableImpl varTable = new VarTableImpl();
        return varTable;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public TableNode createTableNode() {
        TableNodeImpl tableNode = new TableNodeImpl();
        return tableNode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public VarNode createVarNode() {
        VarNodeImpl varNode = new VarNodeImpl();
        return varNode;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public Connection createConnection() {
        ConnectionImpl connection = new ConnectionImpl();
        return connection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public LookupConnection createLookupConnection() {
        LookupConnectionImpl lookupConnection = new LookupConnectionImpl();
        return lookupConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public FilterConnection createFilterConnection() {
        FilterConnectionImpl filterConnection = new FilterConnectionImpl();
        return filterConnection;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public NodeType createNodeTypeFromString(EDataType eDataType, String initialValue) {
        NodeType result = NodeType.get(initialValue);
        if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
        return result;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public String convertNodeTypeToString(EDataType eDataType, Object instanceValue) {
        return instanceValue == null ? null : instanceValue.toString();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PigmapPackage getPigmapPackage() {
        return (PigmapPackage)getEPackage();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @deprecated
     * @generated
     */
    @Deprecated
    public static PigmapPackage getPackage() {
        return PigmapPackage.eINSTANCE;
    }

} //PigmapFactoryImpl
