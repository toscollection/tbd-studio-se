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

import java.util.Iterator;
import java.util.Map;

import org.talend.core.runtime.dynamic.IDynamicAttribute;
import org.talend.hadoop.distribution.dynamic.util.DynamicDistributionUtils;

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

    public String getNewValueByTemplate(String key) {
        return getNewValueByTemplate(key, getDynamicId());
    }

    public String getNewValueByTemplate(String key, String... args) {
        String template = null;
        Object templateObj = getAttributeTemplate(key);
        String fillTemplate = null;
        if (templateObj != null) {
            template = templateObj.toString();
            fillTemplate = DynamicDistributionUtils.fillTemplate(template, args);
        }
        return fillTemplate;
    }

    public IDynamicAttribute getDynamicAttribute() {
        return dynamicAttribute;
    }

    public void adapt() {
        cleanTemplateAndDefault();
    }

    public void cleanTemplateAndDefault() {
        Map<String, Object> attributes = dynamicAttribute.getAttributes();
        if (attributes != null && !attributes.isEmpty()) {
            Iterator<Map.Entry<String, Object>> iter = attributes.entrySet().iterator();
            while (iter.hasNext()) {
                Map.Entry<String, Object> entry = iter.next();
                String key = entry.getKey();
                if (key.startsWith(DEFAULT_PREFIX) || key.startsWith(TEMPLATE_PREFIX)) {
                    iter.remove();
                }
            }
        }
    }
}
