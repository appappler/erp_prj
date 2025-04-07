package csj_dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBMS 와 연결된 Connection 얻
 */
public class DBConnection {

	public DBConnection() throws SQLException {
		// 1.Driver 로딩
		try {
			Class.forName("oracle.jdbc.OracleDriver");
			System.out.println("드라이버 로딩 성공!");

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} // end catch
			// 2.Connection 얻기

		String url = "jdbc:oracle:thin:@localhost:1521:orcl";
		String id = "c##comodo";
		String pass = "dragon";

		Connection con= DriverManager.getConnection(url, id, pass);
		System.out.println("DB연동 객체"+con);
		
	}// DBConnection

	public static void main(String[] args) {

		try {
			new DBConnection();
		} catch (SQLException e) {
			e.printStackTrace();
		}//end catch
	}// main

}// class
