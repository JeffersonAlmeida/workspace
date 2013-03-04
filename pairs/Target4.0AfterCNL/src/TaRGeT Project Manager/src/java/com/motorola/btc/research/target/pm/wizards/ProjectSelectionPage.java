/*
 * @(#)ProjectSelectionPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348    Jan 11, 2007   LIBkk11577   Initial creation.
 * dhq348    Jan 18, 2007   LIBkk11577   Rework of inspection LX133710.
 * wsn013    Fev 26, 2007   LIBll29555   Changes according to LIBll29555.
 */
package com.motorola.btc.research.target.pm.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import com.motorola.btc.research.target.pm.controller.TargetProject;

/**
 * Page wizard responsible for opening an existing project.
 * 
 * <pre>
 * CLASS:
 * Page wizard responsible for opening an existing project.
 * 
 * RESPONSIBILITIES:
 * 1) Open an existing project.
 */
public class ProjectSelectionPage extends WizardPage
{
    /**
     * The text box that receives the project name.
     */
    private Text projectName;

    /**
     * Sets all the titles.
     */
    protected ProjectSelectionPage()
    {
        super("");
        setTitle("Open an existing project");
        setDescription("Select a project which you would like to open.");
    }

    /**
     * Creates the visual components of the page.
     * 
     * @param parent The parent of the page.
     */
    public void createControl(Composite parent)
    {
        Composite container = new Composite(parent, SWT.NULL);

        GridLayout topLayout = new GridLayout();
        container.setLayout(topLayout);
        topLayout.numColumns = 3;
        topLayout.verticalSpacing = 9;

        Label label = new Label(container, SWT.NULL);
        label.setText("Project name:");

        projectName = new Text(container, SWT.BORDER | SWT.SINGLE | SWT.READ_ONLY);
        projectName.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Button destinationButton = new Button(container, SWT.PUSH);
        destinationButton.setText("Browse...");
        destinationButton.addSelectionListener(new SelectionAdapter()
        {
            public void widgetSelected(SelectionEvent e)
            {
                handleProjectBrowse();
            }
        });

        projectName.addModifyListener(new ModifyListener()
        {
            public void modifyText(ModifyEvent e)
            {
                checkProjectName();
            }
        });

        // set the initial status of the finish button
        setPageComplete(false);

        setControl(container);
    }

    /**
     * Uses the standard directory dialog to choose the new value for the destination folder.
     */
    private void handleProjectBrowse()
    {
        FileDialog dialog = new FileDialog(getShell());
        dialog.setText("Browser for file");
        dialog.setFilterExtensions(new String[] { "*" + TargetProject.PROJECT_FILE_EXTENSION });
        dialog.setFilterNames(new String[] { "TaRGeT project" });
        projectName.setText("");
        projectName.insert(dialog.open());
    }

    /**
     * Checks if the project name has been set and sets if the page is complete or not.
     */
    private void checkProjectName()
    {
        setPageComplete(projectName.getText() != null);
    }

    /**
     * Returns the project name typed by the user.
     * 
     * @return The project name.
     */
    public String getProjectFile()
    {
        return projectName.getText();
    }
}
