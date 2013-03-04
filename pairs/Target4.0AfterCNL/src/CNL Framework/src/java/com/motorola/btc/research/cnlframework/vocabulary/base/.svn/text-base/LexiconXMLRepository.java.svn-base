/*
 * @(#)LexiconXMLRepository.java
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
package com.motorola.btc.research.cnlframework.vocabulary.base;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import com.motorola.btc.research.cnlframework.exceptions.RepositoryException;
import com.motorola.btc.research.cnlframework.exceptions.RepositoryInitializationException;
import com.motorola.btc.research.cnlframework.util.UtilNLP;
import com.motorola.btc.research.cnlframework.vocabulary.PartsOfSpeech;
import com.motorola.btc.research.cnlframework.vocabulary.terms.AdjectiveTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.CardinalTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.DeterminerTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry;
import com.motorola.btc.research.cnlframework.vocabulary.terms.NounTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.PrepositionTerm;
import com.motorola.btc.research.cnlframework.vocabulary.terms.VerbTerm;

/**
 * This class implements the ILexiconRepository interface and creates a lexicon from a XML file. 
 * @author Dante Gama Torres
 * 
 */
public class LexiconXMLRepository implements ILexiconRepository {

	private Document document;

	private String fileName;

	private NodeList nouns;

	private NodeList verbs;

	private NodeList determiners;

	private NodeList adjectives;
	
	private NodeList qualifiers;

	private NodeList prepositions;

	private NodeList particles;
	
	private NodeList cardinals;

	/**
	 * This method is responsible to load the lexicon according to the given XML file. 
	 * @param fileName a XML file containing the data for the lexicon. 
	 * @throws RepositoryException
	 */
	public LexiconXMLRepository(String[] fileName) throws RepositoryException {

		this.fileName = fileName[0];
		DocumentBuilder docBuilder;
		try {
			docBuilder = DocumentBuilderFactory.newInstance()
					.newDocumentBuilder();

			this.document = docBuilder.parse(new File(this.fileName));
		} catch (Exception e) {

			throw new RepositoryInitializationException(
					LexiconXMLRepository.class.getName(), e);
		}
		
		Element root = (Element) this.document.getDocumentElement();
		
		this.nouns = root.getElementsByTagName("noun");
		this.verbs = root.getElementsByTagName("verb");
		this.determiners = root.getElementsByTagName("determiner");
		this.adjectives = root.getElementsByTagName("adjective");
		this.qualifiers = root.getElementsByTagName("qualifier");
		this.prepositions = root.getElementsByTagName("preposition");
		this.particles = root.getElementsByTagName("particle");
		this.cardinals = root.getElementsByTagName("cardinal");
	}

	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getParticles()
	 */
	public List<LexicalEntry> getParticles() throws RepositoryException {
		ArrayList<LexicalEntry> list = new ArrayList<LexicalEntry>();

		for (int i = 0; i < this.particles.getLength(); i++) {
			try {
				LexicalEntry term = this
						.readXMLParticleTerm((Element) this.particles.item(i));

				list.add(term);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return list;
	}
	
	
	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getCardinals()
	 */
	public List<CardinalTerm> getCardinals() throws RepositoryException {
        ArrayList<CardinalTerm> list = new ArrayList<CardinalTerm>();

        for (int i = 0; i < this.cardinals.getLength(); i++) {
            CardinalTerm term = this
                    .readXMLCardinalTerm((Element) this.cardinals.item(i));
            list.add(term);
        }
        return list;
    }
	
	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getPrepositions()
	 */
	public List<PrepositionTerm> getPrepositions() throws RepositoryException {
		ArrayList<PrepositionTerm> list = new ArrayList<PrepositionTerm>();

		for (int i = 0; i < this.prepositions.getLength(); i++) {
			PrepositionTerm term = this
					.readXMLPrepositionTerm((Element) this.prepositions.item(i));
			list.add(term);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getDeterminers()
	 */
	public List<DeterminerTerm> getDeterminers() throws RepositoryException {
		ArrayList<DeterminerTerm> list = new ArrayList<DeterminerTerm>();

		for (int i = 0; i < this.determiners.getLength(); i++) {
			DeterminerTerm term = this
					.readXMLDeterminerTerm((Element) this.determiners.item(i));
			list.add(term);
		}
		return list;
	}
	
	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getQualifiers()
	 */
	public List<AdjectiveTerm> getQualifiers() throws RepositoryException {
		ArrayList<AdjectiveTerm> list = new ArrayList<AdjectiveTerm>();

		for (int i = 0; i < this.adjectives.getLength(); i++) {
			AdjectiveTerm term = this
					.readXMLAdjectiveTerm((Element) this.adjectives.item(i));
			list.add(term);
		}
		for (int i = 0; i < this.qualifiers.getLength(); i++) {
			AdjectiveTerm term = this
					.readXMLAdjectiveTerm((Element) this.qualifiers.item(i));
			list.add(term);
		}
		
		return list;
	}
	
	/**
	 * This method returns a lexical entry of the particle type, which represents 
	 * the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return a lexical entry corresponding to the given XML element 
	 */
	private LexicalEntry readXMLParticleTerm(Element element) {

		NodeList nodeList = element.getElementsByTagName("term");
		HashMap<PartsOfSpeech, String> termsMap = new HashMap<PartsOfSpeech, String>();
		for (int i = 0; i < nodeList.getLength(); i++) {
			Element term = (Element) nodeList.item(i);
			String posAttribute = term.getAttribute("pos");
			if (posAttribute != null && !posAttribute.equals("")) {
				termsMap.put(PartsOfSpeech.valueOf(posAttribute.trim()), term
						.getTextContent().trim());
			}
		}
		return new LexicalEntry(termsMap);
	}
	
	/**
	 * This method returns a cardinal term which represents 
	 * the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return a cardinal term corresponding to the given XML element 
	 */
	private CardinalTerm readXMLCardinalTerm(Element element) {

        Element term = (Element) element.getElementsByTagName("term").item(0);
        return new CardinalTerm(term.getTextContent().trim());
    }

	/**
	 * This method returns a preposition term which represents 
	 * the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return a preposition term corresponding to the given XML element 
	 */
	private PrepositionTerm readXMLPrepositionTerm(Element element) {

		Element term = (Element) element.getElementsByTagName("term").item(0);
		return new PrepositionTerm(term.getTextContent());
	}

	/**
	 * This method returns an adjective term which represents 
	 * the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return an adjective term corresponding to the given XML element 
	 */
	private AdjectiveTerm readXMLAdjectiveTerm(Element element) {

		Element term = (Element) element.getElementsByTagName("term").item(0);
		return new AdjectiveTerm(term.getTextContent());
	}
	
	/**
	 * This method returns a determiner term which represents 
	 * the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return a determiner term corresponding to the given XML element 
	 */
	private DeterminerTerm readXMLDeterminerTerm(Element element) {

		Element term = (Element) element.getElementsByTagName("term").item(0);
		return new DeterminerTerm(term.getTextContent());
	}

	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getNouns()
	 */
	public List<NounTerm> getNouns() throws RepositoryException {

		ArrayList<NounTerm> list = new ArrayList<NounTerm>();

		for (int i = 0; i < this.nouns.getLength(); i++) {
			NounTerm term = this.readXMLNounTerm((Element) this.nouns.item(i));
			list.add(term);
		}
		return list;
	}

	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#getVerbs()
	 */
	public List<VerbTerm> getVerbs() {

		ArrayList<VerbTerm> list = new ArrayList<VerbTerm>();

		for (int i = 0; i < this.verbs.getLength(); i++) {
			VerbTerm term = this.readXMLVerbTerm((Element) this.verbs.item(i));
			list.add(term);
		}
		return list;
	}
	
	/**
	 * 
	 * This method returns a noun term which represents the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return a noun term corresponding to the given XML element 
	 */
	private NounTerm readXMLNounTerm(Element element)
			throws RepositoryException {

		NounTerm nounTerm = null;

		Element term = (Element) element.getElementsByTagName("term").item(0);
		Element plural = (Element) element.getElementsByTagName("plural").item(
				0);

		String termStr = term.getTextContent().trim();
		String pluralStr = plural.getTextContent().trim();
		if (pluralStr.equals("")) {
			pluralStr = UtilNLP.getPluralForm(termStr);
		}

		nounTerm = new NounTerm(termStr, pluralStr);

		return nounTerm;
	}

	/**
	 * This method returns a verb term which represents the element passed as parameter.
	 * @param element a XML element to be loaded.
	 * @return a verb term corresponding to the given XML element 
	 */
	private VerbTerm readXMLVerbTerm(Element element) {

		VerbTerm verbTerm = null;

		Element term = (Element) element.getElementsByTagName("term").item(0);
		Element past = (Element) element.getElementsByTagName("past").item(0);
		Element participle = (Element) element.getElementsByTagName(
				"participle").item(0);
		Element gerund = (Element) element.getElementsByTagName("gerund").item(
				0);
		Element thirdPerson = (Element) element.getElementsByTagName(
				"thirdperson").item(0);

		String baseFormStr = term.getTextContent();
		String pastStr = past.getTextContent();
		String participleStr = participle.getTextContent();
		String gerundStr = gerund.getTextContent();
		String thirdPersonStr = thirdPerson.getTextContent();

		if (gerundStr.equals("")) {
			gerundStr = UtilNLP.getGerund(baseFormStr);
		}

		if (thirdPersonStr.equals("")) {
			thirdPersonStr = UtilNLP.getThirdPerson(baseFormStr);
		}

		verbTerm = new VerbTerm(baseFormStr, pastStr, participleStr, gerundStr,
				thirdPersonStr, baseFormStr);

		return verbTerm;
	}
	
	/* (non-Javadoc)
	 * @see com.motorola.btc.research.cnlframework.vocabulary.base.ILexiconRepository#addLexicalEntry(com.motorola.btc.research.cnlframework.vocabulary.terms.LexicalEntry)
	 */
	public void addLexicalEntry(LexicalEntry lexicalEntry)
			throws RepositoryException {

        Node addedNode = null;
		if (lexicalEntry instanceof NounTerm) {
            addedNode = this.getXMLNoun((NounTerm) lexicalEntry);
		} else if (lexicalEntry instanceof AdjectiveTerm) {
            addedNode = this.getXMLQualifier((AdjectiveTerm) lexicalEntry);
		} else if (lexicalEntry instanceof VerbTerm) {
            addedNode = this.getXMLVerb((VerbTerm) lexicalEntry);
        } else {
			throw new NotImplementedException();
		}
        this.appenChildToRoot(addedNode);
		try {            
			this.saveXMLDocument(this.document, this.fileName);
			
		} catch (TransformerException e) {
            this.document.getDocumentElement().removeChild(addedNode);
			throw new RepositoryException("The data could not be persisted!");
		}
	}
	
	/**
	 * This method is used when adding a verb term to the lexicon. It receives a VerbTerm 
	 * object and returns its XML node representation.
	 * @param verbTerm the verb term to be added.
	 * @return the XML node representation of the verb term
	 */
	private Node getXMLVerb(VerbTerm verbTerm) {
        Element verbElement = this.document.createElement("verb");

        Element termElement = this.document.createElement("term");
        termElement.setTextContent(verbTerm.getTerm());
        
        Element pastElement = this.document.createElement("past");
        pastElement.setTextContent(verbTerm.getPast());
        
        Element participleElement = this.document.createElement("participle");
        participleElement.setTextContent(verbTerm.getParticiple());
        
        Element gerundElement = this.document.createElement("gerund");
        gerundElement.setTextContent(verbTerm.getGerund());
        
        Element thirdPersondElement = this.document.createElement("thirdperson");
        thirdPersondElement.setTextContent(verbTerm.getThirdPerson());

        this.appendText(verbElement, "\n\t");
        verbElement.appendChild(termElement);
        this.appendText(verbElement, "\n\t");
        verbElement.appendChild(pastElement);
        this.appendText(verbElement, "\n\t");
        verbElement.appendChild(participleElement);
        this.appendText(verbElement, "\n\t");
        verbElement.appendChild(gerundElement);
        this.appendText(verbElement, "\n\t");
        verbElement.appendChild(thirdPersondElement);
        this.appendText(verbElement, "\n");
        
        return verbElement;
    }
	
	/**
	 * This method is used when adding a noun term to the lexicon. It receives a NounTerm 
	 * object and returns its XML node representation.
	 * @param nounTerm the noun term to be added.
	 * @return the XML node representation of the noun term
	 */
	private Node getXMLNoun(NounTerm nounTerm) {
		Element nounElement = this.document.createElement("noun");

		Element termElement = this.document.createElement("term");
		termElement.setTextContent(nounTerm.getSingular());
		
		Element pluralElement = this.document.createElement("plural");
		pluralElement.setTextContent(nounTerm.getPlural());

		this.appendText(nounElement, "\n\t");
		nounElement.appendChild(termElement);
		this.appendText(nounElement, "\n\t");
		nounElement.appendChild(pluralElement);
		this.appendText(nounElement, "\n");
        
        return nounElement;
	}

	/**
	 * This method is used when adding a qualifier term to the lexicon. It receives a QualifierTerm 
	 * object and returns its XML node representation.
	 * @param qualifierTerm the qualifier term to be added.
	 * @return the XML node representation of the qualifier term
	 */
	private Node getXMLQualifier(AdjectiveTerm qualifier) {
		Element qualifierElement = this.document.createElement("qualifier");

		Element termElement = this.document.createElement("term");
		termElement.setTextContent(qualifier.getTerm());

		this.appendText(qualifierElement, "\n\t");
		qualifierElement.appendChild(termElement);
		this.appendText(qualifierElement, "\n");
		
        return qualifierElement;
	}

	/**
	 * This method appends the given node to the XML doument.
	 * @param node the node to be appended.
	 */
	private void appenChildToRoot(Node node) {
		this.document.getDocumentElement().appendChild(node);
		this.appendText(this.document.getDocumentElement(), "\n");
	}
	
	/**
	 * This method appends the given text to the node passed as parameter.
	 * @param node the node where the text will be appended.
	 * @param str the text to be appended.
	 */
	private void appendText(Node node, String str) {
		node.appendChild(this.document.createTextNode(str));
	}
	
	/**
	 * This method is responsible to update the lexicon XML file
	 * @param document the lexicon XML file
	 * @param fileName the XML file name
	 * @throws TransformerException
	 */
	private void saveXMLDocument(Document document, String fileName)
			throws TransformerException {

		TransformerFactory tranFactory = TransformerFactory.newInstance();
		Transformer aTransformer = tranFactory.newTransformer();

		Source src = new DOMSource(document);
		Result dest = new StreamResult(new File(fileName));
		aTransformer.transform(src, dest);
	}
	
	/**
	 * Main for tests.
	 * @param args
	 * @throws RepositoryException
	 */
	public static void main(String[] args) throws RepositoryException {
		LexiconXMLRepository rep = new LexiconXMLRepository(new String[] {".\\bases\\lexicon.xml"});
		
		rep.addLexicalEntry(new NounTerm("test", "tests"));
		
		System.out.println("End!");
	}
}
