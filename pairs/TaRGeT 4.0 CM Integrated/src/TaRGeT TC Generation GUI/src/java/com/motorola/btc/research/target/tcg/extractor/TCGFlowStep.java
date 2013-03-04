/*
 * @(#)TCGFlowStep.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Jun 08, 2007   LIBmm42774   Initial creation.
 * dhq348    Nov 26, 2007   LIBoo10574   Added setup information.
 * dhq348    Jan 23, 2008   LIBoo10574   Rework after inspection LX229632.
 */
package com.motorola.btc.research.target.tcg.extractor;

import com.motorola.btc.research.target.common.lts.Transition;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;

/**
 * This class extends the <code>FlowStep</code> class with information related to the LTS
 * transition.
 * 
 * <pre>
 * CLASS:
 *
 * The class contains an attribute of the <code>Transition</code> class.
 * </pre>
 */
public class TCGFlowStep extends FlowStep
{
    /** The LTS transition that contains the flow step */
    private Transition<FlowStep, Integer> transition;

    /**
     * This constructor calls the <code>FlowStep</code> constructor, informing as parameters the
     * information contained in the transition.
     * 
     * @param transition The LTS transition.
     */
    public TCGFlowStep(Transition<FlowStep, Integer> transition)
    {
        super(transition.getValue().getId(), transition.getValue().getUserAction(), transition
                .getValue().getSystemCondition(), transition.getValue().getSystemResponse(),
                transition.getValue().getSetup(), transition.getValue().getRelatedRequirements());

        this.transition = transition;
    }


}
