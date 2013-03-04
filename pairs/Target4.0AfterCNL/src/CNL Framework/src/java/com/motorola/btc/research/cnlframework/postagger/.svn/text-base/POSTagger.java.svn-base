/*
 * @(#)POSTagger.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 19, 2008   LIBqq41824   Initial creation.
 */
package com.motorola.btc.research.cnlframework.postagger;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.motorola.btc.research.cnlframework.exceptions.RepositoryException;
import com.motorola.btc.research.cnlframework.tokenizer.TextTokenizer;
import com.motorola.btc.research.cnlframework.tokenizer.TokenizedSentence;
import com.motorola.btc.research.cnlframework.tokenizer.TokenizedText;
import com.motorola.btc.research.cnlframework.vocabulary.TrieWord;
import com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository;
import com.motorola.btc.research.cnlframework.vocabulary.base.LexiconBase;
import com.motorola.btc.research.cnlframework.vocabulary.base.LexiconXMLRepository;
import com.motorola.btc.research.cnlframework.vocabulary.trie.TrieVocabulary;

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
    private boolean getTaggedSentencesAuxiliar(TokenizedSentence tSentence, Matcher matcher,
            Set<TaggedSentence> taggedSentences, Stack<TaggedTerm> currentTerms, String term,
            int termStart, int termEnd, int nextRead, Set<String> suggestions)
    {

        char[] strArray = this.processString(term).toCharArray();
        Set<TrieWord> tWords = this.trieVocabulary.getWords(strArray);

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
    
    /**
     * This method is responsible to remove all the punctuation marks from the text. 
     * @param str the text to be processed
     * @return the result processed text
     */
    private String processString(String str)
    {
        // removing any apostrophes (' " etc.)
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
                .tokenizeText("Open a WMDRM content, asshole. Do not confirm the fucking operation, motherfucker.");

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
