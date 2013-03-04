/*
 * @(#)Perspective.java
 *
 * (c) COPYRIGHT 2006 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 *  dhq348        -         LIBkk11577   Initial creation.
 *  dhq348   6 Jan, 2007    LIBkk11577   Added DefaultView invocation and JavaDoc.
 */
package com.motorola.btc.research.target.core;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

/**
 * This class represents the default RCP perspective of the TaRGeT tool.
 * 
 * <pre>
 * CLASS:
 * This class represents the default RCP perspective of the TaRGeT tool.
 * 
 * RESPONSIBILITIES:
 * 1) Add the default view.
 *
 * USAGE:
 * This class is invoked by ApplicationWorkbenchAdvisor by referring to its ID.
 */
public class Perspective implements IPerspectiveFactory
{
    /**
     * The public id of the perspective. It is also referred in plugin.xml.
     */
    public static final String ID = "com.motorola.btc.research.target.perspective";

    /**
     * Creates the initial layout of the perspective by adding the DefaultView to it.
     */
    public void createInitialLayout(IPageLayout layout)
    {
        layout.setEditorAreaVisible(false);
        layout.addStandaloneView(DefaultView.ID, false, IPageLayout.LEFT, 1.00f, layout
                .getEditorArea());
    }
}
