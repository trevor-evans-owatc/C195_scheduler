package main;
/**
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

/**
 * @author Trevor Evans
 * Purpose: This project is a schedualing software to be used on a desktop enviroment. The
 * build is to demonstrate the skills attained from c195; advanced java Concepts
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import dbAccess.JDBC;

/**
 * the Main class of the program functions as
 * the driver to launch the GUI applications of the program
 */
public class Main extends Application{
    /**
     * Start activates the top level java and fxml files
     * @param mainStage is the primary mainStage
     * @throws IllegalStateException occurs if instance is called multiple times
     * @throws RuntimeException clause to catch runtime exceptions
     *
     * Having errors where the application couldn't launch. After some research I found that the
     * FXML library isn't joind by default in versions of java later than 13 (this being built with
     * java 17) I imported the javafx-sdk.lib and I added the file path and import controls in Intellij's
     * VM.
     *
     * after adding the imports I still was getting errors on loading the main class when it called the
     * login.fxml file. I wasn't sure if it was a library import issue or some thing wrong with my code.
     * to resolve this I created a tester.fxml and a tester control class that I could call instead of the
     * login.fxml file. I found that javafx as a whole is working. That let me know that I can look for typos
     * in my code. I found that the string in the getRecource had \s instead of /s
     */
    @Override
    public void start(Stage mainStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.fxml"));
        mainStage.setTitle("login");
        mainStage.setScene(new Scene(root, 500, 500));
        mainStage.show();
    }

    /**
     * main for driving the program
     * @param args command line arguments
     */
    public static void main(String[] args){
        JDBC.openConnection();
        launch(args);
        JDBC.closeConnection();
    }

}

