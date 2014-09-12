/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.talend.designer.core.model.utils.emf.talendfile.TalendFilePackage;

import org.talend.designer.gefabstractmap.model.abstractmap.AbstractmapPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory
 * @model kind="package"
 * @generated
 */
public interface PigmapPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "pigmap";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.talend.org/pigmap";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "TalendPigMap";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    PigmapPackage eINSTANCE = org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl.init();

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl <em>Pig Map Data</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getPigMapData()
     * @generated
     */
    int PIG_MAP_DATA = 0;

    /**
     * The feature id for the '<em><b>Input Tables</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PIG_MAP_DATA__INPUT_TABLES = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Output Tables</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PIG_MAP_DATA__OUTPUT_TABLES = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Var Tables</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PIG_MAP_DATA__VAR_TABLES = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Connections</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PIG_MAP_DATA__CONNECTIONS = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 3;

    /**
     * The number of structural features of the '<em>Pig Map Data</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int PIG_MAP_DATA_FEATURE_COUNT = TalendFilePackage.ABSTRACT_EXTERNAL_DATA_FEATURE_COUNT + 4;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl <em>Abstract In Out Table</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getAbstractInOutTable()
     * @generated
     */
    int ABSTRACT_IN_OUT_TABLE = 1;

    /**
     * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__NODES = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Activate Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Activate Condensed Tool</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Minimized</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__MINIMIZED = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__NAME = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Filter Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 6;

    /**
     * The number of structural features of the '<em>Abstract In Out Table</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 7;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl <em>Input Table</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getInputTable()
     * @generated
     */
    int INPUT_TABLE = 2;

    /**
     * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__NODES = ABSTRACT_IN_OUT_TABLE__NODES;

    /**
     * The feature id for the '<em><b>Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__EXPRESSION_FILTER = ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER;

    /**
     * The feature id for the '<em><b>Activate Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__ACTIVATE_EXPRESSION_FILTER = ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER;

    /**
     * The feature id for the '<em><b>Activate Condensed Tool</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__ACTIVATE_CONDENSED_TOOL = ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL;

    /**
     * The feature id for the '<em><b>Minimized</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__MINIMIZED = ABSTRACT_IN_OUT_TABLE__MINIMIZED;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__NAME = ABSTRACT_IN_OUT_TABLE__NAME;

    /**
     * The feature id for the '<em><b>Filter Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__FILTER_INCOMING_CONNECTIONS = ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Lookup</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__LOOKUP = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Join Model</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__JOIN_MODEL = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Join Optimization</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__JOIN_OPTIMIZATION = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Custom Partitioner</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__CUSTOM_PARTITIONER = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Increase Parallelism</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE__INCREASE_PARALLELISM = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Input Table</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INPUT_TABLE_FEATURE_COUNT = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl <em>Output Table</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getOutputTable()
     * @generated
     */
    int OUTPUT_TABLE = 3;

    /**
     * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__NODES = ABSTRACT_IN_OUT_TABLE__NODES;

    /**
     * The feature id for the '<em><b>Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__EXPRESSION_FILTER = ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER;

    /**
     * The feature id for the '<em><b>Activate Expression Filter</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__ACTIVATE_EXPRESSION_FILTER = ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER;

    /**
     * The feature id for the '<em><b>Activate Condensed Tool</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__ACTIVATE_CONDENSED_TOOL = ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL;

    /**
     * The feature id for the '<em><b>Minimized</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__MINIMIZED = ABSTRACT_IN_OUT_TABLE__MINIMIZED;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__NAME = ABSTRACT_IN_OUT_TABLE__NAME;

    /**
     * The feature id for the '<em><b>Filter Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__FILTER_INCOMING_CONNECTIONS = ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Reject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__REJECT = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Reject Inner Join</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__REJECT_INNER_JOIN = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Error Reject</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__ERROR_REJECT = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>All In One</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__ALL_IN_ONE = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Enable Empty Element</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 4;

    /**
     * The number of structural features of the '<em>Output Table</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int OUTPUT_TABLE_FEATURE_COUNT = ABSTRACT_IN_OUT_TABLE_FEATURE_COUNT + 5;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.VarTableImpl <em>Var Table</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.VarTableImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getVarTable()
     * @generated
     */
    int VAR_TABLE = 4;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_TABLE__NAME = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Minimized</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_TABLE__MINIMIZED = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_TABLE__NODES = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Var Table</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_TABLE_FEATURE_COUNT = AbstractmapPackage.MAPPER_TABLE_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl <em>Abstract Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getAbstractNode()
     * @generated
     */
    int ABSTRACT_NODE = 5;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__NAME = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__EXPRESSION = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__TYPE = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__KEY = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Pattern</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__PATTERN = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Nullable</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__NULLABLE = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__OUTGOING_CONNECTIONS = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__INCOMING_CONNECTIONS = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Filter Out Going Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 8;

    /**
     * The number of structural features of the '<em>Abstract Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ABSTRACT_NODE_FEATURE_COUNT = AbstractmapPackage.MAPPER_TABLE_ENTITY_FEATURE_COUNT + 9;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.TableNodeImpl <em>Table Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.TableNodeImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getTableNode()
     * @generated
     */
    int TABLE_NODE = 6;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__NAME = ABSTRACT_NODE__NAME;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__EXPRESSION = ABSTRACT_NODE__EXPRESSION;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__TYPE = ABSTRACT_NODE__TYPE;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__KEY = ABSTRACT_NODE__KEY;

    /**
     * The feature id for the '<em><b>Pattern</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__PATTERN = ABSTRACT_NODE__PATTERN;

    /**
     * The feature id for the '<em><b>Nullable</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__NULLABLE = ABSTRACT_NODE__NULLABLE;

    /**
     * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__OUTGOING_CONNECTIONS = ABSTRACT_NODE__OUTGOING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__INCOMING_CONNECTIONS = ABSTRACT_NODE__INCOMING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Filter Out Going Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__FILTER_OUT_GOING_CONNECTIONS = ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Lookup Outgoing Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS = ABSTRACT_NODE_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Lookup Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS = ABSTRACT_NODE_FEATURE_COUNT + 1;

    /**
     * The number of structural features of the '<em>Table Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int TABLE_NODE_FEATURE_COUNT = ABSTRACT_NODE_FEATURE_COUNT + 2;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.VarNodeImpl <em>Var Node</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.VarNodeImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getVarNode()
     * @generated
     */
    int VAR_NODE = 7;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__NAME = ABSTRACT_NODE__NAME;

    /**
     * The feature id for the '<em><b>Expression</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__EXPRESSION = ABSTRACT_NODE__EXPRESSION;

    /**
     * The feature id for the '<em><b>Type</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__TYPE = ABSTRACT_NODE__TYPE;

    /**
     * The feature id for the '<em><b>Key</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__KEY = ABSTRACT_NODE__KEY;

    /**
     * The feature id for the '<em><b>Pattern</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__PATTERN = ABSTRACT_NODE__PATTERN;

    /**
     * The feature id for the '<em><b>Nullable</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__NULLABLE = ABSTRACT_NODE__NULLABLE;

    /**
     * The feature id for the '<em><b>Outgoing Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__OUTGOING_CONNECTIONS = ABSTRACT_NODE__OUTGOING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Incoming Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__INCOMING_CONNECTIONS = ABSTRACT_NODE__INCOMING_CONNECTIONS;

    /**
     * The feature id for the '<em><b>Filter Out Going Connections</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE__FILTER_OUT_GOING_CONNECTIONS = ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS;

    /**
     * The number of structural features of the '<em>Var Node</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int VAR_NODE_FEATURE_COUNT = ABSTRACT_NODE_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.IConnection <em>IConnection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.IConnection
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getIConnection()
     * @generated
     */
    int ICONNECTION = 8;

    /**
     * The number of structural features of the '<em>IConnection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int ICONNECTION_FEATURE_COUNT = 0;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection <em>INode Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getINodeConnection()
     * @generated
     */
    int INODE_CONNECTION = 9;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INODE_CONNECTION__NAME = ICONNECTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INODE_CONNECTION__SOURCE = ICONNECTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INODE_CONNECTION__TARGET = ICONNECTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>INode Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int INODE_CONNECTION_FEATURE_COUNT = ICONNECTION_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getConnection()
     * @generated
     */
    int CONNECTION = 10;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTION__NAME = INODE_CONNECTION__NAME;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTION__SOURCE = INODE_CONNECTION__SOURCE;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTION__TARGET = INODE_CONNECTION__TARGET;

    /**
     * The number of structural features of the '<em>Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int CONNECTION_FEATURE_COUNT = INODE_CONNECTION_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.LookupConnectionImpl <em>Lookup Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.LookupConnectionImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getLookupConnection()
     * @generated
     */
    int LOOKUP_CONNECTION = 11;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOKUP_CONNECTION__NAME = INODE_CONNECTION__NAME;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOKUP_CONNECTION__SOURCE = INODE_CONNECTION__SOURCE;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOKUP_CONNECTION__TARGET = INODE_CONNECTION__TARGET;

    /**
     * The number of structural features of the '<em>Lookup Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int LOOKUP_CONNECTION_FEATURE_COUNT = INODE_CONNECTION_FEATURE_COUNT + 0;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.FilterConnectionImpl <em>Filter Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.FilterConnectionImpl
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getFilterConnection()
     * @generated
     */
    int FILTER_CONNECTION = 12;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FILTER_CONNECTION__NAME = ICONNECTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Source</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FILTER_CONNECTION__SOURCE = ICONNECTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Target</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FILTER_CONNECTION__TARGET = ICONNECTION_FEATURE_COUNT + 2;

    /**
     * The number of structural features of the '<em>Filter Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int FILTER_CONNECTION_FEATURE_COUNT = ICONNECTION_FEATURE_COUNT + 3;

    /**
     * The meta object id for the '{@link org.talend.designer.pigmap.model.emf.pigmap.NodeType <em>Node Type</em>}' enum.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.designer.pigmap.model.emf.pigmap.NodeType
     * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getNodeType()
     * @generated
     */
    int NODE_TYPE = 13;


    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData <em>Pig Map Data</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Pig Map Data</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigMapData
     * @generated
     */
    EClass getPigMapData();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getInputTables <em>Input Tables</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Input Tables</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getInputTables()
     * @see #getPigMapData()
     * @generated
     */
    EReference getPigMapData_InputTables();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getOutputTables <em>Output Tables</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Output Tables</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getOutputTables()
     * @see #getPigMapData()
     * @generated
     */
    EReference getPigMapData_OutputTables();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getVarTables <em>Var Tables</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Var Tables</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getVarTables()
     * @see #getPigMapData()
     * @generated
     */
    EReference getPigMapData_VarTables();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getConnections <em>Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigMapData#getConnections()
     * @see #getPigMapData()
     * @generated
     */
    EReference getPigMapData_Connections();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable <em>Abstract In Out Table</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Abstract In Out Table</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable
     * @generated
     */
    EClass getAbstractInOutTable();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getNodes <em>Nodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Nodes</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getNodes()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EReference getAbstractInOutTable_Nodes();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getExpressionFilter <em>Expression Filter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Expression Filter</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getExpressionFilter()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EAttribute getAbstractInOutTable_ExpressionFilter();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateExpressionFilter <em>Activate Expression Filter</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activate Expression Filter</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateExpressionFilter()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EAttribute getAbstractInOutTable_ActivateExpressionFilter();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateCondensedTool <em>Activate Condensed Tool</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Activate Condensed Tool</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isActivateCondensedTool()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EAttribute getAbstractInOutTable_ActivateCondensedTool();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isMinimized <em>Minimized</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Minimized</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#isMinimized()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EAttribute getAbstractInOutTable_Minimized();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getName()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EAttribute getAbstractInOutTable_Name();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getFilterIncomingConnections <em>Filter Incoming Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Filter Incoming Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable#getFilterIncomingConnections()
     * @see #getAbstractInOutTable()
     * @generated
     */
    EReference getAbstractInOutTable_FilterIncomingConnections();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable <em>Input Table</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Input Table</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable
     * @generated
     */
    EClass getInputTable();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#isLookup <em>Lookup</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Lookup</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable#isLookup()
     * @see #getInputTable()
     * @generated
     */
    EAttribute getInputTable_Lookup();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinModel <em>Join Model</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Join Model</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinModel()
     * @see #getInputTable()
     * @generated
     */
    EAttribute getInputTable_JoinModel();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinOptimization <em>Join Optimization</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Join Optimization</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable#getJoinOptimization()
     * @see #getInputTable()
     * @generated
     */
    EAttribute getInputTable_JoinOptimization();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getCustomPartitioner <em>Custom Partitioner</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Custom Partitioner</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable#getCustomPartitioner()
     * @see #getInputTable()
     * @generated
     */
    EAttribute getInputTable_CustomPartitioner();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.InputTable#getIncreaseParallelism <em>Increase Parallelism</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Increase Parallelism</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.InputTable#getIncreaseParallelism()
     * @see #getInputTable()
     * @generated
     */
    EAttribute getInputTable_IncreaseParallelism();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable <em>Output Table</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Output Table</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable
     * @generated
     */
    EClass getOutputTable();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isReject <em>Reject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reject</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isReject()
     * @see #getOutputTable()
     * @generated
     */
    EAttribute getOutputTable_Reject();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isRejectInnerJoin <em>Reject Inner Join</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Reject Inner Join</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isRejectInnerJoin()
     * @see #getOutputTable()
     * @generated
     */
    EAttribute getOutputTable_RejectInnerJoin();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isErrorReject <em>Error Reject</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Error Reject</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isErrorReject()
     * @see #getOutputTable()
     * @generated
     */
    EAttribute getOutputTable_ErrorReject();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isAllInOne <em>All In One</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>All In One</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isAllInOne()
     * @see #getOutputTable()
     * @generated
     */
    EAttribute getOutputTable_AllInOne();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isEnableEmptyElement <em>Enable Empty Element</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enable Empty Element</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.OutputTable#isEnableEmptyElement()
     * @see #getOutputTable()
     * @generated
     */
    EAttribute getOutputTable_EnableEmptyElement();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable <em>Var Table</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Var Table</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarTable
     * @generated
     */
    EClass getVarTable();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarTable#getName()
     * @see #getVarTable()
     * @generated
     */
    EAttribute getVarTable_Name();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#isMinimized <em>Minimized</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Minimized</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarTable#isMinimized()
     * @see #getVarTable()
     * @generated
     */
    EAttribute getVarTable_Minimized();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.VarTable#getNodes <em>Nodes</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Nodes</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarTable#getNodes()
     * @see #getVarTable()
     * @generated
     */
    EReference getVarTable_Nodes();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode <em>Abstract Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Abstract Node</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode
     * @generated
     */
    EClass getAbstractNode();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getName()
     * @see #getAbstractNode()
     * @generated
     */
    EAttribute getAbstractNode_Name();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getExpression <em>Expression</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Expression</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getExpression()
     * @see #getAbstractNode()
     * @generated
     */
    EAttribute getAbstractNode_Expression();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getType <em>Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Type</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getType()
     * @see #getAbstractNode()
     * @generated
     */
    EAttribute getAbstractNode_Type();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isKey <em>Key</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Key</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isKey()
     * @see #getAbstractNode()
     * @generated
     */
    EAttribute getAbstractNode_Key();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getPattern <em>Pattern</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Pattern</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getPattern()
     * @see #getAbstractNode()
     * @generated
     */
    EAttribute getAbstractNode_Pattern();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isNullable <em>Nullable</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Nullable</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#isNullable()
     * @see #getAbstractNode()
     * @generated
     */
    EAttribute getAbstractNode_Nullable();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getOutgoingConnections <em>Outgoing Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Outgoing Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getOutgoingConnections()
     * @see #getAbstractNode()
     * @generated
     */
    EReference getAbstractNode_OutgoingConnections();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getIncomingConnections <em>Incoming Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Incoming Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getIncomingConnections()
     * @see #getAbstractNode()
     * @generated
     */
    EReference getAbstractNode_IncomingConnections();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getFilterOutGoingConnections <em>Filter Out Going Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Filter Out Going Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.AbstractNode#getFilterOutGoingConnections()
     * @see #getAbstractNode()
     * @generated
     */
    EReference getAbstractNode_FilterOutGoingConnections();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.TableNode <em>Table Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Table Node</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.TableNode
     * @generated
     */
    EClass getTableNode();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.TableNode#getLookupOutgoingConnections <em>Lookup Outgoing Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Lookup Outgoing Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.TableNode#getLookupOutgoingConnections()
     * @see #getTableNode()
     * @generated
     */
    EReference getTableNode_LookupOutgoingConnections();

    /**
     * Returns the meta object for the reference list '{@link org.talend.designer.pigmap.model.emf.pigmap.TableNode#getLookupIncomingConnections <em>Lookup Incoming Connections</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference list '<em>Lookup Incoming Connections</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.TableNode#getLookupIncomingConnections()
     * @see #getTableNode()
     * @generated
     */
    EReference getTableNode_LookupIncomingConnections();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.VarNode <em>Var Node</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Var Node</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.VarNode
     * @generated
     */
    EClass getVarNode();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.IConnection <em>IConnection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>IConnection</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.IConnection
     * @generated
     */
    EClass getIConnection();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection <em>INode Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>INode Connection</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection
     * @generated
     */
    EClass getINodeConnection();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection#getName()
     * @see #getINodeConnection()
     * @generated
     */
    EAttribute getINodeConnection_Name();

    /**
     * Returns the meta object for the reference '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Source</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection#getSource()
     * @see #getINodeConnection()
     * @generated
     */
    EReference getINodeConnection_Source();

    /**
     * Returns the meta object for the reference '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection#getTarget()
     * @see #getINodeConnection()
     * @generated
     */
    EReference getINodeConnection_Target();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.Connection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.Connection
     * @generated
     */
    EClass getConnection();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.LookupConnection <em>Lookup Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Lookup Connection</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.LookupConnection
     * @generated
     */
    EClass getLookupConnection();

    /**
     * Returns the meta object for class '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection <em>Filter Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Filter Connection</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.FilterConnection
     * @generated
     */
    EClass getFilterConnection();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getName <em>Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getName()
     * @see #getFilterConnection()
     * @generated
     */
    EAttribute getFilterConnection_Name();

    /**
     * Returns the meta object for the reference '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getSource <em>Source</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Source</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getSource()
     * @see #getFilterConnection()
     * @generated
     */
    EReference getFilterConnection_Source();

    /**
     * Returns the meta object for the reference '{@link org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getTarget <em>Target</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Target</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.FilterConnection#getTarget()
     * @see #getFilterConnection()
     * @generated
     */
    EReference getFilterConnection_Target();

    /**
     * Returns the meta object for enum '{@link org.talend.designer.pigmap.model.emf.pigmap.NodeType <em>Node Type</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for enum '<em>Node Type</em>'.
     * @see org.talend.designer.pigmap.model.emf.pigmap.NodeType
     * @generated
     */
    EEnum getNodeType();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    PigmapFactory getPigmapFactory();

    /**
     * <!-- begin-user-doc -->
     * Defines literals for the meta objects that represent
     * <ul>
     *   <li>each class,</li>
     *   <li>each feature of each class,</li>
     *   <li>each enum,</li>
     *   <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {
        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl <em>Pig Map Data</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigMapDataImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getPigMapData()
         * @generated
         */
        EClass PIG_MAP_DATA = eINSTANCE.getPigMapData();

        /**
         * The meta object literal for the '<em><b>Input Tables</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PIG_MAP_DATA__INPUT_TABLES = eINSTANCE.getPigMapData_InputTables();

        /**
         * The meta object literal for the '<em><b>Output Tables</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PIG_MAP_DATA__OUTPUT_TABLES = eINSTANCE.getPigMapData_OutputTables();

        /**
         * The meta object literal for the '<em><b>Var Tables</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PIG_MAP_DATA__VAR_TABLES = eINSTANCE.getPigMapData_VarTables();

        /**
         * The meta object literal for the '<em><b>Connections</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference PIG_MAP_DATA__CONNECTIONS = eINSTANCE.getPigMapData_Connections();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl <em>Abstract In Out Table</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractInOutTableImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getAbstractInOutTable()
         * @generated
         */
        EClass ABSTRACT_IN_OUT_TABLE = eINSTANCE.getAbstractInOutTable();

        /**
         * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ABSTRACT_IN_OUT_TABLE__NODES = eINSTANCE.getAbstractInOutTable_Nodes();

        /**
         * The meta object literal for the '<em><b>Expression Filter</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_IN_OUT_TABLE__EXPRESSION_FILTER = eINSTANCE.getAbstractInOutTable_ExpressionFilter();

        /**
         * The meta object literal for the '<em><b>Activate Expression Filter</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_IN_OUT_TABLE__ACTIVATE_EXPRESSION_FILTER = eINSTANCE.getAbstractInOutTable_ActivateExpressionFilter();

        /**
         * The meta object literal for the '<em><b>Activate Condensed Tool</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_IN_OUT_TABLE__ACTIVATE_CONDENSED_TOOL = eINSTANCE.getAbstractInOutTable_ActivateCondensedTool();

        /**
         * The meta object literal for the '<em><b>Minimized</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_IN_OUT_TABLE__MINIMIZED = eINSTANCE.getAbstractInOutTable_Minimized();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_IN_OUT_TABLE__NAME = eINSTANCE.getAbstractInOutTable_Name();

        /**
         * The meta object literal for the '<em><b>Filter Incoming Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ABSTRACT_IN_OUT_TABLE__FILTER_INCOMING_CONNECTIONS = eINSTANCE.getAbstractInOutTable_FilterIncomingConnections();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl <em>Input Table</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.InputTableImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getInputTable()
         * @generated
         */
        EClass INPUT_TABLE = eINSTANCE.getInputTable();

        /**
         * The meta object literal for the '<em><b>Lookup</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_TABLE__LOOKUP = eINSTANCE.getInputTable_Lookup();

        /**
         * The meta object literal for the '<em><b>Join Model</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_TABLE__JOIN_MODEL = eINSTANCE.getInputTable_JoinModel();

        /**
         * The meta object literal for the '<em><b>Join Optimization</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_TABLE__JOIN_OPTIMIZATION = eINSTANCE.getInputTable_JoinOptimization();

        /**
         * The meta object literal for the '<em><b>Custom Partitioner</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_TABLE__CUSTOM_PARTITIONER = eINSTANCE.getInputTable_CustomPartitioner();

        /**
         * The meta object literal for the '<em><b>Increase Parallelism</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INPUT_TABLE__INCREASE_PARALLELISM = eINSTANCE.getInputTable_IncreaseParallelism();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl <em>Output Table</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.OutputTableImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getOutputTable()
         * @generated
         */
        EClass OUTPUT_TABLE = eINSTANCE.getOutputTable();

        /**
         * The meta object literal for the '<em><b>Reject</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TABLE__REJECT = eINSTANCE.getOutputTable_Reject();

        /**
         * The meta object literal for the '<em><b>Reject Inner Join</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TABLE__REJECT_INNER_JOIN = eINSTANCE.getOutputTable_RejectInnerJoin();

        /**
         * The meta object literal for the '<em><b>Error Reject</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TABLE__ERROR_REJECT = eINSTANCE.getOutputTable_ErrorReject();

        /**
         * The meta object literal for the '<em><b>All In One</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TABLE__ALL_IN_ONE = eINSTANCE.getOutputTable_AllInOne();

        /**
         * The meta object literal for the '<em><b>Enable Empty Element</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute OUTPUT_TABLE__ENABLE_EMPTY_ELEMENT = eINSTANCE.getOutputTable_EnableEmptyElement();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.VarTableImpl <em>Var Table</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.VarTableImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getVarTable()
         * @generated
         */
        EClass VAR_TABLE = eINSTANCE.getVarTable();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VAR_TABLE__NAME = eINSTANCE.getVarTable_Name();

        /**
         * The meta object literal for the '<em><b>Minimized</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute VAR_TABLE__MINIMIZED = eINSTANCE.getVarTable_Minimized();

        /**
         * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference VAR_TABLE__NODES = eINSTANCE.getVarTable_Nodes();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl <em>Abstract Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.AbstractNodeImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getAbstractNode()
         * @generated
         */
        EClass ABSTRACT_NODE = eINSTANCE.getAbstractNode();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_NODE__NAME = eINSTANCE.getAbstractNode_Name();

        /**
         * The meta object literal for the '<em><b>Expression</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_NODE__EXPRESSION = eINSTANCE.getAbstractNode_Expression();

        /**
         * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_NODE__TYPE = eINSTANCE.getAbstractNode_Type();

        /**
         * The meta object literal for the '<em><b>Key</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_NODE__KEY = eINSTANCE.getAbstractNode_Key();

        /**
         * The meta object literal for the '<em><b>Pattern</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_NODE__PATTERN = eINSTANCE.getAbstractNode_Pattern();

        /**
         * The meta object literal for the '<em><b>Nullable</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute ABSTRACT_NODE__NULLABLE = eINSTANCE.getAbstractNode_Nullable();

        /**
         * The meta object literal for the '<em><b>Outgoing Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ABSTRACT_NODE__OUTGOING_CONNECTIONS = eINSTANCE.getAbstractNode_OutgoingConnections();

        /**
         * The meta object literal for the '<em><b>Incoming Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ABSTRACT_NODE__INCOMING_CONNECTIONS = eINSTANCE.getAbstractNode_IncomingConnections();

        /**
         * The meta object literal for the '<em><b>Filter Out Going Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference ABSTRACT_NODE__FILTER_OUT_GOING_CONNECTIONS = eINSTANCE.getAbstractNode_FilterOutGoingConnections();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.TableNodeImpl <em>Table Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.TableNodeImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getTableNode()
         * @generated
         */
        EClass TABLE_NODE = eINSTANCE.getTableNode();

        /**
         * The meta object literal for the '<em><b>Lookup Outgoing Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TABLE_NODE__LOOKUP_OUTGOING_CONNECTIONS = eINSTANCE.getTableNode_LookupOutgoingConnections();

        /**
         * The meta object literal for the '<em><b>Lookup Incoming Connections</b></em>' reference list feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference TABLE_NODE__LOOKUP_INCOMING_CONNECTIONS = eINSTANCE.getTableNode_LookupIncomingConnections();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.VarNodeImpl <em>Var Node</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.VarNodeImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getVarNode()
         * @generated
         */
        EClass VAR_NODE = eINSTANCE.getVarNode();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.IConnection <em>IConnection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.IConnection
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getIConnection()
         * @generated
         */
        EClass ICONNECTION = eINSTANCE.getIConnection();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.INodeConnection <em>INode Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.INodeConnection
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getINodeConnection()
         * @generated
         */
        EClass INODE_CONNECTION = eINSTANCE.getINodeConnection();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute INODE_CONNECTION__NAME = eINSTANCE.getINodeConnection_Name();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INODE_CONNECTION__SOURCE = eINSTANCE.getINodeConnection_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference INODE_CONNECTION__TARGET = eINSTANCE.getINodeConnection_Target();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl <em>Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.ConnectionImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getConnection()
         * @generated
         */
        EClass CONNECTION = eINSTANCE.getConnection();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.LookupConnectionImpl <em>Lookup Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.LookupConnectionImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getLookupConnection()
         * @generated
         */
        EClass LOOKUP_CONNECTION = eINSTANCE.getLookupConnection();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.impl.FilterConnectionImpl <em>Filter Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.FilterConnectionImpl
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getFilterConnection()
         * @generated
         */
        EClass FILTER_CONNECTION = eINSTANCE.getFilterConnection();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute FILTER_CONNECTION__NAME = eINSTANCE.getFilterConnection_Name();

        /**
         * The meta object literal for the '<em><b>Source</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FILTER_CONNECTION__SOURCE = eINSTANCE.getFilterConnection_Source();

        /**
         * The meta object literal for the '<em><b>Target</b></em>' reference feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EReference FILTER_CONNECTION__TARGET = eINSTANCE.getFilterConnection_Target();

        /**
         * The meta object literal for the '{@link org.talend.designer.pigmap.model.emf.pigmap.NodeType <em>Node Type</em>}' enum.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.designer.pigmap.model.emf.pigmap.NodeType
         * @see org.talend.designer.pigmap.model.emf.pigmap.impl.PigmapPackageImpl#getNodeType()
         * @generated
         */
        EEnum NODE_TYPE = eINSTANCE.getNodeType();

    }

} //PigmapPackage
