/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.modelsAndData;

import de.hoppmann.gui.messanges.CommonWarnings;
import de.hoppmann.operations.LoadInputFile;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class StoreFindings {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private String geneCol;
    private String impactCol;
    private String pNomenCol;
    private String cNomenCol;
    private String col5;
    private String col6;
    private List<TableData> storedData;
    private LinkedHashMap<String, Integer> header = new LinkedHashMap<>();
    
    
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public StoreFindings(List<String> header) {

	for (String curHeader : header) {
	    this.header.put(curHeader, null);
	}
//	this.header = header;
	storedData = new LinkedList<>();
	
    }

    
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    public void storeFindings(LoadInputFile load){
	
	// check if load has data (there was a file chosen)
	if (load == null) {
	    new CommonWarnings().openFileFirst();
	} else {

	    // store data
	    for (TableData rowData : load.getRowData()) {

		// store non header line
		if (rowData.isCausal()) {
		    
		    // check if row was chosen before
		    if ( ! storedData.contains(rowData)){
			storedData.add(rowData);
		    }
		}
	    }
	}
    }
	
	
    
    //// add index to chosen header
    public void addIndex(String headerName, Integer index) {
	
	header.put(headerName, index);
	
    }
    
    
    
    
    
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public List<TableData> getStoredData() {
	return storedData;
    }

    public List<String> getHeaderList() {
	
	return new ArrayList<>(header.keySet()) ;
    }

    public LinkedHashMap<String, Integer> getHeader() {
	return header;
    }

    
    
    public String getGeneCol() {
	return geneCol;
    }

    public void setGeneCol(String geneCol) {
	this.geneCol = geneCol;
    }

    public String getImpactCol() {
	return impactCol;
    }

    public void setImpactCol(String impactCol) {
	this.impactCol = impactCol;
    }

    public String getpNomenCol() {
	return pNomenCol;
    }

    public void setpNomenCol(String pNomenCol) {
	this.pNomenCol = pNomenCol;
    }

    public String getcNomenCol() {
	return cNomenCol;
    }

    public void setcNomenCol(String cNomenCol) {
	this.cNomenCol = cNomenCol;
    }

    public String getCol5() {
	return col5;
    }

    public void setCol5(String col5) {
	this.col5 = col5;
    }

    public String getCol6() {
	return col6;
    }

    public void setCol6(String col6) {
	this.col6 = col6;
    }

    
    
    








}
