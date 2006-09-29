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
package org.talend.administrator.common.validator;

import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.validator.Field;
import org.apache.commons.validator.GenericTypeValidator;
import org.apache.commons.validator.GenericValidator;
import org.apache.commons.validator.Validator;
import org.apache.commons.validator.ValidatorAction;
import org.apache.commons.validator.util.ValidatorUtils;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.validator.FieldChecks;
import org.apache.struts.validator.Resources;
import org.talend.administrator.common.constants.Constants;
import org.talend.administrator.common.exception.ExceptionHandler;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class TalendFieldChecks extends FieldChecks implements Serializable {

    private static final long serialVersionUID = 4690258450664060077L;

    public static Object validateDate(Object bean, ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {
        return validateDate(bean, va, field, errors, validator, request, Constants.DATE_PATTERN);
    }

    public static Object validateTime(Object bean, ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {
        return validateDate(bean, va, field, errors, validator, request, Constants.TIME_PATTERN);
    }

    public static Object validateDateTime(Object bean, ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request) {
        return validateDate(bean, va, field, errors, validator, request, Constants.DATE_PATTERN
                + Constants.PATTERN_SEPARATOR + Constants.PRECISETIME_PATTERN);
    }

    private static Object validateDate(Object bean, ValidatorAction va, Field field, ActionMessages errors,
            Validator validator, HttpServletRequest request, String pattern) {

        Object result = null;
        String value = null;
        if (isString(bean)) {
            value = (String) bean;
        } else {
            value = ValidatorUtils.getValueAsString(bean, field.getProperty());
        }
        String datePattern = pattern;
        if (datePattern == null || datePattern.length() == 0) {
            throw new IllegalArgumentException("Pattern cannot be null !");
        }

        if (GenericValidator.isBlankOrNull(value)) {
            return Boolean.TRUE;
        }

        try {
            result = GenericTypeValidator.formatDate(value, datePattern, true);
        } catch (Exception e) {
            ExceptionHandler.process(FieldChecks.class.getName(), "validateDate", e);
        }

        if (result == null) {
            errors.add(field.getKey(), Resources.getActionMessage(validator, request, va, field));
        }

        return result == null ? Boolean.FALSE : result;
    }
}
