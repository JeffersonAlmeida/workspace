/*
 * @(#)CNLViewerFilter.java
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
 * lmn3   02/09/2009                     Initial creation.
 */
package br.ufpe.cin.target.cnl.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.target.cnl.controller.CNLFault;


/**
 * This class represents a table vier filter for the error table on CNL View
 */
public class CNLViewerFilter extends ViewerFilter
{
    
    public boolean[] showSentences;
    
    public CNLViewerFilter()
    {
        this.showSentences = new boolean[] { true, true, true };
    }

    
    public boolean select(Viewer viewer, Object parentElement, Object element)
    {
        CNLFault cnlFault = (CNLFault) element;
        if(cnlFault.getField().equals(TestCaseTextType.ACTION)){
            if(this.showSentences[0]){
                return true;
            }
        }
        else if(cnlFault.getField().equals(TestCaseTextType.CONDITION)){
            if(this.showSentences[1]){
                return true;
            }                
        }
        else if(cnlFault.getField().equals(TestCaseTextType.RESPONSE)){
            if(this.showSentences[2]){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Indicates if the action faults will be showed.
     * 
     * @param bool
     */
    public void setShowActions(boolean bool) {
        this.showSentences[0] = bool;
    }

    /**
     * Indicates if the condition faults will be showed.
     * 
     * @param bool
     */
    public void setShowConditions(boolean bool) {
        this.showSentences[1] = bool;
    }

    /**
     * Indicates if the response faults will be showed.
     * 
     * @param bool
     */
    public void setShowResponses(boolean bool) {
        this.showSentences[2] = bool;
    }
    
    
    /**
     * Indicates if the action faults will be showed.
     * 
     * @param bool
     */
    public boolean isShowActions() {
        return this.showSentences[0];
    }

    /**
     * Indicates if the condition faults will be showed.
     * 
     * @param bool
     */
    public boolean isShowConditions() {
        return this.showSentences[1];
    }

    /**
     * Indicates if the response faults will be showed.
     * 
     * @param bool
     */
    public boolean isShowResponses() {
        return this.showSentences[2];
    }
    

}
