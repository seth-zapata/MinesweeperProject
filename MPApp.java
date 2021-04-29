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
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MPApp {
	
	
	// holds the amount of seconds that have passed
	static int secondsPassed = 0, score = 0;
	
	// holds difficulty selected by user
	static String passedDifficultyValue;
	
		// new instance of timer
	static Timer timer;
	
	// this is the task that the timer will execute
	static TimerTask task; 
	
	static Pane gameRoot;
	
	static Scene gameScene;
	
	static Button reset;
	
	static ActionEvent event;
	
	static Text time;
	
	public static String userName = "";
	
	public static void updateSeconds(int seconds) {
		secondsPassed = seconds;
	}
	
	public static void updateScore(int passedScore) {
		score = passedScore;
	}
	
	public static int getScore() {
		return score;
	}
	
	public static void getPaneRoot(Pane root) {
		gameRoot = root;
	}
	
	public static void getTime(Text t) {
		time = t;
	}
		
	// starts the timer
	public static void startTimer() {
		task = new TimerTask() {
			@Override
			public void run() {
				//System.out.println("time elapsed: " + secondsPassed + " seconds");
				secondsPassed++;
				time.setText("Time: " + secondsPassed);
			}
		};
		timer = new Timer();
		// starts the timer after 1 second and then increments by 1 second (units are in milliseconds)
		timer.scheduleAtFixedRate(task, 1000, 1000);
	}
	
	// stops the timer
	public static void stopTimerGame() throws IOException {
		updateSeconds(secondsPassed);
		timer.cancel();
		timer.purge();
		task.cancel();
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
		
		// add username to file
		/*
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
		*/
	public static void addUserInfo() throws IOException {
		File file = new File("userInfo.txt");

		// if file doesn't exists make it  
		if(!file.exists()) {
			file.createNewFile();
		}
				
		FileWriter writer = new FileWriter(file, true);
		String val = getPassedValue();
		String sec = String.valueOf(secondsPassed);
		// write username to file
		String info = String.format("%-6s %-14s  %s", userName, val, sec);
		writer.write(info + System.lineSeparator());
		//writer.write(userName + " " + val + " " + sec + System.lineSeparator());
		
        writer.close();
		
	}
	public static void addTime() throws IOException {
		//File file = new File("times.txt");

		// if file doesn't exists make it  
		//if(!file.exists()) {
			//file.createNewFile();
		//}
				
		//FileWriter writer = new FileWriter(file, true);
			
		//System.out.println("secondsPassed is " + secondsPassed);
		// write username to file
		//writer.write(secondsPassed + System.lineSeparator());
        //writer.close();
        
        updateSeconds(0); // Reset seconds variable
	}

	public static void setScene(Scene sceneGame) {
		gameScene = sceneGame;
		
	}
	
	public static Scene getScene() {
		return gameScene;
		
	}
	
	public static void closeScene() {
		Stage stage = (Stage) gameScene.getWindow();
		stage.close();
	}
	
}
