/*
 * @(#)TableEditingSupport.java
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

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.ColumnViewer;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditor;

public class TableEditingSupport extends EditingSupport
{
    private CellEditor editor;
    private int column;
    private FormEditor edit;
    
 
    public TableEditingSupport(ColumnViewer viewer, int column, FormEditor edit) {
        super(viewer);
        editor = new TextCellEditor(((TableViewer) viewer).getTable());
        this.column = column;
        this.edit = edit;
     
    }

    
    protected boolean canEdit(Object element) {
        return true;
    }

    
    protected CellEditor getCellEditor(Object element) {
        return editor;
    }
    //22/12
    
	protected Object getValue(Object element) {
		FlowStep step = (FlowStep) element;
		
		if(!((UseCaseEditor) edit).isDirty()){
			((UseCaseEditor) edit).setDirty(true);
		}
		switch (this.column) {
		case 0:
			return step.getId().getStepId();
		case 1:
			return step.getUserAction();
		case 2:
			return step.getSystemCondition();
		case 3:
			return step.getSystemResponse();
		default:
			break;
		}
		return null;
	}

    
    protected void setValue(Object element, Object value) {
    	FlowStep step = (FlowStep) element;
        
        switch (this.column) {
		case 0:
			step.getId().setStepId(String.valueOf(value));
			break;
		case 1:
			step.setUserAction(String.valueOf(value));
			break;
		case 2:
			step.setSystemCondition(String.valueOf(value));
			break;
		case 3:
			step.setSystemResponse(String.valueOf(value));
			break;
		default:
			break;
		}
    	getViewer().update(element, null);
    	
	
    }
    

}
