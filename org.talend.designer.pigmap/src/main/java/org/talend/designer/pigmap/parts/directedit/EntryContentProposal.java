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
package org.talend.designer.pigmap.parts.directedit;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.talend.designer.pigmap.model.emf.pigmap.AbstractNode;
import org.talend.designer.pigmap.ui.tabs.MapperManager;

/**
 * 
 * DOC hcyi class global comment. Detailled comment
 */
public class EntryContentProposal implements IContentProposal {

    private AbstractNode entry;

    private String content = "";

    private MapperManager mapperManager;

    public EntryContentProposal(MapperManager mapperManager, AbstractNode entry) {
        this.mapperManager = mapperManager;
        this.entry = entry;

    }

    @Override
    public String getContent() {
        return content + " ";
    }

    @Override
    public int getCursorPosition() {
        return content.length() + 1;
    }

    @Override
    public String getLabel() {
        return content;
    }

    @Override
    public String getDescription() {

        StringBuilder sb = new StringBuilder();

        return sb.toString();

    }

    public String format(Object object) {
        if (object == null) {
            return ""; //$NON-NLS-1$
        }
        return String.valueOf(object);
    }

}
