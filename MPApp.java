// This model file should go in the model package of the project files

package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


public class MPApp {
	
	// holds difficulty selected by user
	static String passedDifficultyValue;
	
	// sets what difficulty the user selected
	public static void setPassedValue(String passedDifficultyValueTemp) {
		passedDifficultyValue = passedDifficultyValueTemp;
	}
	
	// gets what difficulty user selected; can be used by start scene to modify difficulty
	public static String getPassedValue() {
		return passedDifficultyValue;
	}
	
	// check if user entered a "username"
	public static boolean checkIfUsernameEntered(String userName) {
		// if no username is entered
		if(userName.trim().isEmpty()) {
			// display no "username" alert
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("No username");
			a.setHeaderText("Missing Information");
			a.setContentText("Please enter a username");
			a.show();
			return true;
		}
		return false;
	}
	
	// add username to file
	public static void addUserName(String username) throws IOException {
		File file = new File("users.txt");

		// if file doesn't exists make it  
		if(!file.exists()) {
			file.createNewFile();
		}
				
		FileWriter writer = new FileWriter(file, true);
			
		// write username to file
		writer.write(username + System.lineSeparator());
        writer.close();
	}
}
