/*
 * @(#)LexiconXMLRepository.java
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
 * fsf2      Sep 16, 2009                Changed to order words in the repository.
 *                                       Added support for adverbs.
 */
package br.ufpe.cin.cnlframework.vocabulary.base;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
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

import br.ufpe.cin.cnlframework.exceptions.RepositoryException;
import br.ufpe.cin.cnlframework.exceptions.RepositoryInitializationException;
import br.ufpe.cin.cnlframework.util.UtilNLP;
import br.ufpe.cin.cnlframework.vocabulary.PartsOfSpeech;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdjectiveTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.AdverbTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.CardinalTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.DeterminerTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry;
import br.ufpe.cin.cnlframework.vocabulary.terms.NounTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.PrepositionTerm;
import br.ufpe.cin.cnlframework.vocabulary.terms.VerbTerm;

/**
 * This class implements the ILexiconRepository interface and creates a lexicon from a XML file.
 * 
 * @author Dante Gama Torres
 */
public class LexiconXMLRepository implements ILexiconRepository
{
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
    
    private NodeList adverbs;
    
    private Element root;

    /**
     * This method is responsible to load the lexicon according to the given XML file.
     * 
     * @param fileName a XML file containing the data for the lexicon.
     * @throws RepositoryException
     */
    public LexiconXMLRepository(String[] fileName) throws RepositoryException
    {
        this.fileName = fileName[0];
        
        DocumentBuilder docBuilder;
        
        try
        {
            docBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();

            this.document = docBuilder.parse(new File(this.fileName));
        }
        catch (Exception e)
        {
            throw new RepositoryInitializationException("Lexicon XML Repository", e);
        }

        this.root = (Element) this.document.getDocumentElement();

        this.nouns = root.getElementsByTagName("noun");
        this.verbs = root.getElementsByTagName("verb");
        this.determiners = root.getElementsByTagName("determiner");
        this.adjectives = root.getElementsByTagName("adjective");
        this.qualifiers = root.getElementsByTagName("qualifier");
        this.prepositions = root.getElementsByTagName("preposition");
        this.particles = root.getElementsByTagName("particle");
        this.cardinals = root.getElementsByTagName("cardinal");
        this.adverbs = root.getElementsByTagName("adverb");

        try
        {
            this.refactorDocument();
        }
        catch (TransformerException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Comment for method.
     * 
     * @param fileName2
     * @param document2
     * @throws TransformerException
     * @throws RepositoryException
     */
    //INSPECT: Felype - Método novo.
    private void refactorDocument() throws TransformerException, RepositoryException
    {
        this.orderAdjectives();

        this.orderAdverbs();
        
        this.orderDeterminers();

        this.orderNouns();

        this.orderParticles();

        this.orderPrepositions();

        this.orderVerbs();

        this.saveXMLDocument(this.document, this.fileName);
    }

    /**
     * Comment for method.
     * @throws RepositoryException 
     */
    private void orderAdverbs() throws RepositoryException
    {
        List<AdverbTerm> adverbs = this.getAdverbs();

        Collections.sort(adverbs);

        while (this.adverbs.getLength() > 0)
        {
            this.root.removeChild(this.adverbs.item(0));
        }

        for (LexicalEntry lexicalEntry : adverbs)
        {
            this.justAppendEntry(lexicalEntry);
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void orderPrepositions() throws RepositoryException
    {
        List<PrepositionTerm> prepositions = this.getPrepositions();

        Collections.sort(prepositions);

        while (this.prepositions.getLength() > 0)
        {
            this.root.removeChild(this.prepositions.item(0));
        }

        for (PrepositionTerm preposition : prepositions)
        {
            this.justAppendEntry(preposition);
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void orderDeterminers() throws RepositoryException
    {
        List<DeterminerTerm> determiners = this.getDeterminers();

        Collections.sort(determiners);

        while (this.determiners.getLength() > 0)
        {
            this.root.removeChild(this.determiners.item(0));
        }

        for (DeterminerTerm determiner : determiners)
        {
            this.justAppendEntry(determiner);
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void orderVerbs() throws RepositoryException
    {
        List<VerbTerm> verbs = this.getVerbs();

        Collections.sort(verbs);

        while (this.verbs.getLength() > 0)
        {
            this.root.removeChild(this.verbs.item(0));
        }

        for (VerbTerm verb : verbs)
        {
            this.justAppendEntry(verb);
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void orderAdjectives() throws RepositoryException
    {
        List<AdjectiveTerm> adjectives = this.getAdjectives();

        Collections.sort(adjectives);

        while (this.qualifiers.getLength() > 0)
        {
            this.root.removeChild(this.qualifiers.item(0));
        }

        while (this.adjectives.getLength() > 0)
        {
            this.root.removeChild(this.adjectives.item(0));
        }

        for (AdjectiveTerm adjective : adjectives)
        {
            this.justAppendEntry(adjective);
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void orderNouns() throws RepositoryException
    {
        List<NounTerm> nouns = this.getNouns();

        Collections.sort(nouns);

        while (this.nouns.getLength() > 0)
        {
            this.root.removeChild(this.nouns.item(0));
        }

        for (NounTerm noun : nouns)
        {
            this.justAppendEntry(noun);
        }
    }

    /**
     * Comment for method.
     * 
     * @throws RepositoryException
     */
    private void orderParticles() throws RepositoryException
    {
        List<LexicalEntry> particles = this.getParticles();

        Collections.sort(particles);

        while (this.particles.getLength() > 0)
        {
            this.root.removeChild(this.particles.item(0));
        }

        for (LexicalEntry lexicalEntry : particles)
        {
            this.justAppendEntry(lexicalEntry);
        }
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getParticles()
     */
    public List<LexicalEntry> getParticles() throws RepositoryException
    {
        ArrayList<LexicalEntry> list = new ArrayList<LexicalEntry>();

        for (int i = 0; i < this.particles.getLength(); i++)
        {
            try
            {
                LexicalEntry term = this.readXMLParticleTerm((Element) this.particles.item(i));

                if (!list.contains(term))
                {
                    list.add(term);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getParticles()
     */
    
    public List<AdverbTerm> getAdverbs() throws RepositoryException
    {
        ArrayList<AdverbTerm> list = new ArrayList<AdverbTerm>();

        for (int i = 0; i < this.adverbs.getLength(); i++)
        {
            try
            {
                AdverbTerm term = this.readXMLAdverbTerm((Element) this.adverbs.item(i));

                if (!list.contains(term))
                {
                    list.add(term);
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }

        }
        return list;
    }

    /**
     * Comment for method.
     * @param item
     * @return
     */
    private AdverbTerm readXMLAdverbTerm(Element element)
    {
        Element term = (Element) element.getElementsByTagName("term").item(0);
        return new AdverbTerm(term.getTextContent());
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getCardinals()
     */
    public List<CardinalTerm> getCardinals() throws RepositoryException
    {
        ArrayList<CardinalTerm> list = new ArrayList<CardinalTerm>();

        for (int i = 0; i < this.cardinals.getLength(); i++)
        {
            CardinalTerm term = this.readXMLCardinalTerm((Element) this.cardinals.item(i));

            if (!list.contains(term))
            {
                list.add(term);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getPrepositions()
     */
    public List<PrepositionTerm> getPrepositions() throws RepositoryException
    {
        ArrayList<PrepositionTerm> list = new ArrayList<PrepositionTerm>();

        for (int i = 0; i < this.prepositions.getLength(); i++)
        {
            PrepositionTerm term = this.readXMLPrepositionTerm((Element) this.prepositions.item(i));

            if (!list.contains(term))
            {
                list.add(term);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getDeterminers()
     */
    public List<DeterminerTerm> getDeterminers() throws RepositoryException
    {
        ArrayList<DeterminerTerm> list = new ArrayList<DeterminerTerm>();

        for (int i = 0; i < this.determiners.getLength(); i++)
        {
            DeterminerTerm term = this.readXMLDeterminerTerm((Element) this.determiners.item(i));

            if (!list.contains(term))
            {
                list.add(term);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getQualifiers()
     */
    public List<AdjectiveTerm> getAdjectives() throws RepositoryException
    {
        ArrayList<AdjectiveTerm> list = new ArrayList<AdjectiveTerm>();

        for (int i = 0; i < this.adjectives.getLength(); i++)
        {
            AdjectiveTerm term = this.readXMLAdjectiveTerm((Element) this.adjectives.item(i));
            list.add(term);
        }
        for (int i = 0; i < this.qualifiers.getLength(); i++)
        {
            AdjectiveTerm term = this.readXMLAdjectiveTerm((Element) this.qualifiers.item(i));

            if (!list.contains(term))
            {
                list.add(term);
            }
        }

        return list;
    }

    /**
     * This method returns a lexical entry of the particle type, which represents the element passed
     * as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return a lexical entry corresponding to the given XML element
     */
    private LexicalEntry readXMLParticleTerm(Element element)
    {
        NodeList nodeList = element.getElementsByTagName("term");
        HashMap<PartsOfSpeech, String> termsMap = new HashMap<PartsOfSpeech, String>();
        
        for (int i = 0; i < nodeList.getLength(); i++)
        {
            Element term = (Element) nodeList.item(i);
            String posAttribute = term.getAttribute("pos");
            if (posAttribute != null && !posAttribute.equals(""))
            {
                termsMap.put(PartsOfSpeech.valueOf(posAttribute.trim()), term.getTextContent()
                        .trim());
            }
        }
        
        return new LexicalEntry(termsMap);
    }

    /**
     * This method returns a cardinal term which represents the element passed as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return a cardinal term corresponding to the given XML element
     */
    private CardinalTerm readXMLCardinalTerm(Element element)
    {
        Element term = (Element) element.getElementsByTagName("term").item(0);
        return new CardinalTerm(term.getTextContent().trim());
    }

    /**
     * This method returns a preposition term which represents the element passed as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return a preposition term corresponding to the given XML element
     */
    private PrepositionTerm readXMLPrepositionTerm(Element element)
    {
        Element term = (Element) element.getElementsByTagName("term").item(0);
        return new PrepositionTerm(term.getTextContent());
    }

    /**
     * This method returns an adjective term which represents the element passed as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return an adjective term corresponding to the given XML element
     */
    private AdjectiveTerm readXMLAdjectiveTerm(Element element)
    {
        Element term = (Element) element.getElementsByTagName("term").item(0);
        return new AdjectiveTerm(term.getTextContent());
    }

    /**
     * This method returns a determiner term which represents the element passed as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return a determiner term corresponding to the given XML element
     */
    private DeterminerTerm readXMLDeterminerTerm(Element element)
    {
        Element term = (Element) element.getElementsByTagName("term").item(0);
        return new DeterminerTerm(term.getTextContent());
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getNouns()
     */
    public List<NounTerm> getNouns() throws RepositoryException
    {
        ArrayList<NounTerm> list = new ArrayList<NounTerm>();

        for (int i = 0; i < this.nouns.getLength(); i++)
        {
            NounTerm term = this.readXMLNounTerm((Element) this.nouns.item(i));

            if (!list.contains(term))
            {
                list.add(term);
            }
        }
        return list;
    }

    /*
     * (non-Javadoc)
     * @see br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getVerbs()
     */
    public List<VerbTerm> getVerbs()
    {
        ArrayList<VerbTerm> list = new ArrayList<VerbTerm>();

        for (int i = 0; i < this.verbs.getLength(); i++)
        {
            VerbTerm term = this.readXMLVerbTerm((Element) this.verbs.item(i));

            if (!list.contains(term))
            {
                list.add(term);
            }
        }
        return list;
    }

    /**
     * This method returns a noun term which represents the element passed as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return a noun term corresponding to the given XML element
     */
    private NounTerm readXMLNounTerm(Element element) throws RepositoryException
    {
        NounTerm nounTerm = null;

        Element term = (Element) element.getElementsByTagName("term").item(0);
        Element plural = (Element) element.getElementsByTagName("plural").item(0);

        String termStr = term.getTextContent().trim();
        String pluralStr = plural.getTextContent().trim();
        if (pluralStr.equals(""))
        {
            pluralStr = UtilNLP.getPluralForm(termStr);
        }

        nounTerm = new NounTerm(termStr, pluralStr);

        return nounTerm;
    }

    /**
     * This method returns a verb term which represents the element passed as parameter.
     * 
     * @param element a XML element to be loaded.
     * @return a verb term corresponding to the given XML element
     */
    private VerbTerm readXMLVerbTerm(Element element)
    {
        VerbTerm verbTerm = null;

        Element term = (Element) element.getElementsByTagName("term").item(0);
        Element past = (Element) element.getElementsByTagName("past").item(0);
        Element participle = (Element) element.getElementsByTagName("participle").item(0);
        Element gerund = (Element) element.getElementsByTagName("gerund").item(0);
        Element thirdPerson = (Element) element.getElementsByTagName("thirdperson").item(0);

        String baseFormStr = term.getTextContent();
        String pastStr = past.getTextContent();
        String participleStr = participle.getTextContent();
        String gerundStr = gerund.getTextContent();
        String thirdPersonStr = thirdPerson.getTextContent();

        if (gerundStr.equals(""))
        {
            gerundStr = UtilNLP.getGerund(baseFormStr);
        }

        if (thirdPersonStr.equals(""))
        {
            thirdPersonStr = UtilNLP.getThirdPerson(baseFormStr);
        }

        verbTerm = new VerbTerm(baseFormStr, pastStr, participleStr, gerundStr, thirdPersonStr,
                baseFormStr);

        return verbTerm;
    }

    /*
     * (non-Javadoc)
     * @see
     * br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#addLexicalEntry
     * (br.ufpe.cin.cnlframework.vocabulary.terms.LexicalEntry)
     */
    public void addLexicalEntry(LexicalEntry lexicalEntry) throws RepositoryException
    {
        Node addedNode = this.justAppendEntry(lexicalEntry);

        try
        {
            this.saveXMLDocument(this.document, this.fileName);

        }
        catch (TransformerException e)
        {
            this.document.getDocumentElement().removeChild(addedNode);
            throw new RepositoryException("The data could not be persisted!");
        }
    }

    /**
     * Comment for method.
     * 
     * @param lexicalEntry
     * @return
     */
    public Node justAppendEntry(LexicalEntry lexicalEntry)
    {
        Node addedNode = null;

        if (lexicalEntry instanceof NounTerm)
        {
            addedNode = this.getXMLNoun((NounTerm) lexicalEntry);
        }
        else if (lexicalEntry instanceof AdjectiveTerm)
        {
            addedNode = this.getXMLAdjective((AdjectiveTerm) lexicalEntry);
        }
        else if (lexicalEntry instanceof VerbTerm)
        {
            addedNode = this.getXMLVerb((VerbTerm) lexicalEntry);
        }
        else
        {
            addedNode = this.getXMLLexicalEntry(lexicalEntry);
        }

        this.appenChildToRoot(addedNode);

        return addedNode;
    }

    /**
     * Comment for method.
     * 
     * @param lexicalEntry
     * @return
     */
    private Node getXMLLexicalEntry(LexicalEntry lexicalEntry)
    {
        Element element = null;
        
        Element termElement = this.document.createElement("term");

        if (lexicalEntry.getTermsMap() != null && !lexicalEntry.getTermsMap().isEmpty())
        {
            String str = lexicalEntry.getTermsMap().values().iterator().next();
            PartsOfSpeech pos = lexicalEntry.getTermsMap().keySet().iterator().next();
            
            termElement.setTextContent(str);

            if (lexicalEntry instanceof DeterminerTerm)
            {
                element = this.document.createElement("determiner");
            }
            else if (lexicalEntry instanceof AdverbTerm)
            {
                element = this.document.createElement("adverb");
            }
            else if (lexicalEntry instanceof PrepositionTerm)
            {
                element = this.document.createElement("preposition");
            }
            else
            {
                element = this.document.createElement("particle");
                termElement.setAttribute("pos", pos.toString());
            }

            element.appendChild(termElement);
        }

        return element;
    }

    /**
     * This method is used when adding a verb term to the lexicon. It receives a VerbTerm object and
     * returns its XML node representation.
     * 
     * @param verbTerm the verb term to be added.
     * @return the XML node representation of the verb term
     */
    private Node getXMLVerb(VerbTerm verbTerm)
    {
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

        verbElement.appendChild(termElement);
        verbElement.appendChild(pastElement);
        verbElement.appendChild(participleElement);
        verbElement.appendChild(gerundElement);
        verbElement.appendChild(thirdPersondElement);

        return verbElement;
    }

    /**
     * This method is used when adding a noun term to the lexicon. It receives a NounTerm object and
     * returns its XML node representation.
     * 
     * @param nounTerm the noun term to be added.
     * @return the XML node representation of the noun term
     */
    private Node getXMLNoun(NounTerm nounTerm)
    {
        Element nounElement = this.document.createElement("noun");

        Element termElement = this.document.createElement("term");
        termElement.setTextContent(nounTerm.getSingular());

        Element pluralElement = this.document.createElement("plural");
        pluralElement.setTextContent(nounTerm.getPlural());

        nounElement.appendChild(termElement);
        nounElement.appendChild(pluralElement);

        return nounElement;
    }

    /**
     * This method is used when adding a qualifier term to the lexicon. It receives a QualifierTerm
     * object and returns its XML node representation.
     * 
     * @param qualifierTerm the qualifier term to be added.
     * @return the XML node representation of the qualifier term
     */
    private Node getXMLAdjective(AdjectiveTerm qualifier)
    {
        Element qualifierElement = this.document.createElement("adjective");

        Element termElement = this.document.createElement("term");
        termElement.setTextContent(qualifier.getTerm());

        qualifierElement.appendChild(termElement);

        return qualifierElement;
    }

    /**
     * This method appends the given node to the XML doument.
     * 
     * @param node the node to be appended.
     */
    private void appenChildToRoot(Node node)
    {
        this.document.getDocumentElement().appendChild(node);
    }

    /**
     * This method appends the given text to the node passed as parameter.
     * 
     * @param node the node where the text will be appended.
     * @param str the text to be appended.
     */
    //INSPECT Felype - O método não é mais utilizado.
/*    private void appendText(Node node, String str)
    {
        node.appendChild(this.document.createTextNode(str));
    }*/

    /**
     * This method is responsible to update the lexicon XML file
     * 
     * @param document the lexicon XML file
     * @param fileName the XML file name
     * @throws TransformerException
     */
    private void saveXMLDocument(Document document, String fileName) throws TransformerException
    {
        TransformerFactory tranFactory = TransformerFactory.newInstance();
        Transformer aTransformer = tranFactory.newTransformer();
        
        aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
        
        Source src = new DOMSource(document);
        Result dest = new StreamResult(new File(fileName));

        aTransformer.transform(src, dest);
    }

    /**
     * Main for tests.
     * 
     * @param args
     * @throws RepositoryException
     */
    public static void main(String[] args) throws RepositoryException
    {
        LexiconXMLRepository rep = new LexiconXMLRepository(
                new String[] {".\\bases\\lexicon.xml"});

        rep.addLexicalEntry(new NounTerm("test", "tests"));

        System.out.println("End!");
    }

    /* (non-Javadoc)
     * @see br.ufpe.cin.cnlframework.vocabulary.base.ILexiconRepository#getAllVocabulary()
     */
    
    public ArrayList<LexicalEntry> getAllVocabulary() throws RepositoryException
    {
        ArrayList<LexicalEntry> list = new ArrayList<LexicalEntry>();
        
        list.addAll(this.getNouns());
        list.addAll(this.getVerbs());
        list.addAll(this.getDeterminers());
        list.addAll(this.getAdjectives());
        list.addAll(this.getPrepositions());
        list.addAll(this.getParticles());
        list.addAll(this.getCardinals());
        list.addAll(this.getAdverbs());
        
        return list;
    }
    
    
    public void removeWord(LexicalEntry lexicalEntry) throws RepositoryException
    {
        Node nodeToRemove = this.getNode(lexicalEntry);

        try
        {
            this.document.getDocumentElement().removeChild(nodeToRemove);
            
            this.saveXMLDocument(this.document, this.fileName);
        }
        catch (TransformerException e)
        {
            throw new RepositoryException("The data could not be persisted!");
        }
    }

    /**
     * Comment for method.
     * @param lexicalEntry
     * @return
     * @throws RepositoryException 
     */
    private Node getNode(LexicalEntry lexicalEntry) throws RepositoryException
    {
        Node node = null;
        
        if (lexicalEntry instanceof NounTerm)
        {
            NounTerm nounTerm = (NounTerm) lexicalEntry;
            
            for (int i = 0; i < this.nouns.getLength() && node == null; i++)
            {
                NounTerm term = this.readXMLNounTerm((Element) this.nouns.item(i));

                if (term.equals(nounTerm))
                {
                    node = this.nouns.item(i);
                }
            }
        }
        else if (lexicalEntry instanceof VerbTerm)
        {
            VerbTerm verbTerm = (VerbTerm) lexicalEntry;
            
            for (int i = 0; i < this.verbs.getLength() && node == null; i++)
            {
                VerbTerm term = this.readXMLVerbTerm((Element) this.verbs.item(i));

                if (term.equals(verbTerm))
                {
                    node = this.verbs.item(i);
                }
            }
        }
        else if (lexicalEntry instanceof DeterminerTerm)
        {
            DeterminerTerm determinerTerm = (DeterminerTerm) lexicalEntry;
            
            for (int i = 0; i < this.determiners.getLength() && node == null; i++)
            {
                DeterminerTerm term = this.readXMLDeterminerTerm((Element) this.determiners.item(i));

                if (term.equals(determinerTerm))
                {
                    node = this.determiners.item(i);
                }
            }
        }
        else if (lexicalEntry instanceof AdjectiveTerm)
        {
            AdjectiveTerm adjectiveTerm = (AdjectiveTerm) lexicalEntry;
            
            for (int i = 0; i < this.adjectives.getLength() && node == null; i++)
            {
                AdjectiveTerm term = this.readXMLAdjectiveTerm((Element) this.adjectives.item(i));

                if (term.equals(adjectiveTerm))
                {
                    node = this.adjectives.item(i);
                }
            }
        }
        else if (lexicalEntry instanceof PrepositionTerm)
        {
            PrepositionTerm prepositionTerm = (PrepositionTerm) lexicalEntry;
            
            for (int i = 0; i < this.prepositions.getLength() && node == null; i++)
            {
                PrepositionTerm term = this.readXMLPrepositionTerm((Element) this.prepositions.item(i));

                if (term.equals(prepositionTerm))
                {
                    node = this.prepositions.item(i);
                }
            }
        }
        else if (lexicalEntry instanceof CardinalTerm)
        {
            CardinalTerm cardinalTerm = (CardinalTerm) lexicalEntry;
            
            for (int i = 0; i < this.cardinals.getLength() && node == null; i++)
            {
                CardinalTerm term = this.readXMLCardinalTerm((Element) this.cardinals.item(i));

                if (term.equals(cardinalTerm))
                {
                    node = this.cardinals.item(i);
                }
            }
        }
        else if (lexicalEntry instanceof AdverbTerm)
        {
            AdverbTerm adverbTerm = (AdverbTerm) lexicalEntry;
            
            for (int i = 0; i < this.adverbs.getLength() && node == null; i++)
            {
                AdverbTerm term = this.readXMLAdverbTerm((Element) this.adverbs.item(i));

                if (term.equals(adverbTerm))
                {
                    node = this.adverbs.item(i);
                }
            }
        }
        else
        {
            for (int i = 0; i < this.particles.getLength() && node == null; i++)
            {
                LexicalEntry term = this.readXMLParticleTerm((Element) this.particles.item(i));

                if (term.equals(lexicalEntry))
                {
                    node = this.particles.item(i);
                }
            }
        }
        
        return node;
    }
}
