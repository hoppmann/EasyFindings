/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.interfaces;

import de.hoppmann.database.userDB.receiverDB.AddressInfo;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public interface IAddressRepository {
    
    public List<String> getNameList();
    public AddressInfo retrieveAddressInfo(String name);
    public void setAddressInfoFromUI();
    public boolean isValidRepo();
    public boolean makeRepoValid();
    
}
