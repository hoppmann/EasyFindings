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
    
    public int getReceiverId();

    public void setReceiverId(int Id);

    public String getReceiverTitle();

    public void setReceiverTitle(String title);

    public String getReceiverName();

    public void setReceiverName(String name);

    public String getReceiverAddress();

    public void setReceiverAddress(String address);

    public String getReceiverCity();
    
    public void setReceiverCity(String city);

    public String getReceiverZipCode();

    public void setReceiverZipCode(String zipCode);

    public String getReceiverCountry();

    public void setReceiverCountry(String country);

    
}
