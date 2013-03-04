/*
 * @(#)UseCaseError.java
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
 * dhq348   Feb 15, 2007    LIBll27713   Initial creation. Created after LX144782 inspection.
 */
package br.ufpe.cin.target.pm.errors;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;


/**
 * This abstract class encapsulates code that are common to all use case errors.
 * 
 * <pre>
 * CLASS:
 * Besides the use case with error, the class stores the name of the document 
 * that contains the feature in which the use case is included.
 *   
 */
public abstract class UseCaseError implements Error
{
    /** The name of the document that contains the feature */
    protected String documentName;

    /** The feature that contains the use case with duplicated ID */
    protected Feature feature;

    /** The use case with duplicated ID */
    protected UseCase useCase;

    /**
     * Receives the document name, feature and use case.
     * 
     * @param feature The feature where the error occurred.
     * @param useCase The use case where the error occurred.
     * @param documentName The name of the document where the error occurred.
     */
    public UseCaseError(Feature feature, UseCase useCase, String documentName)
    {
        this.documentName = documentName;
        this.feature = feature;
        this.useCase = useCase;
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
     * The get method for <code>useCase</code> attribute.
     * 
     * @return Returns the useCase.
     */
    public UseCase getUseCase()
    {
        return useCase;
    }
}
