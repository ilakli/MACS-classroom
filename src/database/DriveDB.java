package database;

import java.sql.ResultSet;
import java.sql.SQLException;

import database.DBConnection.MyConnection;

public class DriveDB {

	private AllConnections allConnections;
	
	public DriveDB(AllConnections allConnections) {
		this.allConnections = allConnections;
	}
	
	public void addClassroomFolder (String classroomId, String folderId) {
		String query = String.format(
				"insert into `classroom-classroom_folder` (`classroom_id`, `folder_id`) values (%s, '%s');",
				classroomId, folderId);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		allConnections.db.executeUpdate(myConnection);
	}

	public String getClassroomFolder (String classroomId) {
		String folderId = "";
		String query = String.format(
				"select `folder_id` from `classroom-classroom_folder` where `classroom_id` = %s;", classroomId);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {				
				folderId = rs.getString("folder_id");
			}
		} catch (SQLException | NullPointerException e) {
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return folderId;
	}
	
	public void addSeminaristFolder (String classroomId, String seminaristEmail, String folderId) {
		String seminaristId = allConnections.personDB.getPersonId(seminaristEmail);
		String query = String.format(
				"insert into `seminarist-seminarist_folder` (`classroom_id`, `seminarist_id`, `folder_id`)"
				+ "values (%s, %s, '%s');",
				classroomId, seminaristId, folderId);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		allConnections.db.executeUpdate(myConnection);
	}
	
	public String getSeminaristFolder (String classroomId, String seminaristEmail) {
		String folderId = "";
		String seminaristId = allConnections.personDB.getPersonId(seminaristEmail);
		String query = String.format(
				"select `folder_id` from `seminarist-seminarist_folder` where `classroom_id` = %s "
				+ "and `seminarist_id` = %s;", classroomId, seminaristId);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {
				folderId = rs.getString("folder_id");				
			}
		} catch (SQLException | NullPointerException e) {
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return folderId;
	}
	
	public void addSectionLeaderFolder (String classroomId, String sectionLeaderEmail, String folderId) {
		String sectionLeaderId = allConnections.personDB.getPersonId(sectionLeaderEmail);
		String query = String.format(
				"insert into `section_leader-section_leader_folder` (`classroom_id`, `section_leader_id`, `folder_id`)"
				+ "values (%s, %s, '%s');", classroomId, sectionLeaderId, folderId);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		allConnections.db.executeUpdate(myConnection);
	}
	
	public String getSectionLeaderFolder (String classroomId, String sectionLeaderEmail) {
		String folderId = "";
		String sectionLeaderId = allConnections.personDB.getPersonId(sectionLeaderEmail);
		String query = String.format(
				"select `folder_id` from `section_leader-section_leader_folder` where "
				+ "`classroom_id`=%s and `section_leader_id` = %s;", 
				classroomId, sectionLeaderId);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		try {
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {				
				folderId = rs.getString("folder_id");
			}
		} catch (SQLException | NullPointerException e) {
		} finally {
			if (myConnection != null) {
				myConnection.closeConnection();
			}
		}
		return folderId;
	}

	public void addCategoryFolder(String classroomId, String categoryName, String categoryFolder) {
		String query = String.format(
				"insert into `category-category_folder` (`classroom_id`, `category_name`, `category_folder`)"
				+ "values (%s, '%s', '%s');",
				classroomId, categoryName, categoryFolder);
		MyConnection myConnection = allConnections.db.getMyConnection(query);
		allConnections.db.executeUpdate(myConnection);
	}
	
	public String getCategoryFolder (String classroomId, String categoryName) {
		String categoryFolder = "";
		String query = String.format(
				"select `category_folder` from `category-category_folder` where `classroom_id` = %s and `category_name` = '%s';",
				classroomId, categoryName);
		try {
			MyConnection myConnection = allConnections.db.getMyConnection(query);
			ResultSet rs = myConnection.executeQuery();
			if (rs.next()) {
				categoryFolder = rs.getString("category_folder");
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return categoryFolder;
	}
}
