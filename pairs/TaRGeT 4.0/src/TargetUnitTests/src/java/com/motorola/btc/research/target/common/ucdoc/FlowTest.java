/*
 * @(#)FlowTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dwvm83   19/09/2008    	LIBqq51204   Initial creation.
 */
package com.motorola.btc.research.target.common.ucdoc;

import java.util.List;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;

public class FlowTest {
	
	/** Flow used in the whole class **/
	private static Flow flow;
	
    /**
     * Loads document <code>TestCaseGenerationTests.F_UC1_DOC</code>.
     * to get a flow from one of the use case's features
     * 
     * @throws UseCaseDocumentXMLException Thrown if any error occurs during the extraction of
     * contents from the MS Word documents.
     */   
    
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException
    {
    	/* The input use case document */
        PhoneDocument doc = UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.F_UCA_DOC);
        flow = doc.getFeatures().get(0).getUseCases().get(0).getFlows().get(0);
    }
    
    /** Tests the hasStepWithId method from the Flow class
	 *  
	 */
	
	@Test
    public void testFlowHasStepWithId(){
		List<FlowStep> steps = flow.getSteps();
		StepId step = steps.get(0).getId();
		boolean hasStepWithId = flow.hasStepWithId(step);
		Assert.assertEquals(true, hasStepWithId);
		
	}
	
	/** Tests the equals method from the Flow class
	 *  
	 */
	
	@Test
    public void testFlowEquals(){
		Assert.assertEquals(flow, flow);
	}

}
