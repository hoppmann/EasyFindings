/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

import com.google.common.base.Ascii;
import com.lowagie.text.pdf.codec.PngImage;
import de.hoppmann.config.Config;
import de.hoppmann.database.geneInfoDB.GeneInfoModel;
import de.hoppmann.database.geneInfoDB.GeneInfoRepository;
import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hoppmann
 */
public class CatagorizeAcmg implements ICatagorize {

    
    private Config config = Config.getInstance();
    private String lastGene = "";
    private GeneInfoModel geneData;
    
    
    
    
    
    
    
    
    
    
    
 
    /**
     * is LOF variant ("stop_gained", "frameshift_variant", "start_lost", "stop_lost")
     *	    is known for LOF (pLi >= 0.9)
     *		not extreme end (>50bp from end [cDNA-position - transcript length])
     *	
     * is splice variant in +/1 1,2 ("splice_region_variant", "splice_donor_variant", "splice_acceptor_variant")
     *	    is in cDNA +1/+2/-1/-2
     * 
     * @param curLine
     * @param catIndices
     * @return 
     */
    
    public boolean checkPVS1(TableData curLine, HashMap<String, Integer> catIndices) {
	
	boolean pvs1 = false; 
	
	
	
	
	 String geneName = "";
	if (catIndices.get(config.getGeneCol()) >= 0) {
	    geneName = curLine.getEntry(catIndices.get(config.getGeneCol()));
	}

	
	List<String> cNomenList = new LinkedList();
	if (catIndices.get(config.getcNomenCol()) >= 0) {
	    cNomenList = curLine.getSplitEntry(catIndices.get(config.getcNomenCol()), ",");
	}

	
	
	List<Integer> transLengthIntList = new LinkedList<>();
	if (catIndices.get(config.getTranscritpLengthCol()) >= 0) {
	    List<String> transLengthStringList = curLine.getSplitEntry(catIndices.get(config.getTranscritpLengthCol()), ",");

	    for (String curTrans : transLengthStringList) {
		transLengthIntList.add(Integer.valueOf(curTrans));
	    }
	}


	
	// avoid db query when gene info was already queried
	if (!lastGene.equals(geneName)) {
	    GeneInfoRepository geneInfoRepo = new GeneInfoRepository();
	    geneData = new GeneInfoModel(geneName);
	    geneInfoRepo.queryForGene(geneData);
	    lastGene = geneName;
	}
	
	



	///////////////////////////
	///// check for null vaiant
	Set<String> impactCatagories = new TreeSet<>();
	impactCatagories.add("stop_gained");
	impactCatagories.add("frameshift_variant");
	impactCatagories.add("start_lost");
	impactCatagories.add("stop_lost");

	String impactEntry = "";
	if (catIndices.get(config.getImpactCol()) >= 0){
	impactEntry = curLine.getEntry(catIndices.get(config.getImpactCol()));
        }
	Set<String> impactSet = new TreeSet<>(Arrays.asList(impactEntry.split(",")));
	if (!Collections.disjoint(impactSet, impactCatagories)){
	    
	    // check for each entry distance to end
	    int index = 0;
	    boolean closeToEnd = true;
	    int distToEnd = 50;
	    for (String curCNomen : cNomenList){
		List<String> curSplit = Arrays.asList(curCNomen.split(":"));
		int varPos = 99999;
		if (curSplit.size() > 1){
		    curSplit = Arrays.asList(curSplit.get(1).split("\\+"));
		    curSplit = Arrays.asList(curSplit.get(0).split("_"));
		    varPos = Integer.valueOf(curSplit.get(0).replaceAll("\\D", ""));
		}
		
		if (transLengthIntList.size() > 0) {
		    if (index <= transLengthIntList.size() - 1) {
			if (varPos <= transLengthIntList.get(index) - distToEnd) {
			    closeToEnd = false;
			}
		    } else {
			if (varPos <= transLengthIntList.get(0) - distToEnd) {
			    closeToEnd = false;
			}

		    }
		}

		index++;
	    }


	    if (geneData.getExacPli() > 0.9) {
		if (!closeToEnd){
		    pvs1 = true;
		}
	    }
	}
	    
	    
	//////// check for splice site mutations
	Set spliceImpactCatagories = new TreeSet();
	spliceImpactCatagories.add("splice_region_variant");
	spliceImpactCatagories.add("splice_donor_variant");
	spliceImpactCatagories.add("splice_acceptor_variant");

	
	if (!Collections.disjoint(impactSet, spliceImpactCatagories)) {
	
	    for (String curCNomen : cNomenList) {
		List<String> curSplit = Arrays.asList(curCNomen.split(":"));
		if (curSplit.size() > 1){
		    curSplit = Arrays.asList(curSplit.get(1).split("\\+|\\-"));
		}
		if (curSplit.size() > 1) {
		    String positionString = curSplit.get(curSplit.size() - 1).replaceAll("\\D", "");
		    int ssPos = Integer.valueOf(positionString);
		    if (ssPos <= 2) {
			pvs1 = true;
		    }
		}
	    }
	}
	
	
	
	
	return pvs1;
    }
    
    
    
    
    
    
    
    
    /**
     * ClinVar -> Pathogenic
     * HGMD -> DM
     * @param curLine
     * @param catIndices
     * @return 
     */
    private boolean checkPS1(TableData curLine, HashMap<String, Integer> catIndices) {
	boolean ps1 = false;
	
	if (catIndices.get(config.getHgmdCol()) >= 0){
	    String hgmdEntry = curLine.getEntry(catIndices.get(config.getHgmdCol()));
	    Set<String> hgmdSet = new TreeSet<>(Arrays.asList(hgmdEntry.split(",")));
	
	    if (hgmdSet.contains("DM")){
		ps1 = true;
	    }
	}
	
	
	if (catIndices.get(config.getClinvarCol()) >= 0) {
	    String clinVarEntry = curLine.getEntry(catIndices.get(config.getClinvarCol()));
	    Set<String> clinVarSet = new TreeSet<>(Arrays.asList(clinVarEntry.split(",")));
	    
	    if (clinVarSet.contains("pathogenic")){
		ps1 = true;
	    }

	}
	
	return ps1;
    }
    
    
    
    
    
    
    
    
    
    
      /**
     * MAF Cases >> MAF Controls (inhouse DB needed)
     * @param curLine
     * @param catIndices
     * @return 
     */  
    private boolean checkPS4(TableData curLine, HashMap<String, Integer> catIndices) {
	
	boolean ps4 = false;
	
	
	
	
	
	return ps4;
    }
    
    
    
    
    
    
    
    
    
    
    
    /**
     * MAF = 0 (nfe, eas, sas, afr)
     * @param curLine
     * @param catIndices
     * @return 
     */
    
    private boolean checkPM2(TableData curLine, HashMap<String, Integer> catIndices){
	
	boolean pm2 = true;
	
	if (catIndices.get(config.getMafAllCol()) > 0) {
	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafAllCol())));
	    if (maf <= 0){
		pm2 = false;
	    }
	}
	
	
	
	if (catIndices.get(config.getMafNfeCol()) > 0 ){
	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafNfeCol())));
	    	    if (maf <= 0){
		pm2 = false;
	    }

	}
	
	
	if (catIndices.get(config.getMafAfrCol()) > 0) {
	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafAfrCol())));
	    if (maf <= 0){
		pm2 = false;
	    }
	}
	
	
	if (catIndices.get(config.getMafSasCol()) > 0) {
	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafSasCol())));
	    if (maf <= 0){
		pm2 = false;
	    }
	}
	
	
	if (catIndices.get(config.getMafEasCol()) > 0) {
	    double maf = Double.valueOf(curLine.getEntry(catIndices.get(config.getMafEasCol())));
	    if (maf <= 0){
		pm2 = false;
	    }
	}


	return pm2;
    }
    
    
    
    
    

    
    /**
     * inframe variants (alamut_codingEffect > inframe)
     *	    no repeat rich region (repeatmaster [rmsk] entry but not trf/null)
     * 
     * stop loss variants (alamut_codingEffect > stop loss)
     * @param curLine
     * @param catIndices
     * @return 
     */

    private boolean checkPM4(TableData curLine, HashMap<String, Integer> catIndices){
	boolean pm4 = false;
	
	if (catIndices.get(config.getImpactCol()) >= 0
		&& catIndices.get(config.getRmskCol()) >= 0){

	    String impactEntry = curLine.getEntry(catIndices.get(config.getImpactCol()));
	    Set<String> impactSet = new TreeSet<>(Arrays.asList(impactEntry.split(",")));
	    
	    Set<String>  inframes = new TreeSet<>();
	    inframes.add("inframe_deletion");
	    inframes.add("inframe_insertion");
	    
	    
	    if (!Collections.disjoint(impactSet, inframes)){
		String rmskEntry = curLine.getEntry(catIndices.get(config.getRmskCol()));
		Set<String> rmskSet = new TreeSet<>(Arrays.asList(rmskEntry.split(";")));
		rmskSet.remove("trf");
		rmskSet.remove("None");
		if (rmskSet.size() > 0){
		    pm4 = true;
		}
	    }
	    



	    

	}
	
	
	
	
	
	
	return pm4;
    }
    
    
    
    
    
    
    
    
    
    /**
     * Missense var 
     *	    low rate of missense var and missense common mechanism (GDI = HIGH)   
     * @param curLine
     * @param catIndices
     * @return 
     */
    private boolean checkPP2(TableData curLine, HashMap<String, Integer> catIndices) {
	boolean pp2 = false;
	
	if (catIndices.get(config.getGeneCol()) >= 0){
	    String geneName = curLine.getEntry(catIndices.get(config.getGeneCol()));
	    
	    if (!lastGene.equals(geneName)){
		GeneInfoRepository geneInfoRepo = new GeneInfoRepository();
		geneData = new GeneInfoModel(geneName);
		geneInfoRepo.queryForGene(geneData);
		lastGene = geneName;
	    }

	    
	    if (geneData.getGdiAll() != null){
		if (geneData.getGdiAll().equals("HIGH")){
		    pp2 = true;
		}
	    }
	    
	}
	
	
	return pp2;
    }
    
    
    
    
    
    
    
    /**
     * Multiple hints (>= 2 of)
     *	    is_conserved
     *	    total prediction > 3 AND percent damagin >= 0.5
     *	    allSSPred > 2 AND percent splice reduction 45 >= 0.5
     *	    GDI != High
     *	    pLI > 0.9
     * @param curLine
     * @param catIndices
     * @return 
     */
    
    private boolean checkPP3(TableData curLine, HashMap<String, Integer> catIndices) {
	
	boolean pp3 = false;
	int count = 0;

	
	
	
	int conservationIndex = catIndices.get(config.getConservationCol());
	if (conservationIndex >= 0){
	    if (Integer.valueOf(curLine.getEntry(conservationIndex)) == 1){
		count++;
	    }
	}

	
	
	
	int totPredIndex = catIndices.get(config.getTotPredCol());
	int damagingPredIndex = catIndices.get(config.getPredScoreCol());
	if (totPredIndex >= 0 && damagingPredIndex >= 0) {
	    if (!curLine.getEntry(totPredIndex).equals("NA") && !curLine.getEntry(damagingPredIndex).equals("NA")) {
		int totPred = Integer.valueOf(curLine.getEntry(totPredIndex));
		double damPred = Double.valueOf(curLine.getEntry(damagingPredIndex));

		if (totPred >= 3 & damPred >= 0.5) {
		    count++;
		}
	    }
	}
	
	
	
	
	
	
	int allSsPredIndex = catIndices.get(config.getTotSsPredCol());
	int spliceRed45Index = catIndices.get(config.getSplice45Col());
	if (allSsPredIndex >= 0 && spliceRed45Index >= 0){
	    int allSsPred = Integer.valueOf(curLine.getEntry(allSsPredIndex));
	    double spliceRed45 = 0;
	    if (!curLine.getEntry(spliceRed45Index).equals("NA")){
		spliceRed45 = Double.valueOf(curLine.getEntry(spliceRed45Index));
	    }
	    
	    if (allSsPred > 1 && spliceRed45 >= 0.5){
		count++;
	    }
	}

	
	
	
	
	if (catIndices.get(config.getGeneCol()) >= 0) {
	    String geneName = curLine.getEntry(catIndices.get(config.getGeneCol()));

	    if (!lastGene.equals(geneName)) {
		GeneInfoRepository geneInfoRepo = new GeneInfoRepository();
		geneData = new GeneInfoModel(geneName);
		geneInfoRepo.queryForGene(geneData);
		lastGene = geneName;
	    }

	    
	    //// check for GDI != HIGH
	    if (geneData.getGdiAll() != null) {
		if (!geneData.getGdiAll().equals("HIGH")) {
		    count++;
		}
	    }
	    
	    
	    
	    // check for pLI score > 0.9
	    if (geneData.getExacPli() > 0.9){
		count++;
	    }
	    
	    
	    
	    
	}
	
	
	
	if (count > 1){
	    pp3 = true;
	}
	
	
	
	
	
	return pp3;
    }

    
    
    
    
    
    
       
    private String extractColIndeces(List<String> header, HashMap<String, Integer> catIndices) {
	
	// Add needed header
	catIndices.put(config.getGeneCol(), -1);
	catIndices.put(config.getImpactCol(), -1);
	catIndices.put(config.getZygocityCol(), -1);
	catIndices.put(config.getcNomenCol(), -1);
	catIndices.put(config.getpNomenCol(), -1);
	catIndices.put(config.getPredScoreCol(), -1);
	catIndices.put(config.getTotPredCol(), -1);
	catIndices.put(config.getClinvarCol(), -1);
	catIndices.put(config.getHgmdCol(), -1);
	catIndices.put(config.getSplice15Col(), -1);
	catIndices.put(config.getSplice45Col(), -1);
	catIndices.put(config.getMafAllCol(), -1);
	catIndices.put(config.getMafNfeCol(), -1);
	catIndices.put(config.getMafAfrCol(), -1);
	catIndices.put(config.getMafSasCol(), -1);
	catIndices.put(config.getMafEasCol(), -1);
	catIndices.put(config.getRmskCol(), -1);
	catIndices.put(config.getConservationCol(), -1);
	catIndices.put(config.getTotSsPredCol(), -1);
	catIndices.put(config.getTranscritpLengthCol(), -1);
	
	
	
	for (String curCat : catIndices.keySet()) {
	    catIndices.put(curCat, header.indexOf(curCat));
        }
	
	
	// check if all header are available else make not
	if (! header.containsAll(catIndices.keySet())){
	    return "Not all catagories set. Check your column choice.";
	}

	return "";
    }
    
    
    
    
    
    private void checkCatagories(TableData curLine, HashMap<String, Integer> catIndices) {
	curLine.setPvs1(checkPVS1(curLine, catIndices));

	curLine.setPs1(checkPS1(curLine, catIndices));
	curLine.setPs4(checkPS4(curLine, catIndices));

	curLine.setPm2(checkPM2(curLine, catIndices));
	curLine.setPm4(checkPM4(curLine, catIndices));

	curLine.setPp2(checkPP2(curLine, catIndices));
	curLine.setPp3(checkPP3(curLine, catIndices));


    }
    
    
    
    
    private void classify(TableData curLine, HashMap<String, Integer> catIndices) {
	
	curLine.setCatagory(Catagory.getUnclearCode());
	
	
	
	// check for pathogenic variants
		
	if (curLine.isPvs1()){
	    if (curLine.isPs1() || curLine.isPs4()){
		curLine.setCatagory(Catagory.getPathoCode());
	    }
	    
	    if (curLine.isPp2() && curLine.isPp3()){
		curLine.setCatagory(Catagory.getPathoCode());
	    }
	    
	    if (curLine.isPm2() || curLine.isPm4()){
		if (curLine.isPp2() || curLine.isPp3()){
		    curLine.setCatagory(Catagory.getPathoCode());
		}
	    }
	    
	    if (curLine.isPp2() && curLine.isPp3()){
		curLine.setCatagory(Catagory.getPathoCode());
	    }
	    
	}


	if  (curLine.isPs1() && curLine.isPs4()){
	    curLine.setCatagory(Catagory.getPathoCode());
	}
	
	
	if (curLine.isPs1() || curLine.isPs4()){
	    if (curLine.isPm2() && curLine.isPm4()){
		if (curLine.isPp2() && curLine.isPp3()){
		    curLine.setCatagory(Catagory.getPathoCode());
		}
	    }
	}
	
	
	
	
	
	// check for likely pathogenic variants
	
	
	if (curLine.isPvs1()){
	    if (curLine.isPs1() || curLine.isPs4()){
		curLine.setCatagory(Catagory.getProbPathoCode());
	    }
	}
	
	
	if (curLine.isPs1() || curLine.isPs4()){
	    if (curLine.isPm2() || curLine.isPm4()){
		curLine.setCatagory(Catagory.getProbPathoCode());
	    }
	    
	    if (curLine.isPp2() && curLine.isPp3()){
		curLine.setCatagory(Catagory.getProbPathoCode());
	    }
	}
	
	
	if (curLine.isPm2() && curLine.isPm4()) {
	    if (curLine.isPp2() && curLine.isPp3()){
		curLine.setCatagory(Catagory.getProbPathoCode());
	    }
	}
	
	
	
	
	
	
    }
    
    
    
    
    
    @Override
    public void createCatagories(InputRepository inputRepository) {

	List<String> header = inputRepository.getHeader();
	List<TableData> rowData = inputRepository.getRowData();
	HashMap<String, Integer> catIndices = new LinkedHashMap<>();

	extractColIndeces(header, catIndices);

//	int c = 0;

	for (TableData curLine : rowData) {

	    checkCatagories(curLine, catIndices);

	    classify(curLine, catIndices);

//	    if (curLine.isPs1()) {
//		c++;
//	    }
	}
	
//	System.out.println(c);

    }
    
    
    
    
    
}
