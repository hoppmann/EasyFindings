/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.gui.Messneges.CommonWarnings;
import de.hoppmann.gui.model.TableData;
import de.hoppmann.operations.CreateTable;
import de.hoppmann.operations.StoreFindings;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class FindingsGuiController implements Initializable {

    
    ///////////////////////////
    //////// Variables ////////
    ///////////////////////////
    
    // general variables
    private StoreFindings findings; 
    
    
    
    // FXML Variables
    @FXML
    private Button closeButton;
    
    @FXML
    private TableView<TableData> findingsTable;
    
    
    
    /////////////////////////
    //////// Methods ////////
    /////////////////////////


    //// FXML Methods
    @FXML
    private void closeButtonAction (ActionEvent event) {
	
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
    }


    
    //// remove variants that got unselected
    @FXML
    private void removeButtonAction (ActionEvent event) {

	// remove lines that got unmarked
	for (Iterator<TableData> iter = findings.getStoredData().listIterator(); iter.hasNext();){
	    TableData entry = iter.next();
	    if (entry.isCausal() == false) {
		iter.remove();
	    }
	}
	
	
	// recreate table
	CreateTable createTable = new CreateTable(findingsTable);
	createTable.prepareTable(findings.getHeader());
	createTable.fillTable(findings.getStoredData());

    }
    
    
    
    
    
    
    //// general methods
    
    public void init(StoreFindings findings){
	
	// retrieve variables
	this.findings = findings;
	
	// set table options
	findingsTable.setEditable(true);
	
	// check if any findings are stored else close window
	if (findings == null) {
	    new CommonWarnings().noDataAvailable();
	    closeButtonAction(new ActionEvent());
	} else {
	    
	    // create Table	
	    CreateTable createTable = new CreateTable(findingsTable);
	    createTable.prepareTable(findings.getHeader());
	    createTable.fillTable(findings.getStoredData());

	}
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    
    
    /////////////////////////////////
    //////// Getter / Setter ////////
    /////////////////////////////////

    public void setStore(StoreFindings store) {
	this.findings = store;
    }
    
    
    
}
