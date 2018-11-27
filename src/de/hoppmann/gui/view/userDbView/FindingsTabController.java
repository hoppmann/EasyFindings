/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.gui.view.userDbView;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.openFile.CreateTable;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import org.controlsfx.control.textfield.TextFields;

/**
 * FXML Controller class
 *
 * @author hoppmann
 */
public class FindingsTabController implements Initializable {
    
    private Config config = Config.getInstance();
    private FindingsRepository findings;
    @FXML private VariantTabController variantTabController;
    @FXML private AnchorPane findingsTab;
    @FXML private TableView<TableData> findingsTable;
    @FXML private ComboBox<String> geneNameBox;
    @FXML private ComboBox<String> variantNameBox;
    
    
    
    
    
    
    
    
    @FXML
    private void saveButtonAction(ActionEvent event){
	
	System.out.println("TO BE IMPLEMENTED");
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * if gene is selected in findings 
     * add corresponding list of variants to variant combobox
    */
    @FXML
    private void geneBoxAction(ActionEvent event) {
	
	// get selected geneName
	String geneName = geneNameBox.getValue();

	if (geneName != null){
	    geneName = geneName.replaceAll("[^a-zA-Z0-9]", "");
	    updateVarNameBox(geneName);
	}
	
    }

    
    
    
    
    
    
    
    
    
    /**
     * update var name box with all variants of current gene in findings
     * @param curGene 
     */
    
    
    private void updateVarNameBox(String curGene) {
	
	List<String> varList = new LinkedList<>();
	varList = findings.getDependentValueList(config.getcNomenCol(), config.getGeneCol(), curGene, true, ",");
	Collections.sort(varList);
	
	variantNameBox.getItems().clear();
	variantNameBox.getItems().addAll(varList);
	variantNameBox.getSelectionModel().selectFirst();
    }
    
    
       
    
    
    
    
    
    
    
    
    
    
    
    /**
     * update gene name box with all different genes from the findigs
     */
    
    private void updateGeneNameBox() {
	
	// retrieve 
	List<String> geneList = findings.getValueList(config.getGeneCol());
	
	// make gene list uniqe
	geneList = geneList.stream().distinct().collect(Collectors.toList());
	Collections.sort(geneList);

	// fill gene name box
	geneNameBox.getItems().clear();
	geneNameBox.getItems().addAll(geneList);
	geneNameBox.getSelectionModel().selectFirst();
	
	TextFields.bindAutoCompletion(geneNameBox.getEditor(), geneList);
	
	


    }

    
    
    
    
    public void injectVariantTabController(VariantTabController variantTabController) {
	this.variantTabController = variantTabController;
    }
    

    
    
    
    
    public String getSelectedVariant() {
	if(variantNameBox.getValue() != null ) {
	    return variantNameBox.getValue();
	} else {
	    return "";
	}
    }
    
    
    
    
    
    public String getSelectedGene() {
	if (geneNameBox.getValue() != null){
	    return geneNameBox.getValue();
	} else {
	    return "";
	}
    }
    
    
    
    
    
    
    
    
    
    /**
     * retrieve findings object and create table if not null
     * initialize combo fields and fill with gene/variant name list
     * 
     * @param findings 
     */
    
    public void init(FindingsRepository findings) {
	this.findings = findings;
	
	
	
	if (findings != null && findings.getStoredData().size() > 0){
	    
	    
	    // create findings table
	    CreateTable createTable = new CreateTable(findingsTable);
	    createTable.prepareTable();
            createTable.fillHeader(findings.getHeaderList());
	    createTable.fillTable(findings.getStoredData());
		    
	    updateGeneNameBox();
	    geneBoxAction(new ActionEvent());
	}
	
    }
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
	// TODO
    }    
    
}
