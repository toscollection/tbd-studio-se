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
package org.talend.designer.components.thash.io.hashimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

import org.talend.designer.components.thash.io.IMapHashFile;
import org.talend.designer.components.thash.io.beans.Bean;

import com.sleepycat.je.Database;
import com.sleepycat.je.DatabaseConfig;
import com.sleepycat.je.DatabaseEntry;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.je.LockMode;
import com.sleepycat.je.OperationStatus;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class BerkeleyDBHashById implements IMapHashFile {

    private static BerkeleyDBHashById instance;

    private BerkeleyDBHashById() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public synchronized static BerkeleyDBHashById getInstance() {
        if (instance == null) {
            instance = new BerkeleyDBHashById();
        }
        return instance;
    }

    public BerkeleyDBHashById(String database) throws ClassNotFoundException, SQLException {
        connect(database);
    }

    boolean readonly;

    private int counter;

    private Environment myDbEnvironment;

    private Database myDatabase;
    
    private String container = "/home/amaumont/hash_benchs/berkeley_db_files";

    byte[] key = new byte[4];

    public Object get(String container, long id, int hashcode) throws Exception {

        extractBytesKey(key, (int) id);
        DatabaseEntry theKey = new DatabaseEntry(key);
        DatabaseEntry theData = new DatabaseEntry();
        // Perform the get.
        if (myDatabase.get(null, theKey, theData, LockMode.DEFAULT) == OperationStatus.SUCCESS) {
            ObjectInput oi = new ObjectInputStream(new ByteArrayInputStream(theData.getData()));
            return oi.readObject();
        } else {
            return null;
        }

    }

    public long put(String container, Object bean) throws IOException {
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

        extractBytesKey(key, counter);
        DatabaseEntry theKey = new DatabaseEntry(key);
        DatabaseEntry theData = new DatabaseEntry(byteArrayOutputStream.toByteArray());
        try {
            myDatabase.put(null, theKey, theData);
        } catch (DatabaseException e) {
            throw new RuntimeException(e);
        }

        return counter++;
    }

    public void connect(String database) throws ClassNotFoundException, SQLException {
    
        try {
            EnvironmentConfig envConfig = new EnvironmentConfig();
            envConfig.setAllowCreate(true);
            envConfig.setCacheSize(50000000);
            myDbEnvironment = new Environment(new File(container), envConfig);

            // Open the database. Create it if it does not already exist.
            DatabaseConfig dbConfig = new DatabaseConfig();
            dbConfig.setAllowCreate(true);
            dbConfig.setExclusiveCreate(true);
            myDatabase = myDbEnvironment.openDatabase(null, database, dbConfig);

        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
    }

    public void dropTable(String table) throws SQLException {
        // if (conn != null) {
        // stmt.execute("DROP TABLE IF EXISTS '" + table + "';");
        // tables.remove(table);
        // PreparedStatement pstmt = pstmts.remove(table);
        // if (pstmt != null) {
        // pstmt.close();
        // }
        // }
        // commit();
    }

    public void createTable(String table) throws SQLException {
        // stmt.execute("DROP TABLE IF EXISTS '" + table + "';");
        // stmt.execute("CREATE TABLE '" + table + "'(id INT NOT NULL PRIMARY KEY, object BLOB NOT NULL);");
        // tables.put(table, 0);
        // pstmts.put(table, conn.prepareStatement("INSERT INTO '" + table + "' VALUES(?, ?);"));
        // commit();
    }

    public void close() throws SQLException {
        try {
            if (myDatabase != null) {
                myDatabase.close();
            }
        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
        try {
            if (myDbEnvironment != null) {
                myDbEnvironment.close();
            }
        } catch (DatabaseException dbe) {
            dbe.printStackTrace();
        }
    }

    public void endGet(String container) throws IOException {

    }

    public void endPut() throws IOException {

    }

    public void initGet(String container) throws FileNotFoundException {

    }

    public void initPut(String container) throws IOException {

    }

    public void extractBytesKey(byte[] b, int val) {
        b[3] = (byte) (val >>> 0);
        b[2] = (byte) (val >>> 8);
        b[1] = (byte) (val >>> 16);
        b[0] = (byte) (val >>> 24);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.thash.io.MapHashFile#getTotalSize()
     */
    public long getTotalSize() {
        // TODO Auto-generated method stub
        return 0;
    }

    public static void main(String[] args) throws Exception {
        BerkeleyDBHashById bh = BerkeleyDBHashById.getInstance();
        bh.connect("sampledatabase");
        int loop = 20000000;
        // Map m = new THashMap();
        long end = 0;
        long start = java.util.Calendar.getInstance().getTimeInMillis();
//        for (int i = 0; i < loop; i++) {
//            Bean bean = new Bean(i, "test" + (i));
//            bh.put("", bean);
//            // KeyForMap k = new KeyForMap(i, bean.hashCode());
//            // m.put(k, k);
//        }
        end = java.util.Calendar.getInstance().getTimeInMillis();
        System.out.println((end - start) + " milliseconds for " + loop + " objects to store.");

        int size = 1000000;
        int[] ri = new int[size];
        for (int i = 0; i < size; i++) {
            ri[i] = (int) (Math.random() * loop);
        }
        start = java.util.Calendar.getInstance().getTimeInMillis();
        for (int i = 0; i < size; i++) {
            Bean b = (Bean) (bh.get("", ri[i], ri[i]));
//            System.out.println(b.primitiveInt+"   "+b.name);
        }
        end = java.util.Calendar.getInstance().getTimeInMillis();
        System.out.println((end - start) + " milliseconds for " + size + " objects to get randomly.");

    }

}
