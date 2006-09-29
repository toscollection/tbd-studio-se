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
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.EMap;
import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.exception.BusinessException;
import org.talend.administrator.common.exception.DBException;
import org.talend.administrator.common.view.displaytag.checkbox.TalendInputCheckboxField;
import org.talend.administrator.common.view.displaytag.decorator.Link;
import org.talend.core.model.properties.Project;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ProjectProxy implements IProxy<Project> {

    private Project project = null;

    private TalendInputCheckboxField inputCheckboxField;

    public ProjectProxy(Project project) {
        this.project = project;
    }

//    public ProjectProxy(ProjectForm form, Constants.Action mode) {
//        project = PropertiesFactory.eINSTANCE.createProject();
        // setId(form.getId());
        // setLogin(form.getLogin());
        // setPassword(form.getPassword());
        // setLastName(form.getLastName());
        // setFirstName(form.getFirstName());
        // setComponentModifier(form.getComponentModifier());
        //
        // UserRoleProxy role = UserHandler.getUserRoleByPK(form.getRoleId());
        // setUserRole(UserRoleProxy.toDAO(role));
        // setComment(form.getComment());
        //
        // setDeleted(!form.isActive());
        //
        // switch(mode) {
        // case ADD :
        // // user creation
        // setDateCreation(Calendar.getInstance().getTime());
        // setActive(true);
        // setDateDelete(null);
        // break;
        // case UPDATE :
        // // user alter
        // setDateCreation(form.getCreationDate());
        // setDateDelete(form.getSuppressionDate());
        // break;
        // }
//    }

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

    public List<ProjectProxy> toProxy(List<Project> projects) {
        List<ProjectProxy> projectsAsProxies = new ArrayList<ProjectProxy>();
        for (Project project : projects) {
            projectsAsProxies.add((ProjectProxy) toProxy(project));
        }
        return projectsAsProxies;
    }

    public ProjectProxy toProxy(Project project) {
        return new ProjectProxy(project);
    }

    public Project toPersistent() {
        return this.project;
    }

    public Link getDisplayLink() {
        StringBuffer url = new StringBuffer();
        url.append("actionUser.do");
        url.append("?id=").append(getId());
        url.append("&method=");
        url.append(Constants.LOAD);

        Link link = new Link(getName(), url.toString(), null);
        return link;
    }

    public void fillForm(ActionForm form) throws DBException, BusinessException {
//        ProjectForm projectForm = (ProjectForm) form;
//        userForm.setId(getId());
//        userForm.setLogin(getLogin());
//        userForm.setLastName(getLastName());
//        userForm.setFirstName(getFirstName());
//        userForm.setPassword(getPassword());
//        userForm.setActive(getActive());
//        userForm.setCreationDate(getDateCreation());
//        userForm.setSuppressionDate(getDateDelete());
//        userForm.setComment(getComment());
//        userForm.setRoleId(getUserRole().getId());
//        userForm.setComponentModifier(getComponentModifier());
    }

    public EList getDocumentationStatus() {
        return this.project.getDocumentationStatus();
    }

    public int getId() {
        return this.project.getId();
    }

    public EMap getItemStates() {
        return this.project.getItemStates();
    }

    public String getName() {
        return this.project.getName();
    }

    public EList getTechnicalStatus() {
        return this.project.getTechnicalStatus();
    }

    public EList getUsers() {
        return this.project.getUsers();
    }

    public void setId(int value) {
        this.project.setId(value);
    }

    public void setName(String value) {
        this.project.setName(value);
    }
}
