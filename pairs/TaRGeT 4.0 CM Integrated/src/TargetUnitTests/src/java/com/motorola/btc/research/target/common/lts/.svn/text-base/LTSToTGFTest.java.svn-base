/*
 * @(#)LTSToTGFTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dwvm83    23/09/2008     LIBqq51204   Initial creation.
 */
package com.motorola.btc.research.target.common.lts;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;

public class LTSToTGFTest {
	
	/** LTSToTGT used in the whole class */
    private static LTSToTGF originalLTSToTGF;
     
    /**
     * Loads the default document <code>TestCaseGenerationTests.F_UC1_DOC</code>.
     * 
     * @throws UseCaseDocumentXMLException Thrown if any error occurs during the extraction of
     * contents from the MS Word documents.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException
    {
        /* The input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.F_UCB_DOC));
        LTS.resetNextTransitionId();
        UserViewLTSGenerator ltsGenerator = new UserViewLTSGenerator();
        LTS<FlowStep, Integer> lts = ltsGenerator.generateLTS(docs);
        originalLTSToTGF = new LTSToTGF(lts);
   }
    
    /**
     * Tests the method translate from the LTSToTGF class and
     * the method parse from TGFToLTS class
     */
    @Test
    public void testLTSToTGTTranslate(){
    	String translation1 = originalLTSToTGF.translate();
      	LTSToTGF parsedLTSToTGF = new LTSToTGF(TGFToLTS.parse(translation1));
    	String translation2 = parsedLTSToTGF.translate();
    	Assert.assertEquals(translation1,translation2);
    	
    }

}
