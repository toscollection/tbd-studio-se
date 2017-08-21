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
package org.talend.hadoop.distribution.dynamic;

import org.talend.core.runtime.dynamic.IDynamicAttribute;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDynamicAdapter {

    public static final String DEFAULT_PREFIX = "default_"; //$NON-NLS-1$

    public static final String TEMPLATE_PREFIX = "template_"; //$NON-NLS-1$

    private String dynamicId;

    private IDynamicAttribute dynamicAttribute;

    public AbstractDynamicAdapter(IDynamicAttribute dynamicAttr, String id) {
        dynamicAttribute = dynamicAttr;
        dynamicId = id;
    }

    public String getDynamicId() {
        return dynamicId;
    }

    public String getDefaultKey(String key) {
        return DEFAULT_PREFIX + key;
    }

    public String getTemplateKey(String key) {
        return TEMPLATE_PREFIX + key;
    }

    public Object getAttributeDefault(String key) {
        return dynamicAttribute.getAttribute(getDefaultKey(key));
    }

    public Object getAttributeTemplate(String key) {
        return dynamicAttribute.getAttribute(getTemplateKey(key));
    }

    public String fillTemplate(String templateString, String... args) {
        String fullString = templateString;
        if (args != null && 0 < args.length) {
            for (int i = 0; i < args.length; ++i) {
                String arg = args[i];
                fullString = fullString.replaceAll("\\{" + i + "\\}", arg); //$NON-NLS-1$ //$NON-NLS-2$
            }
        }
        return fullString;
    }

    public String getNewValueByTemplate(String key) {
        String template = getAttributeTemplate(key).toString();
        return fillTemplate(template, getDynamicId());
    }

    public String getNewValueByTemplate(String key, String... args) {
        String template = getAttributeTemplate(key).toString();
        return fillTemplate(template, args);
    }

    public IDynamicAttribute getDynamicAttribute() {
        return dynamicAttribute;
    }

    abstract public void adapt();
}
