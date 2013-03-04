/*
 * @(#)ConsistencyManagementWizard.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * tnd783   07/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm.wizard;

import java.io.File;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.cm.editor.ConsistencyManagementEditorInput;
import com.motorola.btc.research.target.cm.editor.ConsistencyManagementMultiPageEditor;
import com.motorola.btc.research.target.cm.progressbars.CompareTestCasesProgress;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * <pre>
 * CLASS:
 * This class represents the Consistency Management wizard that is responsible for selecting an old test suite to compare.
 * 
 * RESPONSIBILITIES:
 * 1) Compare new test suite with selected old test suite
 * 2) Open the Test Comparison Page
 *
 * COLABORATORS:
 * 1) Uses OldTestSuitesPage
 * 2) Uses ProjectManagerExternalFacade
 *
 * USAGE:
 * ConsistencyManagementWizard wizard = new ConsistencyManagementWizard(<Shell>, <List<TextualTestCase>>)
 * </pre>
 */
// INSPECT Felype (integração)
public class ConsistencyManagementWizard extends Wizard
{
    /**
     * The test suites selection page.
     */
    private OldTestSuitesPage oldTestSuitesPage;

    /**
     * The list of new test cases to be compared.
     */
    private List<TextualTestCase> newTestSuite;

    /**
     * The parent component of this wizard.
     */
    private Shell parentShell;

    /**
     * Constructor of the Consistency Management wizard. Receives the parent shell of the current
     * wizard and a list of test cases to be compared.
     * 
     * @param parentShell The wizard's parent component.
     * @param newTestSuite The list of new test cases to be compared.
     */
    public ConsistencyManagementWizard(Shell parentShell, List<TextualTestCase> newTestSuite)
    {
        try
        {
            this.parentShell = parentShell;
            this.setWindowTitle("Consistency Management");
            this.newTestSuite = newTestSuite;

            ProjectManagerExternalFacade pm = ProjectManagerExternalFacade.getInstance();
            String oldTestSuiteDir = pm.getCurrentProject().getTestSuiteDir();
            this.oldTestSuitesPage = new OldTestSuitesPage(new File(oldTestSuiteDir));

            this.addPage(this.oldTestSuitesPage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.eclipse.jface.wizard.Wizard#performFinish()
     */

    public boolean performFinish()
    {
        try
        {
            CompareTestCasesProgress progressBar = new CompareTestCasesProgress(
                    "Comparing test cases...", oldTestSuitesPage.getSelectedTestSuiteFile(),
                    newTestSuite);
            ProgressMonitorDialog dialog = new ProgressMonitorDialog(this.parentShell);
            dialog.setCancelable(false);
            dialog.run(true, false, progressBar);

            IEditorInput input = new ConsistencyManagementEditorInput(progressBar.getComparison());
            IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                    .getActivePage();
            page.openEditor(input, ConsistencyManagementMultiPageEditor.ID);
        }
        catch (Exception e)
        {
            MessageDialog.openError(this.parentShell, "Error while comparing test cases.",
                    "Error while comparing test cases.");
            e.printStackTrace();
        }

        return true;
    }

}
