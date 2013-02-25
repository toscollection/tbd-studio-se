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
package org.talend.repository.hadoopcluster.ui;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.repository.hadoopcluster.ui.common.AbstractHadoopForm;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.ui.swt.utils.AbstractForm;

/**
 * 
 * created by ycbai on 2013-1-21 Detailled comment
 * 
 */
public class HadoopClusterWizardPage extends WizardPage {

    private ConnectionItem connectionItem;

    private AbstractHadoopForm<HadoopClusterConnection> hadoopClusterForm;

    private final String[] existingNames;

    private final boolean isRepositoryObjectEditable;

    public HadoopClusterWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable, String[] existingNames) {
        super("HadoopClusterWizardPage"); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.existingNames = existingNames;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    @Override
    public void createControl(final Composite parent) {
        hadoopClusterForm = new HadoopClusterForm(parent, connectionItem, existingNames);
        hadoopClusterForm.setReadOnly(!isRepositoryObjectEditable);

        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            @Override
            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    HadoopClusterWizardPage.this.setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    HadoopClusterWizardPage.this.setPageComplete(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };
        hadoopClusterForm.setListener(listener);
        setControl(hadoopClusterForm);
        if (StringUtils.isNotEmpty(connectionItem.getProperty().getLabel())) {
            hadoopClusterForm.checkFieldsValue();
        }
    }

}
