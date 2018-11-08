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
public class AddressInfo {


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
	setAddressInfo(title, name, address, city, zipCode, country, id);
    }
    
    
    
    
    public void setAddressInfo(String title, String name, String address, String city, String zipCode, String country, int id){
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
    
    
    public int getiD() {
	return id;
    }

    public void setiD(int iD) {
	this.id = iD;
    }

    public String getTitle() {
	return title;
    }

    public void setTitle(String title) {
	this.title = title;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getCity() {
	return city;
    }

    public void setCity(String city) {
	this.city = city;
    }

    public String getZipCode() {
	return zipCode;
    }

    public void setZipCode(String zipCode) {
	this.zipCode = zipCode;
    }

    public String getCountry() {
	return country;
    }

    public void setCountry(String country) {
	this.country = country;
    }
    
    
    
    
    
    







}
