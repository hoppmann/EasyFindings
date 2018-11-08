/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.receiverDB;

import de.hoppmann.database.userDB.ConnectionBuilder;
import de.hoppmann.database.userDB.DbOperations;
import de.hoppmann.database.userDB.ConnectionHolder;
import de.hoppmann.database.userDB.interfaces.IAddressRepository;
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

    
    
    
    
    private final String receiverTable = AddressDbManager.getReceiverTable();
    private final String IdKey = AddressDbManager.getIdKey();
    private final String titleKey = AddressDbManager.getTitleKey();
    private final String nameKey = AddressDbManager.getNameKey();
    private final String addressKey = AddressDbManager.getAddressKey();
    private final String zipCodeKey = AddressDbManager.getZipCodeKey();
    private final String cityKey = AddressDbManager.getCityKey();
    private final String countryKey = AddressDbManager.getCountryKey();

    
    
    
    
    
    @Override
    public boolean makeRepoValid() {
	
	return true;
	
    }
    
    
    
    
    
    
    
    

    @Override
    public List<String> getNameList() {
    
	List<String> nameList = new LinkedList<>();

	try {
	    
	    String query = "select " + nameKey + " from " + receiverTable;
	    
	    ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
	    
	    while (rs.next()){
		nameList.add(rs.getString(nameKey));
	    }
	    
	} catch (SQLException ex) {
	    Logger.getLogger(DbAddressRepository.class.getName()).log(Level.SEVERE, null, ex);
	}
	Collections.sort(nameList, String.CASE_INSENSITIVE_ORDER);
	return nameList;
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public AddressInfo retrieveAddressInfo(String name) {

	AddressInfo aInfo = null;
    
	try {
	    
	    
	    String query = "select * from " + receiverTable + " where " + nameKey + " == '" + name + "'";
	    
	    ResultSet rs = DbOperations.execute(query, ConnectionHolder.getInstance().getConnection());
	    
	    
	    if (rs.next()){
		int id = Integer.parseInt(rs.getString(IdKey));
		String title = rs.getString(titleKey);
		String address = rs.getString(addressKey);
		String zipCode = rs.getString(zipCodeKey);
		String city = rs.getString(cityKey);
		String country = rs.getString(countryKey);
		
		aInfo = new AddressInfo(title, name, address, city, zipCode, country, id);

	    } 
	    
	} catch (SQLException ex) {
	    Logger.getLogger(DbAddressRepository.class.getName()).log(Level.SEVERE, null, ex);
	}

	
	return aInfo;
	
    }

    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    @Override
    public void setAddressInfoFromUI() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    
    
    
    
    
    
    
    
    
    @Override
    public boolean isValidRepo() {
	
	boolean isValid = false;
	if (ConnectionBuilder.hasConnection()) {
	
	    if (DbOperations.hasTable(receiverTable, ConnectionHolder.getInstance().getConnection())) {
		isValid = true;
	    }
	
	}

	return isValid;
    }
    
    
    









}
