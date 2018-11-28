/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

import de.hoppmann.config.Config;
import de.hoppmann.database.geneInfoDB.GeneInfoModel;
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
    
    
    
    
//    
//    /**
//     * is LOF variant ("stop_gained", "frameshift_variant", "start_lost", "stop_lost")
//     *	    is known for LOF (pLi >= 0.9)
//     *		not extreme end (>50bp from end [cDNA-position - transcript length])
//     *	
//     * is splice variant in +/1 1,2 ("splice_region_variant", "splice_donor_variant", "splice_acceptor_variant")
//     *	    is in cDNA +1/+2/-1/-2
//     * 
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */
//    
//    public boolean checkPVS1(TableData curLine, HashMap<String, Integer> catIndices) {
//	
//	boolean pvs1 = false; 
//	
//	
////	Set<String> impactCatagories = new TreeSet<>();
////	impactCatagories.add("stop_gained");
////	impactCatagories.add("frameshift_variant");
////	impactCatagories.add("start_lost");
////	impactCatagories.add("stop_lost");
//
//	
//	
////	String impactEntry = curLine.getEntry(catIndices.get(config.getImpactCol()));
////	Set<String> impactSet = new TreeSet<>(Arrays.asList(impactEntry.split(",")));
////	
////	// check for null vaiant
////	if (!Collections.disjoint(impactSet, impactCatagories)){
////	    
////	    
////	    // check if LOF is common mechanism
////	    int pliScore = Integer.parseInt(curLine.getEntry(catIndices.get(config.getPliScoreCol())));
////	    if (pliScore >= 0.9 ){
////		
////	    }
////
////
////	}
//	
//	
//	
//	
//	return pvs1;
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    /**
//     * ClinVar -> Pathogenic
//     * HGMD -> DM
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */
//    private boolean checkPS1(TableData curLine, HashMap<String, Integer> catIndices) {
//	boolean ps1 = false;
//	
//	if (catIndices.get(config.getHgmdCol()) >= 0){
//	    String hgmdEntry = curLine.getEntry(catIndices.get(config.getHgmdCol()));
//	    Set<String> hgmdSet = new TreeSet<>(Arrays.asList(hgmdEntry.split(",")));
//	
//	    if (hgmdSet.contains("DM")){
//		ps1 = true;
//	    }
//	}
//	
//	
//	if (catIndices.get(config.getClinvarCol()) >= 0) {
//	    String clinVarEntry = curLine.getEntry(catIndices.get(config.getClinvarCol()));
//	    Set<String> clinVarSet = new TreeSet<>(Arrays.asList(clinVarEntry.split(",")));
//	    
//	    if (clinVarSet.contains("pathogenic")){
//		ps1 = true;
//	    }
//
//	}
//	
//	
//	return ps1;
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//      /**
//     * MAF Cases >> MAF Controls (inhouse DB needed)
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */  
//    private boolean checkPS4(TableData curLine, HashMap<String, Integer> catIndices) {
//	
//	boolean ps4 = false;
//	
//	
//	
//	
//	
//	return ps4;
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    /**
//     * MAF = 0 (nfe, eas, sas, afr)
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */
//    
//    private boolean checkPM2(TableData curLine, HashMap<String, Integer> catIndices){
//	
//	boolean pm2 = true;
//	
//	if (catIndices.get(config.getMafAllCol()) > 0) {
//	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafAllCol())));
//	    if (maf <= 0){
//		pm2 = false;
//	    }
//	}
//	
//	
//	
//	if (catIndices.get(config.getMafNfeCol()) > 0 ){
//	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafNfeCol())));
//	    	    if (maf <= 0){
//		pm2 = false;
//	    }
//
//	}
//	
//	
//	if (catIndices.get(config.getMafAfrCol()) > 0) {
//	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafAfrCol())));
//	    if (maf <= 0){
//		pm2 = false;
//	    }
//	}
//	
//	
//	if (catIndices.get(config.getMafSasCol()) > 0) {
//	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafSasCol())));
//	    if (maf <= 0){
//		pm2 = false;
//	    }
//	}
//	
//	
//	if (catIndices.get(config.getMafEasCol()) > 0) {
//	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafEasCol())));
//	    if (maf <= 0){
//		pm2 = false;
//	    }
//	}
//
//
//	return pm2;
//    }
//    
//    
//    
//    
//    
//
//    
//    /**
//     * inframe variants (alamut_codingEffect > inframe)
//     *	    no repeat rich region (repeatmaster [rmsk] entry but not trf/null)
//     * 
//     * stop loss variants (alamut_codingEffect > stop loss)
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */
//
//    private boolean checkPM4(TableData curLine, HashMap<String, Integer> catIndices){
//	boolean pm4 = false;
//	
//	if (catIndices.get(config.getImpactCol()) >= 0
//		&& catIndices.get(config.getRmskCol()) >= 0){
//
//	    String impactEntry = curLine.getEntry(catIndices.get(config.getImpactCol()));
//	    Set<String> impactSet = new TreeSet<>(Arrays.asList(impactEntry.split(",")));
//	    
//	    Set<String>  inframes = new TreeSet<>();
//	    inframes.add("inframe_deletion");
//	    inframes.add("inframe_insertion");
//	    
//	    
//	    if (!Collections.disjoint(impactSet, inframes)){
//		String rmskEntry = curLine.getEntry(catIndices.get(config.getRmskCol()));
//		Set<String> rmskSet = new TreeSet<>(Arrays.asList(rmskEntry.split(";")));
//		rmskSet.remove("trf");
//		rmskSet.remove("None");
//		if (rmskSet.size() > 0){
//		    pm4 = true;
//		}
//	    }
//	    
//
//
//
//	    
//
//	}
//	
//	
//	
//	
//	
//	
//	return pm4;
//    }
//    
//    
//    
//    
//    
//    
//    
//    
//    
//    /**
//     * Missense var 
//     *	    low rate of missense var and missense common mechanism (GDI = HIGH)   
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */
//    private boolean checkPP2(TableData curLine, HashMap<String, Integer> catIndices) {
//	boolean pp2 = false;
//	
//	if (catIndices.get(config.getGeneCol()) >= 0){
//	    String geneName = curLine.getEntry(catIndices.get(config.getGeneCol()));
//	    
//	    if (!lastGene.equals(geneName)){
//		GeneInfoRepository geneInfoRepo = new GeneInfoRepository();
//		geneData = new GeneInfoModel(geneName);
//		geneInfoRepo.queryForGene(geneData);
//		lastGene = geneName;
//	    }
//
//	    
//	    if (geneData.getGdiAll() != null){
//		if (geneData.getGdiAll().equals("HIGH")){
//		    pp2 = true;
//		}
//	    }
//	    
//	}
//	
//	
//	return pp2;
//    }
//    
//    
//    
//    
//    
//    
//    
//    /**
//     * Multiple hints (>= 2 of)
//     *	    is_conserved
//     *	    total prediction > 3 AND percent damagin >= 0.5
//     *	    allSSPred > 2 AND percent splice reduction 45 >= 0.5
//     *	    GDI != High
//     *	    pLI > 0.9
//     * @param curLine
//     * @param catIndices
//     * @return 
//     */
//    
//    private boolean checkPP3(TableData curLine, HashMap<String, Integer> catIndices) {
//	
//	boolean pp3 = false;
//	int count = 0;
//
//	
//	
//	
//	int conservationIndex = catIndices.get(config.getConservationCol());
//	if (conservationIndex >= 0){
//	    if (Integer.valueOf(curLine.getEntry(conservationIndex)) == 1){
//		count++;
//	    }
//	}
//
//	
//	
//	
//	int totPredIndex = catIndices.get(config.getTotPredCol());
//	int damagingPredIndex = catIndices.get(config.getPredScoreCol());
//	if (totPredIndex >= 0 && damagingPredIndex >= 0) {
//	    if (!curLine.getEntry(totPredIndex).equals("NA") && !curLine.getEntry(damagingPredIndex).equals("NA")) {
//		int totPred = Integer.valueOf(curLine.getEntry(totPredIndex));
//		double damPred = Double.valueOf(curLine.getEntry(damagingPredIndex));
//
//		if (totPred >= 3 & damPred >= 0.5) {
//		    count++;
//		}
//	    }
//	}
//	
//	
//	
//	int allSsPredIndex = catIndices.get(config.getTotSsPredCol());
//	int spliceRed45Index = catIndices.get(config.getSplice45Col());
//	if (allSsPredIndex >= 0 && spliceRed45Index >= 0){
//	    int allSsPred = Integer.valueOf(curLine.getEntry(allSsPredIndex));
//	    double spliceRed45 = Double.valueOf(curLine.getEntry(spliceRed45Index));
//	    if (allSsPred > 1 && spliceRed45 >= 0.5){
//		count++;
//	    }
//	}
//
//	
//	
//	
//	int gdiIndex = catIndices.get()
//	
//	
//	
//	
//	
//	return pp3;
//    }
//    
//    
//    
//    
    
    
    
    
//    
//    private String extractColIndeces(List<String> header, HashMap<String, Integer> catIndices) {
//	
//	// Add needed header
//	catIndices.put(config.getGeneCol(), -1);
//	catIndices.put(config.getImpactCol(), -1);
//	catIndices.put(config.getZygocityCol(), -1);
//	catIndices.put(config.getcNomenCol(), -1);
//	catIndices.put(config.getpNomenCol(), -1);
//	catIndices.put(config.getPredScoreCol(), -1);
//	catIndices.put(config.getTotPredCol(), -1);
//	catIndices.put(config.getClinvarCol(), -1);
//	catIndices.put(config.getHgmdCol(), -1);
//	catIndices.put(config.getSplice15Col(), -1);
//	catIndices.put(config.getSplice45Col(), -1);
//	catIndices.put(config.getMafAllCol(), -1);
//	catIndices.put(config.getMafNfeCol(), -1);
//	catIndices.put(config.getMafAfrCol(), -1);
//	catIndices.put(config.getMafSasCol(), -1);
//	catIndices.put(config.getMafEasCol(), -1);
//	catIndices.put(config.getRmskCol(), -1);
//	catIndices.put(config.getConservationCol(), -1);
//	catIndices.put(config.getTotSsPredCol(), -1);
//	
//	
//	
//	for (String curCat : catIndices.keySet()) {
//	    catIndices.put(curCat, header.indexOf(curCat));
//        }
//	
//	
//	// check if all header are available else make not
//	if (! header.containsAll(catIndices.keySet())){
//	    return "Not all catagories set. Check your column choice.";
//	}
//
//	return "";
//    }
//    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
//    //// detect catagory
//    public String catagorize(InputRepository inputRepository) {
//       
//	List<String> header = inputRepository.getHeader();
//	List<TableData> rowData = inputRepository.getRowData();
//	HashMap<String, Integer> catIndices = new LinkedHashMap<>();
//
//	
//	extractColIndeces(header, catIndices);
//
//	
//	boolean pvs1 = false;
//	
//	boolean ps1 = false;
//	boolean ps4 = false;
//	
//	boolean pm2 = false;
//	boolean pm4 = false;
//
//	boolean pp2 = false;
//	boolean pp3 = false;
//	
//	for (TableData curLine : rowData){
//
//	    pvs1 = checkPVS1(curLine, catIndices);
//
//	    ps1 = checkPS1(curLine, catIndices);
//	    ps4 = checkPS4(curLine, catIndices);
//	    
//	    pm2 = checkPM2(curLine, catIndices);
//	    pm4 = checkPM4(curLine, catIndices);
//	    
//	    pp2 = checkPP2(curLine, catIndices);
//	    pp3 = checkPP3(curLine, catIndices);
//	
//	
//	    curLine.setCatagory(Catagory.getPathoCode());
//
//	}
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	return "";
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
//	
////	List<String> header = inputRepository.getHeader();
////        List<TableData> rowData = inputRepository.getRowData();
////	//// definde variables
////	
////	/**
////	 * check if all header entries needed for automated catagorization are availabe
////	 * get columns from config
////	 * check for their existance
////	*/
////	HashMap<String, Integer> catagories = new LinkedHashMap<>();
////	catagories.put(config.getClinvarCol(), null);
////	catagories.put(config.getHgmdCol(), null);
////	catagories.put(config.getPredScoreCol(), null);
////	catagories.put(config.getSplice15Col(), null);
////	catagories.put(config.getSplice45Col(), null);
////	catagories.put(config.getTotPredCol(), null);
////	
////	// if not all header found return missing header as warning
////	if (! header.containsAll(catagories.keySet())){
////	    
////	    List<String> missing = new LinkedList<>(catagories.keySet());
////	    String warningText = null;
////	    if (missing.size() == 6){
////		missing.removeAll(header);
////		warningText = "Catagories not chosen automatically. " + missing + " not available";
////	    } else {
////		    warningText = "Catagories not chosen automatically. Missing catagories.";
////	    }
////	    // set to unclear and skip rest
////	    for (TableData row : rowData) {
////		row.setCatagory(Catagory.getUnclearCode());
////	    }
////	    return warningText;
////	    
////	    
////	    //// get indeces of the header entries for later value extraction
////	} else {
////	    
////	    for (String curCat : catagories.keySet()) {
////		catagories.put(curCat, header.indexOf(curCat));
////	    }
////	    
////
////	   // for each entry check if criteria for certain catagories are fullfiled
////	    for (TableData row : rowData) {
////		
////		// check if variant is pathogenic
////		if (isPatho(row, catagories)) {
////		    row.setCatagory(Catagory.getPathoCode());
////		} else if (isLikelyPatho(row, catagories)) {
////		    row.setCatagory(Catagory.getProbPathoCode());
////		} else if (isUnclear(row, catagories)){
////		    row.setCatagory(Catagory.getUnclearCode());
////		}
////		} // end for loop
////	    } // end else clause
////	
////	return "";
//		
//	
//	
//    }
//   
//    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

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
    
    
    

    
    

}
