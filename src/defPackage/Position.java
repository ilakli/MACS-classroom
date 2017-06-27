package defPackage;

public class Position {
	
	private String id;
	private String name;
	
	/**
	 * basic constructor, takes all parameters as input
	 * @param id
	 * @param name
	 */
	public Position(String id, String name){
		this.id = id;
		this.name = name;
	}
	
	/**
	 * @return id of position
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * @return name of position
	 */
	public String getName() {
		return name;
	}
}
