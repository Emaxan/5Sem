package edu.iba.sh.bean;

public class Group {
	
	private String groupNumber;
	private double avgMark;
	
	
	
	public Group() {
		super();
	}
	
	
	public Group(String group_number, double avg_mark) {
		super();
		this.groupNumber = group_number;
		this.avgMark = avg_mark;
	}


	public String getGroupNumber() {
		return groupNumber;
	}


	public void setGroupNumber(String groupNumber) {
		this.groupNumber = groupNumber;
	}


	public double getAvgMark() {
		return avgMark;
	}


	public void setAvgMark(double avgMark) {
		this.avgMark = avgMark;
	}


	@Override
	public String toString() {
		return "Group [groupNumber=" + groupNumber + ", avgMark=" + avgMark + "]";
	}

	
}
