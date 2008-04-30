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
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.PreferencesUtil;
import org.talend.core.CorePlugin;
import org.talend.core.context.Context;
import org.talend.core.context.RepositoryContext;
import org.talend.core.language.ECodeLanguage;
import org.talend.core.model.components.ComponentUtilities;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Revision;
import org.talend.designer.components.ecosystem.ui.views.EcosystemPreferencePage;
import org.talend.repository.model.ComponentsFactoryProvider;

/**
 * Utility methods for ecosystem.
 */
public class EcosystemUtils {

    // see ComponentsPreferencePage
    // public static final String USER_COMPONENTS_FOLDER = "USER_COMPONENTS_FOLDER"; //$NON-NLS-1$

    // public static final String ID_COMPONENT_PREFERENCE_PAGE = "org.talend.designer.components.localprovider.page1";

    /**
     * This method is used for generating current T.O.S version.
     * 
     * @return
     */
    public static String getCurrentTosVersion() {
        return (String) CorePlugin.getDefault().getBundle().getHeaders().get(org.osgi.framework.Constants.BUNDLE_VERSION);
    }

    public static ECodeLanguage getCurrentLanguage() {
        return ((RepositoryContext) CorePlugin.getContext().getProperty(Context.REPOSITORY_CONTEXT_KEY)).getProject()
                .getLanguage();
    }

    /**
     * Get the folder that user have set in preference page.
     * 
     * @return
     */
    public static File getUserComponentFolder() {
        IPreferenceStore store = EcosystemPlugin.getDefault().getPreferenceStore();
        String path = store.getString(EcosystemPreferencePage.ECOSYSTEM_COMPONENTS_FOLDER);
        return StringUtils.isEmpty(path) ? null : new File(path);
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
        // IPath folder = EcosystemPlugin.getDefault().getStateLocation();
        // File file = folder.append(fileName).toFile();
        File file = new File(getUserComponentFolder(), fileName);
        saveEmfModel(EcosystemPackage.eNS_URI, EcosystemPackage.eINSTANCE, components, file.getAbsolutePath());
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
        // IPath folder = EcosystemPlugin.getDefault().getStateLocation();
        // File file = folder.append(fileName).toFile();
        File file = new File(getUserComponentFolder(), fileName);
        return loadEmfModel(EcosystemPackage.eNS_URI, EcosystemPackage.eINSTANCE, file.getAbsolutePath());
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> loadEmfModel(String nsURI, EPackage pkg, String file) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();

        Resource.Factory.Registry registry = resourceSet.getResourceFactoryRegistry();
        registry.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put("xml", new XMLResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        EPackage.Registry reg = resourceSet.getPackageRegistry();
        reg.put(nsURI, pkg);

        List<T> list = new ArrayList<T>();

        URI uri = URI.createFileURI(file);
        Resource resource = resourceSet.getResource(uri, true);
        resource.load(null);

        for (EObject obj : resource.getContents()) {
            list.add((T) obj);
        }
        return list;
    }

    public static void saveEmfModel(String nsURI, EPackage pkg, List<? extends EObject> models, String file) throws IOException {
        ResourceSet resourceSet = new ResourceSetImpl();

        Resource.Factory.Registry registry = resourceSet.getResourceFactoryRegistry();
        registry.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put("xml", new XMLResourceFactoryImpl());
        registry.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());

        EPackage.Registry reg = resourceSet.getPackageRegistry();
        reg.put(nsURI, pkg);

        URI uri = URI.createFileURI(file);

        Resource resource = resourceSet.createResource(uri);

        for (EObject model : models) {
            resource.getContents().add(model);
        }

        resource.save(null);
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

    public static void main(String[] args) {
        System.out.println("1.2.3".split("\\.")[0]);

    }

}
