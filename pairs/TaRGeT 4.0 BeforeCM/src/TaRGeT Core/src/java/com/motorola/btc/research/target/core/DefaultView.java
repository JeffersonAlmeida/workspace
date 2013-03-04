/*
 * @(#)DefaultView.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 6, 2007    LIBkk11577   Initial creation.
 * dhq348   Jan 17, 2007    LIBkk11577   Rework of inspection LX133710.
 */
package com.motorola.btc.research.target.core;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;

import com.motorola.btc.research.target.core.TargetCoreActivator;

/**
 * This class represents the default view of the TaRGeT tool.
 * 
 * <pre>
 * CLASS:
 * This class represents the default view of the TaRGeT tool.
 * 
 * RESPONSIBILITIES:
 * 1) Display the TaRGeT logo in background.
 *
 * COLABORATORS:
 * 1) Uses org.eclipse.swt.widgets.Composite
 *
 * USAGE:
 * It is used by a perspective that refers to the ID of this class.
 * E.g. layout.addStandaloneView(DefaultView.ID, false, IPageLayout.LEFT, 1.00f, layout
 .getEditorArea());
 */
public class DefaultView extends ViewPart
{

    /**
     * The id of the view. It is also declared in plugin.xml.
     */
    public static final String ID = "com.motorola.btc.research.target.core.DefaultView";

    /**
     * The parent of the view.
     */
    private Composite parent = null;


    /**
     * Creates the single visual component of the view, that is the logo.
     * 
     * @param parent The component that is the parent of the view.
     */
    public void createPartControl(Composite parent)
    {
        this.parent = parent;
        this.createLogo(this.parent);
    }


    /**
     * Implements the behavior of the view when the focus goes back to it.
     */
    public void setFocus()
    {
    }

    /**
     * Loads from a file and displays the TaRGeT logo.
     * 
     * @param parent The parent component.
     */
    private void createLogo(final Composite parent)
    {
        ImageDescriptor descriptor = TargetCoreActivator
                .getImageDescriptor("icons/logo_TaRGeT.bmp");

        ImageData imageData = descriptor.createImage().getImageData();
        imageData.alpha = 64;

        final Image image = new Image(parent.getDisplay(), imageData);

        Canvas canvas = new Canvas(parent, SWT.NONE);
        canvas.addPaintListener(new PaintListener()
        {
            public void paintControl(PaintEvent e)
            {
                int x = (parent.getBounds().width - image.getBounds().width) / 2;
                int y = (parent.getBounds().height - image.getBounds().height) / 2;
                e.gc.drawImage(image, x, y);
            }
        });
    }
}
