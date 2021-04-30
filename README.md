# MinesweeperProject
Minesweeper game implementation through JavaFX Project

Name of your team: Minesweepers 

Team member names: Seth Zapata, Jhonathan Mejia, Paul Perryman, Karla De La Cruz

Short description of the app: For our project we’ve recreated the game Minesweeper. The main screen will give the user 3 buttons to click; (1) To play the game. (2) To go over the rules of the game. (3) To look at the highscores. 

Known bugs: 
     When a successful game is completed, and the time elapsed is less than 100 seconds, then the highscore screen will incorrectly order the ranking of the player because of          their time, but will still rank within the difficulty they played in. For example, Rank 1 played a Beginner game with 110 second completion. Rank 2 played a Beginner game          with 50 seconds completion. Rank 3 played an Intermediate game with 150 seconds completion. Rank 2 player will still rank lower than Rank 1 because of their score being            lower than 100, but rank high than Rank 3 because of their difficulty.

Log in info: Once the start button is clicked the user will be taken to the difficulties scene and will be required to enter a username. If the user wins the game the username will be shown in the highscores scene.

Versions or other requirements: The app was built using Java 8 and Scene Builder 2.0. All files (except MPApp.java, winner.GIF, and userInfo.txt) should be placed inside the application package in Eclipse.
    - "MPApp.java" will go in the model package.
    - "userInfo.txt" and "winner.GIF" should be placed in the "MinesweeperProject" folder where "bin" and "src" are found.
