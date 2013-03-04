/*
 * @(#)GUIManager.java
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
 * dhq348   Dec 13, 2006    LIBkk11577   Initial creation.
 * dhq348   Jan 18, 2007    LIBkk11577   Rework of inspection LX133710.
 * dhq348   Feb 16, 2007    LIBll27713   Rework of inspection LX144782. 
 * wsn013   Fev 26, 2007    LIBll29555   Changes according to LIBll29555.
 * dhq348   Jun 21, 2007    LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * dhq348   Jun 26, 2007    LIBmm25975   Rework after meeting 2 of inspection LX179934.
 * dhq348   Sep 03, 2007    LIBnn24462   Updated method refreshViews().
 * dhq348   Oct 15, 2007    LIBnn34008   The method refreshViews() now supports interruption updates.
 * dhq348   Jan 14, 2008    LIBnn34008   Rework after inspection LX229625.
 * wdt022   Mar 25, 2008    LIBpp56482   refreshView method updated.
 * wdt022   Apr 01, 2008    LIBpp56482   Changes due to actions framework refactoring.
 * tnd783   Apr 07, 2008    LIBpp71785   Added refreshEditors() method.
 * wln013   May 02, 2008    LIBpp56482   Rework after meeting 1 of inspection LX263835.
 * tnd783   Jul 21, 2008    LIBpp71785   Rework after inspection LX285039. 
 * tnd783	Aug 05, 2008	LIBqq51204	 Changes made to add the refresh automatically functionality.
 * pvcv     Apr 01, 2009				 Internationalization support.
 * lmn3		May 14, 2009				 Changes due code inspection.
 * fsf2     Jun 29, 2009                 Updated method refreshEditors.
 */
package br.ufpe.cin.target.pm;

import java.util.List;
import java.util.Vector;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.pm.common.TargetView;
import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.editors.RefreshableEditor;
import br.ufpe.cin.target.pm.progressbars.ReloadProjectProgressBar;
import br.ufpe.cin.target.pm.search.TargetIndexDocument;
import br.ufpe.cin.target.pm.util.GUIUtil;
import br.ufpe.cin.target.pm.views.ErrorView;
import br.ufpe.cin.target.pm.views.SearchResultsView;
import br.ufpe.cin.target.pm.views.artifacts.ArtifactsView;
import br.ufpe.cin.target.pm.views.features.FeaturesView;


/**
 * Provides GUI utility methods.
 * 
 * <pre>
 * CLASS:
 * Provides GUI utility methods.
 * 
 * RESPONSIBILITIES:
 * 1) Manage interface functionalities.
 * 
 * USAGE:
 * GUIManager manager = GUIManager.getInstance()
 */
public class GUIManager
{
    private static final String DEFAULT_VIEW_ID = "br.ufpe.cin.target.core.DefaultView";

    /**
     * The instance of GUIManager
     */
    private static GUIManager instance = null;

    /**
     * These listeners are responsible for notifying the actions that the status of the project The
     * open project property.
     */
    public static final String OPEN_PROJECT = "open_project";

    /**
     * The close project property.
     */
    public static final String CLOSE_PROJECT = "close_project";

    /**
     * The create project property.
     */
    public static final String CREATE_PROJECT = "create_project";

    /**
     * The refresh automatically preference.
     */
    private boolean refreshAutomatically = true;

    /**
     * The private constructor of the class.
     */
    private GUIManager()
    {
        // the private constructor of the class
    }

    /**
     * Returns the single instance of GUIManager
     * 
     * @return The single instance of GUIManager
     */
    public static GUIManager getInstance()
    {
        if (instance == null)
        {
            instance = new GUIManager();
        }
        return instance;
    }

    /**
     * Returns a view by its ID. If <code>restore</code> is <i>true</i>, it tries to restore the
     * previously opened view.
     * 
     * @param viewID The id of the view to be returned.
     * @param restore Indicates if the view is going to be restored.
     * @return A view.
     */
    public IViewPart getView(String viewID, boolean restore)
    {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        IViewReference reference = page.findViewReference(viewID, null);
        return reference.getView(restore);
    }

    /**
     * Checks if at least one opened view exists. This implementation ignores the default view.
     * 
     * @return <b>True</b> if exists at least one opened view or <b>false</b> otherwise.
     */
    public boolean hasOpenedViews()
    {
        boolean result = false;
        IWorkbench workbench = PlatformUI.getWorkbench();
        if (workbench != null)
        {
            IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
            if (window != null)
            {
                IWorkbenchPage page = window.getActivePage();
                if (page != null)
                {
                    IViewReference[] references = page.getViewReferences();

                    result = (references.length > 0 && !references[0].getId().equals(
                            DEFAULT_VIEW_ID));
                }
            }
        }
        return result;
    }

    /**
     * Refresh all views.
     * 
     * @throws TargetException In case any search error occurs.
     */
    //INSPECT - Lais Inclusão de refresh de views que implementam extension point views em PM
    public void refreshViews() throws TargetException
    {
        if (hasOpenedViews())
        {
            ((FeaturesView) getView(FeaturesView.ID, false)).update();
            ((ArtifactsView) getView(ArtifactsView.ID, false)).update();
            ((ErrorView) getView(ErrorView.ID, true)).update();
            /* updates the search results according to the last query */
            ((SearchResultsView) getView(SearchResultsView.ID, true)).update();
            //updates extension views
            for (String viewId : GUIUtil.getExtensionViews())
            {
            	((TargetView) getView(viewId, true)).update();
            }
            
        }
    }

    /**
     * Refresh all editors.
     * @param refreshAnyway 
     */
    public void refreshEditors(boolean refreshAnyway)
    {
        IEditorReference[] editors = PlatformUI.getWorkbench().getActiveWorkbenchWindow()
                .getActivePage().getEditorReferences();
        
        List<IEditorReference> cms = new Vector<IEditorReference>();
        
        for (IEditorReference editor : editors)
        {
            IEditorPart ep = editor.getEditor(false);
            
            if(editor.getId().equals("br.ufpe.cin.target.cm.editor.ConsistencyManagementMultiPageEditor"))
            {
                cms.add(editor);
            }
            else if(ep instanceof RefreshableEditor)
            {
                ((RefreshableEditor) ep).refresh(refreshAnyway);
            }
        }
        
        //OnTheFlyEditor refresh must be done before Consistency Manager Editor Refresh
        for (IEditorReference editor : cms)
        {
            IEditorPart ep = editor.getEditor(false);
            
            if(ep instanceof RefreshableEditor)
            {
                ((RefreshableEditor) ep).refresh(refreshAnyway);
            }
        }
    }

    /**
     * Refreshes the project by reloading the project's progress bar and refreshing all the views
     * and editors.
     * 
     * @param shell The shell used to display the progress bar.
     */

    public void refreshProject(Shell shell, boolean refreshAnyway)
    {
        try
        {
            boolean reloadProject = this.reloadProjectProgressBar(shell);
            
            if(refreshAnyway || reloadProject){
            	this.refreshEditors(false);
            }
            
            this.refreshViews();
        }
        catch (TargetException e)
        {
            MessageDialog.openError(shell, Properties.getProperty("error_while_reloading_project"),
                    e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Displays the reload progress bar.
     * 
     * @param shell The shell used to display the progress bar.
     * @return <code>true</code> if the bar was displayed.
     */

    private boolean reloadProjectProgressBar(Shell shell)
    {
        boolean result = false;
        try
        {
            if (ProjectManagerController.getInstance().hasDocumentModification())
            {
                ReloadProjectProgressBar bar = new ReloadProjectProgressBar();
                ProgressMonitorDialog dialog = new ProgressMonitorDialog(shell);
                dialog.setCancelable(false);
                dialog.run(true, true, bar);

                if (bar.getException() != null)
                {
                    MessageDialog.openError(shell, Properties
                            .getProperty("error_while_reloading_project"), bar.getException()
                            .getMessage());
                }
                result = true;
            }
        }
        catch (Exception e)
        {
            MessageDialog.openError(shell, Properties.getProperty("error_while_reloading_project"),
                    e.getMessage());
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Update the title of TaRGeT considering if there is an opened project. If no project is
     * opened, the default title is set.
     */
    public void updateApplicationTitle()
    {
        if (ProjectManagerController.getInstance().hasOpenedProject())
        {
            String projectName = ProjectManagerController.getInstance().getCurrentProject()
                    .getName();
            GUIUtil.setCustomTitle(projectName);
        }
        else
        {
            GUIUtil.setDefaultTitle();
        }
    }

    /**
     * Places the specified <code>view</code> on the top of the current views. If it is not visible
     * then makes it visible. This method is used, for instance, when a search is performed and the
     * search results are displayed.
     * 
     * @param view The view to be activated.
     */
    public void activateView(TargetView view)
    {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        page.activate(view);
    }

    /**
     * Displays the search results in <code>SearchResultsView</code>.
     * 
     * @param query The query used in the search.
     * @param documents The results to be displayed.
     */
    public void displaySearchResults(String query, List<TargetIndexDocument> documents)
    {
        SearchResultsView view = ((SearchResultsView) GUIManager.getInstance().getView(
                SearchResultsView.ID, true));
        view.addSearchResults(documents, query);
    }

    /**
     * Returns the current state of the refresh automatically preference.
     * 
     * @return The refresh automatically preference.
     */

    public boolean isRefreshAutomatically()
    {
        return refreshAutomatically;
    }

    /**
     * Switches the state of the refresh automatically preference.
     */

    public void switchRefreshAutomatically()
    {
        this.refreshAutomatically = !this.refreshAutomatically;
    }

}
