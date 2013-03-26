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
package org.talend.repository.hadoopcluster.ui.provider;

import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.repository.hadoopcluster.util.EHadoopClusterImage;
import org.talend.repository.viewer.label.AbstractRepoViewLabelProvider;

/**
 * 
 * created by ycbai on 2013-3-22 Detailled comment
 * 
 */
public class HadoopLabelProvider extends AbstractRepoViewLabelProvider {

    @Override
    protected IImage getIcon(ERepositoryObjectType itemType) {
        if (itemType != null && itemType.equals(ERepositoryObjectType.METADATA_CONNECTIONS)) {
            return EHadoopClusterImage.HADOOP_LINK_DB_ICON;
        }
        return null;
    }

}
