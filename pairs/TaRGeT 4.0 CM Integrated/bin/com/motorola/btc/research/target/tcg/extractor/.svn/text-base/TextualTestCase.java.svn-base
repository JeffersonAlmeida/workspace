/*
 * @(#)TextualTestCase.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013   Jan 8, 2007     LIBkk11577   Initial creation.
 * wsn013   Jan 18, 2007    LIBkk11577   Modifications after inspection (LX135035).
 * dhq348   Jul 05, 2007    LIBmm76986   Modifications according to CR.
 * dhq348   Jul 19, 2007    LIBmm76986   Rework after inspection LX189921.
 * dhq348   Nov 26, 2007    LIBoo10574   Now using setup information.
 * dhq348   Jan 23, 2008    LIBoo10574   Rework after inspection LX229632.
 * tnd783   Apr 07, 2008    LIBpp71785   Removed all methods other than gets and sets and changed all attributes to String.
 * tnd783   Jul 21, 2008    LIBpp71785   Rework after inspection LX285039. 
 */
package com.motorola.btc.research.target.tcg.extractor;

import java.util.List;

/**
 * This class represents a textual test case very close to the test case standard of Test Central.
 * 
 * <pre>
 * CLASS:
 * This class represents a textual test case that is very . It is both a instantiation and a extension 
 * of TestCase<TextualStep>. 
 * 
 * RESPONSIBILITIES:
 * 1) Keeps the textual information that is used to generate a test case in a excel sheet with the 
 * Test Central standard for test cases  
 *
 * COLABORATORS:
 * N/A
 *
 * USAGE:
 * TextualTestCase textualTest = new TextualTestCase();
 * String strId = "TC_" + rawTest.getId();
 * textualTest.setStrId(strId);
 */
public class TextualTestCase extends TestCase<TextualStep>
{
    /** The test execution type of the test case */
    protected String executionType;

    /** Regression level of the test */
    protected String regressionLevel;

    /** String identification of the test */
    protected String description;

    /** Objective of the test case */
    protected String objective;

    /** Requirements of the test case */
    protected String requirements;

    /** The references to the use cases. A reference to a use case is a string like "featureID#ucID". */
    protected String useCaseReferences;

    /** Test setup list */
    protected String setups;

    /** Test initial conditions list */
    protected String initialConditions;

    /** Test note */
    protected String note;

    /** Test final conditions list */
    protected String finalConditions;

    /** Test clean up list */
    protected String cleanup;

    /** Test status */
	protected String status;
	
	/** Test tcID */
	protected String tcIdHeader;


    /**
     * Constructs a textual test case from all of its fields.
     * 
     * @param id The test id
     * @param tcID 
     * @param steps The test textual steps
     * @param executionType The test textual identification
     * @param regressionLevel The test regression level
     * @param strId The test textual identification
     * @param objective The test objective
     * @param requirements The test requirements
     * @param setups The test setup
     * @param initialConditions The test initial conditions
     * @param note The test note
     * @param finalConditions The test final conditions
     * @param cleanup The test cleanup
     * @param useCaseReferences The use case references of this test case
     */
    public TextualTestCase(int id, String tcIdHeader, List<TextualStep> steps, String executionType,
            String regressionLevel, String description, String objective, String requirements,
            String setups, String initialConditions, String note, String finalConditions,
            String cleanup, String useCaseReferences,String status)
    {
        super(id, steps);
        this.executionType = executionType;
        this.regressionLevel = regressionLevel;
        this.description = description;
        this.objective = objective;
        this.requirements = requirements;
        this.setups = setups;
        this.initialConditions = initialConditions;
        this.note = note;
        this.finalConditions = finalConditions;
        this.cleanup = cleanup;
        this.useCaseReferences = useCaseReferences;
        this.status = status;
        this.tcIdHeader = tcIdHeader;
    }

    /**
     * Constructs a textual test case from the id and the textual steps.
     * 
     * @param id The test id.
     * @param steps Test textual steps.
     */
    public TextualTestCase(int id, List<TextualStep> steps)
    {
        super(id, steps);
    }

    /**
     * Return the test execution type code.
     * 
     * @return The execution type.
     */
    public String getExecutionType()
    {
        return executionType;
    }

    /**
     * Gets the cleanup value.
     * 
     * @return The cleanup.
     */
    public String getCleanup()
    {
        return cleanup;
    }

    /**
     * Gets the finalConditions value.
     * 
     * @return Returns the final conditions.
     */
    public String getFinalConditions()
    {
        return finalConditions;
    }

    /**
     * Gets the initialConditions value.
     * 
     * @return Returns the initial conditions.
     */
    public String getInitialConditions()
    {
        return initialConditions;
    }

    /**
     * Gets the note value.
     * 
     * @return Returns the note.
     */
    public String getNote()
    {
        return note;
    }

    /**
     * Gets the objective value.
     * 
     * @return Returns the objective.
     */
    public String getObjective()
    {
        return objective;
    }

    /**
     * Gets the regressionLevel value.
     * 
     * @return Returns the regression level.
     */
    public String getRegressionLevel()
    {
        return regressionLevel;
    }

    /**
     * Gets the setups value.
     * 
     * @return Returns the setups.
     */
    public String getSetups()
    {
        return setups;
    }

    /**
     * Gets the strId value.
     * 
     * @return Returns the strId.
     */
    public String getTcIdHeader()
    {
        return tcIdHeader;
    }

    /**
     * Gets the use case references value.
     * 
     * @return Returns the use case references.
     */
    public String getUseCaseReferences()
    {
        return useCaseReferences;
    }

    /**
     * Gets the requirements value.
     * 
     * @return Returns the requirements.
     */
    public String getRequirements()
    {
        return requirements;
    }
    
    /**
     * Gets the status value.
     * 
     * @return Returns the requirements.
     */
    public String getStatus()
    {
        return status;
    }

    /**
     * Verifies if this test case covers the given use case.
     * 
     * @param usecaseId The use case.
     * @return whether this test case contains or not the use case.
     */
    public boolean coversUseCase(String usecaseId)
    {
        return this.useCaseReferences.contains(usecaseId);
    }

    /**
     * Verifies if this test case covers the given requirement.
     * 
     * @param usecaseId The requirement.
     * @return whether this test case contains or not the requirement.
     */
    public boolean coversRequirement(String requirement)
    {
        return this.requirements.contains(requirement);
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

}
