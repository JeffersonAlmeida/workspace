package com.motorola.btc.research.target.cnl.views;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PlatformUI;

import com.motorola.btc.research.target.cnl.Activator;
import com.motorola.btc.research.target.cnl.controller.CNLFault;
import com.motorola.btc.research.target.cnl.controller.CNLLexionFault;
import com.motorola.btc.research.target.cnl.controller.CNLPluginController;
import com.motorola.btc.research.target.common.exceptions.TargetException;
import com.motorola.btc.research.target.common.ucdoc.PhoneDocument;
import com.motorola.btc.research.target.common.util.FileUtil;
import com.motorola.btc.research.target.pm.common.TargetTableView;
import com.motorola.btc.research.target.pm.controller.TargetProject;
import com.motorola.btc.research.target.pm.export.ProjectManagerExternalFacade;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentData;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtension;
import com.motorola.btc.research.target.pm.extensions.input.InputDocumentExtensionFactory;

/**
 * This class represents the CNL plugin view.
 * @author
 *
 */
public class CNLView extends TargetTableView
{
	public static final String ID = "com.motorola.btc.cnl.views.CNLView";

	private TargetProject currentProject;

	class ViewLabelProvider extends LabelProvider implements ITableLabelProvider
	{
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
			Image image = null;
			if (index == 0)
			{
				CNLFault fault = (CNLFault) obj;
				if (fault instanceof CNLLexionFault)
				{
					image = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							"icons/lexiconFaultIcon.bmp").createImage();
				}
				else
				{
					image = Activator.imageDescriptorFromPlugin(Activator.PLUGIN_ID,
							"icons/grammarFaultIcon.bmp").createImage();
				}

				image = new Image(image.getDevice(), image.getImageData().scaledTo(15, 15));
			}
			return image;
		}

		public Image getImage(Object obj)
		{
			return PlatformUI.getWorkbench().getSharedImages().getImage(
					ISharedImages.IMG_OBJ_ELEMENT);
		}
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
		return result;
	}


	protected void performFinalOperations()
	{
		this.updateErrorCounter();
		CNLPluginController cnlController = CNLPluginController.getInstance();
		try
		{
			cnlController.startController();
		}
		catch (Exception e)
		{
			throw new RuntimeException("Error trying to start the CNL Plugin. " + e.getMessage());
		}
	}

	/**
	 * Create the table columns and set their names.
	 * 
	 * @param table The table where the columns will be created.
	 */
	private void createColumns(Table table)
	{
		TableColumn[] columns = new TableColumn[4];
		columns[0] = new TableColumn(table, SWT.NONE);
		columns[1] = new TableColumn(table, SWT.NONE);
		columns[2] = new TableColumn(table, SWT.NONE);
		columns[3] = new TableColumn(table, SWT.NONE);

		columns[0].setText("Description");
		columns[1].setText("Sentence");
		columns[2].setText("Step");
		columns[3].setText("Resource");
	}

	/**
	 * Updates the error counter
	 */
	private void updateErrorCounter()
	{
		group.setText(this.viewer.getTable().getItems().length + " errors in "
				+ CNLPluginController.getInstance().getSentenceCount() + " sentences.");

	}


	public void update() throws TargetException
	{
		this.update(false);
	}

	public void update(boolean updateAnyWay) throws TargetException
	{
		try
		{
			if (ProjectManagerExternalFacade.getInstance().hasOpenedProject() || updateAnyWay)
			{
				if (this.currentProject == null || this.hasDocumentModification() || updateAnyWay)
				{
					this.currentProject = ProjectManagerExternalFacade.getInstance()
					.getCurrentProject();

					List<CNLFault> faultList = CNLPluginController.getInstance()
					.processPhoneDocuments(this.currentProject.getPhoneDocuments());
					this.viewer.setInput(faultList);
					this.updateErrorCounter();
				}
			}
		}
		catch (IOException e)
		{
			throw new TargetException("Error while updating the CNL View Advisor. "
					+ e.getMessage());
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
			PhoneDocument pDoc = this.currentProject.getPhoneDocumentFromFilePath(file
					.getAbsolutePath());
			// If there is not a document or the file is not updated.
			if (pDoc == null || !this.currentProject.isDocumentUpdated(file))
			{
				result = true;
				break;
			}
		}

		return result || docFiles.size() != this.currentProject.getPhoneDocuments().size();
	}

	/**
	 * 
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
}