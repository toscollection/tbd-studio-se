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
package org.talend.administrator.persistence.proxy;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.DBException;
import org.talend.administrator.common.util.messages.MessageHandler;
import org.talend.administrator.common.view.displaytag.checkbox.TalendInputCheckboxField;
import org.talend.core.model.properties.UserRole;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class UserRoleProxy implements IProxy<UserRole> {

    private UserRole userRole = null;

    public UserRoleProxy() {
    }
    
    public UserRoleProxy(UserRole userRole) {
        this.userRole = userRole;
    }

    public TalendInputCheckboxField getInputCheckboxField() {
        return null;
    }

    public void setInputCheckboxField(TalendInputCheckboxField inputCheckboxField) {
    }

    public boolean equals(Object arg0) {
        return this.userRole.equals(arg0);
    }

    public int getId() {
        return this.userRole.getId();
    }

    public String getName() {
        return this.userRole.getName();
    }

    public int hashCode() {
        return this.userRole.hashCode();
    }

    public void setId(int id) {
        this.userRole.setId(id);
    }

    public void setName(String name) {
        this.userRole.setName(name);
    }

    public String toString() {
        return this.userRole.toString();
    }

    public List<UserRoleProxy> toProxy(List<UserRole> userRoles) {
        List<UserRoleProxy> userRolesAsProxies = new ArrayList<UserRoleProxy>();
        for (UserRole userRole : userRoles) {
            userRolesAsProxies.add(toProxy(userRole));
        }
        return userRolesAsProxies;
    }

    public UserRoleProxy toProxy(UserRole userRole) {
        return new UserRoleProxy(userRole);
    }

    public UserRole toPersistent() {
        return this.userRole;
    }

    public void fillForm(ActionForm form) throws DBException, BusinessException {
    }
    
    public String getLocalizedLabel() {
        return MessageHandler.getMessage(userRole.getLocalizedLabel());
    }
    
    public void setLocalizedLabel(String label) {
        userRole.setLocalizedLabel(label);
    }
}
