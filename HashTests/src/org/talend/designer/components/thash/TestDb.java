// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.thash;

import java.io.IOException;
import java.sql.SQLException;


/**
 * 
 * DOC slanglois  class global comment. Detailled comment
 * <br/>
 *
 */
public class TestDb {
    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException {

        DB.connect("/tmp/talend.db");
        DB.createTable("buffer");
        DB.commit();
        
        // 48s for 100 000, 0,48 s/bean
        
        int loop = 100000000;
        long end = 0;
        long start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            Bean bean = new Bean(i, "test" + i);
            DB.put("buffer", bean);
        }
        DB.commit();
        end = System.currentTimeMillis();
        System.out.println((end - start) + " milliseconds for " + loop + " objects to store.");
        
        // 17s for 100 000,  0,17 s/bean
        
        start = System.currentTimeMillis();
        for (int i = 0; i < loop; i++) {
            Bean bean = (Bean)DB.get("buffer", loop);
        }
        end = System.currentTimeMillis();
        System.out.println((end - start) + " milliseconds for " + loop + " objects to get.");
        
        DB.close();
        
    }
}

