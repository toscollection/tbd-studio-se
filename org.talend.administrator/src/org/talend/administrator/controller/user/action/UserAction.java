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
package org.talend.administrator.controller.user.action;

import java.util.Calendar;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.talend.administrator.business.UserHandler;
import org.talend.administrator.common.action.TalendDispatchAction;
import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.constants.Constants.Action;
import org.talend.administrator.common.constants.Constants.Forward;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.SystemException;
import org.talend.administrator.common.session.ConfirmDeletion;
import org.talend.administrator.common.view.displaytag.checkbox.CheckBoxManager;
import org.talend.administrator.controller.user.form.UserForm;
import org.talend.administrator.persistence.proxy.UserProxy;
import org.talend.core.model.properties.PropertiesFactory;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class UserAction extends TalendDispatchAction<UserForm> {

    public static final String KEY_SESSION = UserAction.class.getSimpleName();

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#add(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward add(UserForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        UserProxy user = new UserProxy(PropertiesFactory.eINSTANCE.createUser());
        request.getSession().setAttribute(Constants.USER, user);

        UserHandler.setUserRolesInSession(request);

        return Forward.FORM;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#confirmdelete(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward confirmdelete(UserForm form, ActionMapping mapping, HttpServletRequest request,
            ActionMessages reqMessages, ActionMessages reqErrors) throws BusinessException, SystemException {

        Constants.Action mode = Constants.Action.DELETE;
        UserProxy user = UserHandler.getCurrentUser(request, KEY_SESSION);
        form.setId(user.getId());

        // Try validation :
        ActionErrors actionErrors = form.validate(mapping, request, mode);
        if (actionErrors != null && !actionErrors.isEmpty()) {
            request.setAttribute(Globals.ERROR_KEY, actionErrors);
            return list(form, request, reqMessages, getErrors(request));
        }

        ConfirmDeletion confirmDeletion = new ConfirmDeletion("user.login", user.getLogin(), "actionUser.do?method="
                + Action.DELETE + "&id=" + user.getId(), "actionUser.do?method=" + Action.LIST);
        request.setAttribute(Action.CONFIRMDELETE.toString(), confirmDeletion);
        return Forward.CONF_DELETE;

    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#delete(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward delete(UserForm form, ActionMapping mapping, HttpServletRequest request,
            ActionMessages reqMessages, ActionMessages reqErrors) throws BusinessException, SystemException {

        Constants.Action mode = Constants.Action.DELETE;
        String msgKey = initMsgKey(mode);

        // Try validation :
        ActionErrors actionErrors = form.validate(mapping, request, mode);
        if (actionErrors != null && !actionErrors.isEmpty()) {
            request.setAttribute(Globals.ERROR_KEY, actionErrors);
            return Forward.FORM;
        }

        UserProxy userProxy = UserHandler.getCurrentUser(request, KEY_SESSION);
        userProxy.setActive(false);
        userProxy.setDateDelete(Calendar.getInstance().getTime());

        UserHandler facade = UserHandler.getInstance();
        facade.saveUser(userProxy);

        reqMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msgKey, userProxy.getLogin()));
        return list(form, request, reqMessages, getErrors(request));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#list(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward list(UserForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {

        List<UserProxy> users = UserHandler.listUsers();

        CheckBoxManager talendTagManager = CheckBoxManager.getInstance(request, KEY_SESSION);
        if (talendTagManager != null) {
            talendTagManager.deleteManager(request);
        }
        talendTagManager = CheckBoxManager.createInstance(KEY_SESSION, true, request, users);

        request.getSession().setAttribute(Constants.USER_LIST, users);
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
    protected Forward load(UserForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {

        UserProxy user = UserHandler.getCurrentUser(request, KEY_SESSION);

        request.getSession().setAttribute(Constants.USER, user);

        UserHandler.setUserRolesInSession(request);

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
    protected Forward submit(ActionMapping mapping, UserForm form, HttpServletRequest request,
            ActionMessages reqMessages, ActionMessages reqErrors) throws BusinessException, SystemException {

        Constants.Action mode = defineAction(form);
        String msgKey = initMsgKey(mode);

        // Try validation :
        ActionErrors actionErrors = form.validate(mapping, request, mode);
        if (actionErrors != null && !actionErrors.isEmpty()) {
            request.setAttribute(Globals.ERROR_KEY, actionErrors);
            return Forward.FORM;
        }

        // Instantiate the proxy :
        UserProxy userProxy = new UserProxy(form, mode);
        UserHandler facade = UserHandler.getInstance();

        // Delegate insert of the proxy to the facade :
        facade.saveUser(userProxy);

        // Delete the checkbox manager (to reload list later) :
        CheckBoxManager checkBoxManager = CheckBoxManager.getInstance(request, KEY_SESSION);
        if (checkBoxManager != null) {
            checkBoxManager.deleteManager(request);
        }

        // Adding message in session :
        reqMessages.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(msgKey, userProxy.getLogin()));

        // Reload the complete list :
        return list(form, request, reqMessages, reqErrors);
    }

    private String initMsgKey(Constants.Action mode) {
        // Find the appropriate message key :
        String msgKey = "common.action." + mode.toString().toLowerCase() + ".ok";
        return msgKey;
    }

    private Constants.Action defineAction(UserForm form) {
        // Define exact action :
        Constants.Action mode = null;
        if (form.getId() == null || form.getId() == 0) {
            mode = Constants.Action.ADD;
        } else {
            mode = Constants.Action.UPDATE;
        }
        return mode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#update(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward update(UserForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {

        UserProxy user = UserHandler.getCurrentUser(request, KEY_SESSION);
        user.fillForm(form);

        request.getSession().setAttribute(Constants.USER, user);

        UserHandler.setUserRolesInSession(request);

        return Forward.FORM;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.action.TalendDispatchAction#validate(org.apache.struts.action.ActionForm,
     * javax.servlet.http.HttpServletRequest, org.apache.struts.action.ActionMessages,
     * org.apache.struts.action.ActionMessages)
     */
    @Override
    protected Forward validate(UserForm form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException {
        // TODO Auto-generated method stub
        return null;
    }
}
