package defPackage;

public class Person {
	private String name;
	private String surname;
	private String Email;
	private String personID;
	
	public Person (String name, String surname, String Email, String personID) {
		this.name = name;
		this.surname = surname;
		this.Email = Email;
		this.personID = personID;
	}
	
	public String getName() {
		return name;
	}
	
	public String getSurname() {
		return surname;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public String getPersonID() {
		return personID;
	}
}
