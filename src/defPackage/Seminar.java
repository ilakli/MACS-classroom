package defPackage;

import java.util.List;

import database.DBConnection;
import database.SeminarDB;
import database.SeminaristDB;
import database.StudentDB;

public class Seminar {
	
	private int seminarN;
	private String classroomId;
	protected DBConnection seminarConnection;
	private SeminaristDB seminaristDB;
	private SeminarDB seminarDB;
	private StudentDB studentDB;

	public Seminar(int seminarN, String classroomId){

		this.seminarN = seminarN;
		this.classroomId = classroomId;
		seminarConnection = new DBConnection();
		seminaristDB = new SeminaristDB();
		seminarDB = new SeminarDB();
		studentDB = new StudentDB();
	}
	

	/**
	 * Returns name of this current seminar.
	 * 
	 * @return
	 */
	public int getSeminarN(){
		return this.seminarN;
	}
	
	/**
	 * Returns id of the classroom associated with this current seminar.
	 * 
	 * @return
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
	
	/**
	 * 
	 * @return id of the seminar
	 */
	public String getSeminarId() {
		return seminarDB.getSeminarId(seminarN, classroomId);
	}
	
	
	/**
	 * Returns person object of the seminarist of current seminar, null if seminarist is not set
	 * 
	 * @return
	 */
	public Person getSeminarist(){
		String seminarId = getSeminarId();
		return seminaristDB.getSeminarist(seminarId);
	}
	
	
	/**
	 * Returns list of person objects of students of current seminar
	 * 
	 * @return
	 */
	public List<Person> getSeminarStudents(){
		String seminarId = getSeminarId();
		return studentDB.getSeminarStudents(seminarId);
	}
	
	/**
	 * Returns true if seminarist was removed from the seminar, false otherwise 
	 * 
	 * @return
	 */
	public boolean removeSeminarist(){
		Person seminarist = getSeminarist();
		return seminaristDB.deleteSeminarist(seminarist.getEmail(), classroomId);
		
	}
	
	/**
	 * Returns true if seminarist was added to the section, false otherwise
	 * @param seminarist
	 * @return
	 */
	public boolean setSeminarist(Person seminarist){
		return seminarDB.addSeminaristToSeminar(seminarN, seminarist.getEmail(), classroomId);		
	}

	/**
	 * Returns true if student was removed from the seminar false otherwise
	 * @param student
	 * @return
	 */
	public boolean removeStudentFromSeminar(Person student){
		return seminarDB.deleteStudentFromSeminar(seminarN, student.getEmail(), classroomId);
	}
	
	/**
	 * Returns true if student was added to the seminar false otherwise
	 * @param student
	 * @return
	 */
	public boolean addStudentToSeminar(Person student){
		return seminarDB.addStudentToSeminar(seminarN, student.getEmail(), classroomId);	
	}
	
	/**
	 * Returns true if student is in current seminar, false otherwise
	 * @param student
	 * @return
	 */
	public boolean seminarContainsStudent(Person student){
		return seminarDB.studentExists(student.getPersonID(), getSeminarId());
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomId == null) ? 0 : classroomId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Seminar other = (Seminar) obj;
		if (classroomId == null) {
			if (other.classroomId != null)
				return false;
		} else if (!classroomId.equals(other.classroomId))
			return false;
		if (seminarN != other.seminarN)
			return false;
		return true;
	}

	
	
}
