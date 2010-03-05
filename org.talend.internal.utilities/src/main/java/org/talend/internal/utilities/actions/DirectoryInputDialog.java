// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
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
package org.talend.internal.utilities.actions;

import java.io.File;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.talend.internal.utilities.Activator;

/**
 * bqian class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ææäº, 29 ä¹æ 2006) nrousseau $
 * 
 */
public class DirectoryInputDialog extends Dialog {

    public final static String SCREENSHOT_STORE_DIRECTORY = "SCREENSHOT_STORE_DIRECTORY"; //$NON-NLS-1$

    private String title;

    private String message;

    private Text input;

    private String result;

    /**
     * bqian DirectoryInputDialog constructor comment.
     * 
     * @param parentShell
     */
    public DirectoryInputDialog(Shell parentShell, String title, String message) {
        super(parentShell);
        this.title = title;

        this.message = message;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.window.Window#configureShell(org.eclipse.swt.widgets.Shell)
     */
    @Override
    protected void configureShell(Shell newShell) {
        super.configureShell(newShell);
        newShell.setText(title);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.InputDialog#createDialogArea(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected Control createDialogArea(Composite parent) {
        Composite composite = (Composite) super.createDialogArea(parent);
        GridLayoutFactory.swtDefaults().numColumns(2).applyTo(composite);

        Label msgLabel = new Label(composite, SWT.NONE);
        GridData gd = new GridData();
        gd.horizontalSpan = 2;
        msgLabel.setLayoutData(gd);
        msgLabel.setText(message);

        input = new Text(composite, SWT.BORDER);
        gd = new GridData(GridData.FILL_HORIZONTAL);
        input.setLayoutData(gd);

        String storedPath = Activator.getDefault().getDialogSettings().get(SCREENSHOT_STORE_DIRECTORY);
        if (storedPath == null) {
            storedPath = ""; //$NON-NLS-1$
        }
        input.setText(storedPath);
        input.addModifyListener(new ModifyListener() {

            public void modifyText(ModifyEvent e) {
                validDirectory();
            }
        });

        Button button = new Button(composite, SWT.NONE);
        button.setText(".."); //$NON-NLS-1$
        gd = new GridData();
        button.setLayoutData(gd);
        button.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent e) {
                DirectoryDialog dd = new DirectoryDialog(DirectoryInputDialog.this.getShell());
                String path = dd.open();
                if (path != null) {
                    input.setText(path);
                }
            }
        });

        return composite;
    }

    public String getResult() {
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#okPressed()
     */
    @Override
    protected void okPressed() {
        result = input.getText();
        Activator.getDefault().getDialogSettings().put(SCREENSHOT_STORE_DIRECTORY, result);
        super.okPressed();
    }

    private void validDirectory() {
        String path = input.getText();
        File file = new File(path);
        getButton(IDialogConstants.OK_ID).setEnabled(file.exists());
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.dialogs.Dialog#createButtonsForButtonBar(org.eclipse.swt.widgets.Composite)
     */
    @Override
    protected void createButtonsForButtonBar(Composite parent) {
        super.createButtonsForButtonBar(parent);
        validDirectory();
    }

}
