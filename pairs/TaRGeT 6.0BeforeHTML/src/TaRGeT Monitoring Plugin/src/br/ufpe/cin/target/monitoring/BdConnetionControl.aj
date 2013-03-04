package br.ufpe.cin.target.monitoring;

import java.sql.SQLException;

public aspect BdConnetionControl {	
	boolean connection = false;
	String user = "andre";	
	
	pointcut generateUser() : 
		execution(* JdbcConnection.generateUser()) && !within(BdConnetionControl);
	
	pointcut generateFilter(String s1, String s2, String s3, String s4) : 
		execution(* JdbcConnection.generateFilter(String, String,String,String))
		&& args(s1,s2,s3,s4) && !within(BdConnetionControl);
	
	pointcut generateProject( String s1, String s2, String s3) : 
		execution(* JdbcConnection.generateProject(String, String, String))
		&& args(s1,s2,s3) && !within(BdConnetionControl);	
	
	pointcut generateTestCase( String s1, String s2) : 
		execution(* JdbcConnection.generateTestCase(String, String)) 
		&& args(s1,s2) && !within(BdConnetionControl);
	
	
	pointcut generateException(String name, String exc) : 
		execution(* JdbcConnection.generateException(String, String)) && args(name, exc);	
	
	void around() : generateUser() {
		try {
			if (connection) {
				JdbcConnection.connect(user);				
				proceed();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	void around(String exc, String exc2) : generateException(exc, exc2) {
		try {
			if (connection) {
				JdbcConnection.connect(user);				
				proceed(exc, exc2);
				JdbcConnection.closeConnection();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	
	
	
	void around(String s1, String s2) : generateTestCase (s1,s2){
		try {
			if (connection) {
				JdbcConnection.connect(user);
				proceed(s1,s2);
				JdbcConnection.closeConnection();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		} 
	}
	
	void around(String s1, String s2, String s3) : generateProject(s1,s2,s3) {
		try {
			if (connection) {		
				proceed(s1,s2,s3);
				JdbcConnection.closeConnection();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
	

	void around(String s1, String s2, String s3, String s4) : generateFilter(s1,s2,s3,s4) {
		try {
			if (connection) {		
				JdbcConnection.connect(user);
				proceed(s1,s2,s3,s4);
				JdbcConnection.closeConnection();
			}
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
