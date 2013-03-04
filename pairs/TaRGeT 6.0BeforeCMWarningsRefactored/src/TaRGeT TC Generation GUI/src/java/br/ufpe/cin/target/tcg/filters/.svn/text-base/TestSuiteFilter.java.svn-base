/*
 * @(#)TestSuiteFilter.java
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
 * wdt022    May 29, 2007   LIBmm42774   Initial creation.
 * wdt022    Aug 14, 2007   LIBmm42774   Modification due to inspection LX198758. Renamed from AbstractTestSuiteFilter.
 * lmn3      Jan 18, 2009                Inclusion of equals() method.
 */
package br.ufpe.cin.target.tcg.filters;

import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;

/** 
 *
 * This interface represents an abstract test suite filter. Its main purpose is provide
 * an interface for a filter implementation that receives as input a test suite
 * and delivers as output a new test suite with fewer test cases.
 * <p>
 * The interface contains a method that must be implemented with the desired filter algorithm.
 * 
 */
public interface TestSuiteFilter<STEP>
{
    /**
     * This method represents any filter that may be performed on a test suite.
     * The filter consists in removing some test cases from the input test suite
     * and delivering a new test suite. 
     * 
     * @param testSuite The test suite that will be filtered.
     * @return The filtered test suite.
     */
    public TestSuite<TestCase<STEP>> filter(TestSuite<TestCase<STEP>> testSuite);
    
    /**
     *     
     * Compares two instances of <code>TestSuiteFilter<STEP></code> filters.
     * @param filter the filter to be compared
     * @return the result of the comparison
     */
    public boolean equals(TestSuiteFilter<STEP> filter);
}
