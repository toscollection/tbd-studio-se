// ============================================================================
//
// Copyright (C) 2006-2012 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.oozie.scheduler.controller;

import java.util.HashMap;
import java.util.Map;

/**
 * DOC Marvin class global comment. Detailled comment
 */
public class OozieJobTraceManager {

    private Map<String, String> traces;

    private static OozieJobTraceManager instance;

    private OozieJobTraceManager() {
        traces = new HashMap<String, String>();
    }

    public static OozieJobTraceManager getInstance() {
        if (instance == null)
            instance = new OozieJobTraceManager();
        return instance;
    }

    public void putTrace(String jobId, String trace) {
        traces.put(jobId, trace);
    }

    public String getTrace(String jobId) {
        return traces.get(jobId);
    }

    public void removeTrace(String jobId) {
        traces.remove(jobId);
    }

}
