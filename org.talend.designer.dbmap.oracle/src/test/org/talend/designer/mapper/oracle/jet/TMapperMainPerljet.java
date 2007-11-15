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
package org.talend.designer.mapper.oracle.jet;

import java.util.List;

import org.talend.core.model.process.AbstractExternalNode;
import org.talend.core.model.process.IConnection;
import org.talend.core.model.process.IExternalNode;
import org.talend.designer.dbmap.MapperMain;
import org.talend.designer.dbmap.external.data.ExternalDbMapData;
import org.talend.designer.dbmap.external.data.ExternalDbMapTable;
import org.talend.designer.dbmap.language.IDbLanguage;
import org.talend.designer.dbmap.oracle.OracleMapperComponent;
import org.talend.designer.dbmap.oracle.language.OracleGenerationManager;
import org.talend.designer.mapper.oracle.model.metadata.OracleDbMapTestGenerator;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: TMapperMainPerljet.java 1275 2007-01-04 13:35:45Z amaumont $
 * 
 */
public class TMapperMainPerljet {

    @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        IExternalNode argument = null;

        AbstractExternalNode node = (AbstractExternalNode) argument;

        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        // start of code to copy in template

        OracleGenerationManager gm = new OracleGenerationManager();
        String uniqueNameComponent = null;
        IDbLanguage currentLanguage = gm.getLanguage();
        List<IConnection> connections;
        ExternalDbMapData data;
        if (node != null) {
            // normal use
            connections = (List<IConnection>) node.getIncomingConnections();
            data = (ExternalDbMapData) node.getExternalData();
            uniqueNameComponent = node.getUniqueName();
        } else {
            // Stand alone / tests
            MapperMain.setStandAloneMode(true);
            OracleDbMapTestGenerator testGenerator = new OracleDbMapTestGenerator(gm, false);
            connections = testGenerator.getConnectionList();
            data = (ExternalDbMapData) testGenerator.getExternalData();
            uniqueNameComponent = "testUniqueNameNode";
        }

        List<ExternalDbMapTable> outputTables = data.getOutputTables();

        String insertQuery = "";
        if (outputTables.size() > 0) {
            ExternalDbMapTable outputTable = outputTables.get(0);

            String sqlQuery = gm.buildSqlSelect((OracleMapperComponent) node, outputTable.getName());
            insertQuery = "$select_query_" + outputTable.getName() + " = \"" + sqlQuery + "\"";

        }

        // end of code to copy in template
        // ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        System.out.println(insertQuery);
    }

}
