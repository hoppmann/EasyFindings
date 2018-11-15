/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

import de.hoppmann.database.userDB.interfaces.IConnectDB;

/**
 *
 * @author hoppmann
 */
public class ConnectGeneInfoSQLite implements IConnectDB{
    
    
    private final String driver = "org.sqlite.JDBC";

    
    @Override
    public boolean connect(String dbPath, String user, String password) {
        String url = "jdbc:sqlite:" + dbPath;

        
        GeneInfoDbConnectionBuilder.openConnection(url, user, password, driver);

        if (GeneInfoDbConnectionBuilder.hasConnection()){
            return true;
        } else {
            return false;
        }    }
}
