/*
 * @(#)TestSuiteFilterAssembler.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    May 31, 2007   LIBmm42774   Initial creation.
 * dhq348    Aug 21, 2007   LIBmm42774   Rework after inspection LX201094.
 * dhq348    Aug 27, 2007   LIBmm42774   Added method setPurposeFilter().
 * dhq348    Aug 28, 2007   LIBmm42774   Rework after inspection LX203164.
 * dhq348    Nov 27, 2007   LIBoo10574   Modifications according to CR.
 * dhq348    Jan 23, 2008   LIBoo10574   Rework after inspection LX229632.
 * wdt022    Mar 20, 2008   LIBpp56482   getRequirementsFilter, getUseCasesFilter, getSimilarityFilter, setPurposeFilter, getPurposeFilter methods created.
 */
package com.motorola.btc.research.target.tcg.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.tcg.exceptions.InvalidSimilarityException;

/**
 * This class is responsible for building the filters that select the test cases from the generated
 * test suite. It is built according to the information contained in the Test Generation Wizard.
 * 
 * <pre>
 * CLASS:
 * The class contains a hash map that stores all the selected filters in the Test Generation Wizard.
 * The class also contains one method for each available filter. These methods inform to the class 
 * the necessary parameters to create the filter objects.
 */
public class TestSuiteFilterAssembler
{
    /** The enumeration of available filters. It is used as the key of the hash map */
    private enum FilterKey {
        REQUIREMENTS, PATH_COVERAGE, PURPOSE, USECASES
    };

    /** The hash map that contains the filters selected by the user */
    private Map<FilterKey, TestSuiteFilter<FlowStep>> filterOptions;

    /** An instance of the default filter */
    private final TestSuiteFilter<FlowStep> defaultFilter = new DefaultTestSuiteFilter();

    /**
     * This constructor only initializes the filter hash map.
     */
    public TestSuiteFilterAssembler()
    {
        this.filterOptions = new HashMap<FilterKey, TestSuiteFilter<FlowStep>>();
    }

    /**
     * Sets the requirements filter.
     * 
     * @see com.motorola.btc.research.target.tcg.filters.RequirementsFilter
     * @param requirements The collection of requirements.
     */
    public void setRequirementsFilter(Collection<String> requirements)
    {
        if (requirements != null && !requirements.isEmpty())
        {
            TestSuiteFilter<FlowStep> requirementsFilter = new RequirementsFilter(requirements);
            this.filterOptions.put(FilterKey.REQUIREMENTS, requirementsFilter);
        }
    }

    /**
     * Gets the requirements' filter.
     * 
     * @see com.motorola.btc.research.target.tcg.filters.RequirementsFilter
     * @return The requirements' filter.
     */
    public RequirementsFilter getRequirementsFilter()
    {
        return (RequirementsFilter) this.filterOptions.get(FilterKey.REQUIREMENTS);
    }

    /**
     * Sets a use case filter given a map between features and use cases.
     * 
     * @param usecases The map between the selected use cases and their respective features.
     */
    public void setUseCasesFilter(HashMap<Feature, List<UseCase>> usecases)
    {
        if (usecases != null && !usecases.isEmpty())
        {
            TestSuiteFilter<FlowStep> usecaseFilter = new UseCasesFilter(usecases);
            this.filterOptions.put(FilterKey.USECASES, usecaseFilter);
        }
    }

    /**
     * Gets the use cases' filter.
     * 
     * @see com.motorola.btc.research.target.tcg.filters.UseCasesFilter
     * @return The use cases' filter.
     */
    public UseCasesFilter getUseCasesFilter()
    {
        return (UseCasesFilter) this.filterOptions.get(FilterKey.USECASES);
    }

    /**
     * Sets the path coverage filter.
     * 
     * @see com.motorola.btc.research.target.tcg.filters.PathCoverageFilter
     * @param requiredPathCoverage The required similarity.
     * @throws InvalidSimilarityException It is thrown if the similarity is set to a value out of the
     * range (0-100). However, it will never be possible, since the GUI restricts the user.
     */
    public void setSimilarityFilter(double requiredPathCoverage) throws InvalidSimilarityException
    {
        TestSuiteFilter<FlowStep> pathCoverageFilter = new SimilarityFilter(requiredPathCoverage);
        this.filterOptions.put(FilterKey.PATH_COVERAGE, pathCoverageFilter);
    }

    /**
     * Gets the similarity filter.
     * 
     * @see com.motorola.btc.research.target.tcg.filters.SimilarityFilter
     * @return The similarity filter instance.
     */
    public SimilarityFilter getSimilarityFilter()
    {
        return (SimilarityFilter) this.filterOptions.get(FilterKey.PATH_COVERAGE);
    }

    /**
     * This method is used to assemble the main filter. This filter is composed of the filters
     * selected by the user.
     * 
     * @return The assembled filter.
     */
    public TestSuiteFilter<FlowStep> assemblyFilter()
    {
        List<TestSuiteFilter<FlowStep>> filterList = new ArrayList<TestSuiteFilter<FlowStep>>();

        TestSuiteFilter<FlowStep> requirementsFilter = this.filterOptions
                .get(FilterKey.REQUIREMENTS);
        TestSuiteFilter<FlowStep> usecasesFilter = this.filterOptions.get(FilterKey.USECASES);
        TestSuiteFilter<FlowStep> similarityFilter = this.filterOptions
                .get(FilterKey.PATH_COVERAGE);
        TestSuiteFilter<FlowStep> purposesFilter = this.filterOptions.get(FilterKey.PURPOSE);

        filterList.add(defaultFilter);
        if (requirementsFilter != null)
        {
            filterList.add(requirementsFilter);
        }

        if (usecasesFilter != null)
        {
            filterList.add(usecasesFilter);
        }

        if (purposesFilter != null)
        {
            filterList.add(purposesFilter);
        }

        if (similarityFilter != null)
        {
            filterList.add(similarityFilter);
        }

        return new CompositionTestSuiteFilter(filterList);
    }

    /**
     * Creates an <code>ORTestSuiteFilter</code> that aggregates a set of purpose filters. One
     * filter is created for each purpose in the list passed as parameter.
     * 
     * @param testPurposesList The purposes used to create the <code>ORTestSuiteFilter</code>.
     */
    public void setPurposeFilter(List<TestPurpose> testPurposesList)
    {
        if (testPurposesList != null && !testPurposesList.isEmpty())
        {
            TestSuiteFilter<FlowStep> testPurposeFilter = new PurposeFilter(testPurposesList);
            this.filterOptions.put(FilterKey.PURPOSE, testPurposeFilter);
        }

    }

    /**
     * Gets the purpose filter.
     * 
     * @see com.motorola.btc.research.target.tcg.filters.PurposeFilter
     * @return The purpose filter.
     */
    public PurposeFilter getPurposeFilter()
    {
        return (PurposeFilter) this.filterOptions.get(FilterKey.PURPOSE);
    }
}
