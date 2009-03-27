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
package org.talend.repository.rules;

import org.talend.core.ui.IRulesProviderService;
import org.talend.repository.rules.service.ITalendRuleService;
import org.talend.repository.rules.service.TalendRuleService;
import org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer;

/**
 * hywang class global comment. Detailled comment
 */
public class RulesProviderService implements IRulesProviderService {

    public void syncAllRules() {
        ITalendRuleService service = new TalendRuleService();
        ITalendRuleSynchronizer synchronizer = service.createRuleSynchronizer();
        synchronizer.syncAllRules();
    }
}
