/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.gui.messanges.CommonErrors;
import de.hoppmann.gui.messanges.CommonWarnings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.LoadInputFile;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.web.HTMLEditor;
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
    @FXML
    private TableView<TableData> inputTable = new TableView();
    
    @FXML
    private Label infoFiled;
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    // Report button 
    @FXML
    private void reportButtonAction (ActionEvent event) {
	
	String INITIAL_TEXT = "Lorem ipsum dolor sit "
            + "amet, consectetur adipiscing elit. Nam tortor felis, pulvinar "
            + "aliquam sagittis gravida eu dolor. Etiam sit amet ipsum "
            + "sem.";
	
	
//	try {
//	    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("HTMLReport.fxml"));
//	    
//	    
//	    
//	    
//	    // create new window
//	    Parent root = fxmlLoader.load();
//	    Stage stage = new Stage();
//	    stage.setTitle("Report");
//	    stage.setAlwaysOnTop(true);
//	    stage.setScene(new Scene(root));
//	   
//	    HTMLReportController controller = fxmlLoader.getController();
//
//	    System.out.println(INITIAL_TEXT);
//	    controller.init(INITIAL_TEXT);
//	    
//	    
//	    // open window
//	    stage.show();
//	} catch (IOException ex) {
//	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
//	}
  
	    
	    HTMLEditor htmlEditor = new HTMLEditor();
	    htmlEditor.setPrefHeight(245);
	    Scene scene = new Scene(htmlEditor);
	    Stage stage = new Stage();
	    htmlEditor.setHtmlText(loadHtmlTemplate());
	    stage.setScene(scene);
	    stage.show();
	    
	    
	    
	
	
    }
    
    
    private String loadHtmlTemplate() {
	
	
	File inputFile = new File("/home/hoppmann/Dropbox/transfer/template.html");
	
	// prepare variables
        String line;
	List<String> input = new ArrayList<>();
	try {
            // read in file
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            while ((line = br.readLine()) != null) {
		
		// read in line split and save in row data object
		input.add(line);
            }
            br.close();
        } catch (IOException iOException) {
	    new CommonErrors().cantOpen(inputFile.toString());
        }
	
	
	String htmlFile = input.toString();
	
	System.out.println(htmlFile);
	
	return htmlFile;
	
    }
    
    
    
    
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
	    stage.setAlwaysOnTop(true);
	    stage.setScene(new Scene(root));
	    
	    
	    // run init 
	    CheckDBController controller = fxmlLoader.getController();
	    controller.init(findings);
	    
	    // show new vie
	    stage.show();

	    
	    
	    
	} catch (IOException ex) {
	    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	
	
	
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
	
	// make info 
	infoFiled.setText("Findings saved");
	
    }

    
    
    

    // new patient button
    @FXML
    private void handleNewPatientButtonAction (ActionEvent event) {
	// reset objects
	findings = null;
	inputTable.getColumns().clear();
	inputTable.getItems().clear();
	infoFiled.setText("Entries cleared.");
    }


    
    
    
    
    // add action listener for open button
    @FXML
    private void handleOpenButtonAction (ActionEvent event) {

        // load input file after open button was pushed
        loadFile = new LoadInputFile(inputTable, infoFiled);
        
        
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
