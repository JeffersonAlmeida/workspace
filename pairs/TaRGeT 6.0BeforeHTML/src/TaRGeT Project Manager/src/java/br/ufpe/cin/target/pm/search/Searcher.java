/*
 * @(#)Searcher.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   May 17, 2007    LIBmm25975   Initial creation.
 * dhq348   Jun 06, 2007    LIBmm25975   Rework after first meeting of inspection LX179934.
 * dhq348   Aug 20, 2007    LIBmm93130   Modifications according to CR.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 */
package br.ufpe.cin.target.pm.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Hits;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.store.Directory;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.exceptions.TargetSearchException;
import br.ufpe.cin.target.pm.search.util.SearchUtil;


/**
 * This class is responsible for searching in a given index directory. The class is responsible for
 * executing different king of searches. The class is visible only in the current package.
 */
class Searcher
{
    /**
     * The index directory.
     */
    private Directory index;

    /**
     * The duration (in milliseconds) of the last search.
     */
    private long lastSearchDuration;

    /**
     * Creates an Searcher given the <code>index</code> directory.
     * 
     * @param index The directory that contains an index.
     */
    public Searcher(Directory index)
    {
        this.index = index;
    }

    /**
     * Searches a documents by its name. This kind of search uses the KeywordAnalyzer because it
     * does not break strings in tokens (the whole string is a token).
     * 
     * @param name The name of the document.
     * @return The requested document or <code>null</code> if none exists.
     * @throws TargetSearchException Thrown when it is not possible to read the index or to parse
     * the query.
     */
    public List<TargetIndexDocument> searchDocumentsByName(String name)
            throws TargetSearchException
    {
        
    	return this.search("", "\""+ SearchUtil.DOCUMENT_NAME+"\"" + ":" + name, new KeywordAnalyzer());
    }

    /**
     * Searches data according to the given <code>query</code>.
     * 
     * @param query The query to be searched.
     * @return A list with the search results.
     * @throws TargetSearchException Thrown when it is not possible to parse the <code>query</code>
     * or to read the index.
     */
    public List<TargetIndexDocument> search(String query) throws TargetSearchException
    {
        return this.search(SearchUtil.DOCUMENT_BODY, query, new StandardAnalyzer());
    }

    /**
     * Returns the duration of the last search.
     * 
     * @return The duration of the last search.
     */
    public long getLastSearchDuration()
    {
        return lastSearchDuration;
    }

    /**
     * Gets the number of documents in the index.
     * 
     * @return The number of documents in the index.
     * @throws TargetSearchException Thrown if it is not possible to read the index.
     */
    public int getNumberOfDocuments() throws TargetSearchException
    {
        int result = 0;

        try
        {
            IndexReader reader = IndexReader.open(index);
            result = reader.numDocs();
            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            TargetSearchException sException = new TargetSearchException("Error reading the index.");
            sException.setStackTrace(e.getStackTrace());
            throw sException;
        }

        return result;
    }

    /**
     * Searches for the given <code>queryStr</code> using the default <code>field</code> using
     * the given <code>analyzer</code>. The method parses the given query using the AND operator
     * which means that a query like 'may the force be with you' is really represented as 'may AND
     * the AND force AND be AND with AND you'.
     * 
     * @param field The default field to be searched.
     * @param queryStr The query.
     * @param analyzer The analyzer used to parse the query.
     * @return A list with the search results.
     * @throws TargetSearchException Thrown when it is not possible to parse the <code>query</code>
     * or to read the index.
     */
    private List<TargetIndexDocument> search(String field, String queryStr, Analyzer analyzer)
            throws TargetSearchException
    {
        ArrayList<TargetIndexDocument> result = new ArrayList<TargetIndexDocument>();
        try
        {
            IndexReader reader = IndexReader.open(index);
            org.apache.lucene.search.Searcher searcher = new IndexSearcher(reader);
            QueryParser parser = new QueryParser(field, analyzer);
            parser.setDefaultOperator(QueryParser.AND_OPERATOR);
            parser.setAllowLeadingWildcard(true);

            Query query = parser.parse(queryStr);
            
            Date start = new Date();
            Hits hits = searcher.search(query);
            Date end = new Date();
            this.lastSearchDuration = end.getTime() - start.getTime();

            for (int i = 0; i < hits.length(); i++)
            {
                Document doc = hits.doc(i);

                String documentName = doc.get(SearchUtil.DOCUMENT_NAME);
                String uc_id = doc.get(SearchUtil.UC_ID);
                String feature_id = doc.get(SearchUtil.FEATURE_ID);

                Feature feature = getFeature(feature_id);
                UseCase usecase = (feature != null) ? feature.getUseCase(uc_id) : null;

                /* Creates a TargetDocument */
                TargetIndexDocument targetDocument = new TargetIndexDocument(usecase, feature,
                        documentName, query.toString(), hits.id(i), hits.score(i));

                result.add(targetDocument);
            }

            reader.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
            TargetSearchException sException = new TargetSearchException("Error reading the index.");
            sException.setStackTrace(e.getStackTrace());
            throw sException;
        }
        catch (ParseException e)
        {
            e.printStackTrace();
            TargetSearchException sException = new TargetSearchException("Error parsing the query.");
            sException.setStackTrace(e.getStackTrace());
            throw sException;
        }

        return result;
    }

    /**
     * Searches for a feature, given its id, in the current project.
     * 
     * @param featureId The id of the feature to be searched.
     * @return The feature or <code>null</code> if it does not exists with the given id.
     */
    private Feature getFeature(String featureId)
    {
        Feature result = null;
        for (Feature feature : ProjectManagerController.getInstance().getAllFeatures())
        {
            if (feature.getId().equals(featureId))
            {
                result = feature;
                break;
            }
        }
        return result;
    }
}
