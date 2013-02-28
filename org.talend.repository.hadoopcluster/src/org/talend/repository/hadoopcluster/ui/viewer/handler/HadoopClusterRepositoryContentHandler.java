package org.talend.repository.hadoopcluster.ui.viewer.handler;

import java.util.Collection;
import java.util.List;
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
import org.talend.core.model.properties.Item;
import org.talend.core.model.repository.AbstractRepositoryContentHandler;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.repository.utils.XmiResourceManager;
import org.talend.repository.hadoopcluster.node.model.HadoopClusterRepositoryNodeType;
import org.talend.repository.hadoopcluster.ui.HadoopClusterWizard;
import org.talend.repository.hadoopcluster.ui.viewer.HadoopSubnodeRepositoryContentManager;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;
import org.talend.repository.model.hadoopcluster.HadoopClusterFactory;
import org.talend.repository.model.hadoopcluster.HadoopClusterPackage;
import orgomg.cwm.foundation.businessinformation.BusinessinformationPackage;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterRepositoryContentHandler extends AbstractRepositoryContentHandler {

    private XmiResourceManager xmiResourceManager = new XmiResourceManager();

    @Override
    public boolean isProcess(Item item) {
        if (item.eClass() == HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isRepObjType(ERepositoryObjectType type) {
        return type == HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
    }

    @Override
    public ERepositoryObjectType getProcessType() {
        return HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
    }

    @Override
    public Resource create(IProject project, Item item, int classifierID, IPath path) throws PersistenceException {
        Resource itemResource = null;
        if (item.eClass() == HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM) {
            ERepositoryObjectType type = HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
            itemResource = create(project, (HadoopClusterConnectionItem) item, path, type);
        }

        return itemResource;
    }

    private Resource create(IProject project, HadoopClusterConnectionItem item, IPath path, ERepositoryObjectType type)
            throws PersistenceException {
        Resource itemResource = xmiResourceManager.createItemResource(project, item, path, type, false);
        itemResource.getContents().add(item.getConnection());

        return itemResource;
    }

    @Override
    public Resource save(Item item) throws PersistenceException {
        Resource itemResource = null;
        if (item.eClass() == HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM) {
            itemResource = save((HadoopClusterConnectionItem) item);
        }

        return itemResource;
    }

    private Resource save(HadoopClusterConnectionItem item) {
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
        if (type == HadoopClusterRepositoryNodeType.HADOOPCLUSTER) {
            return EHadoopClusterImage.HADOOPCLUSTER_RESOURCE_ICON;
        }

        return null;
    }

    @Override
    public Item createNewItem(ERepositoryObjectType type) {
        Item item = null;
        if (type == HadoopClusterRepositoryNodeType.HADOOPCLUSTER) {
            item = HadoopClusterFactory.eINSTANCE.createHadoopClusterConnectionItem();
        }

        return item;
    }

    @Override
    public ERepositoryObjectType getRepositoryObjectType(Item item) {
        ERepositoryObjectType type = null;
        if (item.eClass() == HadoopClusterPackage.Literals.HADOOP_CLUSTER_CONNECTION_ITEM) {
            type = HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
        }

        return type;
    }

    @Override
    public void addNode(ERepositoryObjectType type, RepositoryNode parentNode, IRepositoryViewObject repositoryObject,
            RepositoryNode node) {
        if (type == HadoopClusterRepositoryNodeType.HADOOPCLUSTER) {
            for (IHadoopSubnodeRepositoryContentHandler handler : HadoopSubnodeRepositoryContentManager.getHandlers()) {
                handler.addNode(node);
            }
        }
    }

    @Override
    public ERepositoryObjectType getHandleType() {
        return HadoopClusterRepositoryNodeType.HADOOPCLUSTER;
    }

    @Override
    public boolean hasSchemas() {
        return true;
    }

    @Override
    public IWizard newWizard(IWorkbench workbench, boolean creation, RepositoryNode node, String[] existingNames) {
        if (node == null) {
            return null;
        }
        if (workbench == null) {
            workbench = PlatformUI.getWorkbench();
        }

        return new HadoopClusterWizard(workbench, creation, node, existingNames);
    }

    @Override
    protected void deleteNode(Item item) throws Exception {
        final IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        HadoopClusterConnectionItem hadoopClusterItem = (HadoopClusterConnectionItem) item;
        HadoopClusterConnection connection = (HadoopClusterConnection) hadoopClusterItem.getConnection();
        List<String> connectionList = connection.getConnectionList();
        for (String connId : connectionList) {
            IRepositoryViewObject repObj = null;
            try {
                repObj = factory.getLastVersion(connId);
            } catch (PersistenceException e) {
                // do nothing.
            }
            if (repObj != null) {
                factory.deleteObjectPhysical(repObj);
            }
        }
        connection.getConnectionList().clear();
        factory.save(hadoopClusterItem);
    }

}
