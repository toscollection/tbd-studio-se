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
package org.talend.designer.dbmap.ui.dnd;

import java.util.List;

import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreator;
import org.talend.commons.ui.swt.tableviewer.TableViewerCreatorColumn;
import org.talend.commons.ui.ws.WindowSystem;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.managers.UIManager;
import org.talend.designer.dbmap.model.table.InputTable;
import org.talend.designer.dbmap.model.table.OutputTable;
import org.talend.designer.dbmap.ui.visualmap.table.DataMapTableView;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: DropContextAnalyzer.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class DropContextAnalyzer {

    private DropTargetEvent event;

    private DraggedData draggedData;

    private MapperManager mapperManager;

    private Zone zoneTarget;

    private Zone zoneSource;

    private DataMapTableView dataMapTableViewTarget;

    private DataMapTableView dataMapTableViewSource;

    private boolean isDropValid;

    private TableItem tableItemSource;

    private Table currentTableTarget;

    private int feedback;

    private int detail;

    private boolean insertionEntryMode;

    private boolean mapOneToOneMode;

    private boolean isCursorOverExpressionCell;

    private boolean overwriteExpression;

    private boolean mapOneToOneAuthorized;

    private boolean isInputToInput;

    private boolean isOutputToOutput;

    private boolean invalidKeyPressed;

    public DropContextAnalyzer(DraggedData draggedData, DropTargetEvent event, MapperManager mapperManager) {
        super();
        this.draggedData = draggedData;
        this.event = event;
        this.mapperManager = mapperManager;
        init();
    }

    /**
     * DOC amaumont Comment method "init".
     */
    private void init() {

        DropTarget dropTarget = (DropTarget) event.widget;
        currentTableTarget = (Table) dropTarget.getControl();
        dataMapTableViewTarget = mapperManager.retrieveDataMapTableView(currentTableTarget);
        zoneTarget = dataMapTableViewTarget.getZone();
        draggedData = TableEntriesTransfer.getInstance().getDraggedData();
        dataMapTableViewSource = draggedData.getDataMapTableViewSource();
        tableItemSource = draggedData.getTableItemSource();
        zoneSource = dataMapTableViewSource.getZone();

        analyzeCursorOverExpressionCell();

        invalidKeyPressed = hasInvalidKeyPressed();

        isDropValid = checkDropIsValid();

        analyzeForFeedback();
        analyzeForDetail();
        // System.out.println("isCursorOverExpressionCell="+isCursorOverExpressionCell);
    }

    /**
     * DOC amaumont Comment method "checkDropHasValidTarget".
     */
    private boolean checkDropIsValid() {

        isInputToInput = false;
        mapOneToOneAuthorized = true;

        if (targetTableIsFiltersTable() || draggedData.getTransferableEntryList().size() <= 1) {
            mapOneToOneAuthorized = false;
        }

        if (invalidKeyPressed) {
            return false;
        }

        /*
         * INPUT => INPUT
         */
        if (zoneSource == Zone.INPUTS && zoneTarget == Zone.INPUTS) {

            isInputToInput = true;
            mapOneToOneAuthorized = false;
            return true;

        }

        /*
         * OUTPUT => OUTPUT
         */
        if (zoneSource == Zone.OUTPUTS && zoneTarget == Zone.OUTPUTS) {

            isOutputToOutput = true;
            mapOneToOneAuthorized = true;
            List<OutputTable> outputTables = mapperManager.getOutputTables();
            int indexSourceOutputTable = outputTables.indexOf(dataMapTableViewSource.getDataMapTable());
            int indexTargetOutputTable = outputTables.indexOf(dataMapTableViewTarget.getDataMapTable());
            if (indexSourceOutputTable == indexTargetOutputTable) {
                /*
                 * INPUT => INPUT && index of table source >= index of table target
                 */
                return false;
            } else {
                return true;
            }

        }

        TableItem tableItemTarget = getTableItemFromPosition(new Point(event.x, event.y));
        if (zoneSource == Zone.VARS && zoneTarget == Zone.VARS && tableItemTarget != null) {
            if (tableItemSource == tableItemTarget || !dropVarsEntryIsValid(tableItemTarget)) {
                /*
                 * VAR => VAR && (item source == item target || item target is invalid)
                 */
                return false;
            }
        }

        if (zoneSource == Zone.VARS && zoneTarget == Zone.INPUTS || zoneSource == Zone.OUTPUTS && zoneTarget == Zone.INPUTS
                || zoneSource == Zone.OUTPUTS && zoneTarget == Zone.VARS) {
            /*
             * VAR => INPUT OUTPUT => OUTPUT OUTPUT => INPUT OUTPUT => VAR
             */
            return false;
        }

        return true;
    }

    private boolean hasInvalidKeyPressed() {
        UIManager uiManager = mapperManager.getUiManager();
        if (WindowSystem.isGTK() && uiManager.isCtrlPressed() ^ uiManager.isShiftPressed()) {
            return true;
        }
        return false;
    }

    public int getDetail() {
        return this.detail;
    }

    public int getFeedback() {
        return this.feedback;
    }

    /**
     * .
     */
    @SuppressWarnings("unchecked")//$NON-NLS-1$
    private void analyzeCursorOverExpressionCell() {
        Point pointCursor = currentTableTarget.toControl(event.x, event.y);
        DataMapTableView dataMapTableView = mapperManager.retrieveDataMapTableView(currentTableTarget);
        TableViewerCreator tableViewerCreatorForColumns = dataMapTableView.getTableViewerCreatorForColumns();
        if (tableViewerCreatorForColumns.getTable() != currentTableTarget) {
            isCursorOverExpressionCell = false;
            return;
        }
        if (pointCursor.y < currentTableTarget.getHeaderHeight()
                || pointCursor.y >= currentTableTarget.getHeaderHeight() + currentTableTarget.getItemCount()
                        * currentTableTarget.getItemHeight()) {
            isCursorOverExpressionCell = false;
            return;
        }

        // searching current column index
        int currentColumnIndex = 0;
        TableColumn[] columns = currentTableTarget.getColumns();
        for (int i = 0, width = 0; i < columns.length; i++) {
            TableColumn column = columns[i];
            int widthColumn = column.getWidth();
            if (pointCursor.x >= width && pointCursor.x <= width + widthColumn) {
                currentColumnIndex = i;
                break;
            }
            width += widthColumn;
        }

        List<TableViewerCreatorColumn> viewerColumns = tableViewerCreatorForColumns.getColumns();
        TableViewerCreatorColumn viewerColumn = viewerColumns.get(currentColumnIndex);
        if (DataMapTableView.ID_EXPRESSION_COLUMN.equals(viewerColumn.getId())) {
            isCursorOverExpressionCell = true;
        } else {
            isCursorOverExpressionCell = false;
        }

    }

    private void analyzeForFeedback() {
        int dropFeedback = DND.FEEDBACK_SCROLL;
        boolean targetTableIsConstraintsTable = targetTableIsFiltersTable(dataMapTableViewTarget);

        if (isDropValid) {

            if (mapperManager.getUiManager().isCtrlPressed()) {
                overwriteExpression = true;
            } else {
                overwriteExpression = false;
            }

            if (zoneSource == Zone.INPUTS && zoneTarget == Zone.INPUTS) {
                dropFeedback |= DND.FEEDBACK_SELECT;
                mapOneToOneMode = false;
                insertionEntryMode = false;
            } else if (targetTableIsConstraintsTable) {
                mapOneToOneMode = false;
                insertionEntryMode = false;
                dropFeedback |= DND.FEEDBACK_SELECT;
            } else if (zoneSource == Zone.INPUTS && zoneTarget == Zone.VARS || zoneSource == Zone.INPUTS && zoneTarget == Zone.OUTPUTS
                    || zoneSource == Zone.VARS && zoneTarget == Zone.VARS || zoneSource == Zone.VARS && zoneTarget == Zone.OUTPUTS
                    || zoneSource == Zone.OUTPUTS && zoneTarget == Zone.OUTPUTS) {

                if (isCursorOverExpressionCell) {
                    insertionEntryMode = false;
                    if (mapperManager.getUiManager().isShiftPressed() && draggedData.getTransferableEntryList().size() > 1) {
                        mapOneToOneMode = true;
                    } else {
                        dropFeedback |= DND.FEEDBACK_SELECT;
                        mapOneToOneMode = false;
                    }
                } else {
                    mapOneToOneMode = false;
                    insertionEntryMode = true;
                }

            } else {
                mapOneToOneMode = false;
                insertionEntryMode = true;
            }
        }
        feedback = dropFeedback;
    }

    private void analyzeForDetail() {

        int dropOperation = DND.DROP_NONE;
        if (isDropValid) {
            if (overwriteExpression && !insertionEntryMode) {
                dropOperation = DND.DROP_LINK;
            } else {
                dropOperation = DND.DROP_COPY;
            }
        }
        detail = dropOperation;
    }

    private boolean targetTableIsFiltersTable(DataMapTableView target) {
        if (target.getZone() != Zone.OUTPUTS) {
            return false;
        }
        return currentTableTarget == target.getTableViewerCreatorForFilters().getTable();
    }

    public boolean targetTableIsFiltersTable() {
        return targetTableIsFiltersTable(dataMapTableViewTarget);
    }

    private TableItem getTableItemFromPosition(Point cursorPosition) {
        Point pointCursor = currentTableTarget.toControl(cursorPosition.x, cursorPosition.y);
        return currentTableTarget.getItem(pointCursor);
    }

    private boolean dropVarsEntryIsValid(TableItem tableItemTarget) {
        Table table = tableItemTarget.getParent();
        TableItem[] tableItems = table.getItems();
        for (int i = 0; i < tableItems.length; i++) {
            if (tableItemTarget == tableItems[i]) {
                break;
            }
            if (tableItemSource == tableItems[i]) {
                return true;
            }
        }
        return false;
    }

    public boolean isDropValid() {
        return this.isDropValid;
    }

    public boolean isInsertionEntryMode() {
        return this.insertionEntryMode;
    }

    public boolean isMapOneToOneMode() {
        return this.mapOneToOneMode;
    }

    public boolean isOverwriteExpression() {
        return this.overwriteExpression;
    }

    public boolean isCursorOverExpressionCell() {
        return isCursorOverExpressionCell;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "\nDropContextAnalyzer instance:" + "\n isTargetEntryValid=" + isDropValid + "\n insertionIndicatorVisible=" //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                + insertionEntryMode + "\n mapOneToOne=" + mapOneToOneMode + "\n overwriteExpression=" + overwriteExpression //$NON-NLS-1$ //$NON-NLS-2$
                + "\n isCursorOverExpressionColumn=" + isCursorOverExpressionCell; //$NON-NLS-1$
    }

    public boolean isMapOneToOneAuthorized() {
        return this.mapOneToOneAuthorized;
    }

    /**
     * DOC amaumont Comment method "isTableSourceAndTargetAreSame".
     */
    public boolean isTableSourceAndTargetAreSame() {
        if (tableItemSource == null) {
            return false;
        }
        if (currentTableTarget == null) {
            return false;
        }
        return tableItemSource.getParent() == currentTableTarget;
    }

    public boolean isInputToInput() {
        return this.isInputToInput;
    }

    public boolean isOutputToOutput() {
        return this.isOutputToOutput;
    }

    public void setOutputTuOutput(boolean isOutputTuOutput) {
        this.isOutputToOutput = isOutputTuOutput;
    }

    public boolean isInvalidKeyPressed() {
        return invalidKeyPressed;
    }

}
