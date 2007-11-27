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

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;


/**
 * DOC amaumont  class global comment. Detailled comment
 * <br/>
 *
 * $Id: talend.epf 1 2006-09-29 17:06:40Z nrousseau $
 *
 */
public class Test {

    public static void main(String[] args) {
        
        System.out.println(Long.SIZE);
        System.out.println(Integer.SIZE);
        System.out.println(Short.SIZE);
        long intAddressable = ((long)Integer.MAX_VALUE) - ((long)Integer.MIN_VALUE);
        
        System.out.println("(int)(long)(Integer.MAX_VALUE + 1)="+(int)(long)(Integer.MAX_VALUE + 1));
        System.out.println("(long)(int)(long)(Integer.MAX_VALUE + 1)="+(long)(int)(long)(Integer.MAX_VALUE + 1));
        
        
        
        System.out.println("intAddressable / 121 = " + intAddressable/121);
        System.out.println(intAddressable);
        System.out.println(Integer.MAX_VALUE/8);
        System.out.println(intAddressable/8 );
        int shortAddressable = ((int)Short.MAX_VALUE) - ((int)Short.MIN_VALUE);
        System.out.println("short=" +shortAddressable );
        System.out.println("short2=" +shortAddressable/8 );
        
        System.out.println(24 % 100);
        System.out.println(2444024 % 25);
//        ByteArrayOutputStream bao = new ByteArrayOutputStream();
//        DataOutputStream dos = new DataOutputStream(bao);
//        ObjectOutputStream oos = new ObjectOutputStream(dos);
//        oos.writeObject(object);
//        dos.write(..)
//        
//        randomAccessFile.write(bao.toByteArray()
        
        
    }
    
}
