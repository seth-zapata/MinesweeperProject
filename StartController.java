package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StartController {
	
	@FXML
	private Button reset, back, help;
	
	@FXML 
	public void changeScreenonBack(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonBack(event);
	}
	
	@FXML 
	public void changeScreenonReset(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonBack(event); // Will be deleted eventually (?)
		// This should reset the game immediately, and not show the home screen (?)
	}
	
	@FXML 
	public void changeScreenonHelp(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonRules(event);
	}
}
