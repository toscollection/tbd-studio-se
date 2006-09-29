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
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.struts.action.ActionForm;
import org.talend.administrator.business.UserHandler;
import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.date.DateFormatter;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.DBException;
import org.talend.administrator.common.view.displaytag.checkbox.TalendInputCheckboxField;
import org.talend.administrator.common.view.displaytag.decorator.Link;
import org.talend.administrator.controller.user.form.UserForm;
import org.talend.core.model.properties.PropertiesFactory;
import org.talend.core.model.properties.UserRole;
import org.talend.core.model.properties.User;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class UserProxy implements IProxy<User> {

    private User user = null;

    private TalendInputCheckboxField inputCheckboxField;

    public UserProxy() {
    }
    
    public UserProxy(User user) {
        this.user = user;
    }

    public UserProxy(UserForm form, Constants.Action mode) {
        user = PropertiesFactory.eINSTANCE.createUser();
        setId(form.getId());
        setLogin(form.getLogin());
        setPassword(form.getPassword());
        setLastName(form.getLastName());
        setFirstName(form.getFirstName());
        setComponentModifier(form.getComponentModifier());

        UserRoleProxy role = UserHandler.getUserRoleByPK(form.getRoleId());
        setUserRole(role.toPersistent());
        setComment(form.getComment());

        setDeleted(!form.isActive());

        switch(mode) {
            case ADD :
                // user creation
                setDateCreation(Calendar.getInstance().getTime());
                setActive(true);
                setDateDelete(null);
                break;
            case UPDATE :
                // user alter
                setDateCreation(form.getCreationDate());
                setDateDelete(form.getSuppressionDate());
                break;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.view.ICheckBoxable#getInputCheckboxField()
     */
    public TalendInputCheckboxField getInputCheckboxField() {
        return inputCheckboxField;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.administrator.common.view.ICheckBoxable#setInputCheckboxField(org.talend.administrator.common.view.TalendInputCheckboxField)
     */
    public void setInputCheckboxField(TalendInputCheckboxField inputCheckboxField) {
        this.inputCheckboxField = inputCheckboxField;
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getFirstName()
     */
    public String getFirstName() {
        return this.user.getFirstName();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getLastName()
     */
    public String getLastName() {
        return this.user.getLastName();
    }

    /**
     * @param firstName
     * @see org.talend.administrator.persistence.pojo.User#setFirstName(java.lang.String)
     */
    public void setFirstName(String firstName) {
        this.user.setFirstName(firstName);
    }

    /**
     * @param lastName
     * @see org.talend.administrator.persistence.pojo.User#setLastName(java.lang.String)
     */
    public void setLastName(String lastName) {
        this.user.setLastName(lastName);
    }

    /**
     * @param arg0
     * @return
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object arg0) {
        return this.user.equals(arg0);
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getComment()
     */
    public String getComment() {
        return this.user.getComment();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getDateCreation()
     */
    public Date getDateCreation() {
        return this.user.getCreationDate();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getDateDelete()
     */
    public Date getDateDelete() {
        return this.user.getDeleteDate();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getId()
     */
    public int getId() {
        return this.user.getId();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getLogin()
     */
    public String getLogin() {
        return this.user.getLogin();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getPassword()
     */
    public String getPassword() {
        return this.user.getPassword();
    }

    /**
     * @return
     * @see org.talend.administrator.persistence.pojo.User#getUserRole()
     */
    public UserRole getUserRole() {
        return this.user.getRole();
    }

    /**
     * @return
     * @see java.lang.Object#hashCode()
     */
    public int hashCode() {
        return this.user.hashCode();
    }

    /**
     * @param comment
     * @see org.talend.administrator.persistence.pojo.User#setComment(java.lang.String)
     */
    public void setComment(String comment) {
        this.user.setComment(comment);
    }

    /**
     * @param dateCreation
     * @see org.talend.administrator.persistence.pojo.User#setDateCreation(java.util.Date)
     */
    public void setDateCreation(Date dateCreation) {
        this.user.setCreationDate(dateCreation);
    }

    /**
     * @param dateDelete
     * @see org.talend.administrator.persistence.pojo.User#setDateDelete(java.util.Date)
     */
    public void setDateDelete(Date dateDelete) {
        this.user.setDeleteDate(dateDelete);
    }

    /**
     * @param deleted
     * @see org.talend.administrator.persistence.pojo.User#setDeleted(boolean)
     */
    public void setDeleted(boolean deleted) {
        this.user.setDeleted(deleted);
    }

    /**
     * @param id
     * @see org.talend.administrator.persistence.pojo.User#setId(int)
     */
    public void setId(int id) {
        this.user.setId(id);
    }

    /**
     * @param login
     * @see org.talend.administrator.persistence.pojo.User#setLogin(java.lang.String)
     */
    public void setLogin(String login) {
        this.user.setLogin(login);
    }

    /**
     * @param password
     * @see org.talend.administrator.persistence.pojo.User#setPassword(java.lang.String)
     */
    public void setPassword(String password) {
        this.user.setPassword(password);
    }

    /**
     * @param userRole
     * @see org.talend.administrator.persistence.pojo.User#setUserRole(org.talend.administrator.persistence.pojo.UserRole)
     */
    public void setUserRole(UserRole userRole) {
        this.user.setRole(userRole);
    }

    public boolean getComponentModifier() {
        return this.user.isAllowedToModifyComponents();
    }

    public void setComponentModifier(boolean componentModifier) {
        this.user.setAllowedToModifyComponents(componentModifier);
    }

    public String toString() {
        return this.user.toString();
    }

    public List<UserProxy> toProxy(List<User> users) {
        List<UserProxy> usersAsProxies = new ArrayList<UserProxy>();
        for (User user : users) {
            usersAsProxies.add(toProxy(user));
        }
        return usersAsProxies;
    }

    public UserProxy toProxy(User user) {
        return new UserProxy(user);
    }

    public User toPersistent() {
        return this.user;
    }

    public boolean getActive() {
        return !user.isDeleted();
    }

    public void setActive(boolean active) {
        user.setDeleted(!active);
    }

    public String getRole() {
        return getUserRole().getLocalizedLabel();
    }

    public Link getDisplayLink() {
        StringBuffer url = new StringBuffer();
        url.append("actionUser.do");
        url.append("?id=").append(getId());
        url.append("&method=");
        url.append(Constants.LOAD);

        Link link = new Link(getLogin(), url.toString(), null);
        return link;
    }

    public boolean isAdmin() {
        if (getUserRole() != null) {
            if (getUserRole().getId() == 1) {
                return true;
            }
        }
        return false;
    }

    public void fillForm(ActionForm form) throws DBException, BusinessException {
        UserForm userForm = (UserForm) form;
        userForm.setId(getId());
        userForm.setLogin(getLogin());
        userForm.setLastName(getLastName());
        userForm.setFirstName(getFirstName());
        userForm.setPassword(getPassword());
        userForm.setActive(getActive());
        userForm.setCreationDate(getDateCreation());
        userForm.setSuppressionDate(getDateDelete());
        userForm.setComment(getComment());
        userForm.setRoleId(getUserRole().getId());
        userForm.setComponentModifier(getComponentModifier());
    }
    
    public String getDateDeleteString() {
        return DateFormatter.formatDate(getDateDelete());
    }
    
    public String getDateCreationString() {
        return DateFormatter.formatDate(getDateCreation());
    }
}
