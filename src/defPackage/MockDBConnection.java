package defPackage;

import java.sql.Connection;
import java.sql.DriverManager;

public class MockDBConnection extends DBConnection {
	public MockDBConnection() {
		
	}
	
	@Override
	public Connection getConnection() {
		try {
			Class.forName(DBinfo.JDBC_DRIVER);
			return DriverManager.getConnection(DBinfo.MYSQL_DATABASE_SERVER, DBinfo.MYSQL_USERNAME, DBinfo.MYSQL_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
