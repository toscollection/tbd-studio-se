package org.talend.designer.components.persistent.benchs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class BenchStreamMain {

    static class Data implements Serializable {

        long longValue = 0;

        String stringValue = "my string value";

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        String filePath = "/home/amaumont/hash_benchs/out/benchStream.bin";

        Data data = new Data();

        long count = 1000000;
        // long count = 10;

        long timeStart = 0;
        long timeDelta = 0;

        int sizeBefore = 0;
        int sizeAfter = 0;
        int sizeAll = 0;

        DataOutputStream dosWithBuffer = null;
        ObjectOutputStream oosDosWithBuffer = null;

        if (false) {

            ObjectOutputStream oosWithoutBuffer = new ObjectOutputStream(new FileOutputStream(filePath));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                oosWithoutBuffer.writeLong(i);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time oosWithoutBuffer for write long: " + timeDelta + " ms");
            oosWithoutBuffer.close();

            oosWithoutBuffer = new ObjectOutputStream(new FileOutputStream(filePath));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                oosWithoutBuffer.writeObject(data);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time oosWithoutBuffer for write Object: " + timeDelta + " ms");
            oosWithoutBuffer.close();

            ObjectOutputStream oosWithBuffer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(
                    filePath)));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                oosWithBuffer.writeLong(i);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time oosWithBuffer for write long: " + timeDelta + " ms");
            oosWithBuffer.close();

            oosWithBuffer = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                oosWithBuffer.writeObject(data);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time oosWithBuffer for write Object: " + timeDelta + " ms");
            oosWithBuffer.close();

            DataOutputStream dosWithoutBuffer = new DataOutputStream(new FileOutputStream(filePath));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                dosWithoutBuffer.writeLong(i);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time dosWithoutBuffer for write long: " + timeDelta + " ms");
            dosWithoutBuffer.close();

            dosWithoutBuffer = new DataOutputStream(new FileOutputStream(filePath));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(data);
                oos.flush();
                oos.close();
                dosWithoutBuffer.write(baos.toByteArray());
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time dosWithoutBuffer for write Object: " + timeDelta + " ms");
            dosWithoutBuffer.close();

            dosWithBuffer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                dosWithBuffer.writeLong(i);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time dosWithBuffer: " + timeDelta + " ms");
            dosWithBuffer.close();

            dosWithBuffer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ObjectOutputStream oos = new ObjectOutputStream(baos);
                oos.writeObject(data);
                oos.flush();
                oos.close();
                dosWithBuffer.write(baos.toByteArray());
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time dosWithBuffer to write Object: " + timeDelta + " ms");
            dosWithBuffer.close();

            dosWithBuffer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            oosDosWithBuffer = new ObjectOutputStream(dosWithBuffer);
            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // oos.writeObject(data);
            // oos.flush();
            // oos.close();
            // dosWithBuffer.write(baos.toByteArray());
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                sizeBefore = dosWithBuffer.size();
                // oosDosWithBuffer.writeLong(i);
                dosWithBuffer.writeLong(i);
                // oosDosWithBuffer.flush();
                sizeAfter = dosWithBuffer.size();
                sizeAll += sizeAfter - sizeBefore;
                // System.out.println("sizeBefore=" + sizeBefore + ", sizeAfter=" + sizeAfter);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time oosDosWithBuffer to write long: " + timeDelta + " ms, sizeAll=" + sizeAll);
            oosDosWithBuffer.close();

            dosWithBuffer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
            oosDosWithBuffer = new ObjectOutputStream(dosWithBuffer);
            // ByteArrayOutputStream baos = new ByteArrayOutputStream();
            // baos.
            // oos.writeObject(data);
            // oos.flush();
            // oos.close();
            // dosWithBuffer.write(baos.toByteArray());
            sizeAll = 0;
            timeStart = System.currentTimeMillis();
            for (long i = 0; i < count; i++) {
                sizeBefore = dosWithBuffer.size();
                oosDosWithBuffer.writeObject(data);
                // oosDosWithBuffer.flush();
                sizeAfter = dosWithBuffer.size();
                sizeAll += sizeAfter - sizeBefore;
                // System.out.println("sizeBefore=" + sizeBefore + ", sizeAfter=" + sizeAfter);
            }
            timeDelta = System.currentTimeMillis() - timeStart;
            System.out.println("Time oosDosWithBuffer to write Object: " + timeDelta + " ms, sizeAll=" + sizeAll);
            oosDosWithBuffer.close();

        }

        dosWithBuffer = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(filePath)));
        oosDosWithBuffer = new ObjectOutputStream(dosWithBuffer);
        sizeAll = 0;
        timeStart = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            sizeBefore = dosWithBuffer.size();
            dosWithBuffer.writeLong(i);
            oosDosWithBuffer.writeObject(data);
            // oosDosWithBuffer.flush();
            sizeAfter = dosWithBuffer.size();
            sizeAll += sizeAfter - sizeBefore;
            // System.out.println("sizeBefore=" + sizeBefore + ", sizeAfter=" + sizeAfter);
        }
        timeDelta = System.currentTimeMillis() - timeStart;
        System.out.println("Time ALL oosDosWithBuffer to write (long, Object): " + timeDelta + " ms, sizeAll="
                + sizeAll);
        oosDosWithBuffer.close();

        DataInputStream disWithBuffer = new DataInputStream(new BufferedInputStream(new FileInputStream(filePath)));
        ObjectInputStream oisDisWithBuffer = new ObjectInputStream(disWithBuffer);
        sizeAll = 0;
        timeStart = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            oisDisWithBuffer.readLong();
            oisDisWithBuffer.readObject();
            // oisDisWithBuffer.flush();
            // System.out.println("sizeBefore=" + sizeBefore + ", sizeAfter=" + sizeAfter);
        }
        timeDelta = System.currentTimeMillis() - timeStart;
        System.out.println("Time ALL oisDisWithBuffer to read (long, Object): " + timeDelta + " ms");
        oisDisWithBuffer.close();

    }

}
