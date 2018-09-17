/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.gui.messanges.CommonWarnings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.CreateTable;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    private TableView<TableData> findingsTable = new TableView<>();
    
    
    
    
    /////////////////////////
    //////// Methods ////////
    /////////////////////////

    
    
    
    
    

    // Report button 
    @FXML
    private void showReportButtonAction (ActionEvent event) {

	
	try {

	    
	    // prepare new page
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReportViewer.fxml"));
	    
	    
	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Report");
	    stage.setScene(new Scene(root));
	    System.out.println("");
	    
	    
	    // get controller of new window and pass findings list
	    ReportViewerController controller = fxmlLoader.getController();
	    controller.init(findings);
		    
	    
	    // show window
	    stage.show();
	    
	    	} catch (IOException ex) {
	    Logger.getLogger(FindingsGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void checkDbButtonAction(ActionEvent event) {
	
	
	try {
	    // open database window
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckDB.fxml"));
	    
	    

	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Check databse");
	    stage.setAlwaysOnTop(true);
	    stage.setScene(new Scene(root));


	    // get controller
	    CheckDBController dbController = fxmlLoader.getController();
	    dbController.init(findings);

	    // show view
	    stage.showAndWait();
	    	    



	    
	    
	    
	} catch (IOException ex) {
	    Logger.getLogger(FindingsGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	
	
	
	
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //// show report window
    @FXML
    private void showReportAction (ActionEvent event) {
	
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @FXML
    private void closeButtonAction (ActionEvent event) {
	
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
    }


    
    @FXML
    private void selectColumnsButton(ActionEvent event) {
	
	
	
	
	
	    
	try {
	    // open new window to show findings chosen so far
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SelectColumnsGui.fxml"));
	    
	    //// create controller for passing variables
	    SelectColumnsGuiController controller = new SelectColumnsGuiController(findings);
	    
	    
	    // create new window
	    fxmlLoader.setController(controller);
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Slect columns for findings");
	    stage.setScene(new Scene(root));
	    stage.initModality(Modality.APPLICATION_MODAL);
	    stage.show();
	    
	    
	} catch (IOException ex) {
	    Logger.getLogger(FindingsGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}


	
	
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
	createTable.prepareTable(findings.getHeaderList());
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
	    createTable.prepareTable(findings.getHeaderList());
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

    
    
    
}
