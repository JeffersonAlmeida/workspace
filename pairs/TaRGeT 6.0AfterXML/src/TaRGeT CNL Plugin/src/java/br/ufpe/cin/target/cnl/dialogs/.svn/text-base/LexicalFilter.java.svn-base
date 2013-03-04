/*
 * @(#)CNLViewerFilter.java
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
 * lmn3   02/09/2009                     Initial creation.
 */
package br.ufpe.cin.target.cnl.dialogs;

import java.util.regex.Pattern;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

/**
 * this class represents a table vier filter for the error table on CNL View
 * << high level description of the class >>
 *<pre>
 * CLASS:
 * << Short description of the Utility Function or Toolkit >>
 * [ Template guidelines:
 *     When the class is an API UF, describe only WHAT the UF shall
 *     do, when it is an implementation UF, describe HOW the UF will
 *     do what it should, and if it is a tailoring to an already existing
 *     UF, describe what are the diferences that made the UF needed.
 * ]
 * RESPONSIBILITIES:
 * <<High level list of things that the class does>>
 * 1) responsibility
 * 2) ...
 * <Example:
 * RESPONSIBILITIES:
 * 1) Provide interface to access navigation related UFs
 * 2) Guarantee easy access to the UFs
 * 3) Provide functional implementation to the ScrollTo UF for P2K.
 * >
 *
 * COLABORATORS:
 * << List of descriptions of relationships with other classes, i.e. uses,
 * contains, creates, calls... >>
 * 1) class relationship
 * 2) ...
 *
 * USAGE:
 * << how to use this class - for UFs show how to use the related 
 * toolkit call, for toolkits show how a test case should access the
 * functions >>
 *
 */
//INSPECT: Felype - Inspect new class.
public class LexicalFilter extends ViewerFilter
{
    
    private String string;
    
    public LexicalFilter()
    {
        this.string = "";
    }

    
    public boolean select(Viewer viewer, Object parentElement, Object element)
    {
        boolean result = false;
        
        LexicalEntry lexicalEntry = (LexicalEntry) element;
        
        if(lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.NN)
                || lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.VB)
                || lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.JJ)
                || lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.ADV)
                || lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.PP)
                || lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.CJ)
                || lexicalEntry.getAvailablePOSTags().contains(PartsOfSpeech.DT))
        {
            String word = POSUtil.getInstance().getDefaultTerm(lexicalEntry).toLowerCase();

            String category = POSUtil.getInstance().explainTerm(lexicalEntry).toLowerCase();
            
            this.string = this.string.toLowerCase();
            
            String pattern = this.string.replaceAll(Pattern.quote("*"), ".*");
            
            pattern = pattern.replaceAll(Pattern.quote("?"), ".");
            
            if(word.startsWith(this.string)
                    || category.startsWith(this.string)
                    || word.matches(pattern)
                    || category.matches(pattern))
            {
                result = true;
            }
        }

        return result;
    }
    
    public void setString(String string)
    {
        this.string = string;
    }
}
