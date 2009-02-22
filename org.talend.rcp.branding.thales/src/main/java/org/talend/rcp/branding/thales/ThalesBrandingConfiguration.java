// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.rcp.branding.thales;

import java.util.List;

import org.eclipse.gef.palette.PaletteRoot;
import org.talend.core.model.components.ComponentUtilities;
import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.ui.branding.DefaultBrandingConfiguration;
import org.talend.core.ui.images.ECoreImage;
import org.talend.repository.model.RepositoryNode;
import org.talend.repository.model.StableRepositoryNode;
import org.talend.repository.model.RepositoryNode.EProperties;

/**
 * DOC aiming class global comment. Detailled comment
 */
public class ThalesBrandingConfiguration extends DefaultBrandingConfiguration {

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.IBrandingConfiguration#getHiddenRepositoryCategory()
     */
    public List<RepositoryNode> getHiddenRepositoryCategory(RepositoryNode parent) {
        List<RepositoryNode> nodes = super.getHiddenRepositoryCategory(parent);

        // Code
        StableRepositoryNode codeNode = new StableRepositoryNode(parent, "Code", ECoreImage.CODE_ICON); //$NON-NLS-1$
        codeNode.setProperties(EProperties.CONTENT_TYPE, ERepositoryObjectType.ROUTINES);
        nodes.add(codeNode);
        return nodes;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.core.ui.branding.DefaultBrandingConfiguration#hideComponents()
     */
    @Override
    public void hideComponents() {
        // TODO Auto-generated method stub
        super.hideComponents();
        String[] familys = { "SAP" }; // familys to hide
        PaletteRoot root = ComponentUtilities.getPaletteRoot();
        // just test
        for (String family : familys) {
            hideComponents(root, family);
        }
    }
}
