/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap.util;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

import org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData;

import org.talend.designer.gefabstractmap.model.abstractmap.MapperTable;
import org.talend.designer.gefabstractmap.model.abstractmap.MapperTableEntity;

import org.talend.designer.pigmap.model.emf.pigmap.*;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage
 * @generated
 */
public class PigmapAdapterFactory extends AdapterFactoryImpl {
    /**
     * The cached model package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected static PigmapPackage modelPackage;

    /**
     * Creates an instance of the adapter factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public PigmapAdapterFactory() {
        if (modelPackage == null) {
            modelPackage = PigmapPackage.eINSTANCE;
        }
    }

    /**
     * Returns whether this factory is applicable for the type of the object.
     * <!-- begin-user-doc -->
     * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
     * <!-- end-user-doc -->
     * @return whether this factory is applicable for the type of the object.
     * @generated
     */
    @Override
    public boolean isFactoryForType(Object object) {
        if (object == modelPackage) {
            return true;
        }
        if (object instanceof EObject) {
            return ((EObject)object).eClass().getEPackage() == modelPackage;
        }
        return false;
    }

    /**
     * The switch that delegates to the <code>createXXX</code> methods.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected PigmapSwitch<Adapter> modelSwitch =
        new PigmapSwitch<Adapter>() {
            @Override
            public Adapter casePigMapData(PigMapData object) {
                return createPigMapDataAdapter();
            }
            @Override
            public Adapter caseAbstractInOutTable(AbstractInOutTable object) {
                return createAbstractInOutTableAdapter();
            }
            @Override
            public Adapter caseInputTable(InputTable object) {
                return createInputTableAdapter();
            }
            @Override
            public Adapter caseOutputTable(OutputTable object) {
                return createOutputTableAdapter();
            }
            @Override
            public Adapter caseVarTable(VarTable object) {
                return createVarTableAdapter();
            }
            @Override
            public Adapter caseAbstractNode(AbstractNode object) {
                return createAbstractNodeAdapter();
            }
            @Override
            public Adapter caseTableNode(TableNode object) {
                return createTableNodeAdapter();
            }
            @Override
            public Adapter caseVarNode(VarNode object) {
                return createVarNodeAdapter();
            }
            @Override
            public Adapter caseIConnection(IConnection object) {
                return createIConnectionAdapter();
            }
            @Override
            public Adapter caseINodeConnection(INodeConnection object) {
                return createINodeConnectionAdapter();
            }
            @Override
            public Adapter caseConnection(Connection object) {
                return createConnectionAdapter();
            }
            @Override
            public Adapter caseLookupConnection(LookupConnection object) {
                return createLookupConnectionAdapter();
            }
            @Override
            public Adapter caseFilterConnection(FilterConnection object) {
                return createFilterConnectionAdapter();
            }
            @Override
            public Adapter caseAbstractExternalData(AbstractExternalData object) {
                return createAbstractExternalDataAdapter();
            }
            @Override
            public Adapter caseMapperTable(MapperTable object) {
                return createMapperTableAdapter();
            }
            @Override
            public Adapter caseMapperTableEntity(MapperTableEntity object) {
                return createMapperTableEntityAdapter();
            }
            @Override
            public Adapter defaultCase(EObject object) {
                return createEObjectAdapter();
            }
        };

    /**
     * Creates an adapter for the <code>target</code>.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param target the object to adapt.
     * @return the adapter for the <code>target</code>.
     * @generated
     */
    @Override
    public Adapter createAdapter(Notifier target) {
        return modelSwitch.doSwitch((EObject)target);
    }


    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData <em>Pig Map Data</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigMapData
     * @generated
     */
    public Adapter createPigMapDataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable <em>Abstract In Out Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable
     * @generated
     */
    public Adapter createAbstractInOutTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable <em>Input Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable
     * @generated
     */
    public Adapter createInputTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable <em>Output Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable
     * @generated
     */
    public Adapter createOutputTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable <em>Var Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarTable
     * @generated
     */
    public Adapter createVarTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode <em>Abstract Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode
     * @generated
     */
    public Adapter createAbstractNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.TableNode <em>Table Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.TableNode
     * @generated
     */
    public Adapter createTableNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.VarNode <em>Var Node</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarNode
     * @generated
     */
    public Adapter createVarNodeAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.IConnection <em>IConnection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.IConnection
     * @generated
     */
    public Adapter createIConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection <em>INode Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection
     * @generated
     */
    public Adapter createINodeConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.Connection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.Connection
     * @generated
     */
    public Adapter createConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.LookupConnection <em>Lookup Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.LookupConnection
     * @generated
     */
    public Adapter createLookupConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection <em>Filter Connection</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.pigmap.model.emf.pigmap.FilterConnection
     * @generated
     */
    public Adapter createFilterConnectionAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData <em>Abstract External Data</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.core.model.utils.emf.talendfile.AbstractExternalData
     * @generated
     */
    public Adapter createAbstractExternalDataAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.gefabstractmap.model.abstractmap.MapperTable <em>Mapper Table</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.gefabstractmap.model.abstractmap.MapperTable
     * @generated
     */
    public Adapter createMapperTableAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for an object of class '{@link org.talend.designer.gefabstractmap.model.abstractmap.MapperTableEntity <em>Mapper Table Entity</em>}'.
     * <!-- begin-user-doc -->
     * This default implementation returns null so that we can easily ignore cases;
     * it's useful to ignore a case when inheritance will catch all the cases anyway.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @see org.talend.designer.gefabstractmap.model.abstractmap.MapperTableEntity
     * @generated
     */
    public Adapter createMapperTableEntityAdapter() {
        return null;
    }

    /**
     * Creates a new adapter for the default case.
     * <!-- begin-user-doc -->
     * This default implementation returns null.
     * <!-- end-user-doc -->
     * @return the new adapter.
     * @generated
     */
    public Adapter createEObjectAdapter() {
        return null;
    }

} //PigmapAdapterFactory
