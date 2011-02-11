/**
 * // ============================================================================ // // Copyright (C) 2006-2011 Talend
 * Inc. - www.talend.com // // This source code is available under agreement available at //
 * %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt // // You should have received a
 * copy of the agreement // along with this program; if not, write to Talend SA // 9 rue Pages 92150 Suresnes, France //
 * // ============================================================================
 * 
 * $Id$
 */
package org.talend.designer.components.ecosystem.model.impl;

import java.util.Date;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.talend.designer.components.ecosystem.i18n.Messages;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Revision;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Revision</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getId <em>Id</em>}</li>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getName <em>Name</em>}</li>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getUrl <em>Url</em>}</li>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getDate <em>Date</em>}</li>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getExtension <em>Extension</em>}</li>
 * <li>{@link org.talend.designer.components.ecosystem.model.impl.RevisionImpl#getFileName <em>File Name</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class RevisionImpl extends EObjectImpl implements Revision {

    /**
     * The default value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected static final int ID_EDEFAULT = 0;

    /**
     * The cached value of the '{@link #getId() <em>Id</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getId()
     * @generated
     * @ordered
     */
    protected int id = ID_EDEFAULT;

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
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
     * The default value of the '{@link #getDescription() <em>Description</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getDescription()
     * @generated
     * @ordered
     */
    protected String description = DESCRIPTION_EDEFAULT;

    /**
     * The default value of the '{@link #getUrl() <em>Url</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getUrl()
     * @generated
     * @ordered
     */
    protected static final String URL_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getUrl() <em>Url</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @see #getUrl()
     * @generated
     * @ordered
     */
    protected String url = URL_EDEFAULT;

    /**
     * The default value of the '{@link #getDate() <em>Date</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getDate()
     * @generated
     * @ordered
     */
    protected static final Date DATE_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDate() <em>Date</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     * 
     * @see #getDate()
     * @generated
     * @ordered
     */
    protected Date date = DATE_EDEFAULT;

    /**
     * The default value of the '{@link #getFileName() <em>File Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getFileName()
     * @generated
     * @ordered
     */
    protected static final String FILE_NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getFileName() <em>File Name</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     * 
     * @see #getFileName()
     * @generated
     * @ordered
     */
    protected String fileName = FILE_NAME_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    protected RevisionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return EcosystemPackage.Literals.REVISION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public int getId() {
        return id;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setId(int newId) {
        int oldId = id;
        id = newId;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__ID, oldId, id));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getName() {
        return name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setName(String newName) {
        String oldName = name;
        name = newName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__NAME, oldName, name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getDescription() {
        return description;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDescription(String newDescription) {
        String oldDescription = description;
        description = newDescription;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__DESCRIPTION, oldDescription,
                    description));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getUrl() {
        return url;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setUrl(String newUrl) {
        String oldUrl = url;
        url = newUrl;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__URL, oldUrl, url));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public Date getDate() {
        return date;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setDate(Date newDate) {
        Date oldDate = date;
        date = newDate;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__DATE, oldDate, date));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public ComponentExtension getExtension() {
        if (eContainerFeatureID != EcosystemPackage.REVISION__EXTENSION)
            return null;
        return (ComponentExtension) eContainer();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public NotificationChain basicSetExtension(ComponentExtension newExtension, NotificationChain msgs) {
        msgs = eBasicSetContainer((InternalEObject) newExtension, EcosystemPackage.REVISION__EXTENSION, msgs);
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setExtension(ComponentExtension newExtension) {
        if (newExtension != eInternalContainer()
                || (eContainerFeatureID != EcosystemPackage.REVISION__EXTENSION && newExtension != null)) {
            if (EcoreUtil.isAncestor(this, newExtension))
                throw new IllegalArgumentException(Messages.getString("RevisionImpl.IllegalArgumentError", toString())); //$NON-NLS-1$
            NotificationChain msgs = null;
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            if (newExtension != null)
                msgs = ((InternalEObject) newExtension).eInverseAdd(this, EcosystemPackage.COMPONENT_EXTENSION__REVISIONS,
                        ComponentExtension.class, msgs);
            msgs = basicSetExtension(newExtension, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__EXTENSION, newExtension,
                    newExtension));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    public void setFileName(String newFileName) {
        String oldFileName = fileName;
        fileName = newFileName;
        if (eNotificationRequired())
            eNotify(new ENotificationImpl(this, Notification.SET, EcosystemPackage.REVISION__FILE_NAME, oldFileName, fileName));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case EcosystemPackage.REVISION__EXTENSION:
            if (eInternalContainer() != null)
                msgs = eBasicRemoveFromContainer(msgs);
            return basicSetExtension((ComponentExtension) otherEnd, msgs);
        }
        return super.eInverseAdd(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case EcosystemPackage.REVISION__EXTENSION:
            return basicSetExtension(null, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public NotificationChain eBasicRemoveFromContainerFeature(NotificationChain msgs) {
        switch (eContainerFeatureID) {
        case EcosystemPackage.REVISION__EXTENSION:
            return eInternalContainer().eInverseRemove(this, EcosystemPackage.COMPONENT_EXTENSION__REVISIONS,
                    ComponentExtension.class, msgs);
        }
        return super.eBasicRemoveFromContainerFeature(msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
        case EcosystemPackage.REVISION__ID:
            return new Integer(getId());
        case EcosystemPackage.REVISION__NAME:
            return getName();
        case EcosystemPackage.REVISION__DESCRIPTION:
            return getDescription();
        case EcosystemPackage.REVISION__URL:
            return getUrl();
        case EcosystemPackage.REVISION__DATE:
            return getDate();
        case EcosystemPackage.REVISION__EXTENSION:
            return getExtension();
        case EcosystemPackage.REVISION__FILE_NAME:
            return getFileName();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
        case EcosystemPackage.REVISION__ID:
            setId(((Integer) newValue).intValue());
            return;
        case EcosystemPackage.REVISION__NAME:
            setName((String) newValue);
            return;
        case EcosystemPackage.REVISION__DESCRIPTION:
            setDescription((String) newValue);
            return;
        case EcosystemPackage.REVISION__URL:
            setUrl((String) newValue);
            return;
        case EcosystemPackage.REVISION__DATE:
            setDate((Date) newValue);
            return;
        case EcosystemPackage.REVISION__EXTENSION:
            setExtension((ComponentExtension) newValue);
            return;
        case EcosystemPackage.REVISION__FILE_NAME:
            setFileName((String) newValue);
            return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
        case EcosystemPackage.REVISION__ID:
            setId(ID_EDEFAULT);
            return;
        case EcosystemPackage.REVISION__NAME:
            setName(NAME_EDEFAULT);
            return;
        case EcosystemPackage.REVISION__DESCRIPTION:
            setDescription(DESCRIPTION_EDEFAULT);
            return;
        case EcosystemPackage.REVISION__URL:
            setUrl(URL_EDEFAULT);
            return;
        case EcosystemPackage.REVISION__DATE:
            setDate(DATE_EDEFAULT);
            return;
        case EcosystemPackage.REVISION__EXTENSION:
            setExtension((ComponentExtension) null);
            return;
        case EcosystemPackage.REVISION__FILE_NAME:
            setFileName(FILE_NAME_EDEFAULT);
            return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
        case EcosystemPackage.REVISION__ID:
            return id != ID_EDEFAULT;
        case EcosystemPackage.REVISION__NAME:
            return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
        case EcosystemPackage.REVISION__DESCRIPTION:
            return DESCRIPTION_EDEFAULT == null ? description != null : !DESCRIPTION_EDEFAULT.equals(description);
        case EcosystemPackage.REVISION__URL:
            return URL_EDEFAULT == null ? url != null : !URL_EDEFAULT.equals(url);
        case EcosystemPackage.REVISION__DATE:
            return DATE_EDEFAULT == null ? date != null : !DATE_EDEFAULT.equals(date);
        case EcosystemPackage.REVISION__EXTENSION:
            return getExtension() != null;
        case EcosystemPackage.REVISION__FILE_NAME:
            return FILE_NAME_EDEFAULT == null ? fileName != null : !FILE_NAME_EDEFAULT.equals(fileName);
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     * 
     * @generated
     */
    @Override
    public String toString() {
        if (eIsProxy())
            return super.toString();

        StringBuffer result = new StringBuffer(super.toString());
        result.append(" (id: "); //$NON-NLS-1$
        result.append(id);
        result.append(", name: "); //$NON-NLS-1$
        result.append(name);
        result.append(", description: "); //$NON-NLS-1$
        result.append(description);
        result.append(", url: "); //$NON-NLS-1$
        result.append(url);
        result.append(", date: "); //$NON-NLS-1$
        result.append(date);
        result.append(", fileName: "); //$NON-NLS-1$
        result.append(fileName);
        result.append(')');
        return result.toString();
    }

} // RevisionImpl
