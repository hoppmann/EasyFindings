/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann;

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
    
    
    
    @Override
    public void start(Stage stage) throws Exception {

	  Parent root = FXMLLoader.load(getClass().getResource("database/userDB/testView/MainTestGui.fxml"));
	  Scene scene = new Scene(root);
	  stage.setScene(scene);
	  stage.show();


	
//	stage.setTitle("EasyFindings");
//        Parent root = FXMLLoader.load(getClass().getResource("gui/view/MainGui.fxml"));
//        
//        Scene scene = new Scene(root);
//
//	stage.setScene(scene);
//        stage.show();
	

    
	
	
	

        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
