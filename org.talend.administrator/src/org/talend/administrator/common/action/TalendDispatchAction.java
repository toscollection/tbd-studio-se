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
package org.talend.administrator.common.action;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.DispatchAction;
import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.constants.Constants.Action;
import org.talend.administrator.common.constants.Constants.Forward;
import org.talend.administrator.common.constants.Constants.ReadMode;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.ProgrammerException;
import org.talend.administrator.common.exception.SystemException;

/**
 * DOC mhirt class global comment. Detailled comment <br/> $Id$
 * 
 * @param <T>
 */
public abstract class TalendDispatchAction<T extends ActionForm> extends DispatchAction {

    private static Logger logger = org.talend.administrator.common.logging.LoggerFactory
            .getLogger(TalendDispatchAction.class);

    protected static final String ACTION_UPDATE = "update";

    protected static final String ACTION_ADD = "add";

    protected static final String MESSAGE_ACTION_ADD_OK = "i-common.action.add.ok";

    protected static final String MESSAGE_ACTION_UPDATE_OK = "i-common.action.update.ok";

    protected static final String MESSAGE_ACTION_DELETE_OK = "i-common.action.delete.ok";

    protected static final String MESSAGE_ACTION_GENERAL_OK = "i-common.action.general.ok";

    protected Map<String, Forward> getMethodErrorMap() {
        Map<String, Forward> map = new HashMap<String, Forward>();
        map.put(Action.SUBMIT.toString(), Forward.ERROR_FORM);
        map.put(Action.UPDATE.toString(), Forward.ERROR_LIST);
        map.put(Action.ADD.toString(), Forward.ERROR_LIST);
        map.put(Action.DELETE.toString(), Forward.ERROR_LIST);
        map.put(Action.CONFIRMDELETE.toString(), Forward.ERROR_LIST);
        map.put(Action.LIST.toString(), Forward.ERROR_LIST);

        return map;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.actions.DispatchAction#execute(org.apache.struts.action.ActionMapping,
     * org.apache.struts.action.ActionForm, javax.servlet.http.HttpServletRequest,
     * javax.servlet.http.HttpServletResponse)
     */
    public final ActionForward execute(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        ActionMessages reqMessages = new ActionMessages();
        ActionMessages reqErrors = new ActionMessages();
        try {
            request.setAttribute(Globals.ERROR_KEY, reqErrors);
            request.setAttribute(Globals.MESSAGE_KEY, reqMessages);

            ActionForward forward = super.execute(mapping, form, request, response);
            return forward;

        } catch (BusinessException ex) {

            return getErrorsActionForward(mapping, form, request, response);
        } catch (Exception exc) {
            throw exc;
        }
    }

    protected ActionForward getActionForward(Forward forward, ActionMapping mapping, ActionForm form) {
        return mapping.findForward(forward.toString());
    }

    protected ActionForward getErrorsActionForward(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        Forward forward = getErrorsForMethod(mapping, form, request, response);
        if (forward == null) {
            throw new IllegalArgumentException(this.getClass() + " : Forward action not found");
        }
        return mapping.findForward(forward.toString());
    }

    public ActionForward submit(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = submit(mapping, form, request, getMessages(request), getErrors(request));
        request.setAttribute(Constants.READ_MODE, ReadMode.READ_WRITE);
        return getActionForward(result, mapping, form);
    }

    public ActionForward update(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = update(form, request, getMessages(request), getErrors(request));
        request.setAttribute(Constants.READ_MODE, ReadMode.READ_WRITE);
        return getActionForward(result, mapping, form);
    }

    public ActionForward add(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = add(form, request, getMessages(request), getErrors(request));
        request.setAttribute(Constants.READ_MODE, ReadMode.READ_WRITE);
        return getActionForward(result, mapping, form);
    }

    public ActionForward delete(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = delete(form, mapping, request, getMessages(request), getErrors(request));
        return getActionForward(result, mapping, form);
    }

    public ActionForward confirmdelete(ActionMapping mapping, T form, HttpServletRequest request,
            HttpServletResponse response) throws BusinessException, SystemException {
        Forward result = confirmdelete(form, mapping, request, getMessages(request), getErrors(request));
        return getActionForward(result, mapping, form);
    }

    public ActionForward validate(ActionMapping mapping, T form, HttpServletRequest request,
            HttpServletResponse response) throws BusinessException, SystemException {
        ActionErrors actionErrors = form.validate(mapping, request);
        if (actionErrors != null && !actionErrors.isEmpty()) {
            request.setAttribute(Globals.ERROR_KEY, actionErrors);
        }

        Forward result = validate(form, request, getMessages(request), getErrors(request));
        return getActionForward(result, mapping, form);
    }

    public ActionForward load(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = load(form, request, getMessages(request), getErrors(request));
        request.setAttribute(Constants.READ_MODE, ReadMode.READ_ONLY);
        return getActionForward(result, mapping, form);
    }

    public ActionForward list(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = list(form, request, getMessages(request), getErrors(request));
        return getActionForward(result, mapping, form);
    }

    public ActionForward stop(ActionMapping mapping, T form, HttpServletRequest request, HttpServletResponse response)
            throws BusinessException, SystemException {
        Forward result = stop(form, request, getMessages(request), getErrors(request));
        return getActionForward(result, mapping, form);
    }

    protected abstract Forward submit(ActionMapping mapping, T form, HttpServletRequest request,
            ActionMessages reqMessages, ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward update(T form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward add(T form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward delete(T form, ActionMapping mapping, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward confirmdelete(T form, ActionMapping mapping, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward validate(T form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward load(T form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected abstract Forward list(T form, HttpServletRequest request, ActionMessages reqMessages,
            ActionMessages reqErrors) throws BusinessException, SystemException;

    protected Forward stop(T form, HttpServletRequest request, ActionMessages reqMessages, ActionMessages reqErrors)
            throws BusinessException, SystemException {
        throw new SystemException("Method not implemented");
    }

    protected Forward getErrorsForMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) {
        String methodName;
        try {
            methodName = getMethodName(mapping, form, request, response);
        } catch (Exception e) {
            logger.warning("Cannot find mapping for method " + mapping.getParameter());
            methodName = Action.LIST.toString();
        }
        Map<String, Forward> map = getMethodErrorMap();
        return (Forward) map.get(methodName);
    }

    private String getMethodName(ActionMapping mapping, ActionForm form, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String parameter = mapping.getParameter();
        if (parameter == null) {
            String message = messages.getMessage("dispatch.handler", mapping.getPath());
            throw new ServletException(message);
        }
        String methodName = getMethodName(mapping, form, request, response, parameter);
        return methodName;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.Action#saveMessages(javax.servlet.http.HttpServletRequest,
     * org.apache.struts.action.ActionMessages)
     */
    protected void saveMessages(HttpServletRequest request, ActionMessages messages) {
        throw new ProgrammerException(
                "You shouldn't use this method, use [request.setAttribute( Globals.MESSAGE_KEY, reqErrors );]");
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.apache.struts.action.Action#saveErrors(javax.servlet.http.HttpServletRequest,
     * org.apache.struts.action.ActionMessages)
     */
    protected final void saveErrors(HttpServletRequest request, ActionMessages errors) {
        throw new ProgrammerException(
                "You shouldn't use this method, use [request.setAttribute( Globals.ERROR_KEY, reqErrors );]");
    }
}
