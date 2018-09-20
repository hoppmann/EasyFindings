/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations;

import de.hoppmann.config.Config;
import de.hoppmann.gui.messanges.CommonErrors;
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
import java.util.regex.Pattern;
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
    private TableView<TableData> tableView = new TableView<>();
    private final List<String> header = new ArrayList<>();
    private List<TableData> rowData;
    private Label infoField;
    private Config config = Config.getInstance();
    
    
    
    
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public LoadInputFile(TableView inputTable, Label infoFiled) {
        
        // retrieve variables
        this.tableView = inputTable;
        this.infoField = infoFiled;
	 // reset infoField
	 infoFiled.setText("INFO:");
	
        // choose input file
        File file = chooseFile();
        
        // check if file chosen. If so continue loading and prepareing table
        if (file != null) {
            openFile(file);
	    catagorize();
	    createTable();
        }
        
    }
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    //// choose file to open
    private File chooseFile() {

	// create chooser to choose file
	FileChooser chooser = new FileChooser();
	chooser.setTitle("Open input file");

	// if config path exists take config path else new path
	if (new File(config.getInputPath()).exists()) {
	    chooser.setInitialDirectory(new File(config.getInputPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}

	// check if file is chosen of it was canceld
	File file = chooser.showOpenDialog(new Stage());

	// check if file was chosen then store last used path
	if (file != null && file.exists()) {
	    config.setInputPath(file.getParent());
	}

	return file;
    }
    
    
    
    
    
    
    
    
    
    //// read in file
    private void openFile(File file) {

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
		} else if (isUnclear(row, catagories)){
		    row.setCatagory(Catagory.getUnclearCode());
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
    
    NOT TO BE USED
     * check if variant is unclear
     * ClinVar: uncertain; likely benign
     * MAF just beneath 1% (recessive, X-linked); 0.2% (dominat)
     * OR
     * Splice reduction < 45%
    
    TO BE USED
    all variants that are neither Pathogenic or likely Pathogenic are uncertain
     */
    
    
    private boolean isUnclear(TableData row, HashMap<String, Integer> catagories) {

	
	// create reurn boolean
	boolean isUnclear = true;
	
//	////////////////
//	//////// ClinVar
//	//////// check if ClinVar has uncertain
//	
//	// check if ClinVar has entry likely_pathogenic
//	Integer clinvarIndex = catagories.get(config.getClinvarCol());
//	
//	// split all entries and check if they are pathogenic
//	List<String> allClinvarEntries = Arrays.asList(row.getEntry(clinvarIndex).split(","));
//	if (allClinvarEntries.contains("uncertain")) {
//	    isUnclear = true;
//	}

	
	
	
	//////// check if splice reduction is < 45%
	
	
	
	//////// check if MAF just < cutoff
	
	
	
	return isUnclear;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /** 
     * check if variant is likey pathogenic
     * HGMD: DM?
     * ClinVar: likely_Pathogenic
     * Nondescribed mutations
     * - impact: stop_gained or frameshift_variant or stop_lost
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
	Integer clinvarIndex = catagories.get(config.getClinvarCol());
	
	// split all entries and check if they are pathogenic
	List<String> allClinvarEntries = Arrays.asList(row.getEntry(clinvarIndex).split(","));
	if (allClinvarEntries.contains("likely_pathogenic")) {
	    isLikelyPatho = true;
	}
	
	
	////////////////////////
	//////// nondescribed variants
	
	
	//////// stop gain, stop loss and frame shift
	// check if impact column has one of the following entries
	// stop_gained or frameshift_variant 
	Integer impactColIndex = catagories.get(config.getImpactCol());
	
	String[] impactOfInterest = {"stop_gained", "frameshift_variant", "stop_lost"};
	impactColIndex = 0;
	/// check if index is known els don't check -> else causes crashes.
	if (impactColIndex != null) {
	    for (String curImpact : impactOfInterest){
		if (row.getEntry(impactColIndex).contains(curImpact)){
		    isLikelyPatho = true;
		}

	    }
	}
	
	//////// splice variants
	
	// if variant is splice variant
	// check if splice reduction > 45%
	impactOfInterest = new String[] {"splice_region_variant", "splice_acceptor_variant", "splice_donor_variant"};
	
	Integer splice45DecIndex = catagories.get(config.getSplice45Col());
	
	for (String curImpcat : impactOfInterest) {
	    if (row.getEntry(impactColIndex).contains(curImpcat)){
                // check if entry is parsable to double
                boolean isParseable = Pattern.matches(
                        row.getEntry(splice45DecIndex), 
                        "([0-9]*)\\.([0-9]*)");
		if (isParseable && Double.parseDouble(row.getEntry(splice45DecIndex)) >= 0.5) {
		    isLikelyPatho = true;
		}
	    }
	}
	
	
	
	
	
	
	//////// based on prediction tools
	
	// if variant is predicted by at least 3 tools, and >= 50% state damaging
	// take as likely pathogenic
	
	Integer totPredColIndex = catagories.get(config.getTotPredCol());
	Integer predScoreColIndex = catagories.get(config.getPredScoreCol());
	
	Integer totalPredictors = Integer.parseInt(row.getEntry(totPredColIndex));
	// check if score = NA if so set 0.
	Double predScore = 0.0;

	if (!row.getEntry(predScoreColIndex).equals("NA")){
	    predScore = Double.parseDouble(row.getEntry(predScoreColIndex));
	}
	
	if (totalPredictors > 2 && predScore >= 0.5) {
	    isLikelyPatho = true;
	}
	
	
	
	
	return isLikelyPatho;
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
    public void createTable() {
	
        
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
