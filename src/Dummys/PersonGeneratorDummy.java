package Dummys;

import defPackage.DBConnection;
import defPackage.Person;

public class PersonGeneratorDummy {
	
	public static Person createPersonByEmail(String email){
		
		DBConnection connection = new DBConnection();
		Person person = connection.getPersonByEmail(email);
		
		if (person == null) {
			int len = email.length();
			String name = email.substring(0, len/2);
			String surname = email.substring(len/2);
			
			if (email.indexOf('@') != -1){
				int en = email.indexOf('@');
				name = email.substring(0,en);
				surname = email.substring(en);
			}
			
			connection.addPerson(name, surname, email);
			person = connection.getPersonByEmail(email);
		}
		
		return person;
		
	}
	
}
