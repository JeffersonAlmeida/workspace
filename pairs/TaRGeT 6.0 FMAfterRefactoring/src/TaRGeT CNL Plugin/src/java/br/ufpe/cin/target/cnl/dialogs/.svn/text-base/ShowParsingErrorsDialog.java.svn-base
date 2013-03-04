package br.ufpe.cin.target.cnl.dialogs;


import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import br.ufpe.cin.cnlframework.grammar.ParsingPossibility;
import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.postagger.TaggedSentence;
import br.ufpe.cin.cnlframework.postagger.TaggedTerm;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedSentence;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.target.cnl.Activator;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.cnl.controller.CNLProperties;
import br.ufpe.cin.target.cnl.views.CNLView;


public class ShowParsingErrorsDialog extends Dialog {

	private Combo errorSentencesCombo;
	
	private Group suggestionsGroup;

	private Browser errorMessageBrowser;

	HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> errorSentences;

	/**
	 * Class constructor.
	 * 
	 * @param parentShell
	 */
	public ShowParsingErrorsDialog(Shell parentShell) {
		super(parentShell, SWT.DIALOG_TRIM | SWT.RESIZE | SWT.APPLICATION_MODAL);
		this.setText("Syntax Errors Dialog");

	}
	
	/**
	 * Opens the Dialog.
	 * @param errorSentences the error sentences that will be shown in the dialog
	 */
	public void open(
			HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> errorSentences) {
		this.errorSentences = errorSentences;

		// Creates the dialog window
		Shell shell = new Shell(getParent(), getStyle());
		shell.setText(getText());
		shell.setImage(Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
				"icons/grammarFaultIcon.bmp").createImage());
		
		//Creates the shell contents
		createContents(shell);
		
		shell.setSize(600, 250);
		shell.pack();
		shell.open();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * This method is responsible to add the error sentences and the suggestions
	 * in the dialog view.
	 * 
	 * @param shell
	 */
	private void createContents(final Shell shell) {
		shell.setLayout(new GridLayout(2, false));

		Label errorSentences = new Label(shell, SWT.NONE);
		errorSentences.setText("Error Sentence:");
		GridData data = new GridData();
		errorSentences.setLayoutData(data);

		this.errorSentencesCombo = new Combo(shell, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData();
		this.populateSentencesCombo();
		this.errorSentencesCombo.select(0);
		
		Label errorCount = new Label(shell, SWT.NONE);
		errorCount.setText("Found "+errorSentencesCombo.getItemCount() +" sintax errors.");
		data = new GridData();
		data.horizontalSpan = 2;
		errorCount.setLayoutData(data);
		
		this.suggestionsGroup = new Group(shell, SWT.NONE);
		this.suggestionsGroup.setText("Suggestions");
		//this.suggestionsGroup.setBackground(Display.getDefault().getSystemColor(SWT.COLOR_WHITE));
		
		GridLayout groupLayout = new GridLayout(1, true);
        groupLayout.verticalSpacing = 0;
        this.suggestionsGroup.setLayout(groupLayout); 
		
		GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.horizontalSpan = 2;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        suggestionsGroup.setLayoutData(gridData);

		// Populating the group with "sentence 0" parsing alternatives.
		changeVisibility(this.suggestionsGroup, 0);

		this.errorSentencesCombo.addSelectionListener(new SelectionListener() {

			
			public void widgetDefaultSelected(SelectionEvent e) {
			}

			
			public void widgetSelected(SelectionEvent e) {
				int index = errorSentencesCombo.getSelectionIndex();
				changeVisibility(suggestionsGroup, index);

			}

		});

	}

	/**
	 * This method is responsible to populate the dialog comb with the error
	 * sentences.
	 */
	public void populateSentencesCombo() {
	    this.errorSentencesCombo.removeAll();
	    
	    int sentenceIndex = 1;
	    for (TokenizedSentence sentence : this.errorSentences.keySet()) {
		    if(sentence.getTestCaseTextType().equals(TestCaseTextType.ACTION) && CNLView.getView().getFilter().isShowActions()){
		        this.errorSentencesCombo.add(sentenceIndex + ". " + sentence.getActualSentence());
		        sentenceIndex = sentenceIndex + 1;
		    }
		    else if(sentence.getTestCaseTextType().equals(TestCaseTextType.CONDITION) && CNLView.getView().getFilter().isShowConditions()){
		        this.errorSentencesCombo.add(sentenceIndex + ". " + sentence.getActualSentence());
		        sentenceIndex = sentenceIndex + 1;
		    }
		    else if(sentence.getTestCaseTextType().equals(TestCaseTextType.RESPONSE) && CNLView.getView().getFilter().isShowResponses()){
		        this.errorSentencesCombo.add(sentenceIndex + ". " + sentence.getActualSentence());
		        sentenceIndex = sentenceIndex + 1;
		    }
		}
	}

	/**
	 * This method is responsible to change the information that is displayed
	 * according to the selected sentence.
	 * 
	 * @param shell
	 * @param index
	 *            the selected sentence index
	 */
	private void changeVisibility(Composite shell, int index) {
		this.updateErrorMessageBrowser(shell);

		// a HashMap containing the tagged sentences and parsing possiblities to
		// the selected error sentence
		HashMap<TaggedSentence, List<ParsingPossibility>> taggedSentencesPossibilities = this
				.taggedSentencesPossibilities(index);

		// list containing the tagged sentences according to the selected error
		// sentence
        List<TaggedSentence> treatedTaggedSentences = this.removeUnisgnificantParsingPossibilities(taggedSentencesPossibilities, taggedSentencesPossibilities
                .keySet());

		String parsingAlternativesToString = "<ol>";
		// adding the tagged sentence to the dialog
		for (int i = 0; i < treatedTaggedSentences.size(); i++) {

            List<ParsingPossibility> parsingPossibilities = taggedSentencesPossibilities
                    .get(treatedTaggedSentences.get(i));
            
            int errorPosition = taggedSentencesPossibilities.get(treatedTaggedSentences.get(i))
                    .get(0).getErrorPosition();
		    
            parsingAlternativesToString += "<li>"
                    + this.taggedSentenceToHtml(treatedTaggedSentences.get(i),errorPosition) 
                    + "<ul>"
                    + this.parsingPossibilitiesToString(parsingPossibilities) + "</ul>" + "</li>";
			
		}
		
		parsingAlternativesToString += "</ol>";
		
        this.errorMessageBrowser.setText("<HTML><HEAD><TITLE>Parsing Errors Dialog</TITLE>" +
        		"<style type=\"text/css\">" +
        		"body { background: #FFFFFF; font-family:arial; font-size: 10pt; }"+
        		"</style>"+
        		"</HEAD><BODY>"
                + parsingAlternativesToString + "</BODY></HTML>");
        
        this.errorMessageBrowser.layout();
        this.errorMessageBrowser.pack();
        
        this.suggestionsGroup.layout();
        this.suggestionsGroup.pack();
        
        
		shell.layout();
		shell.pack();
	}
	
	/**
	 * Returns a list of tagged sentences that have a grater value of error position in its parsing possibilities.
	 * @param taggedSentencesPossibilities the HashMap containing the tagged sentences and its parsing possibilities.
	 * @param taggedSentences 
	 * @return a list of tagged sentences
	 */
	private List<TaggedSentence> removeUnisgnificantParsingPossibilities(HashMap<TaggedSentence, List<ParsingPossibility>> taggedSentencesPossibilities, Collection<TaggedSentence> taggedSentences){
	    List<TaggedSentence> result = new ArrayList<TaggedSentence>();
	    int greatestErrorPosition = 0;
	    
	    for (Iterator<TaggedSentence> iterator = taggedSentences.iterator(); iterator.hasNext();)
        {
            TaggedSentence taggedSentence = (TaggedSentence) iterator.next();
            
            List<ParsingPossibility> list = taggedSentencesPossibilities.get(taggedSentence);
            if(list.get(0).getErrorPosition() > greatestErrorPosition){
                greatestErrorPosition = list.get(0).getErrorPosition();
                result.clear();
                result.add(taggedSentence);             
            }
            else if(list.get(0).getErrorPosition() == greatestErrorPosition){
                result.add(taggedSentence);
            }
            
        }
	    
	    return result;
	}
	
	/**
	 * Returns the HTML representation of a tagged sentence, considering the given error position.
	 * @param taggedSentence the tagged sentence to be processed
	 * @param errorPosition the error position in the tagged sentence.
	 * @return a String with the HTML representation of a tagged sentence
	 */
    public String taggedSentenceToHtml(TaggedSentence taggedSentence, int errorPosition)
    {
        StringBuffer result = new StringBuffer();
        result.append("<b>");
        
        int i = 0;
        for (TaggedTerm term : taggedSentence.getTaggedTerms())
        {
            PartsOfSpeech pos = term.getPartOfSpeech();
            String posValue = POSUtil.getInstance().explainTaggedTerm(pos);

            if(i == errorPosition){
                result.append("<span style=\"background-color: #FFA0A0\">");
                result.append("<span style=\"cursor: help;\" title=\"Classification: "+posValue+"\">"+ this.replaceLessGraterThanSymbols(term.getTermString())+ "</span></span> ");
            }
            else
            {
                result.append("<span style=\"cursor: help;\" title=\"Classification: "+posValue+"\">"+ this.replaceLessGraterThanSymbols(term.getTermString())+ "</span> ");
            }
            
            i++;
        }
        result.append("</b><br/><br/>");
        return result.toString();

    }
    
    /**
     * Replaces "<" and ">" symbols that might occur in the text by its HTML codes.
     * @param text the text to be treated.
     * @return the processed text
     */
    private String replaceLessGraterThanSymbols(String text){
        String result = text.replaceAll("<", "&#60;");
        result = result.replaceAll(">", "&#62;");
        
        return result;
    }

	/**
	 * This method is responsible to calls the toString() method for each ParsingPossibility
	 * in the list
	 */
    public String parsingPossibilitiesToString(List<ParsingPossibility> list)
    {
        int greatestErrorPosition = 0;

        StringBuffer result = new StringBuffer();

        List<ParsingPossibility> treatedParsingPossibilities = new ArrayList<ParsingPossibility>();
        
        //putting only the parsing possibilities that have a greather value of error position in treatedParsingPossibilities list
        for (ParsingPossibility parsingPossibility : list)
        {
            if (parsingPossibility.getErrorPosition() > greatestErrorPosition)
            {
                greatestErrorPosition = parsingPossibility.getErrorPosition();

                if (result.length() > 0)
                {
                    result.delete(0, result.length() - 1);
                    treatedParsingPossibilities.clear();
                }
                treatedParsingPossibilities.add(parsingPossibility);
            }
            else if (parsingPossibility.getErrorPosition() == greatestErrorPosition)
            {
                treatedParsingPossibilities.add(parsingPossibility);
            }
        }

        result.append("<li>");
        
        //html representation of parsing possibilities.
        result.append(this.parsingPossibilityToHtml(treatedParsingPossibilities));
        
        result.append("<br/><br/><u>Examples of accepted sentences:</u> <i>"
                + CNLProperties.getInstance().getProperty(
                        treatedParsingPossibilities.get(0).getInitialGrammarNode().getName())
                + "</i>");
        
        result.append("</li>");
        result.append("<br/><br/>");

        return result.toString();
    }
	
    /**
     * Returns the HTML representation of a list of parsing possibility.
     * @param parsingPossibilities the list of parsing possibility
     * @return the HTML representation of a list of parsing possibility
     */
	public String parsingPossibilityToHtml(List<ParsingPossibility> parsingPossibilities){
	    String expectedString = "Expected ";

        String whenFound = "</b></span> when found <b><span style=\"background-color: #FFA0A0\">"
                + POSUtil.getInstance().explainTaggedTerm(
                        parsingPossibilities.get(0).getTaggedSentence().getTaggedTerm(
                                parsingPossibilities.get(0).getErrorPosition()).getPartOfSpeech())
                + "</span></b>";
        
        String errorTerm = parsingPossibilities.get(0).getTaggedSentence().getTaggedTerm(
                parsingPossibilities.get(0).getErrorPosition()).getTermString();
        
        String grammarRule = " at <b><span style=\"background-color: #FFA0A0\">"
                + this.replaceLessGraterThanSymbols(errorTerm) + "</span></b>,"
                + " using the grammar rule: <b>"
                + "<span style=\"cursor: help;\" title=\""
                + CNLProperties.getInstance().getProperty(
                        parsingPossibilities.get(0).getInitialGrammarNode().getName() + 1) + "\">"
                + parsingPossibilities.get(0).getInitialGrammarNode().getName() + "</span></b>.";
        
        int i = 0;
        for (ParsingPossibility parsingPossibility : parsingPossibilities)
        {
            String posName = parsingPossibility.getExpectedGrammarNode().getName();
            

            if(!POSUtil.getInstance().explainTaggedTerm(posName).equals(posName))
            {
                expectedString = expectedString + "<select><option value=\"" + posName + "\">"
                + POSUtil.getInstance().explainTaggedTerm(posName) + "</option>";
                
                PartsOfSpeech partsOfSpeech = PartsOfSpeech.valueOf(posName);

                List<String> termSamples = CNLPluginController.getInstance().getSamples(
                        partsOfSpeech, 5);

                for (String string : termSamples)
                {
                    expectedString += "<option value=\"" + string + "\">" + string + "</option>";
                }
                
                expectedString += "</select>";
            }
            
            else{
                expectedString = expectedString + "<b><span style=\"background-color: #6EF8A0\">"
                        + POSUtil.getInstance().explainTaggedTerm(posName) + "</b></span> ";
            }

            if (i < parsingPossibilities.size() - 1)
            {
                expectedString = expectedString + " or ";
            }

            i++;

        }

        return expectedString + whenFound + grammarRule;
	    
	}
	
	/**
	 * This method is responsible to return the set of tagged sentences and its
	 * parsing possibilities according to an error sentence selected in the
	 * combo.
	 * 
	 * @param sentenceIndex the error sentence index
	 * @return a set of tagged sentences and its parsing possibilities according
	 */
	private HashMap<TaggedSentence, List<ParsingPossibility>> taggedSentencesPossibilities(
			int sentenceIndex) {

		String sentence = this.errorSentencesCombo.getItem(sentenceIndex);

		// a set containing all error sentences
		Set<TokenizedSentence> tokenizedSentences = this.errorSentences
				.keySet();

		// a HashMap containing the tagged sentences and parsing possiblities to
		// the selected error sentence
		HashMap<TaggedSentence, List<ParsingPossibility>> parsingPossibilities = null;

		// if an error sentence in the set is equals to the selected error
		// sentence
		for (TokenizedSentence tokenizedSentence : tokenizedSentences) {
		    if(sentence.contains(tokenizedSentence.getActualSentence())){
				parsingPossibilities = this.errorSentences
						.get(tokenizedSentence);
			}
		}

		return parsingPossibilities;
	}

	/**
	 * This method is responsible to update the Suggestions Browser according to
	 * the selected sentence. If the Browser was already created, it calls the
	 * dispose method to free the the resources and creates the Browser again.
	 * 
	 * @param shell
	 */
	public void updateErrorMessageBrowser(Composite shell) {
        if (errorMessageBrowser != null)
        {
            errorMessageBrowser.dispose();
        }

        errorMessageBrowser = new Browser(shell, SWT.NONE);

        GridData gridData = new GridData();
        gridData.horizontalAlignment = GridData.FILL;
        gridData.grabExcessHorizontalSpace = true;
        gridData.verticalAlignment = GridData.FILL;
        gridData.grabExcessVerticalSpace = true;
        errorMessageBrowser.setLayoutData(gridData);

	}
}
