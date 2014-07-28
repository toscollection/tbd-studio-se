/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.oozie.util;

import java.util.Map;

import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.resource.Resource;

import org.eclipse.emf.ecore.xmi.util.XMLProcessor;

import org.talend.repository.model.oozie.OoziePackage;

/**
 * This class contains helper methods to serialize and deserialize XML documents
 * <!-- begin-user-doc -->
 * <!-- end-user-doc -->
 * @generated
 */
public class OozieXMLProcessor extends XMLProcessor {

    /**
     * Public constructor to instantiate the helper.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public OozieXMLProcessor() {
        super((EPackage.Registry.INSTANCE));
        OoziePackage.eINSTANCE.eClass();
    }
    
    /**
     * Register for "*" and "xml" file extensions the OozieResourceFactoryImpl factory.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected Map<String, Resource.Factory> getRegistrations() {
        if (registrations == null) {
            super.getRegistrations();
            registrations.put(XML_EXTENSION, new OozieResourceFactoryImpl());
            registrations.put(STAR_EXTENSION, new OozieResourceFactoryImpl());
        }
        return registrations;
    }

} //OozieXMLProcessor
