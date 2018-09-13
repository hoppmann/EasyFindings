/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.config.Config;
import de.hoppmann.gui.modelsAndData.StoreFindings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.GeneDB;
import java.io.File;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author hoppmann
 */
public class PrepareFindingsMethods {

    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private StoreFindings findings;
    private final Config config = Config.getInstance();


    private String geneName = null;
    private List<String> varNameList = null;
    private List<String> impact = null;
    private List<String> varType = null;
    private List<String> rsID = null;
    private String pubmedID = null;
    
    // infos from the gene DB
    private String geneInfo = null;
    private Map<String, String> varInfo = new LinkedHashMap<>();
    private List<String> tableElements = new LinkedList<>();
    private String htmlGeneTable = null;
    
    
    
    private GeneDB geneDb;


    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    public PrepareFindingsMethods(StoreFindings findings) {
	this.findings = findings;

	// check if existing DB is saved in config if so connect to it
	geneDb = new GeneDB();
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()) {
	    geneDb.connect(new File(config.getDbPath()));
	} else {
	    File dbPath = geneDb.openDB();
	    geneDb.connect(dbPath);
	}

	
	/* 
	for each finding 
	    retrieve gene and variant info
	    prepare as table
	*/


	for (TableData curFinding : findings.getStoredData()) {

	    // prepare column entries
	    prepareColEntries(curFinding);

	    // get data from DB
	    retrieveVariantDbEntry();

	    // prepare html table
	    prepareHtmlTable();

	}

	// join element to one string
	htmlGeneTable = String.join("\n", tableElements);

    }
    

	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////


    //// for each column of interest get the values
    private void prepareColEntries(TableData curFinding) {
	
	
	
	/* 
	    for each column of interest
		check for existance in config -> if so get entry
		check if entry was retrieved. Else add NA
	    
	 */
	
	
	
	//// gene name
	geneName = retrieveEntries(curFinding, config.getGeneCol(), false).get(0);
		
	
	
	//// Impact 
	impact = retrieveEntries(curFinding, config.getImpactCol(), true);

	
	//// RsID
	rsID = retrieveEntries(curFinding, config.getRsIdCol(), true);



	//// var type
	varType = retrieveEntries(curFinding, config.getVarTypeCol(), true);
	
	
	
	
	//// PubMed ID 
	List<String> pubMedIdList = retrieveEntries(curFinding, config.getPubMedIdCol(), false);
	if (pubMedIdList.size() > 0){
	    pubmedID = retrieveEntries(curFinding, config.getPubMedIdCol(), false).get(0);
	} else {
	    pubmedID = "NA";
	}
	
	
	
	//// variant
	// get list of all variant found in gene
	varNameList = retrieveEntries(curFinding, config.getcNomenCol(), true);
	
//	varNameList = findings.getDependentValueList(config.getcNomenCol(), config.getGeneCol(), geneName, true, ",");
	
	
    }
    
    
    
    
    
    
    ////////////
    //////// retrieve hash entriy, split it and check if NA needed
    private List<String> retrieveEntries(TableData curFinding, String colName, boolean split) {
	
	List<String> entryList = curFinding.getSplitEntry(findings.getColIndex(colName), ",");
	
	// check each element if empty. If so set NA
	for (int i = 0 ; i < entryList.size(); i++) {
	    if (entryList.get(i) == null || entryList.get(i).equals("")) {
		entryList.set(i, "NA");
	    }
	}
	
	return entryList;
	
	
    }
	
    
    
    
    //// get all informations from the databse for current gene and it's variants
    private void retrieveVariantDbEntry(){
	
	// check if gene is known in DB
	geneInfo = geneDb.getGeneInfo(geneName);

	// get infos for all variants listed and save them in hash
	for (String varName : varNameList){
	    varInfo.put(varName, geneDb.getVarInfo(geneName, varName));
	}
	
	
    }
	
	
    
    
    
    
    
    
    
    
    
    //// prepare the HTML code for a table consising of all infos needed for findings
    private void prepareHtmlTable(){

//	// prepare variables
//	List<String> tableElements = new LinkedList<>();
	 
	// start table and define size
	tableElements.add("<table width=\"100%\" >");
	tableElements.add("<tbody>");

	
	/*  
	prepare header
	    new column
	    add header names
	*/
	
	// new line
	tableElements.add("<tr>");
	
	// table elements
	tableElements.add("<td><strong>Gen</strong></td>");
	tableElements.add("<td><strong>cDNA</strong></td>");
	tableElements.add("<td><strong>variant Typ</strong></td>");
	tableElements.add("<td><strong>Protein Effekt</strong></td>");
	tableElements.add("<td><strong>dbSNP</strong></td>");
	tableElements.add("<td><strong>PubMed ID</strong></td>");
	
	// end line
	tableElements.add("</tr>");


	
	// Add current gene and all variants
//	for (String curVar : varInfo.keySet()) {
	    
	for (int i = 0 ; i < varNameList.size(); i++){
	    
	    
	    // new line
	    tableElements.add("<tr>");
	    tableElements.add("<td>" + geneName + "</td>");
	    tableElements.add("<td>" + varNameList.get(i) + "</td>");
	    tableElements.add("<td>" + varType.get(i) + "</td>");
	    tableElements.add("<td>" + impact.get(i) + "</td>");
	    tableElements.add("<td>" + rsID.get(i) + "</td>");
	    tableElements.add("<td>" + pubmedID + "</td>");

	    // end line
	    tableElements.add("</tr>");
	}

	// end table
	tableElements.add("</tbody>");
	tableElements.add("</table>");

	//// add gene description
	tableElements.add("<p> <strong>" + geneName + "</strong> <br>" + geneInfo + "</p>");

	
	// add variant lists if text is available for variant.
	for (String curVar : varInfo.keySet()) {
	    if (varInfo.get(curVar) != null) {
		tableElements.add("<p> <strong>" + curVar + "</strong> <br> " + varInfo.get(curVar) + "</p>");
	    }
	}

	// add some spacing 
	tableElements.add("<p>&nbsp;</p>");
	tableElements.add("<p>&nbsp;</p>");
	
    }
    
    
    
    
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getGeneName() {
	return geneName;
    }

    public List<String> getImpact() {
	return impact;
    }

    public List<String> getRsID() {
	return rsID;
    }

    public String getPubmedID() {
	return pubmedID;
    }

    public List<String> getVarNameList() {
	return varNameList;
    }

    public String getGeneInfo() {
	return geneInfo;
    }

    public Map<String, String> getVarInfo() {
	return varInfo;
    }

    public String getHtmlGeneTable() {
	return htmlGeneTable;
    }

    
    
    
    

    







}
