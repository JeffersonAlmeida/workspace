/*
 * @(#)LTS.java
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
 * wdt022    Jul 22, 2007   LIBmm42774   Added the clone and prune methods.
 * dhq348    Aug 15, 2007   LIBmm42774   Added the merge of LTS.
 * dhq348    Aug 16, 2007   LIBmm42774   Added the method equals after inspection LX199806.
 * dhq348    Aug 21, 2007   LIBmm42774   Rework after inspection LX201094.
 * dhq348    Oct 26, 2007   LIBnn34008   Added transitions to the LTS structure.
 * wln013    Mar 27, 2008   LIBpp56482   Added the method removeTransitionFromState.
 * wln013    May 02, 2008   LIBpp56482   Rework after meeting 1 of inspection LX263835.
 */
package com.motorola.btc.research.target.common.lts;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Represents a graph of a Labeled Transition System (LTS).
 * 
 * <pre>
 * CLASS:
 * Represents a graph that has transitions of type TVALUE and states of type SVALUE.
 * 
 * RESPONSIBILITIES:
 * 1) Provides a map of IDs into states
 * 2) Provides a map of IDs into transitions
 *
 * COLABORATORS:
 * 1) uses State 
 * 2) uses Transition 
 *
 * USAGE:
 * LTS<FlowStep, String> lts = new LTS<FlowStep, String>();
 * lts.setRoot(root);
 * lts.insertState(root);
 */
public class LTS<TVALUE, SVALUE>
{

    /** Maps IDs into the respective state object */
    protected Map<Integer, State<SVALUE, TVALUE>> states;

    /** Maps IDs into the respective transition object */
    protected Map<Integer, Transition<TVALUE, SVALUE>> transitions;

    /** Reference for the root state */
    protected State<SVALUE, TVALUE> root;

    /**
     * The counter of the next transition.
     */
    protected static int nextTransitionId = 0;

    /**
     * The counter of the next state.
     */
    protected static int nextStateId = 0;

    /**
     * Constructs a <code>LTS</code> with empty set of states/transitions and with a default root
     * state.
     */
    protected LTS()
    {
        this(new State<SVALUE, TVALUE>());
    }

    /**
     * Constructs a <code>LTS</code> with empty set of states/transitions and with the given root
     * state.
     * 
     * @param root The <code>State</code> that represents the root state of the LTS
     */
    protected LTS(State<SVALUE, TVALUE> root)
    {
        this.states = new LinkedHashMap<Integer, State<SVALUE, TVALUE>>();
        this.transitions = new LinkedHashMap<Integer, Transition<TVALUE, SVALUE>>();
        this.root = root;
    }

    /**
     * Returns the root state of the LTS.
     * 
     * @return The root state.
     */
    public State<SVALUE, TVALUE> getRoot()
    {
        return root;
    }

    /**
     * Set the root state of the LTS.
     * 
     * @param root The new root.
     */
    public void setRoot(State<SVALUE, TVALUE> root)
    {
        this.root = root;
    }

    /**
     * Returns the iterator of the LTS´s transitions.
     * 
     * @return The iterator of LTS transitions.
     */
    public Iterator<Transition<TVALUE, SVALUE>> getTransitions()
    {
        return transitions.values().iterator();
    }

    /**
     * Returns the iterator of the LTS´s states.
     * 
     * @return The iterator of LTS states.
     */
    public Iterator<State<SVALUE, TVALUE>> getStates()
    {
        return states.values().iterator();
    }

    /**
     * Inserts the state into the LTS states map.
     * 
     * @param state The state to be inserted.
     */
    public void insertState(State<SVALUE, TVALUE> state)
    {
        states.put(new Integer(state.getId()), state);
    }

    /**
     * Inserts the transition into the LTS transitions map.
     * 
     * @param transition The transition to be inserted.
     */
    public void insertTransition(Transition<TVALUE, SVALUE> transition)
    {
        transitions.put(new Integer(transition.getId()), transition);
    }

    /**
     * Removes a transition from a given state.
     * 
     * @param transition The transition to be removed. It is contained in the state passed as
     * parameter. The transition is not contained in the current LTS.
     * @param state The state with the transition to be removed.
     * @param isInTransition True if it is an incoming transition to the state False if it an
     * outcoming transition.
     */

    public void removeTransitionFromState(Transition<TVALUE, SVALUE> transition,
            State<SVALUE, TVALUE> state, boolean isInTransition)
    {
        if (isInTransition)
        {
            state.removeInTransition(transition);
        }
        else
        {
            state.removeOutTransition(transition);
        }
    }

    /**
     * Returns the state which id is given.
     * 
     * @param id Id of the returned state.
     * @return The state which id is given.
     */
    public State<SVALUE, TVALUE> getState(int id)
    {
        return states.get(new Integer(id));
    }

    /**
     * Returns the transition which id is given.
     * 
     * @param id Id of the returned transition.
     * @return The transition which id is given.
     */
    public Transition<TVALUE, SVALUE> getTransition(int id)
    {
        return transitions.get(new Integer(id));
    }

    /**
     * This method removes some transitions from the LTS. It also verifies the impact of the
     * transition removal on states, i.e., if a state becomes unreachable (no transition goes in or
     * out). If there is an unreachable state, it is removed.
     * <p>
     * This method do not treat whether some parts of the LTS will be unconnected after the pruning.
     * It only removes the transitions passed as parameter, and removes states that got unconnected
     * after the transitions removal.
     * 
     * @param transitionsToPrune Collection of transitions that will be removed.
     */
    public void prune(Collection<Transition<TVALUE, SVALUE>> transitionsToPrune)
    {
        for (Transition<TVALUE, SVALUE> t : transitionsToPrune)
        {
            if (this.transitions.containsKey(t.getId()))
            {
                Transition<TVALUE, SVALUE> removedTransition = this.transitions.remove(t.getId());

                // Removing the transition from the to/from states
                removedTransition.getTo().removeInTransition(removedTransition);
                removedTransition.getFrom().removeOutTransition(removedTransition);

                // Verify if the to state is unreachable
                if (removedTransition.getTo().getInTransitionsSize() == 0
                        && removedTransition.getTo().getOutTransitionsSize() == 0)
                {
                    this.states.remove(removedTransition.getTo().getId());
                }
                // Verify if the from state is unreachable
                if (removedTransition.getFrom().getInTransitionsSize() == 0
                        && removedTransition.getFrom().getOutTransitionsSize() == 0)
                {
                    this.states.remove(removedTransition.getFrom().getId());
                }
            }
        }
    }

    /**
     * This method only clones the objects in the LTS class. This means that you can insert/remove
     * transitions and states from the cloned instance without changing the original instance. It
     * also clones the transitions and states objects.
     * <p>
     * However, the value attribute from states and transitions is not cloned.
     */

    public LTS<TVALUE, SVALUE> clone()
    {
        LTS<TVALUE, SVALUE> result = new LTS<TVALUE, SVALUE>();

        Map<Integer, State<SVALUE, TVALUE>> clonedStates = new LinkedHashMap<Integer, State<SVALUE, TVALUE>>();
        Map<Integer, Transition<TVALUE, SVALUE>> clonedTransitions = new LinkedHashMap<Integer, Transition<TVALUE, SVALUE>>();

        for (State<SVALUE, TVALUE> state : this.states.values())
        {
            State<SVALUE, TVALUE> clonedState = new State<SVALUE, TVALUE>();
            clonedState.setId(state.getId());
            clonedState.setValue(state.getValue());
            clonedStates.put(clonedState.getId(), clonedState);
        }

        for (Transition<TVALUE, SVALUE> transition : this.transitions.values())
        {
            Transition<TVALUE, SVALUE> clonedTransition = new Transition<TVALUE, SVALUE>();
            clonedTransition.setId(transition.getId());
            clonedTransition.setValue(transition.getValue());

            State<SVALUE, TVALUE> clonedToState = clonedStates.get(transition.getTo().getId());
            State<SVALUE, TVALUE> clonedFromState = clonedStates.get(transition.getFrom().getId());

            clonedTransition.setTo(clonedToState);
            clonedTransition.setFrom(clonedFromState);

            clonedTransitions.put(clonedTransition.getId(), clonedTransition);
        }

        result.states.putAll(clonedStates);
        result.transitions.putAll(clonedTransitions);

        result.root = clonedStates.get(this.root.getId());

        return result;
    }

    /**
     * Merges the current model with the one passed as parameter. Basically, iterates over all
     * transitions of <code>lts</code> and checks their existence in the current model. It also
     * checks the existence of their respective end points in the current model. If they are not
     * included then include them.
     * 
     * @param lts The model to be merged with the current one.
     */
    public void merge(LTS<TVALUE, SVALUE> lts)
    {
        Iterator<Transition<TVALUE, SVALUE>> toBeCheckedTransitionsIterator = lts.getTransitions();

        while (toBeCheckedTransitionsIterator.hasNext())
        {
            Transition<TVALUE, SVALUE> currentTransition = toBeCheckedTransitionsIterator.next();
            /* verifies if the current transition exists */
            if (this.transitions.get(currentTransition.getId()) == null)
            {
                Transition<TVALUE, SVALUE> newTransition = new Transition<TVALUE, SVALUE>();
                newTransition.setId(currentTransition.getId());
                newTransition.setValue(currentTransition.getValue());

                /* checks the existence of the from state */
                State<SVALUE, TVALUE> from = this.states.get(currentTransition.getFrom().getId());
                if (from == null)
                {
                    /*
                     * if it does not exist then creates a new one and sets the adds to it the new
                     * transition
                     */
                    from = new State<SVALUE, TVALUE>();
                    from.setId(currentTransition.getFrom().getId());
                    from.setValue(currentTransition.getFrom().getValue());
                    this.insertState(from);
                }
                newTransition.setFrom(from);

                /* checks the existence of the to state */
                State<SVALUE, TVALUE> to = this.states.get(currentTransition.getTo().getId());
                if (to == null)
                {
                    /*
                     * if it does not exist then creates a new one and sets the adds to it the new
                     * transition
                     */
                    to = new State<SVALUE, TVALUE>();
                    to.setId(currentTransition.getTo().getId());
                    to.setValue(currentTransition.getTo().getValue());
                    this.insertState(to);
                }
                newTransition.setTo(to);

                this.insertTransition(newTransition);
            }
        }
    }

    /**
     * Compares the current LTS object and the one passed as parameter.
     * 
     * <pre>
     * <ol>
     * <li>Checks if they have the same number of states and transitions.
     * <li>Checks if they have the same root state.
     * <li>Checks if all end points of all transitions are the same.
     * </ol>
     * </pre>
     */
    @SuppressWarnings("unchecked")

    public boolean equals(Object obj)
    {
        boolean result = false;

        if (obj instanceof LTS)
        {
            LTS<TVALUE, SVALUE> newoneLts = (LTS<TVALUE, SVALUE>) obj;

            /* both must have the same root state and the same number of states and transitions */
            if (this.root.equals(newoneLts.root)
                    && this.transitions.size() == newoneLts.transitions.size()
                    && this.states.size() == newoneLts.states.size())
            {
                result = true;
                for (Transition<TVALUE, SVALUE> transition : this.transitions.values())
                {
                    Transition<TVALUE, SVALUE> newoneTransition = newoneLts.getTransition(transition.getId());
                    if (newoneTransition == null
                            || !newoneTransition.getFrom().equals(transition.getFrom())
                            || !newoneTransition.getTo().equals(transition.getTo()))
                    {
                        result = false;
                        break;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Returns the next transition id and increments it.
     * 
     * @return The next transition id.
     */
    public static int getNextTransitionId()
    {
        return nextTransitionId++;
    }

    /**
     * Returns the next state id and increments it.
     * 
     * @return The next state id.
     */
    public static int getNextStateId()
    {
        return nextStateId++;
    }

    /**
     * This method is used only for tests. It should not be called from any other place. It resets
     * the variable that indicates the next transition id.
     */
    static void resetNextTransitionId()
    {
        nextTransitionId = 0;
    }

}
