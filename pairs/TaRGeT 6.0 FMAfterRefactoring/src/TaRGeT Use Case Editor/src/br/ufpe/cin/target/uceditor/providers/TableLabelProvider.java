/*
 * @(#)TableLabelProvider.java
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
 * mcms   11/12/2009      LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.uceditor.providers;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import br.ufpe.cin.target.common.ucdoc.FlowStep;


public class TableLabelProvider extends LabelProvider implements ITableLabelProvider
{
   
    
    public Image getColumnImage(Object element, int columnIndex)
    {
    	return null;
    }

    
    public String getColumnText(Object element, int columnIndex)
    {
    	FlowStep step = (FlowStep)element;
        
        switch (columnIndex) {
		case 0:
			return step.getId().getStepId();
		case 1:
			return step.getUserAction();
		case 2:
			return step.getSystemCondition();
		case 3:
			return step.getSystemResponse();
		default:
			throw new RuntimeException("Should not happen");
		}
    }

}
