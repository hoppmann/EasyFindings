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
public interface IAddressInfo {
    
    public int getiD();

    public void setiD(int iD);

    public String getTitle();

    public void setTitle(String title);

    public String getName();

    public void setName(String name);

    public String getAddress();

    public void setAddress(String address);

    public String getCity();
    
    public void setCity(String city);

    public String getZipCode();

    public void setZipCode(String zipCode);

    public String getCountry();

    public void setCountry(String country);

    
}
