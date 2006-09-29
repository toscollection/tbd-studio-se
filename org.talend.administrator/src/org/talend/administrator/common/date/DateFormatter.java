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
package org.talend.administrator.common.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.talend.administrator.common.exception.BusinessException;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public final class DateFormatter {
    
    private DateFormatter() {
        
    }

    private static final SimpleDateFormat DATEFORMATTER = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public static Date parseString(String source) throws BusinessException {
        try {
            return DATEFORMATTER.parse(source);
        } catch (ParseException e) {
            throw new BusinessException("Not a valid date");
        }
    }

    public static String formatDate(Date source) {
        if (source != null) {
            return DATEFORMATTER.format(source);
        } else {
            return "";
        }
    }
}
