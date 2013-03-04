/*
 * @(#)InvalidSimilarityException.java
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
 * wfo007    Jun 04, 2007   LIBmm42774   Initial creation.
 * wfo007    Aug 24, 2007   LIBmm42774   Rework after inspection LX201876.
 */
package br.ufpe.cin.target.tcg.exceptions;

import br.ufpe.cin.target.tcg.exceptions.TestGenerationException;

/**
 * This class is used to specify an exception when an invalid required similarity is specified to
 * the similarity filter.
 * 
 * <pre>
 * 
 * RESPONSIBILITIES:
 * 1) Launch an exception whenever an invalid required similarity is specified.
 *
 * COLABORATORS:
 * 1) Uses TargetException class.
 *
 */
public class InvalidSimilarityException extends TestGenerationException
{
    /**
     * Default Serial Id.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Initializes the exception. This constructor receives a message describing the
     * exception. A similarity exception is thrown after an invalid required similarity in the
     * similarity filter is specified.
     * 
     * @param message The exception message.
     */
    public InvalidSimilarityException(String message)
    {
        super("Similarity Exception", message);
    }

    /**
     * Constructor. Initialize the exception. A similarity exception is thrown after an invalid
     * required similarity in the similarity filter is specified.
     */
    public InvalidSimilarityException()
    {
        super("Similarity Exception", "The required similarity must be a value between 0 and 100.");
    }
}
