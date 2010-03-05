/**
 * // ============================================================================ // // Copyright (C) 2006-2010 Talend
 * Inc. - www.talend.com // // This source code is available under agreement available at //
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt // // You should have received a
 * copy of the agreement // along with this program; if not, write to Talend SA // 9 rue Pages 92150 Suresnes, France // //
 * ============================================================================
 * 
 * $Id$
 */
package org.talend.designer.components.ecosystem.model;

import java.util.Date;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Revision</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getId <em>Id</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getDescription <em>Description</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getUrl <em>Url</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getDate <em>Date</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getExtension <em>Extension</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.Revision#getFileName <em>File Name</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision()
 * @model
 * @generated
 */
public interface Revision extends EObject {

    /**
     * Returns the value of the '<em><b>Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Id</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Id</em>' attribute.
     * @see #setId(int)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_Id()
     * @model required="true"
     * @generated
     */
    int getId();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getId <em>Id</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Id</em>' attribute.
     * @see #getId()
     * @generated
     */
    void setId(int value);

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
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_Name()
     * @model required="true"
     * @generated
     */
    String getName();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getName <em>Name</em>}' attribute.
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
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_Description()
     * @model required="true"
     * @generated
     */
    String getDescription();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getDescription <em>Description</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Description</em>' attribute.
     * @see #getDescription()
     * @generated
     */
    void setDescription(String value);

    /**
     * Returns the value of the '<em><b>Url</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Url</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Url</em>' attribute.
     * @see #setUrl(String)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_Url()
     * @model required="true"
     * @generated
     */
    String getUrl();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getUrl <em>Url</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Url</em>' attribute.
     * @see #getUrl()
     * @generated
     */
    void setUrl(String value);

    /**
     * Returns the value of the '<em><b>Date</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Date</em>' attribute isn't clear, there really should be more of a description
     * here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Date</em>' attribute.
     * @see #setDate(Date)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_Date()
     * @model required="true"
     * @generated
     */
    Date getDate();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getDate <em>Date</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Date</em>' attribute.
     * @see #getDate()
     * @generated
     */
    void setDate(Date value);

    /**
     * Returns the value of the '<em><b>Extension</b></em>' container reference.
     * It is bidirectional and its opposite is '{@link org.talend.designer.components.ecosystem.model.ComponentExtension#getRevisions <em>Revisions</em>}'.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Extension</em>' container reference isn't clear, there really should be more of a
     * description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Extension</em>' container reference.
     * @see #setExtension(ComponentExtension)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_Extension()
     * @see org.talend.designer.components.ecosystem.model.ComponentExtension#getRevisions
     * @model opposite="revisions" required="true" transient="false"
     * @generated
     */
    ComponentExtension getExtension();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getExtension <em>Extension</em>}' container reference.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @param value the new value of the '<em>Extension</em>' container reference.
     * @see #getExtension()
     * @generated
     */
    void setExtension(ComponentExtension value);

    /**
     * Returns the value of the '<em><b>File Name</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>File Name</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>File Name</em>' attribute.
     * @see #setFileName(String)
     * @see org.talend.designer.components.ecosystem.model.EcosystemPackage#getRevision_FileName()
     * @model
     * @generated
     */
    String getFileName();

    /**
     * Sets the value of the '{@link org.talend.designer.components.ecosystem.model.Revision#getFileName <em>File Name</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>File Name</em>' attribute.
     * @see #getFileName()
     * @generated
     */
    void setFileName(String value);

} // Revision
