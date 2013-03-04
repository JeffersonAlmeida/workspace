/*
 * @(#)RequirementPerspective.java
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
 * wra050   Jul 12, 2006    LIBkk11577   Initial creation.
 * dhq348   May 22, 2007    LIBmm25975   Added the view SearchResultsView.
 * dhq348   Jun 21, 2007    LIBmm25975   Added support to search interface.
 */
package br.ufpe.cin.target.pm.perspectives;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import br.ufpe.cin.target.pm.util.GUIUtil;
import br.ufpe.cin.target.pm.views.ErrorView;
import br.ufpe.cin.target.pm.views.SearchResultsView;
import br.ufpe.cin.target.pm.views.artifacts.ArtifactsView;
import br.ufpe.cin.target.pm.views.features.FeaturesView;

/**
 * <pre>
 *  CLASS:
 *  This class offer a placeholder to requirements related views.
 *  
 *  RESPONSABILITIES:
 *  Define the position of requirement views in workbench. 
 *  
 *  USAGE: 
 *  This class is used internally to the platform. 
 * </pre>
 */
//INSPECT remoção do metodo getExtensionsViews
public class RequirementPerspective implements IPerspectiveFactory
{
    /**
     * The perspective ID declared in plugin.xml
     */
    public static final String ID = "br.ufpe.cin.target.pm.ui.RequirementPerspective";

    /**
     * Set of all visible views Ids in the perspective.
     */
    private static Set<String> visibleViewIDs = new HashSet<String>();

   

    /**
     * Create the initial layout. In this method, the position of all views can be defined.
     * 
     * @param layout Layout used to organize the views.
     */
    public void createInitialLayout(IPageLayout layout)
    {
        layout.setEditorAreaVisible(true);
        layout.addStandaloneView(FeaturesView.ID, true, IPageLayout.LEFT, 0.3f, layout
                .getEditorArea());

        IFolderLayout folderLayout = layout.createFolder("other_views", IPageLayout.BOTTOM, 0.6f,
                layout.getEditorArea());
        folderLayout.addPlaceholder(ArtifactsView.ID + ":*");
        folderLayout.addView(ArtifactsView.ID);
        folderLayout.addView(ErrorView.ID);
        folderLayout.addView(SearchResultsView.ID);

        layout.getViewLayout(FeaturesView.ID).setCloseable(false);
        layout.getViewLayout(ArtifactsView.ID).setCloseable(false);
        layout.getViewLayout(ErrorView.ID).setCloseable(false);
        layout.getViewLayout(SearchResultsView.ID).setCloseable(false);
        visibleViewIDs.add(FeaturesView.ID);
        visibleViewIDs.add(ArtifactsView.ID);
        visibleViewIDs.add(ErrorView.ID);
        visibleViewIDs.add(SearchResultsView.ID);

        for (String viewId : GUIUtil.getExtensionViews())
        {
            visibleViewIDs.add(viewId);
            folderLayout.addView(viewId);
            layout.getViewLayout(viewId).setCloseable(false);
        }
    }

    

    /**
     * 
     * Returns the ids of all visible views.
     *  
     * @return A set of strings representing the view ids.
     */
    public static Set<String> getVisibleViewsIds()
    {
       return new HashSet<String>(visibleViewIDs);
    }
}
