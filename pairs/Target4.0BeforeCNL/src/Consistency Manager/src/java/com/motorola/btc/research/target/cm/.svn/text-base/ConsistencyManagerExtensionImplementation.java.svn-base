/*
 * @(#)ConsistencyManagerExtensionImplementation.java
 *
 * (c) COPYRIGHT 2005 MOTOROLA INC.
 * MOTOROLA CONFIDENTIAL PROPIETARY
 * Template ID and version: IL93-TMP-01-0112  Version 1.10
 *
 * REVISION HISTORY:
 * Author    Date           CR Number    Brief Description
 * -------   ------------   ----------   ----------------------------
 * wxx###   08/07/2008    LIBhh00000   Initial creation.
 */
package com.motorola.btc.research.target.cm;

import java.util.List;

import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.widgets.Shell;

import com.motorola.btc.research.target.cm.wizard.ConsistencyManagementWizard;
import com.motorola.btc.research.target.tcg.extensions.ConsistencyManagerExtension;
import com.motorola.btc.research.target.tcg.extractor.TextualTestCase;

/**
 * CLASS:
 * This class implements the Consistency Manager extension.
 * RESPONSIBILITIES:
 * 1) Open the Consistency Management user interface
 * USAGE:
 * ConsistencyManagerExtension cmExtension = ConsistencyManagerExtensionFactory.newConsistencyManagerExtension();
 */
//INSPECT Felype (integração)
public class ConsistencyManagerExtensionImplementation implements ConsistencyManagerExtension
{

    public void openConsistencyManager(Shell shell, List<TextualTestCase> newTestSuite)
    {
        ConsistencyManagementWizard wizard = new ConsistencyManagementWizard(shell, newTestSuite);
        WizardDialog dialog = new WizardDialog(shell, wizard);
        dialog.open();   
    }
}
