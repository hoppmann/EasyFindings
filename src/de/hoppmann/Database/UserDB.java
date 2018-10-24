/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.Database;

import de.hoppmann.config.Config;
import java.io.File;

/**
 *
 * @author hoppmann
 */
public class UserDB extends Database {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    protected Config config = Config.getInstance();
    

    // gene table
    protected final String geneTable = "genes";
    protected final String geneCol = "gene";
    protected final String geneInfoCol = "geneInfo";
    
    // variant table 
    protected final String varTable = "variants";
    protected final String varCol = "var";
    protected final String varInfoCol = "varInfo";
    
    
    // receiver table
    protected final String receiverTable = "receiver";
    protected final String IdKey = "ID";
    protected final String titleKey = "Title";
    protected final String fullNameKey = "Full_name";
    protected final String addressKey = "Postal_address";
    protected final String zipCodeKey = "ZIP_code";
    protected final String cityKey = "City";
    protected final String countryKey = "Countries";

    
    
    
    
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    ////////////////////////////////////
    //////// general operations ////////
    ////////////////////////////////////
    
    
    
    ///////////////////////////
    //////// connect to gene DB
    
    public void connectDB(File dbFile) {
	
	// connect to DB
	conn = connect(dbFile);
	

	////////////////////////
	//////// check if tables are available else create

	checkGeneTable();
	
        
	
    }
    
    
    
    
    /////////////////////////
    //////// check for tables 
    // check if table is available if not create a new corresponign table
    
    // receiver table
    protected void checkReceiverTable () {
	
	// check if table is avalable
	
	if (! hasTable(receiverTable)) {
	    
	    // prepare command
	    String createTableCmd = "CREATE TABLE " + receiverTable + "( "
		    + IdKey + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		    + titleKey + " Varchar(60), "
		    + fullNameKey + " Varchar(60) not null, " 
		    + addressKey + "Varchar(60), "
		    + zipCodeKey + " Varchar(60), "
		    + cityKey + " Varchar(60), "
		    + countryKey + " Varchar(60) )";
	    execute(createTableCmd);
	}
    }
    
    
    

    // gene Table
    protected void checkGeneTable () {
	
	// check gene table
	if (! hasTable(geneTable)){
	    
	    // prepare command
	    String createTabelCmd = "create table " + geneTable
		    + " (" + geneCol + " VARCHAR(60) not NULL, "
		    + geneInfoCol + " TEXT, "
		    + "PRIMARY KEY ( " + geneCol + " ))";
	    
	    // execute command
	    execute(createTabelCmd);
	}
	
    }
    
    
    
    
    // variant table
    private void checkVariantTable () {
		// check variant table
	if (! hasTable(varTable)) {
	    
	    // prepare command
	    String createTableCmd = "CREATE TABLE " + varTable
		    + " (" + varCol + " VARCHAR(60) not NULL, "
		    + geneCol + " VARCHAR(60), "
		    + varInfoCol + " TEXT, "
		    + "PRIMARY KEY ( " + varCol + " ))";
	    
	    // execute command
	    execute(createTableCmd);
	    
	}

    }
    
    
    
    
    ////////////////////////////
    //////// create new gene DB
    public File createNewGeneDB () {
	
	// check if db has entry in config and load opener correspoingly
	String dbPath = null;
	
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()) {
	    dbPath = new File(config.getDbPath()).getParent();
	}
	
	File geneDB = createNewDB(dbPath);

	if (geneDB != null) {
	    // save path in config
	    config.setDbPath(geneDB.getAbsolutePath());
	}
	
	return geneDB;
	
    }
    
    
    
    /////////////////////
    //////// open gene DB
    public File openGeneDB () {

	// check if db has entry in config and load opener correspoingly
	String dbPath = null;
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()){
	   dbPath = new File(config.getDbPath()).getParent();
	} 


	// open DB file
	File dbFile = openDB(dbPath);
	
	
	
	// save path in config
	if (dbFile != null){
	    config.setDbPath(dbFile.getAbsolutePath());
	} else {
	    return null;
	}
	
	return dbFile;
	
    }
    
    
    
    
    
	
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////











}
