/*
 * @(#)TestCaseTable.java
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
 * wdt022    Apr 2, 2008    LIBhh00000   Initial creation.
 * wdt022    Jul 17, 2008   LIBpp56482   Changes due to rework of inspection LX263835.
 * fsf2      Jun 26, 2009                Changed colors of the cells.
 */
package br.ufpe.cin.target.tcg.util;

import java.text.DecimalFormat;

import org.eclipse.nebula.widgets.grid.Grid;
import org.eclipse.nebula.widgets.grid.GridColumn;
import org.eclipse.nebula.widgets.grid.GridItem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.graphics.TextLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;

import br.ufpe.cin.target.tcg.extractor.TextualStep;
import br.ufpe.cin.target.tcg.extractor.TextualTestCase;

/**
 * 
 * <pre>
 * CLASS:
 * This class provides a grid to exhibits a test case.
 * 
 * RESPONSIBILITIES:
 * Exhibits a specific test case, simulating the Test Central format.
 * 
 * </pre>
 *
 */

///INSPECT Lais chaged setTestCases method.
public class TestCaseGrid
{
   
    /**
     * The grid that exhibits the test case.
     */
    private Grid testCaseGrid;
    
    public static final RGB TEST_CASE_DIFERENCE_COLOR = new RGB(174,213,255);
    
    public static final RGB TEST_CASE_EXTRA_STEP_COLOR = new RGB(248,255,155);

    /**
     * Creates grid component with the following style: SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL .
     * 
     * @param parent The Grid parent.
     */
    public TestCaseGrid(Composite parent)
    {
        this(parent, SWT.BORDER | SWT.V_SCROLL | SWT.H_SCROLL);
    }

    /**
     * 
     * Creates a grid component with the specified style.
     * 
     * @param parent The Grid parent.
     * @param style The Grid style
     */
    public TestCaseGrid(Composite parent, int style)
    {
        this.testCaseGrid = new Grid(parent, style);
        this.testCaseGrid.setHeaderVisible(true);
        this.testCaseGrid.setLinesVisible(true);
        GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
        this.testCaseGrid.setLayoutData(gridData);

        this.createColumns(this.testCaseGrid);
    }

    /**
     * Creates the table columns and sets their names.
     * 
     * @param table The table where the columns will be created.
     */
    private void createColumns(Grid grid)
    {
        GridColumn[] columns = new GridColumn[6];
        columns[0] = new GridColumn(grid, SWT.WRAP);
        columns[1] = new GridColumn(grid, SWT.WRAP);
        columns[2] = new GridColumn(grid, SWT.WRAP);
        columns[3] = new GridColumn(grid, SWT.WRAP);
        columns[4] = new GridColumn(grid, SWT.WRAP);
        columns[5] = new GridColumn(grid, SWT.WRAP);

        columns[0].setText("Case");
        columns[1].setText("Reg. Level");
        columns[2].setText("Exe. Type");
        columns[3].setText("Case Description");
        columns[4].setText("Procedure");
        columns[5].setText("Expected Results");

        columns[0].setWidth(150);
        columns[1].setWidth(70);
        columns[2].setWidth(70);
        columns[3].setWidth(170);
        columns[4].setWidth(250);
        columns[5].setWidth(250);

        for (GridColumn column : columns)
        {
            column.setWordWrap(true);
            column.setResizeable(false);
        }
    }

    /**
     * Sets the test case which will be exhibited on the grid.
     * @param testCase The test case that will be exhibited.
     * @param testCaseToCampare the test case to compare
     * @param useGenericTestCaseId indicates if the test case id should be generic
     */
    //INSPECT Lais - will only paint the fields that will be kept from the new test case in the Consistency management feature
    //Steps, Requirements,  Initial Conditions, use case references, setup, feature id
    
    //receives the stepsPairs matrix calculated by the step similarity algorithm
    public void setTestCase(TextualTestCase testCase, TextualTestCase testCaseToCampare, boolean useGenericTestCaseId, int[][] stepPairs)
    {
        this.testCaseGrid.removeAll();
        
        if (testCase != null)
        {
            GridItem item = new GridItem(this.testCaseGrid, SWT.NONE);
            this.setFont(item);

            String tcIdHeader = testCase.getTcIdHeader().trim();
            if(useGenericTestCaseId){
            	DecimalFormat df = new DecimalFormat("0000");
            	tcIdHeader = "Test Case "+ df.format(testCase.getId());
            }
            	
            item.setText(0, tcIdHeader);
            
            item.setText(1, testCase.getRegressionLevel() + "");
                        
            item.setText(2, testCase.getExecutionType());

            item.setText(3, testCase.getDescription());
            
            item.setText(4, testCase.getObjective());
            
            item.setHeight(this.computeRowHeight(item));

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Use Cases:");
            item.setText(4, testCase.getUseCaseReferences());
            item.setHeight(this.computeRowHeight(item));
            if(testCaseToCampare != null && !testCase.getUseCaseReferences().trim().equals(testCaseToCampare.getUseCaseReferences().trim()))
            {
                item.setBackground(4, new Color(item.getDisplay(), TEST_CASE_DIFERENCE_COLOR));
            }

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Requirements:");
            item.setText(4, testCase.getRequirements());
            item.setHeight(this.computeRowHeight(item));
            if(testCaseToCampare != null && !testCase.getRequirements().trim().equals(testCaseToCampare.getRequirements().trim()))
            {
                item.setBackground(4, new Color(item.getDisplay(), TEST_CASE_DIFERENCE_COLOR));
            }

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Setup:");
            item.setText(4, testCase.getSetups());
            item.setHeight(this.computeRowHeight(item));
            if(testCaseToCampare != null && !testCase.getSetups().trim().equals(testCaseToCampare.getSetups().trim()))
            {
                item.setBackground(4, new Color(item.getDisplay(), TEST_CASE_DIFERENCE_COLOR));
            }

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Initial Conditions:");
            item.setText(4, testCase.getInitialConditions());
            item.setHeight(this.computeRowHeight(item));
            if(testCaseToCampare != null && !testCase.getInitialConditions().trim().equals(testCaseToCampare.getInitialConditions().trim()))
            {
                item.setBackground(4, new Color(item.getDisplay(), TEST_CASE_DIFERENCE_COLOR));
            }

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Notes:");
            item.setText(4, testCase.getNote());
            item.setHeight(this.computeRowHeight(item));
            

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Test Procedure (Step Number):");
            item.setHeight(this.computeRowHeight(item));
            
            int i = 0;
            for (TextualStep step : testCase.getSteps())
            {
                item = this.createNewItem(this.testCaseGrid);
                item.setText(3, (i+1) + "");
                item.setText(4, step.getAction());
                item.setText(5, step.getResponse());
                item.setHeight(this.computeRowHeight(item));
                
                if(testCaseToCampare != null)
                {
                    //step is an extra step
                    if(this.getStepPair(i, stepPairs) >= testCaseToCampare.getSteps().size()){
                        item.setBackground(4, new Color(item.getDisplay(), TEST_CASE_EXTRA_STEP_COLOR));
                        item.setBackground(5, new Color(item.getDisplay(), TEST_CASE_EXTRA_STEP_COLOR));
                    }
                    
                    else
                    {
                        
                        TextualStep stepToCompare = testCaseToCampare.getSteps().get(this.getStepPair(i, stepPairs));
                        
                        if (!step.getAction().trim().equals(stepToCompare.getAction().trim()))
                        {
                            item.setBackground(4, new Color(item.getDisplay(),
                                    TEST_CASE_DIFERENCE_COLOR));
                        }
                        
                        else{
                            //step actions are equals but have different id´s 
                            if(i != this.getStepPair(i, stepPairs))
                            {
                                item.setBackground(4, new Color(item.getDisplay(),
                                        TEST_CASE_DIFERENCE_COLOR));
                            }
                        }

                        if (!step.getResponse().trim().equals(stepToCompare.getResponse().trim()))
                        {
                            item.setBackground(5, new Color(item.getDisplay(),
                                    TEST_CASE_DIFERENCE_COLOR));
                        }
                        //step responses are equals but have different id´s
                        else{
                            if(i != this.getStepPair(i, stepPairs))
                            {
                                item.setBackground(5, new Color(item.getDisplay(),
                                        TEST_CASE_DIFERENCE_COLOR));
                            }
                        }
                    }
                    
                }
                
                i++;
            }

            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Final Conditions:");
            item.setText(4, testCase.getFinalConditions());
            item.setHeight(this.computeRowHeight(item));
            
            item = this.createNewItem(this.testCaseGrid);
            item.setText(3, "Cleanup:");
            item.setText(4, testCase.getCleanup());
            item.setHeight(this.computeRowHeight(item));
                        
            TCGUtil.reflow(this.testCaseGrid);
        }        
        
    }
    
    private int getStepPair(int stepIndex, int[][] stepPairs){
        for (int i = 0; i < stepPairs.length; i++)
        {
            if(stepPairs[i][0] == stepIndex){
                return stepPairs[i][1];
            }
        }
        return -1;
    }
    
    /**
     * Computes the row height for a specific grid item.
     * @param item The grid item.
     * @return An int which represents the value of the row height.
     */
    private int computeRowHeight(GridItem item)
    {
        int height = item.getHeight();
        
        for(int i = 0; i < this.testCaseGrid.getColumnCount(); i++)
        {
            height = Math.max(height, this.computeCellHeight(item, i));
        }
        
        return height;
    }

    /**
     * Computes the cell height for a specific grid item.
     * @param item The grid item.
     * @param column The column index of the cell to be resized.
     * @return An int which represents the value of the cell height.
     */
    private int computeCellHeight(GridItem item, int column)
    {
        int height = 3;

        TextLayout currTextLayout = new TextLayout(this.testCaseGrid.getDisplay());
        currTextLayout.setFont(this.testCaseGrid.getDisplay().getSystemFont());
        currTextLayout.setText(item.getText(column));
        currTextLayout.setAlignment(SWT.LEFT);
        currTextLayout.setWidth(Math.max(10, this.testCaseGrid.getColumn(column).getWidth() - 8));

        for (int cnt = 0; cnt < currTextLayout.getLineCount(); cnt++)
            height += currTextLayout.getLineBounds(cnt).height + 1;

        return height;
    }

    /**
     * Creates an instance of GridItem with SWT.WRAP style.
     * @param grid The GriItem parent.
     * @return A GridItem instance.
     */
    private GridItem createNewItem(Grid grid)
    {
        GridItem item = new GridItem(grid, SWT.WRAP);
        this.setFont(item);
        this.setGreyArea(item);

        return item;
    }

    /**
     * Sets the font of a grid item.
     * @param item The GridItem which the font will be set.
     */
    private void setFont(GridItem item)
    {
        item.setFont(new Font(item.getDisplay(), "Arial", 8, SWT.NORMAL));
    }

    /**
     * Sets the background to gray. 
     * @param item The GridItem which the background will be set
     */
    private void setGreyArea(GridItem item)
    {
        item.setBackground(0, item.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        item.setBackground(1, item.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
        item.setBackground(2, item.getDisplay().getSystemColor(SWT.COLOR_WIDGET_BACKGROUND));
    }

    /**
     * Gets the grid component used to exhibits the test case.
     * @return The grid component
     */
    public Grid getGrid()
    {
        return this.testCaseGrid;
    }
}
