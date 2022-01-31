package org.talend.hadoop.distribution.dataproc20x;

import org.eclipse.core.runtime.Plugin;
import org.osgi.framework.BundleContext;

public class Dataproc20xPlugin extends Plugin {

    private static Dataproc20xPlugin instance;

    public static Dataproc20xPlugin getInstance() {
        return instance;
    }

    @Override
    public void start(BundleContext bundleContext) throws Exception {
        super.start(bundleContext);
        instance = this;
    }

	@Override
    public void stop(BundleContext bundleContext) throws Exception {
        super.stop(bundleContext);
	}

}
