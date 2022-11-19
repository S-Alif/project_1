package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	private BorderPane mainPane;
	@FXML
	private AnchorPane theFrame;
	
	/* load registrar */
	public void registrar(ActionEvent e) {
		PageLoader object = new PageLoader();
		Pane show = object.getPage("Registrar.fxml");
		mainPane.setCenter(show);
	}
	
	/* load accounts */
	public void accounts(ActionEvent e) {
		PageLoader object = new PageLoader();
		Pane show = object.getPage("Accounts.fxml");
		mainPane.setCenter(show);
	}
	
	/* load exam control */
	public void examControl(ActionEvent e) {
		PageLoader object = new PageLoader();
		Pane show = object.getPage("ExamController.fxml");
		mainPane.setCenter(show);
	}
	/* load exam control */
	public void studentInfo(ActionEvent e) {
		PageLoader object = new PageLoader();
		Pane show = object.getPage("StudentInfo.fxml");
		mainPane.setCenter(show);
	}
	
	public void exit() {
		System.exit(0);
	}
	public void minimize() {
		 Stage stage = (Stage) theFrame.getScene().getWindow();
		 stage.setIconified(true);
	}
}
