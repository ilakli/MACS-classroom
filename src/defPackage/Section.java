package defPackage;

public class Section {
	
	private String id;
	private String name;
	private String classroomId;
	
	public Section(String id, String name, String classroomId){
		
		this.id = id;
		this.name = name;
		this.classroomId = classroomId;
	}
	
	/**
	 * Returns id of this current section.
	 * 
	 * @return
	 */
	public String getSectionId(){
		return this.id;
	}
	
	/**
	 * Returns name of this current section.
	 * 
	 * @return
	 */
	public String getSectionName(){
		return this.name;
	}
	
	
	/**
	 * Returns id of the classroom associated with this current section.
	 * 
	 * @return
	 */
	public String getClassroomId(){
		return this.classroomId;
	}
}
