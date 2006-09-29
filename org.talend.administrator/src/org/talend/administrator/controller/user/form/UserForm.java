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
package org.talend.administrator.controller.user.form;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.Globals;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;
import org.talend.administrator.business.UserHandler;
import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.date.DateFormatter;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.session.SessionManager;
import org.talend.administrator.common.util.messages.MessageHandler;
import org.talend.administrator.persistence.proxy.UserProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/> $Id$
 */
public class UserForm extends ValidatorForm {

    private static final long serialVersionUID = 4148998995387032609L;

    private Integer id;

    private String login;

    private String lastName;

    private String firstName;

    private int roleId;

    private String password;

    private Date creationDate;

    private Date suppressionDate;

    private boolean active;

    private String comment;

    private boolean componentModifier;

    public ActionErrors validate(ActionMapping arg0, HttpServletRequest arg1, Constants.Action mode) {
        ActionErrors errors = super.validate(arg0, arg1);
        
        switch (mode) {
        case UPDATE:
            // verif si role=user, que autre admin >=1
            checkLastAdministrator(errors);
            break;
        case DELETE:
            errors.clear();
            UserProxy actualUser = UserHandler.getUserByPK(this.getId());
            
            // si déjà supprimé, pas la peine
            if (!actualUser.getActive()) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("user.error.alreadyDeleted", MessageHandler
                        .getMessage("user.error.alreadyDeleted")));
            }

            // verif si role=user, que autre admin >=1
            checkLastAdministrator(errors);

            // suppression impossible de l'internaute actuellement connecté à l'administrator
            if (SessionManager.getConnectedUsers().containsKey(this.getId())) {
                errors.add(Globals.ERROR_KEY, new ActionMessage("user.error.userConnected", MessageHandler
                        .getMessage("user.error.userConnected")));
            }

            // PTODO MHI suppression impossible d'un user à l'origine d'un verrou actif.
            break;
        default:
            // do nothing
        }

        return errors;
    }

    private void checkLastAdministrator(ActionErrors errors) {
        if (this.getRoleId() != 1) {
            List<UserProxy> users = UserHandler.loadAdministrators();

            if (users.size() == 1) {
                if ((users.get(0)).getId() == this.getId()) {
                    errors.add(Globals.ERROR_KEY, new ActionMessage("user.error.lastAdministrator", MessageHandler
                            .getMessage("user.error.lastAdministrator")));
                }
            }
        }
    }

    public Date getCreationDate() {
        return this.creationDate;
    }

    public void setCreationDate(Date dateCreation) {
        this.creationDate = dateCreation;
    }

    public void setCreationDateString(String dateCreation) {
        try {
            this.creationDate = DateFormatter.parseString(dateCreation);
        } catch (BusinessException e) {
            this.creationDate = Calendar.getInstance().getTime();
        }
    }

    public String getCreationDateString() {
        return DateFormatter.formatDate(this.creationDate);
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return this.login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public Date getSuppressionDate() {
        return this.suppressionDate;
    }

    public void setSuppressionDate(Date deleteDate) {
        this.suppressionDate = deleteDate;
    }

    public String getSuppressionDateString() {
        return DateFormatter.formatDate(this.suppressionDate);
    }

    public void setSuppressionDateString(String deleteDate) {
        try {
            this.suppressionDate = DateFormatter.parseString(deleteDate);
        } catch (BusinessException e) {
            this.suppressionDate = Calendar.getInstance().getTime();
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public boolean getComponentModifier() {
        return this.componentModifier;
    }

    public void setComponentModifier(boolean componentModifier) {
        this.componentModifier = componentModifier;
    }
}
