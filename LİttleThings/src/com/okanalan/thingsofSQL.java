package com.okanalan;

import java.sql.*;

import java.io.*;
import static java.lang.Math.toIntExact;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class thingsofSQL{
	public static void main(String[] args) throws Exception
	{
		String connectionUrl = "jdbc:sqlserver://10.210.10.74:1433;databaseName=demo;user=okan;password=1234";
		
	      Connection con = null;  
	      Statement stmt = null;  
	      ResultSet rs = null; 
	      
	      try {  
	          // Establish the connection.  
	          Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
	          con = DriverManager.getConnection(connectionUrl);  
	          stmt = con.createStatement();
	          
	          try{
	          stmt.executeUpdate("INSERT INTO STUDENT VALUES ('14','oka','zivdk','9378')");
	          }catch(Exception e1){System.out.println("there is same");}
	          // Create and execute an SQL statement that returns some data.  
	              
	          String SQL = "SELECT TOP 10 * FROM STUDENT";  

	          rs = stmt.executeQuery(SQL);  

	          // Iterate through the data in the result set and display it.  
	          while (rs.next()) {  
	             System.out.println(rs.getString("Name"));  
	          }  
	       }  

	       // Handle any errors that may have occurred.  
	       catch (Exception e) {  
	          e.printStackTrace();  
	       }  
	       finally {  
	          if (rs != null) try { rs.close(); } catch(Exception e) {}  
	          if (stmt != null) try { stmt.close(); } catch(Exception e) {}  
	          if (con != null) try { con.close(); } catch(Exception e) {}  
	       }  
	}
}