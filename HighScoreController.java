package application;

import java.io.BufferedReader;
//import java.io.File;
//import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
//import java.util.Properties;
import java.util.TreeMap;

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
	
	private static String getField(String line) { //method to sort map
        return line.split("\\s+")[2];//extracts time value 
    }

	
	@FXML
	void itemPrint() throws FileNotFoundException, IOException { //method to load inventory text to textbox 
		/*HashMap<String, String> hash=new HashMap<String,String>();
		File file=new File("userIDs.properties");
		FileInputStream reader=new FileInputStream(file);
		Properties properties=new Properties();
		properties.load(reader);
		reader.close();
		for(Object key: properties.stringPropertyNames()) {
			hash.put(key.toString(), properties.get(key).toString());
		}
		*/
		BufferedReader reader = new BufferedReader(new FileReader("userInfo.txt"));
        Map<String, String> map=new TreeMap<String, String>();
        String line="";
        while((line=reader.readLine())!=null){
            map.put(getField(line),line); //puts time as key value
        }
        reader.close();
        		
		int[] ordinal = { 1 };
		Iterator<Map.Entry<String, String>> itr = map.entrySet().iterator();
		delay.getKeyFrames().setAll(new KeyFrame(Duration.seconds(0.5)));
		delay.setOnFinished(e -> {    
		    if(itr.hasNext()){
		    Map.Entry<String, String> entry = itr.next();
		    delay.getKeyFrames().setAll(new KeyFrame(Duration.seconds(0.4)));
		    delay.playFromStart();
		    String complete = String.format("%-3d %s\n", ordinal[0]++, entry.getValue());
		    HighScoreText.appendText(complete); 
		   
		}	
		});
		delay.playFromStart();
		
		
		/*Iterator<HashMap.Entry<String, String>> itr = hash.entrySet().iterator();
		delay.getKeyFrames().setAll(new KeyFrame(Duration.seconds(0.5)));
		delay.setOnFinished(e -> {    
		    if(itr.hasNext()){
		    HashMap.Entry<String, String> entry = itr.next();
		    delay.getKeyFrames().setAll(new KeyFrame(Duration.seconds(0.4)));
		    delay.playFromStart();
		    String complete = String.format("%-3d %-6s %s\n", ordinal[0]++, entry.getKey(), entry.getValue());
		    HighScoreText.appendText(complete); 
		   

		}	
		});
		delay.playFromStart();
		*/
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
