/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import de.hoppmann.config.Config;
import de.hoppmann.database.userDB.interfaces.IConnectDB;
import java.io.File;

/**
 *
 * @author hoppmann
 */
public class ConnectUserDB {

    Config config = Config.getInstance();
    private IConnectDB connectDB;

    public ConnectUserDB(IConnectDB connectDB) {
        this.connectDB = connectDB;
    }


    
    
    
    
    public boolean connectSqLiteUserDB(){
        boolean success = connectSqLiteUserDB("", "");
        return success;
    }

    
    


    
    public boolean connectSqLiteUserDB(String user, String password) {
        boolean success = false;
        if (config.getDbFullPath() != null && new File(config.getDbFullPath()).exists()){
            success = connectSqLiteUserDB(config.getDbFullPath(), user, password);
        }
        return success;
    }






    
    
    public boolean connectSqLiteUserDB(String dbPath, String user, String password){
        boolean success = false;
	if (dbPath != null){
	    success = connectDB.connect(dbPath, user, password);
        }
        return success;

    }
    
    
    
    
    
    
}





    
