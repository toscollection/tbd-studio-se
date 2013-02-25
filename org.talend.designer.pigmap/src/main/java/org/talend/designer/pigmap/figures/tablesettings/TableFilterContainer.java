// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.figures.tablesettings;

import org.eclipse.gef.commands.Command;
import org.eclipse.swt.widgets.Composite;
import org.talend.designer.gefabstractmap.figures.manager.TableManager;
import org.talend.designer.gefabstractmap.figures.treesettings.FilterContainer;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class TableFilterContainer extends FilterContainer {

    /**
     * 
     * DOC hcyi TableFilterContainer constructor comment.
     * 
     * @param tableManager
     * @param parent
     */
    public TableFilterContainer(TableManager tableManager, Composite parent) {
        super(tableManager, parent);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.newabstractmap.figures.treesettings.FilterContainer#getFilterChangeCommand()
     */
    @Override
    protected Command getFilterChangeCommand(Object model, String newValue) {
        //
        return null;
    }

}
