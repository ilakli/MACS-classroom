package database;

import java.awt.List;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;

import defPackage.Classroom;
import defPackage.Comment;
import defPackage.Material;
import defPackage.Person;
import defPackage.Post;
import defPackage.Section;
import defPackage.Seminar;

public class DBConnection {

	private DataSource dataSource;
	public static final String DATABASE_ERROR = "DATABASE ERROR";

	// creates constructor
	protected void createConstructor() {
		PoolProperties p = new PoolProperties();
		p.setUrl(DBinfo.MYSQL_DATABASE_SERVER);
		p.setDriverClassName(DBinfo.JDBC_DRIVER);
		p.setUsername(DBinfo.MYSQL_USERNAME);
		p.setPassword(DBinfo.MYSQL_PASSWORD);

		dataSource = new DataSource();
		dataSource.setPoolProperties(p);
	}

	public DBConnection() {
		// delete here to uses tests;
		// createConstructor();
	}

	/**
	 * 
	 * @return new Connection
	 */
	public Connection getConnection() {
		try {
			Class.forName(DBinfo.JDBC_DRIVER);
			return DriverManager.getConnection(DBinfo.MYSQL_DATABASE_SERVER, DBinfo.MYSQL_USERNAME, DBinfo.MYSQL_PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * @param query
	 *            that PreparedStatement needs to execute
	 * @return MyConnection object which includes Connection and
	 *         PreparedStatement
	 */
	MyConnection getMyConnection(String query) {
		Connection con = getConnection();
		PreparedStatement stmnt = null;
		try {
			stmnt = con.prepareStatement(query);
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return new MyConnection(con, stmnt);
	}

	/**
	 * checks if given MyConnection returns empty result after executing
	 * 
	 * @param myConnection
	 *            - object that saves PreparedStatement
	 * @return whether result set is empty or not
	 */
	boolean isResultEmpty(MyConnection myConnection) {
		ResultSet rs = null;
		boolean isEmpty;
		try {
			rs = myConnection.executeQuery();
			isEmpty = !rs.next();
		} catch (SQLException | NullPointerException e) {
			isEmpty = true;
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}

		return isEmpty;
	}

	/**
	 * executes given myConnection
	 * 
	 * @param myConnection
	 * @return true - if execution was successful, false - otherwise
	 */
	boolean executeUpdate(MyConnection myConnection) {
		boolean result;
		try {
			myConnection.executeUpdate();
			result = true;
		} catch (SQLException e) {
			result = false;
		} finally {
			myConnection.closeConnection();
		}
		return result;
	}

	/**
	 * 
	 * this is class which saves connection and given prepared statement
	 *
	 */
	public class MyConnection {
		public Connection con;
		public PreparedStatement stmnt;

		public MyConnection(Connection con, PreparedStatement stmnt) {
			this.con = con;
			this.stmnt = stmnt;
		}

		public ResultSet executeQuery() {
			try {
				return stmnt.executeQuery();
			} catch (SQLException e) {
				return null;
			}
		}

		public void closeConnection() {
			try {
				con.close();
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		}

		public void executeUpdate() throws SQLException {
			stmnt.executeUpdate();
		}
	}	
}

