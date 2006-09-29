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
package org.talend.administrator.common.session;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.talend.administrator.common.util.messages.MessageHandler;
import org.talend.administrator.common.util.reflect.BeanAccessorsInvoker;
import org.talend.administrator.persistence.proxy.IProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ConfirmDeletionElement {

    private String elementMessage;

    private boolean blocking;

    private List<String> elementSubList;

    public ConfirmDeletionElement(String elementMessageKey, List<String> elementSubList, Locale locale, boolean blocking) {
        this.elementMessage = MessageHandler.getMessage(elementMessageKey);
        this.elementSubList = elementSubList;
        this.blocking = blocking;
    }

    public ConfirmDeletionElement(String elementMessageKey, Locale locale, boolean blocking) {
        this.elementMessage = MessageHandler.getMessage(elementMessageKey);
        this.blocking = blocking;
    }

    public String getElementMessage() {
        return elementMessage;
    }

    public List<String> getElementSubList() {
        return elementSubList;
    }

    public void setElementSubList(List<String> elementSubList) {
        this.elementSubList = elementSubList;
    }

    public void addToSubList(String item) {
        if (elementSubList == null) {
            elementSubList = new ArrayList<String>();
        }
        elementSubList.add(item);
    }

    public void addToSubList(String item, Locale locale) {
        String msg = MessageHandler.getMessage(item);
        addToSubList(msg);
    }

    public void updateFromList(List<? extends IProxy> list, String property) {
        List<String> subList = new ArrayList<String>();
        for (IProxy pxy : list) {
            String name = (String) new BeanAccessorsInvoker().getBeanPropertyValue(pxy, property);
            subList.add(name);
        }
        elementSubList = subList;
    }

    public boolean isBlocking() {
        return blocking;
    }

    public boolean getBlocking() {
        return blocking;
    }
}
