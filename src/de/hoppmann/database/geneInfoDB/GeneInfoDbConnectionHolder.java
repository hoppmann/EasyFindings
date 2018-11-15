/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

import java.sql.Connection;

/**
 *
 * @author hoppmann
 */
public class GeneInfoDbConnectionHolder {

    private static GeneInfoDbConnectionHolder instance = new GeneInfoDbConnectionHolder();
    
    public static GeneInfoDbConnectionHolder getInstance() {
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
