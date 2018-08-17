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
    private String curGene;
    private String curVar;
    private StoreFindings findings;
    private Config config;
    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
    //////// FXML Methods
    











    //// close window
    @FXML
    private void closeButtonAction (ActionEvent event) {
	
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
	
    }
    
    
    
    
    
    
    
    
    //// open DB
    @FXML
    private void openDbButtonAction (ActionEvent event) {
	
	File dbFile = geneDB.openDB();
	
	if (dbFile != null) {
	    geneDB.connect(dbFile);
	}
    }
    
    
    
    
    
    
    //// create new database
    @FXML
    private void newDbButtonAction (ActionEvent event) {
	
	File dbFile = geneDB.createNewDB();
	if (dbFile != null) {
	    geneDB.connect(dbFile);
	}
	
	
    }
    
    
    
    
    
    
    @FXML 
    private void saveButtonAction (ActionEvent event) {
	
	// define variables
	String varName = null;
	String geneName = null;
	
	String geneInfo = geneInfoField.getText();
	

	
	
	//check the selected Tab and save correspondigly
	
	Tab curTab = tabpane.getSelectionModel().getSelectedItem();
	

	//// handle case of selection on findings tab
	if (curTab == findingsTab) {

	    
	    ///////////////
	    //// prepare for new entry for gene and variant
	    
	    
    	    // get selectd gene and variant
	    geneName = findingsGeneNameBox.getValue().toString();
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	    
	    varName = findingsVarNameBox.getValue().toString();
	    varName = varName.replaceAll("\\s+", "");

	    /* 
	    if gene not present in DB add gene and var
	    else if gene is in db but not var add only var to db
	    */
	    if (! geneDB.getGeneList().contains(geneName)){
		geneDB.saveGeneInfo(geneName, "");
		geneDB.saveVar(geneName, varName, "");
	    
		// jump to gene tab
	        tabpane.getSelectionModel().select(geneInfoTab);

	    } else if (! geneDB.getVarList(geneName).contains(varName)) {
		geneDB.saveVar(geneName, varName, "");
		
		// jump to var tab
		tabpane.getSelectionModel().select(varInfoTab);
	    }
	    

	    
	    
	    

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //////////////////////
	    //// handle  case of selection on gene tabs
	} else if (curTab == geneInfoTab) {

	    // get geneName and remove all nonalphanumeric elements
	    geneName = geneNameBox.getValue();
	    if (geneName == null) {
		infoLabel.setText("ERROR: No gene chosen.");
		return;
	    }
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	    geneDB.saveGeneInfo(geneName, geneInfo);

	    
	    
	    
	    
	    
	    
	    
	    
	    
	    
	    //////////////////////
	    //// handle case of selection on var tab
	} else if (curTab == varInfoTab) {

	    // get geneName and remove all nonalphanumeric elements
	    geneName = geneNameBox.getValue();
	    if (geneName == null) {
		infoLabel.setText("ERROR: No gene chosen.");
		return;
	    }
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	    
	    
	    
	    
	    
	    
	    // get variant name
	    String varInfo = varInfoField.getText();
	    varName = varNameBox.getValue();
	    if(varName == null) {
		infoLabel.setText("ERROR: No variant name given.");
		return;
	    }
	    // remove all alphanumeric element from variant
	    varName = varName.replaceAll("\\s+", "");
	    
	    geneDB.saveVar(geneName, varName, varInfo);
	    
	}
	
	
	// refresh gene combobox choice
	curGene = geneName;
	curVar = varName;
	loadAllGeneList();
	loadAllVarList(geneName);
	
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
	varList = findings.getDependentValueList(config.getcNomenCol(), config.getGeneCol(), geneName);
	Collections.sort(varList);
	
	// add list of variants to choice box
	findingsVarNameBox.getItems().addAll(varList);
	findingsVarNameBox.getSelectionModel().selectFirst();

	System.out.println(varList);
	// retrieve name of current variant
	curVar = findingsVarNameBox.getValue().toString();
	
	

        // check if gene is in DB
	if (geneDB.getGeneList().contains(geneName)) {

	    // set curGene to genename -> will fill combobox with this value
	    curGene = geneName;
	    
    	    // fill gene info tab
	    loadAllGeneList();
	    
	    
	    // fill variant info box
	    loadAllVarList(geneName);
	    
	    
//	    geneInfoGeneBoxAction(new ActionEvent());

	    // fill 
//    	    findingsVarBoxAction(new ActionEvent());

	    
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

	// get current selected variant
	String varName = findingsVarNameBox.getValue().toString();
	varName = varName.replaceAll("\\s+", "");
	
	
	if (geneDB.getVarList(geneName).contains(varName)) {
	    infoLabel.setText("Variant in DB.");
	} else {
	    infoLabel.setText("No entry for " + varName);
	}
    
	curVar = varName;
	loadAllVarList(geneName);
	
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    
//    ///////////////////////////
//    //// selection of genes in the gene info tab
//    @FXML
//    private void geneInfoGeneBoxAction (ActionEvent event) {
//	
//	// retrieve string and remove all non alphanumeric elements
//	    String geneName = geneNameBox.getValue();
//	    if (geneNameBox.getValue() != null){
//		geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
//	    }
//	    
//	    // get gene info
//	    geneInfoField.setText(geneDB.getGeneInfo(geneName));
//	    curGene = geneName;
//
//	    // fill varNameBox with possible choices and load first entry
//	    
//	    
//	    addVarListToVarBox(geneName);
//	    if (varNameBox.getValue() != null) {
//		varNameBox.getSelectionModel().selectFirst();
//		geneDB.getVarInfo(geneName, varNameBox.getValue().toString());
//	    }
//	
//    }
    
    
    
    
    
//    ////////////////////
//    //// selection of a variant in the variant box
//    @FXML
//    private void varInfoVarBoxAction (ActionEvent event) {
//	
//	   // retrieve string and remove all non alphanumeric elements
//	    String geneName = geneNameBox.getValue();
//	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
//
//	    
//	    ///// load new gene
//	    if (curGene != geneName) {
//		
//		// get gene info
//		geneInfoField.setText(geneDB.getGeneInfo(geneName));
//		curGene = geneName;
//
//		// fill varNameBox with possible choices and load first entry
//		addVarListToVarBox(geneName);
//		if (varNameBox.getValue() != null) {
//		    varNameBox.getSelectionModel().selectFirst();
//		    geneDB.getVarInfo(geneName, varNameBox.getValue().toString());
//		}
//	    } else {
//		
//		// load chosen variant
//		
//		String varName = varNameBox.getValue().toString();
//		System.out.println(varName);
//		if (varName != null) {
//		    varName = varName.replaceAll("\\s+", "");
//		}
//		
//		// remember current variant
//		curVar = varName;
//		
//		varInfoField.setText(geneDB.getVarInfo(geneName, varName));
//		
//	    }
	
	
//    }
    
    
    
    
    
    
    
    
    
    
    
//    ////////////
//    //// get gene info from DB
//    
//    @FXML
//    private void showButtonAction(ActionEvent event) {
//	
//
//	/* 
//	check current tab
//	if curTab = findings tab
//	    check if selected gene exists in DB
//		load gene || give out warning
//		switch to gene tab
//
//	
//	if curTab = gene tab 
//	    load new gene
//	    fill varNameBox with possible choices
//	
//	if tab = var tab 
//	    check if gene was changed
//		load new gene
//	    else load chosen variant
//	*/
//	
//	
//	///////////////////////////////////////////
//	//// handle case that finding tab is chosen
//	if (tabpane.getSelectionModel().getSelectedItem() == findingsTab && findings != null) {
//
//
//	    // get selectd gene and variant
//	    String geneName = findingsGeneNameBox.getValue().toString();
//	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
//	    
//	    String varName = findingsVarNameBox.getValue().toString();
//	    varName = varName.replaceAll("\\s+", "");
//	    
//	    
//	    // check if there are entries for this gene and variant
//	    
//	    if (geneDB.getGeneList().contains(geneName)){
//
//		// set combobox for this gene
//		curGene = geneName;
//		// check for existence of variant in db
//		if (geneDB.getVarList(geneName).contains(varName)){
//		    
//		    // set var combobox for this variant
//		    curVar = varName;
//		    
//		} else {
//		    // write out that variant is not found
//		    infoLabel.setText("No entry for variant " + varName);
//		}
//		    
//
//		
//		    // prepare for new entry of variant
//		
//		
//		
//		
//		// preset both in other tabs
//		
//	    } else {
//		// make info fild entry
//		infoLabel.setText("No entry for gene " + geneName);
//		
//	    }
//	    
//	    
//	    
//	    
//	    
//	    
//	    // create new gene entry
//
//	    
//	    
//	    
//	    ////////////////////////////////////////
//	    //// handle case that gene tab is chosen
//	    
//	} else if (tabpane.getSelectionModel().getSelectedItem() == geneInfoTab) {
//
//	    // retrieve string and remove all non alphanumeric elements
//	    String geneName = geneNameBox.getValue();
//	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
//
//
//	    // get gene info
//	    geneInfoField.setText(geneDB.getGeneInfo(geneName));
//	    curGene = geneName;
//
//	    // fill varNameBox with possible choices and load first entry
//	    addVarListToVarBox(geneName);
//	    if (varNameBox.getValue() != null) {
//		varNameBox.getSelectionModel().selectFirst();
//		geneDB.getVarInfo(geneName, varNameBox.getValue().toString());
//	    }
//
//	    
//	    
//	    
//	    
//	    
//	    
//	    ///////////////////////////////////////
//	    //// handle case that var tab is chosen
//	} else if (tabpane.getSelectionModel().getSelectedItem() == varInfoTab) {
//
//	    
//	    // retrieve string and remove all non alphanumeric elements
//	    String geneName = geneNameBox.getValue();
//	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
//
//	    
//	    ///// load new gene
//	    if (curGene != geneName) {
//		
//		// get gene info
//		geneInfoField.setText(geneDB.getGeneInfo(geneName));
//		curGene = geneName;
//
//		// fill varNameBox with possible choices and load first entry
//		addVarListToVarBox(geneName);
//		if (varNameBox.getValue() != null) {
//		    varNameBox.getSelectionModel().selectFirst();
//		    geneDB.getVarInfo(geneName, varNameBox.getValue().toString());
//		}
//	    } else {
//		
//		// load chosen variant
//		
//		String varName = varNameBox.getValue().toString();
//	        varName = varName.replaceAll("\\s+", "");
//
//		
//		// remember current variant
//		curVar = varName;
//		
//		varInfoField.setText(geneDB.getVarInfo(geneName, varName));
//		
//	    }
//	    
//	    
//	} // end check of chosen tab
//
//	
//	
//	
//    }
    
    
    
    
    
    
    ///////////////////
    //////// remove gene entry from DB
    @FXML
    private void removeButtonAction(ActionEvent event) {
	

	// get geneName and remove all nonalphanumeric elements
	String geneName = geneNameBox.getValue();

	
	if (geneName == null) {
	    infoLabel.setText("ERROR: No gene chosen.");
	    return;
	}
	geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");

	
	
	/* 
	check current tab and proceed accordingly
	if gene tab
	    remove gene and all variants of gene
	
	if var tab
	    remove chosen variant
	*/
	
	
	if (tabpane.getSelectionModel().getSelectedItem() == geneInfoTab) {
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
	    
	    // set curGene to null 
	    curGene = null;
	    
	} else if (tabpane.getSelectionModel().getSelectedItem() == varInfoTab) {
	    
	    // retrieve var name 
	    String varName = varNameBox.getValue().toString();
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
		curVar = null;
	    }
	    
	    
	} 
	
	
	
	
	// update gene name list
	loadAllGeneList();
	loadAllVarList(geneName);

	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////
    //////// non FXML methods

    

    
    // add gene names to gene box
    private void loadAllGeneList() {
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
    private void loadAllVarList(String geneName) {

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
	if (findings != null) {

	    
	    
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
	    
	    // 
	    findingsGeneNameBox.getItems().clear();
    	    findingsGeneNameBox.getItems().addAll(geneList);
	    findingsGeneNameBox.getSelectionModel().selectFirst();
	    
	    
	    
	    
	    // fill findings variant name box
	    findingsGeneBoxAction(new ActionEvent());
	    
	    
	}
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	
	// init conifg
	config = new Config();
	
	//////// prepare DB connection if possible
	
	geneDB = new GeneDB(dbLabel, infoLabel);
	
	// check if DB saved in cofig exists
	// if so connect to it
	
	if (config.getDbFullPath() != null){
	    
	    if (new File(config.getDbFullPath()).exists() && !new File(config.getDbFullPath()).isDirectory()){
		File dbFile = new File(config.getDbFullPath());
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
	    loadAllGeneList();
	}
	

    }    
    
}
