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
package org.talend.repository.hcatalog.ui;

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
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.util.EHadoopDistributions;
import org.talend.designer.hdfsbrowse.util.EHadoopVersion4Drivers;
import org.talend.repository.hcatalog.i18n.Messages;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogForm extends AbstractHCatalogForm {

    private static final int VISIBLE_DISTRIBUTION_COUNT = 5;

    private static final int VISIBLE_VERSION_COUNT = 6;

    private boolean readOnly;

    private LabelledCombo distributionCombo;

    private LabelledCombo versionCombo;

    private UtilsButton checkConnectionBtn;

    private LabelledText hostText;

    private LabelledText portText;

    private LabelledText userNameText;

    private LabelledText databaseText;

    private Button kerberosBtn;

    private LabelledText krbPrincipalText;

    private LabelledText krbRealmText;

    private LabelledText nnPrincipalText;

    private Composite principalPartComposite;

    public HCatalogForm(Composite parent, ConnectionItem connectionItem, String[] existingNames) {
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
        EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getHcatVersion());
        if (version4Drivers != null) {
            versionCombo.setText(version4Drivers.getVersionDisplay());
        }
        hostText.setText(getConnection().getHostName());
        portText.setText(getConnection().getPort());
        userNameText.setText(getConnection().getUserName());
        databaseText.setText(getConnection().getDatabase());
        kerberosBtn.setSelection(getConnection().isEnableKerberos());
        krbPrincipalText.setText(getConnection().getKrbPrincipal());
        krbRealmText.setText(getConnection().getKrbRealm());
        nnPrincipalText.setText(getConnection().getNnPrincipal());

        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        distributionCombo.setReadOnly(readOnly);
        versionCombo.setReadOnly(readOnly);
        hostText.setReadOnly(readOnly);
        portText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
    }

    @Override
    protected void addFields() {
        addDistributionFields();
        addTempletonFields();
        addDatabaseFields();
        addCheckFields();
    }

    private void addDistributionFields() {
        Group distributionGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.distributionSettings"), 60); //$NON-NLS-1$

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

        distributionCombo = new LabelledCombo(
                distributionGroupComposite,
                Messages.getString("HCatalogForm.distribution"), //$NON-NLS-1$
                Messages.getString("HCatalogForm.distribution.tooltip"), new String[] { EHadoopDistributions.HORTONWORKS.getDisplayName() }, 1, true); //$NON-NLS-1$
        distributionCombo.setVisibleItemCount(VISIBLE_DISTRIBUTION_COUNT);
        versionCombo = new LabelledCombo(distributionGroupComposite, Messages.getString("HCatalogForm.version"), //$NON-NLS-1$
                Messages.getString("HCatalogForm.version.tooltip"), new String[0], 1, true); //$NON-NLS-1$
        versionCombo.setVisibleItemCount(VISIBLE_VERSION_COUNT);
    }

    private void addTempletonFields() {
        Group templetonGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.templetonSettings"), 110); //$NON-NLS-1$

        ScrolledComposite templetonComposite = new ScrolledComposite(templetonGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        templetonComposite.setExpandHorizontal(true);
        templetonComposite.setExpandVertical(true);
        templetonComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite templetonGroupComposite = Form.startNewGridLayout(templetonComposite, 4);
        GridLayout disGroupCompLayout = (GridLayout) templetonGroupComposite.getLayout();
        disGroupCompLayout.marginHeight = 0;
        disGroupCompLayout.marginTop = 0;
        disGroupCompLayout.marginBottom = 0;
        disGroupCompLayout.marginLeft = 0;
        disGroupCompLayout.marginRight = 0;
        disGroupCompLayout.marginWidth = 0;
        templetonComposite.setContent(templetonGroupComposite);

        hostText = new LabelledText(templetonGroupComposite, Messages.getString("HCatalogForm.text.host"), 1); //$NON-NLS-1$
        portText = new LabelledText(templetonGroupComposite, Messages.getString("HCatalogForm.text.port"), 1); //$NON-NLS-1$
        userNameText = new LabelledText(templetonGroupComposite, Messages.getString("HCatalogForm.text.userName"), 1); //$NON-NLS-1$
        kerberosBtn = new Button(templetonGroupComposite, SWT.CHECK);
        kerberosBtn.setText(Messages.getString("HCatalogForm.button.kerberos")); //$NON-NLS-1$
        kerberosBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));

        principalPartComposite = new Composite(templetonGroupComposite, SWT.NULL);
        principalPartComposite.setLayout(new GridLayout(4, false));
        GridData ppGridData = new GridData(GridData.FILL_HORIZONTAL);
        ppGridData.horizontalSpan = 4;
        principalPartComposite.setLayoutData(ppGridData);
        principalPartComposite.setVisible(false);

        krbPrincipalText = new LabelledText(principalPartComposite, Messages.getString("HCatalogForm.text.krbPrincipal"), 1); //$NON-NLS-1$
        krbRealmText = new LabelledText(principalPartComposite, Messages.getString("HCatalogForm.text.krbRealm"), 1); //$NON-NLS-1$
        nnPrincipalText = new LabelledText(principalPartComposite, Messages.getString("HCatalogForm.text.nnPrincipal"), 1); //$NON-NLS-1$
    }

    private void addDatabaseFields() {
        Group databaseGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.databaseSettings"), 80); //$NON-NLS-1$

        ScrolledComposite databaseComposite = new ScrolledComposite(databaseGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        databaseComposite.setExpandHorizontal(true);
        databaseComposite.setExpandVertical(true);
        databaseComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite databaseGroupComposite = Form.startNewGridLayout(databaseComposite, 4);
        GridLayout disGroupCompLayout = (GridLayout) databaseGroupComposite.getLayout();
        disGroupCompLayout.marginHeight = 0;
        disGroupCompLayout.marginTop = 0;
        disGroupCompLayout.marginBottom = 0;
        disGroupCompLayout.marginLeft = 0;
        disGroupCompLayout.marginRight = 0;
        disGroupCompLayout.marginWidth = 0;
        databaseComposite.setContent(databaseGroupComposite);

        databaseText = new LabelledText(databaseGroupComposite, Messages.getString("HCatalogForm.text.database"), 1); //$NON-NLS-1$
        Label spaceLabel = new Label(databaseGroupComposite, SWT.NONE);
        spaceLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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
        checkConnectionBtn = new UtilsButton(checkButtonComposite,
                Messages.getString("HCatalogForm.button.check"), WIDTH_BUTTON_PIXEL, HEIGHT_BUTTON_PIXEL); //$NON-NLS-1$
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
                String originalVersionName = getConnection().getHcatVersion();
                EHadoopVersion4Drivers originalVersion4Drivers = EHadoopVersion4Drivers.indexOfByVersion(originalVersionName);
                if (newVersion4Drivers != null && newVersion4Drivers != originalVersion4Drivers) {
                    getConnection().setHcatVersion(newVersion4Drivers.getVersionValue());
                    checkFieldsValue();
                }
            }
        });

        hostText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setHostName(hostText.getText());
                checkFieldsValue();
            }
        });

        portText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setPort(portText.getText());
                checkFieldsValue();
            }
        });

        userNameText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });

        databaseText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setDatabase(databaseText.getText());
                checkFieldsValue();
            }
        });

        kerberosBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setEnableKerberos(kerberosBtn.getSelection());
                updatePrincipalPart();
                checkFieldsValue();
            }
        });

        krbPrincipalText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setKrbPrincipal(krbPrincipalText.getText());
                checkFieldsValue();
            }
        });

        krbRealmText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setKrbRealm(krbRealmText.getText());
                checkFieldsValue();
            }
        });

        nnPrincipalText.addModifyListener(new ModifyListener() {

            public void modifyText(final ModifyEvent e) {
                getConnection().setNnPrincipal(nnPrincipalText.getText());
                checkFieldsValue();
            }
        });

    }

    private void updatePrincipalPart() {
        GridData data = (GridData) principalPartComposite.getLayoutData();
        if (kerberosBtn.getSelection()) {
            principalPartComposite.setVisible(true);
            data.exclude = false;
        } else {
            principalPartComposite.setVisible(false);
            data.exclude = true;
        }
        principalPartComposite.getParent().layout();
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

    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

        if (distributionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.distribution")); //$NON-NLS-1$
            return false;
        }
        if (versionCombo.getSelectionIndex() == -1) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.version")); //$NON-NLS-1$
            return false;
        }

        if (!validText(hostText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.host")); //$NON-NLS-1$
            return false;
        }

        if (!validText(portText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.port")); //$NON-NLS-1$
            return false;
        }

        if (!validText(userNameText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.userName")); //$NON-NLS-1$
            return false;
        }

        if (kerberosBtn.getSelection()) {
            if (!validText(krbPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.krbPrincipal")); //$NON-NLS-1$
                return false;
            }
            if (!validText(krbRealmText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.krbRealm")); //$NON-NLS-1$
                return false;
            }
            if (!validText(nnPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.nnPrincipal")); //$NON-NLS-1$
                return false;
            }

        }

        if (!validText(databaseText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HCatalogForm.check.database")); //$NON-NLS-1$
            return false;
        }

        checkConnectionBtn.setEnabled(true);

        if (!hcatalogSettingIsValide) {
            updateStatus(IStatus.INFO, Messages.getString("HCatalogForm.check.checkConnection")); //$NON-NLS-1$
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
        updatePrincipalPart();
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
