/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.config.Config;
import de.hoppmann.database.userDB.ConnectionBuilder;
import de.hoppmann.database.userDB.IConnectDB;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class MainViewUserDbController implements Initializable {

    
    Config config = Config.getInstance();
    IConnectDB connectDB;
    @FXML Label dbConnectionLabel;
    @FXML Label infoLabel;
    @FXML Button closeButoon;
    @FXML TabPane mainTabPane;
    @FXML Tab addressTab;
    @FXML Tab geneInfoTab;
    
    
    
    
    //// create open dialog and load opende DB
    @FXML 
    private void openDB (ActionEvent e) {
	
	
	// close if old connection exists
	if (ConnectionBuilder.hasConnection()){
	    ConnectionBuilder.closeConnection();
	}
	
	
	
	//// choose DB file
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Open database");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database file (*.db)", "*.db"));
	
	// use path from config if possible
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()) {
	    chooser.setInitialDirectory(new File(config.getDbPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	File dbFile = chooser.showOpenDialog(new Stage());
		
	
	
	// create connection to DB
        if (dbFile != null) {
            boolean succsess = connectDB.connectDB(dbFile.getAbsolutePath(), "", "");
            if (succsess) {
                infoLabel.setText("Opened DB.");
                dbConnectionLabel.setText(dbFile.getAbsolutePath());
            }
        }
	
        
	
	
	
	
	
	
	
	
	
	
//	// refresh gene combobox choice
//	if (userDB.isConnected(UserDB.conn)){
//	    
//	    addGeneList(null);
//	    
//	    
//	    nameField.getItems().setAll(receiverDB.getNameList());
//	    resetAddressFields();
//	    TextFields.bindAutoCompletion(nameField.getEditor(), receiverDB.getNameList());
//
//	}

	
	
    }
    
    
    
   
    // create new DB
    @FXML
    private void newDB (ActionEvent e) {
	
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Create new database.");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database file (*.db)", "*.db"));
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*)", "*.*"));
	
        // use path from config if possible
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()) {
	    chooser.setInitialDirectory(new File(config.getDbPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	File dbFile = chooser.showSaveDialog(new Stage());

        if (dbFile != null) {
            
            boolean succsess = connectDB.connectDB(dbFile.getAbsolutePath(), "", "");
            if (succsess) {
                infoLabel.setText("DB succsessful created.");
                dbConnectionLabel.setText(dbFile.getAbsolutePath());
            }

        }
        
        
    }
    
    


    // close window
    @FXML
    private void closeWindow () {
	
	Stage stage = (Stage) closeButoon.getScene().getWindow();
	stage.close();
	
	
    }
    
    
    
    
    
    public void init(IConnectDB connectDB){
        this.connectDB = connectDB;
    }
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
	
	// connect to DB if available
        
        
        
        // tab change listener
    }    
    
}
