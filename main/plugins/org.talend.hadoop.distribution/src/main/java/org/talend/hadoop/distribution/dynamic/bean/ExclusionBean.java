// ============================================================================
//
// Copyright (C) 2006-2016 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.hadoop.distribution.dynamic.bean;


/**
 * DOC cmeng  class global comment. Detailled comment
 */
public class ExclusionBean implements IVariable {

    public static final String ATTR_GROUP_ID = "groupId"; //$NON-NLS-1$

    public static final String ATTR_ARTIFACT_ID = "artifactId"; //$NON-NLS-1$

    public static final String ATTR_CLASSIFIER = "classifier"; //$NON-NLS-1$

    public static final String ATTR_EXTENSION = "extension"; //$NON-NLS-1$

    private String groupId;

    private String artifactId;

    private String classifier;

    private String extension;

    public String getGroupId() {
        return this.groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getArtifactId() {
        return this.artifactId;
    }

    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public String getClassifier() {
        return this.classifier;
    }

    public void setClassifier(String classifier) {
        this.classifier = classifier;
    }

    public String getExtension() {
        return this.extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    @Override
    public Object getVariableValue(String variable) throws Exception {
        Object result = null;
        switch (variable) {
        case ATTR_ARTIFACT_ID:
            result = getArtifactId();
            break;
        case ATTR_CLASSIFIER:
            result = getClassifier();
            break;
        case ATTR_EXTENSION:
            result = getExtension();
            break;
        case ATTR_GROUP_ID:
            result = getGroupId();
            break;
        default:
            throw new UnsupportedOperationException(
                    "Currently don't support get " + variable + ", please implement it if needed.");
        }
        return result;
    }

}
