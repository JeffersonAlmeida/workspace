/*
 * @(#)CNLView.java
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
 * dgt                                    Initial creation.
 * lmn3      02/09/2009                   Inclusion of table sorter and table filter.
 */
package br.ufpe.cin.target.cnl.views;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import br.ufpe.cin.cnlframework.exceptions.CNLException;
import br.ufpe.cin.cnlframework.grammar.TestCaseTextType;
import br.ufpe.cin.target.cnl.Activator;
import br.ufpe.cin.target.cnl.actions.AddNewLexicalTermActionDelegate;
import br.ufpe.cin.target.cnl.actions.EditRemoveTermsActionDelegate;
import br.ufpe.cin.target.cnl.controller.CNLFault;
import br.ufpe.cin.target.cnl.controller.CNLLexionFault;
import br.ufpe.cin.target.cnl.controller.CNLPluginController;
import br.ufpe.cin.target.common.ucdoc.UseCaseDocument;
import br.ufpe.cin.target.common.util.FileUtil;

import br.ufpe.cin.target.pm.common.TargetTableView;
import br.ufpe.cin.target.pm.controller.TargetProject;
import br.ufpe.cin.target.pm.export.ProjectManagerExternalFacade;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentData;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentExtension;
import br.ufpe.cin.target.pm.extensions.input.InputDocumentExtensionFactory;

/**
 * This class represents the CNL plugin view.
 * 
 * @author
 */
public class CNLView extends TargetTableView
{
    public static final String ID = "br.ufpe.cin.target.cnl.views.CNLView";

    private TargetProject currentProject;

    private CNLTableSorter tableSorter;

    private CNLViewerFilter filter;

    private List<CNLFault> faultList;

    class ViewLabelProvider extends LabelProvider implements ITableLabelProvider
    {
        private Image lexiconFault = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                "icons/lexiconFaultIcon_16_16.bmp").createImage();

        private Image grammarFault = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
                "icons/grammarFaultIcon_16_16.png").createImage();
        
        public String getColumnText(Object obj, int index)
        {
            CNLFault fault = (CNLFault) obj;
            String result = "";
            switch (index)
            {
                case 0:
                    result = fault.getDetails();
                    break;
                case 1:
                    result = fault.getSentence();
                    break;
                case 2:
                    result = fault.getStep();
                    break;
                case 3:
                    result = fault.getResource();
                    break;
                default:
                    break;
            }
            return result;
        }

        public Image getColumnImage(Object obj, int index)
        {
            if (index == 0)
            {
                CNLFault fault = (CNLFault) obj;
                if (fault instanceof CNLLexionFault)
                {
                    return lexiconFault;                    
                }
                else
                {
                    return grammarFault;
                }
            }
            return null;

        }

        public Image getImage(Object obj)
        {
            return PlatformUI.getWorkbench().getSharedImages().getImage(
                    ISharedImages.IMG_OBJ_ELEMENT);
        }
    }

    public void createPartControl(Composite parent)
    {
        // Custom Action for the View's Menu
        AddNewLexicalTermActionDelegate addLexicalTerm = new AddNewLexicalTermActionDelegate();
        addLexicalTerm.setText("Add New Term");
        addLexicalTerm.setImageDescriptor(Activator.getImageDescriptor("icons/addNewLexicalTerm.bmp"));
        getViewSite().getActionBars().getMenuManager().add(addLexicalTerm);
        
        
        EditRemoveTermsActionDelegate editRemoveAction = new EditRemoveTermsActionDelegate();
        editRemoveAction.setText("Edit/Remove Terms");
        editRemoveAction.setImageDescriptor(Activator.getImageDescriptor("icons/edit_term_icon_16_16.png"));
        getViewSite().getActionBars().getMenuManager().add(editRemoveAction);
        
        super.createPartControl(parent);
    }
    
    public TableViewer getViewer() {
        return viewer;
    }

    /**
     * Passing the focus request to the viewer's control.
     */
    public void setFocus()
    {
        viewer.getControl().setFocus();
    }

    
    protected Group createGroup()
    {
        Group result = new Group(this.parent, SWT.FILL);

        /*
         * 4 columns and set that the columns must not have equal width
         */
        GridLayout gridLayout = new GridLayout(4, false);
        gridLayout.marginWidth = 0;
        gridLayout.marginHeight = 0;
        gridLayout.verticalSpacing = 0;
        gridLayout.horizontalSpacing = 0;

        result.setLayout(gridLayout); // sets the layout of the group

        return result;
    }

    
    protected TableViewer createTable()
    {
        this.tableSorter = new CNLTableSorter();

        this.filter = new CNLViewerFilter();

        TableViewer result = new TableViewer(new Table(this.group, SWT.BORDER | SWT.MULTI
                | SWT.V_SCROLL | SWT.FULL_SELECTION));

        Table table = result.getTable();
        table.setBounds(0, 0, 100, 100);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        /*
         * The layout of the data. It is setted to fill the horizontal alignment, fill the vertical
         * alignment. The Grid will also occupy all view space.
         */
        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
        table.setLayoutData(gridData);

        createColumns(table); // creates the table columns

        /* sets a viewer content provider */
        result.setContentProvider(new ArrayContentProvider());
        
        /* sets a viewer label provider */
        result.setLabelProvider(new ViewLabelProvider());

        result.setSorter(tableSorter);

        result.addFilter(filter);

        return result;
    }

    
    protected void performFinalOperations()
    {
        try
        {
            CNLPluginController.getInstance().startController();
        }
        catch (CNLException e)
        {
            //let the CNL view be started with an empty error list
        }        
    }

    /**
     * Create the table columns and set their names.
     * 
     * @param table The table where the columns will be created.
     */
    private void createColumns(final Table table)
    {
        final TableColumn[] columns = new TableColumn[4];
        columns[0] = new TableColumn(table, SWT.NONE);
        columns[1] = new TableColumn(table, SWT.NONE);
        columns[2] = new TableColumn(table, SWT.NONE);
        columns[3] = new TableColumn(table, SWT.NONE);

        columns[0].setText("Description");
        columns[1].setText("Sentence");
        columns[2].setText("Step");
        columns[3].setText("Resource");

        for (int i = 0; i < columns.length; i++)
        {
            final int j = i;
            columns[i].addSelectionListener(new SelectionAdapter()
            {
                
                public void widgetSelected(SelectionEvent e)
                {
                    tableSorter.setColumn(j);
                    int dir = viewer.getTable().getSortDirection();
                    if (viewer.getTable().getSortColumn() == columns[j])
                    {
                        dir = dir == SWT.UP ? SWT.DOWN : SWT.UP;
                    }
                    else
                    {

                        dir = SWT.DOWN;
                    }
                    viewer.getTable().setSortDirection(dir);
                    viewer.getTable().setSortColumn(columns[j]);
                    viewer.refresh();
                }
            });
        }
    }

    /**
     * Updates the error counter
     */
    private void updateErrorCounter(List<CNLFault> faultList)
    {
        group.setText(this.calculateErrorCount(faultList) + " errors in "
                + CNLPluginController.getInstance().getSentenceCount() + " sentences.");

    }

    private int calculateErrorCount(List<CNLFault> faultList)
    {
        int result = 0;

        for (CNLFault cnlFault : faultList)
        {
            if (cnlFault.getField().equals(TestCaseTextType.ACTION)
                    && this.getFilter().isShowActions())
            {
                result++;
            }

            else if (cnlFault.getField().equals(TestCaseTextType.CONDITION)
                    && this.getFilter().isShowConditions())
            {
                result++;
            }

            else if (cnlFault.getField().equals(TestCaseTextType.RESPONSE)
                    && this.getFilter().isShowResponses())
            {
                result++;
            }
        }

        return result;
    }

    
    public void update()
    {
        //verifies if the CNL controller has been started properly in order to update the view.
        if(!CNLPluginController.getInstance().isErrorStartingController())
        {
            this.update(false);
        }        
    }

    public void update(boolean updateAnyWay)
    {
        try
        {
            if (ProjectManagerExternalFacade.getInstance().hasOpenedProject() || updateAnyWay)
            {
                if (this.currentProject == null || this.hasDocumentModification() || updateAnyWay)
                {
                    this.currentProject = ProjectManagerExternalFacade.getInstance()
                            .getCurrentProject();

                    this.faultList = CNLPluginController.getInstance().processUseCaseDocuments(
                            this.currentProject.getUseCaseDocuments());
                    this.viewer.setInput(faultList);
                    this.updateErrorCounter(faultList);
                }
            }

        }
        catch (IOException e)
        {
            MessageDialog.openError(this.getSite().getShell(), "Error while updating the CNL View",
                    e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Method used to verify if there is a modification in any document.
     * 
     * @return <code>True</code> if there is a modification in any document.
     * @throws IOException Thrown when it is not possible to read the project use cases directory.
     */
    public boolean hasDocumentModification() throws IOException
    {
        boolean result = false;

        Collection<File> docFiles = new ArrayList<File>();

        Collection<InputDocumentData> inputExtensionsList = InputDocumentExtensionFactory
                .inputExtensions();

        for (InputDocumentData inputDocumentData : inputExtensionsList)
        {
            InputDocumentExtension inputDocumentExtension = inputDocumentData
                    .getInputDocumentExtension();

            FileFilter fileFilter = inputDocumentExtension.getFileFilter();

            Collection<File> files = FileUtil.loadFilesFromDirectory(this.currentProject
                    .getUseCaseDir(), fileFilter);
            docFiles.addAll(files);
        }

        for (File file : docFiles)
        {
            UseCaseDocument pDoc = this.currentProject.getUseCaseDocumentFromFilePath(file
                    .getAbsolutePath());
            // If there is not a document or the file is not updated.
            if (pDoc == null || !this.currentProject.isDocumentUpdated(file))
            {
                result = true;
                break;
            }
        }

        return result || docFiles.size() != this.currentProject.getUseCaseDocuments().size();
    }

    /**
     * @return the CNL plugin view.
     */
    public static CNLView getView()
    {
        IWorkbench workbench = PlatformUI.getWorkbench();
        IWorkbenchWindow window = workbench.getActiveWorkbenchWindow();
        IWorkbenchPage page = window.getActivePage();
        IViewReference reference = page.findViewReference(ID, null);

        return (CNLView) reference.getPart(true);
    }

    /**
     * Indicates to the table filter if the action faults will be showed.
     * 
     * @param bool
     */
    public void setShowActions(boolean bool)
    {
        this.filter.setShowActions(bool);
        this.updateErrorCounter(this.faultList);
        viewer.refresh();

    }

    /**
     * Indicates to the table filter if the condition faults will be showed.
     * 
     * @param bool
     */
    public void setShowConditions(boolean bool)
    {
        this.filter.setShowConditions(bool);
        this.updateErrorCounter(this.faultList);
        viewer.refresh();
    }

    /**
     * Indicates to the table filter if the response faults will be showed.
     * 
     * @param bool
     */
    public void setShowResponses(boolean bool)
    {
        this.filter.setShowResponses(bool);
        this.updateErrorCounter(this.faultList);
        viewer.refresh();
    }

    /**
     * Gets the filter value.
     * 
     * @return Returns the filter.
     */
    public CNLViewerFilter getFilter()
    {
        return filter;
    }

    /**
     * Sets the filter value.
     * 
     * @param filter The filter to set.
     */
    public void setFilter(CNLViewerFilter filter)
    {
        this.filter = filter;
    }

    /**
     * Gets the faultList value.
     * 
     * @return Returns the faultList.
     */
    public List<CNLFault> getFaultList()
    {
        return faultList;
    }

    /**
     * Sets the faultList value.
     * 
     * @param faultList The faultList to set.
     */
    public void setFaultList(List<CNLFault> faultList)
    {
        this.faultList = faultList;
    }

}
