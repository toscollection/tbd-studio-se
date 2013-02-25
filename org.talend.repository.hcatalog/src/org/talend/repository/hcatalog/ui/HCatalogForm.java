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
package org.talend.repository.hcatalog.ui;

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
import org.eclipse.swt.widgets.Label;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.hcatalog.i18n.Messages;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HCatalogForm extends AbstractHCatalogForm {

    private UtilsButton checkConnectionBtn;

    private LabelledText hostText;

    private LabelledText portText;

    private LabelledText userNameText;

    private LabelledText databaseText;

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
        hostText.setText(getConnection().getHostName());
        portText.setText(getConnection().getPort());
        userNameText.setText(getConnection().getUserName());
        databaseText.setText(getConnection().getDatabase());

        updateStatus(IStatus.OK, EMPTY_STRING);
    }

    @Override
    protected void adaptFormToReadOnly() {
        readOnly = isReadOnly();
        hostText.setReadOnly(readOnly);
        portText.setReadOnly(readOnly);
        userNameText.setReadOnly(readOnly);
    }

    @Override
    protected void addFields() {
        addTempletonFields();
        addDatabaseFields();
        addCheckFields();
    }

    private void addTempletonFields() {
        Group templetonGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.templetonSettings"), 110); //$NON-NLS-1$
        templetonGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

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
    }

    private void addDatabaseFields() {
        Group databaseGroup = Form.createGroup(this, 1, Messages.getString("HCatalogForm.databaseSettings"), 80); //$NON-NLS-1$
        databaseGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

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

    @Override
    protected void addFieldsListeners() {
        hostText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setHostName(hostText.getText());
                checkFieldsValue();
            }
        });

        portText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setPort(portText.getText());
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

        databaseText.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(final ModifyEvent e) {
                getConnection().setDatabase(databaseText.getText());
                checkFieldsValue();
            }
        });
    }

    @Override
    public boolean checkFieldsValue() {
        checkConnectionBtn.setEnabled(false);

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

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.swt.widgets.Control#setVisible(boolean)
     */
    @Override
    public void setVisible(boolean visible) {
        super.setVisible(visible);
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
