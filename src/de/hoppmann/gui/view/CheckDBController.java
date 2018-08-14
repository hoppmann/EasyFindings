/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.config.Config;
import de.hoppmann.operations.GeneDB;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class CheckDBController implements Initializable {

    ///////////////////////////
    //////// Variables ////////
    ///////////////////////////
    
    @FXML
    private Button closeButton;
    
    @FXML
    private TextField geneNameFiled;
    
    @FXML
    private TextArea geneInfoField;
    
    @FXML
    private Label infoLabel;
    
    @FXML
    private Label dbLabel;
    
    
    
    
    private GeneDB geneDB;
    private Config config;
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
    
    
    
    
    
    //////// FXML Methods
    
    //// close windwo
    @FXML
    public void closeButtonAction (ActionEvent event) {
	
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
	
    }
    
    
    
    
    
    
    
    
    
    
    //// open DB
    @FXML
    private void openDbButtonAction (ActionEvent event) {
	
	File dbFile = geneDB.openDB();
	
	if (dbFile != null) {
	    geneDB.connect(dbFile);
	}
    }
    
    
    
    
    
    
    
    
    
    
    
    //// create new database
    @FXML
    private void newDbButtonAction (ActionEvent event) {
	
	File dbFile = geneDB.createNewDB();
	if (dbFile != null) {
	    geneDB.connect(dbFile);
	}
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    @FXML 
    private void saveGeneButtonAction (ActionEvent event) {
	
	
	String geneInfo = geneInfoField.getText();
	
	// get geneName and remove all nonalphanumeric elements
	String geneName = geneNameFiled.getText();
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	
	geneDB.saveGene(geneName, geneInfo);
	
    }
    
   
    
    
    
    @FXML
    private void showGeneInfoButtonAction(ActionEvent event) {
	
	// retrieve string and remove all non alphanumeric elements
	String geneName = geneNameFiled.getText();
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	
	geneInfoField.setText(geneDB.getGeneEntry(geneName));
	
	
    }
    
    
    
    
    //// initialize DB
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	geneDB = new GeneDB(dbLabel, infoLabel);
	
	// check if DB saved in cofig exists
	// if so connect to it
	
	Config config = new Config();
	if (config.getDbFullPath() != null){
	    
	    if (new File(config.getDbFullPath()).exists() && !new File(config.getDbFullPath()).isDirectory()){
		File dbFile = new File(config.getDbFullPath());
		geneDB.connect(dbFile);
	    }
	}
	
	
	
    }    
    
}
