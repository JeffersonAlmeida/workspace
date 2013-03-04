/*
 * @(#)State.java
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

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a state in a graph.
 * 
 * <pre>
 * CLASS:
 * Represents a state in a graph that has a value of type SVALUE and that works with 
 * transitions of type TVALUE.
 * 
 * RESPONSIBILITIES:
 * 1) Keeps track of the incoming and outgoing transitions of the state
 * 2) Stores an id and a value associated with the state
 *
 * COLABORATORS:
 * 1) Contains two List of Transitions objects (one for incoming other for 
 * outgoing transitions)
 *
 * USAGE:
 * State<String, FlowStep> root = new State<String, FlowStep>();
 * root.setValue("root");
 * root.setId(idValue);
 * lts.setRoot(root);
 * lts.insertState(root);
 */
public class State<SVALUE, TVALUE>
{

    /** Id of the state */
    private int id;

    /** Value of the state */
    private SVALUE value;

    /** List of incoming transitions of the state */
    private Set<Transition<TVALUE, SVALUE>> inTransitions;

    /** List of outgoing transitions of the state */
    private Set<Transition<TVALUE, SVALUE>> outTransitions;

    /**
     * Default Constructor. Initializes the attributes to a default value.
     */
    public State()
    {
        id = 0;
        value = null;
        inTransitions = new LinkedHashSet<Transition<TVALUE, SVALUE>>();
        outTransitions = new LinkedHashSet<Transition<TVALUE, SVALUE>>();
    }

    /**
     * Adds an incoming transition to the state.
     * 
     * @param inTransition The in transition to be added.
     */
    public void addInTransition(Transition<TVALUE, SVALUE> inTransition)
    {
        this.inTransitions.add(inTransition);
    }

    /**
     * Removes an incoming transition to the state. The removing operation is performed based on
     * equals method.
     * 
     * @param inTransition The in transition to be removed.
     */
    void removeInTransition(Transition<TVALUE, SVALUE> inTransition)
    {
        this.inTransitions.remove(inTransition);
    }

    /**
     * Returns the id of the state.
     * 
     * @return The state id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Set the value of state id.
     * 
     * @param id The new value for the id.
     */
    void setId(int id)
    {
        this.id = id;
    }

    /**
     * Returns the value of the state.
     * 
     * @return The state value.
     */
    public SVALUE getValue()
    {
        return value;
    }

    /**
     * Sets the value of the state.
     * 
     * @param label The new value of the state.
     */
    void setValue(SVALUE value)
    {
        this.value = value;
    }

    /**
     * Adds an outgoing transition to the state.
     * 
     * @param outTransition The out transition to be added.
     */
    public void addOutTransition(Transition<TVALUE, SVALUE> outTransition)
    {
        this.outTransitions.add(outTransition);
    }

    /**
     * Removes an outgoing transition to the state. The removing operation is performed based on
     * equals method.
     * 
     * @param outTransition The out transition to be removed.
     */
    void removeOutTransition(Transition<TVALUE, SVALUE> outTransition)
    {
        this.outTransitions.remove(outTransition);
    }

    /**
     * Gets the outgoing transitions of the state.
     * 
     * @param oedges The list of outgoing transitions.
     */
    public List<Transition<TVALUE, SVALUE>> getOutTransitions()
    {
        return new ArrayList<Transition<TVALUE, SVALUE>>(this.outTransitions);
    }

    /**
     * Returns the list of incoming transitions of the state.
     * 
     * @return The list of incoming transitions.
     */
    public List<Transition<TVALUE, SVALUE>> getInTransitions()
    {
        return new ArrayList<Transition<TVALUE, SVALUE>>(this.inTransitions);
    }

    /**
     * Retrieves the number of output transitions.
     * 
     * @return The number of output transitions.
     */
    public int getOutTransitionsSize()
    {
        return this.outTransitions.size();
    }

    /**
     * Retrieves the number of input transitions.
     * 
     * @return The number of input transitions.
     */
    public int getInTransitionsSize()
    {
        return this.inTransitions.size();
    }

    /**
     * Compares an object to the current state.
     * 
     * @return <i>True</i> if the given object is equal.
     */
    public boolean equals(Object n)
    {
        boolean equal = false;

        if (n instanceof State)
        {
            equal = ((State) n).getId() == this.getId();
        }

        return equal;
    }

    /**
     * It calls the <code>toString</code> of the <code>value</code> attribute.
     */

    public String toString()
    {
        return value.toString();
    }
}
