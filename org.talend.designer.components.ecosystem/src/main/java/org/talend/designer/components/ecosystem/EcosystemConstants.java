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
package org.talend.designer.components.ecosystem;

import org.talend.designer.components.ecosystem.i18n.Messages;

/**
 * DOC hcw class global comment. Detailled comment
 */
public class EcosystemConstants {

    public static final String STATUS_NOT_INSTALLED = Messages.getString("EcosystemViewComposite.Status.NotInstalled"); //$NON-NLS-1$

    public static final String STATUS_INSTALLED = Messages.getString("EcosystemViewComposite.Status.Installed"); //$NON-NLS-1$

    public static final String STATUS_DEPRECATED = Messages.getString("EcosystemViewComposite.Status.Deprecated"); //$NON-NLS-1$

    public static final String REVISION_TITLE = Messages.getString("EcosystemViewComposite.Revision.Title"); //$NON-NLS-1$

    public static final String STATUS_TITLE = Messages.getString("EcosystemViewComposite.Status.Title"); //$NON-NLS-1$

    public static final String COMPONENT_NAME_TITLE = Messages.getString("EcosystemViewComposite.ComponentName.Title"); //$NON-NLS-1$

    public static final String AUTHOR_TITLE = Messages.getString("EcosystemViewComposite.Author.Title"); //$NON-NLS-1$

    public static final String RELEASED_DATE_TITLE = Messages.getString("EcosystemViewComposite.ReleasedDate.Title"); //$NON-NLS-1$

    public static final String DESCRIPTION_TITLE = Messages.getString("EcosystemViewComposite.Description.Title"); //$NON-NLS-1$    

    public static final String FILTER_LABEL_TEXT = Messages.getString("EcosystemView.FilterLabelText"); //$NON-NLS-1$

    public static final String FILTER_LINK_TEXT = Messages.getString("EcosystemView.FilterLinkText"); //$NON-NLS-1$

    public static final String FILTER_DIALOG_TITLE = Messages.getString("FilterDialog.Title"); //$NON-NLS-1$ 

    public static final String VERSION_FILTER_LABEL = Messages.getString("EcosystemPreferencePage.VersionFilterLabel"); //$NON-NLS-1$

    public static final String DOWNLOAD_TASK_NAME = Messages.getString("DownloadComponenentsAction.DownloadTaskName"); //$NON-NLS-1$

    public static final String DOWNLOAD_TASK_TITLE = Messages.getString("DownloadComponenentsAction.DownloadTaskTitle"); //$NON-NLS-1$

    public static final String RELOAD_PALETTE = Messages.getString("DownloadComponenentsAction.ReloadPalette"); //$NON-NLS-1$

    public static final String FIND_EXTENSIONS_MSG = "RefreshJob.FindExtensions.Message"; //$NON-NLS-1$

    public static final String FIND_EXTENSIONS_TITLE = Messages.getString("RefreshJob.FindExtensions.Title"); //$NON-NLS-1$

    public static final int KEY_ENTER = 13;

    /**
     * File that store information about the components we have installed.
     */
    public static final String COMPONENT_MODEL_FILE = "component_extensions.xmi"; //$NON-NLS-1$

}
