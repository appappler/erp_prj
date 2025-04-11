package kr.co.sist.admin.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 
 */
public class DbConnection {

	private static DbConnection dbCon;
	
	private DbConnection() {
		
	}//DbConnection
	
	public static DbConnection getInstance() {
		
		if ( dbCon == null ) {
			dbCon = new DbConnection();
		}//end if
		return dbCon;
	}//getInstance
	
	public Connection getConn() throws SQLException{
		
		System.out.println("프로퍼티 경로: "+System.getProperty("user.dir"));
		String currentDir=System.getProperty("user.dir");
		
		File propertyFile = new File(currentDir+"/src/properties/database.properties"); 
		System.out.println("프로퍼티 여부: "+propertyFile.exists());
		
		if(!propertyFile.exists()) {
			throw new SQLException("database.properties가 지정된 경로에 존재하지 않아요.");
		}//end if
		
		Properties comodo = new Properties();
		String driver="";
		String url="";
		String id="";
		String pass="";
		
		try {
			comodo.load(new FileInputStream(propertyFile));
		} catch (IOException ie) {
			ie.printStackTrace();
		}//end catch
		
		Connection con=null;
		
		driver=comodo.getProperty("driverClass");
		url=comodo.getProperty("url");
		id=comodo.getProperty("id");
		pass=comodo.getProperty("pass");
		
		//1.드라이버 로딩
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}//end catch
		
		//2. 커넥션얻기
		con=DriverManager.getConnection(url, id, pass);
		
		return con;
		
	}//getConn
	
	public void closeDB(ResultSet rs, Statement stmt, Connection con)
	throws SQLException{
		
		try {
			if( rs != null ) {	rs.close(); }//end if
			if( stmt != null ) {stmt.close(); }//end if
		}finally{
			if( con != null ) {con.close(); }//end if
		}//end finally
		
	}//closeDB
	
}//class
