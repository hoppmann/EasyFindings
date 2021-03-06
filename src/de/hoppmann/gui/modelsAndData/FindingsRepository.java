/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.modelsAndData;

import de.hoppmann.gui.messanges.CommonWarnings;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class FindingsRepository {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    private String geneCol;
    private String impactCol;
    private String pNomenCol;
    private String cNomenCol;
    private List<TableData> storedData;
    private LinkedHashMap<String, Integer> header = new LinkedHashMap<>();
    
    
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public FindingsRepository(List<String> header) {

	// add coumun number to header
	int coulumnInt = 0;
	for (String curHeader : header) {
	    
	    this.header.put(curHeader, coulumnInt);
	    coulumnInt++;
	}
	storedData = new LinkedList<>();
	
    }

	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    
    
    // save data for later use
    public void storeFindings(InputRepository inputRepository){
	
	// check if load has data (there was a file chosen)
	if (inputRepository == null) {
	    new CommonWarnings().openFileFirst();
	} else {

	    // store data
	    for (TableData rowData : inputRepository.getRowData()) {

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
    
    
    
    
    // get all stored values of a key of choice
    public List<String> getValueList(String headerKey) {

	List<String> list = new LinkedList<>();

	for (TableData curEntry : storedData) {
            if (header.get(headerKey) != null){
                list.add(curEntry.getInputLine().get(header.get(headerKey)));
            }
	}

	return list;

    }


    // get all stored values to a key given that it belongs to the dependend key
    // if boolean split is true the result is splitted at splitString and each single entry added to list. 
    public List <String> getDependentValueList(String headerKey, String dependKey, String dependValue, boolean split, String splitString) {
	
	List<String> list = new LinkedList<>();
	
	for (TableData curEntry : storedData) {
            if (header.get(headerKey) == null){
                continue;
            }

            String value = curEntry.getInputLine().get(header.get(headerKey));
	    String dependency = curEntry.getInputLine().get(header.get(dependKey));

	    // check if dependency is met
	    if (dependency.equals(dependValue)) {
		
		// check if result should be splitted or not
		if (split){
		    // split line and add each element to list
		    list.addAll(Arrays.asList(value.split(splitString)));
		} else {
		    list.add(value);
		}
	    }
	}
	
	
	return list;
    }
    
    
    
    // get header index
    public Integer getColIndex (String colName) {
	if (header.get(colName) != null) {
	    return header.get(colName);
	} else {
	    return -1;
	}
	
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








}
