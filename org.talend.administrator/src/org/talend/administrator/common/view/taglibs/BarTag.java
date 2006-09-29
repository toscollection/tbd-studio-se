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

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.talend.administrator.common.constants.Constants.Action;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public abstract class BarTag extends TagSupport {

    boolean isHiddenDispatchWritten = false;

    private String styleClass;

    private String dispatchParam;

    private String dispatchValue;

    private String existingDispatchParam;

    public String getDispatchParam() {
        return dispatchParam;
    }

    public void setDispatchParam(String dispatchParam) {
        this.dispatchParam = dispatchParam;
    }

    public String getDispatchValue() {
        return dispatchValue;
    }

    public void setDispatchValue(String dispatchValue) {
        this.dispatchValue = dispatchValue;
    }

    public void setDispatchValue(Action action) {
        this.dispatchValue = action.toString();
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getExistingDispatchParam() {
        return existingDispatchParam;
    }

    public void setExistingDispatchParam(String existingDispatchParam) {
        this.existingDispatchParam = existingDispatchParam;
        isHiddenDispatchWritten = true;
    }

    @Override
    public int doStartTag() throws JspException {
        TagHelper.checkAtMostOne(dispatchParam, existingDispatchParam);
        TagHelper.checkDepends(dispatchValue, dispatchParam, existingDispatchParam);
        return super.doStartTag();
    }

    @Override
    public int doEndTag() throws JspException {
        // re-initialization (does not work very fine)
        isHiddenDispatchWritten = false;

        return super.doEndTag();
    }
}
