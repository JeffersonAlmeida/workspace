/*
 * @(#)CNLFaultComparator.java
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
package br.ufpe.cin.target.cnl.views;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

import br.ufpe.cin.target.cnl.controller.CNLFault;

/**
 * 
 * This class represents a table sorter for the error table in the CNL View.
 *
 */
public class CNLTableSorter extends ViewerSorter
{
    private int propertyIndex;
    
    private static final int DESCENDING = 1;

    private int direction = DESCENDING;
    
    /**
     * 
     * Constructor.
     *
     */
    public CNLTableSorter() {
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
        CNLFault fault1 = (CNLFault) e1;
        CNLFault fault2 = (CNLFault) e2;
        int result = 0;
        
        switch (propertyIndex) {
          //details
          case 0:
              if(fault1.getDetails().compareTo(fault2.getDetails()) > 0 ){
                  result = 1;
              }
              else if(fault1.getDetails().compareTo(fault2.getDetails()) < 0 ){
                  result = -1;
              }
              break;
          //sentence
          case 1:
              if(fault1.getSentence().compareTo(fault2.getSentence()) > 0 ){
                  result = 1;
              }
              else if(fault1.getSentence().compareTo(fault2.getSentence()) < 0 ){
                  result = -1;
              }
              break;
          //step
          case 2:
              if(fault1.getStep().compareTo(fault2.getStep()) > 0 ){
                  result = 1;
              }
              else if(fault1.getStep().compareTo(fault2.getStep()) < 0 ){
                  result = -1;
              }
              break;
          //resource
          case 3:
              if(fault1.getResource().compareTo(fault2.getResource()) > 0 ){
                  result = 1;
              }
              else if(fault1.getResource().compareTo(fault2.getResource()) < 0 ){
                  result = -1;
              }
              break;
        }
        // If descending order, flip the direction
        if (direction == DESCENDING) {
            result = -result;
        }
        return result;
    }
}
