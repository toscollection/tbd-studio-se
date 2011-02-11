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
package org.talend.designer.components.ecosystem.ui.views;

import org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer;
import org.eclipse.jface.preference.IPreferenceStore;
import org.talend.designer.components.ecosystem.EcosystemPlugin;
import org.talend.designer.components.ecosystem.EcosystemUtils;

/**
 * DOC hcw class global comment. Detailled comment
 */
public class PreferenceInitializer extends AbstractPreferenceInitializer {

    /**
     * DOC hcw PreferenceInitializer constructor comment.
     */
    public PreferenceInitializer() {
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.core.runtime.preferences.AbstractPreferenceInitializer#initializeDefaultPreferences()
     */
    @Override
    public void initializeDefaultPreferences() {
        IPreferenceStore store = EcosystemPlugin.getDefault().getPreferenceStore();
        store.setDefault(EcosystemView.TOS_VERSION_FILTER, EcosystemUtils.getCurrentTosVersion(true));
    }

}
