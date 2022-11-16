package application;

import java.net.URL;

//import javax.print.DocFlavor.URL;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class PageLoader {
	private Pane show;
	
	public Pane getPage(String fileName) {
		try {
			URL fileUrl = Main.class.getResource(""+fileName);
			if(fileUrl == null) {
				throw new java.io.FileNotFoundException("Page Not Found");
			}
			
			show = new FXMLLoader().load(fileUrl);
			
		}catch(Exception e) {
			
		}
		
		return show;
	}
}
