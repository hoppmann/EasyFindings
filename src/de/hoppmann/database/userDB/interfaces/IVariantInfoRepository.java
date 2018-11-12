/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.interfaces;

import de.hoppmann.database.userDB.snipletDB.VariantInfo;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public interface IVariantInfoRepository {
    
    
    public boolean isRepoValid();
    public boolean makeRepoValid();
    public List<String> getVariantList(VariantInfo varInfo);
    public VariantInfo getVariantInfo(VariantInfo varInfo);
    public void saveVariant(VariantInfo varInfo);
    public void removeVariant(VariantInfo varInfo);
    
    
    
}
