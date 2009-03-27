// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package test;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public class asd {

    public static void main(String[] args) {
        try {
            java.lang.Class.forName("org.gjt.mm.mysql.Driver");
            String url = "jdbc:mysql://localhost:3306/test?anoDatetimeStringSync=true";
            java.sql.Connection conn = java.sql.DriverManager.getConnection(url, "root", "root");
            java.sql.Statement stm = conn.createStatement();
            java.sql.ResultSet rs = stm.executeQuery(" select * from feature4 ");
            java.sql.ResultSetMetaData rsmd = rs.getMetaData();
            System.out.println(rsmd.getColumnTypeName(1));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
