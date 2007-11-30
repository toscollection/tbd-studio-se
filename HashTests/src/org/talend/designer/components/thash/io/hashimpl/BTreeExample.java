/*
 * XXL: The eXtensible and fleXible Library for data processing
 * 
 * Copyright (C) 2000-2004 Prof. Dr. Bernhard Seeger Head of the Database Research Group Department of Mathematics and
 * Computer Science University of Marburg Germany
 * 
 * This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General
 * Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to
 * the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307, USA
 * 
 * http://www.xxl-library.de
 * 
 * bugs, requests for enhancements: request@xxl-library.de
 * 
 * If you want to be informed on new versions of XXL you can subscribe to our mailing-list. Send an email to
 * 
 * xxl-request@lists.uni-marburg.de
 * 
 * without subject and the word "subscribe" in the message body.
 */

package org.talend.designer.components.thash.io.hashimpl;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.Random;

import xxl.core.collections.containers.Container;
import xxl.core.collections.containers.io.BlockFileContainer;
import xxl.core.collections.containers.io.BufferedContainer;
import xxl.core.collections.containers.io.ConverterContainer;
import xxl.core.comparators.ComparableComparator;
import xxl.core.cursors.Cursor;
import xxl.core.functions.Constant;
import xxl.core.functions.Function;
import xxl.core.indexStructures.BTree;
import xxl.core.indexStructures.ORTree;
import xxl.core.indexStructures.Tree;
import xxl.core.io.Buffer;
import xxl.core.io.Convertable;
import xxl.core.io.LRUBuffer;
import xxl.core.io.converters.ConvertableConverter;
import xxl.core.io.converters.Converter;
import xxl.core.io.converters.DoubleConverter;
import xxl.core.io.converters.IntegerConverter;
import xxl.core.predicates.Predicate;
import xxl.core.util.Interval1D;

/**
 * Example code concerning the creation and reopening of B+-Trees.
 * 
 * A class named <tt>Data</tt> models the objects to be inserted into the B+-Tree. To be able to store objects of
 * class <tt>Data</tt> to disk and read them back into memory, a <tt>Converter</tt> named <tt>objectConverter</tt>
 * is provided. The attributes of <tt>Data</tt> to be indexed are <tt>name</tt> and <tt>age</tt>. Therefore A
 * tailor-cut class <tt>Key</tt> only consists of those two attributes. Because keys have also to be written to disk,
 * <tt>Key</tt> implements the interface <tt>Convertable</tt>. In case of B+-Trees, keys also have to be
 * comparable, so the class <tt>Key</tt> implements the appropriate interface <tt>Comparable</tt>. The Function
 * <tt>getDescriptor</tt> implements how to obtain a key from a <tt>Data</tt> object. This function is called by the
 * B+-Tree each time when an object is inserted into the tree.
 * 
 * Three static methods, createBTree, closeBTree and openBTree allow to work with B+-Trees more easily and can be used
 * without any modification:
 * <ul>
 * <li>{@link #createBTree(String, int, Buffer, Function, int, Converter, int, Converter, Comparator)} creates a new
 * {@link xxl.core.indexStructures.BTree}.</li>
 * <li>{@link #closeBTree(BTree, String, Converter, Comparator)} closes (and saves) the
 * {@link xxl.core.indexStructures.BTree}.</li>
 * <li>{@link #openBTree(String, Buffer, Function, Converter, Converter, Comparator)} reopens the
 * {@link xxl.core.indexStructures.BTree} from disk.</li>
 * </ul>
 * Besides the converters, comparator and functions mentioned above, some more parameters are needed by those three
 * methods. The tree must have a name which will be used as a prefix for the files containing the B+-Tree data.
 * Furthermore, the method <tt>createBTree</tt> needs to know how many bytes are needed maximally to store keys and
 * objects (of class <tt>Data</tt>) on disk.
 */
public class BTreeExample {

    /**
     * A converter suitable for Descriptors.
     * 
     * @param borderConverter a Converter for borders used in the descriptor
     * @param comparator a comparator suitable for descriptors
     * @return a converter suitable for Descriptors
     */
    public static Converter descriptorConverter(final Converter borderConverter, final Comparator comparator) {
        return new Converter() {

            public Object read(DataInput dataInput, Object object) throws IOException {
                return new Interval1D(borderConverter.read(dataInput, null), borderConverter.read(dataInput, null), comparator);
            }

            public void write(DataOutput dataOutput, Object object) throws IOException {
                Interval1D interval = (Interval1D) object;

                borderConverter.write(dataOutput, interval.border(false));
                borderConverter.write(dataOutput, interval.border(true));
            }
        };
    }

    /**
     * Creates a new {@link xxl.core.indexStructures.BTree}
     * 
     * @param treeName a name for the tree
     * @param blockSize the size reserved for storing a block in the used container
     * @param buffer the buffer to use
     * @param getDescriptor function computing a descriptor for a given object
     * @param objectSize size of the objects stored in the tree in bytes
     * @param objectConverter a converter for objects stored in the tree
     * @param borderSize size of borders used in the tree
     * @param borderConverter a converter for borders used in the tree
     * @param comparator comparator for borders used in the tree
     * @return the created BTree
     * @throws Exception
     */
    public static BTree createBTree(final String treeName, final int blockSize, final Buffer buffer,
            final Function getDescriptor, final int objectSize, final Converter objectConverter, final int borderSize,
            final Converter borderConverter, final Comparator comparator) throws Exception {
        final BTree tree = new BTree();
        final int indexEntrySize = 2 * borderSize + 8;
        final int netBlockSize = blockSize - (4 + 2);
        final int fanOut = netBlockSize / indexEntrySize;
        final int leafCapacity = netBlockSize / objectSize;
        final double splitRatio = 0.5;
        final BufferedContainer treeContainer = new BufferedContainer(new ConverterContainer(new BlockFileContainer(treeName,
                blockSize), tree.nodeConverter(objectConverter, borderConverter, comparator)), buffer, true);
        DataOutputStream constMetaData = new DataOutputStream(new FileOutputStream(treeName + ".cmd"));

        constMetaData.writeInt(objectSize);
        constMetaData.writeInt(borderSize);
        constMetaData.close();

        tree.initialize(null, getDescriptor, new Constant(treeContainer), new Constant(treeContainer), new Predicate() {

            public boolean invoke(Object object) {
                Tree.Node node = (Tree.Node) object;
                return node.number() < splitRatio * (node.level() > 0 ? fanOut : leafCapacity);
            }
        }, new Predicate() {

            public boolean invoke(Object object) {
                Tree.Node node = (Tree.Node) object;
                return node.number() > (node.level() > 0 ? fanOut : leafCapacity);
            }
        }, new Constant(splitRatio), new Constant(1.0 - splitRatio));
        return tree;
    }

    /**
     * @param tree the tree to close
     * @param treeName the name of the <tt>tree</tt>
     * @param borderConverter a converter for borders used in the tree
     * @param comparator comparator for borders used in the tree
     * @throws Exception
     */
    public static void closeBTree(BTree tree, String treeName, Converter borderConverter, Comparator comparator) throws Exception {
        Container treeContainer = (Container) tree.getContainer.invoke();
        DataOutputStream varMetaData = new DataOutputStream(new FileOutputStream(treeName + ".vmd"));

        varMetaData.writeInt(tree.height());
        if (tree.height() > 0) {
            Interval1D rootDescriptor = (Interval1D) tree.rootDescriptor();

            descriptorConverter(borderConverter, comparator).write(varMetaData, rootDescriptor);
            treeContainer.objectIdConverter().write(varMetaData, tree.rootEntry().id());
        }
        varMetaData.close();
        treeContainer.close();
    }

    /**
     * @param treeName the name of the <tt>tree</tt>, i.e. the filename
     * @param buffer the buffer to use
     * @param getDescriptor function computing a descriptor for a given object
     * @param objectConverter a converter for objects stored in the tree
     * @param borderConverter a converter for borders used in the tree
     * @param comparator comparator for borders used in the tree
     * @return the read Btree
     * @throws Exception
     */
    public static BTree openBTree(final String treeName, final Buffer buffer, final Function getDescriptor,
            final Converter objectConverter, final Converter borderConverter, final Comparator comparator) throws Exception {
        final BTree tree = new BTree();
        DataInputStream constMetaData = new DataInputStream(new FileInputStream(treeName + ".cmd"));

        final int objectSize = constMetaData.readInt();
        final int borderSize = constMetaData.readInt();
        constMetaData.close();

        final BlockFileContainer blockFileContainer = new BlockFileContainer(treeName);
        final DataInputStream varMetaData = new DataInputStream(new FileInputStream(treeName + ".vmd"));
        final int height = varMetaData.readInt();
        final ORTree.IndexEntry rootEntry = height == 0 ? null : (ORTree.IndexEntry) ((ORTree.IndexEntry) tree
                .createIndexEntry(height)).initialize(
                (Interval1D) descriptorConverter(borderConverter, comparator).read(varMetaData)).initialize(
                blockFileContainer.objectIdConverter().read(varMetaData));
        final int indexEntrySize = 2 * borderSize + 8;
        final int blockSize = blockFileContainer.blockSize();
        final int netBlockSize = blockSize - (4 + 2);
        final int fanOut = netBlockSize / indexEntrySize;
        final int leafCapacity = netBlockSize / objectSize;
        final double splitRatio = 0.5;
        final BufferedContainer treeContainer = new BufferedContainer(new ConverterContainer(blockFileContainer, tree
                .nodeConverter(objectConverter, borderConverter, comparator)), buffer, true);

        varMetaData.close();
        tree.initialize(rootEntry, getDescriptor, new Constant(treeContainer), new Constant(treeContainer), new Predicate() {

            public boolean invoke(Object object) {
                Tree.Node node = (Tree.Node) object;
                return node.number() < splitRatio * (node.level() > 0 ? fanOut : leafCapacity);
            }
        }, new Predicate() {

            public boolean invoke(Object object) {
                Tree.Node node = (Tree.Node) object;
                return node.number() > (node.level() > 0 ? fanOut : leafCapacity);
            }
        }, new Constant(splitRatio), new Constant(1.0 - splitRatio));
        return tree;
    }

    /**
     * A simple example class for a data object.
     */
    public static class Data {

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

    }

    /**
     * @author cammert
     * 
     * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
     * Generation&gt;Code and Comments
     */
    /**
     * @author cammert
     * 
     * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code
     * Generation&gt;Code and Comments
     */
    /**
     * An example class for a key element for {@link BTreeExample.Data}.
     */
    public static class Key implements Comparable, Convertable {

        /**
         * A suitable converter for key objects.
         */
        public static Converter CONVERTER = new ConvertableConverter(new Function() {

            public Object invoke() {
                return new Key();
            }
        });

        /**
         * A comparator for key objects.
         */
        public static Comparator COMPARATOR = ComparableComparator.DEFAULT_INSTANCE;

        /**
         * Name of the person.
         */
        public String name;

        /**
         * Age of the person.
         */
        public int age;

        /**
         * Creates a key object.
         * 
         * @param name name of the person
         * @param age age of the person
         */
        public Key(String name, int age) {
            this.name = name;
            this.age = age;
        }

        /**
         * Creates a key object containing no data.
         */
        public Key() {
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(Object object) {
            Key key = (Key) object;
            return !name.equals(key.name) ? name.compareTo(key.name) : age - key.age;
        }

        /*
         * (non-Javadoc)
         * 
         * @see xxl.core.io.Convertable#read(java.io.DataInput)
         */
        public void read(DataInput dataInput) throws IOException {
            name = dataInput.readUTF();
            age = dataInput.readInt();
        }

        /*
         * (non-Javadoc)
         * 
         * @see xxl.core.io.Convertable#write(java.io.DataOutput)
         */
        public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeUTF(name);
            dataOutput.writeInt(age);
        }
    }

    public static class SimpleIntegerKey implements Comparable, Convertable {
        
        /**
         * A suitable converter for key objects.
         */
        public static Converter CONVERTER = new ConvertableConverter(new Function() {
            
            public Object invoke() {
                return new SimpleIntegerKey();
            }
        });
        
        /**
         * A comparator for key objects.
         */
        public static Comparator COMPARATOR = ComparableComparator.DEFAULT_INSTANCE;
        
        /**
         * Age of the person.
         */
        public int id;
        
        /**
         * Creates a key object.
         * 
         * @param name name of the person
         * @param id age of the person
         */
        public SimpleIntegerKey(int id) {
            this.id = id;
        }
        
        /**
         * Creates a key object containing no data.
         */
        public SimpleIntegerKey() {
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Comparable#compareTo(java.lang.Object)
         */
        public int compareTo(Object object) {
            SimpleIntegerKey key = (SimpleIntegerKey) object;
            return id - key.id;
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see xxl.core.io.Convertable#read(java.io.DataInput)
         */
        public void read(DataInput dataInput) throws IOException {
            id = dataInput.readInt();
        }
        
        /*
         * (non-Javadoc)
         * 
         * @see xxl.core.io.Convertable#write(java.io.DataOutput)
         */
        public void write(DataOutput dataOutput) throws IOException {
            dataOutput.writeInt(id);
        }
    }
    
    /**
     * The main method inserts 100000 elements into a new BTree, closes and reopens it and counts the contained
     * elements.
     * 
     * @param args command line parameters - unused
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        final Function getDescriptor = new Function() {

            public Object invoke(Object object) {
//                return new Interval1D(new Key(((Data) object).name, ((Data) object).id), Key.COMPARATOR);
                return new Interval1D(new SimpleIntegerKey(((Data) object).id), Key.COMPARATOR);
            }
        };
        final Converter objectConverter = new Converter() {

            public void write(DataOutput dataOutput, Object object) throws IOException {
                Data data = (Data) object;

                dataOutput.writeUTF(data.name);
                dataOutput.writeInt(data.id);
                dataOutput.writeDouble(data.income);
            }

            public Object read(DataInput dataInput, Object object) throws IOException {
                return new Data(dataInput.readUTF(), dataInput.readInt(), dataInput.readDouble());
            }
        };
        final int maxNameSize = 2 + 20; // the strings stored in the name-attribute will contain at most 20 characters
//        final int keySize = maxNameSize + IntegerConverter.SIZE;
        final int keySize = IntegerConverter.SIZE;
        final int objectSize = maxNameSize + keySize + DoubleConverter.SIZE;
        final String treeName = xxl.core.util.XXLSystem.getOutPath(new String[] { "output", "applications", "indexStructures" })
                + File.separator + "MyBTree";
        System.out.println("Tree: " + treeName);

//        int nbItems = 10000000;
//        int nbItems = 60000000;
        int nbItems = 500000;

        BTree tree = null;

        Random rand = new Random(System.currentTimeMillis());

        boolean randomWrite = true;

        boolean randomRead = true;

        boolean readNaturally = true;

        long start = System.currentTimeMillis();

        tree = createBTree(treeName, 2048, new LRUBuffer(10), getDescriptor, objectSize, objectConverter, keySize, SimpleIntegerKey.CONVERTER,
                SimpleIntegerKey.COMPARATOR);
        long lastTime = System.currentTimeMillis();
        for (int i = 0; i < nbItems; i++) {

            int v = i;
            if (randomWrite) {
                v = rand.nextInt(nbItems);
            }
            tree.insert(new Data("test" + v, v, 0));

            if (i % 10000 == 0) {
                long currentTimeMillis = System.currentTimeMillis();
                System.out.println("Writing " + i + ", time since last display: "
                        + (int) ((float) (currentTimeMillis - lastTime)) + " ms");
                
                lastTime = currentTimeMillis;
            }

        }
        closeBTree(tree, treeName, SimpleIntegerKey.CONVERTER, SimpleIntegerKey.COMPARATOR);

        long end = System.currentTimeMillis();

        long deltaTime = (end - start);

        int itemsPerSec = (int) ((float) nbItems / (float) deltaTime * 1000f);

        System.out.println(deltaTime + " milliseconds for " + nbItems + " objects to WRITE. " + itemsPerSec + "  items/s ");

        start = System.currentTimeMillis();

        tree = openBTree(treeName, new LRUBuffer(10), getDescriptor, objectConverter, SimpleIntegerKey.CONVERTER, SimpleIntegerKey.COMPARATOR);

        lastTime = System.currentTimeMillis();
        if(readNaturally) {
            
            System.out.println(">> readNaturally");
            
            Cursor cursor = tree.query();
            
//            System.out.println("Results: " + Cursors.count(cursor));
            int i = 0;
            while(cursor.hasNext()) {
                Data data = (Data) cursor.next();

//                 System.out.println(data.toString());
                if (i++ % 10000 == 0) {
                    long currentTimeMillis = System.currentTimeMillis();
                    System.out.println("Reading " + i + ", time since last display: "
                            + (int) ((float) (currentTimeMillis - lastTime)) + " ms");
                    
                    lastTime = currentTimeMillis;
                }

            }
                    
        } else {
            
            System.out.println(">> randomRead :"  + (randomRead ? "yes" : "no"));
            
            for (int i = 0; i < nbItems; i++) {
                int v = i;
                if (randomRead) {
                    v = rand.nextInt(nbItems);
                }
                Cursor cursor = tree.query(new Interval1D(new SimpleIntegerKey(v)));
                Data data = (Data) cursor.next();
                // System.out.println(data.toString());
                if (i % 10000 == 0) {
                    long currentTimeMillis = System.currentTimeMillis();
                    System.out.println("Reading " + i + ", time since last display: "
                            + (int) ((float) (currentTimeMillis - lastTime)) + " ms");
                    
                    lastTime = currentTimeMillis;
                }
            }
        }

        end = System.currentTimeMillis();

        deltaTime = (end - start);

        itemsPerSec = (int) ((float) nbItems / (float) deltaTime * 1000f);

        System.out.println(deltaTime + " milliseconds for " + nbItems + " objects to READ. " + itemsPerSec + "  items/s ");


        closeBTree(tree, treeName, SimpleIntegerKey.CONVERTER, SimpleIntegerKey.COMPARATOR);
    }
}