/*
 * @(#)AbstractTCGActionDelegate.java
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
 * dhq348    Jun 26, 2007   LIBmm25975   Initial creation.
 * wdt022    Mar 14, 2008   LIBpp56482   Added verification before perform generation.
 * wdt022    Apr 01, 2008   LIBpp56482   Changes due to actions framework refactoring.
 * wln013    Apr 29, 2008   LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * lmn3      Jun 30, 2009                Internationalization support.
 * lmn3      Jun 30, 2009                Changed hasErrorsInProject(), hasImportedFeatures and 
 *                                       hasImportedDocumentsInProject methods to public.
 */
package br.ufpe.cin.target.tcg.actions;

import org.eclipse.jface.dialogs.MessageDialog;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;

/**
 * This class represents an abstract TCG action which means that all actions in TCG plug-in must
 * extends this class. The class registers an action that initializes the current TargetAction and
 * also removes any existing PropertyChangeListener.
 */
public abstract class AbstractTCGActionDelegate extends TargetAction
{
    
    /**
     * Performs the basic verifications before generating test cases.
     */
    protected final void hook()
    {
        if (!ProjectManagerExternalFacade.getInstance().hasOpenedProject())
        {
            MessageDialog.openInformation(this.window.getShell(), Properties.getProperty("test_case_generation"),
                    Properties.getProperty("there_is_no_opened_project"));
        }
        else if (!this.hasImportedDocumentsInProject())
        {
            MessageDialog.openInformation(this.window.getShell(), Properties.getProperty("test_case_generation"),
                    Properties.getProperty("no_imported_documents"));
        }
        else if (!this.hasImportedFeatures())
        {
            MessageDialog.openInformation(this.window.getShell(), Properties.getProperty("test_case_generation"),
                    Properties.getProperty("no_features_in_the_current_project"));
        }
        else if (this.hasErrorsInProject())
        {
            MessageDialog.openInformation(this.window.getShell(), Properties.getProperty("test_case_generation"),
                    Properties.getProperty("there_are_errors_in_the_project_impossible_generate_tests"));
        }
        else
        {
            this.hookGeneration();
        }
    }
    
    /**
     * 
     * Invokes the test case generation wizard.
     */
    protected abstract void hookGeneration();
    
    
    /**
     * Checks if the number of imported documents is greater than 0;
     * 
     * @return <code>true</code> if the number is greater than one, or <code>false</code>
     * otherwise.
     */
    public boolean hasImportedDocumentsInProject()
    {
        return ProjectManagerExternalFacade.getInstance().getCurrentProject().getUseCaseDocuments()
                .size() > 0;
    }

    /**
     * Checks if there is at least one imported feature in the project.
     * 
     * @return <code>true</code> if there is at least one imported feature in the project, or
     * <code>false</code> otherwise.
     */
    public boolean hasImportedFeatures()
    {
        return (ProjectManagerExternalFacade.getInstance().getCurrentProject().getFeatures() != null)
                && (!ProjectManagerExternalFacade.getInstance().getCurrentProject().getFeatures()
                        .isEmpty());
    }

    /**
     * Verifies if there is at least one error in the project.
     * 
     * @return <code>True</code> if there is at least one error, or <code>false</code> otherwise.
     */
    public boolean hasErrorsInProject()
    {
        return ProjectManagerExternalFacade.getInstance().hasErrorsInProject();
    }
}
