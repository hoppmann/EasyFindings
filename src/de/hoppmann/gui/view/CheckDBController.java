/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.operations.GeneDB;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    
    
    
    
    private GeneDB geneDB;
    
    
    
    
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
	
	geneDB.openDB();
	
    }
    
    
    
    
    
    //// create new database
    @FXML
    private void newDbButtonAction (ActionEvent event) {
	
	geneDB.createNewDB();
	
	
    }
    
    
    @FXML 
    private void saveGeneButtonAction (ActionEvent event) {
	
	
	String geneInfo = geneInfoField.getText();
	String geneName = geneNameFiled.getText();
	
	
	geneDB.saveGene(geneInfo, geneName);
	
    }
    
    
    
    //// initialize DB
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	geneDB = new GeneDB();

    }    
    
}
