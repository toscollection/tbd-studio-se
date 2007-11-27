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
package org.talend.designer.components.thash;
    /**
     * 
     * DOC amaumont  class global comment. Detailled comment
     * <br/>
     *
     * 
     *
     */
    class KeyForMap {

        int dbIdBean;

        int hashcode;

        /**
         * DOC amaumont KeyForMap constructor comment.
         */
        public KeyForMap(int dbIdBean, int hashcode) {
            super();
            this.dbIdBean = dbIdBean;
            this.hashcode = hashcode;
        }

        /*
         * (non-Javadoc)
         * 
         * @see java.lang.Object#hashCode()
         */
        @Override
        public int hashCode() {
            return this.hashcode;
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
            if (getClass() != obj.getClass())
                return false;
            final KeyForMap other = (KeyForMap) obj;
            if (this.dbIdBean != other.dbIdBean)
                return false;
            return true;
        }

    }
