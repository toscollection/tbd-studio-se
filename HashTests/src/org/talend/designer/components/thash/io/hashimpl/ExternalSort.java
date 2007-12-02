package org.talend.designer.components.thash.io.hashimpl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.talend.designer.components.thash.io.beans.KeyForMap;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
public class ExternalSort {

    public List<File> files = new ArrayList<File>();

    public String workDirectory = "D:/temp/";

    // public String workDirectory = "/home/amaumont/hash_benchs/external_sort/";

    public int count = 0;

    public void writeBuffer(Data[] list) throws FileNotFoundException, IOException {
        long time1 = System.currentTimeMillis();
        System.out.println("Sorting buffer...");

        Arrays.sort(list);

        long time2 = System.currentTimeMillis();
        long deltaTimeSort = (time2 - time1);
        int itemsPerSecSort = (int) ((float) list.length / (float) deltaTimeSort * 1000f);
        System.out.println(deltaTimeSort + " milliseconds for " + list.length + " objects to sort in memory. " + itemsPerSecSort
                + "  items/s ");

        time1 = System.currentTimeMillis();
        System.out.println("Writing ordered buffer in file...");

        File file = new File(workDirectory + "TEMP_" + count);
        count++;
        DataOutputStream rw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        for (int i = 0; i < list.length; i++) {
            write(list[i], rw);
        }
        rw.close();
        files.add(file);

        time2 = System.currentTimeMillis();
        long deltaTimeWrite = (time2 - time1);
        int itemsPerSecWrite = (int) ((float) list.length / (float) deltaTimeWrite * 1000f);
        System.out.println(deltaTimeWrite + " milliseconds for " + list.length + " objects to write in file. " + itemsPerSecWrite
                + "  items/s ");

    }

    public Map<KeyForMap, KeyForMap> sort() throws IOException, ClassNotFoundException {

        while (files.size() > 1) {// for test, do not return THashMap

            List<File> inter = new ArrayList<File>();

            for (Iterator<File> itr = files.iterator(); itr.hasNext();) {
                File one = itr.next();
                if (itr.hasNext()) {
                    File two = itr.next();
                    inter.add(merge(one, two));
                    one.delete();
                    two.delete();
                } else {
                    inter.add(0, one);
                }
            }
            files = inter;
        }
        // for test do not return the THashMap

        return null;

    }

    /**
     * Merges two sorted files into a single file.
     * 
     * @param one
     * @param two
     * @return
     * @throws IOException
     * @throws ClassNotFoundException
     */
    private File merge(File one, File two) throws IOException, ClassNotFoundException {

        DataInputStream rr1 = new DataInputStream(new BufferedInputStream(new FileInputStream(one)));
        DataInputStream rr2 = new DataInputStream(new BufferedInputStream(new FileInputStream(two)));

        long rr1Length = one.length();
        long rr2Length = two.length();

        DataContainer a = new DataContainer();
        DataContainer b = new DataContainer();

        File output = new File(workDirectory + "TEMP_" + count);

        count++;
        DataOutputStream rw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
        read(rr1, a);
        read(rr2, b);

        while (a.object != null && b.object != null) {
            if (((Comparable) (a.object)).compareTo(b.object) < 0) {
                write(a.data, rw);
                if (a.cursorPosition < rr1Length) {
                    read(rr1, a);
                } else {
                    a.reset();
                }
            } else {
                write(b.data, rw);
                if (b.cursorPosition < rr2Length) {
                    read(rr2, b);
                } else {
                    b.reset();
                }
            }
        }

        if (a.data != null) {
            write(a.data, rw);
        }

        if (b.data != null) {
            write(b.data, rw);
        }

        if (a.cursorPosition < rr1Length) {
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = rr1.read(buffer)) > 0) {
                rw.write(buffer, 0, count);
            }
        }

        if (b.cursorPosition < rr2Length) {
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = rr2.read(buffer)) > 0) {
                rw.write(buffer, 0, count);
            }
        }

        rr1.close();
        rr2.close();
        rw.close();

        return output;
    }

    private void write(Object bean, DataOutputStream w) throws IOException {
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

        w.writeInt(byteArrayOutputStream.size());
        w.write(byteArrayOutputStream.toByteArray());

        byteArrayOutputStream.close();

    }

    private void write(byte[] data, DataOutputStream w) throws IOException {
        w.writeInt(data.length);
        w.write(data);
    }

    private void read(DataInputStream r, DataContainer dataContainer) throws IOException, ClassNotFoundException {
        dataContainer.data = new byte[r.readInt()];
        r.read(dataContainer.data);
        dataContainer.object = new ObjectInputStream(new ByteArrayInputStream(dataContainer.data)).readObject();
        dataContainer.cursorPosition += 4 + dataContainer.data.length;
    }

    /**
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExternalSort esort = new ExternalSort();

        NumberFormat numberFormat = NumberFormat.getInstance();

        // int nbItems = 60000000;
        // int bufferSize = 2000000;
        int nbItems = 60000000;
        int bufferSize = 10000000;
        // int nbItems = 1000000;
        // int bufferSize = 100000;
        // int nbItems = 20;
        // int bufferSize = 2;

        Random rand = new Random(System.currentTimeMillis());

        long start = System.currentTimeMillis();

        Data[] arrayData = new Data[bufferSize];

        int nbItemsProcessed = 0;

        for (int i = 0; nbItemsProcessed < nbItems; i++) {
            int v = rand.nextInt(nbItems);

            arrayData[i] = new Data("test" + v, v, 0);

            if (i == bufferSize - 1) {

                esort.writeBuffer(arrayData);

                long time1 = System.currentTimeMillis();

                Arrays.fill(arrayData, null);

                long time2 = System.currentTimeMillis();
                long deltaTimeNull = (time2 - time1);
                System.out.println(deltaTimeNull + " milliseconds for " + bufferSize + " objects to set buffer as null. ");

                nbItemsProcessed += i + 1;
                System.out.println(numberFormat.format(nbItemsProcessed) + " / " + numberFormat.format(nbItems) + " processed.");
                i = -1;
            }

        }
        System.out.println("Final process : merging file...");
        long time1 = System.currentTimeMillis();

        esort.sort();

        long time2 = System.currentTimeMillis();
        long deltaTimeMerge = (time2 - time1);
        int itemsPerSecMerge = (int) ((float) nbItems / (float) deltaTimeMerge * 1000f);
        System.out.println(deltaTimeMerge + " milliseconds for " + nbItems + " ordered objects to merge. " + itemsPerSecMerge
                + "  items/s ");

        long end = System.currentTimeMillis();

        long deltaTime = (end - start);

        int itemsPerSec = (int) ((float) nbItems / (float) deltaTime * 1000f);

        System.out.println(deltaTime + " milliseconds for " + nbItems + " objects all sort process. " + itemsPerSec
                + "  items/s ");

    }

}

class DataContainer {

    long cursorPosition;

    Object object;

    byte[] data;

    public void reset() {
        object = null;
        data = null;
    }
}

/**
 * A simple example class for a data object.
 */
class Data implements Serializable, Comparable<Data> {

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

}
