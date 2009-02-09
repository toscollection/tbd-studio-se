// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * 
 * launched with these VM arguments: -Xms512m -Xmx1400m -XX:MaxPermSize=256m
 */
public class MemoryHashMapSoibTest {

    static Random r = new Random(System.currentTimeMillis());

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

            // String type = "010";
            // String T_usage = "01";
            // Integer date = 7102008;
            // Integer time = 101200;
            // Long appelant = 1l;
            // Long Column5 = 33157601014l;
            // Integer Column6 = 31;
            // Integer volume = 0;
            // Integer Column8 = 999999;
            // String faisceau = "ENTRANT_NATIONAL_92";
            // Integer JJ = 10;
            // Integer Column11 = 20;
            // Integer GLID = 1;
            // Float bi1 = 2f;
            // Float bi2 = 3.562f;
            // Float bi3 = 4.23f;
            // Float bi4 = 5.2389f;
            // Float bi5 = 8.343f;
            // Float bi6 = 1.1678f;
            // Float bi7 = 6.89234f;
            // Float bi8 = 9.789234f;
            // Float bi9 = 213f;
            // Float bi10 = 2.2983232f;
            // String buffer =
            // "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
            // ;

//            String type;
//            String T_usage;
//            int date;
//            int time;
//            long appelant;
//            long Column5;
//            int Column6;
//            int volume;
//            int Column8;
//            String faisceau;
//            int JJ;
//            int Column11;
//            int GLID;
//            double bi1;
//            double bi2;
//            double bi3;
//            double bi4;
//            double bi5;
//            double bi6;
//            double bi7;
//            double bi8;
//            double bi9;
//            double bi10;
//            String buffer;

            // String type;
            // String T_usage;
            // int date;
            // int time;
            // long appelant;
            // long Column5;
            // int Column6;
            // int volume;
            // int Column8;
            // String faisceau;
            // int JJ;
            // int Column11;
            // int GLID;
            // float bi1;
            // float bi2;
            // float bi3;
            // float bi4;
            // float bi5;
            // float bi6;
            // float bi7;
            // float bi8;
            // float bi9;
            // float bi10;
            // String buffer;
            //            
            // String type = new String("010");
            // String T_usage = new String("01");
            // int date = r.nextInt(100000);
            // int time = r.nextInt(100000);
            // long appelant = r.nextLong();
            // long Column5 = r.nextLong();
            // int Column6 = r.nextInt(100000);
            // int volume = r.nextInt(100000);
            // int Column8 = r.nextInt(100000);
            // String faisceau = new String("ENTRANT_NATIONAL_92");
            // int JJ = r.nextInt(100000);
            // int Column11 = r.nextInt(100000);
            // int GLID = r.nextInt(100000);
            // float bi1 = r.nextFloat();
            // float bi2 = r.nextFloat();
            // float bi3 = r.nextFloat();
            // float bi4 = r.nextFloat();
            // float bi5 = r.nextFloat();
            // float bi6 = r.nextFloat();
            // float bi7 = r.nextFloat();
            // float bi8 = r.nextFloat();
            // float bi9 = r.nextFloat();
            // float bi10 = r.nextFloat();
            // String buffer = new String(
            // "0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789"
            // );
            //            

//             String type = new String("010");
//             String T_usage = new String("01");
//             int date = r.nextInt(100000);
//             int time = r.nextInt(100000);
//             long appelant = r.nextLong();
//             float bi1 = r.nextFloat();
//             float bi2 = r.nextFloat();
//             float bi3 = r.nextFloat();
//             float bi4 = r.nextFloat();
//             float bi5 = r.nextFloat();
//             float bi6 = r.nextFloat();
//             float bi7 = r.nextFloat();
//             float bi8 = r.nextFloat();
//             float bi9 = r.nextFloat();
//             float bi10 = r.nextFloat();

             String type = new String("010");
             String T_usage = new String("01");
             int date = r.nextInt(100000);
             int time = r.nextInt(100000);
             long appelant = r.nextLong();
             double bi1 = r.nextDouble();
             double bi2 = r.nextDouble();
             double bi3 = r.nextDouble();
             double bi4 = r.nextDouble();
             double bi5 = r.nextDouble();
             double bi6 = r.nextDouble();
             double bi7 = r.nextDouble();
             double bi8 = r.nextDouble();
             double bi9 = r.nextDouble();
             double bi10 = r.nextDouble();

//            @Override
//            public int hashCode() {
//                final int prime = 31;
//                int result = 1;
//                result = prime * result + ((T_usage == null) ? 0 : T_usage.hashCode());
//                result = prime * result + (int) (appelant ^ (appelant >>> 32));
//                long temp;
//                temp = Double.doubleToLongBits(bi1);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi10);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi2);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi3);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi4);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi5);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi6);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi7);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi8);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                temp = Double.doubleToLongBits(bi9);
//                result = prime * result + (int) (temp ^ (temp >>> 32));
//                result = prime * result + date;
//                result = prime * result + hashcode;
//                result = prime * result + id;
//                result = prime * result + time;
//                result = prime * result + ((type == null) ? 0 : type.hashCode());
//                return result;
//            }
//            @Override
//            public boolean equals(Object obj) {
//                if (this == obj)
//                    return true;
//                if (obj == null)
//                    return false;
//                if (getClass() != obj.getClass())
//                    return false;
//                KeyForMap other = (KeyForMap) obj;
//                if (T_usage == null) {
//                    if (other.T_usage != null)
//                        return false;
//                } else if (!T_usage.equals(other.T_usage))
//                    return false;
//                if (appelant != other.appelant)
//                    return false;
//                if (Double.doubleToLongBits(bi1) != Double.doubleToLongBits(other.bi1))
//                    return false;
//                if (Double.doubleToLongBits(bi10) != Double.doubleToLongBits(other.bi10))
//                    return false;
//                if (Double.doubleToLongBits(bi2) != Double.doubleToLongBits(other.bi2))
//                    return false;
//                if (Double.doubleToLongBits(bi3) != Double.doubleToLongBits(other.bi3))
//                    return false;
//                if (Double.doubleToLongBits(bi4) != Double.doubleToLongBits(other.bi4))
//                    return false;
//                if (Double.doubleToLongBits(bi5) != Double.doubleToLongBits(other.bi5))
//                    return false;
//                if (Double.doubleToLongBits(bi6) != Double.doubleToLongBits(other.bi6))
//                    return false;
//                if (Double.doubleToLongBits(bi7) != Double.doubleToLongBits(other.bi7))
//                    return false;
//                if (Double.doubleToLongBits(bi8) != Double.doubleToLongBits(other.bi8))
//                    return false;
//                if (Double.doubleToLongBits(bi9) != Double.doubleToLongBits(other.bi9))
//                    return false;
//                if (date != other.date)
//                    return false;
//                if (hashcode != other.hashcode)
//                    return false;
//                if (id != other.id)
//                    return false;
//                if (time != other.time)
//                    return false;
//                if (type == null) {
//                    if (other.type != null)
//                        return false;
//                } else if (!type.equals(other.type))
//                    return false;
//                return true;
//            }
            
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

        int loop = 1000000;
        // int loop = 100;
        // int LOOP = 5000000;

        KeyForMap[] array = new KeyForMap[loop]; // 47 bytes, ?? s

        Sizeof.runGC();
        long heap1 = Sizeof.usedMemory(); // Take a before heap snapshot

        long time1 = System.currentTimeMillis();

        // Tests for 10 000 000 items

        Map hashMap = new HashMap(); // 47 bytes, ?? s
        // Map hashMap = new HashMap(loop*2, 1f); // 47 bytes, ?? s
        // Map hashMap = new HashMap(loop, 1f); // 47 byts, ?? s
        // Map hashMap = new THashMap(); // 37 bytes, ?? s
        // Map hashMap = new THashMap(loop, 0.1f); // ???
        // Map hashMap = new THashMap(loop, 1f); // 25 bytes, ?? s
        // Map hashMap = new THashMap(loop + 10000, 1.0f); // 25 bytes, ?? s
        // Map hashMap = new THashMap(loop + 10000, 0.99f); // 25 bytes, ?? s
        // Map hashMap = new THashMap(100000, 1.0f); // 25 bytes, 9 s

        // KeyForMap keyForMap = null;

        int j = 0;
        for (int i = 0; i < loop; i++) {
            KeyForMap keyForMap = new KeyForMap(i, i /* computed hashcode */); // in reel tests second parameter will
            // be computed from properties of value
            // bean
            hashMap.put(keyForMap, keyForMap);
            // array[i] = keyForMap;
            // j = j * i;
            // if(i% 100000 == 0) {
            // System.gc();
            // }
        }

        // Thread.sleep(10000);

        long time2 = System.currentTimeMillis();

        System.out.println("waiting for garbage collector...");
        Sizeof.runGC();
        long heap2 = Sizeof.usedMemory(); // Take a before heap snapshot

        long l = heap2 - heap1;
        final int size = Math.round(((float) l) / loop);
        System.out.println("'before' heap: " + heap1 + " bytes, 'after' heap: " + heap2 + " bytes ");
        System.out.println("heap delta: " + l + " bytes , " + (l / 1024 / 1024) + " Mo");
        System.out.println("size by item: " + size + " bytes ");
        System.out.println("Number of loops: " + loop);
         System.out.println("Number of items: " + hashMap.size());
         if (loop != hashMap.size()) {
         System.out.println("WARNING: loops number is different of items number !");
         }

        // System.out.println(keyForMap);

        System.out.println("Array size: " + array.length);

        System.out.println("Time: " + (time2 - time1) + " ms");
        System.out.println("Time: " + ((time2 - time1) / 1000) + " s");

        Thread.sleep(10000);

    }

}
