/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.selectColumnView;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.stage.Stage;
import org.controlsfx.control.textfield.TextFields;

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
    private FindingsRepository findings;

    @FXML private ComboBox<String> geneCol = new ComboBox<>();
    @FXML private Label geneLabel;
    @FXML private ComboBox<String> impactCol = new ComboBox<>();
    @FXML private Label impactLabel;
    @FXML private ComboBox<String> pNomenCol = new ComboBox<>();
    @FXML private Label pNomenLabel;
    @FXML private ComboBox<String> cNomenCol = new ComboBox<>();
    @FXML private Label cNomenLabel;
    @FXML private ComboBox<String> pubMedIdCol = new ComboBox<>();
    @FXML private Label pubMedIdlabel;
    @FXML private ComboBox<String> predScoreCol = new ComboBox<>();
    @FXML private Label predScoreLabel;
    @FXML private ComboBox<String> totPredCol = new ComboBox<>();
    @FXML private Label totPredlabel;
    @FXML private ComboBox<String> hgmdCol = new ComboBox<>();
    @FXML private Label hgmdLabel;
    @FXML private ComboBox<String> clinVarCol = new ComboBox<>();
    @FXML private Label clinVarLabel;
    @FXML private ComboBox<String> splice45Col = new ComboBox<>();
    @FXML private Label splice45Label;
    @FXML private ComboBox<String> splice15Col = new ComboBox<>();
    @FXML private Label splice15Label;
    @FXML private ComboBox<String> rsIdCol = new ComboBox<>();
    @FXML private Label rsIdLabel;
    @FXML private ComboBox<String> mafCol = new ComboBox<>();
    @FXML private Label mafLabel;
    @FXML private ComboBox<String> zygocityCol = new ComboBox<>();
    @FXML private Label zygocityLabel;
    
    @FXML
    private Button closeButton;
    @FXML
    private Button okButton;
    
    
    
    
    
    
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
	
	// save in config
	saveDefault();
	
	
	// close window
	Stage stage = (Stage) okButton.getScene().getWindow();
	stage.close();
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    //////// save chosen values as default
    private void saveDefault() {
	
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
	config.setPubMedIdCol(pubMedIdCol.getValue());
	config.setRsIdCol(rsIdCol.getValue());
	config.setImpactCol(impactCol.getValue());
	config.setMafCol(mafCol.getValue());
	config.setZygocityCol(zygocityCol.getValue());
	
	
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

//    public SelectColumnsGuiController(FindingsRepository findings) {
//	this.findings = findings;
//	this.header = FXCollections.observableArrayList(findings.getHeaderList());
//    }
    
    
    
    public void init (FindingsRepository findings){
        this.findings = findings;
	this.header = FXCollections.observableArrayList(findings.getHeaderList());
        
        
        
        
        
        /*
	  prepare gene column
	  check if there is allready a column chosen previously if so use that one 
	  else use the one from the config file
	*/
	geneLabel.setTooltip(new Tooltip(ColViewTooltipTexts.GENE_COL_TP_TEXT));
	geneCol.setEditable(true);
	geneCol.getItems().addAll(header);
	if (findings.getGeneCol() != null && checkStringList(header, findings.getGeneCol()) == true){
	    geneCol.getSelectionModel().select(findings.getGeneCol());
	} else if (checkStringList(header, config.getGeneCol()) == true) {
	    geneCol.getSelectionModel().select(config.getGeneCol());
	}
        TextFields.bindAutoCompletion(geneCol.getEditor(), header);
	
	
	// prepare impact column
	impactLabel.setTooltip(new Tooltip(ColViewTooltipTexts.IMPACT_COL_TP_TEXT));
	impactCol.setEditable(true);
	impactCol.getItems().addAll(header);
	if (findings.getImpactCol() != null && checkStringList(header, findings.getImpactCol()) == true){
	    geneCol.getSelectionModel().select(findings.getImpactCol());
	} else if (checkStringList(header, config.getImpactCol()) == true){
	    impactCol.getSelectionModel().select(config.getImpactCol());
	}
	TextFields.bindAutoCompletion(impactCol.getEditor(), header);

	
	
	// prepare cDNA nomencalture column
	cNomenLabel.setTooltip(new Tooltip(ColViewTooltipTexts.CNOMEN_COL_TP_TEXT));
	cNomenCol.setEditable(true);
	cNomenCol.getItems().addAll(header);
	if (findings.getcNomenCol()!= null && checkStringList(header, findings.getcNomenCol()) == true){
	    geneCol.getSelectionModel().select(findings.getcNomenCol());
	} else if (checkStringList(header, config.getcNomenCol()) == true){
	    cNomenCol.getSelectionModel().select(config.getcNomenCol());
	}
        TextFields.bindAutoCompletion(cNomenCol.getEditor(), header);

	
	
	
	// prepare protein nomenclature column
	pNomenLabel.setTooltip(new Tooltip(ColViewTooltipTexts.PNOMEN_COL_TP_TEXT));
	pNomenCol.setEditable(true);
	pNomenCol.getItems().addAll(header);
	if (findings.getpNomenCol()!= null && checkStringList(header, findings.getpNomenCol()) == true){
	    geneCol.getSelectionModel().select(findings.getpNomenCol());
	} else if(checkStringList(header, config.getpNomenCol()) == true){
	    pNomenCol.getSelectionModel().select(config.getpNomenCol());
	}
        TextFields.bindAutoCompletion(pNomenCol.getEditor(), header);
	
		
	
	// prepare prediction Score column
	predScoreLabel.setTooltip(new Tooltip(ColViewTooltipTexts.PREDSCORE_COL_TP_TEXT));
	predScoreCol.setEditable(true);
	predScoreCol.getItems().addAll(header);
	if (checkStringList(header, config.getPredScoreCol())) {
	    predScoreCol.getSelectionModel().select(config.getPredScoreCol());
	}
	TextFields.bindAutoCompletion(predScoreCol.getEditor(), header);
	
	
	
	// prepare total number of predictions column
	totPredlabel.setTooltip(new Tooltip(ColViewTooltipTexts.TOTPRED_COL_TP_TEXT));
	totPredCol.setEditable(true);
	totPredCol.getItems().addAll(header);
	if (checkStringList(header, config.getTotPredCol())) {
	    totPredCol.getSelectionModel().select(config.getTotPredCol());
	}
        TextFields.bindAutoCompletion(totPredCol.getEditor(), header);
		
	
	
	// prepare ClinVar column
	clinVarLabel.setTooltip(new Tooltip(ColViewTooltipTexts.CLINVAR_COL_TP_TEXT));
	clinVarCol.setEditable(true);
	clinVarCol.getItems().addAll(header);
	if (checkStringList(header, config.getClinvarCol())) {
	    clinVarCol.getSelectionModel().select(config.getClinvarCol());
	}
	TextFields.bindAutoCompletion(clinVarCol.getEditor(), header);
	
	
	
	// prepare HGMD column
	hgmdLabel.setTooltip(new Tooltip(ColViewTooltipTexts.HGMD_COL_TP_TEXT));
	hgmdCol.setEditable(true);
	hgmdCol.getItems().addAll(header);
	if (checkStringList(header, config.getHgmdCol())) {
	    hgmdCol.getSelectionModel().select(config.getHgmdCol());
	}
	TextFields.bindAutoCompletion(hgmdCol.getEditor(), header);
	
	
	
	// prepare splice 15 reduction prediction column
	splice15Label.setTooltip(new Tooltip(ColViewTooltipTexts.SPLICE15_COL_TP_TEXT));
	splice15Col.setEditable(true);
	splice15Col.getItems().addAll(header);
	if (checkStringList(header, config.getSplice15Col())) {
	    splice15Col.getSelectionModel().select(config.getSplice15Col());
	}
	TextFields.bindAutoCompletion(splice15Col.getEditor(), header);
	
	
	
	// prepare splice 45 reduction prediction column
	splice45Label.setTooltip(new Tooltip(ColViewTooltipTexts.SPLICE45_COL_TP_TEXT));
	splice45Col.setEditable(true);
	splice45Col.getItems().addAll(header);
	if (checkStringList(header, config.getSplice45Col())) {
	    splice45Col.getSelectionModel().select(config.getSplice45Col());
	}
	TextFields.bindAutoCompletion(splice45Col.getEditor(), header);
	
	
	
	// prepare pubMed ID column
	pubMedIdlabel.setTooltip(new Tooltip(ColViewTooltipTexts.PUBMEDID_COL_TP_TEXT));
	pubMedIdCol.setEditable(true);
	pubMedIdCol.getItems().addAll(header);
	if(checkStringList(header, config.getPubMedIdCol())) {
	    pubMedIdCol.getSelectionModel().select(config.getPubMedIdCol());
	}
	TextFields.bindAutoCompletion(pubMedIdCol.getEditor(), header);
	
	
	
	// prepare rsId column
	rsIdLabel.setTooltip(new Tooltip(ColViewTooltipTexts.RSID_COL_TP_TEXT));
	rsIdCol.setEditable(true);
	rsIdCol.getItems().addAll(header);
	if(checkStringList(header, config.getRsIdCol())) {
	    rsIdCol.getSelectionModel().select(config.getRsIdCol());
	}
	TextFields.bindAutoCompletion(rsIdCol.getEditor(), header);
	
	
	
	// prepare zygocity column
	zygocityLabel.setTooltip(new Tooltip(ColViewTooltipTexts.ZYGOCITY_COL_TEXT));
	zygocityCol.setEditable(true);
	zygocityCol.getItems().addAll(header);
	if (checkStringList(header, config.getZygocityCol())){
	    zygocityCol.getSelectionModel().select(config.getZygocityCol());
	}
	TextFields.bindAutoCompletion(zygocityCol.getEditor(), header);
	
	
	
	// prepare maf column
	mafLabel.setTooltip(new Tooltip(ColViewTooltipTexts.MAF_COL_TP_TEXT));
	mafCol.setEditable(true);
	mafCol.getItems().addAll(header);
	if (checkStringList(header, config.getMafCol())){
	    mafCol.getSelectionModel().select(config.getMafCol());
	}
	TextFields.bindAutoCompletion(mafCol.getEditor(), header);
	
        
        
        
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {


	// init config file
	config = Config.getInstance();

	
	
	
	
	
	
	
	
	
	
	
    }    
    
}
