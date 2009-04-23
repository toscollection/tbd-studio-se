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

import org.eclipse.core.resources.IFile;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.talend.commons.exception.MessageBoxExceptionHandler;
import org.talend.commons.exception.SystemException;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.core.CorePlugin;
import org.talend.core.model.properties.RulesItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.ui.images.ECoreImage;
import org.talend.repository.model.IProxyRepositoryFactory;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.RepositoryNode.ENodeType;
import org.talend.repository.rules.editor.RuleEditor;
import org.talend.repository.rules.editor.RuleEditorInput;
import org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer;
import org.talend.repository.rules.synchronizer.JavaRuleSynchronizer;
import org.talend.repository.ui.actions.AContextualAction;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public class EditRuleAction extends AContextualAction {

    private static String EDIT_LABEL = "Edit Rule"; //$NON-NLS-1$

    ImageDescriptor defaultImage = ImageProvider.getImageDesc(ECoreImage.METADATA_RULES_ICON);

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.commons.ui.swt.actions.ITreeContextualAction#init(org.eclipse.jface.viewers.TreeViewer,
     * org.eclipse.jface.viewers.IStructuredSelection)
     */
    public void init(TreeViewer viewer, IStructuredSelection selection) {
        RepositoryNode node = (RepositoryNode) selection.getFirstElement();
        if (node != null && node.getType().equals(ENodeType.REPOSITORY_ELEMENT)
                && node.getObject().getType() == ERepositoryObjectType.METADATA_FILE_RULES) {
            this.setText(EDIT_LABEL);
            this.setImageDescriptor(defaultImage);
            setEnabled(true);
        } else
            setEnabled(false);
    }

    @Override
    protected void doRun() {
        RepositoryNode node = (RepositoryNode) ((IStructuredSelection) getSelection()).getFirstElement();
        RulesItem rulesItem = (RulesItem) node.getObject().getProperty().getItem();
        IProxyRepositoryFactory repositoryFactory = CorePlugin.getDefault().getRepositoryService().getProxyRepositoryFactory();
        try {
            openRulesEditor(rulesItem);
            repositoryFactory.lock(rulesItem);
            refresh(node);
        } catch (PartInitException e) {
            MessageBoxExceptionHandler.process(e);
        } catch (SystemException e) {
            MessageBoxExceptionHandler.process(e);
        }
    }

    @Override
    public Class getClassForDoubleClick() {
        return RulesItem.class;
    }

    private IEditorPart openRulesEditor(RulesItem rulesItem) throws SystemException, PartInitException {
        IWorkbenchPage page = getActivePage();
        // String ruleEditorID = "org.talend.repository.rules.reuseDRLEditor";

        if (rulesItem == null) {
            return null;
        }
        ITalendRuleSynchronizer ruleSynchronizer = new JavaRuleSynchronizer();
        // IFile file = ruleSynchronizer.getRuleFile(rulesItem);
        //
        boolean found = false;
        IEditorPart talendEditor = null;
        // for (IEditorReference reference : editorParts) {
        // IEditorPart editor = reference.getEditor(false);
        // if (talendEditorID.equals(editor.getSite().getId())) {
        // found = true;
        // talendEditor = editor;
        // break;
        // }
        // }
        if (!found) {
            ruleSynchronizer.syncRule(rulesItem);
            IFile file = ruleSynchronizer.getRuleFile(rulesItem, rulesItem.getExtension());
            IEditorInput input = new RuleEditorInput(file, rulesItem);
            IEditorPart findEditor = page.findEditor(input);
            if (findEditor == null) {
                findEditor = page.openEditor(input, RuleEditor.ID);
            }
        }
        return talendEditor;
    }

}
