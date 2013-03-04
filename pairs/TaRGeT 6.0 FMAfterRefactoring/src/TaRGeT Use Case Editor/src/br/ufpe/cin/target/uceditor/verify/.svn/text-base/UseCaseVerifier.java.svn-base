/*
 * @(#)UseCaseVerifier.java
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
package br.ufpe.cin.target.uceditor.verify;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;

/**
 * 
 * This class is responsible to verify the steps references on the use case from and to step fields. 
 *
 */
public class UseCaseVerifier
{
    /**
     * 
     * Class Constructor.
     *
     */
    public UseCaseVerifier()
    {
        super();
    }

    /**
     * 
     * Verifies the steps references when the user tries to save the use case.
     * @param references the step id references
     * @param useCase the use case in edition
     * @param feature the feature object
     * @param components the editor graphical components
     */
    public void verifyReferences(Set<StepId> references, UseCase useCase, Feature feature,
    		HashMap<String, Object> components)
    {
        Iterator<StepId> it = references.iterator();
        while (it.hasNext())
        {
            StepId step = (StepId) it.next();
            
            if ((!step.getStepId().equals("START")) && (!step.getStepId().equals("END")))
            {
                //the step belogs to the feature object
                if (step.getFeatureId().equals(feature.getId()))
                {
                    //the step belongs to the useCase object
                    if (step.getUseCaseId().equals(useCase.getId()))
                    {
                        //the field is correct
                        if (this.useCaseContainsStep(step.getStepId(), useCase))
                        {
                            this.highlightComponent(this.stepsToString(references, useCase, feature),
                                    true, components);
                        }
                        
                        //the field is incorrect
                        else
                        {
                            this.highlightComponent(this.stepsToString(references, useCase, feature),
                                    false, components);
                            return;
                        }
                    }
                    
                    //the step belongs to a different use case from the useCase object
                    else
                    {
                        List<UseCase> useCases = feature.getRealUseCases();
                        if (this.verifyReferencesToAnotherUseCase(step.getUseCaseId(), step.getStepId(),
                                useCases))
                        {
                            this.highlightComponent(this.stepsToString(references, useCase, feature),
                                    true, components);
                        }
                        else
                        {
                            this.highlightComponent(step.getUseCaseId() + "#" + step.getStepId(),
                                    components);
                            return;
                        }
                    }
                }
                
                //the step belongs to a different feature from the feature object
                else
                {
                    if (this.verifyReferencesToAnotherFeature(step.getFeatureId(), step.getStepId()))
                    {
                        this.highlightComponent(this.stepsToString(references, useCase, feature),
                                true, components);
                    }
                    else
                    {
                        this.highlightComponent(step.getFeatureId() + "#" + step.getUseCaseId()
                                + "#" + step.getStepId(), components);
                        return;
                    }
                }
            }
            else
            {
                this.highlightComponent(step.getStepId(), true, components);
            }
        }
    }
    
    /**
     * 
     * Verifies if the use case with the given use case id contains the given step id.
     * @param useCaseId the use case to be analyzed
     * @param stepId the step id to be verified
     * @param useCases the use cases list
     * @return the result of the comparison
     */
    public boolean verifyReferencesToAnotherUseCase(String useCaseId, String stepId, List<UseCase> useCases)
    {
        UseCase useCase = this.getUseCase(useCaseId, useCases);
        
        if (useCase != null)
        {
            return this.useCaseContainsStep(stepId, useCase);
            
        }
        return false;

    }
    
    /**
     * 
     * Verifies if the feature with the given feature id contains the given step id.
     * @param featureId the feature to be verified
     * @param stepId the step id to be verified
     * @return the result of the comparison
     */
    public boolean verifyReferencesToAnotherFeature(String featureId, String stepId)
    {
        Feature feature = this.getFeature(featureId);
        if (feature != null)
        {
            List<UseCase> useCases = feature.getRealUseCases();
            Iterator<UseCase> itUC = useCases.iterator();
            while (itUC.hasNext())
            {
                UseCase uc = (UseCase) itUC.next();

                if (this.verifyReferencesToAnotherUseCase(uc.getId(), stepId, useCases))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * Highlights an use case field. 
     * @param step the use case step
     * @param isCorrect indicates if the field is correct
     * @param components the graphical components list
     */
    public void highlightComponent(String step, boolean isCorrect, HashMap<String, Object> components)
    {
        Iterator<Map.Entry<String , Object>> it = components.entrySet().iterator();
        try{
        while (it.hasNext())
        {
        	Map.Entry<String , Object> itemMap = it.next();
            Object item = itemMap.getValue();
            if(item instanceof List){
            	Iterator itList = ((List<Composite>)item).iterator();
            	while(itList.hasNext()){
            		Object composite = itList.next(); 
                    this.setTextBackgroundColor(composite, isCorrect, step);  
            	}
            }
            if(item instanceof Composite)
            {
            	this.setTextBackgroundColor(item, isCorrect, step);
            }       	
        }
        }
        catch(Exception e){
        	System.out.println(e);
        }
    }
    
    public void setTextBackgroundColor(Object composite, boolean isCorrect, String step){
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
                              if((i==3)||(i==5)){
                                  String textContent = ((Text) grandChildren[i]).getText();
                                 
                                  if (step.equals(textContent))
                                  {
                                      if(!isCorrect){
                                          ((Text) grandChildren[i]).setBackground(Display.getCurrent()
                                                  .getSystemColor(SWT.COLOR_RED));
                                      }
                                      else
                                      {  
                                              ((Text) grandChildren[i]).setBackground(Display.getCurrent()
                                                      .getSystemColor(SWT.COLOR_WHITE));  
                                      }
                                      return;
                                  }
                                  
                              }
                          }
                      }
                  }
              }
          }
    }
    
    /**
     * 
     * Highlights an use case field. 
     * @param step the use case step
     * @param components the graphical components list
     */
    public void highlightComponent(String step, HashMap<String, Object> components)
    {
        Iterator<Map.Entry<String , Object>> it = components.entrySet().iterator();
        
        while (it.hasNext())
        {
        	 Map.Entry<String , Object> item = it.next();
             Object composite = item.getValue();
                        
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
                                if ((i == 3) || (i == 5))
                                {
                                    String textContent = ((Text) grandChildren[i]).getText();
                                    if (textContent.contains((CharSequence)step))
                                    {
                                        ((Text) grandChildren[i]).setBackground(Display
                                                .getCurrent().getSystemColor(SWT.COLOR_RED));
                                    }   
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 
     * Verifies if the given use case contains the given use case id.
     * @param stepId the step id to be verified
     * @param useCase the use case to be verifies
     * @return the result of the comparison
     */
    public boolean useCaseContainsStep(String stepId, UseCase useCase)
    {
        List<Flow> flows = useCase.getFlows();
        Iterator<Flow> itFlow = flows.iterator();
        
        while (itFlow.hasNext())
        {
            Flow flow = (Flow) itFlow.next();
            
            for (int x = 0; x < flow.getSteps().size(); x++)
            {
                FlowStep flowStep = flow.getSteps().get(x);
                String id = flowStep.getId().getStepId();
                
                if (id.equals(stepId))
                {
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * 
     * Returns an use case from the list according to the given use case id.
     * @param ucId the use case id
     * @param useCases the use cases list
     * @return if the list contains the use case with the given id, returns it. Otherwise returns null
     */
    public UseCase getUseCase(String ucId, List<UseCase> useCases)
    {
        Iterator<UseCase> it = useCases.iterator();
        
        while (it.hasNext())
        {
            UseCase uc = (UseCase) it.next();
            if (ucId.equals(uc.getId()))
                return uc;
        }
        return null;
    }
    
    /**
     * 
     * Returns a feature from the project´s useCaseDocuments list according to the given use case id.
     * @param featureId the id of the feature to be verified
     * @return if the project´s useCaseDocuments list contains the feature with the given id, returns it. Otherwise returns null.
     */
    public Feature getFeature(String featureId)
    {
        Collection<UseCaseDocument> useCaseDocuments = ProjectManagerController.getInstance()
                .getCurrentProject().getUseCaseDocuments();
        Iterator<UseCaseDocument> useCaseDocumentsIterator = useCaseDocuments.iterator();
        
        while (useCaseDocumentsIterator.hasNext())
        {
            UseCaseDocument phoneDoc = (UseCaseDocument) useCaseDocumentsIterator.next();
            List<Feature> features = phoneDoc.getFeatures();
            Iterator<Feature> featuresIterator = features.iterator();
            
            while (featuresIterator.hasNext())
            {
                Feature feature = (Feature) featuresIterator.next();
                
                if (featureId.equals(feature.getId()))
                {
                    return feature;
                }
            }
        }
        return null;
    }
    
    /**
     * 
     * Returns a String representation of the given step set.
     * @param references the step id set to be converted
     * @param useCase the use case to be analyzed 
     * @param feature the feature to be analyzed
     * @return a String representation of the given step set
     */
    public String stepsToString(Set<StepId> references, UseCase useCase, Feature feature)
    {
        String result = "";
        Iterator<StepId> it = references.iterator();

        while (it.hasNext())
        {
            StepId step = (StepId) it.next();

            if (step.getFeatureId().equals(feature.getId()))
            {
                if (step.getUseCaseId().equals(useCase.getId()))
                {
                    result = result + step.getStepId() + ", ";
                }
                else
                {
                    result = result + step.getUseCaseId() + "#" + step.getStepId() + ", ";
                }
            }
            else
            {
                result = result + step.getFeatureId() + "#" + step.getUseCaseId() + "#"
                        + step.getStepId() + ", ";
            }
        }
        result = result.substring(0, result.length() - 2);
        
        return result;
    }
    
    /**
     *     
     * Verify flow steps at the user interface.
     * @param component
     * @param components
     * @return
     */
    public boolean verifyStepsFromInterface(Object component, List<Composite> components)
    {

        if (component instanceof Composite)
        {
            Control[] children = ((Composite) component).getChildren();
            for (int x = 0; x < children.length; x++)
            {
                if (children[x] instanceof Composite)
                {
                    Control[] grandChildren = ((Composite) children[x]).getChildren();
                    for (int i = 0; i < grandChildren.length; i++)
                    {
                        if (grandChildren[i] instanceof Table)
                        {
                            Table table = (Table) grandChildren[i];
                            for (int y = 0; y < table.getItemCount(); y++)
                            {
                                String step = table.getItem(y).getText(0);
                                if(step.equals(""))
                                {
                                	return false;
                                }
                                if (this.hasStep(step, components, component))
                                {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
    /**
     * 
     * Verifies if the given step exists in the use case.
     * @param step the step to be verified
     * @param components the graphical components list
     * @param currentComponent the component to be verified
     * @return the result of the verification
     */
    public boolean hasStep(String step, List<Composite> components, Object currentComponent)
    {
        boolean hasFound = false;
        
        Iterator<Composite> it = components.iterator();
        
        while (it.hasNext())
        {
        	Composite item = it.next();
            if (item instanceof Composite)
            {
                if (item != currentComponent)
                {
                    Control[] children = ((Composite) item).getChildren();
                    for (int x = 0; x < children.length; x++)
                    {
                        if (children[x] instanceof Composite)
                        {
                            Control[] grandChildren = ((Composite) children[x]).getChildren();
                            for (int i = 0; i < grandChildren.length; i++)
                            {
                                if (grandChildren[i] instanceof Text)
                                {
                                    if (this.hasEqualStep(step, (Text) grandChildren[i]))
                                    {
                                        hasFound = true;
                                    }
                                }
                            }
                        }
                    }

                }
            }
        }
        return hasFound;
    }
    
    /**
     * 
     * Verifies if the content of the Text component is equals to of the given step text.
     * @param step the step to be compared
     * @param text the text component
     * @return the result of the comparison
     */
    public boolean hasEqualStep(String step, Text text)
    {
        if (!text.getText().contains(","))
        {
            if (step.equals(text.getText()))
            {
                text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
                return true;
            }
            else
            {
                text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
        }

        String[] textSteps = text.getText().split(",");
        for (int i = 0; i < textSteps.length; i++)
        {
            if (step.equals(textSteps[i].trim()))
            {
                text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_RED));
                return true;
            }
            else
            {
                text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
            }
        }
        return false;
    }
    
    /**
     * 
     * Paints with white color the components painted with red color. 
     * @param components the graphical components list
     */
    public void paintComponentWhiteColor(List<Composite> components)
    {
    	Iterator<Composite> it = components.iterator();
        
        while (it.hasNext())
        {
        	Composite item = it.next();
            
            if (item instanceof Composite)
            {
                Control[] children = ((Composite) item).getChildren();
                
                for (int x = 0; x < children.length; x++)
                {
                    if (children[x] instanceof Composite)
                    {
                        Control[] grandChildren = ((Composite) children[x]).getChildren();
                        
                        for (int i = 0; i < grandChildren.length; i++)
                        {
                            if (grandChildren[i] instanceof Text)
                            {
                                Text text = (Text) grandChildren[i];
                                
                                if (text.getBackground().equals(
                                        Display.getCurrent().getSystemColor(SWT.COLOR_RED)))
                                {
                                    text.setBackground(Display.getCurrent().getSystemColor(
                                            SWT.COLOR_WHITE));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    // INSPECT mcms 29/01 method changed to this class to be used at Export PDF action
    public String verifyMandatoryFields(HashMap<String, Object> components, FormEditor editor){
    	Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
    	//Feature ID
    	String featureId = ((Text) components.get("ID")).getText();
    	editor.setFocus();
    	if(featureId.equals("")){
    		((Text) components.get("ID")).setBackground(red);
    		return "Feature ID";
    	}
    	//UseCase ID
    	String ucId = ((Text) components.get("UCID")).getText();
    	if(ucId.equals("")){
    		((Text) components.get("UCID")).setBackground(red);
    		return "Use Case ID";
    	}
    	// Flows
        Iterator<Map.Entry<String , Object>> it = components.entrySet().iterator();
        String mandatory;
        while (it.hasNext())
        {
            Map.Entry<String , Object> item = it.next();
            Object component = item.getValue();
           if (item.getKey().equals("MainFlow"))
            {
        	   mandatory = verifyMandatoryFieldsAtFlows(component, red, editor);
        	   if(mandatory!=null){
        		   return mandatory;
        	   }
            }
            if(item.getKey().equals("AlternativeFlows"))
            {
            	List<Composite> flows = (List<Composite> )component;
            	Iterator itFlows = flows.iterator();
            	while(itFlows.hasNext()){
            		Composite composite = (Composite)itFlows.next();
                	mandatory = verifyMandatoryFieldsAtFlows(composite, red, editor);
              	   if(mandatory!=null){
              		   return mandatory;
              	   }

            	}
            }
        }
    	return null;	
    }
    
    // INSPECT mcms 29/01 method changed to this class to be used at Export PDF action
    private String verifyMandatoryFieldsAtFlows(Object composite, Color red, FormEditor editor){
         String from;
         String to;
         String cell;
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
                            if (i == 3)
                            {
                                from = ((Text) grandChildren[i]).getText();
                                if(from.equals("")){
                                	((Text) grandChildren[i]).setBackground(red);
                                	return "FROM Step";
                                }
                            }
                            if (i == 5)
                            {
                                to = ((Text) grandChildren[i]).getText();
                                if(to.equals("")){
                                	((Text) grandChildren[i]).setBackground(red);
                                	return "TO Step";
                                }
                            }
                        }
                        if (grandChildren[i] instanceof Table)
                        {
                        	editor.setFocus();
                            Table table = (Table) grandChildren[i];
                           
                            for(int z=0; z< table.getItemCount(); z++){
                            	for(int y=0; y<table.getColumnCount(); y++){
                            		cell = table.getItem(z).getText(y);
                            		if(cell.equals("")){
                        				if(y==0){
                                			table.getItem(z).setBackground(y, red);
                                			return "Step ID";
                                		}
                                		if(y==1){
                                			table.getItem(z).setBackground(y, red);
                            				return "User Action";
                                		}
                                		if(y==3){
                                			table.getItem(z).setBackground(y, red);
                            				return "System Response";
                            			}
                            		}
                            		}
                            	}
                            }
                        }
                    }
                }
            }
    	return null;
    }

}
