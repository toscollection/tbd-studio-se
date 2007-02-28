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

import org.eclipse.jface.fieldassist.IContentProposalListener;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.swt.widgets.Control;
import org.talend.commons.ui.swt.proposal.ContentProposalAdapterExtended;
import org.talend.commons.ui.swt.proposal.ProposalUtils;
import org.talend.core.ui.proposal.ProcessProposalProvider;
import org.talend.designer.dbmap.MapperMain;
import org.talend.designer.dbmap.managers.MapperManager;
import org.talend.designer.dbmap.model.table.AbstractDataMapTable;
import org.talend.designer.dbmap.model.tableentry.ITableEntry;
import org.talend.designer.dbmap.ui.visualmap.zone.Zone;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: ExpressionProposal.java 968 2006-12-12 10:59:26Z amaumont $
 * 
 */
public class ExpressionProposal {

    private ExpressionProposalProvider proposalProvider;

    private ContentProposalAdapterExtended contentProposalAdapter;

    public ExpressionProposal(MapperManager mapperManager, Control control) {
        super();
        IContentProposalProvider[] contentProposalProviders = new IContentProposalProvider[0];
        if (!MapperMain.isStandAloneMode()) {
            contentProposalProviders = new IContentProposalProvider[] { new ProcessProposalProvider(mapperManager.getComponent()
                    .getProcess()) };
        }
        proposalProvider = new ExpressionProposalProvider(mapperManager, contentProposalProviders);
        contentProposalAdapter = ProposalUtils.getCommonProposal(control, proposalProvider);
    }

    public void init(AbstractDataMapTable currentTable, Zone[] zones, ITableEntry currentEntry) {
        proposalProvider.init(currentTable, zones, currentEntry);
    }

    public ContentProposalAdapterExtended getContentProposalAdapter() {
        return this.contentProposalAdapter;
    }

    public boolean isProposalOpened() {
        if (this.contentProposalAdapter != null) {
            return this.contentProposalAdapter.isProposalOpened();
        }
        return false;
    }

    public void addContentProposalListener(IContentProposalListener listener) {
        this.contentProposalAdapter.addContentProposalListener(listener);
    }

    public ExpressionProposalProvider getProposalProvider() {
        return this.proposalProvider;
    }

}
