/*
 * @(#)ProjectManagerExternalFacade.java
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
 * wdt022   Nov 27, 2006    LIBkk11577   Initial creation.
 * dhq348   Nov 28, 2006    LIBkk11577   Javadoc comments/First methods
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * dhq348   Jul 06, 2007    LIBmm76995   Modifications according to CR.
 * dhq348   Aug 20, 2007    LIBmm93130   Modifications according to CR.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 */
package br.ufpe.cin.target.pm.export;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProject;
import br.ufpe.cin.target.pm.errors.Error;


/**
 * This is the Facade for other plug-ins. It is through this class that functionalities of the plug-in
 * are visible to other plug-ins.
 * 
 * <pre>
 * CLASS:
 * This is the Facade for other external plug-ins. It is used by other plug-ins to access the functionalities of the Project Manager plug-in
 * 
 * RESPONSIBILITIES:
 * 1) Work as an access point to the plug-in functionalities.
 *
 * COLABORATORS:
 *
 * USAGE:
 * ProjectManagerExternalFacade manager = ProjectManagerExternalFacade.getInstance();
 */
public class ProjectManagerExternalFacade
{

    /**
     * The single instance
     */
    private static ProjectManagerExternalFacade instance;

    /**
     * The private constructor of ProjectManagerExternalFacade.
     */
    private ProjectManagerExternalFacade()
    {

    }

    /**
     * Manages the singleton instantiation
     * 
     * @return the ProjectManagerExternalFacade instance
     */
    public static ProjectManagerExternalFacade getInstance()
    {
        if (instance == null)
        {
            instance = new ProjectManagerExternalFacade();
        }
        return instance;
    }

    /**
     * Checks if exists an already opened Target project.
     * 
     * @return <b>true</b> is there is one opened project and <b>false</b> otherwise.
     */
    public boolean hasOpenedProject()
    {
        return ProjectManagerController.getInstance().hasOpenedProject();
    }

    /**
     * Returns the opened Target project
     * 
     * @return The current project.
     */
    public TargetProject getCurrentProject()
    {
        return ProjectManagerController.getInstance().getCurrentProject();
    }

    /**
     * Checks if there are errors in an opened project. It does not consider warnings.
     * 
     * @return <b>true</b> if there is at least one error or <b>false</b> otherwise.
     */
    public boolean hasErrorsInProject()
    {
        boolean result = false;
        for (Error error : ProjectManagerController.getInstance().getErrorList())
        {
            if (error.getType() == Error.ERROR || error.getType() == Error.FATAL_ERROR)
            {
                result = true;
                break;
            }
        }
        return result;
    }

    /**
     * Returns an ordered list of all requirements that are referenced in the project.
     * 
     * @return The ordered list of all referenced requirements.
     */
    public List<String> getAllReferencedRequirementsOrdered()
    {
        return ProjectManagerController.getInstance().getAllReferencedRequirementsOrdered();
    }

    /**
     * Gets a set of use cases related to the given <code>requirement</code>.
     * 
     * @param requirement The requirement whose related use cases will be searched.
     * @return A set of use cases related to the given <code>requirement</code>.
     */
    public Set<UseCase> getUseCasesByRequirement(String requirement)
    {
        Set<UseCase> result = new HashSet<UseCase>();

        for (Feature feature : ProjectManagerController.getInstance().getCurrentProject()
                .getFeatures())
        {
            for (UseCase usecase : feature.getUseCases())
            {
                if (usecase.getAllRelatedRequirements().contains(requirement))
                {
                    result.add(usecase);
                }
            }
        }

        return result;
    }

    /**
     * Method used to get all features, even merged ones.
     * 
     * @return All features in the project.
     */
    public Collection<Feature> getAllFeatures()
    {
        return ProjectManagerController.getInstance().getAllFeatures();
    }
    
    /**
     * Gets the use case which has the given <code>id</code>.
     * 
     * @param ucId The id of the searched use case.
     * @return The use case related to the given <code>id</code>.
     */
    public UseCase getUseCasesById(String ucId){
        UseCase result = null;

        for (Feature feature : ProjectManagerController.getInstance().getCurrentProject()
                .getFeatures())
        {
            for (UseCase usecase : feature.getUseCases())
            {
                if (usecase.getId().equals(ucId))
                {
                    result = usecase;
                    break;
                }
            }
        }
        
        return result;
    }
}
