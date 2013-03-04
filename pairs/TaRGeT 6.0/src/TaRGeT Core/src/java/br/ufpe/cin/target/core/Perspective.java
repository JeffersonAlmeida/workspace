/*
 * @(#)Perspective.java
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
 *  dhq348        -         LIBkk11577   Initial creation.
 *  dhq348   6 Jan, 2007    LIBkk11577   Added DefaultView invocation and JavaDoc.
 */
package br.ufpe.cin.target.core;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class represents the default RCP perspective of the TaRGeT tool.
 * 
 * <pre>
 * CLASS:
 * This class represents the default RCP perspective of the TaRGeT tool.
 * 
 * RESPONSIBILITIES:
 * 1) Add the default view.
 *
 * USAGE:
 * This class is invoked by ApplicationWorkbenchAdvisor by referring to its ID.
 */
public class Perspective implements IPerspectiveFactory
{
    /**
     * The public id of the perspective. It is also referred in plugin.xml.
     */
    public static final String ID = "br.ufpe.cin.target.perspective";

    /**
     * Creates the initial layout of the perspective by adding the DefaultView to it.
     */
    public void createInitialLayout(IPageLayout layout)
    {
        layout.setEditorAreaVisible(false);
        layout.addStandaloneView(DefaultView.ID, false, IPageLayout.LEFT, 1.00f, layout
                .getEditorArea());
    }
}
