/*
 * @(#)Indexer.java
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
 * dhq348    May 17, 2007   LIBmm25975   Initial creation.
 * dhq348    Jun 06, 2007   LIBmm25975   Rework after first meeting of inspection LX179934.
 * dhq348   Aug 24, 2007    LIBmm93130   Rework after inspection LX201888.
 */
package br.ufpe.cin.target.pm.search;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.store.Directory;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.exceptions.TargetSearchException;
import br.ufpe.cin.target.pm.search.util.SearchUtil;


/**
 * This indexer is responsible for indexing contents in UseCaseDocuments for later search.
 * 
 * <pre>
 * CLASS:
 * This class uses the Apache Lucene Search Engine for indexing documents. The current implementation 
 * was developed based on Lucene v.2.1.0. 
 * The class also keeps a reference to the directory used as index. The directory can be disk based or 
 * memory based (RAM).
 * The class is also responsible for creating a Lucene document given the necessary fields and stores 
 * it in the index. It basically extracts the relevant data from a use case document and creates the
 * Lucene document objects. 
 * 
 * Managed operations over the index:
 * <ol>
 *  <li>indexing use case documents
 *  <li>removing use case documents
 *  <li>erasing the index
 *  <li>verifying if an index exists in a given location
 * </ol>
 * The class is visible only in the current package, since it is only visible for 
 * <code>SearchController</code> class.
 * </pre>
 */
class Indexer
{
    /**
     * The index directory, where the search data is stored. It can be a file system directory or a
     * ram directory (in memory).
     */
    private Directory index;

    /**
     * Creates an <code>Indexer</code> instance given the <code>index</code> directory.
     * 
     * @param index The directory that contains an index or the place where the index will be
     * created.
     */
    public Indexer(Directory index)
    {
        this.index = index;
    }

    /**
     * Index a collection of use case documents and stores in the index directory. This method checks
     * if an index already exists. If it does not exist then it creates a new one.
     * 
     * @param documents The collection of documents that will be indexed.
     * @throws TargetSearchException Thrown when it is not possible to read the index.
     */
    public void indexUseCaseDocuments(Collection<UseCaseDocument> documents)
            throws TargetSearchException
    {
        if (documents != null && documents.size() > 0)
        {
            try
            {
                boolean create = !IndexReader.indexExists(this.index);
                IndexWriter writer = new IndexWriter(this.index, new StandardAnalyzer(), create);

                indexUseCaseDocuments(documents, writer);

                writer.optimize();
                writer.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
                TargetSearchException sException = new TargetSearchException(
                        "Error indexing use case documents.");
                sException.setStackTrace(e.getStackTrace());
                throw sException;
            }
        }
    }

    /**
     * Erases the current index directory.
     * 
     * @throws TargetSearchException Thrown if it is not possible to erase the index.
     */
    public void eraseIndex() throws TargetSearchException
    {
        try
        {
            if (IndexReader.indexExists(this.index))
            {
                IndexReader reader = IndexReader.open(this.index);
                TermEnum terms = reader.terms(new Term(SearchUtil.DOCUMENT_NAME, ""));
                while (SearchUtil.DOCUMENT_NAME.equals(terms.term().field()))
                {
                    reader.deleteDocuments(terms.term());
                    if (!terms.next())
                    {
                        break;
                    }
                }
                reader.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            TargetSearchException sException = new TargetSearchException("Error erasing index.");
            sException.setStackTrace(e.getStackTrace());
            throw sException;
        }

    }

    /**
     * Removes some document's data from the index.
     * 
     * @param documents The documents to be removed.
     * @throws TargetSearchException Thrown when it is not possible to find or remove a document.
     */
    public void removeDocumentsFromIndex(List<TargetIndexDocument> documents)
            throws TargetSearchException
    {
        try
        {
            if (IndexReader.indexExists(this.index))
            {
                IndexReader reader = IndexReader.open(this.index);
                for (TargetIndexDocument document : documents)
                {
                    reader.deleteDocument(document.getIndexNumber());
                }
                reader.close();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            TargetSearchException sException = new TargetSearchException("Error removing document.");
            sException.setStackTrace(e.getStackTrace());
            throw sException;
        }
    }

    /**
     * Creates a Lucene Document with the following fields:
     * 
     * <pre>
     *      UC_ID - The id of the use case passed as parameter.
     *      UC_NAME - The name of the use case passed as parameter.
     *      UC_DESCRIPTION - The description of the use case passed as parameter.
     *      DOCUMENT_NAME - The name to the document that contains the use case being indexed.
     *      FEATURE_ID - The id of the feature that contains the use case being indexed.
     *      DOCUMENT_BODY - The content of the use case
     * </pre>
     * 
     * The created document is ready to be indexed (written in an index).
     * 
     * @param feature The feature that contains the use case. It can be null.
     * @param usecase The use case whose data will be indexed. It can be null.
     * @param documentName The name of the document that contains the use case being indexed.
     * @return A list of lucene documents.
     */
    private List<Document> createMainIndexedDocument(Feature feature, UseCase usecase,
            String documentName)
    {
        List<Document> result = new ArrayList<Document>();

        Document doc = new Document();

        String featureId = feature.getId();
        String usecaseId = usecase.getId();
        String usecaseName = usecase.getName();
        String usecaseDescription = usecase.getDescription();

        doc
                .add(new Field(SearchUtil.FEATURE_ID, featureId, Field.Store.YES,
                        Field.Index.TOKENIZED));
        doc.add(new Field(SearchUtil.UC_ID, usecaseId, Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field(SearchUtil.UC_NAME, usecaseName, Field.Store.YES, Field.Index.TOKENIZED));
        doc.add(new Field(SearchUtil.UC_DESCRIPTION, usecaseDescription, Field.Store.YES,
                Field.Index.TOKENIZED));
        doc.add(new Field(SearchUtil.DOCUMENT_NAME, documentName, Field.Store.YES,
                Field.Index.UN_TOKENIZED));

        this.createFlowDocuments(doc, usecase);

        doc.add(new Field(SearchUtil.DOCUMENT_BODY, this.getUseCaseString(usecase),
                Field.Store.YES, Field.Index.TOKENIZED));

        result.add(doc);

        return result;
    }

    /**
     * Creates flow documents based on an already existing <code>parent</code> document.
     * 
     * @param parent The parent document.
     * @param usecase The use case whose flows will be extracted.
     */
    private void createFlowDocuments(Document parent, UseCase usecase)
    {
        for (Flow flow : usecase.getFlows())
        {
            parent.add(new Field(SearchUtil.FLOW_DESCRIPTION, flow.getDescription(),
                    Field.Store.YES, Field.Index.TOKENIZED));
            for (StepId stepid : flow.getFromSteps())
            {
                parent.add(new Field(SearchUtil.FROM_STEP, stepid.toString(), Field.Store.YES,
                        Field.Index.TOKENIZED));
            }
            for (StepId stepid : flow.getToSteps())
            {
                parent.add(new Field(SearchUtil.TO_STEP, stepid.toString(), Field.Store.YES,
                        Field.Index.TOKENIZED));
            }

            this.createStepDocuments(parent, flow);
        }

    }

    /**
     * Creates step documents based on an already existing <code>parent</code> document.
     * 
     * @param parent The parent document.
     * @param flow The flow whose steps will be extracted.
     */
    private void createStepDocuments(Document parent, Flow flow)
    {
        for (FlowStep step : flow.getSteps())
        {
            parent.add(new Field(SearchUtil.STEP_ID, step.getId().toString(), Field.Store.YES,
                    Field.Index.TOKENIZED));
            parent.add(new Field(SearchUtil.STEP_ACTION, step.getUserAction(), Field.Store.YES,
                    Field.Index.TOKENIZED));
            parent.add(new Field(SearchUtil.STEP_CONDITION, step.getSystemCondition(),
                    Field.Store.YES, Field.Index.TOKENIZED));
            parent.add(new Field(SearchUtil.STEP_RESPONSE, step.getSystemResponse(),
                    Field.Store.YES, Field.Index.TOKENIZED));
        }
    }

    /**
     * Generates a string that contains all the <code>usecase</code> text. This representative
     * string contains:
     * 
     * <pre>
     * <ul>
     * <li> use case 
     *      <ol>
     *          <li>id
     *          <li>name
     *          <li>description
     *          <li>flows
     *              <ol>
     *                  <li>id
     *                  <li>system conditions
     *                  <li>responses
     *                  <li>actions
     *                  <li>related requirements
     *              </ol>
     *      </ol>
     * </ul>
     * </pre>
     * 
     * @param usecase The use case whose representative string will be generated.
     * @return A representative string of the use case.
     */
    private String getUseCaseString(UseCase usecase)
    {
        String result = usecase.getId() + " " + usecase.getName() + " " + usecase.getDescription();

        for (Flow flow : usecase.getFlows())
        {
            for (StepId stepId : flow.getFromSteps())
            {
                result += " " + stepId.getStepId();
            }
            for (StepId stepId : flow.getToSteps())
            {
                result += " " + stepId.getStepId();
            }
            result += " " + flow.getDescription();
            for (FlowStep step : flow.getSteps())
            {
                result += " " + step.getId().getStepId();
                result += " " + step.getSystemCondition();
                result += " " + step.getSystemResponse();
                result += " " + step.getUserAction();
                for (String requirement : step.getRelatedRequirements())
                {
                    result += " " + requirement;
                }
            }
        }

        return result;
    }

    /**
     * Index the given <code>documents</code> of the given <code>project</code> using the given
     * <code>writer</code>.
     * 
     * @param documents The documents to be indexed.
     * @param writer The writer that will write the documents into the index.
     * @throws TargetSearchException Thrown when it is not possible to write a document in the
     * index.
     */
    private void indexUseCaseDocuments(Collection<UseCaseDocument> documents, IndexWriter writer)
            throws TargetSearchException
    {
        try
        {
            for (UseCaseDocument document : documents)
            {
                if (document.isDocumentWellFormed()) 
                {
                    for (Feature feature : document.getFeatures())
                    {
                        for (UseCase usecase : feature.getUseCases())
                        {
                            List<Document> luceneDocuments = this.createMainIndexedDocument(
                                    feature, usecase, FileUtil.getFileName(document
                                            .getDocFilePath()));
                            for (Document document2 : luceneDocuments)
                            {
                                writer.addDocument(document2);
                            }
                        }
                    }
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
            TargetSearchException sException = new TargetSearchException(
                    "Error indexing use case documents.");
            sException.setStackTrace(e.getStackTrace());
            throw sException;
        }

    }
}
