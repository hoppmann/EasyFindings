/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

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
	
	
//	Set<String> impactCatagories = new TreeSet<>();
//	impactCatagories.add("stop_gained");
//	impactCatagories.add("frameshift_variant");
//	impactCatagories.add("start_lost");
//	impactCatagories.add("stop_lost");

	
	
//	String impactEntry = curLine.getEntry(catIndices.get(config.getImpactCol()));
//	Set<String> impactSet = new TreeSet<>(Arrays.asList(impactEntry.split(",")));
//	
//	// check for null vaiant
//	if (!Collections.disjoint(impactSet, impactCatagories)){
//	    
//	    
//	    // check if LOF is common mechanism
//	    int pliScore = Integer.parseInt(curLine.getEntry(catIndices.get(config.getPliScoreCol())));
//	    if (pliScore >= 0.9 ){
//		
//	    }
//
//
//	}
	
	
	
	
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
	
	
	
	for (String curCat : catIndices.keySet()) {
	    catIndices.put(curCat, header.indexOf(curCat));
        }
	
	
	// check if all header are available else make not
	if (! header.containsAll(catIndices.keySet())){
	    return "Not all catagories set. Check your column choice.";
	}

	return "";
    }
    
    
    
    
    
    private void checkCatagories(TableData curLine, HashMap<String, Integer> catIndices, CatagoryData catData) {
	catData.setPvs1(checkPVS1(curLine, catIndices));

	catData.setPs1(checkPS1(curLine, catIndices));
	catData.setPs4(checkPS4(curLine, catIndices));

	catData.setPm2(checkPM2(curLine, catIndices));
	catData.setPm4(checkPM4(curLine, catIndices));

	catData.setPp2(checkPP2(curLine, catIndices));
	catData.setPp3(checkPP3(curLine, catIndices));

	
    }
    
    
    
    
    private void classify(CatagoryData catData, TableData curLine) {
	
	
	if (catData.pvs1) {
	    if (catData.ps1 || catData.ps4){
		curLine.setCatagory(Catagory.getPathoCode());
	    }
	    
	    
	    
	    
	} else {
	    curLine.setCatagory(Catagory.getUnclearCode());
	} 
	
	
	
    }
    
    
    
    
    
    @Override
    public void createCatagories(InputRepository inputRepository) {

	List<String> header = inputRepository.getHeader();
	List<TableData> rowData = inputRepository.getRowData();
	HashMap<String, Integer> catIndices = new LinkedHashMap<>();

	extractColIndeces(header, catIndices);


	for (TableData curLine : rowData) {
	    
	    CatagoryData catData = new CatagoryData();
	    checkCatagories(curLine, catIndices,catData);

	    classify(catData, curLine);
	    
//	    curLine.setCatagory(Catagory.getPathoCode());

	}
	
    }
    
    
    
    
    
    
    
    
    
    protected class CatagoryData {

	private boolean pvs1 = false;

	private boolean ps1 = false;
	private boolean ps4 = false;

	private boolean pm2 = false;
	private boolean pm4 = false;

	private boolean pp2 = false;
	private boolean pp3 = false;

	public boolean isPvs1() {
	    return pvs1;
	}

	public void setPvs1(boolean pvs1) {
	    this.pvs1 = pvs1;
	}

	public boolean isPs1() {
	    return ps1;
	}

	public void setPs1(boolean ps1) {
	    this.ps1 = ps1;
	}

	public boolean isPs4() {
	    return ps4;
	}

	public void setPs4(boolean ps4) {
	    this.ps4 = ps4;
	}

	public boolean isPm2() {
	    return pm2;
	}

	public void setPm2(boolean pm2) {
	    this.pm2 = pm2;
	}

	public boolean isPm4() {
	    return pm4;
	}

	public void setPm4(boolean pm4) {
	    this.pm4 = pm4;
	}

	public boolean isPp2() {
	    return pp2;
	}

	public void setPp2(boolean pp2) {
	    this.pp2 = pp2;
	}

	public boolean isPp3() {
	    return pp3;
	}

	public void setPp3(boolean pp3) {
	    this.pp3 = pp3;
	}


    
	
	
    
    }
    
    
    
    
}
