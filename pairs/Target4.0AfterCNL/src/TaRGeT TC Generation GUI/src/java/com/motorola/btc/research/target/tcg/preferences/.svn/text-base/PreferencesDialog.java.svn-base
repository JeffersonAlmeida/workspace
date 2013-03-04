/*
 * @(#)PreferenceDialog.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   10/09/2008      LIBqq51204   Initial creation.
 * dwvm83	30/09/2008	 	LIBqq51204	 Rework after inspection LX302177.
 * dwvm83   31/10/2008		LIBqq51204	 Changes to method okPressed.
 * bqm764   11/12/2008      LIBqq51204   Changes the layout of Text fields.  
 */
package com.motorola.btc.research.target.tcg.preferences;

import java.io.IOException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.tcg.actions.OnTheFlyTCGActionDelegate;
import com.motorola.btc.research.target.tcg.propertiesreader.TestCaseProperties;
import com.motorola.btc.research.target.tcg.propertiesreader.TestCaseProperties.PrintingDescription;

/**
 * This class is responsible for creating the Preferences Dialog
 */
public class PreferencesDialog extends Dialog
{
	
    /**
     * The test case ID.
     */
	private Text testCaseId;

    /** 
     * The number of the first test case generated in the suite.
     */
    private Text testCaseInitialId;

    /**
     * The text that is used for the empty fields.
     */
    private Text emptyField;

    /**
     * The prefix used in the objectives section.
     */
    private Text objectivePrefix;
    
    /**
     * Indicates if the requirements (between brackets) should be printed or not.
     */

    private Button keepRequirements;
    
    /**
     * Indicates how to create the use case description according to the options.
     * The possible values are: All, Last, None. 
     */
    private Combo printUseCaseDescription;

    /**
     * Indicates if the concatenation of the description of the flows 
     * covered by the test should be printed or not.
     * Possible values: None, All, Last.
     */
    private Combo printFlowDescription;

    private TestCaseProperties properties;

    private Shell shell;

    public PreferencesDialog(Shell parentShell)
    {
        super(parentShell);
        this.properties = TestCaseProperties.getInstance();
        this.shell = parentShell;
    }


    /**
	 * Configures the given shell in preparation for opening this window in it.
	 * @param newShell the shell
	 */
    protected void configureShell(Shell newShell)
    {
        super.configureShell(newShell);
        newShell.setText("Test Case Preferences");
    }


	/**
	 * Creates and returns the contents of the upper part of this dialog (above
	 * the button bar).
	 * @param parent: the parent composite to contain the dialog area
	 * @return the dialog area control
	 */
    protected Control createDialogArea(Composite parent)
    {
        Composite composite = (Composite) super.createDialogArea(parent);

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        composite.setLayout(layout);

        createTestCaseIdPreference(composite);
        createTestCaseInitialIdPreference(composite);
        createEmptyFieldPreference(composite);
        createObjectivePrefixPreference(composite);
        createPrintUseCaseDescriptionPreference(composite);
        createPrintFlowDescriptionPreference(composite);
        createKeepRequirementsPreference(composite);

        return composite;
    }

    /**
     * Sets the test case ID preference with the value read from the properties file.
     * @param composite
     */
    private void createTestCaseIdPreference(Composite composite)
    {
    	GridData data = new GridData();
        data.widthHint = 200;
    	Label label = new Label(composite, SWT.NONE);
        label.setText("Test Case ID:");
        testCaseId = new Text(composite, SWT.BORDER);
        testCaseId.setLayoutData(data); 
        testCaseId.setText(properties.getTestCaseId());
    }

    /**
     * Sets the test case initial ID preference with the value read from the properties file.
     * @param composite
     */
    private void createTestCaseInitialIdPreference(Composite composite)
    {
        GridData data = new GridData();
        data.widthHint = 200;
    	Label label = new Label(composite, SWT.NONE);
        label.setText("Test Case Initial ID:");
        testCaseInitialId = new Text(composite, SWT.BORDER);
        testCaseInitialId.setLayoutData(data);
        testCaseInitialId.setText(String.valueOf(properties.getTestCaseInitialId()));
    }

    /**
     * Sets the empty field preference with the value read from the properties file.
     * @param composite
     */
    private void createEmptyFieldPreference(Composite composite)
    {
    	GridData data = new GridData();
        data.widthHint = 200;
    	Label label = new Label(composite, SWT.NONE);
        label.setText("Empty Field");
        emptyField = new Text(composite, SWT.BORDER);
        emptyField.setLayoutData(data);
        emptyField.setText(String.valueOf(properties.getEmptyField()));
    }

    /**
     * Sets the objective prefix preference with the value read from the properties file.
     * @param composite
     */
    private void createObjectivePrefixPreference(Composite composite)
    {
    	GridData data = new GridData();
        data.widthHint = 200;
        Label label = new Label(composite, SWT.NONE);
        label.setText("Objective Prefix:");
        objectivePrefix = new Text(composite, SWT.BORDER);
        objectivePrefix.setLayoutData(data);
        objectivePrefix.setText(String.valueOf(properties.getObjectivePrefix()));
    }

    /**
     * Sets the keep requirements preference with the value read from the properties file.
     * @param composite
     */
    private void createKeepRequirementsPreference(Composite composite)
    {
        keepRequirements = new Button(composite, SWT.CHECK);
        keepRequirements.setText("Keep Requirements");
        keepRequirements.setSelection(properties.isKeepingRequirements());
    }

    /**
     * Sets the use case description preference with the value read from the properties file.
     * @param composite
     */
    private void createPrintUseCaseDescriptionPreference(Composite composite)
    {
        Label label = new Label(composite, SWT.NONE);
        label.setText("Print Use Case Description:");
        printUseCaseDescription = new Combo(composite, SWT.NONE);
        printUseCaseDescription.add("Last", 0);
        printUseCaseDescription.add("All", 1);
        printUseCaseDescription.add("None", 2);
        printUseCaseDescription.select(getIndexFromDescription(properties
                .getPrintUseCaseDescription()));
    }

    /**
     * Sets the print flow description preference with the value read from the properties file.
     * @param composite
     */
    private void createPrintFlowDescriptionPreference(Composite composite)
    {
        Label label = new Label(composite, SWT.NONE);
        label.setText("Print Flow Description:");
        printFlowDescription = new Combo(composite, SWT.NONE);
        printFlowDescription.add("Last", 0);
        printFlowDescription.add("All", 1);
        printFlowDescription.add("None", 2);
        printFlowDescription.select(getIndexFromDescription(properties.getPrintFlowDescription()));
    }

    /**
     * Gets the index of the printUseCaseDescription
     * @param printUseCaseDescription
     * @return An int representing the index 
     */
    private int getIndexFromDescription(PrintingDescription printUseCaseDescription)
    {
        int index = -1;
        switch (printUseCaseDescription)
        {
            case LAST:
                index = 0;
                break;
            case ALL:
                index = 1;
                break;
            case NONE:
                index = 2;
        }
        return index;
    }


    /**
	 * Notifies that the OK button of this dialog has been pressed.
	 */
    protected void okPressed()
    {
        try
        {
            if (validateFields())
            {
                updateProperties();
                properties.store();
                // The generateTestCases method is called after having changed the preferences. 
                // It is only called if the OnTheFly editor is open.
                OnTheFlyTCGActionDelegate actionDelegate = new OnTheFlyTCGActionDelegate();
                IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
                if (actionDelegate.onTheFlyEditorReference(page) != null)
                	actionDelegate.generateTestCases();
                super.okPressed();

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Validates the test case initial ID field.
     * @return A boolean indicating if the aforementioned field is valid or not.  
     */
    private boolean validateFields()
    {
        boolean result = testCaseInitialId.getText().matches("\\d+");
        
        if (!result){
            MessageDialog.openError(shell, "Invalid Field", "Test Case Initial ID must be an integer.");
        }
        
        return result;
    }

    /**
     * Updates the properties with the new values entered by the user.
     * @throws IOException In case something goes wrong during the file writing.
     */
    private void updateProperties() throws IOException
    {
        String testCaseId = this.testCaseId.getText();
        properties.setTestcaseId(testCaseId);

        String testCaseInitialId = this.testCaseInitialId.getText();
        properties.setTestCaseInitialId(new Integer(testCaseInitialId));

        String emptyField = this.emptyField.getText();
        properties.setEmptyField(emptyField);

        String objectivePrefix = this.objectivePrefix.getText();
        properties.setObjectivePrefix(objectivePrefix);

        boolean keepRequirements = this.keepRequirements.getSelection();
        properties.setKeepingRequirements(keepRequirements);

        int index = this.printUseCaseDescription.getSelectionIndex();
        String printUseCaseDescription = this.printUseCaseDescription.getItem(index);
        properties.setPrintUseCaseDescription(PrintingDescription.valueOf(printUseCaseDescription
                .toUpperCase()));

        index = this.printFlowDescription.getSelectionIndex();
        String printFlowDescription = this.printFlowDescription.getItem(index);
        properties.setPrintFlowDescription(PrintingDescription.valueOf(printFlowDescription
                .toUpperCase()));
    }
}
