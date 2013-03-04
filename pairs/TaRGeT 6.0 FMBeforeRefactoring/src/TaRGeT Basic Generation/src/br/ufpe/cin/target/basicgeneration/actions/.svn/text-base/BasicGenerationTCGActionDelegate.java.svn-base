/*
 * @(#)OnTheFlyTCGAction.java
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
 * -------  ------------    ----------    ----------------------------
 * fsf2     Aug 14, 2009                 Initial creation.
 */
package br.ufpe.cin.target.basicgeneration.actions;

import java.io.File;
import java.io.IOException;
import java.util.Collection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.tcg.TCGActivator;
import br.ufpe.cin.target.tcg.actions.AbstractTCGActionDelegate;
import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;
import br.ufpe.cin.target.tcg.exceptions.TestGenerationException;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;
import br.ufpe.cin.target.tcg.util.TCGUtil;

import br.ufpe.cin.target.common.lts.LTS;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;

/**
 * This is the action that activates basic test case generation process.
 * 
 * <pre>
 * CLASS:
 * Activates basic test case generation process. It verifies if there are no errors, and then it 
 * invokes the On the Fly test case generation editor.
 * 
 * RESPONSIBILITIES:
 * 1) Invokes the On the Fly test case generation editor.
 * 
 * USAGE:
 * It is referred by its ID.
 */
public class BasicGenerationTCGActionDelegate extends AbstractTCGActionDelegate {

	/**
	 * The ID of the action that is declared in plugin.xml.
	 */
	public static final String ID = "br.ufpe.cin.target.onthefly.action.BasicGenerationAction";

	
	protected void hookGeneration() {
		generateTestCases();
	}

	/**
	 * @see Generates the Test Cases again after having changed the Test Case
	 *      preferences. The new test cases will reflect the changes made in the
	 *      preferences' parameters.
	 */
	private void generateTestCases() {
		ProjectManagerExternalFacade projectFacade = ProjectManagerExternalFacade
				.getInstance();
		Collection<UseCaseDocument> documents = projectFacade.getCurrentProject()
				.getUseCaseDocuments();

		TestCaseGeneratorController controller = TestCaseGeneratorController
				.getInstance();
		LTS<FlowStep, Integer> lts = controller.generateLTSModel(documents);

		Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
				.getShell();

		try {
			TestSuite<TestCase<FlowStep>> rawTestCases = controller
					.generateRawTests(lts);
			TestSuite<TextualTestCase> textualTestCases = controller
					.mapToTextualTestCases(rawTestCases, documents);

			long beforeTime = System.currentTimeMillis();
			
			//INSPECT: Felype Alterei para que CM possa chamar o mesmo método chamado por On The Fly para a geração.
			File testSuiteFile = TestCaseGeneratorController.getInstance().writeTestSuiteFile(
					textualTestCases.getTestCases());
			long afterTime = System.currentTimeMillis();
			TCGUtil.displayTestCaseSummary(shell, textualTestCases
					.getTestCases().size(), afterTime - beforeTime, testSuiteFile);
		} catch (IOException e1) {
			e1.printStackTrace();
			MessageDialog.openError(shell, Properties.getProperty("error"),
					Properties
							.getProperty("error_while_writing_the_excel_file")
							+ e1.getMessage());
			TCGActivator.logError(0, this.getClass(), Properties
					.getProperty("error_while_writing_the_excel_file")
					+ e1.getMessage(), e1);
		} catch (TestGenerationException e1) {
			e1.printStackTrace();
			TCGActivator.logError(0, this.getClass(),
					"Error generating test cases: " + e1.getMessage(), e1);
			MessageDialog.openError(this.window.getShell(), e1.getTitle(), e1
					.getMessage());
		}
	}
	
    
    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    public void selectionChanged(IAction action, ISelection selection) {
        super.selectionChanged(action, selection);
        //If the project is not closed
        if (ProjectManagerController.getInstance().getCurrentProject() != null)
            action.setEnabled(true);
        
    }
}
