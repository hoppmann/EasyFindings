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
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Connection con = null;
    private String driver = "org.sqlite.JDBC";
    private File file;
    private String dbPath;
    private String geneTable = "genes";
    
    
    // connect to db
    private String url;
    private String user = "";
    private String password = "";
    
    
    
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public GeneDB () {
	
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    //////////////////
    //// save entry in DB
    
    public void saveGene(String geneName, String GeneInfo) {
	
	System.out.println(hasTable(geneTable));
	
	
	
	
	
	
	
    }
    
    
    
    
    
    ////////////////
    //// test connection
    
    
    
    
    
    
    
    
    
    
    //////////////////
    //// get entry from DB
    
    
    
    
    
    
    
    
    
    
    
    
    //////////////////
    //// check if table exists
    
    private boolean hasTable (String tableName) {
	
	boolean exists = false;
	
	try {
	    DatabaseMetaData meta = con.getMetaData();
	    ResultSet tables = meta.getTables(null, null, tableName, null);
	
	    if (tables.next()){
		exists = true;
	    }
	    
	    
	    
	} catch (SQLException ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	System.out.println("HALLO");
	
	
	return exists;
	
    }
    
    
    
    
    
    
    
    
    
    
    ////////////////
    //// create new DB
    
    public Connection createNewDB() {
	
	//////// get DB name
	
	// read DB name from config
	Config config = new Config();
	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file");

	// check if db has entry in config and load opener correspoingly
	if (new File(config.getDbPath()).exists()) {
	    chooser.setInitialDirectory(new File(config.getDbPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	// choos file to open if aborded, chosen chose new file
	file = chooser.showSaveDialog(new Stage());
	
	// save path in config
	if (file != null){
	    config.setDbPath(file.getParent());
	} else {
	    
	    return null;
	    
	}
	
	
	
	
	
	
	
	//////// connect to DB
	
	// define DB url
	url = "jdbc:sqlite:" + file.getAbsoluteFile();
	
	try {
	    // load SQL  driver
	    Class.forName(driver);
	    // connect to database
	    con = DriverManager.getConnection(url, user, password);
	


	} catch (Exception ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	} 
	
	
	
	
	
	return con;

	
    }
    
    
    
    
    
    
    
    
    
    
    
    //////////////////////////
    //// choose Database and remember in config.
    
    public Connection openDB() {
	
	
	//////// get DB name
	
	// read DB name from config
	Config config = new Config();
	FileChooser chooser = new FileChooser();
        chooser.setTitle("Open input file");

	// check if db has entry in config and load opener correspoingly
	if (new File(config.getDbPath()).exists()) {
	    chooser.setInitialDirectory(new File(config.getDbPath()));
	} else {
	    chooser.setInitialDirectory(null);
	}
	
	// choos file to open if aborded, chosen chose new file
	file = chooser.showOpenDialog(new Stage());
	
	// save path in config
	if (file != null){
	    config.setDbPath(file.getParent());
	} else {
	    
	    return null;
	    
	}
	
	
	
	
	
	
	
	//////// connect to DB
	
	// define DB url
	url = "jdbc:sqlite:" + file.getAbsoluteFile();
	
	try {
	    // load SQL  driver
	    Class.forName(driver);
	    // connect to database
	    con = DriverManager.getConnection(url, user, password);
	


	} catch (Exception ex) {
	    Logger.getLogger(GeneDB.class.getName()).log(Level.SEVERE, null, ex);
	} 
	
	
	
	
	
	return con;
	
	
    }
    
    
    
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////










}
