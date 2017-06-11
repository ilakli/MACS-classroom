package defPackage;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

public class MockDBConnection extends DBConnection {
	
	/**
	 * This is mock connection to data base
	 * We use this to make tests;
	 */
	
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
