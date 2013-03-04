/*
 * @(#)RepositoryException.java
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
 * 
 * Represents an exception thrown while accessing the knowledge bases repository.
 */
@SuppressWarnings("serial")
public class RepositoryException extends CNLException {

    /**
     * 
     * Constructor that sets a general message.
     *
     */
	protected RepositoryException() {
		super("Repository error");
	}
	
	/**
	 * 
	 * Constructor that sets a specific error message.
	 * 
	 * @param message The message to be set.
	 */
	public RepositoryException(String message) {
		super(message);
	}
}
