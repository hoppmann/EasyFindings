/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.operations.testmatthias.dbaccess;

import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author hoppmann
 */
public class DbConnectionHolder {

    
    private static DbConnectionHolder instance = new DbConnectionHolder();

    public static DbConnectionHolder getInstance() {
	return instance;
    }
    
    
    private Connection connection;

    public Connection getConnection() {
	if (connection == null) {
	    throw new IllegalArgumentException("class not properly initialized");
	}
	return connection;
    }
    
    public void initConnection(Connection c) {
	connection = c;
    }

}
