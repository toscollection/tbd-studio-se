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
package org.talend.designer.dbmap.ui.proposal.expression;

import org.eclipse.jface.fieldassist.IContentProposal;
import org.talend.core.model.metadata.IMetadataColumn;
import org.talend.designer.dbmap.MapperMain;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.language.AbstractDbLanguage;
import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.model.tableentry.InputColumnTableEntry;
import org.talend.designer.dbmap.model.tableentry.VarTableEntry;

/**
 * Content proposal based on a IContextParameter. <br/>
 * 
 * $Id: EntryContentProposal.java 1795 2007-02-04 04:15:56Z bqian $
 * 
 */
public class EntryContentProposal implements IContentProposal {

    private ITableEntry entry;

    private IDbLanguage language;

    private String content;

    /**
     * Constructs a new ContextParameterProposal.
     * 
     * @param language
     * @param control
     */
    public EntryContentProposal(ITableEntry entry, IDbLanguage language) {
        super();
        this.entry = entry;
        this.language = language;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getContent()
     */
    public String getContent() {
        content = language.getLocation(entry.getParentName(), entry.getName()) + " "; //$NON-NLS-1$
        return content;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getCursorPosition()
     */
    public int getCursorPosition() {
        return content.length() + 1;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getDescription()
     */
    public String getDescription() {

        StringBuilder sb = new StringBuilder();

        String separator = " - "; //$NON-NLS-1$
        if (entry instanceof InputColumnTableEntry) {

            InputColumnTableEntry inputEntry = (InputColumnTableEntry) entry;

            IMetadataColumn metadataColumn = inputEntry.getMetadataColumn();
            sb.append(Messages.getString("EntryContentProposal.metadataColumn")).append(" '").append(metadataColumn.getLabel()) //$NON-NLS-1$ //$NON-NLS-2$
                    .append("' "); //$NON-NLS-1$
            sb.append(Messages.getString("EntryContentProposal.properties")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.column")).append(metadataColumn.getLabel()); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.key")).append(metadataColumn.isKey()); //$NON-NLS-1$
            if (!MapperMain.isStandAloneMode()) {
                sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
                sb.append(separator)
                        .append(Messages.getString("EntryContentProposal.type")).append(format(metadataColumn.getTalendType())); //$NON-NLS-1$
            }
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator)
                    .append(Messages.getString("EntryContentProposal.length")).append(format(metadataColumn.getLength())); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator)
                    .append(Messages.getString("EntryContentProposal.precision")).append(format(metadataColumn.getPrecision())); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator)
                    .append(Messages.getString("EntryContentProposal.default")).append(format(metadataColumn.getDefault())); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator)
                    .append(Messages.getString("EntryContentProposal.comment")).append(format(metadataColumn.getComment())); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.expressionKey")); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(format(entry.getExpression()));
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
        } else if (entry instanceof VarTableEntry) {
            sb.append(Messages.getString("EntryContentProposal.variable")).append(" '").append(entry.getName()).append("' :"); //$NON-NLS-1$//$NON-NLS-2$ //$NON-NLS-3$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(separator).append(Messages.getString("EntryContentProposal.expressionKey")); //$NON-NLS-1$
            sb.append(AbstractDbLanguage.CARRIAGE_RETURN);
            sb.append(format(entry.getExpression()));
        }
        return sb.toString();
    }

    public String format(Object object) {
        if (object == null) {
            return ""; //$NON-NLS-1$
        }
        return String.valueOf(object);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.fieldassist.IContentProposal#getLabel()
     */
    public String getLabel() {
        return language.getLocation(entry.getParentName(), entry.getName());
    }

}
