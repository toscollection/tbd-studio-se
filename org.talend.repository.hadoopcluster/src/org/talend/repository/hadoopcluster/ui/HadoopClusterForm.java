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
package org.talend.repository.hadoopcluster.ui;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledFileField;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.hadoop.version.EAuthenticationMode;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.hadoop.version.custom.HadoopCustomVersionDefineDialog;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterValidator;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopClusterForm extends AbstractHadoopForm<HadoopClusterConnection> {

    private static final int VISIBLE_DISTRIBUTION_COUNT = 5;

    private static final int VISIBLE_VERSION_COUNT = 6;

    private LabelledCombo distributionCombo;

    private LabelledCombo versionCombo;

    private LabelledCombo authenticationCombo;

    private Button kerberosBtn;

    private Button keytabBtn;

    private LabelledText namenodeUriText;

    private LabelledText jobtrackerUriText;

    private LabelledText namenodePrincipalText;

    private LabelledText keytabPrincipalText;

    private LabelledText userNameText;

    private LabelledText groupText;

    private LabelledFileField keytabText;

    private Button customButton;

    private Group customGroup;

    private Button useYarnButton;

    public HadoopClusterForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
        super(parent, SWT.NONE, existingNames);
        this.connectionItem = connectionItem;
        setConnectionItem(connectionItem);
        setupForm();
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    public void initialize() {
        EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(getConnection().getDistribution(), false);
        if (distribution != null) {
            distributionCombo.setText(distribution.getDisplayName());
            updateVersionPart();
        }
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getDfVersion());
        if (version4Drivers != null) {
            versionCombo.setText(version4Drivers.getVersionDisplay());
        }
        EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(getConnection().getAuthMode(), false);
        if (authMode != null) {
            authenticationCombo.setText(authMode.getDisplayName());
        }
        useYarnButton.setSelection(getConnection().isUseYarn());
        namenodeUriText.setText(getConnection().getNameNodeURI());
        jobtrackerUriText.setText(getConnection().getJobTrackerURI());
        kerberosBtn.setSelection(getConnection().isEnableKerberos());
        namenodePrincipalText.setText(getConnection().getPrincipal());
        keytabBtn.setSelection(getConnection().isUseKeytab());
        keytabPrincipalText.setText(getConnection().getKeytabPrincipal());
        keytabText.setText(getConnection().getKeytab());
        userNameText.setText(getConnection().getUserName());
        groupText.setText(getConnection().getGroup());

        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        distributionCombo.setReadOnly(readOnly);
        versionCombo.setReadOnly(readOnly);
        namenodeUriText.setReadOnly(readOnly);
        jobtrackerUriText.setReadOnly(readOnly);
        kerberosBtn.setEnabled(!readOnly);
        namenodePrincipalText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
        groupText.setReadOnly(readOnly);
    }

    @Override
    protected void addFields() {
        addVersionFields();
        addCustomFields();
        addConnectionFields();
        addAuthenticationFields();
        updateVersionPart();
    }

    private void addVersionFields() {
        Group distributionGroup = Form.createGroup(this, 4, Messages.getString("HadoopClusterForm.versionSettings")); //$NON-NLS-1$
        distributionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        distributionCombo = new LabelledCombo(distributionGroup,
                Messages.getString("HadoopClusterForm.distribution"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.distribution.tooltip"), EHadoopDistributions.getAllDistributionDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        distributionCombo.setVisibleItemCount(VISIBLE_DISTRIBUTION_COUNT);
        versionCombo = new LabelledCombo(distributionGroup, Messages.getString("HadoopClusterForm.version"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.version.tooltip"), new String[0], 1, true); //$NON-NLS-1$
        versionCombo.setVisibleItemCount(VISIBLE_VERSION_COUNT);
        customButton = new Button(distributionGroup, SWT.NULL);
        customButton.setImage(ImageProvider.getImage(EImage.THREE_DOTS_ICON));
        customButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
        useYarnButton = new Button(distributionGroup, SWT.CHECK);
        useYarnButton.setText(Messages.getString("HadoopClusterForm.useYarn")); //$NON-NLS-1$
        useYarnButton.setLayoutData(new GridData(SWT.BEGINNING, SWT.CENTER, true, false, 2, 1));
    }

    private void addCustomFields() {
        customGroup = Form.createGroup(this, 4, Messages.getString("HadoopClusterForm.customSettings")); //$NON-NLS-1$
        customGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        authenticationCombo = new LabelledCombo(customGroup,
                Messages.getString("HadoopClusterForm.authentication"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.authentication.tooltip"), EAuthenticationMode.getAllAuthenticationDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 1, false);
    }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("HadoopClusterForm.connectionSettings"), 110); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        Composite uriPartComposite = new Composite(connectionGroup, SWT.NULL);
        GridLayout uriPartLayout = new GridLayout(2, false);
        uriPartLayout.marginWidth = 0;
        uriPartLayout.marginHeight = 0;
        uriPartComposite.setLayout(uriPartLayout);
        uriPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        namenodeUriText = new LabelledText(uriPartComposite, Messages.getString("HadoopClusterForm.text.namenodeURI"), 1); //$NON-NLS-1$
        jobtrackerUriText = new LabelledText(uriPartComposite, Messages.getString("HadoopClusterForm.text.jobtrackerURI"), 1); //$NON-NLS-1$
    }

    private void addAuthenticationFields() {
        Group authGroup = Form.createGroup(this, 1, Messages.getString("HadoopClusterForm.authenticationSettings"), 110); //$NON-NLS-1$
        authGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite authPartComposite = new Composite(authGroup, SWT.NULL);
        GridLayout authPartLayout = new GridLayout(1, false);
        authPartLayout.marginWidth = 0;
        authPartLayout.marginHeight = 0;
        authPartComposite.setLayout(authPartLayout);
        authPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite authCommonComposite = new Composite(authPartComposite, SWT.NULL);
        GridLayout authCommonCompLayout = new GridLayout(4, false);
        authCommonCompLayout.marginWidth = 0;
        authCommonCompLayout.marginHeight = 0;
        authCommonComposite.setLayout(authCommonCompLayout);
        authCommonComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        kerberosBtn = new Button(authCommonComposite, SWT.CHECK);
        kerberosBtn.setText(Messages.getString("HadoopClusterForm.button.kerberos")); //$NON-NLS-1$
        kerberosBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 4, 1));
        namenodePrincipalText = new LabelledText(authCommonComposite,
                Messages.getString("HadoopClusterForm.text.namenodePrincipal"), 1); //$NON-NLS-1$
        userNameText = new LabelledText(authCommonComposite, Messages.getString("HadoopClusterForm.text.userName"), 1); //$NON-NLS-1$
        groupText = new LabelledText(authCommonComposite, Messages.getString("HadoopClusterForm.text.group"), 1); //$NON-NLS-1$

        Composite authKeytabComposite = new Composite(authGroup, SWT.NULL);
        GridLayout authKeytabCompLayout = new GridLayout(5, false);
        authKeytabCompLayout.marginWidth = 0;
        authKeytabCompLayout.marginHeight = 0;
        authKeytabComposite.setLayout(authKeytabCompLayout);
        authKeytabComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        keytabBtn = new Button(authKeytabComposite, SWT.CHECK);
        keytabBtn.setText(Messages.getString("HadoopClusterForm.button.keytab")); //$NON-NLS-1$
        keytabBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 5, 1));
        keytabPrincipalText = new LabelledText(authKeytabComposite,
                Messages.getString("HadoopClusterForm.text.keytabPrincipal"), 1); //$NON-NLS-1$
        String[] extensions = { "*.*" }; //$NON-NLS-1$
        keytabText = new LabelledFileField(authKeytabComposite, Messages.getString("HadoopClusterForm.text.keytab"), extensions); //$NON-NLS-1$
    }

    private List<String> getDistributionVersions(EHadoopDistributions distribution) {
        List<String> result = new ArrayList<String>();
        List<EHadoopVersion4Drivers> v4dList = EHadoopVersion4Drivers.indexOfByDistribution(distribution);
        for (EHadoopVersion4Drivers v4d : v4dList) {
            result.add(v4d.getVersionDisplay());
        }
        return result;
    }

    @Override
    protected void addFieldsListeners() {
        distributionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String newDistributionDisplayName = distributionCombo.getText();
                EHadoopDistributions newDistribution = EHadoopDistributions
                        .getDistributionByDisplayName(newDistributionDisplayName);
                String originalDistributionName = getConnection().getDistribution();
                EHadoopDistributions originalDistribution = EHadoopDistributions.getDistributionByName(originalDistributionName,
                        false);
                if (newDistribution != null && newDistribution != originalDistribution) {
                    getConnection().setDistribution(newDistribution.getName());
                    getConnection().setUseCustomVersion(newDistribution == EHadoopDistributions.CUSTOM);
                    getConnection().setDfVersion(null);
                    updateVersionPart();
                    updateYarnContent();
                    updateConnectionPart();
                    checkFieldsValue();
                }
            }
        });

        versionCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String newVersionDisplayName = versionCombo.getText();
                EHadoopVersion4Drivers newVersion4Drivers = EHadoopVersion4Drivers.indexOfByVersionDisplay(newVersionDisplayName);
                String originalVersionName = getConnection().getDfVersion();
                EHadoopVersion4Drivers originalVersion4Drivers = EHadoopVersion4Drivers.indexOfByVersion(originalVersionName);
                if (newVersion4Drivers != null && newVersion4Drivers != originalVersion4Drivers) {
                    getConnection().setDfVersion(newVersion4Drivers.getVersionValue());
                    if (newVersion4Drivers.isSupportYARN() && !newVersion4Drivers.isSupportMR1()) {
                        getConnection().setUseYarn(true);
                    } else {
                        getConnection().setUseYarn(false);
                    }
                    updateYarnContent();
                    updateConnectionPart();
                    checkFieldsValue();
                }
            }
        });

        authenticationCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String newAuthDisplayName = authenticationCombo.getText();
                EAuthenticationMode newAuthMode = EAuthenticationMode.getAuthenticationByDisplayName(newAuthDisplayName);
                String originalAuthName = getConnection().getAuthMode();
                EAuthenticationMode originalAuthMode = EAuthenticationMode.getAuthenticationByName(originalAuthName, false);
                if (newAuthMode != null && newAuthMode != originalAuthMode) {
                    getConnection().setAuthMode(newAuthMode.getName());
                    updateConnectionPart();
                    checkFieldsValue();
                }
            }
        });

        customButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                HadoopCustomVersionDefineDialog customVersionDialog = new HadoopCustomVersionDefineDialog(getShell(),
                        HCVersionUtil.getCustomVersionMap(getConnection()));
                if (customVersionDialog.open() == Window.OK) {
                    HCVersionUtil.injectCustomVersionMap(getConnection(), customVersionDialog.getLibMap());
                }
            }
        });

        useYarnButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setUseYarn(useYarnButton.getSelection());
                updateConnectionPart();
            }
        });

        namenodeUriText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setNameNodeURI(namenodeUriText.getText());
                checkFieldsValue();
            }
        });

        jobtrackerUriText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setJobTrackerURI(jobtrackerUriText.getText());
                checkFieldsValue();
            }
        });

        namenodePrincipalText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setPrincipal(namenodePrincipalText.getText());
                checkFieldsValue();
            }
        });

        userNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });

        groupText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setGroup(groupText.getText());
                checkFieldsValue();
            }
        });

        kerberosBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setEnableKerberos(kerberosBtn.getSelection());
                updateConnectionPart();
                checkFieldsValue();
            }
        });

        keytabBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setUseKeytab(keytabBtn.getSelection());
                updateConnectionPart();
                checkFieldsValue();
            }
        });

        keytabPrincipalText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setKeytabPrincipal(keytabPrincipalText.getText());
                checkFieldsValue();
            }
        });

        keytabText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setKeytab(keytabText.getText());
                checkFieldsValue();
            }
        });

    }

    private void updateVersionPart() {
        EHadoopDistributions distribution = EHadoopDistributions.getDistributionByName(getConnection().getDistribution(), false);
        if (distribution == EHadoopDistributions.CUSTOM) {
            versionCombo.setHideWidgets(true);
            hideControl(useYarnButton, false);
            hideControl(customButton, false);
            hideControl(customGroup, false);
        } else {
            versionCombo.setHideWidgets(false);
            hideControl(useYarnButton, true);
            hideControl(customButton, true);
            hideControl(customGroup, true);
            List<String> items = getDistributionVersions(distribution);
            String[] versions = new String[items.size()];
            items.toArray(versions);
            versionCombo.getCombo().setItems(versions);
            if (versions.length > 0) {
                versionCombo.getCombo().select(0);
            }
        }
    }

    private void updateConnectionPart() {
        HadoopClusterConnection connection = getConnection();
        String distribution = connection.getDistribution();
        if (EHadoopDistributions.CUSTOM.getName().equals(distribution)) {
            String authModeName = connection.getAuthMode();
            if (authModeName != null) {
                EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(authModeName, false);
                switch (authMode) {
                case KRB:
                    kerberosBtn.setEnabled(true);
                    namenodePrincipalText.setEditable(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
                    keytabBtn.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
                    keytabPrincipalText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
                    keytabText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
                    userNameText.setEditable(false);
                    groupText.setEditable(false);
                    break;
                case UGI:
                    kerberosBtn.setEnabled(false);
                    namenodePrincipalText.setEditable(false);
                    keytabBtn.setEnabled(false);
                    keytabPrincipalText.setEditable(false);
                    keytabText.setEditable(false);
                    userNameText.setEditable(true);
                    groupText.setEditable(true);
                    break;
                default:
                    kerberosBtn.setEnabled(false);
                    namenodePrincipalText.setEditable(false);
                    keytabBtn.setEnabled(false);
                    keytabPrincipalText.setEditable(false);
                    keytabText.setEditable(false);
                    userNameText.setEditable(true);
                    groupText.setEditable(false);
                    break;
                }
            }
        } else {
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(connection.getDfVersion());
            kerberosBtn.setEnabled(version4Drivers.isSupportSecurity());
            namenodePrincipalText.setEditable(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
            keytabBtn.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
            keytabPrincipalText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
            keytabText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
            groupText.setEditable(version4Drivers.isSupportGroup());
            userNameText.setEditable(!kerberosBtn.getSelection());
        }
        updateJobtrackerContent();
        updateConnectionContent();
    }

    private void updateJobtrackerContent() {
        jobtrackerUriText
                .setLabelText(getConnection().isUseYarn() ? Messages.getString("HadoopClusterForm.text.resourceManager") : Messages.getString("HadoopClusterForm.text.jobtrackerURI")); //$NON-NLS-1$ //$NON-NLS-2$
        jobtrackerUriText.getTextControl().getParent().layout();
    }

    private void updateYarnContent() {
        useYarnButton.setSelection(getConnection().isUseYarn());
    }

    private void updateConnectionContent() {
        if (!kerberosBtn.isEnabled()) {
            kerberosBtn.setSelection(false);
            namenodePrincipalText.setText(EMPTY_STRING);
            getConnection().setEnableKerberos(false);
        }
        if (!groupText.getEditable()) {
            groupText.setText(EMPTY_STRING);
        }
        if (!userNameText.getEditable()) {
            userNameText.setText(EMPTY_STRING);
        }
    }

    @Override
    public boolean checkFieldsValue() {
        if (distributionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.distribution")); //$NON-NLS-1$
            return false;
        }

        if (!getConnection().isUseCustomVersion()) {
            if (versionCombo.getSelectionIndex() == -1) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.version")); //$NON-NLS-1$
                return false;
            }
        }

        if (getConnection().isUseCustomVersion()) {
            if (authenticationCombo.getSelectionIndex() == -1) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.authentication")); //$NON-NLS-1$
                return false;
            }
        }

        if (!validText(namenodeUriText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodeURI")); //$NON-NLS-1$
            return false;
        }

        if (!HadoopParameterValidator.isValidNamenodeURI(namenodeUriText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodeURI.invalid")); //$NON-NLS-1$
            return false;
        }

        if (!validText(jobtrackerUriText.getText())) {
            updateStatus(IStatus.ERROR,
                    Messages.getString("HadoopClusterForm.check.jobtrackerURI", jobtrackerUriText.getLabelText())); //$NON-NLS-1$
            return false;
        }

        if (!HadoopParameterValidator.isValidJobtrackerURI(jobtrackerUriText.getText())) {
            updateStatus(IStatus.ERROR,
                    Messages.getString("HadoopClusterForm.check.jobtrackerURI.invalid", jobtrackerUriText.getLabelText())); //$NON-NLS-1$
            return false;
        }

        if (namenodePrincipalText.getEditable()) {
            if (!validText(namenodePrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodePrincipal")); //$NON-NLS-1$
                return false;
            }
            if (!HadoopParameterValidator.isValidPrincipal(namenodePrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodePrincipal.invalid")); //$NON-NLS-1$
                return false;
            }
        }

        if (groupText.getEditable()) {
            if (!validText(userNameText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.userName")); //$NON-NLS-1$
                return false;
            }
            if (!validText(groupText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.group")); //$NON-NLS-1$
                return false;
            }
            if (!HadoopParameterValidator.isValidGroup(groupText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.group.invalid")); //$NON-NLS-1$
                return false;
            }
        }

        if (validText(userNameText.getText()) && !HadoopParameterValidator.isValidUserName(userNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.userName.invalid")); //$NON-NLS-1$
            return false;
        }

        if (keytabPrincipalText.getEditable()) {
            if (!validText(keytabPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.keytabPrincipal")); //$NON-NLS-1$
                return false;
            }
            if (!HadoopParameterValidator.isValidPrincipal(keytabPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.keytabPrincipal.invalid")); //$NON-NLS-1$
                return false;
            }
        }

        if (keytabText.getEditable()) {
            if (!validText(keytabText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.keytab")); //$NON-NLS-1$
                return false;
            }
        }

        updateStatus(IStatus.OK, null);
        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
        updateConnectionPart();
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            updateStatus(getStatusLevel(), getStatus());
        }
    }

}
