/*
 * @(#)ProjectSelectionPage.java
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
 * dhq348    Jan 11, 2007   LIBkk11577   Initial creation.
 * dhq348    Jan 18, 2007   LIBkk11577   Rework of inspection LX133710.
 * wsn013    Fev 26, 2007   LIBll29555   Changes according to LIBll29555.
 * pvcv      Apr 01, 2009				 Internationalization support
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.TargetProject;


/**
 * Page wizard responsible for opening an existing project.
 * 
 * <pre>
 * CLASS:
 * Page wizard responsible for opening an existing project.
 * 
 * RESPONSIBILITIES:
 * 1) Open an existing project.
 */
public class ProjectSelectionPage extends WizardPage {
	/**
	 * The text box that receives the project name.
	 */
	private Text projectName;

	/**
	 * Sets all the titles.
	 */
	protected ProjectSelectionPage() {
		super("");
		setTitle(Properties.getProperty("open_existing_project"));
		setDescription(Properties.getProperty("project_to_open"));
	}

	/**
	 * Creates the visual components of the page.
	 * 
	 * @param parent
	 *            The parent of the page.
	 */
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NULL);

		GridLayout topLayout = new GridLayout();
		container.setLayout(topLayout);
		topLayout.numColumns = 3;
		topLayout.verticalSpacing = 9;

		Label label = new Label(container, SWT.NULL);
		label.setText(Properties.getProperty("project_name"));

		projectName = new Text(container, SWT.BORDER | SWT.SINGLE
				| SWT.READ_ONLY);
		projectName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

		Button destinationButton = new Button(container, SWT.PUSH);
		destinationButton.setText(Properties.getProperty("browse"));
		destinationButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				handleProjectBrowse();
			}
		});

		projectName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				checkProjectName();
			}
		});

		// set the initial status of the finish button
		setPageComplete(false);

		setControl(container);
	}

	/**
	 * Uses the standard directory dialog to choose the new value for the
	 * destination folder.
	 */
	private void handleProjectBrowse() {
		FileDialog dialog = new FileDialog(getShell());
		dialog.setText(Properties.getProperty("browser_file"));
		dialog.setFilterExtensions(new String[] { "*"
				+ TargetProject.PROJECT_FILE_EXTENSION });
		dialog.setFilterNames(new String[] { Properties
				.getProperty("target_project") });
		projectName.setText("");
		projectName.insert(dialog.open());
	}

	/**
	 * Checks if the project name has been set and sets if the page is complete
	 * or not.
	 */
	private void checkProjectName() {
		setPageComplete(projectName.getText() != null);
	}

	/**
	 * Returns the project name typed by the user.
	 * 
	 * @return The project name.
	 */
	public String getProjectFile() {
		return projectName.getText();
	}
}
