package application;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import model.MPApp;
 
public class GameController {
	
	// TODO: Get buttons to display on the same screen as the game
	// This may have to be done without Scenebuilder, but that's not for certain
	
	@FXML
	private Button reset, back, help;
	
	/* All of the three screen change methods below
	 * should be displayed as buttons on the game screen.
	 */
	
	@FXML 
	public void changeScreenonBack(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonBack(event);
	}
	
	@FXML 
	public void changeScreenonReset(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonBack(event);
	}
	
	@FXML 
	public void changeScreenonHelp(ActionEvent event) throws IOException {
		MainController home = new MainController();
		home.changeScreenonRules(event);
	}
	
	
	// Sets the width of the grid based on the difficulty selected and stored in MPApp
	// These sizes can be changed if needed
	// Returns the width
	public int setWidth() {
		int width = 0;
		if (MPApp.getPassedValue().equals("Beginner")) {
			//System.out.println("Beginner was selected");
			width = 400;
		}
		if (MPApp.getPassedValue().equals("Intermediate")) {
			//System.out.println("Intermediate was selected");
			width = 600;
		}
		if (MPApp.getPassedValue().equals("Advanced")) {
			//System.out.println("Advanced was selected");
			width = 800;
		}
		return width;
	}
	
	// Sets the height of the grid based on the difficulty selected and stored in MPApp
	// These sizes can be changed if needed
	// Returns the height
	public int setHeight() {
		int height = 0;
		if (MPApp.getPassedValue().equals("Beginner")) {
			//System.out.println("Beginner was selected");
			height = 200;
		}
		if (MPApp.getPassedValue().equals("Intermediate")) {
			//System.out.println("Intermediate was selected");
			height = 400;
		}
		if (MPApp.getPassedValue().equals("Advanced")) {
			//System.out.println("Advanced was selected");
			height = 600;
		}
		return height;
	}
	
	private final int square_size = 40, width = setWidth(), height = setHeight(), numSquarebyLength = width/square_size, numSquarebyHeight = height/square_size;
	public Pane root;
	public Scene sceneCurrGame;
	public Text scoreTally;
	public int totalSquares = 0, score = 0, totalMines = 0, totalEmptySquares = 0, numericalSquares = 0, flagCounter = 0, tempScore = 0;
	
	int secondsElapsed = 0;
	
	// Timer components instantiated here
	
	public void resetGameTimer() {
		Timer gameResetTimer = new Timer();
		gameResetTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("resetGameTimer method called");
				sceneCurrGame.setRoot(createDynamicGrid());
			}
		}, 10000);
	}
	
	Timer timer = new Timer();
	TimerTask task = new TimerTask() {
		
		public void run() { // Runs the timer task
			secondsElapsed++;
			System.out.println("Time elapsed: " + secondsElapsed + " seconds"); // Counts how long game has lasted
				
		}
	};
	
	public Square[][] gameGrid = new Square[numSquarebyLength][numSquarebyHeight]; // Grid where square data is stored
	
	// Creates a grid based on the passed width/height parameters from MPApp and populates the grid with square data
	public Parent createDynamicGrid() { 
		root = new Pane();
		root.setPrefSize(width, height + 200);
		
		for (int y = 0; y < numSquarebyHeight; y++) {
			for (int x = 0; x < numSquarebyLength; x++) {
				double chanceOfMine = Math.random(); // Random double to create randomness of mine
				Square sqr = new Square(x, y, chanceOfMine < 0.2);
				
				gameGrid[x][y] = sqr;
				root.getChildren().add(sqr); // Adds the square to the root pane
				totalSquares++; // Counts actual number of total squares
			}
		}
		
		for (int y = 0; y < numSquarebyHeight; y++) {
			for (int x = 0; x < numSquarebyLength; x++) {
				Square sqr = gameGrid[x][y];
				
				if(sqr.isMine) { // We can't check these squares for adjacent mines since they hold no text value (an int)
					totalMines++;
					continue;
				}
				
				// Checks the surrounding squares around the current squares (their boolean isMine properties),
				// and returns how many squares have mines in them.
				long mines = getAdjacentSquares(sqr).stream().filter(s -> s.isMine).count(); 
				
				if (mines > 0) {
				sqr.square_value.setText(String.valueOf(mines)); // Set the current square's text to the value of adjacent mines
				numericalSquares++;
				}
			} flagCounter = totalMines;
		}
		
		// TODO: Add score text box to pane
		
		/*
		scoreTally = new Text("Score: ");
		scoreTally.setTextAlignment(TextAlignment.CENTER);
		scoreTally.setFont(Font.font(18));
		root.getChildren().add(scoreTally);
		StackPane.setAlignment(scoreTally, Pos.BOTTOM_LEFT); */
		return root;
	}
	
	public List<Square> getAdjacentSquares(Square sqr) {
		List <Square> adjacentSquare = new ArrayList<>();
		
		// An array of all possible locations from the current square to an adjacent square
		// -1, -1 represents an adjacent square to the left and down
		//  0, 1 represents an adjacent square to the right 
		// 1, 1 represents an adjacent square up and to the right
		int[] allAdjacentSquaresCoords = new int[] { -1, -1, -1, 0, -1, 1, 
				0, -1, 0, 1,
				1, -1, 1, 0, 1, 1
		};
		
		for (int i = 0; i < allAdjacentSquaresCoords.length; i++) {
			int x_Coord = allAdjacentSquaresCoords[i]; // Store the location of an adjacent x coordinate square 
			int y_Coord = allAdjacentSquaresCoords[++i]; // Store the location of an adjacent y coordinate square 
			
			int neighborX_Coord = sqr.square_xCoord + x_Coord; // Store the x coordinate of the current square + x coordinate of possible adjacent square
			int neighborY_Coord = sqr.square_yCoord + y_Coord; // Store the y coordinate of the current square + y coordinate of possible adjacent square
			
			boolean result = isValidCoord(neighborX_Coord, neighborY_Coord, numSquarebyLength, numSquarebyHeight); // Check if the coordinates are valid
			if(result) {
				adjacentSquare.add(gameGrid[neighborX_Coord][neighborY_Coord]); // If coordinates are valid, add the coordinates to a list of adjacent square coordinates
			}
			
		}
		
		return adjacentSquare; // Return the list of adjacent sqaure coordinates
	}
	
	// Check if adjacent square coordinates are valid before adding to new list of adjacent square coordinates
	public boolean isValidCoord(int x, int y, int numSquaresLength, int numSquaresHeight) {
		if (x < numSquaresLength && x >= 0 && y < numSquaresHeight && y >= 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public class Square extends StackPane {
		
		// Properties of a square in the game
		public int square_xCoord;
		public int square_yCoord;
		public boolean isMine, isRevealed = false;
		
		public int option = 0, timerStart = -1; // Option for multiple right clicks on a square, and validating the timer to start on a revealed square click
		
		public Rectangle square_perimeter = new Rectangle(square_size - 2, square_size - 2); // 
		public Text square_value = new Text(); // Text holds the number of adjacent mines, or an empty string
		
		public Square(int square_xCoord, int square_yCoord, boolean isMine) {
			// Instantiating a square in the game, from createDynamicGrid() most likely
			this.square_xCoord = square_xCoord;
			this.square_yCoord = square_yCoord;
			this.isMine = isMine;
			
			// Sets the visual properties of the square and text
			square_perimeter.setStroke(Color.BLACK);
			square_value.setFill(Color.GRAY);
			square_value.setFont(Font.font(18));
			
			if (isMine) {
				square_value.setText("X"); // If the square is designated as isMine==true, then set the text to an "X"
			} else {
				square_value.setText(""); // Else just leave the square text blank
				totalEmptySquares++;
			}
			
			square_value.setVisible(false); // Hide the square values at the start of the game
			getChildren().addAll(square_perimeter, square_value); // Add the square to the children nodes of the screen
			
			setTranslateX(square_xCoord * square_size); // Returns the x coordinate of each square, if needed
			setTranslateY(square_yCoord * square_size); // Returns the y coordinate of each square, if needed
			
			square_perimeter.setOnMouseClicked(e -> {
				if (e.getButton() == MouseButton.PRIMARY) { // On a left-click, go to reveal() to reveal the square
					try {
						reveal();
					} catch (IOException e1) { // Handles IO Exception from when the score is added to a file in reveal(), when game finishes
						e1.printStackTrace();
					} catch (InterruptedException e1) { // Handles error from TimeUnit utility
						e1.printStackTrace();
					}
				}
				if (e.getButton() == MouseButton.SECONDARY) { // On a right-click, go through several possible options (repeats options using modulo)
					if (!isRevealed) { // If the square hasn't been revealed yet
						this.option = this.option + 1;
						if (this.option % 3 == 1) { // First right-click will mark the square as a possible mine
							Image img = new Image("application/unsureOfMine.png"); // Mine image
							square_perimeter.setFill(new ImagePattern(img));
							flagCounter--; // Player uses one attempt at marking a possible mine square
							System.out.println("Flag counter: " + flagCounter);
						}
						if (this.option % 3 == 2) { // Second right-click will mark the square as a question mark (unsure)
							Image img = new Image("application/questionmark.png"); // Question mark image
							square_perimeter.setFill(new ImagePattern(img));
						}
						if (this.option % 3 == 0) { // Third right-click will just black out the square as it originally was
							flagCounter++; // Player gains back the attempt at flagging the square for a mine
							System.out.println("Flag counter: " + flagCounter);
							square_perimeter.setFill(Color.BLACK); // Covers the square again
						}
					}
				}
			});
		}
		public void reveal() throws IOException, InterruptedException {
			if(isRevealed) {
				return; // Square is already visible
			}
			
			timerStart++;
			
			if (timerStart == 0) { 
				//start(); Starts the timer when the first click is made (excluding empty squares)
			}
			
			if (isMine) {
				for (int y = 0; y < numSquarebyHeight; y++) {
					for (int x = 0; x < numSquarebyLength; x++) {
						Square sqr = gameGrid[x][y]; // Goes through all squares in the grid
						if (sqr.isMine) { // Reveals all mines only when game is over (mine square is clicked)
							sqr.isRevealed = true;
							sqr.square_value.setVisible(true);
							sqr.square_perimeter.setFill(null);
						}
						sqr.square_perimeter.setOnMouseClicked(null); // Disables mouse clicks on squares since game is over
					}
				}
				//end(); This will end the timer when a mine square is clicked. FIXME: Errors with multiple timer tasks
				System.out.println("Game Over: You lost :(");
				System.out.println("Final Score: " + score);
				MPApp.setScore(score); // Sets the score so High Scores Screen can grab it
				MPApp.addScore(score); // Adds the score to a text file 
				
				score = 0;
				totalMines = 0;
				numericalSquares = 0;
				totalSquares = 0;
				flagCounter = 0;
				
				//System.out.println("10 seconds until game restarts");
				//resetGameTimer();
				sceneCurrGame.setRoot(createDynamicGrid());
				return;
			} else { // Case where game recursively searches for empty squares; we don't want to update score
				if (square_value.getText().isEmpty()) {
					// Do nothing
					isRevealed = true;
					square_value.setVisible(true);
					square_perimeter.setFill(null);
				} else {
					// Reveals a square that just has a number 
					isRevealed = true;
					square_value.setVisible(true);
					square_perimeter.setFill(null);
					System.out.println("Score updated by 1");
					score++;
				}
			}
			if (score == numericalSquares) { // If the score is equal to all possible numberically filled squares, all mines are found, and game is over
				
				for (int y = 0; y < numSquarebyHeight; y++) {
					for (int x = 0; x < numSquarebyLength; x++) {
						Square sqr = gameGrid[x][y]; // Goes through all squares in the grid
						if (sqr.isMine) { // Reveals all mines only when game is over (mine square is clicked)
							sqr.isRevealed = true;
							sqr.square_value.setVisible(true);
							sqr.square_perimeter.setFill(null);
						}
						sqr.square_perimeter.setOnMouseClicked(null); // Disables mouse clicks on squares since game is over
					}
				}
				
				System.out.println("You Win!");
				System.out.println("Final Score " + score);
				MPApp.setScore(score); // Sets the score so High Scores Screen can grab it
				MPApp.addScore(score); // Adds the score to a text file 
				
				score = 0;
				totalMines = 0;
				numericalSquares = 0;
				totalSquares = 0;
				flagCounter = 0;
				
				//System.out.println("10 seconds until game restarts");
				//resetGameTimer(); // Wait for 10 seconds before restarting game
				//sceneCurrGame.setRoot(createDynamicGrid());
				return;
			}
			
			// If the selected square is empty, reveal all other empty adjacent squares recursively
			if(square_value.getText().isEmpty()) { 
				getAdjacentSquares(this).forEach(arg0 -> { // forEach() goes through list of adjacent squares
					try {
						arg0.reveal(); // Will reveal adjacent squares OF adjacent squares (recursive reveal)
					} catch (IOException e) { // Handles the IOException from possibly writing the score to a file
						e.printStackTrace();
					} catch (InterruptedException e) { // Handles error from TimeUnit utility
						e.printStackTrace();
					}
				});
			}
		}
	}

	public void passScene(Scene sceneGame) {
		sceneCurrGame = sceneGame; // Pass the scene from the Start Controller so we can reset the game screen using sceneCurrGame.setRoot(createDynamicGrid())
		
	}
}