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
package org.talend.repository.nosql.db.ui.neo4j;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Group;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.LabelledText;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.nosql.db.common.neo4j.INeo4jAttributes;
import org.talend.repository.nosql.db.common.neo4j.INeo4jConstants;
import org.talend.repository.nosql.db.util.neo4j.Neo4jConnectionUtil;
import org.talend.repository.nosql.exceptions.NoSQLGeneralException;
import org.talend.repository.nosql.exceptions.NoSQLServerException;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm;
import org.talend.repository.nosql.validator.NonemptyValidator;

/**
 * 
 * created by ycbai on Jul 22, 2014 Detailled comment
 * 
 */
public class Neo4jConnForm extends AbstractNoSQLConnForm {

    private Button localDbBtn;

    private Button remoteDbBtn;

    private StackLayout dbLayout;

    private Composite dbComposite;

    private Composite localDBComposite;

    private Composite remoteDBComposite;

    private Button browseDbPathBtn;

    private LabelledText dbPathTxt;

    private LabelledText serverURLTxt;

    public Neo4jConnForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, existingNames, creation, parentWizardPage);
        setupForm(true);
        GridLayout layout = (GridLayout) getLayout();
        layout.marginHeight = 0;
        layout.marginWidth = 0;
        setLayout(layout);
        setLayoutData(new GridData(GridData.FILL_BOTH));
    }

    @Override
    protected void initializeFields() {
        boolean isRemote = Boolean.valueOf(getConnection().getAttributes().get(INeo4jAttributes.REMOTE_SERVER));
        String dbPath = StringUtils.trimToEmpty(getConnection().getAttributes().get(INeo4jAttributes.DATABASE_PATH));
        String serverURL = StringUtils.trimToNull(getConnection().getAttributes().get(INeo4jAttributes.SERVER_URL));
        localDbBtn.setSelection(!isRemote);
        remoteDbBtn.setSelection(isRemote);
        udpateDBConnPart(isRemote);
        dbPathTxt.setText(dbPath);
        serverURLTxt.setText(serverURL == null ? INeo4jConstants.DEFAULT_SERVER_URL : serverURL);
    }

    @Override
    protected void updateEditableStatus(boolean editable) {
        localDbBtn.setEnabled(editable);
        remoteDbBtn.setEnabled(editable);
        browseDbPathBtn.setEnabled(editable);
        dbPathTxt.setEditable(editable);
        serverURLTxt.setEditable(editable);
    }

    @Override
    protected void addConnFields(Composite parent) {
        addConnectionGroup(parent);
    }

    private void addConnectionGroup(Composite composite) {
        Group connectionGroup = Form.createGroup(composite, 4, "Connection"); //$NON-NLS-1$
        connectionGroup.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        GridLayout gpLayout = new GridLayout(2, true);
        gpLayout.marginWidth = 5;
        gpLayout.marginHeight = 5;
        connectionGroup.setLayout(gpLayout);

        localDbBtn = new Button(connectionGroup, SWT.RADIO);
        localDbBtn.setText(Messages.getString("Neo4jConnForm.button.localDB.label")); //$NON-NLS-1$
        localDbBtn.setSelection(true);

        remoteDbBtn = new Button(connectionGroup, SWT.RADIO);
        remoteDbBtn.setText(Messages.getString("Neo4jConnForm.button.remoteDB.label")); //$NON-NLS-1$

        dbComposite = new Composite(connectionGroup, SWT.NONE);
        GridData dbCompGD = new GridData(GridData.FILL_BOTH);
        dbCompGD.horizontalSpan = 2;
        dbComposite.setLayoutData(dbCompGD);
        dbLayout = new StackLayout();
        dbComposite.setLayout(dbLayout);

        localDBComposite = new Composite(dbComposite, SWT.NONE);
        localDBComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout localDBCompLayout = new GridLayout(4, false);
        localDBCompLayout.marginWidth = 0;
        localDBCompLayout.marginHeight = 0;
        localDBComposite.setLayout(localDBCompLayout);
        dbPathTxt = new LabelledText(localDBComposite, Messages.getString("Neo4jConnForm.dbPath.label")); //$NON-NLS-1$
        browseDbPathBtn = new Button(localDBComposite, SWT.NONE);
        browseDbPathBtn.setText("..."); //$NON-NLS-1$

        remoteDBComposite = new Composite(dbComposite, SWT.NONE);
        remoteDBComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        GridLayout remoteDBCompLayout = new GridLayout(4, false);
        remoteDBCompLayout.marginWidth = 0;
        remoteDBCompLayout.marginHeight = 0;
        remoteDBComposite.setLayout(remoteDBCompLayout);
        serverURLTxt = new LabelledText(remoteDBComposite, Messages.getString("Neo4jConnForm.serverURL.label")); //$NON-NLS-1$
    }

    private void udpateDBConnPart(boolean isRemote) {
        dbLayout.topControl = isRemote ? remoteDBComposite : localDBComposite;
        dbComposite.layout();
    }

    @Override
    protected void addFieldsListeners() {
        localDbBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INeo4jAttributes.REMOTE_SERVER, String.valueOf(remoteDbBtn.getSelection()));
                udpateDBConnPart(!localDbBtn.getSelection());
                updateAttributes();
            }
        });

        remoteDbBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                checkFieldsValue();
                getConnection().getAttributes().put(INeo4jAttributes.REMOTE_SERVER, String.valueOf(remoteDbBtn.getSelection()));
                udpateDBConnPart(remoteDbBtn.getSelection());
                updateAttributes();
            }
        });

        browseDbPathBtn.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dial = new DirectoryDialog(getShell(), SWT.NONE);
                String directory = dial.open();
                if (StringUtils.isNotEmpty(directory)) {
                    String portableValue = Path.fromOSString(directory).toPortableString();
                    dbPathTxt.setText(portableValue);
                }
            }
        });

        dbPathTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                if (localDbBtn.getSelection()) {
                    checkFieldsValue();
                    String originalDBPath = getConnection().getAttributes().get(INeo4jAttributes.DATABASE_PATH);
                    String currentDBPath = dbPathTxt.getText();
                    if (!StringUtils.equals(originalDBPath, currentDBPath)) {
                        getConnection().getAttributes().put(INeo4jAttributes.DATABASE_PATH, currentDBPath);
                        udpateConnectionConfig();
                    }
                }
            }
        });

        serverURLTxt.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                if (remoteDbBtn.getSelection()) {
                    checkFieldsValue();
                    String originalServerURL = getConnection().getAttributes().get(INeo4jAttributes.SERVER_URL);
                    String currentServerURL = serverURLTxt.getText();
                    if (!StringUtils.equals(originalServerURL, currentServerURL)) {
                        getConnection().getAttributes().put(INeo4jAttributes.SERVER_URL, currentServerURL);
                        udpateConnectionConfig();
                    }
                }
            }
        });
    }

    private void udpateConnectionConfig() {
        Neo4jConnectionUtil.resetAll();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.nosql.ui.common.AbstractNoSQLConnForm#updateValidatorEntries()
     */
    @Override
    protected void updateValidatorEntries() {
        super.updateValidatorEntries();
        if (localDbBtn.getSelection()) {
            collectValidateEntry(new NonemptyValidator(dbPathTxt.getText()),
                    Messages.getString("Neo4jConnForm.validate.invalidDbPath")); //$NON-NLS-1$
        }
        if (remoteDbBtn.getSelection()) {
            collectValidateEntry(new NonemptyValidator(serverURLTxt.getText()),
                    Messages.getString("Neo4jConnForm.validate.invalidServerURL")); //$NON-NLS-1$
        }
    }

    @Override
    protected void updateAttributes() {
        super.updateAttributes();
        if (remoteDbBtn.getSelection()) {
            attributes.add(INeo4jAttributes.SERVER_URL);
        } else {
            attributes.add(INeo4jAttributes.DATABASE_PATH);
        }
    }

    @Override
    protected void saveAttributes() {
        super.saveAttributes();
        getConnection().getAttributes().put(INeo4jAttributes.REMOTE_SERVER, String.valueOf(remoteDbBtn.getSelection()));
        getConnection().getAttributes().put(INeo4jAttributes.DATABASE_PATH, dbPathTxt.getText());
        getConnection().getAttributes().put(INeo4jAttributes.SERVER_URL, serverURLTxt.getText());
    }

    @Override
    public void releaseResources() throws NoSQLGeneralException {
        super.releaseResources();
        try {
            Neo4jConnectionUtil.closeConnections();
        } catch (NoSQLServerException e) {
            throw new NoSQLGeneralException(e);
        }
    }

}
