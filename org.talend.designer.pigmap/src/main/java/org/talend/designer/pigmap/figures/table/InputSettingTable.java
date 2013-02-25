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
package org.talend.designer.pigmap.figures.table;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.CompoundBorder;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.PositionConstants;
import org.talend.designer.gefabstractmap.figures.ComboCellLabel;
import org.talend.designer.gefabstractmap.figures.borders.ColumnBorder;
import org.talend.designer.gefabstractmap.figures.borders.RowBorder;
import org.talend.designer.gefabstractmap.figures.layout.RowLayout;
import org.talend.designer.gefabstractmap.figures.table.AbstractTable;
import org.talend.designer.gefabstractmap.figures.table.ColumnKeyConstant;
import org.talend.designer.gefabstractmap.figures.table.ColumnSash;
import org.talend.designer.gefabstractmap.figures.table.TableColumn;
import org.talend.designer.gefabstractmap.part.directedit.DirectEditType;
import org.talend.designer.gefabstractmap.resource.ColorInfo;
import org.talend.designer.gefabstractmap.resource.ColorProviderMapper;
import org.talend.designer.pigmap.figures.tablesettings.IUILookupMode;
import org.talend.designer.pigmap.figures.tablesettings.IUIMatchingMode;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_LOOKUP_MODE;
import org.talend.designer.pigmap.figures.tablesettings.PIG_MAP_MATCHING_MODE;
import org.talend.designer.pigmap.figures.tablesettings.TableSettingsConstant;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.PigmapPackage;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class InputSettingTable extends AbstractTable {

    private InputTable inputTable;

    private Figure lookupModelRow;

    private ComboCellLabel lookupModel;

    private Figure matchModelRow;

    private ComboCellLabel matchModel;

    private Figure joinModelRow;

    private ComboCellLabel joinModel;

    private Figure persistentModelRow;

    private ComboCellLabel persistentModel;

    public InputSettingTable(PigMapTableManager tableModelManager) {
        super(tableModelManager);
        inputTable = (InputTable) tableModelManager.getModel();
        createColumns();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.gefabstractmap.figures.table.AbstractTable#createColumns()
     */
    @Override
    protected void createColumns() {
        TableColumn column = new TableColumn(ColumnKeyConstant.TREE_SETTING_PROPERTY);
        column.setText("Property");
        addColumn(column);

        ColumnSash sash = new ColumnSash(this);
        sash.setLeftColumn(column);
        addSeparator(sash);

        column = new TableColumn(ColumnKeyConstant.TREE_SETTING_VALUE);
        column.setText("Value");
        sash.setRightColumn(column);
        addColumn(column);

        Figure container = getTableItemContainer();

        lookupModelRow = new Figure();
        lookupModelRow.setLayoutManager(new RowLayout());
        Label label = new Label();
        label.setText("Lookup Model");
        label.setLabelAlignment(PositionConstants.LEFT);
        CompoundBorder compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        lookupModelRow.add(label);
        lookupModel = new ComboCellLabel();
        lookupModel.setDirectEditType(DirectEditType.LOOKUP_MODEL);
        lookupModel.setText(getLookupDisplayName(inputTable.getLookupMode()));
        lookupModel.setLabelAlignment(PositionConstants.LEFT);
        lookupModel.setBorder(new RowBorder(2, 5, 2, -1));
        lookupModelRow.add(lookupModel);
        container.add(lookupModelRow);

        matchModelRow = new Figure();
        matchModelRow.setLayoutManager(new RowLayout());
        label = new Label();
        label.setText("Match Model");
        label.setLabelAlignment(PositionConstants.LEFT);
        compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        matchModelRow.add(label);
        matchModel = new ComboCellLabel();
        matchModel.setDirectEditType(DirectEditType.MATCH_MODEL);
        matchModel.setText(getMatchModelDisplayName(inputTable.getMatchingMode()));
        matchModel.setLabelAlignment(PositionConstants.LEFT);
        matchModel.setBorder(new RowBorder(2, 5, 2, -1));
        matchModelRow.add(matchModel);
        container.add(matchModelRow);

        joinModelRow = new Figure();
        joinModelRow.setLayoutManager(new RowLayout());
        label = new Label();
        label.setText("Join Model");
        label.setLabelAlignment(PositionConstants.LEFT);
        compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        joinModelRow.add(label);
        joinModel = new ComboCellLabel();
        joinModel.setDirectEditType(DirectEditType.JOIN_MODEL);

        String join = "";
        if (inputTable.isInnerJoin()) {
            join = TableSettingsConstant.INNER_JOIN;
        } else {
            join = TableSettingsConstant.LEFT_OUTER_JOIN;
        }

        joinModel.setText(join);
        joinModel.setLabelAlignment(PositionConstants.LEFT);
        joinModel.setBorder(new RowBorder(2, 5, 2, -1));
        joinModelRow.add(joinModel);
        container.add(joinModelRow);

        persistentModelRow = new Figure();
        persistentModelRow.setLayoutManager(new RowLayout());
        label = new Label();
        label.setText("Store Temp Data");
        label.setLabelAlignment(PositionConstants.LEFT);
        compoundBorder = new CompoundBorder(new ColumnBorder(), new RowBorder(2, 5, 2, -1));
        label.setBorder(compoundBorder);
        persistentModelRow.add(label);
        persistentModel = new ComboCellLabel();
        persistentModel.setDirectEditType(DirectEditType.PERSISTENT_MODEL);
        persistentModel.setText(String.valueOf(inputTable.isPersistent()));
        persistentModel.setLabelAlignment(PositionConstants.LEFT);
        persistentModel.setBorder(new RowBorder(2, 5, 2, -1));
        persistentModelRow.add(persistentModel);
        container.add(persistentModelRow);
        container.setOpaque(true);
        container.setBackgroundColor(ColorConstants.white);

        container.addMouseListener(new MouseListener() {

            Figure selectedFigure = null;

            @Override
            public void mousePressed(MouseEvent me) {
                boolean lookup = lookupModelRow.containsPoint(me.x, me.y);
                if (lookup) {
                    if (selectedFigure != lookupModelRow) {
                        lookupModelRow.setOpaque(true);
                        lookupModelRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        matchModelRow.setOpaque(false);
                        joinModelRow.setOpaque(false);
                        persistentModelRow.setOpaque(false);
                    }
                    return;
                }
                boolean matchModel = matchModelRow.containsPoint(me.x, me.y);
                if (matchModel) {
                    if (selectedFigure != matchModelRow) {
                        matchModelRow.setOpaque(true);
                        matchModelRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        lookupModelRow.setOpaque(false);
                        joinModelRow.setOpaque(false);
                        persistentModelRow.setOpaque(false);
                    }
                    return;
                }
                boolean joinModel = joinModelRow.containsPoint(me.x, me.y);
                if (joinModel) {
                    if (selectedFigure != joinModelRow) {
                        joinModelRow.setOpaque(true);
                        joinModelRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        lookupModelRow.setOpaque(false);
                        matchModelRow.setOpaque(false);
                        persistentModelRow.setOpaque(false);
                    }
                    return;
                }
                boolean persistentModel = persistentModelRow.containsPoint(me.x, me.y);
                if (persistentModel) {
                    if (selectedFigure != persistentModelRow) {
                        persistentModelRow.setOpaque(true);
                        persistentModelRow.setBackgroundColor(ColorProviderMapper.getColor(ColorInfo.COLOR_COLUMN_TREE_SETTING));
                        lookupModelRow.setOpaque(false);
                        matchModelRow.setOpaque(false);
                        joinModelRow.setOpaque(false);
                    }
                }
            }

            @Override
            public void mouseReleased(MouseEvent me) {
            }

            @Override
            public void mouseDoubleClicked(MouseEvent me) {
            }

        });
    }

    private String getLookupDisplayName(String lookupModel) {
        IUILookupMode[] availableJoins = { PIG_MAP_LOOKUP_MODE.LOAD_ONCE, PIG_MAP_LOOKUP_MODE.RELOAD,
                PIG_MAP_LOOKUP_MODE.CACHE_OR_RELOAD };
        for (IUILookupMode model : availableJoins) {
            if (model.toString().equals(lookupModel)) {
                return model.getLabel();
            }
        }
        return lookupModel;
    }

    private String getMatchModelDisplayName(String matcheModel) {
        IUIMatchingMode[] allMatchingModel = PIG_MAP_MATCHING_MODE.values();
        for (IUIMatchingMode model : allMatchingModel) {
            if (model.toString().equals(matcheModel)) {
                return model.getLabel();
            }
        }
        return matcheModel;
    }

    public void update(int type) {
        switch (type) {
        case PigmapPackage.INPUT_TABLE__LOOKUP_MODE:
            lookupModel.setText(getLookupDisplayName(inputTable.getLookupMode()));
            break;
        case PigmapPackage.INPUT_TABLE__MATCHING_MODE:
            matchModel.setText(getMatchModelDisplayName(inputTable.getMatchingMode()));
            break;
        case PigmapPackage.INPUT_TABLE__INNER_JOIN:
            joinModel.setText(String.valueOf(inputTable.isInnerJoin()));
            break;
        case PigmapPackage.INPUT_TABLE__PERSISTENT:
            persistentModel.setText(String.valueOf(inputTable.isPersistent()));
        default:
            break;
        }
    }

    public void deselectTableSettingRows() {
        if (lookupModelRow.isOpaque()) {
            lookupModelRow.setOpaque(false);
        }
        if (matchModelRow.isOpaque()) {
            matchModelRow.setOpaque(false);
        }
        if (joinModelRow.isOpaque()) {
            joinModelRow.setOpaque(false);
        }
        if (persistentModelRow.isOpaque()) {
            persistentModelRow.setOpaque(false);
        }
    }

}
