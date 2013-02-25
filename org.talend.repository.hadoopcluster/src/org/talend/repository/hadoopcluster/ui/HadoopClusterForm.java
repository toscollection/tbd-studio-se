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
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.util.EHadoopDistributions;
import org.talend.designer.hdfsbrowse.util.EHadoopVersion4Drivers;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopClusterForm extends AbstractHadoopForm<HadoopClusterConnection> {

    private static final int VISIBLE_DISTRIBUTION_COUNT = 5;

    private static final int VISIBLE_VERSION_COUNT = 6;

    private LabelledCombo distributionCombo;

    private LabelledCombo versionCombo;

    private Button kerberosBtn;

    private LabelledText namenodeUriText;

    private LabelledText jobtrackerUriText;

    private LabelledText principalText;

    private LabelledText userNameText;

    private LabelledText groupText;

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
            String distributionDisplayName = distribution.getDisplayName();
            distributionCombo.setText(distributionDisplayName);
            updateVersionCombo(distributionDisplayName);
        }
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getDfVersion());
        if (version4Drivers != null) {
            versionCombo.setText(version4Drivers.getVersionDisplay());
        }
        namenodeUriText.setText(getConnection().getNameNodeURI());
        jobtrackerUriText.setText(getConnection().getJobTrackerURI());
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
        jobtrackerUriText.setReadOnly(readOnly);
        kerberosBtn.setEnabled(!readOnly);
        principalText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
        groupText.setReadOnly(readOnly);
    }

    @Override
    protected void addFields() {
        addVersionFields();
        addConnectionFields();
    }

    private void addVersionFields() {
        Group distributionGroup = Form.createGroup(this, 1, Messages.getString("HadoopClusterForm.versionSettings")); //$NON-NLS-1$
        distributionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

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

        distributionCombo = new LabelledCombo(distributionGroupComposite,
                Messages.getString("HadoopClusterForm.distribution"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.distribution.tooltip"), EHadoopDistributions.getAllDistributionDisplayNames() //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        distributionCombo.setVisibleItemCount(VISIBLE_DISTRIBUTION_COUNT);
        versionCombo = new LabelledCombo(distributionGroupComposite, Messages.getString("HadoopClusterForm.version"), //$NON-NLS-1$
                Messages.getString("HadoopClusterForm.version.tooltip"), new String[0], 1, true); //$NON-NLS-1$
        versionCombo.setVisibleItemCount(VISIBLE_VERSION_COUNT);
    }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("HadoopClusterForm.connectionSettings"), 110); //$NON-NLS-1$

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
        Composite uriPartComposite = new Composite(connectionGroupComposite, SWT.NULL);
        uriPartComposite.setLayout(new GridLayout(2, false));
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.minimumWidth = width;
        gridData.widthHint = width;
        uriPartComposite.setLayoutData(gridData);
        namenodeUriText = new LabelledText(uriPartComposite, Messages.getString("HadoopClusterForm.text.namenodeURI"), 1); //$NON-NLS-1$
        jobtrackerUriText = new LabelledText(uriPartComposite, Messages.getString("HadoopClusterForm.text.jobtrackerURI"), 1); //$NON-NLS-1$

        Composite connectionPartComposite = new Composite(connectionGroupComposite, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(4, false));
        gridData = new GridData(GridData.FILL_HORIZONTAL);
        gridData.minimumWidth = width;
        gridData.widthHint = width;
        connectionPartComposite.setLayoutData(gridData);
        kerberosBtn = new Button(connectionPartComposite, SWT.CHECK);
        kerberosBtn.setText(Messages.getString("HadoopClusterForm.button.kerberos")); //$NON-NLS-1$
        kerberosBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
        principalText = new LabelledText(connectionPartComposite, Messages.getString("HadoopClusterForm.text.principal"), 1); //$NON-NLS-1$
        userNameText = new LabelledText(connectionPartComposite, Messages.getString("HadoopClusterForm.text.userName"), 1); //$NON-NLS-1$
        groupText = new LabelledText(connectionPartComposite, Messages.getString("HadoopClusterForm.text.group"), 1); //$NON-NLS-1$
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
                    updateVersionCombo(newDistributionDisplayName);
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
                    updateConnectionPart();
                    checkFieldsValue();
                }
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

        principalText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setPrincipal(principalText.getText());
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
        HadoopClusterConnection connection = getConnection();
        String dfVersion = connection.getDfVersion();
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(dfVersion);
        kerberosBtn.setEnabled(isSupportSecurity(version4Drivers));
        groupText.setEnabled(isSupportGroup(version4Drivers));
        principalText.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
        userNameText.setEnabled(!kerberosBtn.getSelection());
        if (!kerberosBtn.isEnabled()) {
            kerberosBtn.setSelection(false);
            getConnection().setEnableKerberos(false);
            principalText.setText(EMPTY_STRING);
        }
        if (!groupText.getEnable()) {
            groupText.setText(EMPTY_STRING);
        }
    }

    @Override
    public boolean checkFieldsValue() {

        if (distributionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.distribution")); //$NON-NLS-1$
            return false;
        }
        if (versionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.version")); //$NON-NLS-1$
            return false;
        }

        if (!validText(namenodeUriText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodeURI")); //$NON-NLS-1$
            return false;
        }

        if (!validText(jobtrackerUriText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.jobtrackerURI")); //$NON-NLS-1$
            return false;
        }

        if (kerberosBtn.isEnabled() && kerberosBtn.getSelection() && !validText(principalText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.principal")); //$NON-NLS-1$
            return false;
        }

        if (groupText.getEnable()) {
            if (!validText(userNameText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.userName")); //$NON-NLS-1$
                return false;
            }
            if (!validText(groupText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.group")); //$NON-NLS-1$
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
