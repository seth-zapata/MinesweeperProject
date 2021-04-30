// This model file should go in the model package of the project files

package model;
import java.io.File; 
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.text.Text;
import javafx.stage.Stage;
 

public class MPApp {
	
	
	// Holds the amount of seconds that have passed
	static int secondsPassed = 0, score = 0;
	
	// Holds difficulty selected by user
	static String passedDifficultyValue;
	
	// New instance of timer
	static Timer timer;
	
	// This is the task that the timer will execute
	static TimerTask task; 
	
	// Copied instance of the game scene from createDynamicGrid()
	static Scene gameScene;

	// Instance of the time counter text
	static Text time;
	
	// Instance of the userName
	public static String userName = "";

	// Instance of the Y coordinate of the game scene
	static double sceneY;

	// Instance of the X coordinate of the game scene
	static double sceneX;

	// Instances of the game screen stage, and popup window stage
	static Stage gameStage, popupStage;

	// Instace of the timerCheck; if timer is still active or not
	static boolean timerCheck;
	
	// Updates the seconds elapsed based on passed seconds value
	public static void updateSeconds(int seconds) {
		secondsPassed = seconds;
	}
	
	// Updates the (pseudo) score passed on passed score value
	public static void updateScore(int passedScore) {
		score = passedScore;
	}
	
	// Returns the current instance of (pseudo) score
	public static int getScore() {
		return score;
	}
	
	// Updates the current instance of the time counter text, based on input text
	public static void getTime(Text t) {
		time = t;
	}
		
	// Starts the timer
	public static void startTimer() {
		task = new TimerTask() {
			@Override
			public void run() {
				secondsPassed++;
				time.setText("Time: " + secondsPassed);
			}
		};
		timer = new Timer();
		// Starts the timer after 1 second and then increments by 1 second (units are in milliseconds)
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	// Stops the timer
	public static void stopTimerGame() throws IOException {
		updateSeconds(secondsPassed);
		timer.cancel();
		timer.purge();
		task.cancel();
	}
	
	// Used to retrieve total time
	public static int getTimerTime() {
		return secondsPassed;
	}
	
	// Sets what difficulty the user selected
	public static void setPassedValue(String passedDifficultyValueTemp) {
		passedDifficultyValue = passedDifficultyValueTemp;
	}
	
	// Gets what difficulty user selected; can be used by start scene to modify difficulty
	public static String getPassedValue() {
		return passedDifficultyValue;
	}
	
	// Check if user entered a "username"
		public static boolean checkIfUsernameEntered(String userName) throws IOException {
			// If no username is entered
			if(userName.trim().isEmpty()) {
				// Display no "username" alert
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
				try {
					File file=new File("userInfo.txt");
					@SuppressWarnings("resource")
					Scanner scnr=new Scanner(file);
					while(scnr.hasNextLine()) {
						String line = scnr.nextLine();
						if(line.contains(userName)) {
							Alert a = new Alert(AlertType.ERROR);
							a.setTitle("Error");
							a.setHeaderText("Username taken");
							a.setContentText("Please enter new username");
							a.show();
							return true;
						}
					}
					scnr.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					
				}
			}
			
			return false;
		}
		
	// Adds username to userInfo.txt in a specific format
	public static void addUserInfo() throws IOException {
		File file = new File("userInfo.txt");

		// If file doesn't exists make it  
		if(!file.exists()) {
			file.createNewFile();
		}
				
		FileWriter writer = new FileWriter(file, true);
		String val = getPassedValue();
		String sec = String.valueOf(secondsPassed);
		// Write username to file
		String info = String.format("%-6s %-14s  %s", userName, val, sec);
		writer.write(info + System.lineSeparator());
		
		
        writer.close();
		
	}
	// Resets the secondsPassed back to zero
	public static void resetTime() throws IOException {
		
        updateSeconds(0); // Reset seconds variable
		
	}
	// Updates the static instance of the scene of the game
	public static void setScene(Scene sceneGame) {
		gameScene = sceneGame;
		
	}
	// Returns the current instance of the scene of the game
	public static Scene getScene() {
		return gameScene;
		
	}
	
	// Close the current instance of the gameScene
	public static void closeScene() {
		Stage stage = (Stage) gameScene.getWindow();
		stage.close();
	}
	
	// Pass the X coordinate of the small window scene
	public static void passX(double x) {
		sceneX = x;
		
	}
	
	// Pass the Y coordinate of the small window scene
	public static void passY(double y) {
		sceneY = y;
		
	}

	// Set new window size of gameStage based on difficulty
	// Return the updated gameStage
	public static Stage setGameWindowSize(Stage stage) {
		gameStage = stage;
		if (passedDifficultyValue.equals("Beginner")) {
			gameStage.setX(500);
			gameStage.setY(300);
		}
		if (passedDifficultyValue.equals("Intermediate")) {
			gameStage.setX(500);
			gameStage.setY(150);
		}
		if (passedDifficultyValue.equals("Advanced")) {
			gameStage.setX(300);
			gameStage.setY(200);
		}
		
		return gameStage;
		
	}

	// Set new window size of popupStage based on difficulty
	// Return the updated popupStage
	public static Stage setPopupWindowSize(Stage stage) {
		popupStage = stage;
		if (passedDifficultyValue.equals("Beginner")) {
			popupStage.setX(1000);
			popupStage.setY(300);
		}
		if (passedDifficultyValue.equals("Intermediate")) {
			popupStage.setX(1200);
			popupStage.setY(150);
		}
		if (passedDifficultyValue.equals("Advanced")) {
			popupStage.setX(1200);
			popupStage.setY(200);
		}
		
		return popupStage;
		
	}
	
	// Update the current instance of the boolean timerCheck
	public static void setTimerCheck(boolean passedTimerCheck) {
		timerCheck = passedTimerCheck;
	}
	
	// Return the current instance of timerCheck
	public static boolean getTimerCheck() {
		return timerCheck;
	}
	
}
