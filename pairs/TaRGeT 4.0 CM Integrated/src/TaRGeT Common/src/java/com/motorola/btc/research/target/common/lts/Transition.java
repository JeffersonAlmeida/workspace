/*
 * @(#)Transition.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2006   LIBkk11577   Modifications after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Methods that modify the object were changed to default visibility.
 */

package com.motorola.btc.research.target.common.lts;

/**
 * Represents a transition of a graph.
 * 
 * <pre>
 * CLASS:
 * Represents a transition of a graph that has a value of type TVALUE and that works 
 * with states of type SVALUE.
 * 
 * RESPONSIBILITIES:
 * 1) Keeps track of the source and destination state of the transition
 *
 * COLABORATORS:
 * 1) Every time a new value for source and destination state is set, the 
 * transition updates the list of transitions of the give state 
 *
 * USAGE:
 * Transition<FlowStep, String> t = new Transition<FlowStep, String>();
 * t.setFrom(root);
 * t.setValue(TAU_STEP);
 * t.setId(this.transitionIncIndex++);
 */
public class Transition<TVALUE, SVALUE>
{

    /** Id of the transition */
    private int id;

    /** Value stored at the transition */
    private TVALUE value;

    /** Source state of the transition */
    private State<SVALUE, TVALUE> from;

    /** Destination state of the transition */
    private State<SVALUE, TVALUE> to;

    /**
     * Returns the source state of the transition.
     * 
     * @return The source state.
     */
    public State<SVALUE, TVALUE> getFrom()
    {
        return from;
    }

    /**
     * Sets the source state of the transition.
     * 
     * @param from The new value for source state.
     */
    public void setFrom(State<SVALUE, TVALUE> from)
    {
        this.from = from;
        this.from.addOutTransition(this);
    }

    /**
     * Returns the value of transition id.
     * 
     * @return The id value.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set method for <code>id</code> attribute.
     * 
     * @param id The id to set.
     */
    public void setId(int index)
    {
        this.id = index;
    }

    /**
     * Returns the transition value.
     * 
     * @return The value.
     */
    public TVALUE getValue()
    {
        return value;
    }

    /**
     * Sets the value of the transition.
     * 
     * @param value The new value to be set.
     */
    public void setValue(TVALUE value)
    {
        this.value = value;
    }

    /**
     * Returns the destination state of the transition.
     * 
     * @return The destination state.
     */
    public State<SVALUE, TVALUE> getTo()
    {
        return to;
    }

    /**
     * Sets the destination state of the transition.
     * 
     * @param from The new value for destination state.
     */
    public void setTo(State<SVALUE, TVALUE> to)
    {
        this.to = to;
        this.to.addInTransition(this);
    }

    /**
     * It calls the <code>toString</code> of the <code>value</code> attribute.
     */
    
    public String toString()
    {
        return value.toString();
    }

    /**
     * Verifies if the object passed as parameter has the same id of the current instance.
     */
    
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Transition)
        {
            Transition t = (Transition) obj;
            result = t.getId() == this.getId();
        }

        return result;
    }
}
