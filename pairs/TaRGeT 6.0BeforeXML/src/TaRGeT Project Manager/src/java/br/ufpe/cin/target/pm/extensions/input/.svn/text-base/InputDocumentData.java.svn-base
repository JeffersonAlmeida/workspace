/*
 * @(#)InputDocumentData.java
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
 * wmr068    Aug 5, 2008    LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.pm.extensions.input;

import org.eclipse.jface.resource.ImageDescriptor;

/**
 * This class encapsulates the input documents data. Examples of data are document name, document
 * type, the image icon of the document, and the Input Document extension plug-in.
 */
public class InputDocumentData
{
    /**
     * A reference to the extension plug-in of this input document.
     */
    private InputDocumentExtension inputDocumentExtension;

    /**
     * A reference to the name of this input document.
     */
    private String documentName;

    /**
     * A reference to the extension (i.e. *.doc, *.xml etc) of this input document.
     */
    private String documentTypeFilter;

    /**
     * A reference to the extension (i.e. doc, xml etc) of this input document.
     */
    private String documentType;

    /**
     * A reference to the icon of this input document.
     */
    private ImageDescriptor documentIconDescriptor;

    /**
     * A reference to the error icon of this input document.
     */
    private ImageDescriptor documentErrorIconDescriptor;

    /**
     * Constructor of the <code>InputDocumentData</code> class.
     * 
     * @param inputDocumentExtension the extension plug-in of this input document.
     * @param documentName the input document name.
     * @param documentTypeFilter the extension (i.e. *.doc, *.xml etc) of this input document to be
     * filtered.
     * @param documentType the extension (i.e. doc, xml etc) of this input document.
     * @param documentIconDescriptor the <code>ImageDescriptor</code> representing the icon of
     * this input document.
     * @param documentErrorIconDescriptor the <code>ImageDescriptor</code> representing the error
     * icon of this input.
     */
    public InputDocumentData(InputDocumentExtension inputDocumentExtension, String documentName,
            String documentTypeFilter, String documentType, ImageDescriptor documentIconDescriptor,
            ImageDescriptor documentErrorIconDescriptor)
    {
        this.inputDocumentExtension = inputDocumentExtension;
        this.documentName = documentName;
        this.documentTypeFilter = documentTypeFilter;
        this.documentType = documentType;
        this.documentIconDescriptor = documentIconDescriptor;
        this.documentErrorIconDescriptor = documentErrorIconDescriptor;
    }

    /**
     * Gets the Input Document extension plug-in in which this <code>InputDocumentData</code> is
     * related to.
     * 
     * @return the Input Document extension plug-in in which this <code>InputDocumentData</code>
     * is related to.
     */
    public InputDocumentExtension getInputDocumentExtension()
    {
        return inputDocumentExtension;
    }

    /**
     * Gets the name of this input document.
     * 
     * @return the name of this input document.
     */
    public String getDocumentName()
    {
        return documentName;
    }

    /**
     * Gets the extension (i.e. *.doc, *.xml etc) of this input document to be filtered.
     * 
     * @return the extension of this input document to be filtered.
     */
    public String getDocumentTypeFilter()
    {
        return documentTypeFilter;
    }

    /**
     * Gets the extension (i.e. doc, xml etc) of this input document.
     * 
     * @return the extension of this input document.
     */
    public String getDocumentType()
    {
        return documentType;
    }

    /**
     * Gets the <code>ImageDescriptor</code> representing the icon of this input document.
     * 
     * @return the <code>ImageDescriptor</code> representing the icon of this input document.
     */
    public ImageDescriptor getDocumentIconDescriptor()
    {
        return documentIconDescriptor;
    }

    /**
     * Gets the <code>ImageDescriptor</code> representing the error icon of this input document.
     * 
     * @return the <code>ImageDescriptor</code> representing the error icon of this input
     * document.
     */
    public ImageDescriptor getDocumentErrorIconDescriptor()
    {
        return documentErrorIconDescriptor;
    }

}
