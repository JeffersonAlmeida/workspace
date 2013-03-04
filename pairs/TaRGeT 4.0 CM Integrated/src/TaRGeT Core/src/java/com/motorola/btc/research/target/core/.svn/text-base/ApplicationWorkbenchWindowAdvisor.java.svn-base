/*
 * @(#)ApplicationWorkbenchWindowAdvisor.java
 *
 * (c) COPYRIGHT 2006 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wra050    Jul 6, 2006    LIBkk11577   Initial creation.
 * wdt022    May 15, 2007   LIBmm26220   Changes due to modification in class WordDocumentProcessing.
 * wmr068    Aug 07, 2008   LIBqq64190   Method dispose removed. The MS Word plug-in is now responsible for finishing the listener.
 */
package com.motorola.btc.research.target.core;

import java.awt.Dimension;
import java.awt.Toolkit;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

/**
 * <pre>
 * CLASS:
 * Used to configure the workbench window of TargetApplication.
 * This class is instantiated when the workbench window is being 
 * created. 
 * 
 * Created by New Eclipse Plug-in Project wizard
 * 
 * RESPONSIBILITIES:
 * 1) Create the actionBarAdvisor
 * 2) Configure the visibility of menuBar (preWindowOpen overriden method)
 *
 * COLABORATORS:
 * 
 *
 * USAGE:
 * This class is used internally by eclipse platform. Architecture chosen 
 * for implement TargetApplication. 
 * </pre>
 */
public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor
{
    /**
     * Default Constructor. Created by New Eclipse Plug-in Project wizard
     * 
     * @param configurer The workbench window configurer.
     */
    public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer)
    {
        super(configurer);
    }

    /**
     * Returns the a instance of actionBarAdvisor. Created by New Eclipse Plug-in Project wizard
     * 
     * @param configurer The action bar configurer.
     */
    public ActionBarAdvisor createActionBarAdvisor(IActionBarConfigurer configurer)
    {
        return new ApplicationActionBarAdvisor(configurer);
    }

    /**
     * Used to configure the menuBar, toolBar, statusBar visibility; window size and title. Created
     * by New Eclipse Plug-in Project wizard.
     */
    public void preWindowOpen()
    {
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();

        configurer.setTitle("TaRGeT - Test and Requirements Generation Tool");

        configurer.setShowCoolBar(false);
        configurer.setShowStatusLine(false);
        configurer.setShowMenuBar(true);
    }

    /**
     * Sets the size and the position of the application screen. The set size is 800x600.
     * 
     * @see org.eclipse.ui.application.WorkbenchWindowAdvisor#postWindowOpen()
     */
    
    public void postWindowCreate()
    {
        super.postWindowCreate();
        IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        int x = Math.max(0, (dim.width - 800) / 2);
        // It is moved up 22 pixels in order to place the water mark at the center position.
        int y = Math.max(0, (dim.height - 600) / 2 - 22);

        configurer.getWindow().getShell().setBounds(new Rectangle(x, y, 800, 600));
    }

    
    /** 
     * Finishes the .NET process. It calls the method <code>finishListener</code>
     * from the <code>WordDocumentProcessing</code> class.
     * 
     * @see com.motorola.btc.research.target.common.ucdoc.xml.WordDocumentProcessing
     * 
     */
    //INSPECT this method is no longer needed. The word plugin is now responsible
    //for finishing the listener.
//    
//    public void dispose()
//    {
//        try
//        {
//            WordDocumentProcessing.getInstance().finishListener();
//        }
//        catch (UseCaseDocumentXMLException e)
//        {
//            e.printStackTrace();
//        }
//    }
}
