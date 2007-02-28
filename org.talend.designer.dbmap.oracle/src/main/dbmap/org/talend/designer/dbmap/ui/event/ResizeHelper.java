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
package org.talend.designer.dbmap.ui.event;

import org.eclipse.swt.graphics.Point;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ResizeHelper.java 1 2006-09-29 17:06:40Z nrousseau $
 * 
 */
public class ResizeHelper {

    /**
     * DOC amaumont ResizeHelper class global comment. Detailled comment <br/>
     * 
     */
    public enum RESIZE_MODE {
        HORIZONTAL,
        VERTICAL,
        BOTH,
        NONE,
    }

    private Point lastDragPoint;

    private RESIZE_MODE currentMode;

    public void startDrag(Point point) {
        lastDragPoint = point;
    }

    public void stopDrag() {
        lastDragPoint = null;
    }

    public boolean isDragging() {
        return lastDragPoint != null;
    }

    public Point getLastDragPoint() {
        return lastDragPoint;
    }

    public void setLastDragPoint(Point lastDragPoint) {
        this.lastDragPoint = lastDragPoint;
    }

    public RESIZE_MODE getCurrentMode() {
        return currentMode;
    }

    public void setCurrentMode(RESIZE_MODE currentMode) {
        this.currentMode = currentMode;
    }

}
