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
package org.talend.administrator.controller.login.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.talend.administrator.business.UserHandler;
import org.talend.administrator.common.action.TalendDispatchAction;
import org.talend.administrator.common.constants.Constants.Forward;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.SystemException;
import org.talend.administrator.controller.login.form.LoginForm;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class LoginAction extends TalendDispatchAction<LoginForm> {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#add(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward add(LoginForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#confirmdelete(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward confirmdelete(LoginForm form, ActionMapping mapping, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#delete(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward delete(LoginForm form, ActionMapping mapping, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        UserHandler.unauthenticate(request);
        return Forward.FORM;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#list(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward list(LoginForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        return Forward.LIST;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#load(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward load(LoginForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        return Forward.FORM;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#submit(org.apache.struts.action.ActionMapping,
     * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     * org.apache.struts.action.ActionMessages, org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward submit(ActionMapping mapping, LoginForm form, HttpServletRequest request,
            ActionMessages reqMessages, ActionMessages reqErrors) throws BusinessException, SystemException {
        ActionErrors actionErrors = form.validate(mapping, request);

        if (UserHandler.authenticate(form.getLogin(), form.getPassword(), request)) {
            return Forward.LIST;
        } else {
            actionErrors.add("login.error.authentification", new ActionMessage("login.error.authentification"));
            request.setAttribute(Globals.ERROR_KEY, actionErrors);
            return Forward.ERROR_FORM;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#update(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward update(LoginForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#validate(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward validate(LoginForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        // TODO Auto-generated method stub
        return null;
    }

}
