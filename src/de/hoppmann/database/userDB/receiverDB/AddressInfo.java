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
    private String organisation;
    private String institute;
    private String address;
    private String city;
    private String zipCode;
    private String country;

    
    /////////////////////////////
    //////// Constructor ////////
    /////////////////////////////
	    
    
    public AddressInfo(int id, String title, String name, String organisation, String institute, String address, String city, String zipCode, String country) {
	this.id = id;
	this.title = title;
	this.name = name;
	this.organisation = organisation;
	this.institute = institute;
	this.address = address;
	this.city = city;
        this.zipCode = zipCode;
        this.country = country;
    }

    public AddressInfo() {
        clearStorage();
    }
    

    
    
    
    
    
    
    
    
    
    @Override
    public void clearStorage() {
        id = -1;
        title = "";
        name = "";
        organisation = "";
        institute = "";
        address = "";
        city = "";
        zipCode = "";
        country = "";
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

    @Override
    public String getReceiverOrganisation() {
        return organisation;
    }

    @Override
    public void setReceiverOrganisation(String organisation) {
        this.organisation = organisation;
    }

    @Override
    public String getReceiverInstitute() {
        return institute;
    }

    @Override
    public void setReceiverInstitute(String institute) {
        this.institute = institute;
    }

    
    
    
    
    
    







}
