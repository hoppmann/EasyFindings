/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class ConnectSQLite{

    private static final String driver = "org.sqlite.JDBC";
    



    public static boolean connect(String dbPath, String user, String password) {
        
       String url = "jdbc:sqlite:" + dbPath;
        
        ConnectionBuilder.openConnection(url, user, password, driver);

        if (ConnectionBuilder.hasConnection()){
            return true;
        } else {
            return false;
        }
    }



    
    public static boolean checkValidity(List<String> tables) {

	boolean isValid = true;
	for (String curTable : tables) {
	    if (!DbOperations.hasTable(curTable, ConnectionHolder.getInstance().getConnection())) {
		
		isValid = false;
		
	    }
	}

	return isValid;
    }
    
    
    
    
    public static void close() {
        Connection conn = ConnectionHolder.getInstance().getConnection();
        if (conn != null) {
            try {

                conn.close();
                ConnectionHolder.getInstance().setConnection(null);
            } catch (SQLException ex) {
                Logger.getLogger(ConnectionBuilder.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    
    
    
    
    
    
    


}
