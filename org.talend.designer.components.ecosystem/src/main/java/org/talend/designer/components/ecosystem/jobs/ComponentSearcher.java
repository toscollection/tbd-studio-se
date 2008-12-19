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
package org.talend.designer.components.ecosystem.jobs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.talend.commons.exception.ExceptionHandler;
import org.talend.core.language.ECodeLanguage;
import org.talend.designer.components.ecosystem.EcosystemUtils;
import org.talend.designer.components.ecosystem.model.ComponentExtension;
import org.talend.designer.components.ecosystem.model.EcosystemFactory;
import org.talend.designer.components.ecosystem.model.EcosystemPackage;
import org.talend.designer.components.ecosystem.model.Language;
import org.talend.designer.components.ecosystem.model.RevisionInfo;

/**
 * Search for component extensions.
 */
public class ComponentSearcher {

    private static final String RELEASE_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private static DateFormat formatter = new SimpleDateFormat(RELEASE_DATE_FORMAT);

    /**
     * Find available components.
     * 
     * @param version The tos version.
     * @param language The project language.
     * @return
     */
    public static List<ComponentExtension> getAvailableComponentExtensions(String version, ECodeLanguage language) {
        List<ComponentExtension> extensions = new ArrayList<ComponentExtension>();

        try {
        	List<RevisionInfo> revisions = EcosystemUtils.getRevisionList(
					version, getLanguageId(language));

            Map<String, ComponentExtension> extensionsMap = new HashMap<String, ComponentExtension>();

            for (RevisionInfo revision : revisions) {
                ComponentExtension extension = extensionsMap.get(revision.getExtension_name());
                if (extension == null) {
                    extension = EcosystemFactory.eINSTANCE.createComponentExtension();
                    extension.setName(revision.getExtension_name());
                    extension.setAuthor(revision.getAuthor_name());
                    extension.setLanguage(Language.get(getLanguageId(language)));
                    extension.setDescription(revision.getExtension_description());

                    extensionsMap.put(extension.getName(), extension);
                    extensions.add(extension);
                }

                org.talend.designer.components.ecosystem.model.Revision rev = convertRevision(revision);
                extension.getRevisions().add(rev);
                if (extension.getLatestRevision() == null || extension.getLatestRevision().getDate().before(rev.getDate())) {
                    // assumes that the revision with latest release date is the newest one.
                    extension.setLatestRevision(rev);
                }
            }
        } catch (Exception e) {
            ExceptionHandler.process(e);
        }

        return extensions;

    }

    /**
     * Convert the project language to id.
     * 
     * @param language
     * @return
     */
    private static int getLanguageId(ECodeLanguage language) {
        switch (language) {
        case JAVA:
            return Language.JAVA_VALUE;
        case PERL:
            return Language.PERL_VALUE;
        }
        // unknow language
        return -1;
    }

    /**
     * Convert the web service returned value to our model object.
     * 
     * @param revision The message returned from web service method call.
     * @return
     * @throws ParseException
     */
    private static org.talend.designer.components.ecosystem.model.Revision convertRevision(
			RevisionInfo revision)
            throws ParseException {
        org.talend.designer.components.ecosystem.model.Revision rev = EcosystemFactory.eINSTANCE.createRevision();
        rev.setDate(formatter.parse(revision.getRevision_date()));
        rev.setName(revision.getRevision_name());
        rev.setUrl(revision.getDownload_url());
        rev.setDescription(revision.getRevision_description());
        rev.setId(revision.getRevision_id());
        rev.setFileName(revision.getFilename());
        return rev;
    }

    /**
     * Search components according to user input.
     * 
     * @param components All available components.
     * @param query The user input.
     * @param featureIds The field to search, e.g. name or description.
     * @return
     * @throws Exception
     */
    public static List<ComponentExtension> filterComponentExtensions(List<ComponentExtension> components, String query,
            int[] featureIds) throws Exception {
        ISearchProvider<ComponentExtension> provider = SearchProviderFactory.getInstance().getSearchProvider();
        provider.init();

        for (ComponentExtension component : components) {

            String[] text = new String[featureIds.length];
            for (int i = 0; i < featureIds.length; i++) {
                if (featureIds[i] == EcosystemPackage.COMPONENT_EXTENSION__NAME) {
                    text[i] = component.getName();
                } else if (featureIds[i] == EcosystemPackage.COMPONENT_EXTENSION__DESCRIPTION) {
                    text[i] = component.getDescription();
                } else {
                    text[i] = "";
                }
            }

            provider.addRecord(component, component.getName(), text);
        }
        return provider.search(query);

    }

    /**
     * Find the components that have been installed.
     * 
     * @param components
     * @return
     */
    public static List<ComponentExtension> getInstalledExtensions(List<ComponentExtension> components) {
        List<ComponentExtension> installed = new ArrayList<ComponentExtension>();
        for (ComponentExtension component : components) {
            if (component.getInstalledLocation() != null) {
                installed.add(component);
            }
        }
        return installed;

    }
}
