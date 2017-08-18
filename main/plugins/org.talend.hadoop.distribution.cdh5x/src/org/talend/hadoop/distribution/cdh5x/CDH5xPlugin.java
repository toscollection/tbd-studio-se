package org.talend.hadoop.distribution.cdh5x;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.talend.core.runtime.dynamic.DynamicFactory;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
import org.talend.core.runtime.dynamic.IDynamicConfiguration;
import org.talend.core.runtime.dynamic.IDynamicExtension;
import org.talend.core.runtime.dynamic.IDynamicPlugin;
import org.talend.core.runtime.dynamic.IDynamicPluginConfiguration;
import org.talend.hadoop.distribution.component.HBaseComponent;
import org.talend.hadoop.distribution.component.HCatalogComponent;
import org.talend.hadoop.distribution.component.HDFSComponent;
import org.talend.hadoop.distribution.component.HadoopComponent;
import org.talend.hadoop.distribution.component.HiveComponent;
import org.talend.hadoop.distribution.component.HiveOnSparkComponent;
import org.talend.hadoop.distribution.component.ImpalaComponent;
import org.talend.hadoop.distribution.component.MRComponent;
import org.talend.hadoop.distribution.component.PigComponent;
import org.talend.hadoop.distribution.component.SparkBatchComponent;
import org.talend.hadoop.distribution.component.SparkStreamingComponent;
import org.talend.hadoop.distribution.component.SqoopComponent;

public class CDH5xPlugin extends Plugin {

	private static BundleContext context;

    private ServiceRegistration dynamic;

	static BundleContext getContext() {
		return context;
	}

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
    @Override
    public void start(BundleContext bundleContext) throws Exception {
        super.start(bundleContext);
        CDH5xPlugin.context = bundleContext;
        URL resourceURL = getBundle().getEntry("resources/cdh5x.json");
        String cdh5xPath = FileLocator.toFileURL(resourceURL).getPath();
        String xmlJsonString = DynamicServiceUtil.readFile(new File(cdh5xPath));
        IDynamicPlugin plugin = DynamicFactory.getInstance().createPluginFromJson(xmlJsonString);

        List<IDynamicExtension> allExtensions = plugin.getAllExtensions();
        IDynamicPluginConfiguration pluginConfiguration = plugin.getPluginConfiguration();
        String id = "test1";
        pluginConfiguration.setId(id);
        for (IDynamicExtension extension : allExtensions) {
            String extensionId = extension.getExtensionPoint() + "." + id;
            extension.setExtensionId(extensionId);

        }

        dynamic = DynamicServiceUtil.registOSGiService(bundleContext,
                new String[] { HadoopComponent.class.getName(), HDFSComponent.class.getName(), HBaseComponent.class.getName(),
                        HCatalogComponent.class.getName(), HiveComponent.class.getName(), HiveOnSparkComponent.class.getName(),
                        ImpalaComponent.class.getName(), MRComponent.class.getName(), PigComponent.class.getName(),
                        SqoopComponent.class.getName(), SparkBatchComponent.class.getName(),
                        SparkStreamingComponent.class.getName() },
                new CDH5xDistributionTemplate("test1") {
                }, null);
    }

    private void resolve(IDynamicConfiguration dynamicConfiguration) {
        Map<String, Object> attributeMap = dynamicConfiguration.getAttributes();

    }

	/*
	 * (non-Javadoc)
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	@Override
    public void stop(BundleContext bundleContext) throws Exception {
        super.stop(bundleContext);
		CDH5xPlugin.context = null;
        DynamicServiceUtil.unregistOSGiService(dynamic);
	}

}
