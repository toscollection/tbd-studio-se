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
package org.talend.repository.hadoopcluster.ui.common;

import org.apache.commons.lang.StringUtils;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.talend.core.hadoop.version.EHadoopVersion4Drivers;
import org.talend.repository.ui.swt.utils.AbstractForm;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public abstract class AbstractHadoopForm<T> extends AbstractForm {

    protected static final String EMPTY_STRING = ""; //$NON-NLS-1$

    protected boolean readOnly;

    protected AbstractHadoopForm(Composite parent, int style) {
        super(parent, style);
    }

    protected AbstractHadoopForm(Composite parent, int style, String[] existingNames) {
        super(parent, style, existingNames);
    }

    @Override
    protected void initialize() {
        // Do nothing by default.
    }

    @Override
    protected void addUtilsButtonListeners() {
        // Do nothing by default.
    }

    @Override
    protected void addFieldsListeners() {
        // Do nothing by default.
    }

    @Override
    protected void adaptFormToReadOnly() {
        // Do nothing by default.
    }

    @Override
    public boolean checkFieldsValue() {
        return false;
    }

    protected T getConnection() {
        return (T) connectionItem.getConnection();
    }

    protected boolean isSupportSecurity(EHadoopVersion4Drivers version4Drivers) {
        if (version4Drivers != null) {
            switch (version4Drivers) {
            case HDP_1_0:
            case HDP_1_2:
            case APACHE_1_0_0:
            case CLOUDERA_CDH4:
            case APACHE_1_0_3_EMR:
                return true;
            default:
                return false;
            }
        }

        return false;
    }

    protected boolean isSupportGroup(EHadoopVersion4Drivers version4Drivers) {
        if (version4Drivers != null) {
            switch (version4Drivers) {
            case APACHE_0_20_2:
            case MAPR:
            case MAPR_EMR:
                return true;
            default:
                return false;
            }
        }

        return false;
    }

    protected boolean validText(final String value) {
        return StringUtils.isNotEmpty(value);
    }

    protected void hideControl(Control control, boolean hide) {
        GridData dataBtn = (GridData) control.getLayoutData();
        dataBtn.exclude = hide;
        control.setLayoutData(dataBtn);
        control.setVisible(!hide);
        if (control.getParent() != null) {
            control.getParent().layout();
        }
    }

}
