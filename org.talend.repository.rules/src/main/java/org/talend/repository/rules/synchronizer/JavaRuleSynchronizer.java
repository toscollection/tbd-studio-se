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
package org.talend.repository.rules.synchronizer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.PersistenceException;
import org.talend.commons.exception.SystemException;
import org.talend.commons.utils.generation.JavaUtils;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.properties.Project;
import org.talend.core.model.properties.RulesItem;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryObject;
import org.talend.designer.codegen.CodeGeneratorActivator;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.repository.ProjectManager;
import org.talend.repository.model.IProxyRepositoryFactory;

/**
 * DOC hyWang class global comment. Detailled comment.
 * 
 * This class is used to synchronize the RulesItem and the .drl file.
 */
public class JavaRuleSynchronizer implements ITalendRuleSynchronizer {

    public IPath getPath() {
        IPath path = new Path(getRuleFolder());
        return path;
    }

    public String getRuleFolder() {
        String rulePath = JavaUtils.JAVA_SRC_DIRECTORY + "/" //$NON-NLS-1$
                + JavaUtils.JAVA_RULES_DIRECTORY;
        return rulePath;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer#getRuleFile(org.talend.core.model.properties
     * .RulesItem)
     */
    public IFile getRuleFile(RulesItem rulesItem, String extension) throws SystemException {
        try {
            IRunProcessService service = CodeGeneratorActivator.getDefault().getRunProcessService();
            IProject javaProject = service.getProject(ECodeLanguage.JAVA);
            ProjectManager projectManager = ProjectManager.getInstance();
            org.talend.core.model.properties.Project project = projectManager.getProject(rulesItem);
            initRuleFolder(javaProject, project);
            String ruleFolder = getRuleFolder();
            IFile file = javaProject.getFile(ruleFolder + "/" //$NON-NLS-1$
                    + rulesItem.getProperty().getLabel() + extension);
            return file;
        } catch (CoreException e) {
            throw new SystemException(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer#initRoutineFolder(org.eclipse.core.resources
     * .IProject, org.talend.core.model.properties.Project)
     */
    public void initRuleFolder(IProject javaProject, Project project) throws CoreException {
        IFolder rep = javaProject.getFolder(getRuleFolder());
        if (!rep.exists()) {
            rep.create(true, true, null);
        }

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer#syncRule(org.talend.core.model.properties.RulesItem
     * )
     */
    public void syncRule(RulesItem rulesItem) throws SystemException {
        FileOutputStream fos = null;
        try {
            IFile file = getRuleFile(rulesItem, rulesItem.getExtension());

            String routineContent = new String(rulesItem.getContent().getInnerContent());
            // String label = rulesItem.getProperty().getLabel();
            // if (!label.equals(ITalendSynchronizer.TEMPLATE)) {
            // routineContent = routineContent.replaceAll(ITalendSynchronizer.TEMPLATE, label);
            // routineContent = renameRoutinePackage(routineItem,
            // routineContent);
            File f = file.getLocation().toFile();
            fos = new FileOutputStream(f);
            fos.write(routineContent.getBytes());
            fos.close();
            // }
            file.refreshLocal(1, null);
        } catch (CoreException e) {
            throw new SystemException(e);
        } catch (IOException e) {
            throw new SystemException(e);
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                // ignore me even if i'm null
            }
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer#syncAllRules()
     */
    public void syncAllRules() {
        try {
            List<IRepositoryObject> mainRules = getRoutines();
            for (IRepositoryObject rule : mainRules) {
                RulesItem ruleItem = (RulesItem) rule.getProperty().getItem();
                syncRule(ruleItem);
            }
        } catch (SystemException e) {
            ExceptionHandler.process(e);
        }
    }

    protected List<IRepositoryObject> getRoutines() throws SystemException {
        List<IRepositoryObject> routineList = getMainProjectRule();
        // list.addAll(getReferencedProjectRoutine());

        // remove routine with same name in reference project
        // Set<String> routineNames = new HashSet<String>();
        // for (IRepositoryObject obj : routineList) {
        // routineNames.add(obj.getProperty().getLabel());
        // }
        //
        // List<IRepositoryObject> refRoutines = getReferencedProjectRoutine();
        // for (IRepositoryObject obj : refRoutines) {
        // String name = obj.getProperty().getLabel();
        // // it does not have a routine with same name
        // if (!routineNames.contains(name)) {
        // routineNames.add(name);
        // routineList.add(obj);
        // }
        // }
        return routineList;
    }

    private List<IRepositoryObject> getMainProjectRule() throws SystemException {
        IProxyRepositoryFactory repositoryFactory = CodeGeneratorActivator.getDefault().getRepositoryService()
                .getProxyRepositoryFactory();

        List<IRepositoryObject> rules;
        try {
            rules = repositoryFactory.getAll(ERepositoryObjectType.METADATA_FILE_RULES);
        } catch (PersistenceException e) {
            throw new SystemException(e);
        }
        return rules;
    }

}
