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
package org.talend.administrator.business;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.persistence.PersistenceUtil;
import org.talend.administrator.common.persistence.hibernate.HibernatePersistenceAdapter;
import org.talend.administrator.common.session.SessionManager;
import org.talend.administrator.common.session.SessionManagerException;
import org.talend.administrator.common.view.displaytag.checkbox.CheckBoxManager;
import org.talend.administrator.persistence.helper.UserHelper;
import org.talend.administrator.persistence.helper.UserRoleHelper;
import org.talend.administrator.persistence.proxy.UserProxy;
import org.talend.administrator.persistence.proxy.UserRoleProxy;
import org.talend.core.model.properties.User;
import org.talend.core.model.properties.UserRole;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class UserHandler {

    private static UserHandler instance;

    private HibernatePersistenceAdapter hibernatePersistenceAdapter;

    private UserHandler() {
        super();
    }

    public static synchronized UserHandler getInstance() {
        if (instance == null) {
            instance = new UserHandler();
        }
        instance.setHibernatePersistenceAdapter(PersistenceUtil.getPersistenceAdapter());
        return instance;
    }

    public static boolean authenticate(String login, String password, HttpServletRequest request) {
        User user = UserHelper.getInstance().findByLogin(login);
        if (user != null) {
            if (user.getPassword().compareTo(password) == 0) {
                SessionManager.createUserSession(request, new UserProxy(user));
                return true;
            }
        }
        return false;
    }

    public static void unauthenticate(HttpServletRequest request) {
        try {
            SessionManager.invalidate(request);
        } catch (SessionManagerException e) {
            // do nothing
        }
    }

    public static List<UserProxy> listUsers() {
        List<UserProxy> users = new UserProxy().toProxy(UserHelper.getInstance().listUsers());
        return users;
    }

    public static UserProxy getUserByPK(int id) {
        UserProxy user = new UserProxy(UserHelper.getInstance().findById(id));
        return user;
    }
    
    public static List<UserProxy> getUsersByRole(UserRole role) {
        List<UserProxy> users = new UserProxy().toProxy(UserHelper.getInstance().findByRole(role));
        return users;
    }

    public static UserRoleProxy getUserRoleByPK(int id) {
        UserRoleProxy userRole = new UserRoleProxy(UserRoleHelper.getInstance().findById(id));
        return userRole;
    }

    public HibernatePersistenceAdapter getHibernatePersistenceAdapter() {
        return this.hibernatePersistenceAdapter;
    }

    public void setHibernatePersistenceAdapter(HibernatePersistenceAdapter hibernatePersistenceAdapter) {
        this.hibernatePersistenceAdapter = hibernatePersistenceAdapter;
    }

    public boolean saveUser(UserProxy userProxy) {
        return UserHelper.getInstance().save(userProxy.toPersistent());
    }
    
    public static List<UserRoleProxy> listUserRoles() {
        List<UserRoleProxy> userRoles = new UserRoleProxy().toProxy(UserRoleHelper.getInstance().listUserRoles());
        return userRoles;
    }

    public static UserProxy getCurrentUser(HttpServletRequest request, String sessionKey) throws BusinessException {
        UserProxy user = null;
        Integer id = null;
        try {
            id = Integer.parseInt(request.getParameter("id"));
            user = UserHandler.getUserByPK(id);
        } catch (NumberFormatException e) {
            // nothing to do, try other cases...
        }
        if (user == null) {
            CheckBoxManager checkBoxManager = CheckBoxManager.getInstance(request, sessionKey);
            user = (UserProxy) checkBoxManager.getSingleCheckedObject(request);
        }
        return user;
    }

    public static void setUserRolesInSession(HttpServletRequest request) {
        List<UserRoleProxy> userRoles = UserHandler.listUserRoles();
        request.getSession().setAttribute(Constants.USER_ROLE, userRoles);
    }
    
    public static List<UserProxy> loadAdministrators() {
        UserRole userRole = UserRoleHelper.getInstance().findById(1);
        return UserHandler.getUsersByRole(userRole);
    }
}
