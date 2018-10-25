/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.LoadInputFile;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import de.hoppmann.operations.CreateTable;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
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
    // main view tab
    @FXML private Tab dataTab;
    @FXML private TableView<TableData> inputTable = new TableView();
    
    // findings tab
    @FXML private Tab findingsTab;
    @FXML private TableView<TableData> findingsTable = new TableView();
    
    
    //all tabs
    @FXML private Label infoFiled;
    @FXML private TabPane tabPane;
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    // Button to jump to database window
    
    @FXML
    private void databaseButtonAction (ActionEvent event) {
	
	
	try {
	    // open database window
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CheckDB.fxml"));
	    

	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Check databse");
	    stage.setScene(new Scene(root));
	    
	    
	    // run init 
	    CheckDBController controller = fxmlLoader.getController();
	    controller.init(findings);

	    // show new view
	    stage.show();

	    
	    
	    
	} catch (IOException ex) {
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	
	
	
    }
    
    
    
    
    
    // method to test parts directly
    @FXML
    private void TestButtonAction (ActionEvent event) {
	
//	TestDoc test = new TestDoc();
	
    }
    
    
    
    
    
    
    
    // choose columns needed for automated catagorizing
    @FXML
    private void handleColumnsButton (ActionEvent event) {
	
	
	if (findings == null) {
	    infoFiled.setText("Needs findings to open window.");
	    return;
	}
	
	
	
	try {
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
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	
    }
    
    
    
    
    
    
    
   
    
    

    // new patient button
    @FXML
    private void handleNewPatientButtonAction (ActionEvent event) {
	// reset objects
	findings = null;
        loadFile = null;
	inputTable.getColumns().clear();
	inputTable.getItems().clear();
	infoFiled.setText("Entries cleared.");
    }


    
    
    
    
    // add action listener for open button
    @FXML
    private void handleOpenButtonAction (ActionEvent event) {

        // load input file after open button was pushed
        loadFile = new LoadInputFile(inputTable, infoFiled);
	boolean loadedCorrectly = loadFile.load();
        
	// check if file is loaded correctly ti avoid error 
	if (!loadedCorrectly){
	    loadFile = null;
	}
	
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
    
    
    // refresh button
    @FXML
    private void handleRefreshButtonAction (ActionEvent event) {
        
        // remove lines that got unmarked
	for (Iterator<TableData> iter = findings.getStoredData().listIterator(); iter.hasNext();){
	    TableData entry = iter.next();
	    if (entry.isCausal() == false) {
		iter.remove();
	    }
	}
        
        
        // refresh table
        refreshFindingsTable();
    }
    
    
    
    //// show report window
    @FXML
    private void reportButtonAction(ActionEvent event) {

	try {
	    // do nothing if findings == null
	    if (findings == null) {
		return;
	    }

	    // prepare new page
	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ReportViewer.fxml"));

	    // create new window
	    Parent root = fxmlLoader.load();
	    Stage stage = new Stage();
	    stage.setTitle("Report");
	    stage.setScene(new Scene(root));

	    // get controller of new window and pass findings list
	    ReportViewerController controller = fxmlLoader.getController();
	    controller.init(findings);

	    // show window
	    stage.show();

	} catch (IOException ex) {
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}

    }

    
    
    /////////////////////////
    //////// non FXML methods
    
    private void refreshFindingsTable() {

        /* 
        check if any findings are stored 
            -> create table containing these variants
         */
        if (findings != null) {

            // remove lines that got unmarked
            for (Iterator<TableData> iter = findings.getStoredData().listIterator(); iter.hasNext();) {
                TableData entry = iter.next();
                if (entry.isCausal() == false) {
                    iter.remove();
                }
            }

            findingsTable.setEditable(true);

            CreateTable createTable = new CreateTable(findingsTable);
            createTable.prepareTable(findings.getHeaderList());
            createTable.fillTable(findings.getStoredData());

        }

    }
    
    
    
     // save findings button
    @FXML
    private void saveFindings () {
	
	
	
	// check that a file was loaded first to avoid errors
	if (loadFile != null) {
	    // check if there is existing data else create new object
	    if (findings == null) {
		findings = new StoreFindings(loadFile.getHeader());
	    }

	    // save findigs for later use
	    findings.storeFindings(loadFile);
	   
            // make info 
            infoFiled.setText("Findings saved");
	}
	
	
	
    }

    
    
    
    
    
    
     /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {

         // prepare tables
        inputTable.setEditable(true);
        findingsTable.setEditable(true);
        
        
        //// add listener to tabs
        // changing to findings tab reload findings table
        findingsTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // if changed to tab refresh table 
                if (newValue) {
                     refreshFindingsTable();
                 }    
            }
        });
        
        // changing from data Tab save findings
        dataTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (! newValue) {
                    saveFindings();
                }
            }
        });
        
        
        // changing to the data tab refresh view
        dataTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                // check if tab was just selected
                if (newValue){
                    // check if a file already is opened to avoid errors
                    if (loadFile != null) {
                        // reload table
                        loadFile.createTable();
                    }
                }
            }
        });
                
        
    }    
    
}
