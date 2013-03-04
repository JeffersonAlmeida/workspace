/*
 * @(#)ApplicationWorkbenchWindowAdvisor.java
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
 * wra050    Jul 6, 2006    LIBkk11577   Initial creation.
 * dhq348    May 16, 2007   LIBmm25975   Registered Actions.
 * dhq348    Jun 21, 2007   LIBmm25975   Rework after meeting 3 of inspection LX179934.
 * tnd783	 Jul 31, 2008	LIBqq51204	 Changes made to method fillMenuBar.
 * dwvm83	 Sep 26, 2008	LIBqq51204	 Rework after inspection LX302177.
 */
package br.ufpe.cin.target.core;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import br.ufpe.cin.target.common.util.Properties;


/**
 * <pre>
 * CLASS:
 * Used to configure and initialize default actions and 
 * menus used by Target Core plug-in.
 * 
 * Created by New Eclipse Plug-in Project wizard
 * 
 * RESPONSIBILITIES:
 * 
 * 1) fill the tool bar with main actions 
 * 2) fill the menu bar with main options
 * 3) fill the status line with main status line text
 *
 * COLABORATORS:
 * 
 * USAGE:
 * This class is used internally by eclipse platform. Architecture chosen 
 * for implement TargetApplication. No call to this class is implemented 
 * by development team. 
 * </pre>
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor
{
    /**
     * The exit action
     */
    private IWorkbenchAction exitAction;

    /**
     * The about dialog invocation action.
     */
    private IWorkbenchAction aboutAction;

    /**
     * The help dialog invocation action
     */
    private IWorkbenchAction helpAction;

    /**
     * Default constructor. Created by New Eclipse Plug-in Project wizard
     * 
     * @param configurer The action bar configurer.
     */
    public ApplicationActionBarAdvisor(IActionBarConfigurer configurer)
    {
        super(configurer);
    }

    /**
     * <pre>
     * Create the default actions of Target Core Plug-in. Default actions used 
     * in this project are:
     * <ul>  
     *  <li> new project </li> 
     *  <li> about </li>
     * </ul>
     * 
     * Note that ActionFactory has default implementations from some of 
     * these action. 
     *  </pre>
     * 
     * @param window The workbench window.
     */
    protected void makeActions(IWorkbenchWindow window)
    {
        exitAction = ActionFactory.QUIT.create(window);
        aboutAction = ActionFactory.ABOUT.create(window);
        helpAction = ActionFactory.HELP_CONTENTS.create(window);
        
        /*
         * It is necessary to register the actions to ensure that key bindings will work properly.
         */
        
        this.register(exitAction);
        this.register(helpAction);
        this.register(aboutAction);
    }

    /**
     * Fill the menu bar with default actions. Other menu actions must be defined by plug-ins.
     * 
     * @param menuBar The menu bar to be filled with menus
     */
    
    protected void fillMenuBar(IMenuManager menuBar)
    {
        // note: MenuManager looks like JMenu
        MenuManager fileMenu = new MenuManager(Properties.getProperty("file"), "file");
        MenuManager helpMenu = new MenuManager(Properties.getProperty("help"), "help");
        MenuManager generateMenu = new MenuManager(Properties.getProperty("tools"), "tools");
        MenuManager artifactsMenu = new MenuManager(Properties.getProperty("artifacts"), "artifacts");
        MenuManager projectMenu = new MenuManager(Properties.getProperty("project"), "project");

        fileMenu.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
        fileMenu.add(new Separator());
        fileMenu.add(exitAction);
        helpMenu.add(helpAction);
        helpMenu.add(aboutAction);

        menuBar.add(fileMenu);
        menuBar.add(artifactsMenu);
        menuBar.add(projectMenu);
        menuBar.add(generateMenu);
        menuBar.add(helpMenu);
    }
}
