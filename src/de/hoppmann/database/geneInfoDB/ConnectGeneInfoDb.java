/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

import java.io.File;

/**
 *
 * @author hoppmann
 */
public class ConnectGeneInfoDb {

    
    
    
    
    // get current path of program
    private  static String curDir = System.getProperty("user.dir");
	
    
        
        
    // geneInfoDB variables
    private static final String dbName = "geneInfos.db";

    
    
    public static boolean connectGeneInfoDbSqLite(File dbFile, String user, String password) {
        boolean success = ConnectGeneInfoSQLite.connect(dbFile.getAbsolutePath(), user, password);
	return success;

    }
    
    
    
    
    
    
    public static boolean connectGeneInfoDbSqLite(String user, String password) {
        
        File dbFile = new File(curDir + File.separator + "DBs" + File.separator + dbName);
        boolean succsess = connectGeneInfoDbSqLite(dbFile, user, password);
        return succsess;
    }

    
    
    
    
    
    public static boolean connectGeneInfoDbSqLite(File dbFile) {
        boolean succsess = connectGeneInfoDbSqLite(dbFile, "", "");
        return succsess;
    }
    
    
    
    
    
    public static boolean connectGeneInfoDbSqLite() {
	File dbFile = new File(curDir + File.separator + "DBs" + File.separator + dbName);

        boolean success = connectGeneInfoDbSqLite(dbFile, "", "");
        return success;
    }
    

    
    public static void close() {
	ConnectGeneInfoSQLite.close();
    }
    
    
    

}
