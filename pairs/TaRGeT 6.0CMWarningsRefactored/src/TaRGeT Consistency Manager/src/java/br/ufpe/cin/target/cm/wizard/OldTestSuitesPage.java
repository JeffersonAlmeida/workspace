/*
 * @(#)ListTestSuitesPage.java
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
 * dhq348   Sep 17, 2007    LIBnn34865   Initial creation.
 * tnd783   Jul 07, 2008    LIBhh00000   Table Data type changed to File
 * fsf2		Jun 20, 2009				 Integration.
 */

package br.ufpe.cin.target.cm.wizard;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import br.ufpe.cin.target.cm.CMActivator;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentData;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtension;
import br.ufpe.cin.target.tcg.extensions.output.OutputDocumentExtensionFactory;


/**
 * Represents the page that displays and lets the user select some test suites.
 */
public class OldTestSuitesPage extends WizardPage
{
    /**
     * The viewer that contains all the test suite files in the project.
     */
    private TableViewer viewer;

    /**
     * The directory where the test suites are located.
     */
    private File oldTestSuiteDir;

    /**
     * The Output Document Extension.
     */
    private OutputDocumentExtension outputDocumentExtension;

    /**
     * Sets the page title and description.
     */
    protected OldTestSuitesPage(File oldTestSuiteDir)
    {
        super("");
        this.setTitle(Properties.getProperty("availables_test_suites"));
        this.setDescription(Properties.getProperty("select_a_test_suite_to_compare"));
        this.oldTestSuiteDir = oldTestSuiteDir;
        this.setPageComplete(false); // disables the finish button
    }

    /**
     * Creates page's visual controls.
     * 
     * @param parent The parent component of the current page.
     */
    public void createControl(Composite parent)
    {
        Group group = new Group(parent, SWT.NULL);
        group.setLayout(new GridLayout(1, false));
        this.viewer = this.createTable(group);
        
        List<File> testSuites = this.getContent();
        this.viewer.setInput(testSuites);
        
        if(testSuites.isEmpty()){
        	Label noTestSuiteLabel = new Label(group, SWT.NULL);
        	noTestSuiteLabel.setText(Properties.getProperty("no_test_suite_available"));
        }
        
        this.setControl(group);
    }

    /**
     * Returns a list of Files representing the available test suites in the current project.
     * 
     * @return The available test suites.
     */
    private List<File> getContent()
    {
        ArrayList<File> list = new ArrayList<File>();

        if (this.outputDocumentExtension == null)
        {
            Collection<OutputDocumentData> outputExtensionsList = OutputDocumentExtensionFactory
                    .outputExtensions();

            for (OutputDocumentData outputDocumentData : outputExtensionsList)
            {
                this.outputDocumentExtension = outputDocumentData.getOutputDocumentExtension();
            }
        }

        for (File testSuite : oldTestSuiteDir.listFiles())
        {
            if (this.outputDocumentExtension != null
                    && testSuite.getAbsolutePath().endsWith(
                            this.outputDocumentExtension.getExtensionFile()))
            {
                list.add(testSuite);
            }

        }

        return list;
    }

    /**
     * Creates a table viewer in the given group.
     * 
     * @param group The SWT group in which the table viewer will be created.
     * @return The table viewer created
     */
    protected TableViewer createTable(Group group)
    {

        Table table = new Table(group, SWT.BORDER | SWT.SINGLE);
        table.setBounds(0, 0, 400, 100);
        table.setHeaderVisible(true);
        table.setLinesVisible(true);

        this.addSelectionListener(table);

        GridData gridData = new GridData(GridData.FILL, GridData.FILL, true, true);
        table.setLayoutData(gridData);
        TableColumn colum = new TableColumn(table, SWT.NONE);
        colum.setText(Properties.getProperty("test_suite"));
        colum.setWidth(400);

        TableViewer result = new TableViewer(table);

        result.setContentProvider(new ArrayContentProvider());
        result.setLabelProvider(new TestSuitesLabelProvider());

        return result;
    }

    /**
     * Adds a selection listener to the table.
     * 
     * @param table The SWT table to which the selection listener will be added.
     */
    private void addSelectionListener(final Table table)
    {
        table.addSelectionListener(new SelectionListener()
        {
            public void widgetDefaultSelected(SelectionEvent e)
            {
            }

            public void widgetSelected(SelectionEvent e)
            {
                setPageComplete(true);
            }
        });
    }

    /**
     * Class representing the label provider for the test suite table viewer.
     */
    private class TestSuitesLabelProvider extends LabelProvider implements ITableLabelProvider
    {
        /*
         * (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnImage(java.lang.Object, int)
         */
        public Image getColumnImage(Object element, int columnIndex)
        {
            return CMActivator.imageDescriptorFromPlugin(CMActivator.PLUGIN_ID,
                    "icons/excel_icon_16_16.bmp").createImage();
        }

        /*
         * (non-Javadoc)
         * @see org.eclipse.jface.viewers.ITableLabelProvider#getColumnText(java.lang.Object, int)
         */
        public String getColumnText(Object element, int columnIndex)
        {
            return ((File) element).getName();
        }

        /*
         * (non-Javadoc)
         * @see org.eclipse.jface.viewers.LabelProvider#getText(java.lang.Object)
         */
        
        public String getText(Object element)
        {
            return "";
        }
    }

    /**
     * Gets the test suite selected by the user.
     * 
     * @return The test suite selected by the user.
     */
    public File getSelectedTestSuiteFile()
    {
        File file = null;
        TableItem[] selectedItems = this.viewer.getTable().getSelection();
        file = (File) selectedItems[0].getData();
        return file;
    }
}
