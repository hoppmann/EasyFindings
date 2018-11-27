/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.modelsAndData;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class TableData {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private Boolean causal = false;
    private LinkedList<String> inputLine;
    private String catagory;
    private String geneName;
    private String impact;
    private String cNom;
    private String rsId;
    
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public TableData(LinkedList<String> line) {
	
	this.inputLine = line;
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

	
    // hand back a split list of chosen entry
    public List<String> getSplitEntry(int i, String delim) {
	
	List<String> entryList = new LinkedList<>();
	
	// get entry of choise and split
	
	if (i >= 0){
	    entryList.addAll(Arrays.asList(inputLine.get(i).split(delim)));
	}
	return entryList;
	
    }
    
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////
    

    public Boolean isCausal() {
	return causal;
    }

    public void setCausal(Boolean causal) {
	this.causal = causal;
    }

    public LinkedList<String> getInputLine() {
	return inputLine;
    }

    public void setInputLine(LinkedList<String> inputLine) {
	this.inputLine = inputLine;
    }

    public String getCatagory() {
	return catagory;
    }

    public void setCatagory(String catagory) {
	this.catagory = catagory;
    }

    public String getEntry(int i) {
	return this.inputLine.get(i);
    }
    


}
