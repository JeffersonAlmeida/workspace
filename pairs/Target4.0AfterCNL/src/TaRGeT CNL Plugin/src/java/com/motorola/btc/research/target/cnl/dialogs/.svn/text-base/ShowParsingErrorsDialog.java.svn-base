package com.motorola.btc.research.target.cnl.dialogs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import com.motorola.btc.research.cnlframework.grammar.ParsingPossibility;
import com.motorola.btc.research.cnlframework.postagger.POSUtil;
import com.motorola.btc.research.cnlframework.postagger.TaggedSentence;
import com.motorola.btc.research.cnlframework.tokenizer.TokenizedSentence;
import com.motorola.btc.research.target.cnl.Activator;

public class ShowParsingErrorsDialog extends Dialog {

	private Combo errorSentencesCombo;

	private Composite parsingAlternativesComposite;

	HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> errorSentences;

	/**
	 * Class constructor.
	 * 
	 * @param parentShell
	 */
	public ShowParsingErrorsDialog(Shell parentShell) {
		super(parentShell, SWT.DIALOG_TRIM);
		this.setText("Sintax Errors Dialog");

	}

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
		shell.setLayout(new GridLayout(1, false));

		Label errorSentences = new Label(shell, SWT.NONE);
		errorSentences.setText("Error Sentence:");
		GridData data = new GridData();
		errorSentences.setLayoutData(data);

		this.errorSentencesCombo = new Combo(shell, SWT.DROP_DOWN
				| SWT.READ_ONLY);
		data = new GridData(100, SWT.DEFAULT);
		this.populateSentencesCombo();
		this.errorSentencesCombo.select(0);
		
		Label errorCount = new Label(shell, SWT.NONE);
		errorCount.setText("Found "+errorSentencesCombo.getItemCount() +" sintax errors.");
		data = new GridData();
		errorSentences.setLayoutData(data);
		
		Label suggestions = new Label(shell, SWT.NONE);
		suggestions.setText("Suggestions:");
		data = new GridData();
		errorSentences.setLayoutData(data);

		// Populating the group with "sentence 0" parsing alternatives.
		changeVisibility(shell, 0);

		this.errorSentencesCombo.addSelectionListener(new SelectionListener() {


			public void widgetDefaultSelected(SelectionEvent e) {
			}


			public void widgetSelected(SelectionEvent e) {
				int index = errorSentencesCombo.getSelectionIndex();
				changeVisibility(shell, index);

			}

		});

	}

	/**
	 * This method is responsible to populate the dialog comb with the error
	 * sentences.
	 */
	public void populateSentencesCombo() {
		for (TokenizedSentence sentence : this.errorSentences.keySet()) {
			this.errorSentencesCombo.add(sentence.getActualSentence());

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
	private void changeVisibility(Shell shell, int index) {
		this.updateParsingAlternativesComposite(shell);

		// a HashMap containing the tagged sentences and parsing possiblities to
		// the selected error sentence
		HashMap<TaggedSentence, List<ParsingPossibility>> taggedSentencesPossibilities = this
				.taggedSentencesPossibilities(index);

		// list containing the tagged sentences according to the selected error
		// sentence
		List<TaggedSentence> taggedSentences = new ArrayList<TaggedSentence>();

		for (TaggedSentence taggedSentence : taggedSentencesPossibilities
				.keySet()) {
			taggedSentences.add(taggedSentence);
		}
		Text alternativeSentence1 = new Text(this.parsingAlternativesComposite,
				SWT.READ_ONLY | SWT.V_SCROLL | SWT.H_SCROLL);

		String parsingAlternativesToString = "";
		// adding the tagged sentence to the dialog
		for (int i = 0; i < taggedSentences.size(); i++) {

			parsingAlternativesToString +=(i+1)+". "+ POSUtil.getInstance()
					.explainedTaggedSentence(taggedSentences.get(i))
					+ this
							.parsingPossibilitiesToString(taggedSentencesPossibilities
									.get(taggedSentences.get(i)));
		}
		alternativeSentence1.setSize(588, 132);
		alternativeSentence1.setText(parsingAlternativesToString);

		alternativeSentence1.setBackground(new Color(alternativeSentence1
				.getDisplay(), 255, 255, 255));

		shell.layout();
	}

	/**
	 * This method is responsible to calls the toString() method for each ParsingPossibility
	 * in the list
	 */
	public String parsingPossibilitiesToString(List<ParsingPossibility> list) {
		StringBuffer result = new StringBuffer();
		
		result.append("\n\n");
		for (ParsingPossibility parsingPossibility : list) {
			result.append("\t");
			result.append(parsingPossibility.toString());					
			result.append("\n\n");
		}

		return result.toString();
	}

	/**
	 * This method responsible to return the set of tagged sentences and its
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
			if (tokenizedSentence.getActualSentence().equals(sentence)) {
				parsingPossibilities = this.errorSentences
						.get(tokenizedSentence);
			}
		}

		return parsingPossibilities;
	}

	/**
	 * This method is responsible to update the Suggestions composite according to
	 * the selected sentence. If the composite was already created, it calls the
	 * dispose method to free the the resources and creates the composite again.
	 * 
	 * @param shell
	 */
	public void updateParsingAlternativesComposite(Shell shell) {
		if (parsingAlternativesComposite != null) {
			parsingAlternativesComposite.dispose();
		}
		parsingAlternativesComposite = new Composite(shell, SWT.SHADOW_NONE);
		parsingAlternativesComposite.setVisible(true);
		parsingAlternativesComposite.setBackground(new Color(
		parsingAlternativesComposite.getDisplay(), 255, 255, 255));

	}

}
