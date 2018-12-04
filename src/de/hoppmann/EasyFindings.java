/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann;

import de.hoppmann.gui.view.mainView.MainViewController;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author hoppmann
 */
public class EasyFindings extends Application {
    
    
    
    
    
    
    private File redirectToLog() throws FileNotFoundException {
	
	String logDirName = "logs";
	
	File logDir = new File(logDirName);
	if (!logDir.exists()){
	    logDir.mkdir();
	}
	
	
	
	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
	Date date = new Date();
	String errorOut = logDir + File.separator + dateFormat.format(date) + "_ERROR";
	String standardOut = logDir + File.separator + dateFormat.format(date) + "_STD-OUT";
	
	PrintStream errorStream = new PrintStream(errorOut);
	System.setErr(errorStream);
	
	PrintStream standardStream = new PrintStream(standardOut);
	System.setOut(standardStream);

	
	return logDir;
    }
    
    
    
    
    
    
    
    
    
    @Override
    public void start(Stage stage) throws Exception {

	File logDir = redirectToLog();
	
	
	stage.setTitle("EasyFindings");
	FXMLLoader loader = new FXMLLoader(getClass().getResource("gui/view/mainView/MainView.fxml"));
	Parent root = loader.load();
	Scene scene = new Scene(root);
	stage.setScene(scene);

	MainViewController mainViewController = loader.getController();
	mainViewController.init(logDir);

        stage.show();
	

    
	
	
	

        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
