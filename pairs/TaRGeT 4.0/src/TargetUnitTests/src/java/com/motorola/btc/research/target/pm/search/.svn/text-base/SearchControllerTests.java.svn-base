/*
 * @(#)SearchControllerTests.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    May 31, 2007   LIBmm25975   Initial creation.
 * dhq348    Jun 06, 2007   LIBmm25975   Rework after first meeting of inspection LX179934.
 * dhq348    Jun 21, 2007   LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * dhq348    Sep 03, 2007   LIBnn24462   Updated calls to method SearchController.search()
 */
package com.motorola.btc.research.target.pm.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Stack;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.ProjectManagerTests;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.exceptions.TargetSearchException;

/**
 * This class is responsible for the unit tests that verify the <code>SearchController</code>
 * class. It tests:
 * <ul>
 * <li>The indexing of phone documents
 * <li>The order of the queries
 * <li>The deletion of documents from the index
 * <li>The search process
 * </ul>
 */
public class SearchControllerTests
{
    /**
     * The project manager controller
     */
    private ProjectManagerController pmController;

    /**
     * The search controller
     */
    private SearchController searchController;

    /**
     * The loaded files during the creation of the default project
     */
    private Collection<File> files;

    /**
     * Creates a default project and loads two documents (<code>ProjectManagerTests.correctDoc1</code>
     * and <code>ProjectManagerTests.correctDoc2</code>) into it.
     * 
     * @throws TargetException Thrown when it is not possible to load the documents.
     */
    private void createDefaultProject() throws TargetException
    {
        long time = System.currentTimeMillis();
        String projectName = "SearchTest_" + time;

        /* Creates the TaRgeT project in Memory */
        this.pmController.createProject(projectName, ProjectManagerTests.OUTPUT_FOLDER);

        this.files = new ArrayList<File>();
        this.files.add(new File(ProjectManagerTests.INPUT_CORRECT_DOC_1));
        this.files.add(new File(ProjectManagerTests.INPUT_CORRECT_DOC_2));
        this.pmController.loadImportedDocumentsIntoProject(this.files);
    }

    /**
     * Executed before each test. Initializes the instances of project manager controller and search
     * controller, and creates the default project.
     * 
     * @throws TargetException Thrown when it is not possible to create the default project.
     * @throws IOException Thrown when it is not possible to write the project files.
     */
    @Before
    public void setUp() throws TargetException, IOException
    {
        this.pmController = ProjectManagerController.getInstance();
        this.createDefaultProject();
        this.searchController = SearchController.getInstance();
    }

    /**
     * Tests the document indexing.
     * 
     * @throws TargetSearchException Thrown in case of any error erasing the index.
     */
    @Test
    public void testIndexPhoneDocuments() throws TargetSearchException
    {
        this.searchController.indexPhoneDocuments(this.pmController.getCurrentProject()
                .getPhoneDocuments());
        this.searchController.getNumberOfDocuments();
    }

    /**
     * Tests if the last queries are being stored in the correct order.
     * 
     * @throws TargetSearchException Thrown in case of any error during the search.
     */
    @Test
    public void testGetLastQueries() throws TargetSearchException
    {
        String[] queries = new String[] { "foo", "foobar", "spiderman" };
        for (int i = 0; i < queries.length; i++)
        {
            this.searchController.search(queries[i], true);
        }
        Stack<String> storedQueries = this.searchController.getLastQueries();

        Assert
                .assertEquals("The number of queries is wrong.", queries.length, storedQueries
                        .size());

        while (!storedQueries.isEmpty())
        {
            for (int i = queries.length - 1; i >= 0; i--)
            {
                Assert.assertEquals("Wrong query position.", queries[i], storedQueries.pop());
            }
        }
    }

    /**
     * Tests if the default index was successfully erased.
     * 
     * @throws TargetSearchException Thrown in any case during the search.
     */
    @Test
    public void testEraseIndex() throws TargetSearchException
    {
        this.testIndexPhoneDocuments();
        this.searchController.eraseIndex();
        Assert.assertEquals("Index not successfully erased", this.searchController
                .getNumberOfDocuments(), 0);
    }

    /**
     * Tests the exclusion of documents from the index.
     * 
     * @throws TargetSearchException
     * @throws TargetSearchException Thrown in case of any error during the search procedures.
     */
    @Test
    public void testRemoveDocumentFromIndex() throws TargetSearchException
    {
        /* now tests removeDocumentsFromIndex */
        this.testIndexPhoneDocuments();

        List<TargetIndexDocument> documents = SearchController.getInstance()
                .getPhoneDocumentsAsTargetIndexDocuments(
                        this.pmController.getCurrentProject().getPhoneDocuments());
        this.searchController.removeDocumentsFromIndex(documents);
        documents = SearchController.getInstance().getPhoneDocumentsAsTargetIndexDocuments(
                this.pmController.getCurrentProject().getPhoneDocuments());
        Assert.assertEquals("Documents not successfully removed", 0, this.searchController
                .getNumberOfDocuments());
    }

    /**
     * Tests the document search. It tests the expected number of documents returned when searching
     * for specific fields.
     * 
     * @throws TargetSearchException Thrown in case of any error during the search procedures.
     */
    @Test
    public void testSearch() throws TargetSearchException
    {
        this.testIndexPhoneDocuments();

        List<TargetIndexDocument> documents = this.searchController
                .filterSearch(this.searchController.search("FOOBAR", true));
        Assert.assertEquals("Unexpected number of documents in search result", 0, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search("START", true));
        Assert.assertTrue("Unexpected number of documents in search result", documents.size() > 0);

        documents = this.searchController.filterSearch(this.searchController.search(
                "featureid:11111", true));
        Assert.assertTrue("Unexpected number of documents in search result", documents.size() > 0);

        documents = this.searchController.filterSearch(this.searchController.search("ucid:UC_01",
                true));
        Assert.assertEquals("Unexpected number of documents in search result", 2, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search(
                "ucname:\"Creating a SMS\"", true));
        Assert.assertEquals("Unexpected number of documents in search result", 1, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search(
                "flowdesc:\"Create and send a SMS\"", true));
        Assert.assertEquals("Unexpected number of documents in search result", 1, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search(
                "fromstep:\"11111#UC_02#4M\"", true));
        Assert.assertEquals("Unexpected number of documents in search result", 2, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search("tostep:END",
                true));
        Assert.assertEquals("Unexpected number of documents in search result", 3, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search("stepid:1M",
                true));
        Assert.assertEquals("Unexpected number of documents in search result", 3, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search(
                "stepaction:\"Set the receiver of the message.\"", true));
        Assert.assertEquals("Unexpected number of documents in search result", 1, documents.size());

        documents = this.searchController.filterSearch(this.searchController.search(
                "stepresponse:\"The entered information is displayed.\"", true));
        Assert.assertEquals("Unexpected number of documents in search result", 1, documents.size());
    }
}
