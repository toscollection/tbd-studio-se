package org.talend.hadoop.distribution.cdh5x;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.talend.core.runtime.dynamic.DynamicServiceUtil;
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
        dynamic = DynamicServiceUtil.registOSGiService(bundleContext,
                new String[] { HadoopComponent.class.getName(), HDFSComponent.class.getName(), HBaseComponent.class.getName(),
                        HCatalogComponent.class.getName(), HiveComponent.class.getName(), HiveOnSparkComponent.class.getName(),
                        ImpalaComponent.class.getName(), MRComponent.class.getName(), PigComponent.class.getName(),
                        SqoopComponent.class.getName(), SparkBatchComponent.class.getName(),
                        SparkStreamingComponent.class.getName() },
                new CDH5xDistributionTemplate("1") {
                }, null);
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
