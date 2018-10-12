/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.createReport.CreateReport;
import de.hoppmann.createReport.PreparePanelTable;
import de.hoppmann.createReport.ReportDataModel;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.io.FilenameUtils;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class ReportViewerController implements Initializable {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    //// FXML variables
    
    // editor tab 
    @FXML HTMLEditor htmlEditor;
    @FXML Tab reportTab;
    
    
    // entry mask tab
    @FXML Tab entryMaskTab;
    @FXML TextField nameField;
    @FXML TextField streetField;
    @FXML TextField coFiled;
    @FXML TextField cityField;
    @FXML TextField patientfield;
    @FXML TextField materialField;
    @FXML TextField indicationField;
    @FXML TextField assessmentField;
    @FXML TextArea genePanelArea;
    @FXML ComboBox<String> senderChoice = new ComboBox<>();
    @FXML ComboBox<String> seqMethodChoice = new ComboBox<>();
    @FXML ComboBox<String> diagMethodChoice = new ComboBox<>();
    
    
    
    
    // html report tab
    @FXML Tab htmlReportTab;
    @FXML TextArea htmlTextArea;
    

    
    //// other variables    
    private String report = "";
    private StoreFindings findings;
    private ReportDataModel reportData = new ReportDataModel();
    private CreateReport createReport; 
    private String panelGenes;
   
    
    
    ////////////////////////////////
    //////// initialization ////////
    ////////////////////////////////
    
    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	
	//////////////////////
	//// add listener ////
	//////////////////////
	
	/*
	Textfiled listener
	    if texts are entered save them in ReportDataModel
	*/
	
	
	//////////////
	//// name text field
	nameField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverName(newValue);
	    }
	});
	
	
	//// street field
	streetField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverStreet(newValue);
	    }
	});
	
	
	
	//// co field
	coFiled.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverCoLine(newValue);
	    }
	});

	
	//// city field
	cityField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverCity(newValue);
	    }
	});
	
	
	//// patient field
	patientfield.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setPatientInfo(newValue);
    	    }
	});
	
	
	//// material field
	materialField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setMaterial(newValue);
	    }
	});
	
	
	//// indication field
	indicationField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setIndication(newValue);
	    }
	});
	
	
	//// assessment field
	assessmentField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setAssessment(newValue);
	    }
	});
	
	
	// add listener for gene panel area
	genePanelArea.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		panelGenes = newValue;
	    }
	});
	
	
	
	
	
	
	/////// add listener to html report tab text field to manipulate entire report
	htmlTextArea.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		report = newValue;
	    }
	});
	
	
	
	
	
	
	//////// add listener to html editor
	htmlEditor.addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {
	    @Override
	    public void handle(InputEvent event) {
		report = htmlEditor.getHtmlText();
	    }
	});
	
	
	
	//////////////////////////////////////////
	//////// tab change listener
	////////    if tab is chosen execute method

	// change to entry mask tab.
	entryMaskTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		storeEntryMaskTextFields();
	    }
	});
	
	
	// change to report mask -> reload report
	reportTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		htmlEditor.setHtmlText(report);
	    }
	});
	
	
	//// change to html report tab
	htmlReportTab.selectedProperty().addListener(new ChangeListener<Boolean>() {
	    @Override
	    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		htmlTextArea.setText(report);
	    }
	});
	
	
	
	
	
	////////////////////////////
	//////// prepare comboboxes
	
	////////
	// add choice -> key set of underlying hashes 
	// set prechoice -> default key
	
	senderChoice.getItems().setAll(reportData.getSender().keySet());
	senderChoice.getSelectionModel().select(reportData.getSenderKey());
	
	
	seqMethodChoice.getItems().setAll(reportData.getSeqMethod().keySet());
	seqMethodChoice.getSelectionModel().select(reportData.getSeqMethodKey());
	

	diagMethodChoice.getItems().setAll(reportData.getDiagMethod().keySet());
	diagMethodChoice.getSelectionModel().select(reportData.getDiagMethodKey());
	
    }    
    
    
    
    
    
    
     //// init editor and get initial report document
    public void init (StoreFindings findings) {
	
	// retrieve variables and instanciate variables
	this.findings = findings;
	
	prepareInitialReport();
	
	// show initial report
	htmlEditor.setHtmlText(report);
	
	
	
	
    }
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    ////////////////
    //////// non FXML
    
    // fill text fields
    private void storeEntryMaskTextFields() {
	nameField.setText(reportData.getReceiverName());
	streetField.setText(reportData.getReceiverStreet());
	coFiled.setText(reportData.getReceiverCoLine());
	cityField.setText(reportData.getReceiverCity());
	patientfield.setText(reportData.getPatientInfo());
	materialField.setText(reportData.getMaterial());
	indicationField.setText(reportData.getIndication());
	assessmentField.setText(reportData.getAssessment());
	
    }
    
    
    // reload report
    private void reloadReport() {
	
	createReport.replaceValues();
	report = createReport.getReport();
	htmlEditor.setHtmlText(report);

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////
    //////// FXML
    
    
    
    //////// handle combobox choices
    
    // sender choice box
    @FXML
    private void senderChoiceAction (ActionEvent event) {
	reportData.setSenderKey(senderChoice.getValue());
    }
    
    
    // seq method choice box
    @FXML
    private void seqMethodChoiceAction (ActionEvent event) {
	reportData.setSeqMethodKey(seqMethodChoice.getValue());
    }
    
    // diag method choice 
    @FXML
    private void diagMethodChoiceAction (ActionEvent event) {
	reportData.setDiagMethodKey(diagMethodChoice.getValue());
    }
    
    
    
    
    
    
    
    
    
    
    // reload report based on template
    @FXML
    private void refreshButtonAction (ActionEvent event) {
	
	PreparePanelTable table = new PreparePanelTable(panelGenes);
	createReport.replaceValues();
	report = createReport.getReport();
	htmlEditor.setHtmlText(report);
    }
    
    
    


    //// handle switching to report tab
    @FXML
    private void chooseReportTabAction (ActionEvent event) {
	
//	nameEntryAction(new ActionEvent());
	createReport.replaceValues();
	report = createReport.getReport();
	htmlEditor.setHtmlText(report);
	
	
    }
    
    
    
    
    //// close current window
    @FXML
    public void closeButtonAction(ActionEvent event) {
	
	Stage stage = (Stage) htmlEditor.getScene().getWindow();
	stage.close();
	
    }
    
    
    
    
    
    
    
    
    
    //// prepare initial report text
    private void prepareInitialReport() {
	//// load initial Report
	// load HTML template
	createReport = new CreateReport(findings, reportData);

	// open template
	boolean templateOk = createReport.openTemplate();

        
	// prepare gene table
	createReport.prepareFindingGenesTable();
	
        
        if (templateOk) {
            // replace values
            createReport.replaceValues();
        }
	// get modified template as initial text
	report = createReport.getReport();
    }
    
    

    
    
    
    
    
    
    //// save report 
    @FXML
    private void saveReportButtonAction(ActionEvent event) {

	// choose file where to save
	FileChooser chooser = new FileChooser();
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document (*.doc)", "*.doc"));
       	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html document (*.html)", "*.html"));

	File fileOut = chooser.showSaveDialog(null);
	
	
	
	// check if choice was abborted
	if (fileOut != null) {

	    // check / add default ending
            // check if .doc or .html is chosen else add suffix to file
            if (FilenameUtils.getExtension(fileOut.getName()).equalsIgnoreCase(".doc") && FilenameUtils.getExtension(fileOut.getName()).equalsIgnoreCase(".html")){
                fileOut = new File(fileOut.getAbsolutePath() + ".doc");
            }
//	    if (! fileOut.getName().contains(".doc") && ! fileOut.getName().contains(".html")) {
//		fileOut = new File(fileOut.getAbsolutePath() + ".doc");
//	    }
	    
	    // write report in file
	    BufferedWriter writer = null;
	    try {
		writer = new BufferedWriter(new FileWriter(fileOut));
		writer.write(htmlEditor.getHtmlText());
		writer.close();
	    } catch (IOException ex) {
		Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
	    } finally {
		try {
		    writer.close();
		} catch (IOException ex) {
		    Logger.getLogger(MainGuiController.class.getName()).log(Level.SEVERE, null, ex);
		}
	    }
	}
    }

    
    
    
   
    
    
   
    
    
    
    
    
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////
    
    
    
    
    
}
