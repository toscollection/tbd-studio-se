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
package org.talend.designer.components.thash.io;

import java.io.Serializable;

/**
 * 
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 */
public class Bean implements Serializable {

    int primitiveInt;

    String name;

    transient int hashcode = -1;

    public static int countReturnFalse1 = 0;

    public static int getDataCountRequested = 0;

    /**
     * DOC amaumont Bean constructor comment.
     * 
     * @param primitiveInt
     * @param name
     */
    public Bean(int primitiveInt, String name) {
        super();
        this.primitiveInt = primitiveInt;
        this.name = name;
    }

    public Bean() {
        super();
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        if (hashcode == -1) {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
            result = prime * result + this.primitiveInt;
            hashcode = result;
        }
        return hashcode;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;

        if (this.hashCode() != obj.hashCode()) {
            countReturnFalse1++;
            return false;
        }
        final KeyForMap other = (KeyForMap) obj;

        Object o = null;
        try {
            getDataCountRequested++;
            o = HashFilesBenchs.hashFile.get("buffer", (long) other.cursorPosition, hashcode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (o == null) {
            return false;
        }
        Bean bean = (Bean) o;

        if (this.name == null) {
            if ((String) bean.name != null) {
                // System.out.println("return false 2");
                return false;
            }
        } else if (!this.name.equals((String) bean.name)) {
            // System.out.println("return false 3");
            return false;
        }
        if (this.primitiveInt != bean.primitiveInt) {
            // System.out.println("return false 4");
            return false;
        }
        return true;
    }

}
