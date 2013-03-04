/*
 * @(#)OutputDocumentData.java
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
 * fsf2      Feb 27, 2009                Initial creation.
 */
package br.ufpe.cin.target.tcg.extensions.output;

/**
 * <pre>
 * CLASS:
 * Represents an output document data.
 * 
 * RESPONSIBILITIES:
 * This class encapsulates the output document generation. 
 * 
 * </pre>
 */
public class OutputDocumentData
{
	/**
     * A reference to the extension plug-in of this output generator.
     */
    private OutputDocumentExtension outputDocumentExtension;

    /**
     * Constructor of the <code>OutputDocumentData</code> class.
     */
    public OutputDocumentData(OutputDocumentExtension outputDocumentExtension)
    {
        this.outputDocumentExtension = outputDocumentExtension;
    }

    /**
     * Gets the Output Document extension plug-in in which this <code>OutputDocumentExtension</code> is
     * related to.
     * 
     * @return the Output Document extension plug-in in which this <code>OutputDocumentExtension</code>
     * is related to.
     */
    public OutputDocumentExtension getOutputDocumentExtension()
    {
        return this.outputDocumentExtension;
    }
}
