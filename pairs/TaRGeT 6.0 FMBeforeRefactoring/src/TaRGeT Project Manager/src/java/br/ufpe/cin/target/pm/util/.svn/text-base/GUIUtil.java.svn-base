/*
 * @(#)GUIUtil.java
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
 * dqh348   Dec 11, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX133710.
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * pvcv     Apr 01, 2009				 Internationalization support.
 * lmn3     May 14, 2009				 Changes due new input and output documents formats.
 * fsf2     Jul 15, 2009                 Added addListenerToAllObjects method.
 * lmn3		Jul 20, 2009				 Creation of method  getFileExtension.
 */
package br.ufpe.cin.target.pm.util;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IPerspectiveDescriptor;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.WorkbenchException;
import org.eclipse.ui.forms.widgets.Section;

import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.GUIManager;
import br.ufpe.cin.target.pm.PMActivator;
import br.ufpe.cin.target.pm.common.TargetTreeView;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.views.artifacts.ArtifactsView;
import br.ufpe.cin.target.pm.views.features.FeaturesView;


/**
 * Class that provides utility functions to all GUI classes.
 * 
 * <pre>
 * CLASS:
 * Class that provides utility functions to all GUI classes. E.g. closing a project or displaying an specified perspective.
 * 
 * RESPONSIBILITIES:
 * 1) Close a project
 * 2) Display an specified perspective
 *  
 * USAGE:
 * All methods should be accessed in a static way.
 * E.g. GUIUtil.closeProject(shell);
 */

//INSPECT inclusão do metodo getExtensionsViews
public class GUIUtil
{
	 /**
     * <i>ProjectManagerView</i> extension point id.
     */
    private static final String VIEW_EXTENSION_POINT_ID = "views";

    /**
     * Name of configuration element from <i>ProjectManagerView</i> extension point.
     */
    private static final String VIEW_CONFIGURATION_ELEMENT_NAME = "viewExtension";

    /**
     * Attribute name from configuration element viewId from <i>ProjectManagerView</i> extension
     * point.
     */
    private static final String VIEW_ID_ATTRIBUTE_NAME = "viewId";
	
    /**
     * Closes the current opened project. 1) Closes the logical project 2) Cleans the views 3)
     * Closes the perspective of the active page 4) Detach all observers of the error view 5)
     * Disables close project action 6) Clears the error list
     * 
     * @param shell The shell used to retrieve visual components.
     */
    public static void closeProject(Shell shell)
    {
        /* closes the target project */
        ProjectManagerController.getInstance().closeProject();

        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();

        /* cleans all TargetTreeView data */
        ((TargetTreeView) GUIManager.getInstance().getView(FeaturesView.ID, false)).clean();
        ((TargetTreeView) GUIManager.getInstance().getView(ArtifactsView.ID, false)).clean();

        IWorkbenchPage page = window.getActivePage();
        page.closeEditors(page.getEditorReferences(), false);

        /* closes the active perspective */
        IPerspectiveDescriptor perspective = window.getActivePage().getPerspective();
        window.getActivePage().closePerspective(perspective, false, true);

        GUIUtil.setDefaultTitle();

    }

    /**
     * Displays the perspective specified by its id (perspectiveID).
     * 
     * @param perspectiveID The id of the perspective to be displayed.
     * @throws WorkbenchException Thrown when an error occurs when trying to display a perspective.
     */
    public static void showPerspective(String perspectiveID) throws WorkbenchException
    {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        workbench.showPerspective(perspectiveID, window);
    }

    /**
     * Method that opens a simple YES/NO dialog.
     * 
     * @param parent The parent shell of the dialog
     * @param title The dialog's title
     * @param message The message
     * @param dialogImageType The type of the image to be displayed. (E.g. MessageDialog.QUESTION)
     * @return <code>true</code> if the user presses the YES button or <code>false</code> otherwise.
     */
    public static boolean openYesOrNo(Shell parent, String title, String message,
            int dialogImageType)
    {
        MessageDialog dialog = new MessageDialog(parent, title, null, message, dialogImageType,
                new String[] { IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 0);
        return dialog.open() == 0;
    }

    /**
     * Method that opens a simple YES/NO dialog.
     * 
     * @param parent The parent shell of the dialog
     * @param title The dialog's title
     * @param message The message
     * @return <code>true</code> if the user presses the YES button or <code>false</code> otherwise.
     */
    public static boolean openYesOrNoInformation(Shell parent, String title, String message)
    {
        return openYesOrNo(parent, title, message, MessageDialog.QUESTION);
    }

    /**
     * Method that opens a simple YES/NO dialog.
     * 
     * @param parent The parent shell of the dialog
     * @param title The dialog's title
     * @param message The message
     * @return <code>true</code> if the user presses the YES button or <code>false</code> otherwise.
     */
    public static boolean openYesOrNoError(Shell parent, String title, String message)
    {
        return openYesOrNo(parent, title, message, MessageDialog.WARNING);
    }

    /**
     * Sets the title to its default value. It can be used to set the title when there is no opened
     * project in TaRGeT.
     */
    public static void setDefaultTitle()
    {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        shell.setText(Properties.getProperty("target"));

    }

    /**
     * Sets the title using the given project name that is current opened in TaRGeT.
     * 
     * @param projectName The name of the opened project used to set the title.
     */
    public static void setCustomTitle(String projectName)
    {
        Shell shell = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getShell();
        shell.setText(projectName + " - TaRGeT");
    }

    /**
     * Returns the extension of a given file name.
     * 
     * @param fileName the name of the file
     * @return the file extension
     */
    public static String getFileExtension(String fileName)
    {
        for (int i = fileName.length(); i > 0; i--)
        {
            if (fileName.substring(i - 1, i).equals("."))
            {
                return fileName.substring(i - 1);
            }
        }
        return "";
    }

    /**
     * Adds listener to all components witch inherit from Composite except Tables, Trees and Grids.
     * 
     * @param section
     */
    public static void addListenerToAllObjects(Composite composite, final Section section)
    {
        Control[] controls = composite.getChildren();

        for (int i = 0; i < controls.length; i++)
        {
            if (controls[i] instanceof Composite)
            {
                addListenerToAllObjects((Composite) controls[i], section);
            }

            if (controls[i] instanceof Composite && !(controls[i] instanceof Table)
                    && !(controls[i] instanceof Tree) && !(controls[i] instanceof Grid)
                    && !(controls[i] instanceof Combo) && !(controls[i] instanceof Spinner))
            {
                controls[i].addMouseListener(new MouseListener()
                {
                    
                    public void mouseDoubleClick(MouseEvent e)
                    {
                        
                    }

                    
                    public void mouseDown(MouseEvent e)
                    {
                        section.setFocus();
                    }

                    
                    public void mouseUp(MouseEvent e)
                    {
                        
                    }
                });
            }
        }
    }
    /**
     * Process all the extensions for the <i>ProjectManagerView</i> extension point.
     * 
     * @return All the view IDs of those extending plugins.
     */
    public static Set<String> getExtensionViews()
    {
        Set<String> result = new HashSet<String>();
        IExtension[] extensions = Platform.getExtensionRegistry().getExtensionPoint(
                PMActivator.PLUGIN_ID, VIEW_EXTENSION_POINT_ID).getExtensions();

        for (IExtension extension : extensions)
        {
            IConfigurationElement[] configElements = extension.getConfigurationElements();

            for (IConfigurationElement configElement : configElements)
            {
                if (configElement.getName().equals(VIEW_CONFIGURATION_ELEMENT_NAME))
                {
                    String viewIdElement = configElement.getAttribute(VIEW_ID_ATTRIBUTE_NAME);

                    if (viewIdElement != null)
                    {
                        result.add(viewIdElement);
                    }
                }
            }
        }

        return result;
    }
}
