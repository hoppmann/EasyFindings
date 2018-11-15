/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.receiverDB;

import java.util.List;

/**
 *
 * @author hoppmann
 */
public interface IAddressRepository {
    
    public List<String> getNameList();
    public boolean retrieveAddressInfo(IAddressInfo aInfo);
    public boolean isValidRepo();
    public boolean makeRepoValid();
    public void newAddress(IAddressInfo aInfo);
    public void saveAddress(IAddressInfo aInfo);
    public void removeAddress(IAddressInfo aInfo);
    
}
