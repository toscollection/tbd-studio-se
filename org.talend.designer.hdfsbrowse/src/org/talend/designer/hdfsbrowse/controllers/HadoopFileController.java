// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.hdfsbrowse.controllers;

import org.eclipse.gef.commands.Command;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Text;
import org.talend.core.properties.tab.IDynamicProperty;
import org.talend.core.utils.TalendQuoteUtils;
import org.talend.designer.core.ui.editor.cmd.PropertyChangeCommand;
import org.talend.designer.hdfsbrowse.model.IHDFSNode;

/**
 * DOC ycbai class global comment. Detailled comment
 */
public class HadoopFileController extends AbstractHDFSBrowseController {

    private static final String FILE_HADOOP = "FILE_HADOOP"; //$NON-NLS-1$

    public HadoopFileController(IDynamicProperty dp) {
        super(dp);
    }

    public Command createCommand(SelectionEvent event) {
        HDFSBrowseDialog dial = new HDFSBrowseDialog(composite.getShell(), getHDFSConnectionBean());
        Button btn = (Button) event.getSource();
        String propertyName = (String) btn.getData(PARAMETER_NAME);
        Text filePathText = (Text) hashCurControls.get(propertyName);
        if (dial.open() == Window.OK) {
            IHDFSNode result = dial.getResult();
            String path = result.getRelativePath();
            path = TalendQuoteUtils.addQuotesIfNotExist(path);
            if (!elem.getPropertyValue(propertyName).equals(path)) {
                filePathText.setText(path);
                return new PropertyChangeCommand(elem, propertyName, path);
            }

        }
        return null;
    }

    @Override
    protected String getControllerName() {
        return FILE_HADOOP;
    }

}
