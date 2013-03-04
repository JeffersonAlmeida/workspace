/*
 * @(#)ImportFeaturesWizard.java
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
 * dhq348   Nov 30, 2006    LIBkk11577   Initial creation.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * pvcv     Apr 01, 2009				 Internationalization support
 * lmn3     May 14, 2009				 Changes due code inspection
 */
package br.ufpe.cin.target.pm.wizards;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProjectRefreshInformation;
import br.ufpe.cin.target.pm.errors.Error;
import br.ufpe.cin.target.pm.perspectives.RequirementPerspective;
import br.ufpe.cin.target.pm.progressbars.ImportFeaturesProgressBar;
import br.ufpe.cin.target.pm.util.GUIUtil;


/**
 * The wizard responsible for importing documents into the project.
 * 
 * <pre>
 * CLASS:
 * The wizard responsible for importing documents into the project.
 * 
 * RESPONSIBILITIES:
 * 1) Import new documents into the project.
 * 
 * USAGE:
 * ImportFeaturesWizard wizard = new ImportFeaturesWizard(shell);
 */
public class ImportFeaturesWizard extends TargetWizard {
	/** Indicates that there is an opened project */
	private boolean opened;

	/**
	 * This is the page responsible for setting the project requirements
	 */
	private DocumentsSelectionPage documentsSelectionPage;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            The parent shell.
	 */
	public ImportFeaturesWizard(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Add the wizard pages. In this case, only one page is added
	 * (RequirementSelPage)
	 */
	public void addPages() {
		setWindowTitle(Properties.getProperty("import_documents_wizard"));
		documentsSelectionPage = new DocumentsSelectionPage();
		addPage(documentsSelectionPage);
	}

	/**
	 * @see br.ufpe.cin.target.pm.wizards.TargetWizard#init()
	 */
	
	protected boolean init() throws Exception {
		this.opened = ProjectManagerController.getInstance().hasOpenedProject();
		if (this.opened) {
			/* Retrieves the features from requirementSelPage */
			String[] featureDocumentName = documentsSelectionPage
					.getDocuments();

			ImportFeaturesProgressBar importBar = new ImportFeaturesProgressBar(
					"Importing features", featureDocumentName);
			this.progressBar = importBar;
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(
					this.parentShell);
			dialog.setCancelable(false);
			dialog.run(false, true, this.progressBar);

			/* verifies the exception */
			if (this.progressBar.getException() != null) {
				throw this.progressBar.getException();
			}

			this.displayWarnings(importBar.getRefreshInfo());

			GUIUtil.showPerspective(RequirementPerspective.ID);

			GUIManager.getInstance().refreshViews();
			
			GUIManager.getInstance().refreshProject(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),true);
		}
		return this.opened;
	}

	/**
	 * Displays a message dialog containing any step id warning that exists.
	 * 
	 * @param refreshInfo
	 *            The information about the importing process.
	 */
	private void displayWarnings(TargetProjectRefreshInformation refreshInfo) {
		Collection<Error> stepIdErrors = refreshInfo
				.getNewDifferentErrors(Error.ERROR);

		if (stepIdErrors != null && stepIdErrors.size() > 0) {
			String message = Properties.getProperty("documents_step_id_errors")
					+ "\n\n";
			Set<String> errors = new HashSet<String>();
			for (Error error : stepIdErrors) {
				if (!errors.contains(error.getResource())) {
					errors.add(error.getResource());
					message += "\t" + error.getResource() + "\n\n";
				}
			}

			message += Properties.getProperty("see_errors_tab_for_details");

			MessageDialog.openWarning(null, "Warning", message);
		}

	}

	
	protected boolean finish() {
		return this.isNotCrashed;
	}

	
	protected String getLocation() {
		return Properties.getProperty("importing_documents");
	}
}
