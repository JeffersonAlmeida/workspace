/*
 * @(#)InputDocumentTemplateExtension.java
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
 * fsf2      27/03/2009                  Initial creation.
 */
package br.ufpe.cin.target.tcg.extensions.inputTemplate;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.util.GUIUtil;

/**
 * <pre>
 * CLASS:
 * This class contains the code for extracting the header information from a Test Central 4 spreadsheet.
 * 
 * RESPONSIBILITIES:
 * Extracts the Test Central 4 spreadsheet header. 
 *
 * COLABORATORS:
 *
 * USAGE:
 * InputDocumentTemplateExtension inputDocumentTemplateExtension = new InputDocumentTemplateExtension();
 */
public abstract class InputDocumentTemplateExtension 
{	
	/**
	 * Offers the possibility to extract the header information from a Test Central 4 spreadsheet.
	 * @return true, if the user wants to import the information, false otherwise.
	 */
    public boolean openDialog()
	{
		return GUIUtil.openYesOrNoInformation(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell(),
				Properties.getProperty("import_template"),Properties.getProperty("import_template_msg"));
	}

    /**
     * Returns the header information.
     * @param file 
     * @return informationTestCase, an object with the header information.
     * @throws TargetImportTemplateException 
     */
	abstract public InformationTestCase getInformationTestCase() throws TargetException;

	/**
	 * Returns the file path of a specified document.
	 * @return path, the string with the path.
	 */
	protected String getFilePath() 
	{
	    String result = null;
	    
	    Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();

		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setText(Properties.getProperty("browse_file"));

		dialog.setFilterExtensions(new String[]{"*.xls"});
		dialog.setFilterNames(new String[]{Properties.getProperty("excel_documents")});

		String folderName = dialog.open();
		
		if(folderName != null)
		{
		    folderName = FileUtil.getFilePath(folderName);

	        result = dialog.getFileName();
	        
	        result = folderName + File.separator + result;
		}

		return result;
	}
}
