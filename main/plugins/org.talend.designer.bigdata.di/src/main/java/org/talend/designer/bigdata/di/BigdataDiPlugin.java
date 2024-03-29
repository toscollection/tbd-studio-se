package org.talend.designer.bigdata.di;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.IService;
import org.talend.designer.codegen.ICodeGeneratorService;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.IRepositoryService;

public class BigdataDiPlugin extends Plugin {

    // The plug-in ID
    public static final String PLUGIN_ID = "org.talend.designer.bigdata.di"; //$NON-NLS-1$

    // The shared instance
    private static BigdataDiPlugin plugin;

    /**
     * The constructor
     */
    public BigdataDiPlugin() {
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
     */
    @Override
    public void start(BundleContext context) throws Exception {
        super.start(context);
        plugin = this;
    }

    /*
     * (non-Javadoc)
     *
     * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
     */
    @Override
    public void stop(BundleContext context) throws Exception {
        plugin = null;
        super.stop(context);
    }

    /**
     * Returns the shared instance
     *
     * @return the shared instance
     */
    public static BigdataDiPlugin getDefault() {
        return plugin;
    }

    /**
     * get a implement of CodeGeneratorService.
     *
     * @return a implement of CodeGeneratorService
     */
    public ICodeGeneratorService getCodeGeneratorService() {
        IService service = GlobalServiceRegister.getDefault().getService(ICodeGeneratorService.class);
        return (ICodeGeneratorService) service;
    }

    /**
     * get a implement of RunProcessService.
     *
     * @return
     */
    public IRunProcessService getRunProcessService() {
        IService service = GlobalServiceRegister.getDefault().getService(IRunProcessService.class);
        return (IRunProcessService) service;
    }

    public IRepositoryService getRepositoryService() {
        IService service = GlobalServiceRegister.getDefault().getService(IRepositoryService.class);
        return (IRepositoryService) service;
    }

    public IProxyRepositoryFactory getProxyRepositoryFactory() {
        IRepositoryService service = getRepositoryService();
        return service.getProxyRepositoryFactory();
    }
}