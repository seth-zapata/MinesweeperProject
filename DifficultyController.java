package application;

import java.io.IOException; 
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.MPApp;

public class DifficultyController {
	
	EventHandler <ActionEvent> event;
	
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
			MPApp.setPassedValue("Beginner");
			MPApp.userName = username.getText();
			//MPApp.addUserName(username.getText());
			username.clear();
			// set flag to "beginner"
			
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
			MPApp.setPassedValue("Intermediate");
			// add username to file
			MPApp.userName = username.getText();
			//MPApp.addUserName(username.getText());
			username.clear();
			// set flag to "intermediate"
			
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
			MPApp.setPassedValue("Advanced");
			// add username to file
			MPApp.userName = username.getText();
			//MPApp.addUserName(username.getText());
			username.clear();
			// set flag to "advanced"
			
			// goto start game screen
			changeScreenonStart(event);			
		}
	}	
	
	// takes user to game screen
	@FXML 
	public void changeScreenonStart(ActionEvent event) throws IOException {
GameController game = new GameController();
		
		Scene sceneGame = new Scene(game.createDynamicGrid());
		
		game.passScene(sceneGame);
		MPApp.setScene(sceneGame);
		
		// Pass scene of game to model file for future reseting 
		MPApp.passX(sceneGame.getX());
		MPApp.passY(sceneGame.getY());
		
		sceneGame.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
	
		game.setTimerPopup();
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(sceneGame);
		
		// Alter window size based on the difficulty passed
		stage = MPApp.setGameWindowSize(stage);
		
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
