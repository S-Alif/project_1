package application;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class DataTable {
	private SimpleStringProperty subjects;
	private SimpleIntegerProperty marks;
	private SimpleDoubleProperty grade;
	private SimpleStringProperty LetterGrade;
	
	/* constructor */
	public DataTable(String subjects, int marks, double grade, String LetterGrade) {
		this.subjects = new SimpleStringProperty(subjects);
		this.marks = new SimpleIntegerProperty(marks);
		this.grade = new SimpleDoubleProperty(grade);
		this.LetterGrade = new SimpleStringProperty(LetterGrade);
	}
	
	/* get methods and set methods */
	public String getSubjects() {
		return subjects.get();
	}
	public void setSubjects(String subjects) {
		this.subjects = new SimpleStringProperty(subjects);
	}
	
	
	public int getMarks() {
		return marks.get();
	}
	public void setMarks(int marks) {
		this.marks = new SimpleIntegerProperty(marks);
	}
	
	
	public double getGrade() {
		return grade.get();
	}
	public void setGrade(double grade) {
		this.grade = new SimpleDoubleProperty(grade);
	}
	
	
	
	public void setLetterGrade(String letterGrade) {
		this.LetterGrade = new SimpleStringProperty(letterGrade);
	}
	public String getLetterGrade() {
		return LetterGrade.get();
	}
}
