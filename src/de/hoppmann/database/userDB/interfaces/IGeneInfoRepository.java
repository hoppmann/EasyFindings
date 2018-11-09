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
public interface IGeneInfoRepository {
    
    public boolean isRepoValid();
    public boolean makeRepoValid();
    public List<String> getGeneList();
    public VariantInfo getGeneInfo(VariantInfo varInfo);
    public void saveGene(VariantInfo varInfo);
    public void removeGene(VariantInfo varInfo);
    
    
    
    
    
}
