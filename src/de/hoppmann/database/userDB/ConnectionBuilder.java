/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class ConnectionBuilder {


    
    
    /**
     * 
     * @param url
     * @param user
     * @param password
     * @param driver 
     * 
     * create connection to database
     */
   public void openConnection (String url, String user, String password, String driver) {
       Connection conn = null;
       
	try {
	   if (conn != null) {
	       conn.close();
	   }

	conn = DriverManager.getConnection(url, user, password);
	   
	   
	   
	   
	   
	} catch (SQLException ex) {
	   Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
	}


       // store conncetion
	DBConnectionHolder.getInstance().initConnection(conn);
       
       
   }
    
    
    
    
   /**
    * close connection to current database
    */
   public void closeConnection() {
       try {
	   Connection conn = DBConnectionHolder.getInstance().getConnection();
	   conn.close();
	   DBConnectionHolder.getInstance().initConnection(null);
       } catch (SQLException ex) {
	   Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
       }
   }
    

   
   
   /**
    * check if connection to DB is set up
    */
   public boolean hasConnection() {
       boolean hasConnection = false;
       if (DBConnectionHolder.getInstance().getConnection() != null) {
	   return true;
       } else {
	   return false;
       }
       
   }

   
}
