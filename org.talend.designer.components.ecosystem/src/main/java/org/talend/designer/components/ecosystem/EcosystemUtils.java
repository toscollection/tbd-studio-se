// ============================================================================
//
// Copyright (C) 2006-2009 Talend Inc. - www.talend.com
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
import java.io.StringReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.map.JavaTypeMapper;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jface.action.ActionContributionItem;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.osgi.framework.Version;
import org.talend.commons.emf.EmfHelper;
import org.talend.commons.exception.ExceptionHandler;
import org.talend.commons.utils.StringUtils;
import org.talend.commons.utils.io.FilesUtils;
import org.talend.core.CorePlugin;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.core.model.components.ComponentUtilities;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Revision;
import org.talend.designer.components.ecosystem.model.RevisionInfo;
import org.talend.designer.components.ecosystem.model.VersionInfo;
import org.talend.designer.components.ecosystem.proxy.EcosystemSocketFactory;
import org.talend.designer.components.ecosystem.ui.views.EcosystemView;
import org.talend.repository.model.ComponentsFactoryProvider;

/**
 * Utility methods for ecosystem.
 */
public class EcosystemUtils {

    public static String VERSION_LIST_URL = "http://talendforge.org/ext/api/get_version_list.php";

    public static String REVISION_LIST_URL = "http://talendforge.org/exchange/tos/api/get_revision_list.php";

    private static Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(\\.(RC|M)\\d+)?_r\\d+");

    private static Pattern DEFAULT_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.*(\\d*)");

    private static MultiValueMap versionMap = new MultiValueMap();

    static {
        // System.setProperty("org.apache.axis.components.net.SocketFactory",
        // EcosystemSocketFactory.class.getName());
        System.setProperty("axis.socketFactory", EcosystemSocketFactory.class.getName());
        // AxisProperties.setProperty("axis.socketFactory",
        // EcosystemSocketFactory.class.getName());
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

    // /**
    // * DOC chuang Comment method "getRevisionList".
    // *
    // * @param version
    // * @param language
    // * @return
    // * @throws RemoteException
    // */
    // public static org.talend.designer.components.ecosystem.ws.Revision[]
    // getRevisionList(
    // String version, int language) throws RemoteException {
    //		
    // GetRevisionListPortTypeProxy test = new GetRevisionListPortTypeProxy();
    // org.talend.designer.components.ecosystem.ws.Revision[] revisions = test
    // .get_revision_list(version, language);
    // return revisions;
    // }

    public static List<RevisionInfo> getRevisionList(String version, int language) throws Exception {
        StringBuffer url = new StringBuffer();
        url.append(REVISION_LIST_URL).append("?categories=").append(language).append(",6").append("&version=");
        String[] branch = getBranch(version);
        url.append(StringUtils.join(branch, ","));
        String jsonContent = sendGetRequest(url.toString());
        return parseJsonObject(jsonContent, RevisionInfo.class);

    }

    @SuppressWarnings("unchecked")
    public static String[] getBranch(String version) {
        version = getMainVersion(version);
        Collection branch = versionMap.getCollection(version);
        return (String[]) branch.toArray(new String[branch.size()]);
    }

    public static String getMainVersion(String version) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+).*");
        Matcher matcher = pattern.matcher(version);
        if (matcher.matches()) {
            version = matcher.group(1);
        }
        return version;
    }

    /**
     * 
     * Get a tos version list from ecosystem.
     * 
     * @return
     */
    public static String[] getVersionList() {
        versionMap.clear();
        try {
            String jsonContent = sendGetRequest(VERSION_LIST_URL);
            List<VersionInfo> list = parseJsonObject(jsonContent, VersionInfo.class);

            Pattern pattern = Pattern.compile("(\\d+\\.\\d+).*");
            for (VersionInfo info : list) {
                String name = info.getName();
                Matcher matcher = pattern.matcher(name);
                if (matcher.matches()) {
                    versionMap.put(matcher.group(1), String.valueOf(info.getId()));
                }
            }
            // sort version
            List<String> versions = new ArrayList<String>(versionMap.keySet());
            Collections.sort(versions, new Comparator<String>() {

                public int compare(String o1, String o2) {
                    Version ver1 = new Version(o1);
                    Version ver2 = new Version(o2);
                    return ver2.compareTo(ver1);
                }

            });
            return versions.toArray(new String[versions.size()]);
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }
        return new String[0];
    }

    public static List parseJsonObject(String jsonContent, Class clazz) throws Exception {
        // need factory for creating parser to use
        JsonFactory jf = new JsonFactory();
        List result = (List) new JavaTypeMapper().read(jf.createJsonParser(new StringReader(jsonContent)));
        List objList = new ArrayList(result.size());
        for (int i = 0; i < result.size(); i++) {
            Object obj = clazz.newInstance();
            Object source = result.get(i);
            BeanUtils.copyProperties(obj, source);
            objList.add(obj);
        }
        return objList;
    }

    public static String sendGetRequest(String urlAddress) throws Exception {
        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod(urlAddress);
        httpclient.executeMethod(getMethod);
        String response = getMethod.getResponseBodyAsString();
        getMethod.releaseConnection();
        return response;
    }

    public static String sendPostRequest(String urlAddress, Map<String, String> parameters) throws Exception {
        HttpClient httpclient = new HttpClient();
        PostMethod postMethod = new PostMethod(urlAddress);
        if (parameters != null) {
            NameValuePair[] postData = new NameValuePair[parameters.size()];
            int i = 0;
            for (String key : parameters.keySet()) {
                String value = parameters.get(key);
                postData[i++] = new NameValuePair(key, value);
            }
            postMethod.addParameters(postData);
        }

        httpclient.executeMethod(postMethod);
        String response = postMethod.getResponseBodyAsString();
        postMethod.releaseConnection();
        return response;
    }

    // /**
    // * DOC chuang Comment method "setHttpProxy".
    // */
    // public static void setHttpProxy() {
    // String flag = System.getProperty("proxySet");

    // AxisProperties.setProperty("http.proxyHost",
    // System.getProperty("http.proxyHost"));
    // AxisProperties.setProperty("http.proxyPort",
    // System.getProperty("http.proxyPort"));
    // AxisProperties.setProperty("http.proxyUser",
    // System.getProperty("http.proxyUser"));
    // AxisProperties.setProperty("http.proxyPassword",
    // System.getProperty("http.proxyPassword"));
    // AxisProperties.setProperty("http.nonProxyHosts",
    // StringUtils.trimToEmpty(System.getProperty("http.nonProxyHosts")));

    // String[] keys = { "http.proxyHost", "http.proxyPort", "http.proxyUser",
    // "http.proxyPassword",
    // "http.nonProxyHosts" };
    // for (String key : keys) {
    // AxisProperties.setProperty(key,
    // StringUtils.trimToEmpty(System.getProperty(key)));
    // }

    // }

    /**
     * 
     * Get tos version filter from preference page.
     * 
     * @return
     */
    public static String getTosVersionFilter() {
        return EcosystemPlugin.getDefault().getPreferenceStore().getString(EcosystemView.TOS_VERSION_FILTER);
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
    // IPreferenceStore store =
    // EcosystemPlugin.getDefault().getPreferenceStore();
    // String path =
    // store.getString(EcosystemPreferencePage.ECOSYSTEM_COMPONENTS_FOLDER);
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
        componentsFactory.reset();
        // update the palette view, the position of the new component is
        // determined by the FAMILY value in the
        // component's property file
        ComponentUtilities.updatePalette();
    }

    public static void deleteComponent(ComponentExtension component) {
        File installFolder = new File(component.getInstalledLocation());
        if (installFolder != null) {
            FilesUtils.removeFolder(installFolder, true);
        }

        reloadComponents();
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
        // assumes that the format is something like X.X.X, where X are all
        // digit
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

    public static EcosystemView getEcosystemView() {
        IViewPart part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().findView(EcosystemView.VIEW_ID);
        if (part == null) {
            try {
                part = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(EcosystemView.VIEW_ID);
            } catch (Exception e) {
                ExceptionHandler.process(e);
            }
        }
        return (EcosystemView) part;
    }

    public static IAction findViewAction(String id) {
        IAction action = null;
        EcosystemView view = EcosystemUtils.getEcosystemView();
        // IContributionItem[] items = view.getViewSite().getActionBars()
        // .getToolBarManager().getItems();
        // for (IContributionItem item : items) {
        //		
        // if (item.getId().equals(RefreshComponenentsAction.ID)
        // && item instanceof PluginActionContributionItem) {
        // action = ((ActionContributionItem) item).getAction();
        // break;
        // }
        // }
        action = ((ActionContributionItem) view.getViewSite().getActionBars().getToolBarManager().find(id)).getAction();
        return action;
    }
}
