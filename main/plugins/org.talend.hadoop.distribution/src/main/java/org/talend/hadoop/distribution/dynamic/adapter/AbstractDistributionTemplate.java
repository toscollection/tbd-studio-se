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
package org.talend.hadoop.distribution.dynamic.adapter;

import java.io.File;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.monoid.json.JSONObject;

/**
 * DOC cmeng  class global comment. Detailled comment
 */
public abstract class AbstractDistributionTemplate implements IDistributionTemplate {

    private JSONObject json;
    
    private TemplateBean templateBean;

    public AbstractDistributionTemplate(JSONObject json) throws Exception {
        this.json = json;
        init();
    }

    protected void init() throws Exception {
        ObjectMapper objMapper = new ObjectMapper();
        templateBean = objMapper.readValue(json.toString(), TemplateBean.class);
    }

    public static void main(String args[]) throws Exception {
        ObjectMapper om = new ObjectMapper();
        TemplateBean bean = om.readValue(new File(
                "C:\\Dev\\Ws\\master\\tbd-studio-se\\main\\plugins\\org.talend.hadoop.distribution\\resources\\cdhTemplate.json"), //$NON-NLS-1$
                TemplateBean.class);
        System.out.println(bean.toString());
    }
    
}
