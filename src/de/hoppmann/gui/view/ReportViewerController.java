/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.config.Config;
import de.hoppmann.createReport.CreateReport;
import de.hoppmann.createReport.PreparePanelTable;
import de.hoppmann.database.OldImplementation.ReceiverDB;
import de.hoppmann.database.OldImplementation.UserDB;
import de.hoppmann.createReport.ReportDataModel;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
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
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
    @FXML private TextField titleField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipCodeField;
    @FXML private TextField countryField;
    @FXML TextField coFiled;
    @FXML TextField patientfield;
    @FXML TextField materialField;
    @FXML TextField indicationField;
    @FXML TextField assessmentField;
    @FXML TextArea genePanelArea;
    @FXML TextField dateOfMaterialArrival;
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
    
    
    // misc
    @FXML Label infoLable;
    
    //// other variables    
    private String report = "";
    private FindingsRepository findings;
    private ReportDataModel reportData = new ReportDataModel();
    private CreateReport createReport; 
    private String panelGenes;
    private PreparePanelTable panelTable = new PreparePanelTable();
    private UserDB userDB = new UserDB();
    private ReceiverDB receiverDB = new ReceiverDB();
    
    
    ////////////////////////////////
    //////// initialization ////////
    ////////////////////////////////
    
    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

	
	
	userDB.connectDB(Config.getInstance().getDbFullPath(), false);
	
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
	
	
	//// add listener to arrival date field
	dateOfMaterialArrival.textProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		reportData.setMaterialRecielvalDate(newValue);
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
    public void init (FindingsRepository findings) {
	
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
    
    
    // fill receiver filds
    private void fillReceiverInfo () {
	titleField.setText(receiverDB.getTitle());
	receiverName.setValue(receiverDB.getFullName());
	addressField.setText(receiverDB.getPostalAddress());
	cityField.setText(receiverDB.getCity());
	zipCodeField.setText(receiverDB.getZipCode());
	countryField.setText(receiverDB.getCountry());

    }
    
    
    
    
    // fill text fields
    private void retrieveEntryMaskTextFields() {
	fillReceiverInfo();

	coFiled.setText(reportData.getReceiverCoLine());
	patientfield.setText(reportData.getPatientInfo());
	materialField.setText(reportData.getMaterial());
	indicationField.setText(reportData.getIndication());
	assessmentField.setText(reportData.getAssessment());
	dateOfMaterialArrival.setText(reportData.getMaterialRecielvalDate());
	
    }
    
    
    // reload report
    private void reloadReport() {
	
	createReport.replaceValues();
	report = createReport.getReport();
	htmlEditor.setHtmlText(report);

    }
    
    
    // reset address fields
    private void resetAddressFields () {
	
	// set all values null
	receiverDB.setTitle("");
	receiverDB.setFullName("");
	receiverDB.setPostalAddress("");
	receiverDB.setCity("");
	receiverDB.setZipCode("");
	receiverDB.setCountry("");

	
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
    
    
    
    
    
    
    
    
    
    
    //////////////////
    //// new receiver
    @FXML
    private void newReceiverButtonAction () {
	
	resetAddressFields();
	
	receiverDB.insert();

	// reset fields
	fillReceiverInfo();
	
	infoLable.setText("Created a new entry.");

	
    }
    
    
    
    
    //////////////////////
    //// store receiver data
    @FXML
    private void storeReceiverButtonAction() {

	// set variables
	receiverDB.setTitle(titleField.getText());
	receiverDB.setFullName(receiverName.getValue());
	receiverDB.setPostalAddress(addressField.getText());
	receiverDB.setCity(cityField.getText());
	receiverDB.setZipCode(zipCodeField.getText());
	receiverDB.setCountry(countryField.getText());

	
	// check if entry is new (no ID so far)
	if (receiverDB.getId() != null) {

	    // update DB
	    receiverDB.update();
	    infoLable.setText(receiverName.getValue() + " saved.");
	} else {
	    receiverDB.insert();
	    infoLable.setText(receiverName.getValue() + " added to DB.");
	}

	
	// update choosable list
	receiverName.getItems().setAll(receiverDB.getNameList());

    }
    
    
    
    
    
    
    
    
    ///////////////////
    //// remove receiver data
    @FXML
    private void removeReceiverButtonAction() {
	// remove entry
	receiverDB.remove();
	
	// update choices and fields
	resetAddressFields();
	fillReceiverInfo();
	receiverName.getItems().setAll(receiverDB.getNameList());
	
	infoLable.setText(receiverName.getValue() + " deleted from DB.");
    }
    
    
    
    
    
    
    
    
    //////////////////////////////////////
    //// Button to jump to database window
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
	receiverDB.closeDB(UserDB.conn);
	
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
