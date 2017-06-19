package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Material;

public class MaterialDB {

	private DBConnection db;
	
	public MaterialDB() {
		db = new DBConnection();
	}

	/**
	 * Adds material to the specified classroom
	 * 
	 * @param classroomId
	 *            id of the classroom in which this material is added
	 * @param materialName
	 *            name of the material which is added in classroom
	 * @return returns booelan (whether it added succesfully or not).
	 */
	public boolean addMaterial(String classroomId, String materialName) {

		if (materialName.equals(""))
			return false;

		String query = String.format("insert into `classroom_materials` values (%s,'%s');", classroomId, materialName);

		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}

	/**
	 * Returns all the materials associated with given classroom.
	 * 
	 * @param classroomId
	 *            id of the classroom of which materials is needed
	 * @return returns the ArrayList of materials associated with given
	 *         classroom.
	 */
	public ArrayList<Material> getMaterials(String classroomId) {
		String query = String.format("select * from `classroom_materials` where `classroom_id` = %s;", classroomId);

		MyConnection myConnection = db.getMyConnection(query);
		ArrayList<Material> materials = new ArrayList<Material>();
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				materials.add(new Material(rs.getString(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}

		return materials;
	}
}
