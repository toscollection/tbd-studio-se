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
package org.talend.repository.hadoopcluster.token;

import java.util.HashSet;
import java.util.Set;

import org.talend.core.model.repository.ERepositoryObjectType;
import org.talend.core.model.repository.IRepositoryViewObject;
import org.talend.core.repository.model.ProxyRepositoryFactory;
import org.talend.core.ui.token.AbstractTokenCollector;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnection;
import org.talend.repository.model.hadoopcluster.HadoopClusterConnectionItem;

import us.monoid.json.JSONArray;
import us.monoid.json.JSONObject;



/**
 * created by nrousseau on Apr 1, 2016
 * Detailled comment
 *
 */
public class HadoopClusterTokenCollector extends AbstractTokenCollector  {
    
    private String HADOOPCLUSTER = "HADOOPCLUSTER"; //$NON-NLS-1$

    /**
     * DOC nrousseau HadoopClusterTokenCollector constructor comment.
     */
    public HadoopClusterTokenCollector() {
    }

    /* (non-Javadoc)
     * @see org.talend.core.ui.token.AbstractTokenCollector#collect()
     */
    @Override
    public JSONObject collect() throws Exception {
        JSONObject finalToken = new JSONObject();
        JSONArray typesHadoop = new JSONArray();
        
        Set<String> differentTypesHadoop = new HashSet<>();
        for (IRepositoryViewObject rvo : ProxyRepositoryFactory.getInstance().getAll(ERepositoryObjectType.getType(HADOOPCLUSTER))) {
            HadoopClusterConnectionItem item = (HadoopClusterConnectionItem)rvo.getProperty().getItem();
            HadoopClusterConnection connection = (HadoopClusterConnection) item.getConnection();
            differentTypesHadoop.add(connection.getDistribution() + "/" + connection.getDfVersion()); //$NON-NLS-1$
        }
        for (String type : differentTypesHadoop) {
            typesHadoop.put(type);
        }        

        JSONObject hadoopCluster = new JSONObject();
        finalToken.put(PROJECTS_REPOSITORY.getKey(), hadoopCluster);
        JSONObject types = new JSONObject();
        types.put("types", typesHadoop);
        hadoopCluster.put(HADOOPCLUSTER, types);
        return finalToken;
    }

}
