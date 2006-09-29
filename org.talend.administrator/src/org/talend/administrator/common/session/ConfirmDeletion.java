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
import org.talend.administrator.persistence.proxy.IProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ConfirmDeletion {

    private boolean isDeletable;

    private String urlConfirm;

    private String urlCancel;

    private String message;

    private List<ConfirmDeletionElement> dependencies;

    private String messageDeletable;

    private String messageNotDeletable;

    private boolean deletableFixed = true;

    public ConfirmDeletion(boolean isDeletable, String message, String urlConfirm, String urlCancel) {
        this.isDeletable = isDeletable;
        this.message = message;
        this.urlCancel = urlCancel;
        this.urlConfirm = urlConfirm;
    }

    public ConfirmDeletion(boolean isDeletable, String elementTypeKey, String elementName, String urlConfirm,
            String urlCancel) {
        this(isDeletable, getMessage(isDeletable, elementTypeKey, elementName), urlConfirm, urlCancel);
        messageDeletable = getMessage(true, elementTypeKey, elementName);
        messageNotDeletable = getMessage(false, elementTypeKey, elementName);
    }

    public ConfirmDeletion(String elementTypeKey, String elementName, String urlConfirm, String urlCancel) {
        messageDeletable = getMessage(true, elementTypeKey, elementName);
        messageNotDeletable = getMessage(false, elementTypeKey, elementName);
        this.isDeletable = true;
        this.message = messageDeletable;
        this.urlCancel = urlCancel;
        this.urlConfirm = urlConfirm;
        this.deletableFixed = false;
    }

    private static String getMessage(boolean isDeletable, String elementTypeKey, String elementName) {
        if (elementTypeKey == null) {
            return null;
        }
        String messageKey = (isDeletable ? "common.confirmDelete.message" : "common.confirmDelete.impossible");
        String elementTypeName = MessageHandler.getMessage(elementTypeKey);
        return MessageHandler.getMessage(messageKey, elementTypeName, elementName);
    }

    public boolean isDeletable() {
        return isDeletable;
    }

    public boolean getDeletable() {
        return isDeletable;
    }

    public void setDeletable(boolean deletable) {
        assert (!deletableFixed);
        this.isDeletable = deletable;
        this.message = isDeletable ? messageDeletable : messageNotDeletable;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUrlCancel() {
        return urlCancel;
    }

    public void setUrlCancel(String urlCancel) {
        this.urlCancel = urlCancel;
    }

    public String getUrlConfirm() {
        return urlConfirm;
    }

    public void addElement(ConfirmDeletionElement element) {
        if (dependencies == null) {
            dependencies = new ArrayList<ConfirmDeletionElement>();
        }
        if (element.isBlocking()) {
            setDeletable(false);
        }
        dependencies.add(element);
    }

    public void addDependencies(List<? extends IProxy> list, String property, boolean blocking, String elementTitle,
            Locale locale) {
        if (list != null) {
            if (!list.isEmpty()) {
                if (blocking) {
                    setDeletable(false);
                }
                ConfirmDeletionElement element = new ConfirmDeletionElement(elementTitle, locale, blocking);
                element.updateFromList(list, property);
                addElement(element);
            }
        }
    }

    public List<ConfirmDeletionElement> getBlockingElements() {
        return getElements(true);
    }

    public List<ConfirmDeletionElement> getNotBlockingElements() {
        return getElements(false);
    }

    public int getNotBlockingElementsSize() {
        return getElements(false).size();
    }

    public List<ConfirmDeletionElement> getElements(boolean blocking) {
        List<ConfirmDeletionElement> toReturn = new ArrayList<ConfirmDeletionElement>();

        if (dependencies != null) {
            for (ConfirmDeletionElement deletionProxyElement : dependencies) {
                if (deletionProxyElement.isBlocking() == blocking) {
                    toReturn.add(deletionProxyElement);
                }
            }
        }

        return toReturn;
    }

    public void setElements(List<ConfirmDeletionElement> newDependencies) {
        this.dependencies = newDependencies;
    }
}
