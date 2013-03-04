/*
 * @(#)OnTheFlyTCGAction.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------  ------------    ----------    ----------------------------
 * wdt022   Mar 14, 2008    LIBpp56482   Initial creation.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * dwvm83	Oct 02, 2008	LIBqq51204	 Added method generateTestCases.
 * dwvm83   Oct 14, 2008	LIBqq51204	 Added method selectionChanged.
 * dwvm83   Oct 31, 2008	LIBqq51204	 Added method onTheFlyEditorReference, changes to methods generateTestCases and selectionChanged.
 */
package com.motorola.btc.research.target.tcg.actions;

import java.util.Collection;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.common.lts.LTS;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.TCGActivator;
import com.motorola.btc.research.target.tcg.controller.TestCaseGeneratorController;
import com.motorola.btc.research.target.tcg.editors.OnTheFlyEditorInput;
import com.motorola.btc.research.target.tcg.editors.OnTheFlyMultiPageEditor;
import com.motorola.btc.research.target.tcg.exceptions.TestGenerationException;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * This is the action that activates On the Fly test case generation process.
 * 
 * <pre>
 * CLASS:
 * Activates On the Fly test case generation process. It verifies if there are no errors, and then it 
 * invokes the On the Fly test case generation editor.
 * 
 * RESPONSIBILITIES:
 * 1) Invokes the On the Fly test case generation editor.
 *
 * USAGE:
 * It is referred by its ID.
 */
public class OnTheFlyTCGActionDelegate extends AbstractTCGActionDelegate
{

    /**
     * The ID of the action that is declared in plugin.xml.
     */
    public static final String ID = "com.motorola.btc.research.target.tcg.action.OnTheFlyTCGAction";

    /**
     * @see AbstractTCGActionDelegate#hookGeneration()
     */
    
    protected void hookGeneration()
    {
    	generateTestCases();
    }
    
    /**
     * @see Generates the Test Cases again after having changed the Test Case preferences.
     * The new test cases will reflect the changes made in the preferences' parameters.
     */
    public void generateTestCases()
    {
    	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
      	// Getting the onTheFly editor reference
     	IEditorReference ref = onTheFlyEditorReference(page);
     	// Closing the previous onTheFly editor (in case the preferences were changed)    	
    	if (ref != null)
    		page.closeEditor(ref.getEditor(false), true);
    	
        ProjectManagerExternalFacade projectFacade = ProjectManagerExternalFacade.getInstance();
        Collection<PhoneDocument> documents = projectFacade.getCurrentProject().getPhoneDocuments();

        TestCaseGeneratorController controller = TestCaseGeneratorController.getInstance();
        LTS<FlowStep, Integer> lts = controller.generateLTSModel(documents);

        try
        {
            TestSuite<TestCase<FlowStep>> rawTestCases = controller.generateRawTests(lts);
            TestSuite<TextualTestCase> textualTestCases = controller.mapToTextualTestCases(
                    rawTestCases, documents);

            OnTheFlyEditorInput onTheFlyInput = new OnTheFlyEditorInput(rawTestCases,
                    textualTestCases);
             page.openEditor(onTheFlyInput, OnTheFlyMultiPageEditor.ID);
        }
        catch (TestGenerationException e1)
        {
            e1.printStackTrace();
            TCGActivator.logError(0, this.getClass(),
                    "Error generating test cases: " + e1.getMessage(), e1);
            MessageDialog.openError(this.window.getShell(), e1.getTitle(),
                    e1.getMessage());
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
            TCGActivator.logError(0, this.getClass(),
                    "Error opening " + OnTheFlyMultiPageEditor.ID + ": " + e.getMessage(), e);
            MessageDialog.openError(this.window.getShell(), "Graphical Interface Error",
                    "Graphical interface error while opening On The Fly Generation editor");
        }
    }
    
    /**
     * @see org.eclipse.ui.actions.ActionDelegate#selectionChanged()
     */
    
    public void selectionChanged(IAction action, ISelection selection) 
    {
     	super.selectionChanged(action, selection);
    	IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
    	IEditorReference editor = onTheFlyEditorReference(page);
    	// if the onTheFly editor is open, the menu option should be disabled
    	if (editor!= null)
    		action.setEnabled(false);
    	//if the editor is closed AND the project is not closed, the option menu should be enabled
    	else if (ProjectManagerController.getInstance().getCurrentProject() != null)
    		action.setEnabled(true);
    	
    }
    
    /**
     * Returns a reference to the onTheFly editor if it is open
     * @param page The active page
     * @return IEditorReference A reference to the editor; null if the editor is not open
     */
    public IEditorReference onTheFlyEditorReference(IWorkbenchPage page) 
    {
    	IEditorReference[] editors = page.getEditorReferences();
    	IEditorReference ref = null;
    	for(int i = 0; i < editors.length; i++)
    		try{
    			if (editors[i].getEditorInput() instanceof OnTheFlyEditorInput)
    				ref = editors[i];
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	return ref;
    		
   }
   
}
