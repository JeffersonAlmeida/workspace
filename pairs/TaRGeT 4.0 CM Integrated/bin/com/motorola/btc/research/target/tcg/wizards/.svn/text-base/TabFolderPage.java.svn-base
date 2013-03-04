/*
 * @(#)DefaultPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Nov 12, 2007   LIBoo10574   Initial creation.
 * dhq348    Jan 21, 2008   LIBoo10574   Rework after inspection LX229631.
 * wln013    Mar 20, 2008   LIBpp56482   Update getPurposesList method.
 */
package com.motorola.btc.research.target.tcg.wizards;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.tcg.filters.TestPurpose;

/**
 * Page that handles all other pages in a tabbed way. It is currently adding four pages:
 * 
 * <pre>
 * 1 - The requirements selections page
 * 2 - The use cases selection page
 * 3 - The test purpose creation page
 * </pre>
 */
public class TabFolderPage extends WizardPage
{
    /**
     * The pages that will be presented in tabs.
     */
    private TCGPage[] pages;

    /**
     * Instantiates the following pages: TCGRequirementsSelectionPage, TCGUseCaseSelectionPage
     * and new TestPurposeCreationPage.
     */
    protected TabFolderPage()
    {
        super("");
        setTitle("Test Suite Generation Wizard");
        pages = new TCGPage[] { new TCGRequirementsSelectionPage(), new TCGUseCaseSelectionPage(),
                new TestPurposeCreationPage() };

        setPageComplete(true);
    }

    /**
     * Creates all visual components. It basically creates four tabs:
     * 
     * <pre>
     * 1 - The requirements selections page
     * 2 - The use cases selection page
     * 3 - The test purpose creation page
     * </pre>
     * 
     * @param parent The parent of the tabs to be created.
     */
    public void createControl(Composite parent)
    {
        TabFolder folder = new TabFolder(parent, SWT.TOP);

        /* creates the requirements selection page at position 0 */
        TabItem requirementsSelectionItem = new TabItem(folder, SWT.NONE);
        requirementsSelectionItem.setText("Requirements Selection");
        requirementsSelectionItem.setControl(pages[0].createVisualComponents(folder));

        /* creates the use cases selection page at position 1 */
        TabItem usecaseSelectionItem = new TabItem(folder, SWT.NONE);
        usecaseSelectionItem.setText("Use Cases Selection");
        usecaseSelectionItem.setControl(pages[1].createVisualComponents(folder));

        /* creates the test purpose creation page at position 3 */
        TabItem testPurposeCreationItem = new TabItem(folder, SWT.NONE);
        testPurposeCreationItem.setText("Test Purpose Creation");
        //testPurposeCreationItem.setControl(pages[3].createVisualComponents(folder));
        testPurposeCreationItem.setControl(pages[2].createVisualComponents(folder));

        setControl(folder);
    }

    /**
     * @return <code>true</code> if all requirements were selected or <code>false</code>
     * otherwise.
     */
    public boolean hasSelectedAllRequirements()
    {
        return ((TCGRequirementsSelectionPage) pages[0]).hasSelectedAllRequirements();
    }

    /**
     * @return The set of selected requirements.
     */
    public Set<String> getSelectedRequirements()
    {
        return ((TCGRequirementsSelectionPage) pages[0]).getSelectedRequirements();
    }

    /**
     * @return The path coverage.
     */
    public int getPathCoverage()
    {
        return ((TestPurposeCreationPage) pages[3]).getPathCoverage();
    }

    /**
     * Returns a list with the created test purposes.
     * 
     * @return The list of created purposes.
     */
    public List<TestPurpose> getPurposesList()
    {

        return ((TestPurposeCreationPage) pages[3]).getPurposesList();
    }

    /**
     * @return A map with the selected use cases.
     */
    public HashMap<Feature, List<UseCase>> getSelectedUseCases()
    {
        return ((TCGUseCaseSelectionPage) pages[1]).getSelectedUseCases();
    }


}
