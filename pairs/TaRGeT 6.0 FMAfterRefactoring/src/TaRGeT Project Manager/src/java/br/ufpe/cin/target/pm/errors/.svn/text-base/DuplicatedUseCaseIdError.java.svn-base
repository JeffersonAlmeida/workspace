/*
 * @(#)DuplicatedUseCaseIdError.java
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
 * wdt022   Jan 29, 2007    LIBll12753   Initial creation.
 * wdt022   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Feb 15, 2007    LIBll27713   Rework of inspection LX144782.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 */
package br.ufpe.cin.target.pm.errors;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.FileUtil;

/**
 * This class represents an error of use case ID duplication.
 * 
 * <pre>
 * CLASS:
 * The class contains the attribute <code>documentName</code>, which represents the name of the 
 * document where the error is located, and the attribute <code>feature</code>, which represents the 
 * feature that contains the use case with duplicated ID. The use case is represented by the attribute <code>useCase</code>.
 */
public class DuplicatedUseCaseIdError extends UseCaseError
{
    /**
     * Builds the duplicated use case ID error. It receives the feature that contains the use case
     * with error and the name of the document where the use case is located.
     * 
     * @param feature The feature with the duplicated use case.
     * @param useCase The use case with duplicated ID.
     * @param documentName The name of the document.
     */
    public DuplicatedUseCaseIdError(Feature feature, UseCase useCase, String documentName)
    {
        super(feature, useCase, documentName);
    }

    /**
     * Method used to retrieve the error description.
     * 
     * @return The error description.
     */
    public String getDescription()
    {
        return "The feature \"" + feature.getId()
                + "\" contains one or more use cases with same id \"" + this.useCase.getId()
                + "\". Verify the document \"" + FileUtil.getFileName(this.documentName) + "\".";
    }

    /**
     * Method used to retrieve the location where the error appears in the resource. It returns the
     * name and id of the use case.
     * 
     * @return The id and the name of the use case.
     */
    public String getLocation()
    {
        return this.useCase.getId() + " - " + this.useCase.getName();
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
     * Verifies the equality of the attributes <code>documentName</code>, <code>feature</code>
     * and <code>useCase</code>.
     */
    
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof DuplicatedUseCaseIdError)
        {
            DuplicatedUseCaseIdError error = (DuplicatedUseCaseIdError) obj;

            result = error.documentName.equals(this.documentName)
                    && error.feature.getId().equals(this.feature.getId())
                    && error.useCase.getId().equals(this.useCase.getId());
        }

        return result;
    }
}
