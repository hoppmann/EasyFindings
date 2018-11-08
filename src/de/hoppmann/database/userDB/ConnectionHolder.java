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
public class ConnectionHolder {



    private static ConnectionHolder instance = new ConnectionHolder();
    
    public static ConnectionHolder getInstance() {
	return instance;
    }
    
    
    
    
    
    private Connection connection = null;
    
    public Connection getConnection() {
	return connection;
    }

    
    public void setConnection(Connection connection) {
	this.connection = connection;
    }

    

}
