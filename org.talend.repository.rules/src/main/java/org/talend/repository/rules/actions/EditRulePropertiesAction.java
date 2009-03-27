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

import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.image.EImage;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.ui.IUIRefresher;
import org.talend.designer.core.ui.views.properties.IJobSettingsView;
import org.talend.repository.i18n.Messages;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNodeUtilities;
import org.talend.repository.rules.editor.RuleEditorInput;
import org.talend.repository.ui.actions.AContextualAction;
import org.talend.repository.ui.wizards.PropertiesWizard;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public class EditRulePropertiesAction extends AContextualAction {

    public EditRulePropertiesAction() {
        super();
        this.setText(Messages.getString("EditPropertiesAction.action.title")); //$NON-NLS-1$
        this.setToolTipText(Messages.getString("EditPropertiesAction.action.toolTipText")); //$NON-NLS-1$
        this.setImageDescriptor(ImageProvider.getImageDesc(EImage.EDIT_ICON));
    }

    public void init(TreeViewer viewer, IStructuredSelection selection) {
        boolean canWork = selection.size() == 1;
        for (Object o : ((IStructuredSelection) selection).toArray()) {
            if (canWork) {
                if (o instanceof RepositoryNode) {
                    RepositoryNode node = (RepositoryNode) o;
                    switch (node.getType()) {
                    case REPOSITORY_ELEMENT:
                        if (node.getObjectType() == ERepositoryObjectType.METADATA_FILE_RULES) {
                            canWork = true;
                        } else {
                            canWork = false;
                        }
                        break;
                    default:
                        canWork = false;
                        break;
                    }
                }
            }
        }
        setEnabled(canWork);
    }

    @Override
    public void run() {
        ISelection selection = getSelection();
        Object obj = ((IStructuredSelection) selection).getFirstElement();
        RepositoryNode node = (RepositoryNode) obj;

        IPath path = RepositoryNodeUtilities.getPath(node);

        String originalName = node.getObject().getLabel();

        PropertiesWizard wizard = new PropertiesWizard(node, path);
        WizardDialog dlg = new WizardDialog(Display.getCurrent().getActiveShell(), wizard);
        if (dlg.open() == Window.OK) {
            refresh(node);
            // refresh the corresponding editor's name
            IEditorPart part = getCorrespondingEditor(node);
            if (part != null && part instanceof IUIRefresher) {
                ((IUIRefresher) part).refreshName();
            }
            // else {
            // processRoutineRenameOperation(originalName, node, path);
            // }

            // refresh ...
            IViewPart jobSettingView = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(
                    IJobSettingsView.ID);
            if (jobSettingView != null && jobSettingView instanceof IJobSettingsView) {
                ((IJobSettingsView) jobSettingView).refreshCurrentViewTab();
            }
        }
    }

    protected IEditorPart getCorrespondingEditor(RepositoryNode node) {
        IEditorReference[] eidtors = getActivePage().getEditorReferences();

        for (int i = 0; i < eidtors.length; i++) {
            try {
                IEditorInput input = eidtors[i].getEditorInput();
                if (!(input instanceof RuleEditorInput)) {
                    continue;
                }

                RuleEditorInput ruleInput = (RuleEditorInput) input;
                if (ruleInput.getItem().equals(node.getObject().getProperty().getItem())) {

                    IPath path = ruleInput.getFile().getLocation();

                    return eidtors[i].getEditor(false);
                }
            } catch (PartInitException e) {
                continue;
            }
        }
        return null;
    }

}
