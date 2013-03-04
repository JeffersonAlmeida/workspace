/*
 * @(#)RenameDocumentAction.java
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
 * dhq348   Jan 29, 2007    LIBll12753   Initial creation.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Mar 14, 2007    LIBll66163   Modification according to CR LIBll66163.
 * wdt022   Mar 25, 2008    LIBpp56482   Updated due to GUIManager.refreshView method update.
 * wln013   May 08, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * pvcv      Apr 01, 2009				 Internationalization support
 * lmn3     May 14, 2009				 Changes due new input and output documents formats
 */
package br.ufpe.cin.target.pm.views.artifacts;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.common.TreeObject;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.util.GUIUtil;


/**
 * Implements the behavior of renaming a document.
 * 
 * <pre>
 * CLASS:
 * Renames a document in a tree.
 */
public class RenameDocumentAction extends Action {
	/**
	 * The parent of this action.
	 */
	private Composite parent;

	/**
	 * The viewer that is used to get the current selection of a tree.
	 */
	private TreeViewer viewer;

	/**
	 * The tree node that is the parent of all documents.
	 */
	private TreeObject useCaseParentNode;

	/**
	 * The tree node that is the parent of all test suites.
	 */
	private TreeObject testCaseParentNode;

	/**
	 * The constructor of the action. It also sets the name of the action to be
	 * displayed.
	 * 
	 * @param parent
	 *            The parent component of this action.
	 * @param viewer
	 *            The viewer that is used to get the current selection of a
	 *            tree.
	 * @param useCaseParentNode
	 *            The node that is the parent of the children documents.
	 * @param testCaseParentNode
	 *            The node that is the parent of the children of the test
	 *            suites.
	 */
	public RenameDocumentAction(Composite parent, TreeViewer viewer,
			TreeObject useCaseParentNode, TreeObject testCaseParentNode) {
		this.setText(Properties.getProperty("rename"));
		this.viewer = viewer;
		this.parent = parent;
		this.useCaseParentNode = useCaseParentNode;
		this.testCaseParentNode = testCaseParentNode;
	}

	/**
	 * The behaviour of the action. Checks if the name entered by the user is
	 * valid.
	 */
	public void run() {
		ISelection selection = viewer.getSelection();
		if (selection.isEmpty()) {
			this.setEnabled(false);
		}
		TreeObject object = (TreeObject) ((IStructuredSelection) selection)
				.getFirstElement();
		if (object instanceof DocumentTreeObject) {

			String oldValue = object.getValue().toString();
			
			IInputValidator validator = new TextValidator(oldValue);
			InputDialog dialog = new InputDialog(parent.getShell(), Properties
					.getProperty("rename"), Properties.getProperty("new_name"),
					oldValue, validator);
			dialog.setBlockOnOpen(true); // waits for user enter a name

			int res = dialog.open(); // opens the rename dialog

			if (res == InputDialog.OK) {
				String newValue = dialog.getValue();
				
				String newExtension = GUIUtil.getFileExtension(newValue);
				String oldExtension = GUIUtil.getFileExtension(oldValue);
						
				if(!newExtension.equalsIgnoreCase(oldExtension)){
					newValue += oldExtension;
				}

				if (!newValue.equalsIgnoreCase(oldValue)) {
					if (!ProjectManagerController.getInstance().renameDocument(
							oldValue, newValue)) {
						MessageDialog.openError(parent.getShell(), Properties
								.getProperty("error_while_renaming"),
								Properties.getProperty("can_not_rename"));
					}
					try {
						GUIManager.getInstance().refreshViews();
					} catch (TargetException e) {
						MessageDialog
								.openError(
										this.parent.getShell(),
										Properties
												.getProperty("error_while_reloading_project"),
										e.getMessage());
						e.printStackTrace();
					}
				}
			}
		}
	}

	/**
	 * Validates the name entered by the user when renaming a document
	 * 
	 * <pre>
	 * CLASS:
	 * This class is responsible for implementing a validator for the name
	 * of documents entered by the user.
	 */
	private class TextValidator implements IInputValidator {
		/**
		 * The original name of the document.
		 */
		private String oldValue;

		/**
		 * Instantiates the validator passing the old name of the document as
		 * parameter.
		 * 
		 * @param oldValue
		 *            The original name of the document.
		 */
		public TextValidator(String oldValue) {
			this.oldValue = oldValue;
		}

		/**
		 * Checks if <code>newText</code> is a valid name.
		 * 
		 * @return The error message if the name is not valid or null otherwise.
		 */
		public String isValid(String newText) {
			String message = "";

			newText = newText.trim();
			if (newText.length() == 0) {
				message = Properties.getProperty("name_specified");
			} else if (newText.length() > 50) {
				message = Properties.getProperty("name_too_long");
			} else if (!(newText.equalsIgnoreCase(oldValue))
					&& this.childExists(oldValue, newText)) {
				message = Properties.getProperty("name_already_exists");
			} else {
				boolean valid = true;
				char[] invalidChracteres = new char[] { '\\', '/', ':', '*',
						'?', '"', '<', '>', '|' };
				for (int i = 0; i < invalidChracteres.length; i++) {
					if (newText.indexOf(invalidChracteres[i]) != -1) {
						valid = false;
						break;
					}
				}
				if (!valid) {
					message = Properties.getProperty("forbidden_characters")
							+ " \\  /  :  * ? \"  <  >";
				}
			}

			return (message.length() > 0) ? message : null;
		}

		/**
		 * Verifies if exists a child with the new name to be set.
		 * 
		 * @param oldValue
		 *            Use to get the existent filename extension.
		 * @param newValue
		 *            The new value for the document name.
		 * @return <i>True</i> if exists a child with the new name of
		 *         <i>false</i> otherwise.
		 */
		private boolean childExists(String oldValue, String newValue) {
			String extension = oldValue.toLowerCase().substring(
					oldValue.length() - 4);
			TreeObject[] children = null;
			if (extension.equals(".doc")) {
				children = useCaseParentNode.getChildren();
			} else if (extension.equals(".xls")) {
				children = testCaseParentNode.getChildren();
			} else {
				children = new TreeObject[] {};
			}

			boolean result = false;
			for (int i = 0; i < children.length; i++) {
				if (children[i].getValue().toString()
						.equalsIgnoreCase(newValue)) {
					result = true;
					break;
				}
			}
			return result;
		}

	}
}
