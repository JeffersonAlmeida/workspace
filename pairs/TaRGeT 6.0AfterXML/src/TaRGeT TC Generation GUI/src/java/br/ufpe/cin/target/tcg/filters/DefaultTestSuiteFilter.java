/*
 * @(#)DefaultTestSuiteFilter.java
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
 * wdt022    Jun 05, 2007   LIBmm42774   Initial creation.
 * lmn3      Jan 18, 2009                Inclusion of equals() method.
 */
package br.ufpe.cin.target.tcg.filters;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;


/**
 * This class implements a test suite filter that does nothing. It only clones the received test
 * suite.
 */
public class DefaultTestSuiteFilter implements TestSuiteFilter<FlowStep>
{
    /**
     * This method does not change the input test suite. It only clones the input test suite and
     * delivers as output.
     */
    public TestSuite<TestCase<FlowStep>> filter(TestSuite<TestCase<FlowStep>> testSuite)
    {
        return new TestSuite<TestCase<FlowStep>>(testSuite.getTestCases(), testSuite.getName());
    }

    
    public boolean equals(TestSuiteFilter<FlowStep> filter)
    {
        return filter instanceof DefaultTestSuiteFilter;
    }

}
