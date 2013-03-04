/*
 * @(#)OnTheFlyMultiPageEditor.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022   Mar 14, 2008    LIBpp56482   Initial creation.
 * tnd783   Aug 25, 2008	LIBqq51204	 Changes made on pages' header for on the fly.
 */
package com.motorola.btc.research.target.tcg.editors;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.editor.FormPage;

import com.motorola.btc.research.target.common.ucdoc.FlowStep;
import com.motorola.btc.research.target.tcg.TCGActivator;
import com.motorola.btc.research.target.tcg.extractor.TestCase;
import com.motorola.btc.research.target.tcg.extractor.TestSuite;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * This class is responsible for On The Fly form pages creation.  
 * 
 * RESPONSIBILITIES:
 * Creates form pages, provides whole test suites for form pages.
 * 
 * </pre>
 */
public class OnTheFlyMultiPageEditor extends FormEditor
{
    /**
     * The unique identifier of this form editor.
     */
    public static final String ID = "com.motorola.btc.research.target.tcg.editor.OnTheFlyMultiPageEditor";
    
    /**
     * The list of headers. This list is necessary to maintain the consistency between the combos of filter selections. 
     */
    
    private List<OnTheFlyHeader> headers = new ArrayList<OnTheFlyHeader>();
    
    
    /**
     * Creates and adds pages to this editor.
     */
    protected void createPages()
    {
        try
        {
            OnTheFlyTestSelectionPage testSelection = new OnTheFlyTestSelectionPage(this);

            OnTheFlyTestExclusionPage testInclusionExclusionPage = new OnTheFlyTestExclusionPage(
                    this);

            OnTheFlyGeneratedTestCasesPage generatedTestCasesPage = new OnTheFlyGeneratedTestCasesPage(
                    this, testSelection, testInclusionExclusionPage);

            OnTheFlyTraceabilityMatrixPage traceabilityMatrix = new OnTheFlyTraceabilityMatrixPage(
                    this, testSelection, testInclusionExclusionPage);

            int index;
            try
            {
                index = addPage(testSelection);
                setPageText(index, "Selection");
                index = addPage(generatedTestCasesPage);
                setPageText(index, "Test Cases");
                index = addPage(traceabilityMatrix);
                setPageText(index, "Traceability Matrix");
                index = addPage(testInclusionExclusionPage);
                setPageText(index, "Inclusion/Exclusion");
            }
            catch (PartInitException e)
            {
                TCGActivator.logError(0, this.getClass(), "Error creating pages", e);
                e.printStackTrace();
            }
        }
        catch (Exception e)
        {
            System.out.println("erro!");
        }
    }

    /**
     * @see FormEditor#addPages()
     */

    protected void addPages()
    {
    }

    /**
     * @see FormEditor#doSave(IProgressMonitor)
     */

    public void doSave(IProgressMonitor monitor)
    {
    }

    /**
     * @see FormEditor#doSaveAs()
     */

    public void doSaveAs()
    {
    }

    /**
     * @see FormEditor#isSaveAsAllowed()
     */

    public boolean isSaveAsAllowed()
    {
        return false;
    }

    /**
     * @see FormEditor#setFocus()
     */

    public void setFocus()
    {
        super.setFocus();
        if (this.getActivePage() >= 0)
        {
            ((FormPage) this.pages.get(this.getActivePage())).setFocus();
        }
    }

    /**
     * Gets the whole test suite with test cases in raw format.
     * 
     * @return A test suite with raw test cases.
     */
    public TestSuite<TestCase<FlowStep>> getRawTestSuite()
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getRawTestSuite();
    }

    /**
     * Gets the raw test case associated with the specified id.
     * 
     * @param id The test case id.
     * @return The test case to which the specified id is associated, or null if there is no test
     * case associated with the id.
     */
    public TestCase<FlowStep> getRawTestCase(int id)
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getRawTestCase(id);
    }

    /**
     * Gets the whole test suite with test cases in textual format.
     * 
     * @return A test suite with textual test cases.
     */
    public TestSuite<TextualTestCase> getTextualTestSuite()
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getTextualTestSuite();
    }

    /**
     * Gets the textual test case associated with the specified id.
     * 
     * @param id The test case id.
     * @return The test case to which the specified id is associated, or null if there is no test
     * case associated with the id.
     */
    public TextualTestCase getTextualTestCase(int id)
    {
        OnTheFlyEditorInput input = (OnTheFlyEditorInput) this.getEditorInput();
        return input.getTextualTestCase(id);
    }

    /**
     * Gets the list of headers of the current editor.
     * 
     * @return The list of headers.
     */
    
    public List<OnTheFlyHeader> getHeaders()
    {
        return headers;
    }

}
