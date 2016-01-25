// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.pigmap.commands;

import org.eclipse.gef.commands.Command;
import org.talend.designer.gefabstractmap.dnd.TransferdType;
import org.talend.designer.gefabstractmap.dnd.TransferedObject;
import org.talend.designer.gefabstractmap.part.MapperTablePart;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractInOutTable;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.model.emf.pigmap.FilterConnection;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigMapData;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapFactory;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapTableNodePart;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class UpdateFilterExpressionCommand extends Command {

    TransferedObject objects;

    protected MapperTablePart targetEditPart;

    private PigMapData pigMapData;

    public UpdateFilterExpressionCommand(TransferedObject objects, MapperTablePart targetEditPart, PigMapData pigMapData) {
        this.objects = objects;
        this.targetEditPart = targetEditPart;
        this.pigMapData = pigMapData;
    }

    @Override
    public void execute() {
        AbstractInOutTable targetTable = (AbstractInOutTable) targetEditPart.getModel();

        String expression = targetTable.getExpressionFilter();
        if (objects.getToTransfer() != null) {
            for (Object obj : objects.getToTransfer()) {
                AbstractNode sourceNode = null;
                // INPUT == FILTER
                if (objects.getType() == TransferdType.INPUT) {
                    PigMapTableNodePart part = (PigMapTableNodePart) obj;
                    sourceNode = (TableNode) part.getModel();
                    String tableName = "";
                    if (sourceNode != null && sourceNode.eContainer() instanceof InputTable) {
                        InputTable table = (InputTable) sourceNode.eContainer();
                        tableName = table.getName();
                    }
                    if (expression == null || expression.equals("")) {
                        expression = tableName + "." + sourceNode.getName();
                    } else {
                        expression = expression + " " + tableName + "." + sourceNode.getName();
                    }
                }
                if (sourceNode != null) {
                    targetTable.setExpressionFilter(expression);
                    FilterConnection connection = PigmapFactory.eINSTANCE.createFilterConnection();
                    connection.setSource(sourceNode);
                    connection.setTarget(targetTable);
                    targetTable.getFilterIncomingConnections().add(connection);
                    sourceNode.getFilterOutGoingConnections().add(connection);
                    pigMapData.getConnections().add(connection);
                }
            }
        }
    }
}
