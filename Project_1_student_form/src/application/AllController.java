package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AllController implements Initializable{

	@FXML
    private Button course_btn;

    @FXML
    private ComboBox<String> dept_box;

    @FXML
    private Hyperlink goTo_login;

    @FXML
    private Hyperlink goTo_signIn;

    @FXML
    private TableColumn<DataTable, Double> grade_table;

    @FXML
    private TableColumn<DataTable, String> letter_grade_table;

    @FXML
    private Button logIn_btn;

    @FXML
    private ComboBox<String> login_dept_box;

    @FXML
    private AnchorPane login_page;

    @FXML
    private PasswordField login_password;

    @FXML
    private TextField login_username;

    @FXML
    private TableColumn<DataTable, Integer> marks_table;

    @FXML
    private PasswordField password_box;

    @FXML
    private Button result_btn;

    @FXML
    private TableView<DataTable> result_table;

    @FXML
    private ComboBox<String> result_trimester_box;

    @FXML
    private Label show_id, result_totalGrade, result_letterGrade;

    @FXML
    private Label show_name;

    @FXML
    private AnchorPane signUp_page, theFrame;

    @FXML
    private Button sign_up_btn;

    @FXML
    private AnchorPane student_page;

    @FXML
    private TableColumn<DataTable, String> subName_table;

    @FXML
    private Button submit_btn;

    @FXML
    private ComboBox<String> trimester_box;

    @FXML
    private ListView<String> trimester_sub_name;

    @FXML
    private TextField username_box;
    
    @FXML
    private Button exit_btn;
	    
    
    /* variables for storing */
    private String dept, password, name, newID, trimester, totalLetterGrade;
    private int batch, id,i, theID;
    int[] subject = new int[20];
	double[] grade = new double[20];
	double totalGrade;
	String[] letterGrades = new String[20];
	String[] sub_names = new String[50];
    
    /* comboBox items */
    ObservableList<String> depts = FXCollections.observableArrayList("CSE", "EEE", "JRN", "ENG", "BTE");
    ObservableList<String> trimesters = FXCollections.observableArrayList("SPRING-22", "SUMMER-22", "FALL-22");
    
    ObservableList<DataTable> list;
    ObservableList<String> list2 = FXCollections.observableArrayList("","","","","","");
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		/* adding items to dept-boxes */
		dept_box.setItems(depts);
		login_dept_box.setItems(depts);
		trimester_box.setItems(trimesters);
		result_trimester_box.setItems(trimesters);
		
		/* table to show result */
		subName_table.setCellValueFactory(new PropertyValueFactory<DataTable, String>("subjects"));
		marks_table.setCellValueFactory(new PropertyValueFactory<DataTable, Integer>("marks"));
		grade_table.setCellValueFactory(new PropertyValueFactory<DataTable, Double>("grade"));
		letter_grade_table.setCellValueFactory(new PropertyValueFactory<DataTable, String>("letterGrade"));
		
		/* showing signUp page first */
		signUp_page.setVisible(true);
		login_page.setVisible(false);
		
		signUp_page.setDisable(false);
		login_page.setDisable(true);
		student_page.setVisible(false);
	}
	
	/* login database */
	public void login() {
		
		/* getting data to variables */
		dept = login_dept_box.getValue();
		id = Integer.parseInt(login_username.getText());
		batch = Integer.parseInt(login_username.getText().substring(0,2));
		password = login_password.getText();		
		
		/* getting data from server */
		try {
			Connection con = Database.connectDB();
			
			String query = "SELECT `id`, `firstName`, `lastName`, `department`, `password` FROM `batch_"+dept+""+batch+"` WHERE `id` = ? AND `department` = ?  AND `password` = ?";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setString(2, dept);
			ps.setString(3, password);
			
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {				
				signUp_page.setDisable(false);
				login_page.setDisable(false);
				student_page.setVisible(true);
				
				name = ""+rs.getString("firstName")+" "+rs.getString("lastName");
				newID = ""+rs.getString("department")+" "+rs.getInt("id");
				theID = rs.getInt("id");
				
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Couldn't find");
				alert.setContentText("USERNAME or PASSWORD don't match");
				
				alert.showAndWait();
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		/* showing data */
		show_name.setText(name);
		show_id.setText(newID);
	}
	
	/* sign-in from database */
	public void sign_in() {
		dept = dept_box.getValue();
		id = Integer.parseInt(username_box.getText());
		batch = Integer.parseInt(username_box.getText().substring(0,2));
		password = password_box.getText();
		
		try {
			Connection con = Database.connectDB();
			
			String query = "UPDATE `batch_"+dept+""+batch+"` SET `password` = ? WHERE `id` = ? AND `department` = ?;";			
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setString(1, password);
			ps.setInt(2, id);
			ps.setString(3, dept);
						
			ps.executeUpdate();
			
			/* getting data */
			String query2 = "SELECT `id`, `firstName`, `lastName`, `department`, `password` FROM `batch_"+dept+""+batch+"` WHERE `id` = ? AND `department` = ?  AND `password` = ?";			
			PreparedStatement ps2 = con.prepareStatement(query2);
			
			ps2.setInt(1, id);
			ps2.setString(2, dept);
			ps2.setString(3, password);
			
			ResultSet rs = ps2.executeQuery();
			
			if(rs.next()) {				
				signUp_page.setVisible(false);
				login_page.setVisible(false);
				
				signUp_page.setDisable(false);
				login_page.setDisable(false);
				student_page.setVisible(true);
				
				name = ""+rs.getString("firstName")+" "+rs.getString("lastName");
				newID = ""+rs.getString("department")+" "+rs.getInt("id");
				
			}else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("ERROR");
				alert.setHeaderText("Couldn't find");
				alert.setContentText("USERNAME or PASSWORD don't match");
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		/* showing data */
		show_name.setText(name);
		show_id.setText(newID);
	}
	
	/* exiting the program */
	public void exit() {
		System.exit(0);
	}
	
	/* minimize the program */
	public void minimize() {
		 Stage stage = (Stage) theFrame.getScene().getWindow();
		 stage.setIconified(true);
	}
	
	/* changing the pages */
	public void change_page(ActionEvent event) {
		
		if (event.getSource() == goTo_login) {
			signUp_page.setVisible(false);
			login_page.setVisible(true);
			
			signUp_page.setDisable(true);
			login_page.setDisable(false);
			
		}else {
			signUp_page.setVisible(true);
			login_page.setVisible(false);
			
			signUp_page.setDisable(false);
			login_page.setDisable(true);
		}
		
	}
	
	/* preview courses */
	public void  course_preview() {
		
		trimester_sub_name.getItems().clear();
		
		trimester = trimester_box.getValue();
		batch = Integer.parseInt(newID.substring(4,6));
		id = Integer.parseInt(newID.substring(4,9));
		
		/* getting subject names */
		try {
			Connection con = Database.connectDB();
			
			for(i = 0; i < 6; i++) {
				
				String query2 = "SELECT `sub"+(i+1)+"` FROM subjects WHERE `department` = ? AND `batch` = ? AND `trimester` = ?;";
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
		
		
		/* showing the list */
		for(i = 0; i < 6; i++) {
			list2.set(i, sub_names[i]);
		}
		trimester_sub_name.getItems().addAll(list2);		
	}
	
	
	/* see result */
	public void see_result() {
		
		trimester = result_trimester_box.getValue();
		batch = Integer.parseInt(newID.substring(4,6));
		id = Integer.parseInt(newID.substring(4,9));
		
		/* getting marks from database */
		try {
			Connection con = Database.connectDB();
			
			for(i = 0; i < 6; i++) {
				String query = "SELECT `trimester`, `sub"+(i+1)+"` FROM `"+dept+""+batch+"_result` WHERE `id` = ? AND `department` = ? AND `trimester` = ?;";
				PreparedStatement ps = con.prepareStatement(query);
				
				ps.setInt(1, id);
				ps.setString(2, dept);
				ps.setString(3, trimester);
				
				ResultSet rs = ps.executeQuery();
				
				if(rs.next() == false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERROR");
					alert.setHeaderText("INVALID DATA");
					alert.setContentText("Student marks has not been entered for this trimester");
					alert.showAndWait();
					break;
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
			Connection con = Database.connectDB();
			
			for(i = 0; i < 6; i++) {
				
				String query2 = "SELECT `sub"+(i+1)+"` FROM subjects WHERE `department` = ? AND `batch` = ? AND `trimester` = ?;";
				PreparedStatement ps2 = con.prepareStatement(query2);
				
				ps2.setString(1, dept);
				ps2.setInt(2, batch);
				ps2.setString(3, trimester);
				
				ResultSet rs2 = ps2.executeQuery();
				
				if(rs2.next() == false) {
					Alert alert = new Alert(AlertType.WARNING);
					alert.setTitle("ERROR");
					alert.setHeaderText("INVALID DATA");
					alert.setContentText("Please Select the required trimester");
					alert.showAndWait();
					break;
				}else {
					sub_names[i] = rs2.getString("sub"+(i+1));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		/* getting total grade and letter grade */
		try {
			Connection con = Database.connectDB();
			String query = "SELECT `totalLetterGrade`, `totalGrade` FROM `"+dept+""+batch+"_result` WHERE `id` = ? AND `department` = ? AND `trimester` = ?;";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, id);
			ps.setString(2, dept);
			ps.setString(3, trimester);
			
			ResultSet rs = ps.executeQuery();	
			
			if(rs.next() == false) {
				Alert alert = new Alert(AlertType.WARNING);
				alert.setTitle("ERROR");
				alert.setHeaderText("INVALID DATA");
				alert.setContentText("Student marks has not been entered for this trimester");
				alert.showAndWait();
			}				
			else {
				DecimalFormat format = new DecimalFormat("#.##");
				totalGrade = rs.getDouble("totalGrade");
				totalLetterGrade = rs.getString("totalLetterGrade");
				
				result_totalGrade.setText("Total Grade : "+format.format(totalGrade));
				result_letterGrade.setText("Total Letter Grade : "+totalLetterGrade);
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
		result_table.setItems(list);
		
	}
	
	
	/* submit re-registration */
	public void re_registration() {
		
		try{
			Connection con = Database.connectDB();
			
			/* main re-registration */
			String query = "INSERT INTO `re_registration"+batch+"`(`id`, `department`, `trimester`) VALUES (?,?,?)";
			PreparedStatement ps = con.prepareStatement(query);
			
			ps.setInt(1, theID);
			ps.setString(2, dept);
			ps.setString(3, trimester_box.getValue());
			
			ps.executeUpdate();
			
			
			/* new payment */
			String query2= "INSERT INTO `payment_"+dept+""+batch+"`(`id`,`department`, `trimester`) VALUES (?,?,?);";
			PreparedStatement ps2 = con.prepareStatement(query2);
			
			/* 2nd query */
			ps2.setInt(1, theID);
			ps2.setString(2, dept);
			ps2.setString(3, trimester);
			
			ps2.executeUpdate();
			
			
			String query3= "INSERT INTO `"+dept+""+batch+"_result`(`id`,`department`, `trimester`) VALUES (?,?,?);";
			PreparedStatement ps3 = con.prepareStatement(query3);
			
			/* 2nd query */
			ps3.setInt(1, theID);
			ps3.setString(2, dept);
			ps3.setString(3, trimester);
			
			/* executing the query */
			ps3.executeUpdate();
			
			
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("MESSAGE");
			alert.setHeaderText("Registration Complete");
			alert.setContentText("Your registration for next trimester is complete");
			alert.showAndWait();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
