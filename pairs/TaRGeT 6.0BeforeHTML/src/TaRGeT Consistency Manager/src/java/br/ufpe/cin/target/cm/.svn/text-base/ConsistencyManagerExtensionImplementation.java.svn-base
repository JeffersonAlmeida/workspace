/*
 * @(#)ConsistencyManagerExtensionImplementation.java
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
 * faas      08/07/2008                  Initial creation.
 * fsf2		20/06/2009					 Integration.
 */
package br.ufpe.cin.target.cm;

import java.util.List;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.target.cm.wizard.ConsistencyManagementWizard;
import br.ufpe.cin.target.tcg.extensions.ConsistencyManagerExtension;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;


/**
 * CLASS:
 * This class implements the Consistency Manager extension.
 * RESPONSIBILITIES:
 * 1) Open the Consistency Management user interface
 * USAGE:
 * ConsistencyManagerExtension cmExtension = ConsistencyManagerExtensionFactory.newConsistencyManagerExtension();
 */
public class ConsistencyManagerExtensionImplementation implements ConsistencyManagerExtension
{
    
    public void openConsistencyManager(Shell shell, List<TextualTestCase> newTestSuite)
    {
        ConsistencyManagementWizard wizard = new ConsistencyManagementWizard(shell, newTestSuite);
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.open();   
    }
}
