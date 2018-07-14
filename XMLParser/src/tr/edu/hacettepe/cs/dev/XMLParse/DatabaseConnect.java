package tr.edu.hacettepe.cs.dev.XMLParse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class DatabaseConnect {
	
	private String serverIP_port;
	private String databaseName;
	private String tableName;
	
	private String connectionUrl;
    private Connection con = null;  
    private Statement stmt = null;  
    private ResultSet rs = null;
    
	
	
	public DatabaseConnect() {}
	public DatabaseConnect(String tablename,String dbName,String iport) {
		setDatabaseName(dbName);
		setServerIP_port(iport);
		setTableName(tablename);
	}
	
	protected void dbReader(String whichColumn) throws SQLException {
		setRs("SELECT * FROM "+getTableName());
		int size=0;
		while(getRs().next()) {
			System.out.println(rs.getString(whichColumn));
			size++;
		}
		System.out.println("\n\n"+size+"\n\n");
	}
	
	protected void clearTable() throws SQLException {
		getStmt().executeUpdate("TRUNCATE TABLE "+getTableName());
	}
	
	
	protected void insertNewData(Student[] stus) throws SQLException {
		long startTime = System.currentTimeMillis();
		
		clearTable();
		String sql = "INSERT INTO "+ getTableName() + "(ID,NAME,CLASS,GRADUATE) values(?,?,?,?)";
		int count =0;
		int batchSize=2500;
		PreparedStatement pstmt = null;
		
		try {
			
			getCon().setAutoCommit(false);
			pstmt = getCon().prepareStatement(sql);
			
			for(Student stu:stus) {
				count++;
				
				pstmt.setString(1, stu.getId());
				pstmt.setString(2, stu.getName());
				pstmt.setString(3, stu.getClasss());
				pstmt.setString(4, stu.getGraduate());
				//System.out.println(sql+count);
				pstmt.addBatch();
				
				if(count % batchSize==0) {
					//System.out.println("commit the batch");
					int[] result=pstmt.executeBatch();
					//System.out.println("# rows inserted "+result.length);
					getCon().commit();
				}
			}
			
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}finally {
			if(pstmt==null)
				pstmt.close();
		}
		
		System.out.println("->>-->>batch" + Long.toString(startTime-System.currentTimeMillis()));
		
	}
	
	protected void instertNewData(DefaultTableModel tbl) {
		long startTime = System.currentTimeMillis();
		
		String sqlQuery;
		for(int i=0;i<tbl.getRowCount();i++) {
			sqlQuery = "INSERT INTO "+getTableName()+" VALUES ('"+
					tbl.getValueAt(i, 0)+"','"+tbl.getValueAt(i, 1)+"','"+
					tbl.getValueAt(i, 2)+"','"+tbl.getValueAt(i, 3)+"')";
			//System.out.println(sqlQuery);
			try {
				getStmt().executeUpdate(sqlQuery);
			}catch(Exception e1) {	
	
			}
		}
		
		System.out.println("-->>--<<-normal" + Long.toString(startTime-System.currentTimeMillis()));
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
	protected void dbDisconnection() {
		

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
		setConnectionUrl("jdbc:sqlserver://"+serverIPport+ ";databaseName="+databaseName+";user=okan;password=1234");
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

	public void setRs(String query) throws SQLException {
		this.rs = getStmt().executeQuery(query);
	}

	public String getDatabaseName() {
		return databaseName;
	}

	public void setDatabaseName(String dbName) {
		this.databaseName = dbName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	
}
