/*
 * @(#)PreferenceDialog.java
 *
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
 * -------   ------------   ----------   ----------------------------
 * tnd783   10/09/2008      LIBqq51204   Initial creation.
 * dwvm83	30/09/2008	 	LIBqq51204	 Rework after inspection LX302177.
 * dwvm83   31/10/2008		LIBqq51204	 Changes to method okPressed.
 * bqm764   11/12/2008      LIBqq51204   Changes the layout of Text fields.
 * pvcv     Apr 01, 2009				 Internationalization support
 * lmn3		May 14, 2009				 Changes due code inspection  
 */
package br.ufpe.cin.target.tcg.preferences;

import java.io.IOException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import br.ufpe.cin.target.tcg.propertiesreader.TestCaseProperties;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;


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
        newShell.setText(Properties.getProperty("test_case_preferences"));
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

        this.createFields(composite);
        
        return composite;
    }

    /**
     * Comment for method.
     * @param composite
     * @return
     */
    private void createFields(Composite composite)
    {
        createTestCaseIdPreference(composite);
        createTestCaseInitialIdPreference(composite);
        createEmptyFieldPreference(composite);
        createObjectivePrefixPreference(composite);
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
        label.setText(Properties.getProperty("test_case_id"));
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
        label.setText(Properties.getProperty("test_case_initial_id"));
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
        label.setText(Properties.getProperty("empty_field"));
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
        label.setText(Properties.getProperty("objective_prefix"));
        objectivePrefix = new Text(composite, SWT.BORDER);
        objectivePrefix.setLayoutData(data);
        objectivePrefix.setText(String.valueOf(properties.getObjectivePrefix()));
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
                this.properties.store();
                // The generateTestCases method is called after having changed the preferences. 
                // It is only called if the OnTheFly editor is open.
                
                GUIManager.getInstance().refreshEditors(true);

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
            MessageDialog.openError(shell, Properties.getProperty("invalid_field"), Properties.getProperty("test_case_id_should_be_integer"));
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

    }
}
