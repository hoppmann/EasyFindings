/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import de.hoppmann.operations.Database;
import java.io.File;
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
public class ReceiverDB extends Database {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    private String dbName = "address.db";
    private File dbFile;
    private final String receiverTable = "receiver";
	
    private final String titleKey = "Title";
    private String title; 
    private final String fullNameKey = "Full_name";
    private String fullName;
    private final String addressKey = "Postal_address";
    private String address;
    private final String zipCodeKey = "ZIP_code";
    private String zipCode;
    private final String cityKey = "City";
    private String city; 
    private final String countryKey = "Countries";
    private String country;
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
    
    public ReceiverDB () {
	
	// get current path of program
	String curDir = System.getProperty("user.dir");
    	dbFile = new File(curDir + File.separator + "DBs" + File.separator + dbName);

	connect(dbFile);
	
	
    }
    
    /////////////////////////
    //////// methods ////////
    /////////////////////////
    
    //////////////////////
    // query all doctors names
    
    public List<String> getNameList() {
	
	
	List<String> names = new LinkedList<>();
	
	try {
	    // prepare query
	    String query = "select " + fullNameKey + " from " + receiverTable;
	    
	    ResultSet rs = execute(query);
	    
	    // retrieve names
	    while (rs.next()){
		names.add(rs.getString(fullNameKey));
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ReceiverDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	// sort list then return list
	Collections.sort(names, String.CASE_INSENSITIVE_ORDER);
	return names;
	
    }
    
    



    //////////////////
    //// query addresses
    
    public boolean queryAddress(String receiverName) {
	
	boolean success = false;
	
	// prepare query
	
	String query = "select * from " + receiverTable + " where " + fullNameKey + " == '" + receiverName + "'";
	
	
	// execute query and gather results
	ResultSet rs = execute(query);
	
	try {
	    if (rs.next()){
		success = true;
		title = rs.getString(titleKey);
		fullName = rs.getString(fullNameKey);
		address = rs.getString(addressKey);
		zipCode = rs.getString(zipCodeKey);
		city = rs.getString(cityKey);
		country = rs.getString(countryKey);
	    }
	} catch (SQLException ex) {
	    Logger.getLogger(ReceiverDB.class.getName()).log(Level.SEVERE, null, ex);
	}
	
	
	
	return success;
	
    }
    
    
    
    
    
    
    
    
    ///////////////////////////
    //// store new receiver information
    public void storeReceiver () {

	// perpare update command
	String updateCmd = "REPLACE INTO " + receiverTable + " VALUES ( '" +
		title + "', '" + fullName + "', '" + address + "', '" +
		zipCode + "', '" + city + "', '" +  country + "')";
	
	
	execute(updateCmd);
	
	
    }
    
    
    
    ///////////////////
    //// remove dataset
    public void removeData () {
	
	// prepare cmd
	String removeCmd = "DELETE FROM " + receiverTable + " WHERE " + fullNameKey + 
		" == '" + fullName + "' AND " + cityKey + " == '" + city +"' and " +
		addressKey + " == '" + address + "'";
	
	execute(removeCmd);
	
    }    
    
    
    
    
    
    
    
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getFullName() {
	return fullName;
    }

    public void setFullName(String fullName) {
	this.fullName = fullName;
    }

    public String getPostalAddress() {
	return address;
    }

    public void setPostalAddress(String postalAddress) {
	this.address = postalAddress;
    }

    public String getZipCode() {
	return zipCode;
    }

    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }

    

    









}
