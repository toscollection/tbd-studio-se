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

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Component Extension</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getDescription <em>Description</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getRevisions <em>Revisions</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledRevision <em>Installed Revision</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getLatestRevision <em>Latest Revision</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledLocation <em>Installed Location</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getAuthor <em>Author</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension()
 * @model
 * @generated
 */
public interface ComponentExtension extends EObject {

    /**
     * Returns the value of the '<em><b>Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Name</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Name</em>' attribute.
     * @see #setName(String)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_Name()
     * @model required="true"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getName <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Name</em>' attribute.
     * @see #getName()
     * @generated
     */
    void setName(String value);

    /**
     * Returns the value of the '<em><b>Description</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Description</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Description</em>' attribute.
     * @see #setDescription(String)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_Description()
     * @model required="true"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Language</b></em>' attribute. The default value is <code>""</code>. The
     * literals are from the enumeration {@link org.talend.designer.components.ecosystem.model.Language}. <!--
     * begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Language</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * 
     * @return the value of the '<em>Language</em>' attribute.
     * @see org.talend.designer.components.ecosystem.model.Language
     * @see #setLanguage(Language)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_Language()
     * @model default="" required="true"
     * @generated
     */
    Language getLanguage();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getLanguage <em>Language</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Language</em>' attribute.
     * @see org.talend.designer.components.ecosystem.model.Language
     * @see #getLanguage()
     * @generated
     */
    void setLanguage(Language value);

    /**
     * Returns the value of the '<em><b>Revisions</b></em>' containment reference list.
     * The list contents are of type {@link org.talend.designer.components.ecosystem.model.Revision}.
     * It is bidirectional and its opposite is '{@link org.talend.designer.components.ecosystem.model.Revision#getExtension <em>Extension</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Revisions</em>' containment reference isn't clear, there really should be more of
     * a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Revisions</em>' containment reference list.
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_Revisions()
     * @see org.talend.designer.components.ecosystem.model.Revision#getExtension
     * @model opposite="extension" containment="true" required="true"
     * @generated
     */
    EList<Revision> getRevisions();

    /**
     * Returns the value of the '<em><b>Installed Revision</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Installed Revision</em>' reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Installed Revision</em>' reference.
     * @see #setInstalledRevision(Revision)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_InstalledRevision()
     * @model
     * @generated
     */
    Revision getInstalledRevision();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledRevision <em>Installed Revision</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Installed Revision</em>' reference.
     * @see #getInstalledRevision()
     * @generated
     */
    void setInstalledRevision(Revision value);

    /**
     * Returns the value of the '<em><b>Latest Revision</b></em>' reference.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Latest Revision</em>' reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Latest Revision</em>' reference.
     * @see #setLatestRevision(Revision)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_LatestRevision()
     * @model
     * @generated
     */
    Revision getLatestRevision();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getLatestRevision <em>Latest Revision</em>}' reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Latest Revision</em>' reference.
     * @see #getLatestRevision()
     * @generated
     */
    void setLatestRevision(Revision value);

    /**
     * Returns the value of the '<em><b>Installed Location</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Installed Location</em>' attribute isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Installed Location</em>' attribute.
     * @see #setInstalledLocation(String)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_InstalledLocation()
     * @model
     * @generated
     */
    String getInstalledLocation();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getInstalledLocation <em>Installed Location</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Installed Location</em>' attribute.
     * @see #getInstalledLocation()
     * @generated
     */
    void setInstalledLocation(String value);

    /**
     * Returns the value of the '<em><b>Author</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Author</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Author</em>' attribute.
     * @see #setAuthor(String)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getComponentExtension_Author()
     * @model
     * @generated
     */
    String getAuthor();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getAuthor <em>Author</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Author</em>' attribute.
     * @see #getAuthor()
     * @generated
     */
    void setAuthor(String value);

} // ComponentExtension
