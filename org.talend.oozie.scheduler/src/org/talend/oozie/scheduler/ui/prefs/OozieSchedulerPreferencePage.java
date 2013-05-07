// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.ui.prefs;

import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.core.CorePlugin;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.oozie.scheduler.ui.OozieSettingComposite;

/**
 * created by ycbai on 2013-2-26 Detailled comment
 * 
 */
public class OozieSchedulerPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private OozieSettingComposite settingComposite;

    /**
     * DOC ycbai OozieSchedulerPreferencePage constructor comment.
     */
    public OozieSchedulerPreferencePage() {
        super(FLAT);
        setPreferenceStore(CorePlugin.getDefault().getPreferenceStore());
        this.noDefaultAndApplyButton();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createContents(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createContents(Composite p) {
        Composite parent = (Composite) super.createContents(p);

        settingComposite = new OozieSettingComposite(parent, SWT.NONE, true) {

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.oozie.scheduler.ui.OozieSettingComposite#preInitialization()
             */
            @Override
            protected void preInitialization() {
                super.preInitialization();
                setHadoopDistributionValue(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION));
                setHadoopVersionValue(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION));
                setNameNodeEndPointValue(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT));
                setJobTrackerEndPointValue(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT));
                setOozieEndPointValue(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT));
                setUserNameValue(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME));
                setCustomJars(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS));
                setEnableKerberos(CorePlugin.getDefault().getPreferenceStore()
                        .getBoolean(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KERBEROS));
                setPrincipal(getParamValueFromPreference(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_PRINCIPAL));
            }
        };
        return parent;
    }

    private static String getParamValueFromPreference(String prefKey) {
        String versionValue = CorePlugin.getDefault().getPreferenceStore().getString(prefKey);
        return versionValue;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    @Override
    public void init(IWorkbench workbench) {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#createFieldEditors()
     */
    @Override
    protected void createFieldEditors() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.preference.FieldEditorPreferencePage#performOk()
     */
    @Override
    public boolean performOk() {
        IPreferenceStore prefs = getPreferenceStore();
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_DISTRIBUTION,
                settingComposite.getHadoopDistributionValue());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_HADOOP_VERSION, settingComposite.getHadoopVersionValue());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_NAME_NODE_ENDPOINT, settingComposite.getNameNodeEndPointValue());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_JOB_TRACKER_ENDPOINT,
                settingComposite.getJobTrackerEndPointValue());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SHCEDULER_OOZIE_ENDPOINT, settingComposite.getOozieEndPointValue());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_USER_NAME, settingComposite.getUserNameValue());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_CUSTOM_JARS, settingComposite.getCustomJars());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_KERBEROS, settingComposite.isEnableKerberos());
        prefs.setValue(ITalendCorePrefConstants.OOZIE_SCHEDULER_HADOOP_PRINCIPAL, settingComposite.getPrincipal());
        return super.performOk();
    }

}
