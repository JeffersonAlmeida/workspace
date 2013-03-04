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
 * wdt022   Mar 14, 2008    LIBpp56482   Initial creation.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * dwvm83	Oct 02, 2008	LIBqq51204	 Added method generateTestCases.
 * dwvm83   Oct 14, 2008	LIBqq51204	 Added method selectionChanged.
 * dwvm83   Oct 31, 2008	LIBqq51204	 Added method onTheFlyEditorReference, changes to methods generateTestCases and selectionChanged.
 * fsf2     Jun 29, 2009                 Added support to refresh method.
 * lmn3     Jun 30, 2009                 Internationalization support.
 */
package br.ufpe.cin.target.onthefly.actions;

import java.util.Collection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.lts.LTS;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyEditorInput;
import br.ufpe.cin.target.onthefly.editors.OnTheFlyMultiPageEditor;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.tcg.TCGActivator;
import br.ufpe.cin.target.tcg.actions.AbstractTCGActionDelegate;
import br.ufpe.cin.target.tcg.controller.TestCaseGeneratorController;
import br.ufpe.cin.target.tcg.exceptions.TestGenerationException;
import br.ufpe.cin.target.tcg.extractor.TestCase;
import br.ufpe.cin.target.tcg.extractor.TestSuite;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * This is the action that activates On the Fly test case generation process.
 * 
 * <pre>
 * CLASS:
 * Activates On the Fly test case generation process. It verifies if there are no errors, and then it 
 * invokes the On the Fly test case generation editor.
 * 
 * RESPONSIBILITIES:
 * 1) Invokes the On the Fly test case generation editor.
 *
 * USAGE:
 * It is referred by its ID.
 */
public class OnTheFlyTCGActionDelegate extends AbstractTCGActionDelegate
{

    /**
     * The ID of the action that is declared in plugin.xml.
     */
    public static final String ID = "br.ufpe.cin.target.onthefly.action.OnTheFlyTCGAction";

    /**
     * The last action diferent than null.
     */
    private static IAction actionStored;

    /**
     * @see AbstractTCGActionDelegate#hookGeneration()
     */
    
    protected void hookGeneration()
    {
        generateTestCases();
    }

    /**
     * @see Generates the Test Cases again after having changed the Test Case preferences. The new
     * test cases will reflect the changes made in the preferences' parameters.
     */
    public void generateTestCases()
    {
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();

        // Getting the onTheFly editor reference
        IEditorReference ref = onTheFlyEditorReference(page);

        // Closing the previous onTheFly editor (in case the preferences were changed)
        OnTheFlyEditorInput editorInput = null;

        if (ref != null)
        {
            IEditorPart ep = ref.getEditor(false);

            if (ep instanceof OnTheFlyMultiPageEditor)
            {
                editorInput = (OnTheFlyEditorInput) ((OnTheFlyMultiPageEditor) ep).getEditorInput();
            }
        }

        ProjectManagerExternalFacade projectFacade = ProjectManagerExternalFacade.getInstance();
        Collection<UseCaseDocument> documents = projectFacade.getCurrentProject().getUseCaseDocuments();

        TestCaseGeneratorController controller = TestCaseGeneratorController.getInstance();
        LTS<FlowStep, Integer> lts = controller.generateLTSModel(documents);

        try
        {
            TestSuite<TestCase<FlowStep>> rawTestCases = controller.generateRawTests(lts);
            TestSuite<TextualTestCase> textualTestCases = controller.mapToTextualTestCases(
                    rawTestCases, documents);

            OnTheFlyEditorInput onTheFlyInput = new OnTheFlyEditorInput(rawTestCases,
                    textualTestCases);

            if (!onTheFlyInput.equals(editorInput))
            {
                if (ref != null)
                {
                    page.closeEditor(ref.getEditor(false), true);
                }

                TestCaseGeneratorController.getInstance().setLastSuiteGenerated(
                        textualTestCases.getTestCases());

                page.openEditor(onTheFlyInput, OnTheFlyMultiPageEditor.ID);
            }
        }
        catch (TestGenerationException e1)
        {
            e1.printStackTrace();
            TCGActivator.logError(0, this.getClass(), "Error generating test cases: "
                    + e1.getMessage(), e1);
            MessageDialog.openError(this.window.getShell(), e1.getTitle(), e1.getMessage());
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
            TCGActivator.logError(0, this.getClass(), "Error opening " + OnTheFlyMultiPageEditor.ID
                    + ": " + e.getMessage(), e);
            MessageDialog.openError(this.window.getShell(), Properties
                    .getProperty("graphical_interface_error"), Properties
                    .getProperty("graphical_interface_error_description"));
        }
    }

    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    
    public void selectionChanged(IAction action, ISelection selection)
    {
        super.selectionChanged(action, selection);
        IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
        IEditorReference editor = onTheFlyEditorReference(page);
        
        // If the onTheFly editor is open, the menu option should be disabled
        if (editor != null)
            action.setEnabled(false);
        // If the editor is closed AND the project is not closed, the option menu should be enabled
        else if (ProjectManagerController.getInstance().getCurrentProject() != null)
            action.setEnabled(true);

        // Stores the action.
        if (action != null)
        {
            actionStored = action;
        }
    }

    public void selectionChanged()
    {
        actionStored.setEnabled(true);
    }

    /**
     * Returns a reference to the onTheFly editor if it is open
     * 
     * @param page The active page
     * @return IEditorReference A reference to the editor; null if the editor is not open
     */
    public IEditorReference onTheFlyEditorReference(IWorkbenchPage page)
    {
        IEditorReference[] editors = page.getEditorReferences();
        IEditorReference ref = null;
        
        for (int i = 0; i < editors.length; i++)
            try
            {
                if (editors[i].getId().equals(OnTheFlyMultiPageEditor.ID))
                    ref = editors[i];
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            
        return ref;
    }
}
