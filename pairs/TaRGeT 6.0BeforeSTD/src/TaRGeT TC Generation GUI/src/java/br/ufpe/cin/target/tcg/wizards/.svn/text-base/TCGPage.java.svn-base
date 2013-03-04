/*
 * @(#)TCGPage.java
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
 * dhq348    Nov 12, 2007   LIBoo10574   Initial creation.
 */
package br.ufpe.cin.target.tcg.wizards;

import org.eclipse.swt.widgets.Composite;

/**
 * Contains the signatures of basic methods of a generic TCG wizard page.
 */
public interface TCGPage
{
    /**
     * Creates all visual components of the page.
     * 
     * @param parent The parent of the components to be created.
     * @return The main component of the page. It is used to set the control of the wizard.
     */
    public Composite createVisualComponents(Composite parent);

    /**
     * Indicates if the current page is or not complete.
     * 
     * @return <code>true</code> if the page is complete or <code>false</code> otherwise.
     */
    public boolean isPageComplete();
}
