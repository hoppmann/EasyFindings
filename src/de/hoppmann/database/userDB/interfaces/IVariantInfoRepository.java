/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.interfaces;

import java.util.List;

/**
 *
 * @author hoppmann
 */
public interface IVariantInfoRepository {
    
    
    public boolean isRepoValid();
    public boolean makeRepoValid();
    public List<String> getVariantList(String geneName);
    public String getVariantInfo(String geneName, String varName);
    public void saveVariant(String geneName, String varName, String varInfo);
    public void removeVariant(String geneName, String varName);
    
    
    
}
