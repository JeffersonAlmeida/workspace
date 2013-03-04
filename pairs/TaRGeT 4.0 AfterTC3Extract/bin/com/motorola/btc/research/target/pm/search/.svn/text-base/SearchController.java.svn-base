/*
 * @(#)SearchController.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    May 24, 2007   LIBmm25975   Initial creation.
 * dhq348    Jun 06, 2007   LIBmm25975   Rework after first meeting of inspection LX179934.
 * dhq348    Sep 03, 2007   LIBnn24462   Updated method search()
 */
package com.motorola.btc.research.target.pm.search;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import org.apache.lucene.store.Directory;

import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.exceptions.TargetSearchException;
import com.motorola.btc.research.target.pm.search.util.SearchUtil;

/**
 * This is the search controller.
 * 
 * <pre>
 * CLASS:
 * This singleton class manages all search operations. It also stores the previous queries.
 * </pre>
 */
public class SearchController
{

    /**
     * The last queries.
     */
    private Stack<String> lastQueries;

    /**
     * The single instance of the controller.
     */
    private static SearchController instance = null;

    /**
     * The controller searcher. This searcher is instantiated during the controller creation.
     */
    private Searcher searcher;

    /**
     * The controller indexer. This indexer is instantiated during the controller creation.
     */
    private Indexer indexer = null;

    /**
     * Returns the single instance of the controller.
     * 
     * @return The single instance of the controller.
     */
    public static SearchController getInstance()
    {
        if (instance == null)
        {
            instance = new SearchController();
        }

        return instance;
    }

    /**
     * Returns the last queries.
     * 
     * @return The last queries.
     */
    public Stack<String> getLastQueries()
    {
        Stack<String> tmp = new Stack<String>();
        tmp.addAll(this.lastQueries);
        return tmp;
    }

    /**
     * Searches for data in the default index according to the given <code>query</code.
     * 
     * @param query The query that will guide the search.
     * @param addInLastQueriesStack Indicates if the query will be added in the last queries stack.
     * @return The indexed documents or <code>null</code> if any problem occurs.
     * @throws TargetSearchException Thrown if it is not possible to read the index or to parse the
     * query.
     */
    public List<TargetIndexDocument> search(String query, boolean addInLastQueriesStack)
            throws TargetSearchException
    {
        List<TargetIndexDocument> documents = null;

        documents = this.searcher.search(query);
        if (addInLastQueriesStack)
        {
            this.lastQueries.push(query);
        }

        return documents;
    }

    /**
     * Filter a search result that may contains documents with the same fields. The method keeps
     * only the single results.
     * 
     * @param results The results that may contain documents with same fields.
     * @return The minimum set of target index documents.
     */
    public List<TargetIndexDocument> filterSearch(List<TargetIndexDocument> results)
    {
        HashMap<Integer, TargetIndexDocument> map = new HashMap<Integer, TargetIndexDocument>();
        for (TargetIndexDocument document : results)
        {
            if (!map.containsKey(document.hashCode()))
            {
                map.put(document.hashCode(), document);
            }
        }

        List<TargetIndexDocument> result = new ArrayList<TargetIndexDocument>();
        for (Integer key : map.keySet())
        {
            result.add(map.get(key));
        }
        return result;
    }

    /**
     * Index a collection of phone documents into the default index. This method checks if an index
     * already exists. If it does not exist then a new one is created.
     * 
     * @param documents The collection of documents that will be indexed.
     * @throws TargetSearchException Thrown if any disk error occurs while trying to write in the
     * index.
     */
    public void indexPhoneDocuments(Collection<PhoneDocument> documents)
            throws TargetSearchException
    {
        this.indexer.indexPhoneDocuments(documents);
    }

    /**
     * Cleans the default index.
     * 
     * @throws TargetSearchException Thrown if it is not possible to erase the index.
     */
    public void eraseIndex() throws TargetSearchException
    {
        this.indexer.eraseIndex();
    }

    /**
     * Removes the <code>documents</code> from the default index.
     * 
     * @param documents The documents to be removed.
     * @throws TargetSearchException Thrown when it not possible to find any document or to read any
     * index.
     */
    public void removeDocumentsFromIndex(List<TargetIndexDocument> documents)
            throws TargetSearchException
    {
        this.indexer.removeDocumentsFromIndex(documents);
    }

    /**
     * Gets the number of documents in the index.
     * 
     * @return The number of documents in the index.
     * @throws TargetSearchException Thrown if it is not possible to read the index.
     */
    public int getNumberOfDocuments() throws TargetSearchException
    {
        return this.searcher.getNumberOfDocuments();
    }

    /**
     * Converts a collection of phone documents into a list of TargetIndexedDocuments. It searches
     * for the phone documents name.
     * 
     * @param phoneDocuments The phone documents that will be used when searching for the
     * TargetIndexDocuments.
     * @return The equivalent TargetIndexDocument list.
     * @throws TargetSearchException Thrown when it is not possible to read or write in the index or
     * to finish successfully an operation in the index.
     */
    public List<TargetIndexDocument> getPhoneDocumentsAsTargetIndexDocuments(
            Collection<PhoneDocument> phoneDocuments) throws TargetSearchException
    {
        List<TargetIndexDocument> result = new ArrayList<TargetIndexDocument>();
        for (PhoneDocument document : phoneDocuments)
        {
            result.addAll(this.searcher.searchDocumentsByName(FileUtil.getFileName(document
                    .getDocFilePath())));
        }
        return result;
    }

    /**
     * The private constructor of the controller.
     */
    private SearchController()
    {
        this.lastQueries = new Stack<String>();
        Directory directory = SearchUtil.getDefaultIndex();
        this.searcher = new Searcher(directory);
        this.indexer = new Indexer(directory);
    }

}
