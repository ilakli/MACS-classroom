package defPackage;

import database.ClassroomDB;

public class MockClassroom extends Classroom{
	/**
	 * This is MockClassroom to use in tests;
	 * It uses MockDBConnection so old one is changed;
	 * 
	 */
	
	public MockClassroom(String classroomName, String classroomID) {
		super(classroomName, classroomID);
		super.classroomConnection = new MockDBConnection();
	}
}
