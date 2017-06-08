package defPackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DBConnection {
	
	private Connection getConnection() {
		Connection currentConnection = null;
		try {
			Class.forName(DBinfo.JDBC_DRIVER);
			currentConnection = DriverManager.getConnection(DBinfo.MYSQL_DATABASE_SERVER, DBinfo.MYSQL_USERNAME, 
					DBinfo.MYSQL_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return currentConnection;
	}
	
	public ArrayList <Course> getCourses() {
		ArrayList <Course> result = new ArrayList <Course>();
		return result;
	}
}
