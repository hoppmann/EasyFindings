/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.config.Config;
import de.hoppmann.createReport.CreateReport;
import de.hoppmann.createReport.PreparePanelTable;
import de.hoppmann.createReport.ReceiverDB;
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
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.InputEvent;
import javafx.scene.web.HTMLEditor;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

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
    
    // tab pane
    @FXML TabPane tabPane;
    
    
    // editor tab 
    @FXML HTMLEditor htmlEditor;
    @FXML Tab reportTab;
    
    
    // entry mask tab
    @FXML Tab entryMaskTab;
    @FXML TextField titleField;
    @FXML TextField addressField;
    @FXML TextField cityField;
    @FXML TextField zipCodeField;
    @FXML TextField countryField;
    @FXML TextField coFiled;
    @FXML TextField patientfield;
    @FXML TextField materialField;
    @FXML TextField indicationField;
    @FXML TextField assessmentField;
    @FXML TextArea genePanelArea;
    @FXML ComboBox<String> receiverName = new ComboBox<>();
    @FXML ComboBox<String> senderChoice = new ComboBox<>();
    @FXML ComboBox<String> seqMethodChoice = new ComboBox<>();
    @FXML ComboBox<String> diagMethodChoice = new ComboBox<>();
    
    
    
    
    // html report tab
    @FXML Tab htmlReportTab;
    @FXML TextArea htmlTextArea;
    

    // billing report tab
    @FXML Tab billingTab;
    @FXML HTMLEditor billingEditor;
    
    
    //// other variables    
    private String report = "";
    private StoreFindings findings;
    private ReportDataModel reportData = new ReportDataModel();
    private CreateReport createReport; 
    private String panelGenes;
    private PreparePanelTable panelTable = new PreparePanelTable();
    private ReceiverDB receiverDB = new ReceiverDB();
    
    
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
	
	
	//// title filed
	titleField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setTitle(newValue);
	    }
	});
	
	//// street field
	addressField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverStreet(newValue);
	    }
	});
	
	
	//// city field
	cityField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverCity(newValue);
	    }
	});
	
	
	//// zip code field
	zipCodeField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setZipCode(newValue);
	    }
	});
	
	
	//// country field
	countryField.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setCountry(newValue);
	    }
	});
	
	
	//// co field
	coFiled.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setReceiverCoLine(newValue);
	    }
	});
	coFiled.setTooltip(new Tooltip("Multiple entries seperated by \";\""));

	
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
		retrieveEntryMaskTextFields();
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
	
	
	/* 
	init receiver name combo box
	add auto compleation
	Add action listener
	if name is written check if address is available 
	    if so auto fill other fields
	*/
		
	
	receiverName.getItems().setAll(receiverDB.getNameList());
	receiverName.setEditable(true);
	TextFields.bindAutoCompletion(receiverName.getEditor(), receiverDB.getNameList());
	receiverName.valueProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		fillReceiverData(newValue);
		reportData.setReceiverName(newValue);
	    }
	});
	
    }    
    
    
    
    
    
    //////////////////////
    ///// fill receiver data from database
    private void fillReceiverData(String receiverName) {
	
	boolean success = receiverDB.queryAddress(receiverName);
	if (success){
	    titleField.setText(receiverDB.getTitle());
	    addressField.setText(receiverDB.getPostalAddress());
	    cityField.setText(receiverDB.getCity());
	    zipCodeField.setText(receiverDB.getZipCode());
	    countryField.setText(receiverDB.getCountry());
	    
	}
	
    }
    
    
    
    
    
    
    
	    
	    
	    
	    
	    
	    
	    
	    
    
    //////////////////////
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
    private void retrieveEntryMaskTextFields() {
	titleField.setText(reportData.getTitle());
	receiverName.setValue(reportData.getReceiverName());
	addressField.setText(reportData.getReceiverStreet());
	cityField.setText(reportData.getReceiverCity());
	zipCodeField.setText(reportData.getZipCode());
	countryField.setText(reportData.getCountry());
	coFiled.setText(reportData.getReceiverCoLine());
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
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////
    //// store receiver data
    @FXML
    private void storeReceiverButtonAction () {
	
	// set variables
	receiverDB.setTitle(titleField.getText());
	receiverDB.setFullName(receiverName.getValue());
	receiverDB.setPostalAddress(addressField.getText());
	receiverDB.setCity(cityField.getText());
	receiverDB.setZipCode(zipCodeField.getText());
	receiverDB.setCountry(countryField.getText());
	
	
	
	// update DB
	receiverDB.storeReceiver();
	
	// update choosable list
	receiverName.getItems().setAll(receiverDB.getNameList());
	
    }
    
    
    ///////////////////
    //// remove receiver data
    @FXML
    private void removeReceiverButtonAction() {
	// remove entry
	receiverDB.removeData();
	
	// update Combobox choice
	receiverName.getItems().setAll(receiverDB.getNameList());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // reload report based on template
    @FXML
    private void refreshButtonAction (ActionEvent event) {
	
	// create panel table
	panelTable.createReportTable(panelGenes);
	String genePanelTable = panelTable.getPanelTable();
	reportData.setGenePanelTable(genePanelTable);

	// replace values
	createReport.replaceValues();
	
	// create report
	report = createReport.getReport();
	htmlEditor.setHtmlText(report);
	
	// add billing table to billing tab
	panelTable.createBillingTable(panelGenes);
	String billingTable = panelTable.getBillingTable();
	billingEditor.setHtmlText(billingTable);
	
	// switch to html tab
	tabPane.getSelectionModel().select(reportTab);
	
    }
    
    
    


    //// handle switching to report tab
    @FXML
    private void chooseReportTabAction (ActionEvent event) {
	
	createReport.replaceValues();
	report = createReport.getReport();
	htmlEditor.setHtmlText(report);
	
	
    }
    
    
    
    
    //// close current window
    @FXML
    public void closeButtonAction(ActionEvent event) {
	
	// close connection to receiverDB
	receiverDB.closeDB();
	
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

//	new TestDoc(htmlEditor.getHtmlText());
	
	
	// choose file where to save
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Save report");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word document (*.doc)", "*.doc"));
       	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("html document (*.html)", "*.html"));

	File fileOut = chooser.showSaveDialog(null);
	
	
	
	// check if choice was abborted
	if (fileOut != null) {

	    // check / add default ending
            // check if .doc or .html is chosen else add suffix to file
	    if (! fileOut.getName().contains(".doc") && ! fileOut.getName().contains(".html")) {
		fileOut = new File(fileOut.getAbsolutePath() + ".doc");
	    }
	    
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

    
    
    
   
    //// choose different template
    @FXML 
    private void templateButtonAction (ActionEvent e) {
	Config config = Config.getInstance();
	
	// choose file
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Select report template");
	
	if (new File(config.getHtmlTemplate()).exists()){
	    chooser.setInitialDirectory(new File(config.getHtmlTemplate()).getParentFile());
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	
	File template = chooser.showOpenDialog(new Stage());
	
	
	if (template != null && template.exists()){
	    Config.getInstance().setHtmlTemplate(template.getAbsolutePath());
	}
	
	
    }
    
   
    
    
    
    
    
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////
    
    
    
    
    
}
