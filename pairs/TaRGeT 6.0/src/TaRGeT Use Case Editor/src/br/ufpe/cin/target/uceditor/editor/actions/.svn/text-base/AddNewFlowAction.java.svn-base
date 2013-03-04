/*
 * @(#)AddNewFlowAction.java
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
 * mcms     11/11/2009                    Correction of class structure 
 */
package br.ufpe.cin.target.uceditor.editor.actions;

import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.uceditor.util.UseCaseEditorUtil;


/**
 * <pre>
 * CLASS:
 * This action adds a new flow into Alternative flows section.  
 * 
 * </pre>
 */
//INSPECT mcms class has his extension modified, now it extends from Action
public class AddNewFlowAction extends Action
{
    //INSPECT mcms attribute "manager" was deleted because it became unnecessary
    
    private Composite parent;

    private ScrolledForm scroll;

    private FormToolkit toolkit;

    private HashMap<String, Object> components;

    private UseCase uc;
    
    private List<Composite> alternativeFlows;
    
    private FormEditor editor;

    /**
     * Initializes attributes of this action and calls hook method.
     * 
     * @param manager The tool bar manager.
     * @param parent The composite that hosts the new flow.
     * @param scroll The ScrolledForm used to refresh the Alternative Flows section.
     * @param toolkit The FormToolkit responsible for creating SWT controls adapted to work in
     * Eclipse forms.
     * @param components The list of components used at user interface
     * @param uc The use case that will receive a new flow
     * @param alternativeFlows The list where the new flow will be added
     * @param newFlowImage The image used at tool bar for this action
     */
    //INSPECT mcms constructor received an ImageDescriptor parameter  
    public AddNewFlowAction(IToolBarManager manager, Composite parent, ScrolledForm scroll,
            FormToolkit toolkit, HashMap<String, Object> components, UseCase uc, List<Composite> alternativeFlows, 
            ImageDescriptor newFlowImage, FormEditor editor)
    {
        super("&New Flow", newFlowImage); 
        this.parent = parent;
        this.scroll = scroll;
        this.toolkit = toolkit;
        this.components = components;
        this.uc = uc;
        this.alternativeFlows = alternativeFlows;
        this.setToolTipText(Properties.getProperty("add_flow"));
        this.editor = editor;
        manager.add(this);

    }
    
    
    /**
     * Runs the action.
     */
    //INSPECT mcms method hook() was deleted and method run() was created
    public void run(){
        UseCaseEditorUtil.addFlow(parent, scroll, toolkit, components, uc, alternativeFlows, editor,null, null);
    }
   
}
