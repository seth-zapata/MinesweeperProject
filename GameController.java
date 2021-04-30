package application;
import java.io.FileNotFoundException; 
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.MPApp;
 
public class GameController {
	
	// Scene of the current game running
	public Scene sceneCurrGame; 

	// Buttons instantiated by setTimerPopup()
	@FXML
	private Button reset, back, help;

	/* Sets the width of the grid based on the difficulty selected and stored in MPApp
	 * Returns the width
	 */

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
	
	/* Sets the height of the grid based on the difficulty selected and stored in MPApp
	 * Returns the height
	 */
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
	
	// Width and height are dynamically based on the difficulty passed (from setHeight and setWidth methods)
	private final int square_size = 40, width = setWidth(), height = setHeight(), numSquarebyLength = width/square_size, numSquarebyHeight = height/square_size;
	public Pane root;
	public Text scoreTally;
	public int totalSquares = 0, score = 0, totalMines = 0, totalEmptySquares = 0, numericalSquares = 0, flagCounter = 0, tempScore = 0, gameInstance = 0;
	
	int secondsElapsed = 0, timerStart = -1;
	
	public Square[][] gameGrid = new Square[numSquarebyLength][numSquarebyHeight]; // Grid where square data is stored
	
	// Creates a grid based on the passed width/height parameters from MPApp and populates the grid with square data
	public Parent createDynamicGrid() { 
		totalMines = 0;
		root = new Pane();
		root.setPrefSize(width, height);
		
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
			displayFlags();
		}
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
		public boolean isMine, isRevealed = false, mineFlag = false, questionFlag = false;
		
		public int option = 0; // Option for multiple right clicks on a square
				
		public Rectangle square_perimeter = new Rectangle(square_size - 2, square_size - 2); // 
		public Text square_value = new Text(); // Text holds the number of adjacent mines, or an empty string
				
		public Square(int square_xCoord, int square_yCoord, boolean isMine) {
			// Instantiating a square in the game, from createDynamicGrid() most likely
			this.square_xCoord = square_xCoord;
			this.square_yCoord = square_yCoord;
			this.isMine = isMine;
					
			// Sets the visual properties of the square and text
			square_perimeter.setStroke(Color.BLACK);
			square_perimeter.setFill(Color.LIGHTGRAY);
			square_value.setFill(Color.BLACK);
					square_value.setFont(Font.font(18));
					
			if (isMine) {
				// If the square is designated as isMine==true, then set the text to an ""
				square_value.setText("");
			} else {
				square_value.setText(""); // Else just leave the square text blank
				totalEmptySquares++;
			}
					
			square_value.setVisible(false); // Hide the square values at the start of the game
			getChildren().addAll(square_perimeter, square_value); // Add the square to the children nodes of the screen
					
			setTranslateX(square_xCoord * square_size); // Returns the x coordinate of each square, if needed
			setTranslateY(square_yCoord * square_size); // Returns the y coordinate of each square, if needed
					
			// As the mouse is dragged over the square, update the color fill to black
			square_perimeter.setOnMouseEntered(e -> {
				if (isRevealed == false && this.option % 3 == 0) {
				square_perimeter.setFill(Color.BLACK);
				}
			});
			
			// As the mouse is dragged off of the square, update the color fill back to lightgray
			square_perimeter.setOnMouseExited(e -> {
				if (!isRevealed) {
					if (this.option % 3 == 0) {
						square_perimeter.setFill(Color.LIGHTGRAY);
					}
				}
			});
			
			// When mouse is clicked (left or right), do something specific
			square_perimeter.setOnMouseClicked(e -> {
						
				if (e.getButton() == MouseButton.PRIMARY) { // On a left-click, go to reveal() to reveal the square
					try {
						timerStart+=1;
						if (timerStart == 0) {
							// Open dialog box to display timer
							MPApp.startTimer();
						}
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
							Image img1 = new Image("application/flag.png"); // Mine image;
							square_perimeter.setFill(new ImagePattern(img1));
							this.mineFlag = true;
							this.questionFlag = false;
							flagCounter--; // Player uses one attempt at marking a possible mine square
							displayFlags();
									
						}
						if (this.option % 3 == 2) { // Second right-click will mark the square as a question mark (unsure)
							Image img2 = new Image("application/questionmark.png"); // Question mark image
							square_perimeter.setFill(new ImagePattern(img2));
							this.mineFlag = false;
							this.questionFlag = true;
						}
						if (this.option % 3 == 0) { // Third right-click will just black out the square as it originally was
							flagCounter++; // Player gains back the attempt at flagging the square for a mine
							displayFlags();
							this.mineFlag = false;
							this.questionFlag = false;
							square_perimeter.setFill(Color.LIGHTGRAY); // Covers the square again
						}
					}
				}
			});
		}
		
		/* On left mouse click, or win/lose, this function
		 * will reveal the necessary sqaures based on the condition
		 * passed by user (mouse click, win/lose), as well as 
		 * special displays for flags and missed bombs.
		 */
		public void reveal() throws IOException, InterruptedException {
			Image img = new Image("application/Mine.png"); // Mine image;
			Image imgFlagMineFail = new Image("application/flagMineFail.png"); // Mine flag fail image;
	
			if(isRevealed) {
				return; // Square is already visible
			}
					
			if (isMine) {
				for (int y = 0; y < numSquarebyHeight; y++) {
					for (int x = 0; x < numSquarebyLength; x++) {
						Square sqr = gameGrid[x][y]; // Goes through all squares in the grid
						if (sqr.isMine) { // Reveals all mines only when game is over (mine square is clicked)
							sqr.isRevealed = true;
							sqr.square_value.setVisible(true);
							sqr.square_perimeter.setFill(new ImagePattern(img));
							if (sqr.questionFlag) {
								sqr.isRevealed = true;
								sqr.square_value.setVisible(true);
								sqr.square_perimeter.setFill(new ImagePattern(imgFlagMineFail));
							}
						} 
						else {
							if (sqr.mineFlag) { // Player marked a square with a bomb flag, but it wasn't a bomb
										
							Image imgFail = new Image("application/unsureOfMineFail.png");
										
							sqr.isRevealed = true;
							sqr.square_value.setVisible(false);
							sqr.square_perimeter.setFill(new ImagePattern(imgFail));
							}
							if (sqr.questionFlag) { // Player marked a square with a question flag, but it was just a safe square
								sqr.isRevealed = true;
								sqr.square_value.setVisible(true);
								sqr.square_perimeter.setFill(Color.RED);
							}
									
						}
						sqr.square_perimeter.setOnMouseClicked(null); // Disables mouse clicks on squares since game is over
					}
				}
						
				MPApp.updateScore(score); // Update (pseudo) score
				score = 0;
				totalMines = 0;
				numericalSquares = 0;
				totalSquares = 0;
				flagCounter = 0;
				timerStart = -1;
				gameInstance+=1;
				
				lostFace(); // Display lost face on button reset in popup window
				MPApp.stopTimerGame(); // Stop the timer in the game
				MPApp.resetTime(); // Reset secondsPasssed variable in model file
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
					score+=1;
				}
			}
			if (score == numericalSquares) { // If the score is equal to all possible numberically filled squares, all mines are found, and game is over
						
				for (int y = 0; y < numSquarebyHeight; y++) {
					for (int x = 0; x < numSquarebyLength; x++) {
						Square sqr = gameGrid[x][y]; // Goes through all squares in the grid
						if (sqr.isMine) { // Reveals all mines only when game is over (mine square is clicked)
							sqr.isRevealed = true;
							sqr.square_value.setVisible(true);
							sqr.square_perimeter.setFill(new ImagePattern(img));
						}
						if (sqr.square_value.getText().isEmpty()) { // Case where square is empty, reveal the empty square
							isRevealed = true;
							square_value.setVisible(true);
							square_perimeter.setFill(null);
								}
								sqr.square_perimeter.setOnMouseClicked(null); // Disables mouse clicks on squares since game is over
							}
						}
						
				MPApp.updateScore(score);
				score = 0;
				totalMines = 0;
				numericalSquares = 0;
				totalSquares = 0;
				flagCounter = 0;
				gameInstance+=1;
				
				coolFace();
				MPApp.stopTimerGame();
				MPApp.addUserInfo();
				MPApp.resetTime();
				
				return;
			}
			
			// If the selected square is empty, reveal all other empty adjacent squares recursively
			if(square_value.getText().isEmpty()) { 
				getAdjacentSquares(this).forEach(arg0 -> { // forEach() goes through list of adjacent squares
					try {
						arg0.reveal(); // Will reveal adjacent squares of adjacent squares (recursive reveal)
					} catch (IOException e) { // Handles the IOException from possibly writing the score to a file
						e.printStackTrace();
					} catch (InterruptedException e) { // Handles error from TimeUnit utility
						e.printStackTrace();
					}
				});
			}
		}
	}
	
	
	// Pass the scene from the Start Controller so we can reset the game screen using sceneCurrGame.setRoot(createDynamicGrid())
	public void passScene(Scene sceneGame) {
		sceneCurrGame = sceneGame; 
	}
	
		int secondsPassed = 0;
		
		Text time = new Text();
		
		Text flagsText = new Text();
		
		VBox box = new VBox();
		
		Stage smallStage = new Stage();
		Scene smallScene;
	
		
	/* Creates a new stage that will dynamically 
	 * display the timer and the flag counter
	 * for the game. Also creates buttons for
	 * going to home, rules, and resetting the 
	 * game on win/lose.
	 */
	public void setTimerPopup() throws FileNotFoundException {
		// Set text fonts and fills
		time.setFont(Font.font("ka1.tff", FontWeight.BOLD, FontPosture.REGULAR, 40));
		time.setFill(Color.RED);
		flagsText.setFont(Font.font("ka1.tff", FontWeight.BOLD, FontPosture.REGULAR, 40));
		flagsText.setFill(Color.RED);
		
		Image image = new Image("application/happy.jpg"); // Image displayed on reset button while game is not lost
		ImageView imageView = new ImageView(image);
		
		imageView.setFitHeight(105);
		imageView.setFitWidth(105);
		imageView.setPreserveRatio(false);
		
		//Set reset button format
		reset = new Button();
		reset.setMinSize(100, 100);
		reset.setMaxSize(100, 100);
		
		reset.setTranslateX(220);
		reset.setTranslateY(-102);
		reset.setGraphic(imageView);
		
		// On mouse click of the home button, stop game and go back home
		EventHandler <ActionEvent> home = new EventHandler <ActionEvent>() {
			@FXML
			public void handle(ActionEvent e) {
				MainController homeScreen = new MainController();
				Stage stage = (Stage) back.getScene().getWindow();
				try {				
					stage.close(); // Close the popup window
					MPApp.closeScene();
					
					if (MPApp.getTimerCheck()) { 
						MPApp.stopTimerGame();
					}
					
					homeScreen.changeScreenonBack(e); // Go back home
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		// On mouse click of the help button, stop game and go to the rules screen
		EventHandler <ActionEvent> rules = new EventHandler <ActionEvent>() {
			@FXML
			public void handle(ActionEvent e) {
				MainController rulesScreen = new MainController();
				Stage stage = (Stage) help.getScene().getWindow();
				try {
					stage.close(); // Close the popup window
					MPApp.closeScene();
					
					if (MPApp.getTimerCheck()) { 
						MPApp.stopTimerGame();
					}
					
					rulesScreen.changeScreenonRules(e); // Go to rules screen
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		// Set format for back "home" button
		back = new Button("HOME");
		back.setMinSize(90, 50);
		back.setMaxSize(90, 50);
		
		back.setTranslateX(20);
		back.setTranslateY(-82);
		
		// Set format for help button
		help = new Button("HELP");
		help.setMinSize(90, 50);
		help.setMaxSize(90, 50);
		help.setTranslateX(220);
		help.setTranslateY(-132);
		
		// When the player clicks the reset button
		EventHandler <ActionEvent> event = new EventHandler <ActionEvent>() {
			public void handle(ActionEvent e) {
				score = MPApp.getScore();
				score+=1; // Case where player didn't get a single square revealed (first mine is a bomb);
				
				try {
					if (gameInstance > 0) {
						// Reset timer, counters, graphic on button, and texts
						flagCounter = 0;
						score = 0;
						MPApp.stopTimerGame();
						reset.setGraphic(imageView);
						time.setText("Time: 0");
						sceneCurrGame.setRoot(createDynamicGrid()); // Create a new game grid instance
					}
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		};
		
		// On button action, do specific tasks
		reset.setOnAction(event);
		back.setOnAction(home);
		help.setOnAction(rules);
		
		// Set initial text content
		time.setText("Time: " + secondsPassed);
		flagsText.setText("Flags: " + flagCounter);
		
		// Add objects to the Vbox
		box.getChildren().addAll(time, flagsText, imageView, reset, back, help);
		
		box.setStyle("-fx-background-color: black");
		
		// Add Vbox to scene, and set scene style
		smallScene = new Scene(box, 350, 200);
		smallScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		
		smallStage.setScene(smallScene);
		smallStage.setTitle("Minesweeper: Timer and Flags");
		smallStage.getIcons().add(new Image("application/minesweeperIcon.jpg"));
		
		// Create the appropriate window size in reference to size of grid game (based on difficulty)
		smallStage = MPApp.setPopupWindowSize(smallStage);

		MPApp.getTime(time);
		smallStage.show(); // Show the small window
	}
	
	// Updates the flags counter in popup window when 
	// player right clicks on a square; display only
	public void displayFlags() {
				flagsText.setText("Flags: " + flagCounter);
			}
	
	// Creates the lost face image on reset button in the popup
	// window, when the player loses
	public void lostFace() {
				Image image = new Image("application/lost.png");
				ImageView imageView = new ImageView(image);
				
				imageView.setFitHeight(130);
				imageView.setFitWidth(130);
				imageView.setPreserveRatio(false);
				reset.setGraphic(imageView); // Change the image on button
			}
	
	// Creates the cool face image on reset button in the popup
	// window, when the player wins
	public void coolFace() {
			Image image = new Image("application/cool.png");
			ImageView imageView = new ImageView(image);
				
			imageView.setFitHeight(115);
			imageView.setFitWidth(110);
			imageView.setPreserveRatio(false);
			reset.setGraphic(imageView); // Change the image on button
	}
}