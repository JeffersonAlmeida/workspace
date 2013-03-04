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
 * lmn3     07/10/2009                    Changes due code inspection.
 */
package br.ufpe.cin.target.uceditor.export;

import java.io.File;
import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.sax.SAXResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.fop.apps.FOUserAgent;
import org.apache.fop.apps.Fop;
import org.apache.fop.apps.FopFactory;
import org.apache.fop.apps.MimeConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.uceditor.progressbar.UseCaseEditorProgressBar;


/**
 * This class is responsible to generate the use case document in a PDF format. 
 */
public class PDFGenerator implements IUseCaseDocumentGenerator
{
    
    /** The XSLT file path */
    private static final String TRANSFORMATION_FILE_SOURCE = FileUtil.getUserDirectory()
            + FileUtil.getSeparator() + "resources" + FileUtil.getSeparator()
            + "XMLToPDFTransform.xsl";
    
    /** The location of the use case directory */
    private static final String EXPORTED_FILES_SOURCE = ProjectManagerController.getInstance()
            .getCurrentProject().getRootDir().toString()
            + "\\exported\\";
    
    private Shell shell;
    
    /**
     * Class Constructor.
     */
    public PDFGenerator()
    {
        super();
    }
    //04/01 
    public PDFGenerator(Shell shell)
    {
    	super();
    	this.shell = shell;
    }
    
    /**
     * 
     * Generates the PDF file of a given use case document file. 
     * @param useCaseDocument the document to be transformed
     */
    
    public void generateUseCaseDocumentFile(UseCaseDocument useCaseDocument)
    { 	
        try
        {
        	UseCaseEditorProgressBar progressBar = new UseCaseEditorProgressBar(Properties.getProperty("exporting_pdf"));
        	ProgressMonitorDialog dialog = new ProgressMonitorDialog(
    				this.shell);
    		dialog.run(false, true, progressBar);
            // Setup directories
            File outDir = new File(EXPORTED_FILES_SOURCE);
            
            if (!outDir.exists())
            {
                outDir.mkdirs();
            }

            // Setup input and output files
            String filePath = useCaseDocument.getDocFilePath();
            if(filePath!=null)
            {
                String fileName = FileUtil.getFileName(filePath);
                fileName = fileName.replaceAll("." + FileUtil.getFileExtension(fileName), "");
                
                System.out.println("Path: " + filePath);
                
                File xmlFile = new File(filePath);
                File xsltFile = new File(TRANSFORMATION_FILE_SOURCE);
                File pdfFile = new File(outDir, fileName + ".pdf");
                
                if(pdfFile.exists()){
                    if(!pdfFile.delete()){
                        MessageDialog.openError(null, Properties.getProperty("error_pdf_file"), Properties.getProperty("error_not_export"));
                        return;
                    }
                }
               
                System.out.println("Input: XML (" + xmlFile + ")");
                System.out.println("Stylesheet: " + xsltFile);
                System.out.println("Output: PDF (" + pdfFile + ")");
                System.out.println();
                System.out.println("Transforming...");
                OutputStream out = new java.io.FileOutputStream(pdfFile);
                
                try
                {
                    // configure fopFactory as desired
                    FopFactory fopFactory = FopFactory.newInstance();

                    FOUserAgent foUserAgent = fopFactory.newFOUserAgent();
                    // configure foUserAgent as desired

                    // Setup output
                    
                    out = new java.io.BufferedOutputStream(out);

                    // Construct fop with desired output format
                    Fop fop = fopFactory.newFop(MimeConstants.MIME_PDF, foUserAgent, out);

                    // Setup XSLT
                    TransformerFactory factory = TransformerFactory.newInstance();
                    Transformer transformer = factory.newTransformer(new StreamSource(xsltFile));

                    // Set the value of a <param> in the stylesheet
                    // transformer.setParameter("useCase", "2.0");

                    // Setup input for XSLT transformation
                    Source src = new StreamSource(xmlFile);

                    // Resulting SAX events (the generated FO) must be piped through to FOP
                    Result res = new SAXResult(fop.getDefaultHandler());

                    // Start XSLT transformation and FOP processing
                    transformer.transform(src, res);
                }
                finally
                {
                    out.close();
                }

                System.out.println("Success!");
                
            }
            else{
                MessageDialog.openError(null, Properties.getProperty("error_pdf_file"), Properties.getProperty("error_no_doc"));
            }
            
         }
        catch (Exception e)
        {
            e.printStackTrace(System.err);
            MessageDialog.openError(null, Properties.getProperty("error_pdf_file"), Properties.getProperty("error_not_generate_pdf"));
        }

    }
}
