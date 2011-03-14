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

import org.talend.commons.ui.runtime.image.ECoreImage;
import org.talend.commons.ui.runtime.image.IImage;
import org.talend.core.repository.IExtendRepositoryNode;

/**
 * DOC hywang class global comment. Detailled comment
 */
public class ExampleExtendRepositoryNode implements IExtendRepositoryNode {

    public String getNodeLabel() {
        return "Extend";
    }

    public String getNodeType() {
        return "Extend";
    }

    public IImage getNodeImage() {
        return ECoreImage.DEFAULT_WIZ;
    }

    public int getOrdinal() {
        return 6;
    }

}
