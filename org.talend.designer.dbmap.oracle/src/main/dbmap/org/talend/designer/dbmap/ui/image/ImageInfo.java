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
package org.talend.designer.dbmap.ui.image;

/**
 * DOC mhelleboid class global comment. Detailled comment <br/>
 * 
 * $Id: ImageInfo.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public enum ImageInfo {

    MINIMIZE_ICON("/icons/collapse16.png"), //$NON-NLS-1$
    RESTORE_ICON("/icons/uncollapse16.png"), //$NON-NLS-1$
    // MAPPER_ICON("/icons/tMap32.ico"),
    ADD_CONSTRAINT_ICON("/icons/add_constraint16.png"), //$NON-NLS-1$
    REMOVE_CONSTRAINT_ICON("/icons/remove_constraint16.png"), //$NON-NLS-1$
    CHECKED_ICON("/icons/checked.png"), //$NON-NLS-1$
    UNCHECKED_ICON("/icons/unchecked.png"), //$NON-NLS-1$
    ADD_FILTER_ICON("/icons/addfilter.png"), //$NON-NLS-1$
    REJECT_FILTER_ICON("/icons/rejectfilter.png"), //$NON-NLS-1$
    REJECT_LOOKUP_ICON("/icons/rejectlookup.png"); //$NON-NLS-1$

    private String path;

    ImageInfo(String path) {
        this.path = path;
    }

    public String getPath() {
        return this.path;
    }

}
