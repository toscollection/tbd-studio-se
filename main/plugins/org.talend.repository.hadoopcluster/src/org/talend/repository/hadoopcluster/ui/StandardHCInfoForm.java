// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
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
import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.IStatus;
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
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.runtime.image.EImage;
import org.talend.commons.ui.runtime.image.ImageProvider;
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
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.designer.hdfsbrowse.hadoop.service.EHadoopServiceType;
import org.talend.designer.hdfsbrowse.hadoop.service.HadoopServiceProperties;
import org.talend.designer.hdfsbrowse.hadoop.service.check.CheckHadoopServicesDialog;
import org.talend.designer.hdfsbrowse.manager.HadoopParameterValidator;
import org.talend.metadata.managment.ui.dialog.HadoopPropertiesDialog;
import org.talend.metadata.managment.ui.utils.ConnectionContextHelper;
import org.talend.metadata.managment.ui.utils.ExtendedNodeConnectionContextUtils.EHadoopParamName;
import org.talend.repository.hadoopcluster.conf.HadoopConfsUtils;
import org.talend.repository.hadoopcluster.i18n.Messages;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.hadoopcluster.ui.common.IHadoopClusterInfoForm;
import org.talend.repository.hadoopcluster.util.HCVersionUtil;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

/**
 * 
 * created by ycbai on 2014年9月16日 Detailled comment
 *
 */
public class StandardHCInfoForm extends AbstractHadoopForm<HadoopClusterConnection> implements IHadoopClusterInfoForm {

    private Composite parentForm;

    private LabelledCombo authenticationCombo;

    private Button kerberosBtn;

    private Button keytabBtn;

    private LabelledText namenodeUriText;

    private LabelledText jobtrackerUriText;

    private LabelledText rmSchedulerText;

    private LabelledText jobHistoryText;

    private LabelledText stagingDirectoryText;

    private LabelledText namenodePrincipalText;

    private LabelledText jtOrRmPrincipalText;

    private LabelledText jobHistoryPrincipalText;

    private LabelledText keytabPrincipalText;

    private LabelledText userNameText;

    private LabelledText groupText;

    private Button useDNHostBtn;

    private Button hadoopConfsButton;

    private Button useCustomConfBtn;

    private LabelledFileField keytabText;

    private Group customGroup;

    private HadoopPropertiesDialog propertiesDialog;

    private UtilsButton checkServicesBtn;

    private final boolean creation;

    protected EHadoopDistributions hadoopDistribution;

    protected EHadoopVersion4Drivers hadoopVersison;

    private boolean needInitializeContext = false;

    public StandardHCInfoForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            EHadoopDistributions hadoopDistribution, EHadoopVersion4Drivers hadoopVersison) {
        super(parent, SWT.NONE, existingNames);
        this.parentForm = parent;
        this.connectionItem = connectionItem;
        this.creation = creation;
        this.hadoopDistribution = hadoopDistribution;
        this.hadoopVersison = hadoopVersison;
        setConnectionItem(connectionItem);
        setupForm(true);
        init();
        setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout layout = (GridLayout) getLayout();
        layout.marginWidth = 0;
        layout.marginHeight = 0;
        setLayout(layout);
    }

    @Override
    public void initialize() {
        // initialize for context mode
        if (needInitializeContext) {
            init();
        }
    }

    @Override
    public void init() {
        EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(getConnection().getAuthMode(), false);
        if (authMode != null) {
            authenticationCombo.setText(authMode.getDisplayName());
        } else {
            authenticationCombo.select(0);
        }
        HadoopClusterConnection connection = getConnection();
        namenodeUriText.setText(connection.getNameNodeURI());
        jobtrackerUriText.setText(connection.getJobTrackerURI());
        rmSchedulerText.setText(StringUtils.trimToEmpty(connection.getRmScheduler()));
        jobHistoryText.setText(StringUtils.trimToEmpty(connection.getJobHistory()));
        stagingDirectoryText.setText(StringUtils.trimToEmpty(connection.getStagingDirectory()));
        useDNHostBtn.setSelection(connection.isUseDNHost());
        useCustomConfBtn.setSelection(connection.isUseCustomConfs());
        kerberosBtn.setSelection(connection.isEnableKerberos());
        namenodePrincipalText.setText(connection.getPrincipal());
        jtOrRmPrincipalText.setText(connection.getJtOrRmPrincipal());
        jobHistoryPrincipalText.setText(connection.getJobHistoryPrincipal());
        keytabBtn.setSelection(connection.isUseKeytab());
        keytabPrincipalText.setText(connection.getKeytabPrincipal());
        keytabText.setText(connection.getKeytab());
        userNameText.setText(connection.getUserName());
        groupText.setText(connection.getGroup());

        if (!isContextMode()) {
            fillDefaults();
        }
        needInitializeContext = true;
        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        namenodeUriText.setReadOnly(readOnly);
        jobtrackerUriText.setReadOnly(readOnly);
        rmSchedulerText.setReadOnly(readOnly);
        jobHistoryText.setReadOnly(readOnly);
        stagingDirectoryText.setReadOnly(readOnly);
        useDNHostBtn.setEnabled(!readOnly);
        useCustomConfBtn.setEnabled(!readOnly);
        hadoopConfsButton.setEnabled(!readOnly && useCustomConfBtn.getSelection());
        kerberosBtn.setEnabled(!readOnly);
        namenodePrincipalText.setReadOnly(readOnly);
        jtOrRmPrincipalText.setReadOnly(readOnly);
        jobHistoryPrincipalText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
        groupText.setReadOnly(readOnly);
    }

    @Override
    protected void updateEditableStatus(boolean isEditable) {
        authenticationCombo.setEnabled(isEditable);
        namenodeUriText.setEditable(isEditable);
        jobtrackerUriText.setEditable(isEditable);
        rmSchedulerText.setEditable(isEditable);
        jobHistoryText.setEditable(isEditable);
        stagingDirectoryText.setEditable(isEditable);
        useDNHostBtn.setEnabled(isEditable);
        kerberosBtn.setEnabled(isEditable && (isCurrentHadoopVersionSupportSecurity() || isCustomUnsupportHasSecurity()));
        boolean isKerberosEditable = kerberosBtn.isEnabled() && kerberosBtn.getSelection();
        namenodePrincipalText.setEditable(isKerberosEditable);
        jtOrRmPrincipalText.setEditable(isKerberosEditable);
        jobHistoryPrincipalText.setEditable(isKerberosEditable);
        userNameText.setEditable(isEditable && !kerberosBtn.getSelection());
        groupText.setEditable(isEditable && (isCurrentHadoopVersionSupportGroup() || isCustomUnsupportHasGroup()));
        keytabBtn.setEnabled(isEditable && kerberosBtn.getSelection());
        boolean isKeyTabEditable = keytabBtn.isEnabled() && keytabBtn.getSelection();
        keytabText.setEditable(isKeyTabEditable);
        keytabPrincipalText.setEditable(isKeyTabEditable);
    }

    @Override
    protected void addFields() {
        addCustomFields();
        addConnectionFields();
        addAuthenticationFields();
        addHadoopPropertiesFields();
        addHadoopConfsFields();
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
        rmSchedulerText = new LabelledText(uriPartComposite, Messages.getString("HadoopClusterForm.text.rmScheduler"), 1); //$NON-NLS-1$
        jobHistoryText = new LabelledText(uriPartComposite, Messages.getString("HadoopClusterForm.text.jobHistory"), 1); //$NON-NLS-1$
        stagingDirectoryText = new LabelledText(uriPartComposite,
                Messages.getString("HadoopClusterForm.text.stagingDirectory"), 1); //$NON-NLS-1$
        useDNHostBtn = new Button(uriPartComposite, SWT.CHECK);
        useDNHostBtn.setText(Messages.getString("HadoopClusterForm.button.useDNHost")); //$NON-NLS-1$
        useDNHostBtn.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 2, 1));
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
        jobHistoryPrincipalText = new LabelledText(authCommonComposite,
                Messages.getString("HadoopClusterForm.text.jobHistoryPrincipal"), 1); //$NON-NLS-1$

        // placeHolder is only used to make userNameText and groupText to new line
        Composite placeHolder = new Composite(authCommonComposite, SWT.NULL);
        GridData placeHolderLayoutData = new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1);
        placeHolderLayoutData.heightHint = 1;
        placeHolder.setLayoutData(placeHolderLayoutData);

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

    private void addHadoopPropertiesFields() {
        Composite hadoopPropertiesComposite = new Composite(this, SWT.NONE);
        GridLayout hadoopPropertiesLayout = new GridLayout(1, false);
        hadoopPropertiesLayout.marginWidth = 0;
        hadoopPropertiesLayout.marginHeight = 0;
        hadoopPropertiesComposite.setLayout(hadoopPropertiesLayout);
        hadoopPropertiesComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        String hadoopProperties = getConnection().getHadoopProperties();
        List<Map<String, Object>> hadoopPropertiesList = HadoopRepositoryUtil.getHadoopPropertiesList(hadoopProperties);
        propertiesDialog = new HadoopPropertiesDialog(getShell(), hadoopPropertiesList) {

            @Override
            public void applyProperties(List<Map<String, Object>> properties) {
                getConnection().setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesJsonStr(properties));
            }

        };
        propertiesDialog.createPropertiesFields(hadoopPropertiesComposite);
    }

    private void addHadoopConfsFields() {
        Composite hadoopConfsComposite = new Composite(this, SWT.NONE);
        GridLayout hadoopConfsCompLayout = new GridLayout(3, false);
        hadoopConfsCompLayout.marginWidth = 5;
        hadoopConfsCompLayout.marginHeight = 5;
        hadoopConfsComposite.setLayout(hadoopConfsCompLayout);
        hadoopConfsComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        useCustomConfBtn = new Button(hadoopConfsComposite, SWT.CHECK);
        useCustomConfBtn.setText(Messages.getString("HadoopClusterForm.button.useCustomConf")); //$NON-NLS-1$
        useCustomConfBtn.setLayoutData(new GridData());

        hadoopConfsButton = new Button(hadoopConfsComposite, SWT.NONE);
        hadoopConfsButton.setImage(ImageProvider.getImage(EImage.THREE_DOTS_ICON));
        hadoopConfsButton.setLayoutData(new GridData(30, 25));
        hadoopConfsButton.setEnabled(false);

        Label displayLabel = new Label(hadoopConfsComposite, SWT.NONE);
        displayLabel.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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

        rmSchedulerText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setRmScheduler(rmSchedulerText.getText());
                checkFieldsValue();
            }
        });

        jobHistoryText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setJobHistory(jobHistoryText.getText());
                checkFieldsValue();
            }
        });

        stagingDirectoryText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setStagingDirectory(stagingDirectoryText.getText());
                checkFieldsValue();
            }
        });

        useDNHostBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setUseDNHost(useDNHostBtn.getSelection());
                checkFieldsValue();
            }
        });

        useCustomConfBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                hadoopConfsButton.setEnabled(useCustomConfBtn.getSelection());
                getConnection().setUseCustomConfs(useCustomConfBtn.getSelection());
                checkFieldsValue();
            }
        });

        hadoopConfsButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                AbstractHadoopForm form = null;
                if (parentForm instanceof AbstractHadoopForm) {
                    form = (AbstractHadoopForm) parentForm;
                }
                HadoopConfsUtils.openHadoopConfsWizard(form, (HadoopClusterConnectionItem) connectionItem, creation);
            }
        });

        rmSchedulerText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setRmScheduler(rmSchedulerText.getText());
                checkFieldsValue();
            }
        });

        jobHistoryText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setJobHistory(jobHistoryText.getText());
                checkFieldsValue();
            }
        });

        stagingDirectoryText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setStagingDirectory(stagingDirectoryText.getText());
                checkFieldsValue();
            }
        });

        useDNHostBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                getConnection().setUseDNHost(useDNHostBtn.getSelection());
                checkFieldsValue();
            }
        });

        useCustomConfBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                hadoopConfsButton.setEnabled(useCustomConfBtn.getSelection());
                getConnection().setUseCustomConfs(useCustomConfBtn.getSelection());
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

        jobHistoryPrincipalText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                getConnection().setJobHistoryPrincipal(jobHistoryPrincipalText.getText());
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
        HadoopClusterConnection connection = getConnection();
        ContextType contextType = null;
        if (getConnection().isContextMode()) {
            contextType = ConnectionContextHelper.getContextTypeForContextMode(connection);
        }
        properties.setContextType(contextType);
        properties.setDistribution(connection.getDistribution());
        properties.setVersion(connection.getDfVersion());
        properties.setGroup(connection.getGroup());
        properties.setUseKrb(connection.isEnableKerberos());
        properties.setCustom(connection.isUseCustomVersion());
        properties.setPrincipal(connection.getPrincipal());
        properties.setJtOrRmPrincipal(connection.getJtOrRmPrincipal());
        properties.setJobHistoryPrincipal(connection.getJobHistoryPrincipal());
        properties.setUseKeytab(connection.isUseKeytab());
        properties.setKeytabPrincipal(connection.getKeytabPrincipal());
        properties.setKeytab(connection.getKeytab());
        properties.setHadoopProperties(HadoopRepositoryUtil.getHadoopPropertiesList(connection.getHadoopProperties()));
        properties.setRelativeHadoopClusterId(connectionItem.getProperty().getId());
    }

    @Override
    public void updateForm() {
        HadoopClusterConnection connection = getConnection();
        String distribution = connection.getDistribution();
        boolean isCustomDistribution = EHadoopDistributions.CUSTOM.getName().equals(distribution);
        if (isCustomDistribution) {
            hideControl(customGroup, false);
            String authModeName = connection.getAuthMode();
            if (authModeName != null) {
                EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(authModeName, false);
                switch (authMode) {
                case KRB:
                    kerberosBtn.setEnabled(true);
                    namenodePrincipalText.setEditable(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
                    jtOrRmPrincipalText.setEditable(namenodePrincipalText.getEditable());
                    jobHistoryPrincipalText.setEditable(namenodePrincipalText.getEditable());
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
                    jobHistoryPrincipalText.setEditable(false);
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
                    jobHistoryPrincipalText.setEditable(false);
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
            jobHistoryPrincipalText.setEditable(namenodePrincipalText.getEditable()
                    && isCurrentHadoopVersionSupportJobHistoryPrincipal());
            keytabBtn.setEnabled(kerberosBtn.isEnabled() && kerberosBtn.getSelection());
            keytabPrincipalText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
            keytabText.setEditable(keytabBtn.isEnabled() && keytabBtn.getSelection());
            groupText.setEditable(version4Drivers.isSupportGroup());
            userNameText.setEditable(!kerberosBtn.getSelection());

        }
        updateMRRelatedContent();
        updateConnectionContent();
        if (isContextMode()) {
            adaptFormToEditable();
        }
    }

    /**
     * is current hadoop version support JobHistoryPrincipal
     * 
     * @return
     */
    protected boolean isCurrentHadoopVersionSupportJobHistoryPrincipal() {
        if (hadoopDistribution == null || hadoopVersison == null) {
            return false;
        }
        boolean isSupport = false;
        // this strategy is based on tMRConfiguration_java.xml, Parameter: JOBHISTORY_PRINCIPAL
        if (hadoopVersison == EHadoopVersion4Drivers.MICROSOFT_HD_INSIGHT_3_1) {
            return false;
        } else {
            if (hadoopDistribution == EHadoopDistributions.CUSTOM) {
                return true;
            } else {
                switch (hadoopVersison) {
                case PIVOTAL_HD_1_0_1:
                case PIVOTAL_HD_2_0:
                case HDP_2_0:
                case HDP_2_1:
                case HDP_2_2:
                case CLOUDERA_CDH4_YARN:
                case CLOUDERA_CDH5:
                case CLOUDERA_CDH5_1:
                case CLOUDERA_CDH5_4:
                case MAPR401:
                case APACHE_2_4_0_EMR:
                    isSupport = true;
                    break;
                default:
                    isSupport = false;
                }
            }
        }
        return isSupport;
    }

    private boolean isCurrentHadoopVersionSupportGroup() {
        boolean supportGroup = false;
        EHadoopVersion4Drivers driver = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getDfVersion());
        if (driver != null) {
            supportGroup = driver.isSupportGroup();
        }
        return supportGroup;
    }

    private boolean isCustomUnsupportHasGroup() {
        EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(getConnection().getAuthMode(), false);
        return authMode.equals(EAuthenticationMode.UGI);
    }

    private boolean isCustomUnsupportHasSecurity() {
        EAuthenticationMode authMode = EAuthenticationMode.getAuthenticationByName(getConnection().getAuthMode(), false);
        return authMode.equals(EAuthenticationMode.KRB);
    }

    private boolean isCurrentHadoopVersionSupportSecurity() {
        boolean supportSecurity = false;
        EHadoopVersion4Drivers driver = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getDfVersion());
        if (driver != null) {
            supportSecurity = driver.isSupportSecurity();
        }
        return supportSecurity;
    }

    private boolean isCurrentHadoopVersionSupportYarn() {
        boolean supportYarn = false;
        EHadoopVersion4Drivers driver = EHadoopVersion4Drivers.indexOfByVersion(getConnection().getDfVersion());
        if (driver != null) {
            supportYarn = driver.isSupportYARN();
        }
        return supportYarn;
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
            jobHistoryPrincipalText.setText(EMPTY_STRING);
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
            if (connection.isUseCustomConfs()) {
                return;
            }
            String distribution = connection.getDistribution();
            String version = connection.getDfVersion();
            if (distribution == null) {
                return;
            }
            String[] versionPrefix = new String[] { distribution };
            if (EHadoopDistributions.AMAZON_EMR.getName().equals(distribution)
                    && (EHadoopVersion4Drivers.APACHE_1_0_3_EMR.getVersionValue().equals(version) || EHadoopVersion4Drivers.APACHE_2_4_0_EMR
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
            String defaultJobHistoryPrincipal = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(distribution,
                    EHadoopProperties.JOBHISTORY_PRINCIPAL.getName());
            if (defaultJobHistoryPrincipal != null) {
                jobHistoryPrincipalText.setText(defaultJobHistoryPrincipal);
            }
            String defaultRMS = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(distribution,
                    EHadoopProperties.RESOURCEMANAGER_SCHEDULER_ADDRESS.getName());
            if (defaultRMS != null) {
                rmSchedulerText.setText(defaultRMS);
            }
            String defaultJH = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(distribution,
                    EHadoopProperties.JOBHISTORY_ADDRESS.getName());
            if (defaultJH != null) {
                jobHistoryText.setText(defaultJH);
            }
            String defaultSD = HadoopDefaultConfsManager.getInstance().getDefaultConfValue(distribution,
                    EHadoopProperties.STAGING_DIRECTORY.getName());
            if (defaultSD != null) {
                stagingDirectoryText.setText(defaultSD);
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

        if (!isContextMode() && !HadoopParameterValidator.isValidNamenodeURI(namenodeUriText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodeURI.invalid")); //$NON-NLS-1$
            return false;
        }

        if (!validText(jobtrackerUriText.getText())) {
            updateStatus(IStatus.ERROR,
                    Messages.getString("HadoopClusterForm.check.jobtrackerURI2", jobtrackerUriText.getLabelText())); //$NON-NLS-1$
            return false;
        }

        if (!isContextMode() && !HadoopParameterValidator.isValidJobtrackerURI(jobtrackerUriText.getText())) {
            updateStatus(IStatus.ERROR,
                    Messages.getString("HadoopClusterForm.check.jobtrackerURI.invalid2", jobtrackerUriText.getLabelText())); //$NON-NLS-1$
            return false;
        }

        if (namenodePrincipalText.getEditable()) {
            if (!validText(namenodePrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.namenodePrincipal")); //$NON-NLS-1$
                return false;
            }
            if (!isContextMode() && !HadoopParameterValidator.isValidPrincipal(namenodePrincipalText.getText())) {
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
            if (!isContextMode() && !HadoopParameterValidator.isValidPrincipal(jtOrRmPrincipalText.getText())) {
                updateStatus(IStatus.ERROR,
                        Messages.getString("HadoopClusterForm.check.jtOrRmPrincipal.invalid", jtOrRmPrincipalText.getLabelText())); //$NON-NLS-1$
                return false;
            }
        }

        if (jobHistoryPrincipalText.getEditable()) {
            if (!validText(jobHistoryPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.jobHistoryPrincipal")); //$NON-NLS-1$
                return false;
            }
            if (!isContextMode() && !HadoopParameterValidator.isValidPrincipal(jobHistoryPrincipalText.getText())) {
                updateStatus(IStatus.ERROR, Messages.getString("HadoopClusterForm.check.jobHistoryPrincipal.invalid")); //$NON-NLS-1$
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
            if (!isContextMode() && !HadoopParameterValidator.isValidGroup(groupText.getText())) {
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
            if (!isContextMode() && !HadoopParameterValidator.isValidPrincipal(keytabPrincipalText.getText())) {
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
            adaptFormToEditable();
            updateStatus(getStatusLevel(), getStatus());
        }
    }

    @Override
    protected void collectConParameters() {
        collectConFieldContextParameters(isCurrentHadoopVersionSupportYarn());
        collectAuthFieldContextParameters(kerberosBtn.getSelection());
        collectKeyTabContextParameters(kerberosBtn.getSelection() && keytabBtn.getSelection());
    }

    private void collectConFieldContextParameters(boolean useYarn) {
        addContextParams(EHadoopParamName.NameNodeUri, true);
        addContextParams(EHadoopParamName.JobTrackerUri, !useYarn);
        addContextParams(EHadoopParamName.ResourceManager, useYarn);
        addContextParams(EHadoopParamName.ResourceManagerScheduler, true);
        addContextParams(EHadoopParamName.JobHistory, true);
        addContextParams(EHadoopParamName.StagingDirectory, true);
    }

    private void collectAuthFieldContextParameters(boolean useKerberos) {
        addContextParams(EHadoopParamName.NameNodePrin, useKerberos);
        addContextParams(EHadoopParamName.JTOrRMPrin, useKerberos);
        addContextParams(EHadoopParamName.JobHistroyPrin, useKerberos);
        addContextParams(EHadoopParamName.User, !useKerberos);
        addContextParams(EHadoopParamName.Group, !useKerberos
                && (isCurrentHadoopVersionSupportGroup() || isCustomUnsupportHasGroup()));
    }

    private void collectKeyTabContextParameters(boolean useKeyTab) {
        addContextParams(EHadoopParamName.Principal, useKeyTab);
        addContextParams(EHadoopParamName.KeyTab, useKeyTab);
    }
}
