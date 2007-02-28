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

import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.talend.commons.ui.swt.dnd.DNDKeyAnalyzer;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.managers.UIManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: DefaultDropTargetListener.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public class DefaultDropTargetListener implements TransferDropTargetListener {

    protected MapperManager mapperManager;

    private Point lastCursorPosition;

    public DefaultDropTargetListener(MapperManager mapperManager) {
        super();
        this.mapperManager = mapperManager;
    }

    public void dragEnter(DropTargetEvent event) {
        updatePopupPosition(event);
        setPopupVisible(true);
        event.detail = mapperManager.getUiManager().getCurrentDragDetail();
    }

    public void dragLeave(DropTargetEvent event) {
        dragFinished();
    }

    private void dragFinished() {
        setPopupVisible(false);
        if (!isSubclassed()) {
            mapperManager.getUiManager().setDragging(false);
        }
    }

    private void setPopupVisible(boolean visible) {
        UIManager uiManager = mapperManager.getUiManager();
        DraggingInfosPopup draggingInfosPopup = uiManager.getDraggingInfosPopup();
        draggingInfosPopup.setVisible(visible);
    }

    public void dragOperationChanged(DropTargetEvent event) {
        updatePopupPosition(event);
        detectPressedKeys(event);
        UIManager uiManager = mapperManager.getUiManager();

        DraggingInfosPopup draggingInfosPopup = uiManager.getDraggingInfosPopup();
        if (uiManager.isCtrlPressed()) {
            draggingInfosPopup.setOverwriteMode(true);
        } else {
            draggingInfosPopup.setOverwriteMode(false);
        }

        if (uiManager.isShiftPressed()) {
            draggingInfosPopup.setMapOneToOneMode(true, true);
        } else {
            draggingInfosPopup.setMapOneToOneMode(false, true);
        }

        int detail = DND.DROP_NONE;

        if (uiManager.isCtrlPressed() && uiManager.isShiftPressed()) {
            detail |= DND.DROP_COPY;
        } else if (uiManager.isCtrlPressed()) {
            detail |= DND.DROP_LINK;
        } else if (uiManager.isShiftPressed()) {
            detail |= DND.DROP_COPY;
        } else {
            detail |= DND.DROP_COPY;
        }

        uiManager.setCurrentDragDetail(detail);

        event.detail = DND.DROP_NONE;
    }

    /**
     * DOC amaumont Comment method "detectPressedKeys".
     * 
     * @param event
     */
    protected void detectPressedKeys(DropTargetEvent event) {
        DNDKeyAnalyzer keyAnalyzer = new DNDKeyAnalyzer(event);
        UIManager uiManager = mapperManager.getUiManager();
        uiManager.setCtrlPressed(keyAnalyzer.isCtrlPressed());
        uiManager.setShiftPressed(keyAnalyzer.isShiftPressed());

    }

    public void dragOver(DropTargetEvent event) {
        // System.out.println(((DropTarget)event.widget).getControl());
        event.detail = DND.DROP_NONE;
        updatePopupPosition(event);
        setPopupVisible(true);
    }

    private void updatePopupPosition(DropTargetEvent event) {

        Point cursorPosition = new Point(event.x, event.y);
        if (!cursorPosition.equals(lastCursorPosition)) {
            UIManager uiManager = mapperManager.getUiManager();
            DraggingInfosPopup draggingInfosPopup = uiManager.getDraggingInfosPopup();
            draggingInfosPopup.setCursorPosition(event.x, event.y);
        }
        lastCursorPosition = cursorPosition;
    }

    public void drop(DropTargetEvent event) {
        dragFinished();
    }

    /**
     * DOC amaumont Comment method "isSubclassed".
     * 
     * @return
     */
    private boolean isSubclassed() {
        return getClass() != DefaultDropTargetListener.class;
    }

    public void dropAccept(DropTargetEvent event) {

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.util.TransferDropTargetListener#getTransfer()
     */
    public Transfer getTransfer() {
        return TableEntriesTransfer.getInstance();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.util.TransferDropTargetListener#isEnabled(org.eclipse.swt.dnd.DropTargetEvent)
     */
    public boolean isEnabled(DropTargetEvent event) {
        return true;
    }

}
