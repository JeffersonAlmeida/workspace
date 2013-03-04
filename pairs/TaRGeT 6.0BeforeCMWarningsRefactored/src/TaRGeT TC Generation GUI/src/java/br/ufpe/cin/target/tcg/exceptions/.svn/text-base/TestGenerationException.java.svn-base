/*
 * @(#)TestGenerationException.java
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
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modifications after inspection (LX135035).
 */
package br.ufpe.cin.target.tcg.exceptions;

import br.ufpe.cin.target.common.exceptions.TargetException;

/**
 * Exception that indicates a problem while generating test cases.
 * 
 * <pre>
 * CLASS:
 * Exception that indicates a problem while generating test cases.
 * 
 * RESPONSIBILITIES:
 * N/A
 *
 * COLABORATORS:
 * N/A
 *
 * USAGE:
 * N/A
 */
public class TestGenerationException extends TargetException
{
    /** Default serial Id */ 
    private static final long serialVersionUID = 0L;

    /** The title of the exception */
    private String title;

    /**
     * Initializes the Exception.
     * 
     * @param title The title of the exception.
     * @param message The message of the cause of the exception.
     */
    public TestGenerationException(String title, String message)
    {
        super(message);
        this.title = title;
    }

    /**
     * Retrieved the title of the exception.
     * 
     * @return Exception title.
     */
    public String getTitle()
    {
        return this.title;
    }

}
