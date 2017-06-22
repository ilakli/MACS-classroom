package defPackage;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import database.AllConnections;

@FixMethodOrder (MethodSorters.NAME_ASCENDING)
public class TestSection {
	
	private AllConnections db = new AllConnections();
	
	
	@Test
	public void test0init() {
		//Just add people into database so we can use them
		
		db.personDB.addPerson("irakli", "popkhadze", "ipopk15@freeuni.edu.ge");
		db.personDB.addPerson("giorgi", "khosroshvili", "gkhos15@freeuni.edu.ge");
		db.personDB.addPerson("shota", "gvinepadze", "s.gvinepadze@freeuni.edu.ge");
		db.personDB.addPerson("nika", "begiashvili", "n.begiashvili@freeuni.edu.ge");
		db.personDB.addPerson("giorgi", "cercvadze", "gitser15@freeuni.edu.ge");
		db.personDB.addPerson("aleko", "cxovrebovi", "acxcx15@freeuni.edu.ge");
		db.personDB.addPerson("mari", "berishvili", "mberi15@freeuni.edu.ge");
	}
	
	
	@Test
	public void test1CreateSection(){
		String currentClassroom = db.classroomDB.addClassroom("test section");
		MockClassroom testClass = new MockClassroom("test section",currentClassroom);
		testClass.classroomAddSection();
		testClass.classroomAddSection();		
		
		List <Section> sections = testClass.getClassroomSections();
		
		Section test1 = sections.get(0);
		Section test2 = sections.get(1);
		
		assertEquals(test1.getClassroomId(), currentClassroom);	
		assertEquals(test2.getClassroomId(), currentClassroom);
		assertEquals(test1.getSectionN(), 0);	
		assertEquals(test2.getSectionN(), 1);
		assertEquals(test1.getSectionLeader(),null);	
		assertEquals(test2.getSectionLeader(),null);
		assertEquals(test1.getSectionStudents(), new ArrayList<Person>());
		assertEquals(test2.getSectionStudents(), new ArrayList<Person>());
	}
	
	@Test
	public void test2addPeopleToSection(){
		String currentClassroom = db.classroomDB.addClassroom("add people in sections");
		MockClassroom testClass = new MockClassroom("add people in sections",currentClassroom);
		String notThisOne = db.classroomDB.addClassroom("another one");
		MockClassroom otherOne = new MockClassroom("another one",notThisOne);
		testClass.classroomAddSection();
		testClass.classroomAddSection();		
		
		List <Section> sections = testClass.getClassroomSections();
		
		Section test1 = sections.get(0);
		Section test2 = sections.get(1);
		
		testClass.classroomAddStudent("gkhos15@freeuni.edu.ge");
		testClass.classroomAddSectionLeader("n.begiashvili@freeuni.edu.ge");
		testClass.classroomAddSectionLeader("acxcx15@freeuni.edu.ge");
		otherOne.classroomAddStudent("ipopk15@freeuni.edu.ge");
		otherOne.classroomAddLecturer("s.gvinepadze@freeuni.edu.ge");		
		
		assertTrue(test1.addStudentToSection("gkhos15@freeuni.edu.ge"));
		assertFalse(test1.addStudentToSection("gkhos15@freeuni.edu.ge"));
		assertFalse(test1.addStudentToSection("n.begiashvili@freeuni.edu.ge"));
		assertFalse(test2.addStudentToSection("ipopk15@freeuni.edu.ge"));
		assertFalse(test2.setSectionLeader("ipopk15@freeuni.edu.ge"));
		assertFalse(test2.addStudentToSection("GauqmebuliaGauqmebulia"));
		assertFalse(test1.setSectionLeader("gkhos15@freeuni.edu.ge"));
		assertTrue(test1.setSectionLeader("n.begiashvili@freeuni.edu.ge"));
		assertFalse(test2.setSectionLeader("n.begiashvili@freeuni.edu.ge"));	
		assertFalse(test2.setSectionLeader("s.gvinepadze@freeuni.edu.ge"));
		assertTrue(test2.setSectionLeader("acxcx15@freeuni.edu.ge"));
		assertEquals(testClass.getClassroomSectionLeaders().get(0), test1.getSectionLeader());
		assertEquals(testClass.getClassroomSectionLeaders().get(1), test2.getSectionLeader());
		
	}
	
	@Test
	public void test3DeleteStudents(){
		String currentClassroom = db.classroomDB.addClassroom("delete students in sections");
		MockClassroom testClass = new MockClassroom("delete students in sections",currentClassroom);
		
		testClass.classroomAddSection();
		
		List <Section> sections = testClass.getClassroomSections();
		Section test1 = sections.get(0);
		testClass.classroomAddStudent("ipopk15@freeuni.edu.ge");
		testClass.classroomAddStudent("gkhos15@freeuni.edu.ge");
		
		assertFalse(test1.removeStudentFromSection("notEmail"));
		test1.addStudentToSection("gkhos15@freeuni.edu.ge");
		test1.addStudentToSection("ipopk15@freeuni.edu.ge");
		assertTrue(test1.removeStudentFromSection("ipopk15@freeuni.edu.ge"));
//		assertTrue(test1.seminarContainsStudent("gkhos15@freeuni.edu.ge"));
//		assertFalse(test1.seminarContainsStudent("ipopk15@freeuni.edu.ge"));
	}
	
	@Test
	public void test4SectionLeader(){
		String currentClassroom = db.classroomDB.addClassroom("delete students in sections");
		MockClassroom testClass = new MockClassroom("delete students in sections",currentClassroom);
		
		testClass.classroomAddSection();
		testClass.classroomAddSection();
		testClass.classroomAddSectionLeader("s.gvinepadze@freeuni.edu.ge");
		testClass.classroomAddSectionLeader("n.begiashvili@freeuni.edu.ge");
		List <Section> sections = testClass.getClassroomSections();
		Section test1 = sections.get(0);
		Section test2 = sections.get(1);
		
		assertTrue(test1.setSectionLeader("s.gvinepadze@freeuni.edu.ge"));
		assertFalse(test2.setSectionLeader("notEmail@mail.ru"));
		assertFalse(test2.removeSectionLeader());
		assertFalse(test1.setSectionLeader("n.begiashvili@freeuni.edu.ge"));
		assertTrue(test1.removeSectionLeader());
		assertTrue(test1.setSectionLeader("n.begiashvili@freeuni.edu.ge"));
		assertFalse(test1.setSectionLeader("s.gvinepadze@freeuni.edu.ge"));
		assertFalse(test2.setSectionLeader("n.begiashvili@freeuni.edu.ge"));
		assertTrue(test2.setSectionLeader("s.gvinepadze@freeuni.edu.ge"));
	}
	
	
}
