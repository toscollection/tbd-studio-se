package publishing.tujs;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Arrays;
import java.util.HashSet;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.MatchResult;
import org.apache.oro.text.regex.Pattern;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.eclipse.core.runtime.Path;

public class TmapTujPublisher {

    private static final int BSIZE = 1024;

    /**
     * DOC amaumont Comment method "main".
     * 
     * @param args
     * @throws MalformedPatternException
     * @throws IOException
     */
    public static void main(String[] args) throws MalformedPatternException, IOException {

        boolean copyData = false;

        String sourceFilesDir = "/home/amaumont/data/dev/projets/Talend/TUJV/files/tMap";
        String sourceJobsDir = "/home/amaumont/data/dev/eclipse/workspaces/runtime-talend.product3/JAVA_PROJECT_8/process/components/tMap";
        String sourceRoutineDir = "/home/amaumont/data/dev/eclipse/workspaces/runtime-talend.product3/JAVA_PROJECT_8/code/routines";

        String targetDir = "/home/amaumont/data/dev/eclipse/workspaces/workspace-talend-linux-trunk1/tuj/java/";

        
        System.out.println("sourceFilesDir=" + sourceFilesDir);
        System.out.println("sourceJobsDir=" + sourceJobsDir);
        System.out.println("sourceRoutineDir=" + sourceRoutineDir);
        System.out.println("targetDir=" + targetDir);
        
        
        
        String staticPrefix = "tMap_";

        String[] firstIndices = { "03", "04", "05", "06", "07", "08", "09", "10", };

        String[] exceptions = { "tMap_03_persistence_01_bug", };
        
        HashSet<String> hExceptions = new HashSet<String>();
        for (int i = 0; i < exceptions.length; i++) {
            hExceptions.add(exceptions[i]);
        }

        
        
        File sourceJobsDirFile = new File(sourceJobsDir);

        Perl5Compiler compiler = new Perl5Compiler();
        Pattern pattern = compiler.compile("(.*)_\\d+\\.\\d+\\.item");
        Perl5Matcher matcher = new Perl5Matcher();

        if (sourceJobsDirFile.isDirectory()) {

            File[] listFiles = sourceJobsDirFile.listFiles();

            Arrays.sort(listFiles);

            for (File file : listFiles) {

                if (!file.isDirectory()) {

                    for (String firstIndice : firstIndices) {

                        if (file.getName().startsWith(staticPrefix + firstIndice) && file.getName().endsWith(".item")) {

                            String itemName = file.getName();
                            String propertyName = file.getName().replaceAll("item$", "properties");

                            String itemFilePath = file.getAbsolutePath();
                            String propertyFilePath = file.getParent() + "/" + propertyName;

                            boolean matches = matcher.matches(itemName, pattern);
                            String baseName = null;

                            if (matches) {

                                MatchResult match = matcher.getMatch();
                                baseName = match.group(1);

                                boolean isChild = false;
                                if (baseName.endsWith("_CHILD")) {
                                    baseName = baseName.replaceAll("_CHILD", "");
                                    isChild = true;
                                }

                                if(hExceptions.contains(baseName)) {
                                    continue;
                                }
                                
                                System.out.println("Job '" + baseName + "': ");

                                String targetDirectoryPath = targetDir + baseName + "/process/components/tMap/";

                                File targetDirectoryItems = new File(targetDirectoryPath);

                                File[] listFilesItemsToRemove = targetDirectoryItems.listFiles();

                                if(listFilesItemsToRemove == null) {
                                    
                                    System.err.println("targetDirectoryItems does not exist=" + targetDirectoryItems.getAbsolutePath());
                                    continue;
                                }

                                if (!isChild) {
                                    
                                    for (File fileToRemove : listFilesItemsToRemove) {
                                        if (fileToRemove.isFile()) {
                                            fileToRemove.delete();
                                        }
                                    }
                                }

                                copyFile(itemFilePath, targetDirectoryPath + itemName);
                                copyFile(propertyFilePath, targetDirectoryPath + propertyName);

                                if (isChild) {
                                    continue;
                                }

                                // String sourceJobItem = sourceJobsDir + "/" + file.getName() +

                                if (copyData) {

                                    String routineFileName = "routine_" + baseName + "_0.1.item";
                                    String routinePropertiesFileName = "routine_" + baseName + "_0.1.properties";

                                    String routineItem = sourceRoutineDir + "/" + routineFileName;
                                    String routineProperties = sourceRoutineDir + "/" + routinePropertiesFileName;
                                    String targetRoutineFolder = targetDir + baseName + "/code/routines/";

                                    File routineFile = new File(routineItem);

                                    if (routineFile.isFile()) {
                                        copyFile(routineItem, targetRoutineFolder + routineFileName);
                                        copyFile(routineProperties, targetRoutineFolder + routinePropertiesFileName);
                                    }

                                    File sourceFilesDirFile = new File(sourceFilesDir + "/" + baseName + "/files/in/");
                                    String targetDirFiles = targetDir + "/" + baseName + "/files/in/";

                                    if (!sourceFilesDirFile.isDirectory()) {
                                        throw new IllegalStateException(
                                                "The folder sourceFilesDirFile does not exist: '"
                                                        + sourceFilesDirFile.getAbsolutePath() + "'");
                                    }

                                    File targetFolder = new File(targetDirFiles);
                                    if (!targetFolder.isDirectory()) {
                                        throw new IllegalStateException("The folder targetFolder does not exist: '"
                                                + targetFolder.getAbsolutePath() + "'");
                                    }

                                    File[] listFilesToRemove = targetFolder.listFiles();
                                    for (File fileToRemove : listFilesToRemove) {
                                        if (fileToRemove.isFile()) {
                                            fileToRemove.delete();
                                        }
                                    }

                                    File[] listDataFiles = sourceFilesDirFile.listFiles();
                                    for (File dataFile : listDataFiles) {
                                        String sourceFilePath = sourceFilesDirFile.getAbsolutePath() + "/"
                                                + dataFile.getName();
                                        String targetFilePath = targetDirFiles + dataFile.getName();
                                        copyFile(sourceFilePath, targetFilePath);
                                    }

                                    sourceFilesDirFile = new File(sourceFilesDir + "/" + baseName + "/files/ref/");
                                    targetDirFiles = targetDir + "/" + baseName + "/files/ref/";

                                    if (!sourceFilesDirFile.isDirectory()) {
                                        throw new IllegalStateException(
                                                "The folder sourceFilesDirFile does not exist: '"
                                                        + sourceFilesDirFile.getAbsolutePath() + "'");
                                    }

                                    targetFolder = new File(targetDirFiles);
                                    if (!targetFolder.isDirectory()) {
                                        throw new IllegalStateException("The folder targetFolder does not exist: '"
                                                + targetFolder.getAbsolutePath() + "'");
                                    }

                                    listFilesToRemove = targetFolder.listFiles();
                                    for (File fileToRemove : listFilesToRemove) {
                                        if (fileToRemove.isFile()) {
                                            fileToRemove.delete();
                                        }
                                    }

                                    listDataFiles = sourceFilesDirFile.listFiles();

                                    for (File dataFile : listDataFiles) {
                                        String sourceFilePath = sourceFilesDirFile.getAbsolutePath() + "/"
                                                + dataFile.getName();
                                        String targetFilePath = targetDirFiles + dataFile.getName();
                                        copyFile(sourceFilePath, targetFilePath);
                                    }

                                }

                            } else {

                                System.out.println(itemName + " can't be matched");

                            }

                        }
                    }

                }

            }

        }

    }

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

        // System.out.println("Copied " + sourcePathFile + "\n -> " + destPathFile);
        System.out.println("   Copied: '" + new Path(destPathFile).lastSegment() + "'");

    }

}
