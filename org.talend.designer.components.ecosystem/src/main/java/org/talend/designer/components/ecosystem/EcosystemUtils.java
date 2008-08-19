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

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.ArrayUtils;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.talend.commons.emf.EmfHelper;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.components.ComponentUtilities;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Revision;
import org.talend.designer.components.ecosystem.proxy.EcosystemSocketFactory;
import org.talend.designer.components.ecosystem.ui.views.EcosystemPreferencePage;
import org.talend.designer.components.ecosystem.ws.GetRevisionListPortTypeProxy;
import org.talend.repository.model.ComponentsFactoryProvider;

/**
 * Utility methods for ecosystem.
 */
public class EcosystemUtils {

    private static Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(\\.(RC|M)\\d+)?_r\\d+");

    private static Pattern DEFAULT_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.*(\\d*)");

    static {
        // System.setProperty("org.apache.axis.components.net.SocketFactory", EcosystemSocketFactory.class.getName());
        System.setProperty("axis.socketFactory", EcosystemSocketFactory.class.getName());
        // AxisProperties.setProperty("axis.socketFactory", EcosystemSocketFactory.class.getName());
    }

    /**
     * Make sure that the version match x.x.x or x.x.xMx or x.x.xRCx, where x are all digit.
     * 
     * @param version
     * @return
     */
    public static String normalizeVersion(String version) {
        Matcher matcher = VERSION_PATTERN.matcher(version);
        if (matcher.matches()) {
            String str = version.substring(0, version.indexOf("_r"));
            return str.replaceAll("\\.RC", "RC").replaceAll("\\.M", "M");
        } else {
            // try again, ignore M, RC
            matcher = DEFAULT_PATTERN.matcher(version);
            matcher.find();
            return matcher.group();
        }
    }

    /**
     * This method is used for generating current T.O.S version.
     * 
     * @return
     */
    public static String getCurrentTosVersion(boolean normalize) {
        String version = (String) CorePlugin.getDefault().getBundle().getHeaders().get(
                org.osgi.framework.Constants.BUNDLE_VERSION);
        if (normalize) {
            version = normalizeVersion(version);
        }
        return version;
    }

    /**
     * DOC chuang Comment method "getRevisionList".
     * 
     * @param version
     * @param language
     * @return
     * @throws RemoteException
     */
    public static org.talend.designer.components.ecosystem.ws.Revision[] getRevisionList(String version, int language)
            throws RemoteException {
        GetRevisionListPortTypeProxy test = new GetRevisionListPortTypeProxy();
        org.talend.designer.components.ecosystem.ws.Revision[] revisions = test.get_revision_list(version, language);
        return revisions;
    }

    /**
     * 
     * Get a tos version list from ecosystem.
     * 
     * @return
     */
    public static String[] getVersionList() {
        GetRevisionListPortTypeProxy proxy = new GetRevisionListPortTypeProxy();
        String[] versions = null;
        try {
            versions = proxy.getVersionList().split(",");
            ArrayUtils.reverse(versions);
        } catch (RemoteException e) {
            ExceptionHandler.process(e);
        }
        return versions;
    }

    // /**
    // * DOC chuang Comment method "setHttpProxy".
    // */
    // public static void setHttpProxy() {
    // String flag = System.getProperty("proxySet");

    // AxisProperties.setProperty("http.proxyHost", System.getProperty("http.proxyHost"));
    // AxisProperties.setProperty("http.proxyPort", System.getProperty("http.proxyPort"));
    // AxisProperties.setProperty("http.proxyUser", System.getProperty("http.proxyUser"));
    // AxisProperties.setProperty("http.proxyPassword", System.getProperty("http.proxyPassword"));
    // AxisProperties.setProperty("http.nonProxyHosts",
    // StringUtils.trimToEmpty(System.getProperty("http.nonProxyHosts")));

    // String[] keys = { "http.proxyHost", "http.proxyPort", "http.proxyUser", "http.proxyPassword",
    // "http.nonProxyHosts" };
    // for (String key : keys) {
    // AxisProperties.setProperty(key, StringUtils.trimToEmpty(System.getProperty(key)));
    // }

    // }

    /**
     * 
     * Get tos version filter from preference page.
     * 
     * @return
     */
    public static String getTosVersionFilter() {
        return EcosystemPlugin.getDefault().getPreferenceStore().getString(EcosystemPreferencePage.TOS_VERSION_FILTER);
    }

    public static ECodeLanguage getCurrentLanguage() {
        return LanguageManager.getCurrentLanguage();
    }

    // /**
    // * Get the folder that user have set in preference page.
    // *
    // * @return
    // */
    // public static File getUserComponentFolder() {
    // IPreferenceStore store = EcosystemPlugin.getDefault().getPreferenceStore();
    // String path = store.getString(EcosystemPreferencePage.ECOSYSTEM_COMPONENTS_FOLDER);
    // return StringUtils.isEmpty(path) ? null : new File(path);
    // }

    /**
     * Get the folder that will store downloaded component.
     * 
     * @return
     */
    public static File getComponentFolder() {
        URL url = FileLocator.find(EcosystemPlugin.getDefault().getBundle(), new Path("component"), null);
        try {
            URL fileUrl = FileLocator.toFileURL(url);
            return new File(fileUrl.getPath());
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return null;
    }

    public static void showComponentPreferencePage(Shell shell) {
        showPreferencePage(shell, EcosystemPreferencePage.ID);
    }

    /**
     * Displays the given preference page.
     * 
     * @param pageId the fully qualified id of the preference page, e.g.
     * <code>org.eclipse.jdt.debug.ui.preferences.VMPreferencePage</code>
     * 
     */
    public static void showPreferencePage(Shell shell, String pageId) {
        PreferencesUtil.createPreferenceDialogOn(shell, pageId, new String[] { pageId }, null).open();
    }

    /**
     * Reload all components from folder and update palette.
     */
    public static void reloadComponents() {
        // reload from folder, see ComponentsFactory and UserComponentsProvider
        IComponentsFactory componentsFactory = ComponentsFactoryProvider.getInstance();
        componentsFactory.init();
        // update the palette view, the position of the new component is determined by the FAMILY value in the
        // component's property file
        ComponentUtilities.updatePalette();
    }

    /**
     * Save the emf model of installed components to file.
     * 
     * @param fileName
     * @param components
     * @throws IOException
     */
    public static void saveInstallComponents(String fileName, List<ComponentExtension> components) throws IOException {
        File file = new File(getComponentFolder(), fileName);
        EmfHelper.saveEmfModel(EcosystemPackage.eINSTANCE, components, file.getAbsolutePath());
    }

    /**
     * Load the emf model of installed components from file.
     * 
     * @param fileName
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public static List<ComponentExtension> loadInstallComponents(String fileName) throws IOException {
        File file = new File(getComponentFolder(), fileName);
        return EmfHelper.loadEmfModel(EcosystemPackage.eINSTANCE, file.getAbsolutePath());
    }

    /**
     * Activate the user job.
     * 
     * @param job
     */
    public static void scheduleUserJob(Job job) {
        job.setUser(true);
        job.setPriority(Job.INTERACTIVE);
        job.schedule();
        job.wakeUp(); // start as soon as possible
    }

    /**
     * Return true if revision1 is newer than revision2.
     * 
     * @param revision1
     * @param revision2
     * @return
     */
    public static boolean isRevisionNewerThan(Revision revision1, Revision revision2) {
        // assumes that the format is something like X.X.X, where X are all digit
        String[] rev1 = revision1.getName().split("\\.");
        String[] rev2 = revision2.getName().split("\\.");
        for (int i = 0; i < rev1.length && i < rev2.length; i++) {
            int a = Integer.parseInt(rev1[i]);
            int b = Integer.parseInt(rev2[i]);
            if (a == b) {
                continue;
            } else {
                return a > b;
            }
        }
        // the two revision has different length, the longer one is newer
        return rev1.length > rev2.length;
    }

}
