package org.talend.core;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;
import org.talend.core.model.general.ILibrariesService;
import org.talend.core.model.migration.IMigrationToolService;
import org.talend.core.service.IDesignerMapperService;
import org.talend.core.service.IWebService;
import org.talend.core.ui.ICreateXtextProcessService;
import org.talend.core.ui.IOpenJobScriptActionService;
import org.talend.designer.business.diagram.custom.IDiagramModelService;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.components.IComponentsLocalProviderService;
import org.talend.designer.core.IDesignerCoreService;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.rcp.IRcpService;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryLocalProviderService;
import org.talend.repository.model.IRepositoryService;
import org.talend.resource.IResourceService;

/**
 * The activator class controls the plug-in life cycle
 */
public class PerlPlugin extends AbstractUIPlugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.talend.core.perl"; //$NON-NLS-1$

    // The shared instance
    private static PerlPlugin plugin;

    /**
     * The constructor
     */
    public PerlPlugin() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     * 
     * @return the shared instance
     */
    public static PerlPlugin getDefault() {
        return plugin;
    }

    /**
     * Returns an image descriptor for the image file at the given plug-in relative path
     * 
     * @param path the path
     * @return the image descriptor
     */
    public static ImageDescriptor getImageDescriptor(String path) {
        return imageDescriptorFromPlugin(PLUGIN_ID, path);
    }

    public IProxyRepositoryFactory getProxyRepositoryFactory() {
        IRepositoryService service = getRepositoryService();
        return service.getProxyRepositoryFactory();
    }

    /**
     * DOC get a implement of RunProcessService.
     * 
     * @return a implement of RunProcessService
     */
    public IRunProcessService getRunProcessService() {
        IService service = GlobalServiceRegister.getDefault().getService(IRunProcessService.class);
        return (IRunProcessService) service;
    }

    public IDesignerCoreService getDesignerCoreService() {
        IService service = GlobalServiceRegister.getDefault().getService(IDesignerCoreService.class);
        return (IDesignerCoreService) service;
    }

    public ILibrariesService getLibrariesService() {
        return (ILibrariesService) GlobalServiceRegister.getDefault().getService(ILibrariesService.class);
    }

    public IRepositoryService getRepositoryService() {
        IService service = GlobalServiceRegister.getDefault().getService(IRepositoryService.class);
        return (IRepositoryService) service;
    }

    public IRepositoryLocalProviderService getRepositoryLocalProviderService() {
        return (IRepositoryLocalProviderService) GlobalServiceRegister.getDefault().getService(
                IRepositoryLocalProviderService.class);
    }

    public IComponentsLocalProviderService getComponentsLocalProviderService() {
        return (IComponentsLocalProviderService) GlobalServiceRegister.getDefault().getService(
                IComponentsLocalProviderService.class);
    }

    public ICodeGeneratorService getCodeGeneratorService() {
        return (ICodeGeneratorService) GlobalServiceRegister.getDefault().getService(ICodeGeneratorService.class);
    }

    public IMigrationToolService getMigrationToolService() {
        return (IMigrationToolService) GlobalServiceRegister.getDefault().getService(IMigrationToolService.class);
    }

    public IDiagramModelService getDiagramModelService() {
        return (IDiagramModelService) GlobalServiceRegister.getDefault().getService(IDiagramModelService.class);
    }

    public IResourceService getResourceService() {
        return (IResourceService) GlobalServiceRegister.getDefault().getService(IResourceService.class);
    }

    public IRcpService getRcpService() {
        return (IRcpService) GlobalServiceRegister.getDefault().getService(IRcpService.class);
    }

    /*
     * public boolean useSQLTemplate() { return (Boolean) CorePlugin.getContext().getProperty("useSQLTemplate"); }
     * 
     * public boolean useRefproject() { return (Boolean) CorePlugin.getContext().getProperty("useRefProject"); }
     */

    public IDesignerMapperService getMapperService() {
        return (IDesignerMapperService) GlobalServiceRegister.getDefault().getService(IDesignerMapperService.class);
    }

    public IWebService getWebService() {
        return (IWebService) GlobalServiceRegister.getDefault().getService(IWebService.class);
    }

    public ICreateXtextProcessService getCreateXtextProcessService() {
        return (ICreateXtextProcessService) GlobalServiceRegister.getDefault().getService(ICreateXtextProcessService.class);
    }

    public IOpenJobScriptActionService getOpenJobScriptActionService() {
        return (IOpenJobScriptActionService) GlobalServiceRegister.getDefault().getService(IOpenJobScriptActionService.class);
    }
}
