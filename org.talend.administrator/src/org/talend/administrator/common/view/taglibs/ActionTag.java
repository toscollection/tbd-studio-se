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
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.talend.administrator.common.constants.Constants.Action;
import org.talend.administrator.common.constants.Constants.Event;
import org.talend.administrator.common.session.SessionManager;
import org.talend.administrator.common.util.messages.MessageHandler;
import org.talend.core.model.properties.UserRole;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public abstract class ActionTag extends TagSupport {

    private static final String JS_PREFIX = "javascript:";

    private static final String DESCRIPTION_SUFFIX = ".desc";

    protected String title;

    protected String titleKey;

    protected String titleDesc;

    protected String dispatchParam;

    protected String dispatchValue;

    protected String javascript;

    protected String styleClass;

    protected Boolean titleDispatching;

    protected Boolean titleStyling;

    protected BarTag parentBar;

    protected UserRole role;

    public String getDispatchParam() {
        return dispatchParam;
    }

    public String getTitle() {
        return title;
    }

    public String getTitleKey() {
        return titleKey;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setDispatchParam(String dispatchParam) {
        this.dispatchParam = dispatchParam;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTitleKey(String titleKey) {
        this.titleKey = titleKey;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public String getDispatchValue() {
        return dispatchValue;
    }

    public void setDispatchValue(String paramValue) {
        this.dispatchValue = paramValue;
    }

    public void setDispatchValue(Action action) {
        this.dispatchValue = action.toString();
    }

    public void setDispatchValue(Event event) {
        this.dispatchValue = event.toString();
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public Boolean getTitleDispatching() {
        return titleDispatching;
    }

    public void setTitleDispatching(Boolean titleDispatching) {
        this.titleDispatching = titleDispatching;
    }

    public Boolean getTitleStyling() {
        return titleStyling;
    }

    public void setTitleStyling(Boolean titleStyling) {
        this.titleStyling = titleStyling;
    }

    public String getJavascript() {
        return javascript;
    }

    public void setJavascript(String javascript) {
        this.javascript = javascript;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    protected BarTag getParentBar() {
        if (parentBar == null) {
            parentBar = (BarTag) findAncestorWithClass(this, BarTag.class);
        }
        return parentBar;
    }

    private static Pattern formPattern = Pattern.compile("$form", Pattern.LITERAL);

    private static Pattern formDotPattern = Pattern.compile("$form.", Pattern.LITERAL);

    private static Pattern breakPattern = Pattern.compile("$break", Pattern.LITERAL);

    protected static final String JS_FORM_SYMBOL = "f"; // TODO

    protected static final String JS_WITH_ANCHOR = "w";

    protected String formatJavascript() {
        if (!javascript.startsWith(JS_PREFIX)) {
            javascript = JS_PREFIX + javascript;
        }
        if (!javascript.endsWith(";")) {
            javascript += ";";
        }
        return javascript;
    }

    protected String formatJavascriptUsingForm() {
        if (javascript.startsWith(JS_PREFIX)) {
            javascript = javascript.substring(JS_PREFIX.length());
        }
        javascript = formPattern.matcher(javascript).replaceAll(JS_FORM_SYMBOL);
        javascript = formDotPattern.matcher(javascript).replaceAll("");
        javascript = breakPattern.matcher(javascript).replaceAll("break " + JS_WITH_ANCHOR);
        if (!javascript.endsWith(";")) {
            javascript += ";";
        }
        return javascript;
    }

    protected void initTitle() throws JspException {
        TagHelper.checkExactlyOne(title, titleKey);
        if (!TagHelper.isEmpty(titleKey)) {
            title = MessageHandler.getMessage(titleKey);
            initTitleDesc();
        }
    }

    protected void initTitleDesc() throws JspException {
        titleDesc = MessageHandler.getMessage(titleKey + DESCRIPTION_SUFFIX);
    }

    protected void initDispatch() throws JspException {
        if (TagHelper.isEmpty(dispatchParam)) {
            BarTag parent = getParentBar();
            if (parent != null) {
                if (!TagHelper.isEmpty(parent.getDispatchParam())) {
                    dispatchParam = parent.getDispatchParam();
                } else {
                    dispatchParam = parent.getExistingDispatchParam();
                }
                if (TagHelper.isEmpty(dispatchValue)) {
                    dispatchValue = parent.getDispatchValue();
                }
            }
        }
        if (!TagHelper.isEmpty(dispatchParam) && !TagHelper.isEmpty(titleDispatching)) {
            dispatchValue = titleKey.substring(titleKey.lastIndexOf(".") + 1);
        }
    }

    protected void exit() {
        title = null;
        dispatchParam = null;
        dispatchValue = null;
    }

    private boolean isRoleOK() {
        if (this.role == null) {
            return true;
        }

        String uRole = SessionManager.getUserRoleSession((HttpServletRequest) pageContext.getRequest());
        return (uRole.equals(this.role.getName()));
    }

    public final int doStartTag() throws JspException {
        if (isRoleOK()) {
            initTitle();
            initDispatch();
            TagHelper.checkDepends(titleDispatching, titleKey);
            TagHelper.checkDepends(titleStyling, titleKey);
            TagHelper.checkAtMostOne(styleClass, titleStyling);
            try {
                pageContext.getOut().println(getOpening());
            } catch (IOException e) {
                throw new JspException("I/O Error", e);
            }
        }
        return SKIP_BODY;
    }

    @Override
    public final int doEndTag() throws JspException {
        try {
            pageContext.getOut().println(getClosing());
            if (mustWriteHiddenDispatch && !isHiddenDispatchWritten()) {
                writeHiddenDispatch();
            }
            exit();
        } catch (IOException e) {
            throw new JspException("I/O Error", e);
        }
        return EVAL_PAGE;
    }

    protected abstract String getOpening() throws JspException;

    protected String getClosing() {
        return "";
    }

    public void setHiddenDispatchWritten() {
        if (parentBar != null) {
            parentBar.isHiddenDispatchWritten = true;
        }
    }

    public boolean isHiddenDispatchWritten() {
        if (parentBar != null) {
            return parentBar.isHiddenDispatchWritten;
        } else {
            return false;
        }
    }

    protected boolean mustWriteHiddenDispatch = false;

    public void writeHiddenDispatch() throws JspException {
        StringBuffer buf = new StringBuffer();
        buf.append("<input type=\"hidden\" name=\"").append(getDispatchParam());
        buf.append("\"/>");
        try {
            pageContext.getOut().write(buf.toString());
        } catch (IOException e) {
            throw new JspException("I/O Exception", e);
        }
        setHiddenDispatchWritten();
    }

    public void writeHiddenDispatchFull() throws JspException {
        StringBuffer buf = new StringBuffer();
        buf.append("<input type=\"hidden\" name=\"").append(getDispatchParam());
        buf.append("\" value=\"").append(getDispatchValue());
        buf.append("\"/>");
        try {
            pageContext.getOut().write(buf.toString());
        } catch (IOException e) {
            throw new JspException("I/O Exception", e);
        }
        setHiddenDispatchWritten();
    }

}
