package database;

public class AllConnections {
	public ClassroomDB classroomDB;
	public CommentDB commentDB;
	public LecturerDB lecturerDB;
	public MaterialDB materialDB;
	public PersonDB personDB;
	public PostDB postDB;
	public SectionDB sectionDB;
	public SectionLeaderDB sectionLeaderDB;
	public SeminarDB seminarDB;
	public SeminaristDB seminaristDB;
	public StudentDB studentDB;
	public AssignmentDB assignmentDB;
	public FunctionDB functionDB;
	public PositionDB positionDB;
	public CategoryDB categoryDB;
	
	public AllConnections() {
		classroomDB = new ClassroomDB();
		commentDB = new CommentDB();
		lecturerDB = new LecturerDB();
		materialDB = new MaterialDB();
		personDB = new PersonDB();
		postDB = new PostDB();
		sectionDB = new SectionDB();
		sectionLeaderDB = new SectionLeaderDB();
		seminarDB = new SeminarDB();
		seminaristDB = new SeminaristDB();
		studentDB = new StudentDB();
		assignmentDB = new AssignmentDB();
		functionDB = new FunctionDB();
		positionDB = new PositionDB();
		categoryDB = new CategoryDB();
	}
}
