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
package org.talend.designer.core.perl.runprocess;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.osgi.framework.Bundle;
import org.talend.commons.ui.runtime.exception.ExceptionHandler;
import org.talend.core.CorePlugin;
import org.talend.core.model.components.IComponentsFactory;
import org.talend.core.model.general.ILibrariesService;
import org.talend.designer.core.perl.DesignerPerlPlugin;
import org.w3c.dom.Document;

/**
 * Utilities around perl stuff. <br/>
 * 
 * $Id: PerlUtils.java 52559 2010-12-13 04:14:06Z nrousseau $
 * 
 */
public final class PerlUtils {

    public static final String PERL_PROJECT_NAME = ".Perl"; //$NON-NLS-1$

    private static final String PERL_NATURE = "org.epic.perleditor.perlnature"; //$NON-NLS-1$

    public static final String PERL_LAUNCHCONFIGURATION = "org.epic.debug.launchConfigurationPerl"; //$NON-NLS-1$

    public static final String ATTR_PROJECT_NAME = "ATTR_PROJECT_NAME"; //$NON-NLS-1$

    public static final String ATTR_STARTUP_FILE = "ATTR_STARTUP_FILE"; //$NON-NLS-1$

    public static final String ATTR_WORKING_DIRECTORY = "ATTR_WORKING_DIRECTORY"; //$NON-NLS-1$

    public static final String ATTR_PROGRAM_PARAMETERS = "ATTR_PROGRAM_PARAMETERS"; //$NON-NLS-1$

    public static final String ROUTINE_FILENAME_EXT = ".pm"; //$NON-NLS-1$

    public static final String PERL_RULES_DIRECTORY = "rules"; //$NON-NLS-1$

    public static final String PERL_SRC_DIRECTORY = "src"; //$NON-NLS-1$

    public static final String DRL_EXTENSION = ".drl"; //$NON-NLS-1$

    /**
     * Constructs a new PerlUtils.
     */
    private PerlUtils() {
        super();
    }

    public static IProject getProject() throws CoreException {
        IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
        IProject prj = root.getProject(PERL_PROJECT_NAME);

        // Does the perl nature exists in the environment
        IExtensionRegistry registry = Platform.getExtensionRegistry();
        IExtension nature = registry.getExtension("org.eclipse.core.resources.natures", PERL_NATURE); //$NON-NLS-1$

        if (!prj.exists()) {
            final IWorkspace workspace = ResourcesPlugin.getWorkspace();
            final IProjectDescription desc = workspace.newProjectDescription(prj.getName());
            if (nature != null) {
                desc.setNatureIds(new String[] { PERL_NATURE });
            }
            prj.create(desc, null);
            prj.open(IResource.BACKGROUND_REFRESH, null);
        } else {
            if (!prj.isOpen()) {
                prj.open(null);
            }

            if (prj.getNature(PERL_NATURE) == null && nature != null) {

                IProjectDescription description = prj.getDescription();
                String[] natures = description.getNatureIds();
                String[] newNatures = new String[natures.length + 1];
                System.arraycopy(natures, 0, newNatures, 0, natures.length);
                newNatures[natures.length] = PERL_NATURE;
                description.setNatureIds(newNatures);
                prj.setDescription(description, null);
            }
        }

        // Fix perl module includes
        String[] includeEntries = new String[] { getPerlModulePath().toOSString() };
        writePerlIncludes(prj, includeEntries);

        return prj;
    }

    public static IPath getPerlModulePath() throws CoreException {
        ILibrariesService service = CorePlugin.getDefault().getLibrariesService();
        return new Path(service.getLibrariesPath());
    }

    private static void writePerlIncludes(IProject prj, String[] includeEntries) throws CoreException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.newDocument();
            org.w3c.dom.Element xmlRoot = document.createElement("includepath"); //$NON-NLS-1$
            document.appendChild(xmlRoot);
            for (int i = 0; i < includeEntries.length; i++) {
                org.w3c.dom.Element entry = document.createElement("includepathentry"); //$NON-NLS-1$
                entry.setAttribute("path", includeEntries[i]); //$NON-NLS-1$
                xmlRoot.appendChild(entry);
            }
            String file = prj.getLocation().toString() + File.separator + ".includepath"; //$NON-NLS-1$
            OutputStream out = null;
            try {
                out = new FileOutputStream(file);
                TransformerFactory tFactory = TransformerFactory.newInstance();
                Transformer transformer = tFactory.newTransformer();
                DOMSource source = new DOMSource(document);
                transformer.setOutputProperty(OutputKeys.INDENT, "yes"); //$NON-NLS-1$
                StreamResult result = new StreamResult(out);
                transformer.transform(source, result);
            } catch (TransformerException te) {
                // te.printStackTrace();
                ExceptionHandler.process(te);

            } finally {
                out.close();
            }
        } catch (ParserConfigurationException pce) {
            throw new CoreException(new Status(Status.ERROR, DesignerPerlPlugin.PLUGIN_ID, Status.OK,
                    "Perl Module include failure.", pce)); //$NON-NLS-1$
        } catch (IOException ioe) {
            throw new CoreException(new Status(Status.ERROR, DesignerPerlPlugin.PLUGIN_ID, Status.OK,
                    "Perl Module include failure.", ioe)); //$NON-NLS-1$
        }
    }

    /**
     * DOC ftang Comment method "getPerlModuleDirectoryPath".
     * 
     * @return
     * @throws CoreException
     */
    public static IPath getPerlModuleDirectoryPath() throws CoreException {
        Bundle b = Platform.getBundle(IComponentsFactory.COMPONENTS_LOCATION);
        URL url = null;
        try {
            url = FileLocator.toFileURL(FileLocator.find(b, new Path(IComponentsFactory.COMPONENTS_INNER_FOLDER), null));
            return new Path(url.getFile());
        } catch (IOException e) {
            throw new CoreException(new Status(Status.ERROR, DesignerPerlPlugin.PLUGIN_ID, Status.OK,
                    "Designer Core Plugin not found.", null)); //$NON-NLS-1$
        }
    }
}
