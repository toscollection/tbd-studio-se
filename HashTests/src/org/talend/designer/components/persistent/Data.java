package org.talend.designer.components.persistent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;

import org.talend.designer.components.thash.io.beans.ILightSerializable;

/**
 * A simple example class for a data object.
 */
class Data implements Serializable, ILightSerializable {

    /**
     * Name of the person.
     */
    protected String name;

    /**
     * Id of the person.
     */
    protected int id;

    /**
     * Income of the person.
     */
    protected double income;

    /**
     * Creates a data object.
     * 
     * @param name name of the person
     * @param age age of the person
     * @param income income of the person
     */
    public Data(String name, int age, double income) {
        this.name = name;
        this.id = age;
        this.income = income;
    }

    public Data() {

    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    public String toString() {
        return "name=" + name + ", " + "age=" + id;
    }

    public int compareTo(Data o) {
        return this.id - o.id;
    }

    public ILightSerializable createInstance(byte[] byteArray) {
        Data result = new Data();

        ByteArrayInputStream byteArrayInputStream = null;
        DataInputStream dataInputStream = null;

        try {
            byteArrayInputStream = new ByteArrayInputStream(byteArray);
            dataInputStream = new DataInputStream(byteArrayInputStream);

            int length = dataInputStream.readInt();
            byte[] bytes = null;
            if (length == -1) {
                result.name = null;
            } else {
                bytes = new byte[length];
                dataInputStream.read(bytes);
                result.name = new String(bytes);
            }
            result.id = dataInputStream.readInt();
            result.income = dataInputStream.readDouble();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataInputStream != null) {
                try {
                    dataInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    public byte[] toByteArray() {
        byte[] bytes = null;
        DataOutputStream dataOutputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            dataOutputStream = new DataOutputStream(byteArrayOutputStream);
            if (this.name == null) {
                dataOutputStream.writeInt(-1);
            } else {
                bytes = this.name.getBytes();
                dataOutputStream.writeInt(bytes.length);
                dataOutputStream.write(bytes);
            }

            dataOutputStream.writeInt(this.id);
            dataOutputStream.writeDouble(this.income);
            dataOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        bytes = byteArrayOutputStream.toByteArray();
        return bytes;
    }

    public int compareTo(Object o) {
        Data data = (Data) o;
        return this.id - data.id;
    }

}
