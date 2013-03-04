/*
 * @(#)NewTCGAction.java
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
 * WCB065    14/07/2006         -        Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   ProjectManagerExternalFacade inclusion.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * dhq348   Feb 21, 2008    LIBoo89937   Added the hasImportedFeatures() verification.
 * wdt022   Mar 14, 2008    LIBpp56482   Verification methods moved to AbstractTCGAction. Test case Generation summary moved to TCGUtil.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 */
package br.ufpe.cin.target.cnl.generatetestsuite.actions;

import org.eclipse.jface.wizard.WizardDialog;

import br.ufpe.cin.target.cnl.generatetestsuite.wizards.NewTCGWizard;
import br.ufpe.cin.target.tcg.actions.AbstractTCGActionDelegate;
import br.ufpe.cin.target.tcg.util.TCGUtil;


/**
 * This is the action that activates the test case generation process.
 * 
 * <pre>
 * CLASS:
 * Activates the test case generation process. It verifies if there are no errors, and then it 
 * invokes the test case generation wizard.
 * 
 * RESPONSIBILITIES:
 * 1) Invokes the test case generation wizard.
 *
 * USAGE:
 * It is referred by its ID.
 */
public class NewTCGActionDelegate extends AbstractTCGActionDelegate
{
    /**
     * The ID of the action that is declared in plugin.xml.
     */
    public static final String ID = "br.ufpe.cin.target.cnl.generatetestsuite.newtcg";

    /**
     * Invokes the test case generation wizard.
     */
    
    //INSPECT Lais - Changed
    protected void hookGeneration()
    {
        NewTCGWizard wizard = new NewTCGWizard(this.window.getShell());
        WizardDialog dialog = new WizardDialog(this.window.getShell(), wizard);
        if (dialog.open() == WizardDialog.OK)
        {
            TCGUtil.displayTestCaseSummary(this.window.getShell(), wizard
                    .getNumberOfGeneratedTestCases(), wizard.getGenerationTime(), wizard.getTestSuiteFile());
        }
    }
}
