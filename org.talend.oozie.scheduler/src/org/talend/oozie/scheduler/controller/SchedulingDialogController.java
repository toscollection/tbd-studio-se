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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Text;
import org.talend.commons.ui.swt.tableviewer.celleditor.DateDialog;
import org.talend.oozie.scheduler.ui.SchedulingDialog;
import org.talend.oozie.scheduler.utils.OozieSchedulerDateUtils;

/**
 * Created by Marvin Wang on Mar. 31, 2012 for scheduling controller.
 */
public class SchedulingDialogController {

    private SchedulingDialog schedulingDialog;

    public static final String PATTERN = "[1-9][0-9]*";

    public static final String TITLE_CHECKING_FREQUENCY = "Frequency value error";

    public static final String MSG_ERROR_FREQUENCY = "The frequency value must be positive integer!";

    /**
     */
    public SchedulingDialogController(SchedulingDialog schedulingDialog) {
        this.schedulingDialog = schedulingDialog;
    }

    public void doFrequencyAction() {
        String frequencyValue = schedulingDialog.getFrequencyTxt().getText();
        if (frequencyValue != null && !"".equals(frequencyValue)) {
            boolean isValid = checkFrequencyValue(frequencyValue);
            if (isValid) {
                schedulingDialog.setFrequencyValue(frequencyValue);
            } else {
                MessageDialog.openError(schedulingDialog.getShell(), TITLE_CHECKING_FREQUENCY, MSG_ERROR_FREQUENCY);
            }
        }

        schedulingDialog.getOKButton().setEnabled(checkIfAllValidOrNot());
    }

    public boolean checkIfAllValidOrNot() {
        boolean okBtnIsEnabled = false;
        boolean startTimeHasValue = checkStartTimeValue();
        boolean endTimeHasValue = checkEndTimeValue();
        boolean frequencyValueIsValid = checkFrequencyValue();
        if (startTimeHasValue && endTimeHasValue && frequencyValueIsValid)
            okBtnIsEnabled = true;
        else
            okBtnIsEnabled = false;
        return okBtnIsEnabled;
    }

    protected boolean checkStartTimeValue() {
        boolean hasValue = false;
        String endTimeValue = schedulingDialog.getEndTimeTxt().getText();
        if (endTimeValue != null && !"".equals(endTimeValue))
            hasValue = true;
        else
            hasValue = false;
        return hasValue;
    }

    protected boolean checkEndTimeValue() {
        boolean hasValue = false;
        String startTimeValue = schedulingDialog.getStartTimeTxt().getText();
        if (startTimeValue != null && !"".equals(startTimeValue))
            hasValue = true;
        else
            hasValue = false;
        return hasValue;
    }

    protected boolean checkFrequencyValue() {
        String frequencyValue = schedulingDialog.getFrequencyTxt().getText();
        return checkFrequencyValue(frequencyValue);
    }

    /**
     * Checks the input value for frequency, which must be the positive integer. This method uses the regex that is
     * <code>[1-9][0-9]*</code>.
     * 
     * @param frequencyValue
     * @return
     */
    protected boolean checkFrequencyValue(String frequencyValue) {
        boolean isMatched = false;
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(frequencyValue);
        isMatched = matcher.matches();
        return isMatched;
    }

    public void doTimeUnitAction() {
        int selectionIndex = schedulingDialog.getTimeUnitCombo().getSelectionIndex();
        schedulingDialog.setSelectedTimeUnitIndex(selectionIndex);
    }

    public void doStartTimeAction() {
        DateDialog dateDialog = new DateDialog(schedulingDialog.getShell());
        if (Window.OK == dateDialog.open()) {
            Date startDate = dateDialog.getDate();
            schedulingDialog.setStartDate(startDate);
            String dateString = OozieSchedulerDateUtils.convertDateToString(startDate);
            schedulingDialog.getStartTimeTxt().setText(dateString);
        }
        schedulingDialog.getOKButton().setEnabled(checkIfAllValidOrNot());
    }

    public void doEndTimeAction() {
        DateDialog dateDialog = new DateDialog(schedulingDialog.getShell());
        if (Window.OK == dateDialog.open()) {
            Date endDate = dateDialog.getDate();
            schedulingDialog.setEndDate(endDate);
            String dateString = OozieSchedulerDateUtils.convertDateToString(endDate);
            schedulingDialog.getEndTimeTxt().setText(dateString);
        }
        schedulingDialog.getOKButton().setEnabled(checkIfAllValidOrNot());
    }

    private void initJobContext() {

    }
}
