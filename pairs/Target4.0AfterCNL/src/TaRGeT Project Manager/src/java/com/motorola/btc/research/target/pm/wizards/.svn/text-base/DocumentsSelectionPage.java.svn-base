/*
 * @(#)DocumentsSelectionPage.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wcb	    11/07/2006    	LIBkk11577   Initial creation.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   Feb 12, 2007    LIBll27713   CR LIBll27713 improvements.
 */
package com.motorola.btc.research.target.pm.wizards;

import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.List;

/**
 * Wizard Page that enables the user to select feature documents.
 * 
 * <pre>
 * CLASS:
 * UI Wizard Page that enables the user to select feature documents. 
 * </pre>
 */
public class DocumentsSelectionPage extends WizardPage
{
    /**
     * The list of selected documents.
     */
    private List documentList;

    /**
     * The button used to add the selected requirements.
     */
    private Button addButton;

    /**
     * The button used to remove the selected requirements.
     */
    private Button remButton;

    /**
     * Sets the page default properties
     */
    protected DocumentsSelectionPage()
    {
        super("");
        setTitle("Import requirements documents");
        setDescription("Select the requirement documents.");
        setPageComplete(false); // disables the finish button
    }

    /**
     * Instantiates the GUI components.
     * 
     * @param parent The parent component of this page.
     */
    public void createControl(Composite parent)
    {
        Composite mainContainer = new Composite(parent, SWT.NULL);

        GridLayout topLayout = new GridLayout();
        mainContainer.setLayout(topLayout);
        topLayout.numColumns = 1;
        topLayout.verticalSpacing = 9;

        Label label = new Label(mainContainer, SWT.NULL);
        label.setText("Documents To Import:");

        documentList = new List(mainContainer, SWT.BORDER | SWT.WRAP | SWT.HORIZONTAL
                | SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
        documentList.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));

        Composite buttonContainer = new Composite(mainContainer, SWT.NULL);

        GridLayout buttonLayout = new GridLayout();
        buttonContainer.setLayout(buttonLayout);
        buttonLayout.numColumns = 2;
        buttonLayout.verticalSpacing = 9;

        addButton = new Button(buttonContainer, SWT.PUSH);
        addButton.setText("Add Document");

        remButton = new Button(buttonContainer, SWT.PUSH);
        remButton.setText("Remove Document");
        remButton.setEnabled(false);

        documentList.addSelectionListener(new DocumentsSelectionEventHandler(this));
        addButton.addSelectionListener(new DocumentsSelectionEventHandler(this));
        remButton.addSelectionListener(new DocumentsSelectionEventHandler(this));

        setControl(mainContainer);
    }

    /**
     * Method used to retrieve the document names added to the importing list.
     * 
     * @return The documents selected by the user.
     */
    public String[] getDocuments()
    {
        return documentList.getItems();
    }

    /**
     * Get method for <code>addButton</code> attribute.
     * 
     * @return Returns the add button.
     */
    public Button getAddButton()
    {
        return addButton;
    }

    /**
     * Get method for <code>documentList</code> attribute.
     * 
     * @return Returns the document list.
     */
    public List getDocumentList()
    {
        return documentList;
    }

    /**
     * Get method for <code>remButton</code> attribute.
     * 
     * @return Returns the remove button.
     */
    public Button getRemButton()
    {
        return remButton;
    }
}
