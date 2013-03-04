/*
 * @(#)SearchDialog.java
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
package br.ufpe.cin.target.uceditor.dialogs;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.editor.FormEditor;

import br.ufpe.cin.target.common.util.Properties;

/**
 * This dialog is used to search for contents in use cases documents exhibited by the Use Case Editor. 
 * The dialog displays a search dialog with two buttons ("Search" and "Cancel") and one combo box
 * ("Queries").
 * 
 * <pre>
 * CLASS:
 * This class creates a search dialog. This class overrides methods from Dialog, and adds procedures to
 * handle search operations.
 * </pre>
 */
public class SearchDialog extends Dialog
{

    /**
     * Input text component. Presents the query history and the current query.
     */
    private Combo text;

    /**
     * The title of the search dialog.
     */
    private String title;

    /**
     * The list of components where will be done the search.
     */
    private HashMap<String, Object> fields;
    
    private FormEditor editor;

    /**
     * Creates a search dialog given its parent and title and initializes fields.
     * 
     * @param parent The parent of the search dialog
     * @param title The title of the search dialog.
     * @param components The list of components where will be done the search.
     */
    public SearchDialog(Shell parent, String title, HashMap<String, Object> components,FormEditor editor)
    {
        super(parent);
        this.title = title;
        this.createShell();
        this.createButtonBar(parent);
        this.fields = components;
        this.editor = editor;
    }

    /**
     * Configures the shell. Sets the title, size and location of the shell.
     * 
     * @param shell The shell to be configured.
     */
    
    protected void configureShell(Shell shell)
    {
        super.configureShell(shell);

        if (title != null)
        {
            shell.setText(title);
        }

        shell.setSize(400, 135);
        shell.setLocation(400, 300);
    }

    /**
     * Creates the buttons for the search dialog buttons.
     * 
     * @param parent The search button bar.
     */
    
    protected void createButtonsForButtonBar(Composite parent)
    {
        createButton(parent, IDialogConstants.OK_ID, Properties.getProperty("Search"), true);
        createButton(parent, IDialogConstants.CANCEL_ID, IDialogConstants.CANCEL_LABEL, false);

        /* enables the 'Search' button depending on the value of 'text' */
        boolean enable = false;
        enable = this.text != null && this.text.getText().trim().length() > 0;

        getButton(IDialogConstants.OK_ID).setEnabled(enable);
    }

    /**
     * Handles a button pressed event.
     * 
     * @param buttonId The id of the pressed button.
     */
    
    protected void buttonPressed(int buttonId)
    {
        switch (buttonId)
        {
            case IDialogConstants.OK_ID:
                this.search();
                editor.setFocus();
                break;
            default:
                break;
        }
        
        super.buttonPressed(buttonId);
    }

    /**
     * Creates the dialog area.
     * 
     * @param parent The parent component of the dialog area.
     * @return A composite.
     */
    
    protected Control createDialogArea(Composite parent)
    {
        // create composite
        Composite composite = (Composite) super.createDialogArea(parent);

        GridLayout layout = new GridLayout(2, false);
        layout.marginHeight = 5;
        layout.marginWidth = 10;
        layout.verticalSpacing = 0;

        composite.setLayout(layout);
        composite.setLayoutData(new GridData(GridData.FILL_BOTH));

        Label label = new Label(composite, SWT.WRAP);
        label.setText("Find:");

        text = new Combo(composite, SWT.SINGLE | SWT.BORDER | SWT.V_SCROLL);
        text.setLayoutData(new GridData(GridData.GRAB_HORIZONTAL | GridData.HORIZONTAL_ALIGN_FILL));
        text.setFocus();

        this.addTextModifyListener(this.text);

        applyDialogFont(composite);

        return composite;
    }

    /**
     * Validates the text in the combo box.
     * 
     * @param combo The combo whose text will be validated.
     */
    private void addTextModifyListener(final Combo combo)
    {
        combo.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                if (combo.getText().trim().length() == 0)
                {
                    getButton(IDialogConstants.OK_ID).setEnabled(false);
                }
                else
                {
                    getButton(IDialogConstants.OK_ID).setEnabled(true);
                }
            }
        });
    }

    /**
     * Makes the search.
     */
    private void search()
    {
        String query = this.text.getText().trim();
        if (query.length() > 0)
        {
            Iterator<Map.Entry<String, Object>> it = fields.entrySet().iterator();

            while (it.hasNext())
            {
                Map.Entry<String, Object> item = it.next();
                Object component = item.getValue();
                
                if (component != null)
                {

                    // Search to Text fields of interface
                    if (component instanceof Text)
                    {
                        this.searchText(((Text) component), query);
                    }
                    // Search to Requirement Table 
                    if (component instanceof Table)
                    {
                        this.searchTable(((Table) component), query);
                    }
                    
                    //Search to Main Flow
                    if(item.getKey().equals("MainFlow")){
                    	this.searchFlows((Composite)component, query);
                    }
                    // Search to Alternative Flow
                    if(item.getKey().equals("AlternativeFlows")){
                    	List<Composite> list = (List<Composite>)component;
                    	Iterator<Composite> itFlows = list.iterator();
                    	while(itFlows.hasNext()){
                    		Composite flow = itFlows.next();
                    		this.searchFlows(flow, query);
                    	}
                    }
                }
            }
        }
    }

    /**
     * Highlights text components where the search was successful.
     * 
     * @param text The text component.
     * @param query The word or expression searched.
     */
    private void searchText(Text text, String query)
    {
        Color white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
        String expression = text.getText().trim();
        
        if (this.findQuery(expression, query))
        {
        	if(text.getBackground().equals(white)){
                text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));
        	}
        }
        else{
        	if(!text.getBackground().equals(white)){
           	 	text.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
        	}
        }
    }

    /**
     * Highlights table items where the search was successful.
     * 
     * @param table The table component.
     * @param query The word or expression searched.
     */
    private void searchTable(Table table, String query)
    {
        int numRows = table.getItemCount();
        int numColumns = table.getColumnCount();
        Color white = Display.getCurrent().getSystemColor(SWT.COLOR_WHITE);
        
        for (int k = 0; k < numColumns; k++)
        {
            for (int i = 0; i < numRows; i++)
            {
                //table.getItem(i).setBackground(k,
                  //      Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
                
                String aux = table.getItem(i).getText(k).trim();
                
                if (this.findQuery(aux, query))
                {
                	if(table.getItem(i).getBackground(k).equals(white)){
                        table.getItem(i).setBackground(k,
                                Display.getCurrent().getSystemColor(SWT.COLOR_GREEN));                		
                	}
                }
                else{
                	if(!table.getItem(i).getBackground(k).equals(white)){
                        table.getItem(i).setBackground(k,
                                Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));                		
                	}      
                }
            }
        }
    }
    
    public void searchFlows(Composite component, String query){
    
    	   Object[] children = ((Composite) component).getChildren();
           Object[] grandChildren;
           for (int x = 0; x < children.length; x++)
           {
               if (children[x] instanceof Composite)
               {
                   grandChildren = ((Composite) children[x]).getChildren();
                   for (int y = 0; y < grandChildren.length; y++)
                   {
                       if (grandChildren[y] instanceof Text)
                       {
                           this.searchText(((Text) grandChildren[y]), query);
                       }
                       if (grandChildren[y] instanceof Table)
                       {
                           this.searchTable(((Table) grandChildren[y]), query);
                       }
                   }
               }
           }
    }

    /**
     * Finds the query in the expression.
     * 
     * @param expression text contained into component.
     * @param query The word or expression searched.
     */
    private boolean findQuery(String expression, String query)
    {
        boolean hasFound = false;
        int size = query.length();
        int y = 0;
        int z = 0;
        
        while ((y < expression.length()) && (!hasFound))
        {
            if (expression.charAt(y) == query.charAt(z))
            {
                if (z == size - 1)
                {
                    hasFound = true;
                }
                z++;
            }
            else
            {
                z = 0;
            }
            y++;
        }
        return hasFound;
    }

}
