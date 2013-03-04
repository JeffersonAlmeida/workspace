/*
 * @(#)TestSuite.java
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
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Modifications according to CR.
 * fsf2      Jun 29, 2009                Added method equals().
 */
package br.ufpe.cin.target.tcg.extractor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represents a suite of tests of type <T extends TestCase>.
 * It contains a list of revisions containing the revisions of all use case documents in the test suite.
 * 
 * <pre>
 * CLASS:
 * This class represents a suite of tests of type <T extends TestCase>.
 * 
 * RESPONSIBILITIES:
 * 1) Provide a way to organize the test cases.
 * 
 * COLABORATORS:
 * 1) Uses TestCase
 *
 * USAGE:
 *  TestSuite<TextualTestCase> textualTs = new TestSuite<TextualTestCase>(new ArrayList<TextualTestCase>());
 *  textualTs.insertTest(textualTest);
 */
public class TestSuite<T extends TestCase>
{

    /** Name of the test suite */
    private String name;

    /** Test cases of the suite */
    private List<T> testCases;
    
    /**
     * Constructs a test suite using the given test cases.
     * 
     * @param testCases The test cases of the suite
     */
    public TestSuite(List<T> testCases)
    {
        this.testCases = new ArrayList<T>(testCases);
    }
    
    /** 
     * Constructs a test suite using the given test cases and its name.
     * 
     * @param testCases The test cases of the suite
     * @param name The name of the test suite
     */
    public TestSuite(List<T> testCases, String name)
    {
        this.testCases = new ArrayList<T>(testCases);
        this.name = name;
    }

    /**
     * Retrieves the test cases of the suite.
     * 
     * @return The test cases.
     */
    public List<T> getTestCases()
    {
        return this.testCases;
    }

    /**
     * Insert a test into the suite.
     * 
     * @param test The test to be inserted.
     */
    public void insertTest(T test)
    {
        this.testCases.add(test);
    }

    /**
     * Returns a string representing each of the test cases of the test suite.
     */
    
    public String toString()
    {
        StringBuffer sb = new StringBuffer("");
        Iterator<T> i = testCases.iterator();
        while (i.hasNext())
        {
            T tc = i.next();
            sb.append(tc.toString());
            sb.append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Retrieves the name of the suite.
     * 
     * @return The suite name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name of the suite.
     * 
     * @param name The new test suite name.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    
    public boolean equals(Object obj)
    {
        boolean retorno = false;
        
        if (obj instanceof TestSuite)
        {
            TestSuite ts = (TestSuite) obj;
            
            if(this.isEquals(this.name, ts.name) &&
                    this.isEquals(this.testCases, ts.testCases))
            {
                retorno = true;
            }
        }
        
        return retorno;
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
