/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.modelsAndData;

import java.util.LinkedList;

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
    private String catagory = "Path";
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public TableData(LinkedList<String> line) {
	
	this.inputLine = line;
	
    }
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

	
	
	
	
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
