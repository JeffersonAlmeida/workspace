package com.motorola.btc.research.target.cnl.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.motorola.btc.research.cnlframework.exceptions.CNLException;
import com.motorola.btc.research.cnlframework.grammar.CNLParser;
import com.motorola.btc.research.cnlframework.grammar.GrammarNode;
import com.motorola.btc.research.cnlframework.grammar.GrammarReader;
import com.motorola.btc.research.cnlframework.grammar.ParserTreeNode;
import com.motorola.btc.research.cnlframework.grammar.ParsingPossibility;
import com.motorola.btc.research.cnlframework.postagger.POSTagger;
import com.motorola.btc.research.cnlframework.postagger.POSUtil;
import com.motorola.btc.research.cnlframework.postagger.TaggedSentence;
import com.motorola.btc.research.cnlframework.preprocessor.TextPreprocessor;
import com.motorola.btc.research.cnlframework.tokenizer.TextTokenizer;
import com.motorola.btc.research.cnlframework.tokenizer.TokenizedSentence;
import com.motorola.btc.research.cnlframework.tokenizer.TokenizedText;
import com.motorola.btc.research.cnlframework.vocabulary.TrieWord;
import com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository;
import com.motorola.btc.research.cnlframework.vocabulary.base.LexiconBase;
import com.motorola.btc.research.cnlframework.vocabulary.base.LexiconXMLRepository;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.target.cnl.exceptions.DuplicatedTermInLexiconException;
import com.motorola.btc.research.target.cnl.exceptions.NoParserTreeFoundException;
import com.motorola.btc.research.target.cnl.exceptions.NoTaggedSentenceFoundException;
import com.motorola.btc.research.target.cnl.logger.Logger;
import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.Flow;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;

public class CNLPluginController {

	private static CNLPluginController instance;

	private LexiconBase lexicon;

	private HashMap<TestCaseTextType, GrammarNode> grammars;

	private TextTokenizer textTokenizer;

	private POSTagger posTagger;

	private CNLParser parser;

	private int sentenceCount;

	private Set<String> unkownWords;

	private boolean[] showSentences;

	private HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> parsingFaultsList;

	/**
	 * The class constructor.
	 */
	public CNLPluginController() {
		this.unkownWords = new HashSet<String>();
		this.showSentences = new boolean[] { true, true, true };
	}

	/**
	 * This method returns a CNLPluginController instance.
	 * 
	 * @return a CNLPluginController instance
	 */
	public static CNLPluginController getInstance() {
		if (instance == null) {
			instance = new CNLPluginController();
		}
		return instance;
	}

	/**
	 * This method is responsible to add a new term to the lexicon.
	 * 
	 * @param lexicalEntry
	 *            new term to be added
	 * @param ignoreIntersection
	 *            indicates if the intersection between the lexicon and the
	 *            entry will be ignored.
	 * @throws CNLException
	 */
	public void addNewTermToVocabulary(LexicalEntry lexicalEntry,
			boolean ignoreIntersection) throws CNLException {
		Set<LexicalEntry> intersection = this.lexicon
				.getLexicalEntryIntersection(lexicalEntry);

		if (intersection.size() > 0 && !ignoreIntersection) {
			throw new DuplicatedTermInLexiconException(lexicalEntry,
					intersection);
		}

		this.lexicon.addLexicalEntry(lexicalEntry);
	}

	/**
	 * This method is responsible to initialize the base components of the
	 * plugin.
	 * 
	 * @throws CNLException
	 */
	private void initializeBases() throws CNLException {

		CNLProperties properties = CNLProperties.getInstance();

		ILexiconRepository lexRepository = new LexiconXMLRepository(properties
				.getLexiconFiles());

		this.lexicon = new LexiconBase(lexRepository);

		GrammarReader grammarInstance = GrammarReader.getInstance();

		this.grammars = new HashMap<TestCaseTextType, GrammarNode>();
		this.grammars.put(TestCaseTextType.ACTION, grammarInstance
				.readGrammar(properties.getActionGrammarFiles()));
		this.grammars.put(TestCaseTextType.RESPONSE, grammarInstance
				.readGrammar(properties.getResponseGrammarFiles()));
		this.grammars.put(TestCaseTextType.CONDITION, grammarInstance
				.readGrammar(properties.getConditionGrammarFiles()));

		this.parsingFaultsList = new HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>>();
	}

	/**
	 * This method initializes the text processors TextTokenizer and POSTagger.
	 */
	private void initializeProcessors() {
		this.textTokenizer = new TextTokenizer();
		this.posTagger = new POSTagger(this.lexicon.getTrieVocabulary());
		this.parser = new CNLParser();
	}

	public void startController() throws CNLException {
		this.initializeBases();
		this.initializeProcessors();
	}

	long parseTime;

	long lexTime;

	int parseNum;

	int lexNum;

	/**
	 * This method is responsible to get the tagged sentences
	 * 
	 * @param tSentence
	 *            a tokenized sentence
	 * @param grammarTerms
	 *            a grammar terms set
	 * @return the tagged sentences
	 * @throws NoTaggedSentenceFoundException
	 */
	public Set<TaggedSentence> tagTokenizedSentence(
			TokenizedSentence tSentence, Set<TrieWord> grammarTerms)
			throws NoTaggedSentenceFoundException {
		long temp = System.currentTimeMillis();
		lexNum++;
		this.posTagger.setGrammarTrieVocabulary(TrieWord
				.getVocabularyFromCollection(grammarTerms));
		Set<TaggedSentence> taggedSentences = this.posTagger
				.getTaggedSentences(tSentence);

		temp = System.currentTimeMillis() - temp;
		this.lexTime += temp;

		if (taggedSentences.size() == 0) {
			throw new NoTaggedSentenceFoundException(tSentence);
		}
		return taggedSentences;
	}

	/**
	 * This method is responsible for starting the parsing processing.
	 * 
	 * @param taggedSentence
	 *            The tagged sentence.
	 * @param field
	 *            the field that indicates the grammar that will be matched to
	 *            the sentence.
	 * @throws NoParserTreeFoundException
	 */
	private void parseTaggedSentences(TaggedSentence taggedSentence,
			TestCaseTextType field) throws NoParserTreeFoundException {
		long temp = System.currentTimeMillis();

		List<ParserTreeNode> parseTrees = this.parser.parseTaggedSentence(
				taggedSentence, this.grammars.get(field));
		parseNum++;

		temp = System.currentTimeMillis() - temp;
		this.parseTime += temp;

		if (parseTrees.size() == 0) {
			// colocar as parsings possibilities na lista de erros
			throw new NoParserTreeFoundException(taggedSentence, field);
		}
	}

	/**
	 * Indicates if the action faults will be showed.
	 * 
	 * @param bool
	 */
	public void setShowActions(boolean bool) {
		this.showSentences[0] = bool;
	}

	/**
	 * Indicates if the condition faults will be showed.
	 * 
	 * @param bool
	 */
	public void setShowConditions(boolean bool) {
		this.showSentences[1] = bool;
	}

	/**
	 * Indicates if the response faults will be showed.
	 * 
	 * @param bool
	 */
	public void setShowResponses(boolean bool) {
		this.showSentences[2] = bool;
	}

	/**
	 * This method returns an ordered list with the unknown words.
	 * 
	 * @return the ordered list with the unknown words
	 */
	public List<String> getOrderedUnkownWords() {
		String[] array = this.unkownWords.toArray(new String[0]);
		Arrays.sort(array);

		return Arrays.asList(array);
	}

	/**
	 * This method returns the CNL parsing faults list.
	 * 
	 * @return the CNL fault list
	 */
	public HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> getFaultList() {
		return this.parsingFaultsList;
	}

	/**
	 * This method is responsible to process a phone document.
	 * 
	 * @return the document process faults
	 */
	public List<CNLFault> processPhoneDocuments() {
		List<CNLFault> faults = null;

		Collection<PhoneDocument> pDocs = ProjectManagerController
				.getInstance().getCurrentProject().getPhoneDocuments();

		faults = this.processPhoneDocuments(pDocs);

		return faults;
	}

	/**
	 * This method is responsible to process a list of phone documents.
	 * 
	 * @param pDocs
	 *            a list containing the documents to be processed
	 * @return the document process faults
	 */
	public List<CNLFault> processPhoneDocuments(Collection<PhoneDocument> pDocs) {
		List<CNLFault> result = new ArrayList<CNLFault>();
		parsingFaultsList.clear();
		this.sentenceCount = 0;
		this.unkownWords = new HashSet<String>();

		this.parseTime = 0;
		this.lexTime = 0;

		// Set<String> sentences = new HashSet<String>();
		for (PhoneDocument pDoc : pDocs) {
			if (pDoc.isDocumentWellFormed()) {
				for (Feature feature : pDoc.getFeatures()) {
					for (UseCase useCase : feature.getUseCases()) {
						for (Flow flow : useCase.getFlows()) {
							for (FlowStep step : flow.getSteps()) {
								Collection<CNLFault> faults = null;

								if (this.showSentences[0]) {
									faults = processText(
											FileUtil.getFileName(pDoc
													.getDocFilePath()), step,
											step.getUserAction(),
											TestCaseTextType.ACTION);
									result.addAll(faults);
								}
								// sentences.add(step.getUserAction());

//								if (this.showSentences[1]) {
//									faults = processText(
//											FileUtil.getFileName(pDoc
//													.getDocFilePath()), step,
//											step.getSystemCondition(),
//											TestCaseTextType.CONDITION);
//									result.addAll(faults);
//								}
//								// sentences.add(step.getSystemCondition());
//								if (this.showSentences[2]) {
//									faults = processText(
//											FileUtil.getFileName(pDoc
//													.getDocFilePath()), step,
//											step.getSystemResponse(),
//											TestCaseTextType.RESPONSE);
//									result.addAll(faults);
//								}

								// sentences.add(step.getSystemResponse());
							}
						}
					}
				}
			}
		}

		System.out.println("Parser: " + ((float) this.parseTime)
				/ ((float) parseNum));
		System.out.println("Lexicon: " + ((float) this.lexTime)
				/ ((float) lexNum));
		this.printFaults(result);
		return result;
	}

	/**
	 * This method add the text process faults to the logger.
	 * 
	 * @param faults
	 *            the text process faults
	 */
	private void printFaults(List<CNLFault> faults) {
		Collection<PhoneDocument> phoneDocuments = ProjectManagerController
				.getInstance().getCurrentProject().getPhoneDocuments();
		Logger.getInstance().printFaults(faults, phoneDocuments);
	}

	/**
	 * This method is responsible to perform the text process.
	 * 
	 * @param pDocFile
	 * @param step
	 *            a step in use case flow
	 * @param text
	 *            the text to be processed
	 * @param field
	 *            the grammar type
	 * @return a collection that contains the process faults
	 */
	private Collection<CNLFault> processText(String pDocFile, FlowStep step,
			String text, TestCaseTextType field) {
		Collection<CNLFault> faultList = new ArrayList<CNLFault>();

		try {
			if (!text.trim().equals("")) {
				text = this.removeRequirements(text);

				/*
				 * The following piece of code was inserted in order to answer
				 * Prof. Flavia's requests. Not in original CNL Plugin code.
				 */
				// BEGIN
				TextPreprocessor preprocessor = new TextPreprocessor(lexicon);
				text = preprocessor.preprocess(text);
				// END OF INSERTED CODE.

				TokenizedText tokenText = this.textTokenizer.tokenizeText(text);
				for (TokenizedSentence tSentence : tokenText.getSentenceList()) {
					this.sentenceCount++;
					Set<TaggedSentence> taggedSentences = this
							.tagTokenizedSentence(tSentence, this.grammars.get(
									field).getTextualNodes());

					boolean wasParsed = false;
					String str = "";
					HashMap<TaggedSentence, List<ParsingPossibility>> sentenceParsingPossibilities = new HashMap<TaggedSentence, List<ParsingPossibility>>();
					for (TaggedSentence tagSentence : taggedSentences) {
						try {
							this.parseTaggedSentences(tagSentence, field);
							wasParsed = true;
							break;
						} catch (NoParserTreeFoundException e) {
							str += "[ "
									+ POSUtil.getInstance()
											.explainedTaggedSentence(
													e.getTaggedSentence())
									+ " ], ";

							// adding the parsing fault to the list.
							sentenceParsingPossibilities.put(tagSentence,
									this.parser.getParsingPossibilities());
						}

					}
					if (!wasParsed) {
						CNLFault fault = new CNLGrammarFault(tSentence
								.getActualSentence(), step.getId(), pDocFile,
								field, str.substring(0, str.length() - 2));
						faultList.add(fault);
						this.parsingFaultsList.put(tSentence,
								sentenceParsingPossibilities);

					}
				}
			}
		} catch (NoTaggedSentenceFoundException e) {
			Set<String> suggestions = this.posTagger.getSuggestions(e
					.getTokenizedSentence());
			this.unkownWords.addAll(suggestions);
			String str = "";
			for (String suggestion : suggestions) {
				str = ", " + suggestion + str;
			}
			str = str.length() > 0 ? str.substring(2) : "";

			CNLFault fault = new CNLLexionFault(e.getTokenizedSentence()
					.getActualSentence(), step.getId(), pDocFile, field, str);

			faultList.add(fault);
		}

		return faultList;
	}

	/**
	 * This method removes from the text the requirements between brackets.
	 * 
	 * @param text
	 *            The text that contains the requirements to be removed.
	 * @return The text without requirements.
	 */
	private String removeRequirements(String text) {
		String result = text;
		String patternStr = "[^\\u005B\\u005D]+(\\u005B(.*)\\u005D)";

		Pattern p = Pattern.compile(patternStr);

		Matcher m = p.matcher(text);

		if (m.matches()) {
			int endIndex = m.start(1);
			result = text.substring(0, endIndex);
		}

		return result;
	}

	/**
	 * This method returns the sentence count.
	 * 
	 * @return the sentence count
	 */
	public int getSentenceCount() {
		return this.sentenceCount;
	}
}
