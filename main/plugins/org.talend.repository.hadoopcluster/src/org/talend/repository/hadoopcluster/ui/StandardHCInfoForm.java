// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledFileField;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.hadoop.conf.EHadoopProperties;
import org.talend.core.hadoop.conf.HadoopDefaultConfsManager;
import org.talend.core.hadoop.repository.HadoopRepositoryUtil;
import org.talend.core.hadoop.version.EAuthenticationMode;
import org.talend.core.hadoop.version.EHadoopDistributions;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.hadoop.version.custom.ECustomVersionGroup;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.hadoop.service.EHadoopServiceType;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.CheckHadoopServicesDialog;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterValidator;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.ui.common.IHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.ui.dialog.HadoopPropertiesDialog;

/**
 * 
 * created by ycbai on 2014年9月16日 Detailled comment
 *
 */
public class StandardHCInfoForm extends AbstractHadoopForm<HadoopClusterConnection> implements IHadoopClusterInfoForm {

    private LabelledCombo authenticationCombo;

    private Button kerberosBtn;

    private Button keytabBtn;

    private LabelledText namenodeUriText;

    private LabelledText jobtrackerUriText;

    private LabelledText namenodePrincipalText;

    private LabelledText jtOrRmPrincipalText;

    private LabelledText keytabPrincipalText;

    private LabelledText userNameText;

    private LabelledText groupText;

    private LabelledFileField keytabText;

    private Group customGroup;

    private UtilsButton checkServicesBtn;

    private final boolean creation;

    public StandardHCInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation) {
        super(parent, SWT.NONE, existingNames);
        this.connectionItem = connectionItem;
        this.creation = creation;
        setConnectionItem(connectionItem);
        setupForm();
        init();
        setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = (GridLayout) getLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        setLayout(layout);
    }

    public void init() {
        EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(getConnection().getAuthMode(), false);
        if (authMode != null) {
            authenticationCombo.setText(authMode.getDisplayName());
        } else {
            authenticationCombo.select(0);
        }
        namenodeUriText.setText(getConnection().getNameNodeURI());
        jobtrackerUriText.setText(getConnection().getJobTrackerURI());
        kerberosBtn.setSelection(getConnection().isEnableKerberos());
        namenodePrincipalText.setText(getConnection().getPrincipal());
        jtOrRmPrincipalText.setText(getConnection().getJtOrRmPrincipal());
        keytabBtn.setSelection(getConnection().isUseKeytab());
        keytabPrincipalText.setText(getConnection().getKeytabPrincipal());
        keytabText.setText(getConnection().getKeytab());
        userNameText.setText(getConnection().getUserName());
        groupText.setText(getConnection().getGroup());

        fillDefaults();
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        namenodeUriText.setReadOnly(readOnly);
        jobtrackerUriText.setReadOnly(readOnly);
        kerberosBtn.setEnabled(!readOnly);
        namenodePrincipalText.setReadOnly(readOnly);
        jtOrRmPrincipalText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
        groupText.setReadOnly(readOnly);
    }

    @Override
    protected void addFields() {
        addCustomFields();
        addConnectionFields();
        addAuthenticationFields();
        addHadoopPropertiesFields();
        addCheckFields();
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
        jtOrRmPrincipalText = new LabelledText(authCommonComposite, Messages.getString("HadoopClusterForm.text.rmPrincipal"), 1); //$NON-NLS-1$
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

    protected void addHadoopPropertiesFields() {
        String hadoopProperties = getConnection().getHadoopProperties();
        List<Map<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties);
        new HadoopPropertiesDialog(getShell(), hadoopPropertiesList) {

            @Override
            public void updateHadoopProperties(List<Map<String, Object>> properties) {
                getConnection().setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(properties));
            }

        }.create(this);
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
        checkButtonLayout.marginWidth = 0;
        checkServicesBtn = new UtilsButton(checkButtonComposite, Messages.getString("HadoopClusterForm.button.check"), true); //$NON-NLS-1$
        checkServicesBtn.setEnabled(false);
        addCheckServicesWarning(checkGroup);
    }

    private void addCheckServicesWarning(Composite parent) {
        Display display = getDisplay();
        FontData fontData = display.getSystemFont().getFontData()[0];
        Font font = new Font(display, new FontData(fontData.getName(), fontData.getHeight(), SWT.ITALIC));
        Label warningLabel1 = new Label(parent, SWT.NONE);
        warningLabel1.setText(Messages.getString("HadoopClusterForm.checkServices.warning1")); //$NON-NLS-1$
        warningLabel1.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        warningLabel1.setFont(font);
        Image labelDecorationWarningImage = FieldDecorationRegistry.getDefault()
                .getFieldDecoration(FieldDecorationRegistry.DEC_WARNING).getImage();
        ControlDecoration warningDecorator = new ControlDecoration(warningLabel1, SWT.LEFT | SWT.CENTER);
        warningDecorator.setMarginWidth(4);
        warningDecorator.setImage(labelDecorationWarningImage);
        Label warningLabel2 = new Label(parent, SWT.NONE);
        warningLabel2.setText(Messages.getString("HadoopClusterForm.checkServices.warning2")); //$NON-NLS-1$
        warningLabel2.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        warningLabel2.setFont(font);
    }

    @Override
    protected void addFieldsListeners() {
        authenticationCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String newAuthDisplayName = authenticationCombo.getText();
                EAuthenticationMode newAuthMode = EAuthenticationMode.getAuthenticationByDisplayName(newAuthDisplayName);
                String originalAuthName = getConnection().getAuthMode();
                EAuthenticationMode originalAuthMode = EAuthenticationMode.getAuthenticationByName(originalAuthName, false);
                if (newAuthMode != null && newAuthMode != originalAuthMode) {
                    getConnection().setAuthMode(newAuthMode.getName());
                    updateForm();
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

        namenodePrincipalText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setPrincipal(namenodePrincipalText.getText());
                checkFieldsValue();
            }
        });

        jtOrRmPrincipalText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setJtOrRmPrincipal(jtOrRmPrincipalText.getText());
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
                updateForm();
                checkFieldsValue();
            }
        });

        keytabBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setUseKeytab(keytabBtn.getSelection());
                updateForm();
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

    @Override
    protected void addUtilsButtonListeners() {
        checkServicesBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkServices();
            }
        });
    }

    private void checkServices() {
        Map<EHadoopServiceType, HadoopServiceProperties> serviceTypeToProperties = new HashMap<EHadoopServiceType, HadoopServiceProperties>();
        HadoopServiceProperties nnProperties = new HadoopServiceProperties();
        initCommonProperties(nnProperties);
        nnProperties.setNameNode(getConnection().getNameNodeURI());
        serviceTypeToProperties.put(EHadoopServiceType.NAMENODE, nnProperties);
        HadoopServiceProperties rmORjtProperties = new HadoopServiceProperties();
        initCommonProperties(rmORjtProperties);
        if (getConnection().isUseYarn()) {
            rmORjtProperties.setResourceManager(getConnection().getJobTrackerURI());
            serviceTypeToProperties.put(EHadoopServiceType.RESOURCE_MANAGER, rmORjtProperties);
        } else {
            rmORjtProperties.setJobTracker(getConnection().getJobTrackerURI());
            serviceTypeToProperties.put(EHadoopServiceType.JOBTRACKER, rmORjtProperties);
        }
        if (getConnection().isUseCustomVersion()) {
            nnProperties.setUid(connectionItem.getProperty().getId());
            nnProperties.setCustomJars(HCVersionUtil.getCustomVersionMap(getConnection()).get(
                    ECustomVersionGroup.COMMON.getName()));
            rmORjtProperties.setUid(connectionItem.getProperty().getId());
            rmORjtProperties.setCustomJars(HCVersionUtil.getCustomVersionMap(getConnection()).get(
                    ECustomVersionGroup.MAP_REDUCE.getName()));
        }
        new CheckHadoopServicesDialog(getShell(), serviceTypeToProperties).open();
    }

    private void initCommonProperties(HadoopServiceProperties properties) {
        properties.setDistribution(getConnection().getDistribution());
        properties.setVersion(getConnection().getDfVersion());
        properties.setGroup(getConnection().getGroup());
        properties.setUseKrb(getConnection().isEnableKerberos());
        properties.setCustom(getConnection().isUseCustomVersion());
        properties.setPrincipal(getConnection().getPrincipal());
        properties.setJtOrRmPrincipal(getConnection().getJtOrRmPrincipal());
        properties.setUseKeytab(getConnection().isUseKeytab());
        properties.setKeytabPrincipal(getConnection().getKeytabPrincipal());
        properties.setKeytab(getConnection().getKeytab());
        properties.setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesList(getConnection().getHadoopProperties()));
    }

    @Override
    public void updateForm() {
        HadoopClusterConnection connection = getConnection();
        String distribution = connection.getDistribution();
        if (EHadoopDistributions.CUSTOM.getName().equals(distribution)) {
            hideControl(customGroup, false);
            String authModeName = connection.getAuthMode();
            if (authModeName != null) {
                EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(authModeName, false);
                switch (authMode) {
                case KRB:
                    kerberosBtn.setEnabled(true);
                    namenodePrincipalText.setEditable(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
                    jtOrRmPrincipalText.setEditable(namenodePrincipalText.getEditable());
                    keytabBtn.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
                    keytabPrincipalText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
                    keytabText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
                    userNameText.setEditable(false);
                    groupText.setEditable(false);
                    break;
                case UGI:
                    kerberosBtn.setEnabled(false);
                    namenodePrincipalText.setEditable(false);
                    jtOrRmPrincipalText.setEditable(false);
                    keytabBtn.setEnabled(false);
                    keytabPrincipalText.setEditable(false);
                    keytabText.setEditable(false);
                    userNameText.setEditable(true);
                    groupText.setEditable(true);
                    break;
                default:
                    kerberosBtn.setEnabled(false);
                    namenodePrincipalText.setEditable(false);
                    jtOrRmPrincipalText.setEditable(false);
                    keytabBtn.setEnabled(false);
                    keytabPrincipalText.setEditable(false);
                    keytabText.setEditable(false);
                    userNameText.setEditable(true);
                    groupText.setEditable(false);
                    break;
                }
            }
        } else {
            hideControl(customGroup, true);
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(connection.getDfVersion());
            kerberosBtn.setEnabled(version4Drivers.isSupportSecurity());
            namenodePrincipalText.setEditable(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
            jtOrRmPrincipalText.setEditable(namenodePrincipalText.getEditable());
            keytabBtn.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
            keytabPrincipalText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
            keytabText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
            groupText.setEditable(version4Drivers.isSupportGroup());
            userNameText.setEditable(!kerberosBtn.getSelection());
        }
        updateMRRelatedContent();
        updateConnectionContent();
    }

    private void updateMRRelatedContent() {
        boolean useYarn = getConnection().isUseYarn();
        jobtrackerUriText
                .setLabelText(useYarn ? Messages.getString("HadoopClusterForm.text.resourceManager") : Messages.getString("HadoopClusterForm.text.jobtrackerURI")); //$NON-NLS-1$ //$NON-NLS-2$
        jobtrackerUriText.getTextControl().getParent().layout();
        jtOrRmPrincipalText
                .setLabelText(useYarn ? Messages.getString("HadoopClusterForm.text.rmPrincipal") : Messages.getString("HadoopClusterForm.text.jtPrincipal")); //$NON-NLS-1$ //$NON-NLS-2$
        jtOrRmPrincipalText.getTextControl().getParent().layout();
    }

    private void updateConnectionContent() {
        if (!kerberosBtn.isEnabled()) {
            kerberosBtn.setSelection(false);
            namenodePrincipalText.setText(EMPTY_STRING);
            jtOrRmPrincipalText.setText(EMPTY_STRING);
            getConnection().setEnableKerberos(false);
        }
        if (!groupText.getEditable()) {
            groupText.setText(EMPTY_STRING);
        }
        if (!userNameText.getEditable()) {
            userNameText.setText(EMPTY_STRING);
        }
    }

    private void fillDefaults() {
        if (creation) {
            HadoopClusterConnection connection = getConnection();
            String distribution = connection.getDistribution();
            String version = connection.getDfVersion();
            if (distribution == null) {
                return;
            }
            String[] versionPrefix = new String[] { distribution };
            if (EHadoopDistributions.AMAZON_EMR.getName().equals(distribution)
                    && (EHadoopVersion4Drivers.APACHE_1_0_3_EMR.getVersionValue().equals(version)
                            || EHadoopVersion4Drivers.MAPR_EMR.getVersionValue().equals(version) || EHadoopVersion4Drivers.APACHE_2_4_0_EMR
                            .getVersionValue().equals(version))) {
                versionPrefix = (String[]) ArrayUtils.add(versionPrefix, version);
            }
            boolean isYarn = connection.isUseYarn();
            String defaultNN = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(
                    (String[]) ArrayUtils.add(versionPrefix, EHadoopProperties.NAMENODE_URI.getName()));
            if (defaultNN != null) {
                namenodeUriText.setText(defaultNN);
            }
            String defaultJTORRM = null;
            String defaultJTORRMPrincipal = null;
            if (isYarn) {
                defaultJTORRM = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(
                        (String[]) ArrayUtils.add(versionPrefix, EHadoopProperties.RESOURCE_MANAGER.getName()));
                defaultJTORRMPrincipal = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(
                        (String[]) ArrayUtils.add(versionPrefix, EHadoopProperties.RESOURCE_MANAGER_PRINCIPAL.getName()));
            } else {
                defaultJTORRM = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(
                        (String[]) ArrayUtils.add(versionPrefix, EHadoopProperties.JOBTRACKER.getName()));
                defaultJTORRMPrincipal = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(
                        (String[]) ArrayUtils.add(versionPrefix, EHadoopProperties.JOBTRACKER_PRINCIPAL.getName()));
            }
            if (defaultJTORRM != null) {
                jobtrackerUriText.setText(defaultJTORRM);
            }
            if (defaultJTORRMPrincipal != null) {
                jtOrRmPrincipalText.setText(defaultJTORRMPrincipal);
            }
            String defaultNNP = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(distribution,
                    EHadoopProperties.NAMENODE_PRINCIPAL.getName());
            if (defaultNNP != null) {
                namenodePrincipalText.setText(defaultNNP);
            }
        }
    }

    @Override
    public boolean checkFieldsValue() {
        checkServicesBtn.setEnabled(false);

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
                    Messages.getString("HadoopClusterForm.check.jobtrackerURI2", jobtrackerUriText.getLabelText())); //$NON-NLS-1$
            return false;
        }

        if (!HadoopParameterValidator.isValidJobtrackerURI(jobtrackerUriText.getText())) {
            updateStatus(IStatus.ERROR,
                    Messages.getString("HadoopClusterForm.check.jobtrackerURI.invalid2", jobtrackerUriText.getLabelText())); //$NON-NLS-1$
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

        if (jtOrRmPrincipalText.getEditable()) {
            if (!validText(jtOrRmPrincipalText.getText())) {
                updateStatus(IStatus.ERROR,
                        Messages.getString("HadoopClusterForm.check.jtOrRmPrincipal", jtOrRmPrincipalText.getLabelText())); //$NON-NLS-1$
                return false;
            }
            if (!HadoopParameterValidator.isValidPrincipal(jtOrRmPrincipalText.getText())) {
                updateStatus(IStatus.ERROR,
                        Messages.getString("HadoopClusterForm.check.jtOrRmPrincipal.invalid", jtOrRmPrincipalText.getLabelText())); //$NON-NLS-1$
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

        checkServicesBtn.setEnabled(true);

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
        updateForm();
        if (isReadOnly() != readOnly) {
            adaptFormToReadOnly();
        }
        if (visible) {
            updateStatus(getStatusLevel(), getStatus());
        }
    }

}
