package defPackage;

import database.ClassroomDB;

public class MockClassroom extends Classroom{
	/**
	 * This is MockClassroom to use in tests;
	 * It uses MockDBConnection so old one is changed;
	 * 
	 */
	
	public MockClassroom(String classroomName, String classroomID, String creatorId) {
		super(classroomName, classroomID, creatorId);
		super.classroomConnection = new MockDBConnection();
	}
}
