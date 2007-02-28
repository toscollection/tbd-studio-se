// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.dbmap.ui.dialog;

import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.managers.UIManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class AliasDialog {

    private String tableName;

    private MapperManager mapperManager;

    private String alias;

    private String[] tables;

    private AliasInternalDialog aliasInternalDialog;

    /**
     * DOC amaumont AliasDialog constructor comment.
     * 
     * @param manager
     */
    public AliasDialog(MapperManager manager, String[] tables) {
        this.mapperManager = manager;
        this.tables = tables;
    }

    public boolean open() {

        UIManager uiManager = this.mapperManager.getUiManager();

        // String inputTableName = getCurrentSelectedInputTableView().getDataMapTable().getName();
        //
        String proposedAlias = "alias"; //$NON-NLS-1$

        IInputValidator inputValidator = new IInputValidator() { //$NON-NLS-1$

            public String isValid(String newText) {
                if (newText.length() == 0 ) {// tableManager.getInputTableFromAlias(newText) != null) {
                    return Messages.getString("UIManager.aliasIsInvalid"); //$NON-NLS-1$
                }
                String aliasTest = aliasInternalDialog.getTableName();
                if (aliasTest == null || aliasTest.length() == 0) {
                    return Messages.getString("AliasDialog.TableMustBeSelected"); //$NON-NLS-1$
                }
                return null;
            }
        };

        aliasInternalDialog = new AliasInternalDialog(mapperManager.getUiManager().getShell(), Messages
                .getString("AliasDialog.addNewAlias"), //$NON-NLS-1$
                Messages.getString("AliasDialog.typeAliasOfTable"), proposedAlias, inputValidator);

        int response = aliasInternalDialog.open();
        if (response == InputDialog.OK) {
            this.tableName = aliasInternalDialog.getTableName();
            this.alias = aliasInternalDialog.getValue();
            return true;
        }
        return false;
    }

    public String getAlias() {
        return this.alias;
    }

    /**
     * Getter for tableName.
     * 
     * @return the tableName
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Sets the tableName.
     * 
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * 
     * DOC amaumont AliasDialog class global comment. Detailled comment <br/>
     * 
     * $Id$
     * 
     */
    class AliasInternalDialog extends InputDialog {

        /**
         * DOC amaumont AliasInternalDialog constructor comment.
         * 
         * @param parentShell
         * @param dialogTitle
         * @param dialogMessage
         * @param initialValue
         * @param validator
         */
        public AliasInternalDialog(Shell parentShell, String dialogTitle, String dialogMessage, String initialValue,
                IInputValidator validator) {
            super(parentShell, dialogTitle, dialogMessage, initialValue, validator);
        }

        String tableName;
        String internalTableName;

        Combo combo;

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.dialogs.InputDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
         */
        @Override
        protected Control createDialogArea(Composite parent) {
            Control control = super.createDialogArea(parent);
            Composite composite = (Composite) control;

            GridLayout gridLayout = (GridLayout) composite.getLayout();
            gridLayout.numColumns = 4;

            Control[] children = composite.getChildren();
            GridData gridDataLabel = (GridData) children[0].getLayoutData();
            gridDataLabel.horizontalSpan = 4;

            GridData gridDataText = (GridData) getText().getLayoutData();
            gridDataText.horizontalSpan = 4;
            gridDataText.grabExcessHorizontalSpace = false;

            Text error = (Text) children[2];
            GridData gridDataError = (GridData) children[2].getLayoutData();
            gridDataError.horizontalSpan = 4;
            gridDataError.horizontalAlignment = SWT.FILL;
            gridDataError.grabExcessHorizontalSpace = true;

            Label label = new Label(composite, SWT.WRAP);
            label.setText("  for this table :");
            GridData data = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
            data.horizontalSpan = 2;
            label.setLayoutData(data);
            label.setFont(parent.getFont());

            combo = new Combo((Composite) control, SWT.DROP_DOWN | SWT.READ_ONLY);
            combo.setItems(tables);
            combo.addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e) {
                }

                public void widgetSelected(SelectionEvent e) {
                    internalTableName = combo.getText();
                    validateInput();
                }

            });

            GridData gridDataCombo = new GridData(GridData.HORIZONTAL_ALIGN_BEGINNING);
            gridDataCombo.horizontalSpan = 2;
            combo.setLayoutData(gridDataCombo);

            composite.layout();

            return control;
        }

        /*
         * (non-Javadoc)
         * 
         * @see org.eclipse.jface.dialogs.InputDialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
         */
        @Override
        protected void createButtonsForButtonBar(Composite parent) {
            super.createButtonsForButtonBar(parent);

            getText().selectAll();

        }
        
        public String getTableName() {
            return internalTableName;
        }
        

    };

}
