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

    @FXML private ComboBox<String> geneColBox = new ComboBox<>();
    @FXML private Label geneLabel;
    
    @FXML private ComboBox<String> impactColBox = new ComboBox<>();
    @FXML private Label impactLabel;
    
    @FXML private ComboBox<String> pNomenColBox = new ComboBox<>();
    @FXML private Label pNomenLabel;
    
    @FXML private ComboBox<String> cNomenColBox = new ComboBox<>();
    @FXML private Label cNomenLabel;
    
    @FXML private ComboBox<String> pubMedIdColBox = new ComboBox<>();
    @FXML private Label pubMedIdlabel;
    
    @FXML private ComboBox<String> predScoreColBox = new ComboBox<>();
    @FXML private Label predScoreLabel;
    
    @FXML private ComboBox<String> totPredColBox = new ComboBox<>();
    @FXML private Label totPredlabel;
    
    @FXML private ComboBox<String> hgmdColBox = new ComboBox<>();
    @FXML private Label hgmdLabel;
    
    @FXML private ComboBox<String> clinVarColBox = new ComboBox<>();
    @FXML private Label clinVarLabel;
    
    @FXML private ComboBox<String> splice45ColBox = new ComboBox<>();
    @FXML private Label splice45Label;
    
    @FXML private ComboBox<String> splice15ColBox = new ComboBox<>();
    @FXML private Label splice15Label;
    
    @FXML private ComboBox<String> rsIdColBox = new ComboBox<>();
    @FXML private Label rsIdLabel;
    
    @FXML private ComboBox<String> zygocityColBox = new ComboBox<>();
    @FXML private Label zygocityLabel;
    
    @FXML private ComboBox<String> mafAllBox = new ComboBox<>();
    @FXML private Label mafAllLabel;
    
    @FXML private ComboBox<String> mafNfeBox = new ComboBox<>();
    @FXML private Label mafNfeLabel;
    
    @FXML private ComboBox<String> mafAfrBox = new ComboBox<>();
    @FXML private Label mafAfrLabel;
    
    @FXML private ComboBox<String> mafSasBox = new ComboBox<>();
    @FXML private Label mafSasLabel;
    
    @FXML private ComboBox<String> mafEasBox = new ComboBox<>();
    @FXML private Label mafEasLabel;
    
    @FXML private ComboBox<String> rmskBox = new ComboBox<>();
    @FXML private Label rmskLabel;
    
    @FXML private ComboBox<String> conservationBox = new ComboBox<>();
    @FXML private Label conservationLabel;
    
    @FXML private ComboBox<String> totSsPredBox = new ComboBox<>();
    @FXML private Label totSsPredLable;
    
    
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
	
	config.setGeneCol(geneColBox.getValue());
	config.setImpactCol(impactColBox.getValue());
	config.setcNomenCol(cNomenColBox.getValue());
	config.setpNomenCol(pNomenColBox.getValue());
	config.setClinvarCol(clinVarColBox.getValue());
	config.setHgmdCol(hgmdColBox.getValue());
	config.setPredScoreCol(predScoreColBox.getValue());
	config.setSplice15Col(splice15ColBox.getValue());
	config.setSplice45Col(splice45ColBox.getValue());
	config.setTotPredCol(totPredColBox.getValue());
	config.setPubMedIdCol(pubMedIdColBox.getValue());
	config.setRsIdCol(rsIdColBox.getValue());
	config.setImpactCol(impactColBox.getValue());
	config.setMafAllCol(mafAllBox.getValue());
	config.setZygocityCol(zygocityColBox.getValue());
	config.setMafNfeCol(mafNfeBox.getValue());
	config.setMafAfrCol(mafAfrBox.getValue());
	config.setMafSasCol(mafSasBox.getValue());
	config.setMafEasCol(mafEasBox.getValue());
	config.setRmskCol(rmskBox.getValue());
	config.setConservationCol(conservationBox.getValue());
	config.setTotSsPredCol(totSsPredBox.getValue());
	
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
    







    
    public void init (FindingsRepository findings){
        this.findings = findings;
	this.header = FXCollections.observableArrayList(findings.getHeaderList());
        
        
        
        
        
        /*
	  prepare gene column
	  check if there is allready a column chosen previously if so use that one 
	  else use the one from the config file
	*/
	geneLabel.setTooltip(new Tooltip(ColViewTooltipTexts.GENE_COL_TP_TEXT));
	geneColBox.setEditable(true);
	geneColBox.getItems().addAll(header);
	if (findings.getGeneCol() != null && checkStringList(header, findings.getGeneCol()) == true){
	    geneColBox.getSelectionModel().select(findings.getGeneCol());
	} else if (checkStringList(header, config.getGeneCol()) == true) {
	    geneColBox.getSelectionModel().select(config.getGeneCol());
	}
        TextFields.bindAutoCompletion(geneColBox.getEditor(), header);
	
	
	// prepare impact column
	impactLabel.setTooltip(new Tooltip(ColViewTooltipTexts.IMPACT_COL_TP_TEXT));
	impactColBox.setEditable(true);
	impactColBox.getItems().addAll(header);
	if (findings.getImpactCol() != null && checkStringList(header, findings.getImpactCol()) == true){
	    geneColBox.getSelectionModel().select(findings.getImpactCol());
	} else if (checkStringList(header, config.getImpactCol()) == true){
	    impactColBox.getSelectionModel().select(config.getImpactCol());
	}
	TextFields.bindAutoCompletion(impactColBox.getEditor(), header);

	
	
	// prepare cDNA nomencalture column
	cNomenLabel.setTooltip(new Tooltip(ColViewTooltipTexts.CNOMEN_COL_TP_TEXT));
	cNomenColBox.setEditable(true);
	cNomenColBox.getItems().addAll(header);
	if (findings.getcNomenCol()!= null && checkStringList(header, findings.getcNomenCol()) == true){
	    geneColBox.getSelectionModel().select(findings.getcNomenCol());
	} else if (checkStringList(header, config.getcNomenCol()) == true){
	    cNomenColBox.getSelectionModel().select(config.getcNomenCol());
	}
        TextFields.bindAutoCompletion(cNomenColBox.getEditor(), header);

	
	
	
	// prepare protein nomenclature column
	pNomenLabel.setTooltip(new Tooltip(ColViewTooltipTexts.PNOMEN_COL_TP_TEXT));
	pNomenColBox.setEditable(true);
	pNomenColBox.getItems().addAll(header);
	if (findings.getpNomenCol()!= null && checkStringList(header, findings.getpNomenCol()) == true){
	    geneColBox.getSelectionModel().select(findings.getpNomenCol());
	} else if(checkStringList(header, config.getpNomenCol()) == true){
	    pNomenColBox.getSelectionModel().select(config.getpNomenCol());
	}
        TextFields.bindAutoCompletion(pNomenColBox.getEditor(), header);
	
		
	
	// prepare prediction Score column
	predScoreLabel.setTooltip(new Tooltip(ColViewTooltipTexts.PREDSCORE_COL_TP_TEXT));
	predScoreColBox.setEditable(true);
	predScoreColBox.getItems().addAll(header);
	if (checkStringList(header, config.getPredScoreCol())) {
	    predScoreColBox.getSelectionModel().select(config.getPredScoreCol());
	}
	TextFields.bindAutoCompletion(predScoreColBox.getEditor(), header);
	
	
	
	// prepare total number of predictions column
	totPredlabel.setTooltip(new Tooltip(ColViewTooltipTexts.TOTPRED_COL_TP_TEXT));
	totPredColBox.setEditable(true);
	totPredColBox.getItems().addAll(header);
	if (checkStringList(header, config.getTotPredCol())) {
	    totPredColBox.getSelectionModel().select(config.getTotPredCol());
	}
        TextFields.bindAutoCompletion(totPredColBox.getEditor(), header);
		
	
	
	// prepare ClinVar column
	clinVarLabel.setTooltip(new Tooltip(ColViewTooltipTexts.CLINVAR_COL_TP_TEXT));
	clinVarColBox.setEditable(true);
	clinVarColBox.getItems().addAll(header);
	if (checkStringList(header, config.getClinvarCol())) {
	    clinVarColBox.getSelectionModel().select(config.getClinvarCol());
	}
	TextFields.bindAutoCompletion(clinVarColBox.getEditor(), header);
	
	
	
	// prepare HGMD column
	hgmdLabel.setTooltip(new Tooltip(ColViewTooltipTexts.HGMD_COL_TP_TEXT));
	hgmdColBox.setEditable(true);
	hgmdColBox.getItems().addAll(header);
	if (checkStringList(header, config.getHgmdCol())) {
	    hgmdColBox.getSelectionModel().select(config.getHgmdCol());
	}
	TextFields.bindAutoCompletion(hgmdColBox.getEditor(), header);
	
	
	
	// prepare splice 15 reduction prediction column
	splice15Label.setTooltip(new Tooltip(ColViewTooltipTexts.SPLICE15_COL_TP_TEXT));
	splice15ColBox.setEditable(true);
	splice15ColBox.getItems().addAll(header);
	if (checkStringList(header, config.getSplice15Col())) {
	    splice15ColBox.getSelectionModel().select(config.getSplice15Col());
	}
	TextFields.bindAutoCompletion(splice15ColBox.getEditor(), header);
	
	
	
	// prepare splice 45 reduction prediction column
	splice45Label.setTooltip(new Tooltip(ColViewTooltipTexts.SPLICE45_COL_TP_TEXT));
	splice45ColBox.setEditable(true);
	splice45ColBox.getItems().addAll(header);
	if (checkStringList(header, config.getSplice45Col())) {
	    splice45ColBox.getSelectionModel().select(config.getSplice45Col());
	}
	TextFields.bindAutoCompletion(splice45ColBox.getEditor(), header);
	
	
	
	// total spliceside predicitons
	totSsPredLable.setTooltip(new Tooltip(ColViewTooltipTexts.TOT_SS_PRED_TP_COL));
	totSsPredBox.setEditable(true);
	totSsPredBox.getItems().addAll(header);
	if (checkStringList(header, config.getTotSsPredCol())){
	    totSsPredBox.getSelectionModel().select(config.getTotSsPredCol());
	}
	TextFields.bindAutoCompletion(totSsPredBox.getEditor(), header);
	
	
	
	
	// prepare pubMed ID column
	pubMedIdlabel.setTooltip(new Tooltip(ColViewTooltipTexts.PUBMEDID_COL_TP_TEXT));
	pubMedIdColBox.setEditable(true);
	pubMedIdColBox.getItems().addAll(header);
	if(checkStringList(header, config.getPubMedIdCol())) {
	    pubMedIdColBox.getSelectionModel().select(config.getPubMedIdCol());
	}
	TextFields.bindAutoCompletion(pubMedIdColBox.getEditor(), header);
	
	
	
	// prepare rsId column
	rsIdLabel.setTooltip(new Tooltip(ColViewTooltipTexts.RSID_COL_TP_TEXT));
	rsIdColBox.setEditable(true);
	rsIdColBox.getItems().addAll(header);
	if(checkStringList(header, config.getRsIdCol())) {
	    rsIdColBox.getSelectionModel().select(config.getRsIdCol());
	}
	TextFields.bindAutoCompletion(rsIdColBox.getEditor(), header);
	
	
	
	// prepare zygocity column
	zygocityLabel.setTooltip(new Tooltip(ColViewTooltipTexts.ZYGOCITY_COL_TP_TEXT));
	zygocityColBox.setEditable(true);
	zygocityColBox.getItems().addAll(header);
	if (checkStringList(header, config.getZygocityCol())){
	    zygocityColBox.getSelectionModel().select(config.getZygocityCol());
	}
	TextFields.bindAutoCompletion(zygocityColBox.getEditor(), header);
	
	
	
	
	// prepare different Maf
	
	
	// ALL
	mafAllLabel.setTooltip(new Tooltip(ColViewTooltipTexts.MAF_ALL_COL_TP_TEXT));
	mafAllBox.setEditable(true);
	mafAllBox.getItems().addAll(header);
	if (checkStringList(header, config.getMafAllCol())){
	    mafAllBox.getSelectionModel().select(config.getMafAllCol());
	}
	TextFields.bindAutoCompletion(mafAllBox.getEditor(), header);
	
        
	// NFE
	mafNfeLabel.setTooltip(new Tooltip(ColViewTooltipTexts.MAF_ALL_COL_TP_TEXT));
	mafNfeBox.setEditable(true);
	mafNfeBox.getItems().addAll(header);
	if (checkStringList(header, config.getMafNfeCol())){
	    mafNfeBox.getSelectionModel().select(config.getMafNfeCol());
	}
	TextFields.bindAutoCompletion(mafNfeBox.getEditor(), header);

	
	//AFR
	mafAfrLabel.setTooltip(new Tooltip(ColViewTooltipTexts.MAF_AFR_COL_TP_TEXT));
	mafAfrBox.setEditable(true);
	mafAfrBox.getItems().addAll(header);
	if (checkStringList(header, config.getMafAfrCol())){
	    mafAfrBox.getSelectionModel().select(config.getMafAfrCol());
	}
	TextFields.bindAutoCompletion(mafAfrBox.getEditor(), header);
	
	
	//SAS
	mafSasLabel.setTooltip(new Tooltip(ColViewTooltipTexts.MAF_SAS_COL_TP_TEXT));
	mafSasBox.setEditable(true);
	mafSasBox.getItems().addAll(header);
	if (checkStringList(header, config.getMafSasCol())){
	    mafSasBox.getSelectionModel().select(config.getMafSasCol());
	}
	TextFields.bindAutoCompletion(mafSasBox.getEditor(), header);
	
	
	// EAS
	mafEasLabel.setTooltip(new Tooltip(ColViewTooltipTexts.MAF_EAS_COL_TP_TEXT));
	mafEasBox.setEditable(true);
	mafEasBox.getItems().addAll(header);
	if (checkStringList(header, config.getMafEasCol())) {
	    mafEasBox.getSelectionModel().select(config.getMafEasCol());
	}
	TextFields.bindAutoCompletion(mafEasBox.getEditor(), header);
	
	
	// repeat masker
	rmskLabel.setTooltip(new Tooltip(ColViewTooltipTexts.RMSK_COL_TP_TEXT));
	rmskBox.setEditable(true);
	rmskBox.getItems().addAll(header);
	if (checkStringList(header, config.getRmskCol())){
	    rmskBox.getSelectionModel().select(config.getRmskCol());
    	}
	TextFields.bindAutoCompletion(rmskBox.getEditor(), header);
	
	
	// conservation boolean
	conservationLabel.setTooltip(new Tooltip(ColViewTooltipTexts.CONSERVATION_COL_TP_TEXT));
	conservationBox.setEditable(true);
	conservationBox.getItems().addAll(header);
	if (checkStringList(header, config.getConservationCol())){
	    conservationBox.getSelectionModel().select(config.getConservationCol());
	}
	TextFields.bindAutoCompletion(conservationBox.getEditor(), header);
	
	
	
	
        
        
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
