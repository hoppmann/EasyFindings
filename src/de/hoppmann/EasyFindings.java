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
    
//    private static Logger logger = Logger.getLogger("de.hoppmann.*");
//    private static FileHandler fh;
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void start(Stage stage) throws Exception {


	
	
	
	
	stage.setTitle("EasyFindings");
        Parent root = FXMLLoader.load(getClass().getResource("gui/view/mainView/MainView.fxml"));
        
        
        Scene scene = new Scene(root);

	stage.setScene(scene);
        stage.show();
	

    
	
	
	

        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	
//	System.out.println(logger.getHandlers());
	
//	
//	try {
//	    fh = new FileHandler("EasyFindings.log", 8096, 1, true);
//	    logger.addHandler(fh);
//	    logger.setLevel(Level.ALL);
////	    logger.info("TEST");
//	    
//	    
//	    
//	    
//	    
//	    
//	    
//	} catch (IOException ex) {
//	    Logger.getLogger(EasyFindings.class.getName()).log(Level.SEVERE, null, ex);
//	} catch (SecurityException ex) {
//	    Logger.getLogger(EasyFindings.class.getName()).log(Level.SEVERE, null, ex);
//	}
	    
//	Logger.getLogger(EasyFindings.class.getName()).log(Level.SEVERE, null, "BLUB");

	
	    launch(args);
	    
	    
	    
	
	
    }
    
}
