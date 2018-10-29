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
public class ConnectionBuilder {

    public void openConnection(String url, String user, String pass, String driver) {
	Connection c = null; // TODO 
	// .....
	DbConnectionHolder.getInstance().initConnection(c);
    }

    public void closeConnection() throws SQLException {
	Connection c = DbConnectionHolder.getInstance().getConnection();
	c.close();
	DbConnectionHolder.getInstance().initConnection(null);
    }

}
