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

import javax.servlet.jsp.JspException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ActionsBarTag extends BarTag {

    private static final long serialVersionUID = -4519796412509903218L;

    @Override
    public int doStartTag() throws JspException {
        super.doStartTag();

        try {
            if (getStyleClass() != null) {
                pageContext.getOut().print("<div id=\"actions\" class=\"" + getStyleClass() + "\"><ul>");
            } else {
                pageContext.getOut().print("<div id=\"actions\"><ul>");
            }
            // pageContext.getOut().print( "\"><ul>" );
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        super.doEndTag();
        try {
            pageContext.getOut().println("</ul></div>");
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return SKIP_BODY;
    }
}
