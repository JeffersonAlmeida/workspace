/*
 * @(#)LexicalTermsSort.java
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
 * fsf2      18/09/2009                  Initial creation.
 */
package br.ufpe.cin.target.cnl.dialogs;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;

/**
 * 
 * This class represents a table sorter for the error table in the CNL View.
 *
 */
public class LexicalTermsSort extends ViewerSorter
{
    private int propertyIndex;
    
    private static final int DESCENDING = 1;

    private int direction = DESCENDING;
    
    /**
     * 
     * Constructor.
     *
     */
    public LexicalTermsSort() {
        this.propertyIndex = 0;
        direction = DESCENDING;
    }

    public void setColumn(int column) {
        if (column == this.propertyIndex) {
            // Same column as last sort; toggle the direction
            direction = 1 - direction;
        } else {
            // New column; do an ascending sort
            this.propertyIndex = column;
            direction = DESCENDING;
        }
    }

    
    public int compare(Viewer viewer, Object e1, Object e2) {
        LexicalEntry lexicalEntry1 = (LexicalEntry) e1;
        LexicalEntry lexicalEntry2 = (LexicalEntry) e2;
        
        int result = 0;
        
        switch (propertyIndex) {
          //Term
          case 0:
              String thisString = POSUtil.getInstance().getDefaultTerm(lexicalEntry1);
              String otherString = POSUtil.getInstance().getDefaultTerm(lexicalEntry2);
              
              result = otherString.compareTo(thisString);
              
              break;
          //Type
          case 1:
              String thisPartsOfSpeech = POSUtil.getInstance().explainTerm(lexicalEntry1);
              String otherPartsOfSpeech = POSUtil.getInstance().explainTerm(lexicalEntry2);
              
              result = thisPartsOfSpeech.toString().compareTo(otherPartsOfSpeech.toString());

              break;
        }
        // If descending order, flip the direction
        if (direction == DESCENDING) {
            result = -result;
        }
        return result;
    }
}
