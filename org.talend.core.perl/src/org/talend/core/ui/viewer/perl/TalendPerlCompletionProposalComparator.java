// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.ui.viewer.perl;

import org.epic.perleditor.templates.perl.PerlCompletionProposalComparator;
import org.talend.core.ui.viewer.proposal.TalendCompletionProposal;

/**
 * DOC nrousseau class global comment. Detailled comment <br/>
 * 
 */
public class TalendPerlCompletionProposalComparator extends PerlCompletionProposalComparator {

    /*
     * (non-Javadoc)
     * 
     * @see org.epic.perleditor.templates.perl.PerlCompletionProposalComparator#compare(java.lang.Object,
     * java.lang.Object)
     */
    @Override
    public int compare(Object o1, Object o2) {
        if (o1 instanceof TalendCompletionProposal) {
            return 1;
        }
        return super.compare(o1, o2);
    }

}
