/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.receiverDB;

/**
 *
 * @author hoppmann
 */
public class AddressInfo 
implements IAddressInfo{


    private int id;
    private String title;
    private String name;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    
    /////////////////////////////
    //////// Constructor ////////
    /////////////////////////////
	    
    
    public AddressInfo(String title, String name, String address, String city, String zipCode, String country, int id) {
	this.title = title;
	this.name = name;
	this.address = address;
	this.city = city;
	this.zipCode = zipCode;
	this.country = country;
	this.id = id;
    }
    
    
    
    
    @Override
    public void clearStorage() {
        title = "";
        name = "";
        address = "";
        city = "";
        zipCode = "";
        country = "";
        id = -1;
    }
    
    
    
    
    
    
    ///////////////////////////////
    //////// getter setter ////////
    ///////////////////////////////
    
    
    @Override
    public int getReceiverId() {
	return id;
    }
    
    @Override
    public void setReceiverId(int iD) {
	this.id = iD;
    }
    
    @Override
    public String getReceiverTitle() {
	return title;
    }

    @Override
    public void setReceiverTitle(String title) {
	this.title = title;
    }

    @Override
    public String getReceiverName() {
	return name;
    }

    @Override
    public void setReceiverName(String name) {
	this.name = name;
    }

    @Override
    public String getReceiverAddress() {
	return address;
    }

    @Override
    public void setReceiverAddress(String address) {
	this.address = address;
    }

    @Override
    public String getReceiverCity() {
	return city;
    }

    @Override
    public void setReceiverCity(String city) {
	this.city = city;
    }

    @Override
    public String getReceiverZipCode() {
	return zipCode;
    }

    @Override
    public void setReceiverZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    @Override
    public String getReceiverCountry() {
	return country;
    }

    @Override
    public void setReceiverCountry(String country) {
	this.country = country;
    }

    
    
    
    
    
    







}
