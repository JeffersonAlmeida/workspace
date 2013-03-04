//PreferencesDialog

/*
 * @(#)PreferencesSTDAspect.aj
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * lmn3      06/11/2009                  Initial creation.
 */
import java.io.IOException;

import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.tcg.preferences.PreferencesDialog;

/**
 * Aspect responsible for add the "Component Keyword" field in PreferencesDialog.
 */
privileged public aspect PreferencesSTDAspect
{
    /**
     * The text that is used for the component keyword files
     */
    private Text PreferencesDialog.componentKeyword;
    
    private void PreferencesDialog.createComponentKeywordPreference(Composite composite)
    {
        GridData data = new GridData();
        data.widthHint = 200;
        Label label = new Label(composite, SWT.NONE);
        label.setText(Properties.getProperty("component_keyword"));
        componentKeyword = new Text(composite, SWT.BORDER);
        componentKeyword.setLayoutData(data);
        componentKeyword.setText(properties.getComponentKeywordValue());
    }
    
    pointcut updatePropertiesPointcut(PreferencesDialog dialog) :
        execution(private void PreferencesDialog.updateProperties()) && this(dialog);
    
    declare soft : IOException : withincode(void PreferencesDialog.updateProperties());
    
    after(PreferencesDialog dialog) : updatePropertiesPointcut(dialog) 
    {
        try
        {
            String componentKeyword = dialog.componentKeyword.getText();
            dialog.properties.setComponentKeywordValue(componentKeyword);
        }
        catch (IOException e)
        {
            throw new org.aspectj.lang.SoftException(e);
        }
    }
        
    pointcut createFieldsPointcut(Composite composite, PreferencesDialog dialog) :
        execution(private void PreferencesDialog.createFields(Composite)) 
        && args(composite) && this(dialog);
    
    before(Composite composite, PreferencesDialog dialog) : createFieldsPointcut(composite, dialog)
    {
        dialog.createComponentKeywordPreference(composite);
    }
    
    
}
