/*
 * @(#)RepositoryInitializationException.java
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
 * Exception related to repository initialization errors.
 *
 */
@SuppressWarnings("serial")
public class RepositoryInitializationException extends RepositoryException {
	
    /** The name of the repository that threw the error. */
	private String repositoryName;
	
	/** The original exception that caused the creation of the repository initialization exception */
	private Throwable originalException;

	/**
	 * 
	 * Constructor that sets the error message given the repository name.
	 * 
	 * @param repositoryName The name of the repository in which the exception occurred.
	 * @param originalException The original exception.
	 */
	public RepositoryInitializationException(String repositoryName, Throwable originalException) {

		super("Error on initialization of \"" + repositoryName + "\".");
		this.repositoryName = repositoryName;
		this.originalException = originalException;
	}

	/**
	 * Get method for the repository name.
	 * 
	 * @return The repository name.
	 */
	public String getRepositoryName() {
		return this.repositoryName;
	}

	/**
	 *
	 * Get method for the original exception.
	 * 
	 * @return The original exception.
	 */
	public Throwable getOriginalException() {
		return this.originalException;
	}
	
}
