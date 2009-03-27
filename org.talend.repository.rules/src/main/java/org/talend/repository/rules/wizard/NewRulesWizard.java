// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.rules.wizard;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.properties.RulesItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.ui.images.CoreImageProvider;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public class NewRulesWizard extends Wizard implements INewWizard {

    public RulesItem getRuleItem() {
        return this.ruleItem;
    }

    private IWorkbench workbench;

    private IStructuredSelection selection;

    private NewRulesWizardPage1 mainPage1;

    private NewRulesWizardPage2 mainPage2;

    private IPath destinationPath;

    private Property property;

    private RulesItem ruleItem;

    public NewRulesWizard(IPath destinationPath, Property property) {
        this.destinationPath = destinationPath;
        this.property = property;
        ruleItem = PropertiesFactory.eINSTANCE.createRulesItem();
        ruleItem.setProperty(property);
    }

    public void init(IWorkbench workbench, IStructuredSelection selection) {
        this.workbench = workbench;
        this.selection = selection;
        setWindowTitle("New Rule Package...");
        ImageDescriptor desc = CoreImageProvider.getImageDesc(ERepositoryObjectType.METADATA_FILE_RULES);
        setDefaultPageImageDescriptor(desc);
    }

    public void addPages() {
        mainPage1 = new NewRulesWizardPage1(property, destinationPath);
        mainPage2 = new NewRulesWizardPage2(workbench, selection, destinationPath, property, ruleItem);
        addPage(mainPage1);
        addPage(mainPage2);
    }

    public boolean performFinish() {
        return mainPage2.finish();
    }

}
