/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB;

import de.hoppmann.config.Config;
import java.io.File;

/**
 *
 * @author hoppmann
 */
public class ConnectUserDB {

    
    
    
   public static boolean connectSqLiteUserDB(){
        boolean success = connectSqLiteUserDB("", "");
        return success;
    }

    
    
    
    
    public static boolean connectSqLiteUserDB (String dbPath) {
	
	boolean success = connectSqLiteUserDB(dbPath, "", "");
	return success;
    }


    
    
    
    public static boolean connectSqLiteUserDB(String user, String password) {
        Config config = Config.getInstance();
	
	boolean success = false;
        if (config.getDbFullPath() != null ){
            success = connectSqLiteUserDB(config.getDbFullPath(), user, password);
        }
        return success;
    }



    



    
    
    public static boolean connectSqLiteUserDB(String dbPath, String user, String password){
        boolean success = false;
	if (dbPath != null){
	    success = ConnectSQLite.connect(dbPath, user, password);
        }
        return success;

    }
    
    
    
    public static void closeDB() {
	ConnectSQLite.close();
    }
    
    
    
    
    
    
    
}





    
