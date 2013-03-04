/*
 * @(#)TCGFlowStep.java
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
 * wdt022    Jun 08, 2007   LIBmm42774   Initial creation.
 * dhq348    Nov 26, 2007   LIBoo10574   Added setup information.
 * dhq348    Jan 23, 2008   LIBoo10574   Rework after inspection LX229632.
 */
package br.ufpe.cin.target.tcg.extractor;

import br.ufpe.cin.target.common.lts.Transition;
import br.ufpe.cin.target.common.ucdoc.FlowStep;

/**
 * This class extends the <code>FlowStep</code> class with information related to the LTS
 * transition.
 * 
 * <pre>
 * CLASS:
 *
 * The class contains an attribute of the <code>Transition</code> class.
 * </pre>
 */
public class TCGFlowStep extends FlowStep
{
    /** The LTS transition that contains the flow step */
    private Transition<FlowStep, Integer> transition;

    /**
     * This constructor calls the <code>FlowStep</code> constructor, informing as parameters the
     * information contained in the transition.
     * 
     * @param transition The LTS transition.
     */
    //INSPECT - Laís Exclusão do atributo setup
    public TCGFlowStep(Transition<FlowStep, Integer> transition)
    {
        super(transition.getValue().getId(), transition.getValue().getUserAction(), transition
                .getValue().getSystemCondition(), transition.getValue().getSystemResponse(),
                transition.getValue().getRelatedRequirements());

        this.transition = transition;
    }

    /**
     * Get method for the LTS transition.
     * 
     * @return The LTS transition.
     */
    public Transition<FlowStep, Integer> getTransition()
    {
        return this.transition;
    }
    
    /* (non-Javadoc)
     * @see br.ufpe.cin.target.common.ucdoc.FlowStep#equals(java.lang.Object)
     */
    
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
    
    private boolean isEquals(Object obj1, Object obj2)
    {
        boolean retorno = true;
        
        if(obj1 == null ^ obj2 == null)
        {
            retorno = false;
        }
        else
        {
            retorno = (obj1 != null) ? obj1.equals(obj2) : true;
        }
        
        return retorno;
    }
}
