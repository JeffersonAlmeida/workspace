/*
 * @(#)PDFGenerator.java
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
 * fsf2     07/10/2009                    Changes due code inspection.
 * lmn3     09/10/2009                    Changes due code inspection.
 */
package br.ufpe.cin.target.uceditor.save;

import java.io.File;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditor;

/**
 * 
 * This class is responsible for saving the modifications on the use case document performed using the use case editor.
 *
 */
public class XMLFileGenerator
{
    br.ufpe.cin.target.common.ucdoc.xml.XMLFileGenerator xmlGenerator;
    
    private Shell shell;
    
    /**
     * 
     * Class Constructor.
     * 
     * @param useCaseDocument the use case document to be saved
     * @param shell the parent shell.
     */
    public XMLFileGenerator(UseCaseDocument useCaseDocument, Shell shell)
    {
        this.xmlGenerator = new br.ufpe.cin.target.common.ucdoc.xml.XMLFileGenerator(useCaseDocument);
        this.shell = shell;
    }
    
    /**
     * 
     * Creates the xml document representation of the use case document. 
     */
    public void createXMLSchema(FormEditor editor)
    {
        this.xmlGenerator.createXMLSchema();
        
        if (this.xmlGenerator.getFeature() != null)
        {
        		this.generateXMLFile(this.xmlGenerator.getFeature(), editor);
            
        }
    }
    
    /**
     * 
     * Creates and saves the xml use case file.
     * @param feature the use case document feature.
     */
    public void generateXMLFile(Feature feature, FormEditor editor)
    {
        String fileName = this.xmlGenerator.getUseCaseDocument().getDocFilePath();
        File file = new File(fileName);

        if (file.exists())
        {
            String fileExtension = FileUtil.getFileExtension(file);
            fileName = fileName.replace(fileExtension, "xml");

            file.delete();
        }
        else
        {
            // Create a Dialog to the user inform the name of the new file
            FileDialog dialog = new FileDialog(shell, SWT.SAVE);
            
            dialog.setText(Properties.getProperty("save_as"));
            dialog.setFileName(feature.getName() + ".xml");
            
            String useCaseDirectory = ProjectManagerController.getInstance().getCurrentProject()
                    .getRootDir().toString()
                    + "\\usecases\\";

            dialog.setFilterPath(useCaseDirectory);

            Composite container = new Composite(shell, SWT.NULL);
            Text projectNameText = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);            
            projectNameText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
            
            dialog.setFilterExtensions(new String[] { ".xml" });
            dialog.setFilterNames(new String[] { "xml" });
            
            projectNameText.insert(dialog.open());

            if (dialog.getFileName() != null)
            {
                fileName = useCaseDirectory + dialog.getFileName();
                this.xmlGenerator.getUseCaseDocument().setDocFilePath(fileName);
                //INSPECT mcms 28/01 mudança de lugar do set dirty para q o asterisco suma somente quando o arquivo for salvo
                ((UseCaseEditor)editor).setDirty(false);
            }
        }
        
        this.xmlGenerator.saveXMLFile(new File(fileName));
    }
}
