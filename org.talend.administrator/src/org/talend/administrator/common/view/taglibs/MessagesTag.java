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
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.talend.administrator.common.util.messages.MessageHandler;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class MessagesTag extends TagSupport {

    private static final long serialVersionUID = 2614299752066964626L;

    private static final String STYLE_CLASS_MESSAGE = "ok";

    private static final String STYLE_CLASS_ERROR = "erreur";

    private String titleKey;

    private boolean errors = false;

    private String filterKey;

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public void setErrors(boolean errors) {
        this.errors = errors;
    }

    public void setFilterKey(String filterKey) {
        this.filterKey = filterKey;
    }

    public int doStartTag() throws JspException {
        try {
            // Get the messages in the request :
            ActionMessages actionMessages = null;
            if (this.errors) {
                actionMessages = (ActionMessages) pageContext.getRequest().getAttribute(Globals.ERROR_KEY);
            } else {
                actionMessages = (ActionMessages) pageContext.getRequest().getAttribute(Globals.MESSAGE_KEY);
            }

            if (actionMessages != null) {
                // Get an iterator on these (filtered or not) messages
                Iterator iterator = null;
                if (filterKey != null && filterKey.length() > 0) {
                    iterator = actionMessages.get(filterKey);
                } else {
                    iterator = actionMessages.get();
                }

                if (iterator.hasNext()) {

                    pageContext.getOut().println(
                            "<div id=\"infozone\" class=\"" + (this.errors ? STYLE_CLASS_ERROR : STYLE_CLASS_MESSAGE)
                                    + "\">");
                    // Display zone title :
                    if (titleKey != null) {
                        String title = MessageHandler.getMessage(titleKey);
                        pageContext.getOut().println("<span>" + title + "</span>");
                    }

                    pageContext.getOut().println("<ul>");

                    // Iterate on each message :
                    while (iterator.hasNext()) {
                        // Get the message :
                        ActionMessage actionMessage = (ActionMessage) iterator.next();

                        // Get the message translated in the user locale :
                        String msg = MessageHandler.getMessage(actionMessage.getKey(), actionMessage.getValues());

                        iterator.remove();

                        // Finally print the message :
                        pageContext.getOut().println("<li>" + msg + "</li>");
                    }
                    pageContext.getOut().println("</ul>");
                    pageContext.getOut().println("</div>");
                }
            }
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return SKIP_BODY;
    }
}
