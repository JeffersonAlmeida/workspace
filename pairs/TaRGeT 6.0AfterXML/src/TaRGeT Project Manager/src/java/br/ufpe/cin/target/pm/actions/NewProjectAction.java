/*
 * @(#)NewProjectAction.java
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
 * wra050    -    			LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerController inclusion.
 * dhq348   Dec 22, 2006    LIBkk11577   Inspection (LX124184) faults fixing.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * wln013   Apr 30, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 */
package br.ufpe.cin.target.pm.actions;

import org.eclipse.jface.wizard.WizardDialog;

import br.ufpe.cin.target.common.actions.TargetAction;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.util.GUIUtil;
import br.ufpe.cin.target.pm.wizards.NewProjectWizard;
import br.ufpe.cin.target.pm.wizards.TargetWizard;


/**
 * Action used to create a new project.
 * 
 * <pre>
 * CLASS:
 * Action used to create a new project.
 * 
 * RESPONSIBILITIES:
 * When the related events occur (e.g, new project menu option is clicked), 
 * the wizard used to create a new project is opened.
 * 
 * USAGE:
 * One Action can be associated with events in GUI.
 * </pre>
 */
public class NewProjectAction extends TargetAction
{
    /**
     * The ID of the action that is declared in plugin.xml
     */
    public static final String ID = "br.ufpe.cin.target.pm.actions.NewProjectAction";

    
    protected void hook()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            if (GUIUtil.openYesOrNoInformation(this.window.getShell(), "Close project",
                    "The current project will be closed. Do you want to proceed?"))
            {
                GUIUtil.closeProject(this.window.getShell());
                this.openNewProjectWizard();
            }
        }
        else
        {
            this.openNewProjectWizard();
        }
    }

    /**
     * Opens the new project wizard
     */
    private void openNewProjectWizard()
    {
        TargetWizard wizard = new NewProjectWizard(this.window.getShell());
        WizardDialog dialog = new WizardDialog(this.window.getShell(), wizard);
        dialog.open();
    }
}
