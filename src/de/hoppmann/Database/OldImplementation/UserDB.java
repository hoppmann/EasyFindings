/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.Database.OldImplementation;

import de.hoppmann.config.Config;
import java.io.File;
import java.sql.Connection;
import javafx.scene.control.Label;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author hoppmann
 */
public class UserDB extends Database {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    protected Config config = Config.getInstance();
    public static Connection conn;
    private Label infoLabel = new Label();
    private Label dbLabel = new Label();

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

    
    
    
    
	
    //////////////////////////////
    //////// constructor /////////
    //////////////////////////////

    public UserDB() {
	
//	
//	if (!super.isConnected(conn)) {
//	    connectDB(config.getDbPath(), false);
//	}
	
    }

    public UserDB(Label infoLabel, Label dbLabel) {
	this.infoLabel = infoLabel;
	this.dbLabel = dbLabel;
	
	if (!super.isConnected(conn)) {
	    connectDB(config.getDbPath(), false);
	}
    }
    
    
    
    
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    
    
    
     
   
    ////////////////////////////////////
    //////// general operations ////////
    ////////////////////////////////////
    
    
    /////////////////////////
    //////// check connection

    public boolean isConnected() {
	
	
	boolean connected = super.isConnected(conn);
	return connected;
    }
    
    
    
    ///////////////////////////
    //////// connect to gene DB
    
    public void connectDB(String dbPath, boolean newDb) {
	
	File dbFile = null;
	
	
	
	if (dbPath == null || (!new File(dbPath).exists() && ! newDb)) {
	    dbFile = chooseFile(null);
	} else {
	    dbFile = new File(dbPath);
	}
	
	
	
	// connect to DB
	if (dbFile != null) {
	    
	    config.setDbPath(dbFile.getAbsolutePath());
	    
	    
	    conn = connect(dbFile);

	    ////////////////////////
	    //////// check if tables are available else create

	    checkGeneTable();
	    checkVariantTable();
	    checkReceiverTable();
	}
        
	if (isConnected(conn) && dbFile != null){
	    infoLabel.setText("Connection to " + new File(config.getDbPath()).getAbsolutePath() + " established!");
	    dbLabel.setText(new File(config.getDbPath()).getName());

	}
    }
    
    
    
    
    /////////////////////////
    //////// check for tables 
    // check if table is available if not create a new corresponign table
    
    // receiver table
    private void checkReceiverTable () {
	
	// check if table is avalable
	
	if (! hasTable(receiverTable, conn)) {
	    
	    // prepare command
	    String createTableCmd = "CREATE TABLE " + receiverTable + "( "
		    + IdKey + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		    + titleKey + " Varchar(60), "
		    + fullNameKey + " Varchar(60) not null, " 
		    + addressKey + " Varchar(60), "
		    + zipCodeKey + " Varchar(60), "
		    + cityKey + " Varchar(60), "
		    + countryKey + " Varchar(60) )";
	    execute(createTableCmd, conn);
	}
    }
    
    
    

    // gene Table
    private void checkGeneTable () {
	
	// check gene table
	if (! hasTable(geneTable, conn)){
	    
	    // prepare command
	    String createTabelCmd = "create table " + geneTable
		    + " (" + geneCol + " VARCHAR(60) not NULL, "
		    + geneInfoCol + " TEXT, "
		    + "PRIMARY KEY ( " + geneCol + " ))";
	    
	    // execute command
	    execute(createTabelCmd, conn);
	}
	
    }
    
    
    
    
    // variant table
    private void checkVariantTable () {
		// check variant table
	if (! hasTable(varTable, conn)) {
	    
	    // prepare command
	    String createTableCmd = "CREATE TABLE " + varTable
		    + " (" + varCol + " VARCHAR(60) not NULL, "
		    + geneCol + " VARCHAR(60), "
		    + varInfoCol + " TEXT, "
		    + "PRIMARY KEY (" + varCol + "))";
	    // execute command
	    execute(createTableCmd,conn);
	    
	}

    }
    
    
    
    
    ////////////////////////////
    //////// create new gene DB
    public File newDB () {
	
	// check if db has entry in config and load opener correspoingly
	String dbPath = null;
	
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()) {
	    dbPath = new File(config.getDbPath()).getParent();
	}
	
	File dbFile = createNew(dbPath);

	if (dbFile != null) {
	    // save path in config
	    config.setDbPath(dbFile.getAbsolutePath());
	}

	
	return dbFile;
    }
    
    
    
    
    
    
    
    /////////////////////
    //////// open gene DB
    public File openDB () {

	// check if db has entry in config and load opener correspoingly
	String dbPath = null;
	if (config.getDbPath() != null && new File(config.getDbPath()).exists()){
	   dbPath = new File(config.getDbPath()).getParent();
	} 


	// open DB file
	File dbFile = chooseFile(dbPath);
	
	
	/* 
	check if DB has .db as suffix
	then save path in config
	*/
	
	if (dbFile != null){
	    if (!FilenameUtils.getExtension(dbFile.getAbsolutePath()).equals(".db")){
		dbPath = dbFile.getAbsolutePath();
		dbPath += ".db";
	    }
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
