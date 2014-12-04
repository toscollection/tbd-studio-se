/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.util;

import java.util.List;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;

import org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData;

import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;
import org.talend.designer.gefabstractmap.model.abstractmap.MapperTableEntity;

import org.talend.designer.pigmap.model.emf.pigmap.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage
 * @generated
 */
public class PigmapSwitch<T> {
    /**
     * The cached model package
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static PigmapPackage modelPackage;

    /**
     * Creates an instance of the switch.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PigmapSwitch() {
        if (modelPackage == null) {
            modelPackage = PigmapPackage.eINSTANCE;
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    public T doSwitch(EObject theEObject) {
        return doSwitch(theEObject.eClass(), theEObject);
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(EClass theEClass, EObject theEObject) {
        if (theEClass.eContainer() == modelPackage) {
            return doSwitch(theEClass.getClassifierID(), theEObject);
        }
        else {
            List<EClass> eSuperTypes = theEClass.getESuperTypes();
            return
                eSuperTypes.isEmpty() ?
                    defaultCase(theEObject) :
                    doSwitch(eSuperTypes.get(0), theEObject);
        }
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case PigmapPackage.PIG_MAP_DATA: {
                PigMapData pigMapData = (PigMapData)theEObject;
                T result = casePigMapData(pigMapData);
                if (result == null) result = caseAbstractExternalData(pigMapData);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.ABSTRACT_IN_OUT_TABLE: {
                AbstractInOutTable abstractInOutTable = (AbstractInOutTable)theEObject;
                T result = caseAbstractInOutTable(abstractInOutTable);
                if (result == null) result = caseMapperTable(abstractInOutTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.INPUT_TABLE: {
                InputTable inputTable = (InputTable)theEObject;
                T result = caseInputTable(inputTable);
                if (result == null) result = caseAbstractInOutTable(inputTable);
                if (result == null) result = caseMapperTable(inputTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.OUTPUT_TABLE: {
                OutputTable outputTable = (OutputTable)theEObject;
                T result = caseOutputTable(outputTable);
                if (result == null) result = caseAbstractInOutTable(outputTable);
                if (result == null) result = caseMapperTable(outputTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.VAR_TABLE: {
                VarTable varTable = (VarTable)theEObject;
                T result = caseVarTable(varTable);
                if (result == null) result = caseMapperTable(varTable);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.ABSTRACT_NODE: {
                AbstractNode abstractNode = (AbstractNode)theEObject;
                T result = caseAbstractNode(abstractNode);
                if (result == null) result = caseMapperTableEntity(abstractNode);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.TABLE_NODE: {
                TableNode tableNode = (TableNode)theEObject;
                T result = caseTableNode(tableNode);
                if (result == null) result = caseAbstractNode(tableNode);
                if (result == null) result = caseMapperTableEntity(tableNode);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.VAR_NODE: {
                VarNode varNode = (VarNode)theEObject;
                T result = caseVarNode(varNode);
                if (result == null) result = caseAbstractNode(varNode);
                if (result == null) result = caseMapperTableEntity(varNode);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.ICONNECTION: {
                IConnection iConnection = (IConnection)theEObject;
                T result = caseIConnection(iConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.INODE_CONNECTION: {
                INodeConnection iNodeConnection = (INodeConnection)theEObject;
                T result = caseINodeConnection(iNodeConnection);
                if (result == null) result = caseIConnection(iNodeConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.CONNECTION: {
                Connection connection = (Connection)theEObject;
                T result = caseConnection(connection);
                if (result == null) result = caseINodeConnection(connection);
                if (result == null) result = caseIConnection(connection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.LOOKUP_CONNECTION: {
                LookupConnection lookupConnection = (LookupConnection)theEObject;
                T result = caseLookupConnection(lookupConnection);
                if (result == null) result = caseINodeConnection(lookupConnection);
                if (result == null) result = caseIConnection(lookupConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            case PigmapPackage.FILTER_CONNECTION: {
                FilterConnection filterConnection = (FilterConnection)theEObject;
                T result = caseFilterConnection(filterConnection);
                if (result == null) result = caseIConnection(filterConnection);
                if (result == null) result = defaultCase(theEObject);
                return result;
            }
            default: return defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Pig Map Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Pig Map Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T casePigMapData(PigMapData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract In Out Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract In Out Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractInOutTable(AbstractInOutTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Input Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Input Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseInputTable(InputTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Output Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Output Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOutputTable(OutputTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Var Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Var Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseVarTable(VarTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract Node</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract Node</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractNode(AbstractNode object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Table Node</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Table Node</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTableNode(TableNode object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Var Node</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Var Node</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseVarNode(VarNode object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>IConnection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>IConnection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIConnection(IConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>INode Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>INode Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseINodeConnection(INodeConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConnection(Connection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Lookup Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Lookup Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLookupConnection(LookupConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Filter Connection</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Filter Connection</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFilterConnection(FilterConnection object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Abstract External Data</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Abstract External Data</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseAbstractExternalData(AbstractExternalData object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Mapper Table</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Mapper Table</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMapperTable(MapperTable object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Mapper Table Entity</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Mapper Table Entity</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseMapperTableEntity(MapperTableEntity object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
     * <!-- begin-user-doc -->
     * This implementation returns null;
     * returning a non-null result will terminate the switch, but this is the last case anyway.
     * <!-- end-user-doc -->
     * @param object the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    public T defaultCase(EObject object) {
        return null;
    }

} //PigmapSwitch
