package org.talend.repository.oozie.node;

import java.util.Collection;
import java.util.Map;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.jface.wizard.IWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.metadata.MetadataManager;
import org.talend.core.model.metadata.builder.connection.MetadataTable;
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.XmiResourceManager;
import org.talend.repository.hadoopcluster.ui.viewer.handler.AbstractHadoopSubnodeRepositoryContentHandler;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.oozie.OozieConnectionItem;
import org.talend.repository.model.oozie.OozieFactory;
import org.talend.repository.model.oozie.OoziePackage;
import org.talend.repository.oozie.node.model.OozieRepositoryNodeType;
import org.talend.repository.oozie.ui.OozieWizard;
import org.talend.repository.oozie.util.EOozieImage;

import orgomg.cwm.foundation.businessinformation.BusinessinformationPackage;

public class OozieRepositoryContentHandler extends AbstractHadoopSubnodeRepositoryContentHandler {

    @Override
    public boolean isProcess(Item item) {
        if (item.eClass() == OoziePackage.Literals.OOZIE_CONNECTION_ITEM) {
            return true;
        }
        return false;
    }

    @Override
    public ERepositoryObjectType getProcessType() {
        return OozieRepositoryNodeType.OOZIE;
    }

    @Override
    public Resource create(IProject project, Item item, int classifierID, IPath path) throws PersistenceException {
        if (item.eClass() == OoziePackage.Literals.OOZIE_CONNECTION_ITEM) {
            Resource itemResource = null;
            ERepositoryObjectType type;
            type = OozieRepositoryNodeType.OOZIE;
            itemResource = create(project, (OozieConnectionItem) item, path, type);
            return itemResource;
        }
        return null;
    }

    private Resource create(IProject project, OozieConnectionItem item, IPath path, ERepositoryObjectType type)
            throws PersistenceException {
    	XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider().getResourceManager();
        Resource itemResource = xmiResourceManager.createItemResource(project, item, path, type, false);
        itemResource.getContents().add(item.getConnection());

        return itemResource;
    }

    @Override
    public Resource save(Item item) throws PersistenceException {
        if (item.eClass() == OoziePackage.Literals.OOZIE_CONNECTION_ITEM) {
            Resource itemResource = null;
            itemResource = save((OozieConnectionItem) item);
            return itemResource;
        }
        return null;
    }

    private Resource save(OozieConnectionItem item) throws PersistenceException {
    	XmiResourceManager xmiResourceManager = ProxyRepositoryFactory.getInstance().getRepositoryFactoryFromProvider().getResourceManager();
        Resource itemResource = xmiResourceManager.getItemResource(item);
        itemResource.getContents().clear();
        MetadataManager.addContents(item, itemResource);

        // add to the current resource all Document and Description instances because they are not reference in
        // containment references.
        Map<EObject, Collection<Setting>> externalCrossref = EcoreUtil.ExternalCrossReferencer.find(item.getConnection());
        Collection<Object> documents = EcoreUtil.getObjectsByType(externalCrossref.keySet(),
                BusinessinformationPackage.Literals.DOCUMENT);
        for (Object doc : documents) {
            itemResource.getContents().add((EObject) doc);
        }
        Collection<Object> descriptions = EcoreUtil.getObjectsByType(externalCrossref.keySet(),
                BusinessinformationPackage.Literals.DESCRIPTION);
        for (Object doc : descriptions) {
            itemResource.getContents().add((EObject) doc);
        }
        return itemResource;
    }

    @Override
    public IImage getIcon(ERepositoryObjectType type) {
        if (type == OozieRepositoryNodeType.OOZIE) {
            return EOozieImage.OOZIE_RESOURCE_ICON;
        }

        return null;
    }

    @Override
    public Item createNewItem(ERepositoryObjectType type) {
        Item item = null;
        if (type == OozieRepositoryNodeType.OOZIE) {
            item = OozieFactory.eINSTANCE.createOozieConnectionItem();
        }
        return item;
    }

    @Override
    public boolean isRepObjType(ERepositoryObjectType type) {
        return type == OozieRepositoryNodeType.OOZIE;
    }

    @Override
    public ERepositoryObjectType getRepositoryObjectType(Item item) {
        if (item.eClass() == OoziePackage.Literals.OOZIE_CONNECTION_ITEM) {
            return OozieRepositoryNodeType.OOZIE;
        }
        return null;
    }

    @Override
    public ERepositoryObjectType getHandleType() {
        return OozieRepositoryNodeType.OOZIE;
    }

    @Override
    public boolean hasSchemas() {
        return false;
    }

    @Override
    public IWizard newWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        if (node == null) {
            return null;
        }
        if (workbench == null) {
            workbench = PlatformUI.getWorkbench();
        }

        return new OozieWizard(workbench, creation, node, existingNames);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.model.repository.IRepositoryContentHandler#newSchemaWizard(org.eclipse.ui.IWorkbench,
     * boolean, org.talend.core.model.repository.IRepositoryViewObject,
     * org.talend.core.model.metadata.builder.connection.MetadataTable, java.lang.String[], boolean)
     */
    @Override
    public IWizard newSchemaWizard(IWorkbench workbench, boolean creation, IRepositoryViewObject object,
            MetadataTable metadataTable, String[] existingNames, boolean forceReadOnly) {
        // TODO Auto-generated method stub
        return null;
    }
}
