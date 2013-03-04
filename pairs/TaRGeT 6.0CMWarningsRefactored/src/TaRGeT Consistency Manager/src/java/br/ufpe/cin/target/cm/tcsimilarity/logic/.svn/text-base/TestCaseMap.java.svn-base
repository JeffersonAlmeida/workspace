/*
 * @(#)TestCaseMap.java
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
 * faas      08/07/2008                  Initial creation.
 */
package br.ufpe.cin.target.cm.tcsimilarity.logic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * This class represents a table containing use cases references and their related test cases.
 */
public class TestCaseMap
{

    /**
     * The map that relates use cases references to a list of test cases.
     */
    private HashMap<String, List<TextualTestCase>> map;

    /**
     * Constructor for the class.
     * 
     * @param testCases The list of test cases to be put in the map.
     */
    public TestCaseMap(List<TextualTestCase> testCases)
    {

        map = new HashMap<String, List<TextualTestCase>>();

        for (TextualTestCase test : testCases)
        {
            String ucs = test.getUseCaseReferences();

            if (map.containsKey(ucs))
            {
                //INSPECT
                List<TextualTestCase> list = map.get(ucs);
                list.add(test);
                
                Collections.sort(list);
            }
            else
            {
                List<TextualTestCase> list = new ArrayList<TextualTestCase>();
                list.add(test);
                map.put(ucs, list);
            }

        }

    }

    /**
     * Gets all the test cases which are related to the given use case.
     * 
     * @param uc The use case to which the returned test cases must be related.
     * @return A list of test cases related to the given use case.
     */
    public List<TextualTestCase> getTestCases(String uc)
    {
        return map.get(uc);
    }

}
