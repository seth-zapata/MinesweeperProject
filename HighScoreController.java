package application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.util.Duration;

public class HighScoreController {
	
	@FXML
	private TextArea HighScoreText;
	
	Timeline delay=new Timeline();

	
	@FXML
	void itemPrint() throws FileNotFoundException, IOException { //method to load inventory text to textbox 
		HashMap<String, String> hash=new HashMap<String,String>();
		File file=new File("data.inventory");
		FileInputStream reader=new FileInputStream(file);
		Properties properties=new Properties();
		properties.load(reader);
		reader.close();
		for(Object key: properties.stringPropertyNames()) {
			hash.put(key.toString(), properties.get(key).toString());
		}
		Iterator<HashMap.Entry<String, String>> itr = hash.entrySet().iterator();
		delay.getKeyFrames().setAll(new KeyFrame(Duration.seconds(0.5)));
		delay.setOnFinished(e -> {    
		    if(itr.hasNext()){
		    HashMap.Entry<String, String> entry = itr.next();
		    delay.getKeyFrames().setAll(new KeyFrame(Duration.seconds(0.4)));
		    delay.playFromStart();
		    HighScoreText.appendText(entry.getKey() + " " + entry.getValue() + "\n"); 

		}	
		});
		delay.playFromStart();
		
	
	}
	
	@FXML
	public void initialize() throws FileNotFoundException, IOException {
		itemPrint();
		HighScoreText.setEditable(false);
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
