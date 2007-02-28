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
package org.talend.designer.dbmap.ui.footer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.managers.UIManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: FooterComposite.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class FooterComposite extends Composite {

    private MapperManager mapperManager;

    /**
     * DOC amaumont FooterComposite constructor comment.
     * 
     * @param parent
     * @param style
     * @param mapperManager
     */
    public FooterComposite(Composite parent, int style, MapperManager mapperManager) {
        super(parent, style);
        this.mapperManager = mapperManager;
        createComponents();
    }

    /**
     * DOC amaumont Comment method "createComponents".
     */
    private void createComponents() {

        final UIManager uiManager = mapperManager.getUiManager();

        GridData footerCompositeGridData = new GridData(GridData.FILL_HORIZONTAL);
        this.setLayoutData(footerCompositeGridData);

        FormLayout formLayout = new FormLayout();
        this.setLayout(formLayout);

        Button okButton = new Button(this, SWT.NONE);
        okButton.setText(Messages.getString("FooterComposite.button.OK")); //$NON-NLS-1$
        FormData okFormData = new FormData();
        okButton.setLayoutData(okFormData);
        okButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                uiManager.closeMapper(SWT.OK);
            }

        });

        // final Button applyButton = new Button(this, SWT.NONE);
        // applyButton.setText("Apply");
        // FormData applyFormData = new FormData();
        // applyButton.setLayoutData(applyFormData);
        // applyButton.addSelectionListener(new SelectionListener() {
        //
        // public void widgetDefaultSelected(SelectionEvent e) {
        // }
        //
        // public void widgetSelected(SelectionEvent e) {
        // MessageBox messageBox = new MessageBox(uiManager.retrieveShellParent(footerComposite));
        // messageBox.setText("Info");
        // messageBox.setMessage("Not implemented yet !");
        // messageBox.open();
        // }
        //
        // });

        Button cancelButton = new Button(this, SWT.NONE);
        cancelButton.setText(Messages.getString("FooterComposite.button.Cancel")); //$NON-NLS-1$
        cancelButton.addSelectionListener(new SelectionListener() {

            public void widgetDefaultSelected(SelectionEvent e) {
            }

            public void widgetSelected(SelectionEvent e) {
                uiManager.closeMapper(SWT.CANCEL);
            }

        });
        FormData cancelFormData = new FormData();
        cancelButton.setLayoutData(cancelFormData);

        // applyFormData.right = new FormAttachment(100);
        // cancelFormData.right = new FormAttachment(applyButton, -5);
        cancelFormData.right = new FormAttachment(100, -5);
        okFormData.right = new FormAttachment(cancelButton, -5);

    }

}
