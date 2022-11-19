package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert.AlertType;

public class StudentInfo implements Initializable {
	
	@FXML
	private TextField id_box, show_f_name, show_l_name, show_dob, show_id, show_dept, show_shift, show_mail;
	@FXML
	private ComboBox<String> dept_box, trimester_box;
	@FXML
	private Button search_btn, search_result, update_btn, delete_btn;
	@FXML
	private TableView<DataTable> show_result_table;
	@FXML
	private TableColumn<DataTable, String> subjects;
	@FXML
	private TableColumn<DataTable, Integer> marks;
	@FXML
	private TableColumn<DataTable, Double> grades;
	@FXML
	private TableColumn<DataTable, String> letter_Grade;
	
	
	/* comboBox data */
	ObservableList<String> depts = FXCollections.observableArrayList("CSE", "EEE", "JRN", "ENG", "BTE");
	ObservableList<String> trimesters = FXCollections.observableArrayList("SPRING-22", "SUMMER-22", "FALL-22");
	
	
	/* variable for this program */
	int id, batch, i,newID;
	int[] subject = new int[20];
	double[] grade = new double[20];
	double totaleGrade;
	String[] letterGrades = new String[20];
	String[] sub_names = new String[50];
	String dept, f_name, l_name, mail,n, dob, shift, trimester, totalLetterGrade;
	

	ObservableList<DataTable> list;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		dept_box.setItems(depts);
		trimester_box.setItems(trimesters);
		
		subjects.setCellValueFactory(new PropertyValueFactory<DataTable, String>("subjects"));
		marks.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("marks"));
		grades.setCellValueFactory(new PropertyValueFactory<DataTable, Double>("grade"));
		letter_Grade.setCellValueFactory(new PropertyValueFactory<DataTable, String>("letterGrade"));
		
	}
	
	
	/* search result */
	@FXML
	public void search(ActionEvent event) {
		
		/* storing value */
		id = Integer.parseInt(id_box.getText());
		dept = dept_box.getValue();
		batch = Integer.parseInt(id_box.getText().substring(0,2));
		
		/* performing search */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			/* selecting details for info */
			String query = "SELECT `id`, `firstName`, `lastName`, `DOB`, `department`, `batch`, `shift`, `contact` FROM `batch_"+dept+""+batch+"` WHERE `id` = ? AND `department` = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setString(2, dept);
			
			ResultSet rs = ps.executeQuery();
			
			/* checking and storing search result */
			if(rs.next() == false) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ERROR");
				alert.setHeaderText("STUDENT DATA NOT FOUND");
				alert.setContentText("Either student data is deleted or wrong search info");
				alert.showAndWait();
			}else {
				f_name = rs.getString("firstName");
				l_name = rs.getString("lastName");
				dob = rs.getString("DOB");
				newID = rs.getInt("id");
				dept = rs.getString("department");
				mail = rs.getString("contact");
				shift = rs.getString("shift");
			}
			
			/* showing data */
			show_f_name.setText(f_name);
			show_l_name.setText(l_name);
			show_dob.setText(dob);
			show_id.setText(""+newID);
			show_dept.setText(dept);
			show_shift.setText(shift);
			show_mail.setText(mail);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	/* show result of searched student */
	@FXML
	public void result(ActionEvent event) {
		
		trimester = trimester_box.getValue();
		
		/* getting marks from database */
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			for(i = 0; i < 6; i++) {
				String query = "SELECT `trimester`, `sub"+(i+1)+"` FROM `"+dept+""+batch+"_result` WHERE `id` = ? AND `department` = ? AND `trimester` = ?";
				PreparedStatement ps = con.prepareStatement(query);
				
				ps.setInt(1, newID);
				ps.setString(2, dept);
				ps.setString(3, trimester);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next() == false) {
					if(newID == 0) {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("ERROR");
						alert.setHeaderText("Student Data Has Been Deleted");
						alert.setContentText(null);
						alert.showAndWait();
						break;
					}else {
						Alert alert = new Alert(AlertType.WARNING);
						alert.setTitle("ERROR");
						alert.setHeaderText("INVALID DATA");
						alert.setContentText("Student marks has not been entered for this trimester");
						alert.showAndWait();
						break;
					}
				}				
				else {
					subject[i] = rs.getInt("sub"+(i+1));
				}
			}
			
		} catch (Exception e) {
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
		
		/* calculating grades */
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
		}
		
		/* calculating letter grades */
		for(i = 0; i < 6; i++) {
			
			if(grade[i] == 4.00) {
				letterGrades[i] = "A+";
			}
			else if(grade[i] < 4.00 && grade[i] >= 3.75) {
				letterGrades[i] = "A";
			}
			else if(grade[i] < 3.75 && grade[i] >= 3.50) {
				letterGrades[i] = "A-";
			}
			else if(grade[i] < 3.50 && grade[i] >= 3.25) {
				letterGrades[i] = "B+";
			}
			else if(grade[i] < 3.25 && grade[i] >= 3.00) {
				letterGrades[i] = "B";
			}
			else if(grade[i] < 3.00 && grade[i] >= 2.75) {
				letterGrades[i] = "B-";
			}
			else if(grade[i] < 2.75 && grade[i] >= 2.50) {
				letterGrades[i] = "C+";
			}
			else if(grade[i] < 2.50 && grade[i] >= 2.25) {
				letterGrades[i] = "C";
			}
			else {
				letterGrades[i] = "D";
			}
		}
		
		
		/* passing result to table */
		list = FXCollections.observableArrayList(
				new DataTable(sub_names[0], subject[0], grade[0],letterGrades[0]),
				new DataTable(sub_names[1], subject[1], grade[1],letterGrades[1]),
				new DataTable(sub_names[2], subject[2], grade[2],letterGrades[2]),
				new DataTable(sub_names[3], subject[3], grade[3],letterGrades[3]),
				new DataTable(sub_names[4], subject[4], grade[4],letterGrades[4]),
				new DataTable(sub_names[5], subject[5], grade[5],letterGrades[5])
		);
		show_result_table.setItems(list);
	}
	
	/* update info */
	public void update(ActionEvent event) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			String query = "UPDATE `batch_"+dept+""+batch+"` SET `firstName`= ?,`lastName`= ?,`DOB`= ?,`shift`= ?,`contact`= ? WHERE `id` = ? AND `department` = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, show_f_name.getText());
			ps.setString(2, show_l_name.getText());
			ps.setString(3, show_dob.getText());
			ps.setString(4, show_shift.getText());
			ps.setString(5, show_mail.getText());
			ps.setInt(6, newID);
			ps.setString(7, dept);
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Update");
			alert.setHeaderText(null);
			alert.setContentText("Update Student Information ?");

			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == ButtonType.OK){
			    
				ps.executeUpdate();
				
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Update Complete");
				alert2.setHeaderText(null);
				alert2.setContentText("Student information has been updated");

				alert2.showAndWait();
				
			} else {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Update Cancelled");
				alert2.setHeaderText(null);
				alert2.setContentText("Student information update has been cancelled");

				alert2.showAndWait();
			}
			
		}catch(Exception e) {
			
		}
		
	}
	
	/* delete student information */
	public void delete(ActionEvent event) {
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			String query = "DELETE FROM `batch_"+dept+""+batch+"` WHERE `id` = ? AND `department`=?;";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, newID);
			ps.setString(2, dept);
			
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Confirm Deletion");
			alert.setHeaderText(null);
			alert.setContentText("Delete Student Information ?");

			Optional<ButtonType> result = alert.showAndWait();
			
			if (result.get() == ButtonType.OK){
			    
				ps.executeUpdate();
				
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Update Complete");
				alert2.setHeaderText(null);
				alert2.setContentText("Student information has been deleted");

				alert2.showAndWait();
				
			} else {
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("Update Cancelled");
				alert2.setHeaderText(null);
				alert2.setContentText("Student information deletion has been cancelled");

				alert2.showAndWait();
			}
			
		}catch(Exception e) {
			
		}
	}
	
}
