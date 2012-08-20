/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hdfs;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

import org.talend.core.model.metadata.builder.connection.ConnectionPackage;

import org.talend.core.model.properties.PropertiesPackage;

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
 * @see org.talend.repository.model.hdfs.HDFSFactory
 * @model kind="package"
 * @generated
 */
public interface HDFSPackage extends EPackage {
    /**
     * The package name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "hdfs";

    /**
     * The package namespace URI.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "http://www.talend.org/hdfs";

    /**
     * The package namespace name.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "TalendHDFS";

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    HDFSPackage eINSTANCE = org.talend.repository.model.hdfs.impl.HDFSPackageImpl.init();

    /**
     * The meta object id for the '{@link org.talend.repository.model.hdfs.impl.HDFSConnectionImpl <em>Connection</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hdfs.impl.HDFSConnectionImpl
     * @see org.talend.repository.model.hdfs.impl.HDFSPackageImpl#getHDFSConnection()
     * @generated
     */
    int HDFS_CONNECTION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__NAME = ConnectionPackage.CONNECTION__NAME;

    /**
     * The feature id for the '<em><b>Visibility</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__VISIBILITY = ConnectionPackage.CONNECTION__VISIBILITY;

    /**
     * The feature id for the '<em><b>Client Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CLIENT_DEPENDENCY = ConnectionPackage.CONNECTION__CLIENT_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Supplier Dependency</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__SUPPLIER_DEPENDENCY = ConnectionPackage.CONNECTION__SUPPLIER_DEPENDENCY;

    /**
     * The feature id for the '<em><b>Constraint</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CONSTRAINT = ConnectionPackage.CONNECTION__CONSTRAINT;

    /**
     * The feature id for the '<em><b>Namespace</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__NAMESPACE = ConnectionPackage.CONNECTION__NAMESPACE;

    /**
     * The feature id for the '<em><b>Importer</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__IMPORTER = ConnectionPackage.CONNECTION__IMPORTER;

    /**
     * The feature id for the '<em><b>Stereotype</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__STEREOTYPE = ConnectionPackage.CONNECTION__STEREOTYPE;

    /**
     * The feature id for the '<em><b>Tagged Value</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__TAGGED_VALUE = ConnectionPackage.CONNECTION__TAGGED_VALUE;

    /**
     * The feature id for the '<em><b>Document</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DOCUMENT = ConnectionPackage.CONNECTION__DOCUMENT;

    /**
     * The feature id for the '<em><b>Description</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DESCRIPTION = ConnectionPackage.CONNECTION__DESCRIPTION;

    /**
     * The feature id for the '<em><b>Responsible Party</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__RESPONSIBLE_PARTY = ConnectionPackage.CONNECTION__RESPONSIBLE_PARTY;

    /**
     * The feature id for the '<em><b>Element Node</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__ELEMENT_NODE = ConnectionPackage.CONNECTION__ELEMENT_NODE;

    /**
     * The feature id for the '<em><b>Set</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__SET = ConnectionPackage.CONNECTION__SET;

    /**
     * The feature id for the '<em><b>Rendered Object</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__RENDERED_OBJECT = ConnectionPackage.CONNECTION__RENDERED_OBJECT;

    /**
     * The feature id for the '<em><b>Vocabulary Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__VOCABULARY_ELEMENT = ConnectionPackage.CONNECTION__VOCABULARY_ELEMENT;

    /**
     * The feature id for the '<em><b>Measurement</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__MEASUREMENT = ConnectionPackage.CONNECTION__MEASUREMENT;

    /**
     * The feature id for the '<em><b>Change Request</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CHANGE_REQUEST = ConnectionPackage.CONNECTION__CHANGE_REQUEST;

    /**
     * The feature id for the '<em><b>Dasdl Property</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DASDL_PROPERTY = ConnectionPackage.CONNECTION__DASDL_PROPERTY;

    /**
     * The feature id for the '<em><b>Properties</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__PROPERTIES = ConnectionPackage.CONNECTION__PROPERTIES;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__ID = ConnectionPackage.CONNECTION__ID;

    /**
     * The feature id for the '<em><b>Comment</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__COMMENT = ConnectionPackage.CONNECTION__COMMENT;

    /**
     * The feature id for the '<em><b>Label</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__LABEL = ConnectionPackage.CONNECTION__LABEL;

    /**
     * The feature id for the '<em><b>Read Only</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__READ_ONLY = ConnectionPackage.CONNECTION__READ_ONLY;

    /**
     * The feature id for the '<em><b>Synchronised</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__SYNCHRONISED = ConnectionPackage.CONNECTION__SYNCHRONISED;

    /**
     * The feature id for the '<em><b>Divergency</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DIVERGENCY = ConnectionPackage.CONNECTION__DIVERGENCY;

    /**
     * The feature id for the '<em><b>Owned Element</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__OWNED_ELEMENT = ConnectionPackage.CONNECTION__OWNED_ELEMENT;

    /**
     * The feature id for the '<em><b>Imported Element</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__IMPORTED_ELEMENT = ConnectionPackage.CONNECTION__IMPORTED_ELEMENT;

    /**
     * The feature id for the '<em><b>Data Manager</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DATA_MANAGER = ConnectionPackage.CONNECTION__DATA_MANAGER;

    /**
     * The feature id for the '<em><b>Pathname</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__PATHNAME = ConnectionPackage.CONNECTION__PATHNAME;

    /**
     * The feature id for the '<em><b>Machine</b></em>' container reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__MACHINE = ConnectionPackage.CONNECTION__MACHINE;

    /**
     * The feature id for the '<em><b>Deployed Software System</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DEPLOYED_SOFTWARE_SYSTEM = ConnectionPackage.CONNECTION__DEPLOYED_SOFTWARE_SYSTEM;

    /**
     * The feature id for the '<em><b>Component</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__COMPONENT = ConnectionPackage.CONNECTION__COMPONENT;

    /**
     * The feature id for the '<em><b>Is Case Sensitive</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__IS_CASE_SENSITIVE = ConnectionPackage.CONNECTION__IS_CASE_SENSITIVE;

    /**
     * The feature id for the '<em><b>Client Connection</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CLIENT_CONNECTION = ConnectionPackage.CONNECTION__CLIENT_CONNECTION;

    /**
     * The feature id for the '<em><b>Data Package</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DATA_PACKAGE = ConnectionPackage.CONNECTION__DATA_PACKAGE;

    /**
     * The feature id for the '<em><b>Resource Connection</b></em>' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__RESOURCE_CONNECTION = ConnectionPackage.CONNECTION__RESOURCE_CONNECTION;

    /**
     * The feature id for the '<em><b>Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__VERSION = ConnectionPackage.CONNECTION__VERSION;

    /**
     * The feature id for the '<em><b>Queries</b></em>' containment reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__QUERIES = ConnectionPackage.CONNECTION__QUERIES;

    /**
     * The feature id for the '<em><b>Context Mode</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CONTEXT_MODE = ConnectionPackage.CONNECTION__CONTEXT_MODE;

    /**
     * The feature id for the '<em><b>Context Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CONTEXT_ID = ConnectionPackage.CONNECTION__CONTEXT_ID;

    /**
     * The feature id for the '<em><b>Context Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__CONTEXT_NAME = ConnectionPackage.CONNECTION__CONTEXT_NAME;

    /**
     * The feature id for the '<em><b>Distribution</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DISTRIBUTION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 0;

    /**
     * The feature id for the '<em><b>Df Version</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DF_VERSION = ConnectionPackage.CONNECTION_FEATURE_COUNT + 1;

    /**
     * The feature id for the '<em><b>Df Drivers</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__DF_DRIVERS = ConnectionPackage.CONNECTION_FEATURE_COUNT + 2;

    /**
     * The feature id for the '<em><b>Name Node URI</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__NAME_NODE_URI = ConnectionPackage.CONNECTION_FEATURE_COUNT + 3;

    /**
     * The feature id for the '<em><b>Enable Kerberos</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__ENABLE_KERBEROS = ConnectionPackage.CONNECTION_FEATURE_COUNT + 4;

    /**
     * The feature id for the '<em><b>Principal</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__PRINCIPAL = ConnectionPackage.CONNECTION_FEATURE_COUNT + 5;

    /**
     * The feature id for the '<em><b>User Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__USER_NAME = ConnectionPackage.CONNECTION_FEATURE_COUNT + 6;

    /**
     * The feature id for the '<em><b>Group</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__GROUP = ConnectionPackage.CONNECTION_FEATURE_COUNT + 7;

    /**
     * The feature id for the '<em><b>Row Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__ROW_SEPARATOR = ConnectionPackage.CONNECTION_FEATURE_COUNT + 8;

    /**
     * The feature id for the '<em><b>Field Separator</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION__FIELD_SEPARATOR = ConnectionPackage.CONNECTION_FEATURE_COUNT + 9;

    /**
     * The number of structural features of the '<em>Connection</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_FEATURE_COUNT = ConnectionPackage.CONNECTION_FEATURE_COUNT + 10;

    /**
     * The meta object id for the '{@link org.talend.repository.model.hdfs.impl.HDFSConnectionItemImpl <em>Connection Item</em>}' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see org.talend.repository.model.hdfs.impl.HDFSConnectionItemImpl
     * @see org.talend.repository.model.hdfs.impl.HDFSPackageImpl#getHDFSConnectionItem()
     * @generated
     */
    int HDFS_CONNECTION_ITEM = 1;

    /**
     * The feature id for the '<em><b>Property</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM__PROPERTY = PropertiesPackage.CONNECTION_ITEM__PROPERTY;

    /**
     * The feature id for the '<em><b>State</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM__STATE = PropertiesPackage.CONNECTION_ITEM__STATE;

    /**
     * The feature id for the '<em><b>Parent</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM__PARENT = PropertiesPackage.CONNECTION_ITEM__PARENT;

    /**
     * The feature id for the '<em><b>Reference Resources</b></em>' reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM__REFERENCE_RESOURCES = PropertiesPackage.CONNECTION_ITEM__REFERENCE_RESOURCES;

    /**
     * The feature id for the '<em><b>File Extension</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM__FILE_EXTENSION = PropertiesPackage.CONNECTION_ITEM__FILE_EXTENSION;

    /**
     * The feature id for the '<em><b>Connection</b></em>' reference.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM__CONNECTION = PropertiesPackage.CONNECTION_ITEM__CONNECTION;

    /**
     * The number of structural features of the '<em>Connection Item</em>' class.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int HDFS_CONNECTION_ITEM_FEATURE_COUNT = PropertiesPackage.CONNECTION_ITEM_FEATURE_COUNT + 0;


    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hdfs.HDFSConnection <em>Connection</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection
     * @generated
     */
    EClass getHDFSConnection();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getDistribution <em>Distribution</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Distribution</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getDistribution()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_Distribution();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getDfVersion <em>Df Version</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Df Version</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getDfVersion()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_DfVersion();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getDfDrivers <em>Df Drivers</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Df Drivers</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getDfDrivers()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_DfDrivers();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getNameNodeURI <em>Name Node URI</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name Node URI</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getNameNodeURI()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_NameNodeURI();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#isEnableKerberos <em>Enable Kerberos</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Enable Kerberos</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#isEnableKerberos()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_EnableKerberos();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getPrincipal <em>Principal</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Principal</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getPrincipal()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_Principal();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getUserName <em>User Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>User Name</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getUserName()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_UserName();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getGroup <em>Group</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Group</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getGroup()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_Group();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getRowSeparator <em>Row Separator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Row Separator</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getRowSeparator()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_RowSeparator();

    /**
     * Returns the meta object for the attribute '{@link org.talend.repository.model.hdfs.HDFSConnection#getFieldSeparator <em>Field Separator</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Field Separator</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnection#getFieldSeparator()
     * @see #getHDFSConnection()
     * @generated
     */
    EAttribute getHDFSConnection_FieldSeparator();

    /**
     * Returns the meta object for class '{@link org.talend.repository.model.hdfs.HDFSConnectionItem <em>Connection Item</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for class '<em>Connection Item</em>'.
     * @see org.talend.repository.model.hdfs.HDFSConnectionItem
     * @generated
     */
    EClass getHDFSConnectionItem();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    HDFSFactory getHDFSFactory();

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
         * The meta object literal for the '{@link org.talend.repository.model.hdfs.impl.HDFSConnectionImpl <em>Connection</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hdfs.impl.HDFSConnectionImpl
         * @see org.talend.repository.model.hdfs.impl.HDFSPackageImpl#getHDFSConnection()
         * @generated
         */
        EClass HDFS_CONNECTION = eINSTANCE.getHDFSConnection();

        /**
         * The meta object literal for the '<em><b>Distribution</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__DISTRIBUTION = eINSTANCE.getHDFSConnection_Distribution();

        /**
         * The meta object literal for the '<em><b>Df Version</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__DF_VERSION = eINSTANCE.getHDFSConnection_DfVersion();

        /**
         * The meta object literal for the '<em><b>Df Drivers</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__DF_DRIVERS = eINSTANCE.getHDFSConnection_DfDrivers();

        /**
         * The meta object literal for the '<em><b>Name Node URI</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__NAME_NODE_URI = eINSTANCE.getHDFSConnection_NameNodeURI();

        /**
         * The meta object literal for the '<em><b>Enable Kerberos</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__ENABLE_KERBEROS = eINSTANCE.getHDFSConnection_EnableKerberos();

        /**
         * The meta object literal for the '<em><b>Principal</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__PRINCIPAL = eINSTANCE.getHDFSConnection_Principal();

        /**
         * The meta object literal for the '<em><b>User Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__USER_NAME = eINSTANCE.getHDFSConnection_UserName();

        /**
         * The meta object literal for the '<em><b>Group</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__GROUP = eINSTANCE.getHDFSConnection_Group();

        /**
         * The meta object literal for the '<em><b>Row Separator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__ROW_SEPARATOR = eINSTANCE.getHDFSConnection_RowSeparator();

        /**
         * The meta object literal for the '<em><b>Field Separator</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute HDFS_CONNECTION__FIELD_SEPARATOR = eINSTANCE.getHDFSConnection_FieldSeparator();

        /**
         * The meta object literal for the '{@link org.talend.repository.model.hdfs.impl.HDFSConnectionItemImpl <em>Connection Item</em>}' class.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @see org.talend.repository.model.hdfs.impl.HDFSConnectionItemImpl
         * @see org.talend.repository.model.hdfs.impl.HDFSPackageImpl#getHDFSConnectionItem()
         * @generated
         */
        EClass HDFS_CONNECTION_ITEM = eINSTANCE.getHDFSConnectionItem();

    }

} //HDFSPackage
