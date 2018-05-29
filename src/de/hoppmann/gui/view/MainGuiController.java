/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.gui.Messneges.CommonWarnings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.LoadInputFile;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import de.hoppmann.misc.handleConfig;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class MainGuiController implements Initializable {

    
    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    //// general variables
    LoadInputFile loadFile;
    StoreFindings findings = null;
    
    
    
    //// FXML variables
    @FXML
    private TableView<TableData> inputTable = new TableView();
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    // show all stored findings
    @FXML
    private void handleFindingsButton(ActionEvent event) {

	try {
	    
	    
	    // open new window to show findings chosen so far
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FindingsGui.fxml"));

	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Current Findings");
	    stage.setScene(new Scene(root));
	    stage.show();
	    
	    // setting controller for datatransfer
	    FindingsGuiController controller = fxmlLoader.getController();
	    controller.init(findings);
		    

	} catch (IOException ex) {
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
    }
    
    
    
    
    
    
    // save findings button
    @FXML
    private void handleSaveFindingsButtonAction (ActionEvent event) {
	
	
	// check that a file was loaded first to avoid errors
	if (loadFile == null) {
	    new CommonWarnings().openFileFirst();
	} else {
	    // check if there is existing data else create new object
	    if (findings == null) {
		findings = new StoreFindings(loadFile.getHeader());
	    }

	    // save findigs for later use
	    findings.storeFindings(loadFile);
	    
	}
	
	
    }

    
    
    

    // new patient button
    @FXML
    private void handleNewPatientButtonAction (ActionEvent event) {
	// reset objects
	findings = null;
	inputTable.getColumns().clear();
	inputTable.getItems().clear();
    }


    
    
    
    
    // add action listener for open button
    @FXML
    private void handleOpenButtonAction (ActionEvent event) {

        // load input file after open button was pushed
        loadFile = new LoadInputFile(inputTable);
        
        
    }
    
    
    
    // close application
    @FXML
    private void handleCloseButtonAction (ActionEvent event) {
        System.exit(0);
//	Alert alert = new Alert(AlertType.CONFIRMATION);
//	alert.setTitle("Close EasyFindings");
//	alert.setHeaderText(null);
//	alert.setContentText("Exit EasyFindings?");
//	
//	Optional<ButtonType> result = alert.showAndWait();
//	if (result.get() == ButtonType.OK) {
//	    System.exit(0);
//	}
    }
    
    
    
    
    
    
    
    
    
    
    
     /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
	inputTable.setEditable(true);
    }    
    
}
