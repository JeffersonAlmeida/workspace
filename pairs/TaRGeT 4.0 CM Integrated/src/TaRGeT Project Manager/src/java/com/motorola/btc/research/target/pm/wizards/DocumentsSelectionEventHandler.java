/*
 * @(#)DocumentsSelectionEventHandler.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Dec 13, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   Feb 08, 2007    LIBll12753   Modification after inspection LX142521.
 * wmr068   Aug 07, 2008    LIBqq64190   Method handleDocumentBrowse using the input document infrastructure. Method getFilterExtensions added.
 */
package com.motorola.btc.research.target.pm.wizards;

import java.util.Collection;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.FileDialog;

import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentData;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtensionFactory;

/**
 * Class used to handle the wizard page responsible for collecting documents.
 * 
 * <pre>
 * CLASS:
 * Class used to handle the wizard page responsible for collecting user selected documents.
 * 
 * RESPONSIBILITIES:
 * 1) Enables/Disables the importing button according to user manipulation of the <code>DocumentsSelectionPage</code>.
 *
 * COLABORATORS:
 * 1) References <code>DocumentsSelectionPage</code>
 */
public class DocumentsSelectionEventHandler extends SelectionAdapter
{
    /**
     * A reference to the documents selection page.
     */
    private DocumentsSelectionPage documentsSelectionPage;

    /**
     * Caches the page.
     * 
     * @param page The documents selection page.
     */
    public DocumentsSelectionEventHandler(DocumentsSelectionPage page)
    {
        this.documentsSelectionPage = page;
    }

    /**
     * Handles the widget selection event. This method handles all the events regarding the document
     * selection (E.g. Adding or removing documents).
     * 
     * @param e The selection event.
     */
    public void widgetSelected(SelectionEvent e)
    {
        /* checks if the event is related to a selection in the documents list */
        if (e.getSource() == this.documentsSelectionPage.getDocumentList())
        {
            boolean condition = (this.documentsSelectionPage.getDocumentList().getItemCount() > 0 && this.documentsSelectionPage
                    .getDocumentList().getSelectionCount() > 0);
            /* enables the remove button if there is at least one feature and one selected */
            this.documentsSelectionPage.getRemButton().setEnabled(condition);
        }
        /* checks if the event is related to a click on the add button */
        else if (e.getSource() == this.documentsSelectionPage.getAddButton())
        {
            String[] browsedDocuments = handleDocumentBrowse();
            if (browsedDocuments.length > 0)
            {
                String[] existing = this.documentsSelectionPage.getDocumentList().getItems();

                for (String document : browsedDocuments)
                {
                    // Verifies if the selected files is already inserted in the documents to import
                    // list
                    boolean found = false;
                    for (String string : existing)
                    {
                        if (FileUtil.getFileName(string).toLowerCase().equalsIgnoreCase(
                                FileUtil.getFileName(document).toLowerCase()))
                        {
                            found = true;
                            break;
                        }
                    }
                    if (!found)
                    {
                        this.documentsSelectionPage.getDocumentList().add(document);
                        /* enables the finish button */
                        this.documentsSelectionPage.setPageComplete(true);
                    }
                }
            }
        }
        /* checks if the event is related to a click on the remove button */
        else if (e.getSource() == this.documentsSelectionPage.getRemButton())
        {
            int[] selectedIndex = this.documentsSelectionPage.getDocumentList()
                    .getSelectionIndices();
            if (selectedIndex.length > 0)
            {
                this.documentsSelectionPage.getDocumentList().remove(selectedIndex);
                this.documentsSelectionPage.getRemButton().setEnabled(false);
                /* disables the finish button if there are no features (documents) in the list */
                this.documentsSelectionPage.setPageComplete(this.documentsSelectionPage
                        .getDocumentList().getItemCount() > 0);
            }
        }
    }

    /**
     * Starts a browser dialog that allow the user to select a document from file system.
     * 
     * @return The path of the selected file or <code>null</code> if the dialog was canceled.
     */
    private String[] handleDocumentBrowse()
    {
        FileDialog dialog = new FileDialog(this.documentsSelectionPage.getShell(), SWT.MULTI);
        dialog.setText("Browser for file");

        String[][] documentTypes = this.getFilterExtensions();

        dialog.setFilterExtensions(documentTypes[0]);
        dialog.setFilterNames(documentTypes[1]);

        String folderName = dialog.open();
        folderName = FileUtil.getFilePath(folderName);
        String[] result = dialog.getFileNames();
        for (int i = 0; i < result.length; i++)
        {
            result[i] = folderName + FileUtil.getSeparator() + result[i];
        }

        return result;
    }

    // INSPECT new method
    /**
     * Gets the available input filter extensions (i.e. *.doc, *.xml etc) and the
     * respective names of each filter extension. The filter extensions are stored
     * in the first row of a resulting matrix whereas the names are stored in
     * the second row.
     * 
     * @return A matrix containing the available input filter extensions and their names.
     */
    private String[][] getFilterExtensions()
    {
        String[][] documentTypes = null;

        Collection<InputDocumentData> inputExtensionsList = InputDocumentExtensionFactory
                .inputExtensions();

        documentTypes = new String[2][inputExtensionsList.size()];

        int i = 0;

        for (InputDocumentData inputDocumentData : inputExtensionsList)
        {
            documentTypes[0][i] = inputDocumentData.getDocumentTypeFilter();
            documentTypes[1][i] = inputDocumentData.getDocumentName();
            i++;
        }

        return documentTypes;
    }

}
