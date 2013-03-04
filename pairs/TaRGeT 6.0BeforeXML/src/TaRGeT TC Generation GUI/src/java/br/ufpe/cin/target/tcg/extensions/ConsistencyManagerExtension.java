/*
 * @(#)ConsistencyManagerExtension.java
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
package br.ufpe.cin.target.tcg.extensions;

import java.util.List;

import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * CLASS:
 * This class represents the Consistency Management extension.
 * RESPONSIBILITIES:
 * 1) Open the Consistency Management user interface
 * USAGE:
 * ConsistencyManagerExtension cmExtension = ConsistencyManagerExtensionFactory.newConsistencyManagerExtension();
 */

public interface ConsistencyManagerExtension
{

    /**
     * This method opens the consistency management user interface.
     * @param shell The SWT shell of the application
     * @param list the new test suite to be compared
     */
    public void openConsistencyManager(Shell shell, List<TextualTestCase> list);
    
}
