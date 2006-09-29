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

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.struts.util.MessageResources;
import org.apache.struts.util.MessageResourcesFactory;
import org.talend.administrator.common.logging.LoggerFactory;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class TalendMessageResources extends MessageResources {

   private static final long serialVersionUID = 1L;

    List bundles = new ArrayList();

    public TalendMessageResources(MessageResourcesFactory resourceFactory, String configFactory) {
        super(resourceFactory, configFactory);
        log.info("Initializing, config='" + configFactory + "'");
    }

    public TalendMessageResources(List configList) {
        super(null, null);
        this.bundles = configList;
    }

    public TalendMessageResources(TalendMessageResourcesFactory factory, String config, boolean b) {
        super(factory, config, b);
        log.info("Initializing, config='" + config + "'");
    }

    protected HashMap<Locale, Locale> locales = new HashMap<Locale, Locale>();

    protected HashMap configFiles = new HashMap();

    private static Logger log = LoggerFactory.getLogger(TalendMessageResources.class);

    protected HashMap<String, String> messages = new HashMap<String, String>();

    @Override
    public String getMessage(Locale locale, String key) {
        if (!locales.containsKey(locale)) {
            loadLocale(locale);
        }
        return messages.get(messageKey(locale, key));
    }

    protected synchronized void loadLocale(Locale localeKey) {
        if (locales.get(localeKey) != null) {
            return;
        }

        String name = config.replace('.', '/');
        if (localeKey != null) {
            name += "_" + localeKey.getLanguage();
        }

        name += ".properties";
        InputStream is = null;
        Properties props = new Properties();

        ClassLoader classLoader = this.getClass().getClassLoader();

        is = classLoader.getResourceAsStream(name);
        if (is != null) {
            try {
                props.load(is);

            } catch (IOException e) {
                log.severe("loadLocale()");
            } finally {
                try {
                    is.close();
                } catch (IOException e) {
                    log.severe("loadLocale()");
                }
            }
        }

        if (props.size() < 1) {
            return;
        }

        synchronized (messages) {
            Iterator names = props.keySet().iterator();
            while (names.hasNext()) {
                String key = (String) names.next();
                messages.put(messageKey(localeKey, key), props.getProperty(key));
            }
        }
        locales.put(localeKey, localeKey);
    }
}
