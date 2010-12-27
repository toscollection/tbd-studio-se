// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.help.perl.ui;

import org.talend.help.perl.model.EProperty;
import org.talend.help.perl.model.Node;

/**
 * TreeLabelProvider.java.
 * 
 */
public class TreeLabelProvider extends org.eclipse.jface.viewers.LabelProvider {

    @Override
    public String getText(Object element) {
        String text = ((Node) element).getProperties().get(EProperty.LABEL);
        return text == null ? "" : text; //$NON-NLS-1$
    }

}
