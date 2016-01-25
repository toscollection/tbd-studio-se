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
package org.talend.repository.nosql.db.util.mongodb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil;
import org.talend.utils.json.JSONArray;
import org.talend.utils.json.JSONException;
import org.talend.utils.json.JSONObject;

/**
 * DOC PLV class global comment. Detailled comment
 */
public class MongoDBConnectionUtilTest {

    private static final String KEY = "key"; //$NON-NLS-1$

    private static final String VALUE = "value"; //$NON-NLS-1$

    private static final String QUOTATION_MARK = "\""; //$NON-NLS-1$

    private List<HashMap<String, Object>> replicaSet;

    /**
     * DOC PLV Comment method "setUp".
     * 
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        replicaSet = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> mapObject = new HashMap<String, Object>();
        mapObject.put(KEY, VALUE);
        replicaSet.add(mapObject);
    }

    /**
     * Test method for
     * {@link org.talend.repository.nosql.db.util.mongodb.MongoDBConnectionUtil#getReplicaSetList(java.lang.String, boolean)}
     * .
     */
    @Test
    public void testGetReplicaSetList() {
        try {
            boolean includeQuotes = false;
            JSONArray jsonArr = new JSONArray();
            for (HashMap<String, Object> map : replicaSet) {
                JSONObject object = new JSONObject();
                Iterator it = map.keySet().iterator();
                while (it.hasNext()) {
                    String key = (String) it.next();
                    object.put(key, map.get(key));
                }
                jsonArr.put(object);
            }
            String replicaSetJsonStr = jsonArr.toString();
            List<HashMap<String, Object>> replicaSetList = MongoDBConnectionUtil.getReplicaSetList(replicaSetJsonStr,
                    includeQuotes);
            Assert.assertNotNull(replicaSetList);
            Assert.assertSame(replicaSetList.size(), 1);
            HashMap<String, Object> mapObject = replicaSetList.get(0);
            Assert.assertEquals(mapObject.get(KEY), VALUE);
            includeQuotes = true;
            replicaSetList = MongoDBConnectionUtil.getReplicaSetList(replicaSetJsonStr, includeQuotes);
            mapObject = replicaSetList.get(0);
            Assert.assertEquals(mapObject.get(KEY), QUOTATION_MARK + VALUE + QUOTATION_MARK);
        } catch (JSONException e) {
            Assert.fail();
        }
    }

}
