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
package org.talend.designer.components.thash.io;

import gnu.trove.THashMap;
import gnu.trove.TObjectHashingStrategy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
public class HibernateKeyForMap {

    public static void write(String file, Map<KeyForMap, KeyForMap> map) throws IOException {
        write(file, map.keySet().toArray(new KeyForMap[0]));
    }

    public static void write(String file, KeyForMap[] array) throws IOException {
        File f = new File(file);
        if (f.exists()) {
            f.delete();
        }
        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        dos.writeInt(array.length);

        for (int i = 0; i < array.length; i++) {
            dos.writeInt(array[i].cursorPosition);
            dos.writeInt(array[i].hashcode);
        }
        dos.close();
    }

    public static Map<KeyForMap, KeyForMap> readAsMap(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        int size = dis.readInt();
        TObjectHashingStrategy objectHashingStrategy = new TObjectHashingStrategy() {

            public int computeHashCode(Object arg0) {
                return arg0 == null ? 0 : arg0.hashCode();
            }

            public boolean equals(Object arg0, Object arg1) {
                return arg1 == null ? arg0 == null : arg1.equals(arg0);
            }

        };
        Map<KeyForMap, KeyForMap> hashMap = new THashMap<KeyForMap, KeyForMap>(size, objectHashingStrategy);
        for (int i = 0; i < size; i++) {
            KeyForMap keyForMap = new KeyForMap(dis.readInt(), dis.readInt());
            hashMap.put(keyForMap, keyForMap);
        }
        dis.close();
        return hashMap;
    }

    public static KeyForMap[] readAsArray(String file) throws IOException {
        DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(file)));
        KeyForMap[] result = new KeyForMap[dis.readInt()];
        for (int i = 0; i < result.length; i++) {
            result[i] = new KeyForMap(dis.readInt(), dis.readInt());
        }
        return result;
    }

}
