/*
 * @(#)CNLLexionFault.java
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

import java.util.Set;

import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.target.common.ucdoc.StepId;


/**
 * This method represents a CNL lexion fault.
 * @author
 *
 */
public class CNLLexionFault extends CNLFault
{
    private String details;
    
    //INSPECT - Lais new attribute
    private Set<String> errorTerms;
    
    /**
     * Class constructor.
     * @param sentence the sentence that contains the fault
     * @param step the step where the fault occurred.
     * @param resource the document that contains the fault
     * @param field the test case text type
     * @param details some details about the fault
     */
    protected CNLLexionFault(String sentence, StepId step, String resource, TestCaseTextType field,
            String details, Set<String> errorTerms)
    {
        super(sentence, step, resource, field);

        this.details = details;

        this.errorTerms = errorTerms;
    }

    /**
     * This method returns the fault details.
     * @return the fault details
     */
    public String getDetails()
    {
        return this.details;
    }

    /**
     * Gets the errorTerms value.
     *
     * @return Returns the errorTerms.
     */
    public Set<String> getErrorTerms()
    {
        return errorTerms;
    }
}
