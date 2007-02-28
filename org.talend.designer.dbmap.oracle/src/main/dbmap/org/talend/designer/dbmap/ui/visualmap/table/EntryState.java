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
package org.talend.designer.dbmap.ui.visualmap.table;

import org.eclipse.swt.graphics.Color;
import org.talend.designer.dbmap.ui.color.ColorInfo;
import org.talend.designer.dbmap.ui.color.ColorProviderMapper;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: EntryState.java 898 2006-12-07 11:06:17Z amaumont $
 * 
 */
public enum EntryState {
    HIGHLIGHT(ColorInfo.COLOR_ENTRY_HIGHLIGHTED),
    ERROR(ColorInfo.COLOR_ENTRY_ERROR),
    WARNING(ColorInfo.COLOR_ENTRY_WARNING),
    NONE(ColorInfo.COLOR_ENTRY_NONE);

    private ColorInfo colorInfo;

    private EntryState(ColorInfo colorInfo) {
        this.colorInfo = colorInfo;
    }

    public Color getColor() {
        return ColorProviderMapper.getColor(colorInfo);
    }

}
