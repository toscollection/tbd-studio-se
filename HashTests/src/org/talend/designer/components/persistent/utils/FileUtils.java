package org.talend.designer.components.persistent.utils;

import java.io.File;
import java.io.IOException;

public class FileUtils {

    public static void createParentFolderIfNotExists(String filePath) throws IOException {

        File file = new File(filePath);
        File parentFile = file.getParentFile();
        if (!parentFile.isDirectory()) {
            boolean createFolder = parentFile.mkdirs();
            if (!createFolder) {
                throw new RuntimeException("The following directory can't be created : '"
                        + parentFile.getAbsolutePath() + "'");
            }
        }

    }

    public static void main(String[] args) throws IOException {
        createParentFolderIfNotExists("/home/amaumont/temp/folder/test");
    }

}
