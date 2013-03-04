package br.ufpe.cin.target.monitoring;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JdbcConnection {
	
	static String user;
	static int projectUserId;
	static int userId;
	boolean conectar = true;
	static Connection con;
    static Statement stmt;
    static String connectionUrl = "jdbc:sqlserver://localhost:1433;" +
	   "databaseName=UniTurnKeyTeste;user=sa;password=UCLseF7vctd8;";
    
    
    public static void connect(String name) {
    		user = name;
		    try
		    {
		    	
		    		Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
		    }
		    catch(java.lang.ClassNotFoundException e)
		    {
	            System.err.print("ClassNotFoundException: ");
	            System.err.println(e.getMessage());
		    } 
		    try
		    {	
		    	
		    		con = DriverManager.getConnection(connectionUrl);	           
		    }
	            catch(SQLException ex)
		    {
	            System.err.println("SQLException: " + ex.getMessage());
		    }
    }
    
    public static void generateTestCase(String header, String useCases) throws SQLException {
		PreparedStatement pstmt;		
		pstmt = con.prepareStatement("{ call sp_generateTestCase(?, ?, ?, ?) }");
		pstmt.setString(1, header);
		pstmt.setString(2, useCases);
		pstmt.setInt(3, userId);
		pstmt.setInt(4, projectUserId);
		pstmt.execute();		
    }
    
    public static void generateException(String name, String doc) throws SQLException {
    	
		PreparedStatement pstmt;		
		pstmt = con.prepareStatement("{ call sp_generateException(?,?,?) }");
		pstmt.setString(1, name);
		pstmt.setInt(2, projectUserId);
		pstmt.setString(3, doc);
		pstmt.execute();
    }
    
    public static void generateUser() throws SQLException {
    	CallableStatement cstmt = null;
    	ResultSet rs = null;
    	Statement stmt = null;
    	
		stmt = con.createStatement();
		cstmt = con.prepareCall("{ call sp_generateUser(?) }");
		cstmt.setString(1, user);
		cstmt.execute();
		
		rs = stmt.executeQuery("select id from TargetUser where userName = '" + user + "'");
		
		while (rs.next()) {
		    userId =  rs.getInt("id");
		}		
    }
    
    
    public static void generateFilter(String op, String useCase, String req, String path) throws SQLException {
    	CallableStatement cstmt = null;
		cstmt = con.prepareCall("{ call sp_generateFilter(?,?,?,?,?) }");
		cstmt.setInt(1,projectUserId);
		cstmt.setString(2, op);
		cstmt.setString(3, useCase);
		cstmt.setString(4, req);
		cstmt.setString(5, path);
		cstmt.execute();
		
    }
    
    public static void generateProject(String op, String localFolder, String projectName) throws SQLException {
    	CallableStatement cstmt = null;
    	ResultSet rs = null;
    	Statement stmt = null;		
		stmt = con.createStatement();			
	   	cstmt = con.prepareCall("{ call sp_generateProject(?, ?, ?, ?) }");
		cstmt.setString(1, localFolder);
		cstmt.setString(2, projectName);
		cstmt.setString(3, user);
		cstmt.setString(4, op);
		cstmt.execute();
		rs = stmt.executeQuery("select max(id) as id from ProjectUser");		
		while (rs.next()) {
		    projectUserId =  rs.getInt("id");
		}
    }
    
    public static void closeConnection() {
    	try {
			con.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  
    }
}
