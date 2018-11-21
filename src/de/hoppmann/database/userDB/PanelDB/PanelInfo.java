/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.database.userDB.PanelDB;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class PanelInfo {

    private String panelName;
    private List<String> geneList = new LinkedList<>();
    private int id;

    public PanelInfo(String panelName, int id) {
        this.panelName = panelName;
        this.id = id;
    }

    
    
    

    public String getPanelName() {
        return panelName;
    }

    public void setPanelName(String panelName) {
        this.panelName = panelName;
    }

    public List<String> getGeneList() {
        return geneList;
    }

    public String getGeneListAsString(){
        String panelListString = String.join("\n", geneList);
        return panelListString;
    }
    
    public void setGeneList(List<String> geneList) {
        this.geneList = geneList;
    }
    
    public void setGeneList(String geneListString){
        List<String> geneList = Arrays.asList(geneListString.split("\n"));
        this.geneList = geneList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
    
    
    
    
    
    
    
    


}
