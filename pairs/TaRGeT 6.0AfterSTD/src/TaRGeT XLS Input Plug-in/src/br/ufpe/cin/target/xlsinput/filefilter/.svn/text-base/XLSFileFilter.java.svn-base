/*
 * @(#)XMLFileFilter.java
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
 * wmr068    Aug 07, 2008   LIBqq64190   Initial creation.
 */
package br.ufpe.cin.target.xlsinput.filefilter;

import java.io.File;
import java.io.FileFilter;

/**
 * Implements a FileFilter to XML files.
 * 
 * <pre>
 * CLASS:
 * Implements a <code>FileFilter</code> to XML files. This filter accepts only files that end with '.xml'.
 */
public class XLSFileFilter implements FileFilter
{
    /**
     * This method accepts file names that ends with '.xml'.
     * 
     * @param file The file which extension will be checked.
     * @return <i>True</i> if the file ends with '.xml' or <i>false</i> otherwise.
     */
    public boolean accept(File file)
    {
        return !file.isDirectory() && file.getAbsolutePath().endsWith(".xls");
    }

}
