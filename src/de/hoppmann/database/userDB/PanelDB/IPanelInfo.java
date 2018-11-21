/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.hoppmann.database.userDB.PanelDB;

import java.util.Set;

/**
 *
 * @author hoppmann
 */
public interface IPanelInfo {
    
    
    public String getPanelName();

    public void setPanelName(String panelName);

    public Set<String> getGeneList();

    public String getGeneListAsString();
    
    public void setGeneList(Set<String> geneList);
    
    public void setGeneList(String geneListString);
    
    
    
}
