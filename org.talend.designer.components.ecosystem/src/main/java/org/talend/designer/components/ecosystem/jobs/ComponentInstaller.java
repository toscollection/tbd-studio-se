// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.jobs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Install component to tos.
 */
public class ComponentInstaller {

    private static final int BUFFER_SIZE = 8192;

    /**
     * Unzip the component file to user folder.
     * 
     * @param zipFile The component zip file
     * @param targetFolder The user folder
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public static File unzip(String zipFile, String targetFolder) throws Exception {

        ZipFile zip = new ZipFile(zipFile);
        byte[] buf = new byte[BUFFER_SIZE];

        // folder that contains all the unzipped files
        File rootFolder = getRootFolder(zip, targetFolder);

        if (rootFolder == null) {
            // the zip does not have any directory, fix it
            String fileName = zip.getName().substring(zip.getName().lastIndexOf(File.separatorChar) + 1);
            fileName = fileName.substring(0, fileName.indexOf('.')); // remove extension
            rootFolder = new File(targetFolder, fileName);
            targetFolder = targetFolder + File.separatorChar + fileName;
        }

        if (rootFolder.exists()) {
            // we have installed older revision, delete it
            rootFolder.delete();
        }
        rootFolder.mkdir();

        Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zip.entries();

        while (enumeration.hasMoreElements()) {
            ZipEntry entry = enumeration.nextElement();

            File file = new File(targetFolder, entry.getName());
            String filePath = file.getAbsolutePath();

            if (entry.isDirectory()) {
                if (!file.exists()) {
                    file.mkdir();
                }
            } else {

                InputStream zin = zip.getInputStream(entry);
                OutputStream fout = new FileOutputStream(filePath);

                while (true) {
                    int bytesRead = zin.read(buf);
                    if (bytesRead == -1) { // end of file
                        break;
                    }
                    fout.write(buf, 0, bytesRead);

                }
                fout.flush();
                fout.close();
            }
        }
        zip.close();
        return rootFolder;
    }

    /**
     * Get the root folder that contains all the unzipped files
     * 
     * @param zip The component zip file
     * @param targetFolder The user folder
     * @return
     */
    @SuppressWarnings("unchecked")
    private static File getRootFolder(ZipFile zip, String targetFolder) {
        File rootFolder = null;
        Enumeration<ZipEntry> enumeration = (Enumeration<ZipEntry>) zip.entries();
        while (enumeration.hasMoreElements()) {
            ZipEntry entry = enumeration.nextElement();
            File file = new File(targetFolder, entry.getName());
            if (entry.isDirectory()) {
                // assumes that the first folder is root folder
                if (rootFolder == null) {
                    rootFolder = file;
                    break;
                }

            }
        }
        return rootFolder;
    }

    public static void main(String[] args) {
        try {
            unzip("d:/tFileOutputPDF.zip", "d:/temp");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
