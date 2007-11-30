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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;

import org.talend.designer.components.thash.io.hashimpl.InternalSmallBean;
import org.talend.designer.components.thash.io.hashimpl.MultiplePointerSimpleHashFile;
import org.talend.designer.components.thash.io.hashimpl.NewMultiplePointerSimpleHashFile;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
public class TestRAFile {

    /**
     * 
     */
    private static final String D_20071109TEMP = "/tmp/20071109temp";

    private static final String D_CURSORPOSITION = "/tmp/cursorPosition";

    private static void initForGet(int loop) throws IOException {
        MultiplePointerSimpleHashFile hashFile = MultiplePointerSimpleHashFile.getInstance();

        hashFile.initPut(D_20071109TEMP);
        long[] cursors = new long[loop];
        for (int i = 0; i < loop; i++) {
            InternalSmallBean bean = new InternalSmallBean(i, "test" + i);
            cursors[i] = hashFile.put("", bean);
        }
        hashFile.endPut();

        File f = new File(D_CURSORPOSITION);
        if (f.exists()) {
            f.delete();
        }
        f.createNewFile();
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f));
        oos.writeObject(cursors);
        oos.close();
    }

    public static void main(String[] args) throws ClassNotFoundException, SQLException, IOException, InterruptedException {
        File f1 = new File(D_20071109TEMP);
        File f2 = new File(D_CURSORPOSITION);
        if (!f1.exists() || !f2.exists()) {
            System.out.println("write 20000000 items to file will take long time, please waite...");
            initForGet(20000000);
            System.out.println("write 20000000 items successful");
        }
        System.out.println("File size: " + f1.length());
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(D_CURSORPOSITION));
        long[] cursors = (long[]) ois.readObject();
        System.out.println(cursors.length + " items in file.");
        ois.close();

        // MultiplePointerSimpleHashFile hashFile = MultiplePointerSimpleHashFile.getInstance();
        NewMultiplePointerSimpleHashFile hashFile = NewMultiplePointerSimpleHashFile.getInstance();

        System.out.println("Use " + hashFile.getClass().getSimpleName() + ": ");
        int size = 1000000;
        long[] randomCursors = new long[size];
        for (int j = 0; j < size; j++) {
            randomCursors[j] = cursors[(int) (Math.random() * cursors.length)];
        }

        long end = 0;
        long start = java.util.Calendar.getInstance().getTimeInMillis();
        hashFile.initGet(D_20071109TEMP);
        for (int j = 0; j < size; j++) {
            InternalSmallBean bean = (InternalSmallBean) hashFile.get("", randomCursors[j], -1);
            // System.out.println(bean.primitiveInt+": "+bean.name);
        }

        hashFile.endGet(D_20071109TEMP);
        end = java.util.Calendar.getInstance().getTimeInMillis();
        System.out.println("Use " + hashFile.getClass().getSimpleName() + ": " + (end - start) + " milliseconds for " + size
                + " objects to get.");
        
        
//        SimpleHashFile hashFile = SimpleHashFile.getInstance();
//        
//        hashFile.initPut(D_20071109TEMP);
//
//        List<Long> cursorPositionList = new ArrayList<Long>();
//
//        int loop = 1000000;
//        long end = 0;
//        long start = java.util.Calendar.getInstance().getTimeInMillis();
//        for (int i = 0; i < loop; i++) {
//            InternalSmallBean bean = new InternalSmallBean(i, "test" + i);
//            // KeyForMap keyForMap = new KeyForMap(id, bean.hashCode());
//            cursorPositionList.add(hashFile.put("", bean));
//        }
//        hashFile.endPut();
//        end = java.util.Calendar.getInstance().getTimeInMillis();
//        System.out.println((end - start) + " milliseconds for " + loop + " objects to store.");
//
//        start = java.util.Calendar.getInstance().getTimeInMillis();
//        hashFile.initGet(D_20071109TEMP);
//        int size = cursorPositionList.size();
//        for (int i = 0; i < size; i++) {
//            InternalSmallBean bean = (InternalSmallBean) hashFile.get("", cursorPositionList.get(i), -1);
////            System.out.println(bean.primitiveInt + "  " + bean.name);
//        }
//        hashFile.endGet(D_20071109TEMP);
//
//        end = java.util.Calendar.getInstance().getTimeInMillis();
//        System.out.println((end - start) + " milliseconds for " + loop + " objects to get.");
    }
}
/*
results by slanglois's PC:

File size: 2868888890
20000000 items in file.
Use NewMultiplePointerSimpleHashFile:
Total time to get 1000 items from disk=10031 ms
Total time to get 1000 items from disk=10703 ms
Total time to get 1000 items from disk=10234 ms
Total time to get 1000 items from disk=10219 ms
Total time to get 1000 items from disk=11422 ms
Total time to get 1000 items from disk=9891 ms
Total time to get 1000 items from disk=10579 ms
Total time to get 1000 items from disk=10187 ms
Total time to get 1000 items from disk=10172 ms
Total time to get 1000 items from disk=9843 ms
Total time to get 1000 items from disk=11156 ms
Total time to get 1000 items from disk=10282 ms
Total time to get 1000 items from disk=16109 ms
Total time to get 1000 items from disk=43469 ms
Total time to get 1000 items from disk=10171 ms
Total time to get 1000 items from disk=10219 ms
Total time to get 1000 items from disk=10439 ms
Total time to get 1000 items from disk=10344 ms
Total time to get 1000 items from disk=10625 ms
Total time to get 1000 items from disk=10047 ms
Total time to get 1000 items from disk=10234 ms
Total time to get 1000 items from disk=10485 ms
Total time to get 1000 items from disk=10110 ms
Total time to get 1000 items from disk=9844 ms
Total time to get 1000 items from disk=10984 ms
Total time to get 1000 items from disk=10656 ms
Total time to get 1000 items from disk=10358 ms
Total time to get 1000 items from disk=10343 ms
Total time to get 1000 items from disk=10282 ms
Total time to get 1000 items from disk=11047 ms
Total time to get 1000 items from disk=16939 ms
Total time to get 1000 items from disk=40812 ms
Total time to get 1000 items from disk=10890 ms
Total time to get 1000 items from disk=10110 ms
Total time to get 1000 items from disk=10469 ms
Total time to get 1000 items from disk=10000 ms
Total time to get 1000 items from disk=9937 ms
Total time to get 1000 items from disk=10830 ms
Total time to get 1000 items from disk=9938 ms
Total time to get 1000 items from disk=9859 ms
Total time to get 1000 items from disk=10376 ms
Total time to get 1000 items from disk=10188 ms
Total time to get 1000 items from disk=10032 ms
Total time to get 1000 items from disk=11032 ms
Total time to get 1000 items from disk=9892 ms
Total time to get 1000 items from disk=10328 ms
Total time to get 1000 items from disk=10155 ms
Total time to get 1000 items from disk=10078 ms
Total time to get 1000 items from disk=10375 ms
Total time to get 1000 items from disk=42266 ms
Total time to get 1000 items from disk=13561 ms
Total time to get 1000 items from disk=10766 ms
Total time to get 1000 items from disk=9968 ms
Total time to get 1000 items from disk=10266 ms
Total time to get 1000 items from disk=10030 ms
Total time to get 1000 items from disk=10298 ms
Total time to get 1000 items from disk=9906 ms
Total time to get 1000 items from disk=10827 ms
......
......



//////////////////////////////////////////////////////
File size: 2868888890
20000000 items in file.
Use MultiplePointerSimpleHashFile:
Total time to get 1000 items from disk=9500 ms
Total time to get 1000 items from disk=10015 ms
Total time to get 1000 items from disk=10470 ms
Total time to get 1000 items from disk=12187 ms
Total time to get 1000 items from disk=35796 ms
Total time to get 1000 items from disk=25031 ms
Total time to get 1000 items from disk=30282 ms
Total time to get 1000 items from disk=13688 ms
Total time to get 1000 items from disk=49343 ms
Total time to get 1000 items from disk=26219 ms
Total time to get 1000 items from disk=9922 ms
Total time to get 1000 items from disk=11765 ms
Total time to get 1000 items from disk=10077 ms
Total time to get 1000 items from disk=10439 ms
Total time to get 1000 items from disk=10344 ms
Total time to get 1000 items from disk=11187 ms
Total time to get 1000 items from disk=10328 ms
Total time to get 1000 items from disk=13359 ms
Total time to get 1000 items from disk=10204 ms
Total time to get 1000 items from disk=9859 ms
Total time to get 1000 items from disk=10969 ms
Total time to get 1000 items from disk=10000 ms
Total time to get 1000 items from disk=10140 ms
Total time to get 1000 items from disk=11703 ms
Total time to get 1000 items from disk=10188 ms
Total time to get 1000 items from disk=9860 ms
Total time to get 1000 items from disk=36109 ms
Total time to get 1000 items from disk=20296 ms
Total time to get 1000 items from disk=10986 ms
Total time to get 1000 items from disk=10218 ms
Total time to get 1000 items from disk=9922 ms
Total time to get 1000 items from disk=9986 ms
Total time to get 1000 items from disk=10890 ms
Total time to get 1000 items from disk=10282 ms
Total time to get 1000 items from disk=10874 ms
Total time to get 1000 items from disk=10406 ms
Total time to get 1000 items from disk=10313 ms
Total time to get 1000 items from disk=9875 ms
Total time to get 1000 items from disk=10328 ms
Total time to get 1000 items from disk=10390 ms
Total time to get 1000 items from disk=10875 ms
Total time to get 1000 items from disk=10187 ms
Total time to get 1000 items from disk=10282 ms
Total time to get 1000 items from disk=10202 ms
Total time to get 1000 items from disk=27171 ms
Total time to get 1000 items from disk=33125 ms
Total time to get 1000 items from disk=10812 ms
Total time to get 1000 items from disk=11875 ms
Total time to get 1000 items from disk=10500 ms
Total time to get 1000 items from disk=11891 ms
Total time to get 1000 items from disk=10859 ms
Total time to get 1000 items from disk=11031 ms
Total time to get 1000 items from disk=10343 ms
Total time to get 1000 items from disk=10969 ms
Total time to get 1000 items from disk=10782 ms
Total time to get 1000 items from disk=13375 ms
Total time to get 1000 items from disk=10859 ms
Total time to get 1000 items from disk=10406 ms
Total time to get 1000 items from disk=11110 ms
Total time to get 1000 items from disk=10203 ms
Total time to get 1000 items from disk=10234 ms
Total time to get 1000 items from disk=14391 ms
....
....


Results of printing get time for every 10000 items.

File size: 2868888890
20000000 items in file.
Use MultiplePointerSimpleHashFile: 
Total time to get 10000 items from disk=237936 ms
Total time to get 10000 items from disk=138187 ms
Total time to get 10000 items from disk=101437 ms
Total time to get 10000 items from disk=134842 ms
Total time to get 10000 items from disk=107327 ms
Total time to get 10000 items from disk=135831 ms
Total time to get 10000 items from disk=132764 ms
Total time to get 10000 items from disk=102045 ms
Total time to get 10000 items from disk=133689 ms
Total time to get 10000 items from disk=102200 ms
Total time to get 10000 items from disk=134418 ms
Total time to get 10000 items from disk=101702 ms
Total time to get 10000 items from disk=132843 ms
Total time to get 10000 items from disk=102375 ms
Total time to get 10000 items from disk=132200 ms
Total time to get 10000 items from disk=102128 ms
Total time to get 10000 items from disk=131643 ms
Total time to get 10000 items from disk=100075 ms
Total time to get 10000 items from disk=132998 ms
Total time to get 10000 items from disk=133172 ms
Total time to get 10000 items from disk=102888 ms
Total time to get 10000 items from disk=133234 ms
Total time to get 10000 items from disk=102596 ms
Total time to get 10000 items from disk=133265 ms
Total time to get 10000 items from disk=102590 ms
Total time to get 10000 items from disk=134516 ms
Total time to get 10000 items from disk=102688 ms
Total time to get 10000 items from disk=134673 ms
Total time to get 10000 items from disk=101971 ms
Total time to get 10000 items from disk=134031 ms
Total time to get 10000 items from disk=111702 ms
Total time to get 10000 items from disk=124064 ms
Total time to get 10000 items from disk=133361 ms
Total time to get 10000 items from disk=101124 ms
Total time to get 10000 items from disk=133686 ms
Total time to get 10000 items from disk=101377 ms
Total time to get 10000 items from disk=133003 ms
Total time to get 10000 items from disk=101813 ms
Total time to get 10000 items from disk=135143 ms
Total time to get 10000 items from disk=102718 ms
Total time to get 10000 items from disk=135608 ms
Total time to get 10000 items from disk=101156 ms
Total time to get 10000 items from disk=133326 ms
Total time to get 10000 items from disk=132595 ms
Total time to get 10000 items from disk=102222 ms
Total time to get 10000 items from disk=134342 ms
Total time to get 10000 items from disk=101982 ms
Total time to get 10000 items from disk=133108 ms
Total time to get 10000 items from disk=101483 ms
Total time to get 10000 items from disk=134174 ms
Total time to get 10000 items from disk=103421 ms
Total time to get 10000 items from disk=134638 ms
Total time to get 10000 items from disk=103096 ms
Total time to get 10000 items from disk=135625 ms
Total time to get 10000 items from disk=115889 ms
Total time to get 10000 items from disk=120530 ms
Total time to get 10000 items from disk=136642 ms
Total time to get 10000 items from disk=102143 ms
Total time to get 10000 items from disk=134357 ms
Total time to get 10000 items from disk=102796 ms
Total time to get 10000 items from disk=133747 ms
Total time to get 10000 items from disk=103560 ms
Total time to get 10000 items from disk=134733 ms
Total time to get 10000 items from disk=101796 ms
Total time to get 10000 items from disk=247513 ms
Total time to get 10000 items from disk=131067 ms
Total time to get 10000 items from disk=135177 ms
Total time to get 10000 items from disk=103782 ms
Total time to get 10000 items from disk=135624 ms
Total time to get 10000 items from disk=102042 ms
Total time to get 10000 items from disk=135482 ms
Total time to get 10000 items from disk=103470 ms
Total time to get 10000 items from disk=135159 ms
Total time to get 10000 items from disk=102623 ms
Total time to get 10000 items from disk=133793 ms
Total time to get 10000 items from disk=134545 ms
Total time to get 10000 items from disk=103347 ms
Total time to get 10000 items from disk=133530 ms
Total time to get 10000 items from disk=103140 ms
Total time to get 10000 items from disk=134750 ms
Total time to get 10000 items from disk=103546 ms
Total time to get 10000 items from disk=134235 ms
Total time to get 10000 items from disk=103155 ms
Total time to get 10000 items from disk=133391 ms
Total time to get 10000 items from disk=103205 ms
Total time to get 10000 items from disk=136186 ms
Total time to get 10000 items from disk=132578 ms
Total time to get 10000 items from disk=102637 ms
Total time to get 10000 items from disk=133594 ms
Total time to get 10000 items from disk=103777 ms
Total time to get 10000 items from disk=134248 ms
Total time to get 10000 items from disk=103129 ms
Total time to get 10000 items from disk=133812 ms
Total time to get 10000 items from disk=102486 ms
Total time to get 10000 items from disk=132578 ms
Total time to get 10000 items from disk=103015 ms
Total time to get 10000 items from disk=134483 ms
Total time to get 10000 items from disk=130861 ms
Total time to get 10000 items from disk=107252 ms
Total time to get 10000 items from disk=132857 ms
Use MultiplePointerSimpleHashFile: 12263782 milliseconds for 1000000 objects to get.


File size: 2868888890
20000000 items in file.
Use NewMultiplePointerSimpleHashFile: 
Total time to get 10000 items from disk=106579 ms
Total time to get 10000 items from disk=137847 ms
Total time to get 10000 items from disk=110015 ms
Total time to get 10000 items from disk=135205 ms
Total time to get 10000 items from disk=102482 ms
Total time to get 10000 items from disk=133345 ms
Total time to get 10000 items from disk=105237 ms
Total time to get 10000 items from disk=129487 ms
Total time to get 10000 items from disk=133515 ms
Total time to get 10000 items from disk=103608 ms
Total time to get 10000 items from disk=132626 ms
Total time to get 10000 items from disk=102685 ms
Total time to get 10000 items from disk=132922 ms
Total time to get 10000 items from disk=103013 ms
Total time to get 10000 items from disk=134017 ms
Total time to get 10000 items from disk=103263 ms
Total time to get 10000 items from disk=132016 ms
Total time to get 10000 items from disk=103174 ms
Total time to get 10000 items from disk=133124 ms
Total time to get 10000 items from disk=133093 ms
Total time to get 10000 items from disk=102375 ms
Total time to get 10000 items from disk=133578 ms
Total time to get 10000 items from disk=102982 ms
Total time to get 10000 items from disk=134281 ms
Total time to get 10000 items from disk=101765 ms
Total time to get 10000 items from disk=131845 ms
Total time to get 10000 items from disk=102934 ms
Total time to get 10000 items from disk=131500 ms
Total time to get 10000 items from disk=102970 ms
Total time to get 10000 items from disk=133237 ms
Total time to get 10000 items from disk=131061 ms
Total time to get 10000 items from disk=105235 ms
Total time to get 10000 items from disk=133513 ms
Total time to get 10000 items from disk=103346 ms
Total time to get 10000 items from disk=132248 ms
Total time to get 10000 items from disk=103969 ms
Total time to get 10000 items from disk=133938 ms
Total time to get 10000 items from disk=102171 ms
Total time to get 10000 items from disk=131590 ms
Total time to get 10000 items from disk=104189 ms
Total time to get 10000 items from disk=133747 ms
Total time to get 10000 items from disk=132610 ms
Total time to get 10000 items from disk=103327 ms
Total time to get 10000 items from disk=132902 ms
Total time to get 10000 items from disk=102234 ms
Total time to get 10000 items from disk=132595 ms
Total time to get 10000 items from disk=102044 ms
Total time to get 10000 items from disk=134111 ms
Total time to get 10000 items from disk=103280 ms
Total time to get 10000 items from disk=133359 ms
Total time to get 10000 items from disk=103220 ms
Total time to get 10000 items from disk=133325 ms
Total time to get 10000 items from disk=108847 ms
Total time to get 10000 items from disk=126967 ms
Total time to get 10000 items from disk=132468 ms
Total time to get 10000 items from disk=103216 ms
Total time to get 10000 items from disk=132330 ms
Total time to get 10000 items from disk=103951 ms
Total time to get 10000 items from disk=134076 ms
Total time to get 10000 items from disk=103529 ms
Total time to get 10000 items from disk=132626 ms
Total time to get 10000 items from disk=103765 ms
Total time to get 10000 items from disk=133973 ms
Total time to get 10000 items from disk=167923 ms
Total time to get 10000 items from disk=102752 ms
Total time to get 10000 items from disk=134280 ms
Total time to get 10000 items from disk=102689 ms
Total time to get 10000 items from disk=133015 ms
Total time to get 10000 items from disk=102610 ms
Total time to get 10000 items from disk=133734 ms
Total time to get 10000 items from disk=109611 ms
Total time to get 10000 items from disk=126891 ms
Total time to get 10000 items from disk=132403 ms
Total time to get 10000 items from disk=103998 ms
Total time to get 10000 items from disk=133389 ms
Total time to get 10000 items from disk=101797 ms
Total time to get 10000 items from disk=135062 ms
Total time to get 10000 items from disk=104127 ms
Total time to get 10000 items from disk=135500 ms
Total time to get 10000 items from disk=103141 ms
Total time to get 10000 items from disk=135269 ms
Total time to get 10000 items from disk=134049 ms
Total time to get 10000 items from disk=105341 ms
Total time to get 10000 items from disk=135782 ms
Total time to get 10000 items from disk=105029 ms
Total time to get 10000 items from disk=133734 ms
Total time to get 10000 items from disk=104154 ms
Total time to get 10000 items from disk=136639 ms
Total time to get 10000 items from disk=105955 ms
Total time to get 10000 items from disk=134218 ms
Total time to get 10000 items from disk=120388 ms
Total time to get 10000 items from disk=119467 ms
Total time to get 10000 items from disk=134406 ms
Total time to get 10000 items from disk=103653 ms
Total time to get 10000 items from disk=134533 ms
Total time to get 10000 items from disk=103466 ms
Total time to get 10000 items from disk=132159 ms
Total time to get 10000 items from disk=104532 ms
Total time to get 10000 items from disk=135121 ms
Total time to get 10000 items from disk=105563 ms
Use NewMultiplePointerSimpleHashFile: 12037625 milliseconds for 1000000 objects to get.
 */
