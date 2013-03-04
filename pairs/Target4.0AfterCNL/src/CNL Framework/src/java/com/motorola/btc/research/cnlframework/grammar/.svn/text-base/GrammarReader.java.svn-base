/*
 * @(#)GrammarReader.java
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
package com.motorola.btc.research.cnlframework.grammar;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.motorola.btc.research.cnlframework.exceptions.CNLException;

/**
 * This class is responsible for reading the grammar files. It is a singleton, and the method
 * <code>readGrammar</code> is used to read one or more grammar file. When more than one grammar
 * files are passed as parameter, it works in the same way as the files that were concatenated.
 */
public class GrammarReader
{
    /** The singleton instance */
    private static GrammarReader instance = null;

    /**
     * The singleton <code>getInstance</code> method.
     * 
     * @return The single instance of <code>GrammarReader</code>.
     */
    public static GrammarReader getInstance()
    {
        if (instance == null)
        {
            instance = new GrammarReader();
        }
        return instance;
    }

    /**
     * This method reads the grammars in the files passed as parameter, and return a
     * <code>GrammarNode</code> object.
     * 
     * @param fileName The full names of the files that contain the grammars.
     * @return The <code></code> object that represents the grammar.
     * @throws CNLException An exception in case of any error trying to read the grammar files, or
     * in case it does not comply with the grammar standards.
     */
    public GrammarNode readGrammar(String[] fileName) throws CNLException
    {
        GrammarNode result = null;

        try
        {
            // reads all productions in the files passed as parameter
            List<String[]> productions = this.getGrammarFileList(fileName);
            HashMap<String, GrammarNode> nodes = new HashMap<String, GrammarNode>();

            // process all string productions and map it into a GrammarNode object
            result = this.readGrammarNode("Sentence", productions, nodes);
        }
        catch (IOException e)
        {
            throw new CNLException("Error while reading the grammar. " + e.getMessage());
        }

        return result;
    }

    /**
     * This method mounts the <code>GrammarNode</code> for a given node name. For that, it
     * receives as input, besides the node name, the list of all productions, and a map containing
     * all grammar nodes already read. <br>
     * The grammar reading process starts by calling this method for the root node, called
     * "Sentence".
     * 
     * @param nodeName The name of the node to be read.
     * @param productions The list of productions for all non-terminal nodes.
     * @param nodes The nodes that were already read up-to-date.
     * @return The <code>GrammarNode</code> object read.
     * @throws CNLException In case of any standard fault in the grammar.
     */
    private GrammarNode readGrammarNode(String nodeName, List<String[]> productions,
            HashMap<String, GrammarNode> nodes) throws CNLException
    {

        GrammarNode result = null;
        result = nodes.get(nodeName);

        // All node names must have more than one character.
        if (nodeName.length() <= 1)
        {
            throw new CNLException(
                    "All grammar productions must have at least 2 characters. The production "
                            + nodeName + " is forbidden.");
        }

        // If the node was already created
        if (result == null)
        {
            // Gets all production given a name
            List<String> productionsOfNode = this.getProductionByLeftSide(nodeName, productions);
            // If there is a production, It is a non-terminal node
            if (productionsOfNode.size() > 0)
            {
                // Verifies if the node name contains valid characters
                if (!nodeName.trim().matches("[a-z_A-Z]+[0-9]*"))
                {
                    throw new CNLException(
                            "All grammar productions must be only composed of digits and characters. The production "
                                    + nodeName + " is forbidden.");
                }

                List<List<GrammarNode>> nodeChildren = new ArrayList<List<GrammarNode>>();

                result = new NonTerminalGrammarNode(nodeName, nodeChildren);
                nodes.put(nodeName, result);

                for (String production : productionsOfNode)
                {

                    nodeChildren.addAll(this.processProduction(production, productions, nodes));
                }
            }
            else
            // If there is no production for that node name available, it is a terminal node.
            {
                nodeName = nodeName.trim();

                // Verifies if the node is a textual grammar node. For that, it verifies if the name
                // is between single quotation marks, e.g. 'example'.
                if (nodeName.matches("\\u0027[\\p{Blank}\\w\\p{Punct}]+\\u0027"))
                {
                    nodeName = nodeName.substring(1);
                    nodeName = nodeName.substring(0, nodeName.length() - 1);
                    result = new TextualGrammarNode(nodeName);
                }
                else if (nodeName.matches("[a-z_A-Z]+[0-9]*")) // Verifies if the name is valid
                {
                    result = new GrammarNode(nodeName);
                }
                else
                {
                    throw new CNLException(
                            "All grammar productions must be only composed of digits and characters. The production "
                                    + nodeName + " is forbidden.");
                }
                nodes.put(nodeName, result);
            }
        }
        return result;
    }

    /**
     * This method is responsible for finding all possible combinations of grammar node for a given
     * production. For example, for the production "A, B, (C | D), E?", the method would return the
     * following combinations: <br>
     * 1) A, B, C <br>
     * 2) A, B, C, E <br>
     * 3) A, B, D <br>
     * 4) A, B, D, E <br>
     * 
     * @param production The production to be processed.
     * @param productions The list of productions for all non-terminal nodes.
     * @param nodes The nodes that were already read up-to-date.
     * @return A list of lists of grammar node. Each list of grammar node represents a possible
     * combination for the production.
     * @throws CNLException
     */
    private List<List<GrammarNode>> processProduction(String production,
            List<String[]> productions, HashMap<String, GrammarNode> nodes) throws CNLException
    {

        List<List<GrammarNode>> result = new ArrayList<List<GrammarNode>>();
        // First, the production is split by the character '|', which represents an OR.
        List<String> splitProductionByBar = splitByChar(production, '|');
        for (String prodElementsByBar : splitProductionByBar)
        {
            // Second, the production is split by the character ',', which represents an AND.
            List<String> splitProductionByComma = splitByChar(prodElementsByBar, ',');
            List<List<GrammarNode>> grammarNodeList = new ArrayList<List<GrammarNode>>();
            for (String prodElementsByComma : splitProductionByComma)
            {

                List<List<GrammarNode>> tempNodeList = null;
                // After the two splittings, if the grammar node name in the production is between
                // brackets, they are removed and the method is recursively called for the
                // production between the brackets.
                if (this.isBracketProductionElement(prodElementsByComma))
                {
                    tempNodeList = this.processProduction(this
                            .removeBracketFromProductionElement(prodElementsByComma), productions,
                            nodes);
                }
                else
                {
                    prodElementsByComma = prodElementsByComma.trim();
                    boolean isOptional = false;
                    // If the production is optional, the '?' character is removed.
                    if (prodElementsByComma.endsWith("?"))
                    {
                        prodElementsByComma = prodElementsByComma.substring(0,
                                prodElementsByComma.length() - 1).trim();
                        isOptional = true;
                    }
                    GrammarNode grammarElement = readGrammarNode(prodElementsByComma, productions,
                            nodes);

                    List<GrammarNode> productionGrammarNode = new ArrayList<GrammarNode>();
                    productionGrammarNode.add(grammarElement);
                    tempNodeList = new ArrayList<List<GrammarNode>>();
                    tempNodeList.add(productionGrammarNode);

                    if (isOptional)
                    {
                        tempNodeList.add(new ArrayList<GrammarNode>());
                    }
                }
                grammarNodeList = this.mergeGrammarNodeLists(grammarNodeList, tempNodeList);
            }
            result.addAll(grammarNodeList);
        }
        if (production.equals(""))
        {
            result.add(new ArrayList<GrammarNode>());
        }
        return result;
    }

    /**
     * Verifies if a given string begins and ends with brackets.
     * 
     * @param str The string to be verified.
     * @return <code>true</code> if the string is between brackets, <code>false</code>
     * otherwise.
     */
    private boolean isBracketProductionElement(String str)
    {
        return str.trim().charAt(0) == '(' && str.trim().charAt(str.length() - 1) == ')';
    }

    /**
     * Removes the beginning and final brackets of a given string.
     * 
     * @param str The string containing the brackets.
     * @return The string without the brackets.
     */
    private String removeBracketFromProductionElement(String str)
    {
        str = str.trim();
        return str.substring(1, str.length() - 1);
    }

    /**
     * It does the cartesian product of the two node lists. For example, if the list (1) is [A, B,
     * C] and the list (2) is [E, F] the resulting list is [A+E, A+F, B+E, B+F, C+E, C+F], in which
     * X+Y means X concatenated with Y. Comment for method.
     * 
     * @param nodeList1 The first node list. The elements of this list come first in the
     * concatenation.
     * @param nodeList2 The second node list. The elements of this list come at last position in the
     * concatenation.
     * @return The cartesian product of the two lists.
     */
    private List<List<GrammarNode>> mergeGrammarNodeLists(List<List<GrammarNode>> nodeList1,
            List<List<GrammarNode>> nodeList2)
    {

        List<List<GrammarNode>> result = new ArrayList<List<GrammarNode>>();
        for (List<GrammarNode> node1 : nodeList1)
        {
            for (List<GrammarNode> node2 : nodeList2)
            {
                List<GrammarNode> tempList = new ArrayList<GrammarNode>(node1);
                tempList.addAll(node2);
                result.add(tempList);
            }
        }
        if (result.size() == 0)
        {
            result.addAll(nodeList2);
        }
        return result;
    }

    /**
     * Splits a string according to a given character. The method consider the parentheses in the
     * split operation. For example, if the string
     * 
     * <pre>"car, (bike, truck), airplane"</pre>
     * 
     * is split by the comma, the resulting tokens will be
     * 
     * <pre>[car ; (bike, truck) ; airplane]</pre>
     * 
     * instead of
     * 
     * <pre>[car ; (bike ; truck) ; airplane]</pre>
     * 
     * (consider the ';' character as the limits of the tokens).
     * 
     * @param str The string to be split
     * @param split The split character
     * @return A list of the tokens
     */
    private List<String> splitByChar(String str, char split)
    {
        StringBuffer buffer = new StringBuffer();
        List<String> result = new ArrayList<String>();

        int comma = 0;
        int i = 0;
        while (i < str.length())
        {

            char ch = str.charAt(i);

            if (comma == 0 && ch == split)
            {
                result.add(buffer.toString().trim());
                buffer.setLength(0);
            }
            else
            {
                buffer.append(ch);
            }
            if (ch == '(')
            {
                comma++;
            }
            else if (ch == ')')
            {
                comma--;
            }

            i++;
        }
        if (buffer.length() > 0)
        {
            result.add(buffer.toString().trim());
        }
        return result;
    }

    /**
     * This method get all productions by its left side. See method <code>getGrammarFileList</code>
     * to understand how the production is represented.
     * 
     * @param leftSide The name of the production.
     * @param productions The list of all available productions: left side name and the production
     * itself.
     * @return A list containing all productions given its left side name.
     */
    private List<String> getProductionByLeftSide(String leftSide, List<String[]> productions)
    {
        List<String> result = new ArrayList<String>();

        for (String[] production : productions)
        {
            if (production[0].equals(leftSide))
            {
                result.add(production[1]);
            }
        }
        return result;
    }

    /**
     * This method reads all productions of a grammar. The grammar is stored in the files whose
     * names are passed as parameter. The result of the method is a list containing the productions,
     * which are represented by an array of two elements. The first element is the left element of
     * the production (before the '=' symbol), and the second element is the production itself.
     * 
     * @param fileNames The names of the files that will be read.
     * @return A list of array of two elements representing the productions.
     * @throws IOException In case of any error trying to read the files.
     */
    private List<String[]> getGrammarFileList(String[] fileNames) throws IOException
    {
        RandomAccessFile raf = null;
        List<String[]> result = new ArrayList<String[]>();

        // for each file
        for (String fileName : fileNames)
        {
            raf = new RandomAccessFile(fileName, "r");
            String line = raf.readLine();
            // for each line of the file
            while (line != null)
            {
                // if the line isn't empty, or it isn't a comment
                if (!line.trim().equals("") && !line.trim().startsWith("//"))
                {
                    String[] lineSplit = line.split("=");
                    if (lineSplit.length == 2)
                    {
                        String[] grammarProduction = new String[] { lineSplit[0].trim(),
                                lineSplit[1].trim() };
                        result.add(grammarProduction);
                    }
                }
                line = raf.readLine();
            }
            raf.close();
        }

        return result;
    }

    public static void main(String[] args)
    {
        char ch = '\'';

        int i = ch;

        System.out.println(i);
    }

}
