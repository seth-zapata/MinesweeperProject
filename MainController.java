package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class MainController {
	
	@FXML
	private Button difficulty, start, rules, highscore, back;
	
	@FXML 
	public void changeScreenonDifficulty(ActionEvent event) throws IOException {
		FXMLLoader viewDifficulty = new FXMLLoader(getClass().getResource("Difficulty.fxml"));
		Parent root = (Parent) viewDifficulty.load();
		root.setId("Difficulty");
	
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.setTitle("Minesweeper: Difficulty Settings");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
		stage.show();
	}
	
	@FXML 
	public void changeScreenonStart(ActionEvent event) throws IOException {
		FXMLLoader viewStart = new FXMLLoader(getClass().getResource("Start.fxml"));
		Parent root = (Parent) viewStart.load();
		root.setId("Start");
		
		StartController control = new StartController();
		control = viewStart.getController();
		
	
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.setTitle("Minesweeper: Start Game");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
		stage.show();
	}
	
	@FXML 
	public void changeScreenonRules(ActionEvent event) throws IOException {
		FXMLLoader viewRules = new FXMLLoader(getClass().getResource("Rules.fxml"));
		Parent root = (Parent) viewRules.load();
		root.setId("Rules");
		
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.setTitle("Minesweeper: Rules");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
		stage.show();
	}
	
	@FXML 
	public void changeScreenonHighScore(ActionEvent event) throws IOException {
		FXMLLoader viewHighScore = new FXMLLoader(getClass().getResource("HighScore.fxml"));
		Parent root = (Parent) viewHighScore.load();
		root.setId("HighScore");
	
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.setTitle("Minesweeper: High Scores");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
		stage.show();
	}
	
	@FXML 
	public void changeScreenonBack(ActionEvent event) throws IOException {
		FXMLLoader viewHome = new FXMLLoader(getClass().getResource("Main.fxml"));
		Parent root = (Parent) viewHome.load();
		root.setId("Home");
	
		Scene scene = new Scene(root);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		Stage stage = (Stage)((Node) event.getSource()).getScene().getWindow();
		
		stage.setScene(scene);
		stage.setTitle("Minesweeper: Home");
		stage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
		stage.show();
	}

}
