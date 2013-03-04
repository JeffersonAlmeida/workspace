/*
 * @(#)ShowConditionSentencesActionDelegate.java
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
 * dgt                                    Initial creation.
 * lmn3      02/09/2009                   Inclusion of table filter on CNL View.
 */
package br.ufpe.cin.target.cnl.actions;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

import br.ufpe.cin.target.cnl.views.CNLView;

/**
 * This action shows the condition sentences in the error table on CNL View.
 */
public class ShowConditionSentencesActionDelegate implements
		IViewActionDelegate {
	private IViewPart parentView;

	
	public void init(IViewPart view) {
		this.parentView = view;
	}

	
    public void run(IAction action)
    {
        CNLView.getView().setShowConditions(action.isChecked());
    }

	
	public void selectionChanged(IAction action, ISelection selection) {

	}

}
