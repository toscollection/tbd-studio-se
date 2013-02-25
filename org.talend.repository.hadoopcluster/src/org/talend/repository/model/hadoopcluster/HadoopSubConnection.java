/**
 * <copyright>
 * </copyright>
 *
 * $Id$
 */
package org.talend.repository.model.hadoopcluster;

import org.talend.core.model.metadata.builder.connection.Connection;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Hadoop Sub Connection</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.talend.repository.model.hadoopcluster.HadoopSubConnection#getRelativeHadoopClusterId <em>Relative Hadoop Cluster Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopSubConnection()
 * @model
 * @generated
 */
public interface HadoopSubConnection extends Connection {
    /**
     * Returns the value of the '<em><b>Relative Hadoop Cluster Id</b></em>' attribute.
     * <!-- begin-user-doc -->
     * <p>
     * If the meaning of the '<em>Relative Hadoop Cluster Id</em>' attribute isn't clear,
     * there really should be more of a description here...
     * </p>
     * <!-- end-user-doc -->
     * @return the value of the '<em>Relative Hadoop Cluster Id</em>' attribute.
     * @see #setRelativeHadoopClusterId(String)
     * @see org.talend.repository.model.hadoopcluster.HadoopClusterPackage#getHadoopSubConnection_RelativeHadoopClusterId()
     * @model required="true"
     * @generated
     */
    String getRelativeHadoopClusterId();

    /**
     * Sets the value of the '{@link org.talend.repository.model.hadoopcluster.HadoopSubConnection#getRelativeHadoopClusterId <em>Relative Hadoop Cluster Id</em>}' attribute.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @param value the new value of the '<em>Relative Hadoop Cluster Id</em>' attribute.
     * @see #getRelativeHadoopClusterId()
     * @generated
     */
    void setRelativeHadoopClusterId(String value);

} // HadoopSubConnection
