// ============================================================================
//
// Copyright (C) 2006-2007 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.designer.components.ecosystem.ui.views;

import org.eclipse.jface.preference.DirectoryFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.designer.components.ecosystem.EcosystemPlugin;
import org.talend.designer.components.ecosystem.i18n.Messages;

/**
 * DOC hcw class global comment. Detailled comment
 */
public class EcosystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    private static final String FOLDER_FIELD_LABEL = Messages.getString("EcosystemPreferencePage.DirectoryFieldLabel"); //$NON-NLS-1$

    public static final String ECOSYSTEM_COMPONENTS_FOLDER = "ECOSYSTEM_COMPONENTS_FOLDER"; //$NON-NLS-1$

    public static String ID = "org.talend.designer.components.ecosystem.ui.views.ecosystem.page"; //$NON-NLS-1$

    /**
     * EcosystemPreferencePage constructor.
     */
    public EcosystemPreferencePage() {
        super(GRID);
        setPreferenceStore(EcosystemPlugin.getDefault().getPreferenceStore());
    }

    @Override
    public void createFieldEditors() {
        DirectoryFieldEditor filePathTemp = new DirectoryFieldEditor(ECOSYSTEM_COMPONENTS_FOLDER, FOLDER_FIELD_LABEL,
                getFieldEditorParent());
        addField(filePathTemp);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

}
