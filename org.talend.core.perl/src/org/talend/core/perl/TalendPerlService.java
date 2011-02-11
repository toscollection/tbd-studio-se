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
package org.talend.core.perl;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.widgets.Composite;
import org.epic.core.preferences.PerlMainPreferencePage;
import org.epic.perleditor.PerlEditorPlugin;
import org.talend.core.model.process.INode;
import org.talend.core.service.ICorePerlService;
import org.talend.core.ui.viewer.perl.TalendPerlSourceViewer;

/**
 * DOC guanglong.du class global comment. Detailled comment
 */
public class TalendPerlService implements ICorePerlService {

    public void setExecutablePreference(String perlInterpreter) {
        PerlEditorPlugin.getDefault().setExecutablePreference("\"" + perlInterpreter + "\""); //$NON-NLS-1$ //$NON-NLS-2$
        PerlMainPreferencePage.refreshExecutableTextValue("\"" + perlInterpreter + "\""); //$NON-NLS-1$ //$NON-NLS-2$
    }

    public ISourceViewer createViewer(Composite composite, int styles, boolean checkCode) {
        ISourceViewer viewer = (TalendPerlSourceViewer) TalendPerlSourceViewer.createViewer(composite, styles, true);
        return viewer;
    }

    public ISourceViewer createViewer(Composite composite, int styles, boolean checkCode, final INode node) {
        ISourceViewer viewer = (TalendPerlSourceViewer) TalendPerlSourceViewer
                .createViewer(composite, styles, true, (INode) node);
        return viewer;
    }

}
