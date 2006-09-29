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
package org.talend.administrator.common.util.messages;

import java.util.Locale;

/**
 * DOC mhirt  class global comment. Detailled comment
 * <br/>
 * $Id$
 */
public final class MessageHandler {
       
    private MessageHandler() {
    }

    private static TalendMessageResourcesFactory talendMessageResourcesFactory = null;

    private static TalendMessageResources talendMessageResources = null;

    private static TalendMessageResources createResources(String config) {
        if (MessageHandler.talendMessageResources == null) {
            talendMessageResourcesFactory = new TalendMessageResourcesFactory();
            talendMessageResources = (TalendMessageResources) talendMessageResourcesFactory.createResources(config);
        }
        return talendMessageResources;
    }
    
    public static String getMessage(String key) {
        if (talendMessageResources == null) {
            createResources("TalendResources");
        }
        
        // PTODO MHI initialize from web-inf.
        return talendMessageResources.getMessage(key, Locale.FRENCH);
    }
    
    public static String getMessage(String key, Object... args) {
        if (talendMessageResources == null) {
            createResources("TalendResources");
        }
        
        // PTODO MHI initialize from web-inf.
        return talendMessageResources.getMessage(key, Locale.FRENCH, args);
    }
}
