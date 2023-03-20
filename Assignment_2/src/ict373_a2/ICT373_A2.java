package ict373_a2;

import javafx.application.Application;
import javafx.stage.Stage;

/**
 * Assignment 2
 * @author Anson Ting Lik Yuan
 * Unit 			: ICT 373
 * Student Number               : 34212178
 * Date 			: 26 May 2022
 */

/**
 * 
 * Main class.
 * 
 * This class to call and run the application.
 * 
 */
public class ICT373_A2 extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
		// Call out the View class
        new View(stage);
    }
    
    public static void main(String[] args) {
		// Launch!
        launch(args);
    }
}
