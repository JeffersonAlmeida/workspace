/*
 * @(#)CNLFault.java
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
 * wxx###   Mar 12, 2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.controller;

import org.eclipse.jface.viewers.TextCellEditor;

import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.target.common.ucdoc.StepId;


/**
 * This class represents a CNL process fault.
 * @author 
 *
 */
public abstract class CNLFault
{
    private String sentence;

    private StepId step;

    private String resource;
    
    private TestCaseTextType field;
    
    TextCellEditor test = null;
    
    /**
     * The class constructor.
     * @param sentence the sentence that contains the fault
     * @param step the step where the fault occurred.
     * @param resource the document that contains the fault
     * @param field the test case text type
     */
    protected CNLFault(String sentence, StepId step, String resource, TestCaseTextType field)
    {
        this.step = step;
        this.resource = resource;
        this.sentence = sentence;
        this.field = field;
    }
    
    /**
     * This method returns the sentence attribute.
     * @return the sentence attribute.
     */
    public String getSentence()
    {
        return this.sentence;
    }
    
    /**
     * This method returns the step attribute.
     * @return the step attribute.
     */
    public String getStep()
    {
        return this.step.toString();
    }

    /**
     * This method returns the resource attribute.
     * @return the resource attribute.
     */
    public String getResource()
    {
        return this.resource;
    }
    
    /**
     * This method returns the fault details.
     * @return the fault details
     */
    public abstract String getDetails();

    /**
     * This method returns the field attribute.
     * @return the field attribute.
     */
    public TestCaseTextType getField()
    {
        return this.field;
    }
}
