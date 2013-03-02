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

import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.gef.commands.Command;
import org.eclipse.jface.dialogs.TrayDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;
import org.talend.commons.ui.expressionbuilder.IExpressionBuilderDialogController;
import org.talend.core.GlobalServiceRegister;
import org.talend.core.IService;
import org.talend.designer.gefabstractmap.figures.manager.TableManager;
import org.talend.designer.gefabstractmap.figures.treesettings.FilterContainer;
import org.talend.expressionbuilder.IExpressionBuilderDialogService;

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

    @Override
    protected void addButtonListener() {
        IService expressionBuilderDialogService = GlobalServiceRegister.getDefault().getService(
                IExpressionBuilderDialogService.class);

        final IExpressionBuilderDialogController dialog = ((IExpressionBuilderDialogService) expressionBuilderDialogService)
                .getExpressionBuilderInstance(parent, null, null, true);

        button.addMouseListener(new MouseListener() {

            @Override
            public void mousePressed(MouseEvent me) {
                if (dialog instanceof TrayDialog) {
                    TrayDialog parentDialog = (TrayDialog) dialog;
                    dialog.setDefaultExpression(tableManager.getExpressionFilter());
                    if (Window.OK == parentDialog.open()) {
                        String expressionForTable = dialog.getExpressionForTable();
                        tableManager.setExpressionFilter(expressionForTable);
                        tableManager.getEditPart().getViewer().getEditDomain().getCommandStack()
                                .execute(getFilterChangeCommand(tableManager.getModel(), expressionForTable));
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                // TODO Auto-generated method stub
            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
                // TODO Auto-generated method stub
            }
        });
    }
}
