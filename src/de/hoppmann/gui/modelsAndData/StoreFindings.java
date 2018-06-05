/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.gui.modelsAndData;

import de.hoppmann.gui.messanges.CommonWarnings;
import de.hoppmann.gui.modelsAndData.TableData;
import de.hoppmann.operations.LoadInputFile;
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
    List<TableData> storedData;
    List<String> header; 
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////

    public StoreFindings(List<String> header) {

	this.header = header;
	storedData = new LinkedList<>();
	
    }

    
	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    public void storeFindings(LoadInputFile load){
	
	// check if load has data (there was a file chosen
	if (load == null) {
	    new CommonWarnings().openFileFirst();
	} else {

	    // store data
	    for (TableData rowData : load.getRowData()) {

		// store non header line
		if (rowData.isCausal()) {
		    storedData.add(rowData);
		}
	    }
	}
    }
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////

    public List<TableData> getStoredData() {
	return storedData;
    }

    public List<String> getHeader() {
	return header;
    }

    
    








}
