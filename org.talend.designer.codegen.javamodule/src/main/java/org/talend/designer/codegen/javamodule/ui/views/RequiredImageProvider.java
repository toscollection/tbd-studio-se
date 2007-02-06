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
package org.talend.designer.codegen.javamodule.ui.views;

import org.eclipse.swt.graphics.Image;
import org.talend.commons.ui.image.ImageProvider;
import org.talend.commons.ui.swt.tableviewer.behavior.IColumnImageProvider;
import org.talend.core.ui.images.ECoreImage;
import org.talend.designer.codegen.perlmodule.ModuleNeeded;

/**
 * DOC smallet class global comment. Detailled comment <br/>
 * 
 * $Id: RequiredImageProvider.java 1754 2007-02-01 09:46:26Z plegall $
 * 
 */
public class RequiredImageProvider implements IColumnImageProvider {

    public Image getImage(Object bean) {
        ModuleNeeded componentImportNeeds = (ModuleNeeded) bean;

        if (componentImportNeeds.isRequired()) {
            return ImageProvider.getImage(ECoreImage.MODULE_REQUIRED_ICON);
        } else {
            return ImageProvider.getImage(ECoreImage.MODULE_NOTREQUIRED_ICON);
        }

    }

}
