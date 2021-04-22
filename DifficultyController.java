package application;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.MPApp;

public class DifficultyController {
	
	// button that turns user to home screen
	@FXML private Button back;
	
	// user will enter their username here
	@FXML private TextField username;
	
	// flag to indicate if username was entered
	boolean noUsername = false;
	
	// if user selects beginner difficulty level set flag "difficultyLevel"
	@FXML 
	public void changeScreenonStartGameBeginner(ActionEvent event) throws IOException {
		// check if user left "username" blank
		if(MPApp.checkIfUsernameEntered(username.getText())) {
			// display "no username" alert message 
		} else {
			// add username to file
			MPApp.addUserName(username.getText());
			username.clear();
			// set flag to "beginner"
			MPApp.setPassedValue("Beginner");
			// goto start game screen
			changeScreenonStart(event);			
		}
	}
	
	// if user selects intermediate difficulty level set flag "difficultyLevel"
	@FXML 
	public void changeScreenonStartGameIntermediate(ActionEvent event) throws IOException {
		// check if user left "username" blank
		if(MPApp.checkIfUsernameEntered(username.getText())) {
			// display "no username" alert message
		}
		else {
			// add username to file
			MPApp.addUserName(username.getText());
			username.clear();
			// set flag to "intermediate"
			MPApp.setPassedValue("Intermediate");
			// goto start game screen
			changeScreenonStart(event);			
		}
	}
	
	// if user selects advanced difficulty level set flag "difficultyLevel"
	@FXML 
	public void changeScreenonStartGameAdvanced(ActionEvent event) throws IOException {
		// check if user left "username" blank
		if(MPApp.checkIfUsernameEntered(username.getText())) {
			// display "no username" alert message
		}
		else {
			// add username to file
			MPApp.addUserName(username.getText());
			username.clear();
			// set flag to "advanced"
			MPApp.setPassedValue("Advanced");
			// goto start game screen
			changeScreenonStart(event);			
		}
	}	
	
	// takes user to game screen
	@FXML 
	public void changeScreenonStart(ActionEvent event) throws IOException {
		FXMLLoader viewStart = new FXMLLoader(getClass().getResource("Start.fxml"));
		Parent root = (Parent) viewStart.load();
		root.setId("Start");
	
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.setTitle("Minesweeper: Start Game");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
		stage.show();
	}
	
	// takes user to home screen
	@FXML 
	public void changeScreenonBack(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonBack(event);
	}
}
