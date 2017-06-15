package defPackage;

import java.util.List;

public class Seminar {
	
	private String id;
	private String name;
	private String classroomId;
	
	public Seminar(String id, String name, String classroomId){
		
		this.id = id;
		this.name = name;
		this.classroomId = classroomId;
	}
	
	/**
	 * Returns id associated with this seminar.
	 * 
	 * @return
	 */
	public String getSeminarId(){
		return this.id;
	}
	
	/**
	 * Returns name of this current seminar.
	 * 
	 * @return
	 */
	public String getSeminarName(){
		return this.name;
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
	 * Returns person object of the seminarist of current seminar, null if seminarist is not set
	 * 
	 * @return
	 */
	public Person getSeminarist(){
		return null;	
	}
	
	
	/**
	 * Returns list of person objects of students of current seminar
	 * 
	 * @return
	 */
	public List<Person> getSeminarStudents(){
		return null;	
	}
	
	/**
	 * Returns true if seminarist was removed from the seminar, false otherwise 
	 * 
	 * @return
	 */
	public boolean removeSeminarist(){
		return false;
		
	}
	
	/**
	 * Returns true if seminarist was added to the section, false otherwise
	 * @param seminarist
	 * @return
	 */
	public boolean setSeminarist(Person seminarist){
		return false;
		
	}

	/**
	 * Returns true if student was removed from the seminar false otherwise
	 * @param student
	 * @return
	 */
	public boolean removeStudentFromSeminar(Person student){
		return false;
	}
	
	/**
	 * Returns true if student was added to the seminar false otherwise
	 * @param student
	 * @return
	 */
	public boolean addStudentToSeminar(Person student){
		return false;		
	}
	
	
	/**
	 * Returns true if student is in current seminar, false otherwise
	 * @param student
	 * @return
	 */
	public boolean seminarContainsStudent(Person student){
		return false;	
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classroomId == null) ? 0 : classroomId.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	
	
}
