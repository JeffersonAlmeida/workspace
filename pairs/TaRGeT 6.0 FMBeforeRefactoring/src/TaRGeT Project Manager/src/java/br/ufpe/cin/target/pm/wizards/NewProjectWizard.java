/*
 * @(#)NewProjectWizard.java
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
 * wra050   Jul 10, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   Refactoring.
 * dhq348   Jan 06, 2007    LIBkk11577   Inspection (LX124184) faults fixing.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Feb 15, 2007    LIBkk22912   CR (LIBkk22912) improvements.
 * dhq348   Feb 26, 2007    LIBkk22912   Rework of inspection LX147697.
 * wsn013   Fev 27, 2007    LIBll29555   Changes according to LIBll29555.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * pvcv     Apr 01, 2009				 Internationalization support 
 * lmn3		May 14, 2008				 Changes due code inspection
 */
package br.ufpe.cin.target.pm.wizards;

import java.io.File;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.perspectives.RequirementPerspective;
import br.ufpe.cin.target.pm.progressbars.CreateProjectProgressBar;
import br.ufpe.cin.target.pm.util.GUIUtil;


/**
 * Wizard used to create a project
 * 
 * <pre>
 *   CLASS:
 *   This class represents a wizard used to create a project
 *   RESPONSIBILITIES:
 *   1) This wizard assist the user to create a new Target Project
 *   
 *   COLABORATORS:
 *   1) Uses ProjectInfoPage
 *  
 *   USAGE:
 *   NewProjectWizard wizard = new NewProjectWizard(shell)
 * </pre>
 */
public class NewProjectWizard extends TargetWizard {
	/**
	 * The project info page. This is the page responsible for collecting
	 * information about the project
	 */
	private ProjectInfoPage projectInfoPage;

	/**
	 * Constructor.
	 * 
	 * @param parentShell
	 *            The parent shell of the wizard.
	 */
	public NewProjectWizard(Shell parentShell) {
		super(parentShell);
	}

	/**
	 * Add the wizard pages. In this case, only one page is added
	 * (ProjectInfoPage)
	 */
	public void addPages() {
		setWindowTitle(Properties.getProperty("new_project_wizard"));
		projectInfoPage = new ProjectInfoPage();
		addPage(projectInfoPage);
	}

	/**
	 * Initializes the wizard processing.
	 * 
	 * @throws Exception
	 *             Thrown when an error occurs during the processing.
	 */
	
	protected boolean init() throws Exception {
		/*
		 * Retrieves the project name and destination folder from
		 * projectInfoPage
		 */
		String name = projectInfoPage.getProjectName();
		String dir = projectInfoPage.getDestinationFolder();
		String destinationFolder = dir + FileUtil.getSeparator() + name;

		boolean result = false;

		if (new File(dir).canWrite()) {
			/* checks if exists a 'residual' project in an existing directory */
			if (ProjectManagerController.getInstance()
					.existsProjectInDirectory(destinationFolder)) {
				if (new File(destinationFolder).canWrite()) {
					result = GUIUtil.openYesOrNoError(getShell(), Properties
							.getProperty("overwrite_existing_project"),
							Properties.getProperty("existing_project_found"));
					if (result) {
						File file = FileUtil.deleteAllFiles(dir
								+ FileUtil.getSeparator() + name);
						if (file != null) {
							MessageDialog
									.openError(
											getShell(),
											Properties
													.getProperty("error_while_creating_project"),
											Properties
													.getProperty("cannot_delete_file")
													+ " "
													+ file.getAbsolutePath()
													+ "");
							result = false;
						} else {
							result = this.createProject(name, dir);
						}
					}
				} else {
					MessageDialog
							.openError(
									getShell(),
									Properties
											.getProperty("error_while_creating_project"),
									Properties
											.getProperty("cannot_create_project_folder"));
				}
			} else {
				result = this.createProject(name, dir);
			}
		} else {
			MessageDialog.openError(getShell(), Properties
					.getProperty("error_while_creating_project"), Properties
					.getProperty("selected_folder_not_have_write_permission"));
		}
		return result;
	}

	/**
	 * Creates a project using the specified <code>name</code> and
	 * <code>dir</code>.
	 * 
	 * @param name
	 *            The name of the project to be created.
	 * @param dir
	 *            The name of the directory in which the project will be
	 *            created.
	 * @return <code>true</code> if everything went fine or <code>false</code>
	 *         otherwise.
	 * @throws Exception
	 *             Thrown if a logical error or graphical error was found during
	 *             the processing.
	 */
	private boolean createProject(String name, String dir) throws Exception {
		/* creates the progress bar */
		this.progressBar = new CreateProjectProgressBar(name, dir);
		ProgressMonitorDialog dialog = new ProgressMonitorDialog(
				this.parentShell);
		dialog.setCancelable(false);
		dialog.run(false, true, this.progressBar);

		/* verifies the exception */
		if (this.progressBar.getException() != null) {
			throw this.progressBar.getException();
		}

		GUIUtil.showPerspective(RequirementPerspective.ID);

		GUIManager.getInstance().refreshViews();

		GUIManager.getInstance().updateApplicationTitle();

		return true;
	}

	/**
	 * If everything went fine when processing then enables the "close project"
	 * action otherwise closes the current project.
	 * 
	 * @return The result of the processing.
	 */
	
	protected boolean finish() {
		if (!this.isNotCrashed) {
			/* closes any exiting project if an error occurred */
			ProjectManagerController.getInstance().closeProject();
		}
		return this.isNotCrashed;
	}

	/**
	 * Gets the location of the current wizard.
	 * 
	 * @return The location of the current wizard.
	 */
	
	protected String getLocation() {
		return Properties.getProperty("creating_project");
	}
}
