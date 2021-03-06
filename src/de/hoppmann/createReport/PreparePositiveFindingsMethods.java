/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.Catagory;
import de.hoppmann.gui.modelsAndData.FindingsRepository;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.database.userDB.ConnectSQLite;
import de.hoppmann.database.userDB.ConnectUserDB;
import de.hoppmann.database.userDB.interfaces.IGeneInfoRepository;
import de.hoppmann.database.userDB.interfaces.IVariantInfoRepository;
import de.hoppmann.database.userDB.snipletDB.VariantInfo;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hoppmann
 */
public class PreparePositiveFindingsMethods {

    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private FindingsRepository findings;
    private final Config config = Config.getInstance();


    private String geneName = null;
    private List<String> varNameList = null;
    private List<String> rsID = null;
    private String pubmedID = null;
    private List<String> pNomen = null;
    private String predQuotient = null;
    private String zygocity = null;
    private List<String> maf = null;
    
    // infos from the gene DB
    private String geneSniplet = null;
    private Map<String, String> varSniplet;
    private List<String> tableElements = new LinkedList<>();
    private String htmlGeneTable = null;
    
    private IGeneInfoRepository geneSnipletRepo;
    private IVariantInfoRepository varSnipletRepo;
    
    
    


    
    
    
    
    
    
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public PreparePositiveFindingsMethods(FindingsRepository findings, IGeneInfoRepository geneSnipletRepo, IVariantInfoRepository varSnipletRepo) {
	this.findings = findings;
        this.geneSnipletRepo = geneSnipletRepo;
        this.varSnipletRepo = varSnipletRepo;

	
	
	/* 
	for each finding 
	    retrieve gene and variant info
	    prepare as table
	*/


        
	for (TableData curFinding : findings.getStoredData()) {

	    // check if current variant is classified as causal -> prepare findings state
	    if (curFinding.getCatagory().equals(Catagory.getPathoCode()) ||
		curFinding.getCatagory().equals(Catagory.getProbPathoCode())){
		

		// prepare column entries
		prepareColEntries(curFinding, findings.getHeader());

		// get data from DB
		retrieveVariantDbEntry();

		// prepare html table
		prepareHtmlTable();
		
                // add predifined gene text
                addGeneSnipletText();
		
		// add predefined variant text
		addVarInfoText();
		
	    }

	}

	// join element to one string
	htmlGeneTable = String.join("\n", tableElements);

    }
    

	
    
    
    
    
    
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////


    //// for each column of interest get the values
    private void prepareColEntries(TableData curFinding, LinkedHashMap<String, Integer> header) {
	
	
	
	/* 
	    for each column of interest
		check for existance in config -> if so get entry
		check if entry was retrieved. Else add NA
	    
	 */
	
	
	
	//// gene name
	geneName = retrieveEntries(curFinding, config.getGeneCol(), false, header).get(0);
		
	
	//// RsID
	rsID = retrieveEntries(curFinding, config.getRsIdCol(), true, header);


	//// protein nomenclature
	pNomen = retrieveEntries(curFinding, config.getpNomenCol(), true, header);

	
	//// zygocity
	String zygocityString = retrieveEntries(curFinding, config.getZygocityCol(), false, header).get(0);
	int zygocityNumber = -1;
	if (!zygocityString.equals("NA")){
	    zygocityNumber = Integer.valueOf(zygocityString);
	}
	
	
	switch (zygocityNumber) {
	    case -1: zygocity = "NA";
	    break;
	    case 0: zygocity = "HOM_REF";
		break;
	    case 1:  zygocity = "HET";
		break;
	    case 2: zygocity = "./.";
		break;
	    case 3: zygocity = "HOM";
	}
	
	

	//// minor allele frequency
	maf = retrieveEntries(curFinding, config.getMafAllCol(), true, header);
	
	
	//quotient of prediciton tools
	String totPred = retrieveEntries(curFinding, config.getTotPredCol(), false, header).get(0);
	String percentDamaging = retrieveEntries(curFinding, config.getPredScoreCol(), false, header).get(0);
	
	if(!totPred.equals("NA") && !percentDamaging.equals("NA")){
	    Double damagingDouble = Double.valueOf(percentDamaging) * Double.valueOf(totPred);
	    int damaging = damagingDouble.intValue();
	    predQuotient = damaging + "/" + totPred;
	} else {
	    predQuotient = "NA";
	}

	
	
	
	//// PubMed ID 
	List<String> pubMedIdList = retrieveEntries(curFinding, config.getPubMedIdCol(), false, header);
	if (pubMedIdList.size() > 0){
	    pubmedID = retrieveEntries(curFinding, config.getPubMedIdCol(), false,header).get(0);
	} else {
	    pubmedID = "NA";
	}
	
	
	
	//// variant
	// get list of all variant found in gene
	varNameList = retrieveEntries(curFinding, config.getcNomenCol(), true, header);
	
    }
    
    
    
    
    
    
    ////////////
    //////// retrieve hash entriy, split it and check if NA needed
    private List<String> retrieveEntries(TableData curFinding, String colName, boolean split, LinkedHashMap<String, Integer> header) {
	
	List<String> entryList = new LinkedList();
	
	if (header.containsKey(colName)){
	    entryList = curFinding.getSplitEntry(findings.getColIndex(colName), ",");
	} else {
	    entryList.add("NA");
	}
	// check each element if empty. If so set NA
	if (entryList.size() > 0) {
	    for (int i = 0; i < entryList.size(); i++) {
		if (entryList.get(i) == null || entryList.get(i).equals("")) {
		    entryList.set(i, "NA");
		}
	    }
	} else {
	    entryList.add("NA");
	}
	
	return entryList;
	
	
    }
	
    
    
    
    
    
    
    
    
    
    //// get all informations from the databse for current gene and it's variants
    private void retrieveVariantDbEntry(){
	
        VariantInfo varInfo = new VariantInfo(geneName);
        varInfo = geneSnipletRepo.getGeneInfo(varInfo);
        geneSniplet = varInfo.getGeneInfo();
        
        varSniplet = new LinkedHashMap<>();
        for (String varName : varNameList){
            varInfo.setVarName(varName);
            varInfo = varSnipletRepo.getVariantInfo(varInfo);
            varSniplet.put(varName, varInfo.getVarInfo());
            
        }
    }
	
	

    
    
    
    
    
    
    
    
    
    //// prepare the HTML code for a table consising of all infos needed for findings
    private void prepareHtmlTable(){

	// start table and define size
	tableElements.add("<table width=\"100%\" >");
	tableElements.add("\t<tbody>");

	
	/*  
	prepare header
	    new column
	    add header names
	*/
	
	// new line
	tableElements.add("\t\t<tr>");
	
	// table elements
	tableElements.add("\t\t\t<td><strong>Gen</strong></td>");
	tableElements.add("\t\t\t<td><strong>cDNA</strong></td>");
	tableElements.add("\t\t\t<td><strong>Protein</strong></td>");
	tableElements.add("\t\t\t<td><strong>Zygotie</strong></td>");
	tableElements.add("\t\t\t<td><strong>MAF</strong></td>");
	tableElements.add("\t\t\t<td><strong>Pred</strong></td>");
	tableElements.add("\t\t\t<td><strong>dbSNP</strong></td>");
	tableElements.add("\t\t\t<td><strong>PubMed ID</strong></td>");
	
	// end line
	tableElements.add("</tr>");


	
	// Add current gene and all variants
	    
	for (int i = 0 ; i < varNameList.size(); i++){
	    
	    // check if variant has info if not do not add it
	    if (varSniplet.get(varNameList.get(i)) != null) {
		
                // check if variables has entries, else add ""
                if (varNameList.size()-1 < i) {varNameList.add("NA");}
                if (pNomen.size()-1 < i){pNomen.add("NA");}
                if (maf.size()-1 < i) {maf.add("NA");}
                if (rsID.size()-1 < i) {rsID.add("NA");}
                

                // new line
		tableElements.add("\t\t<tr>");
		tableElements.add("\t\t\t<td><small>" + geneName + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + varNameList.get(i) + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + pNomen.get(i) + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + zygocity + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + maf.get(i) + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + predQuotient + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + rsID.get(i) + "</small></td>");
		tableElements.add("\t\t\t<td><small>" + pubmedID + "</small></td>");
		
		// end line
		tableElements.add("\t\t</tr>");
	    }
	    
	}

	// end table
	tableElements.add("</tbody>");
	tableElements.add("</table>");

//	//// add gene description
//	tableElements.add("<p> <strong>" + geneName + "</strong> <br>" + geneSniplet + "</p>");

	
    }
    
    
    
    
    private void addGeneSnipletText() {
        //// add gene description
	tableElements.add("<p> <strong>" + geneName + "</strong> <br>" + geneSniplet + "</p>");
    }
    
    
    
    
    
    
    //// prepare gene / variant text
    private void addVarInfoText() {
	
	
	// add variant lists if text is available for variant.
	for (String curVar : varSniplet.keySet()) {
	    if (varSniplet.get(curVar) != null) {
		tableElements.add("<p> <strong>" + curVar + "</strong> <br> " + varSniplet.get(curVar) + "</p>");
	    }
	}

	
	
	
	// add some spacing 
	tableElements.add("<p>&nbsp;</p>");
	tableElements.add("<p>&nbsp;</p>");
	
    }
    
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////


    public String getHtmlGeneTable() {
	return htmlGeneTable;
    }

    
    
    
    

    







}
