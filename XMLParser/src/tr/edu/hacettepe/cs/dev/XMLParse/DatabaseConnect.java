package tr.edu.hacettepe.cs.dev.XMLParse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnect {
	
	private String serverIP_port;
	private String connectionUrl;
    private Connection con = null;  
    private Statement stmt = null;  
    private ResultSet rs = null; 
	
	
	public DatabaseConnect() {}
	
	protected void clearTable() {
		
	}
	
	protected void instertNewData(DefaultTableModel tbl) {
		String sqlQuery;
		for(int i=0;i<tbl.getRowCount();i++) {
			sqlQuery = "INSERT INTO STUDENT VALUES ("+
					tbl.getValueAt(i, 0)+tbl.getValueAt(i, 1)+
					tbl.getValueAt(i, 2)+tbl.getValueAt(i, 3)+")";
			try {
				getStmt().executeUpdate(sqlQuery);
			}catch(Exception e1) {	
	
			}
		}
	}

	protected void dbConnection(String ipPort) {
		
		setServerIP_port(ipPort);
		try {
	        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");  
			setCon(DriverManager.getConnection(getConnectionUrl()));
			setStmt(getCon().createStatement());
	        
			JOptionPane.showMessageDialog(null,
				    "We have connected database :)",
				    "Connection",
				    JOptionPane.PLAIN_MESSAGE);
		}catch(Exception e1) {
    		JOptionPane.showMessageDialog(null,
    			    "ohhh noooo there was a problem",
    			    "Error",
    			    JOptionPane.ERROR_MESSAGE);
		}
	}
	protected void dbDisconnection(String ipPort) {
		
		setServerIP_port(ipPort);
		try {
			if(getRs()!=null) getRs().close();
			if(getStmt()!=null) getStmt().close();
			if(getCon()!=null) getCon().close();
	        
			JOptionPane.showMessageDialog(null,
				    "We have disconnected database :)",
				    "Connection",
				    JOptionPane.PLAIN_MESSAGE);
		}catch(Exception e1) {
    		JOptionPane.showMessageDialog(null,
    			    "something happend",
    			    "Error",
    			    JOptionPane.ERROR_MESSAGE);
		}
	}

	public String getServerIP_port() {
		return serverIP_port;
	}

	private void setServerIP_port(String serverIPport) {
		this.serverIP_port = serverIPport;
		setConnectionUrl("jdbc:sqlserver://"+serverIPport+ ";databaseName=demo;user=okan;password=1234");
	}
	
	
	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	}

	public Connection getCon() {
		return con;
	}

	public void setCon(Connection con) {
		this.con = con;
	}

	public Statement getStmt() {
		return stmt;
	}

	public void setStmt(Statement stmt) {
		this.stmt = stmt;
	}

	public ResultSet getRs() {
		return rs;
	}

	public void setRs(ResultSet rs) {
		this.rs = rs;
	}
	
}
