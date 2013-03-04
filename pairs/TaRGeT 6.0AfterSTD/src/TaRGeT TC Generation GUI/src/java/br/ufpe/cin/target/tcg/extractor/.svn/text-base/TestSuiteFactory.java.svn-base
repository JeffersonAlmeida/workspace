/*
 * @(#)TestSuiteFactory.java
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
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2006   LIBkk11577   Modifications after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Modifications according to CR.
 */
package br.ufpe.cin.target.tcg.extractor;

import br.ufpe.cin.target.common.lts.LTS;
import br.ufpe.cin.target.tcg.exceptions.TestGenerationException;


/**
 * This interface is implemented by test case generation algorithms.
 * 
 * <pre>
 * INTERFACE:
 * It is an interface that contains only one method. This method receives as input a 
 * LTS model, and delivers as output a test suite.
 * 
 * RESPONSIBILITIES:
 * 1) Provides a unique interface for the test generation algorithm.
 *
 * COLABORATORS:
 * 2) Uses TestSuite
 * <pre>
 */
public interface TestSuiteFactory<STEP>
{

    /**
     * Generates a test suite from an input LTS model.
     * 
     * @param The input LTS model.
     * @return The generated test suite.
     * @throws TestGenerationException Thrown in case of any error during the test generation.
     */
    public TestSuite<TestCase<STEP>> create(LTS<STEP, Integer> model)
            throws TestGenerationException;

}
