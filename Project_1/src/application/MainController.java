package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

public class MainController {
	
	@FXML
	private BorderPane mainPane;
	
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
}
