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

import org.talend.designer.components.thash.io.beans.ILightSerializable;
import org.talend.designer.components.thash.io.beans.KeyForMap;

/**
 * 
 * DOC slanglois class global comment. Detailled comment <br/>
 * 
 */
public class ExternalSort {

    public List<File> files = new ArrayList<File>();

    // public String workDirectory = "D:/temp/";

    public String workDirectory = "/home/amaumont/hash_benchs/external_sort/";

    public int count = 0;

    ILightSerializable iLightSerializable = new Data();

    /**
     * sort list and then use light serialization to store Data
     * 
     * @param list
     * @throws FileNotFoundException
     * @throws IOException
     */
    public void writeBuffer2(Data[] list, int length) throws FileNotFoundException, IOException {
        long time1 = System.currentTimeMillis();
        System.out.println("Sorting buffer...");

        Arrays.sort(list, 0, length);

        long time2 = System.currentTimeMillis();
        long deltaTimeSort = (time2 - time1);
        int itemsPerSecSort = (int) ((float) length / (float) deltaTimeSort * 1000f);
        System.out.println(deltaTimeSort + " milliseconds for " + length + " objects to sort in memory. " + itemsPerSecSort
                + "  items/s ");

        time1 = System.currentTimeMillis();
        System.out.println("Writing ordered buffer in file...");

        File file = new File(workDirectory + "TEMP_" + count);
        count++;
        DataOutputStream rw = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        byte[] bytes = null;
        for (int i = 0; i < length; i++) {
            bytes = list[i].toByteArray();
            rw.writeInt(bytes.length);
            rw.write(bytes);
        }
        rw.close();
        files.add(file);

        time2 = System.currentTimeMillis();
        long deltaTimeWrite = (time2 - time1);
        int itemsPerSecWrite = (int) ((float) length / (float) deltaTimeWrite * 1000f);
        System.out.println(deltaTimeWrite + " milliseconds for " + length + " objects to write in file. " + itemsPerSecWrite
                + "  items/s ");

    }

    /**
     * Merger all the sorted files for one time(data stored in the files used light serialization);
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void mergeFiles2() throws IOException, ClassNotFoundException {
        File file = new File(workDirectory + "TEMP_" + count);

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int numFiles = files.size();
        List<DataInputStream> diss = new ArrayList<DataInputStream>();
        List<ILightSerializable> datas = new ArrayList<ILightSerializable>();
        List<Long> positions = new ArrayList<Long>();
        List<Long> fileLengths = new ArrayList<Long>();

        boolean someFileStillHasRows = false;
        byte[] bytes = null;
        DataInputStream dis = null;
        for (int i = 0; i < numFiles; i++) {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(files.get(i))));
            diss.add(dis);
            fileLengths.add(files.get(i).length());
            if (0 < fileLengths.get(i)) {
                bytes = new byte[dis.readInt()];
                dis.read(bytes);
                datas.add(iLightSerializable.createInstance(bytes));
                if (!someFileStillHasRows) {
                    someFileStillHasRows = true;
                }
                positions.add((long) (4 + bytes.length));
            } else {
                datas.add(null);
            }
        }

        bytes = null;
        dis = null;
        ILightSerializable dc = null;
        long position = -1;

        while (someFileStillHasRows) {
            ILightSerializable min = null;
            int minIndex = 0;
            dc = datas.get(0);

            if (dc != null) {
                min = dc;
                minIndex = 0;
            } else {
                min = null;
                minIndex = -1;
            }

            // check which one is min
            for (int i = 1; i < datas.size(); i++) {
                dc = datas.get(i);

                if (min != null) {
                    if (dc != null && dc.compareTo(min) < 0) {
                        minIndex = i;
                        min = dc;
                    }
                } else {
                    if (dc != null) {
                        min = dc;
                        minIndex = i;
                    }
                }
            }

            if (minIndex < 0) {
                someFileStillHasRows = false;
            } else {
                // write to the sorted file
                bytes = min.toByteArray();
                dos.writeInt(bytes.length);
                dos.write(bytes);
                bytes = null;

                // get another data from the file
                position = positions.get(minIndex);
                if (position < fileLengths.get(minIndex)) {
                    dis = diss.get(minIndex);
                    bytes = new byte[dis.readInt()];
                    dis.read(bytes);
                    datas.set(minIndex, iLightSerializable.createInstance(bytes));
                    positions.set(minIndex, position + 4 + bytes.length);
                    bytes = null;
                } else {
                    datas.set(minIndex, null);
                }

                // check if one still has data
                someFileStillHasRows = false;
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i) != null) {
                        someFileStillHasRows = true;
                        break;
                    }
                }
            }
        }

        // close all the streams
        dos.close();
        for (int i = 0; i < diss.size(); i++) {
            diss.get(i).close();
        }
        // delete files
        for (int i = 0; i < files.size(); i++) {
            files.get(i).delete();
        }
    }

    /**
     * same as mergeFiles2(). this should perform better if the number of files is big.
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void eMergeFiles2() throws IOException, ClassNotFoundException {
        File file = new File(workDirectory + "TEMP_" + count);

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int numFiles = files.size();
        List<DataInputStream> diss = new ArrayList<DataInputStream>();
        List<ILightSerializable> datas = new ArrayList<ILightSerializable>();
        List<Long> positions = new ArrayList<Long>();
        List<Long> fileLengths = new ArrayList<Long>();

        byte[] bytes = null;
        DataInputStream dis = null;
        int fileCount = 0;
        for (int i = 0; i < numFiles; i++) {
            dis = new DataInputStream(new BufferedInputStream(new FileInputStream(files.get(i))));
            long fileLength = files.get(i).length();
            if (0 < fileLength) {
                bytes = new byte[dis.readInt()];
                dis.read(bytes);
                datas.add(iLightSerializable.createInstance(bytes));
                diss.add(dis);
                fileLengths.add(fileLength);
                positions.add((long) (4 + bytes.length));
                fileCount++;
            }
        }

        bytes = null;
        dis = null;
        ILightSerializable dc = null;
        long position = -1;

        while (fileCount > 1) {
            ILightSerializable min = null;
            int minIndex = 0;
            min = datas.get(0);

            // check which one is min
            for (int i = 1; i < fileCount; i++) {
                dc = datas.get(i);

                if (dc.compareTo(min) < 0) {
                    minIndex = i;
                    min = dc;
                }
            }

            // write to the sorted file
            bytes = min.toByteArray();
            dos.writeInt(bytes.length);
            dos.write(bytes);
            bytes = null;

            // get another data from the file
            position = positions.get(minIndex);
            dis = diss.get(minIndex);
            if (position < fileLengths.get(minIndex)) {
                bytes = new byte[dis.readInt()];
                dis.read(bytes);
                datas.set(minIndex, iLightSerializable.createInstance(bytes));
                positions.set(minIndex, position + 4 + bytes.length);
                bytes = null;
            } else {
                dis.close();
                diss.remove(minIndex);
                datas.remove(minIndex);
                positions.remove(minIndex);
                fileLengths.remove(minIndex);
                fileCount--;
            }
        }

        // copy the last file contents to dos
        bytes = datas.get(0).toByteArray();
        dos.writeInt(bytes.length);
        dos.write(bytes);

        dis = diss.get(0);
        bytes = new byte[1024];
        int count = 0;
        while ((count = dis.read(bytes)) > 0) {
            dos.write(bytes, 0, count);
        }

        // close all the streams
        dos.close();
        dis.close();

        // delete files
        for (int i = 0; i < files.size(); i++) {
            files.get(i).delete();
        }
    }

    // ////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * sort list and then use light serialization to store Data
     * 
     * @param list
     * @throws FileNotFoundException
     * @throws IOException
     */
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

    /**
     * Merger all the sorted files for one time, the function is the same as sort();
     * 
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public void mergeFiles() throws IOException, ClassNotFoundException {
        File file = new File(workDirectory + "TEMP_" + count);

        DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(file)));
        int numFiles = files.size();
        List<DataInputStream> diss = new ArrayList<DataInputStream>();
        List<DataContainer> datas = new ArrayList<DataContainer>();
        List<Long> fileLengths = new ArrayList<Long>();

        boolean someFileStillHasRows = false;

        for (int i = 0; i < numFiles; i++) {
            diss.add(new DataInputStream(new BufferedInputStream(new FileInputStream(files.get(i)))));
            fileLengths.add(files.get(i).length());
            DataContainer dc = new DataContainer();
            if (dc.cursorPosition < fileLengths.get(i)) {
                read(diss.get(i), dc);
                if (!someFileStillHasRows) {
                    someFileStillHasRows = true;
                }
            }
            datas.add(dc);
        }

        DataContainer dataContainer;

        while (someFileStillHasRows) {
            DataContainer min = null;
            int minIndex = 0;
            dataContainer = datas.get(0);

            if (dataContainer.object != null) {
                min = dataContainer;
                minIndex = 0;
            } else {
                min = null;
                minIndex = -1;
            }

            // check which one is min
            for (int i = 1; i < datas.size(); i++) {
                dataContainer = datas.get(i);

                if (min != null) {
                    if (dataContainer.object != null && ((Comparable) (dataContainer.object)).compareTo(min.object) < 0) {
                        minIndex = i;
                        min = dataContainer;
                    }
                } else {
                    if (dataContainer.object != null) {
                        min = dataContainer;
                        minIndex = i;
                    }
                }
            }

            if (minIndex < 0) {
                someFileStillHasRows = false;
            } else {
                // write to the sorted file
                write(min.data, dos);
                min.reset();

                // get another data from the file
                if (min.cursorPosition < fileLengths.get(minIndex)) {
                    read(diss.get(minIndex), min);
                }
                // check if one still has data
                someFileStillHasRows = false;
                for (int i = 0; i < datas.size(); i++) {
                    if (datas.get(i).object != null) {
                        someFileStillHasRows = true;
                        break;
                    }
                }
            }
        }

        // close all the streams
        dos.close();
        for (int i = 0; i < diss.size(); i++) {
            diss.get(i).close();
        }
        // delete files
        for (int i = 0; i < files.size(); i++) {
            files.get(i).delete();
        }
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
        // int nbItems = 60000000;
        // int bufferSize = 4000000;
        int nbItems = 60000000;
        int bufferSize = 10000000;
//         int nbItems = 10000000;
//         int bufferSize = 1000000;
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

                // esort.writeBuffer(arrayData);
                esort.writeBuffer2(arrayData, arrayData.length);

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

        // esort.sort();
        // esort.mergeFiles();
        // esort.mergeFiles2();
        esort.eMergeFiles2();

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
