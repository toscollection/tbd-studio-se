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
package org.talend.repository.nosql.ui.common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.ui.swt.dialogs.ErrorDialogWidthDetailArea;
import org.talend.commons.ui.swt.formtools.Form;
import org.talend.commons.ui.swt.formtools.UtilsButton;
import org.talend.core.model.context.JobContextManager;
import org.talend.core.model.process.IContextParameter;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.core.model.properties.ContextItem;
import org.talend.designer.core.model.utils.emf.talendfile.ContextType;
import org.talend.repository.model.nosql.NoSQLConnection;
import org.talend.repository.nosql.RepositoryNoSQLPlugin;
import org.talend.repository.nosql.constants.INoSQLConstants;
import org.talend.repository.nosql.context.NoSQLConnectionContextManager;
import org.talend.repository.nosql.factory.NoSQLRepositoryFactory;
import org.talend.repository.nosql.i18n.Messages;
import org.talend.repository.nosql.metadata.IMetadataProvider;
import org.talend.repository.ui.utils.ConnectionContextHelper;
import org.talend.repository.ui.wizards.context.ContextWizard;

/**
 * DOC PLV class global comment. Detailled comment
 */
public abstract class AbstractNoSQLConnForm extends AbstractNoSQLForm {

    protected UtilsButton checkBtn;

    protected Composite parentComposite;

    protected Composite topComposite;

    private final NoSQLConnectionContextManager nosqlContextManger;

    protected Set<String> attributes;

    /**
     * DOC PLV AbstractNoSQLConnForm constructor comment.
     * 
     * @param parent
     * @param connectionItem
     * @param existingNames
     */
    protected AbstractNoSQLConnForm(Composite parent, ConnectionItem connectionItem, String[] existingNames, boolean creation,
            WizardPage parentWizardPage) {
        super(parent, connectionItem, existingNames, creation, parentWizardPage);
        parentComposite = parent;
        attributes = new HashSet<String>();
        nosqlContextManger = new NoSQLConnectionContextManager(getConnection(), attributes);
    }

    protected Composite createScrolledComposite() {
        ScrolledComposite scrolledComposite = new ScrolledComposite(this, SWT.V_SCROLL | SWT.H_SCROLL);
        scrolledComposite.setExpandHorizontal(true);
        scrolledComposite.setExpandVertical(true);
        scrolledComposite.setMinWidth(300);
        scrolledComposite.setMinHeight(250);
        scrolledComposite.setAlwaysShowScrollBars(false);
        scrolledComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
        Composite composite = Form.startNewGridLayout(scrolledComposite, 1);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));
        scrolledComposite.setContent(composite);
        return composite;
    }

    @Override
    protected void addFields() {
        topComposite = createScrolledComposite();
        addConnFields(topComposite);
        createCheckBtn(this);
    }

    protected abstract void addConnFields(Composite parent);

    @Override
    protected void initialize() {
        if (isContextMode()) {
            adaptFormToEditable();
        }
        initializeFields();
        saveAttributes();
        updateAttributes();
    }

    protected void initializeFields() {
        // Do nothing by default.
    }

    protected void saveAttributes() {
        getConnection().getAttributes().clear();
    }

    protected void updateAttributes() {
        attributes.clear();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#addUtilsButtonListeners()
     */
    @Override
    protected void addUtilsButtonListeners() {
        if (checkBtn != null) {
            checkBtn.addSelectionListener(new SelectionAdapter() {

                /*
                 * (non-Javadoc)
                 * 
                 * @see org.eclipse.swt.events.SelectionAdapter#widgetSelected(org.eclipse.swt.events.SelectionEvent)
                 */
                @Override
                public void widgetSelected(SelectionEvent e) {
                    try {
                        if (getConnection() != null) {
                            IMetadataProvider metadataProvider = NoSQLRepositoryFactory.getInstance().getMetadataProvider(
                                    getConnection().getDbType());
                            if (metadataProvider != null && metadataProvider.checkConnection(getConnection())) {
                                MessageDialog.openInformation(
                                        getShell(),
                                        Messages.getString("AbstractNoSQLConnForm.checkConn"), Messages.getString("AbstractNoSQLConnForm.checkSuccessful")); //$NON-NLS-1$ //$NON-NLS-2$
                            }
                        }
                    } catch (Exception exception) {
                        new ErrorDialogWidthDetailArea(getShell(), RepositoryNoSQLPlugin.PLUGIN_ID, Messages
                                .getString("AbstractNoSQLConnForm.checkFailed"), //$NON-NLS-1$
                                ExceptionUtils.getFullStackTrace(exception));
                        ExceptionHandler.process(exception);
                    }

                }
            });
        }
    }

    private void adaptDBTypeComboToEditable(boolean editable) {
        Control dbTypeCombo = findControlByData(parentComposite, INoSQLConstants.DB_TYPE_COMBO_DATA);
        if (dbTypeCombo != null) {
            dbTypeCombo.setEnabled(editable);
        }
    }

    @Override
    protected void adaptFormToReadOnly() {
        updateEditableStatus(isEditable());
    }

    @Override
    protected void adaptFormToEditable() {
        super.adaptFormToEditable();
        adaptDBTypeComboToEditable(isEditable());
        updateEditableStatus(isEditable());
    }

    protected boolean isEditable() {
        return !isReadOnly() && !isContextMode();
    }

    protected void updateEditableStatus(boolean editable) {
        // Do nothing by default.
    }

    protected void createCheckBtn(Composite parent) {
        Composite composite = new Composite(parent, SWT.NONE);
        composite.setLayoutData(new GridData(GridData.CENTER, GridData.CENTER, true, false));
        GridLayout gpGrid = new GridLayout(1, false);
        gpGrid.marginWidth = 10;
        gpGrid.marginHeight = 10;
        composite.setLayout(gpGrid);
        checkBtn = new UtilsButton(composite, Messages.getString("AbstractNoSQLConnForm.check"), WIDTH_BUTTON_PIXEL, //$NON-NLS-1$
                HEIGHT_BUTTON_PIXEL);
    }

    private void addContextParams(List<IContextParameter> varList) {
        String contextName = ConnectionContextHelper.convertContextLabel(connectionItem.getProperty().getLabel());
        for (String attr : attributes) {
            ConnectionContextHelper.createParameters(varList, contextName + "_" + attr, //$NON-NLS-1$
                    getConnection().getAttributes().get(attr), null);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#exportAsContext()
     */
    @Override
    protected void exportAsContext() {
        List<IContextParameter> varList = new ArrayList<IContextParameter>();
        addContextParams(varList);
        exportAsContext(varList, getConnection());
    }

    protected void exportAsContext(List<IContextParameter> varList, NoSQLConnection connection) {
        if (hasContextBtn() && connectionItem != null) {
            if (isContextMode()) {
                ConnectionContextHelper.openInConetxtModeDialog();
            } else {
                ContextItem contextItem = null;
                if (connectionItem == null) {
                    contextItem = null;
                }
                String contextName = ConnectionContextHelper.convertContextLabel(connectionItem.getProperty().getLabel());

                if (varList == null || varList.isEmpty()) {
                    contextItem = null;
                }
                ISelection selection = ConnectionContextHelper.getRepositoryContext(contextName, false);
                if (selection == null) {
                    contextItem = null;
                }

                ContextWizard contextWizard = new ContextWizard(contextName, selection.isEmpty(), selection, varList);
                WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), contextWizard);
                if (dlg.open() == Window.OK) {
                    contextItem = contextWizard.getContextItem();
                    contextManager = contextWizard.getContextManager();
                }
                if (contextItem != null) { // create
                    if (contextManager instanceof JobContextManager) {
                        Map<String, String> map = ((JobContextManager) contextManager).getNameMap();
                        // set properties for context mode
                        nosqlContextManger.setPropertiesForContextMode(contextItem, map);
                    }
                    // refresh current UI.
                    initialize();
                }
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.ui.swt.utils.AbstractForm#revertContext()
     */
    @Override
    protected void revertContext() {
        if (hasContextBtn() && connectionItem != null) {
            if (isContextMode()) {
                ContextType contextType = ConnectionContextHelper.getContextTypeForContextMode(getShell(),
                        connectionItem.getConnection(), true);
                if (contextType != null) { // choose
                    nosqlContextManger.revertPropertiesForContextMode(contextType);
                    adaptContextModeToReversion();
                }
            } else {
                ConnectionContextHelper.openOutConetxtModeDialog();
            }
        }
    }

}
