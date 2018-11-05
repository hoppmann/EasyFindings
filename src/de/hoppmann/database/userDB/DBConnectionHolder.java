/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import java.sql.Connection;

/**
 *
 * @author hoppmann
 */
public class DBConnectionHolder {



    private static DBConnectionHolder instance = new DBConnectionHolder();
    
    public static DBConnectionHolder getInstance() {
	return instance;
    }
    
    
    
    
    
    private Connection connection;
    
    public Connection getConnection() {
	if (connection == null){
	    throw new IllegalArgumentException("Class  not properly initialized!");
	}
	return connection;
    }

    
    public void setConnection(Connection connection) {
	this.connection = connection;
    }

    
    
    
    
    public void initConnection (Connection c) {
	connection = c;
    }
    
    

}
