// ============================================================================
//
// Copyright (C) 2006-2010 Talend Inc. - www.talend.com
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

import org.apache.axis.components.net.BooleanHolder;
import org.apache.axis.components.net.TransportClientProperties;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.map.MultiValueMap;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.UsernamePasswordCredentials;
import org.apache.commons.httpclient.auth.AuthScope;
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
import org.talend.designer.components.ecosystem.proxy.TransportClientPropertiesFactory;
import org.talend.designer.components.ecosystem.ui.views.EcosystemView;
import org.talend.repository.model.ComponentsFactoryProvider;

/**
 * Utility methods for ecosystem.
 */
public class EcosystemUtils {

    public static String VERSION_LIST_URL = "http://talendforge.org/exchange/tos/api/get_version_list.php"; //$NON-NLS-1$

    public static String REVISION_LIST_URL = "http://talendforge.org/exchange/tos/api/get_revision_list.php"; //$NON-NLS-1$

    private static Pattern VERSION_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.(\\d+)(\\.(RC|M)\\d+)?_r\\d+"); //$NON-NLS-1$

    private static Pattern DEFAULT_PATTERN = Pattern.compile("(\\d+)\\.(\\d+)\\.*(\\d*)"); //$NON-NLS-1$

    private static MultiValueMap versionMap = new MultiValueMap();

    static {
        System.setProperty("axis.socketFactory", EcosystemSocketFactory.class.getName()); //$NON-NLS-1$ 
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
            String str = version.substring(0, version.indexOf("_r")); //$NON-NLS-1$
            return str.replaceAll("\\.RC", "RC").replaceAll("\\.M", "M"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$
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

    public static List<RevisionInfo> getRevisionList(String version, int language) throws Exception {
        StringBuffer url = new StringBuffer();
        url.append(REVISION_LIST_URL).append("?categories=").append(language).append(",6").append("&version="); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String[] branch = getBranch(version);
        url.append(StringUtils.join(branch, ",")); //$NON-NLS-1$
        String jsonContent = sendGetRequest(url.toString());
        return parseJsonObject(jsonContent, RevisionInfo.class);

    }

    public static List<RevisionInfo> getRevisionList(String version, int language, String type) throws Exception {
        StringBuffer url = new StringBuffer();
        url.append(REVISION_LIST_URL).append("?categories=").append(language).append(",").append(type).append("&version="); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
        String[] branch = getBranch(version);
        url.append(StringUtils.join(branch, ",")); //$NON-NLS-1$
        String jsonContent = sendGetRequest(url.toString());
        return parseJsonObject(jsonContent, RevisionInfo.class);

    }

    @SuppressWarnings("unchecked")
    public static String[] getBranch(String version) {
        version = getMainVersion(version);
        if (versionMap.getCollection(version) == null) {
            getVersionList();

        }
        Collection branch = versionMap.getCollection(version);
        if (branch != null) {
            return (String[]) branch.toArray(new String[branch.size()]);
        } else {
            return new String[0];
        }
    }

    public static String getMainVersion(String version) {
        Pattern pattern = Pattern.compile("(\\d+\\.\\d+).*"); //$NON-NLS-1$
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

            Pattern pattern = Pattern.compile("(\\d+\\.\\d+).*"); //$NON-NLS-1$
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
        List objList = new ArrayList();

        // for 4.1.0 the is no json param on server ,so jsonContent is "wrong parameters for version"
        if (!jsonContent.startsWith("[")) {
            return objList;
        }
        JsonFactory jf = new JsonFactory();
        List result = (List) new JavaTypeMapper().read(jf.createJsonParser(new StringReader(jsonContent)));
        for (int i = 0; i < result.size(); i++) {
            Object obj = clazz.newInstance();
            Object source = result.get(i);
            BeanUtils.copyProperties(obj, source);
            objList.add(obj);
        }
        return objList;
    }

    public static String sendGetRequest(String urlAddress) throws Exception {

        String property = System.getProperty("axis.socketFactory");//$NON-NLS-1$
        Class<?> forName = Class.forName(property);
        Object newInstance = forName.newInstance();

        EcosystemSocketFactory ecosystemSocketFactory = (EcosystemSocketFactory) newInstance;
        StringBuffer stringBuffer = new StringBuffer();
        BooleanHolder booleanHolder = new BooleanHolder(true);

        ecosystemSocketFactory.create("", -1, stringBuffer, booleanHolder);//$NON-NLS-1$

        HttpClient httpclient = new HttpClient();
        GetMethod getMethod = new GetMethod(urlAddress);
        TransportClientProperties tcp = TransportClientPropertiesFactory.create("http");
        if (tcp.getProxyHost().length() != 0) {
            UsernamePasswordCredentials creds = new UsernamePasswordCredentials(tcp.getProxyUser() != null ? tcp.getProxyUser()
                    : "", tcp.getProxyPassword() != null ? tcp.getProxyUser() : "");
            httpclient.getState().setProxyCredentials(AuthScope.ANY, creds);
            HostConfiguration hcf = new HostConfiguration();
            hcf.setProxy(tcp.getProxyHost(), Integer.parseInt(tcp.getProxyPort()));
            httpclient.executeMethod(hcf, getMethod);
        } else {
            httpclient.executeMethod(getMethod);
        }
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

    /**
     * Get the folder that will store downloaded component.
     * 
     * @return
     */
    public static File getComponentFolder() {
        URL url = FileLocator.find(EcosystemPlugin.getDefault().getBundle(), new Path("component"), null); //$NON-NLS-1$
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
        String[] rev1 = revision1.getName().split("\\."); //$NON-NLS-1$
        String[] rev2 = revision2.getName().split("\\."); //$NON-NLS-1$
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
