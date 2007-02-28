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
package org.talend.designer.dbmap.ui.visualmap.zone;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.ui.visualmap.zone.toolbar.ToolbarInputZone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: InputsZone.java 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class InputsZone extends Composite {

    private MapperManager mapperManager;

    private ToolbarInputZone toolbar;

    public InputsZone(Composite parent, int style, MapperManager mapperManager) {
        super(parent, style);
        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.marginBottom = 0;
        gridLayout.marginHeight = 0;
        gridLayout.marginLeft = 0;
        gridLayout.marginRight = 0;
        gridLayout.marginTop = 0;
        gridLayout.marginWidth = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;
        this.setLayout(gridLayout);
        // RowLayout layout = new RowLayout(SWT.VERTICAL);
        // this.setLayout(layout);

        this.mapperManager = mapperManager;
    }

    public void createHeaderZoneComponents() {
        toolbar = new ToolbarInputZone(this, SWT.BORDER, this.mapperManager);
        Composite toolBarComposite = toolbar.getComposite();
        toolBarComposite.setBackgroundMode(SWT.INHERIT_NONE);
        toolBarComposite.setBackground(toolBarComposite.getDisplay().getSystemColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
        GridData gridDataToolbar = new GridData(GridData.FILL_HORIZONTAL);
        toolBarComposite.setLayoutData(gridDataToolbar);
        gridDataToolbar.grabExcessHorizontalSpace = true;

    }

    public ToolbarInputZone getToolbar() {
        return this.toolbar;
    }

}
