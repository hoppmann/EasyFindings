/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author hoppmann
 */
public class Database {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
//    private Config config = Config.getInstance();
    protected Connection conn = null;
    private final String driver = "org.sqlite.JDBC";
    private String dbPath;
    
    // connect to db
    private String url;
    private final String user = "";
    private final String password = "";
    
	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    
    
    
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    



    ///////////
    //// execute DB command
    public ResultSet execute(String cmd) {
	// check  if DB is connected
	if (isConnected() == false){
	    return null;
	}
        
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
	    if (conn == null || conn.isClosed()) {
		isConnected = false;
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}

	return isConnected;
	
    }
    
    
	
    
    
    //////////////////
    //// check if table exists
    
    public boolean hasTable (String tableName) {
	
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
	
	
    
    
    
    //////////////////////////////
    //// chekc if DB has any entry for a given table
    
    public boolean tableHasEntry (String tableName) {
        
        boolean hasEntry = false;
        
        // prepare query
        String query = "select * from " + tableName;
        
        // get results
        ResultSet rs = execute(query);
        
        // check if there are any results. if so set boolean to true
        try {
            if (rs.isBeforeFirst()) {
                hasEntry = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hasEntry;
        
    }
    
    
    
    
    
     ////////////////
    //// create new DB
    
    public File createNewDB(String dbPath) {
	
	//////// get DB name
	
	// read DB name from config
	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open DB file");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database files (*.db)", "*.db"));


	// check if a db path if given, if so check for existance and load opener correspondingly
	if (dbPath != null && new File(dbPath).exists()) {
	    chooser.setInitialDirectory(new File(dbPath));
	} else {
	    chooser.setInitialDirectory(null);
	}
	

	// choos file to open if aborded, chosen chose new file
	File dbFile = chooser.showSaveDialog(new Stage());
	
	
	if (dbFile != null){

	    // check for correct ending
	    String extension = dbFile.getName().substring(dbFile.getName().lastIndexOf(".") + 1);
	    if (!extension.equals("db")) {
		String fileName = dbFile.getAbsolutePath() + ".db";
		dbFile = new File(fileName);
	    }
	    

	    // catch cancel
	} else {
	    return null;
	}
	
	
	return dbFile;
	
    }
    
    
    
    
    
    //////////////////////////
    //// choose Database and remember in config.
    
    public File openDB(String dbPath) {
	
	
	
	//////// get DB name
	
	// read DB name from config
	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open gene database");
	chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Database files (*.db)", "*.db"));
	
	if (dbPath != null && new File(dbPath).exists()) {
	    chooser.setInitialDirectory(new File(dbPath));
	} else {
	    chooser.setInitialDirectory(null);
	}


	// choos file to open if aborded, chosen chose new file
	File dbFile = chooser.showOpenDialog(new Stage());


	return dbFile;
	
    }
    
    
    
    
    
    
    
    /////////////////
    //////// connect to DB
    public Connection connect(File dbFile) {
	
	
	// define DB url
	if (dbFile == null) {
	    return conn;
	}
	url = "jdbc:sqlite:" + dbFile.getAbsoluteFile();
	
	try {
	    
	    // if there allread is an connection close it
	    if (conn != null){
		conn.close();
            }
            // load SQL  driver
            Class.forName(driver);

            // connect to database
            conn = DriverManager.getConnection(url, user, password);

            
        } catch (Exception ex) {
            Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	} 
	
	
	// return opend connection
	return conn;
    }

    
    
    
    
    /////////////////////////
    //////// close connection
    public void closeDB(){
        
	if (conn != null) {

	    try {
		conn.close();
	    } catch (SQLException ex) {
		Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	    }
	}
    }
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}
