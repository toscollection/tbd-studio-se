// ============================================================================
package test.prototype.main;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.talend.designer.components.thash.io.hashimpl.PersistentAdvancedLookup;

import routines.TalendString;
import routines.system.ParserUtils;
import routines.system.TDieException;

/**
 * Job: Case2LookupFiles Purpose: <br>
 * Description: <br>
 * 
 * @author amaumont@talend.com
 * @version 0_1
 * @status
 */
public class MainPrototype1 {

    // create and load default properties
    private static java.util.Properties defaultProps = new java.util.Properties();

    // create application properties with default
    private static class ContextProperties extends java.util.Properties {

        public ContextProperties(java.util.Properties properties) {
            super(properties);
        }

        public ContextProperties() {
            super();
        }
    }

    private static ContextProperties context = new ContextProperties();

    private static final String jobName = "Case2LookupFiles";

    private static final String projectName = "JAVA_PROJECT_5";

    public static Integer errorCode = null;

    private static String currentComponent = "";

    private static final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();

    private static final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();

    private static final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();

    private static final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();

    public static final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

    private class TalendException extends Exception {

        private Exception e = null;

        private MainPrototype1 c = null;

        private TalendException(MainPrototype1 c, Exception e) {
            this.e = e;
            this.c = c;
        }

        @Override
        public void printStackTrace() {
            if (!(e instanceof TalendException || e instanceof TDieException)) {
                globalMap.put(currentComponent + "_ERROR_MESSAGE", e.getMessage());
                System.err.println("Exception in component " + currentComponent);
            }
            if (!(e instanceof TDieException)) {
                e.printStackTrace();
            }
            if (!(e instanceof TalendException)) {
                try {
                    for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
                        if (m.getName().compareTo(currentComponent + "_error") == 0) {
                            m.invoke(c, new Object[] { e });
                            break;
                        }
                    }

                } catch (java.lang.SecurityException e) {
                    this.e.printStackTrace();
                } catch (java.lang.IllegalArgumentException e) {
                    this.e.printStackTrace();
                } catch (java.lang.IllegalAccessException e) {
                    this.e.printStackTrace();
                } catch (java.lang.reflect.InvocationTargetException e) {
                    this.e.printStackTrace();
                }
            }
        }
    }

    public void tFileInputDelimited_1_error(Exception exception) throws TalendException {
        end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tMap_1_error(Exception exception) throws TalendException {
        end_Hash.put("tMap_1", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tFileOutputDelimited_1_error(Exception exception) throws TalendException {
        end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tFileInputDelimited_2_error(Exception exception) throws TalendException {
        end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tFileInputDelimited_3_error(Exception exception) throws TalendException {
        end_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tFileInputDelimited_4_error(Exception exception) throws TalendException {
        end_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tAdvancedHash_LF1_error(Exception exception) throws TalendException {
        end_Hash.put("tAdvancedHash_LF1", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tAdvancedHash_LF2_error(Exception exception) throws TalendException {
        end_Hash.put("tAdvancedHash_LF2", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tAdvancedHash_LF3_error(Exception exception) throws TalendException {
        end_Hash.put("tAdvancedHash_LF3", System.currentTimeMillis());
        tFileInputDelimited_1_onSubJobError(exception);
    }

    public void tFileInputDelimited_1_onSubJobError(Exception exception) throws TalendException {
    }

    private class outStruct {

        Integer K1;

        Integer K2;

        String V;

        Integer K1_1;

        Integer K2_1;

        String V_1;

        Integer K1_2;

        Integer K2_2;

        String V_2;

        Integer K1_3;

        Integer K2_3;

        String V_3;

    }

    private class MStruct {

        Integer K1;

        Integer K2;

        String V;

    }

    private class after_tFileInputDelimited_1Struct {

        Integer K1;

        Integer K2;

        String V;

    }

    public void tFileInputDelimited_1Process() throws TalendException {
        try {
            tFileInputDelimited_2Process();
            tFileInputDelimited_3Process();
            tFileInputDelimited_4Process();

            MStruct M = new MStruct();
            outStruct out = new outStruct();

            /**
             * [tFileOutputDelimited_1 begin ] start
             */

            ok_Hash.put("tFileOutputDelimited_1", false);
            start_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());
            currentComponent = "tFileOutputDelimited_1";

            String fileName_tFileOutputDelimited_1 = (new java.io.File(
                    "/home/amaumont/data/dev/projets/Talend/TUJV/files/tMap/test_persistent_hash_00/out/out.csv"))
                    .getAbsolutePath().replace("\\", "/");
            String fullName_tFileOutputDelimited_1 = null;
            String extension_tFileOutputDelimited_1 = null;
            String directory_tFileOutputDelimited_1 = null;
            if ((fileName_tFileOutputDelimited_1.indexOf("/") != -1)) {
                if (fileName_tFileOutputDelimited_1.lastIndexOf(".") < fileName_tFileOutputDelimited_1.lastIndexOf("/")) {
                    fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
                    extension_tFileOutputDelimited_1 = "";
                } else {
                    fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
                            fileName_tFileOutputDelimited_1.lastIndexOf("."));
                    extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(fileName_tFileOutputDelimited_1
                            .lastIndexOf("."));
                }
                directory_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0, fileName_tFileOutputDelimited_1
                        .lastIndexOf("/"));
            } else {
                if (fileName_tFileOutputDelimited_1.lastIndexOf(".") != -1) {
                    fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(0,
                            fileName_tFileOutputDelimited_1.lastIndexOf("."));
                    extension_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1.substring(fileName_tFileOutputDelimited_1
                            .lastIndexOf("."));
                } else {
                    fullName_tFileOutputDelimited_1 = fileName_tFileOutputDelimited_1;
                    extension_tFileOutputDelimited_1 = "";
                }
                directory_tFileOutputDelimited_1 = "";
            }

            int nb_line_tFileOutputDelimited_1 = 0;
            int splitEvery_tFileOutputDelimited_1 = 1000;
            int splitedFileNo_tFileOutputDelimited_1 = 0;
            int currentRow_tFileOutputDelimited_1 = 0;

            final String OUT_DELIM_tFileOutputDelimited_1 = /**
                                                             * Start field tFileOutputDelimited_1:FIELDSEPARATOR
                                                             */
            ";"/** End field tFileOutputDelimited_1:FIELDSEPARATOR */
            ;

            final String OUT_DELIM_ROWSEP_tFileOutputDelimited_1 = /**
                                                                     * Start field tFileOutputDelimited_1:ROWSEPARATOR
                                                                     */
            "\n"/** End field tFileOutputDelimited_1:ROWSEPARATOR */
            ;

            // create directory only if not exists
            if (directory_tFileOutputDelimited_1 != null && directory_tFileOutputDelimited_1.trim().length() != 0) {
                java.io.File dir_tFileOutputDelimited_1 = new java.io.File(directory_tFileOutputDelimited_1);
                if (!dir_tFileOutputDelimited_1.exists()) {
                    dir_tFileOutputDelimited_1.mkdirs();
                }
            }

            java.io.BufferedWriter outtFileOutputDelimited_1 = new java.io.BufferedWriter(new java.io.OutputStreamWriter(
                    new java.io.FileOutputStream(fileName_tFileOutputDelimited_1, false), "ISO-8859-15"));
            java.io.File filetFileOutputDelimited_1 = new java.io.File(fileName_tFileOutputDelimited_1);

            /**
             * [tFileOutputDelimited_1 begin ] stop
             */

            /**
             * [tMap_1 begin ] start
             */

            ok_Hash.put("tMap_1", false);
            start_Hash.put("tMap_1", System.currentTimeMillis());
            currentComponent = "tMap_1";

            // ###############################
            // # Lookup's keys initialization

            PersistentAdvancedLookup<LF1Struct> tHash_Lookup_LF1 = (PersistentAdvancedLookup<LF1Struct>) globalMap
                    .get("tHash_Lookup_LF1");
            LF1Struct LF1HashKey = new LF1Struct();
            LF1Struct LF1Default = new LF1Struct();

            PersistentAdvancedLookup<LF2Struct> tHash_Lookup_LF2 = (PersistentAdvancedLookup<LF2Struct>) globalMap
                    .get("tHash_Lookup_LF2");
            LF2Struct LF2HashKey = new LF2Struct();
            LF2Struct LF2Default = new LF2Struct();

            PersistentAdvancedLookup<LF3Struct> tHash_Lookup_LF3 = (PersistentAdvancedLookup<LF3Struct>) globalMap
                    .get("tHash_Lookup_LF3");
            LF3Struct LF3HashKey = new LF3Struct();
            LF3Struct LF3Default = new LF3Struct();
            // ###############################

            // ###############################
            // # Vars initialization
            // ###############################

            // ###############################
            // # Outputs initialization
            outStruct out_tmp = new outStruct();
            // ###############################

            /** Start ADDED */

            org.talend.designer.components.thash.io.hashimpl.FlowSorterIterator fsi_M = new org.talend.designer.components.thash.io.hashimpl.FlowSorterIterator();
            fsi_M.initPut(null);

            class SortableRow_Main_M implements Comparable<SortableRow_Main_M> {

                int expKey_LF1__K1;

                int expKey_LF1__K2;

                int M__K1;

                int M__K2;

                String M__V;

                public byte[] toData() {

                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeInt(expKey_LF1__K1);
                        dos.writeInt(expKey_LF1__K2);

                        dos.writeInt(M__K1);
                        dos.writeInt(M__K2);
                        dos.writeUTF(M__V);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bao.toByteArray();

                }

                public void loadData(byte[] data) {

                    DataInputStream dis = null;

                    try {
                        this.expKey_LF1__K1 = dis.readInt();
                        this.expKey_LF1__K2 = dis.readInt();
                        
                        this.M__K1 = dis.readInt();
                        this.M__K2 = dis.readInt();
                        this.M__V = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                public void fillFrom(MStruct m, int expKey_LF1__K1, int expKey_LF1__K2) {

                    this.M__K1 = m.K1;
                    this.M__K2 = m.K2;
                    this.M__V = m.V;

                    this.expKey_LF1__K1 = expKey_LF1__K1;
                    this.expKey_LF1__K2 = expKey_LF1__K2;

                }

                public void copyDataTo(MStruct M) {

                    M.K1 = M__K1;
                    M.K2 = M__K2;
                    M.V = M__V;

                }

                public int compareTo(SortableRow_Main_M other) {

                    int returnValue = 0;
                    returnValue = checkNullsAndCompare(this.expKey_LF1__K1, other.expKey_LF1__K1);
                    if (returnValue != 0) {
                        return returnValue;
                    }
                    returnValue = checkNullsAndCompare(this.expKey_LF1__K2, other.expKey_LF1__K2);
                    if (returnValue != 0) {
                        return returnValue;
                    }
                    return returnValue;
                }

            }

            class SortableRow_Lookup_LF1 implements Comparable<SortableRow_Lookup_LF1> {

                int LF1__K1;

                int LF1__K2;

                String LF1__V;

                public byte[] toKeysData(int valuesDataSize) {

                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeInt(LF1__K1);
                        dos.writeInt(LF1__K2);

                        dos.writeInt(valuesDataSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bao.toByteArray();
                }

                public int loadKeysData(byte[] keysData) {

                    DataInputStream dis = null;

                    int valuesDataSize = 0;
                    try {
                        this.LF1__K1 = dis.readInt();
                        this.LF1__K2 = dis.readInt();

                        valuesDataSize = dis.readInt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return valuesDataSize;
                }

                public byte[] toValuesData() {
                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeUTF(LF1__V);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bao.toByteArray();

                }

                public void loadValuesData(byte[] valuesData) {
                    DataInputStream dis = null;

                    try {
                        this.LF1__V = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                public int compareTo(SortableRow_Lookup_LF1 other) {

                    int returnValue = 0;
                    returnValue = checkNullsAndCompare(this.LF1__K1, other.LF1__K1);
                    if (returnValue != 0) {
                        return returnValue;
                    }
                    returnValue = checkNullsAndCompare(this.LF1__K2, other.LF1__K2);
                    return returnValue;
                }

            }

            class SortableRow_Join_M__LF1 implements Comparable<SortableRow_Join_M__LF1> {

                int expKey_LF2__K1;

                int expKey_LF2__K2;

                int M__K1;

                int M__K2;

                String M__V;

                int LF1__K1;

                int LF1__K2;

                String LF1__V;

                public byte[] toKeysData(int valuesDataSize) {

                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeInt(expKey_LF2__K1);
                        dos.writeInt(expKey_LF2__K1);

                        dos.writeInt(valuesDataSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bao.toByteArray();

                }

                public byte[] toValuesData() {
                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeInt(M__K1);
                        dos.writeInt(M__K2);
                        dos.writeUTF(M__V);

                        dos.writeInt(LF1__K1);
                        dos.writeInt(LF1__K2);
                        dos.writeUTF(LF1__V);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bao.toByteArray();

                }

                public void fillFrom(MStruct M, LF1Struct LF1, int expKey_LF2__K1, int expKey_LF2__K2) {
                    this.M__K1 = M.K1;
                    this.M__K2 = M.K2;
                    this.M__V = M.V;

                    this.LF1__K1 = LF1.K1;
                    this.LF1__K2 = LF1.K2;
                    this.LF1__V = LF1.V;

                    this.expKey_LF2__K1 = expKey_LF2__K1;
                    this.expKey_LF2__K1 = expKey_LF2__K1;
                }

                public void copyDataTo(MStruct M, LF1Struct LF1) {

                    M.K1 = M__K1;
                    M.K2 = M__K2;
                    M.V = M__V;

                    LF1.K1 = LF1__K1;
                    LF1.K2 = LF1__K2;
                    LF1.V = LF1__V;

                }

                public int compareTo(SortableRow_Join_M__LF1 other) {

                    int returnValue = 0;
                    returnValue = checkNullsAndCompare(this.expKey_LF2__K1, other.expKey_LF2__K1);
                    if (returnValue != 0) {
                        return returnValue;
                    }
                    returnValue = checkNullsAndCompare(this.expKey_LF2__K2, other.expKey_LF2__K2);

                    return returnValue;
                }

            }

            class SortableRow_Lookup_LF2 implements Comparable<SortableRow_Lookup_LF2> {

                int LF2__K1;

                int LF2__K2;

                String LF2__V;

                public byte[] toKeysData(int valuesDataSize) {

                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeInt(LF2__K1);
                        dos.writeInt(LF2__K2);

                        dos.writeInt(valuesDataSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bao.toByteArray();
                }

                public int loadKeysData(byte[] keysData) {

                    DataInputStream dis = null;

                    int valuesDataSize = 0;
                    try {
                        this.LF2__K1 = dis.readInt();
                        this.LF2__K2 = dis.readInt();

                        valuesDataSize = dis.readInt();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return valuesDataSize;
                }

                public byte[] toValuesData() {
                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeUTF(LF2__V);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    return bao.toByteArray();

                }

                public void loadValuesData(byte[] valuesData) {
                    DataInputStream dis = null;

                    try {
                        this.LF2__V = dis.readUTF();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                public int compareTo(SortableRow_Lookup_LF2 other) {

                    int returnValue = 0;
                    returnValue = checkNullsAndCompare(this.LF2__K1, other.LF2__K1);
                    if (returnValue != 0) {
                        return returnValue;
                    }
                    returnValue = checkNullsAndCompare(this.LF2__K2, other.LF2__K2);
                    return returnValue;
                }

            }

            class SortableRow_Join_M__LF1__LF2 implements Comparable<SortableRow_Join_M__LF1__LF2> {

                int expKey_LF3__K1;

                int expKey_LF3__K2;

                int M__K1;

                int M__K2;

                String M__V;

                int LF1__K1;

                int LF1__K2;

                String LF1__V;

                int LF2__K1;

                int LF2__K2;

                String LF2__V;

                public byte[] toKeysData(int valuesDataSize) {

                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;
                    try {
                        dos.writeInt(valuesDataSize);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bao.toByteArray();

                }

                public byte[] toValuesData() {
                    ByteArrayOutputStream bao = null;
                    DataOutputStream dos = null;

                    try {
                        dos.writeInt(M__K1);
                        dos.writeInt(M__K2);
                        dos.writeUTF(M__V);

                        dos.writeInt(LF1__K1);
                        dos.writeInt(LF1__K2);
                        dos.writeUTF(LF1__V);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return bao.toByteArray();
                }

                public void fillFrom(MStruct M, LF1Struct LF1, LF2Struct LF2, int expKey_LF3__K1, int expKey_LF3__K2) {
                    this.M__K1 = M.K1;
                    this.M__K2 = M.K2;
                    this.M__V = M.V;

                    this.LF1__K1 = LF1.K1;
                    this.LF1__K2 = LF1.K2;
                    this.LF1__V = LF1.V;

                    this.LF2__K1 = LF2.K1;
                    this.LF2__K2 = LF2.K2;
                    this.LF2__V = LF2.V;

                    this.expKey_LF3__K1 = expKey_LF3__K1;
                    this.expKey_LF3__K2 = expKey_LF3__K2;
                }

                public void copyDataTo(MStruct M, LF1Struct LF1, LF2Struct LF2) {

                    M.K1 = M__K1;
                    M.K2 = M__K2;
                    M.V = M__V;

                    LF1.K1 = LF1__K1;
                    LF1.K2 = LF1__K2;
                    LF1.V = LF1__V;

                    LF2.K1 = LF2__K1;
                    LF2.K2 = LF2__K2;
                    LF2.V = LF2__V;

                }

                public int compareTo(SortableRow_Join_M__LF1__LF2 other) {

                    int returnValue = 0;
                    returnValue = checkNullsAndCompare(this.expKey_LF3__K1, other.expKey_LF3__K1);
                    if (returnValue != 0) {
                        return returnValue;
                    }
                    returnValue = checkNullsAndCompare(this.expKey_LF3__K2, other.expKey_LF3__K2);

                    return returnValue;
                }

            }

            /** End ADDED */

            /**
             * [tMap_1 begin ] stop
             */

            /**
             * [tFileInputDelimited_1 begin ] start
             */

            ok_Hash.put("tFileInputDelimited_1", false);
            start_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());
            currentComponent = "tFileInputDelimited_1";

            org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_1 = new org.talend.fileprocess.FileInputDelimited(
                    "/home/amaumont/data/dev/projets/Talend/TUJV/files/tMap/test_persistent_hash_00/in/main.csv", "ISO-8859-15",
                    ";", "\n", true, 0, 0, -1, -1);
            while (fid_tFileInputDelimited_1.nextRecord()) {
                M = null;
                boolean whetherReject_tFileInputDelimited_1 = false;
                M = new MStruct();
                try {
                    String temp_tFileInputDelimited_1 = "";
                    temp_tFileInputDelimited_1 = fid_tFileInputDelimited_1.get(0);
                    if (temp_tFileInputDelimited_1.length() > 0) {
                        M.K1 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_1);
                    } else {
                        M.K1 = null;
                    }
                    temp_tFileInputDelimited_1 = fid_tFileInputDelimited_1.get(1);
                    if (temp_tFileInputDelimited_1.length() > 0) {
                        M.K2 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_1);
                    } else {
                        M.K2 = null;
                    }
                    M.V = fid_tFileInputDelimited_1.get(2);

                } catch (Exception e) {
                    whetherReject_tFileInputDelimited_1 = true;
                    System.err.println(e.getMessage());
                    M = null;
                }

                /**
                 * [tFileInputDelimited_1 begin ] stop
                 */
                /**
                 * [tFileInputDelimited_1 main ] start
                 */

                currentComponent = "tFileInputDelimited_1";

                /**
                 * [tFileInputDelimited_1 main ] stop
                 */
                // Start of branch "M"
                if (M != null) {

                    /**
                     * [tMap_1 main ] start
                     */

                    currentComponent = "tMap_1";

                    /** Start ADDED */

                    int expKey_LF1__K1 = M.K1 - M.K2;
                    int expKey_LF1__K2 = M.K1 + M.K2;

                    SortableRow_Main_M rscM = new SortableRow_Main_M();
                    
                    rscM.fillFrom(M, expKey_LF1__K1, expKey_LF1__K2);

                    fsi_M.put(null, rscM);

                }// End of branch "main_9"

            } // end temporarly main loop

            fsi_M.endPut();

            tHash_Lookup_LF1.initGet();
            tHash_Lookup_LF2.initGet();
            tHash_Lookup_LF3.initGet();

            SortableRow_Main_M rscM;
            SortableRow_Join_M__LF1 rsc_Join_M__LF1;

            boolean rejectedInnerJoin_tMap_1 = false;

            org.talend.designer.components.thash.io.IPersistentJoiner<V> fsi_Join_M__LF1 = null;//new org.talend.designer.components.thash.io.hashimpl.FlowSorterIterator();
            org.talend.designer.components.thash.io.IPersistentJoiner<V> fsi_Join_M__LF1__LF2 = null;//new org.talend.designer.components.thash.io.hashimpl.FlowSorterIterator();

            while (fsi_M.hasNext()) { // loop M

                /** End ADDED */

                /** Start REMOVED */
                // Start of branch "<%= tableName%>"
                // if (M != null)
                // {
                /** End REMOVED */

                /** Start ADDED */

                rscM = (SortableRow_Main_M) fsi_M.next();

                rscM.copyDataTo(M);

                /** End ADDED */

                /** Start MODIFIED */
                rejectedInnerJoin_tMap_1 = false;
                /** End MODIFIED */

                // ###############################
                // # Input tables (lookups)
                // ////////////////////////////////////////////////////////
                // //////////////////////// LF1 (ALL MATCHES) //////////
                // ////////////////////////////////////////////////////////
                boolean forceLoopLF1 = false;
                int sizeResultsFromLookup_LF1 = -1;

                java.util.List<LF1Struct> listFromLookup_LF1 = null;

                LF1Struct LF1ObjectFromLookup = null;

                if (!rejectedInnerJoin_tMap_1) { // G 20

                    /** Start MODIFIED */
                    LF1HashKey.K1 = rscM.expKey_LF1__K1;
                    LF1HashKey.K2 = rscM.expKey_LF1__K2;
                    /** End MODIFIED */

                    LF1HashKey.hashCodeDirty = true;
                    tHash_Lookup_LF1.get(LF1HashKey);

                    if (tHash_Lookup_LF1.hasResult()) { // G 90

                        if (tHash_Lookup_LF1.resultIsObject()) { // G 49

                            LF1ObjectFromLookup = (LF1Struct) tHash_Lookup_LF1.getResultObject();

                            sizeResultsFromLookup_LF1 = LF1ObjectFromLookup != null ? 1 : -1;

                        } else { // G 49

                            listFromLookup_LF1 = (java.util.List<LF1Struct>) tHash_Lookup_LF1.getResultList();
                            sizeResultsFromLookup_LF1 = listFromLookup_LF1 != null ? listFromLookup_LF1.size() : -1;

                        }

                    } // G 90
                    else { // G 91

                        forceLoopLF1 = true;

                    } // G 91

                } // G 20

                else { // G 21
                    forceLoopLF1 = true;
                } // G 21

                for (int iLF1 = 0; sizeResultsFromLookup_LF1 != -1 && iLF1 < sizeResultsFromLookup_LF1 || forceLoopLF1; iLF1++) { // for
                    // loop
                    // LF1

                    LF1Struct fromLookup_LF1 = null;
                    LF1Struct LF1 = LF1Default;

                    if (!forceLoopLF1) { // G 46

                        if (LF1ObjectFromLookup != null) {

                            fromLookup_LF1 = LF1ObjectFromLookup;
                            LF1 = fromLookup_LF1;
                        }

                        else if (listFromLookup_LF1 != null) {
                            fromLookup_LF1 = listFromLookup_LF1.get(iLF1);
                            LF1 = fromLookup_LF1;
                        }

                        if (!rejectedInnerJoin_tMap_1 &&

                        !(

                        M.K1 > 1

                        )

                        ) { // G 16

                            fromLookup_LF1 = null;
                            LF1 = LF1Default;

                        } // G 16

                    } // G 46

                    forceLoopLF1 = false;

                    /** Start ADDED */

                    int expKey_LF2__K1 = LF1.K1 != null && LF1.K2 != null ? LF1.K1 - LF1.K2 : null;
                    int expKey_LF2__K2 = LF1.K1 != null && LF1.K2 != null ? LF1.K1 + LF1.K2 : null;

                    rsc_Join_M__LF1 = new SortableRow_Join_M__LF1();

                    rsc_Join_M__LF1.fillFrom(M, LF1, expKey_LF2__K1, expKey_LF2__K2);
                    
                    // Sort and store in KeyFile(s) and DataFile(s)
                    fsi_Join_M__LF1.put(null, rsc_Join_M__LF1);

                    // WARNING: THIS BRACKET MUST APPEAR ONLY WITH "ALL MATCHES" or "ALL ROWS" mode
                } // for loop LF1

            } // loop on each M row

            M = new MStruct();
            LF1Struct LF1 = new LF1Struct();

            SortableRow_Join_M__LF1__LF2 rsc_Join_M__LF1__LF2;

            while (fsi_Join_M__LF1.hasNext()) { // loop Join_M__LF1

                rsc_Join_M__LF1 = (SortableRow_Join_M__LF1) fsi_Join_M__LF1.next();

                rsc_Join_M__LF1.copyDataTo(M, LF1);

                /** End ADDED */

                // ////////////////////////////////////////////////////////
                // //////////////////////// LF2 ///////////////////////
                // ////////////////////////////////////////////////////////
                LF2Struct LF2ObjectFromLookup = null;

                if (!rejectedInnerJoin_tMap_1) { // G 20

                    /** Start MODIFIED */
                    LF2HashKey.K1 = rsc_Join_M__LF1.expKey_LF2__K1;
                    LF2HashKey.K2 = rsc_Join_M__LF1.expKey_LF2__K2;
                    /** End MODIFIED */

                    LF2HashKey.hashCodeDirty = true;
                    tHash_Lookup_LF2.get(LF2HashKey);

                    if (tHash_Lookup_LF2.hasResult()) { // G 90

                        LF2ObjectFromLookup = (LF2Struct) tHash_Lookup_LF2.getResultObject();

                    } // G 90
                    else { // G 91

                    } // G 91

                } // G 20

                LF2Struct fromLookup_LF2 = null;
                LF2Struct LF2 = LF2Default;

                if (LF2ObjectFromLookup != null) {

                    fromLookup_LF2 = LF2ObjectFromLookup;
                    LF2 = fromLookup_LF2;
                }

                if (!rejectedInnerJoin_tMap_1 &&

                !(

                M.K2 > 1

                )

                ) { // G 16

                    fromLookup_LF2 = null;
                    LF2 = LF2Default;

                } // G 16

                /** Start ADDED */

                int expKey_LF3__K1 = LF1.K1 != null && LF2.K1 != null ? LF1.K1 - LF2.K1 : null;
                int expKey_LF3__K2 = LF1.K2 != null && LF2.K2 != null ? LF1.K2 + LF2.K2 : null;

                rsc_Join_M__LF1__LF2 = new SortableRow_Join_M__LF1__LF2();
                
                rsc_Join_M__LF1__LF2.fillFrom(M, LF1, LF2, expKey_LF3__K1, expKey_LF3__K2);

                // Sort and store in KeyFile(s) and DataFile(s)
                fsi_Join_M__LF1__LF2.put(null, rsc_Join_M__LF1__LF2);

            } // loop on each Join_M__LF1 row

            { // dummy bracket to simulate Input Row Component connected at Main row on tMap

                LF2Struct LF2 = new LF2Struct();

                while (fsi_Join_M__LF1__LF2.hasNext()) { // loop on each Join_M__LF1__LF2 row

                    rsc_Join_M__LF1__LF2 = (SortableRow_Join_M__LF1__LF2) fsi_Join_M__LF1__LF2.next();
                    
                    rsc_Join_M__LF1__LF2.copyDataTo(M, LF1, LF2);

                    /** End ADDED */

                    // ////////////////////////////////////////////////////////
                    // //////////////////////// LF3 ///////////////////////
                    // ////////////////////////////////////////////////////////
                    LF3Struct LF3ObjectFromLookup = null;

                    if (!rejectedInnerJoin_tMap_1) { // G 20

                        /** Start MODIFIED */
                        LF3HashKey.K1 = rsc_Join_M__LF1__LF2.expKey_LF3__K1;
                        LF3HashKey.K2 = rsc_Join_M__LF1__LF2.expKey_LF3__K2;
                        /** End MODIFIED */

                        LF3HashKey.hashCodeDirty = true;
                        tHash_Lookup_LF3.get(LF3HashKey);

                        if (tHash_Lookup_LF3.hasResult()) { // G 90

                            LF3ObjectFromLookup = (LF3Struct) tHash_Lookup_LF3.getResultObject();

                        } // G 90
                        else { // G 91

                        } // G 91

                    } // G 20

                    LF3Struct fromLookup_LF3 = null;
                    LF3Struct LF3 = LF3Default;

                    if (LF3ObjectFromLookup != null) {

                        fromLookup_LF3 = LF3ObjectFromLookup;
                        LF3 = fromLookup_LF3;
                    }

                    // ###############################

                    { // start of Var scope

                        // ###############################
                        // # Vars tables
                        // ###############################

                        // ###############################
                        // # Output tables
                        out = null;

                        // # Output table : 'out'
                        out_tmp.K1 = M.K1;
                        out_tmp.K2 = M.K2;
                        out_tmp.V = M.V;
                        out_tmp.K1_1 = LF1.K1;
                        out_tmp.K2_1 = LF1.K2;
                        out_tmp.V_1 = LF1.V;
                        out_tmp.K1_2 = LF2.K1;
                        out_tmp.K2_2 = LF2.K2;
                        out_tmp.V_2 = LF2.V;
                        out_tmp.K1_3 = LF3.K1;
                        out_tmp.K2_3 = LF3.K2;
                        out_tmp.V_3 = LF3.V;
                        out = out_tmp;
                        // ###############################

                    } // end of Var scope

                    rejectedInnerJoin_tMap_1 = false;

                    /**
                     * [tMap_1 main ] stop
                     */
                    // Start of branch "out"
                    if (out != null) {

                        /**
                         * [tFileOutputDelimited_1 main ] start
                         */

                        currentComponent = "tFileOutputDelimited_1";

                        StringBuilder sb_tFileOutputDelimited_1 = new StringBuilder();

                        if (out.K1 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K1

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K2 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K2

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.V != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.V

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K1_1 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K1_1

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K2_1 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K2_1

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.V_1 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.V_1

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K1_2 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K1_2

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K2_2 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K2_2

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.V_2 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.V_2

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K1_3 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K1_3

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.K2_3 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.K2_3

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_tFileOutputDelimited_1);

                        if (out.V_3 != null) {

                            sb_tFileOutputDelimited_1.append(

                            out.V_3

                            );

                        }

                        sb_tFileOutputDelimited_1.append(OUT_DELIM_ROWSEP_tFileOutputDelimited_1);

                        outtFileOutputDelimited_1.write(sb_tFileOutputDelimited_1.toString());

                        nb_line_tFileOutputDelimited_1++;

                        /**
                         * [tFileOutputDelimited_1 main ] stop
                         */

                    } // End of branch "out"

                    /** Start REMOVED */
                    // } // close loop of lookup 'LF1'
                    /** End REMOVED */

                } // End of branch "M"

                /**
                 * [tFileInputDelimited_1 end ] start
                 */

                currentComponent = "tFileInputDelimited_1";

            }
            fid_tFileInputDelimited_1.close();
            globalMap.put("tFileInputDelimited_1_NB_LINE", fid_tFileInputDelimited_1.getRowNumber());

            ok_Hash.put("tFileInputDelimited_1", true);
            end_Hash.put("tFileInputDelimited_1", System.currentTimeMillis());

            /**
             * [tFileInputDelimited_1 end ] stop
             */
            /**
             * [tMap_1 end ] start
             */

            currentComponent = "tMap_1";

            // ###############################
            // # Lookup hashes releasing
            globalMap.remove("tHash_LF1");
            // ###############################

            globalMap.remove("tHash_LF2");
            // ###############################

            globalMap.remove("tHash_LF3");
            // ###############################

            ok_Hash.put("tMap_1", true);
            end_Hash.put("tMap_1", System.currentTimeMillis());

            /**
             * [tMap_1 end ] stop
             */
            /**
             * [tFileOutputDelimited_1 end ] start
             */

            currentComponent = "tFileOutputDelimited_1";

            outtFileOutputDelimited_1.close();
            globalMap.put("tFileOutputDelimited_1_NB_LINE", nb_line_tFileOutputDelimited_1);

            ok_Hash.put("tFileOutputDelimited_1", true);
            end_Hash.put("tFileOutputDelimited_1", System.currentTimeMillis());

            /**
             * [tFileOutputDelimited_1 end ] stop
             */

        } catch (Exception e) {

            throw new TalendException(this, e);
        }

        finally {
            // free memory for "tMap_1"
            globalMap.put("tHash_Lookup_LF1", null);

            // free memory for "tMap_1"
            globalMap.put("tHash_Lookup_LF2", null);

            // free memory for "tMap_1"
            globalMap.put("tHash_Lookup_LF3", null);
            System.gc();
        }

    }

    private class LF1Struct implements Comparable<LF1Struct> {

        private static final int DEFAULT_HASHCODE = 1;

        private static final int PRIME = 31;

        private int hashCode = DEFAULT_HASHCODE;

        public boolean hashCodeDirty = true;

        Integer K1;

        Integer K2;

        String V;

        @Override
        public int hashCode() {
            if (this.hashCodeDirty) {
                final int prime = PRIME;
                int result = DEFAULT_HASHCODE;

                result = prime * result + ((this.K1 == null) ? 0 : this.K1.hashCode());

                result = prime * result + ((this.K2 == null) ? 0 : this.K2.hashCode());

                this.hashCode = result;
                this.hashCodeDirty = false;
            }
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final LF1Struct other = (LF1Struct) obj;

            if (this.K1 == null) {
                if (other.K1 != null)
                    return false;
            } else if (!this.K1.equals(other.K1))
                return false;

            if (this.K2 == null) {
                if (other.K2 != null)
                    return false;
            } else if (!this.K2.equals(other.K2))
                return false;

            return true;
        }

        /** Start ADDED */

        public int compareTo(LF1Struct other) {

            int returnValue = 0;
            returnValue = checkNullsAndCompare(this.K1, other.K1);
            if (returnValue != 0) {
                return returnValue;
            }
            returnValue = checkNullsAndCompare(this.K2, other.K2);

            return returnValue;
        }

        /** End ADDED */

    }

    public void tFileInputDelimited_2Process() throws TalendException {
        try {

            LF1Struct LF1 = new LF1Struct();

            /**
             * [tAdvancedHash_LF1 begin ] start
             */

            ok_Hash.put("tAdvancedHash_LF1", false);
            start_Hash.put("tAdvancedHash_LF1", System.currentTimeMillis());
            currentComponent = "tAdvancedHash_LF1";

            PersistentAdvancedLookup.MATCHING_MODE matchingModeEnum_LF1 = PersistentAdvancedLookup.MATCHING_MODE.ALL_MATCHES;
            PersistentAdvancedLookup<LF1Struct> tHash_Lookup_LF1 = PersistentAdvancedLookup
                    .<LF1Struct> getLookup(matchingModeEnum_LF1);
            globalMap.put("tHash_Lookup_LF1", tHash_Lookup_LF1);

            /**
             * [tAdvancedHash_LF1 begin ] stop
             */

            /**
             * [tFileInputDelimited_2 begin ] start
             */

            ok_Hash.put("tFileInputDelimited_2", false);
            start_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());
            currentComponent = "tFileInputDelimited_2";

            org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_2 = new org.talend.fileprocess.FileInputDelimited(
                    "/home/amaumont/data/dev/projets/Talend/TUJV/files/tMap/test_persistent_hash_00/in/lookup_multi_rows1.csv",
                    "ISO-8859-15", ";", "\n", true, 0, 0, -1, -1);
            while (fid_tFileInputDelimited_2.nextRecord()) {
                LF1 = null;
                LF1 = null;
                boolean whetherReject_tFileInputDelimited_2 = false;
                LF1 = new LF1Struct();
                try {
                    String temp_tFileInputDelimited_2 = "";
                    temp_tFileInputDelimited_2 = fid_tFileInputDelimited_2.get(0);
                    if (temp_tFileInputDelimited_2.length() > 0) {
                        LF1.K1 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_2);
                    } else {
                        LF1.K1 = null;
                    }
                    temp_tFileInputDelimited_2 = fid_tFileInputDelimited_2.get(1);
                    if (temp_tFileInputDelimited_2.length() > 0) {
                        LF1.K2 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_2);
                    } else {
                        LF1.K2 = null;
                    }
                    LF1.V = fid_tFileInputDelimited_2.get(2);

                } catch (Exception e) {
                    whetherReject_tFileInputDelimited_2 = true;
                    System.err.println(e.getMessage());
                    LF1 = null;
                }

                /**
                 * [tFileInputDelimited_2 begin ] stop
                 */
                /**
                 * [tFileInputDelimited_2 main ] start
                 */

                currentComponent = "tFileInputDelimited_2";

                /**
                 * [tFileInputDelimited_2 main ] stop
                 */
                // Start of branch "LF1"
                if (LF1 != null) {

                    /**
                     * [tAdvancedHash_LF1 main ] start
                     */

                    currentComponent = "tAdvancedHash_LF1";

                    LF1Struct LF1_HashRow = new LF1Struct();

                    LF1_HashRow.K1 = LF1.K1;
                    LF1_HashRow.K2 = LF1.K2;
                    LF1_HashRow.V = LF1.V;
                    tHash_Lookup_LF1.put(LF1_HashRow);

                    /**
                     * [tAdvancedHash_LF1 main ] stop
                     */

                } // End of branch "LF1"

                /**
                 * [tFileInputDelimited_2 end ] start
                 */

                currentComponent = "tFileInputDelimited_2";

            }
            fid_tFileInputDelimited_2.close();
            globalMap.put("tFileInputDelimited_2_NB_LINE", fid_tFileInputDelimited_2.getRowNumber());

            ok_Hash.put("tFileInputDelimited_2", true);
            end_Hash.put("tFileInputDelimited_2", System.currentTimeMillis());

            /**
             * [tFileInputDelimited_2 end ] stop
             */
            /**
             * [tAdvancedHash_LF1 end ] start
             */

            currentComponent = "tAdvancedHash_LF1";

            ok_Hash.put("tAdvancedHash_LF1", true);
            end_Hash.put("tAdvancedHash_LF1", System.currentTimeMillis());

            /**
             * [tAdvancedHash_LF1 end ] stop
             */

        } catch (Exception e) {

            throw new TalendException(this, e);
        }

    }

    private class LF2Struct implements Comparable<LF2Struct> {

        private static final int DEFAULT_HASHCODE = 1;

        private static final int PRIME = 31;

        private int hashCode = DEFAULT_HASHCODE;

        public boolean hashCodeDirty = true;

        Integer K1;

        Integer K2;

        String V;

        @Override
        public int hashCode() {
            if (this.hashCodeDirty) {
                final int prime = PRIME;
                int result = DEFAULT_HASHCODE;

                result = prime * result + ((this.K1 == null) ? 0 : this.K1.hashCode());

                result = prime * result + ((this.K2 == null) ? 0 : this.K2.hashCode());

                this.hashCode = result;
                this.hashCodeDirty = false;
            }
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final LF2Struct other = (LF2Struct) obj;

            if (this.K1 == null) {
                if (other.K1 != null)
                    return false;
            } else if (!this.K1.equals(other.K1))
                return false;

            if (this.K2 == null) {
                if (other.K2 != null)
                    return false;
            } else if (!this.K2.equals(other.K2))
                return false;

            return true;
        }

        /** Start ADDED */

        public int compareTo(LF2Struct other) {

            int returnValue = 0;
            returnValue = checkNullsAndCompare(this.K1, other.K1);
            if (returnValue != 0) {
                return returnValue;
            }
            returnValue = checkNullsAndCompare(this.K2, other.K2);

            return returnValue;
        }

        /** End ADDED */
    }

    public void tFileInputDelimited_3Process() throws TalendException {
        try {

            LF2Struct LF2 = new LF2Struct();

            /**
             * [tAdvancedHash_LF2 begin ] start
             */

            ok_Hash.put("tAdvancedHash_LF2", false);
            start_Hash.put("tAdvancedHash_LF2", System.currentTimeMillis());
            currentComponent = "tAdvancedHash_LF2";

            PersistentAdvancedLookup.MATCHING_MODE matchingModeEnum_LF2 = PersistentAdvancedLookup.MATCHING_MODE.LAST_MATCH;
            PersistentAdvancedLookup<LF2Struct> tHash_Lookup_LF2 = PersistentAdvancedLookup
                    .<LF2Struct> getLookup(matchingModeEnum_LF2);
            globalMap.put("tHash_Lookup_LF2", tHash_Lookup_LF2);

            /**
             * [tAdvancedHash_LF2 begin ] stop
             */

            /**
             * [tFileInputDelimited_3 begin ] start
             */

            ok_Hash.put("tFileInputDelimited_3", false);
            start_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());
            currentComponent = "tFileInputDelimited_3";

            org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_3 = new org.talend.fileprocess.FileInputDelimited(
                    "/home/amaumont/data/dev/projets/Talend/TUJV/files/tMap/test_persistent_hash_00/in/lookup_multi_rows2.csv",
                    "ISO-8859-15", ";", "\n", true, 0, 0, -1, -1);
            while (fid_tFileInputDelimited_3.nextRecord()) {
                LF2 = null;
                LF2 = null;
                boolean whetherReject_tFileInputDelimited_3 = false;
                LF2 = new LF2Struct();
                try {
                    String temp_tFileInputDelimited_3 = "";
                    temp_tFileInputDelimited_3 = fid_tFileInputDelimited_3.get(0);
                    if (temp_tFileInputDelimited_3.length() > 0) {
                        LF2.K1 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_3);
                    } else {
                        LF2.K1 = null;
                    }
                    temp_tFileInputDelimited_3 = fid_tFileInputDelimited_3.get(1);
                    if (temp_tFileInputDelimited_3.length() > 0) {
                        LF2.K2 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_3);
                    } else {
                        LF2.K2 = null;
                    }
                    LF2.V = fid_tFileInputDelimited_3.get(2);

                } catch (Exception e) {
                    whetherReject_tFileInputDelimited_3 = true;
                    System.err.println(e.getMessage());
                    LF2 = null;
                }

                /**
                 * [tFileInputDelimited_3 begin ] stop
                 */
                /**
                 * [tFileInputDelimited_3 main ] start
                 */

                currentComponent = "tFileInputDelimited_3";

                /**
                 * [tFileInputDelimited_3 main ] stop
                 */
                // Start of branch "LF2"
                if (LF2 != null) {

                    /**
                     * [tAdvancedHash_LF2 main ] start
                     */

                    currentComponent = "tAdvancedHash_LF2";

                    LF2Struct LF2_HashRow = new LF2Struct();

                    LF2_HashRow.K1 = LF2.K1;
                    LF2_HashRow.K2 = LF2.K2;
                    LF2_HashRow.V = LF2.V;
                    tHash_Lookup_LF2.put(LF2_HashRow);

                    /**
                     * [tAdvancedHash_LF2 main ] stop
                     */

                } // End of branch "LF2"

                /**
                 * [tFileInputDelimited_3 end ] start
                 */

                currentComponent = "tFileInputDelimited_3";

            }
            fid_tFileInputDelimited_3.close();
            globalMap.put("tFileInputDelimited_3_NB_LINE", fid_tFileInputDelimited_3.getRowNumber());

            ok_Hash.put("tFileInputDelimited_3", true);
            end_Hash.put("tFileInputDelimited_3", System.currentTimeMillis());

            /**
             * [tFileInputDelimited_3 end ] stop
             */
            /**
             * [tAdvancedHash_LF2 end ] start
             */

            currentComponent = "tAdvancedHash_LF2";

            ok_Hash.put("tAdvancedHash_LF2", true);
            end_Hash.put("tAdvancedHash_LF2", System.currentTimeMillis());

            /**
             * [tAdvancedHash_LF2 end ] stop
             */

        } catch (Exception e) {

            throw new TalendException(this, e);
        }

    }

    private class LF3Struct implements Comparable<LF3Struct> {

        private static final int DEFAULT_HASHCODE = 1;

        private static final int PRIME = 31;

        private int hashCode = DEFAULT_HASHCODE;

        public boolean hashCodeDirty = true;

        Integer K1;

        Integer K2;

        String V;

        @Override
        public int hashCode() {
            if (this.hashCodeDirty) {
                final int prime = PRIME;
                int result = DEFAULT_HASHCODE;

                result = prime * result + ((this.K1 == null) ? 0 : this.K1.hashCode());

                result = prime * result + ((this.K2 == null) ? 0 : this.K2.hashCode());

                this.hashCode = result;
                this.hashCodeDirty = false;
            }
            return this.hashCode;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            final LF3Struct other = (LF3Struct) obj;

            if (this.K1 == null) {
                if (other.K1 != null)
                    return false;
            } else if (!this.K1.equals(other.K1))
                return false;

            if (this.K2 == null) {
                if (other.K2 != null)
                    return false;
            } else if (!this.K2.equals(other.K2))
                return false;

            return true;
        }

        /** Start ADDED */

        public int compareTo(LF3Struct other) {

            int returnValue = 0;
            returnValue = checkNullsAndCompare(this.K1, other.K1);
            if (returnValue != 0) {
                return returnValue;
            }
            returnValue = checkNullsAndCompare(this.K2, other.K2);

            return returnValue;
        }

        /** End ADDED */

    }

    public void tFileInputDelimited_4Process() throws TalendException {
        try {

            LF3Struct LF3 = new LF3Struct();

            /**
             * [tAdvancedHash_LF3 begin ] start
             */

            ok_Hash.put("tAdvancedHash_LF3", false);
            start_Hash.put("tAdvancedHash_LF3", System.currentTimeMillis());
            currentComponent = "tAdvancedHash_LF3";

            PersistentAdvancedLookup.MATCHING_MODE matchingModeEnum_LF3 = PersistentAdvancedLookup.MATCHING_MODE.LAST_MATCH;
            PersistentAdvancedLookup<LF3Struct> tHash_Lookup_LF3 = PersistentAdvancedLookup
                    .<LF3Struct> getLookup(matchingModeEnum_LF3);
            globalMap.put("tHash_Lookup_LF3", tHash_Lookup_LF3);

            /**
             * [tAdvancedHash_LF3 begin ] stop
             */

            /**
             * [tFileInputDelimited_4 begin ] start
             */

            ok_Hash.put("tFileInputDelimited_4", false);
            start_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());
            currentComponent = "tFileInputDelimited_4";

            org.talend.fileprocess.FileInputDelimited fid_tFileInputDelimited_4 = new org.talend.fileprocess.FileInputDelimited(
                    "/home/amaumont/data/dev/projets/Talend/TUJV/files/tMap/test_persistent_hash_00/in/lookup_multi_rows3.csv",
                    "ISO-8859-15", ";", "\n", true, 0, 0, -1, -1);
            while (fid_tFileInputDelimited_4.nextRecord()) {
                LF3 = null;
                LF3 = null;
                boolean whetherReject_tFileInputDelimited_4 = false;
                LF3 = new LF3Struct();
                try {
                    String temp_tFileInputDelimited_4 = "";
                    temp_tFileInputDelimited_4 = fid_tFileInputDelimited_4.get(0);
                    if (temp_tFileInputDelimited_4.length() > 0) {
                        LF3.K1 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_4);
                    } else {
                        LF3.K1 = null;
                    }
                    temp_tFileInputDelimited_4 = fid_tFileInputDelimited_4.get(1);
                    if (temp_tFileInputDelimited_4.length() > 0) {
                        LF3.K2 = ParserUtils.parseTo_Integer(temp_tFileInputDelimited_4);
                    } else {
                        LF3.K2 = null;
                    }
                    LF3.V = fid_tFileInputDelimited_4.get(2);

                } catch (Exception e) {
                    whetherReject_tFileInputDelimited_4 = true;
                    System.err.println(e.getMessage());
                    LF3 = null;
                }

                /**
                 * [tFileInputDelimited_4 begin ] stop
                 */
                /**
                 * [tFileInputDelimited_4 main ] start
                 */

                currentComponent = "tFileInputDelimited_4";

                /**
                 * [tFileInputDelimited_4 main ] stop
                 */
                // Start of branch "LF3"
                if (LF3 != null) {

                    /**
                     * [tAdvancedHash_LF3 main ] start
                     */

                    currentComponent = "tAdvancedHash_LF3";

                    LF3Struct LF3_HashRow = new LF3Struct();

                    LF3_HashRow.K1 = LF3.K1;
                    LF3_HashRow.K2 = LF3.K2;
                    LF3_HashRow.V = LF3.V;
                    tHash_Lookup_LF3.put(LF3_HashRow);

                    /**
                     * [tAdvancedHash_LF3 main ] stop
                     */

                } // End of branch "LF3"

                /**
                 * [tFileInputDelimited_4 end ] start
                 */

                currentComponent = "tFileInputDelimited_4";

            }
            fid_tFileInputDelimited_4.close();
            globalMap.put("tFileInputDelimited_4_NB_LINE", fid_tFileInputDelimited_4.getRowNumber());

            ok_Hash.put("tFileInputDelimited_4", true);
            end_Hash.put("tFileInputDelimited_4", System.currentTimeMillis());

            /**
             * [tFileInputDelimited_4 end ] stop
             */
            /**
             * [tAdvancedHash_LF3 end ] start
             */

            currentComponent = "tAdvancedHash_LF3";

            ok_Hash.put("tAdvancedHash_LF3", true);
            end_Hash.put("tAdvancedHash_LF3", System.currentTimeMillis());

            /**
             * [tAdvancedHash_LF3 end ] stop
             */

        } catch (Exception e) {

            throw new TalendException(this, e);
        }

    }

    public static boolean watch = false;

    public static int portStats = 3334;

    public static int portTraces = 4334;

    public static String clientHost;

    public static String defaultClientHost = "localhost";

    public static String contextStr = "Default";

    public static String pid = "0";

    public static String rootPid = null;

    public static String fatherPid = null;

    private static java.util.Properties context_param = new java.util.Properties();

    public static String status = "";

    public static void main(String[] args) {

        int exitCode = runJobInTOS(args);

        globalBuffer.clear();

        System.exit(exitCode);
    }

    public static String[][] runJob(String[] args) {

        int exitCode = runJobInTOS(args);
        String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

        globalBuffer.clear();

        return bufferValue;
    }

    public static synchronized int runJobInTOS(String[] args) {

        init();

        String lastStr = "";
        for (String arg : args) {
            if (arg.equalsIgnoreCase("--context_param")) {
                lastStr = arg;
            } else if (lastStr.equals("")) {
                evalParam(arg);
            } else {
                evalParam(lastStr + " " + arg);
                lastStr = "";
            }
        }

        if (clientHost == null) {
            clientHost = defaultClientHost;
        }

        pid = TalendString.getAsciiRandomString(6);
        if (rootPid == null) {
            rootPid = pid;
        }
        if (fatherPid == null) {
            fatherPid = pid;
        }

        try {
            java.io.InputStream inContext = MainPrototype1.class.getClassLoader().getResourceAsStream(
                    "test/prototype/main/contexts/Default.properties");
            if (inContext != null) {
                defaultProps.load(inContext);
                inContext.close();
                context = new ContextProperties(defaultProps);
            }

            if (contextStr.compareTo("Default") != 0) {
                inContext = MainPrototype1.class.getClassLoader().getResourceAsStream(
                        "test/prototype/main/contexts/" + contextStr + ".properties");
                if (inContext != null) {
                    context.load(inContext);
                    inContext.close();
                }
            }

            if (!context_param.isEmpty()) {
                context.putAll(context_param);
            }

        } catch (java.io.IOException ie) {
            System.err.println("Could not load context " + contextStr);
            ie.printStackTrace();
        }

        long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long endUsedMemory = 0;
        long end = 0;
        long start = System.currentTimeMillis();
        final MainPrototype1 Case2LookupFilesClass = new MainPrototype1();
        try {
            errorCode = null;
            Case2LookupFilesClass.tFileInputDelimited_1Process();
            status = "end";
        } catch (TalendException e_tFileInputDelimited_1) {
            status = "failure";
            e_tFileInputDelimited_1.printStackTrace();

        } finally {
        }
        end = System.currentTimeMillis();
        if (watch) {
            System.out.println((end - start) + " milliseconds");
        }

        endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        if (false) {
            System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : Case2LookupFiles");
        }

        reset();

        if (errorCode == null) {
            return status != null && status.equals("failure") ? 1 : 0;
        } else {
            return errorCode.intValue();
        }
    }

    public static void evalParam(String arg) {
        if (arg.startsWith("--watch")) {
            watch = true;
        } else if (arg.startsWith("--stat_port=")) {
            portStats = Integer.parseInt(arg.substring(12));
        } else if (arg.startsWith("--trace_port=")) {
            portTraces = Integer.parseInt(arg.substring(13));
        } else if (arg.startsWith("--client_host=")) {
            clientHost = arg.substring(14);
        } else if (arg.startsWith("--context=")) {
            contextStr = arg.substring(10);
        } else if (arg.startsWith("--father_pid=")) {
            fatherPid = arg.substring(13);
        } else if (arg.startsWith("--root_pid=")) {
            rootPid = arg.substring(11);
        } else if (arg.startsWith("--context_param")) {
            String keyValue = arg.substring(16);
            int index = -1;
            if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
                context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
            }
        }

    }

    private static void init() {
        errorCode = null;
        status = "";
    }

    private static void reset() {

        defaultProps.clear();
        context.clear();
        currentComponent = "";
        start_Hash.clear();
        end_Hash.clear();
        ok_Hash.clear();
        globalMap.clear();
        watch = false;
        portStats = 3334;
        portTraces = 4334;
        clientHost = null;
        defaultClientHost = "localhost";
        contextStr = "Default";
        pid = "0";
        rootPid = null;
        fatherPid = null;
        context_param.clear();
        System.gc();
    }

    private static int checkNullsAndCompare(Object object1, Object object2) {

        int ascendingInt = 1;

        int returnValue = 0;
        if (object1 instanceof String && object2 instanceof String) {
            returnValue = compareStrings((String) object1, (String) object2);
        } else if (object1 instanceof Comparable && object2 instanceof Comparable) {
            returnValue = ((Comparable) object1).compareTo(object2);
        } else if (object1 != null && object2 != null) {
            returnValue = compareStrings(object1.toString(), object2.toString());
        } else if (object1 == null && object2 != null) {
            returnValue = 1;
        } else if (object1 != null && object2 == null) {
            returnValue = -1;
        } else {
            returnValue = 0;
        }

        return ascendingInt * returnValue;
    }

    private static int compareStrings(String string1, String string2) {
        boolean ignoreCase = true;
        if (ignoreCase) {
            return string1.compareToIgnoreCase(string2);
        } else {
            return string1.compareTo(string2);
        }
    }

}
/***********************************************************************************************************************
 * 46675 characters generated by Talend OpenStudio on the 26 mars 2008 17:15:48 CET
 **********************************************************************************************************************/
