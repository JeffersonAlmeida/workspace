/*
 * @(#)ConsistencyManagementMultiPageEditor.java
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
package com.motorola.btc.research.target.cm.editor;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;

//INSPECT
/**
 * <pre>
 * CLASS:
 * This class is responsible for Consistency Management pages creation.  
 * 
 * RESPONSIBILITIES:
 * Creates form pages, provides comparison results for form pages.
 * 
 * </pre>
 */
public class ConsistencyManagementMultiPageEditor extends FormEditor
{
    /**
     * The unique identifier of this form editor.
     */
    public static final String ID = "com.motorola.btc.research.target.cm.editor.ConsistencyManagementMultiPageEditor";

    /* (non-Javadoc)
     * @see org.eclipse.ui.forms.editor.FormEditor#addPages()
     */
    
    protected void addPages()
    {

        TestComparisonPage testComparisonPage = new TestComparisonPage(this);

        int index;
        try
        {
            index = addPage(testComparisonPage);
            setPageText(index, "Comparison");
        }
        catch (PartInitException e)
        {
            e.printStackTrace();
        }

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#doSave(org.eclipse.core.runtime.IProgressMonitor)
     */
    
    public void doSave(IProgressMonitor monitor)
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#doSaveAs()
     */
    
    public void doSaveAs()
    {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see org.eclipse.ui.part.EditorPart#isSaveAsAllowed()
     */
    
    public boolean isSaveAsAllowed()
    {
        // TODO Auto-generated method stub
        return false;
    }

}
