package br.ufpe.cin.target.monitoring;



import java.sql.SQLException;

import br.ufpe.cin.target.pm.controller.ProjectManagerController;
import br.ufpe.cin.target.pm.controller.TargetProject;

public aspect ProjectMonitoring {
	
	
	pointcut openProject() : this(OpenProjectWizard) && call(boolean init());
	
	pointcut newProject() : this(NewProjectWizard) && call(boolean init());
	
	
	after () throws SQLException : openProject()  {
		System.out.println("Entrou aspecto open project");
		JdbcConnection.generateUser();
		TargetProject proj = ProjectManagerController.getInstance().getCurrentProject();
		JdbcConnection.generateProject("u", proj.getRootDir(), proj.getName());
	}
	
	after() throws SQLException : newProject() {
		TargetProject proj = ProjectManagerController.getInstance().getCurrentProject();
		JdbcConnection.generateUser();
		JdbcConnection.generateProject("i", proj.getRootDir(), proj.getName());
	}

}
