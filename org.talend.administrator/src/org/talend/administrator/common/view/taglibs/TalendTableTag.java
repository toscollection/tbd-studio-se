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

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

import org.displaytag.tags.el.ELTableTag;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class TalendTableTag extends ELTableTag {

    private static final long serialVersionUID = 2455428123890184770L;

    public static final String SELECTALL_I18NKEY = "i-common.lists.select.all";

    public static final String SELECTNONE_I18NKEY = "i-common.lists.select.none";

    public static final String BUTTON_FILTER_ALTI18NKEY = "i-common.button.OK.desc";

    public static final String BUTTON_FILTER_RELATIVEURL = "/images/liste_applyfilter.gif";

    @Override
    public int doStartTag() throws JspException {
        int toReturn;
        try {
            pageContext.getOut().print("<div class=\"liste\">");

            toReturn = super.doStartTag();
            toReturn = 0;
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return toReturn;
    }

    @Override
    public int doEndTag() throws JspException {
        int toReturn;
        try {
            toReturn = super.doEndTag();
            toReturn = 0;
            pageContext.getOut().print("</div>");
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return toReturn;
    }

    public static String getSelectUnselectBloc(HttpServletRequest request) {
        StringBuffer toReturn = new StringBuffer();
        toReturn.append("<div class=\"selectUnselect\"><a href=\"javascript:editAllBoxes('0','_field_',true)\">");
        toReturn.append("All");
        toReturn.append("</a> / <a href=\"javascript:editAllBoxes('0','_field_',false)\">");
        toReturn.append("None");
        toReturn.append("</a></div>");
        return toReturn.toString();
    }

    public static String getFilterButton(HttpServletRequest request) {
        String altText = "OK Desc";
        StringBuffer toReturn = new StringBuffer();
        toReturn.append("<a href=\"{0}\" title=\"" + altText + "\">");
        toReturn.append("<img src=\"{1}" + TalendTableTag.BUTTON_FILTER_RELATIVEURL + "\" alt=\"" + altText + "\" />");
        toReturn.append("</a>");
        return toReturn.toString();
    }
}
