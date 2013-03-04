/*
 * @(#)RefreshableEditor.java
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
 * fsf2      29/06/2009                  Initial creation.
 */
 
package br.ufpe.cin.target.pm.editors;

/**
 *<pre>
 * CLASS:
 * This interface must be implemented for all editors witch need to be refreshed 
 * when some update is identified in the test suite generated.
 * 
 * RESPONSIBILITIES:
 * Refreshes the editor with the changes after suite generation.
 *
 * USAGE:
 * EditorPart editor = new Editor();
 * editor.refresh();
 */
public interface RefreshableEditor
{

    /**
     * Comment for method.
     * @param refreshAnyway 
     */
    public void refresh(boolean refreshAnyway);
    
}
