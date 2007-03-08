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
package org.talend.designer.dbmap.ui.tabs;

import org.eclipse.gef.commands.CommandStack;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.talend.commons.ui.swt.colorstyledtext.ColorManager;
import org.talend.commons.ui.swt.colorstyledtext.MapperColorStyledText;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.ui.metadata.editor.MetadataTableEditorView;
import org.talend.designer.dbmap.MapperMain;
import org.talend.designer.dbmap.i18n.Messages;
import org.talend.designer.dbmap.managers.MapperManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TabFolderEditors.java 2053 2007-02-13 15:41:54Z amaumont $
 * 
 */
public class TabFolderEditors extends CTabFolder {

    private TabFolderEditors tabFolderEditors;

    private MapperManager mapperManager;

    protected int lastSelectedTab;

    private MetadataTableEditorView inputMetaEditor;

    private MetadataTableEditorView outputMetaEditor;

    public static final int INDEX_TAB_METADATA_EDITOR = 0;

    public static final int INDEX_TAB_EXPRESSION_EDITOR = 1;

    private StyledTextHandler styledTextHandler;

    private StyledText styledSqlText;

    public TabFolderEditors(Composite parent, int style, MapperManager mapperManager) {
        super(parent, style);
        tabFolderEditors = this;
        this.mapperManager = mapperManager;
        createComponents();
    }

    /**
     * DOC amaumont Comment method "createComponents".
     */
    private void createComponents() {

        setSimple(false);
        // TableEditorCompositeBase metaDatasDescriptorView3 = new TableEditorCompositeBase(tabFolder1);
        // item.setControl(metaDatasDescriptorView3);

        createMetadataEditorTab();

        createExpressionEditorTab();

        createSqlViewerTab();
        
        tabFolderEditors.addListener(SWT.Selection, new Listener() {

            public void handleEvent(Event event) {
                lastSelectedTab = tabFolderEditors.getSelectionIndex();
            }
        });
        tabFolderEditors.setSelection(0);
    }

    /**
     * DOC amaumont Comment method "createSqlViewerTab".
     */
    private void createSqlViewerTab() {
        CTabItem item;
        item = new CTabItem(tabFolderEditors, SWT.BORDER);
        item.setText(Messages.getString("TabFolderEditors.SqlSelectQuery")); //$NON-NLS-1$
        styledSqlText = createStyledText(item);
        styledSqlText.setWordWrap(true);
        styledSqlText.setEditable(false);
        styledSqlText.setEnabled(true);
    }

    /**
     * DOC amaumont Comment method "createExpressionEditorTab".
     */
    private void createExpressionEditorTab() {
        CTabItem item;
        item = new CTabItem(tabFolderEditors, SWT.BORDER);
        item.setText(Messages.getString("TabFolderEditors.expressionEditor")); //$NON-NLS-1$
        StyledText styledText = createStyledText(item);
        this.styledTextHandler = new StyledTextHandler(styledText, mapperManager);
    }

    /**
     * DOC amaumont Comment method "createMetadataEditor".
     */
    private void createMetadataEditorTab() {
        CTabItem item = new CTabItem(tabFolderEditors, SWT.BORDER);
        item.setText(Messages.getString("TabFolderEditors.schemaEditor")); //$NON-NLS-1$

        SashForm inOutMetaEditorContainer = new SashForm(tabFolderEditors, SWT.SMOOTH | SWT.HORIZONTAL | SWT.SHADOW_OUT);
        inOutMetaEditorContainer.setLayout(new RowLayout(SWT.HORIZONTAL));
        item.setControl(inOutMetaEditorContainer);

        CommandStack commandStack = mapperManager.getCommandStack();

        inputMetaEditor = new MetadataTableEditorView(inOutMetaEditorContainer, SWT.BORDER);
        inputMetaEditor.initGraphicComponents();
        inputMetaEditor.getExtendedTableViewer().setCommandStack(commandStack);

        outputMetaEditor = new MetadataTableEditorView(inOutMetaEditorContainer, SWT.BORDER);
        outputMetaEditor.initGraphicComponents();
        outputMetaEditor.getExtendedTableViewer().setCommandStack(commandStack);
    }

    private StyledText createStyledText(CTabItem item) {
        StyledText styledText = null;
        if (MapperMain.isStandAloneMode()) {
            styledText = new StyledText(tabFolderEditors, SWT.V_SCROLL | SWT.H_SCROLL);
        } else {
            IPreferenceStore preferenceStore = CorePlugin.getDefault().getPreferenceStore();
            ColorManager colorManager = new ColorManager(preferenceStore);
            styledText = new MapperColorStyledText(tabFolderEditors, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL, colorManager, "tsql");
        }
        styledText.setEnabled(false);
        item.setControl(styledText);
        return styledText;
    }

    public MetadataTableEditorView getInputMetaEditor() {
        return this.inputMetaEditor;
    }

    public MetadataTableEditorView getOutputMetaEditor() {
        return this.outputMetaEditor;
    }

    public StyledTextHandler getStyledTextHandler() {
        return this.styledTextHandler;
    }

    
    /**
     * Getter for styledSqlText.
     * @return the styledSqlText
     */
    public StyledText getStyledSqlText() {
        return this.styledSqlText;
    }

    
    
}
