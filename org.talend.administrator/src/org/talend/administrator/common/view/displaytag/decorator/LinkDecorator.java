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

import org.displaytag.interfaces.descriptor.ContextDescriptor;
import org.displaytag.interfaces.descriptor.Contextual;
import org.talend.administrator.common.util.messages.MessageHandler;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class LinkDecorator implements org.displaytag.decorator.ColumnDecorator, Contextual {

    private static final char DELIMITER = '"';

    private static final char EQUALS = '=';

    private static final char SPACE = ' ';

    ContextDescriptor contextDescriptor;

    public final String decorate(Object columnValue) {
        if (columnValue == null) {
            return null;
        }
        Link linkedObject = (Link) columnValue;
        if (linkedObject.getContent() == null) {
            return null;
        }

        return "<a href=\"" + linkedObject.getHref() + "\" "
                + (linkedObject.getOthersAttrVal() == null ? "" : linkedObject.getOthersAttrVal())
                + (linkedObject.getOthersAttrValMap() == null ? "" : linkedObject.getOthersAttrValMap().toString())
                + (linkedObject.getAttrValToTranslateMap() == null ? "" : getTranslattedAttributes(linkedObject)) + ">"
                + getTranslatedContent(linkedObject) + "</a>";
    }

    private String getTranslattedAttributes(Link linkedObject) {
        StringBuffer toReturn = new StringBuffer();

        for (Object okey : linkedObject.getAttrValToTranslateMap().keySet()) {
            String key = okey.toString();
            String value = MessageHandler.getMessage((String) linkedObject.getAttrValToTranslateMap().get(key));
            toReturn.append(SPACE).append(key).append(EQUALS).append(DELIMITER).append(value).append(DELIMITER);
        }

        return toReturn.toString();
    }

    public void setContextDescriptor(ContextDescriptor contextDescriptor) {
        this.contextDescriptor = contextDescriptor;
    }

    private String getTranslatedContent(Link link) {
        if (link.isContentToTranslate()) {
            return (MessageHandler.getMessage(link.getContent().toString()));
        } else {
            return link.getContent().toString();
        }
    }
}
