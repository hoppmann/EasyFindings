/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.config.Config;
import de.hoppmann.database.userDB.ConnectionBuilder;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import de.hoppmann.database.userDB.interfaces.IConnectDB;
import de.hoppmann.database.userDB.receiverDB.AddressDbRepository;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class MainViewUserDbController implements Initializable {

    
    private Config config = Config.getInstance();
    private IConnectDB connectDB;
    private FindingsRepository findings;
    @FXML private Label dbConnectionLabel;
    @FXML private Label infoLabel;
    @FXML private Button closeButoon;
    @FXML private TabPane tabPane;
    @FXML private Tab addressTabView;
    @FXML private Tab variantTabView;
    @FXML private VariantTabController variantTabController;
    @FXML private AddressTabController addressTabController;
    
    
    
    
    //// create open dialog and load opende DB
    @FXML 
    private void openDB (ActionEvent e) {
	
	
	// close if old connection exists
	if (ConnectionBuilder.hasConnection()){
	    ConnectionBuilder.closeConnection();
	}
	
	
	// get path from config if available
	File dbPath = null;
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()){
	   dbPath = new File(config.getDbPath());
	} 
	
	
	//// choose DB file
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Open database");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database file (*.db)", "*.db"));
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*)", "*.*"));
	chooser.setInitialDirectory(dbPath);
		
	
	File dbFile = chooser.showOpenDialog(new Stage());
	

	// create connection to DB
        if (dbFile != null) {
	    
	    config.setDbFullPath(dbFile.getAbsolutePath());
	    connect(dbFile, "", "");
        
            variantTabController.init(findings);
        }
        
        
	
	
    }
    
    
    
   
    // create new DB
    @FXML
    private void newDB (ActionEvent e) {
	
	
	// close if old connection exists
	if (ConnectionBuilder.hasConnection()){
	    ConnectionBuilder.closeConnection();
	}

	
	// get path from config if available
	File dbPath = null;
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()){
	   dbPath = new File(config.getDbPath());
	} 

	
	// open create dialogue
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Create new database.");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database file (*.db)", "*.db"));
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("All files (*)", "*.*"));
	chooser.setInitialDirectory(dbPath);
	
	File dbFile = chooser.showSaveDialog(new Stage());

	
	
        if (dbFile != null) {
            
	    // add extension if not given
	    if (!FilenameUtils.getExtension(dbFile.getAbsolutePath()).equals(".db")){
		String path = dbFile.getAbsolutePath();
		path += ".db";
		dbFile = new File(path);
	    }

	    connect(dbFile, "", "");

        }
    }
    
    


    // close window
    @FXML
    private void closeWindow () {
	
	Stage stage = (Stage) closeButoon.getScene().getWindow();
	stage.close();
	
	
    }
    
    
    
    
    
    // connect to DB
    private void connect(File dbFile, String user, String password){
	    
	boolean succsess = connectDB.connect(dbFile.getAbsolutePath(), user, password);
	if (succsess) {
	    infoLabel.setText("DB created.");
	    dbConnectionLabel.setText(dbFile.getAbsolutePath());
	} else {
	    infoLabel.setText("Unable to connect to DB.");
	}

    }
    
    
    
    
    
    
    
    
    public void init(IConnectDB connectDB, FindingsRepository findings){
        this.connectDB = connectDB;
	this.findings = findings;
	
	// connect to DB if available
	if (config.getDbFullPath() != null && new File(config.getDbFullPath()).exists()){
	    connect(new File(config.getDbFullPath()), "", "");
	}
	
	
	
	
	// initialize lower views
	variantTabController.init(findings);
	
	

    }

    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	// inject controller
	variantTabController.injectMainController(this);
	addressTabController.injectMainController(this);

	
	
	
	// tab change listener
	addressTabView.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if (newValue){
		    addressTabController.init(new AddressDbRepository());
		}
	    }
	});
	
    }

    
    
    
    public Label getInfoLabel() {
	return infoLabel;
    }
    
    
    
    
    
}


