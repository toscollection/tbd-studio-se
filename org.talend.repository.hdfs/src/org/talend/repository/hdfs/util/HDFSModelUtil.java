// ============================================================================
//
// Copyright (C) 2006-2013 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.hdfs.util;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.log4j.Logger;
import org.talend.designer.hdfsbrowse.model.HDFSConnectionBean;
import org.talend.repository.model.hdfs.HDFSConnection;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HDFSModelUtil {

    private static Logger log = Logger.getLogger(HDFSModelUtil.class);

    /**
     * DOC ycbai Comment method "convert2HDFSConnectionBean".
     * 
     * Convert HDFSConnection to HDFSConnectionBean
     * 
     * @param connection
     * @return
     */
    public static HDFSConnectionBean convert2HDFSConnectionBean(HDFSConnection connection) {
        HDFSConnectionBean bean = new HDFSConnectionBean();
        try {
            BeanUtils.copyProperties(bean, connection);
        } catch (Exception e) {
            log.error("Convert failure from HDFSConnection to HDFSConnectionBean", e);
        }

        return bean;
    }

}
