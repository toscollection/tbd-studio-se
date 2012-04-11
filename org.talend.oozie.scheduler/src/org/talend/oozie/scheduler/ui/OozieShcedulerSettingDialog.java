// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.ui;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.talend.oozie.scheduler.i18n.Messages;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for Talend Oozie Scheduler setup dialog.
 */
public class OozieShcedulerSettingDialog extends Dialog {

    private Text nameNodeEndPointTxt;// "Name Node End Point" Text widget

    private Text jobTrackerEndPointTxt;// "Job Tracker End Point" Text widget

    private Text oozieEndPointTxt;// "Oozie End Point" Text widget

    private String nameNodeEndPointValue;// The value of "Name Node End Point" Text widget

    private String jobTrackerEndPointValue;// The value of "Job Tracker End Point" Text widget

    private String oozieEndPointValue;// The value of "Oozie End Point" Text widget

    /**
     * @param parentShell
     */
    public OozieShcedulerSettingDialog(Shell parentShell) {
        super(parentShell);
        setShellStyle(this.getShellStyle() | SWT.RESIZE);
    }

    /**
     * 
     */
    protected Control createDialogArea(Composite parent) {
        Composite comp = new Composite(parent, SWT.NONE);
        GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

        GridLayout gridLayout = new GridLayout(2, false);
        comp.setLayout(gridLayout);

        // Name node end point
        Label nameNodeEPLbl = new Label(comp, SWT.NONE);
        nameNodeEPLbl.setText(Messages.getString("Label_NameNodeEndPoint"));

        nameNodeEndPointTxt = new Text(comp, SWT.BORDER);
        nameNodeEndPointTxt.setText(nameNodeEndPointValue == null ? "" : nameNodeEndPointValue);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(nameNodeEndPointTxt);

        // Job tracker end point
        Label jobTrackerEPLbl = new Label(comp, SWT.NONE);
        jobTrackerEPLbl.setText(Messages.getString("Label_JobTrackerEndPoint"));

        jobTrackerEndPointTxt = new Text(comp, SWT.BORDER);
        jobTrackerEndPointTxt.setText(jobTrackerEndPointValue == null ? "" : jobTrackerEndPointValue);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(jobTrackerEndPointTxt);

        // Oozie end point
        Label oozieEPLbl = new Label(comp, SWT.NONE);
        oozieEPLbl.setText(Messages.getString("Label_OozieEndPoint"));

        oozieEndPointTxt = new Text(comp, SWT.BORDER);
        oozieEndPointTxt.setText(oozieEndPointValue == null ? "" : oozieEndPointValue);
        GridDataFactory.fillDefaults().grab(true, false).applyTo(oozieEndPointTxt);
        return parent;
    }

    /**
     * Saves all the information like "Name node end point", "Job tracker end point" and "Oozie end point" to project
     * preference. And sets the dialog's return code to <code>Window.OK</code> and closes the dialog.
     */
    @Override
    protected void okPressed() {
        doOk();
        super.okPressed();
    }

    /**
     * Gets the values of "Name node", "Job tracker" and "Oozie" to save.
     */
    protected void doOk() {
        nameNodeEndPointValue = nameNodeEndPointTxt.getText();
        jobTrackerEndPointValue = jobTrackerEndPointTxt.getText();
        oozieEndPointValue = oozieEndPointTxt.getText();
    }

    /**
     * Reset the dialog size.
     */
    protected Point getInitialSize() {
        Point result = super.getInitialSize();
        result.x = 500;
        result.y = 170;
        return result;
    }

    public String getNameNodeEndPointValue() {
        return this.nameNodeEndPointValue;
    }

    public void setNameNodeEndPointValue(String nameNodeEndPointValue) {
        this.nameNodeEndPointValue = nameNodeEndPointValue;
    }

    public String getJobTrackerEndPointValue() {
        return this.jobTrackerEndPointValue;
    }

    public void setJobTrackerEndPointValue(String jobTrackerEndPointValue) {
        this.jobTrackerEndPointValue = jobTrackerEndPointValue;
    }

    public String getOozieEndPointValue() {
        return this.oozieEndPointValue;
    }

    public void setOozieEndPointValue(String oozieEndPointValue) {
        this.oozieEndPointValue = oozieEndPointValue;
    }
}
