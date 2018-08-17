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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
    private final String driver = "org.sqlite.JDBC";
    private String dbPath;
    private final String geneTable = "genes";
    private final String geneCol = "gene";
    private final String geneInfoCol = "geneInfo";
    private final String varTable = "variants";
    private final String varCol = "var";
    private final String varInfoCol = "varInfo";
    
    
    // connect to db
    private String url;
    private final String user = "";
    private final String password = "";
    
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

    
    
    ///////////////////////////////////////
    //////// Gene level operations ////////
    ///////////////////////////////////////
    
    
    
    /////////////////
    //// remove gene entry from DB
    
    public void removeGeneEntry (String geneName) {
	
	String removeCmd;
	// remove from gene table
	if (hasTable(geneTable)) {
	    removeCmd = "DELETE FROM " + geneTable
		    + " where gene = '" + geneName + "'";
	    execute(removeCmd);
	}
	
	// remove from variant table
	if (hasTable(varTable)){
	    removeCmd = "DELETE FROM " + varTable
			+ " where gene = '" + geneName +"'";
	    execute(removeCmd);
	}
	
    }

    
    
    
    
    
    
    
    ////////////////////////////////////////////
    //////// Gene info level operations ////////
    ////////////////////////////////////////////
    
    
    
    //////////////////
    //// save gene entry in DB
    
    public void saveGeneInfo(String geneName, String GeneInfo) {

	// check connection
	if ( conn == null || ! isConnected()) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return;
	}
	
	
	////////////////
	//// create db entry
	
	// prepare command 
	String addEntryCmd = "REPLACE INTO " + geneTable + " VALUES " 
		+ "( " + "'" + geneName + "'" + ", " + "'" + GeneInfo + "'" + " )";
	execute(addEntryCmd);
		
	
	
	//// make lable command
	infoLable.setText("Info for gene " + geneName + " stored!");
	
	
	
    }
    
    

    
	    
	    
    
    /////////////////
    //// get all entered genes
    public List getGeneList() {
	
	List<String> geneList = new LinkedList<>();
	
	// prepare query to get all genes
	String query = "SELECT " + geneCol + " FROM " + geneTable;
	
	
	// execute query
	ResultSet rs = execute(query);
	
	
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
	if ( conn == null || ! isConnected()) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return null;
	}
	
	
	
	// prepare query command
	String queryGeneInfoCmd = "select "+ geneInfoCol + " from " + geneTable
		+ " where gene == '" + geneName + "'";

	// execute query
	ResultSet rs = execute(queryGeneInfoCmd);

	
	
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
	if (hasTable(varTable)) {
	    // prepare deletion query
	    delQuery = "DELETE from " + varTable + " WHERE "
		    + geneCol + " = '" + geneName + "' AND " + varCol
		    + "= '" + varName + "'";

	    // execute query
	    execute(delQuery);
	    
	    infoLable.setText("Variant " + varName + " deleted.");
	}
    }
    
    
    
    
    
    ///////////////////////
    //// retrive a list of all variants for a certain gene to add to combobox
    
    public List<String> getVarList(String geneName) {
	
	
//	ObservableList<String> varList = FXCollections.observableArrayList();
	List<String> varList = new LinkedList<>();
	
	// prepare query to get all genes
	String query = "SELECT " + varCol + " FROM " + varTable + " WHERE " 
		+ geneCol + " = '" + geneName + "'";
	
	
	// execute query
	ResultSet rs = execute(query);
	
	// retrieve results
	try {
	    while (rs.next()){
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
    
    public void saveVar(String geneName, String varName, String varInfo) {
	
	
	// check connection
	if ( conn == null || ! isConnected()) {
	    infoLable.setText("ERROR: Database not connected. Open DB first.");
	    return;
	}

		// prepare command 
	String addEntryCmd = "REPLACE INTO " + varTable + " VALUES " 
		+ "( " + "'" + varName + "'" 
		+ ", '" + geneName + "'" 
		+ ", '" + varInfo + "'" +  " )";
	execute(addEntryCmd);
		
	
	
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
	
	
	ResultSet rs = execute(query);
	
	try {
	    if (rs.next()) {
		varInfo = rs.getString(varInfoCol);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	return varInfo;

    }
    
    
    
    
    
    ////////////////////////////////////
    //////// general operations ////////
    ////////////////////////////////////
    
    
    
    
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
	
	
	if (dbFile != null){

	    // check for correct ending
	    String extension = dbFile.getName().substring(dbFile.getName().lastIndexOf(".") + 1);
	    if (!extension.equals(".db")) {
		String fileName = dbFile.getAbsolutePath() + ".db";
		dbFile = new File(fileName);
	    }
	    
	    // save path in config
	    config.setDbFullPath(dbFile.getAbsolutePath());

	    // catch cancel
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
	    
	    // if there allread is an connection close it
	    if (conn != null){
		conn.close();
	    } else {
		
		// load SQL  driver
		Class.forName(driver);
		// connect to database
		conn = DriverManager.getConnection(url, user, password);
	    }
	    


	} catch (Exception ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	} 
	
	
	////////////////////////
	//////// check if tables are available else create

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
	
	
	
	// give out if DB is connected and name of connected DB
	infoLable.setText("Connected to " + dbFile.getAbsolutePath() + " established!");
	dbInfo.setText(dbFile.getName());
	
	
    }
    
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}
