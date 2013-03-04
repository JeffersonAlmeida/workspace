/*
 * @(#)CNLException.java
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
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package br.ufpe.cin.cnlframework.exceptions;

/**
 * This class represents a general exception thrown by the CNL framework. All other CNL exceptions
 * should extend the <code>CNLException</code>.
 */
@SuppressWarnings("serial")
public class CNLException extends Exception
{
    /**
     * 
     * Constructor that sets a general error message.
     *
     */
    public CNLException()
    {
        super("Error processing the Controlled Natural Language");
    }

    /**
     * 
     * Constructor that allows setting a specific error message.
     * 
     * @param message The error message.
     */
    public CNLException(String message)
    {
        super(message);
    }
}
