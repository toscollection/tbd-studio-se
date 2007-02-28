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
package org.talend.designer.dbmap.ui.visualmap.zone.scrollable;

import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;
import org.talend.designer.dbmap.managers.MapperManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: InputTablesZoneView.java 1443 2007-01-12 12:24:00Z amaumont $
 * 
 */
public class InputTablesZoneView extends TablesZoneView {

    private FormLayout formLayout;

    public InputTablesZoneView(Composite parent, int style, MapperManager mapperManager) {
        super(parent, style, mapperManager);
    }

    @Override
    public Layout initLayout() {
        formLayout = new FormLayout();
        formLayout.marginLeft = 30;
        formLayout.marginRight = 40;
        formLayout.marginTop = MARGIN_TOP_ZONE_WITHOUT_ACTION_BAR;
        formLayout.marginBottom = 10;
        formLayout.marginWidth = 0;
        formLayout.marginHeight = 0;
        formLayout.spacing = 10;
        setLayout(formLayout);
        return formLayout;
    }

    public void setMarginLeft(int marginLeft) {
        formLayout.marginLeft = marginLeft;
        this.layout();
    }

    public int getMarginLeft() {
        return formLayout.marginLeft;
    }

}
