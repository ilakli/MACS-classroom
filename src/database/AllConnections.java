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
	public AssignmentGradeDB assignmentGradeDB;
	public FunctionDB functionDB;
	public PositionDB positionDB;
	public CategoryDB categoryDB;
	public StudentAssignmentDB studentAssignmentDB;
	public DBConnection db;
	public DriveDB driveDB;
	
	public AllConnections() {
		db = new DBConnection();
		personDB = new PersonDB(this);
		lecturerDB = new LecturerDB(this);
		materialDB = new MaterialDB(this);
		postDB = new PostDB(this);
		sectionDB = new SectionDB(this);
		sectionLeaderDB = new SectionLeaderDB(this);
		seminarDB = new SeminarDB(this);
		seminaristDB = new SeminaristDB(this);
		commentDB = new CommentDB(this);
		studentDB = new StudentDB(this);
		assignmentDB = new AssignmentDB(this);
		functionDB = new FunctionDB(this);
		positionDB = new PositionDB(this);
		categoryDB = new CategoryDB(this);
		studentAssignmentDB = new StudentAssignmentDB(this);
		assignmentGradeDB = new AssignmentGradeDB(this);
		classroomDB = new ClassroomDB(this);
		driveDB = new DriveDB(this);
	}
}
