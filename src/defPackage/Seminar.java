package defPackage;

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
}
