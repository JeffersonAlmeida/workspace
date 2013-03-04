package br.ufpe.cin.target.monitoring;

import java.io.File;
import java.sql.SQLException;
import java.util.List;

import br.ufpe.cin.target.common.exceptions.TargetException;
import br.ufpe.cin.target.common.exceptions.UseCaseDocumentXMLException;
import br.ufpe.cin.target.common.ucdoc.xml.UseCaseDocumentXMLParser;

public aspect ExceptionMonitoring {
	
	pointcut TargetRefresh(boolean throwExceptionOnFatalError,int operation ) : 
		execution(private TargetProjectRefreshInformation TargetProjectRefresher.reloadProject(boolean , int)) 
		&& args(throwExceptionOnFatalError,operation);

	
	
	
	pointcut UCDocXMLParser(UseCaseDocumentXMLParser uc, File f) : 
		execution(public UseCaseDocumentXMLParser.new(File)) && args(f) && this(uc);
	
			
	
	after(UseCaseDocumentXMLParser uc, File f) throwing(UseCaseDocumentXMLException u) throws SQLException :
		UCDocXMLParser(uc,f) {
		JdbcConnection.generateException("Erro no XML Parser. XML mal formado", uc.toString());
	}	
	
	after(boolean b,int op) throwing(TargetException t) throws SQLException : TargetRefresh(b,op) {
		JdbcConnection.generateException(t.getMessage(), "None");
	}
}
