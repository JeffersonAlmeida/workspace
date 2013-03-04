/*
 * @(#)FeatureUtil.java
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
 * dhq348   Jul 23, 2007    LIBmm76995   Initial creation.
 * dhq348   Jan 24, 2008    LIBnn34008   Rework after inspection LX229625.
 */
package br.ufpe.cin.target.common.util;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;

/**
 * Class that contains some utility methods when dealing with objects related to use cases;
 */
public class UseCaseUtil
{
    private static String separator = "#";

    /**
     * Composes a use case reference given a <code>feature</code> and a <code>usecase</code>.
     * 
     * @param feature The feature used to compose the reference.
     * @param usecase The use case whose reference will be composed.
     * @return A use case reference.
     */
    public static String getUseCaseReference(Feature feature, UseCase usecase)
    {
        return UseCaseUtil.getUseCaseReference(feature.getId(), usecase.getId());
    }

    /**
     * Composes a use case reference given a <code>flowStep</code>.
     * 
     * @param flowStep The flow step used to compose the use case reference.
     * @return A use case reference.
     */
    public static String getUseCaseReference(FlowStep flowStep)
    {
        return UseCaseUtil.getUseCaseReference(flowStep.getId().getFeatureId(), flowStep.getId()
                .getUseCaseId());
    }

    /**
     * Composes a use case reference given a <code>feature</code> Id and an <code>usecase</code>
     * Id.
     * 
     * @param featureId The id of the feature used to compose the reference.
     * @param useCaseId The id of the use case whose reference will be composed.
     * @return A use case reference.
     */
    public static String getUseCaseReference(String featureId, String useCaseId)
    {
        String reference = featureId;

        if (!useCaseId.equals("") && !featureId.equals(""))
        {
            reference += separator;
        }
        reference += useCaseId;

        return reference;
    }

    /**
     * Formats the given <code>stepId</code> as a string depending on the existence of its
     * defining feature and usecase.
     * 
     * @param stepId The identifier to be transformed.
     * @return A string containing the fields of the given <code>stepId</code> separated by '#'.
     */
    public static String getStepIdAsString(StepId stepId)
    {
        String result = null;
        if (stepId == StepId.START)
        {
            result = StepId.START.getStepId();
        }
        else if (stepId == StepId.END)
        {
            result = StepId.END.getStepId();
        }
        else if (stepId.getFeatureId().equals(""))
        {
            if (stepId.getUseCaseId().equals(""))
            {
                result = stepId.getStepId();
            }
            else
            {
                result = stepId.getUseCaseId() + "#" + stepId.getStepId();
            }
        }
        else
        {
            result = stepId.getFeatureId() + "#" + stepId.getUseCaseId() + "#" + stepId.getStepId();
        }
        return result;
    }

    /**
     * Splits the use case references in an array of strings.
     * 
     * @param reference The use case reference to be broken.
     * @return A string containing the elements of the use case id.
     */
    public static String[] splitUseCaseReference(String reference)
    {
        return reference.split(separator);
    }
}
