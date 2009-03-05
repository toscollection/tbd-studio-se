package org.talend.internal.utilities.actions;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.program.Program;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Link;
import org.eclipse.swt.widgets.Shell;

public class ResultrDialog extends Dialog {

    protected Object result;

    protected Shell shell;

    private String message;

    /**
     * Create the dialog
     * 
     * @param parent
     * @param style
     * @param message
     */
    public ResultrDialog(Shell parent, int style, String message) {
        super(parent, style);
        this.message = message;
    }

    /**
     * Create the dialog
     * 
     * @param parent
     * @param message
     */
    public ResultrDialog(Shell parent, String message) {
        this(parent, SWT.NONE, message);
    }

    /**
     * Open the dialog
     * 
     * @return the result
     */
    public void open() {
        createContents();
        shell.open();
        shell.layout();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch())
                display.sleep();
        }
    }

    /**
     * Create contents of the dialog
     */
    protected void createContents() {
        shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
        shell.setLayout(new GridLayout());
        shell.setSize(200, 130);

        Link directoryLink = new Link(shell, SWT.WRAP);
        GridData gd_directoryLink = new GridData(SWT.FILL, SWT.FILL, true, true);
        directoryLink.setLayoutData(gd_directoryLink);
        directoryLink.setText(message);
        directoryLink.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                Program.launch(e.text);
                shell.close();
            }
        });

        Button okButton = new Button(shell, SWT.NONE);
        GridData gd_okButton = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        gd_okButton.widthHint = IDialogConstants.BUTTON_WIDTH;
        okButton.setLayoutData(gd_okButton);
        okButton.setText("Ok"); //$NON-NLS-1$
        okButton.addSelectionListener(new SelectionAdapter() {

            @Override
            public void widgetSelected(SelectionEvent e) {
                shell.close();
            }
        });

    }
}
