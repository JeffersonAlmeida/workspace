/*
 * @(#)UseCaseEditorPage.java
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
 * mcms     13/11/2009                    Changes necessaries to tool bar actions classes
 * mcms     16/12/2009                    Adapt method to use TableViewer
 */
package br.ufpe.cin.target.uceditor.editor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.uceditor.UseCaseEditorActivator;
import br.ufpe.cin.target.uceditor.editor.actions.AddNewFlowAction;
import br.ufpe.cin.target.uceditor.editor.actions.ExportUseCaseAction;
import br.ufpe.cin.target.uceditor.editor.actions.SaveAction;
import br.ufpe.cin.target.uceditor.editor.actions.SearchAction;
import br.ufpe.cin.target.uceditor.util.UseCaseEditorUtil;

/**
 * This class provides a form page with five sections: 
 *      - Feature:  Exhibits two fields that refers to name and id from feature.    
 *      - UC: Exhibits four fields that refers to name, id, description and setup from use case.
 *      - Requirements: Exhibits a table with requirement codes.
 *      - Main Flow: Exhibits the fields description, from steps and to steps that refers to main flow, 
 *        and shows a table with step id, user actions, system state and system response.
 *      - Alternative Flow -&gt; It begins empty, but user can add new flows if it is necessary.
 * 
 * RESPONSIBILITIES:
 * Show the use case document information and allow to user modify it by inserting and removing flows or lines in tables.
 * 
 */
public class UseCaseEditorPage extends FormPage 
{

	/**
	 * The unique identifier of this form page.
	 */
	public static final String ID = "br.ufpe.cin.target.xmleditor.editor.UseCaseEditorPage";

	/**
	 * List of objects that store all components used in UI.
	 */
	private HashMap<String, Object> components; 

	/**
	 * The use case document that contains a Feature Collection 
	 */
	private UseCaseDocument useCaseDocument;

	private List<Composite> alternativeFlows;

	private FormEditor editor;

	private ScrolledForm form;
	
	//INSPECT mcms 28/01 atributo adicionado para acessar o use case e a feature do editor corrente 
	private UseCaseEditorInput input;



	/**
	 * Creates a form page to display an existing use case.
	 * 
	 * @param editor The editor where this page will be added.
	 * @param useCase The Use Case that will be showed
	 * @param feature The Feature that will be showed
	 */
	public UseCaseEditorPage(FormEditor editor, UseCase useCase, Feature feature) 
	{
		super(editor, ID, Properties.getProperty("use_case_editor"));
		this.editor = editor;
		this.components = new HashMap<String, Object>();
		this.input = (UseCaseEditorInput)editor.getEditorInput();
		this.alternativeFlows = new ArrayList<Composite>();

		if(feature!=null){
			Collection<UseCaseDocument> phoneDocments = ProjectManagerController.getInstance().getCurrentProject().getUseCaseDocuments();
			for (UseCaseDocument useCaseDocument : phoneDocments)
			{
				if(useCaseDocument.getFeature(feature.getId()) != null)
				{
					this.useCaseDocument = useCaseDocument;
					break;
				}
			}
		}
		else{
			this.useCaseDocument = null;
		}		
	}


	/**
	 * Creates content in the form hosted in this page.
	 * 
	 * @param managedForm The form hosted in this page.
	 */
	protected void createFormContent(IManagedForm managedForm) 
	{
		this.form = managedForm.getForm();
		FormToolkit toolkit = managedForm.getToolkit();

		toolkit.decorateFormHeading(form.getForm());
		IToolBarManager manager = form.getToolBarManager();

		this.form.getBody().setLayout(new GridLayout());

		// Feature
		Section featuresSection = UseCaseEditorUtil.createSection(this.form, toolkit, Properties.getProperty("feature"));
		Composite featuresComposite = UseCaseEditorUtil.createComposite(toolkit, featuresSection,SWT.NONE, 2);
		this.createFeatureFields(featuresComposite, toolkit);

		// UC
		Section useCaseSection = UseCaseEditorUtil.createSection(this.form, toolkit, Properties.getProperty("uc"));
		Composite useCaseComposite = UseCaseEditorUtil.createComposite(toolkit, useCaseSection,SWT.NONE, 2);
		this.createUcDescriptionFields(useCaseComposite, toolkit);

		// REQUIREMENTS
		Section requiremenstSection = UseCaseEditorUtil.createSection(this.form, toolkit, Properties.getProperty("requirements"));
		Composite requiremenstComposite = UseCaseEditorUtil.createComposite(toolkit, requiremenstSection,SWT.NONE, 2);
		//create the requirement table and add it in components list
		this.components.put("Requirements", this.createRequirementTable(requiremenstComposite, form, toolkit));

		// MAIN FLOW
		Section mainFlowSection = UseCaseEditorUtil.createSection(this.form, toolkit, Properties.getProperty("main_flow"));
		Composite mainFlowComposite = UseCaseEditorUtil.createComposite(toolkit, mainFlowSection, SWT.NONE, 1);

		// ALTERNATIVE FLOWS
		Section alternativeFlowsSection = UseCaseEditorUtil.createSection(this.form, toolkit,Properties.getProperty("alternative_flows"));
		Composite alternativeFlowsComposite = UseCaseEditorUtil.createComposite(toolkit, alternativeFlowsSection, SWT.NONE, 1);

		//Buttons to ToolBar
		//INSPECT ImageDescriptor objects created to be used at action classes
		ImageDescriptor searchImage = UseCaseEditorActivator.getImageDescriptor("icons/search_icon_16_16.png");
		new SearchAction(manager,this.components, form.getShell(), searchImage, this.editor);
		
		ImageDescriptor newFlowImage = UseCaseEditorActivator.getImageDescriptor("icons/uc_icon_16_16.png");
		new AddNewFlowAction(manager, alternativeFlowsComposite, form, toolkit, this.components, this.input.getUseCase(), this.alternativeFlows, newFlowImage, this.editor);
		
		ImageDescriptor exportUCImage = UseCaseEditorActivator.getImageDescriptor("icons/pdf_icon_16_16.png");
		new ExportUseCaseAction(manager, this, exportUCImage, form.getShell(), this.form, this.components); 
		this.save(manager);


		//Update ToolBar
		form.updateToolBar();

		//Fill in editor with imported use case
		if(this.input.getUseCase()!=null)
		{
			form.setText(this.input.getUseCase().getId() + " - " + this.input.getUseCase().getName());		
			this.initializeEditorFields(alternativeFlowsComposite,mainFlowComposite, form, toolkit);				
		}
		else
		{
			//Create composites to store text fields and table
			UseCaseEditorUtil.addMainFlow(mainFlowComposite, form, toolkit, this.components, this.input.getUseCase(), this.editor, null, this.input.getFeature());
			form.setText(Properties.getProperty("use_cases"));	
		}

		// Used to change components color after every search action
		UseCaseEditorUtil.changeColor(components);

		//add shortcut to save
		this.addSaveShortcut();

	}

	//05/01 shortcut to save (Ctrl+s)
	private void addSaveShortcut(){
		Display  display = this.form.getDisplay();
		display.addFilter(SWT.KeyDown, new Listener() {
			public void handleEvent(Event e) {
				if (e.stateMask == SWT.CTRL && e.keyCode == 's') {
					if(getEditor().equals(PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()))
					{
						save();
						e.doit = false;  // this tells the OS not to send the KeyDown to the focus widget
						editor.setFocus();
					}
				}
			}
		});
	}

	//INSPECT mcms 27/01 para atualizar valor do documento no save
	public void setUseCaseDocument(UseCaseDocument ucDoc){
		this.useCaseDocument = ucDoc;
	}
		
	//INSPECT mcms 27/01 para pegar valor do documento atualizado no export
	public UseCaseDocument getUseCaseDocument(){
		return this.useCaseDocument;
	}
	
	private IWorkbenchWindow getWindow(){
		IWorkbench workbench = PlatformUI.getWorkbench();
		IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
		return window;	
	}
	//22/12
	public void save(){
		SaveAction save = new SaveAction(this.components, this.useCaseDocument, form, (UseCaseEditor) this.editor, UseCaseEditorActivator.getImageDescriptor("icons/save_icon_16_16.png"), this.getWindow(), this);
		save.run();
	}

	//22/12
	private void save(IToolBarManager manager){
		new SaveAction(manager,this.components, this.useCaseDocument, form, (UseCaseEditor) this.editor, UseCaseEditorActivator.getImageDescriptor("icons/save_icon_16_16.png"), this.getWindow(), this);		

	}

	/**
	 * Creates the empty fields Id and Name to the Feature section and add them to the List components.
	 * 
	 * @param parent The composite that will host fields.
	 * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	 * Eclipse forms.
	 */
	//04/01 adição de asterisco
	// 15/01 adição de ToolTip
	private void createFeatureFields(Composite parent, FormToolkit toolkit) 
	{		
		//ID
		Label l = UseCaseEditorUtil.createLabel(toolkit, parent, Properties.getProperty("id"), this.form.getDisplay());
		l.setToolTipText(Properties.getProperty("mandatory"));
		Text text = UseCaseEditorUtil.createText(toolkit, parent, SWT.NONE, this.editor);
		this.components.put( "ID", text);

		//Name
		UseCaseEditorUtil.createLabel(toolkit, parent, Properties.getProperty("name"), this.form.getDisplay());
		this.components.put("FeatureName", UseCaseEditorUtil.createText(toolkit, parent, SWT.NONE, this.editor));		
	}

	/**
	 * Creates the empty fields Id, Name, Description and Setup to the UC section and add 
	 * them to the List components.
	 * 
	 * @param parent The composite that will host fields.
	 * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	 * Eclipse forms.
	 */
	//04/01 adição de asterisco
	// 15/01 adição de ToolTip
	private void createUcDescriptionFields(Composite parent, FormToolkit toolkit) 
	{	
		// ID
		Label l = UseCaseEditorUtil.createLabel(toolkit, parent, Properties.getProperty("id"), this.form.getDisplay());
		l.setToolTipText(Properties.getProperty("mandatory"));
		this.components.put("UCID", UseCaseEditorUtil.createText(toolkit, parent, SWT.NONE, this.editor));

		// Name
		UseCaseEditorUtil.createLabel(toolkit, parent, Properties.getProperty("name"), this.form.getDisplay());
		this.components.put("UCName", UseCaseEditorUtil.createText(toolkit, parent, SWT.NONE, this.editor));

		// Description
		UseCaseEditorUtil.createLabel(toolkit, parent, Properties.getProperty("description"), this.form.getDisplay());
		this.components.put("Description", UseCaseEditorUtil.createText(toolkit, parent, SWT.NONE, this.editor));

		// Setup
		UseCaseEditorUtil.createLabel(toolkit, parent, Properties.getProperty("setup"), this.form.getDisplay());
		this.components.put("Setup", UseCaseEditorUtil.createText(toolkit, parent, SWT.NONE, this.editor));
	}


	/**
	 * Creates an empty table and make it editable to the Requirements section.
	 * 
	 * @param parent The composite that will host fields.
	 * @param sf The ScrolledForm is necessary into editTable to allow refreshing table after 
	 * modifications 
	 * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	 * Eclipse forms.
	 * @return t The table created.
	 */
	private Table createRequirementTable(Composite parent, ScrolledForm sf,
			FormToolkit toolkit) 
	{
		Table requirementsTable = UseCaseEditorUtil.createRequirementTable(toolkit, parent,
				SWT.BORDER | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

		String[] columnName = { Properties.getProperty("requirementscodes") };       

		UseCaseEditorUtil.createTableItem(requirementsTable, SWT.CENTER, columnName, 170);

		return requirementsTable;
	}

	/**
	 * Calls methods to fill in all sections in editor page.
	 * @param toolkit 
	 * @param form 
	 * @param alternativeFlowsComposite 
	 */
	public void initializeEditorFields(Composite alternativeFlowsComposite,Composite mainFlowComposite, ScrolledForm form, FormToolkit toolkit)
	{

		//Feature
		Text featureId = (Text)this.components.get("ID");
		featureId.setText(this.input.getFeature().getId());
		Text featureName = (Text)this.components.get("FeatureName");
		featureName.setText(this.input.getFeature().getName());

		//UC
		Text ucId = (Text)this.components.get("UCID");
		ucId.setText(this.input.getUseCase().getId());

		Text ucName = (Text)this.components.get("UCName");
		ucName.setText(this.input.getUseCase().getName());

		Text ucDescription = (Text)this.components.get("Description");
		ucDescription.setText(this.input.getUseCase().getDescription());

		Text ucSetup = (Text)this.components.get("Setup");
		ucSetup.setText(this.input.getUseCase().getSetup());

		//Requirements
		Table requirementsTable = (Table) this.components.get("Requirements");
		this.fillInRequirementsTable(requirementsTable);

		//Flows
		this.fillInFlows(alternativeFlowsComposite, mainFlowComposite, form, toolkit);
		
		((SharedScrolledComposite) form).reflow(true);

	}

	/**
	 * Fills in the requirement table at the requirements section.
	 * @param requirementTable
	 */
	private void fillInRequirementsTable(Table requirementTable)
	{
		Set<String> requirementList = this.input.getUseCase().getAllRelatedRequirements();

		if (requirementList.size() == 1)
		{
			requirementTable.getItem(0).setText(requirementList.iterator().next());
		}
		if (requirementList.size() > 1)
		{
			requirementTable.removeAll();

			for (String string : requirementList)
			{
				TableItem item = new TableItem(requirementTable, SWT.CENTER);
				item.setText(string);
			}
		}
	}

	/**
	 * 
	 * Fills the use case flows fields (main and alternative flows).
	 * @param alternativeFlowsComposite
	 * @param form the editor form
	 * @param toolkit the editor toolkit
	 */
	public void fillInFlows(Composite alternativeFlowsComposite,Composite mainFlowComposite, ScrolledForm form,
			FormToolkit toolkit)
	{
		List<Flow> flows = this.input.getUseCase().getFlows();

		// Create and fill in the main flow
		UseCaseEditorUtil.addMainFlow(mainFlowComposite, form, toolkit, this.components, this.input.getUseCase(), this.editor, flows.get(0), this.input.getFeature());


		for (int x=1; x<flows.size(); x++){
			UseCaseEditorUtil.addFlow(alternativeFlowsComposite, form, toolkit, this.components,
					this.input.getUseCase(), this.alternativeFlows, this.editor, flows.get(x), this.input.getFeature());
		}
	}    

}
