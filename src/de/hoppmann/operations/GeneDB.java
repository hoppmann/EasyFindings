/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations;

import de.hoppmann.config.Config;
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author hoppmann
 */
public class GeneDB {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private Connection conn = null;
    private String driver = "org.sqlite.JDBC";
//    private File dbF;
    private String dbPath;
    private String geneTable = "genes";
    
    
    // connect to db
    private String url;
    private String user = "";
    private String password = "";
    
    // info fields
    private Label dbInfo;
    private Label infoLable;
    
    
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public GeneDB (Label dbInfo, Label infoLable) {
	
	this.dbInfo = dbInfo;
	this.infoLable = infoLable;
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    //////////////////
    //// save entry in DB
    
    public void saveGene(String geneName, String GeneInfo) {

	// check connection
	if ( conn == null || ! isConnected()) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return;
	}
	
	
	
	
	//////////////////
	// check if gene table already exists if not create table
	if (! hasTable(geneTable)){
	    
	    // prepare command
	    String createTabelCmd = "create table " + geneTable
		    + " (gene VARCHAR(60) not NULL, "
		    + "geneInfo TEXT, "
		    + "PRIMARY KEY ( gene ))";
	    
	    // execute command
	    execute(createTabelCmd);
	}
	
	
	
	////////////////
	//// create db entry
	
	// prepare command 
	String addEntryCmd = "REPLACE INTO " + geneTable + " VALUES " 
		+ "( " + "'" + geneName + "'" + ", " + "'" + GeneInfo + "'" + " )";
	execute(addEntryCmd);
		
	
	
	
	
    }
    
    
    
    
    
    
    
    //////////////////
    //// get entry from DB
    
    public String getGeneEntry(String geneName){
	
	// return string
	String geneInfo = null;
	
	// check connection
	if ( conn == null || ! isConnected()) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return null;
	}
	
	
	
	// prepare query command
	String queryGeneInfoCmd = "select * from " + geneTable
		+ " where gene == '" + geneName + "'";

	// execute query
	ResultSet rs = execute(queryGeneInfoCmd);

	
	
	// check if there are results if so retrieve results
	try {
	    if (rs.next()) {
		geneInfo = rs.getString("geneInfo");
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	return geneInfo;
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    ///////////
    //// execute DB command
    private ResultSet execute(String cmd) {
	
	ResultSet rs = null;
	try {
	    
	    Statement stmt = conn.createStatement();
	    stmt.execute(cmd);
	    rs = stmt.getResultSet();
	    
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	return rs;
	
    }
    
    
    
    /////////////
    //// check DB connection
    public boolean isConnected() {
	
	boolean isConnected = true;
	
	// ceck DB connection
	try {
	
	    if (conn.isClosed()) {
		isConnected = false;
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}

	return isConnected;
	
    }
    
    
    
    
    
    //////////////////
    //// check if table exists
    
    private boolean hasTable (String tableName) {
	
	boolean exists = false;
	
	// check if connected DB has table of interest
	try {
	    DatabaseMetaData meta = conn.getMetaData();
	    ResultSet tables = meta.getTables(null, null, tableName, null);
	
	    if (tables.next()){
		exists = true;
	    }
	    
	    
	    
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	return exists;
	
    }
    
    
    
    
    
    
    
    
    
    
    ////////////////
    //// create new DB
    
    public File createNewDB() {
	
	//////// get DB name
	
	// read DB name from config
	Config config = new Config();
	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database files (*.db)", "*.db"));


	// check if db has entry in config and load opener correspoingly
	if (new File(config.getDbPath()).exists()) {
	    chooser.setInitialDirectory(new File(config.getDbPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	// choos file to open if aborded, chosen chose new file
	File dbFile = chooser.showSaveDialog(new Stage());
	
	// check for correct ending
	String extension = dbFile.getName().substring(dbFile.getName().lastIndexOf(".") + 1);
	if (! extension.equals(".db")) {
	    String fileName = dbFile.getAbsolutePath() + ".db";
	    dbFile = new File(fileName);
	}

	
	// save path in config
	if (dbFile != null){
	    config.setDbFullPath(dbFile.getAbsolutePath());
	} else {
	    
	    return null;
	    
	}
	
	
	return dbFile;
	
    }
    
    
    
    
    
    
    
    
    //////////////////////////
    //// choose Database and remember in config.
    
    public File openDB() {
	
	
	//////// get DB name
	
	// read DB name from config
	Config config = new Config();
	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database files (*.db)", "*.db"));
	
	

	// check if db has entry in config and load opener correspoingly
	if (new File(config.getDbPath()).exists()){
	    chooser.setInitialDirectory(new File(config.getDbPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	
	// choos file to open if aborded, chosen chose new file
	File dbFile = chooser.showOpenDialog(new Stage());




	// save path in config
	if (dbFile != null){
	    config.setDbFullPath(dbFile.getAbsolutePath());
	} else {
	    
	    return null;
	    
	}
	
	
	
	return dbFile;
	
    }
    
    
    
    
    
    
    
    
    
    
    
    /////////////////
    //////// connect to DB
    public void connect(File dbFile) {
	
	
	// define DB url
	url = "jdbc:sqlite:" + dbFile.getAbsoluteFile();
	
	try {
	    // load SQL  driver
	    Class.forName(driver);
	    // connect to database
	    conn = DriverManager.getConnection(url, user, password);
	    
	    


	} catch (Exception ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	} 
	
	
	// give out if DB is connected and name of connected DB
	infoLable.setText("Connected to " + dbFile.getAbsolutePath() + " established!");
	dbInfo.setText(dbFile.getName());
	
	
    }
    
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}
