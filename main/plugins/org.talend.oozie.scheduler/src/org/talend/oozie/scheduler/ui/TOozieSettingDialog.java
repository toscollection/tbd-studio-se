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

    private boolean useYarn;

    private String authMode;

    private String userNameValue;

    private String group;

    private String customJars;

    private OozieSettingComposite settingComposite;

    private String repositoryId;

    private boolean enableKerberos;

    private boolean enableOoKerberos;

    private String principalValue;

    private boolean useKeytab;

    private String ktPrincipal;

    private String keytab;

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
                setNameNodeEndPointValue(nameNodeEndPointValue);
                setJobTrackerEndPointValue(jobTrackerEndPointValue);
                setOozieEndPointValue(oozieEndPointValue);
                setUserNameValue(userNameValue);
                setUseYarn(useYarn);
                setAuthMode(authMode);
                setGroup(group);
                setCustomJars(customJars);
                setRepositoryId(repositoryId);
                setEnableKerberos(enableKerberos);
                setEnableOoKerberos(enableOoKerberos);
                setPrincipal(principalValue);
                setUseKeytab(useKeytab);
                setKtPrincipal(ktPrincipal);
                setKeytab(keytab);
                setProperties(propertiesValue);
            }

            @Override
            protected void createContents(Composite parent, boolean forPrefPage) {
                super.createContents(parent, forPrefPage);
                setHadoopDistributionValue(hadoopDistributionValue);
                setHadoopVersionValue(hadoopVersionValue);
            }
        };

        return parent;
    }

    public String getHadoopDistributionValue() {
        return this.hadoopDistributionValue;

    }

    public void setHadoopDistributionValue(String hadoopDistributionValue) {
        this.hadoopDistributionValue = hadoopDistributionValue;
    }

    public String getHadoopVersionValue() {
        return this.hadoopVersionValue;
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

    /**
     * Getter for enableOoKerberos.
     * 
     * @return the enableOoKerberos
     */
    public boolean isEnableOoKerberos() {
        return settingComposite.isEnableOoKerberos();
    }

    /**
     * Sets the enableOoKerberos.
     * 
     * @param enableOoKerberos the enableOoKerberos to set
     */
    public void setEnableOoKerberos(boolean enableOoKerberos) {
        this.enableOoKerberos = enableOoKerberos;
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

    /**
     * Getter for group.
     * 
     * @return the group
     */
    public String getGroup() {
        return settingComposite.getGroup();
    }

    /**
     * Sets the group.
     * 
     * @param group the group to set
     */
    public void setGroup(String group) {
        this.group = group;
    }

    /**
     * Getter for useKeytab.
     * 
     * @return the useKeytab
     */
    public boolean isUseKeytab() {
        return settingComposite.isUseKeytab();
    }

    /**
     * Sets the useKeytab.
     * 
     * @param useKeytab the useKeytab to set
     */
    public void setUseKeytab(boolean useKeytab) {
        this.useKeytab = useKeytab;
    }

    /**
     * Getter for ktPrincipal.
     * 
     * @return the ktPrincipal
     */
    public String getKtPrincipal() {
        return settingComposite.getKtPrincipal();
    }

    /**
     * Sets the ktPrincipal.
     * 
     * @param ktPrincipal the ktPrincipal to set
     */
    public void setKtPrincipal(String ktPrincipal) {
        this.ktPrincipal = ktPrincipal;
    }

    /**
     * Getter for keytab.
     * 
     * @return the keytab
     */
    public String getKeytab() {
        return settingComposite.getKeytab();
    }

    /**
     * Sets the keytab.
     * 
     * @param keytab the keytab to set
     */
    public void setKeytab(String keytab) {
        this.keytab = keytab;
    }

    /**
     * Getter for useYarn.
     * 
     * @return the useYarn
     */
    public boolean isUseYarn() {
        return settingComposite.isUseYarn();
    }

    /**
     * Sets the useYarn.
     * 
     * @param useYarn the useYarn to set
     */
    public void setUseYarn(boolean useYarn) {
        this.useYarn = useYarn;
    }

    /**
     * Getter for authMode.
     * 
     * @return the authMode
     */
    public String getAuthMode() {
        return settingComposite.getAuthMode();
    }

    /**
     * Sets the authMode.
     * 
     * @param authMode the authMode to set
     */
    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    @Override
    protected void okPressed() {
        this.setHadoopDistributionValue(settingComposite.getHadoopDistributionValue());
        this.setHadoopVersionValue(settingComposite.getHadoopVersionValue());
        super.okPressed();
    }

}
