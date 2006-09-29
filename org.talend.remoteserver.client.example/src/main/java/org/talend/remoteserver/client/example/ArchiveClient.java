// ============================================================================
//
// Talend Community Edition
//
// Copyright (C) 2006 Talend - www.talend.com
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 675 Mass Ave, Cambridge, MA 02139, USA.
//
// ============================================================================
package org.talend.remoteserver.client.example;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.mail.util.ByteArrayDataSource;

import org.talend.remoteserver.client.example.archive.ArchiveProxy;

/**
 * DOC mhirt class global comment. Detailled comment <br/>
 * 
 * $Id$
 * 
 */
public class ArchiveClient {

    /**
     * DOC mhirt Comment method "main".
     * 
     * @param args
     * @throws IOException
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws IOException {
        ArchiveProxy proxy = new ArchiveProxy();

        String fileName = "logo.gif";
        File file = new File("C:\\Documents and Settings\\mhirt.TALEND\\Bureau\\" + fileName);
        DataSource ds = new ByteArrayDataSource(new FileInputStream(file), "multipart/mixed");
        ds.getContentType();
        DataHandler dh = new DataHandler(ds);

        System.out.println(proxy.storeFile("newLogo.gif", dh));

    }

}
