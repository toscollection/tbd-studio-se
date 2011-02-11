// ============================================================================
//
// Copyright (C) 2006-2011 Talend Inc. - www.talend.com
//
// This source code is available under agreement available at
// %InstallDIR%\features\org.talend.rcp.branding.%PRODUCTNAME%\%PRODUCTNAME%license.txt
//
// You should have received a copy of the agreement
// along with this program; if not, write to Talend SA
// 9 rue Pages 92150 Suresnes, France
//
// ============================================================================
package org.talend.core.model.codegen;

import java.util.List;

import org.talend.core.model.process.INode;
import org.talend.core.model.process.IProcess;

/**
 * DOC Administrator class global comment. Detailled comment
 */
public interface ICodeGenerationService {

    /**
     * DOC Administrator Comment method "getDataProcess".
     * 
     * @param abstractProcess
     * @return
     */
    Object getDataProcess(IProcess abstractProcess);

    /**
     * DOC Administrator Comment method "getNodeList".
     * 
     * @param generatingProcess
     * @return
     */
    List<INode> getNodeList(Object generatingProcess);

    /**
     * DOC Administrator Comment method "buildFromGraphicalProcess".
     * 
     * @param generatingProcess
     * @param sortedFlow
     */
    void buildFromGraphicalProcess(Object generatingProcess, List<INode> sortedFlow);

}
