package edu.iba.sh.bean;

public class Study {
	private int id;
	private String name;
	private double hours;
	private int professorId;
	private double avgMark;

	public Study() {
		// TODO Auto-generated constructor stub
	}

	public Study(int id, String name, double hours, int professor_id, double avg_mark) {
		super();
		this.id = id;
		this.name = name;
		this.hours = hours;
		this.professorId = professor_id;
		this.avgMark = avg_mark;
	}



	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getHours() {
		return hours;
	}

	public void setHours(double hours) {
		this.hours = hours;
	}

	public int getProfessorId() {
		return professorId;
	}

	public void setProfessorId(int professorId) {
		this.professorId = professorId;
	}

	public double getAvgMark() {
		return avgMark;
	}

	public void setAvgMark(double avgMark) {
		this.avgMark = avgMark;
	}

	@Override
	public String toString() {
		return "Study [id=" + id + ", name=" + name + ", hours=" + hours + ", professor_id=" + professorId
				+ ", avg_mark=" + avgMark + "]";
	}

}
