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
    private String catagoryEvidence;
    private boolean pvs1 = false;

    private boolean ps1 = false;
    private boolean ps4 = false;
    
    private boolean pm2 = false;
    private boolean pm4 = false;

    private boolean pp2 = false;
    private boolean pp3 = false;

	
	
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
    
    
    
    public void prepareEvidenceString () {
	List<String> evidenceList = new LinkedList<>();
	if (pvs1) {evidenceList.add("PVS1");}
	if (ps1) {evidenceList.add("PS1");}
	if (ps4) {evidenceList.add("PS4");}
	if (pm2) {evidenceList.add("PM2");}
	if (pm4) {evidenceList.add("PM4");}
	if (pp2) {evidenceList.add("PP2");}
	if (pp3) {evidenceList.add("PP3");}
	
	catagoryEvidence = String.join(", ", evidenceList);
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

    public String getCatagoryEvidence() {
	return catagoryEvidence;
    }

    public void setCatagoryEvidence(String catagoryEvidence) {
	this.catagoryEvidence = catagoryEvidence;
    }

    public boolean isPvs1() {
	return pvs1;
    }

    public void setPvs1(boolean pvs1) {
	this.pvs1 = pvs1;
	prepareEvidenceString();

    }

    public boolean isPs1() {
	return ps1;
    }

    public void setPs1(boolean ps1) {
	this.ps1 = ps1;
	prepareEvidenceString();
    }

    public boolean isPs4() {
	return ps4;
    }

    public void setPs4(boolean ps4) {
	this.ps4 = ps4;
	prepareEvidenceString();
    }

    public boolean isPm2() {
	return pm2;
    }

    public void setPm2(boolean pm2) {
	this.pm2 = pm2;
	prepareEvidenceString();
    }

    public boolean isPm4() {
	return pm4;
    }

    public void setPm4(boolean pm4) {
	this.pm4 = pm4;
	prepareEvidenceString();
    }

    public boolean isPp2() {
	return pp2;
    }

    public void setPp2(boolean pp2) {
	this.pp2 = pp2;
	prepareEvidenceString();
    }

    public boolean isPp3() {
	return pp3;
    }

    public void setPp3(boolean pp3) {
	this.pp3 = pp3;
	prepareEvidenceString();
    }
    
    
    


}
