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
public class DbAddressRepository implements IAddressRepository {

    
    private static final String RECEIVER_TABLE = UserDbNamings.RECEIVER_TABLE;
    private static final String ID_KEY = UserDbNamings.ADDRESS_ID_KEY;
    private static final String TITLE_KEY = UserDbNamings.TITLE_KEY;
    private static final String NAME_KEY = UserDbNamings.NAME_KEY;
    private static final String ORGANISATION_KEY = UserDbNamings.ORGANISATION_KEY;
    private static final String INSTITUTE_KEY = UserDbNamings.INSTITUTE_KEY;
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
	    Logger.getLogger(DbAddressRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	Collections.sort(nameList, String.CASE_INSENSITIVE_ORDER);
                
	return nameList;
    }

    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean retrieveAddressInfo(IAddressInfo aInfo) {

	try {
	    if (aInfo.getReceiverName() != null && ! aInfo.getReceiverName().equals("")){
                String query = "select * from " + RECEIVER_TABLE + " where " + NAME_KEY + " == '" + aInfo.getReceiverName() + "'";

                ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
                if (rs != null) {
                    if (rs.next()) {
                        aInfo.setReceiverId(Integer.parseInt(rs.getString(ID_KEY)));
                        aInfo.setReceiverTitle(rs.getString(TITLE_KEY));
                        aInfo.setReceiverAddress(rs.getString(ADDRESS_KEY));
                        aInfo.setReceiverOrganisation(rs.getString(ORGANISATION_KEY));
                        aInfo.setReceiverInstitute(rs.getString(INSTITUTE_KEY));
                        aInfo.setReceiverZipCode(rs.getString(ZIP_CODE_KEY));
                        aInfo.setReceiverCity(rs.getString(CITY_KEY));
                        aInfo.setReceiverCountry(rs.getString(COUNTRY_KEY));
                    }
                }
	    } else {
                return false;
            }
                
	} catch (SQLException ex) {
	    Logger.getLogger(DbAddressRepository.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	return true;
	
    }

    
    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean isValidRepo() {
	
	return hasReceiverTable();
    }
    
    
    
    
    
    
    @Override
    public boolean makeRepoValid() {
	
	boolean success = false;
        // prepare command
        String createTableCmd = "CREATE TABLE " + RECEIVER_TABLE + "( "
                + ID_KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + TITLE_KEY + " Varchar(60), "
                + NAME_KEY + " Varchar(60) not null, "
                + ORGANISATION_KEY + " VARCHAR(60), "
                + INSTITUTE_KEY + " VARCHAR(60), "
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
		+ " (" + TITLE_KEY + ", " + NAME_KEY + ", " + ORGANISATION_KEY + ", " + INSTITUTE_KEY + ", " + ADDRESS_KEY + ", " + ZIP_CODE_KEY
		 + ", " + CITY_KEY + ", " + COUNTRY_KEY + ") " 
		+ " VALUES ( '" + aInfo.getReceiverTitle() + "', '" + aInfo.getReceiverName() + "', '" + aInfo.getReceiverOrganisation() + "', '" + aInfo.getReceiverInstitute() + "', '" + aInfo.getReceiverAddress() + "', '" 
		+ aInfo.getReceiverZipCode() + "', '" + aInfo.getReceiverCity() + "', '" +  aInfo.getReceiverCountry() + "')";
        
            DbOperations.execute(insertCmd, ConnectionHolder.getInstance().getConnection());
	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void saveAddress(IAddressInfo aInfo) {
	
        
	// perpare update command
	String updateCmd = "REPLACE INTO " + RECEIVER_TABLE + " VALUES ( '" + aInfo.getReceiverId() +
		"', '" + aInfo.getReceiverTitle() + "', '" + aInfo.getReceiverName() + "', '" + aInfo.getReceiverOrganisation() + "', '" + aInfo.getReceiverInstitute() + "', '" + aInfo.getReceiverAddress() + "', '" +
		aInfo.getReceiverZipCode() + "', '" + aInfo.getReceiverCity() + "', '" +  aInfo.getReceiverCountry() + "')";

            DbOperations.execute(updateCmd, ConnectionHolder.getInstance().getConnection());
    }

    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void removeAddress(IAddressInfo aInfo) {
	
	// prepare cmd
	String removeCmd = "DELETE FROM " + RECEIVER_TABLE + " WHERE " + ID_KEY + 
		" == '" + aInfo.getReceiverId() + "'";

            ResultSet rs = DbOperations.execute(removeCmd, ConnectionHolder.getInstance().getConnection());
	
    }
    
    
    
    
    
    
    
    
	







}
