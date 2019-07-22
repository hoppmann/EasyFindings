/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.openFile;

import de.hoppmann.config.Config;
import de.hoppmann.database.geneInfoDB.Hg19TableModel;
import de.hoppmann.database.geneInfoDB.Hg19TableRepository;
import de.hoppmann.database.geneInfoDB.HgmdTableRepository;
import de.hoppmann.database.geneInfoDB.IHgmdTableModel;
import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.InputRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hoppmann
 */
public class CatagorizeAcmg {

    
    private Config config = Config.getInstance();
    private String lastGenePp2 = "";
    private String lastGeneHgmdQuery = "";
    private Hg19TableModel geneData;
    List<IHgmdTableModel> hgmdInfo = new LinkedList<>();
    
    
    
    
    
    
    
    
    
    
    
 
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
		if (curTrans.matches("-?\\d+")){
		    transLengthIntList.add(Integer.valueOf(curTrans));
		}
	    }
	}


	
	// avoid db query when gene info was already queried
	if (!lastGenePp2.equals(geneName)) {
	    Hg19TableRepository geneInfoRepo = new Hg19TableRepository();
	    geneData = new Hg19TableModel(geneName);
	    geneInfoRepo.queryForGene(geneData);
	    lastGenePp2 = geneName;
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
     * Known AA change in unknown baseChange
     * 
     * @param curLine
     * @param catIndices
     * @return 
     */
    private boolean checkPS1(TableData curLine, HashMap<String, Integer> catIndices) {
	boolean ps1 = false;
	
        
        // check if columns are defined
        if (catIndices.get(config.getGeneCol()) >= 0 && catIndices.get(config.getCodonChangeCol()) >= 0 && catIndices.get(config.getpNomenCol()) >= 0){
                
            String geneName = curLine.getEntry(catIndices.get(config.getGeneCol()));
            
            // don'r repeat if done before
            if (!lastGeneHgmdQuery.equals(geneName)) {
                HgmdTableRepository hgmdRepo = new HgmdTableRepository();
                hgmdInfo = hgmdRepo.queryForGene(geneName);
            }
            if (hgmdInfo.size() > 0) {

                List<String> pNomenList = curLine.getSplitEntry(catIndices.get(config.getpNomenCol()), ",");
                for (String curPNomen : pNomenList) {

                    curPNomen = curPNomen.replace("p.", "");
                    if (curPNomen.matches("\\?")) {
                        continue;
                    }

                    // if going here we got identical variants of the same gene
                    for (IHgmdTableModel curModel : hgmdInfo) {
                        if (curModel.getHgmdAaChange().equals(curPNomen)) {

                            // check if triplet is different
                            String hgmdCodon = curModel.getHgmdCodonChange();
                            hgmdCodon = Arrays.asList(hgmdCodon.split("-")).get(1);

                            String curLineCodonChange = curLine.getEntry(catIndices.get(config.getCodonChangeCol()));
                            curLineCodonChange = Arrays.asList(curLineCodonChange.split("/")).get(1);

                            if (!curLineCodonChange.toUpperCase().equals(hgmdCodon.toUpperCase())) {
                                ps1 = true;
                            }
                        }
                    }
                }
            }
        }


        
	
	return ps1;
    }
    
    
    
    /**
     * ClinVar -> Pathogenic (case insensitive)
     * HGMD -> DM
     * @param curLine
     * @param catIndices
     * @return 
     */
    
    
    private boolean checkPS3 (TableData curLine, HashMap<String, Integer> catIndices){
	
	boolean ps3 = false;
	
	if (catIndices.get(config.getHgmdCol()) >= 0){
	    String hgmdEntry = curLine.getEntry(catIndices.get(config.getHgmdCol()));
	    Set<String> hgmdSet = new TreeSet<>(Arrays.asList(hgmdEntry.split(",")));
	
	    if (hgmdSet.contains("DM")){
		ps3 = true;
	    }
	}
	
	
	if (catIndices.get(config.getClinvarCol()) >= 0) {
	    String clinVarEntry = curLine.getEntry(catIndices.get(config.getClinvarCol()));

            Set<String> clinVarSet = new TreeSet<>(new Comparator<String>() {
                @Override
                public int compare(String a, String b) {
                    return a.toLowerCase().compareTo(b.toLowerCase());
                }
            });
            
            clinVarSet.addAll(Arrays.asList(clinVarEntry.split(",")));
            
	    if (clinVarSet.contains("pathogenic")){
		ps3 = true;
	    }

	}
	
	return ps3;
	
    }
    
    
    
    
    
    
    
    
      /**
     * MAF Cases >> MAF Controls (inhouse DB needed)
     * @param curLine
     * @param catIndices
     * @return 
     */  
    private boolean checkPS4(TableData curLine, HashMap<String, Integer> catIndices) {
	
	boolean ps4 = false;
	
	// added as test
	
	
	
	return ps4;
    }
    
    
    
    
    
    
    
    
    
    
    
    /**
     * MAF = 0 (nfe, eas, sas, afr)
     * @param curLine
     * @param catIndices
     * @return 
     */
    
    private boolean checkPM2(TableData curLine, HashMap<String, Integer> catIndices){
	
	boolean pm2 = false;
	
	
	if (catIndices.get(config.getMafAllCol()) > 0) {

	    List<String> splitMafString = (curLine.getSplitEntry(catIndices.get(config.getMafAllCol()), ","));
	    List<Double> mafList = new LinkedList<>();
	    for (String curMaf : splitMafString){
		try {
		    mafList.add(Double.valueOf(curMaf));
		    double maf = Collections.max(mafList);
		    if (maf <= 0){
			pm2 = true;
		    }
		} catch (NumberFormatException e){
		    
		}
	    }
	    
	}
	
	
	
	if (catIndices.get(config.getMafNfeCol()) > 0 ){
	    
	    List<String> splitMafString = (curLine.getSplitEntry(catIndices.get(config.getMafNfeCol()), ","));
	    List<Double> mafList = new LinkedList<>();
	    for (String curMaf : splitMafString) {
		try {
		    mafList.add(Double.valueOf(curMaf));
		    double maf = Collections.max(mafList);

		    if (maf <= 0) {
			pm2 = true;
		    }
		} catch (NumberFormatException e) {

		}
	    }
	    

	}
	
	
	if (catIndices.get(config.getMafAfrCol()) > 0) {
	    List<String> splitMafString = (curLine.getSplitEntry(catIndices.get(config.getMafAfrCol()), ","));
	    List<Double> mafList = new LinkedList<>();
	    for (String curMaf : splitMafString) {
		try {
		    mafList.add(Double.valueOf(curMaf));
		    double maf = Collections.max(mafList);
		    if (maf <= 0) {
			pm2 = true;
		    }
		} catch (NumberFormatException e) {

		}
	    }

	}
	
	
	if (catIndices.get(config.getMafSasCol()) > 0) {
	    List<String> splitMafString = (curLine.getSplitEntry(catIndices.get(config.getMafSasCol()), ","));
	    List<Double> mafList = new LinkedList<>();
	    for (String curMaf : splitMafString) {
		try {
		    mafList.add(Double.valueOf(curMaf));
		    double maf = Collections.max(mafList);
		    if (maf <= 0) {
			pm2 = true;
		    }
		} catch (NumberFormatException e) {

		}
	    }

	}
	
	
	if (catIndices.get(config.getMafEasCol()) > 0) {
	    List<String> splitMafString = (curLine.getSplitEntry(catIndices.get(config.getMafEasCol()), ","));
	    List<Double> mafList = new LinkedList<>();
	    for (String curMaf : splitMafString) {
		try {
		    mafList.add(Double.valueOf(curMaf));
		    double maf = Collections.max(mafList);
		    if (maf <= 0) {
			pm2 = true;
		    }

		} catch (NumberFormatException e) {

		}
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
    
    
    
    
    
    
    
    
    
    private boolean checkPM5 (TableData curLine, HashMap<String, Integer> catIndices){
        
        boolean pm5 = false;
        
        
        if (catIndices.get(config.getGeneCol()) >= 0 && catIndices.get(config.getpNomenCol()) >= 0){
            String geneName = curLine.getEntry(catIndices.get(config.getGeneCol()));
            
            // don't repeat if queried before
            if (!lastGeneHgmdQuery.equals(geneName)){
                HgmdTableRepository hgmdRepo = new HgmdTableRepository();
                hgmdInfo = hgmdRepo.queryForGene(geneName);
            }
            
            
            if (hgmdInfo.size() > 0 ){
                
                List<String> pNomenList = curLine.getSplitEntry(catIndices.get(config.getpNomenCol()), ",");
                
                for (String curPNomen : pNomenList){
                    

                    if (curPNomen.equals("\\?") || curPNomen.equals("=")){
                        continue;
                    }
                    
                    // extract pNomen change in seperate variables
                    curPNomen = curPNomen.replace("p.", "");
                    String origAA = curPNomen.replaceAll("(\\w+\\d+)(\\D+)", "$1");
                    String changedAA = curPNomen.replaceAll("(\\w+\\d+)(\\D+)", "$2");

                    
                    // get information from HGMD        
                    Map<String, Set<String>> hgmdCodonChange = new HashMap<>();
                    for (IHgmdTableModel hgmdModel : hgmdInfo){
                        String curChanged = hgmdModel.getHgmdAaChange().replaceAll("(\\w+\\d+)(\\D+)", "$2");
                        curChanged = curChanged.replace("Term", "*");
                        String curOrig = hgmdModel.getHgmdAaChange().replaceAll("(\\w+\\d+)(\\D+)", "$1");
                        hgmdCodonChange.putIfAbsent(curOrig, new HashSet<>());
                        hgmdCodonChange.get(curOrig).add(curChanged);                    }


                    if (hgmdCodonChange.keySet().contains(origAA)){
                        if (!hgmdCodonChange.get(origAA).contains(changedAA)){
                            pm5 = true;
                        }
                    }
                }
            }
    }
        
        return pm5;
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
	    
	    if (!lastGenePp2.equals(geneName)){
		Hg19TableRepository geneInfoRepo = new Hg19TableRepository();
		geneData = new Hg19TableModel(geneName);
		geneInfoRepo.queryForGene(geneData);
		lastGenePp2 = geneName;
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

	    if (!lastGenePp2.equals(geneName)) {
		Hg19TableRepository geneInfoRepo = new Hg19TableRepository();
		geneData = new Hg19TableModel(geneName);
		geneInfoRepo.queryForGene(geneData);
		lastGenePp2 = geneName;
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
        catIndices.put(config.getCodonChangeCol(), -1);
	
	
	
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
	curLine.setPs3(checkPS3(curLine, catIndices));
	curLine.setPs4(checkPS4(curLine, catIndices));

	curLine.setPm2(checkPM2(curLine, catIndices));
	curLine.setPm4(checkPM4(curLine, catIndices));
        curLine.setPm5(checkPM5(curLine, catIndices));

	curLine.setPp2(checkPP2(curLine, catIndices));
	curLine.setPp3(checkPP3(curLine, catIndices));


    }
    
    
    
    
    private void classify(TableData curLine, HashMap<String, Integer> catIndices) {
	
	curLine.setCatagory(Catagory.getUnclearCode());
	
	
	int psCount = 0;
	if (curLine.isPs1())psCount++;
	if (curLine.isPs3())psCount++;
	if (curLine.isPs4())psCount++;
	
	
	int pmCount = 0;
	if (curLine.isPm2())pmCount++;
	if (curLine.isPm4())pmCount++;
        if (curLine.isPm5()) pmCount++;
	
	
	int ppCount = 0;
	if (curLine.isPp2())ppCount++;
	if (curLine.isPp3())ppCount++;

	
	
	
	
	
	// check for pathogenic variants
		
	if (curLine.isPvs1()){
	    if (psCount >= 1){
		setPatho(curLine);
	    }
	    
	    if (pmCount >= 2){
		setPatho(curLine);
	    }
	    
	    if (pmCount >= 1){
		if (ppCount >= 1){
		    setPatho(curLine);
		}
	    }
	    
	    if (ppCount >= 2){
		setPatho(curLine);
	    }
	    
	}


	
	
	if (psCount >= 2){
	    setPatho(curLine);
	}
	

	
	
	if (psCount >= 1){
	    
	    if (pmCount > 3){
		setPatho(curLine);
	    } 
	    
	    
	    if (pmCount >= 2){
		if (ppCount >= 2){
		    setPatho(curLine);
		}
	    }
	    
	    
	    if (pmCount >= 1) {
		if (ppCount >= 4){
		    setPatho(curLine);
		}
	    }
	    
	    
	}
	
	
	
	
	
	
	
	// check for likely pathogenic variants
	
	
	if (curLine.isPvs1()){
	    if (pmCount >= 1){
		setProbPatho(curLine);
	    }
	}
	
	
	
	
	if (psCount >= 1){
	    if (pmCount >= 1){
		setProbPatho(curLine);
	    }
	    
	    if (ppCount >= 2){
		setProbPatho(curLine);
	    }
	}
	
	
	
	
	
	
	if (pmCount >= 3){
	    setProbPatho(curLine);
	}
	
	
	
	
	
	
	if (pmCount >= 2) {
	    if (ppCount >= 2){
		setProbPatho(curLine);
	    }
	}
	
	
	
	if (pmCount == 1){
	    if (ppCount >= 4){
		setProbPatho(curLine);
	    }
	}
	
	}
    
    
    
    
    
	private void setPatho(TableData curLine){
	    curLine.setCatagory(Catagory.getPathoCode());
	}
	
	private void setProbPatho(TableData curLine){
	    if (!curLine.getCatagory().equals(Catagory.getPathoCode())){
		curLine.setCatagory(Catagory.getProbPathoCode());
	    }
	}



    
    
    
    
    
    public void createCatagories(InputRepository inputRepository) {

	List<String> header = inputRepository.getHeader();
	List<TableData> rowData = inputRepository.getRowData();
	HashMap<String, Integer> catIndices = new LinkedHashMap<>();

	extractColIndeces(header, catIndices);


	for (TableData curLine : rowData) {

	    checkCatagories(curLine, catIndices);

	    classify(curLine, catIndices);

	}
	

	
    }
    
    
    
    
    
}
