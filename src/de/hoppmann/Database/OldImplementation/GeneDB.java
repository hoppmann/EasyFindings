/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.Database.OldImplementation;

import de.hoppmann.config.Config;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;

/**
 *
 * @author hoppmann
 */
public class GeneDB extends UserDB {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private Config config = Config.getInstance();


    
    // info fields
    private Label dbInfo = new Label();
    private Label infoLable = new Label();
    
    
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    ///////////////////////////////////////
    //////// Gene level operations ////////
    ///////////////////////////////////////
    
    
    
    /////////////////
    //// remove gene entry from DB
    
    public void removeGeneEntry (String geneName) {
	
	String removeCmd;
	// remove from gene table
	if (hasTable(geneTable, conn)) {
	    removeCmd = "DELETE FROM " + geneTable
		    + " where gene = '" + geneName + "'";
	    execute(removeCmd, conn);
	}
	
	// remove from variant table
	if (hasTable(varTable, conn)){
	    removeCmd = "DELETE FROM " + varTable
			+ " where gene = '" + geneName +"'";
	    execute(removeCmd, conn);
	}
	
    }

    
    
    
    
    
    
    
    ////////////////////////////////////////////
    //////// Gene info level operations ////////
    ////////////////////////////////////////////
    
    
    
    //////////////////
    //// save gene entry in DB
    
    public void saveGeneInfo(String geneName, String geneInfo) {

	// check connection
	if ( conn == null || ! isConnected(conn)) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return;
	}
	
	
	////////////////
	//// create db entry
	
	// replace ' by ` first one causes errors with sql
	geneInfo = geneInfo.replaceAll("'", "´");
	
	// prepare command 
	String addEntryCmd = "REPLACE INTO " + geneTable + " VALUES " 
		+ "( '" + geneName + "' , '" + geneInfo + "')";
	execute(addEntryCmd, conn);
		
	
	
	//// make lable command
	infoLable.setText("Info for gene " + geneName + " stored!");
	
	
	
    }
    
    

    
	    
	    
    
    /////////////////
    //// get all entered genes
    public List getGeneList() {
	
	// check for existance of connection
	if (isConnected(conn) == false){
	    return null;
	}
	
	
	List<String> geneList = new LinkedList<>();
	
	// prepare query to get all genes
	String query = "SELECT " + geneCol + " FROM " + geneTable;
	
	
	// execute query
	ResultSet rs = execute(query, conn);
	
	
	// retrieve results
	try {
	    while (rs.next()){
		geneList.add(rs.getString(geneCol));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	// sort and return gene list
	Collections.sort(geneList);
	return geneList;
	
    }
    
    
    
    
    
    
    
    
    //////////////////
    //// get gene entry from DB
    
    public String getGeneInfo(String geneName){
	
	// return string
	String geneInfo = null;
	
	// check connection
	if ( conn == null || ! isConnected(conn)) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return null;
	}
	
	
	
	// prepare query command
	String queryGeneInfoCmd = "select "+ geneInfoCol + " from " + geneTable
		+ " where gene == '" + geneName + "'";

	// execute query
	ResultSet rs = execute(queryGeneInfoCmd,conn);

	
	
	// check if there are results if so retrieve results
	try {
	    if (rs.next()) {
		geneInfo = rs.getString(geneInfoCol);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	return geneInfo;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////////////////////
    //////// Variant level operations ////////
    //////////////////////////////////////////
    
    
    /////////////////
    //// renove variant from databse
    public void removeVar(String geneName, String varName) {
	String delQuery;
	if (hasTable(varTable, conn)) {
	    // prepare deletion query
	    delQuery = "DELETE from " + varTable + " WHERE "
		    + geneCol + " = '" + geneName + "' AND " + varCol
		    + "= '" + varName + "'";

	    // execute query
	    execute(delQuery, conn);
	    
	    infoLable.setText("Variant " + varName + " deleted.");
	}
    }
    
    
    
    
    
    ///////////////////////
    //// retrive a list of all variants for a certain gene to add to combobox
    
    public List<String> getVarList(String geneName) {
	
	
	List<String> varList = new LinkedList<>();
	
	// prepare query to get all genes
	String query = "SELECT " + varCol + " FROM " + varTable + " WHERE " 
		+ geneCol + " = '" + geneName + "'";
	
	
	// execute query
	ResultSet rs = execute(query, conn);
	
	// retrieve results
	try {
	    while (rs.next()) {
		varList.add(rs.getString(varCol));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	// sort and return variant list
	Collections.sort(varList);
	return varList;

	
	
    }
    
    
    
    
    
    /////////////////
    //// save variant entry in DB
    
    public void saveVarInfo(String geneName, String varName, String varInfo) {
	
	
	// check connection
	if ( conn == null || ! isConnected(conn)) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return;
	}
	
	// replace ' by ` to avoid SQL errors
	varInfo = varInfo.replaceAll("'", "`");
	
	
	// prepare command
	String addEntryCmd = "REPLACE INTO " + varTable + " VALUES " 
		+ "( " + "'" + varName + "'" 
		+ ", '" + geneName + "'" 
		+ ", '" + varInfo + "'" +  " )";
	execute(addEntryCmd, conn);
		
	
	
	//// make lable command
	infoLable.setText("Variant added.");
	
    }
    
    
    
    
    /////////////
    //// retrieve variant entry from DB
    
    public String getVarInfo(String geneName, String varName) {
	
	
	// init variables
	String varInfo = null;
	
	// prepare query string
	String query = "SELECT " + varInfoCol + " FROM "
		+ varTable + " WHERE " + geneCol + " = '" + geneName + "' AND "
		+ varCol + " = '" + varName + "'";
	
	
	ResultSet rs = execute(query,conn);
	
	try {
	    if (rs.next()) {
		varInfo = rs.getString(varInfoCol);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	return varInfo;

    }
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////
    
    
    
    public String getGeneTable() {
        return geneTable;
    }

    public String getVarTable() {
        return varTable;
    }


}
