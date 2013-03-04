/*
 * @(#)IUseCaseDocumentGenerator.java
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
 * -------  ------------    ----------    ----------------------------
 * mcms     07/09/2009                    Initial Creation
 * lmn3     07/10/2009                    Changes due code inspection.
 */
package br.ufpe.cin.target.uceditor.export;

import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;

/**
 * 
 * Exports the use case document for a selected format.
 *
 */
public interface IUseCaseDocumentGenerator
{
    /**
     * 
     * Each implementation of this method will transform the Use Case document in the implemented format.
     * @param useCaseDocument
     */
    public void generateUseCaseDocumentFile(UseCaseDocument useCaseDocument);
}
