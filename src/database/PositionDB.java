package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Position;

public class PositionDB {
	
	private DBConnection db;
	
	public PositionDB() {
		db = new DBConnection();
	}
	
	public ArrayList<Position> getAllPositions(){

		String query = String.format("select * from `positions`;");
		
		MyConnection myConnection = db.getMyConnection(query);
		ArrayList<Position> positions= new ArrayList<Position>();
		
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				positions.add(new Position(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}
		
		return positions;
	}
	
}
