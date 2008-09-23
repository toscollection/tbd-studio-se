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

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.talend.designer.components.ecosystem.EcosystemConstants;
import org.talend.designer.components.ecosystem.EcosystemPlugin;
import org.talend.designer.components.ecosystem.EcosystemUtils;

/**
 * DOC hcw class global comment. Detailled comment
 * 
 * Use the tos version filter within the Ecosystem view directly.
 * 
 * @deprecated
 */
public class EcosystemPreferencePage extends FieldEditorPreferencePage implements IWorkbenchPreferencePage {

    // private static final String FOLDER_FIELD_LABEL =
    // Messages.getString("EcosystemPreferencePage.DirectoryFieldLabel"); //$NON-NLS-1$

    // public static final String ECOSYSTEM_COMPONENTS_FOLDER = "ECOSYSTEM_COMPONENTS_FOLDER"; //$NON-NLS-1$

    public static final String TOS_VERSION_FILTER = "TOS_VERSION_FILTER"; //$NON-NLS-1$

    public static final String ID = "org.talend.designer.components.ecosystem.ui.views.ecosystem.page"; //$NON-NLS-1$

    /**
     * EcosystemPreferencePage constructor.
     */
    public EcosystemPreferencePage() {
        super(GRID);
        setPreferenceStore(EcosystemPlugin.getDefault().getPreferenceStore());
    }

    @Override
    public void createFieldEditors() {
        // get a tos version list from web service
        String[] availableVersionFilter = EcosystemUtils.getVersionList();
        ComboFieldEditor versionFilter = new ComboFieldEditor(TOS_VERSION_FILTER, EcosystemConstants.VERSION_FILTER_LABEL,
                convert(availableVersionFilter), getFieldEditorParent());
        addField(versionFilter);

        // StringFieldEditor versionFilter = new StringFieldEditor(TOS_VERSION_FILTER, VERSION_FILTER_LABEL,
        // getFieldEditorParent());
        // addField(versionFilter);

        // DirectoryFieldEditor filePathTemp = new DirectoryFieldEditor(ECOSYSTEM_COMPONENTS_FOLDER, FOLDER_FIELD_LABEL,
        // getFieldEditorParent());
        // addField(filePathTemp);

    }

    private String[][] convert(String[] values) {
        String[][] namesAndValues = null;
        if (values != null) {
            namesAndValues = new String[values.length][2];

            for (int i = 0; i < values.length; i++) {
                namesAndValues[i][0] = values[i];
                namesAndValues[i][1] = values[i];
            }
        }
        return namesAndValues;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.ui.IWorkbenchPreferencePage#init(org.eclipse.ui.IWorkbench)
     */
    public void init(IWorkbench workbench) {
    }

}
