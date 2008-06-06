package generator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Random;

public class InputFilesGenerator {

    private String folderInStr;

    /**
     * DOC amaumont InputFilesGenerator constructor comment.
     */
    public InputFilesGenerator(String folderInStr) {
        super();
        this.folderInStr = folderInStr;
    }

    public static void main(String[] args) throws IOException {
        new InputFilesGenerator(args[0]).generate();
    }

    private void generate() throws IOException {

        // String folderInStr = "/home/amaumont/data/dev/projets/Talend/TUJV/tests_with_generator/files/in";

        Random random = new Random(System.currentTimeMillis());

        int maxFiles = 1000;
        int maxRowsCount = 10;
        int maxIndex = 5;
        int maxDuplicates = 10;

        for (int iFile = 0; iFile < maxFiles; iFile++) {

            int countRows = 0;

            int rowsCountLimit = random.nextInt(maxRowsCount);

            String path = folderInStr + "/main_" + (iFile + 1) + ".csv";

            File fileIn = new File(path);

            Writer fileWriter = new BufferedWriter(new FileWriter(fileIn));

            int codeChar = 97;

            while (countRows < rowsCountLimit) {

                int duplicates = random.nextInt(maxDuplicates);

                int keyValue = random.nextInt(maxIndex);

                char letter = (char) (codeChar)++;

                String letterStr = new String("" + letter);

                for (int iDuplicate = 0; iDuplicate < duplicates && countRows < rowsCountLimit; iDuplicate++) {

                    String strValue = repeat(letterStr, (iDuplicate + 1));

                    strValue = "LABEL_" + (iDuplicate + 1) + "_" + strValue;

                    fileWriter.write(keyValue + ";" + strValue + "\n");
                    countRows++;

                }

            }

            fileWriter.close();

            copyFile(path, folderInStr + "/lookup_" + (iFile + 1) + ".csv");

        }

    }

    private static final int BSIZE = 1024;

    public static void copyFile(String sourcePathFile, String destPathFile) throws IOException {

        FileChannel in = new FileInputStream(sourcePathFile).getChannel(), out = new FileOutputStream(destPathFile)
                .getChannel();
        ByteBuffer buffer = ByteBuffer.allocate(BSIZE);
        while (in.read(buffer) != -1) {
            buffer.flip(); // Prepare for writing
            out.write(buffer);
            buffer.clear(); // Prepare for reading
        }

        in.close();
        out.close();

        System.out.println("Copied " + sourcePathFile + "\n -> " + destPathFile);
        // System.out.println(" Copied: '" + new Path(destPathFile).lastSegment() + "'");

    }

    /**
     * The empty String <code>""</code>.
     * 
     * @since 2.0
     */
    public static final String EMPTY = "";

    /**
     * Represents a failed index search.
     * 
     * @since 2.1
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>
     * The maximum size to which the padding constant(s) can expand.
     * </p>
     */
    private static final int PAD_LIMIT = 8192;

    /**
     * <p>
     * An array of <code>String</code>s used for padding.
     * </p>
     * 
     * <p>
     * Used for efficient space padding. The length of each String expands as needed.
     * </p>
     */
    private static final String[] PADDING = new String[Character.MAX_VALUE];

    static {
        // space padding is most common, start with 64 chars
        PADDING[32] = "                                                                ";
    }

    public static String repeat(String str, int repeat) {
        // Performance tuned for 2.0 (JDK1.4)

        if (str == null) {
            return null;
        }
        if (repeat <= 0) {
            return EMPTY;
        }
        int inputLength = str.length();
        if (repeat == 1 || inputLength == 0) {
            return str;
        }
        if (inputLength == 1 && repeat <= PAD_LIMIT) {
            return padding(repeat, str.charAt(0));
        }

        int outputLength = inputLength * repeat;
        switch (inputLength) {
        case 1:
            char ch = str.charAt(0);
            char[] output1 = new char[outputLength];
            for (int i = repeat - 1; i >= 0; i--) {
                output1[i] = ch;
            }
            return new String(output1);
        case 2:
            char ch0 = str.charAt(0);
            char ch1 = str.charAt(1);
            char[] output2 = new char[outputLength];
            for (int i = repeat * 2 - 2; i >= 0; i--, i--) {
                output2[i] = ch0;
                output2[i + 1] = ch1;
            }
            return new String(output2);
        default:
            StringBuffer buf = new StringBuffer(outputLength);
            for (int i = 0; i < repeat; i++) {
                buf.append(str);
            }
            return buf.toString();
        }
    }

    private static String padding(int repeat, char padChar) {
        // be careful of synchronization in this method
        // we are assuming that get and set from an array index is atomic
        String pad = PADDING[padChar];
        if (pad == null) {
            pad = String.valueOf(padChar);
        }
        while (pad.length() < repeat) {
            pad = pad.concat(pad);
        }
        PADDING[padChar] = pad;
        return pad.substring(0, repeat);
    }

}
