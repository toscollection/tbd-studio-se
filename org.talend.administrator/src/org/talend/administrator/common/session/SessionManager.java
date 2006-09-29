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
package org.talend.administrator.common.session;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.talend.administrator.persistence.proxy.UserProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class SessionManager {
    
    private SessionManager() {
        
    }

    public static final String USER_KEY = "USER_KEY";

    public static final String ORIGINAL_ROLE = "ORIGINAL_ROLE";

    private static final String USER_KEY_STORED = "USER_KEY_STORED";

    private static HashMap<Integer, UserProxy> usersCurrentlyConnected;

    public static final void saveCurrentUserSession(HttpServletRequest request) {
        UserProxy session = getUserSession(request);
        request.getSession().setAttribute(USER_KEY_STORED, session);
    }

    public static final void createUserSession(HttpServletRequest request, UserProxy user) {
        addConnectedUser(user);
        request.getSession().setAttribute(USER_KEY, user);
        request.getSession().setAttribute(ORIGINAL_ROLE, user.getUserRole().getName());
    }

    public static final void removeUserSession(HttpServletRequest request) {
        removeConnectedUser(getUserSession(request));
        request.getSession().removeAttribute(USER_KEY);
    }

    @SuppressWarnings("unchecked")
    public static final UserProxy getUserSession(HttpServletRequest request) {
        return (UserProxy) request.getSession().getAttribute(USER_KEY);
    }

    @SuppressWarnings("unchecked")
    public static final String getUserRoleSession(HttpServletRequest request) {
        return (String) request.getSession().getAttribute(ORIGINAL_ROLE);
    }

    @SuppressWarnings("unchecked")
    public static final UserProxy getStoredUserSession(HttpServletRequest request) {
        return (UserProxy) request.getSession().getAttribute(USER_KEY_STORED);
    }

    @SuppressWarnings("unchecked")
    public static final UserProxy restoreUserSession(HttpServletRequest request) throws SessionManagerException {
        UserProxy storedSession = getStoredUserSession(request);
        if (storedSession == null) {
            throw new SessionManagerException("There are no stored user session");
        }
        createUserSession(request, storedSession);
        request.getSession().removeAttribute(USER_KEY_STORED);
        return (UserProxy) getUserSession(request);
    }

    public static final void invalidate(HttpServletRequest request) throws SessionManagerException {
        request.getSession().invalidate();
        usersCurrentlyConnected = new HashMap<Integer, UserProxy>();
    }

    public static HashMap<Integer, UserProxy> getConnectedUsers() {
        return usersCurrentlyConnected;
    }

    private static void addConnectedUser(UserProxy user) {
        if (usersCurrentlyConnected == null) {
            usersCurrentlyConnected = new HashMap<Integer, UserProxy>();
        }
        usersCurrentlyConnected.put(user.getId(), user);
    }

    private static void removeConnectedUser(UserProxy user) {
        usersCurrentlyConnected.remove(user.getId());
    }
}
