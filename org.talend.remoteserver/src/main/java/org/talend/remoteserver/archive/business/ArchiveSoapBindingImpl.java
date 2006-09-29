/**
 * ArchiveSoapBindingImpl.java
 * 
 * This file was auto-generated from WSDL by the Apache Axis 1.3 Oct 05, 2005 (05:23:37 EDT) WSDL2Java emitter.
 */

package org.talend.remoteserver.archive.business;

import java.io.FileOutputStream;
import java.io.IOException;

import javax.activation.DataHandler;

public class ArchiveSoapBindingImpl implements org.talend.remoteserver.archive.business.Archive {

    public java.lang.String storeFile(java.lang.String filename, java.lang.Object dh) throws java.rmi.RemoteException {
        try {
            FileOutputStream fos = new FileOutputStream("C:\\wsfiles\\" + filename);
            ((DataHandler) dh).writeTo(fos);
            fos.close();
            return "File received: " + filename;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
    }
}
