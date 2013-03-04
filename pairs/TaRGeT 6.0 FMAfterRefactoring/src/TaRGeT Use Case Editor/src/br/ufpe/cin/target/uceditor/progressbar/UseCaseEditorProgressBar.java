package br.ufpe.cin.target.uceditor.progressbar;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
//04/01 adiciona uma barra de progresso ao exportar PDF
public class UseCaseEditorProgressBar implements IRunnableWithProgress {
	private String taskName;

	public UseCaseEditorProgressBar(String taskName) {
		super();
		this.taskName = taskName;
	}

	
	public void run(IProgressMonitor monitor) throws InvocationTargetException,
			InterruptedException {
		monitor.beginTask(this.taskName, 1);
		monitor.worked(1);
	}

}
