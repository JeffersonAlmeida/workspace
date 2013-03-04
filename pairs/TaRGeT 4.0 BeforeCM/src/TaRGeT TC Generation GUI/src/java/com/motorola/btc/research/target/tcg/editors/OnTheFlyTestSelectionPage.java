/*
 * @(#)OnTheFlyTestSelectionPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Mar 17, 2008    LIBpp56482   Initial creation.
 */
package com.motorola.btc.research.target.tcg.editors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.tcg.TCGActivator;
import com.motorola.btc.research.target.tcg.exceptions.InvalidSimilarityException;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.filters.TestPurpose;
import com.motorola.btc.research.target.tcg.filters.TestSuiteFilterAssembler;
import com.motorola.btc.research.target.tcg.wizards.TCGRequirementsSelectionPage;
import com.motorola.btc.research.target.tcg.wizards.TCGUseCaseSelectionPage;
import com.motorola.btc.research.target.tcg.wizards.TestPurposeCreationPage;

/**
 * <pre>
 * CLASS:
 * This class provides a form page with some test generation filters. The test suite is generated 
 * according to the filters settings.
 *      
 * RESPONSIBILITIES:
 * 
 * 
 * </pre>
 */
public class OnTheFlyTestSelectionPage extends FormPage
{
    /**
     * The unique identifier of this form page.
     */
    public static final String ID = "com.motorola.btc.research.target.tcg.editors.OnTheFlyTestSelectionPage";

    /**
     * The page that provides the filter of test purposes.
     */
    private TestPurposeCreationPage testPurposePage;

    /**
     * The page that provides the filter of requirements.
     */
    private TCGRequirementsSelectionPage requirementsSelectionPage;

    /**
     * The page that provides the filter of use cases.
     */
    private TCGUseCaseSelectionPage useCaseSelectionPage;

    /**
     * Creates a test selection form page.
     * 
     * @param editor The editor where this page will be added.
     */
    public OnTheFlyTestSelectionPage(FormEditor editor)
    {
        super(editor, ID, "Select your Test Cases");
    }

    /**
     * Gets a filter assembler with the filters settings.
     * 
     * @return The filter assembler for the current filters settings.
     */
    public TestSuiteFilterAssembler getFilterAssembler()
    {
        TestSuiteFilterAssembler result = new TestSuiteFilterAssembler();

        if (this.requirementsSelectionPage != null && this.useCaseSelectionPage != null
                && this.testPurposePage != null)
        {
            result.setRequirementsFilter(this.requirementsSelectionPage.getSelectedRequirements());
            result.setUseCasesFilter(this.useCaseSelectionPage.getSelectedUseCases());
            result.setPurposeFilter(this.testPurposePage.getPurposesList());
            try
            {
                result.setSimilarityFilter(this.testPurposePage.getPathCoverage());
            }
            catch (InvalidSimilarityException e)
            {
                e.printStackTrace();
                TCGActivator.logError(0, this.getClass(), "Invalid Similarity value: "
                        + this.testPurposePage.getPathCoverage(), e);
            }
        }

        return result;
    }

    /**
     * Changes the test filters according to the assembler.
     * 
     * @param assembler The new filter assembler
     */
    public void setFilterAssembler(TestSuiteFilterAssembler assembler)
    {
        if (assembler.getRequirementsFilter() != null)
        {
            this.requirementsSelectionPage.setSelectedRequirements(assembler
                    .getRequirementsFilter().getRequirements());
        }
        else
        {
            this.requirementsSelectionPage.setSelectedRequirements(new HashSet<String>());
        }

        if (assembler.getUseCasesFilter() != null)
        {
            this.useCaseSelectionPage.setSelectedUseCases(assembler.getUseCasesFilter()
                    .getUsecases());
        }
        else
        {
            this.useCaseSelectionPage.setSelectedUseCases(new HashMap<Feature, List<UseCase>>());
        }

        if (assembler.getSimilarityFilter() != null)
        {
            this.testPurposePage.setPathCoverage((int) assembler.getSimilarityFilter()
                    .getSimilarity());
        }
        else
        {
            this.testPurposePage.setPathCoverage(100);
        }

        if (assembler.getPurposeFilter() != null)
        {
            this.testPurposePage.setPurposeList(assembler.getPurposeFilter().getPurposes());
        }
        else
        {
            this.testPurposePage.setPurposeList(new ArrayList<TestPurpose>());
        }
    }

    /**
     * Retrieves the editor that contains this page.
     * 
     * @return The editor where this page was added.
     */
    public OnTheFlyMultiPageEditor getEditor()
    {
        return (OnTheFlyMultiPageEditor) super.getEditor();
    }

    /**
     * Selects a use case on the use cases filter.
     * 
     * @param feature The feature that contains the use case.
     * @param usecase The use case that will be selected.
     */
    public void selectUseCase(String feature, String usecase)
    {
        this.useCaseSelectionPage.selectUseCase(feature, usecase);
    }

    /**
     * Verifies if a use case is selected on the use cases filter.
     * 
     * @param feature The feature that contains the use case.
     * @param usecase The use case that will be verified.
     * @return True if the use case exists and it is selected, false otherwise.
     */
    public boolean isUseCaseSelected(String feature, String usecase)
    {
        return this.useCaseSelectionPage.isUseCaseSelected(feature, usecase);
    }

    /**
     * Selects a requirement on the requirements filter.
     * 
     * @param requirement The requirement that will be selected.
     */
    public void selectRequirement(String requirement)
    {
        Set<String> requirements = this.requirementsSelectionPage.getSelectedRequirements();
        requirements.add(requirement);
        this.requirementsSelectionPage.setSelectedRequirements(requirements);
    }

    /**
     * Verifies if a requirement is selected on the requirements filter.
     * 
     * @param requirement The requirement that will be verified.
     * @return True if the requirement exists and it is selected, false otherwise.
     */
    public boolean isRequirementSelected(String requirement)
    {
        return this.requirementsSelectionPage.getSelectedRequirements().contains(requirement);
    }

    /**
     * Gets the current test suite.
     * 
     * @return A test suite generated according to filters settings.
     */
    protected TestSuite<TestCase<FlowStep>> getCurrentTestSuite()
    {

        return getFilterAssembler().assemblyFilter().filter(this.getEditor().getRawTestSuite());

    }

    /**
     * Creates content in the form hosted in this page.
     * 
     * @param managedForm The form hosted in this page.
     */
    protected void createFormContent(IManagedForm managedForm)
    {
        ScrolledForm form = managedForm.getForm();
        FormToolkit toolkit = managedForm.getToolkit();
        form.setText("Test Selection Page");

        GridLayout layout = new GridLayout();
        layout.numColumns = 2;
        form.getBody().setLayout(layout);

        this.createRequirementAndUseCaseSelectionPage(form, toolkit);
        this.createPurposeSelectionPage(form, toolkit);
    }

    /**
     * Creates the test purpose selection page.
     * 
     * @see TestPurposeCreationPage
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     */
    private void createPurposeSelectionPage(final ScrolledForm form, FormToolkit toolkit)
    {
        Section section = OnTheFlyUtil.createSection(form, toolkit, Section.DESCRIPTION
                | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);

        Composite client = OnTheFlyUtil.createComposite(toolkit, section, SWT.NONE, 1);

        this.testPurposePage = new TestPurposeCreationPage(true);
        this.testPurposePage.createVisualComponents(client);

        section.setText("Test Purpose Creation");
        section.setDescription("Create your test purposes");
        section.setClient(client);
        section.setExpanded(true);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 150;
        gd.horizontalSpan = 2;
        section.setLayoutData(gd);
    }

    /**
     * Creates the requirements filter page and the use cases filter page.
     * 
     * @see TCGRequirementsSelectionPage
     * @see TCGUseCaseSelectionPage
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     */
    private void createRequirementAndUseCaseSelectionPage(final ScrolledForm form,
            FormToolkit toolkit)
    {
        Section section = OnTheFlyUtil.createSection(form, toolkit, Section.DESCRIPTION
                | Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED);

        Composite client = OnTheFlyUtil.createComposite(toolkit, section, SWT.WRAP, 2);

        this.requirementsSelectionPage = new TCGRequirementsSelectionPage();
        this.requirementsSelectionPage.createVisualComponents(client);

        this.useCaseSelectionPage = new TCGUseCaseSelectionPage();
        this.useCaseSelectionPage.createVisualComponents(client);

        section.setText("Requirements and Use Cases Selection");
        section.setDescription("Select the requirements and use cases you want to cover");
        section.setClient(client);
        section.setExpanded(true);

        GridData gd = new GridData(SWT.FILL, SWT.TOP, true, false);
        gd.heightHint = 150;
        gd.horizontalSpan = 2;
        section.setLayoutData(gd);
    }

}
