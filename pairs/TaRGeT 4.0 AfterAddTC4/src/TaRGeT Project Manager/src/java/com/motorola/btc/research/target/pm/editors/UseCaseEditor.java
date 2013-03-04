/*
 * @(#)UseCaseEditor.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 11, 2007    LIBkk11577   Initial creation.
 * dhq348   Jan 29, 2007    LIBll12753   Rework of inspection LX136878.
 * dhq348   May 23, 2007    LIBmm25975   Removed FeaturesView.getSelectedUseCase().getName().
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * tnd783   Apr 07, 2008    LIBpp71785   Added the refresh() method.
 * tnd783   Jul 21, 2008    LIBpp71785   Rework after inspection LX285039.  
 */
package com.motorola.btc.research.target.pm.editors;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.part.MultiEditorInput;

import com.motorola.btc.research.target.common.ucdoc.Feature;
import com.motorola.btc.research.target.common.ucdoc.UseCase;
import com.motorola.btc.research.target.pm.ReloadProjectShellListener;
import com.motorola.btc.research.target.pm.controller.ProjectManagerController;
import com.motorola.btc.research.target.pm.errors.Error;

/**
 * Editor that displays the content of a generated HTML file containing use cases.
 * 
 * <pre>
 * CLASS:
 * This editor display the generated HTML file that contains the data about the 
 * uses cases contained in phone documents. It uses a SWT browser to display the
 * content. This class is currently only working well under WinXP and not under 
 * other OS.
 * 
 * RESPONSIBILITIES:
 * 1) Displays HTML data
 * 
 * USAGE:
 * It is only referenced by its ID.
 */
public class UseCaseEditor extends EditorPart
{

    /**
     * The ID of the editor that is declared in plugin.xml.
     */
    public static final String ID = "com.motorola.btc.research.target.pm.editors.UseCaseEditor";

    /**
     * The file hat contains the HTML data to be displayed.
     */
    private File file;

    /**
     * The name to be displayed in the top of the editor.
     */
    private String partName;

    /**
     * The SWT browser that will display the HTML.
     */
    private Browser browser;

    /**
     * Is is not necessary to save anything.
     */

    public void doSave(IProgressMonitor monitor)
    {
    }

    /**
     * Is is not necessary to save anything.
     */

    public void doSaveAs()
    {
    }

    /**
     * Gets the file passed through the path included in <code>input</code>.
     * 
     * @param site The site passed by the class that invokes this class.
     * @param input The input that contains the path of the file used to display the html data.
     * @throws PartInitException At any invalid operation while initializing.
     */

    public void init(IEditorSite site, IEditorInput input) throws PartInitException
    {
        MultiEditorInput mInput = (MultiEditorInput) input;
        this.partName = mInput.getEditors()[0];
        PathEditorInput pInput = (PathEditorInput) mInput.getInput()[0];
        this.file = pInput.getPath().toFile();
        this.setSite(site); // added because the site attribute was null
        this.setInput(pInput);
        
        this.setActionEnablementCapability();
        
        Shell shell = site.getShell();
        shell.addShellListener(new ReloadProjectShellListener(shell));
    }
   
    /**
     * Sets the initial action selection, so the actions are initially unmarked (disabled). It is
     * implemented a basic StructuredViewer that only returns a list with a single item in
     * getSelectionFromWidget and a simple label as a control in getControl. This viewer is used
     * mainly because it was difficult to disable the actions at the tool startup, so the action now
     * in their xml declarations have the enables for attribute set to '+' which means that it begins
     * disabled and when any selection is activated their status is changed.
     */
    protected void setActionEnablementCapability()
    {
        final Shell shell = this.getSite().getShell();
        StructuredViewer tmpViewer = new StructuredViewer()
        {

            protected Widget doFindInputItem(Object element)
            {
                return null;
            }


            protected Widget doFindItem(Object element)
            {
                return null;
            }


            protected void doUpdateItem(Widget item, Object element, boolean fullMap)
            {
            }

            @SuppressWarnings("unchecked")

            protected List getSelectionFromWidget()
            {
                ArrayList<String> list = new ArrayList<String>();
                list.add("InitialActionSelection");
                return list;
            }


            protected void internalRefresh(Object element)
            {
            }


            public void reveal(Object element)
            {
            }

            @SuppressWarnings("unchecked")

            protected void setSelectionToWidget(List l, boolean reveal)
            {
            }

            /**
             * Gets a simple label as a control.
             */

            public Control getControl()
            {
                Label label = new Label(shell, SWT.NONE);
                return label;
            }
        };
        /* sets the new viewer as a selection provider of the current view */
        this.getSite().setSelectionProvider(tmpViewer);
        /* sets a single empty selection that enables any action waiting for a selection */
        tmpViewer.setSelection(new StructuredSelection());
    }

    /**
     * Is is not necessary to verify if the editor is dirty since it will not be edited.
     * 
     * @return Returns always <i>False</i>.
     */

    public boolean isDirty()
    {
        return false;
    }

    /**
     * It is not necessary to check if save is allowed.
     * 
     * @return Returns always <i>False</i>.
     */

    public boolean isSaveAsAllowed()
    {
        return false;
    }

    /**
     * Creates the SWT browser that will display the html data contained in <code>file</code>.
     * 
     * @param parent The parent component of this editor.
     */

    public void createPartControl(Composite parent)
    {
        browser = new Browser(parent, SWT.NONE);
        browser.setUrl(this.file.getAbsolutePath());
        this.setPartName(this.partName);

        file.deleteOnExit();
    }

    /**
     * The behavior when the focus goes to the editor.
     */

    public void setFocus()
    {
    }

    /**
     * Refreshes the editor, updating use case, features and errors information.
     */
    public void refresh()
    {

        IEditorInput input = getEditorInput();

        if (input instanceof PathEditorInput)
        {

            PathEditorInput pathInput = (PathEditorInput) input;

            UseCase useCase = pathInput.getUseCase();
            Feature feature = pathInput.getFeature();

            UseCase newUseCase = null;
            Feature newFeature = null;
            for (Feature f : ProjectManagerController.getInstance().getAllFeatures())
            {
                if (feature.equals(f))
                {
                    newUseCase = f.getUseCase(useCase.getId());
                    newFeature = f;
                    break;
                }
            }
            if (newUseCase != null)
            {
                UseCaseHTMLGenerator generator = new UseCaseHTMLGenerator();

                try
                {
                    List<Error> newErrors = ProjectManagerController.getInstance().getErrorList();
                    generator.createHTMLFromUseCase(newUseCase, newFeature, newErrors);
                    pathInput.setUseCase(newUseCase);
                    pathInput.setFeature(newFeature);
                    setPartName(newUseCase.getName());
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }

        browser.refresh();
    }

}
