/*
 * @(#)POSTagger.java
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
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package br.ufpe.cin.cnlframework.postagger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.tokenizer.TextTokenizer;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedSentence;
import br.ufpe.cin.cnlframework.tokenizer.TokenizedText;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.TrieWord;
import br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository;
import br.ufpe.cin.cnlframework.vocabulary.base.LexiconBase;
import br.ufpe.cin.cnlframework.vocabulary.base.LexiconXMLRepository;
import br.ufpe.cin.cnlframework.vocabulary.terms.CardinalTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.NounTerm;
import br.ufpe.cin.cnlframework.vocabulary.trie.TrieVocabulary;

/**
 * This class represents a Parts of Speech tagger.
 * @author 
 *
 */
public class POSTagger
{
    private TrieVocabulary<TrieWord> trieVocabulary;

    // Specific grammar vocabulary
    private TrieVocabulary<TrieWord> grammarTrieVocabulary;
    
    /**
     * Class constructor.
     * @param trieVocabulary
     */    
    public POSTagger(TrieVocabulary<TrieWord> trieVocabulary)
    {

        this.trieVocabulary = trieVocabulary;

        this.grammarTrieVocabulary = new TrieVocabulary<TrieWord>();
    }
    
    /**
     * Returns a list containing the tagged sentences according to the given
     * tokenized sentence.
     * @param tSentence the tokenized sentence to be tagged
     * @return a list of tagged sentences
     */
    public Set<TaggedSentence> getTaggedSentences(TokenizedSentence tSentence)
    {

        Set<TaggedSentence> result = new HashSet<TaggedSentence>();

        Pattern sentenceSeparator = Pattern.compile("\\p{Blank}+");

        Matcher matcher = sentenceSeparator.matcher(tSentence.getActualSentence());

        this.getTaggedSentences(tSentence, matcher, 0, result, new Stack<TaggedTerm>(), null);

        return result;
    }
    
    /**
     * This method returns the words in the sentence that were not found in the lexicon
     * @param tSentence the sentence to be analyzed
     * @return the missing words
     */
    public Set<String> getSuggestions(TokenizedSentence tSentence)
    {

        Set<String> result = new HashSet<String>();

        Pattern sentenceSeparator = Pattern.compile("\\p{Blank}+");

        Matcher matcher = sentenceSeparator.matcher(tSentence.getActualSentence());

        this.getTaggedSentences(tSentence, matcher, 0, new HashSet<TaggedSentence>(),
                new Stack<TaggedTerm>(), result);

        return result;
    }
    
    /**
     * Returns a list containing the tagged sentences according to the given
     * tokenized sentence.
     * @param tSentence
     * @param matcher
     * @param start
     * @param taggedSentences
     * @param currentTerms
     * @param suggestions
     */
    private void getTaggedSentences(TokenizedSentence tSentence, Matcher matcher, int start,
            Set<TaggedSentence> taggedSentences, Stack<TaggedTerm> currentTerms,
            Set<String> suggestions)
    {

        String sentence = tSentence.getActualSentence();
        
        if (start >= sentence.length())
        {

            taggedSentences.add(new TaggedSentence(new ArrayList<TaggedTerm>(currentTerms),
                    tSentence));
        }
        else
        {

            int numOfSentences = taggedSentences.size();
            String str = "";
            int pos = start;
            while (matcher.find(pos))
            {

                str += " " + sentence.substring(pos, matcher.start());
                pos = matcher.end();

                if (this.getTaggedSentencesAuxiliar(tSentence, matcher, taggedSentences,
                        currentTerms, str, start, matcher.start(), matcher.end(), suggestions))
                {
                    break;
                }
            }

            str += " " + sentence.substring(pos, sentence.length());

            this.getTaggedSentencesAuxiliar(tSentence, matcher, taggedSentences, currentTerms, str,
                    start, sentence.length(), sentence.length(), suggestions);

            if (suggestions != null)
            {
                if (numOfSentences == taggedSentences.size())
                {
                    int nextRead = 0;
                    if (matcher.find(start))
                    {
                        suggestions.add(this.processString(sentence.substring(start, matcher
                                .start())));
                        nextRead = matcher.end();
                    }
                    else
                    {
                        suggestions.add(this.processString(sentence.substring(start, sentence
                                .length())));
                        nextRead = sentence.length();
                    }
                    this.getTaggedSentences(tSentence, matcher, nextRead, taggedSentences,
                            currentTerms, suggestions);
                }
            }
        }
    }
    
    /**
     * Returns a list containing the tagged sentences according to the given
     * tokenized sentence.
     * @param tSentence
     * @param matcher
     * @param taggedSentences
     * @param currentTerms
     * @param term
     * @param termStart
     * @param termEnd
     * @param nextRead
     * @param suggestions
     * @return
     */
    //INSPECT Lais - adicionado tratamento para considerar termos entre "" como nomes;
    private boolean getTaggedSentencesAuxiliar(TokenizedSentence tSentence, Matcher matcher,
            Set<TaggedSentence> taggedSentences, Stack<TaggedTerm> currentTerms, String term,
            int termStart, int termEnd, int nextRead, Set<String> suggestions)
    {
        
        // INSPECT - Lais - start of inserted code
        char[] strArray = null;
        Set<TrieWord> tWords = null;

        String word = term;
        term = term.replaceAll("\\u201C", "\"");
        term = term.replaceAll("\\u201D", "\"");
        term = term.replace(",", "");

        // if the term is between double quotation, it will be considered as a noun
        if (term.trim().startsWith("\"") && term.trim().endsWith("\"") && this.hasTwoDoubleQuotation(term))
        {
            strArray = term.toCharArray();
            tWords = new HashSet<TrieWord>();
            tWords.add(new TrieWord(word, new NounTerm(word, word), PartsOfSpeech.NN));
        }
        // INSPECT Lais - end of inserted code.
        
        //INSPECT - Lais - start of inserted code
        else if(isCardinal(term.trim()))
        {
            strArray = term.toCharArray();
            tWords = new HashSet<TrieWord>();
            tWords.add(new TrieWord(word, new CardinalTerm(word), PartsOfSpeech.CD));
        }
        //INSPECT - Lais - end of inspected code
        
        else
        {
            strArray = this.processString(term).toCharArray();
            tWords = this.trieVocabulary.getWords(strArray);
        }

        Set<TrieWord> grammarWords = this.grammarTrieVocabulary.getWords(strArray);

        if (grammarWords != null)
        {
            if (tWords == null)
            {
                tWords = grammarWords;
            }
            else
            {
                tWords.addAll(grammarWords);
            }
        }

        if (tWords != null)
        {
            for (TrieWord tWord : tWords)
            {

                currentTerms.push(new TaggedTerm(tWord, termStart, termEnd));

                this.getTaggedSentences(tSentence, matcher, nextRead, taggedSentences,
                        currentTerms, suggestions);

                currentTerms.pop();
            }
        }
        return this.grammarTrieVocabulary.isEndWord(strArray)
                && this.trieVocabulary.isEndWord(strArray);
    }
    
    //INSPECT - Lais New Method
    /**
     * 
     * Verifies if the term is a cardinal.
     * @param term the term to be checked
     * @return the result of the verification
     */
    private boolean isCardinal(String term)
    {
        boolean result = false;

        try
        {
            Integer.parseInt(term);
            result = true;
        }
        catch (NumberFormatException ex)
        {
            result = false;
        }
        
        return result;
    }
    
    //INSPECT - Lais New Method
    private boolean hasTwoDoubleQuotation(String term){
        int numberOfQuotations = 0;
        for (int i = 0; i < term.length() && numberOfQuotations <= 2; i++)
        {
            if(term.charAt(i) == '\"'){
                numberOfQuotations++;
            }
            
        }
        
        if(numberOfQuotations == 2){
            return true;
        }
        else{
            return false;
        }
        
    }
    
    /**
     * This method is responsible to remove all the punctuation marks from the text. 
     * @param str the text to be processed
     * @return the result processed text
     */
    private String processString(String str)
    {
        // removing any apostrophes (' " etc.)
        //right single quotation (u2019), left single quotation (u2018), prime (u2032), 
        //right double quotation (u201D), left double quotation (u201C) double prime (u2033), bullet (u2022), hyphenation point (u2027)  
        str = str.trim().replaceAll("[\\u2019\\u2018\\u2032\\u201D\\u201C\\u2033\\u2022\\u2027]+",
                "");
        str = str.replaceAll("\\p{Punct}+", "");
        str = str.toLowerCase();

        return str;
    }
    
    /**
     * 
     * @param grammarTrieVocabulary
     */
    public void setGrammarTrieVocabulary(TrieVocabulary<TrieWord> grammarTrieVocabulary)
    {
        this.grammarTrieVocabulary = grammarTrieVocabulary;
    }
    
    /**
     * Main for tests.
     * @param args
     * @throws RepositoryException
     */
    public static void main(String[] args) throws RepositoryException
    {

        ILexiconRepository lexRepository = new LexiconXMLRepository(
                new String[] { ".\\bases\\lexicon.xml" });

        LexiconBase lexicon = new LexiconBase(lexRepository);

        POSTagger posTagger = new POSTagger(lexicon.getTrieVocabulary());

        TextTokenizer tTokenizer = new TextTokenizer();

        TokenizedText textTokenized = tTokenizer
                .tokenizeText("Open a WMDRM content. Do not confirm the operation.");

        for (TokenizedSentence tSentence : textTokenized.getSentenceList())
        {
            Set<TaggedSentence> tagSents = posTagger.getTaggedSentences(tSentence);

            if (tagSents.size() > 0)
            {
                for (TaggedSentence ts : tagSents)
                {
                    System.out.println(ts.toString());
                }
            }
            else
            {
                Set<String> suggestions = posTagger.getSuggestions(tSentence);
                System.out.print("Suggestions: ");
                String str = "";
                for (String sug : suggestions)
                {
                    str = ", " + sug + str;
                }
                System.out.println(str.length() > 0 ? str.substring(2) : "");
            }

        }

        System.out.println("End!");

    }

}
