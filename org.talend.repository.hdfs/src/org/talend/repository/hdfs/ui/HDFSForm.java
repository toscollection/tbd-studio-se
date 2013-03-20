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
package org.talend.repository.hdfs.ui;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledCombo;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.designer.hdfsbrowse.util.EHDFSFieldSeparator;
import org.talend.designer.hdfsbrowse.util.EHDFSRowSeparator;
import org.talend.repository.hadoopcluster.util.HCRepositoryUtil;
import org.talend.repository.hdfs.i18n.Messages;
import org.talend.repository.hdfs.ui.metadata.ExtractMetaDataFromHDFS;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSForm extends AbstractHDFSForm {

    private static final int VISIBLE_COMBO_ITEM_COUNT = 5;

    private UtilsButton checkConnectionBtn;

    private LabelledText userNameText;

    private LabelledCombo rowSeparatorCombo;

    private LabelledCombo fieldSeparatorCombo;

    private Text rowSeparatorText;

    private Text fieldSeparatorText;

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
        userNameText.setText(StringUtils.trimToEmpty(getConnection().getUserName()));
        String rowSeparatorVal = getConnection().getRowSeparator();
        if (!StringUtils.isEmpty(rowSeparatorVal)) {
            rowSeparatorText.setText(rowSeparatorVal);
        } else {
            rowSeparatorVal = ExtractMetaDataFromHDFS.DEFAULT_ROW_SEPARATOR;
            rowSeparatorText.setText(rowSeparatorVal);
            getConnection().setRowSeparator(rowSeparatorVal);
        }
        EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(rowSeparatorVal, false);
        if (rowSeparator != null) {
            rowSeparatorCombo.setText(rowSeparator.getDisplayName());
        }
        String fieldSeparatorVal = getConnection().getFieldSeparator();
        if (!StringUtils.isEmpty(fieldSeparatorVal)) {
            fieldSeparatorText.setText(fieldSeparatorVal);
        } else {
            fieldSeparatorVal = ExtractMetaDataFromHDFS.DEFAULT_FIELD_SEPARATOR;
            fieldSeparatorText.setText(fieldSeparatorVal);
            getConnection().setFieldSeparator(fieldSeparatorVal);
        }
        EHDFSFieldSeparator fieldSeparator = EHDFSFieldSeparator.indexOf(fieldSeparatorVal, false);
        if (fieldSeparator != null) {
            fieldSeparatorCombo.setText(fieldSeparator.getDisplayName());
        }

        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        userNameText.setReadOnly(readOnly);
        rowSeparatorText.setEditable(!readOnly);
        rowSeparatorCombo.setEnabled(!readOnly);
        fieldSeparatorText.setEditable(!readOnly);
        fieldSeparatorCombo.setEnabled(!readOnly);
    }

    @Override
    protected void addFields() {
        addConnectionFields();
        addSeparatorFields();
        addCheckFields();
    }

    private void addConnectionFields() {
        Group connectionGroup = Form.createGroup(this, 1, Messages.getString("HDFSForm.connectionSettings")); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite connectionPartComposite = new Composite(connectionGroup, SWT.NULL);
        connectionPartComposite.setLayout(new GridLayout(4, false));
        connectionPartComposite.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        userNameText = new LabelledText(connectionPartComposite, Messages.getString("HDFSForm.text.userName"), 1); //$NON-NLS-1$
    }

    private void addSeparatorFields() {
        Group separatorGroup = Form.createGroup(this, 1, Messages.getString("HDFSForm.separatorSettings")); //$NON-NLS-1$
        separatorGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        ScrolledComposite separatorComposite = new ScrolledComposite(separatorGroup, SWT.V_SCROLL | SWT.H_SCROLL);
        separatorComposite.setExpandHorizontal(true);
        separatorComposite.setExpandVertical(true);
        separatorComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Composite separatorGroupComposite = Form.startNewGridLayout(separatorComposite, 6);
        GridLayout separatorGroupCompLayout = (GridLayout) separatorGroupComposite.getLayout();
        separatorGroupCompLayout.marginHeight = 0;
        separatorGroupCompLayout.marginTop = 0;
        separatorGroupCompLayout.marginBottom = 0;
        separatorGroupCompLayout.marginLeft = 0;
        separatorGroupCompLayout.marginRight = 0;
        separatorGroupCompLayout.marginWidth = 0;
        separatorComposite.setContent(separatorGroupComposite);

        rowSeparatorCombo = new LabelledCombo(separatorGroupComposite, Messages.getString("HDFSForm.rowSeparator"), //$NON-NLS-1$
                Messages.getString("HDFSForm.rowSeparator.tooltip"), EHDFSRowSeparator.getAllRowSeparators(true) //$NON-NLS-1$
                        .toArray(new String[0]), 1, true);
        rowSeparatorCombo.setVisibleItemCount(VISIBLE_COMBO_ITEM_COUNT);
        rowSeparatorText = new Text(separatorGroupComposite, SWT.BORDER);
        rowSeparatorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        fieldSeparatorCombo = new LabelledCombo(
                separatorGroupComposite,
                Messages.getString("HDFSForm.fieldSeparator"), //$NON-NLS-1$
                Messages.getString("HDFSForm.fieldSeparator.tooltip"), EHDFSFieldSeparator.getAllFieldSeparators(true).toArray(new String[0]), 1, true); //$NON-NLS-1$
        fieldSeparatorCombo.setVisibleItemCount(VISIBLE_COMBO_ITEM_COUNT);
        fieldSeparatorText = new Text(separatorGroupComposite, SWT.BORDER);
        fieldSeparatorText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
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

    @Override
    protected void addFieldsListeners() {
        userNameText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setUserName(userNameText.getText());
                checkFieldsValue();
            }
        });

        rowSeparatorCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String separatorDisplay = rowSeparatorCombo.getText();
                EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(separatorDisplay, true);
                if (rowSeparator != null) {
                    rowSeparatorText.setText(rowSeparator.getValue());
                    rowSeparatorText.forceFocus();
                    rowSeparatorText.selectAll();
                }
            }
        });

        fieldSeparatorCombo.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                String separatorDisplay = fieldSeparatorCombo.getText();
                EHDFSFieldSeparator fieldSeparator = EHDFSFieldSeparator.indexOf(separatorDisplay, true);
                if (fieldSeparator != null) {
                    fieldSeparatorText.setText(fieldSeparator.getValue());
                    fieldSeparatorText.forceFocus();
                    fieldSeparatorText.selectAll();
                }
            }
        });

        rowSeparatorText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setRowSeparator(rowSeparatorText.getText());
                checkFieldsValue();
                EHDFSRowSeparator rowSeparator = EHDFSRowSeparator.indexOf(rowSeparatorText.getText(), false);
                if (rowSeparator == null) {
                    rowSeparatorCombo.deselectAll();
                } else {
                    rowSeparatorCombo.setText(rowSeparator.getDisplayName());
                }
            }
        });

        fieldSeparatorText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setFieldSeparator(fieldSeparatorText.getText());
                checkFieldsValue();
                EHDFSFieldSeparator fieldSeparator = EHDFSFieldSeparator.indexOf(fieldSeparatorText.getText(), false);
                if (fieldSeparator == null) {
                    fieldSeparatorCombo.deselectAll();
                } else {
                    fieldSeparatorCombo.setText(fieldSeparator.getDisplayName());
                }
            }
        });
    }

    @Override
    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

        HadoopClusterConnection clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
        if (clusterConnection != null) {
            String dfVersion = clusterConnection.getDfVersion();
            EHadoopVersion4Drivers version4Drivers = EHadoopVersion4Drivers.indexOfByVersion(dfVersion);
            boolean supportGroup = isSupportGroup(version4Drivers);
            if (supportGroup) {
                if (!validText(userNameText.getText())) {
                    updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.userName")); //$NON-NLS-1$
                    return false;
                }
            }
        }

        if (!validText(rowSeparatorText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.rowSeparator")); //$NON-NLS-1$
            return false;
        }

        if (!validText(fieldSeparatorText.getText())) {
            updateStatus(IStatus.ERROR, Messages.getString("HDFSForm.check.fieldSeparator")); //$NON-NLS-1$
            return false;
        }

        checkConnectionBtn.setEnabled(true);

        if (!hdfsSettingIsValide) {
            updateStatus(IStatus.INFO, Messages.getString("HDFSForm.check.checkConnection")); //$NON-NLS-1$
            return false;
        }

        updateStatus(IStatus.OK, null);
        return true;

    }

    private void updateConnectionPart() {
        HadoopClusterConnection clusterConnection = HCRepositoryUtil.getRelativeHadoopClusterConnection(getConnection());
        if (clusterConnection != null) {
            userNameText.setEditable(!clusterConnection.isEnableKerberos());
        }
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

    @Override
    protected void addUtilsButtonListeners() {
        checkConnectionBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkConnection();
            }

        });
    }

}
