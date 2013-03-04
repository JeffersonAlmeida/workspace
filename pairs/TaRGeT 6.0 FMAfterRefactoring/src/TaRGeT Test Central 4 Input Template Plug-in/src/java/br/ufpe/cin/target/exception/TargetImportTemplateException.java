/*
 * @(#)TargetImportTemplateException.java
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
 * fsf2      24/07/2009                  Initial creation.
 */
package br.ufpe.cin.target.exception;

import br.ufpe.cin.target.common.exceptions.TargetException;

/**
 * This unity is responsible to inform errors occurred while importing a template.
 *<pre>
 *
 * RESPONSIBILITIES:
 * This exception represent any error occurred during the import template.
 * 1) Informs errors occurred during import template.
 *
 *
 */
public class TargetImportTemplateException extends TargetException
{
    public TargetImportTemplateException()
    {
    }
}
