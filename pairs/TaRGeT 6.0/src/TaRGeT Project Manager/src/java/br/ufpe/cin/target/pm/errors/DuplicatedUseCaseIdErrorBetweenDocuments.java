/*
 * @(#)DuplicatedUseCaseIdErrorBetweenDocuments.java
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
 * dhq348   Jun 19, 2007    LIBmm47221   Initial creation.
 */
package br.ufpe.cin.target.pm.errors;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.FileUtil;

/**
 * This class represents a merge error, that is when two features in different documents have the
 * same id and a common use case.
 * 
 * <pre>
 * CLASS:
 * This class represents a feature merge error when at least two features have the 
 * same id and they also have a common use case.
 * </pre>
 */
public class DuplicatedUseCaseIdErrorBetweenDocuments extends UseCaseError
{

    /**
     * The name of the other document where the duplicated feature occurs.
     */
    private String documentName2;

    /**
     * Receives as parameters the feature and the documents where the use case duplication occurred.
     * 
     * @param feature The feature with the duplicated use ID.
     * @param useCase The use case with duplicated ID.
     * @param documentName The name of the document where the feature appears.
     * @param documentName2 The name of the document where the other feature appears.
     */
    public DuplicatedUseCaseIdErrorBetweenDocuments(Feature feature, UseCase useCase,
            String documentName, String documentName2)
    {
        super(feature, useCase, documentName);
        this.documentName2 = documentName2;
    }

    /**
     * Method used to retrieve the error description.
     * 
     * @return The error description.
     */
    public String getDescription()
    {
        return "The feature " + this.feature.getId() + " in document "
                + FileUtil.getFileName(this.documentName) + " and document "
                + FileUtil.getFileName(this.documentName2) + " has the common use case "
                + this.useCase.getId() + " - " + this.useCase.getName();
    }

    /**
     * Method used to retrieve the location where the error appears in the resource. It returns the
     * name and id of the use case.
     * 
     * @return The names of the documents where the duplication occurred.
     */
    public String getLocation()
    {
        return FileUtil.getFileName(this.documentName) + " and "
                + FileUtil.getFileName(this.documentName2);
    }

    /**
     * Method used to retrieve the path of the resource. It is the absolute path of the document.
     * 
     * @return The absolute path of the document.
     */
    public String getPath()
    {
        return this.documentName;
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

    /**
     * Gets the type of the error.
     * 
     * @return Returns the value <code>Error.FATAL_ERROR</code>.
     */
    public int getType()
    {
        return Error.FATAL_ERROR;
    }

    /**
     * Gets the second document name.
     * @return The second document name.
     */
    public String getSecondDocumentName() {
        return this.documentName2;
    }
}
