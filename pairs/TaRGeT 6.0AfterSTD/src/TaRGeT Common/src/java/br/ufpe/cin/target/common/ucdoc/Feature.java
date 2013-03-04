/*
 * @(#)Feature.java
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
 * wra050   Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 02, 2006    LIBkk11577   Added id checking.
 * wdt022   Jan 10, 2007    LIBkk11577   Java doc modifications and refactorings.
 * wdt022   Jan 12, 2007    LIBkk11577   Modifications due to inspection LX128956.
 * dhq348   Jun 06, 2007    LIBmm25975   Method getUseCase(String) added.
 * dhq348   Jul 11, 2007    LIBmm47221   Rework after inspection LX185000. Added the method mergeFeature().
 * dhq348   Jul 25, 2007    LIBmm93130   Added the method equals.
 * tnd783   Apr 07, 2008    LIBpp71785   Fixed the method equals.
 * dwvm83   Sep 23, 2008	LIBqq51204	 Method public Feature mergeFeature(List<Feature> listOfFeatures) deleted
 */
package br.ufpe.cin.target.common.ucdoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.ufpe.cin.target.common.util.StringsUtil;

/**
 * <pre>
 * CLASS:
 * Represents a feature requirement artifact.
 * 
 * RESPONSIBILITIES:
 * Encapsulates a set of related use cases.
 * 
 * USAGE:
 * </pre>
 */
public class Feature implements Comparable<Feature>
{
    /** The use cases list that composes the feature */
    private List<UseCase> useCases;

    /** The feature Id */
    private String id;

    /** The feature name */
    private String name;

    /**
     * This constructor initializes all attributes.
     * 
     * @param id feature id
     * @param name feature name
     * @param useCases The use cases of the feature
     */
    public Feature(String id, String name, List<UseCase> useCases)
    {
        this.id = id;
        this.name = name;
        this.useCases = new ArrayList<UseCase>(useCases);
    }

    /**
     * Return a use case by its Id.
     * 
     * @param id The use case id
     * @return The use case instance. It returns <code>null</code> if no use case is found.
     */
    public UseCase getUseCase(String id)
    {
        UseCase result = null;
        for (UseCase useCase : useCases)
        {
            if (useCase.getId().equals(id))
            {
                result = useCase;
                break;
            }
        }
        return result;
    }

    /**
     * Get method for <code>useCases</code> attribute.
     * 
     * @return The copy use cases list.
     */
    public List<UseCase> getUseCases()
    {
        return new ArrayList<UseCase>(useCases);
    }
    
    /**
     * Get method for <code>useCases</code> attribute.
     * 
     * @return The list of use cases.
     */
    //INSPECT: Felype - Adicionei um método novo.
    public List<UseCase> getRealUseCases()
    {
        return this.useCases;
    }

    /**
     * Get method for <code>id</code> attribute.
     * 
     * @return Returns the feature id.
     */
    public String getId()
    {
        return id;
    }

    /**
     * Get method for <code>name</code> attribute.
     * 
     * @return The feature name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Returns the ids of all steps that are contained in the feature.
     * 
     * @return A list with all step ids.
     */
    public List<StepId> getActualStepIds()
    {
        List<StepId> result = new ArrayList<StepId>();

        for (UseCase useCase : this.useCases)
        {
            result.addAll(useCase.getActualStepIds());
        }

        return result;
    }

    /**
     * Returns the ids of all steps that are referred in the feature.
     * 
     * @return A list with all referred step ids.
     */
    public Set<StepId> getReferredStepIds()
    {
        Set<StepId> result = new HashSet<StepId>();

        for (UseCase useCase : this.useCases)
        {
            result.addAll(useCase.getReferredStepIds());
        }

        return result;
    }

    /**
     * Returns the feature ID and the name.
     */
    
    public String toString()
    {
        return this.id + " - " + this.name;
    }

    /**
     * Merges the current feature with <code>feature2</code> into a single feature.
     * The features need to have the same id (it has to be the same feature)
     * @param feature2 The feature that will be merged with the current one.
     * @return A merged feature.
     */
  
    public Feature mergeFeature(Feature feature2) 
    {
        Feature merged = null;
        if (feature2 != null && feature2.getId().equals(this.getId()))
        {
            List<UseCase> mergedUseCases = new ArrayList<UseCase>(this.getUseCases());
            mergedUseCases.addAll(feature2.getUseCases());
            merged = new Feature(this.getId(), this.getName(), mergedUseCases);
        }
        else if (feature2 == null)
        {
            merged = new Feature(this.getId(), this.getName(), this.getUseCases());
        }
        return merged;
    }

 
    /**
     * Two features are identical if they have the same id.
     * 
     * @param obj The other feature being compared to the current one.
     * @return <code>true</code> if they have the same id or <code>false</code> otherwise.
     */
    
    public boolean equals(Object obj)
    {
        boolean result = false;
        if (obj instanceof Feature)
        {
            result = this.id.equals(((Feature) obj).id);
        }
        return result;
    }
    
    //INSPECT - Lais: Adição do método compare
    
    public int compareTo(Feature feature)
    {
        if (!(feature instanceof Feature))
        {
            throw new ClassCastException("A Feature object is expected.");
        }

        int result = -1;
        if (StringsUtil.compareNatural(this.id, feature.id) == 0)
        {
            result = 0;
        }
        else if (StringsUtil.compareNatural(this.id, feature.id) > 0)
        {
            result = 1;
        }

        return result;        
    }

    /**
     * Sets the useCases value.
     *
     * @param useCases The useCases to set.
     */
    public void setUseCases(List<UseCase> useCases)
    {
        this.useCases = useCases;
    }

    /**
     * Sets the id value.
     *
     * @param id The id to set.
     */
    public void setId(String id)
    {
        this.id = id;
    }

    /**
     * Sets the name value.
     *
     * @param name The name to set.
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    //INSPECT mcms this method was created to allow adding an use case in the list of use cases from 
    //Feature to be used in the class SaveAction 
    public void addUseCase(UseCase uc){
        this.useCases.add(uc);
    }

}
