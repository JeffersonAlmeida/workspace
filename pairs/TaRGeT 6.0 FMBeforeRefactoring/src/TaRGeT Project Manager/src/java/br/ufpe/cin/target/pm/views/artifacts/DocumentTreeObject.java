/*
 * @(#)DocumentTreeObject.java
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
 * dhq348    Jan 9, 2007    LIBkk11577   Initial creation.
 * dhq348    Jan 17, 2007   LIBkk11577   Rework of inspection LX133710.
 */
package br.ufpe.cin.target.pm.views.artifacts;

import br.ufpe.cin.target.pm.common.TreeObject;

/**
 * This class represents a document that is displayed in the GUI tree view.
 * 
 * <pre>
 * CLASS:
 * Extends TreeObject with the document type and absolute path. The document type information is
 * needed to open a correct editor when the use realizes a double click in feature document or test
 * suite excel spreadsheet.
 */
public class DocumentTreeObject extends TreeObject
{
    /**
     * The document type of the tree object.
     */
    private DocumentType documentType;

    /**
     * The absolute path of the tree object.
     */
    private String absolutePath;

    /**
     * Initializes the name, document type and absolute path of the tree object with the values
     * passed as parameters.
     * 
     * @param name The name of the tree object.
     * @param absolutePath The absolute path of the tree object.
     * @param documentType The document type of the tree object.
     */
    public DocumentTreeObject(String name, String absolutePath, DocumentType documentType)
    {
        super(name);
        this.documentType = documentType;
        this.absolutePath = absolutePath;
    }

    /**
     * The get method for <code>documentType</code> attribute.
     * 
     * @return The documentType attribute.
     */
    public DocumentType getDocumentType()
    {
        return documentType;
    }

    /**
     * The get method for <code>absolutePath</code> attribute.
     * 
     * @return The absolutePath attribute.
     */
    public String getAbsolutePath()
    {
        return absolutePath;
    }
}
