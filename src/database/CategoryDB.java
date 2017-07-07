package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import database.DBConnection.MyConnection;
import defPackage.Category;

public class CategoryDB {
	
	private DBConnection db;
	
	public CategoryDB(AllConnections allConnections) {
		db = allConnections.db;
	}
	
	/**
	 * This method adds category for classroom
	 * @param classroomId - class id
 	 * @param categoryName - name of the new category
	 * @return - true if added, false otherwise.
	 */
	public boolean addCategory(String classroomId, String categoryName) {

		if (categoryName.equals("")) {
			return false;
		}

		String query = String.format("insert into `classroom_material_category`(classroom_id,category_name) values (%s,'%s');", 
				classroomId, categoryName);
		
		System.out.println("Adding Query Is: " + query);
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
	/**
	 * This method returns all categories for classroom;
	 * @param classroomId - ID of the classroom
	 * @return - returns all the category;
	 */
	public ArrayList<Category> getCategorys(String classroomId) {
		String query = String.format("select * from `classroom_material_category` where `classroom_id` = %s;", classroomId);
		System.out.println(query);
		MyConnection myConnection = db.getMyConnection(query);
		ArrayList<Category> categorys = new ArrayList<Category>();
		try {
			ResultSet rs = myConnection.executeQuery();
			while (rs != null && rs.next()) {
				categorys.add(new Category(rs.getString("classroom_id"), rs.getString("category_name"), rs.getString("category_id")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (myConnection != null) {				
				myConnection.closeConnection();
			}
		}

		return categorys;
	}
	
	/**
	 * Deleted category from the classroom, all the materials will be deleted alse; 
	 * @param classroomId - classroom id;
	 * @param categoryId - category id;
	 * @return true if the category was deleted, false otherwise;
	 */
	public boolean deleteCategory(String classroomId, String categoryId) {
		String preQuery = String.format("delete from `classroom_materials` where `classroom_id` = %s and `category_id` = %s",
				classroomId, categoryId);
		System.out.println(preQuery);
		MyConnection preConnection = db.getMyConnection(preQuery);
		db.executeUpdate(preConnection);
		
		String query = String.format("delete from `classroom_material_category` where `classroom_id =  %s and category_id = %s;", 
				classroomId, categoryId);
		System.out.println(query);
		
		MyConnection myConnection = db.getMyConnection(query);
		return db.executeUpdate(myConnection);
	}
	
}
