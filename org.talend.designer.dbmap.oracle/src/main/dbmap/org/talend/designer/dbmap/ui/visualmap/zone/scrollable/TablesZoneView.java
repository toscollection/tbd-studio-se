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
import org.talend.designer.dbmap.ui.dnd.InsertionIndicator;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TablesZoneView.java 968 2006-12-12 10:59:26Z amaumont $
 * 
 */
public abstract class TablesZoneView extends Composite {

    protected static final int MARGIN_TOP_ZONE_WITHOUT_ACTION_BAR = 10;

    private InsertionIndicator insertionIndicator;

    private MapperManager mapperManager;

    public TablesZoneView(Composite parent, int style, MapperManager mapperManager) {
        super(parent, style);
        this.mapperManager = mapperManager;
        initLayout();
    }

    /**
     * DOC amaumont Comment method "getFormLayout".
     * 
     * @return
     */
    public abstract Layout initLayout();

    public InsertionIndicator getInsertionIndicator() {
        return this.insertionIndicator;
    }

    /**
     * Call this method when a form layout is defined for the <code>TablesZoneView</code>.
     */
    public void initInsertionIndicator() {
        this.insertionIndicator = new InsertionIndicator(this, mapperManager);
    }

    @Override
    public void layout() {
        // System.out.println("TablesZoneView layout " + toString() + (i++) );
        if (super.getLayout() == null) {
            initLayout();
        }
        super.layout();
    }

    @Override
    public FormLayout getLayout() {
        // System.out.println("TablesZoneView getLayout " + toString() + (j++) );
        FormLayout formLayout = (FormLayout) super.getLayout();
        if (formLayout == null) {
            formLayout = (FormLayout) initLayout();
        }
        return formLayout;
    }

    @Override
    public void setLayout(Layout layout) {
        // System.out.println("TablesZoneView getLayout " + toString() + (k++) );
        // TODO Auto-generated method stub
        super.setLayout(layout);
    }

}
