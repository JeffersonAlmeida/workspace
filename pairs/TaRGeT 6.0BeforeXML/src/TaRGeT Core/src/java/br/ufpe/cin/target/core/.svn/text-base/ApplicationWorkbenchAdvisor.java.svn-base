/*
 * @(#)ApplicationWorkbenchAdvisor.java
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
 * dhq348         -         LIBkk11577   Initial creation.
 * dhq348    Jan 6, 2006    LIBkk11577   Added JavaDoc.
 */
package br.ufpe.cin.target.core;

import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchAdvisor;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * This class is responsible for creating the initial window perspective.
 * 
 * <pre>
 * CLASS:
 * This class is responsible for creating the initial window perspective.
 * 
 * RESPONSIBILITIES:
 * 1) Create the initial window perspective.
 *
 * USAGE:
 * The class is used by TargetApplication.
 * E.g. PlatformUI.createAndRunWorkbench(display, new ApplicationWorkbenchAdvisor());
 */
public class ApplicationWorkbenchAdvisor extends WorkbenchAdvisor
{

    /**
     * Creates the workbench window advisor.
     * 
     * @param configurer The workbench window configurer.
     * @return The workbench advisor.
     */
    public WorkbenchWindowAdvisor createWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
    {
        return new ApplicationWorkbenchWindowAdvisor(configurer);
    }

    /**
     * Returns the initial window perspective id.
     * 
     * @return The initial window perspective id.
     */
    public String getInitialWindowPerspectiveId()
    {
        return Perspective.ID;
    }

    
    public void initialize(IWorkbenchConfigurer configurer)
    {
        super.initialize(configurer);
    }
}
