/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.receiverDB;

import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.DbOperations;
import java.sql.ResultSet;

/**
 *
 * @author hoppmann
 */
public class AddressDbManager {


    private static final String RECEIVER_TABLE = "receiver";
    private static final String ID_KEY = "ID";
    private static final String TITLE_KEY = "Title";
    private static final String NAME_KEY = "Full_name";
    private static final String ADDRESS_KEY = "Postal_address";
    private static final String ZIP_CODE_KEY = "ZIP_code";
    private static final String CITY_KEY = "City";
    private static final String COUNTRY_KEY = "Countries";

    
    
    
    public boolean hasReceiverTable() {
	
	boolean hasTable = false;
	
	if (DbOperations.hasTable(RECEIVER_TABLE, ConnectionHolder.getInstance().getConnection())){
	    hasTable = true;
	}
	
	
	return hasTable;
	
	
    }
    
    
    
    
    
    
    
    
    
    
    
    
    public boolean createReceiverTable() {
	
	boolean success = false;

	// prepare command
	String createTableCmd = "CREATE TABLE " + RECEIVER_TABLE + "( "
		+ ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
		+ TITLE_KEY + " Varchar(60), "
		+ NAME_KEY + " Varchar(60) not null, "
		+ ADDRESS_KEY + " Varchar(60), "
		+ ZIP_CODE_KEY + " Varchar(60), "
		+ CITY_KEY + " Varchar(60), "
		+ COUNTRY_KEY + " Varchar(60) )";
    
	ResultSet rs = DbOperations.execute(createTableCmd, ConnectionHolder.getInstance().getConnection());
	
	if (hasReceiverTable()){
	    success = true;
	}
	
	return success;
	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    public boolean hasTableEntries() {
	
	boolean tableHasEntry = false;
	
	if (DbOperations.tableHasEntry(RECEIVER_TABLE, ConnectionHolder.getInstance().getConnection())) {
	    tableHasEntry = true;
	}
	
	return tableHasEntry;
		
	
	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    public static String getReceiverTable() {
	return RECEIVER_TABLE;
    }

    public static String getIdKey() {
	return ID_KEY;
    }

    public static String getTitleKey() {
	return TITLE_KEY;
    }

    public static String getNameKey() {
	return NAME_KEY;
    }

    public static String getAddressKey() {
	return ADDRESS_KEY;
    }

    public static String getZipCodeKey() {
	return ZIP_CODE_KEY;
    }

    public static String getCityKey() {
	return CITY_KEY;
    }

    public static String getCountryKey() {
	return COUNTRY_KEY;
    }
    
    

    
    


}
