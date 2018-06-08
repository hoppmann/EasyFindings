/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations;

import de.hoppmann.config.Config;
import de.hoppmann.gui.messanges.CommonErrors;
import de.hoppmann.gui.messanges.CommonWarnings;
import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.TableData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


/**
 *
 * @author hoppmann
 */
public class LoadInputFile {

    


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private final TableView<TableData> tableView;
    private final List<String> header = new ArrayList<>();
    private File file;
    private List<TableData> rowData;
    private Label infoField;
    private Config config;
    
    
    
    
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public LoadInputFile(TableView inputTable, Label infoFiled) {
        
        // retrieve variables
        this.tableView = inputTable;
        this.infoField = infoFiled;
	 // reset infoField
	 infoFiled.setText("INFO:");
        
	// load config file
	config = new Config();
	
        // choose input file
        chooseFile();
        
        // check if file chosen. If so continue loading and prepareing table
        if (file != null) {
            openFile();
	    catagorize();
	    createTable();
        }
        
    }
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    //// choose file to open
    private void chooseFile() {
        
        // create chooser to choose file
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file");
	
	// if config path exists take config path else new path
	Config config = new Config();
	if (new File(config.getInputPath()).exists()){
	    chooser.setInitialDirectory(new File(config.getInputPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
        file = chooser.showOpenDialog(new Stage());
	
	// store last used path
	if (file.exists()) {
	    config.setInputPath(file.getParent());
	}
        
    }
    
    
    
    
    
    
    
    
    
    //// read in file
    private void openFile() {

        // prepare variables
        String line;
        rowData = new ArrayList<>();
        try {

            // read in file
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
		
		// read in line split and save in row data object
		rowData.add(new TableData(new LinkedList<>(Arrays.asList(line.split("\t")))));
            }
            br.close();
        } catch (IOException iOException) {
	    new CommonErrors().cantOpen(file.toString());
        }
	
	// save header line seperately
	header.addAll(rowData.remove(0).getInputLine());
	

    }

    
    
    
    
    
    
    
    
    
    
    
    //// detect catagory
    private void catagorize() {
	
	//// definde variables
	
	/**
	 * check if all header entries needed for automated catagorization are availabe
	 * get columns from config
	 * check for their existance
	*/
	HashMap<String, Integer> catagories = new LinkedHashMap<>();
	catagories.put(config.getClinvarCol(), null);
	catagories.put(config.getHgmdCol(), null);
	catagories.put(config.getImpactCol(), null);
	catagories.put(config.getPredScoreCol(), null);
	catagories.put(config.getSplice15Col(), null);
	catagories.put(config.getSplice45Col(), null);
	catagories.put(config.getTotPredCol(), null);
	
	// if not all header found return missing header as warning
	if (! header.containsAll(catagories.keySet())){
	    List<String> missing = new LinkedList<>(catagories.keySet());
	    missing.removeAll(header);
	    String warningText = "Catagories not chosen automatically. " + missing + " not available";
	    infoField.setText(warningText);
	    
	    // skip rest of method
	    return;
	    
	    
	    //// get indeces of the header entries for later value extraction
	} else {
	    
	    for (String curCat : catagories.keySet()) {
		catagories.put(curCat, header.indexOf(curCat));
	    }
	    

	   // for each entry check if criteria for certain catagories are fullfiled
	    for (TableData row : rowData) {
		
		// check if variant is pathogenic
		if (isPatho(row, catagories)) {
		    row.setCatagory(Catagory.getPathoCode());
		} else if (isLikelyPatho(row, catagories)) {
		    row.setCatagory(Catagory.getProbPathoCode());
		}
		



		
		
		
		} // end for loop
	    } // end else clause
	
    }
    

    /**
     * check if variant is probably benign
     * 
     * 
    */
    
    
    
    /*
     * check if variant is unclear
     * ClinVar: uncertain; likely pathogenic; likely benign
     * MAF just beneath 1% (recessive, X-linked); 0.2% (dominat)
     * OR
     * Splice reduction < 45%
     */
    
    
    
    
    
    
    
    /** 
     * check if variant is likey pathogenic
     * HGMD: DM?
     * ClinVar: likely_Pathogenic
     * Nondescribed mutations
     * - impact: stop_gained or frameshift_variant
     * - impact:splice_region_variant, splice_acceptor_variant, splice_donor_variant
     *	-> splice reduction prediction >45% 
     * -at leas 3 prediction scores calling damaging
     */
    private boolean isLikelyPatho(TableData row, HashMap<String, Integer> catagories) {
	
	// create return boolean
	boolean isLikelyPatho = false;
	
	/////////////
	//////// HGMD
	
	// check if HGMD has entry DM?
	Integer hgmdIndex = catagories.get(config.getHgmdCol());
	
	// split all HGMD entries and check if DM? is there but not DM
	List<String> allHgmdEntires = Arrays.asList(row.getEntry(hgmdIndex).split(","));
	
	if (allHgmdEntires.contains("DM?")) {
	    isLikelyPatho = true;
	}
	
	
	
	
	////////////////
	//////// ClinVar
	
	// check if ClinVar has entry likely_pathogenic
	
	
	
	
	
	return true;
    }
    
    
    
    
    
    
    
    
    /**
     * check if variant is pathogenic
     * Either corresponding HGMD or ClinVar entry
     * HGMD -> DM
     * clinvar -> pathogenic
     */ 
    private boolean isPatho (TableData row, HashMap<String, Integer> catagories) {
	
	// create return boolean
	boolean isPatho = false;
	
	/////////////
	//////// HGMD
	
	// check if HGMD has DM but not DM? entry
	Integer hgmdIndex = catagories.get(config.getHgmdCol());
	
	// split all entries
	List<String> allHgmdEntries;
	allHgmdEntries = Arrays.asList(row.getEntry(hgmdIndex).split(","));
	if (allHgmdEntries.contains("DM")){
	    isPatho = true;
	}
	
	
	
	
	////////////////
	//////// ClinVar
	
	//// check for ClinVar entries with pathogenic
	Integer clinvarIndex = catagories.get(config.getClinvarCol());
	
	// split all entries and check if they are pathogenic
	List<String> allClinvarEntries = Arrays.asList(row.getEntry(clinvarIndex).split(","));
	if (allClinvarEntries.contains("pathogenic")) {
	    isPatho = true;
	}


	
	
	
	
	
	return isPatho;
    }
    
    
    
    
    //// create table
    private void createTable() {
	CreateTable table = new CreateTable(tableView);
	table.prepareTable(header);
	table.fillTable(rowData);
    }
    
    

    
    

    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public List<TableData> getRowData() {
	return rowData;
    }

    public List<String> getHeader() {
	return header;
    }

    

}
