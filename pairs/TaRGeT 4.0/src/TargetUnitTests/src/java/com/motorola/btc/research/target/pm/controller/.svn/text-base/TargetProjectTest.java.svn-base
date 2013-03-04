/*
 * @(#)TargetProjectTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 25, 2007   LIBkk22882   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 */
package com.motorola.btc.research.target.pm.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.ProjectManagerTests;

/**
 * This class comprehends the unit tests for the <code>TargetProject</code> class.
 * The input documents defined in <code>ProjectManagerTests</code> were used to test 
 * the <code>TargetProject</code> methods. 
 *  
 */
public class TargetProjectTest
{
    /** The list of documents that are used as input for the tests */
    private static List<PhoneDocument> phoneDocuments;
    

    /**
     * This method initializes the variables used by the whole <code>TargetProject</code> 
     * class test suite.
     * <p>
     * The variable <code>phoneDocuments</code> is initialized with three loaded documents 
     * and one document that is not well-formed.
     * 
     * @throws UseCaseDocumentXMLException In case of any error during the processing 
     * of the MS Word documents.
     */
    @BeforeClass
    public static void setupData() throws UseCaseDocumentXMLException
    {
        WordDocumentProcessing wDocProcessing = WordDocumentProcessing.getInstance();

        ArrayList<String> docFiles = new ArrayList<String>();

        /* Listing the documents to be loaded */
        docFiles.add(ProjectManagerTests.INPUT_CORRECT_DOC_1);
        docFiles.add(ProjectManagerTests.INPUT_CORRECT_DOC_2);
        docFiles.add(ProjectManagerTests.INPUT_DUP_STEPID_DOC);

        /* Loading the documents */
        phoneDocuments = wDocProcessing.createObjectsFromWordDocument(docFiles, true);

        /* Adding a not well-formed document */
        File file = new File(ProjectManagerTests.INPUT_DUP_FEATID_INSIDE_DOC);
        phoneDocuments.add(new PhoneDocument(file.getAbsolutePath(), file.lastModified(), null, null));
    }

    /** 
     * This method tests the method <code>existPhoneDocument</code> from the 
     * <code>TargetProject</code> class.
     * <p>
     * The method test if a valid and invalid document exists. It also tests the 
     * method with an invalid path.
     * 
     */
    @Test
    public void existPhoneDocument()
    {
        TargetProject tgtProject = new TargetProject("Test Project", UnitTestUtil.OUTPUT_FOLDER,
                phoneDocuments);

        Assert.assertTrue("The valid document must exist in the project", tgtProject
                .existPhoneDocument(FileUtil.getFileName(ProjectManagerTests.INPUT_CORRECT_DOC_2)));

        Assert.assertTrue("The invalid document must exist in the project", tgtProject
                .existPhoneDocument(FileUtil.getFileName(ProjectManagerTests.INPUT_DUP_FEATID_INSIDE_DOC)));

        Assert.assertFalse("This document must not exist in the project. It is an invalid path",
                tgtProject.existPhoneDocument("!!!! Invalid Document Path !!!!"));
    }

    /** 
     * This method tests the method <code>getFeatures</code> from the 
     * <code>TargetProject</code> class.
     * <p>
     * The method test if a valid and invalid document exists. It also tests the 
     * method with an invalid path.
     * 
     */
    @Test
    public void getFeatures()
    {
        TargetProject tgtProject = new TargetProject("Test Project", UnitTestUtil.OUTPUT_FOLDER,
                phoneDocuments);

        Assert.assertTrue("The project must contain three features", tgtProject.getFeatures()
                .size() == 3);

        ArrayList<PhoneDocument> phoneDocList = new ArrayList<PhoneDocument>();

        /* Testing the method with a empty project */
        tgtProject = new TargetProject("Test Project", UnitTestUtil.OUTPUT_FOLDER, phoneDocList);
        Assert.assertTrue(
                "The project must not contain features. There is no document in the project",
                tgtProject.getFeatures().size() == 0);
        
        /* Testing the method with a project containing only one not well-formed document */
        phoneDocList.add(phoneDocuments.get(3));
        tgtProject = new TargetProject("Test Project", UnitTestUtil.OUTPUT_FOLDER, phoneDocList);
        Assert.assertTrue(
                "The project must not contain features",
                tgtProject.getFeatures().size() == 0);
    }

}