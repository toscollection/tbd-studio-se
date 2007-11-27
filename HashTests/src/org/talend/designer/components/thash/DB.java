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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
class DB {

    private static final int COMMITCOUNT = 10000;

    private static Connection conn = null;

    private static Map<String, Integer> tables = new HashMap<String, Integer>();

    private static Map<String, PreparedStatement> pstmts = new HashMap<String, PreparedStatement>();

    private static Map<String, PreparedStatement> selectPstmts = new HashMap<String, PreparedStatement>();

    private static Statement stmt = null;

    private static Statement selectStmt = null;

    private static int counter;

    public static Object get(String container, int id) throws SQLException, IOException, ClassNotFoundException {
        if (conn == null) {
            new SQLException("No database was set for serialize.");
        }

        /*
         * For 100 000 beans 17 s
         */
        PreparedStatement preparedStatement = selectPstmts.get(container);
        if (preparedStatement == null) {
            preparedStatement = conn.prepareStatement("SELECT object FROM '" + container + "' WHERE id = ?");
            selectPstmts.put(container, preparedStatement);
        }
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();

        
        
        /*
         * For 100 000 beans less than 73 s
         */
//         ResultSet rs = stmt.executeQuery("SELECT object FROM '" + container + "' WHERE id = " + id + ";");
        
        
        
        if (rs.next()) {
            ObjectInput oi = new ObjectInputStream(new ByteArrayInputStream(rs.getBytes(1)));
            return oi.readObject();
        } else {
            return null;
        }
    }

    public static int put(String container, Object bean) throws SQLException {
        if (conn == null) {
            new SQLException("No database was set for serialize.");
        }
        ObjectOutputStream objectOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(bean);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (objectOutputStream != null) {
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        PreparedStatement pstmt = pstmts.get(container);
        
        if(pstmt == null) {
            try {
                DB.connect("/tmp/talend.db");
            } catch (ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            DB.createTable("buffer");
            DB.commit();
            pstmt = pstmts.get(container);
        }
        
        // int counter = tables.get(container) + 1;
        pstmt.setInt(1, counter++);
        pstmt.setBytes(2, byteArrayOutputStream.toByteArray());
        pstmt.executeUpdate();
        if (counter % COMMITCOUNT == 0) {
            commit();
        }
        // tables.put(container, counter);
        return counter;
    }

    public static void connect(String database) throws ClassNotFoundException, SQLException {
        java.lang.Class.forName("org.sqlite.JDBC");

        String url = "jdbc:sqlite:" + "/" + database.toLowerCase();

        conn = java.sql.DriverManager.getConnection(url);

        conn.setAutoCommit(false);

        stmt = conn.createStatement();
    }

    public static void dropTable(String table) throws SQLException {
        if (conn != null) {
            stmt.execute("DROP TABLE IF EXISTS '" + table + "';");
            tables.remove(table);
            PreparedStatement pstmt = pstmts.remove(table);
            if (pstmt != null) {
                pstmt.close();
            }
        }
        commit();
    }

    public static void createTable(String table) throws SQLException {
        stmt.execute("DROP TABLE IF EXISTS '" + table + "';");
        stmt.execute("CREATE TABLE '" + table + "'(id INT NOT NULL PRIMARY KEY, object BLOB NOT NULL);");
        tables.put(table, 0);
        pstmts.put(table, conn.prepareStatement("INSERT INTO '" + table + "' VALUES(?, ?);"));
        commit();
    }

    public static void commit() throws SQLException {
        conn.commit();
    }

    public static void close() throws SQLException {
        if (conn != null) {
            stmt.close();
            conn.close();
        }
    }

}
