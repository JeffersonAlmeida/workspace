/*
 * @(#)TargetSearchException.java
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
 * dhq348   Jun 22, 2007    LIBmm25975   Initial creation.
 */
package br.ufpe.cin.target.pm.exceptions;

import br.ufpe.cin.target.common.exceptions.TargetException;

/**
 * This class represents any search exception. Usually it is created when a ParseException or an
 * IOException is thrown.
 */
public class TargetSearchException extends TargetException
{

    /**
     * Field description.
     */
    private static final long serialVersionUID = -5982693773255911419L;

    /**
     * Constructor based on superclass.
     * 
     * @param message The error message.
     */
    public TargetSearchException(String message)
    {
        super(message);
    }
}
