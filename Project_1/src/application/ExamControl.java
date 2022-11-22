package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;

public class ExamControl {
	
	@FXML
	private TextField id_box, sub1_box, sub2_box, sub3_box, sub4_box, sub5_box, sub6_box;
	@FXML
	private ComboBox<String> dept_box, trimester_box;
	@FXML
	private Label std_info_label, show_name, show_id, show_sub1, show_sub2, show_sub3, show_sub4, show_sub5, show_sub6, preview_marks_label;
	@FXML
	private Line mid_line, mid_line2;
	@FXML
	private Label sub_name1, sub_name2, sub_name3, sub_name4, sub_name5, sub_name6;
	
	/* comboBox data */
	ObservableList<String> depts = FXCollections.observableArrayList("CSE", "EEE", "JRN", "ENG", "BTE");
	ObservableList<String> trimesters = FXCollections.observableArrayList("SPRING-22", "SUMMER-22", "FALL-22");
	
	@FXML
	public void initialize() {
		dept_box.setItems(depts);
		trimester_box.setItems(trimesters);
	}
	
	/* variables for this program */
	int[] subject = new int[50];
	double[] grade = new double[50];
	String[] sub_names = new String[50];
	double totalGrade, cgpa;
	int id, i, batch;
	String name, dept, trimester ,n, letter_grade;
	
	/* search action */
	public void search(ActionEvent event) {
		
		/* storing data in variables for query */
		dept = dept_box.getValue();
		trimester = trimester_box.getValue().toLowerCase();
		id = Integer.parseInt(id_box.getText());
		batch = Integer.parseInt(id_box.getText().substring(0, 2));
		
		/* database action */
		try {
			
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			/* selecting details for info */
			String query = "SELECT `firstName`, `lastName`, `id`, `department` FROM `batch_"+dept+""+batch+"` WHERE `id` = ? AND `department` = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setString(2, dept);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next() == false) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ERROR");
				alert.setHeaderText("INVALID DATA");
				alert.setContentText("Student Data not found");
				alert.showAndWait();
			}else {
				name = ""+rs.getString("firstName")+" "+rs.getString("lastName");
				id = rs.getInt("id");
			}
			
			/* showing the search result */
			mid_line.setOpacity(1);
			std_info_label.setOpacity(1);
			show_name.setText("Name : "+name);
			show_id.setText("ID : "+dept+" "+id);
			
			/* subject names */	
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		/* getting subject names */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			for(i = 0; i < 6; i++) {
				
				String query2 = "SELECT `sub"+(i+1)+"` FROM subjects WHERE `department` = ? AND `batch` = ? AND `trimester` = ?";
				PreparedStatement ps2 = con.prepareStatement(query2);
				
				ps2.setString(1, dept);
				ps2.setInt(2, batch);
				ps2.setString(3, trimester);
				
				ResultSet rs2 = ps2.executeQuery();
				
				if(rs2.next() == false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERROR");
					alert.setHeaderText("INVALID DATA");
					alert.setContentText("Please Enter Required Data Perfectly");
					alert.showAndWait();
					break;
				}else {
					sub_names[i] = rs2.getString("sub"+(i+1));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		/* showing subject names */
		sub_name1.setText(""+sub_names[0]+" :");
		sub_name2.setText(""+sub_names[1]+" :");
		sub_name3.setText(""+sub_names[2]+" :");
		sub_name4.setText(""+sub_names[3]+" :");
		sub_name5.setText(""+sub_names[4]+" :");
		sub_name6.setText(""+sub_names[5]+" :");
		
	}
	
	/* marks preview */
	public void marks_preview(ActionEvent event) {
		
		/* storing marks to show  */
		subject[0] = Integer.parseInt(sub1_box.getText());
		subject[1] = Integer.parseInt(sub2_box.getText());
		subject[2] = Integer.parseInt(sub3_box.getText());
		subject[3] = Integer.parseInt(sub4_box.getText());
		subject[4] = Integer.parseInt(sub5_box.getText());
		subject[5] = Integer.parseInt(sub6_box.getText());
		
		/* showing marks */
		mid_line2.setOpacity(1);
		preview_marks_label.setOpacity(1);
		show_sub1.setText(" "+sub_names[0]+" : "+subject[0]);
		show_sub2.setText(" "+sub_names[1]+" : "+subject[1]);
		show_sub3.setText(" "+sub_names[2]+" : "+subject[2]);
		show_sub4.setText(" "+sub_names[3]+" : "+subject[3]);
		show_sub5.setText(" "+sub_names[4]+" : "+subject[4]);
		show_sub6.setText(" "+sub_names[5]+" : "+subject[5]);
	}
	
	/* submit result */
	public void submit(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Marks Submition");
		alert.setHeaderText(null);
		alert.setContentText("Submit Student Marks for "+trimester+" ?");

		Optional<ButtonType> result = alert.showAndWait();
		
		if (result.get() == ButtonType.CANCEL){
			
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Marks Submition Stopped");
			alert2.setHeaderText(null);
			alert2.setContentText("Student marks submition for "+trimester+" has been cancelled");

			alert2.showAndWait();
			
		}
		else {
			
			
			/* calculate result and get grade */
			for(i = 0; i < 6; i++) {
				
				if(subject[i] >= 80) {
					grade[i] = 4.00;
				}
				else if(subject[i] >= 75 && subject[i] < 80) {
					grade[i] = 3.75;
				}
				else if(subject[i] >= 70 && subject[i] < 75) {
					grade[i] = 3.50;
				}
				else if(subject[i] >= 65 && subject[i] < 70) {
					grade[i] = 3.25;
				}
				else if(subject[i] >= 60 && subject[i] < 65) {
					grade[i] = 3.0;
				}
				else if(subject[i] >= 55 && subject[i] < 60) {
					grade[i] = 2.75;
				}
				else if(subject[i] >= 50 && subject[i] < 55) {
					grade[i] = 2.50;
				}
				else if(subject[i] >= 45 && subject[i] < 50) {
					grade[i] = 2.25;
				}
				else if(subject[i] >= 40 && subject[i] < 45) {
					grade[i] = 2.00;
				}
				else {
					grade[i] = 0.00;
				}
				
				totalGrade = totalGrade + grade[i];
			}
			
			/* calculate cgpa avg */
			cgpa = totalGrade / 6;
			
			/* letter grade */
			if(cgpa == 4.00) {
				letter_grade = "A+";
			}
			else if(cgpa < 4.00 && cgpa >= 3.75) {
				letter_grade = "A";
			}
			else if(cgpa < 3.75 && cgpa >= 3.50) {
				letter_grade = "A-";
			}
			else if(cgpa < 3.50 && cgpa >= 3.25) {
				letter_grade = "B+";
			}
			else if(cgpa < 3.25 && cgpa >= 3.00) {
				letter_grade = "B";
			}
			else if(cgpa < 3.00 && cgpa >= 2.75) {
				letter_grade = "B-";
			}
			else if(cgpa < 2.75 && cgpa >= 2.50) {
				letter_grade = "C+";
			}
			else if(cgpa < 2.50 && cgpa >= 2.25) {
				letter_grade = "C";
			}
			else {
				letter_grade = "D";
			}
			
			/* submit to database */
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");			
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
				
				for(i = 0; i < 6; i++) {
					String query = "UPDATE `"+dept+""+batch+"_result` SET sub"+(i+1)+" = ? WHERE `id` = ? AND `department` = ? AND `trimester` = ?";
					PreparedStatement ps = con.prepareStatement(query);
					
					ps.setInt(1, subject[i]);
					ps.setInt(2, id);
					ps.setString(3, dept);
					ps.setString(4, trimester);
					
					ps.executeUpdate();
				}
				
				String query2 = "UPDATE `"+dept+""+batch+"_result` SET `totalLetterGrade` = ?, `totalGrade` = ? WHERE `id` = ? AND `department` = ? AND `trimester` = ?";
				PreparedStatement ps2 = con.prepareStatement(query2);
				
				ps2.setString(1, letter_grade);
				ps2.setDouble(2, cgpa);
				ps2.setInt(3, id);
				ps2.setString(4, dept);
				ps2.setString(5, trimester);
				
				ps2.executeUpdate();
				
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("SUCCESS");
				alert2.setHeaderText("ALL DATA SAVED");
				alert2.setContentText("Student marks has been saved");
				alert2.showAndWait();
				
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}		
	}
	
	/* enter new data */
	public void new_data(ActionEvent event) {
		
		id_box.setText("");
		dept_box.valueProperty().set(null);
		trimester_box.valueProperty().set(null);
		
		mid_line.setOpacity(0);
		std_info_label.setOpacity(0);
		show_name.setText(null);
		show_id.setText(null);
		
		sub1_box.setText(null);
		sub2_box.setText(null);
		sub3_box.setText(null);
		sub4_box.setText(null);
		sub5_box.setText(null);
		sub6_box.setText(null);
		
		mid_line2.setOpacity(0);
		preview_marks_label.setOpacity(0);
		show_sub1.setText(null);
		show_sub2.setText(null);
		show_sub3.setText(null);
		show_sub4.setText(null);
		show_sub5.setText(null);
		show_sub6.setText(null);
	}
}
