package org.talend.repository.oozie.ui;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;
import org.talend.core.model.properties.ConnectionItem;
import org.talend.metadata.managment.ui.wizard.AbstractForm;

public class OozieWizardPage extends WizardPage {

    private ConnectionItem connectionItem;

    private OozieForm oozieForm;

    private final String[] existingNames;

    private final boolean isRepositoryObjectEditable;

    public OozieWizardPage(ConnectionItem connectionItem, boolean isRepositoryObjectEditable, String[] existingNames) {
        super("OozieWizardPage"); //$NON-NLS-1$
        this.connectionItem = connectionItem;
        this.existingNames = existingNames;
        this.isRepositoryObjectEditable = isRepositoryObjectEditable;
    }

    @Override
    public void createControl(Composite parent) {
        oozieForm = new OozieForm(parent, connectionItem, existingNames);
        oozieForm.setReadOnly(!isRepositoryObjectEditable);

        AbstractForm.ICheckListener listener = new AbstractForm.ICheckListener() {

            public void checkPerformed(final AbstractForm source) {
                if (source.isStatusOnError()) {
                    OozieWizardPage.this.setPageComplete(false);
                    setErrorMessage(source.getStatus());
                } else {
                    OozieWizardPage.this.setPageComplete(isRepositoryObjectEditable);
                    setErrorMessage(null);
                    setMessage(source.getStatus(), source.getStatusLevel());
                }
            }
        };
        oozieForm.setListener(listener);
        setControl(oozieForm);
        if (StringUtils.isNotEmpty(connectionItem.getProperty().getLabel())) {
            oozieForm.checkFieldsValue();
        }
    }

}
