package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class Connect {
	private static String url = "jdbc:mysql://localhost:3306/dblab?serverTimezone=UTC";
	private static String user = "admin";
	private static String pwd = "admin";
	public Connection getConnect() throws Exception{		
		Connection conn = null;
		try {
		    // create a connection to the database
		    conn = DriverManager.getConnection(url, user, pwd);
		}catch(SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}
}
