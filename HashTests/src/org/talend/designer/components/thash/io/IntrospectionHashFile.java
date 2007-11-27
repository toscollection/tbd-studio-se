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

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
class IntrospectionHashFile {

    private static IntrospectionHashFile instance;

    private IntrospectionHashFile() {
    }

    /**
     * getInstance.
     * 
     * @return the instance if this project handler
     */
    public static synchronized IntrospectionHashFile getInstance() {
        if (instance == null) {
            instance = new IntrospectionHashFile();
        }
        return instance;
    }

    RandomAccessFile bw = null;

    boolean readonly;

    RandomAccessFile ra = null;

    Object lastRetrievedObject;

    long lastRetrievedCursorPosition = -1;

    String[] names = null;

    int[] types = null;

    private Class<InternalSmallBean> beanClass;

    public Object get(String container, long cursorPosition) throws IOException, ClassNotFoundException,
            InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        if (cursorPosition != lastRetrievedCursorPosition) {
            lastRetrievedObject = beanClass.newInstance();
            ra.seek(cursorPosition);
            for (int i = 0; i < types.length; i++) {
                read(i);
            }
            lastRetrievedCursorPosition = cursorPosition;
        }
        return lastRetrievedObject;

    }

    public long put(String container, Object bean) throws IOException, IllegalAccessException,
            InvocationTargetException, NoSuchMethodException {
        long returnPosition = bw.getFilePointer();
        if (!readonly) {
            lastRetrievedObject = bean;
            for (int i = 0; i < names.length; i++) {
                write(i);
            }
        }
        return returnPosition;
    }

    public void initPut(String container) throws IOException {
        if (!readonly) {
            File file = new File(container);
            file.delete();
            bw = new RandomAccessFile(container, "rw");

        }
    }

    public void endPut() throws IOException {
        if (!readonly) {
            bw.close();
        }
    }

    public void initGet(String container) throws FileNotFoundException {
        ra = new RandomAccessFile(container, "r");
    }

    public void endGet(String container) throws IOException {
        if (!readonly) {
            if (ra != null) {
                ra.close();
            }
            File file = new File(container);
            file.delete();
        }
    }

    public void init(Class beanClass) {
        this.beanClass = beanClass;
        PropertyDescriptor[] propertyDescriptors = PropertyUtils.getPropertyDescriptors(beanClass);
        names = new String[propertyDescriptors.length - 1];
        types = new int[propertyDescriptors.length - 1];
        for (int i = 0, j = 0; i < propertyDescriptors.length; i++) {
            if (propertyDescriptors[i].getName().equals("class")) {
                continue;
            }
            names[j] = propertyDescriptors[i].getName();
            String type = propertyDescriptors[i].getPropertyType().getSimpleName();
            if (type.equals("boolean")) {
                types[j] = 0;
            } else if (type.equals("byte")) {
                types[j] = 1;
            } else if (type.equals("short")) {
                types[j] = 2;
            } else if (type.equals("int")) {
                types[j] = 3;
            } else if (type.equals("long")) {
                types[j] = 4;
            } else if (type.equals("float")) {
                types[j] = 5;
            } else if (type.equals("double")) {
                types[j] = 6;
            } else if (type.equals("char")) {
                types[j] = 7;
            } else if (type.equals("String")) {
                types[j] = 8;
            } else if (type.equals("byte[]")) {
                types[j] = 9;
            } else if (type.equals("Date")) {
                types[j] = 10;
            } else {
                types[j] = 11;
            }
            j++;
        }
    }

    public void write(int index) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = PropertyUtils.getSimpleProperty(lastRetrievedObject, names[index]);
        switch (types[index]) {
        case 0:
            bw.writeBoolean((Boolean) value);
            return;
        case 1:
            bw.writeByte((Byte) value);
            return;
        case 2:
            bw.writeShort((Short) value);
            return;
        case 3:
            bw.writeInt((Integer) value);
            return;
        case 4:
            bw.writeLong((Long) value);
            return;
        case 5:
            bw.writeFloat((Float) value);
            return;
        case 6:
            bw.writeDouble((Double) value);
            return;
        case 7:
            bw.writeChar((Character) value);
            return;
        case 8:
            byte[] byteArray = null;
            if (value == null) {
                byteArray = new byte[0];
            } else {
                byteArray = ((String) value).getBytes();
            }
            bw.writeInt(byteArray.length);
            bw.write(byteArray);
            return;
        case 9:
            if (value == null) {
                byteArray = new byte[0];
            } else {
                byteArray = (byte[]) value;
            }
            bw.writeInt(byteArray.length);
            bw.write(byteArray);
            return;
        case 10:
            if (value == null) {
                bw.writeLong((long) 0);
            } else {
                bw.writeLong(((Date) value).getTime());
            }
            return;
        default:
            if (value == null) {
                byteArray = new byte[0];
            } else {
                byteArray = value.toString().getBytes();
            }
            bw.writeInt(byteArray.length);
            bw.write(byteArray);
        }
    }

    public void read(int index) throws IOException, IllegalAccessException, InvocationTargetException,
            NoSuchMethodException {
        int type = types[index];

        switch (type) {
        case 0:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readBoolean());
            return;
        case 1:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readByte());
            return;
        case 2:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readShort());
            return;
        case 3:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readInt());
            return;
        case 4:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readLong());
            return;
        case 5:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readFloat());
            return;
        case 6:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readDouble());
            return;
        case 7:
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], ra.readChar());
            return;
        case 8:
            byte[] byteArray = new byte[ra.readInt()];
            ra.read(byteArray);
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], new String(byteArray));
            return;
        case 9:
            byteArray = new byte[ra.readInt()];
            ra.read(byteArray);
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], byteArray);
            return;
        case 10:
            long time = ra.readLong();
            if (time == 0) {
                PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], null);
            } else {
                PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], new Date(time));
            }
            return;
        default:
            byteArray = new byte[ra.readInt()];
            ra.read(byteArray);
            PropertyUtils.setSimpleProperty(lastRetrievedObject, names[index], byteArray.toString());
            return;
        }
    }

}
