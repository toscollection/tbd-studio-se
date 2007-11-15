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
package org.talend.designer.dbmap.oracle;

import org.talend.core.model.genhtml.HTMLDocUtils;
import org.talend.core.model.process.IComponentDocumentation;
import org.talend.designer.dbmap.DbMapComponent;
import org.talend.designer.dbmap.language.generation.DbGenerationManager;
import org.talend.designer.dbmap.oracle.language.OracleGenerationManager;

/**
 * DOC amaumont class global comment. Detailled comment <br/>
 * 
 * $Id: MapperComponent.java 1782 2007-02-03 07:57:38Z bqian $
 * 
 */
public class OracleMapperComponent extends DbMapComponent {

    DbGenerationManager generationManager = new OracleGenerationManager();

    /**
     * DOC amaumont OracleMapperComponent constructor comment.
     */
    public OracleMapperComponent() {
        super();
    }

    public static void main(String[] args) {
        // AbstractDbMapComponent dbMapComponent = new OracleMapperComponent();
        // MapperMain.setStandAloneMode(true);
        // OracleDbMapTestGenerator testGenerator = new OracleDbMapTestGenerator(dbMapComponent.getGenerationManager(),
        // true);
        // dbMapComponent.setExternalData(testGenerator.getExternalData());
        // dbMapComponent.setIncomingConnections(testGenerator.getConnectionList());
        // dbMapComponent.setMetadataList(testGenerator.getMetadataListOut());
        //
        // ExternalNodeUtils.prepareExternalNodeReadyToOpen(dbMapComponent);
        //
        // int response = dbMapComponent.open(new Display());
        // if (response == SWT.OK) {
        // // System.out.println("Response = OK");
        //
        // // System.out.println("mapperConnector.getMetadataList()=");
        // // System.out.println(mapperConnector.getMetadataList());
        // // System.out.println("mapperConnector.getPersistentData()=");
        // // System.out.println(mapperConnector.getExternalData(false));
        //
        // } else {
        // // System.out.println("Response = CANCEL");
        // }

    }

    /**
     * Getter for generationManager.
     * 
     * @return the generationManager
     */
    public DbGenerationManager getGenerationManager() {
        return this.generationManager;
    }

}
