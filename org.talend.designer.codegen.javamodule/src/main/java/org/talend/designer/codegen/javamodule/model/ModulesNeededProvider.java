// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.designer.codegen.javamodule.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Level;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.talend.commons.exception.BusinessException;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.exception.MessageBoxExceptionHandler;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.model.components.IComponent;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.designer.codegen.javamodule.JavaModuleService;
import org.talend.designer.codegen.javamodule.i18n.Messages;
import org.talend.designer.codegen.perlmodule.ModuleNeeded;
import org.talend.designer.codegen.perlmodule.ModuleNeeded.ModuleStatus;
import org.talend.designer.runprocess.IRunProcessService;
import org.talend.designer.runprocess.ProcessorException;
import org.talend.repository.model.ComponentsFactoryProvider;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: ModulesNeededProvider.java 1754 2007-02-01 09:46:26Z plegall $
 * 
 */
public class ModulesNeededProvider {

    private static List<ModuleNeeded> componentImportNeedsList;

    private static final String CHECK_PERL_MODULE_RELATIVE_PATH = "java/check_modules.pl"; //$NON-NLS-1$

    private static final String MODULE_PARAM_KEY = "--module="; //$NON-NLS-1$

    private static final String RESULT_SEPARATOR = " => "; //$NON-NLS-1$

    private static final String RESULT_KEY_KO = Messages.getString("ModulesNeededProvider.4"); //$NON-NLS-1$

    private static final String RESULT_KEY_OK = Messages.getString("ModulesNeededProvider.3"); //$NON-NLS-1$

    private static List<ModuleNeeded> getModulesNeededForComponents() {
        List<ModuleNeeded> importNeedsList = new ArrayList<ModuleNeeded>();
        IComponentsFactory compFac = ComponentsFactoryProvider.getInstance();
        List<IComponent> componentList = compFac.getComponents();
        for (IComponent component : componentList) {
            importNeedsList.addAll(component.getModulesNeeded());
        }
        return importNeedsList;
    }

    private static List<ModuleNeeded> getModulesNeededForApplication() {
        List<ModuleNeeded> importNeedsList = new ArrayList<ModuleNeeded>();

        // importNeedsList.add(new ModuleNeeded(null, "tagada", "--", false));

        return importNeedsList;
    }

    public static List<ModuleNeeded> getModulesNeeded() {
        if (componentImportNeedsList == null) {
            componentImportNeedsList = new ArrayList<ModuleNeeded>();
            componentImportNeedsList.addAll(getModulesNeededForApplication());
            componentImportNeedsList.addAll(getModulesNeededForComponents());
            check();
        }
        return componentImportNeedsList;
    }

    public static List<ModuleNeeded> getModulesNeeded(String componentName) {
        List<ModuleNeeded> toReturn = new ArrayList<ModuleNeeded>();
        for (ModuleNeeded current : getModulesNeeded()) {
            if (current.getComponentName().equals(componentName)) {
                toReturn.add(current);
            }
        }

        return toReturn;
    }

    public static ModuleStatus getModuleStatus(String moduleName) throws BusinessException {
        for (ModuleNeeded current : getModulesNeeded()) {
            if (current.getModuleName().equals(moduleName)) {
                return current.getStatus();
            }
        }
        throw new BusinessException(Messages.getString("ModulesNeededProvider.ModuleNotFound", moduleName)); //$NON-NLS-1$
    }

    public static void check() {
        if (getModulesNeeded().isEmpty()) {
            return;
        }

        // This map contains java module name as keys and list of object using it as values :
        Map<String, List<ModuleNeeded>> componentsByModules = new HashMap<String, List<ModuleNeeded>>();

        String[] params = new String[] {};
        for (ModuleNeeded current : getModulesNeeded()) {
            String moduleName = current.getModuleName();
            List<ModuleNeeded> listForThisModule = componentsByModules.get(moduleName);
            if (listForThisModule == null) {
                // We have a new java module to check :
                listForThisModule = new ArrayList<ModuleNeeded>();
                // Add it in the map :
                componentsByModules.put(moduleName, listForThisModule);
                // And in the params java command line :
                params = (String[]) ArrayUtils.add(params, MODULE_PARAM_KEY + moduleName);
            }
            // Add this import in the java module list :
            listForThisModule.add(current);

            // Set the status to unknow as after treatment, modules not in java response are unknown
            current.setStatus(ModuleStatus.UNKNOWN);
        }

        try {
            String checkJavaModuleAbsolutePath = FileLocator.toFileURL(
                    JavaModuleService.JAVA_MODULE_PLUGIN.getEntry(CHECK_PERL_MODULE_RELATIVE_PATH)).getPath();

            StringBuffer out = new StringBuffer();
            StringBuffer err = new StringBuffer();

            IRunProcessService service = (IRunProcessService) GlobalServiceRegister.getDefault().getService(
                    IRunProcessService.class);
            service.perlExec(out, err, new Path(checkJavaModuleAbsolutePath), null, Level.DEBUG, "", "", "", -1, -1, //$NON-NLS-1$
                    params);

            analyzeResponse(out, componentsByModules);

            if (err.length() > 0) {
                throw new ProcessorException(err.toString());
            }

        } catch (IOException e) {
            ExceptionHandler.process(e);
        } catch (ProcessorException e) {
            MessageBoxExceptionHandler.process(e);
        }

    }

    /**
     * DOC smallet Comment method "analyzeResponse".
     * 
     * @param out
     */
    private static void analyzeResponse(StringBuffer buff, Map<String, List<ModuleNeeded>> componentsByModules) {

        String[] lines = buff.toString().split("\n"); //$NON-NLS-1$
        for (String line : lines) {
            if (line != null && line.length() > 0) {
                // Treat a java response line :
                String[] elts = line.split(RESULT_SEPARATOR);

                List<ModuleNeeded> componentsToTreat = componentsByModules.get(elts[0]);

                if (componentsToTreat != null) {
                    // Define status regarding the java response :
                    ModuleStatus status = ModuleStatus.UNKNOWN;
                    if (elts[1].startsWith(RESULT_KEY_OK)) {
                        status = ModuleStatus.INSTALLED;
                    } else if (elts[1].startsWith(RESULT_KEY_KO)) {
                        status = ModuleStatus.NOT_INSTALLED;
                    }

                    // Step on objects using this module and set their status :
                    for (ModuleNeeded current : componentsToTreat) {
                        current.setStatus(status);
                    }
                }
            }

        }
    }
}
