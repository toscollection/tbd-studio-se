// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.ui;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.talend.oozie.scheduler.constants.TOozieUIConstants;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for Talend Oozie Scheduler setup dialog.
 */
public class TOozieSettingDialog extends Dialog {

    private String hadoopDistributionValue;

    private String hadoopVersionValue;

    private String nameNodeEndPointValue;

    private String jobTrackerEndPointValue;

    private String oozieEndPointValue;

    private String userNameValue;

    private String customJars;

    private OozieSettingComposite settingComposite;

    private String repositoryId;

    private boolean enableKerberos;

    private String principalValue;

    private List<HashMap<String, Object>> propertiesValue;

    /**
     * @param parentShell
     */
    public TOozieSettingDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(this.getShellStyle() | SWT.RESIZE);
    }

    @Override
    protected void configureShell(Shell parentShell) {
        super.configureShell(parentShell);
        parentShell.setText(TOozieUIConstants.OOZIE_DLG_SETTING_TITLE);
    }

    @Override
    protected Control createDialogArea(Composite parent) {
        settingComposite = new OozieSettingComposite(parent, SWT.BORDER, false) {

            /*
             * (non-Javadoc)
             * 
             * @see org.talend.oozie.scheduler.ui.OozieSettingComposite#preInitialization()
             */
            @Override
            protected void preInitialization() {
                super.preInitialization();
                setHadoopDistributionValue(hadoopDistributionValue);
                setHadoopVersionValue(hadoopVersionValue);
                setNameNodeEndPointValue(nameNodeEndPointValue);
                setJobTrackerEndPointValue(jobTrackerEndPointValue);
                setOozieEndPointValue(oozieEndPointValue);
                setUserNameValue(userNameValue);
                setCustomJars(customJars);
                setRepositoryId(repositoryId);
                setEnableKerberos(enableKerberos);
                setPrincipal(principalValue);
                setProperties(propertiesValue);
            }
        };

        return parent;
    }

    /**
     * Reset the dialog size.
     */
    @Override
    protected Point getInitialSize() {
        Point result = super.getInitialSize();
        result.x = 500;
        result.y = 600;
        return result;
    }

    public String getHadoopDistributionValue() {
        return settingComposite.getHadoopDistributionValue();
    }

    public void setHadoopDistributionValue(String hadoopDistributionValue) {
        this.hadoopDistributionValue = hadoopDistributionValue;
    }

    public String getHadoopVersionValue() {
        return settingComposite.getHadoopVersionValue();
    }

    public void setHadoopVersionValue(String hadoopVersionValue) {
        this.hadoopVersionValue = hadoopVersionValue;
    }

    public String getNameNodeEndPointValue() {
        return settingComposite.getNameNodeEndPointValue();
    }

    public void setNameNodeEndPointValue(String nameNodeEndPointValue) {
        this.nameNodeEndPointValue = nameNodeEndPointValue;
    }

    public String getJobTrackerEndPointValue() {
        return settingComposite.getJobTrackerEndPointValue();
    }

    public void setJobTrackerEndPointValue(String jobTrackerEndPointValue) {
        this.jobTrackerEndPointValue = jobTrackerEndPointValue;
    }

    public String getOozieEndPointValue() {
        return settingComposite.getOozieEndPointValue();
    }

    public void setOozieEndPointValue(String oozieEndPointValue) {
        this.oozieEndPointValue = oozieEndPointValue;
    }

    public String getUserNameValue() {
        return settingComposite.getUserNameValue();
    }

    public void setUserNameValue(String userNameValue) {
        this.userNameValue = userNameValue;
    }

    public String getCustomJars() {
        return settingComposite.getCustomJars();
    }

    public void setCustomJars(String customJars) {
        this.customJars = customJars;
    }

    public void setRepositoryId(String repositoryId) {
        this.repositoryId = repositoryId;
    }

    public String getRepositoryId() {
        return settingComposite.getRepositoryId();
    }

    public boolean isEnableKerberos() {
        return settingComposite.isEnableKerberos();
    }

    public void setEnableKerberos(boolean enableKerberos) {
        this.enableKerberos = enableKerberos;
    }

    public String getPrincipalValue() {
        return settingComposite.getPrincipal();
    }

    public void setPrincipalValue(String principalValue) {
        this.principalValue = principalValue;
    }

    public void setPropertiesValue(List<HashMap<String, Object>> propertiesValue) {
        this.propertiesValue = propertiesValue;
    }

    public List<HashMap<String, Object>> getPropertiesValue() {
        return settingComposite.getProperties();
    }

}
