/*
 * @(#)FeatureError.java
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
 * dhq348    Jun 5, 2007    LIBmm47221   Initial creation.
 * dhq348    Jul 11, 2007   LIBmm47221   Rework after inspection LX185000.
 */
package br.ufpe.cin.target.pm.errors;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.util.FileUtil;

/**
 * This abstract class represents a generic feature error.
 * 
 * <pre>
 * CLASS:
 * It keeps a reference to the feature and the name of the document 
 * where the error appeared.
 * </pre>
 */
public abstract class FeatureError implements Error
{
    /** The name of the document that contains the feature */
    protected String documentName;

    /** The feature with error */
    protected Feature feature;

    /**
     * Constructor that receives a feature and the document name.
     * 
     * @param feature The feature where the error occurred.
     * @param documentName The name of the document where the error occurred.
     */
    public FeatureError(Feature feature, String documentName)
    {
        this.documentName = documentName;
        this.feature = feature;
    }

    /**
     * The get method for <code>documentName</code> attribute.
     * 
     * @return Returns the documentName.
     */
    public String getDocumentName()
    {
        return documentName;
    }

    /**
     * The get method for <code>feature</code> attribute.
     * 
     * @return Returns the feature.
     */
    public Feature getFeature()
    {
        return feature;
    }

    /**
     * Method used to retrieve the resource. It returns the name of the document where the
     * duplicated ID appeared.
     * 
     * @return The document name.
     */
    public String getResource()
    {
        return FileUtil.getFileName(this.documentName);
    }
}
