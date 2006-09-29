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
package org.talend.administrator.common.view.displaytag.decorator;

import org.displaytag.decorator.ColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.interfaces.Formatable;
import org.displaytag.interfaces.descriptor.ContextDescriptor;
import org.displaytag.interfaces.descriptor.Contextual;
import org.talend.administrator.common.util.messages.MessageHandler;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class I18nDecorator implements ColumnDecorator, Contextual, Formatable {

    ContextDescriptor contextDescriptor;

    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.decorator.ColumnDecorator#decorate(java.lang.Object)
     */
    public String decorate(Object columnValue) throws DecoratorException {
        return format(columnValue);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.interfaces.descriptor.Contextual#setContextDescriptor(org.displaytag.interfaces.descriptor.ContextDescriptor)
     */
    public void setContextDescriptor(ContextDescriptor contextDescriptor) {
        this.contextDescriptor = contextDescriptor;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.displaytag.interfaces.Formatable#format(java.lang.Object)
     */
    public String format(Object value) {
        if (value != null) {
            return MessageHandler.getMessage(value.toString());
        }
        return null;
    }
}
