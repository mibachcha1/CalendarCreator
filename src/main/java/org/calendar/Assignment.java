package org.calendar;

public class Assignment {
	private String className;
	private String assignmentName;
	private String dueDate;
	
	public Assignment(String pClassName, String pAssignmentName, String pDueDate) {
		className = pClassName;
		assignmentName = pAssignmentName;
		dueDate = pDueDate;
	}
	
	public String getClassName() {
		return className;
	}
	
	public String getAssignmentName() {
		return assignmentName;
	}
	
	public String getDueDate() {
		return dueDate;
	}

	
	
}
