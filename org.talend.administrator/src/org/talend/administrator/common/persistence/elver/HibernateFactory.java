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
package org.talend.administrator.common.persistence.elver;

import java.util.Properties;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EPackage;
import org.elver.store.hibernate.HbDataStore;
import org.elver.store.hibernate.HbHelper;
import org.hibernate.cfg.Environment;
import org.talend.administrator.common.logging.LoggerFactory;
import org.talend.core.model.properties.PropertiesPackage;

/**
 * DOC tguiu class global comment. Detailled comment <br/>
 * 
 */
public final class HibernateFactory {

    protected static Logger log = LoggerFactory.getLogger(HibernateFactory.class);

    private static final String DATASTORE_NAME = "preferences";

    private final HbDataStore prefDataStore;

    public HibernateFactory() {
        super();
        prefDataStore = createPreferenceDataStore();
    }

    private HbDataStore createPreferenceDataStore() {
        HbDataStore dataStore = null;
        try {            
            Properties hibernateProperties = new Properties();
            dataStore = HbHelper.INSTANCE.createRegisterDataStore(DATASTORE_NAME);

            hibernateProperties.setProperty(Environment.DRIVER, "org.gjt.mm.mysql.Driver");
            hibernateProperties.setProperty(Environment.USER, "root");
            hibernateProperties.setProperty(Environment.URL, "jdbc:mysql://talend-dbms:3306/model_talend");
            hibernateProperties.setProperty(Environment.PASS, "toor");
            hibernateProperties.setProperty(Environment.DIALECT, "org.hibernate.dialect.MySQLDialect");
            hibernateProperties.setProperty(Environment.SHOW_SQL, "true");
            hibernateProperties.setProperty(Environment.USE_REFLECTION_OPTIMIZER, "false");

            dataStore.setHibernateProperties(hibernateProperties);

            dataStore.setEPackages(new EPackage[] { PropertiesPackage.eINSTANCE });

            dataStore.initialize();
        } catch (RuntimeException e) {
            e.printStackTrace();
            throw e;
        }
        return dataStore;
    }

    public HbDataStore getPrefDataStore() {
        return this.prefDataStore;
    }
}
