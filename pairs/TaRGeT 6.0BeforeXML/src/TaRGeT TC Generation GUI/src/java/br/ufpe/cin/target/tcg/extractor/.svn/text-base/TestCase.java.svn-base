/*
 * @(#)TestCase.java
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
 * wsn013    Jan 8, 2007    LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 * dhq348    Aug 16, 2007   LIBmm42774   Added method equals after inspection LX199806.
 * dhq348    Aug 27, 2007   LIBmm42774   Added method compareTo().
 * dwvm83	 Oct 02, 2008	LIBqq51204   Added method setId.
 * fsf2      Jun 29, 2009                Added method equals().
 */
package br.ufpe.cin.target.tcg.extractor;

import java.util.List;

/**
 * Encapsulates a test case with generic step type. Also denoted as a raw test case.
 * 
 * <pre>
 * CLASS:
 * Encapsulates a test case which steps are of type STEP. Also denoted as a raw test case.
 * 
 * RESPONSIBILITIES:
 * 1) Keep track of the id, steps and execution type of the test case
 *
 * COLABORATORS:
 * N/A
 *
 * USAGE:
 * TestCase<FlowStep> tc = new TestCase<FlowStep>();
 * tc.setId(newId);
 * tc.setSteps(graphTransitions);
 * testSuite.add(tc);
 */
public class TestCase<STEP> implements Comparable<TestCase>
{

    /** The integer id of the test case */
    private int id; 

    /** Test case steps of type STEP */
    protected List<STEP> steps;

    /**
     * Constructs the test case from the id and its steps.
     * 
     * @param id The test id.
     * @param steps The test steps.
     */
    public TestCase(int id, List<STEP> steps)
    {
        super();
        this.id = id;
        this.steps = steps;
    }

    /**
     * Gets the id of the test case.
     * 
     * @return The test case id.
     */
    public int getId()
    {
        return id;
    }

    /**
     * Gets the steps from the test case.
     * 
     * @return The test case steps.
     */
    public List<STEP> getSteps()
    {
        return steps;
    }

    /**
     * Compares the current test case with <code>obj</code> and returns <code>true</code> if
     * they have the same id.
     * 
     * @param obj The object to be compared.
     * @return <code>true/code> if the objects have the same id or <code>false</code> otherwise.
     */
    
    public boolean equals(Object obj)
    {
        boolean retorno = false;
        
        if (obj instanceof TestCase)
        {
            TestCase tc = (TestCase) obj;
            
            if(isEquals(this.id, tc.id) &&
                    isEquals(this.steps, tc.steps))
            {
                retorno = true;
            }
            
        }
        
        
        
        
        
        return retorno;
    }

    /**
     * Compares the given test case with the current one.
     * 
     * @param testCase The test case to be compared with the current one.
     */
    public int compareTo(TestCase testCase)
    {
        if (!(testCase instanceof TestCase))
        {
            throw new ClassCastException("A TesCase object is expected.");
        }

        int result = -1;
        if (this.id == testCase.id)
        {
            result = 0;
        }
        else if (this.id > testCase.id)
        {
            result = 1;
        }

        return result;
    }

    /**
     * Sets the id of the test case.
     * 
     * @param The test case id.
     */

	public void setId(int id) {
		this.id = id;
	}
	
    private boolean isEquals(Object obj1, Object obj2)
    {
        boolean retorno = true;
        
        if(obj1 == null ^ obj2 == null)
        {
            retorno = false;
        }
        else
        {
            retorno = (obj1 != null) ? obj1.equals(obj2) : true;
        }
        
        return retorno;
    }

}
