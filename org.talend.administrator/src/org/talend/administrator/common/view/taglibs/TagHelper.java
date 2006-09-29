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
package org.talend.administrator.common.view.taglibs;

import java.util.Arrays;

import javax.servlet.jsp.JspException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class TagHelper { 
    private TagHelper() {
    }

    public static boolean isEmpty(String value) {
        return value == null || value.equals("");
    }

    public static boolean isEmpty(Object value) {
        return value == null;
    }

    public static void checkExactlyOne(Object... attributes) throws JspException {
        checkRule(true, true, attributes);
    }

    public static void checkAtMostOne(Object... attributes) throws JspException {
        checkRule(false, true, attributes);
    }

    public static void checkAtLeastOne(Object... attributes) throws JspException {
        checkRule(true, false, attributes);
    }

    protected static void checkRule(boolean forbidZero, boolean forbidSeveral, Object... attributes)
            throws JspException {
        int count = 0;
        for (Object attr : attributes) {
            if (!isEmpty(attr)) {
                count++;
            }
        }
        if (count == 1) {
            return;
        }
        if (count > 1 && forbidSeveral) {
            throw new JspException("Attributes with values " + Arrays.deepToString(attributes)
                    + " are mutually exclusive");
        }
        if (count == 0 && forbidZero) {
            throw new JspException("A mandatory attribute is missing, check TLD description");
        }
    }

    public static void checkDepends(Object attribute, Object... toCheck) throws JspException {
        if (isEmpty(attribute)) {
            return;
        }
        for (Object attr : toCheck) {
            if (!isEmpty(attr)) {
                return;
            }
        }
        throw new JspException("There are required attributes for the attribute valued " + attribute + "; see TLD");
    }
}
