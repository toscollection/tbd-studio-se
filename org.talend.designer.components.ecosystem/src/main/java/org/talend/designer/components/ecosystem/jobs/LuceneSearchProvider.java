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
package org.talend.designer.components.ecosystem.jobs;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.Searcher;
import org.apache.lucene.store.RAMDirectory;

/**
 * Use lucene for full text search.
 */
public class LuceneSearchProvider<T> implements ISearchProvider<T> {

    private static final String KEY_FIELD = "key";

    private static final String FIELD_PREFIX = "field";

    // Construct a RAMDirectory to hold the in-memory representation
    // of the index.
    RAMDirectory fIndex;

    private IndexWriter fWriter;

    private Map<String, T> fRecords;

    private int fFieldNum = 0;

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.ecosystem.jobs.ISearchProvider#init()
     */
    public void init() throws IOException {
        try {
            if (fIndex != null) {
                fIndex.close();
            }
        } catch (RuntimeException e) {

        }
        fIndex = new RAMDirectory();
        fRecords = new HashMap<String, T>();
        // Make an writer to create the index
        fWriter = new IndexWriter(fIndex, new StandardAnalyzer(), true);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.ecosystem.jobs.ISearchProvider#addRecord(T, java.lang.String,
     * java.lang.String)
     */
    @SuppressWarnings("deprecation")
    public void addRecord(T bean, String key, String... fields) throws IOException {
        Document doc = new Document();

        if (fields.length > fFieldNum) {
            fFieldNum = fields.length;
        }

        for (int i = 0; i < fields.length; i++) {
            doc.add(Field.Text(FIELD_PREFIX + i, new StringReader(fields[i])));
        }
        doc.add(Field.UnIndexed(KEY_FIELD, key));
        fRecords.put(key, bean);
        // add record to index
        if (fWriter == null) {
            fWriter = new IndexWriter(fIndex, new StandardAnalyzer(), true);
        }
        fWriter.addDocument(doc);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.talend.designer.components.ecosystem.jobs.ISearchProvider#search(java.lang.String)
     */
    @SuppressWarnings("deprecation")
    public List<T> search(String queryString) throws ParseException, IOException {
        if (fWriter != null) {
            // Optimize and close the writer to finish building the index
            fWriter.optimize();
            fWriter.close();
        }

        // Build an IndexSearcher using the in-memory index
        Searcher searcher = new IndexSearcher(fIndex);

        Set<String> keys = new HashSet<String>();

        for (int f = 0; f < fFieldNum; f++) {
            String field = FIELD_PREFIX + f;

            // Build a Query object
            Query query = new QueryParser(field, new StandardAnalyzer()).parse(queryString);

            // Search for the query
            Hits hits = searcher.search(query);

            // Examine the Hits object to see if there were any matches
            int hitCount = hits.length();

            // Iterate over the Documents in the Hits object
            for (int i = 0; i < hitCount; i++) {
                Document doc = hits.doc(i);
                keys.add(doc.get(KEY_FIELD));
            }
        }

        List<T> result = new ArrayList<T>();
        for (String key : keys) {
            result.add(fRecords.get(key));
        }
        return result;

    }

}
