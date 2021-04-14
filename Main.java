package application;
	
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;

public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			
			FXMLLoader load = new FXMLLoader(getClass().getResource("Main.fxml"));
			Parent root = (Parent) load.load();
			root.setId("Home");
			
			MainController control = new MainController();
			control = load.getController();
			// Fill in controller methods to be called
			// These methods will be to globalize data (such as final score and time of game)

			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Minesweeper: Home");
			primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("minesweeperIcon.jpg")));
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
