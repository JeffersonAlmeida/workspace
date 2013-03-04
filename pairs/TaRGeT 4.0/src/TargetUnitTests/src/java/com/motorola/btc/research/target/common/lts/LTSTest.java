/*
 * @(#)LTSTest.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Aug 17, 2007   LIBmm42774   Initial creation.
 * dhq348    Aug 21, 2007   LIBmm42774   Rework after inspection LX201094.
 * dhq348    Feb 21, 2008   LIBoo24851   Modifications according to CR.
 * dhq348    Feb 22, 2008   LIBoo24851   Rework after inspection LX245583.
 */
package com.motorola.btc.research.target.common.lts;

import java.util.ArrayList;
import java.util.Collection;

import junit.framework.Assert;

import org.junit.BeforeClass;
import org.junit.Test;

import com.motorola.btc.research.target.common.UnitTestUtil;
import com.motorola.btc.research.target.common.exceptions.UseCaseDocumentXMLException;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.tcg.TestCaseGenerationTests;

/**
 * Tests the LTS class.
 */
public class LTSTest
{
    /** LTS used in the whole class */
    private static LTS<FlowStep, Integer> originalLts;

    /**
     * Loads the default document <code>TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC</code>.
     * 
     * @throws UseCaseDocumentXMLException Thrown if any error occurs during the extraction of
     * contents from the MS Word documents.
     */
    @BeforeClass
    public static void setUpBeforeClass() throws UseCaseDocumentXMLException
    {
        /* The input use case document */
        ArrayList<PhoneDocument> docs = new ArrayList<PhoneDocument>();
        docs.add(UnitTestUtil.getPhoneDocument(TestCaseGenerationTests.MULTIPLE_REFERENCES_UC_DOC));
        LTS.resetNextTransitionId();
        UserViewLTSGenerator ltsGenerator = new UserViewLTSGenerator();
        originalLts = ltsGenerator.generateLTS(docs);
    }

    /**
     * Tests the methods equals() and clone() from the LTS class.
     */
    @Test
    public void testLTSEqualsAndClone()
    {
        LTS<FlowStep, Integer> clonedLTS = originalLts.clone();
        Assert.assertNotNull("The cloned LTS is null.", clonedLTS);
        Assert
                .assertEquals("The equals method has an unexpected behavior.", originalLts,
                        clonedLTS);
    }

    /**
     * Tests the method merge() from the LTS class. Creates three pruned LTS models. Then merges
     * them in different ways, and then tests if they are equal to the original one.
     * 
     * <pre>
     * 
     *      <ol>
     *          <li>LTSA complements LTSB = originalLts
     *          <li>LTSA does not complement LTSC
     *          <li>LTSA, LTSB and LTSC complement themselves = originalLts
     *      </ol>
     * </pre>
     */
    @Test
    public void testLTSMerge()
    {
        LTS<FlowStep, Integer> ltsA = originalLts.clone();
        Assert.assertEquals("The cloned LTS should be equal to the original.", originalLts, ltsA);
        Collection<Transition<FlowStep, Integer>> transitionsA = new ArrayList<Transition<FlowStep, Integer>>();

        transitionsA.add(ltsA.getTransition(11));
        transitionsA.add(ltsA.getTransition(8));
        ltsA.prune(transitionsA);

        LTS<FlowStep, Integer> ltsB = originalLts.clone();
        Assert.assertEquals("The cloned LTS should be equal to the original.", originalLts, ltsB);
        Collection<Transition<FlowStep, Integer>> transitionsB = new ArrayList<Transition<FlowStep, Integer>>();
        transitionsB.add(ltsB.getTransition(7));
        transitionsB.add(ltsB.getTransition(13));
        ltsB.prune(transitionsB);

        LTS<FlowStep, Integer> ltsC = originalLts.clone();
        Assert.assertEquals("The cloned LTS should be equal to the original.", originalLts, ltsC);
        Collection<Transition<FlowStep, Integer>> transitionsC = new ArrayList<Transition<FlowStep, Integer>>();
        transitionsC.add(ltsC.getTransition(5));
        transitionsC.add(ltsC.getTransition(3));
        ltsC.prune(transitionsC);

        LTS<FlowStep, Integer> mergedLTSABC = ltsA.clone();
        mergedLTSABC.merge(ltsB);
        mergedLTSABC.merge(ltsC);

        LTS<FlowStep, Integer> mergedLTSAC = ltsA.clone();
        mergedLTSAC.merge(ltsC);

        LTS<FlowStep, Integer> mergedLTSAB = ltsA.clone();
        mergedLTSAB.merge(ltsB);

        Assert.assertEquals("The merged LTS should be equal to the original.", originalLts,
                mergedLTSABC);
        Assert.assertEquals("The merged LTS should be equal to the original.", originalLts,
                mergedLTSAB);
        Assert.assertNotSame("The merged LTS should not be equal to the original.", originalLts,
                mergedLTSAC);
    }
}
