/*
 * @(#)OnTheFlyEditorInput.java
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
 * wdt022   Mar 25, 2008    LIBpp56482   Initial creation.
 * fsf2     Jun 29, 2009                 Added method equals().
 */
package br.ufpe.cin.target.onthefly.editors;

import java.util.HashMap;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * <pre>
 * CLASS:
 * Represents an editor input for On The Fly test case generation.
 * 
 * RESPONSIBILITIES:
 * Provides whole test suites for On The Fly FormPages: OnTheFlyGeneratedTestCasesPage, 
 * OnTheFlyTestSelectionPage, OnTheFlyTraceabilityMatrix 
 *         
 * </pre>
 */
public class OnTheFlyEditorInput implements IEditorInput
{
    /**
     * The whole test suite with test cases in raw format.
     */
    private TestSuite<TestCase<FlowStep>> rawTestCases;

    /**
     * The whole test suite with test cases in textual format.
     */
    private TestSuite<TextualTestCase> textualTestCases;

    /**
     * The map with relation test case id - textual test case
     */
    public HashMap<Integer, TextualTestCase> idToTestCaseMap;

    /**
     * The map with relation test case id - raw test case
     */
    public HashMap<Integer, TestCase<FlowStep>> idToRawTestCaseMap;
    
    public OnTheFlyEditorInput() {
		// TODO Auto-generated constructor stub
	}

    /**
     * Creates an editor input for On The Fly test case generation. This class provides the test
     * suites for On The Fly form pages.
     * 
     * @param rawTestCases The whole test suite with test cases in raw format.
     * @param textualTestCases The whole test suite with test cases in textual format.
     */
    public OnTheFlyEditorInput(TestSuite<TestCase<FlowStep>> rawTestCases,
            TestSuite<TextualTestCase> textualTestCases)
    {
        this.rawTestCases = rawTestCases;
        this.textualTestCases = textualTestCases;

        this.idToTestCaseMap = new HashMap<Integer, TextualTestCase>();
        for (TextualTestCase testCase : textualTestCases.getTestCases())
        {
            this.idToTestCaseMap.put(testCase.getId(), testCase);
        }

        this.idToRawTestCaseMap = new HashMap<Integer, TestCase<FlowStep>>();
        for (TestCase<FlowStep> testCase : rawTestCases.getTestCases())
        {
            this.idToRawTestCaseMap.put(testCase.getId(), testCase);
        }

    }

    /**
     * @see IEditorInput#exists()
     * @return false.
     */
    public boolean exists()
    {
        return false;
    }

    /**
     * @see IEditorInput#getImageDescriptor()
     * @return null (there is no image descriptor).
     */
    public ImageDescriptor getImageDescriptor()
    {
        return null;
    }

    /**
     * @see IEditorInput#getName()
     * @return An empty string.
     */
    public String getName()
    {
        return "";
    }

    /**
     * @see IEditorInput#getPersistable()
     * @return null (this editor input cannot be persisted).
     */
    public IPersistableElement getPersistable()
    {
        return null;
    }

    /**
     * @see IEditorInput#getToolTipText()
     * @return An empty string.
     */
    public String getToolTipText()
    {
        return "";
    }

    /**
     * @see IAdaptable#getAdapter(Class)
     * @return null (this object does not have an adapter for the given class).
     */
    @SuppressWarnings("unchecked")
    public Object getAdapter(Class adapter)
    {
        return null;
    }

    /**
     * Gets a test suite with test cases in raw format.
     * 
     * @return The test suite with raw test cases.
     */
    public TestSuite<TestCase<FlowStep>> getRawTestSuite()
    {
        return this.rawTestCases;
    }

    /**
     * Gets a test suite with test cases in textual format.
     * 
     * @return The test suite with textual test cases.
     */
    public TestSuite<TextualTestCase> getTextualTestSuite()
    {
        return this.textualTestCases;
    }

    /**
     * Gets a textual test case.
     * 
     * @param id The test case id.
     * @return The textual test case.
     */
    public TextualTestCase getTextualTestCase(int id)
    {
        return this.idToTestCaseMap.get(id);
    }

    /**
     * Gets a raw test case.
     * 
     * @param id The test case id.
     * @return The raw test case.
     */
    public TestCase<FlowStep> getRawTestCase(int id)
    {
        return this.idToRawTestCaseMap.get(id);
    }
    
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
    
    public boolean equals(Object obj)
    {
        boolean retorno = false;
        
        if (obj instanceof OnTheFlyEditorInput)
        {
            OnTheFlyEditorInput onTheFlyEditorInput = (OnTheFlyEditorInput) obj;
            
            if(this.isEquals(this.rawTestCases,onTheFlyEditorInput.rawTestCases) &&
                    this.isEquals(this.textualTestCases, onTheFlyEditorInput.textualTestCases)&&
                    this.isEquals(this.idToTestCaseMap, onTheFlyEditorInput.idToTestCaseMap) &&
                    this.isEquals(this.idToRawTestCaseMap, onTheFlyEditorInput.idToRawTestCaseMap))
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
