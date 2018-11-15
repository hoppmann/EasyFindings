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
    
    
    
    
    
    
    
    
    ///////////////////////////////
    //////// getter setter ////////
    ///////////////////////////////
    
    
    @Override
    public int getiD() {
	return id;
    }
    
    @Override
    public void setiD(int iD) {
	this.id = iD;
    }
    
    @Override
    public String getTitle() {
	return title;
    }

    @Override
    public void setTitle(String title) {
	this.title = title;
    }

    @Override
    public String getName() {
	return name;
    }

    @Override
    public void setName(String name) {
	this.name = name;
    }

    @Override
    public String getAddress() {
	return address;
    }

    @Override
    public void setAddress(String address) {
	this.address = address;
    }

    @Override
    public String getCity() {
	return city;
    }

    @Override
    public void setCity(String city) {
	this.city = city;
    }

    @Override
    public String getZipCode() {
	return zipCode;
    }

    @Override
    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    @Override
    public String getCountry() {
	return country;
    }

    @Override
    public void setCountry(String country) {
	this.country = country;
    }
    
    
    
    
    
    







}
