/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.geneInfoDB;

import de.hoppmann.database.userDB.interfaces.IConnectDB;
import java.io.File;

/**
 *
 * @author hoppmann
 */
public class ConnectGeneInfoDb {

    public ConnectGeneInfoDb(IConnectDB connecteDB) {
        this.connecteDB = connecteDB;
    }

    
    
    
    
    // get current path of program
    private String curDir = System.getProperty("user.dir");
	
    
        
        
    // geneInfoDB variables
    private final String dbName = "geneInfos.db";
    private IConnectDB connecteDB;

    
    
    public boolean connectGeneInfoDbSqLite(File dbFile, String user, String password) {
        boolean success = connecteDB.connect(dbFile.getAbsolutePath(), user, password);
	return success;

    }
    
    
    
    
    
    
    public boolean connectGeneInfoDbSqLite(String user, String password) {
        
        File dbFile = new File(curDir + File.separator + "DBs" + File.separator + dbName);
        boolean succsess = connectGeneInfoDbSqLite(dbFile, user, password);
        return succsess;
    }

    
    
    
    
    
    public boolean connectGeneInfoDbSqLite(File dbFile) {
        boolean succsess = connectGeneInfoDbSqLite(dbFile, "", "");
        return succsess;
    }
    
    
    
    
    
    public boolean connectGeneInfoDbSqLite() {
	File dbFile = new File(curDir + File.separator + "DBs" + File.separator + dbName);

        boolean success = connectGeneInfoDbSqLite(dbFile, "", "");
        return success;
    }
    


}
