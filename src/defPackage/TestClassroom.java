package defPackage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class TestClassroom {
	
	private MockDBConnection db = new MockDBConnection();
	private  MockClassroom oop ; 
	private MockClassroom pp;
	private MockClassroom meth;
	
	@Before
	public void bla(){
		//creating classes;
		oop = new MockClassroom ("OOP" , db.addClassroom("OOP"));
		pp = new MockClassroom ("Paradigms", db.addClassroom("Paradigms"));
		meth = new MockClassroom ("Methodologies", db.addClassroom("Methodologies"));
		
	}
	
	
	@Test
	public void test1init() {
		//Just add people into database so we can use them
		
		db.addPerson("irakli", "popkhadze", "ipopk15@freeuni.edu.ge");
		db.addPerson("giorgi", "khosroshvili", "gkhos15@freeuni.edu.ge");
		db.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge");
		db.addPerson("nika", "begiashvili", "n.begiashvili@freeuni.edu.ge");
		db.addPerson("giorgi", "cercvadze", "gitser15@freeuni.edu.ge");
		db.addPerson("aleko", "cxovrebovi", "acxcx15@freeuni.edu.ge");
		db.addPerson("mari", "berishvili", "mberi15@freeuni.edu.ge");
		
		//check if class names are correct;
		assertEquals(oop.getClassroomName(), "OOP");
		
		assertEquals(pp.getClassroomName(), "Paradigms");
			
		assertEquals(meth.getClassroomName(), "Methodologies");
		
	}
	
	/**
	 * Testing add methods of classroom; 
	 */
	@Test
	public void test2AddingToClassrom() {
		assertTrue(oop.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge"));
		assertTrue(pp.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge"));
		assertTrue(meth.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge"));
		
		assertTrue(oop.classroomAddSeminarist("n.begiashvili@freeuni.edu.ge"));
		assertTrue(pp.classroomAddSeminarist("n.begiashvili@freeuni.edu.ge"));
		
		assertTrue(meth.classroomAddSectionLeader("ipopk15@freeuni.edu.ge"));
		assertTrue(meth.classroomAddSectionLeader("gkhos15@freeuni.edu.ge"));
		assertTrue(meth.classroomAddSectionLeader("acxcx15@freeuni.edu.ge"));
		
		assertFalse(meth.classroomAddSectionLeader("ipopk15@freeuni.edu.ge"));
		assertFalse(meth.classroomAddSectionLeader("gkhos15@freeuni.edu.ge"));
		
		assertFalse(pp.classroomAddLecturer("baqara@freeuni.edu.ge"));
		assertFalse(pp.classroomAddSeminarist("baqara@freeuni.edu.ge"));
		assertFalse(oop.classroomAddSectionLeader("baquna@freeuni.edu.ge"));
		assertFalse(meth.classroomAddStudent("xosrika@freeuni.edu.ge"));
	}
	
	/**
	 * Testing deleting and exist method in classroom;
	 */
	@Test
	public void test3PersonsDelete() {
		oop.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge");
		pp.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge");
		meth.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge");
		oop.classroomAddSeminarist("n.begiashvili@freeuni.edu.ge");
		meth.classroomAddSectionLeader("ipopk15@freeuni.edu.ge");
		meth.classroomAddSectionLeader("gkhos15@freeuni.edu.ge");
		
		
		assertTrue(oop.classroomSeminaristExists("n.begiashvili@freeuni.edu.ge"));
		assertFalse(oop.classroomDeleteSeminarist("ipopk15@freeuni.edu.ge"));
		assertTrue(oop.classroomDeleteSeminarist("n.begiashvili@freeuni.edu.ge"));
		assertFalse(oop.classroomSeminaristExists("n.begiashvili@freeuni.edu.ge"));
		
		assertTrue(oop.classroomLecturerExists("s.gvinepadze@freeuni.edu.ge"));
		assertFalse(oop.classroomDeleteLecturer("n.begiashvili@freeuni.edu.ge"));
		assertTrue(oop.classroomDeleteLecturer("s.gvinepadze@freeuni.edu.ge"));
		assertFalse(oop.classroomLecturerExists("s.gvinepadze@freeuni.edu.ge"));
		assertTrue(pp.classroomLecturerExists("s.gvinepadze@freeuni.edu.ge"));
		
		assertTrue(meth.classroomSectionLeaderExists("ipopk15@freeuni.edu.ge"));
		assertTrue(meth.classroomDeleteSectionLeader("ipopk15@freeuni.edu.ge"));
		assertTrue(meth.classroomDeleteSectionLeader("gkhos15@freeuni.edu.ge"));
		assertFalse(meth.classroomSectionLeaderExists("gkhos15@freeuni.edu.ge"));
		assertFalse(meth.classroomSectionLeaderExists("ipopk15@freeuni.edu.ge"));
		
		assertFalse(pp.classroomDeleteSectionLeader("vigaca@freeuni.edu.ge"));

		db.addPerson("tpp", "tpp", "tpp@freeuni.edu.ge");
		db.addPerson("kpp", "kpp", "kpp@freeuni.edu.ge");
		db.addPerson("unnamed", "unnamed", "unnamed@freeuni.edu.ge");
		
		assertTrue(oop.classroomAddStudent("tpp@freeuni.edu.ge"));
		assertTrue(oop.classroomAddStudent("kpp@freeuni.edu.ge"));
		assertTrue(oop.classroomAddStudent("unnamed@freeuni.edu.ge"));
		
		assertTrue(oop.classroomStudentExists("tpp@freeuni.edu.ge"));
		assertTrue(oop.classroomStudentExists("kpp@freeuni.edu.ge"));
		assertTrue(oop.classroomStudentExists("unnamed@freeuni.edu.ge"));
		assertTrue(oop.classroomDeleteStudent("tpp@freeuni.edu.ge"));
		assertTrue(oop.classroomDeleteStudent("kpp@freeuni.edu.ge"));
		assertTrue(oop.classroomDeleteStudent("unnamed@freeuni.edu.ge"));
		assertFalse(oop.classroomStudentExists("tpp@freeuni.edu.ge"));
		assertFalse(oop.classroomStudentExists("kpp@freeuni.edu.ge"));
		assertFalse(oop.classroomStudentExists("unnamed@freeuni.edu.ge"));
	}
	
	/**
	 * Testing method of classroom
	 * which return persons list (like students, section leaders and so on);
	 */
	@Test
	public void test4ListGetters() {
		String currentClassroom = db.addClassroom("just to test");
		MockClassroom testClass = new MockClassroom("just to test",currentClassroom);
				
		//Testing students' list
		ArrayList <Person> realStudents = new ArrayList <Person>();
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = String.valueOf(ch);
			String surname = String.valueOf(ch);
			String email = String.valueOf(ch) + "@freeuni.edu.ge";

			db.addPerson(name, surname, email);
			db.addStudent(email, currentClassroom);
			
			realStudents.add(new Person(name, surname, email, "3"));
		}
		
		List <Person> students = testClass.getClassroomStudents();
		assertEquals(students, realStudents);
		
		//Testing lecturers' list
		ArrayList <Person> realLecturers = new ArrayList <Person>(); 
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "a" + String.valueOf(ch);
			String surname = "a" + String.valueOf(ch);
			String email = "a" + String.valueOf(ch) + "@freeuni.edu.ge";

			db.addPerson(name, surname, email);
			db.addLecturer(email, currentClassroom);
			
			realLecturers.add(new Person(name, surname, email, "2"));
		}
		List <Person> lecturers = testClass.getClassroomLecturers();
		assertEquals(lecturers, realLecturers);

		//Testing seminarists' list
		ArrayList <Person> realSeminarists = new ArrayList <Person>(); 
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "b" + String.valueOf(ch);
			String surname = "b" + String.valueOf(ch);
			String email = "b" + String.valueOf(ch) + "@freeuni.edu.ge";

			db.addPerson(name, surname, email);
			db.addSeminarist(email, currentClassroom);
			
			realSeminarists.add(new Person(name, surname, email, "2"));
		}
		List <Person> seminarists = testClass.getClassroomSeminarists();
		assertEquals(seminarists, realSeminarists);

		//Testing section leaders' list;
		ArrayList <Person> realSectionLeaders = new ArrayList <Person>(); 
		for (char ch = 'a'; ch <= 'z'; ch++) {
			String name = "c" + String.valueOf(ch);
			String surname = "c" + String.valueOf(ch);
			String email = "c" + String.valueOf(ch) + "@freeuni.edu.ge";

			db.addPerson(name, surname, email);
			db.addSectionLeader(email, currentClassroom);
			
			realSectionLeaders.add(new Person(name, surname, email, "2"));
		}
		List <Person> sectionLeaders = testClass.getClassroomSectionLeaders();
		assertEquals(sectionLeaders, realSectionLeaders);				
	}
		
	/**
	 * This method tests sections and seminars in the classroom
	 * checked: add/delete and list methods for this objects;
	 */
	@Test
	public void test5SectionAndSeminars(){
		String classID = db.addClassroom("testGroups");
		MockClassroom mockClass = new MockClassroom("testGoups", classID);
		
		assertTrue(mockClass.classroomAddSection("section1"));
		assertFalse(mockClass.classroomAddSection("section1"));
		assertTrue(mockClass.classroomSectionExists("section1"));
		assertFalse(mockClass.classroomSectionExists("section2"));
		assertTrue(mockClass.classroomAddSection("section2"));	
		assertTrue(mockClass.classroomSectionExists("section2"));
		assertTrue(mockClass.classroomAddSection("section3"));
		assertTrue(mockClass.classroomAddSection("deleteSection"));
		assertTrue(mockClass.classroomDeleteSection("deleteSection"));
		assertFalse(mockClass.classroomDeleteSection("deleteSection"));
				
		ArrayList<Section> sections = new ArrayList<Section>();
		
		sections.add(new Section("random","section1",classID));
		sections.add(new Section("random","section2",classID));
		sections.add(new Section("random","section3",classID));
		
		assertEquals(mockClass.getClassroomSections(),sections);
				
		assertTrue(mockClass.classroomAddSeminar("seminar1"));
		assertFalse(mockClass.classroomAddSeminar("seminar1"));
		assertTrue(mockClass.classroomSeminarExists("seminar1"));
		assertFalse(mockClass.classroomSeminarExists("seminar2"));
		assertTrue(mockClass.classroomAddSeminar("seminar2"));
		assertTrue(mockClass.classroomSeminarExists("seminar2"));
		assertTrue(mockClass.classroomAddSeminar("seminar3"));
		assertTrue(mockClass.classroomAddSeminar("deleteSection"));
		assertTrue(mockClass.classroomDeleteSeminar("deleteSection"));
		assertFalse(mockClass.classroomDeleteSeminar("deleteSection"));
		
		ArrayList<Seminar> seminars = new ArrayList<Seminar>();
		
		seminars.add(new Seminar("random","seminar1",classID));
		seminars.add(new Seminar("random","seminar2",classID));
		seminars.add(new Seminar("random","seminar3",classID));
						
		assertEquals(mockClass.getClassroomSeminars(),seminars);
		
	}
	
	/**
	 * This method tests adding people in the groups;
	 * If they work add methods can be used;
	 */
	@Test
	public void test6AddGroupsAndPeople(){
		String classID = db.addClassroom("testGroupsAndPeople");
		MockClassroom mockClass = new MockClassroom("testGoupsAndPeople", classID);
		
		mockClass.classroomAddSection("section1");
		mockClass.classroomAddSection("section2");
		
		mockClass.classroomAddSeminar("seminar1");
		mockClass.classroomAddSeminar("seminar2");
		
		mockClass.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge");
		mockClass.classroomAddSectionLeader("gkhos15@freeuni.edu.ge");
		mockClass.classroomAddSectionLeader("ipopk15@freeuni.edu.ge");
		mockClass.classroomAddSeminarist("n.begiashvili@freeuni.edu.ge");
		mockClass.classroomAddStudent("gitser15@freeuni.edu.ge");
		
		assertFalse(mockClass.classroomAddSeminaristToSeminar("notseminar", "n.begiashvili@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddSeminaristToSeminar("seminar1", "vigaca@mail.re"));
		assertFalse(mockClass.classroomAddSeminaristToSeminar("seminar1", "gitser15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddSeminaristToSeminar("seminar1", "s.gvinepadze@freeuni.edu.ge"));
		assertTrue(mockClass.classroomAddSeminaristToSeminar("seminar1", "n.begiashvili@freeuni.edu.ge"));
		assertTrue(mockClass.classroomAddSeminaristToSeminar("seminar2", "n.begiashvili@freeuni.edu.ge"));
		
		assertFalse(mockClass.classroomAddSectionLeaderToSection("notsection", "gkhos15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddSectionLeaderToSection("section1", "vigaca@mail.re"));
		assertFalse(mockClass.classroomAddSectionLeaderToSection("section1", "gitser15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddSectionLeaderToSection("section1", "s.gvinepadze@freeuni.edu.ge"));
		assertTrue(mockClass.classroomAddSectionLeaderToSection("section1", "gkhos15@freeuni.edu.ge"));
		assertTrue(mockClass.classroomAddSectionLeaderToSection("section2", "ipopk15@freeuni.edu.ge"));
		
		assertFalse(mockClass.classroomAddStudentToSection("section1", "acxcx15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddStudentToSection("section1", "ipopk15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddStudentToSection("section1", "raraca@mail.ru"));
		assertTrue(mockClass.classroomAddStudentToSection("section1", "gitser15@freeuni.edu.ge"));
		
		assertFalse(mockClass.classroomAddStudentToSeminar("seminar1", "acxcx15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddStudentToSeminar("seminar1", "gkhos15@freeuni.edu.ge"));
		assertFalse(mockClass.classroomAddStudentToSeminar("seminar1", "raraca@mail.ru"));
		assertTrue(mockClass.classroomAddStudentToSeminar("seminar1", "gitser15@freeuni.edu.ge"));
		
	}
	
	
}
