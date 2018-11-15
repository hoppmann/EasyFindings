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
import de.hoppmann.database.OldImplementation.UserDB;
import de.hoppmann.database.userDB.DbOperations;
import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class PrepareNegativeFindings extends UserDB {



    ///////////////////////////
    //////// variables ////////
    ///////////////////////////

    private final Config config = Config.getInstance();
    
    private FindingsRepository findings;
    private String geneName = null;
    private String moi = null;
    private List<String> cDna = null;
    private List<String> pNomen = null;
    private String zygocity = null;
    private List<String> rsId = null;
    private String catagory = null; 
    private List<String> cNomenList = null;
    
    private String htmlTable;
    private List<String> tableElements = new LinkedList<>();
    

    // geneInfoDB variables
    private final String dbName = "geneInfos.db";
    private final String tableHg19 = "hg19";


    
    
    
    
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
    public PrepareNegativeFindings(FindingsRepository findings) {
	
	this.findings = findings;
	
	
	connectGeneInfoDB();
	
	
	/* 
	prepare table with header and specifications
	
	for each finding 
	    retrieve gene and variant info
	    prepare as table
	
	finalize table with closing tabs
	*/

	// prepare table
	prepareHtmlTable();

	for (TableData curFinding : findings.getStoredData()) {

	    // prepare column entries
	    prepareColEntries(curFinding);


	    // prepare html table
	    fillHtmlTable();

	}	
	
	// finalize HTML table
	finalizeHtmlTable();
	
	
	// join element to one string
	htmlTable = String.join("\n", tableElements);

//	closeDB(conn);
	    
    }

	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    //////////////////
    //////// prepare column entries for each column of interest
    public void prepareColEntries(TableData curFinding) {
	
	
	
	/* 
	    for each column of interest
		check for existance in config -> if so get entry
		check if entry was retrieved. Else add NA
	    
	 */
	
	/*
	variables to fill
	    geneName
	    moi
	    cDna
	    pNomen
	    zygocity
	    rsId
	    catagory
	*/
	
	// get gene  name of current gene
	geneName = retrieveEntries(curFinding, config.getGeneCol(), false).get(0);
	
	// get moi of current gene
	moi = getMoi(geneName);
	
	// get current variant (cDNA)
	cDna = retrieveEntries(curFinding, config.getcNomenCol(), true);
	
	
	// get protein nomenclature
	pNomen = retrieveEntries(curFinding, config.getpNomenCol(), true);
	
	
	// get zygocity of variant
	int zygocityInteger = Integer.valueOf(retrieveEntries(curFinding, config.getZygocityCol(), false).get(0));
	
	switch (zygocityInteger) {
	    case 0: zygocity = "HOM_REF";
		break;
	    case 1:  zygocity = "HET";
		break;
	    case 2: zygocity = "./.";
		break;
	    case 3: zygocity = "HOM";
	}

	// get rsID
	rsId = retrieveEntries(curFinding, config.getRsIdCol(), true);
	
	
	// get catagory
	catagory = Catagory.getValueByCode(curFinding.getCatagory());
	
	
	
	//// get a list of all variant found in gene
	cNomenList = retrieveEntries(curFinding, config.getcNomenCol(), true);

	
	
    }
	
	
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /////////////////////////////7
    //////// retriev MOI from database
    
    
    
    // below code should be outsourced
    
    
    private void connectGeneInfoDB () {

	// get current path of program
	String curDir = System.getProperty("user.dir");
	File geneInfoDB = new File(curDir + File.separator + "DBs" + File.separator + dbName);

	// connect to geneInfo.db
        
        
        
        
        
        
        
        
        // TO BE ADOPTED
        
        
	conn = connect(geneInfoDB);

    }
    
    
    // retrieve MOI inforamtion of current gene
    private String getMoi(String geneName) {

	
	String moi = "";
	try {
	    String geneNameCol = "geneName";
	    String arCol = "AR";
	    String adCol = "AD";
	    String xlrCol = "XLR";
	    String xldCol = "XLD";
	    
	    // get MOI
	    String query = "SELECT " + arCol + ", " + adCol + ", " + xlrCol + ", " + xldCol
		    + " from hg19 where " + geneNameCol + " == '" + geneName + "'";
	    
	    ResultSet rs = DbOperations.execute(query, conn);
	    Set<String> moiSet = new TreeSet();
	    if (rs.next()) {
		if (rs.getBoolean(arCol)) {
		    moiSet.add("AR");
		}
		if (rs.getBoolean(adCol)) {
		    moiSet.add("AD");
		}
		if (rs.getBoolean(xlrCol)) {
		    moiSet.add("XLR");
		}
		if (rs.getBoolean(xldCol)) {
		    moiSet.add("XLD");
		}
	    }
	    
	    moi = String.join(", ", moiSet);

	} catch (SQLException ex) {
	    Logger.getLogger(PrepareNegativeFindings.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	return moi;
		
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
	
    
    
    ///////////////////
    //////// finalize table
    private void finalizeHtmlTable() {
	
	// end table
	tableElements.add("</tbody>");
	tableElements.add("</table>");


	// add some spacing 
	tableElements.add("<p>&nbsp;</p>");
	tableElements.add("<p>&nbsp;</p>");
	
    }
    
    
    
    
    ////////////////
    //////// prepare table
    
    private void prepareHtmlTable() {
	
	
	// make statement that no causal gene was found
	tableElements.add("<p><strong>Es konnte kein eindeutig pathogene Varianten gefunden werden.</strong></p>");
	
	
	// make statement that findgs might be pathogenic
	tableElements.add("<p><small><small>Nebenbefundlich festgestellte, fraglich pathogene Varianten:</small></small></p>");
	
	
	// start table and define size
	tableElements.add("<table width=\"100%\" border=\"1\">");
	tableElements.add("<tbody>");

	
	
	
	
	
	/*  
	prepare header
	    new column
	    add header names
	*/
	
	// new line
	tableElements.add("<tr>");
	
	// table elements
	tableElements.add("<td><small><small><strong>Gen</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>Vererbung</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>cDNA</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>Protein</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>Zygotie</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>rsID</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>Anlass</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>Effekt der variante auf das Protein</small></small></strong></td>");
	tableElements.add("<td><small><small><strong>Kommentar</small></small></strong></td>");
	
	// end line
	tableElements.add("</tr>");
    }
    
    
    
    //////////////
    //// prepare the HTML code for a table consising of all infos needed for findings
    private void fillHtmlTable(){

	 /*
	variables to fill
	    geneName
	    moi
	    cDna
	    pNomen
	    zygocity
	    rsId
	    Anlass -> will be ACMG criteria
	    catagory
	*/
	
	// Add current gene and all variants
	    
	for (int i = 0 ; i < cNomenList.size(); i++){
//	    
	    // new line
	    tableElements.add("<tr>");
	    tableElements.add("<td><small><small>" + geneName + "</small></small></td>");
	    tableElements.add("<td><small><small>" + moi + "</small></small></td>");
	    tableElements.add("<td><small><small>" + cNomenList.get(i) + "</small></small></td>");
	    tableElements.add("<td><small><small>" + pNomen.get(i) + "</small></small></td>");
	    tableElements.add("<td><small><small>" + zygocity + "</small></small></td>");
	    tableElements.add("<td><small><small>" + rsId.get(i) + "</small></small></td>");
	    tableElements.add("<td><small><small>&nbsp</small></small></td>");
	    tableElements.add("<td><small><small>" + catagory + "</small></small></td>");
	    tableElements.add("<td><small><small>&nbsp</small></small></td>");
	    

	    // end line
	    tableElements.add("</tr>");
	    
	    
	}

    }
    
    
    
    
    
    
    
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getHtmlTable() {
	return htmlTable;
    }


    








}
