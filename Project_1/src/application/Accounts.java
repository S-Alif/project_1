package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.shape.Line;

public class Accounts {
	
	@FXML
	private ComboBox<String> dept_box, trimester_box, fee_trimester_box, fee_term_box;
	@FXML
	private Label show_name, show_id, show_dues, show_pay_now, pay_info_label;
	@FXML
	private Line mid_line;
	@FXML
	private Button search_btn, fee_submit_btn, new_data_btn;
	@FXML
	private TextField id_box, fee_box;
	
	/* comboBox data */
	ObservableList<String> depts = FXCollections.observableArrayList("CSE", "EEE", "JRN", "ENG", "BTE");
	ObservableList<String> terms = FXCollections.observableArrayList("MID-TERM", "FINAL-TERM");
	ObservableList<String> trimesters = FXCollections.observableArrayList("SPRING-22", "SUMMER-22", "FALL-22");
	
	@FXML
	public void initialize() {
		dept_box.setItems(depts);
		fee_term_box.setItems(terms);
		trimester_box.setItems(trimesters);
		fee_trimester_box.setItems(trimesters);
	}
	
	
	/* variables for this program */
	private String name, dept, trimester, full_id, batch, term;
	private int id, pay_mid, pay_final, pay_total, due;
	
	private String n;
	
	
	/* search action */
	public void search(ActionEvent event) {
		id = Integer.parseInt(id_box.getText());
		dept = dept_box.getValue();
		trimester = trimester_box.getValue();
		batch = id_box.getText().substring(0, 2);
		
		try {			
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			/* selecting details for info */
			String getName = "SELECT `firstName`, `lastName`, `id`, `department` FROM `batch_"+dept+""+batch+"` WHERE `id` = ?";
			PreparedStatement ps = con.prepareStatement(getName);
			
			/* selecting details for info */
			String getDue = "SELECT `MidTerm`, `finalTerm`, `paidFee`, `dueFee` FROM `payment_"+dept+""+batch+"` WHERE `id` = ? AND `trimester` = ?";
			PreparedStatement ps2 = con.prepareStatement(getDue);
			
			ps.setInt(1, id);			
			ResultSet rs = ps.executeQuery();		
			
			ps2.setInt(1, id);
			ps2.setString(2, trimester);
			ResultSet rs2 = ps2.executeQuery();
			
			
			if(rs.next() == false || rs2.next() == false) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ERROR");
				alert.setHeaderText("INVALID DATA");
				alert.setContentText("Please Enter Required Data Perfectly");
				alert.showAndWait();
			}else {
				name = ""+rs.getString("firstName")+" "+rs.getString("lastName");
				full_id = ""+rs.getString("department")+" "+rs.getInt("id");
				pay_mid = rs2.getInt("midTerm");
				pay_final = rs2.getInt("finalTerm");
				pay_total = rs2.getInt("paidFee");
				due = rs2.getInt("dueFee");
			}
			
			/* showing payment info */
			pay_info_label.setOpacity(1);
			mid_line.setOpacity(1);
			show_name.setText("Name : "+name);
			show_id.setText("ID : "+full_id);
			show_dues.setText("Dues : "+due+" TK");
			
			if(due == 30000) {
				show_pay_now.setText("Pay Now : "+due/2+" TK");
			}else {
				show_pay_now.setText("Pay Now : "+due+" TK");
			}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* saving the fee */
	public void save_fee() {
		
		trimester = fee_trimester_box.getValue();
		term = fee_term_box.getValue();
		
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");			
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/student", "root",n);
			
			if(pay_mid == 15000 && term == "MID-TERM") {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Payment for this term was completed");
				alert.setContentText("Please Select Final term");
				alert.showAndWait();
			}
			if(pay_final == 15000 && term == "FINAL-TERM") {
				
				if(due == 0) {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Payment Information");
					alert.setHeaderText(null);
					alert.setContentText("Payment for this trimester completed");
					alert.showAndWait();
				}
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Payment Information");
					alert.setHeaderText("Payment for this term was completed");
					alert.setContentText("Please Select Final term");
					alert.showAndWait();
				}
			}
			
			if((pay_mid < 15000 || pay_mid == 0) && term == "MID-TERM") {
				
				pay_mid = pay_mid + Integer.parseInt(fee_box.getText());				
				pay_total = pay_mid + pay_final;
				due = due-Integer.parseInt(fee_box.getText());
				
				String passFee = "UPDATE `payment_"+dept+""+batch+"` SET `midTerm` = ?, `paidFee` = ?, `dueFee` = ? WHERE `id` = ? AND `trimester` = ?";
				PreparedStatement ps = con.prepareStatement(passFee);
				
				ps.setInt(1, pay_mid);
				ps.setInt(2, pay_total);
				ps.setInt(3, due);
				ps.setInt(4, id);
				
				if(fee_trimester_box.getValue() != trimester_box.getValue()) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("Please Select the current trimester");
					alert.showAndWait();
				}
				else {
					ps.setString(5, trimester);					
					ps.executeUpdate();
					
					/* alert */
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCCESS");
					alert.setHeaderText("FEES PAID");
					alert.setContentText("Student has paid the required fees");
					alert.showAndWait();
				}
								
			}
			
			if((pay_final < 15000 || pay_final == 0) && term == "FINAL-TERM") {
				
				pay_final = pay_final + Integer.parseInt(fee_box.getText());
				pay_total = pay_mid + pay_final;
				due = due-Integer.parseInt(fee_box.getText());
				
				String passFee = "UPDATE `payment_"+dept+""+batch+"` SET `finalTerm` = ?, `paidFee` = ?, `dueFee` = ? WHERE `id` = ? AND `trimester` = ?";
				PreparedStatement ps = con.prepareStatement(passFee);
				
				ps.setInt(1, pay_final);
				ps.setInt(2, pay_total);
				ps.setInt(3, due);
				ps.setInt(4, id);
				
				if(fee_trimester_box.getValue() != trimester_box.getValue()) {
					
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("ERROR");
					alert.setHeaderText(null);
					alert.setContentText("Please Select the current trimester");
					alert.showAndWait();
				}
				else {
					ps.setString(5, trimester);					
					ps.executeUpdate();
					
					/* alert */
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("SUCCESS");
					alert.setHeaderText("FEES PAID");
					alert.setContentText("Student has paid the required fees");
					alert.showAndWait();
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/* enter new data */
	public void enter_new_data(ActionEvent event) {
		/* clear fields */
		id_box.setText(null);
		fee_box.setText(null);
		dept_box.valueProperty().set(null);
		fee_term_box.valueProperty().set(null);
		trimester_box.valueProperty().set(null);
		fee_trimester_box.valueProperty().set(null);
		
		
		/* clear previews */
		pay_info_label.setOpacity(0);
		mid_line.setOpacity(0);
		show_name.setText("");
		show_id.setText("");
		show_dues.setText("");
		show_pay_now.setText("");
	}
	
}
