/*
 * @(#)InputDocumentExtensionImplementation.java
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
 * wmr068    Jul 31, 2008    LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.mswordinput.controller;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.mswordinput.filefilter.WordFileFilter;
import br.ufpe.cin.target.mswordinput.ucdoc.WordDocumentProcessing;

import br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension;

/**
 * This class implements the Input Document extension point for MS Word files.
 */
public class MSWordDocumentExtensionImplementation extends InputDocumentExtension
{

    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension#loadDocument(java.io.File)
     */
    
    public UseCaseDocument loadDocument(File docToBeLoaded) throws TargetException
    {
        WordDocumentProcessing wordProcessing = WordDocumentProcessing.getInstance();

        List<String> docNames = new ArrayList<String>();
        docNames.add(docToBeLoaded.getAbsolutePath());

        UseCaseDocument loadedDocument = wordProcessing.createObjectsFromWordDocument(docNames, true)
                .get(0);

        return loadedDocument;
    }

    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension#getFileFilter()
     */
    
    public FileFilter getFileFilter()
    {
        return new WordFileFilter();
    }

    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension#saveDocument(br.ufpe.cin.target.common.ucdoc.UseCaseDocument)
     */
    
    public void saveDocument(UseCaseDocument useCaseDocument) throws TargetException
    {
        throw new UnsupportedOperationException("Save document operation is not implemented");
    }

}
