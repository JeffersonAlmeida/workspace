/*
 * @(#)EditRemoveLexicalTermDialog.java
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
 * fsf2     Sep 22, 2009                Initial creation.
 */
package br.ufpe.cin.target.cnl.dialogs;

import java.util.HashMap;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
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
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;
import br.ufpe.cin.target.cnl.Activator;
import br.ufpe.cin.target.cnl.actions.ReloadBasesActionDelegate;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.controller.CNLProperties;
import br.ufpe.cin.target.cnl.views.CNLView;


//INSPECT: Felype - Alterações na classe toda. Mais tipos de termos, mensagens de erro, etc.
public class EditRemoveLexicalTermDialog extends LexicalDialog
{
    private Shell shell;

    private static final String SAVE = "Save";

    private static final String CANCEL = "Cancel";

    private static final String REMOVE = "Remove";

    private static final String EDIT = "Edit";

    private LexicalTermsSort tableSorter;

    private TableViewer viewer;

    private Button editSaveTermButton;

    private Button removeCancelTermButton;

    private LexicalFilter filter;

    private Text searchField;

    private LexicalEntry lastSelectedTermToEdit;

    private Label termTypeLabel;

    private Composite warningComposite;

    public EditRemoveLexicalTermDialog(Shell parentShell)
    {
        super(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

        this.setText("Edit/Remove Lexical Term");
    }

    public void open()
    {
        // Create the dialog window
        this.shell = new Shell(getParent(), getStyle());
        this.shell.setText(getText());
        this.shell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                "icons/edit_term_icon_16_16.png").createImage());
        createContents(this.shell);
        this.shell.pack();
        this.shell.open();
        Display display = getParent().getDisplay();

        while (!this.shell.isDisposed())
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

            this.viewer.setInput(CNLPluginController.getInstance().getVocabulary());
        }
        catch (Exception e)
        {
            MessageDialog.openError(this.getParent(), "Error reloading bases", e.getMessage());
            e.printStackTrace();
        }
    }

    private void createContents(final Shell shell)
    {
        shell.setLayout(new GridLayout(1, true));
        shell.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));

        this.createEditAndRemoveTerms(shell);
    }

    /**
     * Comment for method.
     * 
     * @param lexiconFaults
     * @param tabfolder
     */
    private void createEditAndRemoveTerms(final Shell shell)
    {
        final Composite compositeEditRemoveTerm = this.createComposite(shell, 2, true);

        Group searchGroup = this.createGroup(compositeEditRemoveTerm, 1, true, "Search");
        searchGroup.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false, 2, 1));

        this.searchField = new Text(searchGroup, SWT.SINGLE | SWT.BORDER);
        this.searchField
                .setLayoutData(new GridData(GridData.FILL, GridData.BEGINNING, true, false));

        this.searchField.addKeyListener(new KeyListener()
        {

            
            public void keyPressed(KeyEvent e)
            {

            }

            
            public void keyReleased(KeyEvent e)
            {
                filterTable(searchField.getText());
            }

        });

        initBundles(shell);
        
        this.addListener();
        
        this.warningComposite = this.createComposite(shell, 2, true);
        
        GridData data = new GridData(SWT.CENTER, SWT.TOP, true, true);
        this.warningComposite.setLayoutData(data);
        
        data = new GridData(SWT.LEFT, SWT.TOP, true, true);
        this.verbWarningLabel = new Label(this.warningComposite, SWT.WRAP);
        this.verbWarningLabel.setLayoutData(data);
        this.verbWarningLabel.setText("You can not add modal verbs.");
        this.verbWarningLabel.setForeground(new Color(this.verbWarningLabel.getDisplay(), ERROR_COLOR));
        
        data = new GridData(SWT.RIGHT, SWT.TOP, true, true);
        this.warningLabel = new Label(this.warningComposite, SWT.WRAP);
        this.warningLabel.setLayoutData(data);
        this.warningLabel.setText("Some required field is empty.");
        this.warningLabel.setForeground(new Color(this.warningLabel.getDisplay(), ERROR_COLOR));

        this.createTermsTable(compositeEditRemoveTerm);
        this.createTermTypeCombo(compositeEditRemoveTerm, shell);
        
        ((GridData) this.termTypeLabel.getLayoutData()).exclude = true;
        ((GridData) this.termTypeCombo.getLayoutData()).exclude = true;

        data = new GridData(90, SWT.DEFAULT);
        data.horizontalAlignment = SWT.END;

        Composite compositeEditAndRemoveButtons = this.createComposite(shell, 2, true); 
        compositeEditAndRemoveButtons.setLayoutData(new GridData(GridData.CENTER, GridData.FILL, true, true, 10, 1));
        
        this.editSaveTermButton = new Button(compositeEditAndRemoveButtons, SWT.PUSH);
        this.editSaveTermButton.setText(EDIT);
        this.editSaveTermButton.setLayoutData(data);

        data = new GridData(90, SWT.DEFAULT);
        data.horizontalAlignment = SWT.BEGINNING;

        this.removeCancelTermButton = new Button(compositeEditAndRemoveButtons, SWT.PUSH);
        this.removeCancelTermButton.setText(REMOVE);
        this.removeCancelTermButton.setLayoutData(data);

        this.removeCancelTermButton.addSelectionListener(new SelectionListener()
        {
            
            public void widgetSelected(SelectionEvent e)
            {
                try
                {
                    if (removeCancelTermButton.getText().equals(REMOVE))
                    {
                        removeWordFromLexicon();
                    }
                    else if (removeCancelTermButton.getText().equals(CANCEL))
                    {
                        viewer.getTable().deselectAll();

                        editSaveTermButton.setText(EDIT);
                        removeCancelTermButton.setText(REMOVE);
                        
                        viewer.getTable().setEnabled(true);
                        editSaveTermButton.setEnabled(true);
                        
                        termTypeLabel.setVisible(false);
                        termTypeCombo.setVisible(false);
                        warningLabel.setVisible(false);
                        verbWarningLabel.setVisible(false);

                        changeVisibility(shell);
                        
                        ((GridData) termTypeLabel.getLayoutData()).exclude = true;
                        ((GridData) termTypeCombo.getLayoutData()).exclude = true;

                        shell.redraw();
                        shell.layout();
                        shell.pack();
                    }
                }
                catch (RepositoryException e1)
                {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            }

            
            public void widgetDefaultSelected(SelectionEvent e)
            {
                // TODO Auto-generated method stub
            }
        });

        this.editSaveTermButton.addSelectionListener(new SelectionListener()
        {
            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                if (editSaveTermButton.getText().equals(EDIT))
                {
                    TableItem[] selection = viewer.getTable().getSelection();

                    if (selection.length > 1)
                    {
                        MessageDialog.openError(shell, "Error Editing a Term", "Select only one term for edition.");
                    }
                    //There is only one term selected
                    else if (selection.length == 1)
                    {
                        LexicalEntry selectedTerm = (LexicalEntry) selection[0].getData();

                        fillBundle(selectedTerm);

                        editSaveTermButton.setText(SAVE);
                        removeCancelTermButton.setText(CANCEL);
                        viewer.getTable().setEnabled(false);

                        termTypeCombo.setVisible(true);
                        termTypeLabel.setVisible(true);

                        setTermTypeComboSelection(selectedTerm);
                        
                        warning();

                        changeVisibility(shell);
                    }
                    //no terms selected
                    else
                    {
                        MessageDialog.openError(shell, "Error Editing a Term", "Select at least one term for edition.");
                    }
                    
                    shell.redraw();
                    shell.layout();
                    shell.pack();
                }
                else if (editSaveTermButton.getText().equals(SAVE))
                {
                    boolean updated = editItem(shell);

                    if (updated)
                    {
                        editSaveTermButton.setText(EDIT);
                        removeCancelTermButton.setText(REMOVE);
                        viewer.getTable().setEnabled(true);
                    }
                }
            }
        });

        try
        {
            this.viewer.setInput(CNLPluginController.getInstance().getVocabulary());
        }
        catch (RepositoryException e)
        {
            MessageDialog.openError(this.getParent(), "Error reloading bases", e.getMessage());
            e.printStackTrace();
        }

        this.warningLabel.setVisible(false);
        this.verbWarningLabel.setVisible(false);

        this.changeVisibility(shell);

        shell.redraw();
        shell.layout();
        shell.pack();
    }

    /**
     * Comment for method.
     */
    private void addListener()
    {
        this.emptyListener = new Listener()
        {
            
            public void handleEvent(Event event)
            {
                warning();
            }
        };
        
        ((Text) this.nounBundle.get("Singular")).addListener(SWT.KeyUp, this.emptyListener);
        ((Text) this.nounBundle.get("Plural")).addListener(SWT.KeyUp, this.emptyListener);
        
        ((Text) this.verbBundle.get("Infinitive")).addListener(SWT.KeyUp,
                this.emptyListener);
        ((Text) this.verbBundle.get("Past")).addListener(SWT.KeyUp, this.emptyListener);
        ((Text) this.verbBundle.get("Past Participle")).addListener(SWT.KeyUp,
                this.emptyListener);
        ((Text) this.verbBundle.get("Gerund")).addListener(SWT.KeyUp, this.emptyListener);
        ((Text) this.verbBundle.get("Third Person")).addListener(SWT.KeyUp,
                this.emptyListener);
        
        ((Text) this.adjectiveBundle.get("Adjective")).addListener(SWT.KeyUp,
                this.emptyListener);
        
        ((Text) this.adverbBundle.get("Adverb")).addListener(SWT.KeyUp, this.emptyListener);
        
        ((Text) this.prepositionBundle.get("Preposition")).addListener(SWT.KeyUp,
                this.emptyListener);
        
        ((Text) this.conjunctionBundle.get("Conjunction")).addListener(SWT.KeyUp,
                this.emptyListener);
        
        ((Text) this.determinerBundle.get("Determiner")).addListener(SWT.KeyUp,
                this.emptyListener);
    }

    /**
     * Comment for method.
     */
    
    protected void warning()
    {
        if (someFieldIsEmpty() || someFieldContainsModalVerbs() || someFieldContainsDOVerb())
        {
            this.editSaveTermButton.setEnabled(false);
        }
        else
        {
            this.editSaveTermButton.setEnabled(true);
        }
        
        super.warning();
        
        shell.redraw();
        shell.layout();
        shell.pack();
    }

    /**
     * Comment for method.
     * 
     * @param compositeEditRemoveTerm
     */
    private void createTermTypeCombo(final Composite compositeEditRemoveTerm, final Shell shell)
    {
        GridData data = new GridData(SWT.RIGHT, SWT.CENTER, true, true);
        
        this.termTypeLabel = new Label(compositeEditRemoveTerm, SWT.NONE);
        this.termTypeLabel.setText("New Term Type:");
        data.verticalIndent = 20;
        this.termTypeLabel.setLayoutData(data);

        this.termTypeCombo = new Combo(compositeEditRemoveTerm, SWT.DROP_DOWN | SWT.READ_ONLY);
        data = new GridData(80, SWT.DEFAULT);
        data.horizontalAlignment = SWT.LEFT;
        data.verticalIndent = 20;

        String[] terms = CNLProperties.getInstance().getProperty("cnl_terms").split(",");
        terms = this.orderTerms(terms);

        for (String term : terms)
        {
            this.termTypeCombo.add(term);
        }

        this.termTypeCombo.setLayoutData(data);

        this.termTypeCombo.setVisible(false);
        this.termTypeLabel.setVisible(false);

        this.termTypeCombo.addSelectionListener(new SelectionListener()
        {
            
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            
            public void widgetSelected(SelectionEvent e)
            {
                changeActiveBundle(shell);
            }
        });
    }

    /**
     * Comment for method.
     */
    protected void changeActiveBundle(Shell shell)
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
                
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.nounBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.NN))
                {
                    Text defaultText = (Text) this.nounBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            else if (selectedTerm.equals(VERB))
            {
                this.setBundleVisibility(this.verbBundle, true);
                   
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.verbBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.VB))
                {
                    Text defaultText = (Text) this.verbBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            else if (selectedTerm.equals(ADJECTIVE))
            {
                this.setBundleVisibility(this.adjectiveBundle, true);
                
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.adjectiveBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.JJ))
                {
                    Text defaultText = (Text) this.adjectiveBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            else if (selectedTerm.equals(ADVERB))
            {
                this.setBundleVisibility(this.adverbBundle, true);
                
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.adverbBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.ADV))
                {
                    Text defaultText = (Text) this.adverbBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            else if (selectedTerm.equals(PREPOSITION))
            {
                this.setBundleVisibility(this.prepositionBundle, true);
                
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.prepositionBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.PP))
                {
                    Text defaultText = (Text) this.prepositionBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            else if (selectedTerm.equals(CONJUNCTION))
            {
                this.setBundleVisibility(this.conjunctionBundle, true);
                
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.conjunctionBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.CJ))
                {
                    Text defaultText = (Text) this.conjunctionBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            else if (selectedTerm.equals(DETERMINER))
            {
                this.setBundleVisibility(this.determinerBundle, true);
                
                //clears the text content of the bundle fields  
                this.clearBundleContents(this.determinerBundle);

                if (!this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.DT))
                {
                    Text defaultText = (Text) this.determinerBundle.get("@default");

                    defaultText.setText(POSUtil.getInstance().getDefaultTerm(
                            this.lastSelectedTermToEdit));
                }
                //fills the bundle with the last selected term contents
                else
                {
                    fillBundle(this.lastSelectedTermToEdit);
                }
            }
            
            warning();
        }

        shell.layout();
        shell.pack();
    }

    /**
     * Comment for method.
     * 
     * @param selectedTerm
     */
    protected void setTermTypeComboSelection(LexicalEntry selectedTerm)
    {
        if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.NN))
        {
            this.selectTermTypeCombo(NOUN);
        }
        else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.VB))
        {
            this.selectTermTypeCombo(VERB);
        }
        else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.JJ))
        {
            this.selectTermTypeCombo(ADJECTIVE);
        }
        else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.ADV))
        {
            this.selectTermTypeCombo(ADVERB);
        }
        else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.PP))
        {
            this.selectTermTypeCombo(PREPOSITION);
        }
        else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.CJ))
        {
            this.selectTermTypeCombo(CONJUNCTION);
        }
        else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.DT))
        {
            this.selectTermTypeCombo(DETERMINER);
        }
    }

    /**
     * Comment for method.
     * 
     * @param noun
     */
    private void selectTermTypeCombo(String category)
    {
        String[] items = this.termTypeCombo.getItems();

        for (int i = 0; i < items.length; i++)
        {
            if (items[i].equals(category))
            {
                this.termTypeCombo.select(i);
            }
        }
    }

    /**
     * Comment for method.
     * 
     * @param text
     */
    protected void filterTable(String text)
    {
        this.filter.setString(text);
        this.viewer.refresh();
    }

    /**
     * Comment for method.
     * @return 
     * 
     * @throws RepositoryException
     */
    private void removeWordFromLexicon() throws RepositoryException
    {
        TableItem[] selection = this.viewer.getTable().getSelection();
        
        Vector<LexicalEntry> lexicalCollection = new Vector<LexicalEntry>();

        LexicalEntry lexicon = null;
        
        String spaces = "       ";
        
        String terms = "";

        for (TableItem item : selection)
        {
            lexicon = (LexicalEntry) item.getData();
            
            lexicalCollection.add(lexicon);
            
            terms += spaces + lexicon.toString();
        }
        
        boolean remove = MessageDialog.openQuestion(this.getParent(),
                "Remove confirmation", "Are you sure you want to remove the following term" 
                + ((lexicalCollection.size() > 1) ? "s" : "") + "?\n\n" + terms);
        
        if(remove)
        {
            for(LexicalEntry lEntry : lexicalCollection)
            {
                CNLPluginController.getInstance().removeTerm(lEntry);
            }
            
            this.refreshContents();
            
            MessageDialog.openInformation(this.getParent(), ((lexicalCollection.size() > 1) ? "Terms" : "Term") + "", ((lexicalCollection.size() > 1) ? "Terms" : "Term") + " removed successfully.");
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void removeToUpdateWordFromLexicon() throws RepositoryException
    {
        CNLPluginController.getInstance().removeTerm(this.lastSelectedTermToEdit);
    }

    /**
     * Comment for method.
     * 
     * @param compositeEditRemoveTerm
     */
    private void createTermsTable(Composite compositeEditRemoveTerm)
    {
        this.viewer = this.createTable(compositeEditRemoveTerm); // creates the table
    }

    private TableViewer createTable(Composite compositeEditRemoveTerm)
    {
        Group group = this.createGroup(compositeEditRemoveTerm, 1, true, "Vocabulary");

        this.tableSorter = new LexicalTermsSort();

        this.filter = new LexicalFilter();

        TableViewer result = new TableViewer(new Table(group, SWT.BORDER | SWT.MULTI | SWT.H_SCROLL
                | SWT.FULL_SELECTION));

        Table table = result.getTable();
        table.setBounds(0, 0, 100, 100);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        GridData gridData = new GridData(275, 100);// gridData = new GridData(SWT.FILL, SWT.TOP,
        // true, false);
        gridData.grabExcessHorizontalSpace = true;
        table.setLayoutData(gridData);

        GridData data = new GridData(SWT.FILL, SWT.TOP, true, false, 2, 1);
        data.minimumHeight = 100;
        group.setLayoutData(data);

        createColumns(table); // creates the table columns

        /* sets a viewer content provider */
        result.setContentProvider(new ArrayContentProvider());

        /* sets a viewer label provider */
        result.setLabelProvider(new LexicalTemsLabelProvider());

        result.setSorter(this.tableSorter);

        result.addFilter(this.filter);

        return result;
    }

    private void fillBundle(LexicalEntry lexicalEntry)
    {
        TableItem[] selection = this.viewer.getTable().getSelection();

        if (selection.length == 1)
        {
            ((GridData) this.termTypeLabel.getLayoutData()).exclude = false;
            ((GridData) this.termTypeCombo.getLayoutData()).exclude = false;
            this.termTypeLabel.setVisible(true);
            this.termTypeCombo.setVisible(true);
            
            this.lastSelectedTermToEdit = (LexicalEntry) selection[0].getData();

            if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.NN))
            {
                ((Text) this.nounBundle.get("Singular")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.NN));
                ((Text) this.nounBundle.get("Plural")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.NNS));
            }
            else if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.VB))
            {
                ((Text) this.verbBundle.get("Infinitive")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.VB));
                ((Text) this.verbBundle.get("Past")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.VBD));
                ((Text) this.verbBundle.get("Past Participle")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.VBN));
                ((Text) this.verbBundle.get("Gerund")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.VBG));
                ((Text) this.verbBundle.get("Third Person")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.VBZ));
            }
            else if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.JJ))
            {
                ((Text) this.adjectiveBundle.get("Adjective")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.JJ));
            }
            else if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.ADV))
            {
                ((Text) this.adverbBundle.get("Adverb")).setText(this.lastSelectedTermToEdit
                        .getTerm(PartsOfSpeech.ADV));
            }
            else if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.PP))
            {
                ((Text) this.prepositionBundle.get("Preposition"))
                        .setText(this.lastSelectedTermToEdit.getTerm(PartsOfSpeech.PP));
            }
            else if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.CJ))
            {
                ((Text) this.conjunctionBundle.get("Conjunction"))
                        .setText(this.lastSelectedTermToEdit.getTerm(PartsOfSpeech.CJ));
            }
            else if (this.lastSelectedTermToEdit.getAvailablePOSTags().contains(PartsOfSpeech.DT))
            {
                ((Text) this.determinerBundle.get("Determiner"))
                        .setText(this.lastSelectedTermToEdit.getTerm(PartsOfSpeech.DT));
            }
        }
    }

    /**
     * Comment for method.
     * 
     * @param shell2
     * @return
     */
    protected boolean editItem(Shell shell)
    {
        boolean updated = false;

        try
        {
            this.removeToUpdateWordFromLexicon();
            //reloads CNL Lexicon
            CNLPluginController.getInstance().startController();

            updated = this.addNewTerm(shell);

            if (!updated)
            {
                this.addLexicalTerm(this.lastSelectedTermToEdit, true);
                
                //reloads CNL Lexicon
                CNLPluginController.getInstance().startController();
            }
            else
            {
                this.termTypeLabel.setVisible(false);
                ((GridData) this.termTypeLabel.getLayoutData()).exclude = true;
                
                this.termTypeCombo.setVisible(false);
                ((GridData) this.termTypeCombo.getLayoutData()).exclude = true;
                
                this.warningLabel.setVisible(false);
                
                this.verbWarningLabel.setVisible(false);
                
                this.setBundleVisibility(this.nounBundle, false);
                this.setBundleVisibility(this.adjectiveBundle, false);
                this.setBundleVisibility(this.verbBundle, false);
                this.setBundleVisibility(this.adverbBundle, false);
                this.setBundleVisibility(this.prepositionBundle, false);
                this.setBundleVisibility(this.conjunctionBundle, false);
                this.setBundleVisibility(this.determinerBundle, false);
                
                shell.redraw();
                shell.layout();
                shell.pack();
                
                MessageDialog.openInformation(this.getParent(), "Term updated",
                        "The new term was updated successfully!");
            }
        }
        catch (RepositoryException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        catch (CNLException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return updated;
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

        TableItem[] selection = this.viewer.getTable().getSelection();

        if (selection.length == 1)
        {
            LexicalEntry selectedTerm = (LexicalEntry) selection[0].getData();
            
            this.setTermTypeComboSelection(selectedTerm);

            if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.NN))
            {
                this.setBundleVisibility(this.nounBundle, true);
            }
            else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.VB))
            {
                this.setBundleVisibility(this.verbBundle, true);
            }
            else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.JJ))
            {
                this.setBundleVisibility(this.adjectiveBundle, true);
            }
            else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.ADV))
            {
                this.setBundleVisibility(this.adverbBundle, true);
            }
            else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.PP))
            {
                this.setBundleVisibility(this.prepositionBundle, true);
            }
            else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.CJ))
            {
                this.setBundleVisibility(this.conjunctionBundle, true);
            }
            else if (selectedTerm.getAvailablePOSTags().contains(PartsOfSpeech.DT))
            {
                this.setBundleVisibility(this.determinerBundle, true);
            }

            this.fillBundle(selectedTerm);
        }

        shell.layout();
        shell.pack();
        shell.redraw();
    }

    /**
     * Create the table columns and set their names.
     * 
     * @param table The table where the columns will be created.
     */
    private void createColumns(final Table table)
    {
        int columnsWidth = 135;

        final TableColumn[] columns = new TableColumn[2];
        columns[0] = new TableColumn(table, SWT.NONE);
        columns[1] = new TableColumn(table, SWT.NONE);

        columns[0].setText("Term");
        columns[1].setText("Category");

        columns[0].setWidth(columnsWidth);
        columns[1].setWidth(columnsWidth);

        for (int i = 0; i < columns.length; i++)
        {
            final int j = i;
            columns[i].addSelectionListener(new SelectionAdapter()
            {
                
                public void widgetSelected(SelectionEvent e)
                {
                    tableSorter.setColumn(j);
                    int dir = viewer.getTable().getSortDirection();
                    if (viewer.getTable().getSortColumn() == columns[j])
                    {
                        dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
                    }
                    else
                    {

                        dir = SWT.DOWN;
                    }
                    viewer.getTable().setSortDirection(dir);
                    viewer.getTable().setSortColumn(columns[j]);
                    viewer.refresh();
                }
            });
        }
    }

    protected void setBundleVisibility(HashMap<String, Widget> bundle, boolean visible)
    {
        Group group = ((Group) bundle.get("@group"));

        group.setVisible(visible);

        group.setLayoutData(new GridData(SWT.CENTER, SWT.TOP, false, false));

        ((GridData) group.getLayoutData()).exclude = !visible;
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.target.cnl.dialogs.LexicalDialog#getAction()
     */
    
    protected String getAction()
    {
        return "updating";
    }
}
