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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class LinkTag extends ActionTag {
    
    private static final long serialVersionUID = 3949132811725393523L;

    private String form;

    private String path;

    private String altKey;

    public String getForm() {
        return form;
    }

    public void setForm(String form) {
        this.form = form;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getAltKey() {
        return altKey;
    }

    public void setAltKey(String altKey) {
        this.altKey = altKey;
    }

    protected String getInterpretedForm() {
        return "form";
    }

    protected String getOpening() throws JspException {
        TagHelper.checkAtLeastOne(path, form, javascript);
        TagHelper.checkAtMostOne(path, form);

        StringBuffer buf = new StringBuffer();
        buf.append("<a href=\"");

        // case 1 ; put path in href
        if (!TagHelper.isEmpty(path)) {
            // context
            if (path.startsWith("/")) {
                buf.append(((HttpServletRequest) pageContext.getRequest()).getContextPath());
            }
            buf.append(path);
            // dispatch
            if (!TagHelper.isEmpty(getDispatchParam())) {
                buf.append(path.contains("?") ? "&" : "?");
                buf.append(dispatchParam).append("=").append(dispatchValue);
            }
            
            // put javascript in onclick
            if (!TagHelper.isEmpty(javascript)) {
                buf.append("\" onclick=\"").append(formatJavascript());
            }

            // case 2 : put custom javascript in href
        } else if (!TagHelper.isEmpty(form)) {
            buf.append("javascript:var f = document.forms['" + form + "']; ");
            buf.append(JS_WITH_ANCHOR).append(":with (f) { ");
            
            // dispatch
            if (!TagHelper.isEmpty(dispatchParam)) {
                buf.append("elements['").append(dispatchParam).append("'].value='").append(dispatchValue).append("';");
                mustWriteHiddenDispatch = true;
            }
            if (!TagHelper.isEmpty(javascript)) {
                buf.append(formatJavascriptUsingForm());
            }

            buf.append(" submit(); };");

            buf.append("\" onMouseover=\"window.status=''; return true;");

            // case 3 : put simple javascript in href
        } else if (!TagHelper.isEmpty(javascript)) {
            buf.append(formatJavascript());
            buf.append("\" onMouseover=\"window.status=''; return true;");
        }

        // deal with style
        if (!TagHelper.isEmpty(styleClass)) {
            buf.append("\" class=\"").append(styleClass);
        } else if (!TagHelper.isEmpty(titleStyling)) {
            String lastKey = titleKey.substring(titleKey.lastIndexOf(".") + 1);
            buf.append("\" class=\"").append(lastKey);
        }

        // deal with description
        buf.append("\"");
        if (!TagHelper.isEmpty(altKey)) {
            buf.append(" title=\"" + titleDesc + "\">");
        } else if (!TagHelper.isEmpty(titleDesc)) {
            buf.append(" title=\"" + titleDesc + "\">");
        } else {
            buf.append(">");
        }

        // write displayed title and close
        buf.append(title);
        buf.append("</a>");

        if (!TagHelper.isEmpty(styleClass) || !TagHelper.isEmpty(titleStyling)) {
            buf.insert(0, "<li>");
            buf.append("</li>");
        }

        return buf.toString();
    }
}
