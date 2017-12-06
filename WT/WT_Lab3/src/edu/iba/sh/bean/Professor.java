package edu.iba.sh.bean;

public class Professor {
	
	private int id;
	private String firstName;
	private String secondName;
	private String fatherName;
	private String birthDate;
	private double avgMark;
	
	public Professor() {
		// TODO Auto-generated constructor stub
	}

	public Professor(int id, String first_name, String second_name, String father_name, String birth_date,
			double avg_mark) {
		super();
		this.id = id;
		this.firstName = first_name;
		this.secondName = second_name;
		this.fatherName = father_name;
		this.birthDate = birth_date;
		this.avgMark = avg_mark;
	}

	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSecondName() {
		return secondName;
	}

	public void setSecondName(String secondName) {
		this.secondName = secondName;
	}

	public String getFatherName() {
		return fatherName;
	}

	public void setFatherName(String fatherName) {
		this.fatherName = fatherName;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public double getAvgMark() {
		return avgMark;
	}

	public void setAvgMark(double avgMark) {
		this.avgMark = avgMark;
	}

	@Override
	public String toString() {
		return "Professor [id=" + id + ", first_name=" + firstName + ", second_name=" + secondName + ", father_name="
				+ fatherName + ", birth_date=" + birthDate + ", avg_mark=" + avgMark + "]";
	}

}
