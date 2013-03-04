/*
 * @(#)CompositionTestSuiteFilter.java
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
 * wdt022    Aug 08, 2007   LIBmm42774   Initial creation.
 */
package br.ufpe.cin.target.tcg.filters;

import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;


/**
 * This class represents a filter that is assembled by the composition of other filters. For
 * instance, the composition of two test suites means that the result from one filter is passed as
 * parameter to the filter.
 * <p>
 * It is possible to compose <b>N</b> filters. This means that the result from filter <b>1</b> is
 * passed to filter <b>2</b>, the result of filter <b>2</b> is passed to filter <b>3</b> and so
 * on.
 */
public class CompositionTestSuiteFilter implements TestSuiteFilter<FlowStep>
{
    /** The list of filters that will be composed */
    private List<TestSuiteFilter<FlowStep>> composedFilter;

    /**
     * Constructor that receives the list of filters that will compose a major filter. The
     * composition is performed from the beginning to the end of the filter list.
     * 
     * @param filters The list of filters to be composed.
     */
    public CompositionTestSuiteFilter(List<TestSuiteFilter<FlowStep>> filters)
    {
        this.composedFilter = new ArrayList<TestSuiteFilter<FlowStep>>(filters);
    }

    /**
     * Composes the filters contained in the attribute <code>composedFilter</code>.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        for (TestSuiteFilter<FlowStep> filter : this.composedFilter)
        {
            testSuite = filter.filter(testSuite);
        }
        return testSuite;
    }
    
    /**
     * Gets the composedFilter value.
     *
     * @return Returns the composedFilter.
     */
    public List<TestSuiteFilter<FlowStep>> getComposedFilter()
    {
        return composedFilter;
    }

    /**
     * Sets the composedFilter value.
     *
     * @param composedFilter The composedFilter to set.
     */
    public void setComposedFilter(List<TestSuiteFilter<FlowStep>> composedFilter)
    {
        this.composedFilter = composedFilter;
    }

    
    public boolean equals(TestSuiteFilter<FlowStep> composedFilter)
    {
        boolean result = true;
        
        if(composedFilter instanceof CompositionTestSuiteFilter)
        {
            for (TestSuiteFilter<FlowStep> filter : ((CompositionTestSuiteFilter)composedFilter).getComposedFilter())
            {
                if(!this.composedFilter.contains(filter))
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
