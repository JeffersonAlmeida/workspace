/*
 * @(#)UseCasesFilter.java
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
 * dhq348    Nov 19, 2007   LIBoo10574   Initial creation.
 * dhq348    Jan 23, 2008   LIBoo10574   Rework after inspection LX229632.
 * wdt022    Mar 20, 2008   LIBpp56482   getUsecases method added. Constructor updated.
 * lmn3      Jan 18, 2009                Inclusion of equals() method.
 */
package br.ufpe.cin.target.tcg.filters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;


/**
 * This class is used to filter a Test Suite using the a set of user selected use cases.
 */
public class UseCasesFilter implements TestSuiteFilter<FlowStep>
{

    /**
     * A map with the selected use case.
     */
    private HashMap<Feature, List<UseCase>> useCases;

    /**
     * Creates an UseCasesFilter given a map with the user selected <code>usecases</code>.
     */
    public UseCasesFilter(HashMap<Feature, List<UseCase>> usecases)
    {
        this.useCases = new HashMap<Feature, List<UseCase>>();
        for (Feature f : usecases.keySet())
        {
            this.useCases.put(f, new ArrayList<UseCase>(usecases.get(f)));
        }
    }

    /**
     * Filter the specified test suite using the given use cases.
     * 
     * @see TestSuiteFilter.filter()
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        List<TestCase<FlowStep>> result = new ArrayList<TestCase<FlowStep>>();

        for (TestCase<FlowStep> testCase : testSuite.getTestCases())
        {
            if (containsUseCase(testCase))
            {
                result.add(testCase);
            }
        }

        return new TestSuite<TestCase<FlowStep>>(result, testSuite.getName());
    }

    /**
     * Checks if the given <code>testCase</code> is covered by at least one of the selected use
     * cases.
     * 
     * @param testCase The test case to be checked.
     * @return <code>true</code> if the test case is covered or <code>false</code> otherwise.
     */
    private boolean containsUseCase(TestCase<FlowStep> testCase)
    {
        boolean result = false;

        /* iterates over the test case flow steps */
        for (FlowStep flowStep : testCase.getSteps())
        {
            /* iterates over the keys (features) of the user selected use cases */
            for (Feature feature : useCases.keySet())
            {
                StepId stepId = flowStep.getId();
                /* if the feature IDs are the same then iterates over the use cases list */
                if (stepId.getFeatureId().equals(feature.getId()))
                {
                    for (UseCase usecase : useCases.get(feature))
                    {
                        /*
                         * if the use case id and the step use case id are the same then the given
                         * testCase is covered by one of the user selected use cases
                         */
                        if (stepId.getUseCaseId().equals(usecase.getId()))
                        {
                            result = true;
                            break;
                        }
                    }
                    if (result)
                    {
                        break;
                    }
                }
            }
            if (result)
            {
                break;
            }
        }

        return result;
    }

    /**
     * The set of use cases that were selected into this filter.
     * 
     * @return A hashmap relating a feature to one or more use cases.
     */
    public HashMap<Feature, List<UseCase>> getUsecases()
    {
        return this.useCases;
    }

    
    public boolean equals(TestSuiteFilter<FlowStep> filter)
    {
        if (filter instanceof UseCasesFilter)
        {
            return this.getUsecases().equals(((UseCasesFilter) filter).getUsecases());            
        }
        else
        {
            return false;
        }
    }

}
