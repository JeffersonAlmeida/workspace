/*
 * @(#)ApplicationWorkbenchWindowAdvisor.java
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
 * wra050    Jul 6, 2006    LIBkk11577   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * wmr068    Aug 07, 2008   LIBqq64190   Method dispose removed. The MS Word plug-in is now responsible for finishing the listener.
 * lmn3      May 14, 2009   LIBkk11577   Changes due code inspection
 */
package br.ufpe.cin.target.core;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * <pre>
 * CLASS:
 * Used to configure the workbench window of TargetApplication.
 * This class is instantiated when the workbench window is being 
 * created. 
 * 
 * Created by New Eclipse Plug-in Project wizard
 * 
 * RESPONSIBILITIES:
 * 1) Create the actionBarAdvisor
 * 2) Configure the visibility of menuBar (preWindowOpen overriden method)
 *
 * COLABORATORS:
 * 
 *
 * USAGE:
 * This class is used internally by eclipse platform. Architecture chosen 
 * for implement TargetApplication. 
 * </pre>
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{
    /**
     * Default Constructor. Created by New Eclipse Plug-in Project wizard
     * 
     * @param configurer The workbench window configurer.
     */
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
    {
        super(configurer);
    }

    /**
     * Returns the a instance of actionBarAdvisor. Created by New Eclipse Plug-in Project wizard
     * 
     * @param configurer The action bar configurer.
     */
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
    {
        return new ApplicationActionBarAdvisor(configurer);
    }

    /**
     * Used to configure the menuBar, toolBar, statusBar visibility; window size and title. Created
     * by New Eclipse Plug-in Project wizard.
     */
    public void preWindowOpen()
    {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();

        configurer.setTitle("TaRGeT - Test and Requirements Generation Tool");

        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setShowMenuBar(true);
    }

    /**
     * Sets the size and the position of the application screen. The set size is 800x600.
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#postWindowOpen()
     */
    
    public void postWindowCreate()
    {
        super.postWindowCreate();
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = Math.max(0, (dim.width - 800) / 2);
        // It is moved up 22 pixels in order to place the water mark at the center position.
        int y = Math.max(0, (dim.height - 600) / 2 - 22);

        configurer.getWindow().getShell().setBounds(new Rectangle(x, y, 800, 600));
    }
}
