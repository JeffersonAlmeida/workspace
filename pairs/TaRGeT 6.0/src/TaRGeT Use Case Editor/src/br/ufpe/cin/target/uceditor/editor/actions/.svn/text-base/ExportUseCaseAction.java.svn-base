/*
 * @(#)ExportUseCaseAction.java
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
 * mcms     13/11/2009                    Correction of class structure
 */
package br.ufpe.cin.target.uceditor.editor.actions;

import java.util.HashMap;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditorPage;
import br.ufpe.cin.target.uceditor.export.IUseCaseDocumentGenerator;
import br.ufpe.cin.target.uceditor.export.PDFGenerator;
import br.ufpe.cin.target.uceditor.verify.UseCaseVerifier;


/**
 * 
 * This class is responsible to export the use case document in a selected format. 
 *
 */
//INSPECT mcms class has his extension modified, now it extends from Action
public class ExportUseCaseAction extends Action
{
    //INSPECT mcms attribute "manager" was deleted because it became unnecessary
    
    private UseCaseEditorPage page;
    
    private ScrolledForm form;
    
    private HashMap<String, Object> components;
    
    private IUseCaseDocumentGenerator ucDocumentGenerator;
    
    /**
     * 
     * Class Constructor.
     * 
     * @param manager the tool bar manager
     * @param useCaseDocument the use case document
     * @param exportUCImage The image used at tool bar for this action
     */
    //04/01 add shell
    //INSPECT mcms constructor received an ImageDescriptor parameter  
    //INSPECT mcms 27/01 mudança no atributo. Ao invés de receber o documento de casos de uso, o construtor recebe a página 
    public ExportUseCaseAction(IToolBarManager manager, UseCaseEditorPage page,ImageDescriptor exportUCImage, Shell shell, ScrolledForm form, HashMap<String, Object> components)
    {
        super("&Export Use Case", exportUCImage);
        this.page = page;
        this.components = components;
        this.form = form;
        this.ucDocumentGenerator = new PDFGenerator(shell);
        this.setToolTipText(Properties.getProperty("export_doc"));
        manager.add(this);
    }

    /**
     * Runs the action.
     */
    //INSPECT mcms 29/01 adicionada verificação se os campos estão vazios ou se o documento q se quer exportar existe
    //INSPECT mcms method hook() was deleted and method run() was created
    public void run()
    {
    	UseCaseVerifier verifier = new UseCaseVerifier();
    	String result = verifier.verifyMandatoryFields(this.components, this.page.getEditor()); 
    	if(result==null){
    		if(this.page.getUseCaseDocument()!=null){
        		ucDocumentGenerator.generateUseCaseDocumentFile(page.getUseCaseDocument());
    		}
    		else{
        		MessageDialog.open(MessageDialog.ERROR, form.getShell(), Properties.getProperty("warning"), Properties.getProperty("warning_save"), SWT.NONE);	    			
    		}
    	}
    	else{
    		MessageDialog.open(MessageDialog.ERROR, form.getShell(), Properties.getProperty("warning"), Properties.getProperty("warning_export")+" "+result+" "+Properties.getProperty("rest_warning_export"), SWT.NONE);	
    	}
        

    }
}
