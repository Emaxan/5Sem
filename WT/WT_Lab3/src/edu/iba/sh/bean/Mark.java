package edu.iba.sh.bean;


public class Mark {

	private int id;
	private int studyId;
	private int studentId;
	private String date;
	private int professorId;
	private int mark;
	private String comments;
	
	public Mark() {
		// TODO Auto-generated constructor stub
	}

	public Mark(int id, int study_id, int student_id, String date, int id_professor, int mark) {
		super();
		this.id = id;
		this.studyId = study_id;
		this.studentId = student_id;
		this.date = date;
		this.professorId = id_professor;
		this.mark = mark;
	}


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getStudyId() {
		return studyId;
	}

	public void setStudyId(int studyId) {
		this.studyId = studyId;
	}

	public int getStudentId() {
		return studentId;
	}

	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public int getMark() {
		return mark;
	}

	public void setMark(int mark) {
		this.mark = mark;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	@Override
	public String toString() {
		return "Marks [id=" + id + ", study_id=" + studyId + ", student_id=" + studentId + ", date=" + date
				+ ", id_professor=" + professorId + ", mark=" + mark + ", comments=" + comments + "]";
	}

}
