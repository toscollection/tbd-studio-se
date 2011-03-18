// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repositorynode.test;

import java.util.ArrayList;
import java.util.List;

import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.repository.IExtendRepositoryNode;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.StableRepositoryNode;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class ExampleExtendRepositoryNode implements IExtendRepositoryNode {

    public IImage getNodeImage() {
        return ECoreImage.DEFAULT_WIZ;
    }

    public int getOrdinal() {
        return 26;
    }

    @Override
    public Object[] getChildren() {
        List<RepositoryNode> children = new ArrayList<RepositoryNode>();
        RepositoryNode generatedFolder = new StableRepositoryNode(null, "folder", ECoreImage.FOLDER_CLOSE_ICON);
        children.add(generatedFolder);
        return children.toArray(new RepositoryNode[0]);
    }

}
