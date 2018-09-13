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
import de.hoppmann.operations.GeneDB;
import java.io.File;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
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
    
    @FXML
    private Button closeButton;
    
    @FXML
    private ComboBox<String> geneNameBox;
    
    @FXML
    private TextArea geneInfoField;
    
    @FXML
    private TextArea varInfoField;
    
    @FXML
    private Label infoLabel;
    
    @FXML
    private Label dbLabel;
    
    @FXML
    private ComboBox<String> varNameBox;
    
    @FXML
    private TabPane tabpane;
    
    @FXML
    private Tab varInfoTab;
    
    @FXML
    private Tab geneInfoTab;

    @FXML
    private Tab findingsTab;
    
    @FXML
    private TableView<TableData> findingsTable;
    
    @FXML
    private ComboBox<String> findingsGeneNameBox;
    
    @FXML 
    private ComboBox<String> findingsVarNameBox;

    
    
    
    private GeneDB geneDB;
    private StoreFindings findings;
    private Config config = Config.getInstance();

    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
    //////// FXML Methods
    











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
        
	File dbFile = geneDB.openDB();
	
	if (dbFile != null) {
	    geneDB.connect(dbFile);
	}
    }
    
    
    
    
    
    
    //// create new database
    @FXML
    private void newDbButtonAction (ActionEvent event) {
        // check if there is already an open connection if so close it
        if (geneDB.isConnected()) {
            geneDB.closeDB();
        }

	File dbFile = geneDB.createNewDB();
	if (dbFile != null) {
	    geneDB.connect(dbFile);
	}
	
	
    }
    
    
    
    ////////////////////////////////
    //////// handle different remove buttons
    
    
    /* 
    gene info tab remove button
    get gene name and remove gene entry and all variants listed to this gene
    */
    
    @FXML
    private void geneInfoTabRemoveButtonAction (ActionEvent event) {
	
	// get geneName and remove all nonalphanumeric elements
	String geneName = geneNameBox.getValue();

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
	loadAllGeneList(null);
	geneInfoGeneBoxAction(new ActionEvent());

	// print out that gene was removed
	infoLabel.setText("Removed: " + geneName);


    }
    
    
    

    //// var info tab remove button
    @FXML
    private void varInfoTabRemoveButtonAction (ActionEvent event) {
	
	// get geneName and remove all nonalphanumeric elements
	String geneName = geneNameBox.getValue();
	if (geneName == null) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	
	// retrieve var name 
	String varName = varNameBox.getValue();
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
	loadAllVarList(geneName, null);

	// print out that variant was removed
	infoLabel.setText("Removed: " + varName);

    }
    
    
    //////////////////////////
    //////// handle different save buttons
    
    
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
	geneName = findingsGeneNameBox.getValue();
	if (findingsGeneNameBox.getValue() != null){
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	}
	
	varName = findingsVarNameBox.getValue();
	if (findingsVarNameBox.getValue() != null) {
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
        loadAllGeneList(geneName);
        loadAllVarList(geneName, varName);

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
	geneName = findingsGeneNameBox.getValue();

	// get geneName and remove all nonalphanumeric elements
	geneName = geneNameBox.getValue();
	if (geneNameBox.getValue() == null || geneName.equals("")) {
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
	loadAllGeneList(geneName);


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
	geneName = geneNameBox.getValue();
	if (geneName == null || geneName.equals("")) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	// get variant name
	varName = varNameBox.getValue();
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
	loadAllVarList(geneName, varName);

    }








    
    
    ////////////////////////////////
    /* if gene is selected in findings 
    add corresponding list of variants to variant combobox
    */
    @FXML
    private void findingsGeneBoxAction(ActionEvent event) {
	
	// get selected gene name
	String geneName = findingsGeneNameBox.getValue().toString();
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	
	
	
	/////////////////
	/////// fill findings var box with elements
	
	// select all variants for selected gene and add to var name box
	List<String> varList = new LinkedList<>();
	varList = findings.getDependentValueList(config.getcNomenCol(), config.getGeneCol(), geneName, true, ",");
	Collections.sort(varList);
	
	// add list of variants to choice box
	findingsVarNameBox.getItems().clear();
	findingsVarNameBox.getItems().addAll(varList);
	findingsVarNameBox.getSelectionModel().selectFirst();


        // check if gene is in DB
	if (geneDB.getGeneList().contains(geneName)) {

    	    // fill gene info tab
	    loadAllGeneList(geneName);
	    
	    
	    
	    // if gene not in DB print out info
	} else {

	    // make info fild entry
	    infoLabel.setText("No entry for gene " + geneName);
	    
	}

	
    }
    
    
    
    
    ////////////////
    //// findings variant box action
    // check if variant is in DB if so save as current default
    @FXML
    private void findingsVarBoxAction (ActionEvent event) {


	// get selected gene name
	String geneName = findingsGeneNameBox.getValue().toString();
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");


	//	 get current selected variant
	// handle the case all variants are cleared to avoid error
	String varName = null;
	if (findingsVarNameBox.getValue() != null) {
	    varName = findingsVarNameBox.getValue().toString();
	    varName = varName.replaceAll("\\s+", "");
	    
	    // cehck if DB contains variant 
	    if (geneDB.getVarList(geneName).contains(varName)) {
		infoLabel.setText("Variant in DB.");
	    } else {
		infoLabel.setText("No entry for " + varName);
	    }

	}
	
	
	
	
	loadAllVarList(geneName, varName);

	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///////////////////////////
    //// selection of genes in the gene info tab
    @FXML
    private void geneInfoGeneBoxAction (ActionEvent event) {
	
	// retrieve string and remove all non alphanumeric elements
	    String geneName = geneNameBox.getValue();
	    if (geneNameBox.getValue() != null){
		geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	    }
	    
	    
	    // get gene info
	    geneInfoField.setText(geneDB.getGeneInfo(geneName));

	    
	    // fill varNameBox with possible choices and load first entry
	    loadAllVarList(geneName, null);
	    
    }
    
    
    
    
    
    ////////////////////
    //// selection of a variant in the variant box
    @FXML
    private void varInfoVarBoxAction (ActionEvent event) {
	
	   // retrieve string and remove all non alphanumeric elements
	    String geneName = geneNameBox.getValue();
	    if (geneNameBox.getValue() != null){
		geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	    }
	    // retrieve variant
	    String varName = varNameBox.getValue();

	    if (varNameBox.getValue() != null){
		varName = varName.replaceAll("\\s+", "");
	    }
	    // fill var info filed
	    varInfoField.setText(geneDB.getVarInfo(geneName, varName));
	    
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////
    //////// non FXML methods

    

    
    // add gene names to gene box
    private void loadAllGeneList(String curGene) {
	// get list and sort it
	List geneList = geneDB.getGeneList();
	geneNameBox.getItems().clear();
	geneNameBox.getItems().addAll(geneList);
	
	if (curGene == null || curGene.equals("")) {
	    curGene = geneList.get(0).toString();
	}
    
	geneNameBox.getSelectionModel().select(curGene);
	TextFields.bindAutoCompletion(geneNameBox.getEditor(), geneNameBox.getItems());

    }
    
    
    // add variant names to varNameBox
    private void loadAllVarList(String geneName, String curVar) {
	
	// get list of variant for current gene
	List varList = geneDB.getVarList(geneName);
	varNameBox.getItems().clear();
	varNameBox.getItems().addAll(varList);
	
	
	// check if there is a current var
	if (curVar == null || curVar.equals("")) {
	    if (varList.size() > 0) {
		curVar = varList.get(0).toString();
	    } else {
		
		// reset var
		curVar = "";
	    }
	}
	
	varNameBox.getSelectionModel().select(curVar);
	TextFields.bindAutoCompletion(varNameBox.getEditor(), varNameBox.getItems());
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //// initialize DB
    public void init(StoreFindings findings) {
	
	// retrieve variants
	this.findings = findings;

	
	// check if any findings are stored if so fill table
	if (findings != null && findings.getStoredData().size() > 0) {


		// check if databse is connected if not open DB
	    if (!geneDB.isConnected()){
		File dbFile = geneDB.openDB();
		geneDB.connect(dbFile);
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
	    // uniquify gene list
	    geneList = geneList.stream().distinct().collect(Collectors.toList());
	    Collections.sort(geneList);
	    
	    // fill gene name box
	    findingsGeneNameBox.getItems().clear();
    	    findingsGeneNameBox.getItems().addAll(geneList);
	    findingsGeneNameBox.getSelectionModel().selectFirst();
	    

	    
	    
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
	
	geneDB = new GeneDB(dbLabel, infoLabel);
	
	// check if DB saved in cofig exists
	// if so connect to it
        // then check if tables exist
        // if so fill 
	
	
	
	if (config.getDbPath() != null){
	    
	    if (new File(config.getDbPath()).exists() && !new File(config.getDbPath()).isDirectory()){
		File dbFile = new File(config.getDbPath());
		geneDB.connect(dbFile);
	    }
	}
	
	
	
	
	
	
	//////// prepare FXML elemets
	// prepare varinat chooser box
	varNameBox.setEditable(true);
	
	/////////
	//// prepare geneName box
	geneNameBox.setEditable(true);

	
	// if a DB is connected fill gene name choices
	if (geneDB.isConnected()) {

            // if DB has tables load lists
            if (geneDB.hasTable(geneDB.getGeneTable())){
                if (geneDB.tableHasEntry(geneDB.getGeneTable())){
                    loadAllGeneList(null);
                }
            }
	}
	
	

    }    
    
}
