// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend – www.talend.com
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
package org.talend.designer.codegen.perlmodule;

import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.osgi.framework.Bundle;
import org.talend.commons.exception.BusinessException;
import org.talend.designer.codegen.perlmodule.ModuleNeeded.ModuleStatus;
import org.talend.designer.codegen.perlmodule.model.ModulesNeededProvider;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: talend.epf 1 2006-09-29 17:06:40 +0000 (ven., 29 sept. 2006) nrousseau $
 * 
 */
public class PerlModuleService implements IPerlModuleService {

    public static final String PERLMODULE_PLUGIN_ID = "org.talend.designer.codegen.perlmodule";

    public static final Bundle PERL_MODULE_PLUGIN = Platform.getBundle(PERLMODULE_PLUGIN_ID);

    public List<URL> getBuiltInRoutines() {
        List<URL> toReturn = new ArrayList<URL>();

        Enumeration entryPaths = PERL_MODULE_PLUGIN.getEntryPaths("perl/routines/system/");
        for (Enumeration enumer = entryPaths; enumer.hasMoreElements();) {
            String routine = (String) enumer.nextElement();
            if (routine.endsWith(".pm")) {
                URL url = PERL_MODULE_PLUGIN.getEntry(routine);
                toReturn.add(url);
            }
        }
        return toReturn;
    }

    public URL getRoutineTemplate() {
        return PERL_MODULE_PLUGIN.getEntry("perl/routines/Template.pm");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.codegen.perlmodule.IPerlModuleService#getModulesNeeded(java.lang.String)
     */
    public List<ModuleNeeded> getModulesNeeded(String componentName) {
        return ModulesNeededProvider.getModulesNeeded(componentName);
    }

    public ModuleStatus getModuleStatus(String moduleName) throws BusinessException {
        return ModulesNeededProvider.getModuleStatus(moduleName);
    }
}
