/*
 * @(#)XMLDocumentExtensionImplementation.java
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
 * wmr068    Aug 7, 2008    LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.xmlinput.controller;

import java.io.File;
import java.io.FileFilter;

import br.ufpe.cin.target.xmlinput.filefilter.XMLFileFilter;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.ucdoc.xml.UseCaseDocumentXMLParser;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension;

/**
 * <pre>
 * CLASS:
 * This class implements the Input Document extension point for XML files.
 * 
 * RESPONSIBILITIES:
 * Loads and saves a XML document and generates the XML filter. 
 *
 * COLABORATORS:
 *
 * USAGE:
 * XMLDocumentExtensionImplementation xmlExtension = new XMLDocumentExtensionImplementation();
 */
public class XMLDocumentExtensionImplementation extends InputDocumentExtension 
{

    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension#getFileFilter()
     */
    
    public FileFilter getFileFilter()
    {
        return new XMLFileFilter();
    }

    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension#loadDocument(java.io.File)
     */
    
    public UseCaseDocument loadDocument(File file) throws TargetException
    {
        UseCaseDocumentXMLParser xmlParser = new UseCaseDocumentXMLParser(file);
        
        UseCaseDocument phoneDoc = xmlParser.buildUseCaseDocument();
        
        return phoneDoc;
    }
    
    /* (non-Javadoc)
     * @see br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension#saveDocument(br.ufpe.cin.target.common.ucdoc.UseCaseDocument)
     */
    
    public void saveDocument(UseCaseDocument useCaseDocument) throws TargetException
    {
        throw new UnsupportedOperationException("Save document operation is not implemented");
    }

}
