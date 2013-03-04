/*
 * @(#)TableContentProvider.java
 *
 * Copyright (c) <2007-2009> <Research Project Team – CIn-UFPE>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS 
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF 
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. 
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, 
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR 
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE 
 * USE OR OTHER DEALINGS IN THE SOFTWARE
 *
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   11/12/2009      LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.uceditor.providers;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import br.ufpe.cin.target.common.ucdoc.FlowStep;

public class TableContentProvider implements IStructuredContentProvider {

    private List<FlowStep> allContent;
    
    
	
    public Object[] getElements(Object inputElement) {
        @SuppressWarnings("unchecked")
        List<FlowStep> content = (List<FlowStep>) inputElement;
        this.allContent = content;
        return content.toArray();
    }

    
    public void dispose() {
    }

    
    public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
    }
    
    public void addElement(FlowStep step){
    	allContent.add(step);
    }
    
    public void addElementAt(FlowStep step, int index){
    	allContent.add(index, step);
    }
    
    public void removeElementAt(int index){
    	allContent.remove(index);
    }
}