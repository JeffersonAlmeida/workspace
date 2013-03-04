/*
 * @(#)UserViewLTSGenerator.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    16/10/2006     LIBkk11577   Initial creation.
 * wsn013    07/01/2006     LIBkk11577   Refactories.
 * wsn013    Jan 18, 2006   LIBkk11577   Modifications after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   LTS generation was changed to support new selection algorithms.
 * dhq348    Oct 26, 2007   LIBnn34008   Added interruption support.
 * dhq348    Jan 16, 2008   LIBnn34008   Rework after inspection LX229627.
 */
package br.ufpe.cin.target.common.lts;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;

/**
 * This class generates a graph model from use case documents that describe a user view.
 * 
 * <pre>
 * CLASS:
 * This class generates a LTS<FlowStep, Integer> from use case documents that describes a user view.
 * 
 * RESPONSIBILITIES:
 * 1) Generates a LTS<String,FlowStep> graph that models the behavior described in the use case documents.
 *
 * COLABORATORS:
 * 1) uses UseCaseDocument
 * 2) uses StepId
 * 3) uses FlowStep
 * 4) uses LTS
 * 5) uses State
 * 6) uses Transition
 *
 * USAGE:
 * UserViewLTSGenerator gen = new UserViewLTSGenerator(useCaseDocuments);
 * LTS<FlowStep, Integer> lts = gen.generateLTS();
 */
public class UserViewLTSGenerator
{
    /** The value of the final transition */
    public static final FlowStep END_STEP = new FlowStep(StepId.END, "", "", "",
            new HashSet<String>());

    /** Mapping of a document step id to a set of step ids that can be reached by the first */
    private Map<StepId, Set<StepId>> neighborStepsMap;

    /** Mapping of a document step id to its FlowStep object */
    private Map<StepId, FlowStep> docStepsMap;

    /**
     * Initializes the mapping and the incremental indexes.
     */
    protected void init()
    {
        this.neighborStepsMap = new LinkedHashMap<StepId, Set<StepId>>();
        this.docStepsMap = new LinkedHashMap<StepId, FlowStep>();
    }

    /**
     * Return the next unique id for the LTS transitions.
     * 
     * @return The next id.
     */
    private int nextTransitionId()
    {
        return LTS.getNextTransitionId();
    }

    /**
     * Return the next unique id for the LTS states.
     * 
     * @return The next id.
     */
    private int nextStateId()
    {
        return LTS.getNextStateId();
    }

    /**
     * This method creates a LTS model from the use case documents.
     * 
     * @param userViewDocuments A list of the use case documents that are the input to create a LTS.
     * @return Returns the LTS model.
     */
    public LTS<FlowStep, Integer> generateLTS(Collection<UseCaseDocument> userViewDocuments)
    {
        this.init();
        this.prepareFeatureUseCaseMaps(userViewDocuments);
        LTS<FlowStep, Integer> lts = new LTS<FlowStep, Integer>();
        return this.generateLTS(lts);
    }

    /**
     * Auxiliary method that fills an empty <code>lts</code>. Sets the root state and the end
     * state of the graph. Uses <code>neighborStepsMap</code> to create transitions between states
     * in the graph.
     * 
     * @param lts The LTS in which the states and transitions are inserted.
     * @return The LTS in which the states and transitions are inserted.
     */
    protected LTS<FlowStep, Integer> generateLTS(LTS<FlowStep, Integer> lts)
    {
        // queue of the document step ids that are waiting to be processed
        Queue<StepId> stepsQueue = new LinkedList<StepId>();

        // mapping of a step id to the LTS state
        Map<StepId, State<Integer, FlowStep>> stepStateMap = new LinkedHashMap<StepId, State<Integer, FlowStep>>();

        // the root state of the graph
        State<Integer, FlowStep> root = new State<Integer, FlowStep>();
        int stateId = nextStateId();
        root.setValue(stateId);
        root.setId(stateId);
        lts.setRoot(root);
        lts.insertState(root);

        // the final state of the graph
        State<Integer, FlowStep> end = new State<Integer, FlowStep>();
        stateId = nextStateId();
        end.setValue(stateId);
        end.setId(stateId);
        lts.insertState(end);

        stepStateMap.put(StepId.START, root);
        stepStateMap.put(StepId.END, end);

        stepsQueue.add(StepId.START);

        while (!stepsQueue.isEmpty())
        {
            StepId currentDocumentStepId = stepsQueue.poll();
            Collection<StepId> nextStepIds = this.neighborStepsMap.get(currentDocumentStepId);
            State<Integer, FlowStep> currentState = stepStateMap.get(currentDocumentStepId);

            for (StepId nextStepId : nextStepIds)
            {
                State<Integer, FlowStep> nextState = stepStateMap.get(nextStepId);
                if (nextState == null)
                {
                    nextState = new State<Integer, FlowStep>();
                    int id = this.nextStateId();
                    nextState.setId(id);
                    nextState.setValue(id);
                    lts.insertState(nextState);
                    stepsQueue.add(nextStepId);
                    stepStateMap.put(nextStepId, nextState);
                }

                Transition<FlowStep, Integer> transition = new Transition<FlowStep, Integer>();
                transition.setId(this.nextTransitionId());
                transition.setFrom(currentState);
                lts.insertTransition(transition);
                if (!nextStepId.equals(StepId.END))
                {
                    transition.setValue(this.docStepsMap.get(nextStepId));
                    transition.setTo(nextState);
                }
                else
                {
                    transition.setValue(END_STEP);
                    transition.setTo(end);
                }
            }
        }

        return lts;
    }

    /**
     * Creates a map of connections between the document steps. Populates the the atributes
     * <code>neighborStepsMap</code> and <code>docStepsMap</code>.
     * 
     * @param userViewDocuments The use case documents used to create the map.
     */
    private void prepareFeatureUseCaseMaps(Collection<UseCaseDocument> userViewDocuments)
    {
        /* Each "from step" determine a link between the "from step" to the first step of a flow. */
        for (UseCaseDocument phoneDoc : userViewDocuments)
        {
            /* for each feature of a document */
            for (Feature feature : phoneDoc.getFeatures())
            {
                /* for each use case of a feature */
                for (UseCase useCase : feature.getUseCases())
                {
                    /* for each flow of a use case */
                    for (Flow flow : useCase.getFlows())
                    {
                        this.linkSteps(flow);
                    }
                }
            }
        }
    }

    /**
     * Auxiliary method that populates the <code>neighborStepsMap</code> hash map with information
     * about the links between the steps of a given flow. It also takes into cosinderation the from
     * and to steps of a flow.
     * 
     * @param flow The flow from which the steps will be retrieved.
     */
    protected void linkSteps(Flow flow)
    {
        FlowStep firstFlowStep = this.getFirstStepFromFlow(flow);

        // for each step of the "From step" field
        for (StepId stepId : flow.getFromSteps())
        {
            // set the first step of the flow as its neighbor
            this.addToMap(this.neighborStepsMap, stepId, firstFlowStep.getId());
        }

        List<FlowStep> stepsList = flow.getSteps();

        // first step of the flow
        FlowStep step = stepsList.get(0);

        // for each step of the list
        for (int i = 1; i < stepsList.size(); i++)
        {
            // the subsequent step
            FlowStep nextStep = stepsList.get(i);

            // set the next as a neighbor
            this.addToMap(this.neighborStepsMap, step.getId(), nextStep.getId());

            // populates docStepsMap
            this.docStepsMap.put(step.getId(), step);

            step = nextStep;
        }

        // updated docStepsMap with the last flow's step
        this.docStepsMap.put(step.getId(), step);

        for (StepId stepId : flow.getToSteps())
        {
            // set each step as a neighbor
            this.addToMap(this.neighborStepsMap, step.getId(), stepId);
        }
    }

    /**
     * Updates the map value associated with the given key.
     * 
     * @param map The map whose value will be updated.
     * @param key The key used to specify the map value (set) to be updated.
     * @param newValue The new value.
     */
    private void addToMap(Map<StepId, Set<StepId>> map, StepId key, StepId newValue)
    {
        // map value to be updated
        Set<StepId> valueToBeUpdated = null;

        // if there is a value for this key
        if (map.containsKey(key))
        {
            // get the reference to the value
            valueToBeUpdated = map.get(key);
        }
        else
        {
            // initialize the map value for this key keeping the order of each inserted value
            valueToBeUpdated = new LinkedHashSet<StepId>();
            map.put(key, valueToBeUpdated);
        }

        // update the value by the insertion of newValue
        valueToBeUpdated.add(newValue);
    }

    /**
     * Gets the id from the first step from the given document flow.
     * 
     * @param flow The document flow.
     * @return The first Step from the given flow.
     */
    private FlowStep getFirstStepFromFlow(Flow flow)
    {
        FlowStep firstStepId = null;
        List<FlowStep> steps = flow.getSteps();
        if (steps.size() > 0)
        {
            firstStepId = steps.get(0);
        }
        return firstStepId;
    }
}
