/*
 * @(#)ProjectInfoPage.java
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
 *    -      Jul 10, 2006    LIBkk11577   Initial creation.
 * dhq348    Jan 06, 2007    LIBkk11577   Inspection (LX124184) faults fixing.
 * pvcv      Apr 01, 2009				  Internationalization support
 */
package br.ufpe.cin.target.pm.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import br.ufpe.cin.target.common.util.Properties;

/**
 * Page used to create a new project.
 * 
 * <pre>
 *   CLASS:
 *   Page used to create a new project. It is responsible for collecting data about the project.
 *   
 *   RESPONSIBILITIES:
 *   1) Allow the user to enter project name, destination folder.
 *  
 *   USAGE:
 *   This class is used by NewProjectWizard.
 *   ProjectInfoPage projectInfoPage = new ProjectInfoPage()
 * </pre>
 */
public class ProjectInfoPage extends WizardPage {
	/**
	 * The project name
	 */
	private Text projectName;

	/**
	 * The destination folder
	 */
	private Text destinationFolder;

	/**
	 * Constructor. Sets the page title and description.
	 */
	public ProjectInfoPage() {
		super(Properties.getProperty("create_project_page"));
		setTitle(Properties.getProperty("create_target_project"));
		setDescription(Properties.getProperty("specify_name_destintion_folder"));
	}

	/**
	 * Creates the page visual widgets. Private methods are used to improve
	 * understandability.
	 * 
	 * @param parent
	 */
	public void createControl(Composite parent) {
		Composite mainContainer = createMainContainer(parent);
		createProjectContainer(mainContainer);
		createBrowseContainer(mainContainer);
		dialogChanged();
		setControl(mainContainer);
	}

	/**
	 * Create the main container and layout. The main container is compound by
	 * projectContainer and browseContainer.
	 * 
	 * @param parent
	 *            The parent.
	 */
	private Composite createMainContainer(Composite parent) {
		Composite mainContainer = new Composite(parent, SWT.NULL);

		GridLayout mainLayout = new GridLayout();

		mainLayout.numColumns = 1;
		mainLayout.verticalSpacing = 9;

		mainContainer.setLayout(mainLayout);

		return mainContainer;
	}

	/**
	 * Create the projectContainer. The field used to enter project name is
	 * placed in this container.
	 * 
	 * @param mainContainer
	 *            The main container.
	 */
	private void createProjectContainer(Composite mainContainer) {
		Composite projectContainer = new Composite(mainContainer, SWT.NULL);

		GridLayout projectLayout = new GridLayout();

		projectLayout.numColumns = 2;
		projectContainer.setLayout(projectLayout);
		projectContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(projectContainer, SWT.NULL);
		label.setText(Properties.getProperty("project_name"));

		projectName = new Text(projectContainer, SWT.BORDER | SWT.SINGLE);
		projectName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		projectName.setText(Properties.getProperty("new_project"));

		projectName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				dialogChanged();
			}
		});
	}

	/**
	 * Create the browseContainer The destination folder and browse button are
	 * placed in browseContainer.
	 * 
	 * @param mainContainer
	 *            The main container.
	 */
	private void createBrowseContainer(Composite mainContainer) {
		Composite browseContainer = new Composite(mainContainer, SWT.NULL);

		GridLayout browseLayout = new GridLayout();

		browseLayout.numColumns = 3;
		browseContainer.setLayout(browseLayout);
		browseContainer.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Label label = new Label(browseContainer, SWT.NULL);
		label.setText(Properties.getProperty("destination_folder"));

		destinationFolder = new Text(browseContainer, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		destinationFolder.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		destinationFolder.setText("");
		destinationFolder.insert(System.getProperty("user.dir"));

		Button destinationButton = new Button(browseContainer, SWT.PUSH);
		destinationButton.setText(Properties.getProperty("browse"));

		destinationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleDestinationBrowse();
			}
		});
	}

	/**
	 * Uses the standard directory dialog to choose the new value for the
	 * destination folder.
	 */
	private void handleDestinationBrowse() {
		DirectoryDialog dialog = new DirectoryDialog(getShell());
		dialog.setText(Properties.getProperty("browser_folder"));
		dialog.setMessage(Properties.getProperty("project_directory"));
		dialog.setFilterPath(System.getProperty("user.dir"));
		destinationFolder.setText("");
		
		String directory = dialog.open();
		
		if(directory != null)
		{
		    destinationFolder.insert(directory);
		}
		else{
		    destinationFolder.insert(System.getProperty("user.dir"));
		}
		
	}

	/**
	 * Manages the dialog changes.
	 */
	private void dialogChanged() {
		String fileName = projectName.getText();
		if (fileName.length() == 0) {
			updateStatus(Properties.getProperty("name_specified"));
		} else if (fileName.length() > 50) {
			updateStatus(Properties.getProperty("name_too_long"));
		} else {
			boolean valid = true;
			char[] invalidChracteres = new char[] { '\\', '/', ':', '*', '?',
					'"', '<', '>', '|' };
			for (int i = 0; i < invalidChracteres.length; i++) {
				if (fileName.indexOf(invalidChracteres[i]) != -1) {
					valid = false;
					break;
				}
			}
			if (!valid) {
				updateStatus(Properties.getProperty("name_entered_valid"));
			} else {
				updateStatus(null);
			}
		}
	}

	/**
	 * Sets the error message of the page.
	 * 
	 * @param message
	 *            The message to be displayed.
	 */
	private void updateStatus(String message) {
		setErrorMessage(message);
		setPageComplete(message == null);
	}

	/**
	 * @return The project name
	 */
	public String getProjectName() {
		return projectName.getText();
	}

	/**
	 * @return The selected destination folder
	 */
	public String getDestinationFolder() {
		return destinationFolder.getText();
	}
}
