/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.receiverDB;

import de.hoppmann.database.userDB.DbOperations;
import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.UserDbNamings;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author hoppmann
 */
public class AddressDbRepository implements IAddressRepository {

    
    private static final String RECEIVER_TABLE = UserDbNamings.RECEIVER_TABLE;
    private static final String ID_KEY = UserDbNamings.ADDRESS_ID_KEY;
    private static final String TITLE_KEY = UserDbNamings.TITLE_KEY;
    private static final String NAME_KEY = UserDbNamings.NAME_KEY;
    private static final String ADDRESS_KEY = UserDbNamings.ADDRESS_KEY;
    private static final String ZIP_CODE_KEY = UserDbNamings.ZIP_CODE_KEY;
    private static final String CITY_KEY = UserDbNamings.CITY_KEY;
    private static final String COUNTRY_KEY = UserDbNamings.COUNTRY_KEY;

    
    
    
  
    
    
    
    
    
    
    

    @Override
    public List<String> getNameList() {
    
	List<String> nameList = new LinkedList<>();

	try {
	    
	    String query = "select " + NAME_KEY + " from " + RECEIVER_TABLE;
	    
	    ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
	    while (rs.next()){
		nameList.add(rs.getString(NAME_KEY));
	    }
	    
	} catch (SQLException ex) {
	    Logger.getLogger(AddressDbRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	Collections.sort(nameList, String.CASE_INSENSITIVE_ORDER);
                
	return nameList;
    }

    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean retrieveAddressInfo(IAddressInfo aInfo) {

	try {
	    if (aInfo.getName() != null){
                String query = "select * from " + RECEIVER_TABLE + " where " + NAME_KEY + " == '" + aInfo.getName() + "'";

                ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
                if (rs.next()){
                    aInfo.setiD(Integer.parseInt(rs.getString(ID_KEY)));
                    aInfo.setTitle(rs.getString(TITLE_KEY));
                    aInfo.setAddress(rs.getString(ADDRESS_KEY));
                    aInfo.setZipCode(rs.getString(ZIP_CODE_KEY));
                    aInfo.setCity(rs.getString(CITY_KEY));
                    aInfo.setCountry(rs.getString(COUNTRY_KEY));
                }
	    } else {
                return false;
            }
	    
	} catch (SQLException ex) {
	    Logger.getLogger(AddressDbRepository.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	return true;
	
    }

    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean isValidRepo() {
	
	boolean isValid = hasReceiverTable();
	return isValid;
    }
    
    
    



 
    
    
    
    
    @Override
    public boolean makeRepoValid() {
	
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
    
    
    
    
    
    
    
    
    
    private boolean hasReceiverTable(){
	
	boolean hasTable = false;
	
	if (DbOperations.hasTable(RECEIVER_TABLE, ConnectionHolder.getInstance().getConnection())){
	    hasTable = true;
	}
	
	
	return hasTable;
	
	
    }
    
    
    
    
    
    
    
    
    private boolean hasTableEntry() {
	
	boolean tableHasEntry = false;
	
	if (DbOperations.tableHasEntry(RECEIVER_TABLE, ConnectionHolder.getInstance().getConnection())) {
	    tableHasEntry = true;
	}
	
	return tableHasEntry;
	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void newAddress(IAddressInfo aInfo) {
	
	// prepare query
	String insertCmd = "INSERT INTO " + RECEIVER_TABLE 
		+ " (" + TITLE_KEY + ", " + NAME_KEY + ", " + ADDRESS_KEY + ", " + ZIP_CODE_KEY
		 + ", " + CITY_KEY + ", " + COUNTRY_KEY + ") " 
		+ " VALUES ( '" + aInfo.getTitle() + "', '" + aInfo.getName() + "', '" + aInfo.getAddress() + "', '" 
		+ aInfo.getZipCode() + "', '" + aInfo.getCity() + "', '" +  aInfo.getCountry() + "')";
	DbOperations.execute(insertCmd, ConnectionHolder.getInstance().getConnection());
	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void saveAddress(IAddressInfo aInfo) {
	
        
	// perpare update command
	String updateCmd = "REPLACE INTO " + RECEIVER_TABLE + " VALUES ( '" + aInfo.getiD() +
		"', '" + aInfo.getTitle() + "', '" + aInfo.getName() + "', '" + aInfo.getAddress() + "', '" +
		aInfo.getZipCode() + "', '" + aInfo.getCity() + "', '" +  aInfo.getCountry() + "')";

	DbOperations.execute(updateCmd, ConnectionHolder.getInstance().getConnection());
    }

    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void removeAddress(IAddressInfo aInfo) {
	
	// prepare cmd
	String removeCmd = "DELETE FROM " + RECEIVER_TABLE + " WHERE " + ID_KEY + 
		" == '" + aInfo.getiD() + "'";

	ResultSet rs = DbOperations.execute(removeCmd, ConnectionHolder.getInstance().getConnection());
	
    }
    
    
    
    
    
    
    
    
	







}
