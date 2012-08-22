// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.ui;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.util.EHadoopDistributions;
import org.talend.repository.hdfs.util.EHadoopVersion4Drivers;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSForm extends AbstractHDFSForm {

    private static final int VISIBLE_DISTRIBUTION_COUNT = 5;

    private static final int VISIBLE_VERSION_COUNT = 6;

    private boolean readOnly;

    private LabelledCombo distributionCombo;

    private LabelledCombo versionCombo;

    private Button kerberosBtn;

    private UtilsButton checkConnectionBtn;

    private LabelledText namenodeUriText;

    private LabelledText principalText;

    private LabelledText userNameText;

    private LabelledText groupText;

    public HDFSForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
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
            String distributionDisplayName = distribution.getDisplayName();
            distributionCombo.setText(distributionDisplayName);
            updateVersionCombo(distributionDisplayName);
        }
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getDfVersion());
        if (version4Drivers != null) {
            versionCombo.setText(version4Drivers.getVersionDisplay());
        }
        namenodeUriText.setText(getConnection().getNameNodeURI());
        kerberosBtn.setSelection(getConnection().isEnableKerberos());
        principalText.setText(getConnection().getPrincipal());
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
        kerberosBtn.setEnabled(!readOnly);
        principalText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
        groupText.setReadOnly(readOnly);
    }

    @Override
    protected void addFields() {
        addDistributionFields();
        addConnectionFields();
        addCheckFields();
    }

    private void addDistributionFields() {
        Group distributionGroup = Form.createGroup(this, 1, Messages.getString("HDFSForm.distributionSettings"), 60); //$NON-NLS-1$

        ScrolledComposite distributionComposite = new ScrolledComposite(distributionGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        distributionComposite.setExpandHorizontal(true);
        distributionComposite.setExpandVertical(true);
        distributionComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite distributionGroupComposite = Form.startNewGridLayout(distributionComposite, 4);
        GridLayout disGroupCompLayout = (GridLayout) distributionGroupComposite.getLayout();
        disGroupCompLayout.marginHeight = 0;
        disGroupCompLayout.marginTop = 0;
        disGroupCompLayout.marginBottom = 0;
        disGroupCompLayout.marginLeft = 0;
        disGroupCompLayout.marginRight = 0;
        disGroupCompLayout.marginWidth = 0;
        distributionComposite.setContent(distributionGroupComposite);

        distributionCombo = new LabelledCombo(distributionGroupComposite, Messages.getString("HDFSForm.distribution"), //$NON-NLS-1$
                Messages.getString("HDFSForm.distribution.tooltip"), EHadoopDistributions.getAllDistributionDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        distributionCombo.setVisibleItemCount(VISIBLE_DISTRIBUTION_COUNT);
        versionCombo = new LabelledCombo(distributionGroupComposite, Messages.getString("HDFSForm.version"), //$NON-NLS-1$
                Messages.getString("HDFSForm.version.tooltip"), new String[0], 1, true); //$NON-NLS-1$
        versionCombo.setVisibleItemCount(VISIBLE_VERSION_COUNT);
    }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("HDFSForm.connectionSettings"), 110); //$NON-NLS-1$

        ScrolledComposite connectionComposite = new ScrolledComposite(connectionGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        connectionComposite.setExpandHorizontal(true);
        connectionComposite.setExpandVertical(true);
        connectionComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite connectionGroupComposite = Form.startNewGridLayout(connectionComposite, 1);
        GridLayout disGroupCompLayout = (GridLayout) connectionGroupComposite.getLayout();
        disGroupCompLayout.marginHeight = 0;
        disGroupCompLayout.marginTop = 0;
        disGroupCompLayout.marginBottom = 0;
        disGroupCompLayout.marginLeft = 0;
        disGroupCompLayout.marginRight = 0;
        disGroupCompLayout.marginWidth = 0;
        connectionComposite.setContent(connectionGroupComposite);

        int width = getSize().x;
        Composite namenodePartComposite = new Composite(connectionGroupComposite, SWT.NULL);
        namenodePartComposite.setLayout(new GridLayout(2, false));
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.minimumWidth = width;
        gridData.widthHint = width;
        namenodePartComposite.setLayoutData(gridData);
        namenodeUriText = new LabelledText(namenodePartComposite, Messages.getString("HDFSForm.text.namenodeURI"), 1); //$NON-NLS-1$

        Composite connectionPartComposite = new Composite(connectionGroupComposite, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(4, false));
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.minimumWidth = width;
        gridData.widthHint = width;
        connectionPartComposite.setLayoutData(gridData);
        kerberosBtn = new Button(connectionPartComposite, SWT.CHECK);
        kerberosBtn.setText(Messages.getString("HDFSForm.button.kerberos")); //$NON-NLS-1$
        kerberosBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
        principalText = new LabelledText(connectionPartComposite, Messages.getString("HDFSForm.text.principal"), 1); //$NON-NLS-1$
        userNameText = new LabelledText(connectionPartComposite, Messages.getString("HDFSForm.text.userName"), 1); //$NON-NLS-1$
        groupText = new LabelledText(connectionPartComposite, Messages.getString("HDFSForm.text.group"), 1); //$NON-NLS-1$
    }

    private void addCheckFields() {
        Composite checkGroup = new Composite(this, SWT.NONE);
        GridLayout checkGridLayout = new GridLayout(1, false);
        checkGroup.setLayout(checkGridLayout);
        GridData checkGridData = new GridData(GridData.FILL_HORIZONTAL);
        checkGridData.minimumHeight = 5;
        checkGroup.setLayoutData(checkGridData);
        Composite checkButtonComposite = Form.startNewGridLayout(checkGroup, 1, false, SWT.CENTER, SWT.BOTTOM);
        GridLayout checkButtonLayout = (GridLayout) checkButtonComposite.getLayout();
        checkButtonLayout.marginHeight = 0;
        checkButtonLayout.marginTop = 0;
        checkButtonLayout.marginBottom = 0;
        checkButtonLayout.marginLeft = 0;
        checkButtonLayout.marginRight = 0;
        checkButtonLayout.marginWidth = 0;
        checkConnectionBtn = new UtilsButton(checkButtonComposite, "Check", WIDTH_BUTTON_PIXEL, HEIGHT_BUTTON_PIXEL);
        checkConnectionBtn.setEnabled(false);
    }

    private List<String> getDistributionVersions(String distribution) {
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

            public void modifyText(final ModifyEvent e) {
                String newDistributionDisplayName = distributionCombo.getText();
                EHadoopDistributions newDistribution = EHadoopDistributions
                        .getDistributionByDisplayName(newDistributionDisplayName);
                String originalDistributionName = getConnection().getDistribution();
                EHadoopDistributions originalDistribution = EHadoopDistributions.getDistributionByName(originalDistributionName,
                        false);
                if (newDistribution != null && newDistribution != originalDistribution) {
                    getConnection().setDistribution(newDistribution.getName());
                    updateVersionCombo(newDistributionDisplayName);
                    checkFieldsValue();
                }
            }
        });

        versionCombo.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                String newVersionDisplayName = versionCombo.getText();
                EHadoopVersion4Drivers newVersion4Drivers = EHadoopVersion4Drivers.indexOfByVersionDisplay(newVersionDisplayName);
                String originalVersionName = getConnection().getDfVersion();
                EHadoopVersion4Drivers originalVersion4Drivers = EHadoopVersion4Drivers.indexOfByVersion(originalVersionName);
                if (newVersion4Drivers != null && newVersion4Drivers != originalVersion4Drivers) {
                    getConnection().setDfVersion(newVersion4Drivers.getVersionValue());
                    getConnection().setDfDrivers(newVersion4Drivers.getDriverStrings());
                    updateConnectionPart();
                    checkFieldsValue();
                }
            }
        });

        namenodeUriText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setNameNodeURI(namenodeUriText.getText());
                checkFieldsValue();
            }
        });

        principalText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setPrincipal(principalText.getText());
                checkFieldsValue();
            }
        });

        userNameText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });

        groupText.addModifyListener(new ModifyListener() {

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
    }

    private void updateVersionCombo(String distribution) {
        List<String> items = getDistributionVersions(distribution);
        String[] versions = new String[items.size()];
        items.toArray(versions);
        versionCombo.getCombo().setItems(versions);
        if (versions.length > 0) {
            versionCombo.getCombo().select(0);
        }
    }

    private void updateConnectionPart() {
        HDFSConnection connection = getConnection();
        String dfVersion = connection.getDfVersion();
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(dfVersion);
        kerberosBtn.setEnabled(isSupportSecurity(version4Drivers));
        groupText.setEnabled(isSupportGroup(version4Drivers));
        principalText.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
        userNameText.setEnabled(!kerberosBtn.getSelection());
        if (!kerberosBtn.isEnabled()) {
            kerberosBtn.setSelection(false);
            principalText.setText(EMPTY_STRING);
        }
        if (!groupText.getEnable()) {
            groupText.setText(EMPTY_STRING);
        }
    }

    private boolean isSupportSecurity(EHadoopVersion4Drivers version4Drivers) {
        if (version4Drivers != null) {
            switch (version4Drivers) {
            case HDP_1_0:
            case APACHE_1_0_0:
                return true;
            default:
                return false;
            }
        }

        return false;
    }

    private boolean isSupportGroup(EHadoopVersion4Drivers version4Drivers) {
        if (version4Drivers != null) {
            switch (version4Drivers) {
            case APACHE_0_20_2:
            case MAPR:
                return true;
            default:
                return false;
            }
        }

        return false;
    }

    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

        if (distributionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.distribution")); //$NON-NLS-1$
            return false;
        }
        if (versionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.version")); //$NON-NLS-1$
            return false;
        }

        if (!validText(namenodeUriText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.namenodeURI")); //$NON-NLS-1$
            return false;
        }

        if (kerberosBtn.isEnabled() && kerberosBtn.getSelection() && !validText(principalText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.principal")); //$NON-NLS-1$
            return false;
        }

        if (groupText.getEnable()) {
            if (!validText(userNameText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.userName")); //$NON-NLS-1$
                return false;
            }
            if (!validText(groupText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.group")); //$NON-NLS-1$
                return false;
            }
        }

        checkConnectionBtn.setEnabled(true);

        if (!hdfsSettingIsValide) {
            updateStatus(IStatus.INFO, Messages.getString("HDFSForm.check.checkConnection")); //$NON-NLS-1$
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;

    }

    private boolean validText(final String value) {
        return StringUtils.isNotEmpty(value);
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

    protected void addUtilsButtonListeners() {
        checkConnectionBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkConnection();
            }

        });
    }

}
