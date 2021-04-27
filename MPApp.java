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
	
	
	// holds the amount of seconds that have passed
	static int secondsPassed = 0;
	
	// holds difficulty selected by user
	static String passedDifficultyValue;
	
		// new instance of timer
	static Timer timer = new Timer();
	
	// this is the task that the timer will execute
	static TimerTask task = new TimerTask() {
		@Override
		public void run() {
			secondsPassed++;
		}
	};
	
	// starts the timer
	public static void startTimer() {
		// starts the timer after 1 second and then increments by 1 second (units are in milliseconds)
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	// stops the timer
	public static void stopTimer() {
		timer.cancel();
		timer.purge();
	}
	
	// used to retrieve total time
	public static int getTimerTime() {
		return secondsPassed;
	}
	
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
