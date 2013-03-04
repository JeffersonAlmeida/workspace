/*
 * @(#)FileAlredyExistsException.java
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
 * dhq348   Jan 30, 2007    LIBll12753   Initial creation.
 */
package br.ufpe.cin.target.pm.exceptions;

/**
 * Represents an exception indicating that a duplicated file exists.
 * 
 * <pre>
 * CLASS:
 * Represents an exception indicating that a duplicated file exists.
 */
public class FileAlreadyExistsException extends TargetProjectLoadingException
{

    /** Default serial ID. */
    private static final long serialVersionUID = 1L;

    /**
     * Default constructor.
     */
    public FileAlreadyExistsException()
    {
    }

    /**
     * Returns the exception message, which is mounted with each error message.
     */
    
    public String getMessage()
    {
        String message = "The following errors were found during the project loading:\n\n";

        message += "A document with the same name already exists.";

        return message;
    }

}
