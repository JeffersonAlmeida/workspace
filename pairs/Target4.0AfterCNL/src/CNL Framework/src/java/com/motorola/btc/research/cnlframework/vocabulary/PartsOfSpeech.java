/*
 * @(#)PartsOfSpeech.java
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
package com.motorola.btc.research.cnlframework.vocabulary;

/**
 * This class enumerates all the parts of speech tags.
 * @author
 *
 */
public enum PartsOfSpeech {
	
    CD,  // Cardinal
	CJ,  // Conjunction
	DT,  // Determiner
	NG,  // Negation 'Not'
	DO,  // Auxiliar 'Do'
	EX,  // Existential There
	NN,  // Noun
	NNS, // Plural noun
	OD,  // Ordinal
	PP,  // Preposition, to, from, by, on, in, etc.
	JJ,  // Adjective
	VB,  // base form
	VBD, // past tense
	VBG, // gerund
	VBN, // past participle
	VBP, // non-3rd person singular present
	VBZ,  // 3rd person singular present
	
	/*
	 * VERB TO BE
	 */
	VTB,   // 'be'
	VTBDZ, // I/He/She 'was' 
	VTBDP, // We/You/They 'were' 
	VTBG,  // 'being'
	VTBN,  // 'been'
	VTBP1, // I 'am'
	VTBP,  // You/We/They 'are'
	VTBZ,   // He/She 'is'
	
	// Grammar term
	GRAMMAR_TERM
}


/*  1.	CC	Coordinating conjunction
	2.	CD	Cardinal number
	3.	DT	Determiner
	4.	EX	Existential there
	5.	FW	Foreign word
	6.	IN	Preposition or subordinating conjunction
	7.	JJ	Adjective
	8.	JJR	Adjective, comparative
	9.	JJS	Adjective, superlative
	10.	LS	List item marker
	11.	MD	Modal
	12.	NN	Noun, singular or mass
	13.	NNS	Noun, plural
	14.	NP	Proper noun, singular
	15.	NPS	Proper noun, plural
	16.	PDT	Predeterminer
	17.	POS	Possessive ending
	18.	PP	Personal pronoun
	19.	PP$	Possessive pronoun
	20.	RB	Adverb
	21.	RBR	Adverb, comparative
	22.	RBS	Adverb, superlative
	23.	RP	Particle
	24.	SYM	Symbol
	25.	TO	to
	26.	UH	Interjection
	27.	VB	Verb, base form
	28.	VBD	Verb, past tense
	29.	VBG	Verb, gerund or present participle
	30.	VBN	Verb, past participle
	31.	VBP	Verb, non-3rd person singular present
	32.	VBZ	Verb, 3rd person singular present
	33.	WDT	Wh-determiner
	34.	WP	Wh-pronoun
	35.	WP$	Possessive wh-pronoun
	36.	WRB	Wh-adverb
 */
