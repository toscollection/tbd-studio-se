/**
 * // ============================================================================ // // Copyright (C) 2006-2007 Talend
 * Inc. - www.talend.com // // This source code is available under agreement available at //
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt // // You should have received a
 * copy of the agreement // along with this program; if not, write to Talend SA // 9 rue Pages 92150 Suresnes, France // //
 * ============================================================================
 * 
 * $Id$
 */
package org.talend.designer.components.ecosystem.model;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc --> The <b>Package</b> for the model. It contains accessors for the meta objects to represent
 * <ul>
 * <li>each class,</li>
 * <li>each feature of each class,</li>
 * <li>each enum,</li>
 * <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.talend.designer.components.ecosystem.model.EcosystemFactory
 * @model kind="package"
 * @generated
 */
public interface EcosystemPackage extends EPackage {

    /**
     * The package name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNAME = "model"; //$NON-NLS-1$

    /**
     * The package namespace URI.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_URI = "model"; //$NON-NLS-1$

    /**
     * The package namespace name.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    String eNS_PREFIX = "model"; //$NON-NLS-1$

    /**
     * The singleton instance of the package.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    EcosystemPackage eINSTANCE = org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl.init();

    /**
     * The meta object id for the '{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl <em>Component Extension</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl
     * @see org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl#getComponentExtension()
     * @generated
     */
    int COMPONENT_EXTENSION = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__NAME = 0;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__DESCRIPTION = 1;

    /**
     * The feature id for the '<em><b>Language</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__LANGUAGE = 2;

    /**
     * The feature id for the '<em><b>Revisions</b></em>' containment reference list.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__REVISIONS = 3;

    /**
     * The feature id for the '<em><b>Installed Revision</b></em>' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__INSTALLED_REVISION = 4;

    /**
     * The feature id for the '<em><b>Latest Revision</b></em>' reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__LATEST_REVISION = 5;

    /**
     * The feature id for the '<em><b>Installed Location</b></em>' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__INSTALLED_LOCATION = 6;

    /**
     * The feature id for the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION__AUTHOR = 7;

    /**
     * The number of structural features of the '<em>Component Extension</em>' class.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     * @ordered
     */
    int COMPONENT_EXTENSION_FEATURE_COUNT = 8;

    /**
     * The meta object id for the '{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl <em>Revision</em>}' class.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.talend.designer.components.ecosystem.model.impl.RevisionImpl
     * @see org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl#getRevision()
     * @generated
     */
    int REVISION = 1;

    /**
     * The feature id for the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REVISION__ID = 0;

    /**
     * The feature id for the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REVISION__NAME = 1;

    /**
     * The feature id for the '<em><b>Description</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     * @ordered
     */
    int REVISION__DESCRIPTION = 2;

    /**
     * The feature id for the '<em><b>Url</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REVISION__URL = 3;

    /**
     * The feature id for the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REVISION__DATE = 4;

    /**
     * The feature id for the '<em><b>Extension</b></em>' container reference.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @generated
     * @ordered
     */
    int REVISION__EXTENSION = 5;

    /**
     * The feature id for the '<em><b>File Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     * @ordered
     */
    int REVISION__FILE_NAME = 6;

    /**
     * The number of structural features of the '<em>Revision</em>' class. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @generated
     * @ordered
     */
    int REVISION_FEATURE_COUNT = 7;

    /**
     * The meta object id for the '{@link org.talend.designer.components.ecosystem.model.Language <em>Language</em>}' enum.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @see org.talend.designer.components.ecosystem.model.Language
     * @see org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl#getLanguage()
     * @generated
     */
    int LANGUAGE = 2;

    /**
     * Returns the meta object for class '{@link org.talend.designer.components.ecosystem.model.ComponentExtension <em>Component Extension</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Component Extension</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension
     * @generated
     */
    EClass getComponentExtension();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getName()
     * @see #getComponentExtension()
     * @generated
     */
    EAttribute getComponentExtension_Name();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getDescription()
     * @see #getComponentExtension()
     * @generated
     */
    EAttribute getComponentExtension_Description();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getLanguage <em>Language</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Language</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getLanguage()
     * @see #getComponentExtension()
     * @generated
     */
    EAttribute getComponentExtension_Language();

    /**
     * Returns the meta object for the containment reference list '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getRevisions <em>Revisions</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the containment reference list '<em>Revisions</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getRevisions()
     * @see #getComponentExtension()
     * @generated
     */
    EReference getComponentExtension_Revisions();

    /**
     * Returns the meta object for the reference '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledRevision <em>Installed Revision</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Installed Revision</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledRevision()
     * @see #getComponentExtension()
     * @generated
     */
    EReference getComponentExtension_InstalledRevision();

    /**
     * Returns the meta object for the reference '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getLatestRevision <em>Latest Revision</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the reference '<em>Latest Revision</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getLatestRevision()
     * @see #getComponentExtension()
     * @generated
     */
    EReference getComponentExtension_LatestRevision();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledLocation <em>Installed Location</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Installed Location</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledLocation()
     * @see #getComponentExtension()
     * @generated
     */
    EAttribute getComponentExtension_InstalledLocation();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getAuthor <em>Author</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Author</em>'.
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getAuthor()
     * @see #getComponentExtension()
     * @generated
     */
    EAttribute getComponentExtension_Author();

    /**
     * Returns the meta object for class '{@link org.talend.designer.components.ecosystem.model.Revision <em>Revision</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for class '<em>Revision</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision
     * @generated
     */
    EClass getRevision();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.Revision#getId <em>Id</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Id</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getId()
     * @see #getRevision()
     * @generated
     */
    EAttribute getRevision_Id();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.Revision#getName <em>Name</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Name</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getName()
     * @see #getRevision()
     * @generated
     */
    EAttribute getRevision_Name();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.Revision#getDescription <em>Description</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Description</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getDescription()
     * @see #getRevision()
     * @generated
     */
    EAttribute getRevision_Description();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.Revision#getUrl <em>Url</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Url</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getUrl()
     * @see #getRevision()
     * @generated
     */
    EAttribute getRevision_Url();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.Revision#getDate <em>Date</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>Date</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getDate()
     * @see #getRevision()
     * @generated
     */
    EAttribute getRevision_Date();

    /**
     * Returns the meta object for the container reference '{@link org.talend.designer.components.ecosystem.model.Revision#getExtension <em>Extension</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for the container reference '<em>Extension</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getExtension()
     * @see #getRevision()
     * @generated
     */
    EReference getRevision_Extension();

    /**
     * Returns the meta object for the attribute '{@link org.talend.designer.components.ecosystem.model.Revision#getFileName <em>File Name</em>}'.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @return the meta object for the attribute '<em>File Name</em>'.
     * @see org.talend.designer.components.ecosystem.model.Revision#getFileName()
     * @see #getRevision()
     * @generated
     */
    EAttribute getRevision_FileName();

    /**
     * Returns the meta object for enum '{@link org.talend.designer.components.ecosystem.model.Language <em>Language</em>}'.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the meta object for enum '<em>Language</em>'.
     * @see org.talend.designer.components.ecosystem.model.Language
     * @generated
     */
    EEnum getLanguage();

    /**
     * Returns the factory that creates the instances of the model.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @return the factory that creates the instances of the model.
     * @generated
     */
    EcosystemFactory getEcosystemFactory();

    /**
     * <!-- begin-user-doc --> Defines literals for the meta objects that represent
     * <ul>
     * <li>each class,</li>
     * <li>each feature of each class,</li>
     * <li>each enum,</li>
     * <li>and each data type</li>
     * </ul>
     * <!-- end-user-doc -->
     * @generated
     */
    interface Literals {

        /**
         * The meta object literal for the '{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl <em>Component Extension</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl
         * @see org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl#getComponentExtension()
         * @generated
         */
        EClass COMPONENT_EXTENSION = eINSTANCE.getComponentExtension();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute COMPONENT_EXTENSION__NAME = eINSTANCE.getComponentExtension_Name();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPONENT_EXTENSION__DESCRIPTION = eINSTANCE.getComponentExtension_Description();

        /**
         * The meta object literal for the '<em><b>Language</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute COMPONENT_EXTENSION__LANGUAGE = eINSTANCE.getComponentExtension_Language();

        /**
         * The meta object literal for the '<em><b>Revisions</b></em>' containment reference list feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference COMPONENT_EXTENSION__REVISIONS = eINSTANCE.getComponentExtension_Revisions();

        /**
         * The meta object literal for the '<em><b>Installed Revision</b></em>' reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference COMPONENT_EXTENSION__INSTALLED_REVISION = eINSTANCE.getComponentExtension_InstalledRevision();

        /**
         * The meta object literal for the '<em><b>Latest Revision</b></em>' reference feature.
         * <!-- begin-user-doc
         * --> <!-- end-user-doc -->
         * @generated
         */
        EReference COMPONENT_EXTENSION__LATEST_REVISION = eINSTANCE.getComponentExtension_LatestRevision();

        /**
         * The meta object literal for the '<em><b>Installed Location</b></em>' attribute feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EAttribute COMPONENT_EXTENSION__INSTALLED_LOCATION = eINSTANCE.getComponentExtension_InstalledLocation();

        /**
         * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute COMPONENT_EXTENSION__AUTHOR = eINSTANCE.getComponentExtension_Author();

        /**
         * The meta object literal for the '{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl <em>Revision</em>}' class.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.talend.designer.components.ecosystem.model.impl.RevisionImpl
         * @see org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl#getRevision()
         * @generated
         */
        EClass REVISION = eINSTANCE.getRevision();

        /**
         * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute REVISION__ID = eINSTANCE.getRevision_Id();

        /**
         * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute REVISION__NAME = eINSTANCE.getRevision_Name();

        /**
         * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REVISION__DESCRIPTION = eINSTANCE.getRevision_Description();

        /**
         * The meta object literal for the '<em><b>Url</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute REVISION__URL = eINSTANCE.getRevision_Url();

        /**
         * The meta object literal for the '<em><b>Date</b></em>' attribute feature.
         * <!-- begin-user-doc --> <!--
         * end-user-doc -->
         * @generated
         */
        EAttribute REVISION__DATE = eINSTANCE.getRevision_Date();

        /**
         * The meta object literal for the '<em><b>Extension</b></em>' container reference feature. <!--
         * begin-user-doc --> <!-- end-user-doc -->
         * 
         * @generated
         */
        EReference REVISION__EXTENSION = eINSTANCE.getRevision_Extension();

        /**
         * The meta object literal for the '<em><b>File Name</b></em>' attribute feature.
         * <!-- begin-user-doc -->
         * <!-- end-user-doc -->
         * @generated
         */
        EAttribute REVISION__FILE_NAME = eINSTANCE.getRevision_FileName();

        /**
         * The meta object literal for the '{@link org.talend.designer.components.ecosystem.model.Language <em>Language</em>}' enum.
         * <!-- begin-user-doc --> <!-- end-user-doc -->
         * @see org.talend.designer.components.ecosystem.model.Language
         * @see org.talend.designer.components.ecosystem.model.impl.EcosystemPackageImpl#getLanguage()
         * @generated
         */
        EEnum LANGUAGE = eINSTANCE.getLanguage();

    }

} // EcosystemPackage
