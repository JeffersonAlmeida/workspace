/*
 * @(#)TargetView.java
 *
 *
 * (Copyright (c) 2007-2009 Research Project Team-CIn-UFPE)
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS
 * OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 * OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE
 * USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 * 
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Jan 8, 2007    LIBkk11577   Initial creation.
 * dhq348   Jan 29, 2007   LIBll12753   Rework of inspection LX136878.
 */
package br.ufpe.cin.target.pm.common;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.StructuredViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.part.ViewPart;

import br.ufpe.cin.target.common.exceptions.TargetException;

/**
 * Generic view. This is the base class for all views in the project.
 * 
 * <pre>
 * CLASS:
 * This is the base class for all views in the project.
 */
public abstract class TargetView extends ViewPart
{
    /**
     * The parent component of the view.
     */
    protected Composite parent;

    /**
     * Creates the view visual components.
     * 
     * @param parent The parent component of the view.
     */
    //INSPECT - Felype: Tirei o final.
    
    public void createPartControl(Composite parent)
    {
        this.parent = parent;
        this.createIndividualControl();
        this.setActionEnablementCapability();
    }

    /**
     * Creates each individual view part control.
     */
    public abstract void createIndividualControl();

    /**
     * This is the default implementation of setFocus to all views.
     */
    
    public void setFocus()
    {
    }

    /**
     * Sets the initial action selection, so the actions are initially unmarked (disabled). It is
     * implemented a basic StructuredViewer that only returns a list with a single item in
     * getSelectionFromWidget and a simple label as a control in getControl. This viewer is used
     * mainly because it was difficult to disable the actions at the tool startup, so the action now
     * in their xml declarations have the enablesfor attribute set to '+' which means that it begins
     * disabled and when any selection is activated their status is changed.
     */
    protected void setActionEnablementCapability()
    {
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

            
            protected void setSelectionToWidget(List l, boolean reveal)
            {
            }

            /**
             * Gets a simple label as a control.
             */
            
            public Control getControl()
            {
                Label label = new Label(parent.getShell(), SWT.NONE);
                return label;
            }
        };
        /* sets the new viewer as a selection provider of the current view */
        this.getSite().setSelectionProvider(tmpViewer);
        /* sets a single empty selection that enables any action waiting for a selection */
        tmpViewer.setSelection(new StructuredSelection());
    }
    
    /**
     * Method that is called to update the view contents.
     * 
     * @throws TargetException In case any error during update.
     */
    public abstract void update() throws TargetException;
}
