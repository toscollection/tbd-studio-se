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
package org.talend.administrator.common.view.displaytag.checkbox;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.displaytag.decorator.data.InputCheckboxField;
import org.displaytag.form.IFormField;
import org.displaytag.manager.DisplayTagManager;
import org.talend.administrator.common.exception.BusinessException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class CheckBoxManager extends DisplayTagManager {

    private static final String VALUE_CHECKED = "true";

    private static final String COLUMN_ID = "inputCheckboxField";

    public static final String CHECKBOX_ERROR_MAX_ONE = "ex-winthor.common.checkbox.maxOne";

    public static final String CHECKBOX_ERROR_MIN_ONE = "ex-winthor.common.checkbox.minOne";

    private String keySession;

    private boolean persistance;

    private List<? extends ICheckBoxable> objects;

    public CheckBoxManager(String keySession, boolean persistance, List<? extends ICheckBoxable> listObjects) {
        this.persistance = persistance;
        this.keySession = keySession;
        this.objects = listObjects;
    }

    public void initiate(List<? extends ICheckBoxable> listObjects) {
        for (ICheckBoxable object : listObjects) {
            object.setInputCheckboxField(new TalendInputCheckboxField(this.getFormFieldManager(), COLUMN_ID, object,
                    false));
        }
    }

    public void update(HttpServletRequest request) {
        if (persistance) {
            this.getFormFieldManager().updateFields(request,
                    this.getFormFieldManager().getRegisteredFieldsByColumn(COLUMN_ID));
        }
    }

    public void updateAll(HttpServletRequest request) {
        if (persistance) {
            this.getFormFieldManager().updateFields(request, this.getFormFieldManager().getRegisteredFields());
        }
    }

    public void update(List<? extends ICheckBoxable> listObjects, Collection<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            return;
        }
        for (ICheckBoxable object : listObjects) {
            if (ids.contains(object.getId())) {
                object.getInputCheckboxField().setChecked(true);
            }
        }
    }

    public void update(List<? extends ICheckBoxable> listObjects, List<? extends ICheckBoxable> listObjectsToChecked) {
        if (listObjectsToChecked != null) {
            Collection<Integer> ids = new ArrayList<Integer>();
            for (ICheckBoxable cb : listObjectsToChecked) {
                ids.add(cb.getId());
            }
            update(listObjects, ids);
        }
    }

    public void update(List<? extends ICheckBoxable> listObjects, ICheckBoxable objectToCheck) {
        if (objectToCheck != null) {
            Collection<Integer> ids = new ArrayList<Integer>();
            ids.add(objectToCheck.getId());
            update(listObjects, ids);
        }
    }

    public <T extends ICheckBoxable> List<T> getManyCheckedObjects(List<T> source) throws BusinessException {
        List<T> toReturn = new ArrayList<T>();

        for (T cb : source) {
            if (VALUE_CHECKED.equals(cb.getInputCheckboxField().getValue())) {
                toReturn.add(cb);
            }
        }
        if (toReturn.size() < 1) {
            throw new BusinessException(CHECKBOX_ERROR_MIN_ONE);
        }
        return toReturn;
    }

    public <T extends ICheckBoxable> T getSingleCheckedObject(List<T> source) throws BusinessException {
        List<T> checkBoxable = getManyCheckedObjects(source);

        if (checkBoxable.size() != 1) {
            throw new BusinessException(CHECKBOX_ERROR_MAX_ONE);
        }

        return checkBoxable.get(0);
    }

    public List<? extends ICheckBoxable> getManyCheckedObjects(HttpServletRequest request,
            boolean atLeastOneCheckBoxSelected) throws BusinessException {
        List<ICheckBoxable> checkBoxable = new ArrayList<ICheckBoxable>();

        IFormField[] formFields = getFormFieldManager().getRegisteredFieldsByColumn(COLUMN_ID);
        boolean checked = false;
        for (IFormField formField : formFields) {
            InputCheckboxField inputCheckboxField = (InputCheckboxField) formField;
            checked = VALUE_CHECKED.equals(request.getParameter(inputCheckboxField.getName()));

            if (checked) {
                checkBoxable.add((ICheckBoxable) formField.getParent());
            }
        }

        if ((atLeastOneCheckBoxSelected) && (checkBoxable.size() < 1)) {
            throw new BusinessException(CHECKBOX_ERROR_MIN_ONE);
        }

        return checkBoxable;
    }

    public List<? extends ICheckBoxable> getManyCheckedObjects(HttpServletRequest request) throws BusinessException {
        return getManyCheckedObjects(request, true);
    }

    public ICheckBoxable getObject(Integer id) {
        for (ICheckBoxable cb : this.objects) {
            if (cb.getId() == id) {
                return cb;
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public ICheckBoxable getSingleCheckedObject(HttpServletRequest request) throws BusinessException {
        List<ICheckBoxable> checkBoxable = (List<ICheckBoxable>) getManyCheckedObjects(request);

        if (checkBoxable.size() != 1) {
            throw new BusinessException(CHECKBOX_ERROR_MAX_ONE);
        }

        return checkBoxable.get(0);
    }

    private void saveManager(HttpServletRequest request) {
        request.getSession().setAttribute(keySession, this);
    }

    public void deleteManager(HttpServletRequest request) {
        request.getSession().removeAttribute(keySession);
    }

    public static CheckBoxManager createInstance(String keySession, boolean persistance, HttpServletRequest request,
            List<? extends ICheckBoxable> listObjects) {
        CheckBoxManager winthorTagManager = new CheckBoxManager(keySession, persistance, listObjects);

        winthorTagManager.initiate(listObjects);

        winthorTagManager.saveManager(request);

        return winthorTagManager;
    }

    public static CheckBoxManager getInstance(HttpServletRequest request, String keySession) {
        CheckBoxManager winthorTagManager = (CheckBoxManager) request.getSession().getAttribute(keySession);
        return winthorTagManager;
    }

    public static boolean deleteIfExists(HttpServletRequest request, String keySession) {
        CheckBoxManager winthorTagManager = getInstance(request, keySession);
        if (winthorTagManager == null) {
            return false;
        }
        winthorTagManager.deleteManager(request);
        return true;
    }

}
