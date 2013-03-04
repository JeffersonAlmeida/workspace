//PreferencesDialog
/*
 * @(#)PreferencesOnTheFlyAspect.aj
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * fsf2      31/08/2009                  Initial creation.
 */
/**
 * << high level description of the aspect >>
 *<pre>
 * CLASS:
 * << Short description of the Utility Function or Toolkit >>
 * [ Template guidelines:
 *     When the class is an API UF, describe only WHAT the UF shall
 *     do, when it is an implementation UF, describe HOW the UF will
 *     do what it should, and if it is a tailoring to an already existing
 *     UF, describe what are the diferences that made the UF needed.
 * ]
 * RESPONSIBILITIES:
 * <<High level list of things that the class does>>
 * 1) responsibility
 * 2) ...
 * <Example:
 * RESPONSIBILITIES:
 * 1) Provide interface to access navigation related UFs
 * 2) Guarantee easy access to the UFs
 * 3) Provide functional implementation to the ScrollTo UF for P2K.
 * >
 *
 * COLABORATORS:
 * << List of descriptions of relationships with other classes, i.e. uses,
 * contains, creates, calls... >>
 * 1) class relationship
 * 2) ...
 *
 * USAGE:
 * << how to use this class - for UFs show how to use the related 
 * toolkit call, for toolkits show how a test case should access the
 * functions >>
 *
 */

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.tcg.preferences.PreferencesDialog;
import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties.PrintingDescription;



privileged public aspect PreferencesOnTheFlyAspect
{
    /**
     * Indicates if the requirements (between brackets) should be printed or not.
     */
    private Button PreferencesDialog.keepRequirements;
    
    /**
     * Indicates if the concatenation of the description of the flows 
     * covered by the test should be printed or not.
     * Possible values: None, All, Last.
     */
    private Combo PreferencesDialog.printFlowDescription;
    
    /**
     * Indicates how to create the use case description according to the options.
     * The possible values are: All, Last, None. 
     */
    private Combo PreferencesDialog.printUseCaseDescription;
    
    /**
     * Sets the use case description preference with the value read from the properties file.
     * @param composite
     */
    private void PreferencesDialog.createPrintUseCaseDescriptionPreference(Composite composite)
    {
        Label label = new Label(composite, SWT.NONE);
        label.setText(Properties.getProperty("print_description"));
        printUseCaseDescription = new Combo(composite, SWT.NONE);
        printUseCaseDescription.add(Properties.getProperty("last"), 0);
        printUseCaseDescription.add(Properties.getProperty("all"), 1);
        printUseCaseDescription.add(Properties.getProperty("none"), 2);
        printUseCaseDescription.select(getIndexFromDescription(properties
                .getPrintUseCaseDescription()));
    }
    
    /**
     * Gets the index of the printUseCaseDescription
     * @param printUseCaseDescription
     * @return An int representing the index 
     */
    private int PreferencesDialog.getIndexFromDescription(PrintingDescription printUseCaseDescription)
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
     * Sets the print flow description preference with the value read from the properties file.
     * @param composite
     */
    private void PreferencesDialog.createPrintFlowDescriptionPreference(Composite composite)
    {
        Label label = new Label(composite, SWT.NONE);
        label.setText(Properties.getProperty("print_flow"));
        printFlowDescription = new Combo(composite, SWT.NONE);
        printFlowDescription.add(Properties.getProperty("last"), 0);
        printFlowDescription.add(Properties.getProperty("all"), 1);
        printFlowDescription.add(Properties.getProperty("none"), 2);
        printFlowDescription.select(getIndexFromDescription(properties.getPrintFlowDescription()));
    }
    
    /**
     * Sets the keep requirements preference with the value read from the properties file.
     * @param composite
     */
    private void PreferencesDialog.createKeepRequirementsPreference(Composite composite)
    {
        GridData data = new GridData();
        data.horizontalSpan = 2;
        
        keepRequirements = new Button(composite, SWT.CHECK);
        keepRequirements.setText(Properties.getProperty("keep_requirements"));
        keepRequirements.setSelection(properties.isKeepingRequirements());
        keepRequirements.setLayoutData(data);
    }
    
    pointcut updatePropertiesPointcut(PreferencesDialog dialog) :
        execution(private void PreferencesDialog.updateProperties()) && this(dialog);
    
    declare soft : IOException : withincode(void PreferencesDialog.updateProperties());
    
    after(PreferencesDialog dialog) : updatePropertiesPointcut(dialog) 
    {
        try
        {
            boolean keepRequirements = dialog.keepRequirements.getSelection();
            dialog.properties.setKeepingRequirements(keepRequirements);

            dialog.properties.setPrintUseCaseDescription(dialog.printUseCaseDescription.getSelectionIndex());
            
            dialog.properties.setPrintFlowDescription(dialog.printFlowDescription.getSelectionIndex());
        }
        catch (IOException e)
        {
            throw new org.aspectj.lang.SoftException(e);
        }
    }
    
    
    pointcut createFieldsPointcut(Composite composite, PreferencesDialog dialog) :
        execution(private void PreferencesDialog.createFields(Composite)) 
        && args(composite) && this(dialog);
    
    after(Composite composite, PreferencesDialog dialog) : createFieldsPointcut(composite, dialog)
    {
        dialog.createPrintUseCaseDescriptionPreference(composite);
        dialog.createPrintFlowDescriptionPreference(composite);
        dialog.createKeepRequirementsPreference(composite);
    }
}
