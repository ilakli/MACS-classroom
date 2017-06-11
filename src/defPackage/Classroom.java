package defPackage;

import java.util.ArrayList;
import java.util.List;

public class Classroom {
	
	public static final String ID_ATTRIBUTE_NAME = "classroomID";
	public static final String EMAIL_ATTRIBUTE_NAME = "email";
	
	private String classroomName;
	private String classroomID;
	protected DBConnection classroomConnection;
	
	//Constructor;
	public Classroom (String classroomName, String classroomID) {
		this.classroomName = classroomName;
		this.classroomID = classroomID;
		classroomConnection = new DBConnection();
	}
	
	/**
	 * This method returns name of a classroom.
	 * @return - name of a classroom;
	 */
	public String getClassroomName(){
		return this.classroomName;
	}
	
	
	/**
	 * This method returns ID of a classroom.
	 * @return - ID of a classroom 
	 */
	public String getClassroomID(){
		return this.classroomID;
	}
	
	
	/**
	 * This method returns section leaders' list from a classroom.
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - section leaders list
	 */
	public List <Person> getClassroomSectionLeaders(){
		return this.classroomConnection.getSectionLeaders(this.classroomID);
	}
	
	
	/**
	 * This method returns seminarists' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - list of a seminarists
	 */
	public List <Person> getClassroomSeminarists(){
		return this.classroomConnection.getSeminarists(this.classroomID);
	}
	
	/**
	 * This method returns students' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - list of students
	 */
	public List <Person> getClassroomStudents(){
		return this.classroomConnection.getStudents(this.classroomID);
	}
	
	/**
	 * This method returns lecturers' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information from database. 
	 * @return - list of lecturers
	 */
	public List <Person> getClassroomLecturers(){
		return (this.classroomConnection.getLecturers(this.classroomID));
	}
	
	
	/**
	 * This method returns seminars' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information.
	 * @return - list of seminars
	 */
	public List <Seminar> getClassroomSeminars(){
		return this.classroomConnection.getSeminars(this.classroomID);
	}
	
	/**
	 * This method returns sections' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information.
	 * @return - list of sections
	 */
	public List <Section> getClassroomSections(){
		return this.classroomConnection.getSections(this.classroomID);
	}
	
	/**
	 * This method returns ActiveSeminars' list from a classroom;
	 * This method returns only copy of real data, so it can't be changed from outside;
	 * We use DBConnection to return information.
	 * @return - list of ActiveSeminars
	 */
	public List <ActiveSeminar> getClassroomActiveSeminar(){
		return this.classroomConnection.getActiveSeminars(this.classroomID);
	}
	
	/**
	 * This method tells us if the person is a lecturer in the classroom;
	 * @param email - person's email'
	 * @return - true if a person is a lecturer, false otherwise;
	 */
	public boolean classroomLecturerExists (String email){
		return this.classroomConnection.lecturerExists(email, this.classroomID);
	}
	
	/**
	 * This method tells us if the person is a seminarist in the classroom
	 * @param email - person's email;
	 * @return - true if a person is a seminarist, false otherwise; 
	 */
	public boolean classroomSeminaristExists (String email){
		return this.classroomConnection.seminaristExists(email, this.classroomID);
	}
	
	/**
	 * This method tells us if the person is a section leader in the classroom;
	 * @param email - person's email
	 * @return - true if a person is a section leader, false otherwise;
	 */
	public boolean classroomSectionLeaderExists (String email){
		return this.classroomConnection.sectionLeaderExists(email, this.classroomID);
	}
	
	/**
	 * This method tells us if the person is a student in the classroom;
	 * @param email - person's email
	 * @return - true if a person is a student, false otherwise
	 */
	public boolean classroomStudentExists (String email){
		return this.classroomConnection.studentExists(email, this.classroomID);
	}
	/**
	 * This method tells us if a person is a member of the classroom
	 * @param email - person's email;
	 * @return - true if a person is a member, false otherwise;
	 */
	public boolean classroomPersonExists (String email){
		return this.classroomConnection.personExists(email, this.classroomID);
	}
	
	/**
	 * This method adds person in the classroom as a lecturer; 
	 * @param email - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it was a lecturer before or some error occurred) 
	 */
	public boolean classroomAddLecturer (String email){
		if(classroomLecturerExists(email)){
			return false;
		} else {
			return this.classroomConnection.addLecturer(email, this.classroomID);
		}
		
	}
	
	/**
	 * This method adds person in the classroom as a student; 
	 * @param email - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it was a student before or some error occurred) 
	 */
	public boolean classroomAddStudent (String email){
		if(classroomStudentExists(email)){
			return false; 
		} else { 
			return this.classroomConnection.addStudent(email, this.classroomID);
		}
	}
	
	/**
	 * This method adds person in the classroom as a section leader; 
	 * @param email - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it was a section leader before or some error occurred) 
	 */
	public boolean classroomAddSectionLeader (String email){
		if(classroomSectionLeaderExists(email)){
			return false;
		} else {
			return this.classroomConnection.addSectionLeader(email, this.classroomID);
		}
	}
	
	
	//mari
	/**
	 * This method adds seminar in the classroom; 
	 * @param seminarName - name of the seminar;
	 * @return - true if a seminar has added successfully, false otherwise 
	 * (if seminar with same name already existed in this classroom or some error occurred) 
	 */
	public boolean classroomAddSeminar(String seminarName){
		return false;
	}
	

	//mari
	/**
	 * This method adds section in the classroom; 
	 * @param sectionName - name of the section;
	 * @return - true if a section has added successfully, false otherwise 
	 * (if section with same name already existed in this classroom or some error occurred) 
	 */
	public boolean classroomAddSection(String sectionName){
		return false;
	}
	
	
	/**
	 * This method adds person in the classroom as a seminarist; 
	 * @param email - person's email;
	 * @return - true if a person has added successfully, false otherwise (if it was seminarist before or some error occurred) 
	 */
	public boolean classroomAddSeminarist (String email){
		if(classroomSeminaristExists(email)){
			return false;
		} else {
			return this.classroomConnection.addSeminarist(email, this.classroomID);
		}
	}
	
	/**
	 * This method deletes a lecturer from the classroom; 
	 * @param email - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if it was not lecturer before or some error occurred) 
	 */
	public boolean classroomDeleteLecturer (String email){
		if(!classroomLecturerExists(email)){
			return false;
		} else {
			return this.classroomConnection.deleteLecturer(email, this.classroomID);
		}
	}
	
	/**
	 * This method deletes a student from the classroom; 
	 * @param email - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if it was not a student before or some error occurred) 
	 */
	public boolean classroomDeleteStudent (String email){
		if(!classroomStudentExists(email)){
			return false;
		} else {
			return this.classroomConnection.deleteStudent(email, this.classroomID);
		}
	}
	
	/**
	 * This method deletes a section leader from the classroom; 
	 * @param email - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if it was not a section leader before or some error occurred) 
	 */
	public boolean classroomDeleteSectionLeader (String email){
		if(!classroomSectionLeaderExists(email)){
			return false;
		} else {
			return this.classroomConnection.deleteSectionLeader(email, this.classroomID);
		}
	}
	
	/**
	 * This method deletes a seminarist from the classroom; 
	 * @param email - person's email;
	 * @return - true if a person has deleted successfully, false otherwise (if it was not seminarist before or some error occurred) 
	 */
	public boolean classroomDeleteSeminarist (String email){
		if(!classroomSeminaristExists(email)){
			return false;
		} else {
			return this.classroomConnection.deleteSeminarist(email, this.classroomID);
		}
	}
	
	
	//mari  sheidzleba saxelis magivrad mtlianad seminari gadmogce not sure
	/**
	 * This method deletes seminar from the classroom; 
	 * @param seminarName - name of the seminar;
	 * @return - true if a seminar has deleted successfully, false otherwise 
	 * (if seminar with same name didn't exist in this classroom or some error occurred) 
	 */
	public boolean classroomDeleteSeminar(String seminarName){
		return false;
	}
	

	//mari  sheidzleba saxelis magivrad mtlianad seqcia gadmogce not sure
	/**
	 * This method deletes section in the classroom; 
	 * @param sectionName - name of the section;
	 * @return - true if a section has deleted successfully, false otherwise 
	 * (if section with same name didn't exist in this classroom or some error occurred) 
	 */
	public boolean classroomDeleteSection(String sectionName){
		return false;
	}

	
	// UNIQUE KEY `unique-key-timetable` (`seminar_id`, `seminar_name`, `seminar_location`)
	// asea bazashi da mgoni araa maincdamainc logikuri
	public boolean classroomAddActiveSeminar(String activeSeminarName, String seminarName, String time,
			String location) {
		// TODO Auto-generated method stub
		return false;
	}

	
	public boolean classroomAddStudentToSection(String sectionName, String studentEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean classroomAddStudentToSeminar(String seminarName, String studentEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean classroomAddSeminaristToSeminar(String seminarName, String seminaristEmail) {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean classroomAddsectionLeaderToSection(String sectionName, String sectionLeaderEmail) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
