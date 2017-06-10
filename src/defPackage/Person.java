package defPackage;

public class Person {
	
	public static final String ID_ATTRIBUTE_NAME = "personID";

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
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		if (!(obj instanceof Person)) return false;
		
		Person p = (Person) obj;
		
		return p.Email.equals(this.Email);
	}
}
