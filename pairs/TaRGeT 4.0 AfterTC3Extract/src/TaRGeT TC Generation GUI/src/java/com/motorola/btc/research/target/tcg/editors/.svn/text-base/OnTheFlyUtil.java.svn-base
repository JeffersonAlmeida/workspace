/*
 * @(#)OnTheFlyUtil.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wdt022    Apr 15, 2008   LIBpp56482   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 */
package com.motorola.btc.research.target.tcg.editors;

import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/**
 * 
 * <pre>
 * CLASS:
 * This class provides some usual methods for on the fly classes. 
 * 
 * </pre>
 *
 */
public class OnTheFlyUtil
{

    /**
     * Represents the color for new test cases.
     */
    public static final RGB ADDED_ITEM_RGB = new RGB(0, 200, 0);

    /**
     * Represents the color for removed test cases.
     */
    public static final RGB REMOVED_ITEM_RGB = new RGB(255, 0, 0);

    /**
     * Represents the color common new test cases.
     */
    public static final RGB COMMON_ITEM_RGB = new RGB(0, 0, 0);
    
    /**
     * Represents the gray color.
     */
    public static final RGB GRAY_RGB = new RGB (192, 192, 192);
    
    /**
     * Constructs a new instance of GridData according to the parameters.
     * @param style The GridData style.
     * @param widthHint The preferred width in pixels.
     * @param heightHint The preferred height in pixels.
     * @return
     */
    public static GridData createGridData(int style, int widthHint, int heightHint)
    {
        GridData gd = new GridData(style);
        gd.widthHint = widthHint;
        gd.heightHint = heightHint;
        return gd;
    }

    /**
     * Creates a Section according to the parameters.
     * 
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     * @param style The section style.
     * @return The section widget.
     */
    public static Section createSection(ScrolledForm form, FormToolkit toolkit, int style)
    {

        Section section = toolkit.createSection(form.getBody(), style);
        section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
        section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));

        return section;
    }

    /**
     * Creates a composite according to the parameters.
     * @param toolkit The toolkit used by this form.
     * @param section The composite parent. 
     * @param style The composite style.
     * @param columns The number of columns.
     * @return A composite 
     */
    public static Composite createComposite(FormToolkit toolkit, Section section, int style, int columns)
    {
        Composite client = toolkit.createComposite(section, style);
        client.setLayout(createGridLayout(columns));
        return client;
    }
    
    /**
     * Creates a grid layout with default style and a specific number of columns.
     * @param columns The number of columns.
     * @return A grid layout with a specific number of columns.
     */
    public static GridLayout createGridLayout(int columns)
    {
        GridLayout layout = new GridLayout();
        layout.numColumns = columns;
        return layout;
    }
    
}
