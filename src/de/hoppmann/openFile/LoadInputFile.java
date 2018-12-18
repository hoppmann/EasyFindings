/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

import de.hoppmann.config.Config;
import de.hoppmann.database.geneInfoDB.Hg19TableModel;
import de.hoppmann.gui.messanges.CommonErrors;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;


/**
 *
 * @author hoppmann
 */
public class LoadInputFile {

    


    private Config config = Config.getInstance();
    private ICatagorize catagorize;
    


    
    
    
    
    //// read in file
    public void openFile(File file, InputRepository inputRepository) {

        // prepare variables
        String line;

        try {
            // read in file
            BufferedReader br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null) {
		
		// read in line split and save in row data object
		inputRepository.getRowData().add(new TableData(new LinkedList<>(Arrays.asList(line.split("\t")))));
            }
            br.close();
        } catch (IOException iOException) {
	    new CommonErrors().cantOpen(file.toString());
        }
	
	// save header line seperately
	inputRepository.getHeader().addAll(inputRepository.getRowData().remove(0).getInputLine());
    }

    
    
    
    
    
    public void catagorize(ICatagorize catagorize, InputRepository input){
	
	catagorize.createCatagories(input);
	
    }
    
    
    








    
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
    
    
    

    
    

}
