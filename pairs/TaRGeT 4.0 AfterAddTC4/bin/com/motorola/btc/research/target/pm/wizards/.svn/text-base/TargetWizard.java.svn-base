/*
 * @(#)TargetWizard.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * dhq348   Nov 28, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 06, 2007    LIBkk11577   Inspection (LX124184) faults fixing.
 * wdt022   Mar 08, 2007    LIBll29572   Modification according to CR.
 * dhq348   Apr 26, 2007    LIBmm09879   Modifications according to CR.
 * dhq348   Apr 28, 2007    LIBmm09879   Rework of inspection LX168613.
 */
package com.motorola.btc.research.target.pm.wizards;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.swt.widgets.Shell;

import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.pm.progressbars.TargetProgressBar;

/**
 * Represents a generic Target wizard.
 * 
 * <pre>
 *   CLASS:
 *   Represents a generic Target wizard.
 *   
 *   RESPONSIBILITIES:
 *   1) Manages the exceptions thrown in Target wizards
 *  
 *   COLABORATORS:
 *   1) Uses Shell
 *  
 *   USAGE:
 *   It is extended by Target wizards.
 */
public abstract class TargetWizard extends Wizard
{
    /**
     * A reference to the parent shell of the wizard
     */
    protected Shell parentShell;

    /**
     * The boolean result of any operation that can crash the wizard processing.
     */
    protected boolean isNotCrashed; 

    /**
     * The progress bar started after the last step of the wizard
     */
    protected TargetProgressBar progressBar;

    /**
     * Constructor.
     * 
     * @param parentShell The parent shell.
     */
    public TargetWizard(Shell parentShell)
    {
        super();
        this.parentShell = parentShell;
        this.progressBar = null;
    }


    /**
     * Called when a wizard is finished.
     * 
     * @return <code>True</code> if the wizard finishes correctly and <code>false</code>
     * otherwise.
     */
    public boolean performFinish()
    {
        this.isNotCrashed = false;
        try
        {
            this.isNotCrashed = this.init();
        }
        catch (TargetException e)
        {
            MessageDialog.openError(this.parentShell, "Error while " + this.getLocation(), e
                    .getMessage());
            e.printStackTrace();
        }
        catch (Exception e)
        {
            MessageDialog.openError(this.parentShell, "Error while " + this.getLocation(),
                    "An error ocurred while " + this.getLocation());
            e.printStackTrace();
        }
        finally
        {
            this.isNotCrashed = this.finish();
        }

        return this.isNotCrashed;
    }

    /**
     * Abstract method that is intended to be responsible for executing the main processing of the
     * wizard.
     * 
     * @return <i>True</i> if everything went fine in the processing and <i>false</i> otherwise.
     * @throws Exception
     */
    protected abstract boolean init() throws Exception;

    /**
     * Performs operations in the end of the wizard processing.
     * 
     * @return <i>True</i> if everything went fine in the processing and <i>false</i> otherwise.
     */
    protected abstract boolean finish();

    /**
     * Returns the current wizard name.
     * 
     * @return The current wizard name.
     */
    protected abstract String getLocation();
}
