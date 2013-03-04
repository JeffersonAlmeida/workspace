/*
 * @(#)AddLexicalTermDialog.java
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
 * wxx###   May 14, 2008    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.dialogs;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.target.cnl.Activator;
import br.ufpe.cin.target.cnl.actions.ReloadBasesActionDelegate;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.controller.CNLProperties;
import br.ufpe.cin.target.cnl.dictionary.CNLSynonym;
import br.ufpe.cin.target.cnl.views.CNLView;


//INSPECT: Felype - Alterações na classe toda. Mais tipos de termos, mensagens de erro, etc.
public class AddLexicalTermDialog extends LexicalDialog
{
    private Combo unknownTermsCombo;

    private Button addButton;
    
    private Composite synonymsComposite;
    
    private Group synonymsGroup;

    public AddLexicalTermDialog(Shell parentShell)
    {
        super(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

        this.setText("Add New Lexical Term");
    }

    public void open(HashMap<TestCaseTextType, Collection<String>> lexiconFaults)
    {
        // Create the dialog window
        Shell shell = new Shell(getParent(), getStyle());
        shell.setText(getText());
        shell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                "icons/addNewLexicalTerm.bmp").createImage());
        createContents(shell, lexiconFaults);
        shell.pack();
        shell.open();
        Display display = getParent().getDisplay();
        
        while (!shell.isDisposed())
        {
            if (!display.readAndDispatch())
            {
                display.sleep();
            }
        }
    }

    
    protected void refreshContents()
    {
        try
        {
            ReloadBasesActionDelegate action = new ReloadBasesActionDelegate();
            action.init(CNLView.getView());
            action.hook();

            HashMap<TestCaseTextType, Collection<String>> list = CNLPluginController.getInstance()
                    .getOrderedUnkownWords();
            this.unknownTermsCombo.removeAll();

            this.populateUnknownTermsCombo(list);

            if (this.unknownTermsCombo.getItemCount() > 1)
            {
                this.unknownTermsCombo.select(1);
            }
            else
            {
                this.unknownTermsCombo.select(0);
            }
        }
        catch (Exception e)
        {
            MessageDialog.openError(this.getParent(), "Error reloading bases", e.getMessage());
            e.printStackTrace();
        }

    }

    private void createContents(final Shell shell,
            HashMap<TestCaseTextType, Collection<String>> lexiconFaults)
    {
        shell.setLayout(new GridLayout(2, false));

        Label unknownTermsLabel = new Label(shell, SWT.NONE);
        unknownTermsLabel.setText("Term:");
        GridData data = new GridData();
        unknownTermsLabel.setLayoutData(data);

        this.unknownTermsCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);

        data = new GridData(100, SWT.DEFAULT);

        this.populateUnknownTermsCombo(lexiconFaults);

        this.unknownTermsCombo.select(0);

        Label termTypeLabel = new Label(shell, SWT.NONE);
        termTypeLabel.setText("New Term Type:");
        data = new GridData();
        termTypeLabel.setLayoutData(data);

        this.termTypeCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        data = new GridData(100, SWT.DEFAULT);
        
        this.termTypeCombo.setLayoutData(data);
        this.unknownTermsCombo.setLayoutData(data);

        String[] terms = CNLProperties.getInstance().getProperty("cnl_terms").split(",");
        terms = this.orderTerms(terms);

        for (String term : terms)
        {
            this.termTypeCombo.add(term);
        }

        this.termTypeCombo.select(0);

        this.termTypeCombo.addSelectionListener(new SelectionListener()
        {

            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                changeVisibility(shell);
                
                warning();
            }
        });

        initBundles(shell);
        
        data = new GridData(SWT.LEFT, SWT.TOP, false, false);
        
        this.warningLabel = new Label(shell, SWT.WRAP);
        this.warningLabel.setLayoutData(data);
        this.warningLabel.setText("Some required field is empty.");
        this.warningLabel.setForeground(new Color(this.warningLabel.getDisplay(), ERROR_COLOR));
        
        this.verbWarningLabel = new Label(shell, SWT.WRAP);
        this.verbWarningLabel.setLayoutData(data);
        this.verbWarningLabel.setText("You can not add modal verbs.");
        this.verbWarningLabel.setForeground(new Color(this.verbWarningLabel.getDisplay(), ERROR_COLOR));
        
        this.synonymsComposite = new Composite(shell, SWT.NONE);
        
        GridLayout gridLayout = new GridLayout(1, true);
        gridLayout.verticalSpacing = 0;
        synonymsComposite.setLayout(gridLayout); 
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        synonymsComposite.setLayoutData(gridData);
        
        this.setSynonymsCompositeVisible(false);
        
        this.addButton = new Button(shell, SWT.PUSH);
        data = new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1);
        this.addButton.setLayoutData(data);
        this.addButton.setText("Add New Term");
        
        this.addButton.setEnabled(false);

        this.addButton.addSelectionListener(new SelectionListener()
        {
            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                if(addNewTerm(shell))
                {
                    displayTermAddedDialog();
                }
            }
        });

        this.unknownTermsCombo.addSelectionListener(new SelectionListener()
        {
            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                int selectedIndex = termTypeCombo.getSelectionIndex();

                if (selectedIndex > -1)
                {
                    String selectedTerm = termTypeCombo.getItem(selectedIndex);

                    if (selectedTerm.equals(NOUN))
                    {
                        fillBundle(nounBundle);
                    }
                    else if (selectedTerm.equals(VERB))
                    {
                        fillBundle(verbBundle);
                    }
                    else if (selectedTerm.equals(ADJECTIVE))
                    {
                        fillBundle(adjectiveBundle);
                    }
                    else if (selectedTerm.equals(ADVERB))
                    {
                        fillBundle(adverbBundle);
                    }
                    else if (selectedTerm.equals(PREPOSITION))
                    {
                        fillBundle(prepositionBundle);
                    }
                    else if (selectedTerm.equals(CONJUNCTION))
                    {
                        fillBundle(conjunctionBundle);
                    }
                    else if (selectedTerm.equals(DETERMINER))
                    {
                        fillBundle(determinerBundle);
                    }
                                        
                    fillSynonymes();
                    
                    warning();
                    
                    shell.pack();
                    shell.layout();
                    shell.redraw();
                }
            }
            
        });

        this.emptyListener = new Listener()
        {
            
            public void handleEvent(Event event)
            {
                switch (event.keyCode)
                {
                    case 13:
                    case 16777296:

                        if (addButton.isEnabled() && addNewTerm(shell))
                        {
                            displayTermAddedDialog();
                            changeVisibility(shell);
                            warning();
                        }

                        break;
                    default:
                        warning();
                }
            }
        };

        this.unknownTermsCombo.addListener(SWT.KeyUp, this.emptyListener);

        this.termTypeCombo.addListener(SWT.KeyUp, this.emptyListener);

        this.changeVisibility(shell);
        
        this.warning();
    }
    
    private Group createSynonymsGroup()
    {
        Group group = new Group(this.synonymsComposite, SWT.NULL);
        group.setText("Synonyms");
        
        GridLayout groupLayout = new GridLayout(1, true);
        groupLayout.verticalSpacing = 0;
        group.setLayout(groupLayout); 
        
        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        group.setLayoutData(gridData);
        
        return group;
    }

    private void fillSynonymes()
    {
        if(this.synonymsGroup != null)
        {
            this.synonymsGroup.dispose();
        }
        
        this.setSynonymsCompositeVisible(false);
        
        int index = this.unknownTermsCombo.getSelectionIndex();

        if (index > 0)
        {
            String selectedTerm = this.unknownTermsCombo.getItem(index);

            List<CNLSynonym> synonyms = CNLPluginController.getInstance().getSynonyms(selectedTerm);

            if (!synonyms.isEmpty())
            {
                this.setSynonymsCompositeVisible(true);
                
                this.synonymsGroup = this.createSynonymsGroup();

                GridData gridData = new GridData();
                gridData.horizontalAlignment = GridData.FILL;
                gridData.grabExcessHorizontalSpace = true;
                gridData.verticalAlignment = GridData.FILL;
                gridData.grabExcessVerticalSpace = true;

                int i = 1;
                for (CNLSynonym synonym : synonyms)
                {
                    Label categoryLabel = new Label(this.synonymsGroup, SWT.NONE);
                    categoryLabel.setLayoutData(gridData);

                    categoryLabel.setText(i + ". Category: "
                            + POSUtil.getInstance().explainTaggedTerm(synonym.getPartsOfSpeech()));

                    String terms = "            ";

                    for (String word : synonym.getSynonyms())
                    {
                        if (!terms.trim().equals(""))
                        {
                            terms = terms + ", " + word;
                        }
                        else
                        {
                            terms = terms + "Synonyms: " + word;
                        }
                    }

                    Label termsLabel = new Label(this.synonymsGroup, SWT.NONE);
                    categoryLabel.setLayoutData(gridData);

                    termsLabel.setText(terms);

                    if (i < synonyms.size())
                    {
                        Label blankLine = new Label(this.synonymsGroup, SWT.NONE);
                        blankLine.setLayoutData(gridData);
                        blankLine.setText("");
                    }
                    i++;
                }

                this.synonymsGroup.pack();
                this.synonymsGroup.layout();
                
                this.synonymsComposite.pack();
                this.synonymsComposite.layout();
            }
        }
    }

    private void setSynonymsCompositeVisible(boolean visible)
    {
        this.synonymsComposite.setVisible(visible);

        ((GridData) this.synonymsComposite.getLayoutData()).exclude = !visible;
        
    }

    private void populateUnknownTermsCombo(
            HashMap<TestCaseTextType, Collection<String>> lexiconFaults)
    {
        this.unknownTermsCombo.add("<New Term>");

        if (CNLView.getView().getFilter().isShowActions())
        {
            List<String> orderUnknownTerms = this.orderUnknownTerms(lexiconFaults.get(TestCaseTextType.ACTION));
            
            if(orderUnknownTerms != null)
            {
                for (String term : orderUnknownTerms)
                {
                    if(!Arrays.asList(this.unknownTermsCombo.getItems()).contains(term))
                    {
                        this.unknownTermsCombo.add(term);
                    }
                }
            }
        }

        if (CNLView.getView().getFilter().isShowConditions())
        {
            List<String> orderUnknownTerms = this.orderUnknownTerms(lexiconFaults.get(TestCaseTextType.CONDITION));
            
            if(orderUnknownTerms != null)
            {
                for (String term : orderUnknownTerms)
                {
                    if(!Arrays.asList(this.unknownTermsCombo.getItems()).contains(term))
                    {
                        this.unknownTermsCombo.add(term);
                    }
                }
            }
        }

        if (CNLView.getView().getFilter().isShowResponses())
        {
            List<String> orderUnknownTerms = this.orderUnknownTerms(lexiconFaults.get(TestCaseTextType.RESPONSE));
            
            if(orderUnknownTerms != null)
            {
                for (String term : orderUnknownTerms)
                {
                    if(!Arrays.asList(this.unknownTermsCombo.getItems()).contains(term))
                    {
                        this.unknownTermsCombo.add(term);
                    }
                }
            }
        }
    }

    private List<String> orderUnknownTerms(Collection<String> terms)
    {
        List<String> result = null;
        
        if(terms != null)
        {
            String[] array = terms.toArray(new String[0]);
            
            Arrays.sort(array);
            
            result = Arrays.asList(array);
        }

        return result;
    }

    private void fillBundle(HashMap<String, Widget> bundle)
    {
        Text defaultText = (Text) bundle.get("@default");

        int index = this.unknownTermsCombo.getSelectionIndex();

        if (index > 0)
        {
            defaultText.setText(this.unknownTermsCombo.getItem(index));
        }
        else
        {
            defaultText.setText("");
        }

        for (String key : bundle.keySet())
        {
            if (bundle.get(key) != defaultText && bundle.get(key) instanceof Text)
            {
                Text text = (Text) bundle.get(key);
                text.setText("");

                text.addListener(SWT.KeyUp, this.emptyListener);
            }
        }

        defaultText.addListener(SWT.KeyUp, this.emptyListener);
    }

    

    private void displayTermAddedDialog()
    {
        MessageDialog.openInformation(this.getParent(), "Term added",
                "The new term was added successfully!");
    }

    protected void changeVisibility(Shell shell)
    {
        this.setBundleVisibility(this.nounBundle, false);
        this.setBundleVisibility(this.adjectiveBundle, false);
        this.setBundleVisibility(this.verbBundle, false);
        this.setBundleVisibility(this.adverbBundle, false);
        this.setBundleVisibility(this.prepositionBundle, false);
        this.setBundleVisibility(this.conjunctionBundle, false);
        this.setBundleVisibility(this.determinerBundle, false);

        int selectedIndex = this.termTypeCombo.getSelectionIndex();

        if (selectedIndex > -1)
        {
            String selectedTerm = this.termTypeCombo.getItem(selectedIndex);

            if (selectedTerm.equals(NOUN))
            {
                this.setBundleVisibility(this.nounBundle, true);
                this.fillBundle(this.nounBundle);
            }
            else if (selectedTerm.equals(VERB))
            {
                this.setBundleVisibility(this.verbBundle, true);
                this.fillBundle(this.verbBundle);
            }
            else if (selectedTerm.equals(ADJECTIVE))
            {
                this.setBundleVisibility(this.adjectiveBundle, true);
                this.fillBundle(this.adjectiveBundle);
            }
            else if (selectedTerm.equals(ADVERB))
            {
                this.setBundleVisibility(this.adverbBundle, true);
                this.fillBundle(this.adverbBundle);
            }
            else if (selectedTerm.equals(PREPOSITION))
            {
                this.setBundleVisibility(this.prepositionBundle, true);
                this.fillBundle(this.prepositionBundle);
            }
            else if (selectedTerm.equals(CONJUNCTION))
            {
                this.setBundleVisibility(this.conjunctionBundle, true);
                this.fillBundle(this.conjunctionBundle);
            }
            else if (selectedTerm.equals(DETERMINER))
            {
                this.setBundleVisibility(this.determinerBundle, true);
                this.fillBundle(this.determinerBundle);
            }
        }
        
        fillSynonymes();

        shell.layout();
        shell.pack();
    }
    
    
    protected String getAction()
    {
        return "adding";
    }
    
    
    protected void warning()
    {
        if (someFieldIsEmpty() || someFieldContainsModalVerbs() || someFieldContainsDOVerb())
        {
            this.addButton.setEnabled(false);
        }
        else
        {
            this.addButton.setEnabled(true);
        }
        
        super.warning();
    }
}
