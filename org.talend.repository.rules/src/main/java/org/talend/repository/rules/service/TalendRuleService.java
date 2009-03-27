// ============================================================================
//
// Copyright (C) 2006-2008 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.repository.rules.service;

import org.talend.core.language.ECodeLanguage;
import org.talend.core.language.LanguageManager;
import org.talend.repository.rules.synchronizer.ITalendRuleSynchronizer;
import org.talend.repository.rules.synchronizer.JavaRuleSynchronizer;
import org.talend.repository.rules.synchronizer.PerlRuleSynchronizer;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public class TalendRuleService implements ITalendRuleService {

    public ITalendRuleSynchronizer createRuleSynchronizer() {
        if (LanguageManager.getCurrentLanguage() == ECodeLanguage.JAVA) {
            return new JavaRuleSynchronizer();
        } else {
            return new PerlRuleSynchronizer();
        }
    }

}
