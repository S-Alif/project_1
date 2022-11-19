package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class Registrar {
	@FXML
	private ComboBox<String> dept_box, shift_box, trimester_box, batch_box;
	@FXML
	private TextField f_name_box, l_name_box, mail_box;
	@FXML
	private DatePicker dob_box;
	@FXML
	private Button preview_btn, save_btn, new_data_btn;
	@FXML
	private Label show_name, show_dept, show_shift, show_trimester, show_mail, show_batch, show_dob, preview_label;
	
	
	/* validation alert */
	Alert validationAlert = new Alert(AlertType.ERROR);
	
	/* comboBox data */
	ObservableList<String> depts = FXCollections.observableArrayList("CSE", "EEE", "JRN", "ENG", "BTE");
	ObservableList<String> shifts = FXCollections.observableArrayList("DAY", "EVENING");
	ObservableList<String> trimesters = FXCollections.observableArrayList("SPRING-22", "SUMMER-22", "FALL-22");
	ObservableList<String> batches = FXCollections.observableArrayList("27", "28", "29", "30");
	
	@FXML
	public void initialize() {
		dept_box.setItems(depts);
		shift_box.setItems(shifts);
		trimester_box.setItems(trimesters);
		batch_box.setItems(batches);
	}
	
	/* variables for this program */
	private String name, shift, trimester, mail, dept,dob;
	private int batch;
	
	private String n;
	
	/* email validation */
	public boolean isValid(String email){
		String valid = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
		Pattern pattern = Pattern.compile(valid);
		if (email == null) {
			return false;
		}
		
		return pattern.matcher(email).matches();
	}
	
	/* preview action */
	public void preview(ActionEvent event) {
		validationAlert.setHeaderText(null);
		validationAlert.setTitle("ERROR");
		
		/* email validation form */		
		if(f_name_box.getText().trim().isEmpty() || l_name_box.getText().trim().isEmpty()) {
			validationAlert.setContentText("Name is required");
			validationAlert.showAndWait();
		}
		else if(dob_box.getValue().toString().isEmpty()) {
			validationAlert.setContentText("Date of Birth is required !!");
			validationAlert.showAndWait();
		}
		else if(batch_box.getValue() == null) {
			validationAlert.setContentText("Please Enter Batch Number !!");
			validationAlert.showAndWait();
		}
		else if(dept_box.getValue() == null) {
			validationAlert.setContentText("Please enter department !!");
			validationAlert.showAndWait();
		}
		else if(shift_box.getValue() == null) {
			validationAlert.setContentText("Please enter the SHIFT !!");
			validationAlert.showAndWait();
		}
		else if(trimester_box.getValue() == null) {
			validationAlert.setContentText("Please select trimester !!");
			validationAlert.showAndWait();
		}
		else if(isValid(mail_box.getText()) == false) {
			validationAlert.setContentText("Please enter a valid email !!");
			validationAlert.showAndWait();
		}
		else {
			
			/* storing data in variables for use */
			name = ""+f_name_box.getText()+" "+l_name_box.getText();
			dept = dept_box.getValue();
			shift = shift_box.getValue();
			trimester = trimester_box.getValue();
			dob = dob_box.getValue().toString();
			mail = mail_box.getText();
			batch = Integer.parseInt(batch_box.getValue());
			
			preview_label.setOpacity(1);
			
			/* inserting the data to preview */
			show_name.setText("Name : "+name);
			show_dept.setText("Department : "+dept);
			show_shift.setText("Shift : "+shift);
			show_trimester.setText("Trimester : "+trimester);
			show_mail.setText("Contact : "+mail);
			show_batch.setText("Batch : "+batch);
			show_dob.setText("Date of Birth : "+dob);
			
		}		
		
	}
	
	/* save to database */
	public void save_data(ActionEvent event) {
		
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Registration");
		alert.setHeaderText(null);
		alert.setContentText("Submit Student Registration ?");

		Optional<ButtonType> result = alert.showAndWait();
		if (result.get() == ButtonType.CANCEL){
			
			Alert alert2 = new Alert(AlertType.INFORMATION);
			alert2.setTitle("Registration Stopped");
			alert2.setHeaderText(null);
			alert2.setContentText("Student registration has been cancelled");

			alert2.showAndWait();
			
		} else {
			
			
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
				
				/* main info table for batches */
				String query = "INSERT INTO `batch_"+dept+""+batch+"`(`firstName`, `lastName`, `DOB`, `department`, `batch`, `shift`, `trimester`, `contact`) VALUES (?,?,?,?,?,?,?,?);";				
				PreparedStatement ps = con.prepareStatement(query);
				
				/* 1st query */
				ps.setString(1, f_name_box.getText());
				ps.setString(2, l_name_box.getText());
				ps.setString(3, dob);
				ps.setString(4, dept);
				ps.setInt(5, batch);
				ps.setString(6, shift);
				ps.setString(7, trimester);
				ps.setString(8, mail);
				
				ps.executeUpdate();
				
				/* getting id */
				String id = "SELECT `id` FROM `batch_"+dept+""+batch+"` WHERE `firstName` = ? AND `lastName` = ?";
				PreparedStatement findId = con.prepareStatement(id);
				
				findId.setString(1, f_name_box.getText());
				findId.setString(2, l_name_box.getText());
				
				ResultSet theID = findId.executeQuery();
				
				int newID = 0;
				
				if(theID.next() == false) {
					Alert alert2 = new Alert(AlertType.WARNING);
					alert2.setTitle("ERROR");
					alert2.setHeaderText("INVALID DATA");
					alert2.setContentText("Please Enter Required Data Perfectly");
					alert2.showAndWait();
				}else {
					newID = theID.getInt("id");
				}
				
				
				/* for payemnt */
				String query2= "INSERT INTO `payment_"+dept+""+batch+"`(`id`,`department`, `trimester`) VALUES (?,?,?);";
				PreparedStatement ps2 = con.prepareStatement(query2);
				
				/* 2nd query */
				ps2.setInt(1, newID);
				ps2.setString(2, dept);
				ps2.setString(3, trimester);
				
				/* executing the query */
				ps2.executeUpdate();
				
				/* for result */
				String query3= "INSERT INTO `"+dept+""+batch+"_result`(`id`,`department`, `trimester`) VALUES (?,?,?);";
				PreparedStatement ps3 = con.prepareStatement(query3);
				
				/* 2nd query */
				ps3.setInt(1, newID);
				ps3.setString(2, dept);
				ps3.setString(3, trimester);
				
				/* executing the query */
				ps3.executeUpdate();
				
				/* confirm data entry */
				Alert alert2 = new Alert(AlertType.INFORMATION);
				alert2.setTitle("SUCCESS");
				alert2.setHeaderText("All data is saved properly");
				alert2.setContentText("Student registration is complete !");
				alert2.showAndWait();
				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			
		}
		
	}
	
	/* enter new data */
	public void enter_new_data(ActionEvent event) {
		/* clear fields */
		f_name_box.setText(null);
		l_name_box.setText(null);
		dept_box.valueProperty().set(null);
		shift_box.valueProperty().set(null);
		trimester_box.valueProperty().set(null);
		batch_box.valueProperty().set(null);
		mail_box.setText(null);
		dob_box.setValue(null);
		
		preview_label.setOpacity(0);
		
		/* clear previews */
		show_name.setText("");
		show_dept.setText("");
		show_shift.setText("");
		show_trimester.setText("");
		show_mail.setText("");
		show_batch.setText("");
		show_dob.setText("");
	}
}
