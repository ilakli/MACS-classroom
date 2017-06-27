package defPackage;

public class Function {
	private String id;
	private String name;
	
	/**
	 * basic constructor, takes all parameters as input
	 * @param id
	 * @param name
	 */
	public Function(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return id of function
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * @return name of function
	 */
	public String getName() {
		return name;
	}
}
