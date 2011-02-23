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
package org.talend.librariesmanager.perl.ui.actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.commons.ui.runtime.exception.MessageBoxExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.model.general.ModuleNeeded;
import org.talend.core.model.general.ModuleNeeded.ELibraryInstallStatus;
import org.talend.core.prefs.ITalendCorePrefConstants;
import org.talend.librariesmanager.perl.i18n.Messages;
import org.talend.librariesmanager.ui.views.ModulesViewComposite;

/**
 * DOC qwei class global comment. Detailled comment <br/>
 * 
 */
public class InstallPerlModulesAction extends Action {

    public InstallPerlModulesAction() {
        super();
        setText(Messages.getString("InstallPerlModulesAction.InstallBtn.Text")); //$NON-NLS-1$
        setToolTipText(Messages.getString("InstallPerlModulesAction.InstallBtn.Text")); //$NON-NLS-1$
        setImageDescriptor(PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_TOOL_NEW_WIZARD));
        init();
    }

    List<ModuleNeeded> modules;

    public void init() {
        modules = new ArrayList<ModuleNeeded>();
        if (ModulesViewComposite.getTableViewerCreator() != null) {
            ModulesViewComposite.getTableViewerCreator().getTable().addSelectionListener(new SelectionListener() {

                public void widgetDefaultSelected(SelectionEvent e) {

                }

                public void widgetSelected(SelectionEvent e) {
                    modules.clear();
                    TableItem[] selection = ModulesViewComposite.getTableViewerCreator().getTable().getSelection();
                    for (TableItem tableItem : selection) {
                        ModuleNeeded needed = (ModuleNeeded) tableItem.getData();
                        if (!(needed.getStatus() == ELibraryInstallStatus.NOT_INSTALLED)) {
                            setEnabled(false);
                            return;
                        } else {
                            modules.add(needed);
                        }
                    }
                    setEnabled(true);
                }
            });
            setEnabled(false);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.action.Action#run()
     */
    @Override
    // public void run() {
    // String osName = "";
    // if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0) {
    // osName = "WINDOWS";
    // } else {
    // osName = "LINUX";
    // }
    // try {
    // for (ModuleNeeded module : modules) {
    // String modulename = module.getModuleName();
    // // process = runTime.exec("cmd /c start /D" + path + "\\" + " ppm install " + modulename);
    // // process = runTime.exec("cmd /c start /D" + path + "\\" + " ppm install " + modulename);
    // List<InstallModule> InstallModuleAll = module.getInstallModule();
    // if (InstallModuleAll != null) {
    // if (InstallModuleAll.size() != 0) {
    // for (InstallModule installModule : InstallModuleAll) {
    // execSpecialInstall(osName, installModule);
    // }
    //
    // } else {
    // execNormalInstall(modulename);
    // }
    //
    // }
    //
    // }
    // setEnabled(false);
    // } catch (Exception e) {
    // ExceptionHandler.process(e);
    // }
    //
    // }
    public void run() {
        try {
            for (ModuleNeeded module : modules) {
                String modulename = module.getModuleName();
                // process = runTime.exec("cmd /c start /D" + path + "\\" + " ppm install " + modulename);
                // process = runTime.exec("cmd /c start /D" + path + "\\" + " ppm install " + modulename);
                List<String> InstallModuleAll = module.getInstallURL();
                if (InstallModuleAll != null) {
                    if (InstallModuleAll.size() != 0 && System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0) { //$NON-NLS-1$ //$NON-NLS-2$
                        for (String installModule : InstallModuleAll) {
                            execSpecialInstall(installModule);
                        }

                    } else {
                        execNormalInstall(modulename);
                    }
                }

            }
            setEnabled(false);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
    }

    public void execSpecialInstall(String installModule) {
        Runtime runTime = Runtime.getRuntime();
        Process process = null;
        try {
            if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0) { //$NON-NLS-1$ //$NON-NLS-2$
                String path = CorePlugin.getDefault().getPreferenceStore().getString(ITalendCorePrefConstants.PERL_INTERPRETER);
                path = path.substring(0, path.lastIndexOf("\\")); //$NON-NLS-1$
                process = runTime.exec("cmd /c start /D" + path + "\\" + " ppm install " + installModule); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            }
            // else if (System.getProperty("os.name").toUpperCase().indexOf("LINUX") >= 0) {
            // String command = "perl -MCPAN -e " + "'install " + installModule + "'\n";
            // openTerminal(command, process);
            // }
        } catch (Exception e) {

            ExceptionHandler.process(e);
        }
    }

    public void execNormalInstall(String modulename) {
        Runtime runTime = Runtime.getRuntime();
        Process process = null;
        try {
            if (System.getProperty("os.name").toUpperCase().indexOf("WINDOWS") >= 0) { //$NON-NLS-1$ //$NON-NLS-2$
                modulename = modulename.replaceAll("::", "-"); //$NON-NLS-1$ //$NON-NLS-2$
                String path = CorePlugin.getDefault().getPreferenceStore().getString(ITalendCorePrefConstants.PERL_INTERPRETER);
                path = path.substring(0, path.lastIndexOf("\\")); //$NON-NLS-1$
                process = runTime.exec("cmd /c start /D" + path + "\\" + " ppm install " + modulename); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
            } else if (System.getProperty("os.name").toUpperCase().indexOf("LINUX") >= 0) { //$NON-NLS-1$ //$NON-NLS-2$
                String command = "perl -MCPAN -e 'install " + modulename + "'"; //$NON-NLS-1$ //$NON-NLS-2$
                openTerminal(command, process);
            }
        } catch (Exception e) {
            MessageBoxExceptionHandler.process(e);
        }
    }

    public static void openTerminal(String command, Process process) {
        String terminal = System.getenv("TERM"); //$NON-NLS-1$
        try {
            process = Runtime.getRuntime().exec(new String[] { terminal, "-e", command + "; $SHELL" }); //$NON-NLS-1$ //$NON-NLS-2$
            process.waitFor();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InterruptedException e) {
            // TODO: handle exception
        }
    }
}
