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
package org.talend.administrator.common.view.displaytag.checkbox;

import java.io.Serializable;

import org.displaytag.decorator.data.InputCheckboxField;
import org.displaytag.manager.FormFieldManager;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class TalendInputCheckboxField extends InputCheckboxField implements Serializable {

    private static final long serialVersionUID = 8106958476932675198L;

    public TalendInputCheckboxField() {
        super(null, null, false);
    }

    public TalendInputCheckboxField(FormFieldManager manager, Object parent, boolean checked) {
        super(manager, parent, checked);
    }

    public TalendInputCheckboxField(FormFieldManager manager, Object parent, String name, boolean checked) {
        super(manager, parent, name, checked);
    }

    public TalendInputCheckboxField(FormFieldManager manager, String idColumn, Object parent, boolean checked) {
        super(manager, idColumn, parent, checked);
    }

    public TalendInputCheckboxField(FormFieldManager manager, String idColumn, Object parent, String name,
            boolean checked) {
        super(manager, idColumn, parent, name, checked);
    }

}
