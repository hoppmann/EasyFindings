/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class DbOperations {


    /**
     * Check connection and execute DB query command 
     * @param cmd
     * @param conn
     * @return 
     */
    public static ResultSet execute(String cmd, Connection conn) {
	// check  if DB is connected
	if (conn == null){
	    return null;
	}
	

	// make foreign keys available
	String pragma = "PRAGMA foreign_keys = ON;";
	
	
        
	ResultSet rs = null;
	try {
	    Statement stmt = conn.createStatement();
	    stmt.execute(pragma);
	    stmt.execute(cmd);
	    rs = stmt.getResultSet();
	    
	} catch (SQLException ex) {
	    Logger.getLogger(DbOperations.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	return rs;
    }
    
    
    
    
    
    
    
    
    
    
    /**
     * Check if table is available in DB
     * @param tableName
     * @param conn
     * @return 
     */
    public static boolean hasTable (String tableName, Connection conn) {
	
        
	boolean exists = false;
	
	// check if connected DB has table of interest
	try {
	    DatabaseMetaData meta = conn.getMetaData();
	    ResultSet tables = meta.getTables(null, null, tableName, null);
	
	    if (tables.next()){
		exists = true;
	    }
	    
	    
	    
	} catch (SQLException ex) {
	    Logger.getLogger(DbOperations.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	return exists;
	
    }
	
	
    
    
    
    
    
    
    
    /**
     * Check if table has any entry
     * @param tableName
     * @param conn
     * @return 
     */
    public static boolean tableHasEntry (String tableName, Connection conn) {
        
        boolean hasEntry = false;
        
        // prepare query
        String query = "select * from " + tableName;
        
        // get results
        
        
        // check if there are any results. if so set boolean to true
        try {
            ResultSet rs = execute(query, conn);
            if (rs.isBeforeFirst()) {
                hasEntry = true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DbOperations.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return hasEntry;
        
    }
    
    
    
    
    
    
    
    
    
    
}
