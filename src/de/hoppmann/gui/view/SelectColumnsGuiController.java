/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class SelectColumnsGuiController implements Initializable {

    
    
    ///////////////////////////
    //////// Variables ////////
    ///////////////////////////
    
    // non FXML
    private Config config;
    private ObservableList<String> header;
    private StoreFindings findings;

//    private String geneCol = "gene";
    
    @FXML
    private ComboBox<String> geneCol = new ComboBox<>();
    @FXML
    private ComboBox<String> impactCol = new ComboBox<>();
    @FXML
    private ComboBox<String> pNomenCol = new ComboBox<>();
    @FXML
    private ComboBox<String> cNomenCol = new ComboBox<>();
    @FXML
    private ComboBox<String> col5 = new ComboBox<>();
    @FXML
    private ComboBox<String> predScoreCol = new ComboBox<>();
    @FXML
    private ComboBox<String> totPredCol = new ComboBox<>();
    @FXML
    private ComboBox<String> hgmdCol = new ComboBox<>();
    @FXML
    private ComboBox<String> clinVarCol = new ComboBox<>();
    @FXML
    private ComboBox<String> HGMD = new ComboBox<>();
    @FXML
    private ComboBox<String> splice45Col = new ComboBox<>();
    @FXML
    private ComboBox<String> splice15Col = new ComboBox<>();
    @FXML
    private ComboBox<String> col7 = new ComboBox<>();
    
    
    @FXML
    private Button closeButton;
    @FXML
    private Button okButton;
    
    @FXML
    private ChoiceBox<String> choice = new ChoiceBox<>();
    
    
    
    
    
    /////////////////////////
    //////// Methods ////////
    /////////////////////////
    
    //////// close window without storing columns chosen
    @FXML
    private void closeButtonEvent(ActionEvent event) {
	Stage stage = (Stage) closeButton.getScene().getWindow();
	stage.close();
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //////// save chosen values for current session only
    @FXML
    private void okButtonEvent(ActionEvent event) {
	
	//////// store selection  
	// geneCol
	findings.addIndex(geneCol.getValue(), header.indexOf(geneCol.getValue()));
	findings.setGeneCol(geneCol.getValue());
	
	// impactCol
	findings.addIndex(impactCol.getValue(), header.indexOf(impactCol.getValue()));
	findings.setImpactCol(impactCol.getValue());
	
	// protNomenCol
	findings.addIndex(pNomenCol.getValue(), header.indexOf(pNomenCol.getValue()));
	findings.setpNomenCol(pNomenCol.getValue());
	
	//cNomenCol
	findings.addIndex(cNomenCol.getValue(), header.indexOf(cNomenCol.getValue()));
	findings.setcNomenCol(cNomenCol.getValue());
	
	
	
	// close window
	Stage stage = (Stage) okButton.getScene().getWindow();
	stage.close();
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////// save chosen values as default
    @FXML
    private void saveDefaultButtonEvent(ActionEvent event) {
	
	config.setGeneCol(geneCol.getValue());
	config.setImpactCol(impactCol.getValue());
	config.setcNomenCol(cNomenCol.getValue());
	config.setpNomenCol(pNomenCol.getValue());
	config.setClinvarCol(clinVarCol.getValue());
	config.setHgmdCol(hgmdCol.getValue());
	config.setPredScoreCol(predScoreCol.getValue());
	config.setSplice15Col(splice15Col.getValue());
	config.setSplice45Col(splice45Col.getValue());
	config.setTotPredCol(totPredCol.getValue());
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    // check if header name in config file exists if so use as column
    private boolean checkStringList(ObservableList<String> list, String searchStr) {
	boolean check = false;
	
	for (String str : list) {
	    
	    if (str.trim().equals(searchStr)){
		check=true;
	    }
	}
	return check;
    }
    
    

    /////////////////////////////
    //////// Constructor ////////
    /////////////////////////////

    public SelectColumnsGuiController(StoreFindings findings) {
	this.findings = findings;
	this.header = FXCollections.observableArrayList(findings.getHeaderList());
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


	// init config file
	config = new Config();

	
	
	
	
	
	
	
	/**
	 * 
	 * prepare gene column
	 * check if there is allready a column chosen previously if so use that one 
	 * else use the one from the config file
	*/
	geneCol.getItems().addAll(header);
	if (findings.getGeneCol() != null && checkStringList(header, findings.getGeneCol()) == true){
	    geneCol.getSelectionModel().select(findings.getGeneCol());
	} else if (checkStringList(header, config.getGeneCol()) == true) {
	    geneCol.getSelectionModel().select(config.getGeneCol());
	}
	
	
	// prepare impact column
	impactCol.getItems().addAll(header);
	if (findings.getImpactCol() != null && checkStringList(header, findings.getImpactCol()) == true){
	    geneCol.getSelectionModel().select(findings.getImpactCol());
	} else if (checkStringList(header, config.getImpactCol()) == true){
	    impactCol.getSelectionModel().select(config.getImpactCol());
	}
	
	// prepare cDNA nomencalture column
	cNomenCol.getItems().addAll(header);
	if (findings.getcNomenCol()!= null && checkStringList(header, findings.getcNomenCol()) == true){
	    geneCol.getSelectionModel().select(findings.getcNomenCol());
	} else if (checkStringList(header, config.getcNomenCol()) == true){
	    cNomenCol.getSelectionModel().select(config.getcNomenCol());
	}
	
	// prepare protein nomenclature column
	pNomenCol.getItems().addAll(header);
	if (findings.getpNomenCol()!= null && checkStringList(header, findings.getpNomenCol()) == true){
	    geneCol.getSelectionModel().select(findings.getpNomenCol());
	} else if(checkStringList(header, config.getpNomenCol()) == true){
	    pNomenCol.getSelectionModel().select(config.getpNomenCol());
	}
	
	
	// prepare prediction Score column
	predScoreCol.getItems().addAll(header);
	if (checkStringList(header, config.getPredScoreCol())) {
	    predScoreCol.getSelectionModel().select(config.getPredScoreCol());
	}
	
	
	// prepare total number of predictions column
	totPredCol.getItems().addAll(header);
	if (checkStringList(header, config.getTotPredCol())) {
	    totPredCol.getSelectionModel().select(config.getTotPredCol());
	}
	
	
	// prepare ClinVar column
	clinVarCol.getItems().addAll(header);
	if (checkStringList(header, config.getClinvarCol())) {
	    clinVarCol.getSelectionModel().select(config.getClinvarCol());
	}
	
	// prepare HGMD column
	hgmdCol.getItems().addAll(header);
	if (checkStringList(header, config.getHgmdCol())) {
	    hgmdCol.getSelectionModel().select(config.getHgmdCol());
	}
	
	// prepare splice 15 reduction prediction column
	splice15Col.getItems().addAll(header);
	if (checkStringList(header, config.getSplice15Col())) {
	    splice15Col.getSelectionModel().select(config.getSplice15Col());
	}
	
	// prepare splice 45 reduction prediction column
	splice45Col.getItems().addAll(header);
	if (checkStringList(header, config.getSplice45Col())) {
	    splice45Col.getSelectionModel().select(config.getSplice45Col());
	}
	
	
	
	
	
	
	
    }    
    
}
