package defPackage;

public class ActiveSeminar {
	
	private String seminarId;
	private String location;
	private String name;
	private String time;

	public ActiveSeminar(String seminarId, String name, String location, String time) {
		
		this.seminarId = seminarId;
		this.location = location;
		this.name = name;
		this.time = time;
	}

	/**
	 * Returns seminar associated with this current active seminar.
	 * 
	 * @return
	 */
	public String getSeminarId() {
		return this.seminarId;
	}

	/**
	 * Returns location where this seminar is held.
	 * 
	 * @return
	 */
	public String getActiveSeminarLocation() {
		return this.location;
	}

	/**
	 * Returns name of the seminar.
	 * 
	 * @return
	 */
	public String getActiveSeminarName() {
		return this.name;
	}

	/**
	 * Returns time on which seminar is held.
	 * 
	 * @return
	 */
	public String getActiveSeminarTime() {
		return this.time;
	}

}
