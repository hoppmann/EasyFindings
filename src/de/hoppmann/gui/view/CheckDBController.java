/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.CreateTable;
import de.hoppmann.Database.GeneDB;
import de.hoppmann.Database.ReceiverDB;
import de.hoppmann.Database.UserDB;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class CheckDBController implements Initializable {

    ///////////////////////////
    //////// Variables ////////
    ///////////////////////////
    
    //// main pane
    
    @FXML private Button closeButton;
    @FXML private Label infoLabel;
    @FXML private Label dbLabel;
    @FXML private TabPane tabpane;

    
    
    
    
    //// variants tab
     private GeneDB geneDB;
    
    // findings
    @FXML private TableView<TableData> findingsTable;
    @FXML private ComboBox<String> findingsTabGeneNameBox;
    @FXML private ComboBox<String> findingsTabVarNameBox;

    
    // gene info
    @FXML private Tab geneInfoTab;
    @FXML private ComboBox<String> geneInfoTabGeneNameBox;
    @FXML private TextArea geneInfoField;
    

    
    
    // variant info
    @FXML private Tab varInfoTab;
    @FXML private ComboBox<String> varInfotabVarNameBox;
    @FXML private TextArea varInfoField;
    
   
    //// address tab
    private ReceiverDB receiverDB;
    @FXML private TextField titleField;
    @FXML private ComboBox<String> nameField;
    @FXML private TextField addressField;
    @FXML private TextField cityField;
    @FXML private TextField zipCodeField;
    @FXML private TextField countryField;
    
    
    
   
    //// general variables
    private UserDB userDB;
    private StoreFindings findings;
    private Config config = Config.getInstance();

    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
    //////// FXML Methods


    //////////////////
    //////// Main Pane 
    
    //// close window
    @FXML
    private void closeButtonAction (ActionEvent event) {
	
	geneDB.closeDB();
	
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
	
    }
    






    //// open DB
    @FXML
    private void openDbButtonAction (ActionEvent event) {
        
    // check if there is already an open connection if so close it
        
	File dbFile = geneDB.openGeneDB();
	
	if (dbFile != null) {
	    geneDB.connectDB(dbFile);
	}
	
	// refresh gene combobox choice
	addGeneList(null);
	
    }
    
    
    
    
    
    
    //// create new database
    @FXML
    private void newDbButtonAction (ActionEvent event) {
        // check if there is already an open connection if so close it
        if (geneDB.isConnected()) {
            geneDB.closeDB();
        }

	File dbFile = geneDB.createNewGeneDB();
	if (dbFile != null) {
	    geneDB.connectDB(dbFile);
	}
	
	addGeneList(null);
	
    }
    
    
    
    
    
    
    
    
    /////////////////////
    //////// Variants Tab    

    
    
    //////// gene tab
    
    
    /* 
    gene info tab remove button
    get gene name and remove gene entry and all variants listed to this gene
    */
    
    @FXML
    private void geneInfoTabRemoveButtonAction (ActionEvent event) {
	
	// get geneName and remove all nonalphanumeric elements
	String geneName = geneInfoTabGeneNameBox.getValue();

	if (geneName == null) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	// double check if deletion is desired
	Alert deletionDialog = new Alert(Alert.AlertType.CONFIRMATION);
	deletionDialog.setTitle("Remove " + geneName + " from database?");
	deletionDialog.setHeaderText(null);
	deletionDialog.setContentText("This deletion can't be undone.");
	deletionDialog.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = deletionDialog.showAndWait();

	// remove all entries of gene in DB if chosen
	if (result.get() == ButtonType.OK) {
	    geneDB.removeGeneEntry(geneName);
	}

	// update gene name list
	addGeneList(null);
	geneInfoGeneBoxAction(new ActionEvent());

	// print out that gene was removed
	infoLabel.setText("Removed: " + geneName);


    }
    
    
    
    
     /*
    gene info tab save button
    get gene name and save gene info to corresponding gene name
    */
    
    @FXML
    private void geneInfoTabSaveButtonAction(ActionEvent event) {

	String varName = null;
	String geneName = null;

	// get selectd gene and variant
	geneName = findingsTabGeneNameBox.getValue();

	// get geneName and remove all nonalphanumeric elements
	geneName = geneInfoTabGeneNameBox.getValue();
	if (geneInfoTabGeneNameBox.getValue() == null || geneName.equals("")) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	// get gene info text
	String geneInfo;

	if (geneInfoField.getText() == null) {
	    geneInfo = "";
	} else {
	    geneInfo = geneInfoField.getText();
	}

	// save gene info
	geneDB.saveGeneInfo(geneName, geneInfo);
	
	// refresh gene combobox choice
	addGeneList(geneName);


    }

    
    
    
    
    
      
    
    //// selection of genes in the gene info tab
    @FXML
    private void geneInfoGeneBoxAction (ActionEvent event) {
	
	// retrieve string and remove all non alphanumeric elements
	    String geneName = geneInfoTabGeneNameBox.getValue();
	    if (geneInfoTabGeneNameBox.getValue() != null){
		geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	    }
	    
	    
	    // get gene info
	    geneInfoField.setText(geneDB.getGeneInfo(geneName));

	    
	    // fill varNameBox with possible choices and load first entry
	    addVarList(geneName, null);
	    
    }
    
    
    
    
    
    
     
    // add gene names to gene box
    private void addGeneList(String curGene) {
	
	
	// get list and sort it
	List geneList = geneDB.getGeneList();
	geneInfoTabGeneNameBox.getItems().clear();
	geneInfoTabGeneNameBox.getItems().addAll(geneList);
	
	
	if ((curGene == null || curGene.equals("")) && geneList.size() > 0) {
	    curGene = geneList.get(0).toString();
	}
    
	geneInfoTabGeneNameBox.getSelectionModel().select(curGene);
	TextFields.bindAutoCompletion(geneInfoTabGeneNameBox.getEditor(), geneInfoTabGeneNameBox.getItems());

    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////// variants tab
    
    

    //// var info tab remove button
    @FXML
    private void varInfoTabRemoveButtonAction (ActionEvent event) {
	
	// get geneName and remove all nonalphanumeric elements
	String geneName = geneInfoTabGeneNameBox.getValue();
	if (geneName == null) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	
	// retrieve var name 
	String varName = varInfotabVarNameBox.getValue();
	if (varName == null){
	    infoLabel.setText("ERROR: No variant chosen");
	}
	varName = varName.replaceAll("\\s+", "");

	
	
	// double check if  variant should be deleated
	Alert delDiag = new Alert(Alert.AlertType.CONFIRMATION);
	delDiag.setTitle("Remove " + varName + " from database?");
	delDiag.setHeaderText(null);
	delDiag.setContentText("This deletion can't be undone.");
	delDiag.initOwner(infoLabel.getScene().getWindow());
	Optional<ButtonType> result = delDiag.showAndWait();

	// remove var if desired
	if (result.get() == ButtonType.OK) {
	    geneDB.removeVar(geneName, varName);
	}

	// update variant name list
	addVarList(geneName, null);

	// print out that variant was removed
	infoLabel.setText("Removed: " + varName);

    }
    
    
    
    
    
    
    
    
    
    
    /* 
    variant info tab save button
    get gene name and var name and save var info to var name given gene name
    */
    
    @FXML
    private void varInfoTabSaveButtonAction(ActionEvent event) {

	// define variables
	String varName = null;
	String geneName = null;
	
	// get geneName and remove all nonalphanumeric elements
	geneName = geneInfoTabGeneNameBox.getValue();
	if (geneName == null || geneName.equals("")) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	// get variant name
	varName = varInfotabVarNameBox.getValue();
	if (varName == null || varName.equals("")) {
	    infoLabel.setText("ERROR: No variant name given.");
	    return;
	}
	// remove all alphanumeric element from variant
	varName = varName.replaceAll("\\s+", "");

	
	// get variant info
	String varInfo;
	if (varInfoField.getText() == null) {
	    varInfo = "";
	} else {
	    varInfo = varInfoField.getText();
	}

	geneDB.saveVarInfo(geneName, varName, varInfo);

	// refresh gene combobox choice
	addVarList(geneName, varName);

    }




     //// selection of a variant in the variant box
    @FXML
    private void varInfoVarBoxAction (ActionEvent event) {
	
	   // retrieve string and remove all non alphanumeric elements
	    String geneName = geneInfoTabGeneNameBox.getValue();
	    if (geneInfoTabGeneNameBox.getValue() != null){
		geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	    }
	    // retrieve variant
	    String varName = varInfotabVarNameBox.getValue();

	    if (varInfotabVarNameBox.getValue() != null){
		varName = varName.replaceAll("\\s+", "");
	    }
	    // fill var info filed
	    varInfoField.setText(geneDB.getVarInfo(geneName, varName));
	    
	
    }
    
    
    
    
    
    
    
    
    // add variant names to varNameBox
    private void addVarList(String geneName, String curVar) {
	
	// get list of variant for current gene
	List varList = geneDB.getVarList(geneName);
	varInfotabVarNameBox.getItems().clear();
	varInfotabVarNameBox.getItems().addAll(varList);
	
	
	// check if there is a current var
	if (curVar == null || curVar.equals("")) {
	    if (varList.size() > 0) {
		curVar = varList.get(0).toString();
	    } else {
		
		// reset var
		curVar = "";
	    }
	}
	
	varInfotabVarNameBox.getSelectionModel().select(curVar);
	TextFields.bindAutoCompletion(varInfotabVarNameBox.getEditor(), varInfotabVarNameBox.getItems());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////// findings tab
    
    
    /* 
    findings tab save button
    Extract gene name and var name and prepare to save in var and gene info tab
    */
    
    @FXML
    private void findingsSaveButtonAction(ActionEvent event){
	//////////////
	//// prepare for new entry for gene and variant
	// define variables
	String varName = null;
	String geneName = null;

	// get selectd gene and variant
	geneName = findingsTabGeneNameBox.getValue();
	if (findingsTabGeneNameBox.getValue() != null){
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	}
	
	varName = findingsTabVarNameBox.getValue();
	if (findingsTabVarNameBox.getValue() != null) {
	    varName = varName.replaceAll("\\s+", "");
	}
	
	/* 
	    if gene not present in DB add gene and var
	    else if gene is in db but not var add only var to db
	 */
	if (!geneDB.getGeneList().contains(geneName)) {
	    geneDB.saveGeneInfo(geneName, "");
	    geneDB.saveVarInfo(geneName, varName, "");

	    // jump to gene tab
	    tabpane.getSelectionModel().select(geneInfoTab);

	} else if (!geneDB.getVarList(geneName).contains(varName)) {
	    geneDB.saveVarInfo(geneName, varName, "");

	    // jump to var tab
	    tabpane.getSelectionModel().select(varInfoTab);
	} else {
	    
	    
	    // print out that gene and variant already are in DB
	    // jump to gene tab
	    
	    infoLabel.setText("Gene and variant are already in DB.");
	    tabpane.getSelectionModel().select(geneInfoTab);
	    
	    
	}
	
	// refresh gene combobox choice
        addGeneList(geneName);
        addVarList(geneName, varName);

    }
    
    
    
    
    
    
     
    
    /* if gene is selected in findings 
    add corresponding list of variants to variant combobox
    */
    @FXML
    private void findingsGeneBoxAction(ActionEvent event) {
	
	// get selected gene name
	String geneName = findingsTabGeneNameBox.getValue().toString();
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	
	
	
	/////////////////
	/////// fill findings var box with elements
	
	// select all variants for selected gene and add to var name box
	List<String> varList = new LinkedList<>();
	varList = findings.getDependentValueList(config.getcNomenCol(), config.getGeneCol(), geneName, true, ",");
	Collections.sort(varList);
	
	// add list of variants to choice box
	findingsTabVarNameBox.getItems().clear();
	findingsTabVarNameBox.getItems().addAll(varList);
	findingsTabVarNameBox.getSelectionModel().selectFirst();


        // check if gene is in DB
	if (geneDB.getGeneList().contains(geneName)) {

    	    // fill gene info tab
	    addGeneList(geneName);
	    
	    
	    
	    // if gene not in DB print out info
	} else {

	    // make info fild entry
	    infoLabel.setText("No entry for gene " + geneName);
	    
	}

	
    }
    
    
    
    
    
    
    
    //// findings variant box action
    // check if variant is in DB if so save as current default
    @FXML
    private void findingsVarBoxAction (ActionEvent event) {


	// get selected gene name
	String geneName = findingsTabGeneNameBox.getValue().toString();
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");


	//	 get current selected variant
	// handle the case all variants are cleared to avoid error
	String varName = null;
	if (findingsTabVarNameBox.getValue() != null) {
	    varName = findingsTabVarNameBox.getValue().toString();
	    varName = varName.replaceAll("\\s+", "");
	    
	    // cehck if DB contains variant 
	    if (geneDB.getVarList(geneName).contains(varName)) {
		infoLabel.setText("Variant in DB.");
	    } else {
		infoLabel.setText("No entry for " + varName);
	    }

	}
	
	
	
	
	addVarList(geneName, varName);

	
	
    }
    
    
    
    
    
    
    
    
    
    
    
   
    
    
    
    
    ///////////////////
    //////// Adress tab
    
    
    
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
    
     
    //// new receiver
    @FXML
    private void newReceiverButtonAction () {
	
	resetAddressFields();
	
	receiverDB.insert();

	// reset fields
	fillReceiverInfo();
	
	infoLabel.setText("Created a new entry.");

	
    }
    
    
    
    // fill receiver filds
    private void fillReceiverInfo () {
	titleField.setText(receiverDB.getTitle());
	nameField.setValue(receiverDB.getFullName());
	addressField.setText(receiverDB.getPostalAddress());
	cityField.setText(receiverDB.getCity());
	zipCodeField.setText(receiverDB.getZipCode());
	countryField.setText(receiverDB.getCountry());

    }
    
    
    
    
    
    
    //// store receiver data
    @FXML
    private void storeReceiverButtonAction() {

	// set variables
	receiverDB.setTitle(titleField.getText());
	receiverDB.setFullName(nameField.getValue());
	receiverDB.setPostalAddress(addressField.getText());
	receiverDB.setCity(cityField.getText());
	receiverDB.setZipCode(zipCodeField.getText());
	receiverDB.setCountry(countryField.getText());

	
	// check if entry is new (no ID so far)
	if (receiverDB.getId() != null) {

	    // update DB
	    receiverDB.update();
	    infoLabel.setText(nameField.getValue() + " saved.");
	} else {
	    receiverDB.insert();
	    infoLabel.setText(nameField.getValue() + " added to DB.");
	}

	
	// update choosable list
	nameField.getItems().setAll(receiverDB.getNameList());

    }
    
    
    
    
    
    
    
    
    //// remove receiver data
    @FXML
    private void removeReceiverButtonAction() {
	// remove entry
	receiverDB.remove();
	
	// update choices and fields
	resetAddressFields();
	fillReceiverInfo();
	nameField.getItems().setAll(receiverDB.getNameList());
	
	infoLabel.setText(nameField.getValue() + " deleted from DB.");
    }
    
    
    
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




   
    
    
    
    
    
    
    
    
  
    
    
   
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //// initialize DB
    public void init(StoreFindings findings) {
	
	// retrieve variants
	this.findings = findings;

	
	
	// check if any findings are stored if so fill table
	if (findings != null && findings.getStoredData().size() > 0) {


	    // check if databse is connected if not open DB
	    if (!geneDB.isConnected()){
		File dbFile = geneDB.openGeneDB();
		geneDB.connectDB(dbFile);
	    }
	    
	    //// create findings table	
	    CreateTable createTable = new CreateTable(findingsTable);
	    createTable.prepareTable(findings.getHeaderList());
	    createTable.fillTable(findings.getStoredData());


	    
	    /* 
	    get all genes selected
	    sort them and fill gene name choice box with those genes in findings
	    */
	    
	    List<String> geneList = findings.getValueList(config.getGeneCol());
	    
	    // make gene list uniqe
	    geneList = geneList.stream().distinct().collect(Collectors.toList());
	    Collections.sort(geneList);
	    
	    // fill gene name box
	    findingsTabGeneNameBox.getItems().clear();
    	    findingsTabGeneNameBox.getItems().addAll(geneList);
	    findingsTabGeneNameBox.getSelectionModel().selectFirst();
	    

	    
	    
	    // fill findings variant name box
	    findingsGeneBoxAction(new ActionEvent());
	    findingsVarBoxAction(new ActionEvent());
	    geneInfoGeneBoxAction(new ActionEvent());
	    varInfoVarBoxAction(new ActionEvent());

	}
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	//////// prepare DB connection if possible
	
//	geneDB = new GeneDB(dbLabel, infoLabel);
	
	userDB = new UserDB();


	// check if DB saved in cofig exists
	// if so connect to it
        // then check if tables exist
        // if so fill 
	
	
	
	if (config.getDbPath() != null){
	    
	    if (new File(config.getDbPath()).exists() && !new File(config.getDbPath()).isDirectory()){
		File dbFile = new File(config.getDbPath());
		userDB.connectDB(dbFile);
	    }
	}
	
	
	
	/* 
	check if connection is established if so init gene and address class
	using the connection of the superclass
	*/
	
	if (userDB.isConnected()) {
	    
	    geneDB = new GeneDB(dbLabel, infoLabel, userDB.getConn());
	    receiverDB = new ReceiverDB(userDB.getConn());
	    
	}
	
	
	
	//////// prepare FXML elemets
	// prepare varinat chooser box
	varInfotabVarNameBox.setEditable(true);
	
	/////////
	//// prepare geneName box
	geneInfoTabGeneNameBox.setEditable(true);

	
	// if a DB is connected fill gene name choices
	if (geneDB.isConnected()) {

            // if DB has tables load lists
            if (geneDB.hasTable(geneDB.getGeneTable())){
                if (geneDB.tableHasEntry(geneDB.getGeneTable())){
                    addGeneList(null);
                }
            }
	}
	
	
	/* 
	init receiver name combo box
	add auto compleation
	Add action listener
	if name is written check if address is available 
	    if so auto fill other fields
	*/
		
	
	nameField.getItems().setAll(receiverDB.getNameList());
	nameField.setEditable(true);
	TextFields.bindAutoCompletion(nameField.getEditor(), receiverDB.getNameList());
	nameField.valueProperty().addListener(new ChangeListener<String>() {
	    @Override
	    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		fillReceiverData(newValue);
	    }
	});
	
	
	
	

    }    
    
}
