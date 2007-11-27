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

import gnu.trove.THashMap;

import java.util.Map;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * 
 * launched with these VM arguments: -Xms512m -Xmx1400m -XX:MaxPermSize=256m
 */
public class MemoryHashMapTest {

    public static void main(String[] args) throws Exception {

        /**
         * 
         * DOC amaumont MemoryHashMapTest class global comment. Detailled comment <br/>
         * 
         */

        class KeyForMap {

            int id;

            int hashcode;

            /**
             * DOC amaumont KeyForMap constructor comment.
             */
            public KeyForMap(int id, int hashcode) {
                super();
                this.id = id;
                this.hashcode = hashcode;
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.lang.Object#hashCode()
             */
            @Override
            public int hashCode() {
                return this.hashcode;
            }

            /*
             * (non-Javadoc)
             * 
             * @see java.lang.Object#equals(java.lang.Object)
             */
            @Override
            public boolean equals(Object obj) {
                if (this == obj)
                    return true;
                if (obj == null)
                    return false;
                if (getClass() != obj.getClass())
                    return false;
                final KeyForMap other = (KeyForMap) obj;
                if (this.id != other.id)
                    return false;
                return true;
            }

        }

        int loop = 10000000;
        // int loop = 100;
        // int LOOP = 5000000;

        Sizeof.runGC();
        long heap1 = Sizeof.usedMemory(); // Take a before heap snapshot

        long time1 = System.currentTimeMillis();

        // Tests for 10 000 000 items

        // Map hashMap = new HashMap(); // 47 bytes, ?? s
        // Map hashMap = new HashMap(loop, 1f); // 47 byts, ?? s
        // Map hashMap = new THashMap(); // 37 bytes, ?? s
        // Map hashMap = new THashMap(loop, 0.1f); // ???
        // Map hashMap = new THashMap(loop, 1f); // 25 bytes, ?? s
        // Map hashMap = new THashMap(loop + 10000, 1.0f); // 25 bytes, ?? s
        // Map hashMap = new THashMap(loop + 10000, 0.99f); // 25 bytes, ?? s
        Map hashMap = new THashMap(10000, 1.0f); // 25 bytes, 9 s

        for (int i = 0; i < loop; i++) {
            KeyForMap keyForMap = new KeyForMap(i, i /* computed hashcode */); // in reel tests second parameter will
                                                                                // be computed from properties of value
                                                                                // bean
            hashMap.put(keyForMap, keyForMap);
        }

        long time2 = System.currentTimeMillis();

        System.out.println("waiting for garbage collector...");
        Sizeof.runGC();
        long heap2 = Sizeof.usedMemory(); // Take a before heap snapshot

        final int size = Math.round(((float) (heap2 - heap1)) / loop);
        System.out.println("'before' heap: " + heap1 + " bytes, 'after' heap: " + heap2 + " bytes ");
        System.out.println("heap delta: " + (heap2 - heap1) + " bytes ");
        System.out.println("size by item: " + size + " bytes ");
        System.out.println("Number of loops: " + loop);
        System.out.println("Number of items: " + hashMap.size());
        if (loop != hashMap.size()) {
            System.out.println("WARNING: loops number is different of items number !");
        }
        System.out.println("Time: " + ((time2 - time1) / 1000) + " s");

    }

}
