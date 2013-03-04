/*
 * @(#)CNLPluginController.java
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
 * dgt                                    Initial creation.
 * lmn3      02/09/2009                   Remotion of showSentences treatment to CNLView
 * lmn3      19/11/2009                   Inclusion of an attribute to indicate if the controller has been started without errors.
 */
package br.ufpe.cin.target.cnl.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.grammar.CNLParser;
import br.ufpe.cin.cnlframework.grammar.GrammarNode;
import br.ufpe.cin.cnlframework.grammar.GrammarReader;
import br.ufpe.cin.cnlframework.grammar.ParserTreeNode;
import br.ufpe.cin.cnlframework.grammar.ParsingPossibility;
import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.cnlframework.postagger.POSTagger;
import br.ufpe.cin.cnlframework.postagger.POSUtil;
import br.ufpe.cin.cnlframework.postagger.TaggedSentence;
import br.ufpe.cin.cnlframework.tokenizer.TextTokenizer;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedSentence;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedText;
import br.ufpe.cin.cnlframework.util.UtilNLP;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.TrieWord;
import br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository;
import br.ufpe.cin.cnlframework.vocabulary.base.LexiconBase;
import br.ufpe.cin.cnlframework.vocabulary.base.LexiconXMLRepository;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdjectiveTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdverbTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;
import br.ufpe.cin.cnlframework.vocabulary.terms.NounTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;
import br.ufpe.cin.target.cnl.dictionary.CNLSynonym;
import br.ufpe.cin.target.cnl.dictionary.ICNLDictionary;
import br.ufpe.cin.target.cnl.dictionary.WordNetDictionary;
import br.ufpe.cin.target.cnl.exceptions.DuplicatedTermInLexiconException;
import br.ufpe.cin.target.cnl.exceptions.InvalidEntryInDictionaryException;
import br.ufpe.cin.target.cnl.exceptions.NoParserTreeFoundException;
import br.ufpe.cin.target.cnl.exceptions.NoTaggedSentenceFoundException;
import br.ufpe.cin.target.cnl.logger.Logger;
import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.FileUtil;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;

public class CNLPluginController
{

    private static CNLPluginController instance;
    
    /** Indicates if the controller has been started without errors */
    public boolean errorStartingController;

    private List<VerbTerm> verbTerms;

    private List<AdjectiveTerm> adjectiveTerms;

    private List<NounTerm> nounTerms;

    private List<AdverbTerm> adverbTerms;

    private LexiconBase lexicon;

    private ICNLDictionary cnlDictionary;

    private HashMap<TestCaseTextType, GrammarNode> grammars;

    private TextTokenizer textTokenizer;

    private POSTagger posTagger;

    private CNLParser parser;

    private int sentenceCount;

    private HashMap<TestCaseTextType, Collection<String>> unkownWords;

    private HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> parsingFaultsList;

    public List<LexicalEntry> getVocabulary() throws RepositoryException
    {
        return this.lexicon.getVocabulary();
    }

    /**
     * The class constructor.
     */
    public CNLPluginController()
    {
        this.unkownWords = new HashMap<TestCaseTextType, Collection<String>>();
        this.hashmapSamples = new HashMap<PartsOfSpeech, List<String>>();
        this.errorStartingController = false;
    }

    /**
     * This method returns a CNLPluginController instance.
     * 
     * @return a CNLPluginController instance
     */
    public static CNLPluginController getInstance()
    {
        if (instance == null)
        {
            instance = new CNLPluginController();
        }
        return instance;
    }

    /**
     * This method is responsible to add a new term to the lexicon.
     * 
     * @param lexicalEntry new term to be added
     * @param ignoreIntersection indicates if the intersection between the lexicon and the entry
     * will be ignored.
     * @throws CNLException
     */
    public void addNewTermToVocabulary(LexicalEntry lexicalEntry, boolean ignoreIntersection)
            throws CNLException
    {
        Set<LexicalEntry> intersection = this.lexicon.getLexicalEntryIntersection(lexicalEntry);

        if (intersection.size() > 0 && !ignoreIntersection)
        {
            throw new DuplicatedTermInLexiconException(lexicalEntry, intersection);
        }

        Set<PartsOfSpeech> lexicalEntryPOS = lexicalEntry.getAvailablePOSTags();

        if (lexicalEntryPOS.size() > 0 && !ignoreIntersection)
        {
            PartsOfSpeech pos = lexicalEntryPOS.iterator().next();

            if (!this.cnlDictionary.isValidWord(lexicalEntry))
            {
                throw new InvalidEntryInDictionaryException(lexicalEntry, pos);
            }
        }

        this.lexicon.addLexicalEntry(lexicalEntry);
    }

    /**
     * This method is responsible to initialize the base components of the plugin.
     * 
     * @throws CNLException
     */
    private void initializeBases() throws CNLException
    {

        CNLProperties properties = CNLProperties.getInstance();

        ILexiconRepository lexRepository = new LexiconXMLRepository(properties.getLexiconFiles());

        this.cnlDictionary = new WordNetDictionary();

        this.lexicon = new LexiconBase(lexRepository);

        GrammarReader grammarInstance = GrammarReader.getInstance();

        this.grammars = new HashMap<TestCaseTextType, GrammarNode>();
        this.grammars.put(TestCaseTextType.ACTION, grammarInstance.readGrammar(properties
                .getActionGrammarFiles()));
        this.grammars.put(TestCaseTextType.RESPONSE, grammarInstance.readGrammar(properties
                .getResponseGrammarFiles()));
        this.grammars.put(TestCaseTextType.CONDITION, grammarInstance.readGrammar(properties
                .getConditionGrammarFiles()));

        this.parsingFaultsList = new HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>>();

        this.verbTerms = this.lexicon.getRepository().getVerbs();

        this.adjectiveTerms = this.lexicon.getRepository().getAdjectives();

        this.nounTerms = this.lexicon.getRepository().getNouns();

        this.adverbTerms = this.lexicon.getRepository().getAdverbs();
    }

    /**
     * This method initializes the text processors TextTokenizer and POSTagger.
     */
    private void initializeProcessors()
    {
        this.textTokenizer = new TextTokenizer();
        this.posTagger = new POSTagger(this.lexicon.getTrieVocabulary());
        this.parser = new CNLParser();
    }

    public void startController() throws CNLException
    {
        try
        {
            this.initializeBases();
            this.initializeProcessors();
            this.errorStartingController = false;
        }
        catch (CNLException e)
        {
            this.errorStartingController = true;
            throw new CNLException("Problem while reloading configuration files. " + e.getMessage());

        }
    }

    long parseTime;

    long lexTime;

    int parseNum;

    int lexNum;

    private HashMap<PartsOfSpeech, List<String>> hashmapSamples;

    /**
     * This method is responsible to get the tagged sentences
     * 
     * @param tSentence a tokenized sentence
     * @param grammarTerms a grammar terms set
     * @return the tagged sentences
     * @throws NoTaggedSentenceFoundException
     */
    public Set<TaggedSentence> tagTokenizedSentence(TokenizedSentence tSentence,
            Set<TrieWord> grammarTerms) throws NoTaggedSentenceFoundException
    {
        long temp = System.currentTimeMillis();
        lexNum++;
        this.posTagger.setGrammarTrieVocabulary(TrieWord.getVocabularyFromCollection(grammarTerms));
        Set<TaggedSentence> taggedSentences = this.posTagger.getTaggedSentences(tSentence);

        temp = System.currentTimeMillis() - temp;
        this.lexTime += temp;

        if (taggedSentences.size() == 0)
        {
            throw new NoTaggedSentenceFoundException(tSentence);
        }
        return taggedSentences;
    }

    /**
     * This method is responsible for starting the parsing processing.
     * 
     * @param taggedSentence The tagged sentence.
     * @param field the field that indicates the grammar that will be matched to the sentence.
     * @throws NoParserTreeFoundException
     */
    private void parseTaggedSentences(TaggedSentence taggedSentence, TestCaseTextType field)
            throws NoParserTreeFoundException
    {
        long temp = System.currentTimeMillis();

        List<ParserTreeNode> parseTrees = this.parser.parseTaggedSentence(taggedSentence,
                this.grammars.get(field), this.getParentNodeNames());
        parseNum++;

        temp = System.currentTimeMillis() - temp;
        this.parseTime += temp;

        if (parseTrees.size() == 0)
        {
            // colocar as parsings possibilities na lista de erros
            throw new NoParserTreeFoundException(taggedSentence, field);
        }
    }

    /**
     * This method returns an ordered list with the unknown words.
     * 
     * @return the ordered list with the unknown words
     */
    public HashMap<TestCaseTextType, Collection<String>> getOrderedUnkownWords()
    {
        return this.unkownWords;
    }

    /**
     * This method returns the CNL parsing faults list.
     * 
     * @return the CNL fault list
     */
    public HashMap<TokenizedSentence, HashMap<TaggedSentence, List<ParsingPossibility>>> getFaultList()
    {
        return this.parsingFaultsList;
    }

    /**
     * This method is responsible to process a use case document.
     * 
     * @return the document process faults
     */
    public List<CNLFault> processUseCaseDocuments()
    {
        List<CNLFault> faults = null;

        Collection<UseCaseDocument> pDocs = ProjectManagerController.getInstance()
                .getCurrentProject().getUseCaseDocuments();

        faults = this.processUseCaseDocuments(pDocs);

        return faults;
    }

    /**
     * This method is responsible to process a list of use case documents.
     * 
     * @param pDocs a list containing the documents to be processed
     * @return the document process faults
     */
    public List<CNLFault> processUseCaseDocuments(Collection<UseCaseDocument> pDocs)
    {
        List<CNLFault> result = new ArrayList<CNLFault>();
        parsingFaultsList.clear();
        this.sentenceCount = 0;
        this.unkownWords = new HashMap<TestCaseTextType, Collection<String>>();

        this.parseTime = 0;
        this.lexTime = 0;

        Set<String> sentences = new HashSet<String>();
        for (UseCaseDocument pDoc : pDocs)
        {
            if (pDoc.isDocumentWellFormed())
            {
                for (Feature feature : pDoc.getFeatures())
                {
                    for (UseCase useCase : feature.getUseCases())
                    {
                        for (Flow flow : useCase.getFlows())
                        {
                            for (FlowStep step : flow.getSteps())
                            {
                                Collection<CNLFault> faults = null;
                                // processing action sentences
                                faults = processText(FileUtil.getFileName(pDoc.getDocFilePath()),
                                        step, step.getUserAction(), TestCaseTextType.ACTION);

                                result.addAll(faults);
                                sentences.add(step.getUserAction());
                                // processing condition sentences
                                faults = processText(FileUtil.getFileName(pDoc.getDocFilePath()),
                                        step, step.getSystemCondition(), TestCaseTextType.CONDITION);

                                result.addAll(faults);
                                sentences.add(step.getSystemCondition());
                                // processing response sentences
                                faults = processText(FileUtil.getFileName(pDoc.getDocFilePath()),
                                        step, step.getSystemResponse(), TestCaseTextType.RESPONSE);

                                result.addAll(faults);
                                sentences.add(step.getSystemResponse());
                            }
                        }
                    }
                }
            }
        }

        System.out.println("Parser: " + ((float) this.parseTime) / ((float) parseNum));
        System.out.println("Lexicon: " + ((float) this.lexTime) / ((float) lexNum));
        this.printFaults(result);
        return result;
    }

    /**
     * This method add the text process faults to the logger.
     * 
     * @param faults the text process faults
     */
    private void printFaults(List<CNLFault> faults)
    {
        Collection<UseCaseDocument> useCaseDocuments = ProjectManagerController.getInstance()
                .getCurrentProject().getUseCaseDocuments();
        Logger.getInstance().printFaults(faults, useCaseDocuments);
    }

    /**
     * This method is responsible to perform the text process.
     * 
     * @param pDocFile
     * @param step a step in use case flow
     * @param text the text to be processed
     * @param testCaseTextType the grammar type
     * @return a collection that contains the process faults
     */
    private Collection<CNLFault> processText(String pDocFile, FlowStep step, String text,
            TestCaseTextType testCaseTextType)
    {
        Collection<CNLFault> faultList = new ArrayList<CNLFault>();

        try
        {
            if (!text.trim().equals(""))
            {
                text = this.removeRequirements(text);

                // INSPECT - Lais added.
                text = this.removeTextBetweenBrackets(text);

                // BEGIN
                // INSPECT - Laís -> comentado a pedido da Prof. Flávia em 25/09/09
                /*
                 * TextPreprocessor preprocessor = new TextPreprocessor(lexicon); text =
                 * preprocessor.preprocess(text);
                 */
                // END OF INSERTED CODE.

                TokenizedText tokenText = this.textTokenizer.tokenizeText(text);
                for (TokenizedSentence tSentence : tokenText.getSentenceList())
                {
                    this.sentenceCount++;
                    Set<TaggedSentence> taggedSentences = this.tagTokenizedSentence(tSentence,
                            this.grammars.get(testCaseTextType).getTextualNodes());

                    boolean wasParsed = false;
                    String str = "";
                    HashMap<TaggedSentence, List<ParsingPossibility>> sentenceParsingPossibilities = new HashMap<TaggedSentence, List<ParsingPossibility>>();

                    for (TaggedSentence tagSentence : taggedSentences)
                    {
                        try
                        {
                            this.parseTaggedSentences(tagSentence, testCaseTextType);
                            wasParsed = true;
                            break;
                        }
                        catch (NoParserTreeFoundException e)
                        {
                            str += "[ "
                                    + POSUtil.getInstance().explainedTaggedSentence(
                                            e.getTaggedSentence()) + " ], ";

                            // adding the parsing fault to the list.
                            sentenceParsingPossibilities.put(tagSentence, this.parser
                                    .getParsingPossibilities());
                        }

                    }
                    if (!wasParsed)
                    {
                        CNLFault fault = new CNLGrammarFault(tSentence.getActualSentence(), step
                                .getId(), pDocFile, testCaseTextType,
                                "Error on the sentence syntax");
                        faultList.add(fault);
                        tSentence.setTestCaseTextType(testCaseTextType);
                        this.parsingFaultsList.put(tSentence, sentenceParsingPossibilities);
                    }
                }
            }
        }
        catch (NoTaggedSentenceFoundException e)
        {
            Set<String> suggestions = this.posTagger.getSuggestions(e.getTokenizedSentence());

            if (this.unkownWords.get(testCaseTextType) == null)
            {
                this.unkownWords.put(testCaseTextType, suggestions);
            }
            else
            {
                this.unkownWords.get(testCaseTextType).addAll(suggestions);
            }

            String str = "";
            for (String suggestion : suggestions)
            {
                str = ", " + suggestion + str;                
            }

            if (str.length() > 0)
            {
                if (suggestions.size() > 1)
                {
                    str = "\"" + str.substring(2) + "\" were not found in the lexicon";
                }
                else
                {
                    str = "\"" + str.substring(2) + "\" was not found in the lexicon";
                }
            }
            else
            {
                str = "";
            }

            CNLFault fault = new CNLLexionFault(e.getTokenizedSentence().getActualSentence(), step
                    .getId(), pDocFile, testCaseTextType, str, suggestions);

            faultList.add(fault);
        }

        return faultList;
    }

    

    /**
     * Removes all occurrences of text between brackets from the original text
     * 
     * @param text the text to be treated.
     * @return original text without the text between brackets.
     */
    // INSPECT - Laís New Method.
    private String removeTextBetweenBrackets(String text)
    {
        String result = text;

        int index = -1;

        boolean hasBrackets = text.contains("(");

        if (hasBrackets)
        {
            result = "";
            while (text.contains("("))
            {
                index = text.indexOf("(");
                result += text.substring(0, index);
                text = text.substring(text.indexOf(")") + 1, text.length());
            }
            result += text;
        }

        return result;
    }

    /**
     * This method removes from the text the requirements between brackets.
     * 
     * @param text The text that contains the requirements to be removed.
     * @return The text without requirements.
     */
    private String removeRequirements(String text)
    {
        String result = text;
        String patternStr = "[^\\u005B\\u005D]+(\\u005B(.*)\\u005D)";

        Pattern p = Pattern.compile(patternStr);

        Matcher m = p.matcher(text);

        if (m.matches())
        {
            int endIndex = m.start(1);
            result = text.substring(0, endIndex);
        }

        return result;
    }

    /**
     * Removes a lexical entry from the repository.
     * 
     * @param lexicalEntry the lexical entry to be removed
     * @throws RepositoryException
     */
    public void removeTerm(LexicalEntry lexicalEntry) throws RepositoryException
    {
        // INSPECT - Lais changed
        this.lexicon.removeLexicalEntry(lexicalEntry);
    }

    /**
     * This method returns the sentence count.
     * 
     * @return the sentence count
     */
    public int getSentenceCount()
    {
        return this.sentenceCount;
    }

    public List<String> getSamples(PartsOfSpeech pos, int quantity)
    {
        List<String> result = this.hashmapSamples.get(pos);

        if (result == null)
        {
            result = new ArrayList<String>();

            try
            {
                List<LexicalEntry> lexicalEntries;
                List<LexicalEntry> possibleResults = new ArrayList<LexicalEntry>();

                lexicalEntries = this.lexicon.getVocabulary();

                for (LexicalEntry lexical : lexicalEntries)
                {
                    if (lexical.getAvailablePOSTags().contains(pos))
                    {
                        possibleResults.add(lexical);
                    }
                }

                if (quantity > possibleResults.size())
                {
                    quantity = possibleResults.size();
                }

                Collections.shuffle(possibleResults);

                possibleResults = possibleResults.subList(0, quantity);

                for (LexicalEntry possibleResult : possibleResults)
                {
                    result.add(possibleResult.getTerm(pos));
                }
            }
            catch (RepositoryException e)
            {
                e.printStackTrace();
                result = new ArrayList<String>();

                return result;
            }

            this.hashmapSamples.put(pos, result);
        }

        return result;
    }
    
    public List<CNLSynonym> getSynonyms(String word){
        try
        {
            return this.validateDictionarySynonyms(this.cnlDictionary.getSynonyms(word));
        }
        catch (RepositoryException e)
        {
            e.printStackTrace();
            return new ArrayList<CNLSynonym>();
        }
    }
    
    /**
     * Removes from the list the synonyms that are not in the lexicon.
     * 
     * @param synonyms the list of synonyms returned by the dictionary
     * @return a list containing only the synonyms that are in the lexicon
     * @throws RepositoryException
     */
    private List<CNLSynonym> validateDictionarySynonyms(List<CNLSynonym> synonyms)
            throws RepositoryException
    {

        List<CNLSynonym> result = new ArrayList<CNLSynonym>();

        for (CNLSynonym cnlSynonym : synonyms)
        {
            // if the synonym is an Adjective synonym
            if (cnlSynonym.getPartsOfSpeech().equals(PartsOfSpeech.JJ))
            {
                List<String> validSynonyms = new ArrayList<String>();

                for (String string : cnlSynonym.getSynonyms())
                {
                    AdjectiveTerm adjective = new AdjectiveTerm(string);

                    if (this.adjectiveTerms.contains(adjective))
                    {
                        validSynonyms.add(string);
                    }
                }
                if (!validSynonyms.isEmpty())
                {
                    result
                            .add(new CNLSynonym(cnlSynonym.getWord(), PartsOfSpeech.JJ,
                                    validSynonyms));
                }
            }
            // if the synonym is a Verb synonym
            else if (cnlSynonym.getPartsOfSpeech().equals(PartsOfSpeech.VB))
            {
                List<String> validSynonyms = new ArrayList<String>();

                for (String string : cnlSynonym.getSynonyms())
                {
                    VerbTerm verb = new VerbTerm(string, UtilNLP.getPast(string), UtilNLP
                            .getPastParticiple(string), UtilNLP.getGerund(string), UtilNLP
                            .getThirdPerson(string), string);

                    if (this.verbTerms.contains(verb))
                    {
                        validSynonyms.add(string);
                    }
                }
                if (!validSynonyms.isEmpty())
                {
                    result
                            .add(new CNLSynonym(cnlSynonym.getWord(), PartsOfSpeech.VB,
                                    validSynonyms));
                }
            }
            // if the synonym is a Noun synonym
            else if (cnlSynonym.getPartsOfSpeech().equals(PartsOfSpeech.NN))
            {
                List<String> validSynonyms = new ArrayList<String>();

                for (String string : cnlSynonym.getSynonyms())
                {
                    NounTerm noun = new NounTerm(string, UtilNLP.getPluralForm(string));

                    if (this.nounTerms.contains(noun))
                    {
                        validSynonyms.add(string);
                    }
                }
                if (!validSynonyms.isEmpty())
                {
                    result
                            .add(new CNLSynonym(cnlSynonym.getWord(), PartsOfSpeech.NN,
                                    validSynonyms));
                }
            }
            // if the synonym is a Adverb synonym
            else if (cnlSynonym.getPartsOfSpeech().equals(PartsOfSpeech.ADV))
            {
                List<String> validSynonyms = new ArrayList<String>();

                for (String string : cnlSynonym.getSynonyms())
                {
                    AdverbTerm adverb = new AdverbTerm(string);

                    if (this.adverbTerms.contains(adverb))
                    {
                        validSynonyms.add(string);
                    }
                }
                if (!validSynonyms.isEmpty())
                {
                    result.add(new CNLSynonym(cnlSynonym.getWord(), PartsOfSpeech.ADV,
                            validSynonyms));
                }
            }
        }
        return result;
    }
    
  //INSPECT - Lais new method
    public List<String> getParentNodeNames()
    {
        List<String> result = new ArrayList<String>();
        
        String [] parentNodeNames = CNLProperties.getInstance().getProperty("parent_grammar_nodes").split(",");
        
        for (int i = 0; i < parentNodeNames.length; i++)
        {
            result.add(parentNodeNames[i]);
        }
        
        return result;
       
    }
    
    //INSPECT - Lais new method
    
    /**
     * Gets the errorStartingController value.
     *
     * @return Returns the errorStartingController.
     */
    public boolean isErrorStartingController()
    {
        return errorStartingController;
    }

    /**
     * Sets the errorStartingController value.
     *
     * @param errorStartingController The errorStartingController to set.
     */
    public void setErrorStartingController(boolean errorStartingController)
    {
        this.errorStartingController = errorStartingController;
    }

}
