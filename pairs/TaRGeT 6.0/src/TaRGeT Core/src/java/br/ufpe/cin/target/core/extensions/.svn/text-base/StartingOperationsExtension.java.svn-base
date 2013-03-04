/*
 * @(#)StartingOperationsExtension.java
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
 * wmr068    Aug 7, 2008    LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.core.extensions;

/**
 * Before launching TaRGeT, sometimes it is necessary to check if the system has all requirements
 * for launching the application successfully. This interface represents the extension point for
 * plug-ins that need to perform starting operations before initiating the TaRGeT system. This way,
 * clients must implement this interface to provide their concrete starting operations. Notice that
 * such starting operations provided by the clients plug-ins will be called when the TaRGeT is
 * to be launched.
 * 
 * @see br.ufpe.cin.target.core.TargetApplication
 */
public interface StartingOperationsExtension
{
    /**
     * Performs the starting operations of plug-ins that extends this extension point. Such
     * operations consist of checking dependencies and requirements that guarantees that the extended plug-in
     * is initiated correctly.
     * 
     * @return <code>true</code> if the operations performed by the extended plug-in were
     * correctly initiated. <code>false</code>, otherwise.
     */
    boolean performStartingOperations();
}
