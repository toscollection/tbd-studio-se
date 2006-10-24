// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.repository.remoteprovider.model;

import java.util.Date;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.talend.commons.exception.BusinessException;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.utils.data.container.RootContainer;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.general.Project;
import org.talend.core.model.general.User;
import org.talend.core.model.metadata.builder.connection.DatabaseConnection;
import org.talend.core.model.metadata.builder.connection.DelimitedFileConnection;
import org.talend.core.model.metadata.builder.connection.PositionalFileConnection;
import org.talend.core.model.metadata.builder.connection.RegexpFileConnection;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.Item;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.Status;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.ERepositoryType;
import org.talend.core.model.repository.Folder;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.core.model.temp.ECodeLanguage;
import org.talend.repository.model.IRepositoryFactory;
import org.talend.repository.remoteprovider.i18n.Messages;
import org.talend.repository.remoteprovider.wsclient.pingclient.ServerManagement;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class RepositoryFactory implements IRepositoryFactory {

    private RepositoryContext repositoryContext;

    public void setRepositoryContext(RepositoryContext repositoryContext) {
        this.repositoryContext = repositoryContext;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IDocumentationFactory#getDocumentation(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getDocumentation() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataConnection(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getMetadataConnection() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataConnections(org.talend.core.model.general.Project)
     */
    public List<ConnectionItem> getMetadataConnectionsItem() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataDatabase(org.talend.core.model.general.Project, int)
     */
    public DatabaseConnection getMetadataDatabase(int id) throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFileDelimited(org.talend.core.model.general.Project,
     * int)
     */
    public DelimitedFileConnection getMetadataFileDelimited(int id) throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFileDelimited(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getMetadataFileDelimited() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFilePositional(org.talend.core.model.general.Project,
     * int)
     */
    public PositionalFileConnection getMetadataFilePositional(int id) throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFilePositional(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getMetadataFilePositional() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IProcessFactory#getProcess(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getProcess() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#createProject(java.lang.String, java.lang.String,
     * org.talend.core.model.temp.ECodeLanguage, org.talend.core.model.general.User)
     */
    public Project createProject(String label, String description, ECodeLanguage language, User author)
            throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#deleteFolder(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.ERepositoryObjectType, org.eclipse.core.runtime.IPath)
     */
    public void deleteFolder(ERepositoryObjectType type, IPath path) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#deleteObjectLogical(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject, org.talend.core.model.general.User)
     */
    public void deleteObjectLogical(IRepositoryObject objToDelete) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#deleteObjectPhysical(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject, org.talend.core.model.general.User)
     */
    public void deleteObjectPhysical(IRepositoryObject objToDelete) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getAllVersion(org.talend.core.model.general.Project, int)
     */
    public List<IRepositoryObject> getAllVersion(String id) throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getLockDate(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject)
     */
    public Date getLockDate(IRepositoryObject obj) throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getLocker(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject)
     */
    public User getLocker(IRepositoryObject obj) throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getNextId(org.talend.core.model.general.Project)
     */
    public String getNextId() {

        return "";
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getType()
     */
    public ERepositoryType getType() {

        return ERepositoryType.REMOTE;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#isLocked(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject)
     */
    public boolean isLocked(IRepositoryObject obj) throws PersistenceException {

        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#lock(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject, org.talend.core.model.general.User)
     */
    public void lock(IRepositoryObject obj) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#moveFolder(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.ERepositoryObjectType, org.eclipse.core.runtime.IPath,
     * org.eclipse.core.runtime.IPath)
     */
    public void moveFolder(ERepositoryObjectType type, IPath sourcePath, IPath targetPath) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#moveObject(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject, org.eclipse.core.runtime.IPath)
     */
    public void moveObject(IRepositoryObject objToMove, IPath newPath) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#readProject(java.lang.String, java.lang.String,
     * java.lang.String)
     */
    public Project[] readProject(String server, String port, User user) throws PersistenceException {

        return new Project[] {};
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#renameFolder(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.ERepositoryObjectType, org.eclipse.core.runtime.IPath, java.lang.String)
     */
    public void renameFolder(ERepositoryObjectType type, IPath path, String label) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#restoreObject(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject, org.eclipse.core.runtime.IPath,
     * org.talend.core.model.general.User)
     */
    public void restoreObject(IRepositoryObject objToRestore) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#unlock(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject)
     */
    public void unlock(IRepositoryObject obj) throws PersistenceException {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRoutineFactory#getRoutine(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getRoutine() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IBusinessProcessFactory#getBusinessProcess(org.talend.core.model.general.Project)
     */
    public RootContainer<String, IRepositoryObject> getBusinessProcess() throws PersistenceException {

        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#createFolder(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.ERepositoryObjectType, org.eclipse.core.runtime.IPath, java.lang.String)
     */
    public Folder createFolder(ERepositoryObjectType type, IPath path, String label) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getAll(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.ERepositoryObjectType)
     */
    public List<IRepositoryObject> getAll(ERepositoryObjectType type) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getLastVersion(org.talend.core.model.general.Project, int)
     */
    public IRepositoryObject getLastVersion(String id) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getOldPath(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject)
     */
    public String getOldPath(IRepositoryObject obj) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#setDocumentationStatus(java.util.List)
     */
    public void setDocumentationStatus(List<Status> list) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#setTechnicalStatus(java.util.List)
     */
    public void setTechnicalStatus(List<Status> list) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#isDeleted(org.talend.core.model.general.Project,
     * org.talend.core.model.repository.IRepositoryObject)
     */
    public boolean isDeleted(IRepositoryObject obj) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getDocumentationStatus()
     */
    public List<Status> getDocumentationStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#getTechnicalStatus()
     */
    public List<Status> getTechnicalStatus() {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#isServerValid(java.lang.String, java.lang.String, int)
     */
    public String isServerValid() {
        Integer port = repositoryContext.getPort();

        if (port == null) {
            // PTODO MHI
        }

        try {
            if (ServerManagement.ping(repositoryContext.getServer(), repositoryContext.getContext(), port)) {
                return Messages.getString("webServiceClient.serverManagement.serverRunning");
            }
        } catch (BusinessException e) {
            return Messages.getString("webServiceClient.serverManagement.serverNotRunning") + " " + ServerManagement.getMessage();
        }
        // Should never happend
        return Messages.getString("webServiceClient.serverManagement.serverRunning");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#isPathValid(org.talend.core.model.repository.ERepositoryObjectType,
     * org.eclipse.core.runtime.IPath, java.lang.String, boolean)
     */
    public boolean isPathValid(ERepositoryObjectType type, IPath path, String label) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFileRegexp()
     */
    public RootContainer<String, IRepositoryObject> getMetadataFileRegexp() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFileXml(int)
     */
    public RegexpFileConnection getMetadataFileXml(int id) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFileXml()
     */
    public RootContainer<String, IRepositoryObject> getMetadataFileXml() throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IMetadataFactory#getMetadataFileRegexp(int)
     */
    public RegexpFileConnection getMetadataFileRegexp(int id) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    
    public void create(Item item, IPath path) throws PersistenceException {
    }

    public void save(Item item) throws PersistenceException {
    }

    public void save(Property property) throws PersistenceException {
    }

    public boolean isDeleted(Item item) throws PersistenceException {
        return false;
    }

    public boolean isLocked(Item item) throws PersistenceException {
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#copy(org.talend.core.model.properties.Item,
     * org.eclipse.core.runtime.IPath)
     */
    public Item copy(Item item, IPath path) throws PersistenceException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#isPathValid(org.talend.core.model.properties.Item,
     * org.eclipse.core.runtime.IPath, boolean)
     */
    public boolean isNameAvailable(Item item, String name) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#restoreObject(org.talend.core.model.repository.IRepositoryObject,
     * org.eclipse.core.runtime.IPath)
     */
    public void restoreObject(IRepositoryObject objToRestore, IPath path) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#reload(org.talend.core.model.properties.Property)
     */
    public void reload(Property property) {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#lock(org.talend.core.model.properties.Item)
     */
    public void lock(Item item) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#unlock(org.talend.core.model.properties.Item)
     */
    public void unlock(Item obj) throws PersistenceException {
        // TODO Auto-generated method stub

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#createUser(org.talend.core.model.general.User)
     */
    public boolean findUser(Project project, RepositoryContext repositoryContext) throws PersistenceException {
        // TODO Auto-generated method stub
        return false;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#createUser(org.talend.core.model.general.User)
     */
    public void createUser(Project project, RepositoryContext repositoryContext) throws PersistenceException {
        // TODO Auto-generated method stub
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.model.IRepositoryFactory#initialize()
     */
    public void initialize(){
        // TODO Auto-generated method stub
    }

}
