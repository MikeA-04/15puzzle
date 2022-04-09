// Name:	Mike Apreza
// Project Description: This project contains the program to play a puzzle called "15 puzzle."
//			A window will appear to welcome the player for 3 seconds before puzzle #1 is shown,
//			which needs to be solved. The user has a choice of manually solving it or using
//			algorithms called H1 or H2. It will show the next 10 moves.

import java.io.IOException;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

public class JavaFXTemplate extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
            // Read file fxml and draw interface.
            Parent root = FXMLLoader.load(getClass().getResource("/FXML/15PuzzleWelcomeScene.fxml"));
 
            primaryStage.setTitle("15 Puzzle");
            Scene scene = new Scene(root, 700, 700);
            primaryStage.setScene(scene);
            
            // Switch screen after 3 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(3));
            
			pause.setOnFinished(e->{
				try {
					Parent r = FXMLLoader.load(getClass().getResource("/FXML/15PuzzleGameScene.fxml"));
					Scene s = new Scene(r, 700, 700);
		            primaryStage.setScene(s);
		            primaryStage.show();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				});
			
			primaryStage.show();
			pause.play();
         
        } catch(Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

	}

}
