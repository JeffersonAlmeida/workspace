/*
 * @(#)InputExtension.java
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
 * wmr068    Jul 31, 2008   LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.pm.extensions.input;

import java.awt.Desktop;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;

/**
 * This class represents the extension point for different kinds of input documents. This way, this
 * class must be extended by clients that need to implement a new input document extension to the
 * TaRGeT system.
 */
public abstract class InputDocumentExtension
{

    /**
     * Opens a file which represents the input document. This method uses the Desktop API provided
     * by Java, so that it opens the input document by using the currently default program installed
     * in the operating system. For example, if the input document is a *.doc file and the Microsoft
     * Word is the default program, calling this method implies opening the document through the
     * Microsoft Word. Clients may override this method to change the way of opening documents.
     * 
     * @param file the file to be opened.
     * @throws IOException thrown if the Desktop API is not supported on the current platform.
     */
    public void openInputDocument(File file) throws IOException
    {
        if (Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().open(file);
        }
        else
        {
            throw new UnsupportedOperationException(
                    "Desktop API not supported on the current platform");
        }
    }

    /**
     * Opens the default editor for the given input document. This method uses the Desktop API
     * provided by Java, so that it opens the currently default editor installed in the operating
     * system to edit the input document. For example, if the input document is a *.doc file and the
     * Microsoft Word is the default editor, calling this method implies opening the Microsoft Word
     * program. Clients may override this method to change the way of opening document editors.
     * 
     * @param file the file to be edited.
     * @throws IOException thrown if the Desktop API is not supported on the current platform.
     */
    public void editInputDocument(File file) throws IOException
    {
        if (Desktop.isDesktopSupported())
        {
            Desktop.getDesktop().edit(file);
        }
        else
        {
            throw new UnsupportedOperationException(
                    "Desktop API not supported on the current platform");
        }
    }

    /**
     * Returns the <code>UseCaseDocument</code> of a file. Clients that need to implement a new
     * input document extension must implement this method because the way in which the
     * <code>UseCaseDocument</code> object is created may depend on the kind of the input document.
     * 
     * @param file the file to be loaded.
     * @return the <code>UseCaseDocument</code> of a file.
     * @throws TargetException if a problem occur when loading the file.
     */
    public abstract UseCaseDocument loadDocument(File file) throws TargetException;

    /**
     * Returns the corresponding FileFilter of this kind of input document. For example, if the
     * input document is a XML file, the FileFilter returned by this method must be capable of
     * filtering *.xml files.
     * 
     * @return Returns the corresponding FileFilter of this kind of input document.
     */
    public abstract FileFilter getFileFilter();

    /**
     * Saves the <code>UseCaseDocument</code> into a file.
     * 
     * @param useCaseDocument the <code>UseCaseDocument</code> to be saved.
     * @throws TargetException if a problem occur when saving the <code>UseCaseDocument</code>.
     */
    public abstract void saveDocument(UseCaseDocument useCaseDocument) throws TargetException;

}
