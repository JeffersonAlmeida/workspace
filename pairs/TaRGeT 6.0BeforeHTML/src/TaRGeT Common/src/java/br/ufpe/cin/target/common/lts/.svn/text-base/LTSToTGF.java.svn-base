/*
 * @(#)LTSToTGF.java
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
 * wsn013    Nov 28, 2006   LIBkk11577   Initial creation.
 * wsn013    Jan 18, 2007   LIBkk11577   Modification after inspection (LX135035).
 * wdt022    Jul 22, 2007   LIBmm42774   Moved from the common.util package.
 */
package br.ufpe.cin.target.common.lts;

import java.util.Iterator;


/**
 * This class is used to translate a LTS<T,S> to the Trivial Graph Format. It is useful for DEBUG
 * purposes.
 * 
 * <pre>
 * CLASS:
 * This class is used to translate a LTS<T,S> to the Trivial Graph Format 
 * (http://www.yworks.com/products/yfiles/doc/developers-guide/tgf.html).
 * 
 * RESPONSIBILITIES:
 * 1) Translate a given LTS to TGF.
 *
 * COLABORATORS:
 * 1) Uses LTS, State and Transitions classes
 *
 * USAGE:
 *  LTSToTGF l2t = new LTSToTGF(lts);
 *  String TGF = t2t.translate();
 */
public class LTSToTGF<T, S>
{
    /** The LTS to be translated */
    private LTS<T, S> lts;

    /**
     * Constructor. Stores the reference to the LTS used as the input to the translation.
     * 
     * @param lts The LTS to be translated.
     */
    public LTSToTGF(LTS<T, S> lts)
    {
        this.lts = lts;
    }

    /**
     * Translates the LTS object to its TGF representation.
     * 
     * @return The TGF content.
     */
    public String translate()
    {
        StringBuffer sb = new StringBuffer("");
        Iterator<State<S, T>> si = lts.getStates();
        while (si.hasNext())
        {
            State s = si.next();
            sb.append(s.getId());
            sb.append(" ");
            sb.append(s.getValue().toString());
            sb.append("\n");
        }
        sb.append("#\n");
        Iterator<Transition<T, S>> ti = lts.getTransitions();
        while (ti.hasNext())
        {
            Transition t = ti.next();
            sb.append(t.getFrom().getId());
            sb.append(" ");
            sb.append(t.getTo().getId());
            sb.append(" ");
            sb.append(t.getValue().toString());
            sb.append("\n");
        }
        return sb.toString();
    }

}
