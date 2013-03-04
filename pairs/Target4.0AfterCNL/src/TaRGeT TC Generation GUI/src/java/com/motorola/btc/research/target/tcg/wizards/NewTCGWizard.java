/*
 * @(#)NewTCGWizard.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * WCB065    14/07/2006     none         Initial creation.
 * dhq348    Jan 18, 2007   LIBkk11577   Rework of inspection LX135556.
 * dhq348    Feb 12, 2007   LIBll27713   CR LIBll27713 improvements.
 * dhq348    Aug 01, 2007   LIBmm42774   Added the page TestPurposeCreationPage.
 * dhq348    Aug 09, 2007   LIBmm93130   CR LIBmm93130 improvements.
 * dhq348    Jan 17, 2008   LIBnn34008   Modifications according to CR. 
 * dhq348    Jan 21, 2008   LIBoo10574   Rework after inspection LX229631.
 */
package com.motorola.btc.research.target.tcg.wizards;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.motorola.btc.research.target.pm.GUIManager;
import com.motorola.btc.research.target.tcg.progressbars.GenerateTestCaseProgress;

/**
 * <pre>
 * CLASS:
 * This class implements a wizard for test case generation.
 * 
 * RESPONSIBILITIES:
 * 
 * 1) Sets the wizard page (method <i>public void addPages()</i>) 
 * 2) Implements the process to generate test cases (method public boolean performFinish())
 * </pre>
 */
public class NewTCGWizard extends Wizard
{
    /**
     * The parent component of this wizard.
     */
    private Shell parentShell;

    /**
     * The progress bar hat generate test cases.
     */
    private GenerateTestCaseProgress progress;

    /**
     * The default page that works as a wrapper to all other pages present in the wizard.
     */
    private TabFolderPage defaultPage;

    /**
     * Instantiates the class by passing its parent shell.
     * 
     * @param parentShell The parent shell from where it was originated
     */
    public NewTCGWizard(Shell parentShell)
    {
        this.parentShell = parentShell;
    }

    /**
     * <pre>
     * Adds to the wizard the following pages:
     * <ol>
     * <li>TCGRequirementsSelectionPage</li>
     * <li>TestPurposeCreationPage</li>
     * </ol>
     * </pre>
     */
    public void addPages()
    {
        setWindowTitle("New Test Suite Wizard");
        defaultPage = new TabFolderPage();
        addPage(defaultPage);
    }

    /**
     * Implements the GUI logic of the test case generation process.
     * 
     * @return <i>True</i> if the test generation process finishes correctly, <i>false</i>
     * otherwise.
     */
    public boolean performFinish()
    {
        boolean result = false;

        try
        {
            if (this.defaultPage.hasSelectedAllRequirements())
            {
                MessageDialog
                        .openWarning(
                                this.parentShell,
                                "Warning",
                                "Although all requirements were selected, some scenarios may not be generated since they may not be referred by a requirement.");
            }
            progress = new GenerateTestCaseProgress("Generating Test Cases", defaultPage
                    .getSelectedRequirements(), defaultPage.getSelectedUseCases(), defaultPage
                    .getPathCoverage(), defaultPage.getPurposesList());

            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.parentShell);
            dialog.setCancelable(false);
            dialog.run(true, false, progress);

            GUIManager.getInstance().refreshViews();
            result = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            this.showErrorMessage("Error while generating test cases.");
        }

        return result;
    }

    /**
     * Method used to display to the user a message in case of error.
     * 
     * @param message The message to be displayed.
     */
    private void showErrorMessage(String message)
    {
        MessageDialog.openError(this.parentShell, "Error while generating test cases...", message);
    }

    /**
     * @see com.motorola.btc.research.target.tcg.progressbars.GenerateTestCaseProgress#getGenerationTime()
     */
    public long getGenerationTime()
    {
        long result = 0;
        if (progress != null)
        {
            result = progress.getGenerationTime();
        }
        return result;
    }

    /**
     * @see com.motorola.btc.research.target.tcg.progressbars.GenerateTestCaseProgress#getNumberOfGeneratedTestCases()
     */
    public int getNumberOfGeneratedTestCases()
    {
        int result = 0;
        if (progress != null)
        {
            result = progress.getNumberOfGeneratedTestCases();
        }
        return result;
    }

    /**
     * Verifies if the wizard can be finalized so enables the finish button.
     * 
     * @return <code>true</code> if the tcgRequirementsSelectionPage is complete or
     * <code>false</code> otherwise.
     */

    public boolean canFinish()
    {
        return this.defaultPage.isPageComplete();
    }
}
