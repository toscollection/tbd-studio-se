/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie.impl;

import org.eclipse.emf.ecore.EClass;

import org.talend.repository.model.hadoopcluster.impl.HadoopSubConnectionItemImpl;

import org.talend.repository.model.oozie.OozieConnectionItem;
import org.talend.repository.model.oozie.OoziePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Connection Item</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * </p>
 *
 * @generated
 */
public class OozieConnectionItemImpl extends HadoopSubConnectionItemImpl implements OozieConnectionItem {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected OozieConnectionItemImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return OoziePackage.Literals.OOZIE_CONNECTION_ITEM;
    }

} //OozieConnectionItemImpl
