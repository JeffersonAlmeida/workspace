/*
 * @(#)ORTestSuiteFilter.java
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
 * dhq348   Aug 27, 2007    LIBmm42774   Initial creation.
 * dhq348   Aug 28, 2007    LIBmm42774   Rework after inspection LX203164.
 * lmn3      Jan 18, 2009                Inclusion of equals() method.
 */
package br.ufpe.cin.target.tcg.filters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;


/**
 * This class represents a filter that joins together the result of other filter. It represents an
 * 'OR' operation on the result test suite.
 */
public class ORTestSuiteFilter implements TestSuiteFilter<FlowStep>
{

    /**
     * A list of filters.
     */
    private List<TestSuiteFilter<FlowStep>> filters;

    /**
     * Creates an <code>ORTestSuiteFilter</code>.
     * 
     * @param filters The list of filters.
     */
    public ORTestSuiteFilter(List<TestSuiteFilter<FlowStep>> filters)
    {
        this.filters = new ArrayList<TestSuiteFilter<FlowStep>>(filters);
    }

    /**
     * The test suite passed as parameter is filtered by each filter contained in the list
     * <code>filters</code>. The result of each filter is joined together, excluding duplicated
     * test cases (like a set).
     * 
     * @param testSuite The suite whose test cases will be filtered.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        Set<TestCase<FlowStep>> testCases = new HashSet<TestCase<FlowStep>>();

        for (TestSuiteFilter<FlowStep> filter : filters)
        {
            TestSuite<TestCase<FlowStep>> currentSuite = filter.filter(testSuite);
            testCases.addAll(currentSuite.getTestCases());
        }

        TestSuite<TestCase<FlowStep>> newSuite = new TestSuite<TestCase<FlowStep>>(
                new ArrayList<TestCase<FlowStep>>(testCases), testSuite.getName());
        return this.sortTestCases(newSuite);
    }

    /**
     * Sorts the test cases contained in <code>suite</code> attribute based on test case Id. See
     * <code>TestCase.compareTo()</code> method.
     * 
     * @param suite The suite whose test cases will be sorted.
     * @return A new test suite with sorted test cases.
     */
    private TestSuite<TestCase<FlowStep>> sortTestCases(TestSuite<TestCase<FlowStep>> suite)
    {
        List<TestCase<FlowStep>> testCases = suite.getTestCases();
        Collections.sort(testCases);
        return new TestSuite<TestCase<FlowStep>>(testCases, suite.getName());
    }

    /**
     * Gets the filters value.
     *
     * @return Returns the filters.
     */
    public List<TestSuiteFilter<FlowStep>> getFilters()
    {
        return filters;
    }

    /**
     * Sets the filters value.
     *
     * @param filters The filters to set.
     */
    public void setFilters(List<TestSuiteFilter<FlowStep>> filters)
    {
        this.filters = filters;
    }
    
    
    public boolean equals(TestSuiteFilter<FlowStep> composedFilter)
    {
        boolean result = true;

        if (composedFilter instanceof ORTestSuiteFilter)
        {
            for (TestSuiteFilter<FlowStep> filter : ((ORTestSuiteFilter) composedFilter)
                    .getFilters())
            {
                if (!this.filters.contains(filter))
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
        return result;
    }
}
