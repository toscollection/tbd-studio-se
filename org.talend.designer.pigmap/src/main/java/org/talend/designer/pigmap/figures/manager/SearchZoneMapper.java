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
package org.talend.designer.pigmap.figures.manager;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.util.EList;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.ui.dialogs.SearchPattern;
import org.talend.designer.gefabstractmap.figures.table.entity.TableEntityFigure;
import org.talend.designer.gefabstractmap.part.TableEntityPart;
import org.talend.designer.gefabstractmap.resource.EntryState;
import org.talend.designer.pigmap.figures.InputTableFigure;
import org.talend.designer.pigmap.figures.OutputTableFigure;
import org.talend.designer.pigmap.figures.tablenode.PigMapTableNodeFigure;
import org.talend.designer.pigmap.figures.tablesettings.TableFilterContainer;
import org.talend.designer.pigmap.model.emf.pigmap.InputTable;
import org.talend.designer.pigmap.model.emf.pigmap.OutputTable;
import org.talend.designer.pigmap.model.emf.pigmap.TableNode;
import org.talend.designer.pigmap.parts.PigMapInputTablePart;
import org.talend.designer.pigmap.parts.PigMapOutputTablePart;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * DOC hcyi class global comment. Detailled comment
 */
public class SearchZoneMapper {

    private MapperManager mapperManager;

    private boolean isHightlightAll = false;

    public SearchZoneMapper(MapperManager mapperManager) {
        this.mapperManager = mapperManager;
    }

    public void search(Map<Integer, Figure> searchMaps, String searchValue) {
        if (searchValue.equals("") || searchValue == null) {
            return;
        }
        // SearchPattern
        SearchPattern matcher = new SearchPattern();
        matcher.setPattern(searchValue);

        List<InputTable> inputTables = mapperManager.getExternalData().getInputTables();
        List<OutputTable> outputTables = mapperManager.getExternalData().getOutputTables();

        int index = -1;

        // for the Lookup InputTables
        for (InputTable inputTable : inputTables) {
            // ExpressionFilter
            if (inputTable.getExpressionFilter() != null && matcher.matches(inputTable.getExpressionFilter())) {
                EList<Adapter> adapter = inputTable.eAdapters();
                if (adapter.size() > 0) {
                    if (adapter.get(0) instanceof PigMapInputTablePart) {
                        PigMapInputTablePart inputTablePart = (PigMapInputTablePart) adapter.get(0);
                        if (inputTablePart != null && inputTablePart.getFigure() != null
                                && inputTablePart.getFigure() instanceof InputTableFigure) {
                            InputTableFigure inputTableFigure = (InputTableFigure) inputTablePart.getFigure();
                            index++;
                            searchMaps.put(index, inputTableFigure.getFilterContainer());
                        }
                    }
                }
            }
            // TableNode
            for (TableNode node : inputTable.getNodes()) {
                if (node.getExpression() != null && matcher.matches(node.getExpression())) {
                    EList<Adapter> adapter = node.eAdapters();
                    if (adapter.size() > 0) {
                        if (adapter.get(0) instanceof TableEntityPart) {
                            TableEntityPart tableEntityPart = (TableEntityPart) adapter.get(0);
                            if (tableEntityPart != null && tableEntityPart.getFigure() != null
                                    && tableEntityPart.getFigure() instanceof TableEntityFigure) {
                                TableEntityFigure nodeFigure = (TableEntityFigure) tableEntityPart.getFigure();
                                index++;
                                searchMaps.put(index, nodeFigure);
                            }
                        }
                    }
                }
            }
        }

        // for the OutputTables
        for (OutputTable outputTable : outputTables) {
            // ExpressionFilter
            if (outputTable.getExpressionFilter() != null && matcher.matches(outputTable.getExpressionFilter())) {
                EList<Adapter> adapter = outputTable.eAdapters();
                if (adapter.size() > 0) {
                    if (adapter.get(0) instanceof PigMapOutputTablePart) {
                        PigMapOutputTablePart outputTablePart = (PigMapOutputTablePart) adapter.get(0);
                        if (outputTablePart != null && outputTablePart.getFigure() != null
                                && outputTablePart.getFigure() instanceof OutputTableFigure) {
                            OutputTableFigure outputTableFigure = (OutputTableFigure) outputTablePart.getFigure();
                            index++;
                            searchMaps.put(index, outputTableFigure.getFilterContainer());
                        }
                    }
                }
            }
            // OutputTableNode
            for (TableNode node : outputTable.getNodes()) {
                if (node.getExpression() != null && matcher.matches(node.getExpression())) {
                    EList<Adapter> adapter = node.eAdapters();
                    if (adapter.size() > 0) {
                        if (adapter.get(0) instanceof TableEntityPart) {
                            TableEntityPart tableEntityPart = (TableEntityPart) adapter.get(0);
                            if (tableEntityPart != null && tableEntityPart.getFigure() != null
                                    && tableEntityPart.getFigure() instanceof TableEntityFigure) {
                                TableEntityFigure nodeFigure = (TableEntityFigure) tableEntityPart.getFigure();
                                index++;
                                searchMaps.put(index, nodeFigure);
                            }
                        }
                    }
                }
            }
        }
    }

    public Integer selectHightlight(Map<Integer, Figure> searchMaps, Integer selectKey, String option) {
        if (searchMaps.containsKey(selectKey)) {
            if (option.equals("next") && selectKey + 1 < searchMaps.size()) {
                if (isHightlightAll) {
                    setEntryState(mapperManager, EntryState.HIGHLIGHTALL, searchMaps.get(selectKey));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, searchMaps.get(selectKey + 1));
                } else {
                    setEntryState(mapperManager, EntryState.NONE, searchMaps.get(selectKey));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, searchMaps.get(selectKey + 1));
                }
                // move scrollBarZone at selected TableItem
                moveScrollBarZoneAtSelectedTableItem(searchMaps.get(selectKey + 1));
                return selectKey + 1;
            } else if (option.equals("previous") && selectKey > 0) {
                if (isHightlightAll) {
                    setEntryState(mapperManager, EntryState.HIGHLIGHTALL, searchMaps.get(selectKey));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, searchMaps.get(selectKey - 1));
                } else {
                    setEntryState(mapperManager, EntryState.NONE, searchMaps.get(selectKey));
                    setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, searchMaps.get(selectKey - 1));
                }
                // move scrollBarZone at selected TableItem
                moveScrollBarZoneAtSelectedTableItem(searchMaps.get(selectKey - 1));
                return selectKey - 1;
            } else if (option.equals("first")) {
                setEntryState(mapperManager, EntryState.SEARCH_HIGHLIGHT, searchMaps.get(0));
                // move scrollBarZone at selected TableItem
                moveScrollBarZoneAtSelectedTableItem(searchMaps.get(0));
                return 0;
            }
        }
        return selectKey;
    }

    public void hightlightAll(Map<Integer, Figure> searchMaps, boolean isHightlight) {
        Iterator iter = searchMaps.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            if (isHightlight) {
                setEntryState(mapperManager, EntryState.HIGHLIGHTALL, (Figure) entry.getValue());
            } else {
                setEntryState(mapperManager, EntryState.NONE, (Figure) entry.getValue());
            }
        }
    }

    public void setEntryState(MapperManager mapperManager, EntryState entryState, Figure entry) {
        if (entry != null) {
            if (entry instanceof PigMapTableNodeFigure) {
                PigMapTableNodeFigure tableNodeFigure = (PigMapTableNodeFigure) entry;
                if (tableNodeFigure != null && tableNodeFigure.getExpressionFigure() != null) {
                    tableNodeFigure.getExpressionFigure().setOpaque(true);
                    tableNodeFigure.getExpressionFigure().setBackgroundColor(entryState.getColor());
                }
            } else if (entry instanceof TableFilterContainer) {
                TableFilterContainer filterText = (TableFilterContainer) entry;
                if (filterText != null && filterText.getTextArea() != null) {
                    filterText.getTextArea().setOpaque(true);
                    filterText.getTextArea().setBackgroundColor(entryState.getColor());
                }
            }
        }
    }

    public void moveScrollBarZoneAtSelectedTableItem(Figure entry) {
        if (entry != null) {
            Rectangle bounds = entry.getBounds();
            int selection = bounds.y - 30;
            ScrolledComposite scrollComposite = null;
            if (scrollComposite != null) {
                setPositionOfVerticalScrollBarZone(scrollComposite, selection);
            }
        }
    }

    private void setPositionOfVerticalScrollBarZone(ScrolledComposite scrollComposite, int scrollBarSelection) {
        ScrollBar verticalBar = scrollComposite.getVerticalBar();
        verticalBar.setSelection(scrollBarSelection);
        scrollComposite.setOrigin(0, scrollBarSelection);
    }

    public Integer getSelectedKeyAtSelectedTableItem(Map<Integer, Figure> searchMaps) {
        Integer selectKey = 0;
        IFigure figure = mapperManager.getSelectedFigure();
        if (figure != null && searchMaps.containsValue(figure)) {
            Iterator iter = searchMaps.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry entry = (Map.Entry) iter.next();
                if (entry.getValue() != null && entry.getValue() instanceof IFigure) {
                    IFigure figureTemp = (IFigure) entry.getValue();
                    if (figure.equals(figureTemp)) {
                        mapperManager.setSelectedFigure(null);
                        return (Integer) entry.getKey();
                    }
                }
            }
        }

        return selectKey;
    }

    public boolean isHightlightAll() {
        return this.isHightlightAll;
    }

    public void setHightlightAll(boolean isHightlightAll) {
        this.isHightlightAll = isHightlightAll;
    }
}
