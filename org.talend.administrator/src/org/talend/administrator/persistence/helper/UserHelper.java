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
package org.talend.administrator.persistence.helper;

import java.util.ArrayList;
import java.util.List;

import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.DBException;
import org.talend.administrator.common.exception.ItemNotFoundException;
import org.talend.administrator.common.exception.SystemException;
import org.talend.administrator.common.exception.UnicityException;
import org.talend.administrator.common.persistence.PersistenceUtil;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.User;
import org.talend.core.model.properties.UserRole;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class UserHelper {

    private static UserHelper instance;

    private UserHelper() {
        super();
    }

    public static synchronized UserHelper getInstance() {
        if (instance == null) {
            instance = new UserHelper();
        }
        return instance;
    }

    public User findByLogin(String login) {
        User user = PropertiesFactory.eINSTANCE.createUser();
        user.setLogin(login);
        try {
            return PersistenceUtil.getPersistenceAdapter().retrieveUnique(user, new String[] { "login" }, new String[] { "role" });
        } catch (ItemNotFoundException e) {
            // do nothing
        } catch (UnicityException e) {
            // do nothing
        } catch (DBException e) {
            // do nothing
        } catch (BusinessException e) {
            // do nothing
        }
        return null;
    }

    public User findById(int id) {
        User user = PropertiesFactory.eINSTANCE.createUser();
        user.setId(id);
        try {
            return PersistenceUtil.getPersistenceAdapter().retrieveUnique(user, new String[] { "id" }, new String[] { "role" });
        } catch (ItemNotFoundException e) {
            // do nothing
        } catch (UnicityException e) {
            // do nothing
        } catch (DBException e) {
            // do nothing
        } catch (BusinessException e) {
            // do nothing
        }
        return null;
    }

    public List<User> findByRole(UserRole role) {
        User user = PropertiesFactory.eINSTANCE.createUser();
        user.setRole(role);
        try {
            return PersistenceUtil.getPersistenceAdapter().retrieve(user, new String[] { "role" });
        } catch (DBException e) {
            // do nothing
        }
        return null;
    }

    public List<User> listUsers() {
        User user = PropertiesFactory.eINSTANCE.createUser();
        try {
            return PersistenceUtil.getPersistenceAdapter().retrieve(user, null, new String[] { "role" });
        } catch (DBException e) {
            e.printStackTrace();
            return new ArrayList<User>();
        }
    }

    public boolean save(User user) {
        try {
            PersistenceUtil.getPersistenceAdapter().saveOrUpdate(user);
            return true;
        } catch (SystemException e) {
            e.printStackTrace();
        }
        return false;
    }
}
