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
package org.talend.oozie.scheduler.controller;

import java.util.Date;

import org.eclipse.jface.window.Window;
import org.talend.commons.ui.swt.tableviewer.celleditor.DateDialog;
import org.talend.oozie.scheduler.ui.SchedulingDialog;
import org.talend.oozie.scheduler.utils.OozieSchedulerDateUtils;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for scheduling controller.
 */
public class SchedulingDialogController {

    private SchedulingDialog chedulingDialog;

    /**
     */
    public SchedulingDialogController(SchedulingDialog chedulingDialog) {
        this.chedulingDialog = chedulingDialog;
    }

    public void doTimeUnitAction() {
        int selectionIndex = chedulingDialog.getTimeUnitCombo().getSelectionIndex();
        chedulingDialog.setSelectedTimeUnitIndex(selectionIndex);
    }

    public void doStartTimeAction() {
        DateDialog dateDialog = new DateDialog(chedulingDialog.getShell());
        if (Window.OK == dateDialog.open()) {
            Date startDate = dateDialog.getDate();
            chedulingDialog.setStartDate(startDate);
            String dateString = OozieSchedulerDateUtils.convertDateToString(startDate);
            chedulingDialog.getStartTimeTxt().setText(dateString);
        }
    }

    public void doEndTimeAction() {
        DateDialog dateDialog = new DateDialog(chedulingDialog.getShell());
        if (Window.OK == dateDialog.open()) {
            Date endDate = dateDialog.getDate();
            chedulingDialog.setEndDate(endDate);
            String dateString = OozieSchedulerDateUtils.convertDateToString(endDate);
            chedulingDialog.getEndTimeTxt().setText(dateString);
        }
    }

    /**
     * When clicking the button OK, this action will be run to shcedule the job.
     */
    public void doOKAction() {

    }

    private void initJobContext() {

    }
}
