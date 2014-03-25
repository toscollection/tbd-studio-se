// ============================================================================
//
// Copyright (C) 2006-2014 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.pigudf.wizard;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.talend.commons.exception.PersistenceException;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.designer.codegen.PigTemplate;
import org.talend.repository.pigudf.i18n.Messages;
import org.talend.repository.ui.wizards.PropertiesWizardPage;

/**
 * Page for new project details. <br/>
 * 
 * $Id: NewPigudfWizardPage.java 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class NewPigudfWizardPage extends PropertiesWizardPage {

    private CCombo pigTemplate;

    protected IStatus templateStatus;

    /**
     * Constructs a new NewPigudfWizardPage.
     * 
     */
    public NewPigudfWizardPage(Property property, IPath destinationPath) {
        super("WizardPage", property, destinationPath);
        setTitle(Messages.getString("NewPigudfWizard.title")); //$NON-NLS-1$
        setDescription(Messages.getString("NewPigudfWizardPage.description")); //$NON-NLS-1$
        templateStatus = createStatus(IStatus.ERROR, Messages.getString("NewPigudfWizardPage.TemplateFormatError"));
    }

    @Override
    public ERepositoryObjectType getRepositoryObjectType() {
        return ERepositoryObjectType.PIG_UDF;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.wizards.routines.NewRoutineWizardPage#createControl(org.eclipse.swt.widgets.Composite)
     */
    @Override
    public void createControl(Composite parent) {
        Composite container = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout(2, false);
        container.setLayout(layout);

        createPigTemplateCombo(container);
        super.createControl(container);
        setControl(container);
        updateContent();
        addListeners();
        setPageComplete(false);

    }

    private void createPigTemplateCombo(Composite parent) {
        Label label = new Label(parent, SWT.NONE);
        label.setText(Messages.getString("NewPigudfWizardPage.pigtemplate")); //$NON-NLS-1$

        pigTemplate = new CCombo(parent, SWT.BORDER);
        pigTemplate.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
        pigTemplate.setEditable(false);
        pigTemplate.setItems(PigTemplate.getPigTemplateToDispaly());
        pigTemplate.setText("");
        pigTemplate.addModifyListener(new ModifyListener() {

            @Override
            public void modifyText(ModifyEvent e) {
                if (!pigTemplate.isDisposed() && "".equals(pigTemplate.getText())) {
                    templateStatus = createStatus(IStatus.ERROR, Messages.getString("NewPigudfWizardPage.TemplateFormatError")); //$NON-NLS-1$
                } else {
                    templateStatus = createOkStatus();
                }
                updatePageStatus();
            }
        });
    }

    @Override
    protected void evaluateTextField() {
        if (!pigTemplate.isDisposed() && "".equals(pigTemplate.getText())) {
            templateStatus = createStatus(IStatus.ERROR, Messages.getString("NewPigudfWizardPage.TemplateFormatError")); //$NON-NLS-1$
        } else {
            templateStatus = createOkStatus();
        }
        super.evaluateTextField();
        if (nameStatus.getSeverity() == IStatus.OK) {
            evaluateNameInJob();
        }
    }

    @Override
    protected IStatus[] getStatuses() {
        return new IStatus[] { templateStatus, nameStatus, purposeStatus, commentStatus };
    }

    @Override
    protected List<IRepositoryViewObject> loadRepViewObjectWithOtherTypes() throws PersistenceException {
        List<IRepositoryViewObject> list = new ArrayList<IRepositoryViewObject>();

        // List for common process
        list.addAll(loadRepViewObjectWithOtherTypes(ERepositoryObjectType.PROCESS));

        // List for m/r process
        ERepositoryObjectType mrRepObjType = ERepositoryObjectType.valueOf(ERepositoryObjectType.class, "PROCESS_MR");//$NON-NLS-1$
        list.addAll(loadRepViewObjectWithOtherTypes(mrRepObjType));

        // routine
        list.addAll(loadRepViewObjectWithOtherTypes(ERepositoryObjectType.ROUTINES));

        return list;
    }

    public PigTemplate getSelectedTemplate() {
        if (pigTemplate.getSelectionIndex() != -1) {
            return PigTemplate.values()[pigTemplate.getSelectionIndex()];
        } else {
            return PigTemplate.EVALFUNC;
        }
    }

}
