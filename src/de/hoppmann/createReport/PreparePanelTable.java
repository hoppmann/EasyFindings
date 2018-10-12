/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.hoppmann.createReport;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author hoppmann
 */
public class PreparePanelTable {


    ///////////////////////////
    //////// variables ////////
    ///////////////////////////
    
    private List<String> panelGenes = new LinkedList<>();
	
	
	
    /////////////////////////////
    //////// constructor ////////
    /////////////////////////////
	
    public PreparePanelTable(String inputText) {
    
	List<String> geneList = createPanelList(inputText);
	
	
    
    }

	
	
	
    /////////////////////////
    //////// methods ////////
    /////////////////////////

    
    // form list for text field
    private List createPanelList(String inputText) {
	
	// check if input not null if  so split text into list 
	List<String> geneList = new LinkedList<>();
	if (inputText != null) {
	    
	    // remove white spaces
	    List<String> tmpList = Arrays.asList(inputText.split("\n"));
	    for (String curGene : tmpList) {
		geneList.add(curGene.replaceAll("\\s", ""));
	    }
	}

	return geneList;
	
    }
	
    
    
    
    // conpare for changes
    
    
    
    
    // connect to DB
    
    
    // collect infos for gene list
    
    
    // preapre table
    
    // add sum of coding region
    
    // add muliplyer
	
	
	
    /////////////////////////////////
    //////// getter / setter ////////
    /////////////////////////////////











}
