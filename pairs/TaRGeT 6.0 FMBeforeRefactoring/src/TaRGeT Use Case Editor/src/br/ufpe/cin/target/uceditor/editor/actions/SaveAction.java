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
 * mcms     06/11/2009                    Bug correction - update editor title
 * mcms     13/11/2009                    Correction of class structure
 */
package br.ufpe.cin.target.uceditor.editor.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.common.util.StringsUtil;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditor;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditorInput;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditorPage;
import br.ufpe.cin.target.uceditor.save.XMLFileGenerator;
import br.ufpe.cin.target.uceditor.verify.UseCaseVerifier;


/**
 * Saves the use case document in a XML file.
 */
//INSPECT mcms class has his extension modified, now it extends from Action
public class SaveAction extends Action
{

    //INSPECT mcms attribute "manager" was deleted because it became unnecessary
    
    private HashMap<String, Object> components;

    private UseCaseDocument useCaseDocument;

    private ScrolledForm form;

    private UseCaseVerifier verifier;
    
    private UseCaseEditor editor;
    
    private UseCaseEditorPage page;
        
    //INSPECT mcms this attribute was added to be used at method refreshUseCaseTree()
    private IWorkbenchWindow window;
    
	//INSPECT mcms 28/01 atributo adicionado para acessar o use case e a feature do editor corrente 
	private UseCaseEditorInput input;


    /**
     * Class Constructor.
     * 
     * @param manager the tool bar manager
     * @param components the graphical components list
     * @param useCase the use case on edition
     * @param feature the feature on edition
     * @param useCaseDocument the use case document
     * @param form the editor scrolled form
     * @param editor A form page
     * @param saveImage The image used at tool bar for this action
     * @param window Project's window
     */
  //INSPECT mcms two parameters were added to constructor: an ImageDescriptor and an IWorkbenchWindow   
    public SaveAction(IToolBarManager manager, HashMap<String, Object> components, UseCaseDocument useCaseDocument, ScrolledForm form, UseCaseEditor editor,
            ImageDescriptor saveImage, IWorkbenchWindow window, UseCaseEditorPage page)
    {
        super("&Save", saveImage);
        
        this.verifier = new UseCaseVerifier();
          
        this.components = components;
           
        this.input = (UseCaseEditorInput)editor.getEditorInput();
        
        this.useCaseDocument = useCaseDocument;
        
        this.form = form;
        
        this.editor = editor;
        
        this.window = window;
        
        this.setToolTipText(Properties.getProperty("save"));
        
        this.page = page;
        
        manager.add(this);
    }

    public SaveAction(HashMap<String, Object> components, UseCaseDocument useCaseDocument, ScrolledForm form, UseCaseEditor editor,
            ImageDescriptor saveImage, IWorkbenchWindow window, UseCaseEditorPage page)
    {
        super("&Save", saveImage);
        
        this.verifier = new UseCaseVerifier();
          
        this.components = components;
        
        this.input = (UseCaseEditorInput)editor.getEditorInput();
        
        this.useCaseDocument = useCaseDocument;
        
        this.form = form;
        
        this.editor = editor;
        
        this.window = window;
        
        this.page = page;
        
        this.setToolTipText(Properties.getProperty("save"));
        
    }

    /**
     * Runs the action.
     */
    //INSPECT mcms method hook() was deleted and method run() was created
    public void run()
    {
    	UseCaseVerifier verifier = new UseCaseVerifier();
    	String result = verifier.verifyMandatoryFields(this.components, this.editor); 
    	if(result==null){
    		editor.setFocus(); 
          	boolean existFeature = false;
          	boolean existUseCase = true;
        	String featureId = ((Text) this.components.get("ID")).getText();
        	Collection<UseCaseDocument> ucDocs = ProjectManagerController.getInstance().getCurrentProject().getUseCaseDocuments();
        	for (UseCaseDocument documents: ucDocs){
        		Iterator it = documents.getFeatures().iterator();
        		while(it.hasNext()){
        			Feature feature = (Feature)it.next();
        			if(featureId.equals(feature.getId())){
        				existFeature = true;
        				String ucId = ((Text) this.components.get("UCID")).getText();
        		    	UseCase uc = feature.getUseCase(ucId);
        				if(uc==null){
        					existUseCase = false;
        					mergeDocument(feature, documents);
            				((UseCaseEditor)this.editor).setDirty(false);        					
        				}
        				break;
        			}
        		}
        	}
            if (input.getUseCase()==null)
            {     	
            	if(!existFeature){
            		// Create a new use case document	
        			saveUseCase();
    			}    
            	else{
            		modifyUseCaseFields();
                    ((UseCaseEditor)this.editor).setDirty(false);    
            	}
            		
            }
            else
            {
                // Save modifications
            	if(existUseCase){
            	    modifyUseCaseFields();
                    ((UseCaseEditor)this.editor).setDirty(false);            		
            	}
            }
            
            refreshUseCaseTree();
    		
    	}
    	else{
    		MessageDialog.open(MessageDialog.ERROR, form.getShell(), Properties.getProperty("warning"),Properties.getProperty("warning_not_save")+" "+result+" "+Properties.getProperty("rest_warning_export"), SWT.NONE);	
    	}
        
        
    }
    
        
    //INSPECT mcms 27/01 method to add an use case into an use case document with the same feature
    private void mergeDocument(Feature feature, UseCaseDocument documents ){
    	this.useCaseDocument = documents;
		this.createUseCase();
		this.input.setFeature(feature);
		addUseCase(feature);
		this.generateXMLFile();
		this.updateRequirementsFields();
	    this.form.setText(this.input.getUseCase().getId() + " - " + this.input.getUseCase().getName());
	    //INSPECT update editor title
	    this.editor.updateTitle(this.input.getUseCase().getName());
	    ((SharedScrolledComposite) form).reflow(true);
	    this.editor.setFocus();	
    }
    
    private void addUseCase (Feature feature){
    	 
	 	 this.updateUseCaseFields();

         // Flows
         Iterator<Map.Entry<String , Object>> it = components.entrySet().iterator();
         while (it.hasNext())
         {
             Map.Entry<String , Object> item = it.next();
             Object component = item.getValue();
             if (item.getKey().equals("MainFlow"))
             {
             	updateFlows(component);	
             }
             if(item.getKey().equals("AlternativeFlows"))
             {
             	Iterator<Composite> flows = ((List<Composite>)component).iterator();
             	while(flows.hasNext())
             	{
             		Composite comp = (Composite)flows.next();
             		updateFlows(comp);	
             	}
             	
             }
         }
         feature.addUseCase(this.input.getUseCase());
         this.input.setFeature(feature);
    }
    
  
    public void createUseCaseDocument(){
    	List<UseCase> useCases = new ArrayList<UseCase>();
    	
    	this.input.setFeature(new Feature("","", useCases));
    	
    	this.createUseCase();

		List<Feature> featureCollection = new ArrayList<Feature>();

		this.useCaseDocument = new UseCaseDocument(featureCollection);

    }
    
    private void createUseCase(){
    	List<Flow> flowList = new ArrayList<Flow>();

		this.input.setUseCase(new UseCase("", "", "", flowList, ""));
    }
    
    /**
     * Updates the project contents and saves the use case fields.
     */
    public void saveUseCase()
    {   
    	//cria um documento de caso de uso vazio
    	this.createUseCaseDocument();
    	// Feature
        this.updateFeatureFields();

        // UC
        this.updateUseCaseFields();

        // Flows
        Iterator<Map.Entry<String , Object>> it = components.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry<String , Object> item = it.next();
            Object component = item.getValue();
            if (item.getKey().equals("MainFlow"))
            {
            	updateFlows(component);	
            }
            if(item.getKey().equals("AlternativeFlows"))
            {
            	Iterator<Composite> flows = ((List<Composite>)component).iterator();
            	while(flows.hasNext())
            	{
            		Composite comp = (Composite)flows.next();
            		updateFlows(comp);	
            	}
            	
            }
        }
        Feature feature = this.input.getFeature();
        feature.addUseCase(this.input.getUseCase());
        this.input.setFeature(feature);
        
        this.useCaseDocument.addFeature(this.input.getFeature());
        
        this.useCaseDocument.setDocFilePath(ProjectManagerController.getInstance()
                .getCurrentProject().getRootDir().toString()
                + "\\useCases\\" + this.input.getFeature().getName() + ".xml");
  
    
        for (int i = 0; i < (this.input.getUseCase()).getFlows().size(); i++)
        {
            Flow f = (this.input.getUseCase()).getFlows().get(i);
            verifier.verifyReferences(f.getFromSteps(), this.input.getUseCase(), this.input.getFeature(), this.components);
            verifier.verifyReferences(f.getToSteps(), this.input.getUseCase(), this.input.getFeature(), this.components);
        }
        
        this.generateXMLFile();
        this.updateRequirementsFields();
        this.form.setText(this.input.getUseCase().getId() + " - " + this.input.getUseCase().getName());
        //INSPECT update editor title
        this.editor.updateTitle(this.input.getUseCase().getName());
        this.page.setUseCaseDocument(this.useCaseDocument);
        
         }
    
    /**
     * 
     * Updates feature fields.
     */
	private void updateFeatureFields() {
		String featureId = ((Text) this.components.get("ID")).getText();
		Feature feature = this.input.getFeature();
		feature.setId(featureId);
		String featureName = ((Text) this.components.get("FeatureName"))
				.getText();
		feature.setName(featureName);
		
		this.input.setFeature(feature);
	}
	

    /**
     * 
     * Updates use case fields.
     */
    private void updateUseCaseFields()
    {
    	UseCase useCase = this.input.getUseCase();
        String ucId = ((Text) this.components.get("UCID")).getText();
        useCase.setId(ucId);
        
        String ucName = ((Text) this.components.get("UCName")).getText();
        useCase.setName(ucName);
        
        String ucDescription = ((Text) this.components.get("Description")).getText();
        useCase.setDescription(ucDescription);
        
        String ucSetup = ((Text) this.components.get("Setup")).getText();
        useCase.setSetup(ucSetup);
     
        this.input.setUseCase(useCase);
    }
    
    /**
     * 
     * Updates requirements fields.
     */
    private void updateRequirementsFields()
    {
        Table requirementTable = (Table) this.components.get("Requirements");
        requirementTable.removeAll();
        
        List<String> allRequirements = this.joinRequirements();
        
        for (String string : allRequirements)
        {
            TableItem item = new TableItem(requirementTable, SWT.CENTER);
            item.setText(string);
        }
    }
    
    /**
     * 
     * Returns a list containing the use case requirements.
     * @return a list containing the use case requirements
     */
    private List<String> joinRequirements()
    {
        List<String> result = new ArrayList<String>();
        Iterator<Flow> flowIterator = (this.input.getUseCase()).getFlows().iterator();

        while (flowIterator.hasNext())
        {
            Flow flow = (Flow) flowIterator.next();
            Iterator<FlowStep> stepsIterator = flow.getSteps().iterator();
            while (stepsIterator.hasNext())
            {
                FlowStep flowStep = (FlowStep) stepsIterator.next();
                Iterator<String> requirementsIterator = flowStep.getRelatedRequirements().iterator();
                while (requirementsIterator.hasNext())
                {
                    String stepRequirement = (String) requirementsIterator.next();
                    result.add(stepRequirement);
                }
            }
        }
        return result;
    }
    
    /**
     * 
     * Divides the content of the step text and returns a step set.
     * @param step the step texts
     * @return a step set
     */
    private Set<StepId> createStepsSet(String step)
    {
        Set<StepId> steps = new LinkedHashSet<StepId>();
        String stepId = "";
        
        for (int i = 0; i < step.length(); i++)
        {
            if (step.charAt(i) != ',')
            {
                stepId = stepId + step.charAt(i);
            }
            if ((step.charAt(i) == ',') || (i == step.length() - 1))
            {
                StepId oneStep = this.createStepId(stepId);
                steps.add(oneStep);
                stepId = "";
            }
        }
        return steps;
    }
    
    /**
     * 
     * Creates a StepId object from a step id string.
     * @param oneStep a step id string
     * @return a StepId object 
     */
    private StepId createStepId(String oneStep)
    {
        String[] ids = oneStep.split("#");
        StepId id = null;
        
        if (ids.length == 1)
        {
            if ((ids[0].equals("START")) || (ids[0].equals("END")))
            {
                id = new StepId("", "", ids[0]);
            }
            else
            {
                id = new StepId(this.input.getFeature().getId(), this.input.getUseCase().getId(), ids[0]);
            }
        }
        
        if (ids.length == 2)
        {
            id = new StepId(this.input.getFeature().getId(), ids[0], ids[1]);
        }
        
        if (ids.length == 3)
        {
            id = new StepId(ids[0], ids[1], ids[2]);
        }
        
        return id;
    }
    
    /**
     * 
     * Creates a flow step id object
     * @param id a step id string
     * @return a flow step id object
     */
    private StepId createFlowStepId(String id)
    {
        return new StepId(this.input.getFeature().getId(), this.input.getUseCase().getId(), id);
    }
    
    /**
     * 
     * Creates a flow step id object.
     * @param table
     * @return a flow step id object
     */
    private List<FlowStep> createFlowList(Table table)
    {
        List<FlowStep> flowSteps = new ArrayList<FlowStep>();
        
        Set<String> relatedRequirements = new HashSet<String>();
        
        TableItem[] items = table.getItems();
        
        int x = 0;
      
        for (int i = 0; i < table.getItemCount(); i++)
        {
            StepId id = createFlowStepId(items[i].getText(x));
            
            String userAction = items[i].getText(x + 1);
            this.updateRequirementsSteps(relatedRequirements, StringsUtil
                    .retrieveRequirements(userAction));
             
            String systemCondition = items[i].getText(x + 2);
            this.updateRequirementsSteps(relatedRequirements, StringsUtil
                    .retrieveRequirements(systemCondition));
             
            String systemResponse = items[i].getText(x + 3);
            this.updateRequirementsSteps(relatedRequirements, StringsUtil
                    .retrieveRequirements(systemResponse));
             
            FlowStep step = new FlowStep(id, userAction, systemCondition, systemResponse,
                    relatedRequirements);
            
             
            
            flowSteps.add(step);
            
            relatedRequirements = new HashSet<String>();
        }

        return flowSteps;
    }
    
    /**
     * 
     * Updates the requirements steps
     * @param relatedRequirements
     * @param foundRequirements
     */
    private void updateRequirementsSteps(Set<String> relatedRequirements,
            Set<String> foundRequirements)
    {
        if (foundRequirements.size() >= 1)
        {
            Iterator<String> it = foundRequirements.iterator();
            while (it.hasNext())
            {
                String requirement = (String) it.next();
                relatedRequirements.add(requirement);
            }
        }
    }
       
    /**
     * 
     * Updates the flow fields.
     * @param composite
     */
    private void updateFlows(Object composite)
    {
        List<FlowStep> steps = new ArrayList<FlowStep>();
        
        Set<StepId> fromSteps = new LinkedHashSet<StepId>();
        
        Set<StepId> toSteps = new LinkedHashSet<StepId>();
        
        Flow mainFlow = new Flow(steps, fromSteps, toSteps, "");
        
        String description = "";
        String from = "";
        String to = "";

        if (composite instanceof Composite)
        {
            Control[] children = ((Composite) composite).getChildren();
            for (int x = 0; x < children.length; x++)
            {
                if (children[x] instanceof Composite)
                {
                    Control[] grandChildren = ((Composite) children[x]).getChildren();
                    for (int i = 0; i < grandChildren.length; i++)
                    {
                        if (grandChildren[i] instanceof Text)
                        {
                            //description field
                            if (i == 1)
                            {
                                description = ((Text) grandChildren[i]).getText();
                                mainFlow.setDescription(description);
                                UseCase useCase = this.input.getUseCase();
                                useCase.addFlow(mainFlow);
                                this.input.setUseCase(useCase);
                            }
                            //from steps
                            if (i == 3)
                            {
                                from = ((Text) grandChildren[i]).getText();
                                mainFlow.setFromSteps(this.createStepsSet(from));
                            }
                            //to steps
                            if (i == 5)
                            {
                                to = ((Text) grandChildren[i]).getText();
                                mainFlow.setToSteps(this.createStepsSet(to));
                            }
                        }
                        //flow steps table
                        if (grandChildren[i] instanceof Table)
                        {
                            Table table = (Table) grandChildren[i];
                            mainFlow.setSteps(createFlowList(table));
                        }
                    }
                }
            }
        }

    }
    
    /**
     * 
     * Refreshes project use case tree.
     */
    private void refreshUseCaseTree()
    {
        try
        {
        	GUIManager.getInstance().refreshProject(this.window.getShell(), true);
        }
        catch (Exception e)
        {
            System.out.println("Exception no refresh: " + e.getMessage());
        }
    }
    
    /**
     * 
     * Generates a XML representation of the Use Case document.
     */
    private void generateXMLFile()
    {
        XMLFileGenerator generate = new XMLFileGenerator(this.useCaseDocument, this.window
                .getShell());
        generate.createXMLSchema(editor);
    }
    
    /**
     * 
     * Modifies an already created use case.
     */
    private void modifyUseCaseFields()
    {
    	Iterator<Map.Entry<String , Object>> it = components.entrySet().iterator();
         
    	this.useCaseDocument.removeFeature(this.input.getFeature());
        // Feature
        this.updateFeatureFields();
        // UC
        this.updateUseCaseFields();
        
        
     // Flows
        int index = 0;
         
        while (it.hasNext())
        {
            Map.Entry<String , Object> item = it.next();
            Object component = item.getValue();
           if (item.getKey().equals("MainFlow"))
            {
            	modifyFlowFields(component, this.input.getUseCase().getFlows().get(index));	
            	index++;
            }
            if(item.getKey().equals("AlternativeFlows"))
            {
            	Iterator<Composite> flows = ((List<Composite>)component).iterator();
            	this.verifyFlowAmount();
            	while(flows.hasNext())
            	{
            		Composite comp = (Composite)flows.next();
            		modifyFlowFields(comp, this.input.getUseCase().getFlows().get(index));	
            		index++;
            	}
            	
            }
        }
 

        for (int i = 0; i < (this.input.getUseCase()).getFlows().size(); i++)
        {
            Flow flow = (this.input.getUseCase()).getFlows().get(i);
            verifier.verifyReferences(flow.getFromSteps(), this.input.getUseCase(), this.input.getFeature(), this.components);
            verifier.verifyReferences(flow.getToSteps(), this.input.getUseCase(), this.input.getFeature(), this.components);
        }
       
        
        this.useCaseDocument.addFeature(this.input.getFeature());
        
        this.generateXMLFile();
        
        this.updateRequirementsFields();
        
        this.form.setText(this.input.getUseCase().getId() + " - " + this.input.getUseCase().getName());
        //INSPECT mcms update editor title
        this.editor.updateTitle(this.input.getUseCase().getName());
        this.page.setUseCaseDocument(this.useCaseDocument);
        ((SharedScrolledComposite) form).reflow(true);
        this.editor.setFocus();
        
    }
    
    /**
     * 
     * Updates the flow fields.
     * @param composite
     * @param flow
     */
    private void modifyFlowFields(Object composite, Flow flow)
    {
        String description;
        String from;
        String to;

        if (composite instanceof Composite)
        {
            Control[] children = ((Composite) composite).getChildren();
            for (int x = 0; x < children.length; x++)
            {
                if (children[x] instanceof Composite)
                {
                    Control[] grandChildren = ((Composite) children[x]).getChildren();
                    for (int i = 0; i < grandChildren.length; i++)
                    {
                        if (grandChildren[i] instanceof Text)
                        {
                            if (i == 1)
                            {
                                description = ((Text) grandChildren[i]).getText();
                                flow.setDescription(description);
                            }
                            if (i == 3)
                            {
                                from = ((Text) grandChildren[i]).getText();
                                flow.setFromSteps(this.createStepsSet(from));
                            }
                            if (i == 5)
                            {
                                to = ((Text) grandChildren[i]).getText();
                                flow.setToSteps(this.createStepsSet(to));
                            }
                        }
                        if (grandChildren[i] instanceof Table)
                        {
                            Table table = (Table) grandChildren[i];
                            flow.setSteps(createFlowList(table));
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     * Verifies the consistency of the modified flows.
     */
    private void verifyFlowAmount()
    {
        int oldFlowsAmount = (this.input.getUseCase()).getFlows().size()-1;
        List<Composite> flowList = (List<Composite>)components.get("AlternativeFlows");
        int newFlowsAmount = flowList.size();
       
        
        int amount = newFlowsAmount - oldFlowsAmount;
        
        if (amount > 0)
        {
            for (int x = 0; x < amount; x++)
            {
                List<FlowStep> steps = new ArrayList<FlowStep>();
                
                Set<StepId> fromSteps = new LinkedHashSet<StepId>();
                
                Set<StepId> toSteps = new LinkedHashSet<StepId>();
                
                Flow altFlow = new Flow(steps, fromSteps, toSteps, "");
                
                UseCase useCase = this.input.getUseCase();
                useCase.addFlow(altFlow);
                this.input.setUseCase(useCase);
            }
        }
        if (amount < 0)
        {
            if ((this.input.getUseCase()).getFlows().size() > 2)
            {
                for (int y = 1; y < (this.input.getUseCase()).getFlows().size(); y++)
                {
                    if (!exists((this.input.getUseCase()).getFlows().get(y)))
                    {
                    	UseCase useCase = this.input.getUseCase();
                        useCase.removeFlow((this.input.getUseCase()).getFlows().get(y));
                        this.input.setUseCase(useCase);
                    }
                }
            }
            else if (this.input.getUseCase().getFlows().size() == 2)
            {
            	UseCase useCase = this.input.getUseCase();
                useCase.removeFlow((this.input.getUseCase()).getFlows().get(1));
                this.input.setUseCase(useCase);
            }
        }
    }
    
    /**
     * 
     * Verifies if a flow already exists.
     * @param flow the flow to be verified
     * @return the result of the comparison
     */
    private boolean exists(Flow flow)
    {
        Object composite = null;
        if (this.components.size() == 11)
        {
            return false;
        }
        else
        {
            for (int i = 11; i < components.size(); i++)
            {
                composite = components.get(i);
                if (composite instanceof Composite)
                {
                    Control[] children = ((Composite) composite).getChildren();
                    for (int x = 0; x < children.length; x++)
                    {
                        if (children[x] instanceof Composite)
                        {
                            Control[] grandChildren = ((Composite) children[x]).getChildren();
                            for (int y = 0; y < grandChildren.length; y++)
                            {
                                if (grandChildren[y] instanceof Table)
                                {
                                    String id = ((Table) grandChildren[y]).getItem(0).getText(0);
                                    
                                    String flowId = flow.getSteps().get(0).getId().getStepId();
                                    
                                    if (flowId.equals(id))
                                    {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
