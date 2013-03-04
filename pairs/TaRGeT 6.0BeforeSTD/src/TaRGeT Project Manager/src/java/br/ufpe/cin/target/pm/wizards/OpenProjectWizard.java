/*
 * @(#)OpenProjectWizard.java
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
 * dhq348   Nov 28, 2006    LIBkk11577   Javadoc comments.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * pvcv     Apr 01, 2009				 Internationalization support 
 * lmn3		May 14, 2009				 Changes due code inspection
 */

package br.ufpe.cin.target.pm.wizards;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.perspectives.RequirementPerspective;
import br.ufpe.cin.target.pm.progressbars.OpenProjectProgressBar;
import br.ufpe.cin.target.pm.util.GUIUtil;


/**
 * This class represents the Open Project wizard that is responsible for opening
 * an existing project.
 * 
 * <pre>
 * CLASS:
 * This class represents the Open Project wizard that is responsible for opening an existing project.
 * 
 * RESPONSIBILITIES:
 * 1) Collect the data distributed in the pages
 * 2) Open an existing Target Project
 * 
 * COLABORATORS:
 * 1) Uses ProjectOpenPage
 * 2) Uses TargetProject
 * 
 * USAGE:
 * OpenProjectWizard wizard = new OpenProjectWizard()
 * </pre>
 */
public class OpenProjectWizard extends TargetWizard {
	/**
	 * The project selection page
	 */
	private ProjectSelectionPage projectSelectionPage;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            The parent shell of the wizard.
	 */
	public OpenProjectWizard(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Adds an open page to the wizard.
	 */
	public void addPages() {
		setWindowTitle(Properties.getProperty("open_project"));
		projectSelectionPage = new ProjectSelectionPage();
		addPage(projectSelectionPage);
	}

	/**
	 * Initializes the wizard. 1) Retrieves the name of the project. 2) Displays
	 * a progress bar when opening the project. 3) Shows the correct
	 * perspective. 4) Attaches ErrorView as a ProjectManagerController
	 * observer. 5) Notifies all observers (recently added and existent). 6)
	 * Refreshes the tree views.
	 * 
	 * @return <i>True</i> if everything goes fine or <i>false</i> otherwise.
	 */
	
	protected boolean init() throws Exception {
		boolean result = false;

		ProjectManagerController controller = ProjectManagerController
				.getInstance();

		/* Retrieves the project file from projectOpenPage */
		String projectFile = projectSelectionPage.getProjectFile();

		/* Opens the project */
		try {
			controller.openProject(projectFile);

			/* Manages the project opening progress */
			this.progressBar = new OpenProjectProgressBar();
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(
					this.parentShell);
			dialog.setCancelable(false);
			dialog.run(true, true, this.progressBar);

			/* verifies the exception */
			if (this.progressBar.getException() != null) {
				throw this.progressBar.getException();
			}

			GUIUtil.showPerspective(RequirementPerspective.ID);

			/*
			 * the code below updates the tree views, the code must be here and
			 * not in the respective progress bar because when the progress bar
			 * is executed the perspective has not been created yet, so there is
			 * no how to update the views
			 */
			GUIManager.getInstance().refreshViews();

			GUIManager.getInstance().updateApplicationTitle();

			result = true;

		} catch (TargetException te) {
			te.printStackTrace();
			MessageDialog.openInformation(null, Properties
					.getProperty("error_trying_open_project"), Properties
					.getProperty("opened_file_is_not_valid"));
		}

		return result;
	}

	/**
	 * Enables or disables the close project action depending on the open
	 * project process.
	 * 
	 * @return The previous state of the open project process.
	 */
	
	protected boolean finish() {
		if (!this.isNotCrashed) {
			/* closes any exiting project is an error occurred */
			ProjectManagerController.getInstance().closeProject();
		}
		return this.isNotCrashed;
	}

	/**
	 * @return The text of the wizard. Used to refer to external locations. E.g.
	 *         The titles of error messages.
	 */
	
	protected String getLocation() {
		return Properties.getProperty("opening_project");
	}
}
