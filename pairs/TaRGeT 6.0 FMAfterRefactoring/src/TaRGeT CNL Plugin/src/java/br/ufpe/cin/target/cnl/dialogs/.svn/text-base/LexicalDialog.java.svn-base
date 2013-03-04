/*
 * @(#)LexicalDialog.java
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
 * wxx###   24/09/2009    LIBhh00000   Initial creation.
 */
package br.ufpe.cin.target.cnl.dialogs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.util.UtilNLP;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdjectiveTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdverbTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.DeterminerTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;
import br.ufpe.cin.cnlframework.vocabulary.terms.NounTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.PrepositionTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.controller.CNLProperties;
import br.ufpe.cin.target.cnl.exceptions.DuplicatedTermInLexiconException;
import br.ufpe.cin.target.cnl.exceptions.InvalidEntryInDictionaryException;


/**
 * Abstract class that defines the structure and common behavior of CNL Dialogs used to manage the lexicon repository.
 *
 */
public abstract class LexicalDialog extends Dialog
{
    protected HashMap<String, Widget> nounBundle;

    protected HashMap<String, Widget> verbBundle;

    protected HashMap<String, Widget> adjectiveBundle;

    protected HashMap<String, Widget> adverbBundle;

    protected HashMap<String, Widget> prepositionBundle;

    protected HashMap<String, Widget> conjunctionBundle;

    protected HashMap<String, Widget> determinerBundle;
    
    protected Label warningLabel;
    
    protected static final String NOUN = "Noun";

    protected static final String VERB = "Verb";

    protected static final String ADJECTIVE = "Adjective";

    protected static final String ADVERB = "Adverb";

    protected static final String PREPOSITION = "Preposition";

    protected static final String CONJUNCTION = "Conjunction";
    
    protected static final String DETERMINER = "Determiner";
    
    protected static final RGB ERROR_COLOR = new RGB(255,0,0);
   
    protected Combo termTypeCombo;
    
    protected Listener emptyListener;
    
    protected abstract void refreshContents();
    
    protected abstract String getAction();
    
    protected abstract void changeVisibility(Shell shell);
    
    protected Label verbWarningLabel;
    
    /**
     * Constructor.
     * 
     * @param parent
     */
    public LexicalDialog(Shell parent)
    {
        super(parent);
        // TODO Auto-generated constructor stub
    }

    /**
     * Constructor.
     * 
     * @param parentShell
     * @param i
     */
    public LexicalDialog(Shell parentShell, int i)
    {
        super(parentShell, i);
    }
    
    /**
     * Comment for method.
     * 
     * @param terms
     */
    protected String[] orderTerms(String[] terms)
    {
        String[] result = new String[terms.length];

        Vector<String> termsVector = new Vector<String>();

        for (String term : terms)
        {
            termsVector.add(term);
        }

        Collections.sort(termsVector);

        return termsVector.toArray(result);
    }
    
    protected Group createGroup(Composite parent, int numberOfColumns,
            boolean hasColumnsSameSize, String text)
    {
        GridLayout groupLayout = new GridLayout(numberOfColumns, hasColumnsSameSize);
        groupLayout.verticalSpacing = 0;

        Group group = new Group(parent, SWT.NULL);
        group.setLayout(groupLayout);
        group.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
        group.setText(text);

        return group;
    }
    
    protected HashMap<String, Widget> createGroup(Composite parent, String groupName,
            String[] fieldNames, String defaultField)
    {
        HashMap<String, Widget> groupBundle = new HashMap<String, Widget>();
        Group group = new Group(parent, SWT.SHADOW_NONE);
        group.setText(groupName);
        GridLayout layout = new GridLayout(2, false);
        GridData data = new GridData();
        data.horizontalSpan = 2;
        group.setLayoutData(data);
        group.setLayout(layout);

        for (String fieldName : fieldNames)
        {
            Label label = new Label(group, SWT.NONE);
            label.setText(fieldName + ":");
            data = new GridData();
            label.setLayoutData(data);

            Text text = new Text(group, SWT.SINGLE);
            data = new GridData();
            data.widthHint = 200;
            text.setLayoutData(data);
            groupBundle.put(fieldName, text);
            if (fieldName.equals(defaultField))
            {
                groupBundle.put("@default", text);
            }
        }

        groupBundle.put("@group", group);

        return groupBundle;
    }
    
    protected void setBundleVisibility(HashMap<String, Widget> bundle, boolean visible)
    {
        Group group = ((Group) bundle.get("@group"));

        group.setVisible(visible);

        ((GridData) group.getLayoutData()).exclude = !visible;
    }
    
    protected String changeIfEmpty(String str1, String str2)
    {
        return (str1.length() == 0) ? str2 : str1;
    }
    
    protected boolean addVerbTerm(Shell shell)
    {
        boolean result = false;
        
        String infinitive = ((Text) this.verbBundle.get("Infinitive")).getText().trim();
        String past = ((Text) this.verbBundle.get("Past")).getText().trim();
        String pastParticiple = ((Text) this.verbBundle.get("Past Participle")).getText().trim();
        String gerund = ((Text) this.verbBundle.get("Gerund")).getText().trim();
        String thirdPerson = ((Text) this.verbBundle.get("Third Person")).getText().trim();

        if (infinitive.length() > 0)
        {
            VerbTerm verbTerm = new VerbTerm(infinitive, this.changeIfEmpty(past, UtilNLP
                    .getPast(infinitive)), this.changeIfEmpty(pastParticiple, UtilNLP
                    .getPastParticiple(infinitive)), this.changeIfEmpty(gerund, UtilNLP
                    .getGerund(infinitive)), this.changeIfEmpty(thirdPerson, UtilNLP
                    .getThirdPerson(infinitive)), infinitive);

            result = this.addLexicalTerm(verbTerm, false);

            if (result)
            {
                this.refreshContents();
                System.out.println("Verb Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform at least the infinitive term.");
        }
        
        return result;
    }

    protected boolean addAdjectiveTerm(Shell shell)
    {
        boolean result = false;
        
        String adjective = ((Text) this.adjectiveBundle.get("Adjective")).getText().trim();

        if (adjective.length() > 0)
        {
            AdjectiveTerm adjectiveTerm = new AdjectiveTerm(adjective);
            result = this.addLexicalTerm(adjectiveTerm, false);

            if (result)
            {
                this.refreshContents();
                System.out.println("Adjective Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform the adjective term.");
        }
        
        return result;
    }
    
    /**
     * Comment for method.
     * @param shell
     */
    protected void initBundles(final Shell shell)
    {
        this.nounBundle = this.createGroup(shell, NOUN, new String[] { "Singular", "Plural" },
                "Singular");

        this.adjectiveBundle = this.createGroup(shell, ADJECTIVE, new String[] { "Adjective" },
                "Adjective");

        this.verbBundle = this.createGroup(shell, VERB, new String[] { "Infinitive", "Past",
                "Past Participle", "Gerund", "Third Person" }, "Infinitive");

        this.adverbBundle = this.createGroup(shell, ADVERB, new String[] { "Adverb" }, "Adverb");

        this.prepositionBundle = this.createGroup(shell, PREPOSITION,
                new String[] { "Preposition" }, "Preposition");

        this.conjunctionBundle = this.createGroup(shell, CONJUNCTION,
                new String[] { "Conjunction" }, "Conjunction");
        
        this.determinerBundle = this.createGroup(shell, DETERMINER,
                new String[] { "Determiner" }, "Determiner");
    }
    
    protected boolean addNounTerm(Shell shell)
    {
        boolean result = false;
        
        String singular = ((Text) this.nounBundle.get("Singular")).getText().trim();
        String plural = ((Text) this.nounBundle.get("Plural")).getText().trim();

        if (singular.length() > 0)
        {
            NounTerm nounTerm = new NounTerm(singular, this.changeIfEmpty(plural, UtilNLP
                    .getPluralForm(singular)));
            result = this.addLexicalTerm(nounTerm, false);
            if (result)
            {
                this.refreshContents();
                System.out.println("Noun Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform at least the singular term.");
        }
        
        return result;
    }
    

    /**
     * Comment for method.
     * @param bundle
     */
    protected boolean isEmpty(HashMap<String, Widget> bundle)
    {
        boolean result = false;
        
        Text defaultText = (Text) bundle.get("@default");
        
        if(defaultText.getText().trim().equals(""))
        {
            result = true;
        }

        for (String key : bundle.keySet())
        {
            if (bundle.get(key) != defaultText && bundle.get(key) instanceof Text)
            {
                Text text = (Text) bundle.get(key);

                if(text.getText().trim().equals(""))
                {
                    result = true;
                }
            }
        }
        
        return result;
    }
    
    /**
     * Comment for method.
     * @param shell
     * @return 
     */
    protected boolean addDeterminerTerm(Shell shell)
    {
        boolean result = false;
        
        String determiner = ((Text) this.determinerBundle.get("Determiner")).getText().trim();

        if (determiner.length() > 0)
        {
            DeterminerTerm determinerTerm = new DeterminerTerm(determiner);
            result = this.addLexicalTerm(determinerTerm, false);

            if (result)
            {
                this.refreshContents();
                System.out.println("Determiner Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform the determiner term.");
        }
        
        return result;
    }

    /**
     * Comment for method.
     * 
     * @param shell
     * @return 
     */
    protected boolean addConjunctionTerm(Shell shell)
    {
        boolean result = false;
        
        String conjunction = ((Text) this.conjunctionBundle.get("Conjunction")).getText().trim();

        if (conjunction.length() > 0)
        {
            LexicalEntry conjunctionTerm = new LexicalEntry(PartsOfSpeech.CJ, conjunction);
            result = this.addLexicalTerm(conjunctionTerm, false);

            if (result)
            {
                this.refreshContents();
                System.out.println("Conjunction Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform the conjunction term.");
        }
        
        return result;
    }

    /**
     * Comment for method.
     * 
     * @param shell
     * @return 
     */
    protected boolean addPrepositionTerm(Shell shell)
    {
        boolean result = false;
        
        String preposition = ((Text) this.prepositionBundle.get("Preposition")).getText().trim();

        if (preposition.length() > 0)
        {
            PrepositionTerm prepositionTerm = new PrepositionTerm(preposition);
            result = this.addLexicalTerm(prepositionTerm, false);

            if (result)
            {
                this.refreshContents();
                System.out.println("Preposition Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform the preposition term.");
        }
        
        return result;
    }

    /**
     * Comment for method.
     * 
     * @param shell
     * @return 
     */
    protected boolean addAdverbTerm(Shell shell)
    {
        boolean result = false;
        
        String adverb = ((Text) this.adverbBundle.get("Adverb")).getText().trim();

        if (adverb.length() > 0)
        {
            AdverbTerm adverbTerm = new AdverbTerm(adverb);
            result = this.addLexicalTerm(adverbTerm, false);

            if (result)
            {
                this.refreshContents();
                System.out.println("Adverb Added");
            }
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform the adverb term.");
        }
        
        return result;
    }

    protected boolean addLexicalTerm(LexicalEntry lEntry, boolean addAnyway)
    {
        boolean result = false;
        
        String spaces = "       ";
        try
        {
            CNLPluginController.getInstance().addNewTermToVocabulary(lEntry, addAnyway);
            result = true;
        }
        catch (DuplicatedTermInLexiconException duplicatedTermException)
        {
            String duplicatedTerms = "";
            
            Set<PartsOfSpeech> duplicatedPOS = new HashSet<PartsOfSpeech>();
            
            for (LexicalEntry lexicalEntry : duplicatedTermException.getDuplicatedEntries())
            {
                duplicatedPOS.addAll(lexicalEntry.getAvailablePOSTags());
            }
            
            //INSPECT Lais - changed the condition
            if(duplicatedPOS.containsAll(lEntry.getAvailablePOSTags()))
            {
                
                for (LexicalEntry lexicalEntry : duplicatedTermException.getDuplicatedEntries())
                {
                    if(lEntry.getAvailablePOSTags().equals(lexicalEntry.getAvailablePOSTags())){
                        duplicatedTerms += spaces + lexicalEntry.toString();
                    }
                }
                
                String message = "The term that you are " + this.getAction() + ":\n" + spaces;

                message += lEntry.toString() + "\n";
                message += "already exists in this category:\n";
                message += duplicatedTerms
                        + "\nYou can not add this term in this category again.\n";

                MessageDialog.openError(this.getParent(), "Duplicate Term in Lexicon", message);
            }
            else
            {
                for (LexicalEntry lexicalEntry : duplicatedTermException.getDuplicatedEntries())
                {
                    duplicatedTerms += spaces + lexicalEntry.toString();
                }
                
                String message = "The term that you are adding:\n" + spaces;

                message += lEntry.toString() + "\n";
                message += "already exists in another category:\n";
                message += duplicatedTerms + "\n";
                message += "Do you want to proceed?";

                addAnyway = MessageDialog.openQuestion(this.getParent(),
                        "Duplicate Term in Lexicon", message);

                if (addAnyway)
                {
                    result = this.addLexicalTerm(lEntry, true);
                }
            }
        }
        catch (InvalidEntryInDictionaryException invalidEntryException)
        {
            String message = "The term that you are " + this.getAction() + ":\n\n" ;
            message += spaces + lEntry.toString() + "\n";
            
            message += "May not be well spelled or is not suitable for its category according to our dictionary.\n\n";
            
            message += "Do you want to add the term anyway?";
            
            addAnyway = MessageDialog.openQuestion(this.getParent(),
                    "Invalid Term", message);

            if (addAnyway)
            {
                result = this.addLexicalTerm(lEntry, true);
            }
        }
        catch (CNLException e)
        {
            MessageDialog.openInformation(this.getParent(), "Error Adding Term", e.getMessage());
            e.printStackTrace();
        }

        return result;
    }
    
    protected Composite createComposite(Composite parent, int numberOfColumns,
            boolean hasColumnsSameSize)
    {
        Composite composite = new Composite(parent, SWT.NULL);
        GridLayout layout = new GridLayout(numberOfColumns, hasColumnsSameSize);

        composite.setLayout(layout);
        layout.verticalSpacing = 4;
        composite.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));

        return composite;
    }
    
    protected boolean addNewTerm(Shell shell)
    {
        boolean result = false;
        
        int selectedIndex = this.termTypeCombo.getSelectionIndex();

        if (selectedIndex > -1)
        {
            String selectedTerm = this.termTypeCombo.getItem(selectedIndex);

            if (selectedTerm.equals(NOUN))
            {
                result = this.addNounTerm(shell);
            }
            else if (selectedTerm.equals(VERB))
            {
                result = this.addVerbTerm(shell);
            }
            else if (selectedTerm.equals(ADJECTIVE))
            {
                result = this.addAdjectiveTerm(shell);
            }
            else if (selectedTerm.equals(ADVERB))
            {
                result = this.addAdverbTerm(shell);
            }
            else if (selectedTerm.equals(PREPOSITION))
            {
                result = this.addPrepositionTerm(shell);
            }
            else if (selectedTerm.equals(CONJUNCTION))
            {
                result = this.addConjunctionTerm(shell);
            }
            else if (selectedTerm.equals(DETERMINER))
            {
                result = this.addDeterminerTerm(shell);
            }
        }
        
        this.changeVisibility(shell);
        
        return result;
    }
    
    /**
     * Erases the content of the Text widgets in a given bundle;
     * @param bundle the bundle to be treated.
     */
    protected void clearBundleContents(HashMap<String, Widget> bundle)
    {
        Set<String> textFieldsNames = bundle.keySet();
        
        for (String string : textFieldsNames)
        {
            if(!string.startsWith("@"))
            {
                ((Text) bundle.get(string)).setText("");
            }
        }
    }
    
    protected boolean someFieldIsEmpty()
    {
        int selectedIndex = this.termTypeCombo.getSelectionIndex();
        
        boolean isEmpty = true;

        if (selectedIndex > -1)
        {
            String selectedTerm = this.termTypeCombo.getItem(selectedIndex);

            if (selectedTerm.equals(NOUN))
            {
                isEmpty = this.isEmpty(this.nounBundle);
            }
            else if (selectedTerm.equals(VERB))
            {
                isEmpty = this.isEmpty(this.verbBundle);
            }
            else if (selectedTerm.equals(ADJECTIVE))
            {
                isEmpty = this.isEmpty(this.adjectiveBundle);
            }
            else if (selectedTerm.equals(ADVERB))
            {
                isEmpty = this.isEmpty(this.adverbBundle);
            }
            else if (selectedTerm.equals(PREPOSITION))
            {
                isEmpty = this.isEmpty(this.prepositionBundle);
            }
            else if (selectedTerm.equals(CONJUNCTION))
            {
                isEmpty = this.isEmpty(this.conjunctionBundle);
            }
            else if (selectedTerm.equals(DETERMINER))
            {
                isEmpty = this.isEmpty(this.determinerBundle);
            }
        }
        
        return isEmpty;
    }
    
    /**
     * Checks if some of the verb fields contains modal verbs. 
     * @return the result of the validation
     */
    //INSPECt - Lais alterie a comparação para equals
    protected boolean someFieldContainsModalVerbs()
    {
        boolean contains = false;
        
        int selectedIndex = this.termTypeCombo.getSelectionIndex();

        if (selectedIndex > -1)
        {
            String selectedTerm = this.termTypeCombo.getItem(selectedIndex);
            
            if(selectedTerm.equals(VERB))
            {
                String[] verbCandidates = this.getVerbCandidates();
                
                String[] prohibitedVerbs = CNLProperties.getInstance().getProperty("prohibited_verbs").split(",");
                
                for(int i = 0; i < verbCandidates.length && !contains; i++)
                {
                    for(int j = 0; j < prohibitedVerbs.length && !contains; j++)
                    {
                        if(verbCandidates[i].toLowerCase().equals(prohibitedVerbs[j].toLowerCase()))
                        {
                            contains = true;
                        }
                    }
                }
            }
        }

        return contains;
    }
    
    /**
     * 
     * Checks if some of the verb fields contains any verb tense of DO verb. 
     * @return the result of the validation
     */
    //INSPECT Lais new method
    protected boolean someFieldContainsDOVerb()
    {
        boolean contains = false;
        
        int selectedIndex = this.termTypeCombo.getSelectionIndex();

        if (selectedIndex > -1)
        {
            String selectedTerm = this.termTypeCombo.getItem(selectedIndex);

            if (selectedTerm.equals(VERB))
            {
                String[] verbCandidates = this.getVerbCandidates();

                for (int i = 0; i < verbCandidates.length && !contains; i++)
                {
                    if (verbCandidates[i].toLowerCase().equals("do")
                            || verbCandidates[i].toLowerCase().equals("does")
                            || verbCandidates[i].toLowerCase().equals("did")
                            || verbCandidates[i].toLowerCase().equals("doing")
                            || verbCandidates[i].toLowerCase().equals("done"))
                    {
                        contains = true;
                    }

                }
            }
        }

        return contains;
    }
    
    /**
     * 
     * Checks if some of the bundle fields contains a pronoun. 
     * @return the result of the validation
     */
    //INSPECT Lais new method
    protected boolean someFieldContainsPronouns()
    {
        String[] pronouns = CNLProperties.getInstance().getProperty("prohibited_pronouns").split(",");
        
        boolean contains = false;

        int selectedIndex = this.termTypeCombo.getSelectionIndex();

        if (selectedIndex > -1)
        {
            String selectedTerm = this.termTypeCombo.getItem(selectedIndex);

            List<String> candidates = new ArrayList<String>();

            if (selectedTerm.equals(VERB))
            {
                candidates = this.getTerms(this.verbBundle);
            }
            else if(selectedTerm.equals(NOUN))
            {
                candidates = this.getTerms(this.nounBundle);
            }
            else if(selectedTerm.equals(ADJECTIVE))
            {
                candidates = this.getTerms(this.adjectiveBundle);
            }
            else if(selectedTerm.equals(ADVERB))
            {
                candidates = this.getTerms(this.adverbBundle);
            }
            else if(selectedTerm.equals(PREPOSITION))
            {
                candidates = this.getTerms(this.prepositionBundle);
            }
            else if(selectedTerm.equals(CONJUNCTION))
            {
                candidates = this.getTerms(this.conjunctionBundle);
            }
            else if(selectedTerm.equals(DETERMINER))
            {
                candidates = this.getTerms(this.determinerBundle);
            }
            
            for(int i = 0; i < pronouns.length && !contains; i++)
            {
                if(candidates.contains(pronouns[i].toLowerCase()))
                {
                    contains = true;
                }
            }
        }

        return contains;
    }
    
    /**
     * 
     * Returns a list containing the text in the bundle fields.
     * @param bundle the bundle to check the fields 
     * @return a list containing the text in the bundle fields.
     */
    private List<String> getTerms(HashMap<String, Widget> bundle)
    {
        List<String> result = new ArrayList<String>();
        
        for (Widget widget : bundle.values())
        {
            if(widget instanceof Text)
            {
                result.add(((Text) widget).getText().toLowerCase());
            }
        }
        
        return result;
        
    }
    
    /**
     * 
     * Returns an array containing the text in the verb fields.
     * @return an array containing the text in the verb fields
     */
    private String [] getVerbCandidates()
    {
        String[] verbCandidates = new String[]{((Text) this.verbBundle.get("Infinitive")).getText(),
                ((Text) this.verbBundle.get("Past")).getText(),
                ((Text) this.verbBundle.get("Past Participle")).getText(),
                ((Text) this.verbBundle.get("Gerund")).getText(),
                ((Text) this.verbBundle.get("Third Person")).getText()};
        
        return verbCandidates;
    }
    
    /**
     * 
     * Verifies the conditions to display the warning labels.
     */
    protected void warning()
    {

        if (someFieldIsEmpty())
        {
            this.warningLabel.setVisible(true);
        }
        else
        {
            this.warningLabel.setVisible(false);
        }
        
        if(someFieldContainsModalVerbs())
        {
            this.verbWarningLabel.setText("You can not add modal verbs.");
            this.verbWarningLabel.setVisible(true);
        }
        else if(someFieldContainsDOVerb())
        {
            //label texts should have same number of characters in order to display well 
            this.verbWarningLabel.setText("You cannot add the DO verb. ");
            this.verbWarningLabel.setVisible(true);
        }
        else if(someFieldContainsPronouns())
        {
            //label texts should have same number of characters in order to display well 
            this.verbWarningLabel.setText("You cannot add pronouns.    ");
            this.verbWarningLabel.setVisible(true);
        }
        else
        {
            this.verbWarningLabel.setVisible(false);
        }
        
        this.packLabels();
    }
    
    /**
     * 
     * Calls the <code>pack()</code> method for the dialog's warning label.
     */
    public void packLabels()
    {
        this.verbWarningLabel.pack();
        this.warningLabel.pack();
    }
}
