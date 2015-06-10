// ============================================================================
//
// Copyright (C) 2006-2015 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hadoopcluster.ui.conf;

import java.util.List;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.talend.repository.hadoopcluster.service.IRetrieveConfsService;
import org.talend.repository.hadoopcluster.ui.AbstractCheckedComposite;
import org.talend.repository.hadoopcluster.ui.ICheckListener;

/**
 * created by ycbai on 2015年5月29日 Detailled comment
 *
 */
public abstract class AbstractHadoopImportConfsPage extends WizardPage implements IImportConfsWizardPage {

    protected IRetrieveConfsService confsService;

    protected AbstractHadoopImportConfsPage(String pageName) {
        super(pageName);
    }

    protected Composite createParentComposite(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.marginWidth = 5;
        layout.marginHeight = 5;
        container.setLayout(layout);
        return container;
    }

    protected void addCheckListener(AbstractCheckedComposite checkedComposite) {
        ICheckListener checkListener = new ICheckListener() {

            @Override
            public void checkPerformed(AbstractCheckedComposite source) {
                if (source.isStatusOnError()) {
                    setErrorMessage(source.getStatus());
                    updatePageStatus();
                } else {
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                    updatePageStatus();
                }
            }
        };
        checkedComposite.setListener(checkListener);
    }

    protected void updatePageStatus() {
        setPageComplete(getErrorMessage() == null);
    }

    @Override
    public boolean isLastPage() {
        return true;
    }

    @Override
    public IRetrieveConfsService getConfsService() {
        return confsService;
    }

    @Override
    public List<String> getSelectedServices() {
        return null;
    }

}
