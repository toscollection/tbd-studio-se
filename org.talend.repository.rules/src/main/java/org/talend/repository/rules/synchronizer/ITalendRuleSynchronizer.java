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
package org.talend.repository.rules.synchronizer;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.talend.commons.exception.SystemException;
import org.talend.core.model.properties.RulesItem;

/**
 * DOC hyWang class global comment. Detailled comment
 */
public interface ITalendRuleSynchronizer {

    public IFile getRuleFile(RulesItem rulesItem, String extension) throws SystemException;

    public void initRuleFolder(IProject javaProject, org.talend.core.model.properties.Project project) throws CoreException;

    public String getRuleFolder();

    public void syncRule(RulesItem rulesItem) throws SystemException;

    public IPath getPath();

    public void syncAllRules();
}
