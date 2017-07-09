package defPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import database.AllConnections;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class TestSeminar {
	
	private AllConnections db = new AllConnections();
	
	
	@Test
	public void test0init() {
		//Just add people into database so we can use them
		
		db.personDB.addPerson("irakli", "popkhadze", "ipopk15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		db.personDB.addPerson("giorgi", "khosroshvili", "gkhos15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		db.personDB.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		db.personDB.addPerson("nika", "begiashvili", "n.begiashvili@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		db.personDB.addPerson("giorgi", "cercvadze", "gitser15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		db.personDB.addPerson("aleko", "cxovrebovi", "acxcx15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
		db.personDB.addPerson("mari", "berishvili", "mberi15@freeuni.edu.ge","http://cdn.makeuseof.com/wp-content/uploads/2011/04/0-incognito-intro.jpg?x92042");
	}
	
	
	@Test
	public void test1CreateSeminar(){
		String currentClassroom = db.classroomDB.addClassroom("test seminars","1");
		MockClassroom testClass = new MockClassroom("test seminars",currentClassroom,"1");
		testClass.classroomAddSeminar();
		testClass.classroomAddSeminar();		
		
		List <Seminar> seminars = testClass.getClassroomSeminars();
		
		Seminar test1 = seminars.get(0);
		Seminar test2 = seminars.get(1);
		
		assertEquals(test1.getClassroomId(), currentClassroom);	
		assertEquals(test2.getClassroomId(), currentClassroom);
		assertEquals(test1.getSeminarN(), 0);	
		assertEquals(test2.getSeminarN(), 1);
		assertEquals(test1.getSeminarist(),null);	
		assertEquals(test2.getSeminarist(),null);
		assertEquals(test1.getSeminarStudents(), new ArrayList<Person>());
		assertEquals(test2.getSeminarStudents(), new ArrayList<Person>());
	}
	
	@Test
	public void test2addPeople(){
		String currentClassroom = db.classroomDB.addClassroom("add people in seminars","1");
		MockClassroom testClass = new MockClassroom("add people in seminars",currentClassroom,"1");
		String notThisOne = db.classroomDB.addClassroom("another one","1");
		MockClassroom otherOne = new MockClassroom("another one",notThisOne,"1");
		testClass.classroomAddSeminar();
		testClass.classroomAddSeminar();		
		
		List <Seminar> seminars = testClass.getClassroomSeminars();
		
		Seminar test1 = seminars.get(0);
		Seminar test2 = seminars.get(1);
		
		testClass.classroomAddStudent("gkhos15@freeuni.edu.ge");
		testClass.classroomAddSeminarist("n.begiashvili@freeuni.edu.ge");
		testClass.classroomAddSeminarist("acxcx15@freeuni.edu.ge");
		otherOne.classroomAddStudent("ipopk15@freeuni.edu.ge");
		otherOne.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge");		
		
		assertTrue(test1.addStudentToSeminar("gkhos15@freeuni.edu.ge"));
		assertFalse(test1.addStudentToSeminar("gkhos15@freeuni.edu.ge"));
		assertFalse(test1.addStudentToSeminar("n.begiashvili@freeuni.edu.ge"));
		assertFalse(test2.addStudentToSeminar("ipopk15@freeuni.edu.ge"));
		assertFalse(test2.setSeminarist("ipopk15@freeuni.edu.ge"));
		assertFalse(test2.addStudentToSeminar("GauqmebuliaGauqmebulia"));
		assertFalse(test1.setSeminarist("gkhos15@freeuni.edu.ge"));
		assertTrue(test1.setSeminarist("n.begiashvili@freeuni.edu.ge"));
		assertFalse(test2.setSeminarist("n.begiashvili@freeuni.edu.ge"));	
		assertFalse(test2.setSeminarist("s.gvinepadze@freeuni.edu.ge"));
		assertTrue(test2.setSeminarist("acxcx15@freeuni.edu.ge"));
		assertEquals(testClass.getClassroomSeminarists().get(0), test1.getSeminarist());
		assertEquals(testClass.getClassroomSeminarists().get(1), test2.getSeminarist());
	}
	
	@Test
	public void test3DeleteStudents(){
		String currentClassroom = db.classroomDB.addClassroom("delete students in seminars","1");
		MockClassroom testClass = new MockClassroom("delete students in seminars",currentClassroom,"1");
		
		testClass.classroomAddSeminar();
		
		List <Seminar> seminars = testClass.getClassroomSeminars();
		Seminar test1 = seminars.get(0);
		testClass.classroomAddStudent("ipopk15@freeuni.edu.ge");
		testClass.classroomAddStudent("gkhos15@freeuni.edu.ge");
		
		assertFalse(test1.removeStudentFromSeminar("notEmail"));
		test1.addStudentToSeminar("gkhos15@freeuni.edu.ge");
		test1.addStudentToSeminar("ipopk15@freeuni.edu.ge");
		assertTrue(test1.removeStudentFromSeminar("ipopk15@freeuni.edu.ge"));
//		assertTrue(test1.seminarContainsStudent("gkhos15@freeuni.edu.ge"));
//		assertFalse(test1.seminarContainsStudent("ipopk15@freeuni.edu.ge"));
	}
	
	@Test
	public void test4Seminarist(){
		String currentClassroom = db.classroomDB.addClassroom("delete students in seminars","1");
		MockClassroom testClass = new MockClassroom("delete students in seminars",currentClassroom,"1");
		
		testClass.classroomAddSeminar();
		testClass.classroomAddSeminar();
		testClass.classroomAddSeminarist("s.gvinepadze@freeuni.edu.ge");
		testClass.classroomAddSeminarist("n.begiashvili@freeuni.edu.ge");
		List <Seminar> seminars = testClass.getClassroomSeminars();
		Seminar test1 = seminars.get(0);
		Seminar test2 = seminars.get(1);
		
		assertTrue(test1.setSeminarist("s.gvinepadze@freeuni.edu.ge"));
		assertFalse(test2.setSeminarist("notEmail@mail.ru"));
		assertFalse(test2.removeSeminarist());
		assertFalse(test1.setSeminarist("n.begiashvili@freeuni.edu.ge"));
		assertTrue(test1.removeSeminarist());
		assertTrue(test1.setSeminarist("n.begiashvili@freeuni.edu.ge"));
		assertFalse(test1.setSeminarist("s.gvinepadze@freeuni.edu.ge"));
		assertFalse(test2.setSeminarist("n.begiashvili@freeuni.edu.ge"));
		assertTrue(test2.setSeminarist("s.gvinepadze@freeuni.edu.ge"));
	}
	
	
}
