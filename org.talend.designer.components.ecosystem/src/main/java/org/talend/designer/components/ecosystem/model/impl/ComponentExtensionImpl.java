/**
 * // ============================================================================ // // Copyright (C) 2006-2010 Talend
 * Inc. - www.talend.com // // This source code is available under agreement available at //
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt // // You should have received a
 * copy of the agreement // along with this program; if not, write to Talend SA // 9 rue Pages 92150 Suresnes, France // //
 * ============================================================================
 * 
 * $Id$
 */
package org.talend.designer.components.ecosystem.model.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Language;
import org.talend.designer.components.ecosystem.model.Revision;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Component Extension</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getDescription <em>Description</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getLanguage <em>Language</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getRevisions <em>Revisions</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getInstalledRevision <em>Installed Revision</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getLatestRevision <em>Latest Revision</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getInstalledLocation <em>Installed Location</em>}</li>
 *   <li>{@link org.talend.designer.components.ecosystem.model.impl.ComponentExtensionImpl#getAuthor <em>Author</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComponentExtensionImpl extends EObjectImpl implements ComponentExtension {

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getLanguage() <em>Language</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getLanguage()
     * @generated
     * @ordered
     */
    protected static final Language LANGUAGE_EDEFAULT = Language.PERL;

    /**
     * The cached value of the '{@link #getLanguage() <em>Language</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getLanguage()
     * @generated
     * @ordered
     */
    protected Language language = LANGUAGE_EDEFAULT;

    /**
     * The cached value of the '{@link #getRevisions() <em>Revisions</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getRevisions()
     * @generated
     * @ordered
     */
    protected EList<Revision> revisions;

    /**
     * The cached value of the '{@link #getInstalledRevision() <em>Installed Revision</em>}' reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInstalledRevision()
     * @generated
     * @ordered
     */
    protected Revision installedRevision;

    /**
     * The cached value of the '{@link #getLatestRevision() <em>Latest Revision</em>}' reference.
     * <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     * @see #getLatestRevision()
     * @generated
     * @ordered
     */
    protected Revision latestRevision;

    /**
     * The default value of the '{@link #getInstalledLocation() <em>Installed Location</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInstalledLocation()
     * @generated
     * @ordered
     */
    protected static final String INSTALLED_LOCATION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getInstalledLocation() <em>Installed Location</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getInstalledLocation()
     * @generated
     * @ordered
     */
    protected String installedLocation = INSTALLED_LOCATION_EDEFAULT;

    /**
     * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAuthor()
     * @generated
     * @ordered
     */
    protected static final String AUTHOR_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
     * <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * @see #getAuthor()
     * @generated
     * @ordered
     */
    protected String author = AUTHOR_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    protected ComponentExtensionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return EcosystemPackage.Literals.COMPONENT_EXTENSION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION, oldDescription, description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Language getLanguage() {
        return language;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setLanguage(Language newLanguage) {
        Language oldLanguage = language;
        language = newLanguage == null ? LANGUAGE_EDEFAULT : newLanguage;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__LANGUAGE, oldLanguage, language));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public EList<Revision> getRevisions() {
        if (revisions == null) {
            revisions = new EObjectContainmentWithInverseEList<Revision>(Revision.class, this, EcosystemPackage.COMPONENT_EXTENSION__REVISIONS, EcosystemPackage.REVISION__EXTENSION);
        }
        return revisions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Revision getInstalledRevision() {
        if (installedRevision != null && installedRevision.eIsProxy()) {
            InternalEObject oldInstalledRevision = (InternalEObject)installedRevision;
            installedRevision = (Revision)eResolveProxy(oldInstalledRevision);
            if (installedRevision != oldInstalledRevision) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_REVISION, oldInstalledRevision, installedRevision));
            }
        }
        return installedRevision;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Revision basicGetInstalledRevision() {
        return installedRevision;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setInstalledRevision(Revision newInstalledRevision) {
        Revision oldInstalledRevision = installedRevision;
        installedRevision = newInstalledRevision;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_REVISION, oldInstalledRevision, installedRevision));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Revision getLatestRevision() {
        if (latestRevision != null && latestRevision.eIsProxy()) {
            InternalEObject oldLatestRevision = (InternalEObject)latestRevision;
            latestRevision = (Revision)eResolveProxy(oldLatestRevision);
            if (latestRevision != oldLatestRevision) {
                if (eNotificationRequired())
                    eNotify(new ENotificationImpl(this, Notification.RESOLVE, EcosystemPackage.COMPONENT_EXTENSION__LATEST_REVISION, oldLatestRevision, latestRevision));
            }
        }
        return latestRevision;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public Revision basicGetLatestRevision() {
        return latestRevision;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setLatestRevision(Revision newLatestRevision) {
        Revision oldLatestRevision = latestRevision;
        latestRevision = newLatestRevision;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__LATEST_REVISION, oldLatestRevision, latestRevision));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getInstalledLocation() {
        return installedLocation;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setInstalledLocation(String newInstalledLocation) {
        String oldInstalledLocation = installedLocation;
        installedLocation = newInstalledLocation;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_LOCATION, oldInstalledLocation, installedLocation));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public String getAuthor() {
        return author;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    public void setAuthor(String newAuthor) {
        String oldAuthor = author;
        author = newAuthor;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.COMPONENT_EXTENSION__AUTHOR, oldAuthor, author));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case EcosystemPackage.COMPONENT_EXTENSION__REVISIONS:
                return ((InternalEList<InternalEObject>)(InternalEList<?>)getRevisions()).basicAdd(otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case EcosystemPackage.COMPONENT_EXTENSION__REVISIONS:
                return ((InternalEList<?>)getRevisions()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case EcosystemPackage.COMPONENT_EXTENSION__NAME:
                return getName();
            case EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION:
                return getDescription();
            case EcosystemPackage.COMPONENT_EXTENSION__LANGUAGE:
                return getLanguage();
            case EcosystemPackage.COMPONENT_EXTENSION__REVISIONS:
                return getRevisions();
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_REVISION:
                if (resolve) return getInstalledRevision();
                return basicGetInstalledRevision();
            case EcosystemPackage.COMPONENT_EXTENSION__LATEST_REVISION:
                if (resolve) return getLatestRevision();
                return basicGetLatestRevision();
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_LOCATION:
                return getInstalledLocation();
            case EcosystemPackage.COMPONENT_EXTENSION__AUTHOR:
                return getAuthor();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case EcosystemPackage.COMPONENT_EXTENSION__NAME:
                setName((String)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION:
                setDescription((String)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__LANGUAGE:
                setLanguage((Language)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__REVISIONS:
                getRevisions().clear();
                getRevisions().addAll((Collection<? extends Revision>)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_REVISION:
                setInstalledRevision((Revision)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__LATEST_REVISION:
                setLatestRevision((Revision)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_LOCATION:
                setInstalledLocation((String)newValue);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__AUTHOR:
                setAuthor((String)newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case EcosystemPackage.COMPONENT_EXTENSION__NAME:
                setName(NAME_EDEFAULT);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION:
                setDescription(DESCRIPTION_EDEFAULT);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__LANGUAGE:
                setLanguage(LANGUAGE_EDEFAULT);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__REVISIONS:
                getRevisions().clear();
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_REVISION:
                setInstalledRevision((Revision)null);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__LATEST_REVISION:
                setLatestRevision((Revision)null);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_LOCATION:
                setInstalledLocation(INSTALLED_LOCATION_EDEFAULT);
                return;
            case EcosystemPackage.COMPONENT_EXTENSION__AUTHOR:
                setAuthor(AUTHOR_EDEFAULT);
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case EcosystemPackage.COMPONENT_EXTENSION__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION:
                return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
            case EcosystemPackage.COMPONENT_EXTENSION__LANGUAGE:
                return language != LANGUAGE_EDEFAULT;
            case EcosystemPackage.COMPONENT_EXTENSION__REVISIONS:
                return revisions != null && !revisions.isEmpty();
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_REVISION:
                return installedRevision != null;
            case EcosystemPackage.COMPONENT_EXTENSION__LATEST_REVISION:
                return latestRevision != null;
            case EcosystemPackage.COMPONENT_EXTENSION__INSTALLED_LOCATION:
                return INSTALLED_LOCATION_EDEFAULT == null ? installedLocation != null : !INSTALLED_LOCATION_EDEFAULT.equals(installedLocation);
            case EcosystemPackage.COMPONENT_EXTENSION__AUTHOR:
                return AUTHOR_EDEFAULT == null ? author != null : !AUTHOR_EDEFAULT.equals(author);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy()) return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", language: "); //$NON-NLS-1$
        result.append(language);
        result.append(", installedLocation: "); //$NON-NLS-1$
        result.append(installedLocation);
        result.append(", author: "); //$NON-NLS-1$
        result.append(author);
        result.append(')');
        return result.toString();
    }

} // ComponentExtensionImpl
