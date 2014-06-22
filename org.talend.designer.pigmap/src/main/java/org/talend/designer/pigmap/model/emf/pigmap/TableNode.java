/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.designer.pigmap.model.emf.pigmap;

import org.eclipse.emf.common.util.EList;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Table Node</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.TableNode#getLookupOutgoingConnections <em>Lookup Outgoing Connections</em>}</li>
 *   <li>{@link org.talend.designer.pigmap.model.emf.pigmap.TableNode#getLookupIncomingConnections <em>Lookup Incoming Connections</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getTableNode()
 * @model
 * @generated
 */
public interface TableNode extends AbstractNode {

    /**
     * Returns the value of the '<em><b>Lookup Outgoing Connections</b></em>' reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.LookupConnection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lookup Outgoing Connections</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lookup Outgoing Connections</em>' reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getTableNode_LookupOutgoingConnections()
     * @model
     * @generated
     */
    EList<LookupConnection> getLookupOutgoingConnections();

    /**
     * Returns the value of the '<em><b>Lookup Incoming Connections</b></em>' reference list.
     * The list contents are of type {@link org.talend.designer.pigmap.model.emf.pigmap.LookupConnection}.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Lookup Incoming Connections</em>' reference list isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Lookup Incoming Connections</em>' reference list.
     * @see org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage#getTableNode_LookupIncomingConnections()
     * @model
     * @generated
     */
    EList<LookupConnection> getLookupIncomingConnections();
} // TableNode
