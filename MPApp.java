// This model file should go in the model package of the project files

package model;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;


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
	public static boolean checkIfUsernameEntered(String userName) throws IOException {
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
		else if(Pattern.matches("[A-Z]{3}", userName) == false) {
			Alert a = new Alert(AlertType.ERROR);
			a.setTitle("Error");
			a.setHeaderText("Invalid username format");
			a.setContentText("Please enter 3 uppercase characters");
			a.show();
			return true;
		
		}
		else if(Pattern.matches("[A-Z]{3}", userName)) {
			File file=new File("userIDs.properties");
			FileInputStream reader=new FileInputStream(file);
			Properties properties=new Properties();
			properties.load(reader);
			reader.close();
			if(properties.containsKey(userName)) {
				Alert a = new Alert(AlertType.ERROR);
				a.setTitle("Error");
				a.setHeaderText("Username taken");
				a.setContentText("Please enter new username");
				a.show();
				return true;
			}
			else {
				return false;
			}

		}
		
		return false;
	}
	
	// add username to file
	public static void addUserName(String username) throws IOException {
		HashMap<String, String> hash=new HashMap<String,String>();
		Properties properties=new Properties();
		String val = getPassedValue();
		String complete = String.format("%-14s%s", val, "20secs");
		hash.put(username, complete);
		properties.putAll(hash);
		File file=new File("userIDs.properties");
		FileOutputStream writer=new FileOutputStream(file,true);
		properties.store(writer, null);
		writer.close();

	}

	public static void updateScore(int score) {
		// TODO Auto-generated method stub
		
	}

	public static void stopTimerGame() {
		// TODO Auto-generated method stub
		
	}

	public static void addTime() {
		// TODO Auto-generated method stub
		
	}

	public static void setScene(Scene sceneGame) {
		// TODO Auto-generated method stub
		
	}

	public static void closeScene() {
		// TODO Auto-generated method stub
		
	}

	public static void getTime(Text time) {
		// TODO Auto-generated method stub
		
	}

	public static int getScore() {
		// TODO Auto-generated method stub
		return 0;
	}
}
