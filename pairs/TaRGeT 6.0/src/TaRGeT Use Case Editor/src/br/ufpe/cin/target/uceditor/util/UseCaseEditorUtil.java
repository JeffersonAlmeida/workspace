/*
 * @(#)XMLEditorUtil.java
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
 * -------  ------------    ----------    ----------------------------
 */
package br.ufpe.cin.target.uceditor.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.FocusEvent;
import org.eclipse.swt.events.FocusListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IFormColors;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.forms.widgets.SharedScrolledComposite;

import br.ufpe.cin.target.common.ucdoc.Feature;
import br.ufpe.cin.target.common.ucdoc.Flow;
import br.ufpe.cin.target.common.ucdoc.FlowStep;
import br.ufpe.cin.target.common.ucdoc.StepId;
import br.ufpe.cin.target.common.ucdoc.UseCase;
import br.ufpe.cin.target.common.util.Properties;
import br.ufpe.cin.target.uceditor.editor.UseCaseEditor;
import br.ufpe.cin.target.uceditor.editor.actions.AddStepAboveAction;
import br.ufpe.cin.target.uceditor.editor.actions.AddStepBellowAction;
import br.ufpe.cin.target.uceditor.editor.actions.RemoveStepAction;
import br.ufpe.cin.target.uceditor.providers.TableContentProvider;
import br.ufpe.cin.target.uceditor.providers.TableEditingSupport;
import br.ufpe.cin.target.uceditor.providers.TableLabelProvider;
import br.ufpe.cin.target.uceditor.verify.UseCaseVerifier;


/**
 * 
 * <pre>
 * CLASS:
 * This class provides some usual methods for XML Editor classes. 
 * 
 * </pre>
 *
 */
public class UseCaseEditorUtil 
{

    /**
     * Constructs a new instance of GridData according to the parameters.
     * 
     * @param style The GridData style.
     * @param widthHint The preferred width in pixels.
     * @param heightHint The preferred height in pixels.
     * @return A GridData
     */
	public static GridData createGridData(int style, int widthHint, int heightHint)
    {
        GridData gd = new GridData(style);
        gd.widthHint = widthHint;
        gd.heightHint = heightHint;
        return gd;
    }

    /**
     * Creates a Section according to the parameters.
     * 
     * @param form The form widget managed by this form.
     * @param toolkit The toolkit used by this form.
     * @param name The section name.
     * @return The section widget.
     */
    public static Section createSection(ScrolledForm form, FormToolkit toolkit, String name)
    {
    	int style = Section.TITLE_BAR | Section.TWISTIE | Section.EXPANDED;

    	Section section = toolkit.createSection(form.getBody(), style);
    	section.setActiveToggleColor(toolkit.getHyperlinkGroup().getActiveForeground());
    	section.setToggleColor(toolkit.getColors().getColor(IFormColors.SEPARATOR));
    	section.setText(name);

    	GridData gd = new GridData(GridData.FILL, GridData.FILL, true, false);
    	gd.heightHint = 150;
    	gd.horizontalSpan = 2;
    	section.setLayoutData(gd);
	
	    return section;
	  }
	
	  /**
	     * Creates a composite into a section according to the parameters.
	     * 
	     * @param toolkit The toolkit used by this form.
	     * @param section The composite parent. 
	     * @param style The composite style.
	     * @param columns The number of columns.
	     * @return A composite 
	     */
	   public static Composite createComposite(FormToolkit toolkit, Section section, int style, int columns)
	   {
	        Composite client = toolkit.createComposite(section, style);
	        client.setLayout(createGridLayout(columns,false));
	        client.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));
			section.setClient(client);
			section.setExpanded(true);

	        return client;
	   }
   
	   /**
	     * Creates a composite into another composite according to the parameters.
	     * 
	     * @param toolkit The toolkit used by this form.
	     * @param comp The composite parent. 
	     * @param style The composite style.
	     * @param columns The number of columns.
	     * @return A composite 
	     */
	   public static Composite createComposite(FormToolkit toolkit, Composite comp, int style, int columns)
	   {
	        Composite client = toolkit.createComposite(comp, style);
	        client.setLayout(createGridLayout(columns, false));
	        client.setLayoutData(new GridData(GridData.FILL, GridData.FILL, true, false));

	        return client;
	   }

	   /**
	     * Creates a grid layout with default style and a specific number of columns.
	     * 
	     * @param columns The number of columns.
	     * @param columnsSameSize It says if the columns will have the same size.
	     * @return A grid layout with a specific number of columns.
	     */
	    public static GridLayout createGridLayout(int columns, boolean columnsSameSize )
	    {
	        GridLayout layout = new GridLayout(columns,columnsSameSize);
	        return layout;
	    }
	    	
	    /**
	     * Creates a Text component.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Text.
	     * @param style The composite style.
	     * @return A text component.
	     */
	    public static Text createText(FormToolkit toolkit, Composite parent, int style, FormEditor editor)
	    {
	    	Text text = toolkit.createText(parent, "", style);
			text.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                   true, false));
			textFocusListening(text, editor);
			
	    	return text;
	    }
	    
	    //INSPECT mcms 05/01 muda a cor do componente após search
		public static void changeColor(HashMap<String, Object> componentSet) {
			
			Iterator<Map.Entry<String, Object>> it = componentSet.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> item = it.next();
				final Object component = item.getValue();
	
				if (component != null) {
					// Change Text fields color from sections feature and Use Case
					if (component instanceof Text) {
						Text text = (Text) component;
						listeningToChangeTextColor(componentSet, text);
						
					}
					// Change main flow section components color
					if(item.getKey().equals("MainFlow")){
						changeFlowComponentsColor(componentSet, (Composite)component);
	                }	
					
					if(item.getKey().equals("AlternativeFlows")){
						List<Composite> list = (List<Composite>)component;
                    	Iterator<Composite> itFlows = list.iterator();
                    	while(itFlows.hasNext()){
                    		Composite flow = itFlows.next();
                    		changeFlowComponentsColor(componentSet, flow);
                    	}
						
	                }
				}
			}
		}
		
		// INSPECT mcms 06/01 muda a cor do component no fluxo após search
		public static void changeFlowComponentsColor(HashMap<String, Object> componentSet, Composite component){
			 Object[] children = ((Composite) component).getChildren();
	           Object[] grandChildren;
	           for (int x = 0; x < children.length; x++)
	           {
	               if (children[x] instanceof Composite)
	               {
	                   grandChildren = ((Composite) children[x]).getChildren();
	                   for (int y = 0; y < grandChildren.length; y++)
	                   {
	                       if (grandChildren[y] instanceof Text)
	                       {
	                    	   listeningToChangeTextColor(componentSet,((Text) grandChildren[y]));
	                       }
	                       if (grandChildren[y] instanceof Table)
	                       {
	                    	   listeningToChangeTableColor(componentSet,((Table) grandChildren[y]));
	                       }
	                   }
	               }
	           }
		}
		
		// INSPECT mcms 29/01 core vermelha adicionada ao listening
		// INSPECT mcms 06/01 muda cor do componente texto nos fluxos
		public static void changeFlowTextColor(Composite component) {
			Object[] children = ((Composite) component).getChildren();
			Object[] grandChildren;
			for (int x = 0; x < children.length; x++) {
				if (children[x] instanceof Composite) {
					grandChildren = ((Composite) children[x]).getChildren();
					for (int y = 0; y < grandChildren.length; y++) {
						if (grandChildren[y] instanceof Text) {
							Color color = Display.getCurrent().getSystemColor(
									SWT.COLOR_GREEN);
							Color red = Display.getCurrent().getSystemColor(
									SWT.COLOR_RED);
							
							Text text = (Text) grandChildren[y];
							if ((text.getBackground().equals(color))||(text.getBackground().equals(red))) {
								text.setBackground(Display.getCurrent()
										.getSystemColor(SWT.COLOR_WHITE));
							}
						}
					}
				}
			}
		}
		
		 // INSPECT mcms 29/01 core vermelha adicionada ao listening
		// INSPECT mcms 06/01 muda cor do componente de texto fora do fluxo
		public static void changeTextColor(HashMap<String, Object> componentSet) {
			Iterator<Map.Entry<String, Object>> it = componentSet.entrySet()
					.iterator();
			while (it.hasNext()) {
				Map.Entry<String, Object> item = it.next();
				final Object component = item.getValue();
	
				if (component != null) {
					// Change Text fields color from sections feature and Use Case
					if (component instanceof Text) {
						Color color = Display.getCurrent().getSystemColor(
								SWT.COLOR_GREEN);
						Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
						final Text text = (Text) component;
						if ((text.getBackground().equals(color))||(text.getBackground().equals(red))) {
							text.setBackground(Display.getCurrent()
									.getSystemColor(SWT.COLOR_WHITE));
						}
					}
				}
			}
		}

		// INSPECT mcms 29/01 core vermelha adicionada ao listening
		// INSPECT mcms 05/01 coloca listening para mudar a cor do campo de texto após search
		public static void listeningToChangeTextColor(final HashMap<String, Object> components, final Text text){
			text.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					Iterator<Map.Entry<String, Object>> it2 = components
							.entrySet().iterator();
					while (it2.hasNext()) {
						Map.Entry<String, Object> item = it2.next();
						Object component2 = item.getValue();

						if (component2!=null) {
							// Search to Text fields of interface
							if (component2 instanceof Text) {
								Text otherText = (Text) component2;
								Color color = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
								Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);

								if ((otherText.getBackground().equals(color))||(otherText.getBackground().equals(red))) {
									otherText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
								}
							}
							if (component2 instanceof Composite){
								changeFlowTextColor((Composite)component2);
								changeTableColor((Composite)component2);
							}
							if(component2 instanceof Table){
								Table table = (Table)component2;
								changeFlowTextColor((Composite)component2);
								changeTableColor((Composite)component2);
								changeTableItemColor(table);
							}
							if (component2 instanceof List){
								List<Composite> list = (List<Composite>)component2;
		                    	Iterator<Composite> itFlows = list.iterator();
		                    	while(itFlows.hasNext()){
		                    		Composite flow = itFlows.next();
		                    		changeFlowTextColor(flow);
									changeTableColor(flow);
		                    	}
							}
						}
					}
				}

				
				public void focusLost(FocusEvent arg0) {
					//Do nothing
				}
			});

		}
		
		// INSPECT mcms 06/01 tira o destaque da tabela
		public static void changeTableColor(Composite component){
			Object[] children = ((Composite) component).getChildren();
			Object[] grandChildren;
			for (int x = 0; x < children.length; x++) {
				if (children[x] instanceof Composite) {
					grandChildren = ((Composite) children[x]).getChildren();
					for (int y = 0; y < grandChildren.length; y++) {
						if (grandChildren[y] instanceof Table) {
							Table table = (Table) grandChildren[y];
							changeTableItemColor(table);
						}
					}
				}
			}
		}
		
		// INSPECT mcms 29/01 core vermelha adicionada ao listening
		// INSPECT mcms 07/01 muda a cor do item da tabela
		public static void  changeTableItemColor(Table table){
			int numRows = table.getItemCount();
	        int numColumns = table.getColumnCount();
	        Color color = Display.getCurrent().getSystemColor(SWT.COLOR_GREEN);
	        Color red = Display.getCurrent().getSystemColor(SWT.COLOR_RED);
	        for (int k = 0; k < numColumns; k++)
	        {
	            for (int i = 0; i < numRows; i++)
	            {
	            	if((table.getItem(i).getBackground(k).equals(color))||(table.getItem(i).getBackground(k).equals(red))){
	            		table.getItem(i).setBackground(k,
		                        Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
					
	            	}
	            }
	        }		
		}
		
		//INSPECT mcms 06/01 adiciona listening para mudar a cor da tabela após search
		public static void listeningToChangeTableColor(
				final HashMap<String, Object> components, final Table table) {
	
			table.addFocusListener(new FocusListener() {
				public void focusGained(FocusEvent e) {
					changeTextColor(components);
	
					Iterator<Map.Entry<String, Object>> it = components.entrySet()
							.iterator();
					while (it.hasNext()) {
						Map.Entry<String, Object> item = it.next();
						final Object component = item.getValue();
	
						if (component != null) {
							// Change Text fields color from sections
							// feature and Use Case
							if (component instanceof Composite) {
								changeFlowTextColor((Composite) component);
								changeTableColor((Composite) component);
							}
	
							if (component instanceof Table) {
								Table table = (Table) component;
								changeFlowTextColor((Composite) component);
								changeTableColor((Composite) component);
								changeTableItemColor(table);
							}
	
							if (component instanceof List) {
								List<Composite> list = (List<Composite>) component;
								Iterator<Composite> itFlows = list.iterator();
	
								while (itFlows.hasNext()) {
									Composite flow = itFlows.next();
									changeFlowTextColor(flow);
									changeTableColor(flow);
								}
							}
						}
					}
				}
	
				
				public void focusLost(FocusEvent arg0) {
					// Do nothing
				}
			});
	
		}
    
	    //INSPECT mcms 17/12 RETIRADO NA CORREÇÃO DO BUG DO SETDIRTY
	    /*public static void listeningKeyEvent(Text text, final FormEditor editor){   
	    	text.addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent e) {
					if(!((UseCaseEditor)editor).isDirty())
					{	
						((UseCaseEditor)editor).setDirty(true);	
					}
				}
				public void keyReleased(KeyEvent e) {
				}
			});
	    }
	    */
		
	    //INSPECT mcms 14/01 correção do bug do do setDirty 
	    public static void textFocusListening(Text text, final FormEditor editor){
	    	text.addFocusListener(new FocusListener() {
	    	      public void focusGained(FocusEvent e) {
	    	    	  if(!((UseCaseEditor)editor).isDirty())
						{	
							((UseCaseEditor)editor).setDirty(true);	
						}
	    	        }

				
				public void focusLost(FocusEvent arg0) {
					// TODO Auto-generated method stub
					
				}
	    	});
	    }
	    
	    /**
	     * Creates a Label component.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Label.
	     * @param text The text of the label.
	     * @return A label component.
	     */
	    public static Label createLabel(FormToolkit toolkit, Composite parent, String text, Display display)
	    {
	    	Label l = toolkit.createLabel(parent, text);
	    	l.setLayoutData(new GridData(GridData.BEGINNING, GridData.FILL,
                    false, false));
			return l;
	    }
	    
	    /**
	     * Creates a Button component.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Label.
	     * @param buttonName The name of the button.
	     * @param style The composite style.
	     * @return A button.
	     */
	    public static Button createButton(FormToolkit toolkit, Composite parent, String buttonName, int style)
	    {
	    	Button add = toolkit.createButton(parent, buttonName, style);
	    	return add;
	    }
	    
	    /**
	     * Creates a Table component that fills all layout width.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Label.
	     * @param style The composite style.
	     * @return A table.
	     */
	    public static Table createTable(FormToolkit toolkit, Composite parent, int style)
	    {
	    	Table t = toolkit.createTable(parent, style);
			t.setLinesVisible(true);
			t.setHeaderVisible(true);
			t.setLayoutData(new GridData(GridData.FILL, GridData.FILL,
                    true, false));
			
		
			return t;
	    }
	    
	    /**
	     * Creates an specific Table to Requirements section.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Label.
	     * @param style The composite style.
	     * @return A requirement table.
	     */
	    public static Table createRequirementTable(FormToolkit toolkit, Composite parent, int style)
	    {
	    	Table t = toolkit.createTable(parent, style);
			t.setLinesVisible(true);
			t.setHeaderVisible(true);
			return t;
	    }
	    //INSPECT mcms 11/12
	    public static TableViewer createTableViewer(Table table, String[] columnName, ScrolledForm sf, FormEditor editor, Flow flow){
	        //Create TableViewer
	    	TableViewer viewer = new TableViewer(table);
           
	        //viewer.setUseHashlookup(true);
	    	viewer.setColumnProperties(columnName);
	        createColumns(viewer, columnName, editor);
	        
			viewer.getTable().getColumn(0).setWidth(50);
	        
	        viewer.setContentProvider(new TableContentProvider());
	        viewer.setLabelProvider(new TableLabelProvider());	        	

	        if(flow == null){
	        	 //First line at table
		        List<FlowStep> content = new ArrayList<FlowStep>();
		        StepId id = new StepId("","","");
				Set<String> relatedRequirements = new HashSet<String>();
				FlowStep step = new FlowStep(id, "", "", "", relatedRequirements);
		        content.add(step);
		        viewer.setInput(content);
	        }
	        else{
	        	viewer.setInput(flow.getSteps());
	        } 
	        
	        //Method to add Line to the table
	        addLine(viewer, sf);
	        
	        //Method to remove Line from the table
	        removeLine(viewer, sf);
	         
	        return viewer;
	    }
	    
	    
	    //INSPECT mcms 15/12
	    public static void addLine(final TableViewer tv, final ScrolledForm sf) {
	  
			tv.getControl().addKeyListener(new KeyListener() {
				public void keyPressed(KeyEvent event) {
					if (event.keyCode == SWT.CR) {
						addLineBellow(tv, sf);
					}
				}

				public void keyReleased(KeyEvent event) {
					// noop
				}
			});
	    }
	    
	    // INSPECT mcms 22/01 created to avoid code repetition
		public static void addLineBellow(TableViewer tv, ScrolledForm sf) {
			TableContentProvider tcp = (TableContentProvider) tv
					.getContentProvider();
			tcp.addElementAt(createEmptyStep(), tv.getTable().getSelectionIndex() + 1);
			tv.refresh();
			((SharedScrolledComposite) sf).reflow(true);
		}
		
		//INSPECT mcms 22/01 created to add a step above selected one
		public static void addLineAbove(TableViewer tv, ScrolledForm sf) {
			TableContentProvider tcp = (TableContentProvider) tv
					.getContentProvider();
			tcp.addElementAt(createEmptyStep(),tv.getTable().getSelectionIndex());
			tv.refresh();
			((SharedScrolledComposite) sf).reflow(true);
		}
		
		// INSPECT mcms 22/01 created to avoid code repetition
		public static FlowStep createEmptyStep(){
			StepId id = new StepId("", "", "");
			Set<String> relatedRequirements = new HashSet<String>();
			FlowStep step = new FlowStep(id, "", "", "", relatedRequirements);
			
			return step;
		}

	    //INSPECT mcms 15/12
	    public static void removeLine(final TableViewer tv, final ScrolledForm sf)
		{
			tv.getControl().addKeyListener(new KeyListener()
		    {	
		           public void keyPressed(KeyEvent event)
		           {   	   
		        	   if (event.keyCode == SWT.DEL)
		               { 		   
		        		   if (tv.getTable().getItemCount()>=1)
		        		   {
		        			   removeStep(tv, sf);
		        		   }
		               }	
		           }
		           
		           public void keyReleased(KeyEvent event)
		           {
		               //noop
		           }
		       });
	}
	    
	//INSPECT mcms 22/01 created to avoid code repetition
    public static void removeStep(TableViewer tv, ScrolledForm sf){
    	TableContentProvider tcp = (TableContentProvider) tv
		.getContentProvider();
    	tcp.removeElementAt(tv.getTable().getSelectionIndex());
    	tv.refresh();
    	((SharedScrolledComposite) sf).reflow(true);
    }
		

	//INSPECT mcms method add to support the change from Table to TableViewer
	// 14/12
	public static void createColumns(final TableViewer viewer,
			String[] columnName, FormEditor editor) {

		for (int i = 0; i < columnName.length; i++) {
			final TableViewerColumn column = new TableViewerColumn(viewer,
					SWT.CENTER);			    
			
			column.getColumn().setAlignment(SWT.CENTER);
			column.getColumn().setText(columnName[i]);
			column.getColumn().setResizable(true);
			column.getColumn().setMoveable(true);
			column.getColumn().setWidth(280);
			if(columnName[i].endsWith("*")){
				column.getColumn().setToolTipText(Properties.getProperty("mandatory"));
			}
			
			// enable editing support
			column.setEditingSupport(new TableEditingSupport(viewer, i, editor));
		}

		viewer.getTable().setLayout(createGridLayout(columnName.length, true));
	}

	    /**
	     * Creates an specific Table used at Main Flow and Alternative Flows sections.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Label.
	     * @param scroll The ScrolledForm is necessary into editTable to allow refreshing table after 
	     * modifications.
	     * @return A requirement table.
	     */
		//INSPECT mcms 04/01 adição de asterisco
	    public static TableViewer createFlowTable(Composite parent, ScrolledForm scroll,
				FormToolkit toolkit, FormEditor editor, Flow flow) 
	    {	
			Table t = createTable(toolkit, parent, SWT.BORDER
					| SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);

			String[] columnName = { Properties.getProperty("step_id"), Properties.getProperty("user_action"), 
					Properties.getProperty("system_state"),	Properties.getProperty("system_response") };
			
			TableViewer viewer = UseCaseEditorUtil.createTableViewer(t, columnName, scroll, editor, flow);
			
			//Create context menu to steps at any flow
			createContextMenu(viewer, scroll, editor);
		
			return viewer;
		}
	    
	    //INSPECT mcms 22/01
	    public static void createContextMenu(TableViewer viewer,ScrolledForm scroll,FormEditor editor){
	   	 // Creating context menu for the flow
	        MenuManager menuManager = new MenuManager("br.ufpe.cin.target.uceditor.util.contextmenu.Flows");
	        ISelectionProvider selectionProvider = viewer;
	        
	        AddStepBellowAction addSB = new AddStepBellowAction(viewer,scroll,"br.ufpe.cin.target.uceditor.editor.actions.Flows.AddStepBellow",Properties.getProperty("insert_bellow"), null, selectionProvider);
	        addSB.setEnabled(true);
	        
	        AddStepAboveAction addSA = new AddStepAboveAction(viewer,scroll,"br.ufpe.cin.target.uceditor.editor.actions.Flows.AddStepAbove",Properties.getProperty("insert_above"), null, selectionProvider);
	        addSA.setEnabled(true);
	        
	        RemoveStepAction remove = new RemoveStepAction(viewer,scroll,"br.ufpe.cin.target.uceditor.editor.actions.Flows.RemoveStep",Properties.getProperty("remove"), null, selectionProvider);
	        remove.setEnabled(true);
	        
	        menuManager.add(addSB);
	        menuManager.add(addSA);
	        menuManager.add(remove);

	        Menu contextMenu = menuManager.createContextMenu(viewer.getTable());
	        viewer.getTable().setMenu(contextMenu);
	        editor.getSite().registerContextMenu("br.ufpe.cin.target.uceditor.util.contextmenu.Flows",menuManager, selectionProvider);

	    }
	    
	    /**
	     * Creates the empty fields Description, From Steps and To Steps to the Main Flow 
	     * section and add them to the List components.
	     * 
	     * @param toolkit The FormTollkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param parent The composite that will host the Label.
	     */
	  //INSPECT mcms 04/01 adição de asterisco
	 // INSPECT mcms 11/01 adicionar paramêtro feature ao método
	    public static void descriptionSteps(Composite parent, FormToolkit toolkit, FormEditor editor, UseCase uc, Flow flow, Feature feature, Display display) 
	    {
			// Description
			createLabel(toolkit, parent, Properties.getProperty("description"), display);
			Text desc = createText(toolkit, parent, SWT.LEFT, editor);

			// From Steps
			Label l = createLabel(toolkit, parent, Properties.getProperty("from_steps"), display);
			l.setToolTipText(Properties.getProperty("mandatory"));
			Text from = createText(toolkit, parent, SWT.LEFT, editor);

			// To Steps
			Label label = createLabel(toolkit, parent, Properties.getProperty("to_steps"), display);
			label.setToolTipText(Properties.getProperty("mandatory"));
			Text to = createText(toolkit, parent, SWT.LEFT, editor);
			
			if(flow!=null){
				desc.setText(flow.getDescription());
				from.setText(generateFromToStepText(flow.getFromSteps(), uc, feature));
				to.setText(generateFromToStepText(flow.getToSteps(), uc, feature));
//				from.setText(flow.g)
			}

		}
	    
	    public static String generateFromToStepText(Set<StepId> stepIds, UseCase uc, Feature feature){
	    	String result = "";
	    	//there is only one from or to step
	        if (stepIds.size() == 1)
	        {
	            StepId stepId = stepIds.iterator().next();
	            
	            if (stepId.getFeatureId().equals(feature.getId()))
	            {
	                if (stepId.getUseCaseId().equals(uc.getId()))
	                {
	                    result = stepId.getStepId();
	                }
	                else
	                {
	                    result = stepId.getUseCaseId() + "#" + stepId.getStepId();
	                }
	            }
	            else
	            {
	                result = stepId.toString();
	            }
	        }
	        
	        //separates from/to steps with comma
	        else
	        {
	            for (StepId stepId : stepIds)
	            {
	                if (stepId.getFeatureId().equals(feature.getId()))
	                {
	                    if (stepId.getUseCaseId().equals(uc.getId()))
	                    {
	                        result = result + stepId.getStepId() + ", ";
	                    }
	                    else
	                    {
	                        result = result + stepId.getUseCaseId() + "#" + stepId.getStepId()
	                                + ", ";
	                    }
	                }
	                else
	                {
	                    result = result + stepId.toString() + ", ";
	                }
	            }            
	            result = result.substring(0, result.length() - 2);
	        }        
	   	return result;
	    }
	    
	    
	    
	    /**
	     * Creates TableItem for a Table.
	     * 
	     * @param table The table for items will be created.
	     * @param style The style of the table item.
	     * @param columnName Names of each columns.
	     * @param width Width of the columns.
	     */
	    public static void createTableItem(Table table, int style, String[] columnName, int width)
	    {
	    	for(int i=0; i<columnName.length; i++)
	    	{
	    		TableColumn column = new TableColumn(table, style);
				column.pack();
				column.setText(columnName[i]);
				column.setWidth(width);
	    	}
	    	TableItem item = new TableItem(table, style);    	
	    }
	    		
		/**
         * Adds a new flow to section Alternative Flows and inserts it to components list.
         * 
         * @param parent The composite that will host the new flow.
         * @param scroll The ScrolledForm to refresh the section.
         * @param toolkit The FormToolkit responsible for creating SWT controls adapted to work in
         * Eclipse forms.
         * @param components The list of components from user interface
         */
	    // INSPECT mcms 11/01 adicionar paramêtro feature ao método
        public static void addMainFlow(Composite parent, ScrolledForm scroll,
                FormToolkit toolkit, HashMap<String, Object> components, UseCase uc, FormEditor editor, Flow flow, Feature feature) 
        {   
            //Create composites to store text fields and table
            Composite parentAll = UseCaseEditorUtil.createComposite(toolkit,parent, SWT.NONE, 1);
            // Create composite to store text fields
            Composite parentSteps = UseCaseEditorUtil.createComposite(toolkit,parentAll, SWT.NONE, 2);
            //Create composite to store table
            Composite parentTable = UseCaseEditorUtil.createComposite(toolkit,parentAll, SWT.NONE, 1);
            
            //Create description, steps from and to
            UseCaseEditorUtil.descriptionSteps(parentSteps, toolkit, editor, uc, flow, feature, scroll.getDisplay());
            //Create flow table
            UseCaseEditorUtil.createFlowTable(parentTable, scroll, toolkit, editor, flow);
                        
            components.put("MainFlow", parentAll);
       
        }
		
		/**
	     * Adds a new flow to section Alternative Flows and inserts it to components list.
	     * 
	     * @param parent The composite that will host the new flow.
	     * @param scroll The ScrolledForm to refresh the section.
	     * @param toolkit The FormToolkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param components The list of components from user interface
	     */
        // INSPECT mcms 11/01 adicionar paramêtro feature ao método
        public static void addFlow(Composite parent, ScrolledForm scroll,
				FormToolkit toolkit, HashMap<String, Object> components, UseCase uc, List<Composite> alternativeFlows, FormEditor editor, Flow flow, Feature feature) 
		{	
		  //Create composites to store text fields and table
            Composite parentAll = UseCaseEditorUtil.createComposite(toolkit,parent, SWT.NONE, 1);
            // Create composite to store text fields
            Composite parentSteps = UseCaseEditorUtil.createComposite(toolkit,parentAll, SWT.NONE, 2);
            //Create composite to store table
            Composite parentTable = UseCaseEditorUtil.createComposite(toolkit,parentAll, SWT.NONE, 1);
            
            //Create description, steps from and to
            UseCaseEditorUtil.descriptionSteps(parentSteps, toolkit, editor, uc, flow, feature, scroll.getDisplay());
            //Create flow table
            UseCaseEditorUtil.createFlowTable(parentTable, scroll, toolkit, editor, flow);
            
            if(alternativeFlows.isEmpty())
            {
            	 alternativeFlows.add(parentAll);
            	 components.put("AlternativeFlows", alternativeFlows);
            }
            else {
				List<Composite> list = (List<Composite>)components.get("AlternativeFlows");
				list.add(parentAll);
		}
            removeFlow(parentAll, parentTable, scroll, toolkit, components, uc, alternativeFlows);
			((SharedScrolledComposite)scroll).reflow(true);
		}
		
		/**
	     * Removes a flow from section Alternative Flows and from components list.
	     * 
	     * @param parentAll The composite that hosts the new flow.
	     * @param parentTable The composite that hosts the table and will host the button remove.
	     * @param scroll The ScrolledForm to refresh the section.
	     * @param toolkit The FormToolkit responsible for creating SWT controls adapted to work in
	     * Eclipse forms.
	     * @param components The list of components from user interface.
	     */
		private static void removeFlow(final Composite parentAll, final Composite parentTable, final ScrolledForm scroll,
				 FormToolkit toolkit, final HashMap<String, Object> components, final UseCase uc, final List<Composite> alternativeFlows) 
		{	
			final Button remove = UseCaseEditorUtil.createButton(toolkit, parentTable,
					Properties.getProperty("button_remove"), SWT.PUSH);

			remove.addSelectionListener(new SelectionAdapter() 
			{
				
			public void widgetSelected(SelectionEvent event) {
				Button buttonClicked = (Button) event.getSource();
				Iterator<Composite> it = alternativeFlows.iterator();
				boolean hasFound = false;
				while ((it.hasNext()) && (!hasFound)) {
					Object component = it.next();
		           if (component instanceof Composite) {
						if (component.equals(parentAll)) {
							UseCaseVerifier v = new UseCaseVerifier();
							if (v.verifyStepsFromInterface(component,
									alternativeFlows)) {
								boolean answer = MessageDialog
										.openQuestion(
												scroll.getShell(),
												Properties.getProperty("warning"),Properties.getProperty("warning_remove_referred_flow"));
								if (answer) {
									alternativeFlows.remove(component);
									((Composite) component).dispose();			
								}
								else
								{
									v.paintComponentWhiteColor(alternativeFlows);	
								}
							}
							else
							{
								alternativeFlows.remove(component);
								((Composite) component).dispose();			
							}
							hasFound = true;
						}
					}
				}
		        ((SharedScrolledComposite)scroll).reflow(true);
				}
			});	
		}		
}
