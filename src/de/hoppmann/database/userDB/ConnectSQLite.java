/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import de.hoppmann.database.userDB.interfaces.IConnectDB;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class ConnectSQLite implements IConnectDB {

    private final String driver = "org.sqlite.JDBC";
    
    // connect to db
    private String url;




    @Override
    public boolean connect(String dbPath, String user, String password) {
        
        url = "jdbc:sqlite:" + dbPath;

        
        ConnectionBuilder.openConnection(url, user, password, driver);

        if (ConnectionBuilder.hasConnection()){
            return true;
        } else {
            return false;
        }
    }



    
    public boolean checkValidity(List<String> tables) {

	boolean isValid = true;
	for (String curTable : tables) {
	    if (!DbOperations.hasTable(curTable, ConnectionHolder.getInstance().getConnection())) {
		
		isValid = false;
		
	    }
	}

	return isValid;
    }
    
    
    
    
    
    
    
    
    
    
    


}
