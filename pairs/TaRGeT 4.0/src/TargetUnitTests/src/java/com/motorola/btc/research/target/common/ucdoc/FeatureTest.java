/*
 * @(#)FeatureTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dwmv83   18/09/2008    	LIBqq51204	 Initial creation.
 */
package com.motorola.btc.research.target.common.ucdoc;

import java.util.List;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;

public class FeatureTest {
	
	/** Features used in the whole class */
    private static Feature featureA;
    private static Feature featureB;
    private static Feature featureAB;
 
    /**
     * Loads the documents <code>TestCaseGenerationTests.F_UCA_DOC</code>, <code>TestCaseGenerationTests.F_UCB_DOC</code> and
     * <code>TestCaseGenerationTests.MERGED_FEATURES_UAB_DOC</code> 
     * 
     * @throws UseCaseDocumentXMLException Thrown if any error occurs during the extraction of
     * contents from the MS Word documents.
     */   
    
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException
    {
        /* The input use case document */
        PhoneDocument doc = UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.F_UCA_DOC);
        featureA = doc.getFeatures().get(0);
        
        PhoneDocument doc2 = UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.F_UCB_DOC);
        featureB = doc2.getFeatures().get(0);
        
        PhoneDocument doc3 = UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.MERGED_FEATURES_UAB_DOC);
        featureAB = doc3.getFeatures().get(0);

    }
  
	/** Tests the merge method from the Feature class
	 *  
	 */
	
	@Test
    public void testFeatureMerge() 
    {
		Feature mergedFeature = featureA.mergeFeature(featureB);
		assertEquals(mergedFeature.getUseCases(),featureAB.getUseCases());
	
		Feature nullFeature = null;
		Feature mergedWithNullFeature = featureA.mergeFeature(nullFeature);
		assertEquals(mergedWithNullFeature.getUseCases(),featureA.getUseCases());
    
    }
	
	 private void assertEquals(List<UseCase>expected,List<UseCase>actual){
   	  for (int i = 0; i < actual.size(); i++) {
   		  List<Flow> flowsActual = actual.get(i).getFlows();
   		  List<Flow> flowsExpected = expected.get(i).getFlows();
   		  for (int j = 0; j < flowsActual.size(); j++){
  			  Assert.assertEquals("The flow steps should be equal.",flowsExpected.get(j).getSteps(), flowsActual.get(j).getSteps());
   		  }
   	 
   	  }
   	 }
	 
		
		/** Tests the equals method from the Feature class
		 *  
		 */
		
		@Test
	    public void testFeatureEquals() 
	    {
			Assert.assertEquals(featureA, featureA);
	    }
		
		

}
