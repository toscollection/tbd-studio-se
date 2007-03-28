// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend � www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License.
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
package org.talend.designer.codegen.javamodule;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.BusinessException;
import org.talend.commons.utils.generation.JavaUtils;
import org.talend.commons.utils.workbench.resources.ResourceUtils;
import org.talend.designer.codegen.javamodule.i18n.Messages;
import org.talend.designer.codegen.javamodule.model.ModulesNeededProvider;
import org.talend.designer.codegen.perlmodule.ModuleNeeded;
import org.talend.designer.codegen.perlmodule.ModuleNeeded.ModuleStatus;
import org.talend.repository.model.RepositoryConstants;
import org.talend.repository.model.ResourceModelUtils;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class JavaModuleService implements IJavaModuleService {

    public static final Bundle JAVA_MODULE_PLUGIN = Platform.getBundle(JavaUtils.JAVAMODULE_PLUGIN_ID);

    public List<URL> getBuiltInRoutines() {
        List<URL> toReturn = new ArrayList<URL>();

        Enumeration entryPaths = JAVA_MODULE_PLUGIN.getEntryPaths(JavaUtils.JAVA_DIRECTORY+"/"+JavaUtils.JAVA_ROUTINES_DIRECTORY+"/system/"); //$NON-NLS-1$ //$NON-NLS-2$
        for (Enumeration enumer = entryPaths; enumer.hasMoreElements();) {
            String routine = (String) enumer.nextElement();
            if (routine.endsWith(JavaUtils.JAVA_EXTENSION)) {
                URL url = JAVA_MODULE_PLUGIN.getEntry(routine);
                toReturn.add(url);
            }
        }
        return toReturn;
    }

    public URL getRoutineTemplate() {
        return JAVA_MODULE_PLUGIN.getEntry(JavaUtils.JAVA_DIRECTORY+"/"+JavaUtils.JAVA_ROUTINES_DIRECTORY+"/Template.java"); //$NON-NLS-1$ //$NON-NLS-2$
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.codegen.javamodule.IJavaModuleService#getJavaModule()
     */
    public List<URL> getModule() throws IOException {
        List<URL> list = new ArrayList<URL>();
        URL url = JAVA_MODULE_PLUGIN.getEntry(JavaUtils.JAVA_DIRECTORY+"/talend"); //$NON-NLS-1$
        //url = FileLocator.toFileURL(url);
        url = FileLocator.resolve(url);
        list.add(url);
        return list;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.codegen.javamodule.IJavaModuleService#getModulesNeeded(java.lang.String)
     */
    public List<ModuleNeeded> getModulesNeeded(String componentName) {
        return ModulesNeededProvider.getModulesNeeded(componentName);
    }

    public ModuleStatus getModuleStatus(String moduleName) throws BusinessException {
        return ModulesNeededProvider.getModuleStatus(moduleName);
    }
}
