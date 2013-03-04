/*
 * @(#)AddLexicalTermDialog.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   May 14, 2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cnl.dialogs;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;

import com.motorola.btc.research.cnlframework.exceptions.CNLException;
import com.motorola.btc.research.cnlframework.util.UtilNLP;
import com.motorola.btc.research.cnlframework.vocabulary.terms.AdjectiveTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.cnlframework.vocabulary.terms.NounTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.VerbTerm;
import com.motorola.btc.research.target.cnl.Activator;
import com.motorola.btc.research.target.cnl.controller.CNLPluginController;
import com.motorola.btc.research.target.cnl.exceptions.DuplicatedTermInLexiconException;
import com.motorola.btc.research.target.cnl.views.CNLView;

public class AddLexicalTermDialog extends Dialog
{

    private HashMap<String, Widget> nounBundle;

    private HashMap<String, Widget> verbBundle;

    private HashMap<String, Widget> qualifierBundle;

    private Combo unknownTermsCombo;

    private Combo termTypeCombo;

    private Button addButton;

    public AddLexicalTermDialog(Shell parentShell)
    {
        super(parentShell, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);

        this.setText("Add Lexical Term Dialog");
    }

    public void open(Collection<String> unknownTerms)
    {
        // Create the dialog window
        Shell shell = new Shell(getParent(), getStyle());
        shell.setText(getText());
        shell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                "icons/addNewLexicalTerm.bmp").createImage());
        createContents(shell, unknownTerms);
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
    
    private void refreshContents()
    {
    	try
        {
    	CNLView.getView().update(true);
    	
    	CNLPluginController.getInstance().processPhoneDocuments();
        List<String> list = CNLPluginController.getInstance().getOrderedUnkownWords();
        this.unknownTermsCombo.removeAll();
        
        for(String word : list)
        {
            this.unknownTermsCombo.add(word);
        }
        this.unknownTermsCombo.select(0);
        }
        catch (Exception e)
        {
        	MessageDialog.openError(this.getParent(), "Error reloading bases", e.getMessage());
            e.printStackTrace();
        }
        
    }

    private void createContents(final Shell shell, Collection<String> unknownTerms)
    {
        shell.setLayout(new GridLayout(2, false));

        Label unknownTermsLabel = new Label(shell, SWT.NONE);
        unknownTermsLabel.setText("Unknown Terms:");
        GridData data = new GridData();
        unknownTermsLabel.setLayoutData(data);

        this.unknownTermsCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        data = new GridData(100, SWT.DEFAULT);
        for (String str : unknownTerms)
        {
            this.unknownTermsCombo.add(str);
        }
        this.unknownTermsCombo.select(0);

        Label termTypeLabel = new Label(shell, SWT.NONE);
        termTypeLabel.setText("New Term Type:");
        data = new GridData();
        termTypeLabel.setLayoutData(data);

        this.termTypeCombo = new Combo(shell, SWT.DROP_DOWN | SWT.READ_ONLY);
        data = new GridData(100, SWT.DEFAULT);
        this.termTypeCombo.setLayoutData(data);
        this.termTypeCombo.add("Noun");
        this.termTypeCombo.add("Verb");
        this.termTypeCombo.add("Qualifier");
        this.termTypeCombo.select(0);

        this.termTypeCombo.addSelectionListener(new SelectionListener()
        {


            public void widgetDefaultSelected(SelectionEvent e)
            {
            }


            public void widgetSelected(SelectionEvent e)
            {
                int index = termTypeCombo.getSelectionIndex();
                changeVisibility(shell, index);

            }
        });

        this.nounBundle = this.createGroup(shell, "Noun", new String[] { "Singular", "Plural" },
                "Singular");

        this.qualifierBundle = this.createGroup(shell, "Qualifier", new String[] { "Qualifier" },
                "Qualifier");

        this.verbBundle = this.createGroup(shell, "Verb", new String[] { "Infinitive", "Past",
                "Past Participle", "Gerund", "Third Person" }, "Infinitive");

        this.addButton = new Button(shell, SWT.PUSH);
        data = new GridData(SWT.RIGHT, SWT.TOP, false, false, 2, 1);
        this.addButton.setLayoutData(data);
        this.addButton.setText("Add New Term");

        this.addButton.addSelectionListener(new SelectionListener()
        {


            public void widgetDefaultSelected(SelectionEvent e)
            {
            }


            public void widgetSelected(SelectionEvent e)
            {
                addNewTerm();
            }
        });

        this.unknownTermsCombo.addSelectionListener(new SelectionListener()
        {


            public void widgetDefaultSelected(SelectionEvent e)
            {
            }


            public void widgetSelected(SelectionEvent e)
            {
                int i = termTypeCombo.getSelectionIndex();
                switch (i)
                {
                    case 0:
                        fillBundle(nounBundle);
                        break;
                    case 1:
                        fillBundle(verbBundle);
                        break;
                    case 2:
                        fillBundle(qualifierBundle);
                }
                shell.redraw();
            }
        });

        changeVisibility(shell, 0);
    }

    private void fillBundle(HashMap<String, Widget> bundle)
    {
        Text defaultText = (Text) bundle.get("@default");

        int index = this.unknownTermsCombo.getSelectionIndex();
        if (index >= 0)
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
            }
        }
    }

    private void addNewTerm()
    {
        int index = this.termTypeCombo.getSelectionIndex();

        switch (index)
        {
            case 0:
                this.addNounTerm();
                break;
            case 1:
                this.addVerbTerm();
                break;
            case 2:
                this.addQualifierTerm();
        }
    }

    private boolean addLexicalTerm(LexicalEntry lEntry, boolean addAnyway)
    {
        boolean result = false;
        try
        {
            CNLPluginController.getInstance().addNewTermToVocabulary(lEntry, addAnyway);
            result = true;
        }
        catch (DuplicatedTermInLexiconException dtile)
        {
            addAnyway = MessageDialog.openQuestion(this.getParent(), "Duplicate Term in Lexicon",
                    dtile.getMessage() + "\n\n Add anyway?");

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
    
    private void displayTermAddedDialog()
    {
        MessageDialog.openInformation(this.getParent(), "Term added", "The new term was added successfully!");
    }

    private void addNounTerm()
    {
        String singular = ((Text) this.nounBundle.get("Singular")).getText().trim();
        String plural = ((Text) this.nounBundle.get("Plural")).getText().trim();
        if (singular.length() > 0)
        {
            NounTerm nounTerm = new NounTerm(singular, this.changeIfEmpty(plural, UtilNLP
                    .getPluralForm(singular)));
            boolean result = this.addLexicalTerm(nounTerm, false);
            if(result)
            {
                this.displayTermAddedDialog();
                this.refreshContents();
            }
            System.out.println("Noun Added");
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform at least the singular term.");
        }
    }

    private String changeIfEmpty(String str1, String str2)
    {
        return (str1.length() == 0) ? str2 : str1;
    }

    private void addVerbTerm()
    {
        String infinitive = ((Text) this.verbBundle.get("Infinitive")).getText().trim();
        String past = ((Text) this.verbBundle.get("Past")).getText().trim();
        String pastParticiple = ((Text) this.verbBundle.get("Past Participle")).getText().trim();
        String gerund = ((Text) this.verbBundle.get("Gerund")).getText().trim();
        String thirdPerson = ((Text) this.verbBundle.get("Third Person")).getText().trim();
        if (infinitive.length() > 0)
        {
            VerbTerm verbTerm = new VerbTerm(infinitive, this.changeIfEmpty(past, UtilNLP.getPast(infinitive)), this
                    .changeIfEmpty(pastParticiple, UtilNLP.getPastParticiple(infinitive)), this.changeIfEmpty(gerund,
                    UtilNLP.getGerund(infinitive)), this.changeIfEmpty(thirdPerson, UtilNLP
                    .getThirdPerson(infinitive)), infinitive);
            boolean result = this.addLexicalTerm(verbTerm, false);
            if(result)
            {
                this.displayTermAddedDialog();
                this.refreshContents();
            }
            System.out.println("Verb Added");
        }
        else
        {
            MessageDialog.openInformation(this.getParent(), "Missing Field",
                    "You have to inform at least the infinitive term.");
        }
    }

    private void addQualifierTerm()
    {
        String qualifier = ((Text) this.qualifierBundle.get("Qualifier")).getText().trim();
        if (qualifier.length() > 0)
        {
            AdjectiveTerm qualifierTerm = new AdjectiveTerm(qualifier);
            boolean result = this.addLexicalTerm(qualifierTerm, false);
            if(result)
            {
                this.displayTermAddedDialog();
                this.refreshContents();
            }
            System.out.println("Qualifier Added");
        }
    }

    private void changeVisibility(Shell shell, int i)
    {
        this.setBundleVisibility(this.nounBundle, false);
        this.setBundleVisibility(this.qualifierBundle, false);
        this.setBundleVisibility(this.verbBundle, false);

        switch (i)
        {
            case 0:
                this.setBundleVisibility(this.nounBundle, true);
                this.fillBundle(this.nounBundle);
                break;
            case 1:
                this.setBundleVisibility(this.verbBundle, true);
                this.fillBundle(this.verbBundle);
                break;
            case 2:
                this.setBundleVisibility(this.qualifierBundle, true);
                this.fillBundle(this.qualifierBundle);
        }
        shell.layout();
        shell.pack();
    }

    private void setBundleVisibility(HashMap<String, Widget> bundle, boolean visible)
    {
        Group group = ((Group) bundle.get("@group"));

        group.setVisible(visible);

        ((GridData) group.getLayoutData()).exclude = !visible;
    }

    private HashMap<String, Widget> createGroup(Composite parent, String groupName,
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
}
