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

package org.talend.designer.components.persistent;

import java.io.IOException;
import java.util.Comparator;

/**
 * DOC amaumont  class global comment. Detailled comment
 * <br/>
 *
 */
public interface IPersistentBinaryDataManager {

    public void initPut() throws IOException;

    public void put(byte[] data) throws IOException;

    public void endPut() throws IOException;

    public void initGet() throws IOException;
    
    public boolean hasNext() throws IOException;
    
    public byte[] next(int length) throws IOException;

    public void endGet() throws IOException;
    
}
