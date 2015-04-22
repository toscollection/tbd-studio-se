package org.talend.oozie.scheduler.ui.prefs;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.talend.oozie.scheduler.OozieSchedulerPlugin;
import org.talend.oozie.scheduler.constants.IOoziePrefConstants;

/**
 * 
 * created by ycbai on 2015年4月22日 Detailled comment
 *
 */
public class OozieExecutionPreferenceInitializer extends AbstractPreferenceInitializer {

    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = OozieSchedulerPlugin.getDefault().getPreferenceStore();
        store.setDefault(IOoziePrefConstants.OOZIE_STATUS_TIMEOUT, IOoziePrefConstants.OOZIE_STATUS_TIMEOUT_DEFAULT);
    }

}
