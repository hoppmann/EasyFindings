/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.PanelDB;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author hoppmann
 */
public class PanelInfo implements IPanelInfo{

    private String panelName;
    private Set<String> geneList = new TreeSet<>();

    public PanelInfo(String panelName) {
        this.panelName = panelName;
    }

    
    
    

    @Override
    public String getPanelName() {
        return panelName;
    }

    @Override
    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    @Override
    public Set<String> getGeneList() {
        return geneList;
    }

    @Override
    public String getGeneListAsString(){
        String panelListString = String.join("\n", geneList);
        return panelListString;
    }
    
    @Override
    public void setGeneList(Set<String> geneList) {
	Set<String> newSet = new TreeSet<>();
	for (String curGenen : geneList){
	    curGenen = curGenen.replaceAll("\\s", "");
	    curGenen = curGenen.toUpperCase();
	    newSet.add(curGenen);
	}
	       
                
        newSet.removeAll(Arrays.asList(""));
        this.geneList = newSet;
    }
    
    @Override
    public void setGeneList(String geneListString){
        Set<String> geneList = new TreeSet<>(Arrays.asList(geneListString.split("\n")));
        setGeneList(geneList);
    }

    
    
    
    
    
    
    
    
    
    


}
