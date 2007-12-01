package org.talend.designer.components.thash.io.hashimpl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
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

    public int count = 0;

    public void writeArray(List<Data> list) throws FileNotFoundException, IOException {
        Collections.sort(list);
        File file = new File(workDirectory + "TEMP_" + count);
        count++;
        RandomAccessFile rw = new RandomAccessFile(file, "rw");
        for (int i = 0; i < list.size(); i++) {
            write(list.get(i), rw);
        }
        rw.close();
        files.add(file);
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
        //for test do not return the THashMap

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
        RandomAccessFile rr1 = new RandomAccessFile(one, "r");
        RandomAccessFile rr2 = new RandomAccessFile(two, "r");

        File output = new File(workDirectory + "TEMP_" + count);
        
        count++;
        RandomAccessFile rw = new RandomAccessFile(output, "rw");
        Data a = (Data) read(rr1);
        Data b = (Data) read(rr2);

        while (a != null && b != null) {
            if (a.compareTo(b) < 0) {
                write(a, rw);
                if (rr1.getFilePointer() < rr1.length()) {
                    a = (Data) read(rr1);
                } else {
                    a = null;
                }
            } else {
                write(b, rw);
                if (rr2.getFilePointer() < rr2.length()) {
                    b = (Data) read(rr2);
                } else {
                    b = null;
                }
            }
        }

        if (a != null) {
            write(a, rw);
        }

        if (b != null) {
            write(b, rw);
        }

        if (rr1.getFilePointer() < rr1.length()) {
            byte[] buffer = new byte[1024];
            int count = 0;
            while ((count = rr1.read(buffer)) > 0) {
                rw.write(buffer, 0, count);
            }
        }

        if (rr2.getFilePointer() < rr2.length()) {
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

    private void write(Object bean, RandomAccessFile w) throws IOException {
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

        int sizeBytes = byteArrayOutputStream.size();

        w.writeInt(sizeBytes);
        w.write(byteArrayOutputStream.toByteArray());

        byteArrayOutputStream.close();

    }

    private Object read(RandomAccessFile r) throws IOException, ClassNotFoundException {
        byte[] byteArray = new byte[r.readInt()];
        r.read(byteArray);
        return new ObjectInputStream(new ByteArrayInputStream(byteArray)).readObject();
    }

    /**
     * @param args
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExternalSort esort = new ExternalSort();

        int nbItems = 1000000;

        int bufferSize = 100000;

        Random rand = new Random(System.currentTimeMillis());

        long start = System.currentTimeMillis();

        long lastTime = System.currentTimeMillis();

        List<Data> list = new ArrayList<Data>(bufferSize);

        for (int i = 0; i < nbItems;) {
            int v = rand.nextInt(nbItems);

            list.add(new Data("test" + v, v, 0));

            i++;
            if (i % bufferSize == 0) {
                esort.writeArray(list);
                list = new ArrayList<Data>(bufferSize);
            }
        }
        if (list.size() > 0) {
            esort.writeArray(list);
        }
        esort.sort();

        long end = System.currentTimeMillis();

        long deltaTime = (end - start);

        int itemsPerSec = (int) ((float) nbItems / (float) deltaTime * 1000f);

        System.out.println(deltaTime + " milliseconds for " + nbItems + " objects to WRITE. " + itemsPerSec + "  items/s ");

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

    @Override
    public int compareTo(Data o) {
        return this.id - o.id;
    }

}
