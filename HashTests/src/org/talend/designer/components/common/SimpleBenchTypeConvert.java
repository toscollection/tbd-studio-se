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
package org.talend.designer.components.common;

import java.util.List;
import java.util.Random;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * 
 * launched with these VM arguments: -Xms512m -Xmx1400m -XX:MaxPermSize=256m
 */
public class SimpleBenchTypeConvert {

    public static void main(String[] args) throws Exception {

        long loop = 2000000000;
//        int loop = 0;
        // int loop = 100;
        // int LOOP = 5000000;

        long time1 = System.currentTimeMillis();

        System.out.println(Float.MAX_VALUE);
        System.out.println(Double.MAX_VALUE);
        System.out.println(Float.MAX_VALUE / 2);

        float f = Float.MAX_VALUE + Float.MAX_VALUE / 2;
        System.out.println("dep:"+f);

//        checkedIADD(Float.MAX_VALUE, Float.MAX_VALUE / 2);
//        checkedIADD(Double.MAX_VALUE, Double.MAX_VALUE / 2);

        System.out.println();

        Random rand = new Random(System.currentTimeMillis());

        System.out.println("ulp1 1.6777216E7f: " + Math.ulp(16777216f));;
        System.out.println("ulp1 0: " + Math.ulp(0));;
        System.out.println("ulp1 1: " + Math.ulp(1));;
        System.out.println("ulp1 Float.MAX_VALUE: " + Math.ulp(Float.MAX_VALUE));;
        
        short valueS = 0;
        float value = 0;
        double valueD = 1E306d;

//        float inc = 1e20f;
        float inc = 1;
        short incS = 1;
        double incD = 1E300d;

        for (long i = 0; i < loop; i++) {

//             value = checkedIADD(value, inc);
//             valueD = checkedIADD(valueD, incD);
             valueS = checkedIADD(valueS, incS);
            
//            float minAddedValue = Math.ulp(value);
//            if(minAddedValue <= inc) {
//                value = value + inc;
//            } else {
//                throw new RuntimeException("inc can be added to:" + value);
//            }
//            
//            valueD = valueD + incD;
//            valueS = valueS + incS;
            // value += inc;

            if (i % 10000000 == 0) {
//                System.out.println(value);
                System.out.println(valueS);
                System.out.println(i);
            }

            // Integer integer = new Integer(rand.nextInt());
            // Integer integer = (Integer) (int) rand.nextInt();
            // Integer integer = (int) rand.nextInt();
            //            
            // List[] list =new List[] {java.util.Arrays.asList("D", "E", "F"), java.util.Arrays.asList("D", "E", "F")};

        }

        long time2 = System.currentTimeMillis();

        System.out.println("value=" + value);
        System.out.println("value=" + (double) value);

        System.out.println("Time: " + ((time2 - time1)) + " ms");
        System.out.println("Time: " + ((time2 - time1) / 1000) + " s");

    }

    static short checkedIADD(short a, short b) {
        short r = (short) (a + b);
        if (((a ^ r) & (b ^ r)) < 0) {
            throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'int/Integer'", "'short/Short'"));
        } else {
            return r;
        }
    }

    static int checkedIADD(int a, int b) {
        int r = a + b;
        if (((a ^ r) & (b ^ r)) < 0) {
            throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'long/Long'", "'int/Integer'"));
        } else {
            return r;
        }
    }
    
    static long checkedIADD(long a, long b) {
        long r = a + b;
        if (((a ^ r) & (b ^ r)) < 0) {
            throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'long/Long'"));
        } else {
            return r;
        }
    }

    static float checkedIADD(float a, float b) {

        float minAddedValue = Math.ulp(a);
        if (minAddedValue > b) {
            throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(a), "'double' or 'BigDecimal'", "'float/Float'"));
        }

        if ((a + b > (double) Float.MAX_VALUE) || (a + b < (double) Float.MAX_VALUE)) {
            throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'double' or 'BigDecimal'", "'float/Float'"));
        } else {
            return a + b;
        }
    }

    static double checkedIADD(double a, double b) {

        double minAddedValue = Math.ulp(a);
        if (minAddedValue > b) {
            throw new RuntimeException(buildPrecisionMessage(String.valueOf(a), String.valueOf(a), "'BigDecimal'", "'double/Double'"));
        }

        if ((a + b > (double) Double.MAX_VALUE) || (a + b < Double.MIN_VALUE)) {
            throw new RuntimeException(buildOverflowMessage(String.valueOf(a), String.valueOf(b), "'BigDecimal'", "'double/Double'"));
        } else {
            return a + b;
        }
    }

    private static String buildOverflowMessage(String a, String b, String advicedTypes, String originalType) {
        return "Type overflow when adding " + b + " to " + a
        + ", to resolve this problem, increase the precision by using "+ advicedTypes +" type in place of "+ originalType +".";
    }
    
    private static String buildPrecisionMessage(String a, String b, String advicedTypes, String originalType) {
        return "The double precision is unsufficient to add the value " + b + " to " + a
        + ", to resolve this problem, increase the precision by using "+ advicedTypes +" type in place of "+ originalType +".";
    }
    
}
