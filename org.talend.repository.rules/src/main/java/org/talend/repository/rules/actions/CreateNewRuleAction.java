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
package org.talend.repository.rules.actions;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.INewWizard;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.utils.VersionUtils;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.Property;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.RepositoryManager;
import org.talend.core.ui.images.ECoreImage;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.ProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.model.RepositoryNode.EProperties;
import org.talend.repository.rules.wizard.NewRulesWizard;
import org.talend.repository.ui.actions.metadata.AbstractCreateAction;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public class CreateNewRuleAction extends AbstractCreateAction {

    private static String CREATE_LABEL = "Create Rules"; //$NON-NLS-1$

    private Property property;

    private RepositoryNode node;

    ImageDescriptor defaultImage = ImageProvider.getImageDesc(ECoreImage.METADATA_RULES_ICON);

    public CreateNewRuleAction() {
        super();
        this.setText(CREATE_LABEL);
        this.setToolTipText(CREATE_LABEL);
    }

    public CreateNewRuleAction(boolean isToolbar) {
        super();
        setToolbar(isToolbar);
        this.setText(CREATE_LABEL);
        this.setToolTipText(CREATE_LABEL);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.ui.actions.metadata.AbstractCreateAction#init(org.talend.repository.model.RepositoryNode)
     */
    @Override
    protected void init(RepositoryNode node) {
        ERepositoryObjectType nodeType = (ERepositoryObjectType) node.getProperties(EProperties.CONTENT_TYPE);
        if (!ERepositoryObjectType.METADATA_FILE_RULES.equals(nodeType)) {
            setEnabled(false);
            return;
        }
        IProxyRepositoryFactory factory = ProxyRepositoryFactory.getInstance();
        switch (node.getType()) {
        case SIMPLE_FOLDER:
        case SYSTEM_FOLDER:
            if (factory.isUserReadOnlyOnCurrentProject() || !ProjectManager.getInstance().isInCurrentMainProject(node)) {
                setEnabled(false);
                return;
            }
            this.setText(CREATE_LABEL);
            collectChildNames(node);
            this.setImageDescriptor(defaultImage);
            break;
        case REPOSITORY_ELEMENT:
            setEnabled(false);
            return;
        }
        setEnabled(true);
    }

    @Override
    protected void doRun() {
        node = null;
        IPath path = null;
        if (!isToolbar()) {
            ISelection selection = getSelection();
            Object obj = ((IStructuredSelection) selection).getFirstElement();
            node = (RepositoryNode) obj;
            path = RepositoryNodeUtilities.getPath(node);
            this.property = PropertiesFactory.eINSTANCE.createProperty();
            this.property.setAuthor(((RepositoryContext) CorePlugin.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY))
                    .getUser());
            this.property.setVersion(VersionUtils.DEFAULT_VERSION);
            this.property.setStatusCode(""); //$NON-NLS-1$
            NewRulesWizard wizard = new NewRulesWizard(path, property);
            launchWizard(Display.getCurrent().getActiveShell(), wizard);
        }
    }

    private void launchWizard(Shell shell, INewWizard wizard) {
        wizard.init(CorePlugin.getDefault().getWorkbench(), new DummySelection());
        WizardDialog dialog = new WizardDialog(shell, wizard);
        if (dialog.open() == Window.OK) {
            IProxyRepositoryFactory repositoryFactory = CorePlugin.getDefault().getRepositoryService()
                    .getProxyRepositoryFactory();

            try {
                NewRulesWizard ruleWizard = (NewRulesWizard) wizard;
                repositoryFactory.lock(ruleWizard.getRuleItem());
            } catch (PersistenceException e) {
                ExceptionHandler.process(e);
            }
            RepositoryManager.refreshCreatedNode(ERepositoryObjectType.METADATA_FILE_RULES);
        }
    }

    /** Stub structured selection listener, as is required to launch the wizard */
    static class DummySelection implements IStructuredSelection {

        public Object getFirstElement() {
            return null;
        }

        public Iterator iterator() {
            return (new ArrayList()).iterator();
        }

        public int size() {
            return 0;
        }

        public Object[] toArray() {
            return null;
        }

        public List toList() {
            return null;
        }

        public boolean isEmpty() {
            return true;
        }
    }

}
