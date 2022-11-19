module Project_1_student_form {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.sql;
	requires java.desktop;
	requires mysql.connector.j;
	
	opens application to javafx.graphics, javafx.fxml, javafx.base;
}
