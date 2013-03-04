package com.motorola.btc.research.target.cm.tcsimilarity.logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * This class represents a table containing use cases references and their related test cases.
 */
public class TestCaseMap
{

    /**
     * The map that relates use cases references to a list of test cases.
     */
    private HashMap<String, List<TextualTestCase>> map;

    /**
     * Constructor for the class.
     * 
     * @param testCases The list of test cases to be put in the map.
     */
    public TestCaseMap(List<TextualTestCase> testCases)
    {

        map = new HashMap<String, List<TextualTestCase>>();

        for (TextualTestCase test : testCases)
        {
            String ucs = test.getUseCaseReferences();

            if (map.containsKey(ucs))
            {
                List<TextualTestCase> list = map.get(ucs);
                list.add(test);
                Object[] array = list.toArray();
                Arrays.sort(array);
                list.clear();
                for (int i = 0; i < array.length; i++)
                {
                    list.add((TextualTestCase) array[i]);
                }

                map.put(ucs, list);
            }
            else
            {
                List<TextualTestCase> list = new ArrayList<TextualTestCase>();
                list.add(test);
                map.put(ucs, list);
            }

        }

    }

    /**
     * Gets all the test cases which are related to the given use case.
     * 
     * @param uc The use case to which the returned test cases must be related.
     * @return A list of test cases related to the given use case.
     */
    public List<TextualTestCase> getTestCases(String uc)
    {
        return map.get(uc);
    }

}
